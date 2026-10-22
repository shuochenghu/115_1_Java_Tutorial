# 第 6 週驗證紀錄（claude 版本）

驗證日期：2026-10-22。執行者：Claude（Claude Code）。以下為本次實際執行的結果，不是推測。暫存編譯目錄、測試副本與 PNG 都放在工作階段的暫存目錄，未放入本目錄。

## 環境

- OpenJDK 22（build 22+36-2370），`javac 22`；以 `javac -encoding UTF-8 -Xlint:all` 在新的暫存目錄分開編譯，無警告。
- 本週不限定 JDK 版本。另以 `--release 11` 分開編譯 examples、starter、teacher、teacher/club，只確認較舊的語法與 API 也能通過；這不等於在 JDK 11 實機執行，也不是學生的編譯要求。
- PlantUML 1.2026.9（`plantuml-mit-1.2026.9.jar`，官方 GitHub Releases，17,893,617 bytes），以 `-P layout=smetana` 產生 SVG／PNG，本機未安裝 Graphviz。
- Marp CLI v4.5.1（marp-core v4.4.0），以 `npx -y @marp-team/marp-cli@latest --no-stdin` 匯出 HTML 與逐頁 PNG。

## 程式

| 項目 | 方法 | 結果 |
|---|---|---|
| 範例 | `examples/` 單獨編譯，執行 `RelationDemo` | 輸出 `true`、`false`、`true`、`4`、`+6`、`true`、`4`，與講義 B 段、教師筆記一致 |
| 學生資料夾 | 4 個範例＋5 個 starter 放進同一個 `src` 一起編譯 | 9 個檔案編譯成功，無警告 |
| 起始碼 | 執行 `MissionLog`、`MissionLog 3 9 0`、`RoverTry` | 可執行並安全結束；兩種參數都是空回放、`steps=0, ok=0, rejected=0, ignored=3`、`final energy=20, samples=0, actionCount=0 -> UNFINISHED`；`RoverTry` 有 6 行 `DIFF`，`done: 14/7/1`。與講義 0、D1 及教師筆記一致 |
| 起始碼 TODO | 搜尋 `TODO 數字` | TODO 1–2 在 `Rover`，3–6 在 `Mission`；TODO 7 在 `DESIGN.md`（文件） |
| 教師解答 | `MissionCheck` | 31/31 passed：14 組任務狀態、4 項停止規則、4 項 `Mission` 計算、5 條必做測試完整輸出逐行比對、4 項設計檢查 |
| 與第 5 週 claude 版比對 | 兩週教師版 `MissionLog` 以同樣參數執行並 `diff` 完整輸出：無參數、`2 2 2 2 2`、`3 9 0`、`1 1 1 1`、`2 1`、`0 1 1`、`9 9`、`1 2 3 1 1 2`、`3 3 1 1 1 2 2`、`1 1 1 1 1 1` | 10 組皆逐字相同 |
| 完成後的學生資料夾 | starter 的 `MissionStep`、`MissionLog`、`RoverTry` 加上教師版 `Rover`、`Mission` 與範例一起編譯 | 五條必做測試的摘要、最低能源、最後一行與作業說明表格一致；`RoverTry` 12 行全為 `OK` |
| 檢查工具搭配起始碼 | starter 四個類別＋`MissionCheck` | 首次執行時在「能源快照」項目因 `getStep(0)` 越界而中斷；改為依 `getStepCount()` 讀取後重跑，5/31 passed，不中斷 |
| 檢查工具的偵測力 | 教師解答副本各做一個變異後執行 `MissionCheck` | 拿掉 `execute` 停止檢查 28/31；拿掉 `run` 停止檢查 26/31；`lowestEnergy` 初值 0 24/31；`explore` 保持 public 30/31；加上 `getSteps()` 29/31；結果判斷順序顛倒 30/31。六種變異都被抓到 |
| D5 實驗 | 拿掉 `run` 停止檢查的副本執行 `1 1 1 1` 與無參數 | `1 1 1 1` 多出 `step 4: command=1, result=STOPPED, energy=2`，`counts[0..3]=[0, 4, 0, 0]`，`steps=4, ok=3, rejected=1, ignored=0`，最後一行仍為 `2/6/3 SUCCESS`；無參數輸出不變。與講義 D5、投影片、教師筆記一致 |
| 講義 A2 | 第 5 週 `Rover` 加上五行呼叫一起編譯 | 第 1、4、5 行錯誤：`energy has private access in Rover`、`non-static method explore() cannot be referenced from a static context`、`constructor Rover in class Rover cannot be applied to given types;`；第 2、3 行通過。與教師筆記一致 |
| 講義 C1 | 第 5 週 `Rover`：`new Rover(0, 0).charge()` | 印出 `OK`，能源 5、行動次數 1 |
| private 行動 | 第 6 週 `Rover` 外部呼叫 `explore()` | `explore() has private access in Rover`，與教師筆記常見錯誤表一致 |
| 作業延伸參考 | `teacher/club/` 編譯，執行 `ClubCheck` | 17/17 passed（借出、已借出、上限、未登記器材、拒絕不建立紀錄、歸還、重複歸還、歸還後再借、歷史筆數 4 與同時 2 件、報表、空報表） |
| `--release 11` | 四組分開編譯，執行 `MissionCheck`、`ClubCheck` | 編譯成功；31/31、17/17 |
| 程式輸出字元 | 檢查所有 `println` 字串 | 只用英數字；中文只出現在註解 |

