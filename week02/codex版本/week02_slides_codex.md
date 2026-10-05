---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: 第二週｜火星探測車的型態與運算
author: 胡碩誠
description: 115 學年度第 1 學期｜物件導向程式設計
footer: '物件導向程式設計｜Week 02'
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

# 第二週｜火星探測車任務儀表板

C# → Java 型態・值與參考・運算子・AI 驗證

資訊管理學系二年級｜胡碩誠

<!-- 教師提示：學生已有 C# WinForms 經驗。讓學生先替探測車命名，不增加遊戲引擎、GUI 或隨機事件。 -->

---
## 今天交付什麼？

- 一個固定資料的探測車儀表板。
- 能解釋 **7 格能源為何是 35.0%**。
- 判斷可完整探索幾次、剩多少、是否被鎖定。
- 用實際執行反駁有問題的 AI 建議。

今天只計算與顯示；下週才操作探測車。

<!-- 教師提示：展示 teacher/RoverDashboard.java 的結果但先不發答案。今天不要求 if 或輸入。 -->

---
## 150 分鐘的行程

| 節次 | 活動 |
|---|---|
| 第一節 | 取回與診斷 10；型態 15；參考 15；追蹤 10 |
| 第二節 | 運算轉型 15；條件 10；儀表板 25 |
| 第三節 | 獨立練習與 AI 驗證 20；檢核 15；保存 15 |

每一題先預測，再執行，最後解釋差異。

<!-- 教師提示：時間為備課估算。診斷較慢時刪減短情境題，保留邊界資料與最後保存。 -->

---
## 從第一週帶來什麼？

```java
double percent = calculateBatteryPercent(7);
System.out.println(percent);
```

1. 這裡的引數是什麼？方法內用什麼參數接收？
2. `return` 和 `println` 有何差別？
3. WinForms 更新 Label 的工作，在這裡由誰負責？

<!-- 教師提示：答案為 7、energy；回傳交給呼叫處，println 才顯示。沿用第一週純方法和 UI/輸出分工。 -->

---
## 先取回學期 repo

- 沒有副本：Desktop **Clone repository**。
- 已有副本：先保存本機工作，**Fetch → 有更新才 Pull**。
- 建立 `實際日期/java-02/src`，沿用 `115_1_Java`。
- 複製 starter 與 examples；不在日期內再建立 repo。
- 實際上課日期由教師指定。

<!-- 教師提示：確認使用者選到原本 repo 和 main。編譯指令見 lab；GitHub 登入與作者設定沿用第一週。 -->

---
## 任務規則：先讀懂需求

電池容量 **20 格**；探索一次消耗 **6 格**。

```text
探測車：赤砂號；區域：A
能源：7；電量百分比：35.0
完整探索次數：1；剩餘能源：1
有效能源：true；能否探索：true
```

未鎖定且至少 6 格，才有探索資格。

<!-- 教師提示：共用規則另有掃描 2 格、充電 5 格及至少 5 樣本目標，下週逐步導入。這是遊戲設定。 -->

---
## 從 C# 經驗選擇 Java 型態

| 用途 | C# | Java |
|---|---|---|
| 整數能源 | int | int |
| 含小數百分比 | double | double |
| 是否鎖定 | bool | **boolean** |
| 區域字元 | char | char |
| 探測車名稱 | string | **String** |

<!-- 教師提示：不是把 C# 程式原封不動貼過來。String 大寫；Java 沒有內建 string 別名或 bool 關鍵字。 -->

---
<!-- _class: small -->
## 八種基本型態：辨識，不背所有範圍

| 類別 | 型態 | 宣告例 |
|---|---|---|
| 整數 | byte、short | `byte n=20; short s=300;` |
| 整數 | int、long | `int e=7; long d=3000000000L;` |
| 浮點數 | float、double | `float f=3.5F; double p=35.0;` |
| 字元 | char | `char zone='A';` |
| 真偽 | boolean | `boolean locked=false;` |

`L` 指定 long 常值；`F` 指定 float 常值。**String 不在八種之中。**

<!-- 教師提示：byte/short/int/long 分別 8/16/32/64 位元有號整數；float/double 為32/64位元浮點。char 是16位元UTF-16 code unit，不保證一個完整圖像字元；boolean不教授儲存大小。使用TypeReferenceDemo。 -->

---
## char 與 String：引號也有意義

```java
char zone = 'A';
String roverName = "赤砂號";
```

- `'A'` 是 char；`"A"` 是 String，型態不同。
- char 適合此處的單一英文字母區域代碼。
- boolean 只能是 true／false，不用 0／1 代替。

<!-- 教師提示：不要用 emoji 解釋單一 char；本週只需辨識。學生完成 lab A。 -->

---
## 基本型態的指定運算：複製值

```java
int energy = 7;
int backupEnergy = energy;
energy = 6;
```

```text
energy       [6]
backupEnergy [7]
```

改變 energy，不會自動更新 backupEnergy。

<!-- 教師提示：先遮住示意圖讓學生預測，再請學生畫出第二行之後兩格都是7的狀態。 -->

---
## 參考型態的指定運算：複製參考值

