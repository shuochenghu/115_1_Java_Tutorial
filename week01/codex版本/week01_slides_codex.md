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

教師先確認還原後的 JDK、IDE、Git／GCM 可用。
每次課程保留最後 **15 分鐘**完成 push 與遠端檢查。

<!-- 教師提示：3×50 分鐘為建議節奏；分支、PR 為課後延伸。首次建立 GitHub repo 後全學期沿用。 -->

---

## 還原卡教室：每次上課的固定流程

```text
上課前：GitHub → clone 整個 115_1_Java → 開啟當日專案
上課中：寫程式 → 測試 → commit → 適時 push
下課前：儲存全部檔案 → commit → push → 網頁確認 → 關機
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
## GitHub 登入與作者資訊不同

- Git for Windows 安裝時保留 **Git Credential Manager（GCM）**。
- 第一次透過 HTTPS 存取時，依 GCM 提示在瀏覽器登入／授權。
- GitHub 帳號密碼不能直接當作 Git HTTPS 操作的密碼。
- 共用電腦請依教室規範清除登入與已儲存認證。

**Token、密碼不放進網址、程式碼、截圖或 AI 紀錄。**
若沒有登入視窗，先檢查 GCM 是否安裝及其設定。

<!-- 來源：https://docs.github.com/en/get-started/git-basics/caching-your-github-credentials-in-git 。學生講義補充 GCM／GitHub CLI 登入處理；不要求學生建立或交出 Token。 -->

---

## 每次上課前：clone 整個學期儲存庫

在教師指定的本機工作位置開啟 PowerShell。
以自己的 GitHub 帳號取代 `YOUR_ACCOUNT`：

```powershell
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git
cd 115_1_Java
git remote -v
git branch --show-current
```

確認遠端是自己的 repo、目前分支是 `main`。
**clone 已建立 `.git` 與 `origin`，不必再 init 或 remote add。**

<!-- 來源：https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository 。目標資料夾若已存在，先查是否有未保存內容；不要直接覆蓋、刪除或在其中再 clone 一份。 -->

---

## 每次 clone 後：確認作者與登入

在 **`115_1_Java` 根目錄**執行，替換為自己的資料：

```powershell
git config user.name "Your Name"
git config user.email "YOUR_GITHUB_EMAIL"
git config --get user.name
git config --get user.email
git status -sb
```

作者資訊存在本機設定，**不會隨 clone 自動帶回**。
GitHub 認證也可能需重新登入；作者資訊不是登入憑證。

<!-- 教師提示：使用 repo 層級設定，避免共用電腦混用作者。信箱可使用自己 GitHub Settings → Emails 提供的 noreply 地址。 -->

---

## 建立當日專案，辨識兩種工作位置

首次實作：在 **`115_1_Java` 根目錄**建立資料夾。

```powershell
New-Item -ItemType Directory -Path 2026_09_18/java-01/src -Force
cd 2026_09_18/java-01
```

| 位置 | 用途 |
|---|---|
| `115_1_Java` | 執行課程 Git 指令、管理整學期成果 |
| `115_1_Java/2026_09_18/java-01` | IDE 開啟位置、編譯與執行 Java |

在專案目錄執行 `cd ../..`，即可回到 repo 根目錄。
日期目錄、專案目錄內都**不要建立另一個 Git 儲存庫**。

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
- **Git for Windows＋GCM、GitHub 帳號**：本機版本與遠端協作。
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
Git 操作用另一個終端機，固定停在 **`115_1_Java` 根目錄**。

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
## Git 與 GitHub：本機版本與遠端協作

```text
工作目錄 ──add──→ 暫存區 ──commit──→ 本機儲存庫
                                       │ push
                                       ▼
                                 GitHub 遠端儲存庫
                                       │ pull
                                       ▼
                                  本機目前分支