已知限制：`countOk` 改用 `==` 比字串時，結果字串都是常值，`MissionCheck` 抓不到，已寫入教師筆記。

## 類別圖

| 項目 | 方法 | 結果 |
|---|---|---|
| 12 個 `.puml` | PlantUML 以 `-pipe` 逐檔轉 SVG 與 PNG（資料夾名稱含中文時，檔案模式會讀錯路徑） | 全部成功；PNG 逐張目視 |
| 圖與程式一致 | 對照 `week06_mission_claude`、`week06_changes_claude` 與 `code/teacher`；`club_ref_claude` 與 `code/teacher/club` | 欄位、方法、可見性、static 一致。首輪發現 `relations_claude` 漏了 `Flashlight.getName()`、`week06_mission_claude` 漏了 `MissionLog ..> MissionStep`（`printReplay` 呼叫 `getStep(i).describe()`），修正後重新產生 |
| Markdown 內的 PlantUML 區塊 | 以行為單位抽出 `@startuml` 開頭的區塊，逐一交給 PlantUML | 10 個區塊（DESIGN 範本 6、講義 2、作業 1、投影片 1）全部無語法錯誤；PlantUML 對錯誤語法回傳非 0，已用刻意錯誤的例子確認 |
| 範本的空白區塊 | 檢視轉出的 PNG | 原本空白區塊產生空圖、只有註解的區塊產生 PlantUML 歡迎頁；改為放一行提示 note，重新確認 |
| 講義與作業中的模擬 AI 原始碼 | 去除註解與空行後，和 `samplebay_ai_claude.puml`、`club_ai_claude.puml` 逐行比較 | 相同 |
| PowerShell 產圖指令 | README 中的 `cmd /c` 轉向指令實際在 Windows PowerShell 5.1 執行 | 產出的 SVG 與 Git Bash 產出的位元組相同（SHA-256 相同） |

## 投影片

