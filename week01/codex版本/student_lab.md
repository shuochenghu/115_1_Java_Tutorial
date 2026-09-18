# 第一週學生實作講義：C# WinForms 經驗遷移與 GitHub 版本控制

適用對象：已學過基本 C# WinForms、變數、流程控制與方法呼叫的大二學生。

本週使用 JDK 21、VS Code＋Extension Pack for Java、IntelliJ IDEA，以及 Git／GitHub。依序完成 A–F；G 為課後延伸。請在每個「待填」處留下自己的觀察，不只貼上成功畫面。

## 課前與檔案準備：還原卡教室

每人整學期只使用一個 GitHub repo：**`115_1_Java`**。每次上課前 clone 整個 repo；當日以 `YYYY_MM_DD` 建立目錄，再放入 Java 專案。教室關機後新增檔案會清除，所以下課前必須確認 push 與 GitHub 最新內容。

### 首次建庫（只做一次）

GitHub → New repository → 名稱 `115_1_Java` → 依教師要求設定可見性（未指定時建議 Private）→ **勾選 Add a README file** → 建立。確認預設分支為 main，複製 Code → HTTPS 網址。私人 repo 依教師公告授予存取權限。

### 每次上課前

在教師指定的本機工作位置開啟 PowerShell，以自己的帳號取代 YOUR_ACCOUNT：

```powershell
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git
cd 115_1_Java
git remote -v
git branch --show-current
git config user.name "Your Name"
git config user.email "YOUR_GITHUB_EMAIL"
```

作者與信箱請填本人資料，可用 GitHub Settings → Emails 的 noreply 信箱；設定不隨 clone 帶回，還原後需重新確認。HTTPS 依 GCM 提示登入；不要把憑證放進指令網址或作業。clone 已設定 .git、origin 與分支追蹤，不再 git init 或 remote add。

若目標 115_1_Java 已存在，先確認是否有未保存內容，不要直接覆蓋、刪除或在其中再 clone。一台尚未還原、已有乾淨 repo 的電腦，可用 pull 同步。

### 第一週資料夾

在 repo 根目錄建立：

```powershell
New-Item -ItemType Directory -Path 2026_09_18/java-01/src -Force
```

- `examples/HelloCourse.java`、`starter/GreetingApp.java` 複製至 `2026_09_18/java-01/src`。
- `starter/.gitignore` 複製至 **repo 根目錄**，下一週沿用。
- 本講義另存為 `2026_09_18/LAB_REPORT.md`，AI 範本另存為 `2026_09_18/AI_USAGE.md`。
- 專案的執行說明存於 `2026_09_18/java-01/README.md`；學期根 README 用作日期索引。
- 所有 Git 指令固定在 `115_1_Java` 根目錄；編譯／執行指令固定在 `115_1_Java/2026_09_18/java-01`。
- 專案內 `cd ../..` 回 repo 根目錄；repo 內 `cd 2026_09_18/java-01` 進入專案。

| 基本資料 | 填寫 |
|---|---|
| 課堂暱稱／日期 | 待填 |
| repo 本機根目錄 | 待填 |
| 當日專案相對路徑 | `2026_09_18/java-01` |
| GitHub repo 網址與初始 clone 版本 | 待填 |
| 作業系統與 JDK 版本 | 待填 |

## A｜解釋並驗證執行環境

### A1. 連結已學過的 WinForms 經驗

以下是事件處理方法內的 C# 片段，不是完整程式：

```csharp
string name = nameTextBox.Text;
resultLabel.Text = BuildGreeting(name);
```

1. `nameTextBox` 與 `resultLabel` 分別代表什麼？`Text` 是什麼？
   - 回答：待填。
2. 使用者尚未點擊按鈕時，已繫結的 Click 處理方法會自動執行嗎？WinForms 的程式起點是否就是這個方法？
   - 回答：待填。
3. 哪一段可以脫離畫面、用固定資料驗證？
   - 回答：待填。

### A2. 檢查命令列環境

開啟 PowerShell，執行：

```powershell
java -version
javac -version
where.exe java
where.exe javac
git --version
```

