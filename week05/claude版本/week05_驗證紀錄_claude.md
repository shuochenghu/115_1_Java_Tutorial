# 第 5 週驗證紀錄（claude 版本）

驗證日期：2026-10-15。執行者：Claude（Claude Code）。以下為本次實際執行的結果，不是推測。

## 環境

- OpenJDK 22（build 22+36-2370），`javac 22`；以 `javac -encoding UTF-8 -Xlint:all` 在新的暫存目錄分開編譯。
- 本週不限定 JDK 版本。另以 `--release 11` 分開編譯 examples、starter、teacher，只確認較舊的語法與 API 也能通過；這不等於在 JDK 11 實機執行，也不是學生的編譯要求。
- Marp CLI v4.5.1（marp-core v4.4.0），以 `npx -y @marp-team/marp-cli@latest --no-stdin` 匯出 HTML 與逐頁 PNG。

## 已驗證

| 項目 | 方法 | 結果 |
|---|---|---|
| 兩個範例 | `examples/` 單獨編譯，執行 `RawStepDemo`、`BatteryDemo` | 無警告；輸出與講義 A、B 及教師筆記解答一致（`println(a)` 的雜湊碼每次不同） |
| 範例的刻意編譯錯誤 | 在副本拿掉 `BatteryDemo` 最後兩行註解，各自編譯 | `level has private access in Battery`；`constructor Battery in class Battery cannot be applied to given types;`（`required: int,int`、`found: no arguments`），與投影片、教師筆記一致 |
| 學生資料夾結構 | 4 個範例＋4 個 starter 檔放進同一個輸出目錄一起編譯 | 編譯成功，無警告 |
| 起始碼 | 執行 `MissionLog`、`MissionLog 3 9 0`、`RoverTry` | 可執行並安全結束；`MissionLog` 兩種參數都印出 `final energy=0, samples=0, actionCount=0 -> EXHAUSTED`、回放為空、摘要為 `TODO 7: summary`；`RoverTry` 出現 `DIFF`。與講義 0、D1 的說明一致 |
| 起始碼 TODO | 搜尋 `TODO 數字` | TODO 1–7 都有，分布於 `Rover`（1、2）、`MissionStep`（3、4、7）、`MissionLog`（5、6、7） |
| 教師解答：五條必做測試 | 逐條執行 `MissionLog` | 最終狀態、結果、摘要、`counts`、回放與作業說明的表格一致；輸出與第 4 週 claude 版的同組測試逐字相同 |
| 與第 4 週 claude 版比對 | 兩週教師版 `MissionLog` 以同樣參數執行並 `diff` 完整輸出：無參數、`2 2 2 2 2`、`3 9 0`、`1 1 1 1`、`2 1`、`0 1 1`、`9 9`、`1 2 3 1 1 2` | 8 組皆逐字相同 |
| 教師解答：邊界與完整輸出 | `MissionLogCheck` 共 27 項：14 組任務狀態（含初始 5／0、6／0、2／4 成功優先、1／0、0／0 與 20／5 一出發就停止、建構子修正 25／-3、空腳本、先返回）、能源快照、`describe`、`isRejected` 四種（含 `new String("OK")`）、`printSummary`、五條必做測試的完整輸出逐行比對、`countCommands` 遇到 9 | 27/27 passed |
| 檢查工具的偵測力 | 教師解答副本各做一個變異後執行 `MissionLogCheck`：①`printSummary` 的 `lowest` 初值改 0；②`isRejected` 與 ok 計數改用 `==` 比字串 | ① 21/27（`printSummary` 與五條完整輸出 FAIL）；② 25/27（`isRejected new String` 與 `printSummary` FAIL）。修訂前的 20 項版本對這兩個變異都是全數通過 |
| 檢查工具搭配起始碼 | starter 三個類別＋`MissionLogCheck` 一起編譯執行 | 編譯成功；4/27 passed，未完成項目顯示 FAIL，不中斷 |
| `RoverTry` 搭配教師解答 | starter 的 `RoverTry` 與教師版三個類別一起編譯執行 | 所有 `show` 行為 `OK`，與教師筆記 D2 的完整輸出相同 |
| 延伸題 `SampleBay` | `SampleBayCheck` | 4 項 PASS（存入、填滿、拒絕 0／負數／超量、卸下、容量修正 0→1、15→10） |
| 模擬 AI 錯誤 1（`MissionStep` 存 `Rover`） | 副本執行無參數、`3 9 0`、`2 2 2 2 2` | 無參數：回放能源三步都是 6，最後一行與摘要仍正確；`2 2 2 2 2`：回放能源五步都是 10；`3 9 0` 看不出差異 |
| 模擬 AI 錯誤 2（`charge` 去掉已滿檢查） | 同上 | `3 9 0`：step 1 變成 `result=OK`，`ok=1, rejected=1`，`actionCount=1`；無參數與 `2 2 2 2 2` 正常 |
| 模擬 AI 錯誤 3（建構子先存再修正） | 同上，再執行 `RoverTry` | 三組 `MissionLog` 參數皆正常；`RoverTry` 的 `new Rover(25, -3)` 為 `25/-3/0 ... DIFF` |
| 講義的編譯錯誤題 | 副本中：`Rover.explore();`、`MissionStep` 欄位為 `final` 時刪掉一行指定、`final` 欄位寫成 `command = command;`、外部存取 `rover.energy`、`new Rover()` | 依序為 `non-static method explore() cannot be referenced from a static context`、`variable energyAfter might not have been initialized`、`variable command might not have been initialized`、`energy has private access in Rover`、`constructor Rover in class Rover cannot be applied to given types;`，與講義、教師筆記一致 |
| `--release 11` | examples、starter、teacher 分開編譯，執行 `MissionLogCheck` | 編譯成功，27/27 passed |
| 程式輸出字元 | 檢查所有 `println` 字串 | 只用英數字，與前幾週一致；中文只出現在註解 |
| Markdown | 檢查本機連結與 code fence 配對 | 無斷連結，fence 成對 |
| 投影片 | 匯出 35 頁 HTML 與 PNG，逐頁目視 PNG | 每頁都有 `claude` 徽章；有程式的頁面有範例檔標籤。首輪發現 4 頁需修正（第 9 頁箭頭圖對不齊、第 19 頁 ▶ 顯示為表情符號、第 25 頁直接列出 TODO 6 答案、第 31 頁表格內程式換行），修正後重新匯出並再次目視這 4 頁 |

