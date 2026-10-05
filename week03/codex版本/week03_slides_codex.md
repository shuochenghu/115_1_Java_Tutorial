---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: 第三週｜火星探測車互動任務
author: 胡碩誠
description: 115 學年度第 1 學期｜物件導向程式設計
footer: '物件導向程式設計｜Week 03'
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
## 第三週｜火星探測車互動任務

從第二週儀表板，到可操作的任務

選擇結構・重複結構・狀態追蹤・AI 除錯

資訊管理學系二年級｜胡碩誠

<!-- 教師提示：用按鈕選擇行動的 WinForms 經驗開場。本週不建立 GUI。 -->

---
## 今天的成果

- 先判斷能源，再執行探索或掃描。
- 用 for 預測三輪充電。
- 用 while 讓主控台持續讀取指令。
- 找出一項 AI 建議錯誤，提出反例並重測。
- 保存、push，從新副本重新編譯。

<!-- 教師提示：主線只補三個 TODO，其餘提供骨架。 -->

---
## 150 分鐘任務表

| 第 1 節 | 第 2 節 | 第 3 節 |
|---|---|---|
| 取回與診斷 10 | for 15 | 補完與 AI 25 |
| if／邊界 20 | Scanner 10 | 課末檢核 10 |
| switch 與練習 20 | 迴圈與追蹤 25 | 保存與取回 15 |

休息另計；實際上課日期由教師公告。

<!-- 教師提示：需要補強時刪減策略延伸，不刪邊界及保存。 -->

---
## 取回上週能力

```java
7 / 20 * 100.0
7 / 20.0 * 100
energy >= 6 && !locked
```

預測能源為 6、未鎖定時的資格。

本週固定未鎖定；不新增鎖定選單。

<!-- 教師提示：答案 0.0、35.0、true。locked 為第二週診斷，不是本週新狀態。 -->

---
## 課前專案準備

1. 無 repo：clone；已有副本：保存後 Fetch／Pull。
2. 在 `115_1_Java/YYYY_MM_DD/java-03/src` 放示範與 starter。
3. 日期下保存 LAB_REPORT 與 AI_USAGE。
4. 不把同名 teacher 程式複製進同一專案。

```powershell
javac --release 21 -encoding UTF-8 -d out src/*.java
java -cp out RoverMission
```

<!-- 教師提示：先輸入 0 確認骨架能結束。JDK 21 教學基準，作者使用 JDK 22。 -->

---
## 同一台車，三種行動

| 指令 | 成本與效果 | 拒絕條件 |
|---|---|---|
| 1 探索 | -6 能源、+2 樣本 | 能源不足 6 |
| 2 掃描 | -2 能源、+1 樣本 | 能源不足 2 |
| 3 充電 | +5 能源，上限 20 | 已滿電 |
| 0 返回 | 停止任務 | 無 |

起始 20 格、0 樣本；取得至少 5 份即成功。

<!-- 教師提示：有效行動才計次；這是教學遊戲規則。 -->

---
## C# 經驗如何接過來

| 熟悉的經驗 | 本週 Java 主控台 |
|---|---|
| Click 後選擇行動 | 讀取 command 後選 case |
| if 判斷是否足夠 | if 判斷能源成本 |
| 更新畫面數值 | 更新狀態再輸出儀表板 |

主控台由 main 啟動，今天自己寫迴圈讀下一個指令。

<!-- 教師提示：WinForms 也有框架訊息迴圈，勿說它沒有迴圈。 -->

---
## 先排除無效能源

對照範例：[`examples/EnergyStatus.java`](examples/EnergyStatus.java)

```java
if (energy < 0 || energy > 20) {
    System.out.println("INVALID");
} else if (energy < 6) {
    System.out.println("LOW");
} else {
    System.out.println("READY");
}
```

每次只走一個分支；條件需是 boolean。

<!-- 教師提示：執行 EnergyStatus。若先判小於 6，-1 會誤分類。 -->

---
## 邊界比隨便選一個值更有用

對照範例：[`examples/EnergyStatus.java`](examples/EnergyStatus.java)

| energy | -1 | 0 | 5 | 6 | 20 | 21 |
|---|---|---|---|---|---|---|
| 預測 | ? | ? | ? | ? | ? | ? |

先寫預測，再執行 `EnergyStatus`。

為什麼 5 和 6 都要測？

<!-- 教師提示：答案無效、低電、低電、可探索、可探索、無效；5/6 可抓 > 與 >=。 -->

---
## 扣能源與給樣本要一起受保護

對照範例：[`examples/SingleAction.java`](examples/SingleAction.java)（探索分支）

```java
if (energy >= 6) {
    energy -= 6;
    samples += 2;
} else {
    System.out.println("NOT_ENOUGH_ENERGY");
}
```

若把 samples += 2 移到 if 後面，會發生什麼？

<!-- 教師提示：不足能源仍領獎勵。用 energy=5 做反例。 -->

---
## switch 選擇一個入口

對照範例：[`examples/SingleAction.java`](examples/SingleAction.java)（本頁為簡化片段）

