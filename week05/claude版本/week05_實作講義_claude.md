# 第 5 週實作講義：讓探測車自己管好自己（claude 版本）

> 本講義初稿由 Anthropic Claude（Claude Code）生成，教師審閱後使用。
> 主題：類別與物件設計(I)：封裝、建構子與設計原則——AI 協作需求轉換練習。
> 參考書：Deitel, *Java How to Program, Early Objects* 11e，3.2–3.5、8.2–8.4、8.6–8.7、8.13（節次見各段）。

「待填」的地方請直接寫在本檔，課後複製到 `LAB_REPORT.md`。凡是要你**先預測**的題目，請先寫預測再執行；預測錯了不扣分，但不可事後修改預測。

## 0｜準備（10 分鐘）

1. 用 GitHub Desktop 取回學期 repo `115_1_Java`（還原過的電腦請重新 clone；已有副本請先 Fetch／Pull）。
2. 建立今天的資料夾：

   ```text
   115_1_Java/YYYY_MM_DD/java-05/src/
   ```

3. 把教師提供的檔案複製到 `src/`：
   - `code/examples/` 的 `RawStep.java`、`RawStepDemo.java`、`Battery.java`、`BatteryDemo.java`
   - `code/starter/` 的 `Rover.java`、`MissionStep.java`、`MissionLog.java`、`RoverTry.java`

   **不要**複製 `code/teacher/` 的檔案（同名類別會互相衝突）。
4. 在 `java-05` 內編譯並執行：

   ```powershell
   javac -encoding UTF-8 -d out src/*.java
   java -cp out MissionLog
   ```

   起始碼能執行，最後一行是 `final energy=0, samples=0, actionCount=0 -> EXHAUSTED`。這**不是**第四週的結果，原因在 D 段揭曉。

上週留下兩個問題，請各用一句話回答：

- 三條平行清單 `commandLog`、`energyLog`、`resultLog`，最怕發生什麼事？待填
- `state[1]++` 這行，讀程式的人要先知道什麼才看得懂？待填

## A｜類別與物件（20 分鐘，Deitel 3.2、3.5）

開啟 `RawStep.java`：它把「一步」的三個值包成一個類別。再開啟 `RawStepDemo.java`，**先預測**再執行 `java -cp out RawStepDemo`。

| 程式 | 預測輸出 | 實際輸出 |
|---|---|---|
| `new RawStep()` 後的三個欄位 | 待填 | 待填 |
| `alias.energy = 99` 之後的 `a.energy`、`a == alias` | 待填 | 待填 |
| `println(a)` | 待填 | 待填 |
| `steps.get(1).energy` | 待填 | 待填 |
| `bad` 的三個欄位 | 待填 | 待填 |

A1. 用自己的話說明「類別」和「物件」的差別，並指出 `RawStepDemo` 裡哪一行**產生**了物件：待填

A2. 畫出 `RawStep alias = a;` 之後的箭頭圖：`a`、`alias`、`b` 各指向哪個物件？

```text
待填
```

A3. `println(a)` 為什麼不是印出三個欄位？和第四週 `println(script)` 有什麼共同點？待填

A4. 從 `bad` 那一段看，`RawStep` 這種寫法有哪兩個問題？待填

## B｜封裝與建構子（25 分鐘，Deitel 3.2–3.4、8.2–8.4、8.6、8.13）

開啟 `Battery.java` 與 `BatteryDemo.java`。`Battery` 的規則：容量至少 1，電量必須在 0 到容量之間。

**先預測**再執行 `java -cp out BatteryDemo`：

| 程式 | 預測 | 實際 |
|---|---|---|
| `new Battery(10, 25)` 的電量 | 待填 | 待填 |
| `new Battery(10, -3)` 的電量 | 待填 | 待填 |
| `spare.use(5)`（此時電量 1）的回傳值與電量 | 待填 | 待填 |
| `spare.use(-2)` 的回傳值 | 待填 | 待填 |
| `spare.charge(20)` 的回傳值與電量 | 待填 | 待填 |

B1. 把 `BatteryDemo.java` 裡 `// spare.level = 99;` 的註解拿掉再編譯，記下錯誤訊息的關鍵句：待填

看完後恢復成註解。

B2. 把 `// Battery empty = new Battery();` 的註解拿掉再編譯，記下錯誤訊息，並說明為什麼 `RawStep` 可以 `new RawStep()`，`Battery` 卻不行：待填

看完後恢復成註解。

B3. 在 `Battery` 建構子中，`this.level = level;` 左右兩個 `level` 各指什麼？如果寫成 `level = level;` 會怎樣？待填

B4. `Battery` 沒有 `setLevel(int)` 方法。這是疏忽還是刻意的設計？理由：待填

