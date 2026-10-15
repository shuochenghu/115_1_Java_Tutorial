---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: "第5週：類別與物件設計(I)：封裝、建構子與設計原則"
description: "物件導向程式設計（與AI協作）115學年度第1學期 第5週投影片（claude版本）"
footer: "物件導向程式設計｜Week 05｜claude 版本"
style: |
  section { font-size: 25px; }
  section h1 { font-size: 1.6em; }
  section h2 { font-size: 1.2em; }
  section code { font-size: 0.85em; }
  section pre { font-size: 0.72em; }
  section table { font-size: 0.78em; }
  footer { font-size: 14px; }
  /* 生成來源標註：右上角徽章。by-claude = Claude 生成 */
  section.by-claude::before {
    content: "claude"; background: #c96442; color: #fff;
    position: absolute; top: 14px; right: 20px;
    font-size: 0.5em; font-weight: bold; letter-spacing: 0.05em;
    padding: 2px 10px; border-radius: 10px;
  }
  section.lead h1, section.lead h2, section.lead p { text-align: center; }
  /* 對應範例檔：左上角標籤 */
  section .ex {
    position: absolute; top: 14px; left: 20px;
    background: #2b5d7d; color: #fff; font-size: 0.6em;
    padding: 2px 10px; border-radius: 10px;
  }
  .cols { display: grid; grid-template-columns: 1fr 1fr; gap: 28px; }
  .small { font-size: 0.8em; }
---

<!-- _class: lead by-claude -->

# 第 5 週
# 類別與物件設計(I)：封裝、建構子與設計原則

## AI 協作需求轉換練習

物件導向程式設計（與 AI 協作）｜115 學年度第 1 學期
資訊管理學系 二年級｜授課教師：胡碩誠

<!--
講者備註：本週延續第四週 claude 版的 MissionLog。開場直接投影上週的三條清單與 state[1]++，問學生「哪裡容易出錯」。
-->

---

<!-- _class: by-claude -->

## 關於這份教材

- 每頁右上角 <span style="background:#c96442;color:#fff;padding:2px 10px;border-radius:10px;font-size:0.8em">claude</span>：初稿由 Anthropic Claude 生成，教師審閱後使用
- 參考書：Deitel, *Java How to Program, Early Objects* 11e
  - 第 3 章：3.2–3.5（欄位、get 方法、建構子、參考型態）
  - 第 8 章：8.2–8.4、8.6–8.7、8.13（存取控制、`this`、預設建構子、`final` 欄位）
- 搭配檔案：`week05_實作講義_claude.md`、`week05_作業說明_claude.md`、`code/`

**本週不教**：繼承與 `toString` 覆寫（第 7–8 週）、建構子過載（第 10 週）、例外處理（第 12 週）、Lambda／Stream（第 13 週）、JUnit（第 14 週）

---

<!-- _class: by-claude -->

<div class="ex">範例：week04 MissionLog.java</div>

## 上週留下的問題

```java
int[] state = {20, 0, 0};                  // ENERGY=0, SAMPLES=1, ACTIONS=2
commandLog.add(command);
energyLog.add(state[ENERGY]);
// 忘了 resultLog.add(result);  → 之後每一步都對錯行
state[1]++;                                 // 1 是 SAMPLES？還是 ENERGY？
```

- 「一步」其實是**一筆紀錄**：指令＋結果＋能源
- 「探測車狀態」其實是**一個東西**：能源＋樣本＋行動次數
- 而且：誰都能寫 `state[ENERGY] = -7`，沒有人擋

→ 本週：**自己設計類別**，讓資料和規則待在一起

---

<!-- _class: by-claude -->

## 本週學習目標

1. 說明類別與物件的差別，用箭頭圖解釋 `new` 與物件參考
2. 用 `private` 欄位＋公開方法守住物件的規則（封裝）
3. 寫建構子，讓物件一產生就是合法狀態；用 `this` 區分欄位與參數
4. 分辨實例方法與 `static` 方法
5. 用 `ArrayList<MissionStep>` 取代三條平行清單
6. 把文字需求轉換成**類別設計卡**，用它檢查 AI 產生的類別

---

<!-- _class: by-claude -->

## 今日流程（150 分鐘）

