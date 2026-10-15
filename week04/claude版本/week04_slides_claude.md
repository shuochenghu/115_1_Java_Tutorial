---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: "第4週：陣列、ArrayList 與 Collections Framework 入門"
description: "物件導向程式設計（與AI協作）115學年度第1學期 第4週投影片（claude版本）"
footer: "物件導向程式設計｜Week 04｜claude 版本"
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

# 第 4 週
# 陣列、ArrayList 與 Collections Framework 入門

## Prompt 設計練習：描述資料結構需求

物件導向程式設計（與 AI 協作）｜115 學年度第 1 學期
資訊管理學系 二年級｜授課教師：胡碩誠

<!--
講者備註：本週延續火星探測車。第三週結尾的問題「只剩最後狀態，能不能回放每一步？」就是本週的開場。
-->

---

<!-- _class: by-claude -->

## 關於這份教材

- 每頁右上角 <span style="background:#c96442;color:#fff;padding:2px 10px;border-radius:10px;font-size:0.8em">claude</span>：初稿由 Anthropic Claude 生成，教師審閱後使用
- 參考書：Deitel, *Java How to Program, Early Objects* 11e
  - 第 7 章：7.2–7.4、7.7–7.9、7.14–7.16
  - 第 16 章：16.2–16.4、16.6–16.7（挑選）
- 搭配檔案：`week04_實作講義_claude.md`、`week04_作業說明_claude.md`、`code/`

**本週不教**：自訂類別（第 5 週）、泛型細節（第 11 週）、try/catch（第 12 週）、Lambda／Stream（第 13 週）

---

<!-- _class: by-claude -->

## 上週留下的問題

第三週任務結束時，我們只剩三個數字：

```text
final energy=6, samples=5, actionCount=3 -> SUCCESS
```

- 走的是「探索、探索、掃描」還是「掃描、探索、探索」？
- 中間有沒有被拒絕的指令？能源最低到多少？

**一個變數只能存一個值** → 需要能存「很多筆」的資料結構

<!--
講者備註：讓學生口頭回答。「6/5/3」至少可由 1,1,2 / 1,2,1 / 2,1,1 三種順序得到，無法重建。
-->

---

<!-- _class: by-claude -->

## 本週學習目標

1. 對照 C# 宣告、建立、走訪 Java 陣列，說明 `length`、預設值與越界錯誤
2. 用參考的觀念解釋：陣列傳進方法後，改元素與重新指定的差別
3. 讀取命令列參數 `String[] args`，轉成 `int[]` 腳本
4. 使用 `ArrayList<Integer>`／`ArrayList<String>`，說明包裝類別與自動裝箱
5. 使用 `Collections` 的 `min`、`max`、`frequency`、`sort`
6. 寫出清楚的「資料結構需求」Prompt，並用反例檢驗 AI 的選擇

---

<!-- _class: by-claude -->

## 今日流程（150 分鐘）

| 時間 | 段落 | 產出 |
|---|---|---|
| 0–10 | 取回 repo、上週問題 | `java-04` 專案 |
| 10–25 | A. 陣列語法（C# 對照） | `ArrayBasics` 預測表 |
| 25–40 | B. 陣列是參考型態 | `ArrayReference` 追蹤圖 |
| 40–50 | C. `args` 與指令腳本 | `ScriptArgs` |
| 50–75 | D. ArrayList、包裝類別、Collections | `ListBasics` 三個陷阱 |
| 75–112 | E. 任務日誌 `MissionLog` TODO 1–5 | 五條測試 |
| 112–130 | F. Prompt 需求描述＋AI 除錯 | 決策表、反例 |
| 130–135 | G. 課末檢核、第 5 週引題 | |
| 135–150 | H. 保存、push、全新 clone 驗證 | GitHub 紀錄 |

---

<!-- _class: by-claude -->

## 範例檔與投影片對照

