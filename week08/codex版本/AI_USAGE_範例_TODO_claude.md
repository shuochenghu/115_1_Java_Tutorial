# 第八週 AI 使用與驗證紀錄｜TODO 1–3 填寫範例（claude 版本）

本檔由 Anthropic Claude（Claude Code）依教師要求撰寫，示範學生**自己使用真實 AI** 協助完成 starter 的 TODO 1–3 時，[AI_USAGE_template.md](AI_USAGE_template.md) 可以怎麼填。

> **虛構填寫示範**：學生「王小明」與對話內容都是虛構的，但表中的輸出、編譯訊息都是依本週程式在 JDK 22 實際執行的結果。學生繳交時請寫自己的紀錄，不可照抄。

學生姓名／學號：王小明／B1234567（虛構）

| 使用目的與來源 | Prompt／建議摘要 | 採納或拒絕及理由 | 人工驗證方式與結果 | 最終修改 |
|---|---|---|---|---|
| **TODO 1**：理解要怎麼改 `explore`。<br>ChatGPT，2026-11-10 | 問：「Rover 有一個 `protected int exploreYield()` 回傳 2，`explore()` 裡寫死 `addSamples(2)`。要怎麼改，才能讓子類別改變探索產量？」<br>AI 答：把 `addSamples(2)` 改成 `addSamples(exploreYield())`，其他流程不動。 | **採納**。符合講義 D2：只換掉產量的來源，扣能源、計次的順序不變；`explore` 仍是 private，子類別碰不到流程本身。 | 1. `MissionLog` 輸出和改之前完全相同（最終 6/5/3 任務成功），一般車行為沒變。<br>2. `HeavyRoverTry` 仍有兩行不符：`探索後: 6/5/2/1（預期 6/6/2/1）`。這是預期中的，因為 TODO 2 還沒做，重型車還沒有自己的產量。 | `Rover.explore()` 中 `addSamples(2)` 改為 `addSamples(exploreYield())` |
| **TODO 2**：請 AI 寫重型車的產量方法。<br>ChatGPT，2026-11-10 | 問：「HeavyRover 繼承 Rover，請幫我寫方法讓重型車探索得 3 份。」<br>AI 給：<br>`public int exploreYeild() { return 3; }` | **部分採納**：保留「回傳 3」的想法，但**拒絕** AI 的寫法：<br>① 方法名拼錯（Yeild），變成一個新方法，不是覆寫；<br>② 沒加 `@Override`，所以編譯器沒發現拼錯；<br>③ 改成 public，但規格要沿用父類別的 protected。 | 1. 照 AI 原文編譯成功，但 `HeavyRoverTry` 一樣是 `探索後: 6/5/2/1 … 不符`：能編譯不代表有覆寫到。<br>2. 在原文上方加 `@Override` 再編譯，出現 `method does not override or implement a method from a supertype`，證明它沒有覆寫任何方法。<br>3. 改成正確宣告後重編譯，`HeavyRoverTry` 十五行全部「相符」，探索後為 6/6/2/1。 | `HeavyRover` 新增：<br>`@Override`<br>`protected int exploreYield() { return 3; }`<br>只回傳產量，不扣能源、不加樣本、不計次 |
| **TODO 3**：請 AI 寫兩車比較的 main。<br>ChatGPT，2026-11-11 | 問：「CompareMissions 要讓一般車和重型車跑同一份 script，再用 show 印報表，main 怎麼寫？」<br>AI 給的程式只建立**一台** `new HeavyRover(20, 0)`，先後交給兩個 `new Mission(rover)`。 | **拒絕**。講義規定「每個新任務配新車，只 run 一次」，而且要比較的是兩種車；AI 讓兩個任務共用同一台車，還漏了一般車。 | 照 AI 原文執行：第二段報表 `步數=0, 成功=0, 拒絕=0, 略過=3`，最終狀態和第一段一樣是 8/6/2。原因是同一台車在第一個任務已經達標，第二個任務一開始就停止。<br>自己改寫後：一般探測車 `步數=3`、最終 6/5/3；重型探測車 `步數=2, 略過=1`、最終 8/6/2，與講義 D5 預期相同。 | 自己寫：兩個變數都宣告為 `Rover`，分別 `new Rover(20, 0)`、`new HeavyRover(20, 0)`，各配一個 `new Mission(...)`、各 run 一次、各 show 一次 |

本人 TODO 1–3 完成 commit：`[TODO] 完成產量覆寫與兩車比較`（虛構）。
TODO 2 修正前後證據：`LAB_REPORT.md` D3 段貼了 AI 原文的兩行不符、加 `@Override` 的編譯錯誤，以及修正後十五行相符的輸出。
E 段教師模擬 AI 的紀錄：另外照原範本填一列，不和本表混在一起。
本次沒有把帳號、密碼或他人程式貼給 AI。

## 這份範例想示範的重點

| 列 | 判斷 | 學生做到的事 |
|---|---|---|
| TODO 1 | 採納 | 採納了也要驗證：確認舊行為沒變，也知道為什麼還有不符 |
| TODO 2 | 部分採納 | 「可以編譯」不是證據；用 `@Override` 讓編譯器幫忙抓錯 |
| TODO 3 | 拒絕 | 用講義規格判斷，並用實際輸出（步數 0）證明 AI 的寫法不對 |

- **每一列都有自己的證據**：實際輸出的那幾行、編譯訊息，或對照講義的哪一段。
- **寫清楚拒絕的理由**：理由要指向本週觀念（覆寫的條件、`@Override` 的用途、每個任務配新車），不是只寫「不對」。
- **最終修改寫到能看懂改了什麼**：方法簽名、改了哪一行。
