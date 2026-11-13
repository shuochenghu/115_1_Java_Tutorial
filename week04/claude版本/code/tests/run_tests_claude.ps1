# run_tests_claude.ps1（claude 版本，第 4 週 MissionLog 黑箱測試腳本）
#
# 作用：把指定資料夾裡的 MissionLog.java 編譯到暫存資料夾，逐一執行 cases\*.txt 的情境，
#       把程式印出的每一行和案例檔裡的預期輸出逐行比對，最後列出通過數。
#       案例分兩類：run: MissionLog …      直接用命令列參數執行學生的 main（能源一律從 20 開始）；
#                   run: MissionLogProbe … 透過 MissionLogProbe 自訂初始能源與樣本，測 main 走不到的情況。
#
# 用法（在 claude版本\code\tests 執行）：
#   powershell -ExecutionPolicy Bypass -File .\run_tests_claude.ps1                       # 測教師解答 ..\teacher
#   powershell -ExecutionPolicy Bypass -File .\run_tests_claude.ps1 -Source ..\starter    # 測起始碼（會有很多失敗）
#   powershell -ExecutionPolicy Bypass -File .\run_tests_claude.ps1 -Source D:\學生\src   # 測某位學生的作業
#   加上 -Case 08 只跑檔名含 08 的案例。
#
# 結束碼：全部通過為 0；有案例失敗或無法編譯為 1，方便之後接到批次批改流程。

param(
    [string]$Source = (Join-Path $PSScriptRoot '..\teacher'),
    [string]$Case = '',
    [int]$TimeoutSeconds = 10
)

$ErrorActionPreference = 'Stop'

# Invoke-Tool：執行 java 或 javac，回傳結束碼、標準輸出與標準錯誤。
# 用 Process 而不是直接 & java，是因為 Windows PowerShell 5.1 會用主控台編碼解讀輸出，
# 中文很容易變成亂碼；這裡明確指定以 UTF-8 讀取。超過時間限制就強制結束，
# 避免學生程式寫出無窮迴圈時整個測試卡住。
function Invoke-Tool([string]$Exe, [string[]]$Arguments, [string]$WorkDir) {
    $psi = New-Object System.Diagnostics.ProcessStartInfo
    $psi.FileName = $Exe
    $psi.Arguments = ($Arguments | ForEach-Object { '"' + $_ + '"' }) -join ' '
    $psi.WorkingDirectory = $WorkDir
    $psi.UseShellExecute = $false
    $psi.RedirectStandardOutput = $true
    $psi.RedirectStandardError = $true
    $psi.StandardOutputEncoding = New-Object System.Text.UTF8Encoding $false
    $psi.StandardErrorEncoding = New-Object System.Text.UTF8Encoding $false
    $process = [System.Diagnostics.Process]::Start($psi)
    $outTask = $process.StandardOutput.ReadToEndAsync()
    $errTask = $process.StandardError.ReadToEndAsync()
    if (-not $process.WaitForExit($TimeoutSeconds * 1000)) {
        $process.Kill()
        return @{ Exit = -1; Out = ''; Err = "逾時：超過 $TimeoutSeconds 秒仍未結束（可能是無窮迴圈）" }
    }
    $process.WaitForExit()
    return @{ Exit = $process.ExitCode; Out = $outTask.Result; Err = $errTask.Result }
}

# Get-NormalizedText：統一換行符號並去掉結尾空行，讓 Windows 的 \r\n 與案例檔的 \n 可以直接比較。
function Get-NormalizedText([string]$Text) {
    return ($Text -replace "`r`n", "`n").TrimEnd("`n")
}

# Read-Case：讀一個案例檔。--- 以上是設定（# 說明、run:、exit:、stderr:），--- 以下是預期的標準輸出。
function Read-Case([string]$Path) {
    $lines = [System.IO.File]::ReadAllLines($Path, [System.Text.Encoding]::UTF8)
    $info = @{ Name = [System.IO.Path]::GetFileNameWithoutExtension($Path); Notes = @(); Run = @();
               Exit = 0; Stderr = ''; Expected = '' }
    $separator = [Array]::IndexOf($lines, '---')
    if ($separator -lt 0) { throw "案例檔缺少 --- 分隔線：$Path" }
    for ($i = 0; $i -lt $separator; $i++) {
        $line = $lines[$i]
        if ($line.StartsWith('#')) { $info.Notes += $line.Substring(1).Trim() }
        elseif ($line.StartsWith('run:')) { $info.Run = @($line.Substring(4).Trim() -split '\s+' | Where-Object { $_ -ne '' }) }
        elseif ($line.StartsWith('exit:')) { $info.Exit = [int]$line.Substring(5).Trim() }
        elseif ($line.StartsWith('stderr:')) { $info.Stderr = $line.Substring(7).Trim() }
    }
    if ($separator + 1 -lt $lines.Length) {
        $info.Expected = Get-NormalizedText ($lines[($separator + 1)..($lines.Length - 1)] -join "`n")
    }
    return $info
}

