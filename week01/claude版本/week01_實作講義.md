# 第 1 週 學生實作講義：Java 環境、第一支程式、Git/GitHub 與 AI 協作初體驗

> 物件導向程式設計（與 AI 協作）115-1｜資管系二年級｜生成來源：claude（教師審閱）
> 適用對象：已學過 C# 與 WinForms 的同學。本講義假設你會用 Visual Studio 寫過主控台或視窗程式，但**沒有**用過 Java 與 Git。

## 使用方式

- 依 Lab 0 → Lab 6 的順序做，每個 Lab 結尾有「✅ 檢核點」，全部打勾才算完成。
- 遇到問題先看該 Lab 的「疑難排解」，再問同學、助教或 AI（問 AI 時要記錄到 AI 使用紀錄表）。
- 課堂做不完的部分回家完成；**下課前務必 `git push`**，教室電腦不會保留你的檔案。
- 整份講義的指令都以 Windows PowerShell 為準；Git Bash 也可以用。

---

## Lab 0：安裝工具（建議課前完成）

| 工具 | 下載 | 安裝注意事項 |
|---|---|---|
| JDK 21 LTS（Eclipse Temurin） | https://adoptium.net | 選 **Temurin 21 (LTS)**、Windows x64、`.msi`。安裝時展開選項，勾選 **Add to PATH** 與 **Set JAVA_HOME variable** |
| VS Code | https://code.visualstudio.com | 安裝後在 Extensions（`Ctrl+Shift+X`）搜尋 **Extension Pack for Java** 安裝 |
| IntelliJ IDEA | https://www.jetbrains.com/idea | 安裝時選免費授權即可；用學校信箱可申請學生授權 |
| Git for Windows | https://git-scm.com | 一路 Next 即可；預設會安裝 Git Bash 與 Git Credential Manager |
| GitHub 帳號 | https://github.com | 用**學校信箱**註冊；之後可申請 GitHub Student Developer Pack |

---

## Lab 1：環境檢查

開啟 PowerShell（開始功能表輸入 `powershell`），依序輸入：

```powershell
java -version
javac -version
git --version
```

預期輸出（版本號可能略有不同）：

```
openjdk version "21.0.x" 2025-xx-xx LTS
OpenJDK Runtime Environment Temurin-21.0.x ...
javac 21.0.x
git version 2.5x.x.windows.1
```

### 疑難排解

| 現象 | 原因 | 處理 |
|---|---|---|
| `'javac' is not recognized as an internal or external command` | PATH 沒指到 JDK 的 `bin` | 開始功能表搜尋「編輯系統環境變數」→「環境變數」→ 使用者變數 `Path` → 新增 `C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot\bin`（依實際路徑）。**保留原有項目**。存檔後**重新開啟** PowerShell |
| `java` 有版本但 `javac` 沒有 | 只裝了 JRE，或 PATH 指到別的 Java | 重新安裝 Temurin JDK；用 `where.exe java` 檢查有幾個 java，順序前面的優先 |
| `java -version` 顯示 8 或 17 | 電腦上有舊版 Java 排在前面 | 把 JDK 21 的 `bin` 移到 Path 清單最上面，或移除舊版 |
| `git` 找不到 | Git 沒裝或 PATH 沒設 | 重新安裝 Git for Windows，安裝時選「Git from the command line and also from 3rd-party software」 |

`JAVA_HOME` 是給 IDE 與建置工具看的，應指向 JDK 根目錄（不含 `bin`）；它**不能取代** PATH。

✅ 檢核點
- [ ] `java -version` 與 `javac -version` 都顯示 21.x
- [ ] `git --version` 顯示 2.x

---

## Lab 2：第一支程式 Welcome1，三種方式執行

### 2-1 建立資料夾並手寫程式

```powershell
mkdir $HOME\oop-115-1\week01\src
cd $HOME\oop-115-1\week01\src
notepad Welcome1.java
```

**手動輸入**（不要複製貼上，這是為了熟悉 Java 語法）：

```java
// Welcome1.java  (adapted from Deitel Fig. 2.1)
public class Welcome1 {
   public static void main(String[] args) {
      System.out.println("Welcome to Java Programming!");
   }
}
```

