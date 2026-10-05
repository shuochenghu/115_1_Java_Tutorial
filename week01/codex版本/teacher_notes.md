# 第一週教師筆記與實作解答

## 教材定位與深度

對象為已修過基本 C# WinForms 的資管系二年級學生。用控制項、事件處理、方法參數與回傳值作為橋接，略過變數、分號與 if／for 的逐項入門講解；透過診斷題確認是否需要補強。

- 主題依課綱第一週：Java 環境、物件導向概論、Git 與 AI 協作規範；依本次教學需求加入 GitHub 遠端版本控制。
- 參考書：Deitel & Deitel, *Java How to Program, Early Objects*, 11th Edition，Pearson。對應 §1.5、§1.8–1.10、§2.2–2.3；依先前核對的 EPUB 章節，避免不確定的紙本頁碼。
- `GreetingApp` 是自編遷移練習。首週只要求單一字串方法，利用既有 C# 能力引出參數、回傳值、UI 與邏輯分工；不提前要求建構子、繼承、完整類別設計或 JUnit。
- 教學基準維持 JDK 21 LTS、傳統 `public class`／`public static void main(String[] args)`。課綱未指定版本，教室調整版本時須同步實測。
- A–F 必做；G（分支與 PR）為課後延伸。GitHub 是本週正式成果之一，不能以只有本機 commit 代替。

## 教學節奏與還原卡教室流程

學期 repo 固定為 `115_1_Java`；第一週專案為 `2026_09_18/java-01`。GitHub Desktop 管理整個學期 repo，編譯及 IDE 操作在 java-01。每次課前 clone 整個 repo，日期及專案內不做 git init。兩套 IDE 開啟同一份原始碼。

首次在 GitHub 建庫並初始化 README，確認預設分支 main，接著 clone。還原後，本機作者設定、登入認證、IDE JDK 設定可能不在，必須重新確認。教師先將 JDK、IDE、GitHub Desktop 納入可用教室環境。

| 節次 | 50 分鐘安排 |
|---|---|
| 第 1 節 | 建庫／clone 15；C# 銜接 15；工具驗證 20 |
| 第 2 節 | 方法移植與除錯 30；commit 與中途 push 20 |
| 第 3 節 | AI 驗證 20；互查與檢核 15；關機前保存 15 |

關機前核對必要檔案已保存、工作目錄乾淨、push 成功、Fetch 後無待處理更新、History 與 GitHub main 最新 commit 一致，以及 GitHub main 的當日來源檔與紀錄。只 Commit 或只看 Changes 空白均不足。

推送失敗不可直接關機。先協助修復；仍無法完成時，安排將完整 repo（含 .git 及未提交檔案）複製至 USB 或不受還原影響的位置並確認可讀，後續補推送。學生未完成程式也必須保存，標記 WIP、錯誤與待辦。

## A 解答：執行流程與環境

- `nameTextBox`、`resultLabel` 為指向控制項物件的參考；`Text` 是 C# 屬性，不是 Java 的通用語法。
- 典型 WinForms 由 Main 執行初始化及 Application.Run，啟動訊息迴圈；事件已繫結且 Click 發生後才呼叫處理方法。不要把 Click handler 當作程式進入點。
- `BuildGreeting(name)` 的字串組合不依賴 UI，可取出以固定輸入驗證。
- Java 主控台由 main 呼叫方法；UI 框架與程式語言是不同層次，不能說 Java 沒有 GUI。
- javac 編譯原始碼成位元組碼，java 啟動 JVM。IDE 啟動指令與路徑不同不影響相同原始碼的預期程式輸出。

重點診斷：學生能按 Run，不代表能說明執行哪個類別、使用哪個 JDK、是否執行舊的 class。

## B 解答：GreetingApp

完整解答另放在 `teacher/GreetingApp.java`。不要覆蓋 `starter/GreetingApp.java` 的 TODO。

```java
public class GreetingApp {
    public static void main(String[] args) {
        String first = buildGreeting("Ada");
        System.out.println(first);
        System.out.println(buildGreeting("Grace"));
        System.out.println(buildGreeting("Ada Lovelace"));
    }

    static String buildGreeting(String name) {
        // 保持計算與顯示分離，呼叫端才能選擇主控台或 UI。
        return "Hello, " + name + "!";
    }
}
```