| 時間 | 段落 | 產出 |
|---|---|---|
| 0–10 | 取回 repo、上週問題 | `java-05` 專案 |
| 10–30 | A. 類別與物件 | `RawStepDemo` 預測表、箭頭圖 |
| 30–55 | B. 封裝與建構子（C# 對照） | `BatteryDemo`、兩個編譯錯誤 |
| 55–65 | C. IntelliJ IDEA 開多檔專案 | 執行 `MissionLog 3 9 0` |
| 65–105 | D. 任務日誌改用物件 TODO 1–7 | `RoverTry`、五條測試 |
| 105–130 | E. 需求轉換＋AI 除錯 | 設計卡、偏差表、反例 |
| 130–135 | F. 課末檢核、第 6 週引題 | |
| 135–150 | G. 保存、push、全新 clone 驗證 | GitHub 紀錄 |

---

<!-- _class: by-claude -->

## 範例檔與投影片對照

| 段落 | 範例檔（`code/`） | 執行 |
|---|---|---|
| A 類別與物件 | `examples/RawStep.java`、`RawStepDemo.java` | `java -cp out RawStepDemo` |
| B 封裝與建構子 | `examples/Battery.java`、`BatteryDemo.java` | `java -cp out BatteryDemo` |
| D Rover | `starter/Rover.java`、`RoverTry.java` | `java -cp out RoverTry` |
| D 任務日誌 | `starter/MissionStep.java`、`MissionLog.java` | `java -cp out MissionLog 3 9 0` |
| E AI 除錯 | 在完成的程式副本上修改 | 同上 |

各頁左上角的藍色標籤，標出該頁對應的檔案；解答在 `code/teacher/`，**不要**複製到 `src/`

---

<!-- _class: by-claude -->

## A｜你其實早就用過物件

<div class="cols">
<div>

C# WinForms：

```csharp
var report = new ReportForm();
report.Text = "任務報告";
report.Show();
button1.Enabled = false;
```

</div>
<div>

- `ReportForm` 是**類別**：設計圖
- `new ReportForm()` 產生**物件**
- `report` 存的是指向物件的**參考**
- `Text`、`Show()` 是這個物件的資料與操作

</div>
</div>

本週換成**自己寫**類別：Java 的類別寫法和 C# 幾乎一樣

<!--
講者備註：WinForms 的 Form1 其實繼承了 Form（: Form），繼承第 7 週才談，這裡只強調「類別 → new → 物件」。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/RawStep.java、RawStepDemo.java</div>

## A｜`RawStep`：把一步打包成一個類別

```java
public class RawStep {          // 檔名必須是 RawStep.java
    public int command;
    public String result;
    public int energy;
}
```

```java
RawStep a = new RawStep();      // 產生物件；a 存參考
a.command = 1;
a.result = "OK";
a.energy = 14;
```

**先預測**：剛 `new` 完、還沒指定之前，三個欄位各是多少？

<!--
答案：0、null、0。欄位有預設值（和陣列元素相同）；區域變數則沒有，必須先指定才能用。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/RawStepDemo.java</div>

## A｜兩個物件、一個別名

```java
RawStep b = new RawStep();      // 第二個物件，有自己的三個欄位
RawStep alias = a;              // 不是新物件，只是複製參考
alias.energy = 99;
System.out.println(a.energy);   // ?
System.out.println(a == alias); // ?
```

```text
a     ──> 物件 1 [ command=1 | result="OK" | energy=99 ]
alias ──> 物件 1（同一個）
b     ──> 物件 2 [ command=2 | result="OK" | energy=12 ]
```

和第二週的 `String`、第四週的陣列相同：**變數存參考，`==` 比參考**

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/RawStepDemo.java</div>

## A｜印出物件、放進清單

```java
System.out.println("println(a): " + a);
ArrayList<RawStep> steps = new ArrayList<>();
steps.add(a);
steps.add(b);
System.out.println(steps.get(1).energy);
```

```text
println(a): RawStep@2f4d3709        ← 類別名稱@雜湊碼（每次可能不同）
12
```

- 直接印物件看不到欄位：和第四週 `println(script)` 一樣
- 自訂文字格式的正式寫法（`toString`）第 7–8 週再教；本週用自己寫的方法

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/RawStepDemo.java</div>

## A｜打包了，但誰都能亂改

```java
RawStep bad = new RawStep();
bad.command = 1;
bad.energy = -7;     // 能源不會是負的，但編譯器不會阻止
```