儲存時確認副檔名是 `.java`，不是 `.java.txt`（記事本另存新檔時「存檔類型」選「所有檔案」）。

### 2-2 命令列編譯與執行

```powershell
javac Welcome1.java
dir                      # 應該多了 Welcome1.class
java Welcome1            # 注意：不加 .class
```

預期輸出：

```
Welcome to Java Programming!
```

再試一次 JDK 11 以後的單檔執行（不產生 `.class`）：

```powershell
java Welcome1.java
```

### 2-3 看看 bytecode（對照 .NET 的 IL）

```powershell
javap -c Welcome1
```

找出 `main` 底下的 `getstatic`、`ldc`、`invokevirtual`、`return` 四個指令，想一想它們分別對應原始碼的哪一部分。這就是 JVM 真正執行的東西。

### 2-4 用 VS Code 開啟並執行

1. 開啟 VS Code → **File → Open Folder** → 選 `oop-115-1`
2. 第一次開啟 `.java` 會提示安裝 Java 擴充或設定 JDK，照提示做；若已裝好 Extension Pack for Java 會自動偵測 JDK 21
3. 開啟 `week01/src/Welcome1.java`，等右下角的 Java 狀態轉為 ready
4. 在 `main` 上方會出現 **Run | Debug** 小字，點 **Run**；輸出在下方 TERMINAL
5. 練習除錯：在 `System.out.println` 那一行左邊點一下設中斷點，改點 **Debug**，程式會停在該行，用上方工具列 **Step Over (F10)** 往下走

> VS Code 沒有建置工具時會把 `.class` 放到自己的暫存資料夾，不會弄髒 `src`。

### 2-5 用 IntelliJ IDEA 開啟並執行

1. 開啟 IntelliJ → **New Project**
2. 左側 **Java**；Name `week01-idea`；Location 任意（例如 `$HOME\idea-projects`）；**JDK** 選 21（沒有就 *Download JDK* → Temurin 21）；Build system **IntelliJ**；取消 *Add sample code* → **Create**
3. 在 `src` 按右鍵 → **New → Java Class** → 名稱 `Welcome1` → 貼上同一支程式
4. 點 `main` 旁邊的綠色 ▶ → **Run 'Welcome1.main()'**；輸出在下方 Run 視窗
5. 練習除錯：行號左側點一下設中斷點 → ▶ 旁的蟲圖示（或 `Shift+F9`）→ `F8` 逐行

> 這個 IntelliJ 專案只是練習用，**不進 Git**。作業程式都放在 `oop-115-1` 這個資料夾。

✅ 檢核點
- [ ] 命令列 `javac` + `java` 輸出正確
- [ ] `javap -c` 看得到 `invokevirtual ... println`
- [ ] VS Code 與 IntelliJ 都能執行，輸出與命令列相同
- [ ] 在其中一種 IDE 成功停在中斷點

---

## Lab 3：命令列參數與自訂類別（手寫）

### 3-1 Welcome2.java：讀取命令列參數

在 `oop-115-1\week01\src` 新增 `Welcome2.java`：

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

```powershell
javac Welcome2.java
java Welcome2 Alice
java Welcome2
java Welcome2 Alice Bob
```

**思考題**（寫在 README 或口頭回答）：
1. 把 `if` 區塊整段刪掉再 `java Welcome2`，會出現什麼例外？它是編譯錯誤還是執行期錯誤？
2. C# 的 `Console.WriteLine($"Hello, {name}!")` 在 Java 有沒有對應的字串插值語法？（提示：沒有，Java 用 `printf`、`+` 串接或 `String.format`）

在 IDE 裡傳參數：VS Code 點 **Debug** 後會產生 `.vscode/launch.json`，在 `"args": []` 填入 `["Alice"]`；IntelliJ 用 **Run → Edit Configurations → Program arguments**。

### 3-2 Student.java：自己定義一個類別

