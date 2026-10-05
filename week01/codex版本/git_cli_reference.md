# Git 指令補充：GitHub Desktop 操作的替代方式

課堂以 GitHub Desktop 為主，不必同時重做這份指令流程。本檔保留原教材的命令列版本，供教師排錯或學生延伸閱讀；需另行安裝可在終端機使用的 Git。Desktop 本身不要求學生另裝命令列 Git。

所有 Git 指令在 `115_1_Java` 根目錄執行，clone 則在 repo 外的工作位置。Java 編譯指令在 `2026_09_18/java-01` 執行。新建學期 repo、日期結構及關機前遠端驗證規則，與 Desktop 版相同。

桌面按鈕與指令是操作目的的對照，不是完全相同的實作：Desktop 的 Pull origin 不保證等同此處 `git pull --ff-only`。Desktop 勾選變更也不必手動執行 git add。

## 指令與註解怎麼讀

- 程式碼區塊內以 `#` 開頭的是 PowerShell 註解，可連同指令複製貼上；註解本身不會執行。
- `origin` 是 clone 時通常自動設定的遠端名稱；`main` 是本課程使用的分支名稱，兩者不是資料夾名稱。
- `--short` 這類文字是選項；單獨的 `--` 是選項與檔案路徑的分隔符號。
- 引號將含空白的姓名或提交摘要視為同一個參數；請保留引號並替換範例資料。
- 工作目錄是正在編輯的檔案；暫存區是準備提交的內容；Commit 保存本機版本，Push 才送到遠端。
- 指令依各節情境執行，不要一次貼上整份教材。`cd`、`New-Item` 是 PowerShell 指令，`javac`、`java` 是 Java 工具。

## 每次上課前：clone 整個學期儲存庫

在教師指定的本機工作位置開啟 PowerShell。
以自己的 GitHub 帳號取代 `YOUR_ACCOUNT`：

```powershell
# clone：下載儲存庫與版本歷史；網址為來源，預設建立 115_1_Java 資料夾。
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git

# PowerShell 的 cd：切換到剛 clone 的 repo 根目錄，讓後續 Git 指令作用於此 repo。
cd 115_1_Java

# remote：查看遠端設定；-v（verbose）同時列出遠端名稱及 fetch／push 網址，不會上傳或下載。
git remote -v

# branch：查看分支；--show-current 只輸出目前分支名稱，本週應為 main。
git branch --show-current
```

確認遠端是自己的 repo、目前分支是 `main`。
**clone 已建立 `.git` 與 `origin`，不必再 init 或 remote add。**

## 每次 clone 後：確認作者與登入

在 **`115_1_Java` 根目錄**執行，替換為自己的資料：

```powershell
# config：設定此 repo 的提交作者名稱；未加 --global，寫入本機 repo 設定，不影響其他 repo。
git config user.name "Your Name"

# user.email：此 repo 的提交作者信箱；請替換為本人信箱或 GitHub 提供的 noreply 信箱。
git config user.email "YOUR_GITHUB_EMAIL"

# --get：讀取目前生效的設定值；user.name 是作者名稱的設定鍵。
git config --get user.name

# --get：讀取目前生效的設定值；user.email 是作者信箱的設定鍵。
git config --get user.email

# status：查看工作目錄與暫存區；-sb 合併 -s（簡短格式）與 -b（顯示分支及追蹤資訊）。
git status -sb
```

作者資訊存在本機設定，**不會隨 clone 自動帶回**。
GitHub 認證也可能需重新登入；作者資訊不是登入憑證。

## 實作 D：提交第一週的基準版本

在 `java-01` 測試後，回到 **`115_1_Java` 根目錄**。
專案 README 寫執行方式；當日紀錄放 `2026_09_18` 下。

```powershell
# --short（同 -s）：用簡短格式列出變更；前兩欄分別表示暫存區／工作目錄狀態，?? 表示未追蹤。
git status --short

# add：將指定路徑的新增、修改或刪除加入暫存區；-- 結束選項，後面都是路徑。
# 日期目錄會納入其下未被忽略的新檔及已追蹤檔案的變更；不提交其他日期的修改。
git add -- .gitignore README.md 2026_09_18

# diff：比較內容差異；--cached（同 --staged）比較暫存區與 HEAD，檢查下一筆提交的內容。
git diff --cached

# commit：將暫存區內容保存為一筆本機提交；-m（message）後的引號字串是提交摘要，不會自動 Push。
git commit -m "2026_09_18: add java-01 greeting baseline"

# push：把本機 main 的提交送到遠端 origin 的 main；不會上傳尚未 commit 的修改。
git push origin main
```