| 檢查 | 實際結果 | 判斷與處理 |
|---|---|---|
| `java` 主版本 | 待填 | 待填 |
| `javac` 主版本 | 待填 | 待填 |
| `java`／`javac` 實際路徑 | 待填 | 待填 |
| Git 版本（本講義使用 Git 2.28+） | 待填 | 待填 |

若版本不一致，先檢查 PATH 與 IDE 指定的 JDK；調整後重新開啟終端機。`JAVA_HOME` 應指向 JDK 根目錄，PATH 所需的執行檔位於該目錄的 `bin`。

### A3. HelloCourse：命令列與兩套 IDE

在 `115_1_Java/2026_09_18/java-01` 執行（後面統一採這個編譯方式）：

```powershell
javac -encoding UTF-8 -d out src/HelloCourse.java
java -cp out HelloCourse
```

預期輸出：

```text
Hello, Java!
Course: Object-Oriented Programming
```

`-d out` 將編譯結果放入 `out`；`-cp out` 指定執行時尋找類別的位置。編譯失敗先修正，不執行可能殘留的舊 `.class`。

VS Code：Open Folder 開啟 `2026_09_18/java-01`，等待 Java 擴充套件載入，使用 **Java: Configure Java Runtime** 選定 JDK 21，再點 `main` 上方 **Run**。

IntelliJ IDEA：Open 開啟同一個 `2026_09_18/java-01`，在 Project Structure 設 SDK 21；必要時將 src 標為 Sources Root。若需匯入，使用從既有來源建立專案的流程，位置仍是 java-01。不要勾選建立 Git repo。兩套 IDE 共用原始碼，.idea／.iml 可由 IDE 重新建立。

| 執行方式 | JDK | 實際程式輸出／證據 | 是否符合預期 |
|---|---|---|---|
| PowerShell | 待填 | 待填 | 待填 |
| VS Code | 待填 | 待填 | 待填 |
| IntelliJ IDEA | 待填 | 待填 | 待填 |

請解釋：IDE 啟動路徑不同，但程式輸出相同，是否正常？`javac` 與 `java` 各負責哪一段？

回答：待填。

## B｜移植不依賴 UI 的方法

### B1. 需求與起始碼

C# 的原方法：

```csharp
private string BuildGreeting(string name) {
    return "Hello, " + name + "!";
}
```

開啟個人專案的 `src/GreetingApp.java`，完成唯一的 TODO：

- `buildGreeting` 接收姓名，回傳 `Hello, `＋姓名＋`!`。
- 保留姓名本身的字元及空白，不新增 trim、大小寫轉換或輸入介面。
- 方法內不做輸出；由 `main` 決定如何呈現回傳值。
- 本題假設非空姓名；不要求處理 `null` 或空字串。

起始碼可編譯，但目前回傳 `TODO`，所以不符合需求。不要把「可以按 Run」當成完成。

在 `115_1_Java/2026_09_18/java-01` 執行：

```powershell
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java
java -cp out GreetingApp
```

### B2. 先預測，再填實際結果

| 輸入 | 預期回傳值 | 實際輸出 | 是否通過／如何修正 |
|---|---|---|---|
| `Ada` | `Hello, Ada!` | 待填 | 待填 |
| `Grace` | `Hello, Grace!` | 待填 | 待填 |
| `Ada Lovelace` | `Hello, Ada Lovelace!` | 待填 | 待填 |

兩套 IDE 開啟同一個 java-01。修改後先存檔，在另一套工具確認內容已重新載入，再重新編譯執行，比較同一版本的輸出。

| 工具 | 三行輸出／截圖位置 | 與命令列結果是否一致 |
|---|---|---|
| VS Code | 待填 | 待填 |
| IntelliJ IDEA | 待填 | 待填 |

### B3. 追蹤方法呼叫

在 `String first = buildGreeting("Ada");` 設定中斷點，以 Debug 啟動。使用 Step Into 進入 `buildGreeting`，觀察 `name`；執行回傳後觀察 `first`，再執行輸出。

| 停下的位置 | 應觀察的資料 | 實際觀察 |
|---|---|---|
| 呼叫前 | 即將傳入的值 | 待填 |
| `buildGreeting` 內 | 參數 `name` | 待填 |
| 回到 `main` 且完成賦值後 | 變數 `first` | 待填 |
| `println(first)` 執行後 | Console 輸出 | 待填 |

