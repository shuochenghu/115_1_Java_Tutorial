---
marp: true
theme: default
size: 16:9
paginate: true
title: "第1週：Java程式環境、物件導向開發概論與Git/GitHub版本控制"
description: "物件導向程式設計（與AI協作）115學年度第1學期 第1週投影片（claude版本，適用已學過 C# WinForms 的大二學生）"
style: |
  section { font-size: 25px; }
  section h1 { font-size: 1.6em; }
  section h2 { font-size: 1.2em; }
  section code { font-size: 0.85em; }
  section pre { font-size: 0.74em; }
  section table { font-size: 0.78em; }
  /* 生成來源標註：右上角徽章。by-claude = Claude 生成 */
  section.by-claude::before {
    content: "claude"; background: #c96442; color: #fff;
    position: absolute; top: 14px; right: 20px;
    font-size: 0.5em; font-weight: bold; letter-spacing: 0.05em;
    padding: 2px 10px; border-radius: 10px;
  }
  section.lead h1, section.lead h2, section.lead p { text-align: center; }
  .small { font-size: 0.78em; }
---

<!-- _class: lead by-claude -->

# 第 1 週
# Java 程式環境、物件導向開發概論與 Git / GitHub 版本控制

## 建立 AI 協作學習規範

物件導向程式設計（與 AI 協作）｜115 學年度第 1 學期
資訊管理學系 二年級｜授課教師：胡碩誠

<!--
講者備註：學生大一已學過 C# 與 WinForms，所以本週不從「什麼是程式」講起，
而是用 C# 的既有經驗對照 Java。重點是：環境、第一支程式、Git/GitHub 兩次 commit、AI 規範。
-->

---

<!-- _class: by-claude -->

## 關於這份教材

- 本課程教材由教師與 AI 工具協作編寫，每頁右上角標註初稿來源：
  <span style="background:#c96442;color:#fff;padding:2px 10px;border-radius:10px;font-size:0.8em">claude</span> = 初稿由 Anthropic Claude 生成，教師審閱修改後使用
- 這正是本課程要各位做的事：**使用 AI 可以，但要揭露、要驗證、要負責**
- 參考書：Deitel & Deitel, *Java How to Program, Early Objects*, 11th ed.（第 1 章、2.2 節）
- 搭配文件：`week01_實作講義.md`（課堂與回家實作步驟）、`AI使用紀錄表_範本.md`

**你已經會的（大一 C#）**：變數、if/for、方法、class、`new`、WinForms 事件處理
**本週要補上的**：Java 的工具鏈、Java 版本的 OOP 名詞、Git/GitHub、AI 協作規範

---

<!-- _class: by-claude -->

## 本週學習目標

上完本週課程，你應該能：

1. 對照 C#/.NET 說明 Java 的編譯執行流程（`javac` → bytecode → JVM），並用 `javap` 看到 bytecode
2. 把 WinForms 專案裡的 `Form1`、按鈕、事件處理，對應到類別、物件、屬性、方法、繼承
3. 安裝 JDK 21、VS Code + Extension Pack for Java、IntelliJ IDEA、Git，並在兩種 IDE 執行程式
4. 寫出一支會讀取命令列參數的 Java 程式，並自己定義一個簡單類別
5. 用 Git 完成兩次 commit，推上 GitHub，並在 GitHub 上比較兩次 commit 的差異
6. 說出本課程的 AI 協作規範，並填寫「AI 使用紀錄表」

---

<!-- _class: by-claude -->

## 今日流程（3 小時）

| 時間 | 段落 | 形式 |
|---|---|---|
| 15 分 | A. 課程說明與評量方式 | 講授 |
| 40 分 | B. 從 C# 到 Java：平台與物件導向對照 | 講授 + 問答 |
| 45 分 | C. 開發環境、第一支程式與程式結構 | 教師示範 + 同步操作 |
| 40 分 | D. Git 與 GitHub 版本控制 | 教師示範 + 同步操作 |
| 20 分 | E. AI 協作規範與 AI 使用紀錄表 | 講授 + 示範 |
| 20 分 | F. 課堂實作（依實作講義）與隨堂小測 | 實作 |

---

<!-- _class: lead by-claude -->

# A. 課程說明

---

<!-- _class: by-claude -->

## 這門課在學什麼？

**物件導向程式設計 (OOP)** + **與 AI 協作開發的實務能力**

- 用 Java 重新走一遍 OOP：封裝、繼承、多型、抽象類別、介面、泛型、例外、Lambda
- 以 Java 設計、實作、測試（JUnit）與維護中小型程式
- AI 輔助：需求理解、程式發想、除錯、測試案例設計、Code Review、重構、文件
- Git/GitHub：追蹤並比較「手寫程式碼」與「AI 建議程式碼」的差異
- 期末專題：結合物件導向設計、單元測試與 AI 協作歷程紀錄

> 你們大一寫過 C# WinForms，這學期的 Java 是「第二個 OOP 語言」。
> 學第二個語言的正確方法：不是重學，而是**對照**。第 2 週起每週都有 C# ↔ Java 對照。

---

<!-- _class: by-claude -->

## 評量方式

| 項目 | 佔比 |
|---|---|
| 平時作業與課堂練習 | 30% |
| AI 協作紀錄與學習反思 | 15% |
| 期末整合專題與成果展示（含第 17–18 週自主學習產出） | 30% |
| 期末考（物件導向程式邏輯測驗，無期中考） | 15% |
| 課堂參與、Code Review 與同儕回饋 | 10% |