```text
bad: command=1, result=null, energy=-7
```

兩個問題：

1. **可以寫入不合理的值**：欄位是 `public`
2. **可以忘了設定**：`result` 仍是 `null`

→ 需要：**欄位藏起來**＋**一產生就合法**

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/Battery.java</div>

## B｜封裝：欄位 `private`，只開放方法

```java
public class Battery {
    private final int capacity;   // final：建構子指定後不能再改
    private int level;

    public int getLevel() {
        return level;
    }

    public boolean use(int amount) {
        if (amount <= 0 || amount > level) {
            return false;         // 拒絕，電量不變
        }
        level -= amount;
        return true;
    }
}
```

外部只能**讀**電量、只能用 `use`／`charge` **改**電量 → 規則只寫在一個地方

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/Battery.java｜建構子</div>

## B｜建構子：物件一產生就要合法

```java
public Battery(int capacity, int level) {
    if (capacity < 1) {
        capacity = 1;
    }
    this.capacity = capacity;
    if (level < 0) {
        level = 0;
    } else if (level > capacity) {
        level = capacity;
    }
    this.level = level;           // 先修正參數，再存入欄位
}
```

- 名稱與類別相同、**沒有回傳型態**（寫了 `void` 就變成普通方法）
- 不合理的初始值改成最接近的合法值（本週還沒教例外處理）

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/Battery.java｜this</div>

## B｜`this`：欄位還是參數？

```java
public Battery(int capacity, int level) {
    ...
    this.level = level;
//  ^^^^^^^^^^   ^^^^^
//  這個物件的欄位   參數
}
```

| 寫法 | 結果 |
|---|---|
| `this.level = level;` | 參數存進欄位 ✔ |
| `level = level;` | 參數指定給自己，**欄位仍是 0**，編譯器不一定警告 |
| 欄位是 `final` 卻沒指定 | 編譯錯誤 `variable ... might not have been initialized` |

`this` = 「目前這個物件」：C# 的 `this` 意思相同

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/BatteryDemo.java</div>

## B｜先預測再執行

```java
Battery spare = new Battery(10, 4);
Battery tooFull = new Battery(10, 25);
Battery negative = new Battery(10, -3);
spare.use(3);
spare.use(5);
spare.use(-2);
spare.charge(20);
```

| 呼叫 | 預測回傳 | 預測電量 |
|---|---|---|
| `tooFull`、`negative` 建立後 | — | ？／？ |
| `spare.use(3)` | ？ | ？ |
| `spare.use(5)` | ？ | ？ |
| `spare.use(-2)` | ？ | ？ |
| `spare.charge(20)` | ？ | ？ |

<!--
答案：10／0；true, 1；false, 1；false, 1；9, 10。use(-2) 若沒擋 amount <= 0，電量會變成 3。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/BatteryDemo.java 最後兩行註解</div>

## B｜讓編譯器幫你守規則

拿掉 `// spare.level = 99;` 的註解：

```text
error: level has private access in Battery
```

拿掉 `// Battery empty = new Battery();` 的註解：

```text
error: constructor Battery in class Battery cannot be applied to given types;
  required: int,int
  found:    no arguments
```

- 類別**完全沒寫**建構子時，才有編譯器給的無參數預設建構子（`RawStep` 就是）
- 自己寫了一個，預設的就消失 → 不可能產生「沒給初始值」的 `Battery`

---

<!-- _class: by-claude -->

## B｜C# 對照：三個容易帶過來的習慣

| 想做的事 | C# | Java |
|---|---|---|
| 外部只能讀 | `public int Level { get; private set; }` | `private int level;`＋`public int getLevel()` |
| 欄位沒寫修飾字 | 預設 `private` | **套件存取**：同套件的類別都能用 |
| 指定後不能改 | `readonly` | `final` |
| 讀取的寫法 | `spare.Level` | `spare.getLevel()` |

Java 沒有屬性（property）；慣例是 `getXxx()`，布林可用 `isXxx()`

**每個欄位都明確寫 `private`**：不寫也能編譯，錯誤不會自己出現

---

<!-- _class: by-claude -->

## B｜設計原則：本週的四條

1. **資料和規則放在一起**：誰保管電量，誰負責檢查電量
2. **欄位 `private`，只公開必要的操作**：有 getter 不代表要有 setter
3. **建構子保證一出生就合法**：之後每個方法都維持這個條件
4. **一個類別負責一件事**：類別名稱說得出它負責什麼