1. 方法命名 `BuildGreeting`／`buildGreeting` 是慣例差異；呼叫與宣告的大小寫不一致，是否仍可呼叫？待填。
2. C# `string` 能直接貼到 Java 使用嗎？待填。
3. 本例把方法設為 `static`，是為了什麼？是否所有 Java 方法都應如此？待填。
4. 為何回傳字串比直接更新 `resultLabel.Text` 更容易用固定資料驗證？待填。

## C｜分類、重現與修正錯誤

先保留已完成的程式內容。每次只引入一個錯誤，記錄後恢復，再開始下一題。編譯失敗時不可繼續以舊 `.class` 宣稱測試通過。

| 實驗 | 預測結果 | 實際訊息／輸出 | 原因、最小修正及重跑結果 |
|---|---|---|---|
| 方法回傳型態的 `String` 改成 `string` | 待填 | 待填 | 待填 |
| 宣告改 `BuildGreeting`，呼叫仍為 `buildGreeting` | 待填 | 待填 | 待填 |
| 方法回傳固定的 `Hello, Ada!` | 待填 | 待填 | 待填 |

哪個問題可通過編譯，卻無法通過三筆需求測試？為什麼？

回答：待填。

結束本節前，恢復正確版本，確認三筆測試都通過。

## D｜在學期 repo 提交當日實作

### D1. README 與忽略規則

在 `2026_09_18/java-01/README.md` 寫明用途、JDK、兩支程式的執行指令與預期輸出。根目錄 README 可加上日期索引。根目錄 `.gitignore` 應包含：

```gitignore
*.class
bin/
out/
.idea/
*.iml
```

這些規則涵蓋各層子目錄；不把日期／專案建立成另一個 Git repo。

- 為什麼 .java 要保存，out 與 .class 可以不保存？待填。
- repo 根 README 與 java-01/README.md 的用途有何不同？待填。

### D2. 第一筆課堂提交與中途 push

在 java-01 確認兩支程式可執行，填寫目前紀錄；再從專案目錄執行 `cd ../..` 回 **115_1_Java**：

```powershell
git status --short
git add -- .gitignore README.md 2026_09_18
git diff --cached
git commit -m "2026_09_18: add java-01 greeting baseline"
git push origin main
```

此清單也包含根 README；沒有變更的檔案不會產生額外差異。commit 前檢查暫存區，確定原始碼、當日紀錄與專案 README 在正確層級，沒有編譯產物或憑證。

- 本次 commit：待填。
- 包含哪些當日檔案？待填。
- push 成功與 GitHub 核對結果：待填。

### D3. 第二筆課堂提交

把 HelloCourse 第一行改為 `Hello, my Java course!`，同步修改**專案 README** 的預期輸出。在 java-01 測試後，回 repo 根目錄：

```powershell
git diff -- 2026_09_18
git add -- 2026_09_18
git diff --cached
git commit -m "2026_09_18: update course greeting"
git push origin main
git log --oneline -3
```

- 第二筆 commit 與實際修改：待填。
- add 後又修改檔案，commit 是否包含新修改？用暫存區概念說明：待填。
- 檔案已存檔，或只是 commit 成功，為何不能直接關機？待填。

`git diff` 比較工作目錄與暫存區，`git diff --cached` 看準備提交的差異。每完成一個階段即可 commit／push，不必等到最後一分鐘。

## E｜檢查 GitHub 與演練下一次取回

### E1. 核對遠端已保存

儲存並提交目前改動後，在 **115_1_Java 根目錄**執行：

```powershell
git push origin main
git fetch origin
git status -sb
git rev-parse HEAD
git rev-parse origin/main
```

本週主線操作 main；兩個雜湊應相同，而且沒有未提交檔案。開啟 GitHub main，檢查 `2026_09_18/java-01/src` 原始碼、專案 README 與當日紀錄。Everything up-to-date 不代表未存檔或未 commit 的內容已上傳。

| 核對項目 | 實際結果 |
|---|---|
| repo 網址／分支 | 待填 |
| 本機與遠端 commit 是否一致 | 待填 |
| 日期下的程式與紀錄是否齊全 | 待填 |

