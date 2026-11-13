---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: 第 10 週｜過載與多型
author: 胡碩誠
footer: '物件導向程式設計｜Week 10｜Codex'
style: |
  section { font-family: "Microsoft JhengHei", "Noto Sans TC", sans-serif; font-size: 28px; line-height: 1.4; color: #203047; background: #fbfcfe; padding: 46px 64px; }
  h1 { font-size: 44px; color: #123d65; }
  h2 { font-size: 38px; color: #123d65; margin-bottom: 20px; }
  strong { color: #006d77; }
  pre { font-size: 22px; line-height: 1.3; background: #eef2f6; }
  code { font-family: Consolas, "Microsoft JhengHei", monospace; }
  table { font-size: 24px; width: 100%; }
  th { background: #e7eff5; }
  footer, section::after { font-size: 16px; color: #62748a; }
  section.lead { background: #123047; color: #f5f8fc; }
  section.lead h1, section.lead h2 { color: white; }
  section.lead footer, section.lead::after { color: #c3d4e1; }
---

<!-- _class: lead -->
# 第 10 週｜過載與多型

同一台重型車，為什麼會印出兩種報表？

資訊管理學系二年級　胡碩誠

<!-- 先發學生講義。完成版方法本體留到講評。 -->

---
## 三節課要完成的事

| 節次 | 動手做 | 留下的證據 |
|---|---|---|
| 1 | 預測簽名及覆寫 | 呼叫追蹤表、兩題編譯預測 |
| 2 | 寫過載並檢查舊入口 | 舊入口三個階段、新入口六行 |
| 3 | 修正模擬 AI 報表 | 錯誤輸出、修正重測、保存成果 |

<!-- 三節各 50 分鐘。活動 A 佔第一節，B/C 佔第二節，D/E 佔第三節。 -->

---
## A｜哪台車會讀到掃描？

```java
Rover rover = new HeavyRover(20, 0);
```

- 宣告型態是什麼？實際物件是哪一種車？
- 兩台新車各跑 `{1, 1, 2}`。哪台會讀到第 3 步？
- 先填講義 A，執行 `CompareMissions` 後記下能源、樣本、成功行動數與步數。

<!-- 10 分鐘。一般車 6/5/3 且讀 3 步。重型車 8/6/2 且讀 2 步。由學生指出哪裡算產量。 -->

---
## A｜會員短例先猜三行

開 `examples/MemberOverloadDemo.java`。

```java
VipMember vip = new VipMember();
Member member = vip;
System.out.println(member.describe());
System.out.println(vip.describe(30));
System.out.println(vip.describe("迎新"));
```

先標出引數個數與型態，再找對應的 `describe`。

<!-- 先投影呼叫處。實測為 VIP 會員、VIP 會員 點數=30、VIP 會員 活動=迎新。短例用 VS Code，與 starter 分開編譯。 -->

---
## A｜兩個編譯預測

1. 在 `VipMember.describe(int points)` 加上 `@Override`，可以編譯嗎？父類別有哪個簽名？
2. 同一類別已有 `String describe(int points)`，再宣告 `int describe(int points)`，可以編譯嗎？

先寫理由，再看教師用獨立副本編譯。

<!-- 回收第 8 週問題。父類別只有 describe()，所以第一題加標註會失敗。第二題簽名重複。 -->

---
## A｜同一台車，兩個報表簽名

```java
Rover rover = new HeavyRover(20, 0);
SampleSource source = rover;
// 車輛已完成任務後，再讀取報表
SampleReport.print("重型探測車", rover);
SampleReport.print("重型探測車", source);
```

兩個參考指向同一個物件。這兩行各選哪個簽名？哪行會有能源欄位？

<!-- 15 分鐘。先預測再由教師跑完成版 SampleOverloadDemo，只投影前兩行與呼叫處。 -->

---
## A｜把兩個階段分開追蹤

| 呼叫 | 編譯期找什麼 | 執行時看到什麼 |
|---|---|---|
| `print(label, rover)` | `Rover` 較具體，選詳報簽名 | 印樣本數及能源 |
| `print(label, source)` | 介面引數適用共用簽名 | 印樣本數 |
| 車內呼叫 `exploreYield()` | 已確定零參數簽名 | 由實際車種決定回 2 或 3 |

<!-- Rover 實作 SampleSource。用 Rover 引數時兩個參考版本都適用，Rover 較具體。最後一列為可覆寫的實例方法。 -->

---
## B｜用 starter 跑舊報表

從 `java-10` 執行。

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out SampleReportDemo
```

先保存三行結果。重型車的變數 `heavy` 宣告為 `Rover`，目前仍選到共用報表簽名。

<!-- 10 分鐘讀規格。IDE 示範標記 src 為來源根目錄並從 main 啟動。 -->

---
## B｜TODO 1、2　先看變化再修正

1. 新增車輛詳報，印樣本數及能源。
2. 舊呼叫先照原樣。預測三行，重編譯並執行。
3. 依舊入口的共用格式要求，改用同一台車的 `SampleSource` 參考。重跑確認三行。

在講義留下三個階段的輸出與簽名。

<!-- 完成 TODO 1 後第一行多出能源 8。不要在學生預測前顯示結果。讓學生用宣告型態解釋修正。 -->

---
## B/C｜TODO 3、4　加入手動清點

- `int` 版本印一行樣本數。讓共用報表讀到數量後轉交給這個版本。
- 在 `SampleOverloadDemo.java`，用同一台車的兩個參考各印一次，再印樣本盒與手動清點。
- 比對講義 C 的六行，另用一個自選案例抓可能的錯誤。

<!-- B 的工作合計 25 分鐘。C 用 15 分鐘。強調 0 可由樣本盒或整數引數送入，走的簽名不同。 -->

---
## D｜這段程式可以編譯

教師模擬 AI 在共用報表加了這段。

```java
if (source instanceof Rover) {
    print(label, (Rover) source);
    return;
}
```

這段先辨認車輛，再把引數以 `Rover` 型態傳入。把同一台車經兩種參考傳入時，哪一行會違反規格？

<!-- 前 5 分鐘先寫需求與預測。只讀這段型態判斷，不要求學生實作轉型。 -->

---
## D｜用同一入口重現和重測

1. 保存人工完成版，複製 Java 檔到 `ai-check/`。
2. 替換副本的 `SampleReport.java`，重編譯後跑 `SampleOverloadDemo`。
3. 標出呼叫端選到的簽名，以及方法本體額外轉交的簽名。
4. 做最小修正，從同一入口比對六行。

<!-- 20 分鐘。只改副本。答案見教師筆記。提醒樣本盒與手動案例可能都正確。 -->

---
## E｜同儕核對與保存

- 各指出一處程式：報表為何有兩種結果？探索為何有兩種產量？
- 在正式 `src` 重跑新舊入口，核對講義與 AI 紀錄。
- 用 GitHub Desktop 檢查差異、提交及推送。遠端核對後取回並重編譯。

<!-- 同儕 10 分鐘，保存 15 分鐘。無法推送時保留本機提交並據實記錄。 -->

---
## 課後｜新增一個快速查詢入口

新增 `print(SampleSource source)`，自訂標籤，轉交給共用報表。

- 用 `Rover` 參考與樣本盒各呼叫一次，包含一個零樣本案例。
- 說明為何單參數呼叫仍只顯示樣本數。
- 重跑三個課堂入口，繳交程式、講義與 AI 紀錄。

<!-- 預估 25–35 分鐘。第 11 週由 Mission 已用的 ArrayList<MissionStep> 引入泛型。 -->