```

- `commit` 留下本機版本；`push` 才把提交傳至遠端。
- `pull` 取得遠端更新並整合；操作前先確認分支與工作目錄。
- `clone` 建立另一份本機儲存庫，包含歷史與遠端連線資訊。

<!-- 來源：https://git-scm.com/docs/gittutorial ; https://git-scm.com/docs/git-pull 。pull 並非只下載檔案。 -->

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

## 實作 D：提交第一週的基準版本

在 `java-01` 測試後，回到 **`115_1_Java` 根目錄**。
專案 README 寫執行方式；當日紀錄放 `2026_09_18` 下。

```powershell
git status --short
git add -- .gitignore README.md 2026_09_18
git diff --cached
git commit -m "2026_09_18: add java-01 greeting baseline"
git push origin main
```

`git add` 收進當日程式、紀錄與根目錄設定；先檢查清單。
**GitHub 已有初始化 README 提交，本機不需 git init。**

---

## 實作 D：修改、比較與第二次提交

修改 HelloCourse 第一行並同步專案 README，重新測試。
在 **repo 根目錄**執行：

```powershell
git diff -- 2026_09_18
git add -- 2026_09_18
git diff --cached
git commit -m "2026_09_18: update course greeting"
git push origin main
```

`add` 後又修改檔案，必須再次 `add` 才會納入該筆提交。
**上課中完成一個階段就 commit／push，降低臨時關機的損失。**

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

## 實作 E：確認 GitHub 已收到當日成果

在 **`115_1_Java` 根目錄**執行：

```powershell
git push origin main
git fetch origin
git status -sb
git rev-parse HEAD
git rev-parse origin/main
```

本週在 main 操作：兩個完整雜湊應相同，且沒有未提交修改。
再開 GitHub **main → 2026_09_18 → java-01 → src** 檢查內容。

<!-- 教師提示：工作目錄乾淨不等於推送成功；Everything up-to-date 也不能代表尚未 add/commit 的檔案已上傳。請同時比對清單、分支、雜湊與網頁。 -->

---

## 何時 clone？何時 pull？

| 狀況 | 操作 |
|---|---|
| 教室已還原，本機沒有 repo | clone 整個 `115_1_Java` |
| 同一堂課 repo 還在，遠端有新提交 | 先保存本機修改，再 pull |
| 家中已有同一份 repo | 開始修改前先確認狀態並同步 |

在既有 repo 根目錄、main 且工作目錄乾淨時：

```powershell
git pull --ff-only origin main
```

剛 clone 成功通常已是最新，不需要馬上再 pull。
若歷史分岔停止，先請教師協助，不使用強制推送覆蓋。

---

## 實作 E：演練下一次取回成果

本次 push 後，在 repo 外的工作位置建立**不同名稱的測試副本**：

```powershell
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git 115_1_Java-check
cd 115_1_Java-check/2026_09_18/java-01
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java
java -cp out GreetingApp
```

確認三筆輸出，再回到原本 `115_1_Java` 繼續實作。
這是額外驗證副本；平時每次上課 clone 後資料夾名稱仍為 `115_1_Java`。

<!-- 教師提示：不實際關機、不刪除原工作目錄來測試還原；以新位置 clone 驗證遠端足以重建。 -->

---

## push 失敗或資料夾狀態不對時

| 現象 | 處理方式 |
|---|---|
| clone 目標已存在 | 先檢查舊資料；不要直接刪除或覆蓋 |
| 認證失敗／Repository not found | 核對帳號、網址、權限與 GCM |
| push 被拒／無法快轉 | fetch 後檢視歷史，請教師協助整合 |
| GitHub 缺少日期／檔案 | 檢查是否存檔、add、commit、push 到 main |
| 專案變成另一個 repo | 檢查是否誤建內層 .git，請教師協助 |

**push 失敗不能直接關機；先完成下頁的保存處理。**

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

## 延伸練習：分支與 Pull Request

在 **`115_1_Java` 根目錄**且工作目錄乾淨時：

```powershell
git switch -c docs/verification
```

新增 `2026_09_18/VERIFICATION.md`，整理測試結果，再執行：

```powershell
git add -- 2026_09_18/VERIFICATION.md
git commit -m "2026_09_18: document verification"
git push -u origin docs/verification
```

<!-- 教師提示：只在 main 練習的同學可略過；分支上的未推送提交，同樣會受還原影響。 -->

---
## 延伸練習：審查、合併與同步

1. 在 GitHub 建立 PR：**base = main**，compare = `docs/verification`。
2. 描述修改目的與驗證方式；查看 **Files changed**。
3. 同儕或自己依檢核表審查，確認只有預期修改後合併。
4. 回到 repo 根目錄，同步合併結果：

```powershell
git switch main
git pull --ff-only origin main
git log --oneline --graph --all
```

**PR 不是 `git pull`；建立 PR 也不代表已合併。**

<!-- 來源：https://docs.github.com/en/pull-requests/how-tos/create-pull-requests/creating-a-pull-request 。若有保護規則，依規定取得 review，不略過檢查；延伸時保留分支即可，不必刪除。 -->

---

## 下課前：存檔、測試、提交、推送

先儲存全部檔案，在 `java-01` 測試；再回到 **repo 根目錄**：

```powershell
git status --short
git add -- .gitignore README.md 2026_09_18
git diff --cached
git commit -m "2026_09_18: save java-01 and learning records"
git push origin main
```

清單包含學期 README、忽略規則及當日全部成果，commit 前先檢查。
沒有新修改可略過 commit，**仍須確認已有提交都推送成功**。

<!-- 教師提示：未完成程式也要保存，提交訊息與紀錄標明 WIP 與錯誤，不宣稱測試通過。 -->

---

## 關機前的最後檢核

- `git status --short` 沒有輸出，必要檔案都已追蹤。
- push 成功；fetch 後 **HEAD 與 origin/main 雜湊一致**。
- GitHub main 上看得到當日 `java-01/src` 的最新內容。
- 當日 `LAB_REPORT.md`、`AI_USAGE.md` 與專案 README 已保存。
- 最終 commit 連結交到指定平台；共用電腦依規範登出。

**確認遠端有最新成果，才關機。**
若最後又改了紀錄，要再存檔、commit、push 與確認一次。

<!-- 教師提示：不要求把最終 commit 的雜湊寫回該提交內的檔案；使用繳交平台欄位。必要時人工核對學生 GitHub 當日路徑。 -->

---

## 下一次上課：沿用 repo，新增日期

還原後，在工作位置重新 clone 同一個儲存庫：

```powershell
git clone https://github.com/YOUR_ACCOUNT/115_1_Java.git
cd 115_1_Java
```

依當日日期新增，例如下一次為 `2026_09_25` 時：

```powershell
New-Item -ItemType Directory -Path 2026_09_25/java-01/src -Force
```

先核對作者／登入設定，再開新專案；保留前次 `2026_09_18`。
日期以實際上課日為準，不每週另建 GitHub repo。

---

## 離堂檢核

1. 今天的 repo、日期目錄、專案目錄分別叫什麼？
2. 編譯指令與 Git 指令，各在哪一層執行？
3. 為什麼只存檔或 commit，關機後仍可能失去成果？
4. 下次還原後應 clone 還是 pull？作者設定會自動回來嗎？
5. push 失敗時，在關機前應做什麼？

下週：**基本型態、參考型態與運算子，系統整理 C#／Java 差異。**
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