### E2. 新位置 clone，演練下次還原後取回

先保存並推送目前內容，再於 repo 外的工作位置執行。測試副本名稱 `115_1_Java-check` 必須尚不存在：

```powershell
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git 115_1_Java-check
cd 115_1_Java-check/2026_09_18/java-01
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java
java -cp out HelloCourse
java -cp out GreetingApp
```

確認 HelloCourse 是修改後的內容，GreetingApp 三筆輸出正確。不需要真的關機或刪除原目錄。此處只是驗證副本，完成後回到**原本 115_1_Java** 繼續填紀錄及 F。

- 此次 clone 的 commit 與兩支程式輸出：待填。
- 下次剛 clone 後為何要重新編譯？待填。
- clone 是否會帶回作者設定與 IDE 的 JDK 路徑？待填。

### E3. 每次上課與家中續作

還原後沒有本機 repo，執行 clone。若家中或同一堂課已有 repo，先保存工作、確認 main 與工作目錄乾淨，再 `git pull --ff-only origin main`。剛 clone 通常不需立即再 pull。

下一次例如在 2026_09_25 上課，在 repo 根目錄建立 `2026_09_25/java-01/src`（日期依實際上課日），保留既有 2026_09_18；同日多個專案可依序用 java-02、java-03。全學期沿用同一 GitHub repo。

- 下次預計使用的日期／專案路徑：待填。
- clone 與 pull 的差異：待填。

### E4. 失敗處理

- 目標資料夾已存在：先檢查有無未保存工作，不直接刪除或覆蓋。
- 認證或權限問題：核對帳號、HTTPS 網址、私人 repo 權限及 GCM。
- push 被拒或無法快轉：先 fetch 與查看提交圖，請教師協助；不要 force push。
- 專案中誤有 .git：先由教師檢查，不能把整個專案當作巢狀 repo 而漏交來源檔。
- .class 仍被追蹤：.gitignore 不會取消已有追蹤；檢查 git ls-files，由教師協助取消特定產物的追蹤。

**若關機前仍無法 push，立刻告知教師**。先儲存檔案，依教師安排將完整 repo（包含 .git、原始碼與尚未提交的紀錄）備份至 USB 或不會還原的位置，確認可讀，連線恢復後補 push。不能只在同一還原磁碟另存 ZIP 就關機。

- 本次遇到的問題與保存方式（沒有填未遇到）：待填。

## F｜AI 審查、人工驗證與最終提交

### F1. 先保存人工初版，再請 AI 檢查

以下是提示範例。以自己已完成的 `GreetingApp.java` 與實際環境填入：

```text
我學過 C# WinForms，正在把 BuildGreeting 移植到 Java。
環境為 JDK 21，採傳統 class/main，不使用 GUI 或外部函式庫。
程式碼：（貼上自己的程式）
buildGreeting 應回傳 "Hello, " + 姓名 + "!"，不在方法內輸出。
輸入假設為非空姓名，保留姓名中的空白，不增加輸入驗證。
請以 Ada、Grace、Ada Lovelace 三筆資料檢查。
先說明 C# 與 Java 的差異，指出是否符合需求；如需修改，僅做必要變更。
請列出我能親自執行的驗證步驟。
```

填寫 `AI_USAGE.md`，包含初版 commit、回覆摘要、採用或未採用原因、指令、三筆實際結果。原程式已符合需求時，可以不改程式，但須記錄判斷依據。

### F2. 即使畫面有文字，也要檢查回傳值

下列為教材自編的 AI 建議情境，不是真實服務紀錄：

```java
static String buildGreeting(String name) {
    System.out.println("Hello, " + name + "!");
    return "";
}
```

1. 把這段放入測試副本，主程式執行 `System.out.println(buildGreeting("Ada"));`，精確記錄包含空白行的輸出：待填。
2. 實際回傳值是什麼？若 UI 顯示這個回傳值會得到什麼？待填。
3. 為何此建議不符合 B 的需求？最小修正方向為何？待填。

實驗後恢復符合需求的版本，不把錯誤示範當作最終答案。

### F3. 下課關機前：完整保存

