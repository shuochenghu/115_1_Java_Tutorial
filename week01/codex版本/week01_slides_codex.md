---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: 第一週｜C# WinForms 銜接 Java 與 GitHub 版本控制
author: 胡碩誠
description: 115 學年度第 1 學期｜物件導向程式設計
footer: '物件導向程式設計｜Week 01'
style: |
  section {
    font-family: "Microsoft JhengHei", "Noto Sans TC", sans-serif;
    font-size: 28px;
    line-height: 1.45;
    color: #203047;
    background: #fbfcfe;
    padding: 48px 64px;
  }
  h1 { font-size: 44px; color: #123d65; }
  h2 { font-size: 38px; color: #123d65; margin-bottom: 22px; }
  strong { color: #006d77; }
  a { color: #126496; }
  pre { font-size: 23px; line-height: 1.35; background: #eef2f6; }
  code { font-family: Consolas, "Microsoft JhengHei", monospace; }
  table { font-size: 25px; width: 100%; }
  th { background: #e7eff5; }
  footer { font-size: 16px; color: #62748a; }
  section::after { font-size: 16px; color: #62748a; }
  blockquote { border-left: 5px solid #00858c; color: #344d63; }
  section.lead { background: #123047; color: #f5f8fc; }
  section.lead h1, section.lead h2 { color: #ffffff; }
  section.lead strong { color: #81ded8; }
  section.small { font-size: 24px; }
  section.small table { font-size: 22px; }
---
<!-- _class: lead -->
<!-- _paginate: false -->
<!-- _footer: '' -->

# 第一週｜銜接 C# WinForms 與 Java

方法與執行流程・Java 工具・Git／GitHub・AI 協作

物件導向程式設計｜115 學年度第 1 學期
資訊管理學系二年級｜胡碩誠

<!-- 教師提示：以學生先前 C# 經驗開場。詢問是否曾經遇過「程式昨天能跑，今天不能跑」，連結環境檢查與版本控制。 -->

---
## 今天要完成的事

- 用 C# WinForms 經驗理解 Java 的方法、參數與回傳值。
- 建立學期儲存庫 **`115_1_Java`**，依日期與專案整理實作。
- 課前 clone，開啟 **`2026_09_18/java-01`** 進行雙 IDE 練習。
- 修改後測試、commit、push，確認 GitHub 已保存最新成果。
- 說明 AI 建議的採用理由，保留人工驗證紀錄。

**教室電腦關機後會還原；未傳到遠端的內容可能遺失。**

---
## 本週進度與參考書

Paul Deitel、Harvey Deitel
**Java How to Program, Early Objects, 11th Edition**（Pearson）

| 閱讀範圍 | 本週用途 |
|---|---|
| §1.5 | 類別、物件、屬性、方法與封裝概念 |
| §1.8–1.10 | Java、開發流程與執行程式 |
| §2.2–2.3 | 第一個程式、文字輸出與常見錯誤 |

方法遷移、Git／GitHub、現行 IDE 操作與 AI 規範為課程補充。

<!-- 來源：專案提供之 EPUB，內部書名與目錄確認為第 11 版。採章節定位，避免 EPUB 與紙本頁碼差異。書中 Java SE 8/9 背景與舊工具畫面須區分；本教材使用原創校園範例。 -->

---
## 課程如何評量

| 項目 | 比例 |
|---|---:|
| 平時作業與課堂練習 | 30% |
| AI 協作紀錄與學習反思 | 15% |
| 期末整合專題與成果展示（含第 17–18 週） | 30% |
| 期末考：物件導向程式邏輯測驗 | 15% |
| 課堂參與、Code Review 與同儕回饋 | 10% |

**能說明自己的程式、修改理由與驗證方式，是本課程的基本要求。**

<!-- 來源：物件導向程式設計與AI協作_課程大綱_整合版.md「評量方式」。無期中考，第 16 週期末考。 -->

---
## 本週課堂安排

| 節次 | 內容（每節 50 分鐘） |
|---|---|
| 第 1 節 | 首次建庫／clone 15 分；C# 銜接 15 分；工具驗證 20 分 |
| 第 2 節 | 方法移植與除錯 30 分；Git 提交與中途 push 20 分 |
| 第 3 節 | AI 驗證 20 分；互查與檢核 15 分；關機前保存 15 分 |

教師先確認還原後的 JDK、IDE、GitHub Desktop 可用。
每次課程保留最後 **15 分鐘**完成 push 與遠端檢查。

<!-- 教師提示：3×50 分鐘為建議節奏；分支、PR 為課後延伸。首次建立 GitHub repo 後全學期沿用。 -->

---
## 還原卡教室：每次上課的固定流程

```text
上課前：Desktop Clone repository → 開啟日期下的 java-01
上課中：寫程式 → 測試 → Commit to main → Push origin
下課前：Save All → Commit → Push origin → 網站確認 → 關機
```

- **存檔**只寫入教室磁碟；**commit**只保存在本機 Git。
- 必須 push 成功，並確認遠端版本，成果才已送回 GitHub。
- 下次開機重新 clone 同一個學期儲存庫，取回歷週成果。

未完成的程式也要保存，並在紀錄寫明問題與待辦。

---
<!-- _class: small -->

## 一個學期儲存庫，日期下放專案

```text
115_1_Java/
├── .git/                  # clone 產生，只有根目錄有
├── .gitignore
├── README.md              # 學期說明／日期索引
└── 2026_09_18/
    ├── LAB_REPORT.md
    ├── AI_USAGE.md
    └── java-01/
        ├── README.md      # 此專案的執行方式
        └── src/           # Java 原始碼
```

同日新增專案可用 `java-02`；下次以實際日期新增資料夾。

<!-- 教師提示：.git 不會顯示在 GitHub 檔案清單；Git 不追蹤空資料夾，需有檔案才會出現在遠端。 -->

---
## 首次上課：建立 GitHub 學期儲存庫

1. 登入自己的 GitHub，選 **New repository**。
2. Repository name 輸入 **`115_1_Java`**。
3. 可見性依教師規定；未指定時建議 Private。
4. **勾選 Add a README file**，建立第一筆提交。
5. 確認預設分支為 **main**，複製 Code → HTTPS 網址。

此 repo **只建一次**；後續每週在同一 repo 增加日期目錄。
私人 repo 需依公告授予教師存取權限。

<!-- 來源：https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-new-repository 。本流程從 GitHub 開始，因此初始化 README 後 clone，不使用先在本機 init 再連空白遠端的流程。 -->

---

## GitHub Desktop：登入自己的帳號

1. 開啟 **GitHub Desktop**，Windows 選 **File → Options → Accounts**。
2. 在 GitHub.com 選 **Sign in**，依提示切換到瀏覽器授權。
3. 確認瀏覽器登入的是本人帳號，再返回 Desktop。
4. 檢查 Accounts 顯示的帳號，避免用到前一位同學的登入。

還原後可能需要重新登入；Desktop 與瀏覽器都要核對。
**使用 Desktop 不需另外操作 GCM；不要把密碼／Token 寫進作業。**

<!-- 來源：https://docs.github.com/en/desktop/installing-and-authenticating-to-github-desktop/authenticating-to-github-in-github-desktop 。本課以 Windows 介面為準；認證畫面文字可能隨版本調整。 -->

---

## 每次上課前：用 Desktop clone

1. **File → Clone repository… → GitHub.com**。
2. 選自己的 **`115_1_Java`**；找不到可用 URL 分頁貼上 HTTPS 網址。
3. 在 **Local path** 選教師指定位置，確認最終路徑以 `115_1_Java` 結尾。
4. 按 **Clone**，等待完成。
5. 核對 **Current repository = 115_1_Java**、**Current branch = main**。

複製的是整學期 repo，**不是只複製 java-01**。
若目標目錄已有資料，先確認是否有未保存內容，不直接刪除。

<!-- 來源：https://docs.github.com/en/desktop/adding-and-cloning-repositories/cloning-and-forking-repositories-from-github-desktop 。也可在網站 Code → Open with GitHub Desktop 啟動；不要誤選 New repository 或重複 Publish repository。 -->

---

## 每次 clone 後：確認提交作者

1. **File → Options → Git**，填入自己的 **Name、Email**，按 Save。
2. 選好 repo 後，查看 **Repository → Repository settings → Git Config**。
3. 若此 repo 使用獨立設定，確認不是別人的姓名與信箱。

可選本人 GitHub 帳號的信箱或提供的 noreply 信箱。
**Accounts 決定登入誰；Git 設定決定 commit 記錄的作者。**
還原後檢查一次；這些本機設定不會隨原始碼 clone 自動帶回。

<!-- 來源：https://docs.github.com/en/desktop/configuring-and-customizing-github-desktop/configuring-git-for-github-desktop 。File → Options → Git 影響全域預設；repo 的 local Git Config 可覆蓋它。 -->

---

## Desktop 畫面：先認識這些位置

| 位置／按鈕 | 用來確認什麼 |
|---|---|
| Current repository／Current branch | 目前 repo 與分支 |
| Changes | 尚未提交的檔案與差異，勾選本次要提交的內容 |
| Summary／Commit to main | 寫提交摘要，建立本機版本 |
| History | 已提交的版本、作者與變更內容 |
| Push origin | 把本機提交傳到 GitHub |
| Fetch origin／Pull origin | 檢查遠端更新／將更新整合到本機 |

**Changes 沒有檔案，不代表本機提交都已上傳。**

---

## 用檔案總管建立當日專案

1. Desktop 選好 repo → **Repository → Show in Explorer**。
2. 在 `115_1_Java` 內新增資料夾 **`2026_09_18`**。
3. 在日期下新增 **`java-01`**，再於其中新增 **`src`**。

| 工具 | 開啟的位置 |
|---|---|
| GitHub Desktop | 整學期 `115_1_Java` repo |
| VS Code／IntelliJ IDEA | `2026_09_18/java-01` 專案 |
| Java 編譯終端機 | `2026_09_18/java-01` 專案 |

Desktop 會偵測子目錄的變更，不需把每個專案另加成 repo。

---
## 你已經用過物件與方法

```csharp
// WinForms 事件處理方法內的片段
string name = nameTextBox.Text;
resultLabel.Text = BuildGreeting(name);
```

| 熟悉的操作 | 這週要說清楚的概念 |
|---|---|
| `nameTextBox`、`resultLabel` | 指向控制項物件的參考 |
| `.Text` | C# 屬性，用來讀取／設定文字 |
| `BuildGreeting(name)` | 呼叫方法，傳入資料並取得回傳值 |

**會操作控制項後，再問：哪些邏輯其實不需要視窗？**

<!-- 教師提示：這是事件方法內片段，並非完整 C# 檔案。Java 不能直接沿用 WinForms 控制項或 C# 的屬性語法。 -->

---
## 執行起點與事件處理

```text
典型 C# WinForms
Main → Application.Run → 訊息迴圈 → 使用者點擊 → Click 處理方法

本週 Java 主控台程式
main → 呼叫 buildGreeting → 取得回傳值 → println → 結束
```

- WinForms 也有啟動程式；按鈕事件方法不是程式進入點。
- 事件方法要在事件發生且已繫結時，才會被呼叫。
- 本週 Java 用固定測試資料取代 TextBox 輸入。

**Java 也能寫 GUI；本週用主控台觀察執行流程。**

<!-- 來源：https://learn.microsoft.com/en-us/dotnet/api/system.windows.forms.application.run 。典型桌面啟動流程示意，省略初始化細節；不把 WinForms 與 Java 語言本身混為比較層級。 -->

---
## C# 經驗可以遷移，API 與慣例要確認

| C# 常見寫法 | 本週 Java 寫法／注意事項 |
|---|---|
| `string name` | `String name`，大小寫不同 |
| `BuildGreeting(name)` | `buildGreeting(name)`，Java 方法通常小寫起頭 |
| `Console.WriteLine(text)` | `System.out.println(text)`，皆為主控台輸出 |
| `resultLabel.Text = text` | UI 更新；本週以主控台輸出呈現結果 |
| `using`、`namespace` | Java 有 `import`、`package`，不是直接換字就等價 |

本週維持無 `package` 的小程式；型態與運算子下週再系統比較。

<!-- 教師提示：Console.WriteLine 不等於 Label 更新。C#／Java 方法名稱大小寫是慣例，語言辨識符號則皆區分大小寫。 -->

---
## 類別與物件：校園借書案例

**類別（class）**：描述一類事物共有的資料與行為。
**物件（object）**：依類別建立、具有個別狀態的實體。

| 概念 | 借書系統的例子 |
|---|---|
| 類別 | `LibraryBook`：一本實體館藏的設計 |
| 物件 | 館藏 B001、館藏 B002 |
| 屬性／狀態 | 館藏編號、書名、目前是否借出 |
| 方法／行為 | 借出、歸還、查詢借閱狀態 |

兩本同書名的館藏，**可以有不同的借閱狀態**。

<!-- 來源：Deitel 第 11 版 §1.5；校園借書案例為課程自編。先談狀態與行為，不要求本週實作建構子。 -->

---
## 封裝：讓物件維護自己的狀態

借書系統收到「借出 B001」的要求時：

1. 找到 B001 對應的物件。
2. 呼叫借出方法，由方法檢查目前是否可借。
3. 檢查通過，才更新該物件的借閱狀態。

**封裝**把相關資料與操作組織在一起；
隱藏內部細節，有助於集中維護資料規則。

思考：若任何程式都能直接改「是否借出」，可能出現什麼問題？

<!-- 來源：Deitel §1.5.7。引導回答：可能跳過資格或重複借閱檢查。private 與方法實作留至第 5 週。 -->

---
## 先認識物件導向的方向

| 概念 | 借閱系統中的想法 | 後續週次 |
|---|---|---|
| 封裝 | 由借出方法檢查並更新狀態 | 5–6 |
| 繼承 | 不同館藏類型延伸共同設計 | 7–8 |
| 抽象化／介面 | 定義可借閱物品應提供的操作 | 9 |
| 多型 | 同一操作，由不同類型提供適當行為 | 8、10 |

本週先練習：**辨識資料、行為，以及誰負責維護資料。**

<!-- 來源：Deitel §1.5 與課綱第 5–10 週。避免將繼承誤教為所有共用程式碼的唯一解法。 -->

---
## Java 程式如何執行

```text
編輯                  編譯                     執行
HelloCourse.java  →   HelloCourse.class   →   JVM 執行
                  javac                    java
```

- `.java`：人可閱讀、修改的原始碼。
- `.class`：編譯後的 **bytecode（位元組碼）**。
- JVM 載入類別、驗證位元組碼，並執行程式。
- IDE 的 Run 會協助完成建置與執行流程。

跨平台仍需**相容的 JVM 版本與程式所需資源**。

<!-- 來源：Deitel §1.9 的編輯、編譯、載入、驗證、執行五階段。執行可能包含直譯與 JIT；不將 bytecode 說成 CPU 直接執行的機器碼。 -->

---
## JDK、JVM 與 IDE 的分工

| 名稱 | 主要工作 |
|---|---|
| JDK | Java 開發工具包，提供 `javac`、`java` 等工具 |
| JVM | 執行 Java 位元組碼的虛擬機器 |
| JRE | Java 執行環境的概念，包含 JVM 與執行所需函式庫 |
| VS Code＋Java 擴充套件 | 編輯、程式提示、執行與除錯 |
| IntelliJ IDEA | 整合專案管理、編輯、建置與除錯 |

本課程安裝 **JDK**；兩套 IDE 的專案都選用同一個 JDK。

<!-- 來源：Deitel §1.9；工具設定補充見 VS Code Java 與 JetBrains 官方文件。現代 JDK 發行方式不同，不要求另裝獨立 JRE。 -->

---
## 安裝清單與本教材版本

- **JDK 21 LTS**：本教材選定的教學基準。
- **VS Code** ＋ Microsoft 的 **Extension Pack for Java**。
- **IntelliJ IDEA**：本週使用一般 Java 專案功能。
- **GitHub Desktop＋GitHub 帳號**：圖形介面的版本控制。
- **Marp for VS Code**：閱讀、預覽本教材時使用。

書籍以 Java SE 8／9 為背景；本週採傳統類別與 `main` 寫法。
教室若統一採其他 JDK，請依教師指定並記錄版本。

<!-- 來源：https://adoptium.net/temurin/releases/?version=21 ; https://code.visualstudio.com/docs/languages/java ; https://marketplace.visualstudio.com/items?itemName=marp-team.marp-vscode 。JDK 21 為教材選擇，不是課綱既有指定。 -->

---
## Windows：安裝並檢查 JDK

1. 至 [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=21) 選擇 JDK 21、Windows 與符合電腦的架構。
2. 執行安裝程式，啟用加入 `PATH`；可同時設定 `JAVA_HOME`。
3. 安裝後重新開啟 PowerShell 與 IDE。
4. 執行下列指令，確認 `java` 與 `javac` 主版本皆為 21。

```powershell
java -version
javac -version
where.exe java
where.exe javac
```

<!-- 來源：https://adoptium.net/installation/windows 。JAVA_HOME 指向 JDK 根目錄，PATH 中是其 bin 子目錄；若電腦已正確設定，不需重複修改。 -->

---
## 環境問題：先確認使用了哪個 JDK

| 現象 | 優先檢查 |
|---|---|
| 找不到 `javac` | 是否安裝 JDK、PATH 是否包含 JDK 的 `bin` |
| `java` 與 `javac` 版本不同 | `where.exe` 是否列出多個安裝位置 |
| IDE 能跑，終端機不能跑 | IDE 的 JDK 與終端機 PATH 可能不同 |
| `UnsupportedClassVersionError` | 執行用 JVM 可能比編譯目標版本舊 |

**先保留錯誤訊息，再確認路徑；每次只調整一項設定。**

<!-- 教師提示：避免一開始就要求學生移除全部 Java。必要時由教師協助選定 JDK，重新編譯後執行。 -->

---
## 第一支程式：HelloCourse.java

```java
public class HelloCourse {
    public static void main(String[] args) {
        // 固定輸出，方便比較不同工具的執行結果。
        System.out.println("Hello, Java!");
        System.out.println("Course: Object-Oriented Programming");
    }
}
```

檔名必須是 **`HelloCourse.java`**，大小寫與公開類別名稱一致。
先使用英文字串，讓首次驗證聚焦在編譯與執行。

<!-- 來源：概念對應 Deitel §2.2；程式為課程自編，完整檔案位於 examples/HelloCourse.java。 -->

---
## 用已知的方法概念讀懂 Java

```java
public static void main(String[] args) {
    String message = buildGreeting("Ada");
    System.out.println(message);
}
```

- `main` 是本週程式的進入點；`args` 是命令列參數，本例未使用。
- `String message` 儲存方法回傳的字串參考。
- `static` 方法可由類別呼叫；此範例不需先 `new` 一個物件。
- `void` 表示不回傳值；`buildGreeting` 則要回傳 `String`。

**不要推論所有 Java 方法都必須是 `static`。**

<!-- 教師提示：本頁為 main 片段，完整程式見後面的 GreetingApp。static 僅為首週方法遷移橋接，不取代第 5 週的物件設計。 -->

---
## 實作 A：在當日專案編譯與執行

將範例放入 `2026_09_18/java-01/src/HelloCourse.java`。
在 **`115_1_Java/2026_09_18/java-01`** 執行：

```powershell
javac -encoding UTF-8 -d out src/HelloCourse.java
java -cp out HelloCourse
```

確認兩行輸出，並檢查專案內的 `out/HelloCourse.class`。
`-d out` 指定編譯輸出；`-cp out` 指定尋找類別的位置。

**下次 clone 不會帶回 out，須重新編譯。**

<!-- 教師提示：編譯失敗先修正，不執行舊 class；所有課程專案的產物皆以 repo 根目錄的 .gitignore 排除。 -->

---
## VS Code：開啟當日的 java-01

1. 確認已安裝 **Extension Pack for Java**。
2. **Open Folder** 選 `115_1_Java/2026_09_18/java-01`。
3. 將範例與起始碼放進該專案的 `src`。
4. 使用 **Java: Configure Java Runtime** 選 JDK 21。
5. 等待 Java 載入完成，點 `main` 上方 **Run**。

每次還原後，重新確認 JDK 與擴充套件是否可用。
Git 操作用 Desktop 選 **`115_1_Java`**，IDE 則只開當日專案。

<!-- 來源：https://code.visualstudio.com/docs/java/java-project 。每次開啟單一課程專案，避免歷週同名類別互相干擾。 -->

---
## VS Code：執行與觀察

1. 將範例放在 `src/HelloCourse.java`，儲存檔案。
2. 等待 Java 專案載入完成。
3. 點選 `main` 上方的 **Run**，確認終端機輸出。
4. 把第一行改成 `Hello, my Java course!`，儲存後再 Run。

若看不到 Run：檢查 Java 擴充套件、JDK 與 `main` 是否正確。

**檢核：確認新輸出後，恢復原本的 `Hello, Java!` 再繼續。**

<!-- 來源：https://code.visualstudio.com/docs/languages/java 。只對自己建立或可信任的課程檔案啟用工作區信任。預設 App.java 可留著，但本週要執行的是 HelloCourse。 -->

---
## IntelliJ IDEA：開啟同一份專案

1. **Open** 選 `115_1_Java/2026_09_18/java-01`。
2. 在 **Project Structure → Project** 設定 SDK 為 JDK 21。
3. 若 `src` 未被辨識，右鍵 → **Mark Directory as → Sources Root**。
4. 開啟 `HelloCourse.java`，用 `main` 左側綠色按鈕執行。

若版本要求匯入專案，選從既有來源建立，位置仍用同一個 `java-01`。
本機 `.idea`／`.iml` 可重新產生；還原後可能需要重設 SDK 與 src。

<!-- 來源：https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html 。不勾 Create Git repository，不在日期或專案內新增 .git；兩套 IDE 操作同一份原始碼。 -->

---
## IntelliJ IDEA：核對原始碼與輸出

1. 確認檔案位於 **`2026_09_18/java-01/src`**。
2. 點 `main` 左側綠色三角形 → **Run 'HelloCourse.main()'**。
3. 在 Run 視窗比對輸出，再用同一份 `GreetingApp` 做三筆測試。
4. 若在另一套 IDE 修改，儲存並確認檔案已重新載入。

兩套 IDE 開啟**同一個 java-01**；不要另存成兩份答案。
執行路徑或 IDE 結束訊息可以不同，程式輸出應一致。

<!-- 教師提示：不用在 repository 外建立另一份 IDEA 驗證專案，以免下課忘記納入 Git。 -->

---
## 兩套工具的操作對照

| 任務 | VS Code＋Java 擴充套件 | IntelliJ IDEA |
|---|---|---|
| 開啟專案 | Open Folder | Open |
| 建立 Java 類別 | 在 `src` 建立 `.java` | New → Java Class |
| 選擇專案 JDK | Configure Java Runtime | Project Structure → SDK |
| 執行 | `main` 上方 Run | `main` 左側綠色三角形 |
| 偵錯 | `main` 上方 Debug | 綠色三角形 → Debug |

**能指出目前檔案、目前類別與所用 JDK，比記住快捷鍵更重要。**

<!-- 來源：VS Code Java 官方文件與 IntelliJ IDEA 官方教學，完整連結見末頁。 -->

---
## 實作 B：把 UI 以外的邏輯移植到 Java

C# 方法：由 WinForms 的 Click 方法呼叫。

```csharp
private string BuildGreeting(string name) {
    return "Hello, " + name + "!";
}
```

完成 `starter/GreetingApp.java` 的 TODO：

```java
static String buildGreeting(String name) {
    // TODO：依相同規則產生並回傳字串，不在此處輸出。
    return "TODO";
}
```


<!-- 來源：課程自編遷移練習；方法語法先以 C# 既有能力銜接，正式方法與物件設計於後續週次深化。 -->

---
## 實作 B：預測、執行與追蹤

`main` 依序呼叫 `buildGreeting`，傳入以下資料：

| 傳入參數 | 預期回傳值 | 驗證重點 |
|---|---|---|
| `"Ada"` | `"Hello, Ada!"` | 逗號、空白與驚嘆號 |
| `"Grace"` | `"Hello, Grace!"` | 未把姓名寫死 |
| `"Ada Lovelace"` | `"Hello, Ada Lovelace!"` | 保留姓名中的空白 |

在方法呼叫處設中斷點，用 **Step Into** 觀察 `name`，
再用 **Step Over** 觀察回傳後的變數與輸出。

本題假設輸入為非空姓名；空值、輸入驗證留待後續練習。

<!-- 教師提示：本頁測試案例用來驗證需求，不是完整程式解答。學生須在講義填入實際輸出及追蹤紀錄。 -->

---
## 除錯：辨認問題發生在哪個階段

| 類型 | 例子 | 驗證方式 |
|---|---|---|
| 編譯錯誤 | 少分號、`system` 大小寫錯誤 | 讀取編譯診斷，修正後重新編譯 |
| 啟動／執行問題 | 指定錯誤類別、JVM 版本不相容 | 檢查類別名、路徑與版本 |
| 邏輯錯誤 | 預期兩行，卻用了兩次 `print` | 與預期輸出逐行比對 |

除錯順序：**重現 → 讀訊息 → 提出原因 → 改一處 → 重跑**。

<!-- 來源：Deitel §1.9、§2.2–2.3。首週不必用例外語法示範執行期錯誤。 -->

---
## 實作 C：診斷 C# 移植留下的錯誤

分別在 `GreetingApp.java` 的副本做一項變更：

1. 把 `String` 改成 C# 常用的 `string`，觀察編譯訊息。
2. 把方法宣告改成 `BuildGreeting`，呼叫處維持原名。
3. 讓方法回傳固定的 `"Hello, Ada!"`，執行三筆測試。

每次記錄 **錯誤階段、原因、最小修正、重跑結果**，再恢復程式。

**前兩項可編譯嗎？第三項會被編譯器發現嗎？**

<!-- 教師提示：一次只做一項，不提交故意破壞的版本。此練習強調型態名稱、識別字大小寫與需求驗證；解答在教師筆記。 -->

---

## Desktop 操作背後的版本概念

```text
IDE 儲存檔案 → Changes 選取變更 → Commit to main → 本機版本
                                                  │
                                             Push origin
                                                  ▼
                                              GitHub
```

- Commit 建立本機快照；Push 才把提交傳到 GitHub。
- History 可比較已提交版本；Changes 顯示尚未提交內容。
- 取消勾選只表示本次不提交，**不是刪除，也不是已備份**。

指令版另放 `git_cli_reference.md`，課堂不要求輸入 Git 指令。

<!-- 來源：https://docs.github.com/en/desktop/making-changes-in-a-branch/committing-and-reviewing-changes-to-your-project-in-github-desktop 。不要把 Desktop 勾選機制直接當作命令列暫存區的凍結快照；提交前重新查看最新差異。 -->

---

## 圖形介面與 Git 操作目的對照（補充）

| 目的 | 本課主要用 Desktop | 指令版概念 |
|---|---|---|
| 取回 repo | Clone repository | clone |
| 查看／選擇變更 | Changes、勾選檔案 | status、diff、add |
| 建立本機版本 | Commit to main | commit |
| 查看歷史 | History | log |
| 上傳提交 | Push origin | push |
| 檢查／整合遠端 | Fetch origin／Pull origin | fetch／pull |

**操作目的相近，按鈕不必然等於某一條含特定參數的指令。**
需要時才查 `git_cli_reference.md`，不必兩種流程都做一次。

---
## 根目錄的 .gitignore 管理所有專案

將教材 `starter/.gitignore` 複製到 **`115_1_Java/.gitignore`**：

```gitignore
*.class
bin/
out/
.idea/
*.iml
```

這些未以 `/` 開頭的規則，也適用於日期／專案子目錄。
原始碼、專案 README、當日紀錄要提交；產物可重新生成。
**只有一個 repo；下一週沿用這份忽略規則。**

---

## 實作 D：Changes → Commit → Push

1. IDE 儲存並測試後，切換 Desktop，確認 repo／main。
2. 在 **Changes** 點選檔案，檢查右側新增與刪除內容。
3. 勾選 `.gitignore`、需要更新的 README 與當日程式／紀錄。
4. **Summary** 填 `2026_09_18: add java-01 greeting baseline`。
5. 按 **Commit to main**，再按 **Push origin**，等待完成。

**兩個按鈕都要理解：Commit 保存本機版本，Push 傳到 GitHub。**
應排除的 out／class 若出現，先檢查忽略規則，勿只取消勾選了事。

<!-- 來源：https://docs.github.com/en/desktop/making-changes-in-a-branch/committing-and-reviewing-changes-to-your-project-in-github-desktop ; https://docs.github.com/en/desktop/making-changes-in-a-branch/pushing-changes-to-github-from-github-desktop -->

---

## 實作 D：比較修改並查看 History

1. 修改 HelloCourse 第一行與專案 README，儲存並重跑。
2. 回 Desktop，在 **Changes** 檢查兩個檔案的差異。
3. 勾選要提交的檔案，Summary 填入此次修改目的。
4. **Commit to main → Push origin**。
5. 切換 **History**，選剛才的提交，指出哪些行被修改。

**勾選後若又改檔案，commit 前再看一次最新差異。**
每完成一個階段就提交並推送，避免只等下課最後一分鐘。

---
## 當日紀錄也要放在 repo 內

```text
115_1_Java/2026_09_18/
├── LAB_REPORT.md      # 講義作答、測試、目前進度
├── AI_USAGE.md        # AI 協作與人工驗證
└── java-01/
    ├── README.md      # 專案用途、指令、預期輸出
    └── src/
```

還沒完成時，寫清楚「已完成、目前錯誤、下次要做什麼」。
**不能只留在桌面、IDE 未存檔分頁或聊天視窗中。**

<!-- 教師提示：紀錄與程式一同保存，不再要求學生暫停填講義做網頁 README 修改實驗。 -->

---

## 實作 E：Desktop 與 GitHub 交叉確認

1. Commit 後按 **Push origin**，等待操作成功、無錯誤。
2. **Fetch origin** 檢查遠端狀態，確認沒有待推送／待拉取更新。
3. 在 **History** 選最新提交，查看其識別碼與內容。
4. **Repository → View on GitHub**，核對 main 的最新提交。
5. 查看 `2026_09_18/java-01/src` 及當日紀錄確實是最新版本。

**沒有待提交檔案，也可能還有未推送的 commit。**
最新 commit 要能在網站找到；只看到 Fetch origin 按鈕仍不足。

<!-- 教師提示：可用 History 提交的右鍵 Copy SHA（依版本）與網站提交識別碼比對；學生可直接提供網站 commit 連結，不需 rev-parse。 -->

---

## Fetch、Pull、Clone 的差別

| 狀況 | Desktop 操作 |
|---|---|
| 還原後沒有本機 repo | File → Clone repository |
| repo 已存在，想檢查遠端 | Fetch origin |
| 遠端有更新，要帶入目前分支 | Pull origin |
| 有本機提交要上傳 | Push origin |

已有 repo 時，先儲存、處理本機修改，再 **Fetch → Pull**。
**Fetch 只取得遠端資訊，不能當成已完成 Pull。**
出現衝突或 rebase 提示先請教師協助；課堂不以強制推送修復。

---

## 實作 E：用另一個位置 clone 驗證

1. 本次 Push 成功後，**File → Clone repository → URL**。
2. 貼同一 repo 網址，Local path 指向另一個新的測試位置。
3. 按 Clone，再 **Show in Explorer** 核對這次的實際路徑。
4. 開啟副本的 `2026_09_18/java-01`，在 IDE 執行兩支程式。
5. 驗證後，回到原本課堂副本繼續實作。

兩份副本可能顯示相同 repo 名稱，要核對本機路徑。
不刪原資料、不真的關機；這是在演練下一次取回遠端成果。

<!-- 教師提示：Desktop 若未同時列出兩份副本，可用 File → Add local repository 選回原 repo 根目錄；Add 是開啟已有 clone，不是 New repository。 -->

---

## Desktop 問題排查

| 現象 | 先檢查 |
|---|---|
| Changes 看不到新程式 | 是否已存檔、放在正確 clone 目錄、被忽略？ |
| 出現其他週或大量非課程檔 | Current repository、實際路徑與勾選內容 |
| No local changes，但網站沒更新 | History 是否有提交？是否完成 Push origin？ |
| 需要登入／沒有權限 | Options → Accounts 是否為自己的帳號？ |
| 推送被拒／衝突 | 保留修改與訊息，請教師協助整合 |

**Discard changes 會捨棄修改，不是取消勾選，也不是備份。**
push 失敗不能直接關機；依下一頁先保存。

---
## 無法推送時：先保存，再處理關機

1. 儲存所有原始碼與紀錄，記下目前錯誤及待辦。
2. 先嘗試修復登入或網路問題，再 push、檢查 GitHub。
3. 若下課前仍無法完成，立即告知教師。
4. 依教師安排，將完整 repo（含 `.git`）保存至 USB 或其他不會還原的位置，並確認備份可讀。

**同一台會還原的磁碟上，另存 ZIP 仍可能一起消失。**
待恢復連線後再補 push，並保留「尚未同步」的清楚紀錄。

<!-- 教師提示：備份含 .git 可保留未推送提交；原始碼與未提交檔案也必須包含。不要為備份加入 token 或憑證。 -->

---
## AI 協作規範

**可以使用 AI** 解釋概念、找錯、發想測試與檢查程式碼。

- 不可直接提交自己無法解釋的 AI 生成程式碼。
- 作業或專題使用 AI，須揭露用途與採用情形。
- 必須附上**人工驗證方式與實際結果**。
- 不上傳個資、密碼、未經授權資料或敏感程式碼。

**AI 的回答是待驗證的建議；提交成果由你負責說明。**

<!-- 來源：課綱「AI 協作規範」。未限定付費工具，工具選擇依課堂可用資源；不可把示範紀錄冒充自己的實際使用。 -->

---
## AI 提問：描述遷移目標與限制

| 資訊 | 本週內容 |
|---|---|
| 已有經驗 | C# WinForms、基本變數與方法呼叫 |
| 遷移目標 | `BuildGreeting` → Java `buildGreeting` |
| 環境與限制 | JDK 21、傳統 class／main、無 GUI／外部套件 |
| 證據 | 起始碼、三筆測試的預期／實際輸出 |
| 要求 | 解釋差異、最小修改、人工驗證步驟 |

**先完成自己的初版，再比較 AI 建議與原始提交。**

<!-- 教師提示：將相關 commit 雜湊填入 AI 使用紀錄，勿把 token、私人路徑或整個儲存庫上傳。 -->

---
## 實作 F：請 AI 審查 Java 移植

```text
我學過 C# WinForms，正在將 BuildGreeting 移植到 Java。
環境是 JDK 21，使用傳統 class/main，程式碼如下：（貼上）
buildGreeting 應回傳 "Hello, " + 姓名 + "!"，方法內不輸出。
請以 Ada、Grace、Ada Lovelace 三筆資料檢查。
先指出 C# 與 Java 的差異，再說明我是否符合需求。
請只提出必要修改，列出可人工執行的驗證步驟。
不要加入 GUI、外部函式庫、輸入驗證或新功能。
```

親自執行三筆測試，填寫 `2026_09_18/AI_USAGE.md`，再提交並 push。

<!-- 教師提示：本次實作不要求一定採用 AI 修改；確認原程式已符合需求，也可以記錄不修改的理由。 -->

---
## 判讀 AI 建議：有顯示不代表有回傳

自編的 AI 建議情境：

```java
static String buildGreeting(String name) {
    System.out.println("Hello, " + name + "!");
    return "";
}
```

1. 呼叫端再 `println(buildGreeting("Ada"))`，會發生什麼？
2. 若日後改成 UI 顯示回傳值，結果是否正確？
3. 以「參數 → 回傳值 → 顯示」分工說明採用或拒絕的原因。

<!-- 教師提示：可編譯但回傳空字串；主控台看似有文字，實際多一空白行。教師解答列出回傳值與副作用的差異。 -->

---
<!-- _class: small -->

## AI 使用紀錄：留下採納與驗證依據

| 欄位 | 本週應填內容 |
|---|---|
| 工具、日期、使用目的 | 使用的服務／模型（可見時）、日期、要解決的問題 |
| 主要 Prompt 摘要 | 環境、原始問題、提供的程式與限制 |
| AI 回覆摘要 | AI 建議修改什麼、原因是什麼 |
| 採用／未採用原因 | 你如何判斷建議是否符合需求 |
| 人工驗證方式與結果 | 指令、預期輸出、實際輸出、是否一致 |
| 最終修改與反思 | 改了哪裡、學到了什麼、仍有什麼疑問 |

使用附檔 `AI_USAGE_template.md`，複製為當日目錄的 `2026_09_18/AI_USAGE.md`。

<!-- 來源：課綱要求之 AI 使用紀錄表；額外加入工具日期以便追溯，不虛構服務或模型名稱。 -->

---

## 延伸 1：從 main 建立分支

1. Desktop 選 `115_1_Java`，確認 **Current branch = main**。
2. 儲存並提交既有修改，確認 Changes 乾淨；Fetch／Pull 同步。
3. **Current branch → New branch**，輸入 `docs/verification`。
4. 若出現來源選擇，選 main，再按 **Create branch**。
5. 確認 Current branch 已變為 **docs/verification**。

分支是另一條版本歷史，**不是新增資料夾，也不需重新 Clone**。
本練習：建立分支 → 新增文件 → PR 審查 → 合併 → 同步本機。

---

## 延伸 2：自行建立文件，提交並上傳

1. 在 repo 的日期資料夾內，自行新增 **VERIFICATION.md**。
2. 填寫 JDK、雙 IDE 執行結果、三筆姓名測試與修正紀錄，存檔。
3. 回 Desktop **Changes**，查看差異並勾選該文件。
4. Summary 填 `Document cross-IDE verification`，提交至目前分支。
5. 按 **Publish branch** 上傳；同一分支後續修改用 **Push origin**。

教材路徑：`2026_09_18/VERIFICATION.md`，放在 java-01 的上一層。
若實際日期目錄是 `115_09_18`，就使用該目錄，不另建一份。

<!-- 教師提示：文件由學生自行建立，不是 Git 自動產生。Commit 按鈕應指向 docs/verification；未完成測試須據實記錄。Publish branch 只上傳分支，尚未合併到 main。 -->

---

## 延伸 3：在 GitHub 建立 PR 並檢查差異

1. Desktop 選 **Preview Pull Request → Create Pull Request**。
2. 瀏覽器核對 **base: main ← compare: docs/verification**。
3. 標題填「新增第一週驗證紀錄」，描述目的、內容與驗證結果。
4. 按 **Create pull request**，進入這筆 PR。
5. 開啟 **Files changed**，確認只有預期文件，內容與測試紀錄正確。

沒有 Desktop PR 按鈕時：GitHub repo → **Pull requests → New pull request**。
需修改時，回原分支編輯、Commit、Push；同一筆 PR 會更新。

<!-- 教師提示：base 是接收修改的分支，compare 是提供修改的分支。依 repo 規則完成同儕審查；自行檢查不等於取得他人的核准。建立 PR 尚未合併。 -->

---

## 延伸 4：執行合併，確認 GitHub main

1. 在 PR 回到 **Conversation**，往下找到合併區域。
2. 確認必要審查與檢查已通過，且沒有待解決的衝突。
3. 按 **Merge pull request → Confirm merge**。
4. 確認 PR 狀態顯示 **Merged**，不是只有 Closed。
5. 回 repo 的 **Code**，選 **main**，確認日期下已有驗證文件。

**這一步完成的是 GitHub 上的合併；教室電腦尚須同步。**
按鈕不可用時，先處理畫面列出的原因並請教師協助。

<!-- 來源：https://docs.github.com/en/pull-requests/how-tos/merge-and-close-pull-requests/merging-a-pull-request 。本頁示範允許 merge commit 的 repo；若 repo 只允許 Squash 或 Rebase，按鈕不同，依教師指定方式確認合併。分支保護、必要核准、檢查與衝突不可略過。Close pull request 不等於 Merge。 -->

---

## 延伸 5：同步本機 main，完成驗證

1. 回 Desktop，確認 Changes 沒有尚未保存的修改。
2. **Current branch → main**，再按 **Fetch origin**。
3. 出現 **Pull origin** 時按下，等待同步完成。
4. **Repository → Show in Explorer**，開啟日期下的驗證文件。
5. 比對內容與 GitHub main；在 History 確認已取得合併結果。

沒有 Pull 按鈕時，先核對 repo／main 與 History，確認是否已同步。
**Merged ＋ GitHub main 有文件 ＋ 本機 main 有相同內容，才完成。**
分支可先保留；若再修改講義紀錄，仍須 Commit／Push 後才關機。

<!-- 教師提示：Fetch 取得遠端資訊，Pull 才整合到本機。不要拿 docs/verification 的最新雜湊直接當作合併後 main 的雜湊；合併方式可能產生不同提交。若畫面提示攜帶未提交變更或衝突，先完成保存並請教師協助。 -->

---

## 下課前：用 Desktop 完成保存

1. IDE **Save All**，測試並補齊當日學習／AI 紀錄。
2. Desktop 選 **115_1_Java／main**，Changes 核對並勾選必要檔案。
3. Summary 填 `2026_09_18: save java-01 and learning records`。
4. **Commit to main → Push origin**，等待成功。
5. **View on GitHub** 檢查程式與紀錄，完成下一頁檢核。

沒有新修改可略過 Commit，但仍要確認既有 commit 已推送。
未完成程式也應保存，紀錄錯誤／待辦，不宣稱測試通過。

---

## 關機前的最後檢核

- **Changes：No local changes**，必要成果也未被錯誤忽略。
- **Push origin 已成功**，同步後沒有待推送／待拉取更新。
- Desktop **History 最新提交**能在 GitHub main 的提交紀錄找到。
- 網站有最新 `java-01/src`、專案 README、當日兩份學習紀錄。
- 提交連結已繳交；依教室規範登出 **Desktop 與瀏覽器**。

**確認遠端保存最新成果，才關機。**
最後又修改紀錄，就再 Save All → Commit → Push → 網站確認。

<!-- 教師提示：避免把未提交的變更留在 stash 後關機；stash 也只在本機。不要為使畫面乾淨而 Discard。 -->

---

## 下一次上課：Desktop clone，再建日期

1. 開機後開 Desktop，確認本人登入與作者設定。
2. **File → Clone repository**，取回同一個 `115_1_Java`。
3. **Show in Explorer**，查看前次日期的成果仍在。
4. 在檔案總管新增實際上課日期，例如 `2026_09_25`。
5. 於其下建立 `java-01/src`，IDE 開啟新的 java-01。

**每週沿用同一個 repo；日期與專案不是新的 repo。**
已有本機 repo 才使用 Fetch／Pull；還原後通常重新 Clone。

---

## 離堂檢核

1. Desktop 應選哪個 repo？IDE 應開哪一層資料夾？
2. Commit to main 與 Push origin 分別把成果存在哪裡？
3. No local changes 能保證 GitHub 已更新嗎？還要查什麼？
4. Fetch origin 與 Pull origin 有何不同？還原後應選哪個操作？
5. push 失敗時，關機前應如何保存？

下週：**基本型態、參考型態與運算子，整理 C#／Java 差異。**
預習：參考書 §2.5–2.8、§3.5。

---
<!-- _class: small -->

## 參考資料與操作文件

- 專案課程大綱；Deitel & Deitel, *Java How to Program, Early Objects*, 11th ed.：§1.5、§1.8–1.10、§2.2–2.3。
- [Temurin JDK](https://adoptium.net/temurin/releases/?version=21)｜[Windows 安裝](https://adoptium.net/installation/windows)
- [VS Code Java](https://code.visualstudio.com/docs/languages/java)｜[JDK 設定](https://code.visualstudio.com/docs/java/java-project)
- [IntelliJ IDEA Java 專案](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)
- [Git 入門](https://git-scm.com/docs/gittutorial)｜[git pull](https://git-scm.com/docs/git-pull)
- [GitHub：建庫](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-new-repository)｜[clone](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)｜[認證](https://docs.github.com/en/get-started/git-basics/caching-your-github-credentials-in-git)｜[建立 PR](https://docs.github.com/en/pull-requests/how-tos/create-pull-requests/creating-a-pull-request)

工具依教室版本為準；本週使用 JDK 21 與一般 Java Class。

---

<!-- _class: small -->

## GitHub Desktop 官方操作文件

- [安裝與設定](https://docs.github.com/en/desktop/installing-and-authenticating-to-github-desktop/setting-up-github-desktop)
- [登入 GitHub](https://docs.github.com/en/desktop/installing-and-authenticating-to-github-desktop/authenticating-to-github-in-github-desktop)｜[作者設定](https://docs.github.com/en/desktop/configuring-and-customizing-github-desktop/configuring-git-for-github-desktop)
- [Clone repo](https://docs.github.com/en/desktop/adding-and-cloning-repositories/cloning-and-forking-repositories-from-github-desktop)
- [查看差異與提交](https://docs.github.com/en/desktop/making-changes-in-a-branch/committing-and-reviewing-changes-to-your-project-in-github-desktop)
- [Push 至 GitHub](https://docs.github.com/en/desktop/making-changes-in-a-branch/pushing-changes-to-github-from-github-desktop)
- [Fetch／Pull 同步](https://docs.github.com/en/desktop/working-with-your-remote-repository-on-github-or-github-enterprise/syncing-your-branch-in-github-desktop)

以 Windows GitHub Desktop 為教學主線，選單依安裝版本可能略有不同。