| 段落 | 範例檔（`code/`） | 執行 |
|---|---|---|
| A 陣列語法 | `examples/ArrayBasics.java` | `java -cp out ArrayBasics` |
| B 陣列是參考型態 | `examples/ArrayReference.java` | `java -cp out ArrayReference` |
| C `args` 與腳本 | `examples/ScriptArgs.java` | `java -cp out ScriptArgs 1 1 2` |
| D ArrayList、Collections、三陷阱 | `examples/ListBasics.java` | `java -cp out ListBasics` |
| E 任務日誌、F AI 除錯 | `starter/MissionLog.java`（解答在 `teacher/`） | `java -cp out MissionLog 3 9 0` |

各頁左上角的藍色標籤，標出該頁對應的檔案與段落

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayBasics.java｜開頭的宣告與 length</div>

## A｜陣列：和 C# 幾乎一樣

<div class="cols">
<div>

**C#**
```csharp
int[] counts = new int[4];
int[] script = { 1, 1, 2 };
Console.WriteLine(script.Length);
foreach (int c in script) { ... }
```

</div>
<div>

**Java**
```java
int[] counts = new int[4];
int[] script = {1, 1, 2};
System.out.println(script.length);
for (int c : script) { ... }
```

</div>
</div>

- 長度建立後**不能改變**；索引從 0 到 `length - 1`
- Java 的 `length` 是欄位：小寫、**沒有括號**（`String` 的 `length()` 才有括號）

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayBasics.java｜預設值、Arrays.toString</div>

## 預設值與「印不出內容」

```java
int[] counts = new int[4];        // [0, 0, 0, 0]
boolean[] flags = new boolean[2]; // [false, false]
String[] names = new String[2];   // [null, null]

System.out.println(script);                  // [I@1b28cdfa  ← 型態@雜湊碼
System.out.println(Arrays.toString(script)); // [1, 1, 2]
```

- 數值 → 0、`boolean` → `false`、參考型態 → `null`（與 C# 相同）
- `import java.util.Arrays;`：陣列的工具類別（7.15）

**先預測**：`new double[3]` 印出什麼？`new char[2]` 呢？

<!--
講者備註：double → [0.0, 0.0, 0.0]；char 預設 '\u0000'，印出來看不見。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayBasics.java｜索引走訪、加強版 for</div>

## 兩種走訪：要不要索引？

```java
for (int i = 0; i < script.length; i++) {
    System.out.println("step " + (i + 1) + " -> command " + script[i]);
}

for (int command : script) {   // 加強版 for（enhanced for）
    counts[command]++;          // 指令本身當索引：counts[1]++、counts[1]++、counts[2]++
}
```

| 需求 | 選擇 |
|---|---|
| 需要「第幾個」、要改元素、要同時讀兩個陣列 | 索引 for |
| 只讀每個值 | 加強版 for |

`counts[command]++` 這招：**值的範圍小而連續（0–3）時，陣列就是計數表**

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayBasics.java｜最後一行（拿掉註解）</div>

## 越界：C# 和 Java 都不會默默放過

```java
int[] script = {1, 1, 2};
System.out.println(script[script.length]);
```

```text
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException:
    Index 3 out of bounds for length 3
	at ArrayBasics.main(ArrayBasics.java:36)
```

- C#：`IndexOutOfRangeException`；Java：`ArrayIndexOutOfBoundsException`
- 讀錯誤訊息三件事：**哪個例外**、**哪個索引／長度**、**哪一行**
- 本週只要求看懂並修正原因；`try/catch` 在第 12 週

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayReference.java｜第 1 段 alias、第 4 段 copyOf</div>

## B｜陣列是參考型態（接第二週的箭頭圖）

```java
int[] state = {20, 0, 0};
int[] alias = state;   // 複製的是「參考」，不是陣列
alias[0] = 14;
System.out.println(Arrays.toString(state));   // ?
```

```text
state ──┐
        ├──▶ [ 14 | 0 | 0 ]
alias ──┘
```

答案：`[14, 0, 0]`，兩個變數指向**同一個**陣列物件

需要獨立副本：`int[] backup = Arrays.copyOf(state, state.length);`

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayReference.java｜第 2–3 段、explore／reset</div>

## 陣列傳進方法：改元素 vs. 重新指定