- **作業一律以 GitHub 儲存庫連結繳交**，助教看的是 commit 歷史，不只是最終檔案
- **每次主要作業／專題附「AI 使用紀錄表」**
- 評分重點：問題理解、設計選擇、程式正確性、驗證能力與反思，**不是 AI 回答本身**

---

<!-- _class: by-claude -->

## 16 週路線圖（節錄）

| 週 | 主題 | 週 | 主題 |
|---|---|---|---|
| 1 | 環境、C#→Java 對照、Git/GitHub、AI 規範 | 9 | 抽象類別與介面 |
| 2 | 基本型態、參考型態、運算子 | 10 | 過載與多型 |
| 3 | 選擇與重複結構 | 11 | 泛型 |
| 4 | 陣列、ArrayList、Collections | 12 | 例外處理與執行緒 |
| 5–6 | 類別與物件設計、UML | 13 | Lambda 與 Stream |
| 7–8 | 繼承、覆寫、多型基礎 | 14 | JUnit 單元測試 |
| | | 15–16 | 期末專題啟動、期末考 |

第 17–18 週：自主學習（專題擴充、AI 使用揭露、影片說明、同儕審查）

---

<!-- _class: lead by-claude -->

# B. 從 C# 到 Java：平台與物件導向對照

Deitel 第 1 章：1.5、1.8、1.9 節

---

<!-- _class: by-claude -->

## Java 是什麼？（Deitel 1.8）

- 1991 年 Sun Microsystems（James Gosling）設計，以 C++ 為基礎的物件導向語言；2010 年併入 Oracle
- 口號 **Write once, run anywhere**：原始碼編譯成與 CPU 無關的 bytecode，由各平台的 JVM 執行
- C# 在 2000 年由 Microsoft 推出，設計上大量參考 Java，所以兩者**語法相似度非常高**
- 應用：企業後端（Spring）、Android、大數據（Hadoop、Kafka、Spark）、金融系統

**版本**

| 年份 | 2014 | 2018 | 2021 | 2023 | 2025 |
|---|---|---|---|---|---|
| LTS 版本 | Java 8 | Java 11 | Java 17 | **Java 21**（本課程） | Java 25 |

- 教科書以 Java 8/9 撰寫；本課程核心語法在 21 上完全適用，新語法會另外標示
- JDK 發行商很多（Oracle、Eclipse Temurin、Microsoft、Amazon Corretto），課程用 **Temurin 21**

---

<!-- _class: by-claude -->

## 平台對照：.NET vs Java

| 你熟悉的 C# / .NET | Java 對應 | 說明 |
|---|---|---|
| `.cs` 原始碼 | `.java` 原始碼 | 檔名規則不同（稍後） |
| C# 編譯器 (`csc`) → **IL** | `javac` → **bytecode**（`.class`） | 都是中介碼，不是機器碼 |
| **CLR** + JIT | **JVM** + JIT（HotSpot） | 執行時把中介碼再編成機器碼 |
| .NET SDK | **JDK** | 開發工具組 |
| .NET Runtime | **JRE** | 只執行不開發 |
| BCL（`System.*`） | Java API（`java.*`） | 標準類別庫 |
| NuGet | Maven Central | 套件庫（第 14 週才會用） |
| `.sln` / `.csproj` | 資料夾 + 建置工具（Maven/Gradle）或 IDE 專案 | 本週先不用建置工具 |
| Visual Studio | IntelliJ IDEA / VS Code | 本課程兩者都用 |

---

<!-- _class: by-claude -->

## Java 開發環境的五個階段（Deitel 1.9）

```
 Phase 1        Phase 2            Phase 3        Phase 4         Phase 5
  編輯    →      編譯       →       載入     →     驗證     →      執行
 Editor      javac 編譯器       Class Loader   Bytecode       JVM (直譯 +
 .java   →   產生 .class       把 .class      Verifier       JIT 編譯)
             (bytecode)        載入記憶體      檢查安全性
```

- **載入**：JVM 的 class loader 把你的 `.class` 與用到的 Java API `.class` 載入記憶體
- **驗證**：bytecode verifier 檢查 bytecode 合法且不違反安全限制（網路下載的程式也不能亂搞）
- **執行**：早期純直譯很慢；現代 JVM 找出「熱點」用 JIT 編成機器碼，所以 Java 程式**跑起來**其實不慢
- 想親眼看 bytecode：`javap -c Welcome1`（實作講義 Lab 2 會做）

---

<!-- _class: by-claude -->

## 你早就寫過物件導向：WinForms 回顧

大一的 WinForms 專案裡其實已經有所有 OOP 元件：

```csharp
public partial class Form1 : Form              // 類別 Form1「繼承」Form
{
    private int clickCount = 0;                // 屬性（欄位 / 實例變數）
    public Form1() { InitializeComponent(); }  // 建構子
    private void button1_Click(object sender, EventArgs e)   // 方法
    {
        clickCount++;
        label1.Text = $"你按了 {clickCount} 次";   // 對 label1 物件送訊息
    }
}
Application.Run(new Form1());                  // new = 實例化，產生 Form1 物件
```

