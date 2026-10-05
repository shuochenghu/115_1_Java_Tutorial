# 第二週教師筆記｜型態、參考與探測車儀表板

## 定位與時間

依根課綱第二週主題與跨週銜接設計，承接第一週 main、static 方法、參數／引數與回傳值。學生已有 C# WinForms 基礎，以語言遷移與可觀察錯誤為重點。教學目標 JDK 21；實際驗證環境與限制見 [VALIDATION.md](VALIDATION.md)。

| 節次 | 150 分鐘配置 | 對應 lab |
|---|---|---|
| 1 | 取回與診斷 10；型態 15；參考與追蹤 25 | 0、A、B |
| 2 | 運算與條件 25；儀表板 25 | C、D |
| 3 | AI 反例與重測 20；檢核 15；保存 15 | E、F、G |

這是備課估算，未經學生試做。若方法能力不足，先追蹤 `calculateBatteryPercent(7)`；刪減配料短題，不刪能源 5／6 邊界與關機保存。第三節的獨立練習為修正與換值驗證，不再要求從空白重寫。

第二週不要求分支、迴圈、Scanner、陣列、集合或自行設計類別。`String[] args` 僅沿用 main 骨架，不視為已教陣列。`new String` 是教師的參考差異演示，不當成建構子設計的先備要求。

## 課前與示範準備

- 學生只取得 starter、examples、lab、AI 範本；teacher 與本文件為核對解答使用。
- 同名 `RoverDashboard` 的 starter 與 teacher 分開編譯，不對整週遞迴全選 `.java`。
- 在每個資料夾個別編譯可用 `javac --release 21 -encoding UTF-8 -d out RoverDashboard.java`；學生複製到 src 後依 lab 指令。
- 規則為 20 格容量、探索 6 格；掃描耗 2 得 1、探索得 2、充電加 5 上限 20、目標至少 5 樣本在第三週實作。
- 先展示 energy=7 的儀表板輸出，再讓學生預測 5 與 6 的差異。

## Lab 0／A 解答：型態與遷移

診斷：7 是引數，energy 是方法的 int 參數，回傳值 35.0 是 double；return 不自行輸出，main 的 println 負責主控台顯示。這正是第一週從 WinForms 抽離畫面更新的同一分工。

| 資料 | 型態 | 理由 |
|---|---|---|
| 名稱 | String | 多個字元的文字；參考型態 |
| 區域 | char | 本題只用單一英文字母 |
| 能源 | int | 本題為整數格數 |
| 百分比 | double | 保留小數 |
| 是否鎖定 | boolean | 真偽 |

八種基本型態：byte、short、int、long、float、double、char、boolean。String 不是基本型態。整數 byte/short/int/long 為 8/16/32/64 位元有號；float/double 為 32/64 位元 IEEE 754 浮點。本週不考背範圍，也不聲稱 boolean 的記憶體大小固定。char 為 16 位元 UTF-16 code unit，不保證能容納每個完整圖像字元；以 'A' 避免超出範圍。

`bool` 應改 `boolean`，`string` 應改 `String`；`char 'A'` 與 `String "A"` 型態不同。L 表示 long 常值，F 表示 float 常值；一般小數常值預設 double。學生能辨識即可。

TypeReferenceDemo 前兩行：

```text
20 300 7 3000000000
3.5 35.0 A false
```

## Lab B 解答：值與參考

基本型態第一列 energy=7、backupEnergy=7；第二列 energy=6、backupEnergy=7。指定運算會複製當時的值，不建立自動同步關係。

參考題三列為：「赤砂號／未宣告」、「赤砂號／赤砂號」、「晨星號／赤砂號」。第二列複製參考值，兩個變數指向同一個 String；第三列重新指定 consoleName 指向另一個字串。原物件並未改名，logName 仍指向赤砂號。String 不可變，不能推論所有參考型態都不可變。箭頭是概念示意，不是可直接操作的實體位址。

`code == receivedCode` 為 false，`.equals` 為 true。前者檢查是否同一參考，後者呼叫 String 的內容比較。兩個相同字串常值可共用 interned 物件，所以 `==` 可能偶然得到 true；必須用刻意不同參考作反例，不能以常值測試替代理解。

**C# 遷移重點：C# string 的 `==` 運算子定義了內容相等比較；Java String 的 `==` 比較參考。** 本週使用非 null String，`.equals` 區分大小寫；null 僅提醒「沒有指向物件」，完整 null 防護留待後續。

TypeReferenceDemo 後四行：

```text
energy=6, backupEnergy=7
晨星號 / 赤砂號
false
true
```

## Lab C 解答：運算與條件

| 運算式 | 結果 | 原因 |
|---|---|---|
| `energy / 20 * 100.0` | 0.0 | 7/20 先做整數除法得 0 |
| `(double)(energy / 20) * 100` | 0.0 | 轉型時結果已成為 0 |
| `(double)energy / 20 * 100` | 35.0 | 第一個運算元先成為 double |
| `energy / 20.0 * 100` | 35.0 | 除法使用 double |
| `(int)7.9` | 7 | 向零截斷，不是四捨五入 |
| `"合計=" + 2 + 3` | 合計=23 | 左至右成為字串串接 |
| `"合計=" + (2+3)` | 合計=5 | 括號先算數值和 |