```java
static void explore(int[] s) { s[0] -= 6; s[1] += 2; s[2]++; }
static void reset(int[] s)   { s = new int[] {20, 0, 0}; }

int[] state = {14, 0, 0};
explore(state);   // state = [8, 2, 1]   ← 呼叫端看得到
reset(state);     // state = [8, 2, 1]   ← 沒變！
```

- Java 永遠是**傳值**；陣列變數的「值」是參考（Deitel 7.9）
- `explore`：沿著參考去改**同一個物件**
- `reset`：讓區域變數 `s` 指向**新物件**，呼叫端的 `state` 不受影響

<!--
講者備註：請學生在講義 B 畫兩張箭頭圖。C# 的 ref 參數 Java 沒有，不必提太多。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ArrayReference.java｜第 5 段</div>

## `==` 還是內容比較？（第二週的老問題）

```java
int[] state = {8, 2, 1};
int[] same  = {8, 2, 1};

state == same                 // false：兩個不同物件
Arrays.equals(state, same)    // true ：內容相同
```

| 型態 | 比參考 | 比內容 |
|---|---|---|
| `String` | `==` | `equals` |
| 陣列 | `==` | `Arrays.equals` |
| `Integer` | `==`（陷阱！稍後） | `equals` |

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ScriptArgs.java</div>

## C｜`main(String[] args)`：終於可以解釋了

```java
public static void main(String[] args)
```

```powershell
java -cp out ScriptArgs 1 1 2
```

```text
args.length = 3
args = [1, 1, 2]
args[0] + args[0] = 11      ← 字串串接，不是 2
script = [1, 1, 2], sum = 4
```

- `args` 是字串陣列；C# `int.Parse` → Java `Integer.parseInt`
- 輸入 `abc` 會得到 `NumberFormatException`：本週約定只輸入整數

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ScriptArgs.java、code/starter/MissionLog.java</div>

## 在 IDE 裡傳參數

| 工具 | 設定位置 |
|---|---|
| 命令列 | `java -cp out MissionLog 3 9 0` |
| VS Code | `launch.json` 的 `"args": "3 9 0"`，或 Run 面板「Run with Arguments」 |
| IntelliJ IDEA | Run ▸ Edit Configurations ▸ Program arguments：`3 9 0` |

為什麼用參數，不再用 Scanner 一個一個輸入？

- **同一份腳本可以重跑**：改完程式，再跑一次 `1 1 2` 比對結果
- 把「測試輸入」也變成資料，這就是陣列的用處

---

<!-- _class: by-claude -->

## D｜陣列的限制：長度要先決定

任務日誌要記幾步？**事先不知道。**

```java
int[] commandLog = new int[100];  // 猜一個上限
int count = 0;                    // 另外記用了幾格
commandLog[count] = command;
count++;
```

- 猜太小 → 越界；猜太大 → 浪費，而且 `length` 不等於實際筆數
- 每次都要同時維護 `count`，忘了就出錯

→ 需要**會自己長大**的容器：`ArrayList`（Deitel 7.16）

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜開頭 add／get／set</div>

## ArrayList：C# `List<T>` 的 Java 版

| 動作 | C# `List<int>` | Java `ArrayList<Integer>` |
|---|---|---|
| 建立 | `new List<int>()` | `new ArrayList<>()` |
| 新增 | `Add(x)` | `add(x)` |
| 讀取 | `log[i]` | `log.get(i)` |
| 修改 | `log[i] = x` | `log.set(i, x)` |
| 筆數 | `Count` | `size()` |
| 是否包含 | `Contains(x)` | `contains(x)` |
| 位置 | `IndexOf(x)` | `indexOf(x)` |

`import java.util.ArrayList;`，印出時直接 `println(log)` 就看得到 `[1, 1, 2]`

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜開頭（自動裝箱）</div>

## 為什麼是 `Integer` 不是 `int`？

```java
ArrayList<int> log;       // 編譯錯誤：泛型參數只能是參考型態
ArrayList<Integer> log = new ArrayList<>();
log.add(1);               // 自動裝箱：int → Integer
int first = log.get(0);   // 自動拆箱：Integer → int
```