```java
switch (command) {
    case 1:
        System.out.println("Explore");
        break;
    case 2:
        System.out.println("Scan");
        break;
    default:
        System.out.println("UNKNOWN_COMMAND");
        break;
}
```

<!-- 教師提示：片段示範分派，完整行動見 SingleAction。本週只教傳統冒號形式。 -->

---
## break 是控制流程的一部分

對照範例：[`examples/SingleAction.java`](examples/SingleAction.java)

- Java 傳統 case 若沒有 break，可能接續下一段。
- command=1 不代表永遠只執行 case 1 的內容。
- default 接住其他值，例如 9。

用 `SingleAction`：起始 20，移除第一個 break。
預測 → 執行 → 還原。

<!-- 教師提示：原版 energy14 samples2；移除後 energy12 samples3。與 C# 非空 case 接續限制不同。 -->

---
## 輪到你：單次指令驗證

對照範例：[`examples/SingleAction.java`](examples/SingleAction.java)

| energy／command | 要檢查什麼？ |
|---|---|
| 6／1 | 恰好足夠 |
| 5／1 | 不足時不改狀態 |
| 6／2 | 換成掃描 |
| 6／9 | 無效指令 |

每次改值、重編譯，將預測與實際填入講義 B。

<!-- 教師提示：預期0/2、5/0、4/1、6/0。確認執行新class。 -->

---
## 固定三輪：for 充電預測

對照範例：[`examples/ChargeForecast.java`](examples/ChargeForecast.java)

```java
for (int round = 1; round <= 3; round++) {
    energy += 5;
    if (energy > 20) {
        energy = 20;
    }
    System.out.println(energy);
}
```

初始化一次 → 判斷 → 主體 → 更新 → 再判斷。

<!-- 教師提示：只處理固定次數，不加輸入；起始 energy 在迴圈外。 -->

---
## 先用表格跑三輪

對照範例：[`examples/ChargeForecast.java`](examples/ChargeForecast.java)

| 起點 | 第 1 輪 | 第 2 輪 | 第 3 輪 |
|---|---|---|---|
| 4 | 9 | 14 | 19 |
| 18 | 20 | 20 | 20 |

若 `round <= 3` 改成 `round < 3`，會少哪輪？

這是三輪預測；任務中的滿電充電不計有效行動。

<!-- 教師提示：少第3輪。執行 ChargeForecast；不要把預測次數和有效行動次數混淆。 -->

---
## 變數的有效範圍（scope）

對照範例：[`examples/ChargeForecast.java`](examples/ChargeForecast.java)（本頁為延伸改寫）

```java
int energy = 4;
for (int round = 1; round <= 3; round++) {
    energy += 5;
}
System.out.println(energy);
// System.out.println(round); // 離開有效範圍，不能使用
```

若每輪把 energy 重新指定為 4，結果無法累積。

<!-- 教師提示：有效範圍決定哪裡能使用；重新指定值決定值何時被清除。勿在同方法重疊範圍重複宣告。 -->

---
## Scanner：今天提供的輸入工具

```java
import java.util.Scanner; // 在 class 前
```
```java
Scanner input = new Scanner(System.in);
int command = input.nextInt();
```

import：使用短名稱；System.in：標準輸入。
nextInt：讀一個整數，尚無輸入時等待。

<!-- 教師提示：使用現成物件，不代表今天要自行設計類別。建立與close由骨架提供。 -->

---
## 先約定本週的輸入

- 輸入 Java int 範圍內的整數。
- 一次一個，直到成功、耗盡或輸入 0。
- 9 是有效整數，但不是有效行動指令。
- 不混用 nextInt 與 nextLine。

非整數、輸入中斷與例外處理留後續週次。

<!-- 教師提示：輸入耗盡會失敗，因此自動測試每個未成功路線都要提供0。 -->

---
## while：什麼時候繼續？

```java
while (running && energy > 0 && samples < 5) {
    int command = input.nextInt();
    // switch 處理本次指令
    // 顯示更新後狀態
}
```

仍在執行、仍有能源、尚未達標：三項都成立才進入。

<!-- 教師提示：本頁為流程片段。請學生逐項說明停止原因。 -->

---
## case 0 的 break 到哪裡？

```java
case 0:
    running = false;
    break;
```

1. 離開最近的 switch。
2. 執行 switch 後的儀表板顯示。
3. 回到 while 條件，因 running=false 而停止。

只寫 break，不會同時離開外層 while。

<!-- 教師提示：以starter輸入9、0示範。不要使用標記break增加負擔。 -->

---
## do-while：至少執行一次

對照範例：[`examples/LoopComparison.java`](examples/LoopComparison.java)（本頁為分段示意）

```java
int count = 0;
while (count < 0) {
    count++;
}
```
```java
int count = 0;
do {
    count++;
} while (count < 0);
```

<!-- 教師提示：兩段各自獨立，不能同有效範圍宣告兩個count。上0下1；完整檔使用不同名稱。 -->

---
## 哪些狀態要跨輪留下？

