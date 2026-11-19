# 第 9 週學生實作｜抽象類別、介面與 AI 協作

依 [README](README.md) 準備。將本頁複製為當日的 `LAB_REPORT.md`。每段先記預測，再編譯、執行並比較差異。編譯失敗時，先看第一個與本次改動有關的診斷。

## A｜先跑第 8 週行為基準（10 分鐘）

開 `Rover.java`、`HeavyRover.java` 與 `CompareMissions.java`。

1. 在 `Rover rover = new HeavyRover(20, 0)` 中，變數的宣告型態及實際物件類別各是什麼？呼叫 `execute(1)` 時，哪個類別決定探索產量？待填。
2. 兩台新車各跑 `{1, 1, 2}`。預測哪台車會讀到第 3 個指令，理由是什麼？待填。
3. 記下 JDK 版本。編譯起始碼，執行 `CompareMissions`。留下兩段 `最終能源=...` 與 `步數=...` 行，B 段會用同樣方式比對。

## B｜讓每一種車都要交代產量（40 分鐘）

兩台車都照同一套規則探索，但產量各自決定。假如未來增加新車種，沿用目前的 `Rover`，即使忘記寫產量也會默默得到 2。目標是把這件事提早交給編譯器檢查。

先看範例一 `ParcelDemo`（教師執行，或依 [示範程式說明](examples/README_claude.md) 自己跑）。先預測兩行運費，再寫出運費公式 `fee()` 寫在哪個類別、每公斤費率由哪個類別決定。待填。

1. **TODO 1**。開 `StandardRover.java`，參考 `HeavyRover` 的方法簽名，自行寫一般車的覆寫，固定回傳 2。建構子已交給 `super`。重新編譯，跑一次 `CompareMissions`。
2. **TODO 2**。開 `Rover.java`，將類別與 `exploreYield()` 改為抽象宣告。抽象方法留簽名與分號。保留建構子、私有狀態、`execute` 與共用探索流程。
3. 重跑 `CompareMissions` 與 `HeavyRoverTry`。比較 A 段報表，核對一般車 `6/5/3` 且讀 3 步，重型車 `8/6/2` 且讀 2 步。這三個數依序為能源、樣本、成功行動數。若 `HeavyRoverTry` 有 `不符`，先找第一個不同的欄位。
4. 擇一編譯實驗。在 `java-09/experiments/` 複製全部 `src` Java 檔，進入該目錄編譯 `javac -encoding UTF-8 -d out *.java`。實驗一是在 `CompareMissions` 中把 `new StandardRover(20, 0)` 改成 `new Rover(20, 0)`。實驗二是刪除 `StandardRover` 的產量覆寫。先預測哪一段會被拒絕，記錄第一個相關診斷及理由。完成後可還原副本，正式 `src` 保持可編譯。

| 要記錄的證據 | 預測 | 實際及原因 |
|---|---|---|
| 改造後兩車的狀態、結果及已讀步數 | 待填 | 待填 |
| 編譯實驗選擇與診斷 | 待填 | 待填 |
| `Mission` 為何仍可把欄位宣告為 `Rover` | 待填 | 待填 |

## C｜讓同一份報表讀兩種來源（45 分鐘）

先看範例二 `ScaleDemo`。先預測四行輸出，再用一句話說明行李箱和貓沒有共同父類別，為什麼能交給同一個 `weigh` 方法。待填。

地面站要顯示「標籤 樣本數=數量」。探測車的數量來自行動累積，樣本盒的數量由建構時給定。先寫下自己的介面草案，再對照指定契約修改，保留草案和理由給 E 使用。

| 草案要回答的事 | 你的判斷 |
|---|---|
| 報表應從來源讀到什麼？方法需要參數嗎？ | 待填 |
| 方法的回傳型態與簽章是什麼？ | 待填 |
| 哪兩種類別應提供此方法？讀取會改變什麼狀態？ | 待填 |

1. **TODO 3a**。開 `SampleSource.java`，依契約宣告 `int getSamples()`，呼叫時不修改來源的狀態。
2. **TODO 3b**。開 `Rover.java`，宣告實作 `SampleSource`，在既有的公開 `getSamples()` 上加 `@Override`。保留它從原欄位讀資料的行為。
3. **TODO 3c**。開 `SampleTray.java`，實作同一介面，公開方法應回傳該樣本盒的欄位值。使用 `@Override` 讓編譯器檢查簽名。先重新編譯。
4. **TODO 4**。開 `SampleReport.java`，自行實作 `public static void print(String label, SampleSource source)`。每次呼叫印一行，透過 `source` 讀一次樣本數。接著在 `SampleReportDemo.java` 依序以 `heavy`、`tray`、`empty` 為標籤和來源變數呼叫同一個方法，移除 `尚未完成`。編譯成功後執行 `SampleReportDemo`。