B5. C# 對照，完成下表：

| 想做的事 | C# | Java |
|---|---|---|
| 外部只能讀、不能改電量 | `public int Level { get; private set; }` | 待填 |
| 欄位沒寫存取修飾字 | 預設是 `private` | 待填 |
| 建構子指定後不能再改的欄位 | `readonly` | 待填 |

## C｜三個以上的檔案：用 IntelliJ IDEA 開專案（10 分鐘）

本週的任務程式由 `Rover`、`MissionStep`、`MissionLog` 三個類別合作，`MissionLog` 呼叫另外兩個。檔案變多後，用 IDE 跳轉與查看結構比較方便。VS Code 仍可使用；以下為 IntelliJ IDEA 的操作。

1. File → Open，選 `java-05` 資料夾（不是 `src`）。
2. `src` 圖示應為藍色（Sources Root）。若不是：在 `src` 按右鍵 → Mark Directory as → Sources Root。
3. File → Project Structure → Project → SDK：選電腦上已安裝的 JDK，不必刻意選某個版本。
4. 開啟 `MissionLog.java`，按 `main` 左邊的綠色 ▶ 執行。
5. Run → Edit Configurations…，在 Program arguments 填 `3 9 0` 再執行一次。
6. 在 `rover.execute(command)` 的 `execute` 上按 Ctrl＋左鍵，跳到 `Rover.java`；Alt＋7 開啟 Structure，看 `Rover` 有哪些欄位與方法。

C1. IntelliJ 的 Structure 列出 `Rover` 的哪些欄位與方法？哪些是 `public`、哪些是 `private`？待填

C2. `java-05` 的 `src` 裡有好幾個類別都有 `main`。IDE 怎麼知道要執行哪一個？命令列又是靠什麼決定？待填

A、B 段的兩檔範例用命令列或 VS Code 即可，不必為它們開 IntelliJ。

提醒：IntelliJ 會在專案內產生 `.idea/` 與 `out/`，第 1 週的 `.gitignore` 已排除它們；commit 前在 GitHub Desktop 確認沒有被列入。若 IntelliJ 詢問是否建立 Git repository，選否：`java-05` 已在 `115_1_Java` repo 裡，不要再建巢狀 repo。

## D｜任務日誌改用物件（40 分鐘，Deitel 8.2–8.4、8.7）

任務的輸出格式、規則與第四週**完全相同**；改變的是資料放在哪裡、由誰負責。

| 類別 | 負責 | 對照第四週 |
|---|---|---|
| `Rover` | 保管能源、樣本、行動次數；執行探索、掃描、充電規則 | `int[] state` 與 `applyCommand` |
| `MissionStep` | 記錄一步：指令、結果、執行後能源 | 三條清單的同一個索引 |
| `MissionLog` | 讀腳本、跑任務、回放與摘要 | `main` 與流程方法 |

起始碼的 TODO：

| TODO | 檔案 | 內容 | 方式 |
|---|---|---|---|
| 1 | `Rover` | 建構子：修正初始值、用 `this` 存入欄位 | 跟做 |
| 2 | `Rover` | `scan()`、`charge()` | 模仿 `explore()` |
| 3 | `MissionStep` | 建構子：用 `this` 存三個欄位 | 跟做 |
| 4 | `MissionStep` | `getResult()`、`getEnergyAfter()` | 模仿 `getCommand()` |
| 5 | `MissionLog` | `runMission` 中建立一筆 `MissionStep` 加入清單 | 跟做 |
| 6 | `MissionLog` | `printReplay` | 模仿 |
| 7 | `MissionStep`＋`MissionLog` | `isRejected()` 與 `printSummary` | 獨立 |

D1. 動手前先回答：起始碼為什麼印出 `final energy=0, samples=0, actionCount=0 -> EXHAUSTED`？（提示：`Rover` 的建構子目前是空的，A 段看過欄位的預設值。）待填

### D2. TODO 1–2：Rover（跟做＋模仿）

完成後執行 `java -cp out RoverTry`，每一行都應出現 `OK` 或與 `expect` 相同的結果。

| `RoverTry` 的行 | 實際輸出 | 和 expect 一致？ |
|---|---|---|
| `new Rover(25, -3)` | 待填 | 待填 |
| 第一次 `r.charge()` | 待填 | 待填 |
| 第二次 `r.charge()` 之後的 `r` | 待填 | 待填 |
| `low` | 待填 | 待填 |

D2-1. 第四週寫 `applyCommand(state, command)`，要把 `state` 傳進去；本週寫 `rover.execute(command)`，不用傳狀態。`execute` 怎麼知道要改哪一台探測車的能源？待填