```java
String consoleName = "赤砂號";
String logName = consoleName;
```

```text
consoleName ──┐
              ├──→ String 物件「赤砂號」
logName ──────┘
```

兩個變數指向同一物件；不是複製兩份字串內容。

<!-- 教師提示：參考值用箭頭表達，不稱為學生可直接操作的實體記憶體位址。 -->

---
## 重新指定，不是修改字串物件

```java
consoleName = "晨星號";
```

```text
consoleName ───→ String 物件「晨星號」
logName ───────→ String 物件「赤砂號」
```

String 不可變；**不能推論所有參考物件都不可變**。
`null` 表示沒有指向物件；本週範例使用非 null 字串。

<!-- 教師提示：讓學生說出 logName 仍為赤砂號的原因。可變物件留待後續，不提前建立 Rover 類別。 -->

---
## 密室門禁：名字相同就是同一個物件？

```java
String code = "MARS";
String receivedCode = new String("MARS");
System.out.println(code == receivedCode);       // false
System.out.println(code.equals(receivedCode));  // true
```

- `==`：此處比較參考是否相同。
- `.equals()`：String 的內容是否相同（區分大小寫）。

C# 的 string `==` 比較內容；Java String `==` 不沿用此規則。

<!-- 教師提示：new String 僅為刻意建立不同物件的最小演示，平常不需如此複製。字串常值可能共享，不能以一次==成功推導一般內容比較。 -->

---
## 數值計算：每個符號解決什麼需求？

| 運算 | 例子 | 結果 |
|---|---|---|
| 加減乘 | `7 + 5`、`7 - 6`、`2 * 6` | 12、1、12 |
| 整數除法 | `7 / 6` | 完整探索 1 次 |
| 餘數 | `7 % 6` | 剩 1 格 |
| 浮點除法 | `7 / 20.0` | 比例 0.35 |

正整數題裡，商對應完整次數，餘數對應剩餘量。

<!-- 教師提示：整數除法一般向零截斷；本週計算契約採非負能源，避免不必要的負數餘數負擔。 -->

---
## 為何這個百分比是 0.0？

```java
int energy = 7;
double percent = energy / 20 * 100.0;
```

```text
7 / 20       → 0      （int 除以 int）
0 * 100.0    → 0.0
```

存進 double，不會補回前面已捨去的部分。

<!-- 教師提示：先請學生預測。乘除同優先序，由左至右分組。不要以變數宣告型態代替子運算式分析。 -->

---
## 轉型的位置決定結果

| 寫法 | energy=7 | 判讀 |
|---|---:|---|
| `(double)(energy / 20) * 100` | 0.0 | 轉型太晚 |
| `(double)energy / 20 * 100` | 35.0 | 先成為 double |
| `energy / 20.0 * 100` | 35.0 | 除數為 double |

`double e = 7;` 可自動擴大轉換。
`int whole = (int)7.9;` 得到 **7**，不是四捨五入。

<!-- 教師提示：大整數轉浮點可能失去精度，不說所有擴大轉換永遠精確。這組小整數沒有該問題。 -->

---
## 括號讓計算目的清楚

```java
System.out.println("合計=" + 2 + 3);    // 合計=23
System.out.println("合計=" + (2 + 3));  // 合計=5
```

乘除餘數優先於加減；相同優先序注意分組順序。
字串出現在 `+` 中，可能是在串接文字。

短題：53 單位珍珠，每份 8 單位，能做幾份？剩多少？

<!-- 教師提示：答案6份餘5。OperatorDemo可直接執行。這是運算遷移小題，不另做手搖飲系統。 -->

---
## 指定運算、累加、遞增與遞減

```java
int samples = 0;
samples += 2;  // 現在 2
samples++;    // 現在 3
samples--;    // 現在 2
```

`=` 是指定運算，`==` 才是相等比較。
本週的 `++`／`--` 各自獨立一行，準備下週更新計次。

<!-- 教師提示：不要引入 i++ + ++i 等混合副作用題。讓學生逐行填 lab 追蹤表。 -->

---
## 條件也是可以存放的值

```java
int energy = 7;
boolean locked = false;
boolean valid = energy >= 0 && energy <= 20;
boolean canExplore = energy >= 6 && !locked;
boolean invalid = energy < 0 || energy > 20;
```

`&&` 兩者都成立；`||` 至少一個成立；`!` 反轉真偽。
Java 不能寫數學式的 `0 <= energy <= 20`。

<!-- 教師提示：只 println 三個布林值，尚不要求分支。比較符號補充 >、<、>=、<=、==、!=。 -->

---
## 用邊界檢查你的條件

| `energy` | `locked` | `valid` | `canExplore` |
|---:|---|---|---|
| 5 | false | true | false |
| 6 | false | true | true |
| 6 | true | true | false |
| 20 | false | true | true |

範圍題另外測 -1、21，兩者 valid 都是 false。
核心探索計算假設能源有效，今天不加拒絕分支。

<!-- 教師提示：提醒 canExplore 簡式本身不驗上限；在已知有效的能源契約下使用，下一週才先排除非法狀態。 -->