- `Form` 類別是微軟寫好的，你**繼承**它並加上自己的東西
- `button1`、`label1` 是 `Button`、`Label` 類別的**物件**，各自有自己的 `Text`
- 你不需要知道 `Form` 怎麼畫視窗，這就是**封裝與資訊隱藏**

---

<!-- _class: by-claude -->

## 物件導向名詞對照（Deitel 1.5 × 你的 WinForms 經驗）

| Deitel 的汽車比喻 | OOP 名詞 | WinForms 裡的例子 |
|---|---|---|
| 工程設計圖 | **類別 (class)** | `class Form1`、`Button` |
| 造出來的一台真車 | **物件 (object) / 實例 (instance)** | `new Form1()`、`button1` |
| 造車 | **實例化 (instantiation)** | `new Button()` |
| 油門踏板：踩了就加速，不必懂引擎 | **方法 (method)**，隱藏實作 | `label1.Text = ...` 背後的重繪 |
| 踩油門 = 對車下指令 | **呼叫方法 / 傳送訊息** | `this.Close()` |
| 顏色、油量、里程 | **屬性 / 實例變數** | `clickCount`、`button1.Text` |
| 敞篷車是一種汽車 | **繼承 (inheritance)** | `Form1 : Form` |
| 方向盤 + 油門 + 煞車 = 基本駕駛介面 | **介面 (interface)** | `IDisposable`、`IComparable` |

**多型 (polymorphism)**：第 8、10 週正式介紹；你在 C# 用過 `override` 就已經碰過了。

---

<!-- _class: by-claude -->

## C# 與 Java 的 OOP 語法對照（預告，第 5 週起詳細講）

| 概念 | C# | Java |
|---|---|---|
| 繼承 | `class Dog : Animal` | `class Dog extends Animal` |
| 實作介面 | `class Dog : IRunnable` | `class Dog implements Runnable` |
| 覆寫 | `override void Speak()`（父類要 `virtual`） | `@Override void speak()`（預設可覆寫） |
| 屬性 | `public string Name { get; set; }` | `getName()` / `setName()` 方法（沒有 property 語法） |
| 命名空間 / 套件 | `namespace App.Models` + `using` | `package app.models` + `import` |
| 程式進入點 | `static void Main(string[] args)` | `public static void main(String[] args)` |
| 輸出 | `Console.WriteLine(x)` | `System.out.println(x)` |
| 字串 / 布林 | `string`、`bool` | `String`、`boolean` |
| 方法命名 | `PascalCase` | `camelCase` |

> Java **沒有** property、`partial class`、事件 `event` 關鍵字、`var` 以外的型別推斷限制較多。這些差異第 2 週細講。

---

<!-- _class: by-claude -->

## 重用、OOAD 與 AI（Deitel 1.5.4、1.5.10）

**Software Engineering Observation 1.1**：用積木的方式組合程式，避免重新發明輪子。

- WinForms 的 `Button`、`DataGridView` 是重用；Java API 的 `String`、`ArrayList`、`Scanner` 也是
- 經過大量測試的類別庫比自己寫更快、更可靠

**OOAD（物件導向分析與設計）**：先分析「要做什麼」、再設計「怎麼做」、審查後才寫程式；第 6 週用 UML 類別圖

**這門課的延伸思考：AI 生成的程式碼是一種「重用」嗎？**
- 是，但它**沒有**經過大量測試與同儕審查，且可能與你的設計不一致
- 所以要求：讀懂、編譯、執行、測試、能解釋，才可以採用；需求講清楚（分析）是 Prompt 的核心

---

<!-- _class: lead by-claude -->

# C. 開發環境、第一支程式與程式結構

VS Code + Extension Pack for Java ／ IntelliJ IDEA

---

<!-- _class: by-claude -->

## 本課程的工具清單

| 工具 | 用途 | 下載 |
|---|---|---|
| **JDK 21 LTS**（Eclipse Temurin） | 編譯與執行 Java | adoptium.net |
| **VS Code** + **Extension Pack for Java** | 輕量編輯器 + Java 支援 + AI 擴充 | code.visualstudio.com |
| **IntelliJ IDEA**（免費版即可） | 專業 Java IDE | jetbrains.com/idea |
| **Git for Windows** | 版本控制（含 Git Bash、Credential Manager） | git-scm.com |
| **GitHub 帳號** | 遠端儲存庫、繳交作業 | github.com（用學校信箱可申請 Student Pack） |

安裝後在 PowerShell 確認（版本號應為 21.x 與 2.x）：

```
java -version
javac -version
git --version
```

安裝 Temurin 時勾選 **Add to PATH** 與 **Set JAVA_HOME**；沒勾到的處理方式見實作講義 Lab 1。

---

<!-- _class: by-claude -->

## 為什麼要學兩套 IDE？（你以前只用 Visual Studio）

| | VS Code + Extension Pack for Java | IntelliJ IDEA |
|---|---|---|
| 定位 | 通用編輯器 + Java 擴充 | 專為 Java 打造的 IDE，最接近 Visual Studio 的體驗 |
| 優點 | 輕量、AI 擴充豐富（Copilot、Claude Code、Codex） | 重構、除錯、程式碼提示最強 |
| 專案結構 | 資料夾 + `.vscode/`；可不用建置工具 | `.idea/` + `*.iml`；Build system 可選 IntelliJ / Maven / Gradle |
| 適合 | 小程式、AI 協作練習 | 中大型專案、期末專題 |

