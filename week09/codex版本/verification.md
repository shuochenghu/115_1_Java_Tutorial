# 第 9 週 Codex 教材驗證

日期為 2026-11-10。以下為作者在本機的檢查。

## 程式與文件

在 Windows 使用 OpenJDK 22（22+36-2370）、javac 22、Python 3.14.0。以 `python -X utf8 tools/verify.py` 執行下表，全部通過。工具以 UTF-8、`-Xlint:all` 在獨立暫存目錄編譯，採用 JDK 預設目標。另依 README 的 PowerShell 指令，在新建學生目錄以 `src/*.java` 編譯 12 個起始檔，兩車結果符合基準，樣本報表為 `NOT_READY`。

| 項目 | 期望與驗收方式 |
|---|---|
| 起始碼、教師解答、獨立示範 | 分開編譯。起始報表為 `NOT_READY`，完成版與講義三行一致 |
| 第 8 週回歸 | `MissionLog`、`CompareMissions`、`HeavyRoverTry` 完整輸出比對前週。另執行 15 項一般車輸出、16 項任務狀態、51 項多型與狀態檢查 |
| 學生完成路徑 | 只替換包含 TODO 的六個檔案後重編譯，主線四個入口與教師解答一致 |
| 報表與遷移 | 6、4、0 三行，另測 9 份樣本盒、重讀及車輛狀態維持。遷移參考跑 7、0、7 |
| 兩車能源不足 | 初始 5 格、0 份執行探索，日誌記拒絕，兩車維持 5/0/0、`UNFINISHED` |
| 示範 | 範例一、二、三的輸出見下節「examples 拆成三個範例」。通知回放印 `能源偏低，步驟=2 能源=2` |
| 編譯反例 | 隔離測試抽象類別建立、漏實作抽象方法、漏寫 `public`。模擬 AI 的兩個盒子呼叫無法編譯，單車對照通過，修正參數型態後原三行通過 |
| 靜態核對 | `Mission` 與 `MissionStep` 逐位元組比對第 8 週。檢查 Markdown 本機連結、程式區塊、UTF-8 與 Java 檔名 |

## 圖與投影片

使用本機 PlantUML 1.2026.9 jar 與 Smetana 產出 SVG。以 Marp CLI 4.5.1、Marp Core 4.4.0 匯出正式 HTML 及檢查用 bare HTML。使用本機 Chrome 的獨立未登入設定檔渲染 19 頁。自動檢查無溢出及圖檔載入問題。以聯絡表巡視全 19 頁，再放大檢視第 12 頁的程式與表格、第 14 頁的 Prompt 表和第 17 頁的 UML。圖中文字和箭頭在檢視影像中可辨識。

## 驗證界線

課堂節奏與課後時間是備課估計。雙 IDE 的實際 GUI 操作、教室投影、學生試教、真實 AI 對話和 GitHub 登入及推送待教室驗證。模擬 AI 回覆由教師撰寫。本週驗收針對 README 的輸入範圍。

## claude 投影片與類別圖（2026-11-10）

教師要求依本版教材另做一份投影片，說明時先講完 `examples/` 再講 `starter/`，並以 UML 說明與第 8 週的演進。Anthropic Claude（Claude Code）新增 `week09_slides_codex_claude.md`／`.html`（50 頁，每頁 `by-claude`），以及 `diagrams/` 下 `evolution_w08_claude`、`evolution_w09_claude`、`starter_todo_claude`、`fixture_claude`、`ai_param_claude` 五組 puml／SVG。未修改本版其他教材、程式或 `week09_slides_codex.*`；本節與 README 的索引列是應教師要求補上的。

- 程式：在暫存目錄以 OpenJDK 22（22+36-2370）、`javac -encoding UTF-8` 分開編譯 examples、starter、teacher。投影片引用的輸出均為實測：`fixture samples=2` 兩行、`MissionLog`／`CompareMissions` 基準、`HeavyRoverTry` 15 行 OK、起始 `NOT_READY`、完成版 6／4／0 三行、`LOW step=2 energy=2`。
- 編譯診斷：投影片引用的錯誤訊息都在副本實測，包括 `new Rover(...)`、刪除 `StandardRover` 覆寫、`SampleTray` 漏寫 `public`、模擬 AI 參數型態，以及兩項新增情況：先做 TODO 3b 而 3a 尚未完成時的 `@Override` 錯誤、examples 方法名打錯時的兩個診斷，另有 `new SampleSource()`。
- 演進表：以 diff 比對第 8 週與第 9 週 teacher；`Mission`、`MissionStep`、`HeavyRoverTry` 相同，`HeavyRover` 只差註解。
- 圖與投影片：PlantUML 1.2026.9（Smetana）產生 SVG；Marp CLI（npx 最新版）以 `--html --allow-local-files` 匯出 HTML 與 50 張 PNG。逐頁看過聯絡表，修正圖過小、一頁溢出及一頁表格超出右緣後重新匯出，並再次檢視修改過的頁面。
- 未驗證：教室投影、雙 IDE GUI、學生試教、真實 AI 與 GitHub 操作。其他 JDK 的錯誤訊息字句可能略有不同。