`double e=7;` 可自動擴大轉換；不要延伸成「所有整數轉浮點都精確」。`int e=7.9;` 不可直接編譯，明確指定的縮小型態轉換需理解資訊可能遺失。可口頭示範錯誤，但不把不能編譯的程式放進必編譯 examples。

`n` 依序 0→2→3→2；獨立 `++` 表示加 1，為下一週計次準備。`=` 指定運算、`==` 比較，不能互換。算術 `* / %` 優先於 `+ -`，比較優先於 `&&`，`&&` 優先於 `||`；教學生有歧義就用括號，不考整張優先序表。

- 有效能源：`energy >= 0 && energy <= 20`。
- 探索資格：`energy >= 6 && !locked`（已知能源有效的前提）。
- 無效能源：`energy < 0 || energy > 20`。
- Java 不接受 `0 <= energy <= 20`：前半會產生 boolean，不能接著與整數比較。
- short-circuit evaluation 題依序「不呼叫／false」、「不呼叫／true」、「呼叫／true」。完整三題輸出為 false、true、右側檢查已執行、true，共四行。
- 配料：`53 / 8 = 6` 份，`53 % 8 = 5` 餘量。

完整 OperatorDemo 預期：

```text
0.0
0.0
35.0
35.0
7
合計=23
合計=5
樣本=2
true
true
false
false
true
右側檢查已執行
true
配料份數=6，剩餘=5
```

## Lab D 解答：儀表板

完整程式在 [teacher/RoverDashboard.java](teacher/RoverDashboard.java)。五個 TODO：`energy / 6`、`energy % 6`、`energy >= 0 && energy <= 20`、`energy >= 6 && !locked`、`return energy / 20.0 * 100;`。

| energy | locked | 百分比 | 次數 | 餘量 | 有效 | 能探索 |
|---:|---|---:|---:|---:|---|---|
| 0 | false | 0.0 | 0 | 0 | true | false |
| 5 | false | 25.0 | 0 | 5 | true | false |
| 6 | false | 30.0 | 1 | 0 | true | true |
| 7 | false | 35.0 | 1 | 1 | true | true |
| 20 | false | 100.0 | 3 | 2 | true | true |
| 6 | true | 30.0 | 1 | 0 | true | false |

範圍獨立題 -1、0、20、21 的 valid 為 false、true、true、false。不要拿無效能源下的探索計算作功能驗收；第二週核心方法假設有效輸入，下一週先排除無效狀態再行動。

energy=7 預期四行與投影片一致：

```text
探測車：赤砂號；區域：A
能源：7；電量百分比：35.0
完整探索次數：1；剩餘能源：1
有效能源：true；能否探索：true
```

## Lab E／F 解答：反例與課末檢核

AI 情境是作者編寫的教學材料，非服務實測。錯誤式 `energy / 20 * 100.0` 在 7 得 0.0，雖回傳 double，不能恢復先前整數除法失去的比例。0 與 20 剛好在電池空／滿端點，錯誤式也得正確答案，因此加入中間值 7 才揭露問題。最小修正把 20 改 20.0，然後重跑六筆。

課末練習：

1. 13 格：65.0%、2 次、餘 1、true。
2. `7/20` 先得 int 0，再轉 double 是 0.0。
3. b 仍指向內容 A 的字串，a 重新指定到 B。
4. `(energy < 0 || energy > 20) || locked`。
5. return 把計算結果交給呼叫處；println 把值顯示到 Console。

核心驗收看學生能否口頭解釋「為何」並提供結果，而不只對照抄寫表格。採納 AI 零項建議也可，只要有驗證依據。未使用服務應據實記錄。

## 保存與評量

沿用課綱原評量比例，不另訂成績比重。必做為完整 TODO、六筆結果、參考追蹤、AI 判斷與 GitHub 保存證據；新位置 clone 重編譯可課後完成。名稱與區域可創作，不能自行改能源規則使測試失效。第三週以前不要求新行動、地圖或圖形介面。

同名類別兩版分開。starter 能編譯並輸出佔位值是刻意設計，不能因此宣稱需求通過。教師不替學生虛填 GitHub、IDE 或 AI 執行紀錄。

## 第三週能力交接

具體對照見 [handoff.md](handoff.md)。可直接使用 int/double/boolean/String、算術、比較、short-circuit evaluation、指定運算、累加、獨立 ++ 與既有 static 方法。不可假設已會 Java if、switch、迴圈或 Scanner。第二週 locked 僅作複合條件診斷，第三週預設未鎖定，不增加鎖定選單。

## 備課來源

- [JLS 21 §4.2 基本型態](https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.2)、[§4.3 參考型態](https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.3)：型態、String 與 null。
- [JLS 21 §5.1.2 擴大基本轉換](https://docs.oracle.com/javase/specs/jls/se21/html/jls-5.html#jls-5.1.2)、[§5.1.3 縮小基本轉換](https://docs.oracle.com/javase/specs/jls/se21/html/jls-5.html#jls-5.1.3)：轉型資訊損失。
- [JLS 21 §15.17.2 除法](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.17.2)、[§15.17.3 餘數](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.17.3)。
- [JLS 21 §15.21.3 參考相等](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.21.3)、[String.equals](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#equals(java.lang.Object))。
- [JLS 21 §15.23 &&](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.23)、[§15.24 ||](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.24)：short-circuit evaluation。
- [Microsoft：C# 字串相等](https://learn.microsoft.com/en-us/dotnet/csharp/language-reference/operators/equality-operators#string-equality)：string == 的內容比較遷移陷阱。
