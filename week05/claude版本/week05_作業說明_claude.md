# 第 5 週作業：讓探測車自己管好自己（claude 版本）

> 本說明初稿由 Anthropic Claude（Claude Code）生成，教師審閱後使用。截止時間與繳交平台依教師公告。

把第四週的任務日誌改成由物件負責：`Rover` 保管狀態與行動規則，`MissionStep` 記錄一步，`MissionLog` 只負責流程與輸出。輸出格式與第四週相同。延續課堂專案，不必從空白檔重寫。

## 1. 要完成什麼？

從 `code/starter/` 的 `Rover.java`、`MissionStep.java`、`MissionLog.java` 開始，完成 TODO 1–7。已提供的 `explore()`、`execute()`、`toScript()`、`countCommands()` 與結束判斷請勿修改。

| TODO | 檔案 | 要求 |
|---|---|---|
| 1 | `Rover` | 建構子：energy 限制在 0–20，samples 小於 0 時改為 0，actionCount 為 0；用 `this` 存入欄位 |
| 2 | `Rover` | `scan()`：能源不足 2 回傳 `NOT_ENOUGH_ENERGY`；`charge()`：已滿回傳 `ALREADY_FULL`，否則 +5 並封頂 20。被拒絕時狀態不變，成功時 actionCount +1 並回傳 `OK` |
| 3 | `MissionStep` | 建構子用 `this` 存入三個欄位；完成後三個欄位改為 `private final` |
| 4 | `MissionStep` | `getResult()`、`getEnergyAfter()` |
| 5 | `MissionLog` | `runMission` 每一步（含被拒絕、返回、無效指令）建立一筆 `MissionStep` 加入 `steps`，能源為執行後的值 |
| 6 | `MissionLog` | `printReplay`：依序印出 `step N: ` 加上 `describe()` |
| 7 | `MissionStep`＋`MissionLog` | `isRejected()`：結果不是 `OK` 也不是 `RETURN` 時為 true；`printSummary` 用迴圈算出 `steps`、`ok`、`rejected`、`ignored` 與 `lowest energy`（沒有紀錄時不印最低能源） |

限制：

- 所有欄位都是 `private`；不新增 setter。
- 不必處理非整數參數（`abc` 會丟 `NumberFormatException`，第 12 週再處理）。
- 不使用繼承、`toString` 覆寫、`record`、Lambda、Stream。

## 2. 必做測試

每列重新執行一次，把實際輸出貼到 `LAB_REPORT.md`：一般列貼回放之後的四行（`counts`、摘要、最低能源、final）；`3 9 0` 貼完整輸出（含回放）。

| 參數 | 最終 energy／samples／actionCount | 結果 | 摘要 |
|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=0`，`lowest energy=6` |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | `steps=5, ok=5, rejected=0, ignored=0`，`lowest energy=10` |
| `3 9 0` | 20／0／0 | RETURNED | `steps=3, ok=0, rejected=2, ignored=0`，`lowest energy=20` |
| `1 1 1 1` | 2／6／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=1`，`lowest energy=2` |
| `2 1` | 12／3／2 | UNFINISHED | `steps=2, ok=2, rejected=0, ignored=0`，`lowest energy=12` |

另外執行 `java -cp out RoverTry`，貼上完整輸出；所有 `show` 行都必須是 `OK`。

再自訂**一條**你認為能抓到錯誤的測試（`MissionLog` 參數，或在 `RoverTry` 之外另寫的呼叫皆可），寫出內容、預期與實際結果，並說明它在測什麼。

## 3. 需求轉換與 AI 除錯

1. 講義 E1：把「樣本儲存艙」需求轉換成設計卡，六格都要填。
2. 講義 E2：對照模擬 AI 回覆，列出至少四處不符合需求的地方（每處附一個呼叫例子），並交出改寫後的 Prompt。
3. 講義 E3：從三種模擬 AI 修改中選一種，交四筆證據：反例的 AI 版結果、最小修正後結果、修正後 `2 2 2 2 2`、修正後 `3 9 0`。

可以只用教師的模擬建議，不要求 AI 帳號。若使用真實 AI，請在 `AI_USAGE.md` 記錄實際 Prompt、回覆摘要、採納或拒絕的理由。

## 4. 繳交檔案

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md      ← 講義 0、A–G 的作答，五條＋RoverTry＋一條自訂測試結果
   ├─ AI_USAGE.md        ← 依 AI使用紀錄表_範本_claude.md
   └─ java-05/
      ├─ README.md       ← 如何編譯執行、本週完成項目、三個類別各負責什麼
      └─ src/
         ├─ RawStep.java      RawStepDemo.java
         ├─ Battery.java      BatteryDemo.java
         ├─ Rover.java        MissionStep.java
         └─ MissionLog.java   RoverTry.java
```

不交 `out/`、`.class`、`.idea/`、`*.iml`。commit 訊息使用 `[manual]`、`[AI]`、`[fix]`、`[doc]` 前綴；有 `[AI]` 的 commit，`AI_USAGE.md` 必須有對應紀錄。

## 5. 驗收標準

| 項目 | 通過條件 |
|---|---|
| 編譯 | 全新 clone 後在 `java-05` 執行 `javac -encoding UTF-8 -d out src/*.java` 無錯誤 |
| 功能 | 五條必做測試的最終狀態、結果、摘要與上表一致；`3 9 0` 的回放三步與 `counts=[1, 0, 0, 1]` 正確；`RoverTry` 全部 `OK` |
| 封裝 | `Rover`、`MissionStep` 的欄位皆為 `private`，`MissionStep` 欄位為 `final`；沒有 setter；`MissionLog` 只透過方法讀寫狀態 |
| 自訂測試 | 有內容、預期、實際與說明 |
| 需求轉換與 AI | E1 六格齊全；E2 至少四項偏差且有呼叫例子、附改寫 Prompt；E3 四筆證據齊全且為實際結果 |
| 誠信 | 能逐行解釋三個類別；真實與模擬 AI 分開記錄 |