| 來源 | 先寫預測 | 實際輸出與數量從哪裡來 |
|---|---|---|
| 重型車從 20 格、0 份執行 `{1, 1, 2}` | 待填 | 待填 |
| 建構時放入 4 份的樣本盒 | 待填 | 待填 |
| 建構時放入 0 份的樣本盒 | 待填 | 待填 |

檢查點是三行依序為 `重型探測車 樣本數=6`、`樣本盒 樣本數=4`、`空盒 樣本數=0`。兩個樣本盒都印成 4 時，先查方法是否真的從各自的物件取值。兩個樣本盒都無法傳入時，查 `implements` 與報表的參數型態。再自行提出一個能抓出「寫死樣本數」的輸入，說明要比較什麼並實測。可以在 `experiments` 副本操作，保留正式三行基準。

## D｜同一測試資料的兩種寫法（5 分鐘）

這是範例三。看教師執行 `FixtureDemo`，或依 [示範程式說明](examples/README_claude.md) 在獨立資料夾跑。先預測具名巢狀類別與匿名類別各印什麼，再圈出兩個 `getWeight()` 的位置。用一句話說明兩個物件如何交給同一個 `show` 方法。待填。

## E｜把「可以共用」寫成可驗收的 Prompt（25 分鐘）

保留 C 的人工草案，先存自己的完成碼。比較「幫我寫地面站樣本報表」這句話和自己補齊的 Prompt。把四欄寫入 `AI_USAGE.md`，限 3–5 句即可。

| 欄位 | 要填的內容 |
|---|---|
| 呼叫者 | 哪個報表方法需要同時處理哪些來源？ |
| 必要操作 | 共用方法需要什麼簽章？回傳值代表什麼？ |
| 限制 | 誰持有資料？讀取是否改狀態？共用報表要保留哪個參數型態？ |
| 驗收案例 | 列出至少一種車與一種樣本盒，以及各自的預期輸出 |

教師模擬回覆聲稱「探測車已有 `getSamples()`，報表可以共用」。程式在 [ai/SampleReport.java](ai/SampleReport.java)。先判讀是否滿足你補齊的 Prompt，再驗證。

1. 在 `java-09/ai-check/` 複製已完成的全部 `src` Java 檔，將其中的 `SampleReport.java` 換成模擬版本。正式 `src` 保持原樣。
2. 從 `ai-check` 執行 `javac -encoding UTF-8 -d out *.java`。記第一個關於樣本盒的診斷，以及為什麼重型車呼叫可以通過型別檢查。
3. 暫時註解兩個樣本盒的報表呼叫，重新編譯後執行 `java -cp out SampleReportDemo`，驗證只有車的案例會通過。恢復兩個呼叫。
4. 只修正共用方法的參數型態。重新編譯並執行相同入口，記三行結果，說明為什麼樣本盒已能通過。記採納或拒絕理由。真實 AI 為選用，使用時另外記錄來源與人工驗證。

## F｜看圖核對責任（10 分鐘）

看 [類別圖](diagrams/responsibilities.svg)。兩人交換答案，各指出一處對應程式。

- `Mission` 的欄位需要什麼型態？`SampleReport.print` 的參數需要什麼型態？
- 為什麼 `SampleTray` 的箭頭接到 `SampleSource`？
- 介面方法在實作類別中漏寫 `public`，你預期編譯器會接受嗎？若時間足夠，在副本驗證。

## G｜保存及取回（15 分鐘）

- [ ] 正式 `src` 重編譯，執行兩車比較和樣本報表。
- [ ] GitHub Desktop 檢查差異，commit 並 push，在 GitHub 核對原始碼、講義和 AI 紀錄。
- [ ] 在原 repo 外的新位置 clone，重編譯並執行 `SampleReportDemo`，記實際三行及 commit。遠端有問題時先保留本機 commit，據實記錄。
- [ ] 排除 `out`、`.class`、實驗副本及憑證。模擬 AI 的證據放在報告即可。