- 兩套都要會：建專案、寫程式、執行、看錯誤、用 Git
- 作業不限 IDE，但**專案結構要讓助教 clone 下來就能開**（實作講義有規定）

---

<!-- _class: by-claude -->

## VS Code：Extension Pack for Java 裝了什麼？

| 擴充 | 作用 | 對應 Visual Studio 功能 |
|---|---|---|
| Language Support for Java (Red Hat) | 語法檢查、自動完成、錯誤標示 | IntelliSense + 紅色波浪線 |
| Debugger for Java | 中斷點、單步執行、監看變數 | F5 / F10 / F11 |
| Test Runner for Java | 執行 JUnit 測試（第 14 週） | Test Explorer |
| Maven for Java | Maven 專案支援 | NuGet + 建置 |
| Project Manager for Java | 建立與管理專案、切換 JDK | 方案總管 |
| IntelliCode | AI 輔助程式碼建議 | IntelliCode |

建立專案：`Ctrl+Shift+P` → **Java: Create Java Project** → **No build tools** → 資料夾 → 專案名稱
執行：在 `main` 上方點 **Run**，或右上角 ▶；輸出在 TERMINAL 面板
切換 JDK：`Ctrl+Shift+P` → **Java: Configure Java Runtime**

---

<!-- _class: by-claude -->

## IntelliJ IDEA：建立並執行專案

1. **New Project** → 左側 **Java**；Name `week01-lab`；**JDK** 選 21（沒有就 *Download JDK* → Temurin 21）
2. Build system 選 **IntelliJ**；取消 *Add sample code* → **Create**
3. `src` 右鍵 → **New → Java Class** → `Welcome1`；貼上程式
4. `main` 旁綠色 ▶ 或 `Shift+F10`；輸出在下方 **Run** 視窗
5. 除錯：行號左側點一下設中斷點 → `Shift+F9`；`F8` 逐行、`F7` 進入方法（與 VS 的 F10 / F11 相同意義）

```
week01-lab/
├── .idea/            ← IntelliJ 設定，不進 Git
├── src/
│   └── Welcome1.java
└── out/              ← 編譯產物，不進 Git
```

---

<!-- _class: by-claude -->

## 第一支程式：Welcome1.java（Deitel Fig. 2.1）

```java
// Fig. 2.1: Welcome1.java
// Text-printing program.

public class Welcome1 {
   // main method begins execution of Java application
   public static void main(String[] args) {
      System.out.println("Welcome to Java Programming!");
   } // end method main
} // end class Welcome1
```

對照你熟悉的 C# 主控台程式：

```csharp
class Program {
    static void Main(string[] args) { Console.WriteLine("Welcome to C#!"); }
}
```

---

<!-- _class: by-claude -->

## 逐行對照：Java 與 C# 哪裡不一樣？（Deitel 2.2）

| Java | C# | 差異重點 |
|---|---|---|
| `public class Welcome1` | `class Program` | **public 類別名稱必須等於檔名** `Welcome1.java`（C# 沒這規定） |
| `public static void main(String[] args)` | `static void Main(string[] args)` | `main` 小寫、必須 `public`、`String` 大寫 S |
| `System.out.println(...)` | `Console.WriteLine(...)` | `System.out` 是標準輸出物件；另有 `print`（不換行）、`printf` |
| 沒有 `namespace` / `using` | `namespace` + `using System;` | Java 用 `package` / `import`；`java.lang` 自動匯入 |
| `// ...`、`/* */`、`/** */` | `//`、`/* */`、`///` | Javadoc 用 `/** */`，工具 `javadoc` 產生 HTML 文件 |
| 大括號慣例：`class X {` 同一行 | Allman：`{` 換行 | 都能編譯，但 Java 社群慣例是 K&R 風格 |

**Java 區分大小寫**：`System` ≠ `system`；`String` ≠ `string`（Java 沒有小寫 `string`）

---

<!-- _class: by-claude -->

## 命令列參數與程式結構：Welcome2.java

```java
public class Welcome2 {
   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("Usage: java Welcome2 <name>");
         return;
      }
      String name = args[0];
      System.out.printf("Hello, %s! You passed %d argument(s).%n", name, args.length);
   }
}
```

```
javac Welcome2.java
java Welcome2 Alice        →  Hello, Alice! You passed 1 argument(s).
java Welcome2              →  Usage: java Welcome2 <name>
```

- `args` 就是 C# 的 `args`；`printf` 的格式化與 C 相同（`%s` 字串、`%d` 整數、`%n` 換行）
- IDE 設定參數：VS Code 用 `launch.json` 的 `args`；IntelliJ 用 *Run → Edit Configurations → Program arguments*

---

<!-- _class: by-claude -->

## 自己定義一個類別：Student.java（第 5 週的預告）

```java
public class Student {
   private String name;          // 實例變數（C# 的欄位）
   private int score;

   public Student(String name, int score) {   // 建構子
      this.name = name;
      this.score = score;
   }
   public boolean isPassing() { return score >= 60; }   // 方法（camelCase）
   public String getName() { return name; }            // 沒有 property，用 getter

   public static void main(String[] args) {
      Student s = new Student("Alice", 72);              // 實例化
      System.out.println(s.getName() + " passing? " + s.isPassing());
   }
}
```