| 基本型態 | 包裝類別 |
|---|---|
| `int` | `Integer` |
| `double` | `Double` |
| `boolean` | `Boolean` |
| `char` | `Character` |

C# 的 `List<int>` 可以直接放 `int`，這是兩種語言真正不同的地方（Deitel 16.3–16.4）

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜Collections 段</div>

## Collections：整個集合的現成方法

```java
ArrayList<Integer> energy = new ArrayList<>(Arrays.asList(14, 8, 20, 6));

Collections.min(energy)           // 6
Collections.max(energy)           // 20
Collections.frequency(energy, 8)  // 1
Collections.sort(energy);         // [6, 8, 14, 20]
```

- `Arrays` 處理陣列；`Collections` 處理集合（`List`、`Set`…）
- `Arrays.asList(...)`：建立固定大小的清單（可改元素）；外層 `new ArrayList<>(...)` 建立可增刪的副本

**能用現成方法，就不要自己寫找最小值的迴圈**

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜List&lt;String&gt; results</div>

## Collections Framework 地圖（只看一眼）

| 介面 | 特性 | 常用實作 | 本週？ |
|---|---|---|---|
| `List` | 有順序、可重複、用索引 | `ArrayList` | ✅ 主角 |
| `Set` | 不重複 | `HashSet` | 延伸 |
| `Map` | 鍵 → 值 | `HashMap` | 延伸 |
| `Queue` | 先進先出 | `ArrayDeque` | 不教 |

```java
List<String> results = new ArrayList<>();   // 左邊寫介面、右邊寫實作
```

為什麼左邊寫 `List`？第 9 週「介面」再說，現在先認得這種寫法

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜陷阱 1</div>

## 陷阱 1：`remove(0)` 刪的是哪一個？

```java
ArrayList<Integer> a = new ArrayList<>(Arrays.asList(5, 0, 2));
a.remove(0);                    // [0, 2]   ← 刪「索引 0」
a = new ArrayList<>(Arrays.asList(5, 0, 2));
a.remove(Integer.valueOf(0));   // [5, 2]   ← 刪「值 0」
```

- `remove(int index)` 與 `remove(Object o)` 是兩個不同的方法（過載，第 10 週）
- 傳 `int` 時 Java 選 `remove(int)`，**不會**自動裝箱
- `ArrayList<String>` 沒有這個問題；`ArrayList<Integer>` 一定要小心

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜陷阱 2</div>

## 陷阱 2：`Integer` 用 `==`

```java
Integer small1 = 100, small2 = 100;
Integer big1 = 1000, big2 = 1000;

small1 == small2   // true   ← -128..127 有快取，剛好同一物件
big1 == big2       // false  ← 不同物件
big1.equals(big2)  // true
```

- 測試資料剛好都很小時，錯誤的 `==` 也會**通過測試**
- 規則：**包裝類別一律用 `equals`**；或先拆箱成 `int` 再用 `==`

<!--
講者備註：任務的能源值 0..20 都在快取範圍內，所以用 == 寫錯也看不出來。這正是「測試通過≠正確」的例子。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/examples/ListBasics.java｜陷阱 3</div>

## 陷阱 3：邊走訪邊刪除

```java
// 想刪掉所有不是 "OK" 的步驟
List: [OK, FULL, UNKNOWN, OK]
for (int i = 0; i < steps.size(); i++) {
    if (!steps.get(i).equals("OK")) steps.remove(i);
}
// 結果：[OK, UNKNOWN, OK]   ← UNKNOWN 沒刪掉！
```

i=1 刪掉 FULL → UNKNOWN 往前補到索引 1 → 下一輪 i=2，**跳過了它**

修法：**從後往前**走（`i = size() - 1; i >= 0; i--`）

（改用加強版 for 邊走邊刪，通常會丟 `ConcurrentModificationException`）

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java｜main</div>

## E｜任務日誌 `MissionLog`：資料怎麼放？

