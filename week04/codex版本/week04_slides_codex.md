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
---
<!-- _class: lead -->
## 第四週｜火星探測車任務日誌

陣列・ArrayList・Collections Framework 入門

Prompt 設計：描述資料結構需求

資訊管理學系二年級｜胡碩誠

<!-- 教師提示：延續第三週；學生先想到需求，再看到容器。 -->

---
## 相同終點，能分辨路線嗎？

| 指令順序 | 最終能源 | 樣本 | 成功行動 |
|---|---:|---:|---:|
| 1 → 1 → 2 | 6 | 5 | 3 |
| 2 → 1 → 1 | 6 | 5 | 3 |

只保存最後狀態，無法回放每一步。

今天新增「逐筆保存」與「回放、統計」。

<!-- 教師提示：A診斷。要求學生解釋不同順序，不只回答三個數字。 -->

---
## 今天的成果

- 用陣列保存固定腳本與四種指令計數。
- 用 ArrayList 保存每一步的指令、能源、結果。
- 回放日誌，用 Collections 產生摘要。
- 寫出資料需求，用反例驗證 AI 建議。
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

| 程式 | 練習 |
|---|---|
| ArrayBasics | B：陣列、兩種 for |
| CommandCounter | B：合法索引與計數 |
| ArrayReference | C：改元素、改參考 |
| ListBasics | D：清單操作與 Integer |
| CollectionsDemo | F：摘要與排序 |
| MissionLog | E、F：五個 TODO |

ScriptArgs 是課後選讀；主作業修改固定腳本。

<!-- 教師提示：逐一開對應程式再示範，不要求一次閱讀所有檔案。 -->

---
## B｜陣列：保存固定腳本

範例：ArrayBasics.java

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

範例：ArrayBasics.java

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

範例：CommandCounter.java

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

範例：ArrayReference.java

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
## D｜逐步增加的日誌

腳本長度固定，但實際執行步數會改變。

- {1,1,1,1} 達標後，最後一筆不執行。
- 未來改回互動輸入，事先不知道會操作幾步。
- ArrayList 用 add 逐筆增加，不需管理有效格數。

陣列也能存歷程，但需處理容量與有效筆數。

<!-- 教師提示：避免宣稱固定腳本無法使用陣列，或ArrayList有無限容量。 -->

---
<!-- _class: small -->
## ArrayList 與 C# 清單

範例：ListBasics.java

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
## Collections Framework 概觀

| 類型 | 資料需求 | 本週深度 |
|---|---|---|
| List | 有順序、可重複 | 使用 ArrayList |
| Set | 不重複 | 辨識用途 |
| Map | 鍵對值，鍵不重複 | 辨識用途 |

Collection：集合介面；Collections：工具類別。
Map 屬框架，但不繼承 Collection。

<!-- 教師提示：Deitel16.2、16.5；介面實作與多型在後續，不把HashMap當必做。 -->

---
<!-- _class: small -->
## E｜同一步的三條日誌

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

範例：CollectionsDemo.java

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

| 初始能源/樣本；腳本 | 結果 | 日誌 |
|---|---|---|
| 5/0；{1,3,0} | 10/0/1 RETURNED | 3筆 |
| 2/4；{2} | 0/5/1 SUCCESS | 1筆 |
| 0/0；{3,1} | EXHAUSTED | 空 |
| 20/5；{1} | SUCCESS | 空 |
| 20/0；{} | UNFINISHED | 空 |

填「教師觀察」；自行跑過才寫「本人複驗」。

<!-- 教師提示：FixedScriptMission是教師工具。空日誌最低能源N/A；第一例最低5。 -->

---
## Prompt｜描述資料需求

寫成可直接提問的一段文字：

- 欄位與型態：指令 int、能源 int、結果 String。
- 筆數：逐步增加；目前由固定腳本驅動。
- 順序與重複：保留執行順序，允許重複行動。
- 操作：新增、回放、計數、最低能源。
- 技術與驗證：本週容器、邊界、預期結果。

不要只寫「幫我用 ArrayList」。

<!-- 教師提示：F2學生先寫再比對需求。完整教師Prompt在teacher_notes；不是宣稱真實AI對話。 -->

---
## 判讀兩個模擬建議

| 模擬建議 | 要追問什麼？ |
|---|---|
| int[100] 就能保存全部歷程 | 有效筆數？滿了怎麼辦？ |
| 用 HashSet 保存指令來去重 | 重複指令是否必要？順序呢？ |

陣列可保存有限歷程，但需容量與有效筆數。
日誌需要順序與重複，List 適合此需求。

依需求判斷，不因 API 名稱看起來進階就採納。

<!-- 教師提示：Set概觀足以判讀，不需實作HashSet；不能說ArrayList無資源上限。 -->

---
## AI 反例｜二選一

| 模擬修改 | 可用反例 | 最小修正 |
|---|---|---|
| 刪 counts 範圍檢查 | {3,9,0} | 恢復合法索引檢查 |
| 回放 < 改成 <= | {0} | 恢復 < |

先在副本重現，記錄預期與實際錯誤。
修正後重跑反例、{1,1,2}、{3,9,0}。

可以只用模擬建議；紀錄必須依本人操作。

<!-- 教師提示：F3。第一項在counts[9]越界；第二項get(1)越界。不要只繳AI說正確。 -->

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

- 恢復 {1,1,2}，重新編譯執行。
- LAB_REPORT、四欄 AI_USAGE、專案 README。
- commit → push → 核對 GitHub main 最新檔案。
- 新位置 clone，重新編譯與執行預設路線。

交 repo 網址、作業目錄與最終 commit。
未完成也保存並註明 WIP；取回未做寫待補。

<!-- 教師提示：最後15分鐘。push失敗先協助，仍無法解決保存完整repo至不受還原位置。 -->

---
## 選讀與參考章節

- ScriptArgs：main 的 String[] args 與 parseInt。
- 自訂路線：比較達標能源與成功行動次數。
- 只顯示成功步驟：跳過顯示，保留原日誌。

Deitel 11e：§7.2–7.4、7.7–7.9、7.16。
§16.2–16.5、16.6–16.7 選讀；§7.14 延伸。

本週不要求自訂領域類別、Lambda、Stream、JUnit。

<!-- 教師提示：原創情境；官方語意來源與驗證限制在教師筆記及verification。 -->