和 C# 幾乎一樣，差別只在：`extends`/`implements`、getter/setter、`boolean`、方法名小寫開頭。

---

<!-- _class: by-claude -->

## 命令列：IDE 背後在做的事

```
cd week01-lab\src
javac Welcome1.java            ← 編譯，產生 Welcome1.class
java Welcome1                  ← 執行（不加 .class）
java Welcome1.java             ← JDK 11+：單檔直接執行（小程式適用）
javap -c Welcome1              ← 反組譯，看看 bytecode 長什麼樣
```

`javap -c` 節錄（對照 .NET 的 `ildasm`）：

```
public static void main(java.lang.String[]);
  Code:
     0: getstatic     #7   // Field java/lang/System.out:Ljava/io/PrintStream;
     3: ldc           #13  // String Welcome to Java Programming!
     5: invokevirtual #15  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     8: return
```

- 常見錯誤 `'javac' is not recognized ...` ➜ PATH 沒指到 JDK 的 `bin`；修正後**重開終端機**

---

<!-- _class: by-claude -->

## 兩種錯誤：編譯錯誤 vs 執行期錯誤

| | 編譯錯誤 (Compile-time) | 執行期錯誤 (Runtime) |
|---|---|---|
| 何時 | `javac` 階段 | `java` 執行時 |
| 原因 | 少分號、大小寫錯、檔名 ≠ 類別名、型別不符 | 除以零、陣列越界、`null` 呼叫方法 |
| 現象 | 產生不了 `.class`，IDE 畫紅線 | 程式終止並印出 **Exception** 與堆疊追蹤 |
| C# 對照 | 一樣 | 一樣（`NullReferenceException` ↔ `NullPointerException`） |

**課堂示範**（照著在自己機器上做）
1. 把 `Welcome1.java` 存成 `welcome1.java` → 看編譯錯誤訊息
2. `java Welcome2` 不給參數、把 `if` 拿掉 → 看 `ArrayIndexOutOfBoundsException` 的堆疊追蹤

**讀懂錯誤訊息**是驗證 AI 程式碼的第一步；第 12 週正式學例外處理。

---

<!-- _class: lead by-claude -->

# D. Git 與 GitHub 版本控制

---

<!-- _class: by-claude -->

## 為什麼要版本控制？

- **存檔歷史**：每次 commit 是一個快照，隨時可以回到任一時間點（比 `專題_final_v3_真的最終版.zip` 可靠）
- **比較差異**：`git diff` 一眼看出改了哪幾行
- **本課程核心用途**：追蹤並比較**手寫程式碼**與 **AI 建議程式碼**的差異（課程目標 5）
- **團隊協作**：期末專題多人開發不會互相覆蓋
- **繳交作業**：以 GitHub 儲存庫連結繳交，助教看你的 commit 歷史

| 角色 | Git | GitHub |
|---|---|---|
| 是什麼 | 你電腦上的版本控制**工具** | 放 Git 儲存庫的**網站／服務** |
| 沒有網路 | 照常運作 | 需要網路 |
| 類似的東西 | （Mercurial、SVN） | GitLab、Bitbucket、Gitea |

---

<!-- _class: by-claude -->

## Git 的三個區域 + 遠端

```
 工作目錄              暫存區                本地儲存庫             遠端儲存庫
 Working Dir   →    Staging Area   →     Repository     ⇄      GitHub
 (你正在編輯     git add          git commit         git push / git pull
  的檔案)      (選擇要記錄的檔案)  (存成一個快照)       (上傳 / 下載)
```

| 指令 | 做什麼 |
|---|---|
| `git status` | 看哪些檔案被修改、哪些已暫存 |
| `git add <檔案>` / `git add .` | 把修改放進暫存區 |
| `git commit -m "訊息"` | 把暫存區存成快照 |
| `git log --oneline --graph` | 看歷史 |
| `git diff` / `git diff --staged` | 看未暫存 / 已暫存的修改 |
| `git show HEAD~1` | 看上一次 commit 改了什麼 |

---

<!-- _class: by-claude -->

## 第一次使用 Git：設定與初始化

只做一次的全域設定（會寫進每個 commit）：

```
git config --global user.name "你的 GitHub 帳號名"
git config --global user.email "你在 GitHub 註冊的信箱"
git config --global init.defaultBranch main
```

在專案資料夾：

```
cd week01-lab
git init
git status
```

`git init` 會在資料夾內建立隱藏的 `.git/` 目錄，整個歷史都存在裡面；刪掉它就等於「取消版本控制」。

---

<!-- _class: by-claude -->

## .gitignore：哪些東西不要進儲存庫

放在 repo 根目錄，檔名就是 `.gitignore`；**做第一次 commit 之前就要有**：

```
*.class
/bin/
/out/
/target/
.idea/
*.iml
.vscode/
```

> 永遠不要 commit：密碼、API 金鑰、個資、學號名單、下載的教科書檔案。推上 GitHub 後就算刪掉，歷史裡還在。

---

<!-- _class: by-claude -->

## 本課程的 commit message 規範

格式：`[來源] 做了什麼`，一次 commit 只做一件事

| 前綴 | 使用時機 | 範例 |
|---|---|---|
| `[manual]` | 自己手寫的修改 | `[manual] Welcome2 讀取命令列參數` |
| `[AI]` | 採用 AI 生成或建議的修改（已驗證） | `[AI] Welcome1 改為印出三行自我介紹` |
| `[fix]` | 修正錯誤 | `[fix] 修正檔名大小寫導致的編譯錯誤` |
| `[doc]` | 文件、AI 使用紀錄表 | `[doc] 新增第 1 週 AI 使用紀錄表` |