D2-2. 教師會示範在 `MissionLog` 的 `main` 加一行 `Rover.explore();` 後的編譯錯誤（投影片「實例方法 vs `static` 方法」頁）。看完示範後說明 `Rover.explore()` 與 `rover.explore()` 的差別：待填

### D3. TODO 3–4：MissionStep（跟做＋模仿）

完成 TODO 3 後，照檔案中的提示把三個欄位改成 `private final`，重新編譯。

D3-1（選做）. 故意刪掉建構子中 `this.energyAfter = energyAfter;` 再編譯，記下錯誤訊息；看完後恢復：待填

D3-2. `MissionStep` 為什麼不提供 `setResult(String)`？待填

### D4. TODO 5–7：MissionLog（跟做＋模仿＋獨立）

TODO 5 只需要一行。TODO 7 的 `printSummary` 不能再用 `Collections.frequency(resultLog, "OK")` 或 `Collections.min(energyLog)`：清單裡的元素是 `MissionStep`，請用迴圈搭配 getter 與 `isRejected()`。

必做測試（每列重新執行一次）：

| 參數 | 最終 E／S／A | 結果 | 摘要 | 實際（貼回放之後的四行） |
|---|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=0`，`lowest energy=6` | 待填 |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | `steps=5, ok=5, rejected=0, ignored=0`，`lowest energy=10` | 待填 |
| `3 9 0` | 20／0／0 | RETURNED | `steps=3, ok=0, rejected=2, ignored=0`，`lowest energy=20` | 待填 |
| `1 1 1 1` | 2／6／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=1`，`lowest energy=2` | 待填 |
| `2 1` | 12／3／2 | UNFINISHED | `steps=2, ok=2, rejected=0, ignored=0`，`lowest energy=12` | 待填 |

E／S／A = energy／samples／actionCount。`3 9 0` 另外貼上完整輸出（含回放）；回放與 `counts` 應與第四週相同：

```text
=== replay ===
step 1: command=3, result=ALREADY_FULL, energy=20
step 2: command=9, result=UNKNOWN_COMMAND, energy=20
step 3: command=0, result=RETURN, energy=20
counts[0..3]=[1, 0, 0, 1]
```

D4-1. 第四週新增一步要寫三行 `add`，本週只要一行。除了少打字，還避免了什麼錯誤？待填

D4-2. 本週的 `runMission` 在**執行指令之前**先檢查是否已成功或耗盡。如果 `MissionLog` 改成 `new Rover(0, 0)` 出發，會讀幾個指令、記錄幾筆？（不必修改程式，從程式碼推論）待填

## E｜AI 協作：需求轉換與除錯（25 分鐘）

### E1. 把需求轉換成類別設計卡（8 分鐘）

任務中心的需求如下：

> 探測車要加裝一個樣本儲存艙。儲存艙的容量在建造時決定，至少 1、最多 10，超出範圍就用最接近的合法值。一開始是空的。可以一次存入好幾份樣本，但數量必須是正數，放不下就整批拒絕，並讓呼叫的人知道有沒有存成功。返航時把樣本全部卸下，要知道卸下了幾份。任務中心隨時要能查目前存了幾份、還剩多少空間，但不能直接改存量。

把需求轉換成設計卡：

| 項目 | 你的設計 |
|---|---|
| 類別名稱 | 待填 |
| 欄位（名稱、型態、存取修飾字、是否 final） | 待填 |
| 建構子（參數、如何修正不合法的值） | 待填 |
| 公開方法（名稱、參數、回傳型態、拒絕條件） | 待填 |
| 刻意**不**提供的方法與理由 | 待填 |
| 物件任何時候都必須成立的條件 | 待填 |

### E2. 模糊 Prompt 與設計卡 Prompt（10 分鐘）

有位同學只輸入「幫我寫一個存樣本的 Java 類別」，以下是**教師設計的模擬 AI 回覆**（不是真實對話）：

```java
public class SampleBay {
    public int capacity;
    public int count;

    public SampleBay() {
        capacity = 10;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void store(int n) {
        count += n;
    }

    public int getCount() {
        return count;
    }
}
```

對照你的設計卡，找出至少四處不符合需求的地方：

| # | 不符合之處 | 違反需求或設計卡的哪一點 | 可能造成的錯誤（舉一個呼叫例子） |
|---|---|---|---|
| 1 | 待填 | 待填 | 待填 |
| 2 | 待填 | 待填 | 待填 |
| 3 | 待填 | 待填 | 待填 |
| 4 | 待填 | 待填 | 待填 |

改寫 Prompt：把設計卡的內容放進 Prompt，並寫出本週限制（不可使用例外處理、繼承、Lambda）以及你要 AI 一起提供的測試呼叫：

```text
待填
```