`Battery` 沒有 `setLevel()`：**刻意的**。電量只能透過 `use`／`charge` 改變

<!--
講者備註：Deitel 8.7 也提到 set 方法應驗證資料。本週直接用「行動方法」取代 setter，讓學生看見 getter/setter 不是成對必備。
-->

---

<!-- _class: by-claude -->

<div class="ex">C｜java-05 專案（三個以上的檔案）</div>

## C｜三個類別合作：用 IntelliJ IDEA 開專案

1. File → Open → 選 **`java-05`**（不是 `src`）
2. `src` 應為藍色 Sources Root；不是的話：右鍵 → Mark Directory as → Sources Root
3. File → Project Structure → SDK：選已安裝的 JDK（不必指定版本）
4. 開 `MissionLog.java` → 按 `main` 左邊的綠色執行箭頭
5. Run → Edit Configurations… → Program arguments：`3 9 0`
6. 在 `execute` 上 Ctrl＋左鍵跳到 `Rover`；Alt＋7 看 Structure

- 很多類別都有 `main`：IDE 執行你按下箭頭的那個；命令列看 `java -cp out 類別名`
- `.idea/`、`out/` 不進 Git；IDEA 問要不要建立 Git repository 時選否（不建巢狀 repo）

---

<!-- _class: by-claude -->

## D｜第四週 → 第五週：誰負責什麼？

| 第四週 | 第五週 | 負責 |
|---|---|---|
| `int[] state`＋`ENERGY`、`SAMPLES`、`ACTIONS` | `Rover` 的三個 `private` 欄位 | 保管狀態 |
| `applyCommand(state, command)` | `rover.execute(command)` | 執行規則 |
| 三條清單的同一個索引 | 一個 `MissionStep` 物件 | 記錄一步 |
| `main`、`printReplay`、`printSummary` | `MissionLog`（流程與輸出） | 跑任務、報告 |

**輸出格式和規則完全不變**：第四週的五條測試，本週要得到一模一樣的結果

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/Rover.java｜TODO 1–2</div>

## D｜`Rover`：TODO 1–2

```java
private int energy;
private int samples;
private int actionCount;

public Rover(int energy, int samples) {   // TODO 1：照 Battery 的做法
}                                          // energy 0..20、samples >= 0、actionCount 0

public String explore() {                  // 已提供：scan、charge 照著寫（TODO 2）
    if (energy < 6) {
        return "NOT_ENOUGH_ENERGY";
    }
    energy -= 6;
    samples += 2;
    actionCount++;
    return "OK";
}
```

起始碼的建構子是空的 → 欄位都是 0 → `MissionLog` 一開始就 `EXHAUSTED`（D1）

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/Rover.java、MissionLog.java</div>

## D｜實例方法 vs `static` 方法

```java
// 第四週：static 方法，狀態要當參數傳進去
String result = applyCommand(state, command);

// 第五週：實例方法，物件自己帶著狀態
String result = rover.execute(command);
```

- `rover.execute(...)` 裡的 `energy` 就是 `rover` 這台車的欄位（隱含的 `this`）
- `static` 方法屬於類別，**沒有** `this`：`main` 是 static，所以要先 `new Rover(...)`

```text
Rover.explore();
error: non-static method explore() cannot be referenced from a static context
```

<!--
講者備註：這一頁的錯誤由教師當場加一行示範（講義 D2-2），學生只需說明差別，不必自己製造這個編譯錯誤。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/RoverTry.java</div>

## D｜`RoverTry`：直接測類別

不經過 `MissionLog`，直接建立物件、呼叫方法、比對預期：

```text
new Rover(20, 0): 20/0/0  (expect 20/0/0) OK
new Rover(25, -3): 20/0/0  (expect 20/0/0) OK
new Rover(4, 2): 4/2/0  (expect 4/2/0) OK
r.charge() -> ALREADY_FULL  (expect ALREADY_FULL)
r.scan() -> OK  (expect OK)
r.charge() -> OK  (expect OK, 18+5 capped at 20)
r: 20/1/2  (expect 20/1/2) OK
```

- `MissionLog` 只用 `new Rover(20, 0)`：建構子修正有沒有錯，**任務測試看不出來**
- 完成 TODO 1–2 後先跑 `RoverTry`，全部 `OK` 再往下

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionStep.java｜TODO 3–4</div>