- [ ] 儲存所有檔案，將進度、測試結果與待辦寫入當日紀錄。
- [ ] 在 `2026_09_18/java-01` 編譯並執行；未完成時也須保存，明確標示錯誤與待辦。
- [ ] repo 根目錄 `git status --short` 已核對必要檔案，無巢狀 .git 或不該上傳的內容。

在 **115_1_Java 根目錄**：

```powershell
git add -- .gitignore README.md 2026_09_18
git diff --cached
git commit -m "2026_09_18: save java-01 and learning records"
git push origin main
git fetch origin
git status -sb
git rev-parse HEAD
git rev-parse origin/main
```

先核對暫存區是否包含本次預計保存的內容。沒有新修改可略過 commit，但仍要 push 與核對；若尚有未完成程式，提交訊息可標 WIP，記錄實際失敗而非宣稱通過。

- [ ] fetch 後 HEAD 與 origin/main 一致，且工作目錄乾淨。
- [ ] GitHub main 的 `2026_09_18/java-01/src` 是最新版本，當日紀錄齊全。
- [ ] 最終 commit 連結已填於繳交平台，不必再寫回同一 commit 的檔案。
- [ ] 共用電腦依教室規範登出；確認 GitHub 已保存後才關機。

若最後又修改講義或 AI 紀錄，請再次 commit／push／核對。沒有 AI 工具權限時據實標示使用教師示範或未使用，不影響保存程式與紀錄的要求。

## G｜課後延伸：分支與 Pull Request

在 F 完成後、`115_1_Java` 根目錄的 `main` 且工作目錄乾淨時開始：

```powershell
git switch -c docs/verification
```

自行新增 `2026_09_18/VERIFICATION.md`，記錄三筆測試與雙 IDE 結果；再執行：

```powershell
git add -- 2026_09_18/VERIFICATION.md
git commit -m "Document cross-IDE verification"
git push -u origin docs/verification
```

1. 在 GitHub 建立 Pull Request，選 **base: main**、**compare: docs/verification**。
2. 標題說明本次修改；內容寫出目的、修改檔案與驗證結果。
3. 在 Files changed 確认只新增預期的驗證文件。請有權限的同學 review；無同儕時依檢核表自行審查。
4. 確認符合儲存庫規則後合併。若要求他人核准，不略過規則。
5. 回到本機：

```powershell
git switch main
git pull --ff-only origin main
git log --oneline --graph --all
```

| 延伸紀錄 | 填寫（未做填「未做延伸」） |
|---|---|
| PR 網址與 base／compare | 待填 |
| 修改目的、review 發現 | 待填 |
| 是否已合併？本機是否看得到文件？ | 待填 |
| PR 與 `git pull` 有何不同？ | 待填 |

若完成 G 後還要更新本講義紀錄，另提交一次並 push，再於平台更新最終提交雜湊即可。

## 繳交清單

繳交平台與期限依教師公告；私人儲存庫需在 GitHub 的存取管理設定中，依公告邀請教師帳號並確認對方可存取。

| 成果 | 要求 |
|---|---|
| GitHub 網址與最終 commit | 可存取、與最終本機版本一致 |
| `2026_09_18/java-01/src/HelloCourse.java` | D3 修改後版本 |
| `2026_09_18/java-01/src/GreetingApp.java` | 完成 TODO、三筆測試通過 |
| 根 `.gitignore`、`java-01/README.md` | 排除產物；說明重現方式與預期輸出 |
| `2026_09_18/LAB_REPORT.md` | 本講義作答、實際輸出與 GitHub 操作證據 |
| `2026_09_18/AI_USAGE.md` | 實際協作情形、採納原因、人工驗證 |
| PR 連結／`VERIFICATION.md` | 僅延伸練習提交 |

## 查閱文件

- [VS Code Java 專案](https://code.visualstudio.com/docs/java/java-project)
- [IntelliJ IDEA Java 入門](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)
- [GitHub：clone](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
- [GitHub：認證工具](https://docs.github.com/en/get-started/git-basics/caching-your-github-credentials-in-git)
- [Git：pull 與快轉](https://git-scm.com/docs/git-pull)
- [GitHub：建立 Pull Request](https://docs.github.com/en/pull-requests/how-tos/create-pull-requests/creating-a-pull-request)
