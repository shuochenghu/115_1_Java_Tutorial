# 第一週教材（Codex 版本）

對象為已有基礎 C# WinForms 經驗的大二學生。以事件處理與方法呼叫銜接 Java，完成雙 IDE 驗證、方法移植、GitHub Desktop／GitHub 版本控制，以及 AI 建議的人工驗證。

## 教材檔案

| 檔案 | 用途 |
|---|---|
| [week01_slides_codex.md](week01_slides_codex.md) | 63 頁 Marp 投影片，16:9，含講者備註 |
| [week01_slides_codex.html](week01_slides_codex.html) | 與 Markdown 同步的瀏覽器版投影片 |
| [student_lab.md](student_lab.md) | 學生實作講義，含 A–F 必做、G 延伸與作答欄位 |
| [git_cli_reference.md](git_cli_reference.md) | Git 指令補充；課堂以 Desktop 為主 |
| [examples/README.md](examples/README.md) | 可複製至學生專案的 README 範例 |
| [examples/HelloCourse.java](examples/HelloCourse.java) | 環境驗證範例 |
| [starter/GreetingApp.java](starter/GreetingApp.java) | 學生方法移植起始碼，保留 TODO |
| [starter/.gitignore](starter/.gitignore) | 個人 Java 練習專案忽略規則 |
| [AI_USAGE_template.md](AI_USAGE_template.md) | 五項簡易 AI 紀錄、三筆測試表與完整填寫範例 |
| [teacher_notes.md](teacher_notes.md) | 教師安排、講義解答及驗收重點 |
| [teacher/GreetingApp.java](teacher/GreetingApp.java) | 教師完整解答，與學生起始碼分開 |

`starter` 與 `teacher` 各有同名 GreetingApp 類別，供不同教學用途；不要放進同一學生專案一起編譯。

## 課程安排

以 3 節各 50 分鐘規劃，教師先確認還原後仍可使用開發工具，學生課前準備 GitHub 帳號，休息另計。JDK 21 LTS 為本教材教學基準；課綱本身未指定 JDK 與每節分鐘數。

- A：WinForms 執行起點、JDK 與雙 IDE 驗證。
- B：把字串處理移植成 Java 方法，追蹤參數與回傳值。
- C：型態／方法名稱的編譯錯誤，以及寫死結果的邏輯錯誤。
- D：Desktop Changes、差異、基準版與修改提交及 History。
- 課前：GitHub 學期建庫、Desktop 登入、Clone、作者設定及日期／專案結構。
- E：GitHub 最新版本核對、clone 後重跑、還原卡教室保存與失敗處理。
- F：AI 審查、三筆人工測試、紀錄與最終推送。
- G：課後延伸分支與 Pull Request，審查合併後同步。

正式教學的型態、運算子與控制流程仍按第 2–3 週安排，完整物件設計留在後續週次。首週聚焦既有程式能力的遷移。

## 學生使用方式

學生整學期使用 GitHub `115_1_Java`。首次在 GitHub 建庫、初始化 README，再 clone；每次上課前還原後重新 clone，依日期建立專案，例如 `115_1_Java/2026_09_18/java-01`。

兩套 IDE 都開 java-01，原始碼放其 src；當日 LAB_REPORT.md 與 AI_USAGE.md 放 2026_09_18 下；.gitignore 放 repo 根目錄。學生起始碼保留 TODO。

GitHub Desktop 管理整個 115_1_Java，編譯在 java-01。下課前預留 15 分鐘保存、commit、push、核對 GitHub，再關機；未完成程式標示 WIP 也要保存。

在 `115_1_Java/2026_09_18/java-01` 執行：

```powershell
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java
java -cp out HelloCourse
java -cp out GreetingApp
```

完成 GreetingApp 後應輸出：

```text
Hello, Ada!
Hello, Grace!
Hello, Ada Lovelace!
```

兩套 IDE 使用同一份已完成原始碼；Desktop 選取整個學期 repo；不在日期／專案內建新 repo。GitHub 認證、私人儲存庫存取及繳交方式詳見講義。教材中的遠端網址為占位範例，必須替換。

## 預覽投影片

在 VS Code 安裝 [Marp for VS Code](https://marketplace.visualstudio.com/items?itemName=marp-team.marp-vscode)，開啟 Markdown 後執行 **Markdown: Open Preview to the Side**（Ctrl+K V）。匯出用 **Marp: Export Slide Deck**。Extension Pack for Java 與 Marp 為不同用途的擴充套件。

也可直接用瀏覽器開啟本目錄的 HTML。修改 Markdown 後要重新匯出 HTML，避免閱讀到舊版。版型、字型與色彩設定已內嵌於 Markdown，無須外部圖片。

## 教師備課與驗證範圍

教材依原課綱及 Deitel《Java How to Program, Early Objects》第 11 版編製；GitHub 與 C# 遷移為教學補充，官方來源連結附於投影片、講義及教師筆記。

先前已在現有 JDK 20.0.2 驗證範例、TODO 起始碼與教師解答，並測試三筆回傳值、無方法內輸出副作用，以及四種錯誤情境。先前 CLI Git 流程以臨時本機 bare remote 模擬：初始化 README、第一堂 clone、日期／專案內編譯、根目錄忽略規則、push 與雜湊核對、第二個位置重新 clone 後編譯，以及新增下個日期並保留前次成果，均已通過。

尚未在 JDK 21、VS Code／IntelliJ IDEA GUI 或真實 GitHub 認證與 PR 網頁完整實測；授課前仍需依教室版本確認。此次以 GitHub 官方文件核對 Desktop 操作，涵蓋登入、Clone、作者設定、Changes、Commit、Push、Fetch／Pull、分支與 PR；未實際操作 Desktop GUI。

本版共 63 頁，已重新匯出 Marp HTML 並檢查分頁與程式碼區塊。Git 指令集中於選讀補充，Java 編譯指令保留於主教材。63 頁已通過瀏覽器元素邊界檢查，未偵測到內容超出設定範圍，並抽查 Desktop 操作頁面截圖。
