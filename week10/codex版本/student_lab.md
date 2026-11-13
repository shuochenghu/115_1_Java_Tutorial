# 第 10 週學生實作｜過載、多型與 AI 除錯

依 [README](README.md) 設定，將本頁另存為 `LAB_REPORT.md`。每個實驗先填預測，編譯成功後再執行。

## A｜基準與簽名預測（第 1 節，50 分鐘）

1. 開 `CompareMissions.java`。兩台新車各用 20 格、0 份跑 `{1, 1, 2}`。預測哪台讀到第 3 筆。執行後記下兩台車的能源、樣本、成功行動數與步數。
2. 看 [會員短例](examples/MemberOverloadDemo.java)。預測三行輸出，再看教師執行或從 `java-10` 另行編譯 `javac -encoding UTF-8 -d out-examples examples/*.java`，接著執行 `java -cp out-examples MemberOverloadDemo`。
3. 在 `VipMember.describe(int points)` 上加 `@Override`，能否編譯？假如在同一個類別同時保留 `String describe(int points)` 和 `int describe(int points)`，又會如何？先寫理由，再記教師在獨立副本看到的診斷。
4. 看教師對同一台重型車使用 `Rover rover` 與 `SampleSource source = rover`。報表同時具有 `print(String, Rover)` 和 `print(String, SampleSource)` 時，分別圈出編譯器選到的簽名。

| 呼叫或案例 | 預測與理由 | 實際證據 |
|---|---|---|
| 兩車的狀態與步數 | 待填 | 待填 |
| `member.describe()`、`vip.describe(30)`、`vip.describe("迎新")` | 待填 | 待填 |
| `describe(int)` 加 `@Override` | 待填 | 待填 |
| `describe(int)` 只換回傳型態再宣告一次 | 待填 | 待填 |
| 用 `rover` 和 `source` 各呼叫一次報表 | 待填 | 待填 |

## B｜新增簽名並檢查舊入口（第 2 節前 35 分鐘）

先跑一次 `SampleReportDemo`，留下三行基準。

- **TODO 1** 在 `SampleReport.java` 新增 `public static void print(String label, Rover rover)`。用公開 getter 讀取樣本數、能源，印一行 `<標籤> 樣本數=<數量> 能源=<能源>`。共用報表的規格仍是一行 `<標籤> 樣本數=<數量>`。
- **TODO 2** 先保留 `SampleReportDemo` 的原呼叫。預測新增簽名會改變哪一行、為什麼，再編譯執行。原入口應維持共用報表格式。用同一台車建立 `SampleSource` 參考，修改重型車的呼叫並重跑。
- **TODO 3** 新增 `public static void print(String label, int samples)`，手動清點印共用格式。把 `SampleSource` 版改成先讀數量，再交給 `int` 版印出。重跑舊入口，核對三行皆維持共用格式。

| `SampleReportDemo` 階段 | 三行實際輸出 | 重型車呼叫選到的簽名及理由 |
|---|---|---|
| 新增簽名前 | 待填 | 待填 |
| 新增後、改呼叫前 | 待填 | 待填 |
| 改呼叫並完成 TODO 3 後 | 待填 | 待填 |

## C｜並排比較報表（第 2 節後 15 分鐘）

**TODO 4** 在 `SampleOverloadDemo.java` 讓 `Rover` 與 `SampleSource` 參考指向同一台重型車。移除「尚未完成」，依序實作以下六個呼叫。每次修改 `src` 後重編譯。

```text
重型探測車 樣本數=6 能源=8
重型探測車 樣本數=6
樣本盒 樣本數=4
空盒 樣本數=0
手動清點 樣本數=7
手動清點 樣本數=0
```

另選一個合法值檢查報表是否讀到正確的資料，例如把樣本盒的數量從 4 改為 9。記下你想抓的錯誤、預期、實際和判斷。提交前恢復六行基準。

## D｜除錯能編譯的模擬 AI 程式（第 3 節前 25 分鐘）

1. 保存人工完成版。在 `AI_USAGE.md` 用 3–5 句寫清楚兩種車輛報表、手動清點與讀取限制，先預測同一台車的兩行。
2. 複製完成的 `src` Java 檔到 `java-10/ai-check/`，用 [模擬版本](ai/SampleReport.java) 覆蓋副本中的同名檔。教師提供的 `instanceof` 表示「來源是一台 Rover」；`(Rover) source` 讓後續呼叫以 `Rover` 型態傳入。本活動只要求讀懂這兩行。
3. 在 `ai-check` 執行 `javac -encoding UTF-8 -d out *.java`，編譯成功後執行 `java -cp out SampleOverloadDemo`。圈出違反規格的欄位。寫下呼叫端先選到哪個簽名、方法本體又轉交給哪個簽名。
4. 只改造成違規的轉交。重編譯並從同一入口比較六行。補上錯誤前後結果及採納或拒絕理由。

## E｜同儕檢查與保存（第 3 節後 25 分鐘）

- 用 10 分鐘交換檢查：解釋「同一台車為何有兩種報表」與「相同探索流程為何有兩種產量」，各指出對應程式行。
- 用 15 分鐘在正式 `src` 重跑舊入口及新入口。用 GitHub Desktop 查看差異、commit、push、到遠端核對後在原 repo 外取回並重編譯。記錄取回後輸出。遠端有問題時先保存本機提交並據實記錄。
- 排除 `out`、`.class`、`ai-check`、實驗副本及憑證。依 [作業說明](assignment.md) 完成課後快速查詢。
