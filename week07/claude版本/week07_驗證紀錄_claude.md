# 第 7 週驗證紀錄（claude 版本）

驗證日期：2026-10-27。執行者：Claude（Claude Code）。以下為本次實際執行的結果，不是推測。暫存編譯目錄、測試副本與 PNG 都放在工作階段的暫存目錄，未放入本目錄。

## 環境

- OpenJDK 22（build 22+36-2370），`javac 22`；以 `javac -encoding UTF-8 -Xlint:all` 在新的暫存目錄分開編譯，無警告。
- 本週不限定 JDK 版本。另以 `--release 11` 分開編譯 examples、starter、teacher、teacher/club，只確認較舊的語法與 API 也能通過；這不等於在 JDK 11 實機執行，也不是學生的編譯要求。
- PlantUML 1.2026.9（`plantuml-mit-1.2026.9.jar`，第 6 週下載的同一個檔案），以 `-P layout=smetana` 產生 SVG／PNG，本機未安裝 Graphviz。
- Marp CLI v4.5.1（marp-core v4.4.0），以 `npx -y @marp-team/marp-cli@latest --no-stdin` 匯出 HTML 與逐頁 PNG（`--allow-local-files`）。
- JEP 513（Flexible Constructor Bodies）的狀態與規則依 openjdk.org/jeps/513（2026-10-27 查閱）：Closed／Delivered，JDK 25。

## 程式

| 項目 | 方法 | 結果 |
|---|---|---|
| 範例 | `examples/` 單獨編譯，執行 `InheritDemo` | 輸出與教師筆記 A 段一致（建構子順序、`+12`、`+8`、`28`、`parent of Member: java.lang.Object`） |
| 學生資料夾 | 3 個範例＋7 個 starter 放進同一個 `src` 一起編譯 | 10 個檔案編譯成功，無警告 |
| 起始碼 | 執行 `MissionLog`、`MissionLog 3 9 0`、`RoverTry`、`HeavyRoverTry` | `MissionLog` 與第 6 週完成版相同；`RoverTry` 12 行全 `OK`；`HeavyRoverTry` 10 行 `DIFF`，`big` 一行 `OK`。與講義 0、D1 一致 |
| 起始碼 TODO | 搜尋 `TODO 數字` | TODO 1–2 在 `Rover`，3–4 在 `HeavyRover`；TODO 5 在 `DESIGN.md`（文件） |
| 完成後的學生資料夾 | starter 加上教師版 `Rover`、`HeavyRover` | `HeavyRoverTry` 15 行、`RoverTry` 12 行全 `OK`；五條必做測試的最後一行與作業說明表格一致 |
| 教師解答 | `HeavyRoverCheck` | 44/44 passed |
| 與第 6 週 claude 版比對 | 兩週教師版 `MissionLog` 以同樣參數執行並 `diff` 完整輸出：無參數、`2 2 2 2 2`、`3 9 0`、`1 1 1 1`、`2 1`、`0 1 1`、`9 9`、`1 2 3 1 1 2`、`3 3 1 1 1 2 2`、`1 1 1 1 1 1` | 10 組皆逐字相同 |
| 檢查工具的偵測力 | 教師解答副本各做一個變異後執行 `HeavyRoverCheck` | `drill` 漏停止檢查 41/44；子類別多宣告 `energy` 43/44；`spendEnergy` 改 public 43/44；`spendEnergy` 不足時扣到 0 40/44；`drill` 漏 `countAction` 40/44；`energy` 改 protected 43/44；`explore` 得 3 份，任務回歸多項失敗。七種變異都被抓到 |
| 檢查工具搭配起始碼 | starter 五個類別＋`HeavyRoverCheck` | 無法編譯（`Rover` 沒有 `spendEnergy`）。已寫入教師筆記：未完成 TODO 1 時以編譯錯誤呈現 |
| 作業參考 | `teacher/club/` 編譯，執行 `ShelfTry`、`ShelfCheck` | `ShelfTry` 9 行全 `OK`；`ShelfCheck` 11/11 |
| 作業 starter | `starter/club/` 單獨編譯 | 如預期失敗：`cannot find symbol ... class Camera`（作業說明已標示要等兩個類別寫好才複製 `ShelfTry`） |
| 講義 A2 | 範例加上四行呼叫一起編譯 | `amy.claimBonus()`：`cannot find symbol`；`bob.points = 99`：`points has private access in Member`；另外兩行通過。另確認 `bob.addPoints(5)` 從 `InheritDemo` 呼叫可以編譯（同套件） |
| 講義 B2 | 註解掉 `VipMember` 的 `super(id, name)` | `constructor Member in class Member cannot be applied to given types;`、`required: String,String`、`found: no arguments` |
| 講義 B3 | `addPoints(BONUS)` 改成 `points += BONUS` | `points has private access in Member` |
| super 不在第一行（未放入講義實驗） | `this.bonusCount = 0;` 移到 `super(id, name);` 之前，JDK 22 編譯 | `cannot reference this before supertype constructor has been called` 與 `statements before super() is a preview feature and is disabled by default.`；依 JEP 513，JDK 25 起這段可以編譯，所以講義只寫規定與補充說明 |
| 講義 D5 | 另一個類別呼叫 `rover.spendEnergy(5)` | 可以編譯，回傳 `true`、能源 15 |
| 課末檢核第 5 題 | `new Mission(new HeavyRover(20, 0))` 跑預設腳本 | 可以編譯；`SUCCESS`、3 步 |
| 講義 E（模擬 AI 的 `HeavyRover`） | 搭配教師版 `Rover`、`HeavyRoverTry`，依講義步驟操作 | 原樣：`constructor Rover in class Rover cannot be applied to given types;`（`required: int,int`、`found: no arguments`）。加上 `super` 後：15 行中 7 行 `DIFF`，完整輸出已寫入教師筆記。刪掉重複欄位後：`energy has private access in Rover` 兩處、`samples has private access in Rover` 一處 |
| 作業第 3 部分（模擬 AI 的 `Camera`） | 搭配 `Equipment`、教師版 `Projector`、`ShelfTry` | 能編譯；3 行 `DIFF`（`null null | cards=2`、`-1`、`null null | cards=-1`）；搭配 `ShelfCheck` 7/11（含「no hidden copies」失敗） |
| `--release 11` | 四組分開編譯，執行 `HeavyRoverCheck`、`ShelfCheck` | 編譯成功；44/44 |
| 程式輸出字元 | 檢查 `println` 字串 | 只用英數字；中文只出現在註解 |