```
git add .
git commit -m "[manual] 第一支程式 Welcome1"
git log --oneline
```

- 有 `[AI]` 前綴的 commit，AI 使用紀錄表裡**必須**有對應的一列
- 助教用 `git log` 與 GitHub 的 compare 功能，就能看出「手寫 → AI 修改」的差異

---

<!-- _class: by-claude -->

## GitHub：建立帳號與儲存庫

1. 用**學校信箱**註冊 github.com（可申請 GitHub Student Developer Pack，含 Copilot Pro）
2. 帳號名建議用英文姓名或學號的組合，這學期都會用到
3. 右上角 **+ → New repository**

| 欄位 | 建議 |
|---|---|
| Repository name | `oop-115-1`（整學期作業放同一個 repo，用資料夾 `week01/`、`week02/` 分週） |
| Visibility | **Private**；再到 *Settings → Collaborators* 加入助教帳號 |
| Add a README | **不要勾**（本地已經有內容，勾了會衝突） |
| Add .gitignore | 不要勾（我們自己寫） |

4. 建好後 GitHub 會顯示「…or push an existing repository from the command line」的指令，就是下一頁

---

<!-- _class: by-claude -->

## GitHub：把本地儲存庫推上去（HTTPS + Credential Manager）

```
git remote add origin https://github.com/<你的帳號>/oop-115-1.git
git branch -M main
git push -u origin main
```

- 第一次 `push` 會跳出 **Git Credential Manager** 的瀏覽器登入視窗，登入 GitHub 授權即可（Git for Windows 內建）
- 之後只需要 `git push`；`-u` 已經記住 `origin main`
- 用 SSH 金鑰也可以（進階，講義附錄）；**不要**把密碼寫在 URL 裡

**驗證**：重新整理 GitHub 頁面，應該看到 `src/Welcome1.java`、`.gitignore`，且 **Commits** 顯示你的 commit

**常見錯誤**
- `remote origin already exists` → `git remote set-url origin <新網址>`
- `Updates were rejected` → 遠端有你本地沒有的 commit（例如勾了 README），先 `git pull --rebase origin main`

---

<!-- _class: by-claude -->

## GitHub：日常工作流程與從另一台電腦取回

```
# 每天開工前（教室電腦 ↔ 自己的筆電）
git pull                      ← 把 GitHub 上的新 commit 拉回來

# 改程式 → 檢查 → 提交 → 上傳
git status
git diff
git add .
git commit -m "[manual] ..."
git push
```

第一次在另一台電腦：

```
git clone https://github.com/<你的帳號>/oop-115-1.git
cd oop-115-1
```

- **教室電腦不會保留你的檔案**：下課前一定要 `git push`，回家 `git clone` 或 `git pull`
- `git clone` 下來的資料夾就是完整儲存庫，含所有歷史

---

<!-- _class: by-claude -->

## GitHub：在網頁上看歷史與差異

助教批改時就是看這些畫面，你自己也要會用：

| 位置 | 看到什麼 |
|---|---|
| **Code** 頁的時鐘圖示 / **Commits** | 所有 commit，點進去看每個檔案的差異（綠色新增、紅色刪除） |
| 網址 `.../compare/<commit1>...<commit2>` | 比較任兩個 commit，**用來對照手寫版與 AI 版** |
| 檔案頁 → **History** | 單一檔案的修改歷史 |
| 檔案頁 → **Blame** | 每一行是哪個 commit 改的 |
| **Issues** | 記錄待辦、bug；期末專題會用來分工 |
| **README.md** | 儲存庫首頁說明，作業請寫：週次、如何執行、AI 使用紀錄表位置 |

**分支 (branch)** 與 **Pull Request**：期末專題多人協作時（第 15 週）再教；本週只用 `main`。

---

<!-- _class: by-claude -->

## 在 IDE 裡做同樣的事

| 動作 | VS Code | IntelliJ IDEA |
|---|---|---|
| 開啟版本控制面板 | 左側 Source Control（`Ctrl+Shift+G`） | 底部 **Git** 工具視窗、左側 **Commit** 面板 |
| 初始化 | *Initialize Repository* | **VCS → Enable Version Control Integration → Git** |
| 暫存 + 提交 | 在檔案旁點 **+**，輸入訊息，✓ **Commit** | `Ctrl+K`：勾選檔案、輸入訊息、**Commit** |
| 推送 | **Sync Changes** 或 *… → Push* | `Ctrl+Shift+K` **Push** |
| 發佈到 GitHub | *Publish Branch*（會幫你建 repo） | **Git → GitHub → Share Project on GitHub** |
| 看差異 | 點修改過的檔案，左右對照 | 檔案右鍵 → **Git → Show Diff** |
| 看歷史 | 安裝 *Git Graph* 擴充 | 底部 **Git → Log** |

- IDE 按鈕做的事和命令列**完全一樣**，看不懂 IDE 在做什麼時，回到命令列 `git status` 就知道
- 作業繳交只認 GitHub 上的內容，用哪一種方式推上去都可以

---

<!-- _class: lead by-claude -->