## D｜`MissionStep`：一步＝一個不可變的物件

```java
public class MissionStep {
    private final int command;     // 完成 TODO 3 後加上 final
    private final String result;
    private final int energyAfter; // 執行「後」的能源數值

    public MissionStep(int command, String result, int energyAfter) {
        this.command = command;    // TODO 3：三個欄位都要存
        ...
    }
    public int getCommand() { return command; }   // TODO 4：照著寫另外兩個
    public String describe() {                    // 已提供：回放用的一行
        return "command=" + command + ", result=" + result + ", energy=" + energyAfter;
    }
}
```

沒有 setter：發生過的步驟不該被改寫。漏存一個 `final` 欄位 → 編譯錯誤

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java｜TODO 5–6</div>

## D｜`MissionLog`：三行 `add` 變一行（TODO 5 跟做）

```java
for (int command : script) {
    if (rover.isSuccess() || rover.isExhausted()) {
        break;                     // 已停止：不讀、不記
    }
    String result = rover.execute(command);
    steps.add(new MissionStep(command, result, rover.getEnergy()));   // TODO 5
    if (command == 0) {
        break;
    }
}
```

TODO 6 `printReplay`：照第四週的回放迴圈，改成每步印 `"step N: "` 加上 `describe()`

```text
=== replay ===
step 1: command=3, result=ALREADY_FULL, energy=20
step 2: command=9, result=UNKNOWN_COMMAND, energy=20
step 3: command=0, result=RETURN, energy=20
```

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionStep.java、MissionLog.java｜TODO 7</div>

## D｜TODO 7：物件清單的摘要

第四週的現成方法，現在不能直接用：

```java
Collections.frequency(resultLog, "OK");   // 清單裡是 MissionStep，不是 String
Collections.min(energyLog);               // MissionStep 不知道怎麼比大小
```

改用迴圈＋getter；「被拒絕」的判斷交給 `MissionStep` 自己：

```java
public boolean isRejected() {             // 不是 OK、也不是 RETURN
    ...
}
```

```text
steps=3, ok=0, rejected=2, ignored=0      ← 3 9 0
lowest energy=20
```

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/（教師：code/teacher/MissionLogCheck.java）</div>

## D｜必做測試（和第四週相同）

| 參數 | 最終 E／S／A | 結果 | 摘要 |
|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | `ok=3`，最低 6 |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | `ok=5`，最低 10 |
| `3 9 0` | 20／0／0 | RETURNED | `ok=0, rejected=2`，最低 20 |
| `1 1 1 1` | 2／6／3 | SUCCESS | `ignored=1`，最低 2 |
| `2 1` | 12／3／2 | UNFINISHED | `ok=2`，最低 12 |

加上 `java -cp out RoverTry` 全部 `OK`。E／S／A = energy／samples／actionCount

---

<!-- _class: by-claude -->

## E｜需求轉換：先把需求寫成設計卡

> 樣本儲存艙的容量在建造時決定，1 到 10，超出範圍用最接近的合法值；一開始是空的。可一次存入多份，數量必須是正數，放不下就整批拒絕，並讓呼叫的人知道有沒有成功。返航時全部卸下，要知道卸下幾份。隨時可查存量與剩餘空間，但不能直接改存量。

| 設計卡 | 從需求的哪一句來？ |
|---|---|
| 類別名稱、欄位（型態、修飾字、`final`） | |
| 建構子（參數、修正規則） | |
| 公開方法（參數、回傳、拒絕條件） | |
| 刻意**不**提供的方法 | |
| 任何時候都成立的條件 | |

---

<!-- _class: by-claude -->

<div class="ex">講義 E2</div>

## E｜模糊 Prompt：「幫我寫一個存樣本的 Java 類別」

<div class="cols">
<div>

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

</div>
<div>

**教師設計的模擬回覆**，不是真實對話

對照設計卡，找出**至少四處**不符合需求：

- 哪一句需求沒做到？
- 舉一個會出錯的呼叫

能編譯、看起來整齊，**不等於符合需求**

</div>
</div>

<!--
參考：public 欄位；容量不能指定；setCount 繞過規則；store 不檢查正數與空間、回傳 void；沒有 unload 與剩餘空間。
-->

---

<!-- _class: by-claude -->

## E｜把設計卡放進 Prompt