# Show-Diff：逐行對照預期與實際輸出。相同的行前面是兩個空白，不同的行用 - 標預期、+ 標實際。
function Show-Diff([string]$Expected, [string]$Actual) {
    $exp = if ($Expected -eq '') { @() } else { $Expected -split "`n" }
    $act = if ($Actual -eq '') { @() } else { $Actual -split "`n" }
    $count = [Math]::Max($exp.Count, $act.Count)
    for ($i = 0; $i -lt $count; $i++) {
        $e = if ($i -lt $exp.Count) { $exp[$i] } else { $null }
        $a = if ($i -lt $act.Count) { $act[$i] } else { $null }
        if ($e -ceq $a) {
            Write-Host "      $e"
        } else {
            if ($null -ne $e) { Write-Host "    - $e" -ForegroundColor Yellow }
            if ($null -ne $a) { Write-Host "    + $a" -ForegroundColor Cyan }
        }
    }
}

# ---- 1. 準備暫存資料夾並編譯 ----
$sourceFile = Join-Path $Source 'MissionLog.java'
if (-not (Test-Path -LiteralPath $sourceFile)) {
    Write-Host "找不到 $sourceFile" -ForegroundColor Red
    exit 1
}
foreach ($tool in 'java', 'javac') {
    if (-not (Get-Command $tool -ErrorAction SilentlyContinue)) {
        Write-Host "找不到 $tool，請確認已安裝 JDK 並加入 PATH" -ForegroundColor Red
        exit 1
    }
}

# 先把檔案複製到純英文的暫存路徑再編譯，避免「claude版本」這類中文路徑在部分 JDK 上出問題，
# 也不會在教材或學生資料夾裡留下 .class 檔。
$work = Join-Path ([System.IO.Path]::GetTempPath()) ('missionlog-test-' + [Guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $work | Out-Null
try {
    Copy-Item -LiteralPath $sourceFile -Destination $work
    Copy-Item -LiteralPath (Join-Path $PSScriptRoot 'MissionLogProbe.java') -Destination $work

    Write-Host "測試對象：$((Resolve-Path -LiteralPath $sourceFile).Path)"
    $compile = Invoke-Tool 'javac' @('-encoding', 'UTF-8', '-d', 'out', 'MissionLog.java') $work
    if ($compile.Exit -ne 0) {
        Write-Host "MissionLog.java 編譯失敗：" -ForegroundColor Red
        Write-Host $compile.Err
        exit 1
    }
    # MissionLogProbe 會直接呼叫 runMission、printReplay 等方法；學生若改了方法名稱或參數，
    # 這一步會編譯失敗，此時只跑 MissionLog 的命令列案例，Probe 案例一律記為失敗。
    $probe = Invoke-Tool 'javac' @('-encoding', 'UTF-8', '-cp', 'out', '-d', 'out', 'MissionLogProbe.java') $work
    $probeReady = ($probe.Exit -eq 0)
    if (-not $probeReady) {
        Write-Host "MissionLogProbe 編譯失敗（方法名稱或參數可能被改動），Probe 案例將記為失敗：" -ForegroundColor Yellow
        Write-Host $probe.Err
    }

    # ---- 2. 逐一執行案例 ----
    $javaOptions = @('-Dfile.encoding=UTF-8', '-Dstdout.encoding=UTF-8', '-Dstderr.encoding=UTF-8', '-cp', 'out')
    $caseFiles = Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'cases') -Filter '*.txt' | Sort-Object Name
    if ($Case -ne '') { $caseFiles = $caseFiles | Where-Object { $_.Name -like "*$Case*" } }
    $passed = 0
    $failed = @()
    foreach ($file in $caseFiles) {
        $info = Read-Case $file.FullName
        $label = "$($info.Name)  java $($info.Run -join ' ')"
        if ($info.Run[0] -eq 'MissionLogProbe' -and -not $probeReady) {
            Write-Host "失敗 $label（MissionLogProbe 無法編譯）" -ForegroundColor Red
            $failed += $info.Name
            continue
        }

        $result = Invoke-Tool 'java' ($javaOptions + $info.Run) $work
        $actual = Get-NormalizedText $result.Out
        $problems = @()
        if ($result.Exit -ne $info.Exit) { $problems += "結束碼應為 $($info.Exit)，實際為 $($result.Exit)" }
        if ($info.Stderr -ne '' -and -not $result.Err.Contains($info.Stderr)) { $problems += "標準錯誤應包含 $($info.Stderr)" }
        if ($actual -cne $info.Expected) { $problems += '標準輸出與預期不同' }

        if ($problems.Count -eq 0) {
            Write-Host "通過 $label" -ForegroundColor Green
            $passed++
        } else {
            Write-Host "失敗 $label" -ForegroundColor Red
            foreach ($note in $info.Notes) { Write-Host "    說明：$note" }
            foreach ($problem in $problems) { Write-Host "    問題：$problem" }
            if ($actual -cne $info.Expected) { Show-Diff $info.Expected $actual }
            if ($result.Exit -ne $info.Exit -and $result.Err -ne '') {
                Write-Host "    標準錯誤："
                ($result.Err.TrimEnd() -split "`r?`n") | Select-Object -First 5 | ForEach-Object { Write-Host "      $_" }
            }
            $failed += $info.Name
        }
    }

    # ---- 3. 總結 ----
    $total = @($caseFiles).Count
    Write-Host ''
    Write-Host "$passed/$total 項通過"
    if ($failed.Count -gt 0) {
        Write-Host "失敗案例：$($failed -join ', ')" -ForegroundColor Red
        exit 1
    }
    exit 0
} finally {
    Remove-Item -LiteralPath $work -Recurse -Force -ErrorAction SilentlyContinue
}