預期三行，沒有額外空白行：

```text
Hello, Ada!
Hello, Grace!
Hello, Ada Lovelace!
```

追蹤第一筆：呼叫前引數為 Ada；方法內 name 為 Ada；回到 main 完成賦值後 first 為 Hello, Ada!；執行 println 才顯示。

- C# 的 string 在此對應 Java String；方法命名慣例不同，但宣告與呼叫都必須遵守識別字大小寫。
- 本例 static 讓 static main 能直接呼叫；不需建立 GreetingApp 物件。不應推論所有 Java 方法都是 static。
- 回傳字串能在沒有視窗的環境驗證，也能供不同顯示方式使用。可測試性來自明確輸入／輸出與責任分工，不是加上 static 就自動達成。
- 使用多個姓名可抓出把結果寫死的錯誤。姓名包含空白可檢查是否誤做拆分或刪除；本週不納入 null、空字串或 trim 行為。

## C 解答：三種遷移錯誤

| 修改 | 階段與原因 | 最小修正 |
|---|---|---|
| String → string | 編譯錯誤，找不到 string 型態 | 恢復 String |
| 只改宣告為 BuildGreeting | 編譯錯誤，呼叫處找不到原方法 | 宣告與呼叫名稱一致，本課程用 buildGreeting |
| 回傳固定 Hello, Ada! | 可編譯，Grace 與 Ada Lovelace 測試失敗 | 用傳入參數組合回傳值 |

錯誤訊息可能依 JDK 版本與工具不同。驗收判斷是否正確定位階段、原因與最小修正，不要求逐字背誦訊息。

## D–E 解答：學期 repo 與還原後恢復

- GitHub 初始化 README 是初始提交；clone 取得歷史、origin 與 main，不能再在日期或專案內 init。
- .gitignore 放 repo 根目錄；無開頭斜線的 out/、bin/、.idea/ 規則涵蓋子目錄。
- 日期下存 LAB_REPORT.md、AI_USAGE.md；java-01 內存 README、src。學期根 README 可作日期索引。
- Desktop Changes 顯示已存檔的修改；勾選決定本次提交範圍，取消勾選不刪檔。勾選後又編輯須再檢查差異；不可把勾選等同於 git add 凍結當下內容。CLI 暫存區教學集中在 git_cli_reference.md。
- 本週以兩筆程式提交加階段紀錄作練習，不以固定提交總數評分；完成一階段就 push。
- 工作目錄乾淨只表示沒有未提交變更，不代表所有 commit 已到 GitHub。需確認 repo／main、Push 成功、History 與 GitHub 最新 commit 一致及網頁檔案。
- 還原後沒有 repo 時 clone；已有 repo 才談 pull。家中續作先同步，關閉前仍要推送。
- 作者設定不是版本追蹤內容，clone 後重新設定；JDK 或 IDE 本機路徑也不會自動重建。
- clone 到另一個測試位置，可模擬下次取回成果；必須在日期／專案內重新編譯，驗證不依賴本機 out。
- Git 不追蹤空目錄；有來源檔或說明檔後才會顯示。多日期同名 Java 類別應按個別專案開啟與編譯。

驗證還原流程時不刪原目錄、不真的關機，以新的 clone 副本測試。發現巢狀 .git、認證失敗或分岔時先保留資料，再由教師協助。

## F 解答：AI 建議的副作用

錯誤建議在方法內 println，卻回傳空字串。呼叫端再次 println 時，每筆姓名會先印一行問候，再多一個空白行；真正回傳的值為空字串。若 UI 只顯示回傳值，會得到空內容。

最小修正：方法只回傳組合完成的字串；由呼叫端處理輸出。驗證要同時檢查回傳值與實際輸出，不能只看到 Console 有姓名就判定成功。

學生可以拒絕所有 AI 變更，只要能說明原程式已通過需求。沒有工具權限時，可驗證教師示範並據實標示，不捏造服務、模型或對話紀錄。

第一週 AI_USAGE 採五項簡易紀錄，範本內提供完整的虛構填寫範例。學生不需在文件填 commit 雜湊、模型版本或完整指令；保留提問、採用判斷及三筆實測結果。人工初版仍可由 GitHub History 查閱，最終 commit 連結填在繳交平台。範例不能當成學生的實際紀錄。