## 與 Codex 版比較後的修訂

依使用者要求，另派一個唯讀 agent 比較本版與 `week05/codex版本/`，依共用技能規範判斷本版是否需要修改。agent 回報：主線設計、程式正確性與第 4 週 claude 版的銜接成立；兩版執行契約不同（本版用命令列參數腳本、空日誌不印最低能源）屬既定差異，不算缺陷。它實際執行兩版教師檢查（本版 20/20、Codex 版 `MissionStepCheck` 8/8、`MissionLogCheck` 15/15）並重現三種模擬 AI 錯誤，結果與本紀錄一致。

採納的建議與實際修改：

| 建議 | 修改 |
|---|---|
| 教師檢查工具測不到 TODO 6–7 與 `==` 比字串（agent 以兩個變異實測仍 20/20） | `MissionLogCheck` 新增 `new String("OK")` 的 `isRejected` 檢查、`printSummary` 檢查、五條必做測試完整輸出逐行比對（借鏡 Codex 版擷取輸出的做法，期望值用本版自己的契約），共 27 項；README、教師筆記同步項數與批改用法 |
| 講義「貼最後兩行」與作業「最後四行」不一致，且看不到 `3 9 0` 的回放 | 講義、作業統一為「回放之後的四行」，`3 9 0` 另貼完整輸出 |
| D 段 45 分鐘份量過重，課綱主題 E 段只有 20 分鐘 | D2-2 改為教師示範、D3-1 改為選做；D 段 40 分鐘、E 段 25 分鐘（E1 8、E2 10、E3 7）；講義、教師筆記、投影片時間表同步 |
| E3 副本位置與 IDEA 建立 repo 的提醒 | 講義 E3 註明副本放在 `115_1_Java` 以外且不 commit；C 段補「A、B 範例不必開 IDEA」「IDEA 詢問建立 Git repository 時選否」；投影片同步 |

考慮後不修改：`Rover` 在能源 0 時仍允許 `charge()`（刻意留給第 6 週的責任討論，四處說法一致）、迴圈改為執行前判斷停止（從 20／0 出發與第 4 週逐字相同）、不改成 Codex 版把狀態留在 `MissionLog` 的設計。

修訂後重新驗證：examples、starter、teacher 與學生資料夾組合分開編譯皆無警告；`MissionLogCheck` 27/27、`SampleBayCheck` 4 項 PASS；投影片重新匯出 35 頁，目視改動的第 5、19、31 頁（第 22 頁只加講者備註）；Markdown 連結、code fence、行尾空白檢查無問題；本目錄沒有 `.class` 或輸出目錄。

## 未驗證

- 教室電腦實際安裝的 JDK 版本與終端機編碼。
- IntelliJ IDEA 的實際操作畫面（Sources Root、Run Configuration、Ctrl＋左鍵、Alt＋7）；步驟依第 1 週流程與一般操作撰寫，選單名稱可能因版本略有差異。VS Code 執行設定也未重新操作。
- GitHub Desktop 的 push 與全新 clone 流程（沿用第 1–4 週流程，本週未重新操作）。
- 150 分鐘時間配置的課堂試做。
- 學生使用真實 AI 工具時的回覆內容；講義中的 AI 回覆都是教師設計的模擬內容。
- 暫存編譯與測試副本放在工作階段的暫存目錄，未放入本目錄。
