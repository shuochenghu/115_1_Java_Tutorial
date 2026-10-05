# 第一週學生實作講義：C# WinForms 經驗遷移與 GitHub 版本控制

適用對象：已學過基本 C# WinForms、變數、流程控制與方法呼叫的大二學生。

本週使用 JDK 21、VS Code＋Extension Pack for Java、IntelliJ IDEA，以及 GitHub Desktop／GitHub。依序完成 A–F；G 為課後延伸。請在每個「待填」處留下自己的觀察，不只貼上成功畫面。

## 課前與檔案準備：還原卡教室

每人整學期只使用一個 GitHub repo：**`115_1_Java`**。每次上課前 clone 整個 repo；當日以 `YYYY_MM_DD` 建立目錄，再放入 Java 專案。教室關機後新增檔案會清除，所以下課前必須確認 push 與 GitHub 最新內容。

### 首次建庫（只做一次）

GitHub → New repository → 名稱 `115_1_Java` → 依教師要求設定可見性（未指定時建議 Private）→ **勾選 Add a README file** → 建立。確認預設分支為 main，複製 Code → HTTPS 網址。私人 repo 依教師公告授予存取權限。

### 每次上課前：GitHub Desktop

1. 開啟 GitHub Desktop，選 **File → Options → Accounts**，使用瀏覽器登入自己的 GitHub 帳號，再返回 Desktop。
2. 選 **File → Clone repository → GitHub.com**，選自己的 `115_1_Java`；或在 URL 分頁貼上自己的 HTTPS repo 網址。
3. 設定 **Local path** 為教師指定位置下的 `115_1_Java`，按 **Clone**。確認最後路徑沒有重複的 `115_1_Java/115_1_Java`。
4. 確認 **Current repository：115_1_Java**、**Current branch：main**。用 **Repository → Show in Explorer** 確認實際位置。
5. 在 **File → Options → Git** 確認自己的 Name、Email，可使用 GitHub Settings → Emails 提供的 noreply 信箱。若 **Repository → Repository settings → Git Config** 有本機覆寫，也須確認是本人資料。

作者設定不等於登入；設定不隨 clone 帶回，還原後須重新確認。clone 已建立 `.git` 與 origin，不再使用 New repository，也不在日期／專案內建立 repo。

若目標目錄已存在，先檢查未保存工作，不覆蓋或刪除；已有本機 repo 可用 **File → Add local repository** 加入 Desktop，再依 E3 同步。

本講義的版本操作全部使用 Desktop；[Git 指令補充](git_cli_reference.md) 僅供選讀，不必重做一次。

### 第一週資料夾

在 Desktop 選 **Repository → Show in Explorer**，於 repo 根目錄依序新增 `2026_09_18`、`java-01`、`src` 資料夾。