## 類別圖

| 項目 | 方法 | 結果 |
|---|---|---|
| 7 個 `.puml` | PlantUML 以 `-pipe` 逐檔轉 SVG 與 PNG | 全部成功；PNG 逐張目視 |
| 圖與程式一致 | 對照 `member_claude` 與 `examples/`、`heavy_claude` 與 `teacher/Rover`、`HeavyRover`、`club_claude` 與 `teacher/club/` | 欄位、方法、可見性、static 一致 |
| 修正 | 首輪目視 | `isa_hasa_claude` 的四個 note 擠在一起，改成在箭頭上標 is-a／has-a；`ai_heavy_claude` 改為 `left to right direction`；另加投影片用的節錄圖 `heavy_focus_claude` |
| Markdown 內的 PlantUML 區塊 | 以行為單位抽出 `@startuml` 開頭的區塊，逐一交給 PlantUML 並目視 PNG | 3 個區塊（DESIGN 範本 2、作業說明 1）無語法錯誤；範本的空方框與 note 正常顯示，沒有出現 PlantUML 歡迎頁 |
| 作業說明的類別圖原始碼 | 去除註解與空行後和 `club_claude.puml` 比較 | 相同 |
| 講義與作業中的模擬 AI 程式碼 | 去除註解與空行後和 `code/ai/HeavyRover.java`、`Camera.java` 比較 | 相同 |

## 投影片

- 匯出 39 頁 HTML 與逐頁 PNG，以每張 12 頁的縮圖表巡視全部 39 頁：每頁都有 `claude` 徽章，有程式或圖的頁面有檔案標籤。
- 首輪發現 3 頁問題：第 10 頁表格內的 `**粗體**` 因緊鄰全形字元沒有生效（改用 `<b>`）；第 22 頁完整 `Rover` 圖縮到難以閱讀（改用橫向節錄圖，第一次改版圖片太高、條列超出頁面，再改為兩欄）；第 33 頁模擬 AI 圖過小（改為橫向）。修正後以原尺寸重新目視這 3 頁。
- HTML 引用的 6 個 `diagrams/*.svg` 都存在。

## 文件

- Markdown 本機連結無斷連（`week07_交接_claude.md` 建立後重新檢查）；code fence 皆成對；`.md`、`.java`、`.puml` 無行尾空白。
- 講義題號、TODO 1–5、作業表格、教師筆記解答、投影片 TODO 表彼此一致；五條必做測試的數字與第 5、6 週作業表格相同。
- 本目錄沒有 `.class` 或輸出目錄。
- 第 6 週與 Codex 版檔案未修改（本次只新增 `week07/claude版本/`）。

## 未驗證

- **IntelliJ IDEA**：Ctrl＋H（Type Hierarchy）、Ctrl＋B（Go to Declaration）未在本機實際操作；選單名稱或快速鍵若不同（例如 macOS 鍵盤配置），修改講義 D 段、課後選做與投影片第 23 頁。
- **PlantUML for GitHub 擴充功能**：第 6 週留下的待辦仍未完成，本週的繼承箭頭 `<|--`、`#`、`left to right direction` 在擴充功能中的顯示也未確認。
- **JDK 25 實機**：本機只有 JDK 22；JDK 25 的行為依官方 JEP 頁面描述，未實際編譯。學生若使用 JDK 25 以上，講義 B2、E2 的錯誤訊息仍應相同（都是「沒有無參數建構子」），但未實測。
- **真實 GitHub 操作、真實 AI 服務、課堂試教**：未進行。150 分鐘配置是備課估計。

## 目前進度與下一步（2026-10-27）

本週 claude 版教材已完成初版（README 所列檔案齊全，程式、類別圖、投影片已驗證如上）。接續工作時依序處理：

1. 教師審閱講義、教師筆記、投影片，特別是本週的範圍決定（只新增、不覆寫；「探索得 3 份」留到第 8 週）。
2. 在教室電腦確認 IntelliJ 的 Ctrl＋H 與 PlantUML for GitHub 擴充功能（與第 6 週的待辦一起）。
3. 修改程式後重跑 `HeavyRoverCheck`、`ShelfCheck`；修改圖或投影片後重新產圖與匯出 HTML（README 有指令）。