一個好的「類別需求」Prompt 包含：

1. **類別負責什麼**，名稱是什麼
2. **欄位**：名稱、型態、`private`、是否 `final`
3. **建構子**：參數、不合法初始值怎麼處理
4. **每個方法**：參數、回傳、什麼情況拒絕、拒絕時狀態是否改變
5. **不要提供**的方法（例如 setter）與理由
6. **限制**：本週不用例外處理、繼承、Lambda
7. **請 AI 附上邊界測試呼叫**，你再自己執行驗證

需求的每一句，都應該對應到設計卡的一格；Prompt 長短不是重點

---

<!-- _class: by-claude -->

<div class="ex">範例：在完成的程式副本上修改</div>

## E｜AI 除錯三選一：預設腳本都還是 `6／5／3 SUCCESS`

| # | 模擬 AI 修改 | 說法 |
|---|---|---|
| 1 | `MissionStep` 改存 `Rover`，`getEnergyAfter()` 回傳 `rover.getEnergy()` | 「直接存探測車，之後要什麼都能查」 |
| 2 | `charge()` 改用 `Math.min(energy + 5, CAPACITY)`，之後一律 `actionCount++` 並回傳 `OK` | 「充電一行寫完」 |
| 3 | 建構子先 `this.energy = energy;` 再修正 `energy` | 「先存再修正，程式比較短」 |

1. **預測**哪個輸入或哪支程式會讓錯誤現形 → 2. 在 repo **以外**的副本重現（不 commit）→ 3. 最小修正 → 4. 重跑必做測試

**只看最後一行，抓不到**：要看回放、看邊界、直接測類別

<!--
答案：1 → 預設腳本回放能源 6,6,6（同一物件的參考）；2 → 3 9 0 變成 OK、actionCount=1；3 → RoverTry 的 new Rover(25, -3) 變成 25/-3/0。
-->

---

<!-- _class: by-claude -->

## F｜課末檢核

1. `Battery a = new Battery(10, 5); Battery b = a; b.use(3);` 之後 `a.getLevel()` 是多少？
2. 寫了 `public Rover(int energy, int samples)` 之後，`new Rover()` 能編譯嗎？
3. `int energy;`（沒寫 `private`）時，其他類別能存取嗎？
4. 為什麼 `MissionStep` 存能源的**數值**，而不是存 `Rover`？

<!--
答案：2（同一物件）；不能（預設建構子消失）；可以（套件存取）；存參考只會讀到最新狀態，紀錄要保存當下的值。
-->

---

<!-- _class: by-claude -->

## 下週預告：把類別之間的關係畫出來

```text
MissionLog ──建立、呼叫──▶ Rover
     │
     └──建立、保存多筆──▶ MissionStep
```

- 每個類別：名稱、欄位（`-` private）、方法（`+` public）
- 誰用到誰？`Rover` 和 `MissionStep` 互相知道對方嗎？
- 想一想：能源 0 時，誰該擋下 `charge()`？`Rover` 還是 `MissionLog`？

→ 第 6 週：UML 類別圖與物件導向設計思維

---

<!-- _class: by-claude -->

## G｜保存與推送（最後 15 分鐘）

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md
   ├─ AI_USAGE.md
   └─ java-05/
      ├─ README.md
      └─ src/  RawStep.java  RawStepDemo.java  Battery.java  BatteryDemo.java
               Rover.java    MissionStep.java  MissionLog.java  RoverTry.java
```

- [ ] 重新編譯；不交 `out/`、`.class`、`.idea/`、`*.iml`
- [ ] commit 前綴：`[manual]`、`[AI]`、`[fix]`、`[doc]`
- [ ] Push → 到 GitHub 確認 → 另一個路徑全新 clone，重跑 `3 9 0` 與 `RoverTry`

---

<!-- _class: by-claude -->

## 作業與 AI 使用紀錄

- 作業說明：`week05_作業說明_claude.md`
- 必繳：完成的三個類別、五條測試＋`RoverTry`＋一條自訂測試、設計卡、偏差表與改寫 Prompt、AI 除錯四筆證據
- `AI_USAGE.md`：使用目的、Prompt 摘要、採納／未採納原因、人工驗證方式、最終修改
- 沒有使用 AI 也要交，寫「本次未使用 AI」

> 你可以用 AI，但你要能逐行解釋你交的每一個類別。