## 2026-11-11 繁體中文輸出驗證

- OpenJDK 22，使用 javac -encoding UTF-8 -Xlint:all 分開編譯範例、學生起始碼及教師解答。以 UTF-8 擷取主控台輸出。
- 執行該週 tools/verify.py，中文預期輸出、教師檢查與原有回歸案例通過。
- 歷史驗證中的英文輸出反映當時的程式，現行預期輸出以本週講義和教師筆記為準。
- Marp CLI 4.5.1 重新匯出 2 份 HTML（19/50 頁）。以無頭 Chromium 檢查程式碼區塊與表格的版面邊界，未偵測到溢出。

## examples 拆成三個範例（2026-11-19）

教師要求把抽象類別、介面與巢狀／匿名類別分開在不同範例說明。Anthropic Claude（Claude Code）改寫 `examples/`：

- 範例一 抽象類別：新增 `Parcel`（抽象）、`NormalParcel`、`ExpressParcel`、`ParcelDemo`。
- 範例二 介面：新增 `Weighable`、`Suitcase`、`Cat`、`ScaleDemo`。
- 範例三 巢狀與匿名類別：`FixtureDemo` 改用範例二的 `Weighable`，刪除 `examples/SampleSource.java`（原本與 starter TODO 3a 的答案同一行）。
- 新增 `examples/README_claude.md`。同步修改 README、student_lab（B、C 開頭各加一題範例預測，D 改為 `getWeight()`）、teacher_notes、tools/README、tools/verify.py、`week09_slides_codex.md` 第 4、8、13 頁與其 HTML（Marp CLI 4.5.1），以及 claude 投影片第一部分（50→61 頁）與 `diagrams/parcel_claude`、`scale_claude`、更新後的 `fixture_claude`。starter、teacher、ai、assignment 未改。

實測（OpenJDK 22，`javac -encoding UTF-8 -Xlint:all`，無警告）：

| 入口 | 輸出 |
|---|---|
| `ParcelDemo` | `書籍 3公斤 運費=90元`、`藥品 3公斤 運費=135元` |
| `ScaleDemo` | `行李箱 重量=13公斤`、`貓 重量=5公斤`、`咪咪：喵～`、`同一隻貓用 Weighable 變數讀取：5公斤` |
| `FixtureDemo` | `測試資料重量=2` 兩行 |

編譯實驗在副本實測：`new Parcel(...)` → `Parcel is abstract; cannot be instantiated`；刪除 `ExpressParcel.ratePerKg()` → `ExpressParcel is not abstract and does not override abstract method ratePerKg() in Parcel`；`item.meow()` → `cannot find symbol`；把 `NormalParcel` 傳給 `weigh` → `NormalParcel cannot be converted to Weighable`；`Suitcase.getWeight()` 去掉 `public` → `attempting to assign weaker access privileges; was public`；`new Weighable()` → `Weighable is abstract; cannot be instantiated`。`tools/verify.py` 新增三個範例的輸出與前三項編譯實驗檢查，全部通過。兩份投影片重新匯出後逐頁檢視改動頁面，修正溢出後再確認。未在教室投影或 IDE GUI 中驗證。

## 2026-11-19 沿用第 8 週指令 4（Claude Code 修改）

教師要求把第 8 週主線新增的 TODO 4 帶入本週：`starter/HeavyRover.java` 與 `teacher/HeavyRover.java` 加上同一個 `execute` 覆寫（指令 4 交給 `drill()`，其他指令交回 `super.execute`），兩份仍逐位元組相同。`Rover`、`Mission`、TODO 檔與既有檢查程式未改。

`tools/verify.py` 新增 `DrillProbe`：重型車 20/0 跑 `{4, 1}` 為 `成功 6/6/2/1 任務成功 [0, 1, 0, 0]`；一般車為 `無效指令 14/2/1 任務未完成`；已達標、能源不足與掃描分別為 `任務已停止`、`能源不足`、`成功`。OpenJDK 22 執行全部通過；既有三個入口不含指令 4，與前週比對的輸出不變，15／16／51 項數量不變。投影片與學生講義未新增指令 4 活動。