```java
int[] state = {20, 0, 0};                  // ENERGY=0, SAMPLES=1, ACTIONS=2
ArrayList<Integer> commandLog = new ArrayList<>();
ArrayList<Integer> energyLog  = new ArrayList<>();
ArrayList<String>  resultLog  = new ArrayList<>();
```

| 步驟 i | commandLog | resultLog | energyLog |
|---|---|---|---|
| 0 | 1 | OK | 14 |
| 1 | 1 | OK | 8 |
| 2 | 2 | OK | 6 |

**三條平行清單**：同一個索引 = 同一步。新增時三條要一起加，否則就對不齊

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java</div>

## 程式結構（行動規則已提供）

| 方法 | 用到的觀念 | 誰寫 |
|---|---|---|
| `applyCommand(int[] state, int command)` | 第三週規則，改 state 元素 | 已提供 |
| `runMission(...)` 迴圈與結束判斷 | 加強版 for、break | 已提供 |
| `toScript(String[] args)` | 建立陣列、`parseInt` | **TODO 1**（跟做） |
| `runMission` 內寫入日誌 | `add` | **TODO 2**（跟做） |
| `printReplay(...)` | 索引 for、`get`、`size` | **TODO 3**（模仿） |
| `countCommands(...)` | `int[]` 計數、範圍檢查 | **TODO 4**（獨立） |
| `printSummary(...)` | `Collections.frequency`、`min` | **TODO 5**（獨立） |

結束判斷與第三週相同：成功 > 耗盡 > 返回；腳本用完但未結束 → `UNFINISHED`

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java｜toScript、runMission</div>

## TODO 1–2：跟做

```java
static int[] toScript(String[] args) {
    int[] script = new int[args.length];
    for (int i = 0; i < args.length; i++) {
        script[i] = Integer.parseInt(args[i]);
    }
    return script;
}
```

```java
String result = applyCommand(state, command);
commandLog.add(command);
energyLog.add(state[ENERGY]);   // 執行「後」的能源
resultLog.add(result);
```

被拒絕的指令**也要記**：回放要看得到「哪一步失敗」

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java｜printReplay、countCommands、printSummary</div>

## TODO 3–5：預期輸出

```text
> java -cp out MissionLog 3 9 0
=== replay ===
step 1: command=3, result=ALREADY_FULL, energy=20
step 2: command=9, result=UNKNOWN_COMMAND, energy=20
step 3: command=0, result=RETURN, energy=20
counts[0..3]=[1, 0, 0, 1]
steps=3, ok=0, rejected=2, ignored=0
lowest energy=20
final energy=20, samples=0, actionCount=0 -> RETURNED
```

- `counts`：指令 9 不在 0–3，**不計數、也不能讓程式當掉**
- `rejected`：不是 OK、也不是 RETURN 的步數
- `ignored`：任務結束後，腳本裡沒執行到的指令數

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java（教師：code/teacher/MissionLogCheck.java）</div>

## 必做測試（每列重跑一次）

| 參數 | 最終 E／S／A | 結果 | 檢查重點 |
|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | 預設腳本 1 1 2 |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | 和上週相同 |
| `3 9 0` | 20／0／0 | RETURNED | 拒絕也記錄；9 不越界 |
| `1 1 1 1` | 2／6／3 | SUCCESS | `ignored=1` |
| `2 1` | 12／3／2 | UNFINISHED | 腳本用完 |

E／S／A = energy／samples／actionCount

---

<!-- _class: by-claude -->

## F｜Prompt 設計：先把「資料需求」說清楚

不好的 Prompt：「幫我存探測車的資料」

描述資料結構需求的七個欄位：

1. **存什麼**：每筆資料包含哪些欄位？型態？
2. **幾筆**：固定？有上限？會一直增加？
3. **順序**：重要嗎？
4. **重複**：允許嗎？
5. **怎麼找**：依位置？依某個鍵？
6. **要做什麼**：新增、走訪、計數、刪除、排序？
7. **限制**：Java 版本、本週不可用自訂類別／Lambda／Stream

---

<!-- _class: by-claude -->

## 三個需求、三個模擬 AI 建議