## G 延伸解答：分支與 PR

`docs/verification` 承載驗證文件的修改，PR 比較來源分支與 main，讓審查者看目的、差異與測試證據。PR 是協作審查流程，Desktop 的 Pull origin 是本機取得及整合遠端更新的操作；兩者不相同。PR 建立後尚未合併，main 也尚未取得該變更。

合併後在 Desktop 選 Current branch → main，執行 Fetch origin → Pull origin，確認 2026_09_18/VERIFICATION.md 存在；不要求刪除分支，避免額外操作負擔。

## 離堂檢核參考答案

1. repo 是 115_1_Java，日期為 2026_09_18，專案為 java-01。
2. Desktop 管理整個 115_1_Java；編譯／執行在日期下的 java-01。
3. 存檔與 commit 都還在本機；關機還原會清除新增資料，需 push 並核對遠端。
4. 還原後沒有本機 repo，要 clone；作者設定不會隨 clone 帶回。
5. 先請教師協助修復；仍無法 push 時保存完整 repo 至不會還原的位置，確認可讀，恢復連線後補推送。

## 驗收重點

| 面向 | 證據 |
|---|---|
| 經驗遷移 | 說明 Click／main、String、回傳值與 UI 分工 |
| 可執行與追蹤 | 兩套 IDE 輸出一致，能追蹤引數與回傳值 |
| 除錯能力 | 分清編譯錯誤與需求錯誤，能以三筆資料重現 |
| 版本控制 | 多筆有意義的 commit、能用 Changes 選擇提交範圍、閱讀差異與 History |
| GitHub | remote、push、pull、clone 重跑證據與可存取連結 |
| AI 協作 | 人工初版、採用判斷、實際測試結果與反思 |

維持原課綱評量比例；不為本週另訂未授權的配分。G 是延伸，不納入 A–F 的必交缺項。教科書電子檔、憑證與個資均不應進入學生遠端。

## 官方查閱文件

- [WinForms Application.Run](https://learn.microsoft.com/en-us/dotnet/api/system.windows.forms.application.run)
- [VS Code Java 專案](https://code.visualstudio.com/docs/java/java-project)
- [IntelliJ IDEA Java 入門](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)
- [GitHub clone](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)
- [GitHub 認證](https://docs.github.com/en/get-started/git-basics/caching-your-github-credentials-in-git)
- [Git pull](https://git-scm.com/docs/git-pull)
- [GitHub PR](https://docs.github.com/en/pull-requests/how-tos/create-pull-requests/creating-a-pull-request)

## GitHub Desktop 授課補充

- 首次示範 File → Options → Accounts 登入；File → Options → Git 設定作者。作者不是登入身分，repo 的 Git Config 若覆寫也須核對。
- 每堂 File → Clone repository，確認 Local path；Repository → Show in Explorer 建日期目錄。已有 repo 使用 Add local repository，不用 New repository 重建。
- 示範 Changes → 勾選檔案 → Summary → Commit to main → Push origin，再以 History 和 View on GitHub 核對。
- Fetch 只更新遠端資訊，Pull 才整合；Desktop 的 Pull 不應直接解釋成 CLI 的 --ff-only。遇到衝突先保留工作、由教師協助。
- 延伸分支使用 Current branch → New branch → Publish branch → PR；不把 Publish branch 與 Publish repository 混淆。
- 第二份 Clone 用路徑辨識，測完回原始副本；Stash 仍在本機，不能代替 Push。
- Desktop 已含操作所需 Git；只有選做 CLI 補充者才需要另確認命令列 Git 安裝。Java 編譯指令仍為必學內容。
- 本次按官方文件修訂流程，尚未在教室 Desktop GUI、真實登入與 PR 全程實測；課前用示範帳號走一次完整流程。

參考：[Desktop 提交與差異](https://docs.github.com/en/desktop/making-changes-in-a-branch/committing-and-reviewing-changes-to-your-project-in-github-desktop)、[Desktop 同步](https://docs.github.com/en/desktop/working-with-your-remote-repository-on-github-or-github-enterprise/syncing-your-branch-in-github-desktop)。指令對照見 [git_cli_reference.md](git_cli_reference.md)。