```java
public class Student {
   private String name;
   private int score;

   public Student(String name, int score) {
      this.name = name;
      this.score = score;
   }

   public String getName() { return name; }
   public boolean isPassing() { return score >= 60; }

   public static void main(String[] args) {
      Student a = new Student("Alice", 72);
      Student b = new Student("Bob", 55);
      System.out.println(a.getName() + " passing? " + a.isPassing());
      System.out.println(b.getName() + " passing? " + b.isPassing());
   }
}
```

```powershell
javac Student.java
java Student
```

**對照練習**：把這個類別用 C# 寫一遍（不必執行），標出三個語法差異。你應該會找到：`boolean` vs `bool`、方法名大小寫、沒有 property 語法。

✅ 檢核點
- [ ] `java Welcome2 <你的化名>` 印出正確訊息；不給參數時印出 Usage
- [ ] `java Student` 印出兩行，一個 true 一個 false
- [ ] 能說出 Welcome2 沒有參數時的例外名稱（`ArrayIndexOutOfBoundsException`）

---

## Lab 4：Git 本地儲存庫與兩次 commit

### 4-1 一次性設定

```powershell
git config --global user.name "你的GitHub帳號名"
git config --global user.email "你在GitHub註冊的信箱"
git config --global init.defaultBranch main
git config --global core.autocrlf true
git config --list --show-origin | Select-String user
```

### 4-2 初始化並建立 .gitignore

```powershell
cd $HOME\oop-115-1
git init
notepad .gitignore
```

`.gitignore` 內容：

```
# 編譯產物
*.class
/bin/
/out/
/target/

# IDE 私人設定
.idea/
*.iml
.vscode/
```

存檔時檔名要是 `.gitignore`（前面有點，沒有副檔名）。用 `dir -Force` 確認。

### 4-3 第一次 commit（手寫版本）

```powershell
git status                 # 應看到 .gitignore 與 week01/src/*.java 是 Untracked，沒有 .class
git add .
git status                 # 變成 Changes to be committed
git commit -m "[manual] 第 1 週：Welcome1、Welcome2、Student 手寫版本"
git log --oneline
```

### 4-4 新增 README 並做第二次 commit

在 `oop-115-1` 建立 `README.md`：

```markdown
# oop-115-1：物件導向程式設計（與 AI 協作）作業儲存庫

- 化名：（自訂英文化名，不要寫真名與學號）
- 執行方式：`cd weekNN/src && javac *.java && java <ClassName>`
- 各週資料夾：`week01/` 環境、第一支程式、Git/GitHub、AI 協作初體驗
- AI 使用紀錄表：每週資料夾內的 `AI使用紀錄表.md`
```

```powershell
git add README.md
git commit -m "[doc] 新增 README"
git log --oneline --graph
git show HEAD~1 --stat      # 看上一次 commit 動了哪些檔案
```

### 疑難排解

| 現象 | 處理 |
|---|---|
| `git status` 看到 `.class` | `.gitignore` 沒生效：確認檔名、位置在 repo 根目錄；若已 add，`git rm --cached <檔案>` |
| commit 時跳出編輯器（vim） | 你忘了 `-m`。按 `Esc`，輸入 `:q!` 離開，重新下 `git commit -m "..."` |
| `Author identity unknown` | 回到 4-1 設定 user.name / user.email |
| 中文訊息變亂碼 | PowerShell 執行 `chcp 65001` 或改用 Git Bash |

✅ 檢核點
- [ ] `git log --oneline` 有兩筆：`[manual]` 與 `[doc]`
- [ ] `git status` 顯示 `nothing to commit, working tree clean`
- [ ] 儲存庫裡沒有任何 `.class`

---

## Lab 5：推上 GitHub 並邀請助教

### 5-1 在 GitHub 建立儲存庫

1. 登入 github.com → 右上角 **+** → **New repository**
2. Repository name：`oop-115-1`
3. Visibility：**Private**
4. **不要**勾 *Add a README file*、*Add .gitignore*、*Choose a license*（本地都有了）
5. **Create repository**

### 5-2 推送

GitHub 頁面會顯示「…or push an existing repository from the command line」，內容就是：

```powershell
cd $HOME\oop-115-1
git remote add origin https://github.com/<你的帳號>/oop-115-1.git
git branch -M main
git push -u origin main
```