若你用真實 AI 工具送出這個 Prompt，請把實際回覆摘要與你的判斷記在 `AI_USAGE.md`；不要把上面的模擬回覆寫成真實對話。

### E3. AI 除錯三選一（7 分鐘＋課後）

**先 commit 你完成的程式**，再把 `src` 複製到 `115_1_Java` repo **以外**的資料夾（例如桌面的 `w05-ai-test`），在副本重現錯誤；副本不要 commit。以下三種**模擬 AI 修改**都讓預設腳本的最後一行維持 `6／5／3 SUCCESS`：

1. AI 說「直接存探測車比較省事，之後要什麼都能查」，把 `MissionStep` 改成保存 `Rover`：

   ```java
   private final Rover rover;

   public MissionStep(int command, String result, Rover rover) {
       this.command = command;
       this.result = result;
       this.rover = rover;
   }

   public int getEnergyAfter() {
       return rover.getEnergy();
   }
   // describe() 也改用 rover.getEnergy()；runMission 改成 new MissionStep(command, result, rover)
   ```

2. AI 說「充電可以一行寫完」，把 `charge()` 改成：

   ```java
   public String charge() {
       energy = Math.min(energy + 5, CAPACITY);
       actionCount++;
       return "OK";
   }
   ```

3. AI 說「先存再修正，程式比較短」，把 `Rover` 建構子改成：

   ```java
   public Rover(int energy, int samples) {
       this.energy = energy;
       this.samples = samples;
       this.actionCount = 0;
       if (energy > CAPACITY) {
           energy = CAPACITY;
       }
       if (energy < 0) {
           energy = 0;
       }
       if (samples < 0) {
           samples = 0;
       }
   }
   ```

我選第 待填 題。先預測：哪一個輸入或哪一個程式（`MissionLog` 的哪組參數，或 `RoverTry`）會讓錯誤現形？待填

| 證據 | 執行內容 | 預期 | 實際 |
|---|---|---|---|
| 反例：AI 版 | 待填 | 待填 | 待填 |
| 反例：最小修正後 | 同上 | 待填 | 待填 |
| 修正後一般成功 | `MissionLog 2 2 2 2 2` | 10／5／5 SUCCESS，回放能源 18、16、14、12、10 | 待填 |
| 修正後拒絕／返回 | `MissionLog 3 9 0` | 20／0／0 RETURNED，`ok=0, rejected=2` | 待填 |

最小修正是什麼？採納或拒絕這個 AI 建議？理由：待填

## F｜課末檢核（5 分鐘）

1. `Battery a = new Battery(10, 5); Battery b = a; b.use(3);` 之後 `a.getLevel()` 是多少？為什麼？待填
2. 一個類別寫了 `public Rover(int energy, int samples)` 建構子後，`new Rover()` 能不能編譯？待填
3. 欄位宣告 `int energy;`（沒有寫 `private`）時，其他類別能不能存取？待填
4. `MissionStep` 存「執行後能源」的數值，而不是存 `Rover`。用一句話說明理由：待填
5. 下週預告：`Rover`、`MissionStep`、`MissionLog` 之間是誰用到誰？試著用三個方框和箭頭畫出來：待填

## G｜保存、推送、取回驗證（15 分鐘）

- [ ] 重新編譯並跑一次 `3 9 0`；`LAB_REPORT.md`、`AI_USAGE.md`、`java-05/README.md`、`src/` 齊全；不交 `out/`、`.class`、`.idea/`、`*.iml`
- [ ] GitHub Desktop 檢查差異；commit 訊息使用 `[manual]`／`[AI]`／`[fix]`／`[doc]` 前綴
- [ ] Push origin，到 GitHub 網頁確認今天的檔案與最新 commit
- [ ] 在另一個路徑全新 clone，於 `java-05` 重新編譯並跑 `3 9 0` 與 `RoverTry`；不要刪除原本的副本

最新 commit 訊息與時間：待填
全新 clone 路徑與執行結果：待填
未完成事項（課後補）：待填

推送失敗時：先把整個 repo 複製到隨身碟或雲端，確認檔案打得開，再請教師協助；不要直接關機。

## 課後選做

- 依你的設計卡實作 `SampleBay`，再寫一個 `SampleBayTry`（仿照 `RoverTry`）測試：存入剛好填滿、超過容量、存入 0 或負數、卸下空的儲存艙。
- 讓 `MissionLog` 可以用第一個參數指定初始能源，例如 `java -cp out MissionLog E=8 1 1`。說明你如何區分這個參數與指令，並寫出兩個測試。
- `Rover` 現在可以在能源為 0 時被直接呼叫 `charge()`。任務規則說能源耗盡就結束，這條規則應該由 `Rover` 還是 `MissionLog` 負責？寫下你的理由（第 6 週會討論）。