| 需求 | 模擬 AI 建議 | 你的判斷？ |
|---|---|---|
| 統計指令 0–3 各用幾次 | `HashMap<Integer, Integer>` | |
| 記錄每一步歷程 | `int[] log = new int[100]` | |
| 記錄每一步歷程 | 一條 `ArrayList<String>`，每筆存 `"1,OK,14"` | |

- 每一格都要寫：**可行嗎？代價是什麼？你會怎麼改 Prompt？**
- 這些是**教師設計的模擬建議**，不是真實對話；用真實 AI 時照實記錄

<!--
講者備註：參考判斷——HashMap 可行但鍵是 0..3 連續小整數，int[4] 更簡單；int[100] 有上限與 count 問題；
字串合併失去型態，取能源要 split+parseInt，且無法用 Collections.min。好的 Prompt 應寫出第 2、5、6、7 項。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：在 MissionLog.java 的副本上修改</div>

## AI 除錯三選一：都能通過 `1 1 2`

| # | 模擬 AI 修改 | 說法 |
|---|---|---|
| 1 | `countCommands` 拿掉範圍檢查，只寫 `counts[command]++` | 「指令只有 0–3，檢查多餘」 |
| 2 | `printReplay` 改成 `i <= commandLog.size()` | 「這樣才會印到最後一步」 |
| 3 | 新增 `removeRejected`，從前往後刪掉被拒絕的步驟 | 「回放時只看有效步驟比較清楚」 |

1. 先**預測**哪個輸入會出錯 → 2. 在副本重現 → 3. 最小修正 → 4. 重跑必做測試

**預設腳本通過，不代表程式正確**：反例通常在邊界與「不該出現的輸入」

---

<!-- _class: by-claude -->

## G｜課末檢核

1. `int[] a = {1, 2}; int[] b = a; b[0] = 9;` 之後 `a[0]` 是多少？為什麼？
2. `ArrayList<Integer> x` 內容 `[3, 9, 0]`，`x.remove(0)` 之後是什麼？
3. 為什麼 `counts` 用陣列、日誌用 `ArrayList`？
4. 兩個 `Integer` 比內容要用什麼？

<!--
答案：9（同一物件）；[9, 0]（刪索引 0）；指令範圍固定 0..3 vs 步數不固定；equals。
-->

---

<!-- _class: by-claude -->

<div class="ex">範例：code/starter/MissionLog.java｜runMission</div>

## 下週預告：三條清單真的好嗎？

```java
commandLog.add(command);
energyLog.add(state[ENERGY]);
// 忘了 resultLog.add(result);  → 之後每一步都對錯行
```

```java
state[1]++;   // 1 是 SAMPLES？還是 ENERGY？
```

- 「一步」其實是**一筆紀錄**：指令＋結果＋能源
- 「探測車狀態」其實是**一個東西**：能源＋樣本＋行動次數

→ 第 5 週：自己設計類別，`ArrayList<MissionStep>`

---

<!-- _class: by-claude -->

## H｜保存與推送（最後 15 分鐘）

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md
   ├─ AI_USAGE.md
   └─ java-04/
      ├─ README.md
      └─ src/  ArrayBasics.java  ArrayReference.java  ScriptArgs.java
               ListBasics.java   MissionLog.java
```

- [ ] 重新編譯；不交 `out/`、`.class`
- [ ] commit 前綴：`[manual]`、`[AI]`、`[fix]`、`[doc]`
- [ ] Push → 到 GitHub 確認 → 另一個路徑全新 clone，重跑 `3 9 0`

---

<!-- _class: by-claude -->

## 作業與 AI 使用紀錄

- 作業說明：`week04_作業說明_claude.md`
- 必繳：完成的 `MissionLog.java`、五條測試結果、決策表、AI 除錯紀錄（三筆證據）
- `AI_USAGE.md`：使用目的、Prompt 摘要、採納／未採納原因、人工驗證方式、最終修改
- 沒有使用 AI 也要交，寫「本次未使用 AI」

> 你可以用 AI，但你要能逐行解釋你交的每一行程式碼。
