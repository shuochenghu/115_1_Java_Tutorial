# 第 4 週作業：火星探測車任務日誌（claude 版本）

> 本說明初稿由 Anthropic Claude 生成，教師審閱後使用。截止時間與繳交平台依教師公告。

完成課堂的 `MissionLog.java`，讓任務可以依指令腳本執行、記錄每一步，並在結束後回放與統計。延續課堂專案，不必從空白檔重寫。

## 1. 要完成什麼？

從 `code/starter/MissionLog.java` 開始，完成 TODO 1–5。行動規則（`applyCommand`）與迴圈已提供，請勿修改。

| TODO | 要求 |
|---|---|
| 1 `toScript` | 把 `String[] args` 轉成同長度的 `int[]` |
| 2 寫入日誌 | 每一步（含被拒絕、返回）都加入 `commandLog`、`energyLog`（執行後的能源）、`resultLog` |
| 3 `printReplay` | 依序印出 `step N: command=…, result=…, energy=…` |
| 4 `countCommands` | 回傳 `int[4]`，`counts[k]` 為指令 k 出現的次數；0–3 以外的指令不計，且程式不能當掉 |
| 5 `printSummary` | 印出 `steps`、`ok`、`rejected`（非 OK 也非 RETURN）、`ignored`（腳本中未執行的指令數），以及 `lowest energy`（日誌為空時不印） |

不必處理非整數參數（`abc` 會丟 `NumberFormatException`，第 12 週再處理）。不可使用自訂類別、Lambda、Stream。

## 2. 必做測試

每列重新執行一次，把實際輸出貼到 `LAB_REPORT.md`：

| 參數 | 最終 energy／samples／actionCount | 結果 | 摘要 |
|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=0` |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | `steps=5, ok=5, rejected=0, ignored=0` |
| `3 9 0` | 20／0／0 | RETURNED | `steps=3, ok=0, rejected=2, ignored=0` |
| `1 1 1 1` | 2／6／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=1` |
| `2 1` | 12／3／2 | UNFINISHED | `steps=2, ok=2, rejected=0, ignored=0` |

另外自訂**一條**你認為能抓到錯誤的測試，寫出參數、預期與實際結果，並說明它在測什麼。

## 3. Prompt 設計與 AI 除錯

1. 講義 F1：用七個欄位寫出「記錄任務每一步歷程」的資料結構需求。
2. 講義 F2：完成三個模擬 AI 建議的判斷表。
3. 講義 F3：從三種模擬 AI 修改中選一種，交四筆證據：反例的修改前結果、最小修正後結果、修正後 `2 2 2 2 2`、修正後 `3 9 0`。

可以只用教師的模擬建議，不要求 AI 帳號。若使用真實 AI，請在 `AI_USAGE.md` 記錄實際 Prompt、回覆摘要、採納或拒絕的理由。

## 4. 繳交檔案

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md      ← 講義 A–H 的作答、五條＋一條測試結果
   ├─ AI_USAGE.md        ← 依 AI使用紀錄表_範本_claude.md
   └─ java-04/
      ├─ README.md       ← 如何編譯執行、本週完成項目
      └─ src/
         ├─ ArrayBasics.java
         ├─ ArrayReference.java
         ├─ ScriptArgs.java
         ├─ ListBasics.java
         └─ MissionLog.java
```

不交 `out/`、`.class`。commit 訊息使用 `[manual]`、`[AI]`、`[fix]`、`[doc]` 前綴；有 `[AI]` 的 commit，`AI_USAGE.md` 必須有對應紀錄。

## 5. 驗收標準

| 項目 | 通過條件 |
|---|---|
| 編譯 | 全新 clone 後 `javac -encoding UTF-8 -d out src/*.java` 無錯誤 |
| 功能 | 五條必做測試的最終狀態、結果、摘要與上表一致；`3 9 0` 的 `counts` 為 `[1, 0, 0, 1]` |
| 自訂測試 | 有預期、有實際、有說明 |
| Prompt 與 AI | F1 七欄齊全；F2 三列有判斷；F3 四筆證據齊全且有實際結果 |
| 誠信 | 能逐行解釋 `MissionLog.java`；真實與模擬 AI 分開記錄 |