| 變數 | 宣告位置 | 原因 |
|---|---|---|
| energy | while 外／方法參數 | 下一輪承接餘額 |
| samples | while 外／方法參數 | 累積成果 |
| actionCount | while 外 | 累積有效行動 |
| command | while 內 | 只處理本輪指令 |

把 `samples = 0;` 放每輪開頭，會清掉成果。

<!-- 教師提示：區分指定運算和重複宣告；本週不需static欄位保存狀態。 -->

---
## 紙筆執行：1 → 1 → 2

| 行動 | energy | samples | actionCount |
|---|---:|---:|---:|
| 初始 | 20 | 0 | 0 |
| 探索 | 14 | 2 | 1 |
| 探索 | 8 | 4 | 2 |
| 掃描 | 6 | 5 | 3 |

下一次 while 判斷為 false，不再讀下一個指令。

<!-- 教師提示：先遮住答案讓學生填講義；D完成後用程式核對。 -->

---
## 任務結束：成功優先

```java
if (samples >= 5) {
    System.out.println("SUCCESS");
} else if (energy == 0) {
    System.out.println("EXHAUSTED");
} else {
    System.out.println("RETURNED");
}
```

energy=2、samples=4，掃描後應判哪一項？

<!-- 教師提示：0能源5樣本，依需求成功。不能以習慣決定if順序。 -->

---
## 實作 D：只補三個 TODO

1. D1 探索：足夠才扣 6、加 2 樣本、計次。
2. D2 掃描：足夠才扣 2、加 1 樣本、計次。
3. D3 充電：未滿才加 5，超出設 20、計次。

不足能源、滿電、無效指令及返回，都不計次。

保留輸入、顯示、迴圈與返回骨架。

<!-- 教師提示：先跟做D1，模仿D2，獨立D3。不要求從空白手寫完整程式。 -->

---
## 四條路線，每次重新開始

| 指令 | 最終能源／樣本／次數 | 結果 |
|---|---|---|
| 0 | 20／0／0 | 返回 |
| 1、1、2 | 6／5／3 | 成功 |
| 2、2、2、2、2 | 10／5／5 | 成功 |
| 3、9、0 | 20／0／0 | 返回 |

<!-- 教師提示：再由教師帶固定5/0、6/0、2/4案例。記錄預測及實際。 -->

---
## AI 建議：先反例，再採納

教師模擬的三種建議，選一個驗證：

- 把 `energy >= 6` 改成 `energy > 6`。
- 移除 case 1 尾端的 break。
- 每輪開頭加上 `samples = 0;`。

保留人工初版 → 預測 → 實測 → 最小修正 → 重測。

<!-- 教師提示：並非真實AI對話。無工具者直接驗證教師案例，據實填AI_USAGE。 -->

---
## 如何把 AI 問題問清楚？

> 請檢查這個探索分支。規則是能源至少 6 才能探索，
> 成功扣 6、取得 2 樣本；拒絕時狀態不變。
> 不要改用陣列、物件設計或新版 switch。
> 請給出一個邊界反例，我會自行編譯驗證。

AI_USAGE：目的、Prompt、採納理由、實測、本人修改。

<!-- 教師提示：提醒不要上傳敏感資料，不能以AI肯定代替測試。 -->

---
## 兩條成功路線，兩種取捨

- 探索、探索、掃描：3 次行動，剩 6 格。
- 掃描五次：5 次行動，剩 10 格。

今天不求最佳化演算法；解釋你的選擇即可。

下一週問題：只看最後狀態，能回放每一步嗎？

<!-- 教師提示：不能一般性重建路線，需要逐筆保存歷程；陣列與集合下週教。 -->

---
## 課末檢核

1. 能源剛好 6，是否可探索？
2. 返回為何要改 running？
3. 樣本數為何放在 while 外？
4. 同時耗盡與達標，判定順序為何？
5. 你拒絕哪項 AI 建議？證據是什麼？

<!-- 教師提示：答案至少6、旗標讓while停止、跨輪累計、成功優先、要求可重現輸入。 -->

---
## 最後 15 分鐘：保存與取回

- 保存 src、LAB_REPORT、AI_USAGE、README。
- Desktop 檢查差異 → commit → push。
- GitHub 核對當日檔案及最新 commit。
- 新路徑 clone → 重新編譯 → 跑 1、1、2。

尚未完成也保存 WIP；push 失敗先找教師協助。

<!-- 教師提示：不刪原副本，不提交out。已有副本CLI選讀git pull --ff-only；未實做標示待驗證。 -->

---
## 查閱來源與驗證範圍

- [Java SE 21 JLS §14](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html)：if、switch、迴圈、break。
- [Scanner API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html)：整數輸入。
- 本課綱與跨週銜接設計：主題、能力邊界。

JDK 21 教學基準；實測使用 JDK 22，目標 Java 21。
教室 IDE、真實 GitHub 與學生試做仍需課前確認。

<!-- 教師提示：詳見verification。不要把目標版本當成實際運行版本。 -->