---
## Short-circuit evaluation

左側已能決定結果時，右側運算式不會執行。

```java
static boolean reportCheck() {
    System.out.println("右側檢查已執行");
    return true;
}
```

- `false && reportCheck()`：右側不呼叫，結果 false。
- `true || reportCheck()`：右側不呼叫，結果 true。
- `true && reportCheck()`：右側呼叫，結果 true。

<!-- 教師提示：執行OperatorDemo觀察訊息出現幾次。強調求值順序，不加入例外處理或位元運算。 -->

---
## 跟做：只回傳計算結果的方法

```java
static double calculateBatteryPercent(int energy) {
    return energy / 20.0 * 100;
}
```

```java
double percent = calculateBatteryPercent(7);
System.out.println(percent);
```

引數 7 → 參數 energy → 回傳 35.0 → 呼叫處決定顯示。

<!-- 教師提示：對照第一週 buildGreeting。static 是沿用 main 可直接呼叫的方法骨架，非要求所有設計都用 static。 -->

---
## 獨立補完四個欄位

開啟 starter/RoverDashboard.java：

1. 完整探索次數：每次耗 6 格。
2. 探索後剩餘能源。
3. 能源有效：0 至 20，含兩端。
4. 探索資格：至少 6 格，而且未鎖定。

starter 能編譯，但佔位值不符合需求。

<!-- 教師提示：保留25分鐘讓學生修改與填表；不要求從空白建立完整主程式。教師答案為 /6、%6、>=0&&<=20、>=6&&!locked。 -->

---
## 先寫預期，再逐筆重跑

| energy | 百分比 | 探索次數 | 餘量 | 資格（未鎖定） |
|---:|---:|---:|---:|---|
| 0 | 0.0 | 0 | 0 | false |
| 5 | 25.0 | 0 | 5 | false |
| 6 | 30.0 | 1 | 0 | true |
| 7 | 35.0 | 1 | 1 | true |
| 20 | 100.0 | 3 | 2 | true |

再測 energy=6、locked=true，資格應為 false。

<!-- 教師提示：投影片表於學生自行預測後展示。每改資料重新編譯，記錄實際結果，不只打勾。 -->

---
## AI 說「回傳 double，所以沒問題」？

```java
return energy / 20 * 100.0;
```

這是教材自編情境，不是真實 AI 對話。

- 只測 0 和 20，錯誤可能被掩蓋。
- 加入 7：預期 35.0，實際 0.0。
- 要求 AI 解釋每一步型態，再用執行證據決定採納。

<!-- 教師提示：保留人工初版、採納理由與最小修正。沒有AI權限可分析此情境，但紀錄明寫未使用服務。 -->

---
## AI 使用紀錄不是貼一張對話截圖

記錄在 `AI_USAGE_WEEK02.md`：

- 人工初版與使用目的。
- 實際工具、Prompt、回覆摘要。
- 採納或拒絕理由，以及本人修改。
- 輸入、預期、實際輸出及重跑結果。

不提交自己無法解釋的程式；不傳個資或憑證。

<!-- 教師提示：沿用課綱規範，未另外改變評量比例。由學生用一個反例口頭說明。 -->

---
## 課末練習：換一組資料

1. energy=13、未鎖定：百分比、探索次數、餘量、資格？
2. `(double)(7 / 20)` 為什麼不是 0.35？
3. `String a="A"; String b=a; a="B";`，b 是什麼？
4. 寫出「能源無效或被鎖定」的條件。

先獨立回答，再向同學解釋推理。

<!-- 教師提示：65.0、2、1、true；整數除法已得0；b仍指A；(energy<0||energy>20)||locked。答案詳見teacher_notes。 -->

---
## 最後 15 分鐘：保存後才關機

1. Save All → 重新編譯執行。
2. Desktop 檢查來源檔與紀錄 → Commit to main。
3. Push origin → GitHub 核對 commit 與檔案。
4. 再修改紀錄，須再次 commit／push。

Changes 空白只表示沒有未提交修改，**不代表已推送**。

<!-- 教師提示：無法Push即協助備份至不會還原位置。課後新位置clone重編譯可重現；不宣稱本教材已代學生完成GitHub實測。 -->

---
## 下週：儀表板變成互動任務

- if：能量足夠才執行。
- switch：探索、掃描、充電或返回。
- for／while：重複行動並追蹤狀態。

保留本週 `calculateBatteryPercent`。
今天不用 Scanner、陣列、集合或自訂 Rover 類別。

<!-- 教師提示：第三週入口契約在README與teacher_notes；String、邏輯、方法能力已教，但不假設分支迴圈已會。 -->

---
<!-- _class: small -->
## 課後查閱

- [Java 教學：基本型態](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)
- [Java 教學：運算子](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html)
- [Java 21 String：equals](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#equals(java.lang.Object))
- [JLS 21：運算式與 short-circuit evaluation](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html)

舊版教學頁僅參考穩定基礎語法；課程目標為 Java 21。

<!-- 教師提示：不要求全讀規格；有疑義時用規格與實測核對，尤其是除法、轉型、參考相等及 short-circuit evaluation。 -->