# E. AI 協作規範與 AI 使用紀錄表

---

<!-- _class: by-claude -->

## 本課程的 AI 協作規範

**可以做的事**
- 用 AI 協助**理解概念、產生範例、找錯、設計測試案例、重構、撰寫文件**
- 使用任何 AI 工具（ChatGPT / Codex、Claude、Gemini、GitHub Copilot、IntelliJ AI Assistant……）

**不可以做的事**
- 提交**自己無法解釋**的 AI 生成程式碼（抽問時要能逐行說明）
- 使用了 AI 卻**不揭露**（作業／專題必須附 AI 使用紀錄表與人工驗證結果）
- 上傳**個資、帳號密碼、未經授權的資料、具敏感性的程式碼**到外部 AI 服務

**Git 是揭露的證據**：`[AI]` commit + 紀錄表 + GitHub compare，三者對得起來就是合規。

---

<!-- _class: by-claude -->

## AI 使用紀錄表（每次作業／專題必附）

| 欄位 | 要寫什麼 | 範例 |
|---|---|---|
| 使用目的 | 想請 AI 幫什麼忙 | 把 Welcome1 改成印出三行自我介紹 |
| 主要 Prompt 摘要 | 你怎麼問的（重點即可） | 「請用 Java 21，不用額外套件，把下面程式改成用三個 println 印出化名、系級、學習目標」 |
| AI 回覆採納／未採納原因 | 用了哪些、沒用哪些、為什麼 | 採納三行 println；未採納它加的 `String.format`，因為與需求無關 |
| 人工驗證方式 | 你怎麼確認它是對的 | 編譯通過、執行輸出三行、逐行能解釋 |
| 最終修改內容 | 你自己又改了什麼 | 把第三行改成自己的學習目標、調整縮排 |
| 對應 commit | 哪個 `[AI]` commit | `[AI] Welcome1 改為印出三行自我介紹` |

範本：`AI使用紀錄表_範本.md`，複製到你的 repo 內 `week01/AI使用紀錄表.md`

---

<!-- _class: by-claude -->

## Prompt 設計基礎：把需求講清楚

| 部分 | 內容 | 本週範例 |
|---|---|---|
| **背景** | 你是誰、在做什麼、已經會什麼 | 我學過 C#，正在學 Java 第 1 週 |
| **需求** | 具體要做什麼、輸入輸出是什麼 | 把下面的程式改成印出三行：化名、系級、學習目標 |
| **限制** | 版本、不能用什麼、風格 | Java 21、只用 `System.out.println`、不加其他類別、不用套件 |
| **輸出格式** | 要完整程式還是只要改動 | 完整的 `Welcome1.java`，並逐行說明改了什麼 |

**進階技巧（有 C# 基礎的你可以這樣問）**
- 「這段 Java 對應的 C# 寫法是什麼？差異在哪？」➜ 用已知對照未知
- 「請列出這段程式碼可能的三個錯誤情境」➜ 讓 AI 幫你想測試案例，但驗證要自己做
- 貼錯誤訊息全文 + 相關程式碼 ➜ 除錯效率最高

---

<!-- _class: by-claude -->

## 驗證流程：AI 給的程式碼怎麼處理？

```
 AI 回覆 → 1.讀懂每一行 → 2.編譯 → 3.執行 → 4.測試邊界 → 5.能對別人解釋 → 採用 + commit [AI]
               ↓ 看不懂        ↓ 錯誤      ↓ 結果不對
           追問 AI 或查書    讀錯誤訊息   修改或不採納 → 記錄原因
```

- **不能跳過任何一步**：能跑不代表對，對了不代表你懂
- 「測試邊界」：沒有參數、空字串、負數、超大數字……AI 最常在這裡出錯
- 每一步的結果都寫進 AI 使用紀錄表的「人工驗證方式」
- 第 3 週開始，我們會刻意讓 AI 產生有錯的程式碼，練習找出來

---

<!-- _class: by-claude -->

## 資安與學術誠信

**不要貼給 AI 的東西**
- 學號 + 姓名 + 身分證字號、電話、住址等個資（Lab 用**化名與虛構資料**）
- 任何帳號密碼、API 金鑰、Token（也不要 commit 進 Git）
- 未經授權的資料（公司資料、他人程式碼、付費教材全文）

**學術誠信**
- 揭露 = 誠實，不會扣分；**不揭露**被發現才會
- 同學之間可以討論，但 AI 使用紀錄表與程式碼要各自完成；GitHub 歷史會說話
- 期末專題的 AI 協作歷程本身就是評分項目（15%）

**教師示範**：用 AI 把 Welcome1 改成印三行，照上一頁流程走一遍，做出 `[AI]` commit，並在 GitHub compare 看差異。

---

<!-- _class: lead by-claude -->

# F. 課堂實作與隨堂小測

請打開 `week01_實作講義.md`

---

<!-- _class: by-claude -->

## 課堂實作（依實作講義進行）