`git add` 收進當日程式、紀錄與根目錄設定；先檢查清單。
**GitHub 已有初始化 README 提交，本機不需 git init。**

## 實作 D：修改、比較與第二次提交

修改 HelloCourse 第一行並同步專案 README，重新測試。
在 **repo 根目錄**執行：

```powershell
# diff：比較工作目錄與暫存區，查看尚未暫存的修改；-- 後的路徑限制比較範圍。
# 此指令不顯示未追蹤新檔的內容；新檔先用 status 確認，再 add 與 diff --cached 檢查。
git diff -- 2026_09_18

# add：暫存指定日期目錄內的變更；-- 表示後面是路徑，不是功能選項。
git add -- 2026_09_18

# diff：比較內容差異；--cached（同 --staged）比較暫存區與 HEAD，檢查下一筆提交的內容。
git diff --cached

# commit：將暫存區內容保存為一筆本機提交；-m（message）後的引號字串是提交摘要，不會自動 Push。
git commit -m "2026_09_18: update course greeting"

# push：把本機 main 的提交送到遠端 origin 的 main；不會上傳尚未 commit 的修改。
git push origin main
```

`add` 後又修改檔案，必須再次 `add` 才會納入該筆提交。
**上課中完成一個階段就 commit／push，降低臨時關機的損失。**

## 實作 E：確認 GitHub 已收到當日成果

在 **`115_1_Java` 根目錄**執行：

```powershell
# push：把本機 main 的提交送到遠端 origin 的 main；不會上傳尚未 commit 的修改。
git push origin main

# fetch：取得 origin 的遠端提交並更新遠端追蹤參照；不會把修改合併到目前工作分支。
git fetch origin

# status：查看工作目錄與暫存區；-sb 合併 -s（簡短格式）與 -b（顯示分支及追蹤資訊）。
git status -sb

# rev-parse：解析參照並輸出完整提交雜湊；HEAD 是目前取出的提交，本週即本機 main 的最新提交。
git rev-parse HEAD

# origin/main：本機記錄的遠端 main 位置；先 fetch 才能用它核對剛取得的遠端狀態。
git rev-parse origin/main
```

本週在 main 操作：兩個完整雜湊應相同，且沒有未提交修改。
再開 GitHub **main → 2026_09_18 → java-01 → src** 檢查內容。

## 何時 clone？何時 pull？

| 狀況 | 操作 |
|---|---|
| 教室已還原，本機沒有 repo | clone 整個 `115_1_Java` |
| 同一堂課 repo 還在，遠端有新提交 | 先保存本機修改，再 pull |
| 家中已有同一份 repo | 開始修改前先確認狀態並同步 |

在既有 repo 根目錄、main 且工作目錄乾淨時：

```powershell
# pull：取得 origin 的 main 並整合到目前分支；執行前確認目前是 main。
# --ff-only：只允許快轉（直接前移分支指標），歷史分岔時停止，不自動建立合併提交。
git pull --ff-only origin main
```

剛 clone 成功通常已是最新，不需要馬上再 pull。
若歷史分岔停止，先請教師協助，不使用強制推送覆蓋。

## 實作 E：演練下一次取回成果

本次 push 後，在 repo 外的工作位置建立**不同名稱的測試副本**：

```powershell
# clone：下載同一份 repo；最後的 115_1_Java-check 指定本機目標資料夾名稱。
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git 115_1_Java-check

# 切換到測試副本的當日專案目錄，以下編譯指令在此執行。
cd 115_1_Java-check/2026_09_18/java-01

# javac：編譯列出的兩個來源檔；-encoding UTF-8 指定原始碼編碼，-d out 指定輸出目錄。
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java

# java：啟動程式；-cp out 指定類別搜尋路徑，GreetingApp 是要執行的類別名稱。
java -cp out GreetingApp
```

