---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: 第四週｜火星探測車任務日誌
author: 胡碩誠
description: 115 學年度第 1 學期｜物件導向程式設計
footer: '物件導向程式設計｜Week 04'
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
  section .slide-ref {
    position: absolute;
    top: 12px;
    left: 64px;
    right: 64px;
    margin: 0;
    font-size: 18px;
    line-height: 1.2;
    color: #62748a;
  }
  section .slide-ref code { font-size: inherit; background: none; padding: 0; }
  section.lead .slide-ref { color: #c9dce8; }
---
<!-- _class: lead -->
## 第四週｜火星探測車任務日誌

陣列・ArrayList・Collections Framework 入門

Prompt 設計：描述資料結構需求

資訊管理學系二年級｜胡碩誠

<!-- 教師提示：延續第三週；學生先想到需求，再看到容器。 -->

---
## 相同終點，能分辨路線嗎？

<p class="slide-ref" data-slide="2">對應程式：<code>teacher/FixedScriptMission.java</code></p>

| 指令順序 | 最終能源 | 樣本 | 成功行動 |
|---|---:|---:|---:|
| 1 → 1 → 2 | 6 | 5 | 3 |
| 2 → 1 → 1 | 6 | 5 | 3 |

只保存最後狀態，無法回放每一步。

今天新增「逐筆保存」與「回放、統計」。

<!-- 教師提示：A診斷。要求學生解釋不同順序，不只回答三個數字。 -->

---
## 今天的成果

<p class="slide-ref" data-slide="3">對應程式：<code>starter/MissionLog.java</code></p>

完成能回放每一步的 MissionLog，並說明資料結構選擇的理由。

- 用陣列保存固定腳本與四種指令計數。
- 用 ArrayList 保存每一步的指令、能源、結果。
- 回放日誌，用 Collections 產生摘要。
- 把日誌需求寫成 Prompt，據此判讀 AI 建議，再用反例驗證修改。
- 保存、push，取回後重新編譯。

<!-- 教師提示：本週保留具名狀態，不要求設計 Rover 或 MissionStep 類別。 -->

---
## 150 分鐘安排

| 第 1 節 | 第 2 節 | 第 3 節 |
|---|---|---|
| A 取回/診斷 10 | D ArrayList 15 | F 摘要/Prompt/AI 30 |
| B 陣列/計數 30 | E 日誌/回放/計數 35 | G 檢核 5 |
| C 參考 10 | | H 保存 15 |

休息另計；實際日期由教師公告。
未完成測試可課後補齊，保存時間要保留。

<!-- 教師提示：時間為備課估算，尚未經學生試做。 -->

---
<!-- _class: small -->
## A｜取回與專案準備

<p class="slide-ref" data-slide="5">對應程式：<code>starter/MissionLog.java</code></p>

1. 無 repo 時 clone；已有副本先保存再 Fetch/Pull。
2. 建立 YYYY_MM_DD/java-04/src。
3. 放五個核心範例與 starter 的 MissionLog。
4. LAB_REPORT、AI_USAGE 放日期下。

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
```

JDK 不鎖定版本；記錄 java / javac 實際版本。

<!-- 教師提示：編譯在 java-04，Desktop 管理學期 repo。不要混入 teacher 同名類別。 -->

---
<!-- _class: small -->
## 範例與講義對照

| 教材來源檔案 | 講義練習 |
|---|---|
| `examples/ArrayBasics.java` | B：陣列、兩種 for |
| `examples/CommandCounter.java` | B：合法索引與計數 |
| `examples/ArrayReference.java` | C：改元素、改參考 |
| `examples/ListBasics.java` | D：清單操作與 Integer |
| `examples/CollectionsDemo.java` | F：摘要與排序 |
| `starter/MissionLog.java` | E、F：五個 TODO |

`examples/ScriptArgs.java` 是課後選讀；主作業修改固定腳本。
路徑以本教材目錄為準；學生複製後，在自己的 src 找同名檔。

<!-- 教師提示：逐一開對應程式再示範，不要求一次閱讀所有檔案。 -->

---
## B｜陣列：保存固定腳本

<p class="slide-ref" data-slide="7">對應程式：<code>examples/ArrayBasics.java</code></p>

```java
int[] script = {1, 1, 2};
int[] counts = new int[4];
String[] labels = new String[2];
```

- 同一陣列的元素型態一致，長度建立後固定。
- Java `int[]` 對應熟悉的 C# 陣列宣告。
- 指令的順序與重複都有意義。

<!-- 教師提示：Deitel 7.2–7.3；JLS https://docs.oracle.com/javase/specs/jls/se22/html/jls-10.html。 -->

---
## 長度、索引與預設值

<p class="slide-ref" data-slide="8">對應程式：<code>examples/ArrayBasics.java</code></p>

```java
script.length       // 3，沒有括號
script[0]           // 第一筆：1
script[2]           // 最後一筆：2
```

| 建立 | 元素預設值 |
|---|---|
| new int[4] | 0、0、0、0 |
| new String[2] | null、null |

合法索引：**0 ≤ i < length**。

<!-- 教師提示：new陣列元素的預設值不等於未初始化區域變數也有預設值。 -->

---
## 列印內容與越界

<p class="slide-ref" data-slide="9">對應程式：<code>examples/ArrayBasics.java</code></p>

```java
System.out.println(Arrays.toString(script));
// [1, 1, 2]
```

`Arrays` 是陣列工具，先 import java.util.Arrays。

副本試做：`script[script.length]`

預測：編譯錯誤，還是執行時錯誤？
測完立即恢復。

<!-- 教師提示：執行時 ArrayIndexOutOfBoundsException，索引3。不要要求固定錯誤文字或行號。 -->

---
## 需要步號：索引式 for

<p class="slide-ref" data-slide="10">對應程式：<code>examples/ArrayBasics.java</code></p>

```java
for (int i = 0; i < script.length; i++) {
    System.out.println("step " + (i + 1)
            + ": " + script[i]);
}
```

索引 i 從 0 開始；顯示給人的步號從 1 開始。

最後條件使用 `<`，不是 `<=`。

<!-- 教師提示：承接第三週 for，這次只多了索引存取。 -->

---
## 只讀每筆值：加強版 for

<p class="slide-ref" data-slide="11">對應程式：<code>examples/ArrayBasics.java</code></p>

```java
int total = 0;
for (int command : script) {
    total += command;
}
```

C# foreach 的 Java 對應寫法。

不需索引；加總是 4，**不是成功行動次數**。
把 command 改成 0，不會改掉原 int[] 元素。

<!-- 教師提示：Deitel 7.7。參考型態元素仍可透過參考修改物件；勿概括為不能改任何物件。 -->

---
## 固定四種指令的計數

<p class="slide-ref" data-slide="12">對應程式：<code>examples/CommandCounter.java</code></p>

```java
int[] counts = new int[4];
for (int command : script) {
    if (command >= 0 && command < counts.length) {
        counts[command]++;
    }
}
```

{3, 9, 0, 1, 1, -1} → 預測 counts？
9、-1 不進計數，避免非法索引。

<!-- 教師提示：答案[1,2,0,1]。計數是指令頻率，不是成功行動數。 -->

---
## C｜陣列參考：共同指向

<p class="slide-ref" data-slide="13">對應程式：<code>examples/ArrayReference.java</code></p>

```java
int[] energy = {20, 14, 8};
int[] alias = energy;
alias[0] = 19;
```

energy ──→ 同一個陣列 ←── alias

複製的是參考值，沒有複製陣列元素。
預測 energy 的第一格？

<!-- 教師提示：答案19；沿用第二週參考追蹤。這是文字示意，不把參考當可操作位址。 -->

---
## 改元素與重新指定參數

<p class="slide-ref" data-slide="14">對應程式：<code>examples/ArrayReference.java</code></p>

```java
static void changeFirst(int[] values) {
    values[0] = 18;
}
static void replaceLocal(int[] values) {
    values = new int[] {20, 20, 20};
}
```

Java **一律傳值**，陣列傳入參考值的副本。
改元素影響同一物件；重新指定只改區域參考。

<!-- 教師提示：Deitel 7.8–7.9；ArrayReference 輸出回主程式仍[18,14,8]。 -->

---
## 參考比較與內容比較

<p class="slide-ref" data-slide="15">對應程式：<code>examples/ArrayReference.java</code></p>

```java
int[] a = {18, 14, 8};
int[] b = {18, 14, 8};
System.out.println(a == b);             // false
System.out.println(Arrays.equals(a, b)); // true
```

第二週 String 內容比較用 equals。
本例 int[] 內容比較用 Arrays.equals。

<!-- 教師提示：Arrays.equals本例是一維int陣列比較；不提前引入多維deepEquals。 -->

---
<!-- _class: small -->
## 腳本是預定指令，日誌記錄實際過程

<p class="slide-ref" data-slide="16">對應程式：<code>starter/MissionLog.java</code></p>

例：從能源20、樣本0出發，腳本 `{1,1,1,1}` 代表四次探索。
每次取得2份樣本，第三次累積6份已達標，第四筆不讀取。

只保存「已讀指令」時，兩種方式都能記錄這三步：

| 保存方式 | 保存三步後的內容 | 如何知道實際筆數？ |
|---|---|---|
| 長度4的 int 陣列 | [1,1,1,0]，最後一格未使用 | 另記 used=3；length 仍為4 |
| ArrayList 清單 | [1,1,1] | size() 直接得到3 |

陣列末格的0是預設值，**不是讀取過返回指令**。

ArrayList 每記錄一筆就 **add**，**size()** 就是已存筆數。
本週用它簡化筆數管理；下一頁練習新增與讀取。

<!-- 教師提示：本頁先區分腳本與日誌，再說明選用ArrayList的便利。指令1是探索，每次耗6能源、取2樣本；三步後能源2、樣本6，已達標，第四筆不讀不記。表格是「只存指令」的兩種概念比較：陣列先建4格，按順序填入三個1，需另記used=3，不能把預設0當返回；清單從空開始，每讀一筆add一次，size為3。這不是要求新增另一個陣列版程式，正式MissionLog仍用三條日誌；已讀的拒絕、無效與返回也記錄。固定腳本可以用等長陣列搭配有效筆數，ArrayList省去自行維護有效格數，容量仍受記憶體限制。下一頁以ListBasics示範add/get/size；不宣稱陣列不能記錄歷程，也不把本頁的筆數3誤當任何腳本都固定三步。 -->

---
<!-- _class: small -->
## ArrayList 與 C# 清單

<p class="slide-ref" data-slide="17">對應程式：<code>examples/ListBasics.java</code></p>

| 動作 | C# List<int> | Java ArrayList<Integer> |
|---|---|---|
| 新增 | Add(x) | add(x) |
| 筆數 | Count | size() |
| 讀取 | list[i] | get(i) |
| 修改 | list[i]=x | set(i,x) |
| 刪索引 | RemoveAt(i) | remove(i) |

沒學過 C# List<T> 也可以直接練右欄。

<!-- 教師提示：ArrayList API：https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/ArrayList.html。 -->

---
## Integer 與裝箱／拆箱

<p class="slide-ref" data-slide="18">對應程式：<code>examples/ListBasics.java</code></p>

```java
ArrayList<Integer> commands = new ArrayList<>();
commands.add(1);             // int → Integer
int first = commands.get(0); // Integer → int
```

ArrayList 的型態參數須為參考型態。
`ArrayList<int>` 不能編譯。

本週學使用方式，泛型設計留到第十一週。

<!-- 教師提示：Deitel7.16、16.3–16.4。import java.util.ArrayList；不使用new Integer。 -->

---
<!-- _class: small -->
## set、remove 與 isEmpty

<p class="slide-ref" data-slide="19">對應程式：<code>examples/ListBasics.java</code></p>

```java
// 原清單：[1, 1, 2]
commands.set(1, 3);                 // [1, 3, 2]
commands.remove(0);                 // [3, 2]
commands.remove(Integer.valueOf(3)); // [2]
commands.isEmpty();                 // false
```

`set` 不增加筆數；`remove` 會移動後方元素。
傳 int 刪索引，傳 Integer 刪第一個匹配值。

<!-- 教師提示：短示範即可，不在主線刪日誌；過載規則留第十週。 -->

---
<!-- _class: small -->
## 依資料需求分辨 List、Set、Map

<p class="slide-ref" data-slide="20">對應程式：<code>examples/CollectionsDemo.java</code></p>

Collections Framework：Java 處理多筆資料的一組介面與類別。

| 類型 | 資料特性 | 探測車例子 | 實作類別 |
|---|---|---|---|
| List（清單） | 有順序、可重複，可用索引讀取 | 依序記錄指令 1、1、2 | ArrayList |
| Set（集合） | 元素不重複；順序依實作而定 | 只記出現過的指令種類 1、2 | HashSet |
| Map（對照表） | 鍵不重複，依鍵查找值 | 代碼 → 名稱，如 1 → 探索 | HashMap |

- **Collection**：List、Set 的共同上層介面；Map 屬框架，但不繼承它。
- **Collections**：工具類別，提供 min、frequency、sort 等方法。

本週日誌用 **ArrayList**：保留執行順序與重複行動；Set、Map 先辨識用途。

<!-- 教師提示：Deitel16.2、16.5。本頁目的為依資料需求辨認容器用途，並分清框架名稱、介面與工具類別。List、Set、Map是介面，右欄ArrayList、HashSet、HashMap是實作類別，先辨識角色，介面實作與多型留後續。List的第0、1、2筆能保留兩次探索；Set只回答出現過哪些指令，不能當完整路線，HashSet不保證執行順序。Map例子以指令代碼當鍵、動作名稱當值，同鍵只能對應一個值，不同鍵的值可以相同。Collection是List/Set的共同上層介面；Map也屬Collections Framework但不繼承Collection。Collections則是工具類別，後面以Collections.min/frequency/sort示範。本週只實作ArrayList，不新增HashSet/HashMap或介面繼承作業。 -->

---
<!-- _class: small -->
## E｜同一步的三條日誌

<p class="slide-ref" data-slide="21">對應程式：<code>starter/MissionLog.java</code></p>

```java
ArrayList<Integer> commandLog = new ArrayList<>();
ArrayList<Integer> energyLog = new ArrayList<>();
ArrayList<String> resultLog = new ArrayList<>();
```

| 索引 | 指令 | 執行後能源 | 結果 |
|---|---:|---:|---|
| 0 | 1 | 14 | OK |
| 1 | 1 | 8 | OK |
| 2 | 2 | 6 | OK |

三條清單的同一索引，代表同一步。

<!-- 教師提示：目前狀態仍為具名變數；三條清單是第五週物件設計的過渡。 -->

---
## 主程式與停止條件

<p class="slide-ref" data-slide="22">對應程式：<code>starter/MissionLog.java</code></p>

```java
int[] script = {1, 1, 2};
runMission(script, 20, 0);
```

教師提供行動規則與迴圈骨架。

先檢查：尚未返回、能源 > 0、樣本 < 5。
腳本用完但任務未停止 → UNFINISHED。

初始已耗盡或已達標，不執行第一筆。

<!-- 教師提示：SUCCESS優先於EXHAUSTED，再RETURNED，最後UNFINISHED。使用一般for停止條件，不要求新增state陣列。 -->

---
## 五個 TODO 的操作順序

<p class="slide-ref" data-slide="23">對應程式：<code>starter/MissionLog.java</code>、<code>teacher/MissionLog.java</code></p>

| 課堂順序 | TODO | 完成內容 |
|---|---|---|
| 1 | 2 | 同步新增三欄 |
| 2 | 3 | 回放每筆 |
| 3 | 1 | 四種指令計數 |
| 4 | 4 | 摘要筆數與結果 |
| 5 | 5 | 空清單與最低能源 |

E 做 2→3→1；F1 做 4→5。
每階段編譯，未完成摘要仍會顯示占位提示。

<!-- 教師提示：對照starter，逐題支持，不要直接複製完整teacher給學生。 -->

---
## TODO 2｜每一步都保存

<p class="slide-ref" data-slide="24">對應程式：<code>starter/MissionLog.java</code></p>

switch 執行後，同時新增：

```java
commandLog.add(command);
energyLog.add(energy);
resultLog.add(result);
```

能源是行動**執行後**的值。
拒絕、無效指令、返回也必須記錄。
停止後沒讀取的指令不記錄。

<!-- 教師提示：跟做；用3,9,0證明三筆日誌但成功行動0。 -->

---
<!-- _class: small -->
## TODO 3｜用索引回放

<p class="slide-ref" data-slide="25">對應程式：<code>starter/MissionLog.java</code></p>

```java
for (int i = 0; i < commandLog.size(); i++) {
    System.out.println("step " + (i + 1)
        + ": command=" + commandLog.get(i)
        + ", result=" + resultLog.get(i)
        + ", energy=" + energyLog.get(i));
}
```

這裡需要索引，才能對齊三個欄位。

<!-- 教師提示：由學生仿寫；最後有效索引size()-1。 -->

---
## 指令筆數與成功行動不同

<p class="slide-ref" data-slide="26">對應程式：<code>starter/MissionLog.java</code></p>

{3,9,0} 從能源 20、樣本 0 開始：

| 步 | 指令 | 結果 | 能源 |
|---|---:|---|---:|
| 1 | 3 | ALREADY_FULL | 20 |
| 2 | 9 | UNKNOWN_COMMAND | 20 |
| 3 | 0 | RETURN | 20 |

steps=3；actionCount=0；counts=[1,0,0,1]。
返回不算拒絕；無效指令不進 counts。

<!-- 教師提示：E追蹤題。counts中的3雖被拒絕仍計入。 -->

---
<!-- _class: small -->
## 六條必做路線

<p class="slide-ref" data-slide="27">對應程式：<code>starter/MissionLog.java</code></p>

| 腳本 | 最終 energy/samples/actions | 結果 |
|---|---|---|
| {0} | 20/0/0 | RETURNED |
| {1,1,2} | 6/5/3 | SUCCESS |
| 五次2 | 10/5/5 | SUCCESS |
| {3,9,0} | 20/0/0 | RETURNED |
| {1,1,1,1} | 2/6/3 | SUCCESS，剩一筆未讀 |
| {2,1} | 12/3/2 | UNFINISHED |

改 main 的 script，每次重新編譯；繳交前恢復預設。

<!-- 教師提示：每筆回放、counts及摘要也要檢查；完整表在assignment。 -->

---
## F｜Collections：整理整份清單

<p class="slide-ref" data-slide="28">對應程式：<code>examples/CollectionsDemo.java</code>、<code>starter/MissionLog.java</code></p>

```java
Collections.min(energyLog);
Collections.frequency(resultLog, "OK");
```

min：最小值；frequency：相同值的筆數。
本週不自行寫比較器或 Stream。

工具類別需 import java.util.Collections。

<!-- 教師提示：Deitel16.7；官方：https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/Collections.html。 -->

---
<!-- _class: small -->
## TODO 4｜定義摘要

<p class="slide-ref" data-slide="29">對應程式：<code>starter/MissionLog.java</code></p>

```java
int ok = Collections.frequency(resultLog, "OK");
int returned = Collections.frequency(resultLog, "RETURN");
int steps = resultLog.size();
int rejected = steps - ok - returned;
int ignored = scriptLength - steps;
```

{3,9,0} → steps=3、ok=0、rejected=2、ignored=0。
{1,1,1,1} → ignored=1，最後一筆未讀。

<!-- 教師提示：摘要必須基於完整日誌；不可為了顯示簡潔先刪資料。 -->

---
<!-- _class: small -->
## TODO 5｜空清單沒有最小值

<p class="slide-ref" data-slide="30">對應程式：<code>starter/MissionLog.java</code></p>

```java
if (energyLog.isEmpty()) {
    System.out.println("lowest energy=N/A");
} else {
    System.out.println("lowest energy="
        + Collections.min(energyLog));
}
```

最低能源只看每步結束後的值，不含初始能源。
空腳本、初始已停止，都會有空日誌。

<!-- 教師提示：不以0代替沒有資料；空清單直接min會丟例外。 -->

---
## 排序要保留原日誌順序

<p class="slide-ref" data-slide="31">對應程式：<code>examples/CollectionsDemo.java</code></p>

```java
ArrayList<Integer> sorted = new ArrayList<>();
for (int energy : energyLog) {
    sorted.add(energy);
}
Collections.sort(sorted);
```

原日誌：[14,8,20,6]；sorted：[6,8,14,20]。
單獨排序 energyLog，會破壞與其他兩欄的對應。

<!-- 教師提示：CollectionsDemo可執行；此處另建清單只存Integer值，不展開深複製。 -->

---
<!-- _class: small -->
## 教師邊界示範

<p class="slide-ref" data-slide="32">對應程式：<code>teacher/FixedScriptMission.java</code>、<code>teacher/MissionLogCheck.java</code></p>

終端機位於 `week04/codex版本`，先編譯，再執行：

```powershell
javac -encoding UTF-8 -d out-teacher teacher/*.java
java -cp out-teacher FixedScriptMission 5 0 1 3 0
```

參數：**初始能源、初始樣本、後續指令**；上例為探索 → 充電 → 返回。

| 初始能源/樣本；腳本 | 結果 | 日誌 |
|---|---|---|
| 5/0；{1,3,0} | 10/0/1 RETURNED | 3筆 |
| 2/4；{2} | 0/5/1 SUCCESS | 1筆 |
| 0/0；{3,1} | EXHAUSTED | 空 |
| 20/5；{1} | SUCCESS | 空 |
| 20/0；{} | UNFINISHED | 空 |

填「教師觀察」；自行跑過才寫「本人複驗」。

<!-- 教師提示：FixedScriptMission是教師工具，會呼叫同目錄MissionLog的runMission。表格每列以前兩個數字指定初始值，後接腳本；空腳本只傳20 0。編譯成功後才執行。空日誌最低能源N/A；第一例最低5。 -->

---
<!-- _class: small -->
## F2｜先說清需求，才能判斷 AI 建議

<p class="slide-ref" data-slide="33">對應程式：<code>starter/MissionLog.java</code></p>

目的：把剛才的日誌需求寫成 Prompt，作為判斷 AI 建議的依據。

| 要交代什麼？ | 本週日誌的需求 |
|---|---|
| 欄位與型態 | 每步的指令 int、行動後能源 int、結果 String |
| 筆數 | 逐筆新增；目前固定腳本有上限，未來可改互動輸入 |
| 順序與重複 | 保留順序與重複探索；拒絕、無效與返回也記錄 |
| 操作 | 新增、回放、指令計數、最低能源 |
| 技術與驗證 | 本週容器與已教語法；測試案例及預期結果 |

課堂產出：在講義 F2 寫一段完整 Prompt，下一頁用需求檢查建議。

<!-- 教師提示：先回顧第2頁：要回放路線，必須保存過程。現在讓學生把已實作的日誌轉述成可檢查的資料需求，確認理解為何選用容器，而不只會照寫add。學生先填需求表並自行組成一段Prompt，再進入第34頁判讀。技術限制沿用三條ArrayList、一般for、Collections，不自訂類別；驗證可用{3,9,0}與空日誌。完整參考Prompt在teacher_notes，待學生先寫後再展示。本段沿用原MissionLog，產出為Prompt與判斷表，真實AI服務可選。 -->

---
## F2｜用需求判讀兩個模擬 AI 建議

目的：依上一頁的需求，判斷建議適用的條件與代價。

| 模擬建議 | 要追問什麼？ |
|---|---|
| int[100] 就能保存全部歷程 | 有效筆數？滿了怎麼辦？ |
| 用 HashSet 保存指令來去重 | 重複指令是否必要？順序呢？ |

課堂產出：講義 F2 每項寫「採納、拒絕或補充條件」，並附理由。

用自己的 Prompt 檢查：筆數、順序與重複是否都符合需求？

<!-- 教師提示：承接第33頁學生自行寫的需求；這裡練習依需求判讀，而非背固定答案。陣列可保存有限歷程，但需容量、有效筆數與滿載處理；固定腳本也能使用等長陣列。日誌要保留重複行動及順序，HashSet去重並不適合回放。Set概觀足以判讀，不需實作HashSet，也不能說ArrayList無資源上限。 -->

---
## F3｜用反例驗證 AI 修改是否正確

<p class="slide-ref" data-slide="35">對應程式：<code>starter/MissionLog.java</code></p>

目的：實際執行，檢查修改後是否仍符合索引與回放規則。

| 模擬修改 | 可用反例 | 最小修正 |
|---|---|---|
| 刪 counts 範圍檢查 | {3,9,0} | 恢復合法索引檢查 |
| 回放 < 改成 <= | {0} | 恢復 < |

選一項，在副本依序做：預測 → 重現錯誤 → 最小修正 → 重測。

課堂產出：講義 F3 的修改前後證據；重測反例、{1,1,2}、{3,9,0}。

可以只用模擬建議；紀錄必須依本人操作。

<!-- 教師提示：與第34頁區別：第34頁用需求判斷資料結構建議，第35頁用執行證據驗證程式修改，無須採用上頁建議後重寫整份程式。第一項在counts[9]越界；第二項get(1)越界。完整紀錄可課後補，沿用F3表格，不新增報告。 -->

---
## G｜課末檢核與下週

1. length、size()、length() 分別用在哪裡？
2. 三筆日誌是否代表三次成功行動？
3. 三條清單漏加其中一筆會怎樣？

下週：把同一步的指令、能源與結果，放在一起。

以 MissionStep 的欄位、建構子與封裝維持一致。
本週先理解需求，不要求寫這個類別。

<!-- 教師提示：答案陣列/ArrayList/String；不必定；錯配或越界。第五週不是今天作業。 -->

---
## H｜保存、推送與繳交

<p class="slide-ref" data-slide="37">對應程式：<code>starter/MissionLog.java</code></p>

- 恢復 {1,1,2}，重新編譯執行。
- LAB_REPORT、四欄 AI_USAGE、專案 README。
- commit → push → 核對 GitHub main 最新檔案。
- 新位置 clone，重新編譯與執行預設路線。

交 repo 網址、作業目錄與最終 commit。
未完成也保存並註明 WIP；取回未做寫待補。

<!-- 教師提示：最後15分鐘。push失敗先協助，仍無法解決保存完整repo至不受還原位置。 -->

---
## 選讀與參考章節

<p class="slide-ref" data-slide="38">對應程式：<code>examples/ScriptArgs.java</code>、<code>starter/MissionLog.java</code></p>

- ScriptArgs：main 的 String[] args 與 parseInt。
- 自訂路線：比較達標能源與成功行動次數。
- 只顯示成功步驟：跳過顯示，保留原日誌。

Deitel 11e：§7.2–7.4、7.7–7.9、7.16。
§16.2–16.5、16.6–16.7 選讀；§7.14 延伸。

本週不要求自訂領域類別、Lambda、Stream、JUnit。

<!-- 教師提示：原創情境；官方語意來源與驗證限制在教師筆記及verification。 -->