| Lab | 內容 | 檢核點 |
|---|---|---|
| 1 | 環境檢查：`java`、`javac`、`git` 版本；PATH 排錯 | 三個指令都顯示正確版本 |
| 2 | `Welcome1` 命令列 → VS Code → IntelliJ；`javap -c` 看 bytecode | 三種方式輸出相同 |
| 3 | `Welcome2` 命令列參數 + `Student` 類別（手寫） | `java Welcome2 你的化名` 正確；`Student` 印出 passing 結果 |
| 4 | Git：`.gitignore` → 兩次 `[manual]` commit | `git log --oneline` 有兩筆 |
| 5 | GitHub：建 repo → push → 加助教為 collaborator → 看 Commits | 網頁上看得到程式與歷史 |
| 6 | AI 協作：請 AI 改 `Welcome1` 印三行 → 驗證 → `[AI]` commit → 填紀錄表 → push → compare | compare 頁面看到差異 |

課堂做不完的部分回家完成；**下課前務必 push**。

---

<!-- _class: by-claude -->

## 隨堂小測（單選，5 題）

1. `javac Welcome1.java` 成功後主要產生什麼？
   A. 原生執行檔　B. 含 bytecode 的 `.class`，由 JVM 執行　C. IL 檔　D. Git commit
2. 下列哪一項在 Java 裡是**錯**的？
   A. `public class Welcome1` 存在 `Welcome1.java`　B. `static void Main(String[] args)`　C. `String name = args[0];`　D. `System.out.println("Hi");`
3. WinForms 的 `public partial class Form1 : Form`，對應 Java 的哪個關鍵字？
   A. `implements`　B. `extends`　C. `import`　D. `package`
4. 關於 `git add` 與 `git commit`，何者正確？
   A. `add` 上傳 GitHub　B. `commit` 會自動 push　C. `add` 放進暫存區，`commit` 存成本地快照　D. 兩者相同
5. AI 產生程式後，哪種做法符合課程規範？
   A. 能跑就交　B. 驗證、能解釋、`[AI]` commit 並填紀錄表　C. 把同學資料貼給 AI 測試　D. 刪掉 AI 痕跡再交

---

<!-- _class: by-claude -->

## 隨堂小測：解答

1. **B**：`javac` 產生平台無關的 bytecode，由該平台的 JVM 載入、驗證、執行；C 是 .NET 的用語。
2. **B**：Java 的進入點必須是 `public static void main(String[] args)`，`Main` 大寫是 C# 的寫法。
3. **B**：繼承用 `extends`；`implements` 是實作介面。
4. **C**：`add` 選擇要記錄的修改放進暫存區，`commit` 存成快照；都不會自動上傳。
5. **B**：規範要求可解釋、有驗證、有揭露；A、C、D 分別違反「能解釋」、「不上傳個資」、「揭露」。

---

<!-- _class: by-claude -->

## 本週作業（下週上課前繳交）

**繳交方式**：在課程平台貼上 GitHub 儲存庫連結（`https://github.com/<帳號>/oop-115-1`）

儲存庫內必須有：

```
oop-115-1/
├── .gitignore
├── README.md                    ← 姓名化名、如何執行、各週資料夾說明
└── week01/
    ├── src/Welcome1.java        ← 已改為印三行自我介紹（[AI] commit）
    ├── src/Welcome2.java        ← 命令列參數版本
    ├── src/Student.java         ← 自訂類別
    └── AI使用紀錄表.md           ← 依範本填寫，至少一列對應 [AI] commit
```

- commit 歷史至少 3 筆：`[manual]` 兩筆以上、`[AI]` 一筆以上；助教已被加為 collaborator
- **加分題**：讓 `Student` 支援多位學生（陣列或 `ArrayList`），印出及格人數；用 `[manual]` 或 `[AI]` 誠實標註
- 下週上課抽查：執行你的程式並逐行說明 `[AI]` commit 改了什麼

---

<!-- _class: by-claude -->

## 本週重點回顧

1. Java 與 .NET 是同構的：`javac`/bytecode/JVM ↔ `csc`/IL/CLR；`javap -c` 可以看到 bytecode
2. 你在 WinForms 已經用過類別、物件、屬性、方法、繼承、介面；Java 只是換了關鍵字（`extends`、`implements`）
3. Java 的硬規則：public 類別名 = 檔名、`main` 小寫、`String` 大寫、區分大小寫、方法 camelCase
4. Git 三區域：`add` → `commit` → `push`；commit 前綴 `[manual]` / `[AI]` / `[fix]` / `[doc]`
5. GitHub：private repo + 助教 collaborator；Commits 與 compare 頁面是繳交與批改的依據
6. AI 協作：可以用、要揭露、要驗證、不能貼個資；`[AI]` commit 與紀錄表要對得起來

---

<!-- _class: by-claude -->

## 下週預告與參考資料

**第 2 週**：基本型態、參考型態與運算子：C# 到 Java 語法對照與 AI 協作學習
- 預習：Deitel 2.3–2.8（`printf`、`Scanner` 輸入、算術、關係運算子）
- 帶著問題來：C# 的 `int`/`string`/`var`/`decimal` 在 Java 裡是什麼？Java 為什麼有 `int` 又有 `Integer`？

**參考資料**
- Deitel & Deitel, *Java How to Program, Early Objects*, 11th ed.：第 1 章、2.2 節
- Java 官方教學：dev.java/learn；API 文件：docs.oracle.com/en/java/javase/21/docs/api
- VS Code Java：code.visualstudio.com/docs/java/java-tutorial
- IntelliJ IDEA：jetbrains.com/help/idea/creating-and-running-your-first-java-application.html
- Pro Git（繁體中文）：git-scm.com/book/zh-tw；GitHub Docs：docs.github.com/zh