確認三筆輸出，再回到原本 `115_1_Java` 繼續實作。
這是額外驗證副本；平時每次上課 clone 後資料夾名稱仍為 `115_1_Java`。

## 延伸練習：分支與 Pull Request

在 **`115_1_Java` 根目錄**且工作目錄乾淨時：

```powershell
# switch：切換分支；-c（create）從目前提交建立新分支並切換；後面是新分支名稱。
git switch -c docs/verification
```

新增 `2026_09_18/VERIFICATION.md`，整理測試結果，再執行：

```powershell
# add：只暫存此驗證文件的變更；-- 表示後面的內容是檔案路徑。
git add -- 2026_09_18/VERIFICATION.md

# commit：將暫存區內容保存為一筆本機提交；-m（message）後的引號字串是提交摘要，不會自動 Push。
git commit -m "2026_09_18: document verification"

# push：上傳 docs/verification 分支至 origin；-u（--set-upstream）設定上游追蹤關係。
# 成功後，在此分支通常可直接用 git push／git pull；本講義仍明列遠端與分支以利辨識。
git push -u origin docs/verification
```

## 延伸練習：審查、合併與同步

1. 在 GitHub 建立 PR：**base = main**，compare = `docs/verification`。
2. 描述修改目的與驗證方式；查看 **Files changed**。
3. 同儕或自己依檢核表審查，確認只有預期修改後合併。
4. 回到 repo 根目錄，同步合併結果：

```powershell
# switch：切回既有 main 分支；未加 -c，因此不會建立新分支。
git switch main

# pull：取得 origin 的 main 並整合到目前分支；執行前確認目前是 main。
# --ff-only：只允許快轉（直接前移分支指標），歷史分岔時停止，不自動建立合併提交。
git pull --ff-only origin main

# log：查看提交歷史；--oneline 每筆一行（縮短雜湊＋摘要），--graph 顯示文字分支圖。
# --all：從所有參照（包含本機分支、遠端追蹤分支及標籤等）顯示可達歷史，不會自動 fetch。
git log --oneline --graph --all
```

**PR 不是 `git pull`；建立 PR 也不代表已合併。**

## 下課前：存檔、測試、提交、推送

先儲存全部檔案，在 `java-01` 測試；再回到 **repo 根目錄**：

```powershell
# --short（同 -s）：用簡短格式列出變更；前兩欄分別表示暫存區／工作目錄狀態，?? 表示未追蹤。
git status --short

# add：將指定路徑的新增、修改或刪除加入暫存區；-- 結束選項，後面都是路徑。
# 日期目錄會納入其下未被忽略的新檔及已追蹤檔案的變更；不提交其他日期的修改。
git add -- .gitignore README.md 2026_09_18

# diff：比較內容差異；--cached（同 --staged）比較暫存區與 HEAD，檢查下一筆提交的內容。
git diff --cached

# commit：將暫存區內容保存為一筆本機提交；-m（message）後的引號字串是提交摘要，不會自動 Push。
git commit -m "2026_09_18: save java-01 and learning records"

# push：把本機 main 的提交送到遠端 origin 的 main；不會上傳尚未 commit 的修改。
git push origin main
```

清單包含學期 README、忽略規則及當日全部成果，commit 前先檢查。
沒有新修改可略過 commit，**仍須確認已有提交都推送成功**。

## 下一次上課：沿用 repo，新增日期

還原後，在工作位置重新 clone 同一個儲存庫：

```powershell
# clone：下載儲存庫與版本歷史；網址為來源，預設建立 115_1_Java 資料夾。
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git

# PowerShell 的 cd：切換到剛 clone 的 repo 根目錄，讓後續 Git 指令作用於此 repo。
cd 115_1_Java
```

依當日日期新增，例如下一次為 `2026_09_25` 時：

```powershell
# PowerShell 建目錄：-ItemType Directory 指定資料夾，-Path 指定路徑；-Force 允許既有目錄，不清空內容。
New-Item -ItemType Directory -Path 2026_09_25/java-01/src -Force
```

先核對作者／登入設定，再開新專案；保留前次 `2026_09_18`。
日期以實際上課日為準，不每週另建 GitHub repo。