- `examples/HelloCourse.java`、`starter/GreetingApp.java` 複製至 `2026_09_18/java-01/src`。
- `starter/.gitignore` 複製至 **repo 根目錄**，下一週沿用。
- 本講義另存為 `2026_09_18/LAB_REPORT.md`，AI 範本另存為 `2026_09_18/AI_USAGE.md`。
- 專案的執行說明存於 `2026_09_18/java-01/README.md`；學期根 README 用作日期索引。
- Desktop 管理整個 `115_1_Java`；編譯／執行指令固定在 `115_1_Java/2026_09_18/java-01`。

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
```

| 檢查 | 實際結果 | 判斷與處理 |
|---|---|---|
| `java` 主版本 | 待填 | 待填 |
| `javac` 主版本 | 待填 | 待填 |
| `java`／`javac` 實際路徑 | 待填 | 待填 |
| GitHub Desktop 可開啟、登入且選對 repo | 待填 | 待填 |

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

可複製教師提供的 `examples/README.md` 至 `2026_09_18/java-01/README.md`，填入自己的資訊；寫明用途、JDK、兩支程式的執行指令與預期輸出。根目錄 README 可加上日期索引。根目錄 `.gitignore` 應包含：

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

### D2. 第一筆課堂提交與中途 Push

1. 在 java-01 確認兩支程式可執行，填寫紀錄並儲存所有檔案。
2. 回 Desktop，確認 repo 是 `115_1_Java`、分支是 `main`。
3. 在 **Changes** 點選每個檔案閱讀差異，勾選根 `.gitignore`、有修改的根 README、當日程式與紀錄。不要包含編譯產物或憑證。
4. 在 **Summary** 填 `2026_09_18: add java-01 greeting baseline`，按 **Commit to main**。
5. 按 **Push origin**，等待成功，再選 **Repository → View on GitHub** 核對遠端。

勾選表示納入這次提交；取消勾選不會刪除檔案。勾選後若又編輯，請再次儲存並檢查 Changes，再提交。不要把勾選視為凍結當下內容。

- History 中的本次 commit：待填。
- 包含哪些當日檔案？待填。
- Push 與 GitHub 核對結果：待填。

### D3. 第二筆課堂提交

把 HelloCourse 第一行改為 `Hello, my Java course!`，同步修改**專案 README** 的預期輸出。測試、存檔後：

1. 在 Desktop 的 **Changes** 查看原始碼與 README 差異，勾選要提交的檔案。
2. Summary 填 `2026_09_18: update course greeting`，按 **Commit to main → Push origin**。
3. 開啟 **History**，選取兩次提交，查看各自修改了哪些內容。

- 第二筆 commit 與實際修改：待填。
- 若取消勾選 README，這次 commit 是否包含它？原檔是否會消失？待填。
- 檔案已存檔，或只有 Commit 成功，為何不能直接關機？待填。

每完成一個階段即可 Commit／Push，不必等到最後一分鐘。

## E｜檢查 GitHub 與演練下一次取回

### E1. 核對遠端已保存

1. 儲存所有檔案，將必要的 Changes 提交；若顯示 **Push origin**，點選並等待完成。
2. 按 **Fetch origin** 取得遠端狀態；若顯示 **Pull origin**，表示遠端有更新，依 E3 處理。
3. 在 **History** 查看 main 的最新 commit；開啟 **Repository → View on GitHub**，切到 main，比對最新 commit 識別碼與內容。
4. 確認 GitHub 的 `2026_09_18/java-01/src`、專案 README、當日兩份紀錄都是最新版本。

**Changes 空白只表示沒有未提交變更；Fetch 也不會替你 Push。** 必須同時確認提交已上傳及網頁內容。

| 核對項目 | 實際結果 |
|---|---|
| repo 網址／分支 | 待填 |
| History 與 GitHub 最新 commit 是否一致 | 待填 |
| 日期下的程式與紀錄是否齊全 | 待填 |

### E2. 新位置 Clone，演練下次還原後取回

1. 完成 E1 後，選 **File → Clone repository → URL**，填同一個 repo 網址。
2. Local path 指向 repo 外尚不存在的 `115_1_Java-check`，按 **Clone**。
3. 用 **Repository → Show in Explorer** 確認現在是測試副本；兩份 repo 在清單中可能顯示相同名稱，要以路徑辨別。
4. 用 IDE 開啟測試副本內 `2026_09_18/java-01`，在此專案目錄執行：

```powershell
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java
java -cp out HelloCourse
java -cp out GreetingApp
```

確認 HelloCourse 是修改後內容，GreetingApp 三筆輸出正確。不需真的關機或刪除原目錄。完成後回到**原本的 115_1_Java** 繼續填紀錄及 F；必要時用 File → Add local repository 選回原路徑。

- 此次 Clone 的 commit、實際路徑與兩支程式輸出：待填。
- 下次剛 Clone 後為何要重新編譯？待填。
- Clone 是否會帶回作者設定與 IDE 的 JDK 路徑？待填。

### E3. 每次上課與家中續作

- 還原後沒有本機 repo：使用 **Clone repository**。
- 家中或同堂課已有 repo：先儲存、提交本機工作，確認 Changes 沒有遺漏，選 main，按 **Fetch origin**；有更新時再按 **Pull origin**。有衝突先請教師協助。
- **Fetch** 更新遠端資訊；**Pull** 將遠端修改整合到目前本機分支。剛 Clone 通常不需立即再 Pull。

下一次例如 2026_09_25，在學期 repo 新增 `2026_09_25/java-01/src`，保留先前日期；同日多專案可用 java-02、java-03。全學期沿用同一個 repo。

- 下次日期／專案路徑：待填。
- Clone、Fetch、Pull 的差異：待填。

### E4. 失敗處理

- 目標資料夾已存在：先檢查未保存工作，不直接刪除或覆蓋。
- 認證或權限問題：核對 Desktop 的登入帳號、repo 網址與私人 repo 權限。
- Changes 看不到修改：確認 IDE 已存檔、Desktop 選到正確實際路徑；檢查忽略規則及巢狀 `.git`。
- Push 被拒或出現衝突：先 Fetch，再請教師檢查歷史與衝突；不要 Force push 或用 Discard changes 隱藏問題。
- `.class` 已被追蹤：新增 `.gitignore` 不會取消既有追蹤，請教師協助處理。

**若關機前仍無法 Push，立刻告知教師**。依安排將完整 repo（包含 `.git`、原始碼與未提交紀錄）備份至 USB 或不會還原的位置，確認可讀，連線恢復後補 Push。不能只在同一還原磁碟另存 ZIP 就關機。

- 本次問題與保存方式（沒有填未遇到）：待填。

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

依 `AI_USAGE_template.md` 的完整範例，填寫五項簡易紀錄：使用方式、提問、AI 建議與自己的決定、親自驗證、學到什麼。第一週不要求填 commit 雜湊或重貼操作指令；三筆結果須據實填寫。原程式已符合需求時可以不修改，說明原因即可。繳交前刪除範例部分。

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
- [ ] 在 java-01 編譯並執行；未完成也須保存，標示錯誤與待辦。
- [ ] Desktop 選對原本的 `115_1_Java` 與 main，檢查 Changes、勾選所有必要檔案。
- [ ] Summary 填 `2026_09_18: save java-01 and learning records`，按 **Commit to main**；未完成可標 WIP。
- [ ] 按 **Push origin** 並等待成功；沒有新修改可略過 Commit，但仍要確認既有提交已上傳。
- [ ] **Fetch origin** 後確認沒有待處理更新，History 與 GitHub main 最新 commit 一致，Changes 無遺漏。
- [ ] GitHub main 當日來源檔與紀錄齊全，最終 commit 連結填於繳交平台。
- [ ] 依教室規範登出 Desktop 與瀏覽器 GitHub；確認遠端保存後才關機。

若最後又修改講義或 AI 紀錄，請再次 Commit／Push／核對。不要以 Stash 代替上傳，Stash 仍在本機。沒有 AI 權限時據實記錄，不影響保存要求。

## G｜課後延伸：Desktop 分支與 Pull Request

1. 完成 F，選 main，確認 Changes 乾淨；按 **Current branch → New branch**，建立 `docs/verification`。
2. 新增 `2026_09_18/VERIFICATION.md`，記錄三筆測試與雙 IDE 結果。
3. 在 Changes 檢查、勾選文件，Summary 填 `Document cross-IDE verification`，提交至目前分支。
4. 按 **Publish branch**，把新分支上傳；後續更新使用 Push origin。
5. 使用 **Preview Pull Request → Create Pull Request**，或到 GitHub 建立 PR，確認 **base: main**、**compare: docs/verification**。
6. 說明目的、修改與驗證，查看 Files changed；依 repo 規則請同學 review，無同儕時自行審查。符合規則後合併。
7. 回 Desktop 選 **Current branch → main**，按 **Fetch origin → Pull origin**，確認驗證文件已出現。PR 尚未合併時，main 不會取得該文件。

| 延伸紀錄 | 填寫（未做填「未做延伸」） |
|---|---|
| PR 網址與 base／compare | 待填 |
| 修改目的、review 發現 | 待填 |
| 是否已合併？本機 main 是否看得到文件？ | 待填 |
| PR 與 Pull origin 有何不同？ | 待填 |

若又修改本講義紀錄，在 main 再提交、Push 並更新平台連結。

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

- [GitHub Desktop：提交與查看差異](https://docs.github.com/en/desktop/making-changes-in-a-branch/committing-and-reviewing-changes-to-your-project-in-github-desktop)
- [GitHub Desktop：同步分支](https://docs.github.com/en/desktop/working-with-your-remote-repository-on-github-or-github-enterprise/syncing-your-branch-in-github-desktop)