- 匯出 41 頁 HTML 與逐頁 PNG（`--allow-local-files`），以每張 4 頁的縮圖表巡視全部 41 頁：每頁都有 `claude` 徽章，有程式或圖的頁面有檔案標籤，沒有文字、表格或程式碼超出頁面。
- 首輪發現 4 頁的類別圖投影時太小：第 13 頁（第 5 週完整圖）、第 15 頁（關聯與相依）、第 24 頁（第 6 週完整圖縮到無法閱讀）、第 36 頁（模擬 AI 圖細長）。修正：第 24 頁改用只含 `Mission`、`Rover` 的 `week06_changes_claude.svg`；模擬 AI 圖加上 `left to right direction` 改為橫向，並同步講義 E2 的原始碼；其餘兩頁放大圖片欄位。修正後以原尺寸重新目視這 4 頁，第 24 頁另修一處換行。
- HTML 引用的 8 個 `diagrams/*.svg` 都存在。

## 文件

- Markdown 本機連結無斷連；code fence 皆成對（以行首 ```` ``` ```` 判斷）；`.md`、`.java`、`.puml` 無行尾空白。
- 講義題號、TODO 1–7、作業表格、教師筆記解答、投影片 TODO 表彼此一致；五條必做測試的數字與第 5 週作業表格相同。
- 本目錄沒有 `.class` 或輸出目錄。

## 未驗證

- **PlantUML for GitHub 擴充功能的實際顯示**：沒有在 Chrome／Firefox 安裝擴充功能，也沒有把 `DESIGN.md` 推上 GitHub 檢視。擴充功能使用瀏覽器內編譯的 PlantUML 引擎，版本可能與本機 1.2026.9 不同；`skinparam classAttributeIconSize 0`、`hide circle`、`left to right direction` 與中文 note 在擴充功能中的效果，課前需由教師實際確認。擴充功能的說明依其官方 GitHub 頁面與 Chrome 線上應用程式商店頁面（2026-10-22 查閱）。
- 教室電腦能否安裝瀏覽器擴充功能。
- IntelliJ IDEA 的實際操作（開啟專案、Alt＋F7 Find Usages、課後選做的 Diagrams → Show Diagram 是否存在於學生的版本）；VS Code 也未重新操作。
- GitHub Desktop 的 push 與全新 clone 流程（沿用第 1–5 週流程，本週未重新操作）。
- 150 分鐘時間配置的課堂試做。
- 學生使用真實 AI 工具時的回覆內容；講義與作業中的 AI 類別圖都是教師設計的模擬回覆。
- 樣本艙需求只有參考設計圖（`samplebay_ref_claude`），沒有參考程式；課後選做的實作與 `1 1 4 1 1 4` 的預期回放未驗證。
- 本次未建立 commit，也未 fetch；不能宣稱與遠端同步。

## 目前進度與下一步（2026-10-22）

本週 claude 版教材已完成初版（README 所列檔案齊全，程式、類別圖、投影片已驗證如上）。接續工作時依序處理：

1. **確認擴充功能顯示**：在 Chrome 或 Firefox 安裝「PlantUML for GitHub」，把 `DESIGN_範本_claude.md`（或任一含 ```` ```plantuml ```` 的檔案）推上 GitHub 檢視。重點看：`+`／`-` 是否顯示為符號（`skinparam classAttributeIconSize 0`）、`hide circle`、`{static}` 底線、`left to right direction`、中文 note。若顯示與本機 SVG 不同，修改講義 A 段的寫法說明與範本，並更新本紀錄。
2. **確認教室電腦能否安裝擴充功能**；不能時講義 G 段已有替代做法，必要時再調整作業說明的「圖能顯示」驗收項。
3. **IntelliJ 操作**：實際試 Alt＋F7（Find Usages）與課後選做的 Diagrams → Show Diagram，選單名稱不同時修改講義 D2、課後選做與投影片第 26 頁。
4. 教師審閱講義、教師筆記、投影片後，依需要修訂並重新匯出 HTML（README 有指令）。

重新產生類別圖需要 PlantUML jar：本次使用的 `plantuml-mit-1.2026.9.jar` 放在作者電腦的暫存目錄，沒有放進專案；換電腦時從 [PlantUML GitHub Releases](https://github.com/plantuml/plantuml/releases) 重新下載，指令見 README「重新產生類別圖與投影片」。只改文字、不改圖時不需要它。