第一次 push 會跳出 **Git Credential Manager** 視窗 → **Sign in with your browser** → 在瀏覽器登入 GitHub 並 Authorize。之後不會再問。

### 5-3 邀請助教並檢查

1. 儲存庫頁面 → **Settings → Collaborators → Add people** → 輸入助教的 GitHub 帳號（課堂公布）→ Add
2. 回到 **Code** 頁：應看到 `README.md`、`.gitignore`、`week01/src/`，右上方顯示 **2 commits**
3. 點 **2 commits** → 點其中一個 commit → 看到綠色（新增）/ 紅色（刪除）的差異畫面
4. 把 README 的內容顯示在儲存庫首頁確認無誤

### 5-4 模擬「回家繼續做」

在另一個資料夾 clone 一份，確認歷史完整：

```powershell
cd $HOME
git clone https://github.com/<你的帳號>/oop-115-1.git oop-115-1-clone
cd oop-115-1-clone
git log --oneline
```

看完後可以刪掉 `oop-115-1-clone`。回家後真正要做的是：在家裡電腦 `git clone` 一次，之後每次開工 `git pull`、收工 `git push`。

### 疑難排解

| 現象 | 處理 |
|---|---|
| `remote origin already exists` | `git remote set-url origin https://github.com/<帳號>/oop-115-1.git` |
| `! [rejected] ... fetch first` / `Updates were rejected` | 你在 GitHub 勾了 README。執行 `git pull --rebase origin main` 後再 `git push` |
| `Permission denied` 或 403 | Credential Manager 記到別的帳號：控制台 →「認證管理員」→ Windows 認證 → 刪除 `git:https://github.com` 後重新 push |
| 想用 SSH | `ssh-keygen -t ed25519 -C "信箱"` → 把 `~/.ssh/id_ed25519.pub` 內容貼到 GitHub *Settings → SSH and GPG keys*；remote 改用 `git@github.com:<帳號>/oop-115-1.git` |

✅ 檢核點
- [ ] GitHub 上看得到程式碼與兩筆 commit
- [ ] 助教已是 collaborator
- [ ] `git clone` 下來的副本 `git log` 與原本一致

---

## Lab 6：AI 協作初體驗：讓 AI 改程式，你負責驗證

### 6-1 提出需求

打開任一 AI 工具（ChatGPT、Claude、Gemini、Copilot Chat……），把你手寫的 `Welcome1.java` 貼上，加上以下 Prompt（可以改寫，但四個部分都要有）：

> **背景**：我學過 C#，正在學 Java 第 1 週。
> **需求**：請把下面的 Welcome1.java 改成用三個 `System.out.println` 印出三行：英文化名、科系、本學期學習目標。請用虛構資料。
> **限制**：Java 21、只用 `System.out.println`、保留原本的類別名稱與 `main` 結構、不新增其他類別或套件。
> **輸出格式**：給我完整的 `Welcome1.java`，並逐行說明你改了哪裡。

**不要**貼真實姓名、學號、聯絡方式。

### 6-2 驗證（五步驟，缺一不可）

| 步驟 | 動作 | 記錄到紀錄表的哪一欄 |
|---|---|---|
| 1 讀懂 | AI 回覆的每一行你都能說出作用；看不懂就追問或查 Deitel 2.2 | 人工驗證方式 |
| 2 編譯 | 把 AI 的版本存回 `week01/src/Welcome1.java`，`javac Welcome1.java` | 人工驗證方式 |
| 3 執行 | `java Welcome1`，恰好三行、內容正確 | 人工驗證方式 |
| 4 測試邊界 | 這支程式沒有輸入，但檢查：有沒有多印空行？有沒有多餘的 import？ | AI 回覆採納／未採納原因 |
| 5 能解釋 | 對旁邊同學說明三個 println 與原本一行的差別 | 人工驗證方式 |

如果 AI 加了你沒要求的東西（例如 `String.format`、`var`、額外的類別），**不要採納**，並在紀錄表寫下原因。

### 6-3 比較差異並 commit

```powershell
cd $HOME\oop-115-1
git diff                                   # 先看 AI 改了哪幾行
git add week01/src/Welcome1.java
git commit -m "[AI] Welcome1 改為印出三行自我介紹"
```

