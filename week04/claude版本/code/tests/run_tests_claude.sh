#!/usr/bin/env bash
# run_tests_claude.sh（claude 版本，第 4 週 MissionLog 黑箱測試腳本；macOS、Linux、Git Bash 用）
#
# 作用與 run_tests_claude.ps1 相同：把指定資料夾的 MissionLog.java 編譯到暫存資料夾，
# 逐一執行 cases/*.txt 的情境，把實際輸出和預期輸出逐行比對，最後列出通過數。
#
# 用法（在 claude版本/code/tests 執行）：
#   bash run_tests_claude.sh                 # 測教師解答 ../teacher
#   bash run_tests_claude.sh ../starter      # 測起始碼（會有很多失敗）
#   bash run_tests_claude.sh ~/學生/src 08   # 測某位學生的作業，只跑檔名含 08 的案例
#
# 結束碼：全部通過為 0；有案例失敗或無法編譯為 1。

set -u
here="$(cd "$(dirname "$0")" && pwd)"
source_dir="${1:-$here/../teacher}"
case_filter="${2:-}"
timeout_seconds=10

if [ ! -f "$source_dir/MissionLog.java" ]; then
    echo "找不到 $source_dir/MissionLog.java"
    exit 1
fi

# 複製到暫存資料夾編譯，不在教材或學生資料夾留下 .class 檔；腳本結束時自動刪除。
work="$(mktemp -d)"
trap 'rm -rf "$work"' EXIT
cp "$source_dir/MissionLog.java" "$here/MissionLogProbe.java" "$work/"
cd "$work" || exit 1

echo "測試對象：$(cd "$source_dir" && pwd)/MissionLog.java"
if ! javac -encoding UTF-8 -d out MissionLog.java 2> compile_err.txt; then
    echo "MissionLog.java 編譯失敗："
    cat compile_err.txt
    exit 1
fi
# MissionLogProbe 直接呼叫學生的 runMission、printReplay 等方法；方法名稱或參數被改動時這裡會失敗，
# 此時 Probe 案例一律記為失敗，命令列案例照常執行。
probe_ready=1
if ! javac -encoding UTF-8 -cp out -d out MissionLogProbe.java 2> probe_err.txt; then
    probe_ready=0
    echo "MissionLogProbe 編譯失敗（方法名稱或參數可能被改動），Probe 案例將記為失敗："
    cat probe_err.txt
fi

# macOS 預設沒有 timeout 指令；有就用它擋住無窮迴圈，沒有就直接執行。
runner=()
if command -v timeout > /dev/null 2>&1; then
    runner=(timeout "$timeout_seconds")
fi

passed=0
total=0
failed=()
for case_file in "$here"/cases/*.txt; do
    name="$(basename "$case_file" .txt)"
    if [ -n "$case_filter" ] && [[ "$name" != *"$case_filter"* ]]; then
        continue
    fi
    total=$((total + 1))

    # --- 以上是設定（# 說明、run:、exit:、stderr:），--- 以下是預期的標準輸出。
    header="$(sed '/^---\r\?$/,$d' "$case_file" | tr -d '\r')"
    expected="$(sed '1,/^---\r\?$/d' "$case_file" | tr -d '\r')"
    read -r -a run_args <<< "$(printf '%s\n' "$header" | sed -n 's/^run:[[:space:]]*//p')"
    expected_exit="$(printf '%s\n' "$header" | sed -n 's/^exit:[[:space:]]*//p')"
    expected_exit="${expected_exit:-0}"
    expected_stderr="$(printf '%s\n' "$header" | sed -n 's/^stderr:[[:space:]]*//p')"
    label="$name  java ${run_args[*]}"

    if [ "${run_args[0]}" = "MissionLogProbe" ] && [ "$probe_ready" -eq 0 ]; then
        echo "失敗 $label（MissionLogProbe 無法編譯）"
        failed+=("$name")
        continue
    fi

    "${runner[@]}" java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 \
        -cp out "${run_args[@]}" > actual.txt 2> stderr.txt
    exit_code=$?
    actual="$(tr -d '\r' < actual.txt)"

    problems=()
    [ "$exit_code" -eq 124 ] && [ ${#runner[@]} -gt 0 ] && problems+=("逾時：超過 ${timeout_seconds} 秒仍未結束（可能是無窮迴圈）")
    [ "$exit_code" -ne "$expected_exit" ] && problems+=("結束碼應為 $expected_exit，實際為 $exit_code")
    if [ -n "$expected_stderr" ] && ! grep -qF "$expected_stderr" stderr.txt; then
        problems+=("標準錯誤應包含 $expected_stderr")
    fi
    [ "$actual" != "$expected" ] && problems+=("標準輸出與預期不同")

    if [ ${#problems[@]} -eq 0 ]; then
        echo "通過 $label"
        passed=$((passed + 1))
    else
        echo "失敗 $label"
        printf '%s\n' "$header" | sed -n 's/^#[[:space:]]*/    說明：/p'
        printf '    問題：%s\n' "${problems[@]}"
        if [ "$actual" != "$expected" ]; then
            # diff 的 < 是預期、> 是實際。
            diff <(printf '%s\n' "$expected") <(printf '%s\n' "$actual") | sed 's/^/    /'
        fi
        if [ "$exit_code" -ne "$expected_exit" ] && [ -s stderr.txt ]; then
            echo "    標準錯誤："
            head -n 5 stderr.txt | sed 's/^/      /'
        fi
        failed+=("$name")
    fi
done

echo
echo "$passed/$total 項通過"
if [ ${#failed[@]} -gt 0 ]; then
    echo "失敗案例：${failed[*]}"
    exit 1
fi
exit 0