### 6-4 填寫 AI 使用紀錄表

把 `AI使用紀錄表_範本.md` 複製為 `week01/AI使用紀錄表.md`，填寫至少一列（六個欄位都要有內容，「對應 commit」填上面那個 `[AI]` 訊息）。然後：

```powershell
git add week01/AI使用紀錄表.md
git commit -m "[doc] 新增第 1 週 AI 使用紀錄表"
git push
```

### 6-5 在 GitHub 上比較手寫版與 AI 版

1. 儲存庫頁面 → **Commits**，複製 `[manual]` 那筆與 `[AI]` 那筆的 7 碼 SHA
2. 開網址 `https://github.com/<帳號>/oop-115-1/compare/<manualSHA>...<AISHA>`
3. 看到 `Welcome1.java` 的差異：紅色是你原本那一行，綠色是 AI 的三行

這個 compare 頁面就是助教批改 `[AI]` commit 時看的東西。

✅ 檢核點
- [ ] `git log --oneline` 至少 4 筆，其中一筆 `[AI]`、一筆 `[doc]` 紀錄表
- [ ] `week01/AI使用紀錄表.md` 的「對應 commit」與 `[AI]` commit 訊息一致
- [ ] GitHub compare 頁面看得到差異
- [ ] 你能不看螢幕說出 AI 改了哪三行

---

## 繳交清單（下週上課前）

| 項目 | 要求 |
|---|---|
| GitHub 連結 | `https://github.com/<帳號>/oop-115-1`，Private，助教為 collaborator |
| 檔案 | `README.md`、`.gitignore`、`week01/src/Welcome1.java`（AI 版）、`Welcome2.java`、`Student.java`、`week01/AI使用紀錄表.md` |
| commit 歷史 | 至少 4 筆；`[manual]` ≥ 2、`[AI]` ≥ 1、`[doc]` ≥ 1；沒有 `.class` |
| 加分題 | 讓 `Student` 支援多位學生（陣列或 `ArrayList`）並印出及格人數；用 `[manual]` 或 `[AI]` 誠實標註，AI 協助的要有紀錄 |
| 抽查 | 下週上課隨機抽人：執行程式，並逐行說明 `[AI]` commit 改了什麼 |

---

## 附錄 A：C# → Java 速查（第 1 週範圍）

| C# | Java | 備註 |
|---|---|---|
| `static void Main(string[] args)` | `public static void main(String[] args)` | 小寫 `main`、大寫 `String` |
| `Console.WriteLine(x)` | `System.out.println(x)` | `print` 不換行、`printf` 格式化 |
| `Console.WriteLine($"Hi {name}")` | `System.out.printf("Hi %s%n", name)` | Java 沒有 `$""` 插值 |
| `string` / `bool` | `String` / `boolean` | |
| `namespace X;` / `using X;` | `package x;` / `import x.*;` | 套件名小寫 |
| `class Dog : Animal` | `class Dog extends Animal` | |
| `class Dog : IRunnable` | `class Dog implements Runnable` | |
| `public string Name { get; set; }` | `getName()` / `setName()` | Java 沒有 property |
| `void Speak()`（PascalCase） | `void speak()`（camelCase） | |
| `.sln` / `.csproj` | 資料夾（本週）；Maven/Gradle（之後） | |
| `dotnet build` / `dotnet run` | `javac` / `java` | |
| IL / CLR / `ildasm` | bytecode / JVM / `javap -c` | |

## 附錄 B：Git 指令速查

| 目的 | 指令 |
|---|---|
| 看狀態 / 看差異 | `git status`、`git diff`、`git diff --staged` |
| 暫存 / 提交 | `git add .`、`git commit -m "[manual] ..."` |
| 看歷史 | `git log --oneline --graph`、`git show HEAD~1` |
| 反悔（尚未 add） | `git restore <檔案>` |
| 反悔（已 add 未 commit） | `git restore --staged <檔案>` |
| 上傳 / 下載 | `git push`、`git pull` |
| 複製遠端 | `git clone <網址>` |
| 遠端設定 | `git remote -v`、`git remote set-url origin <網址>` |
