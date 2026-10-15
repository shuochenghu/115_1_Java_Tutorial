# 第 4 週驗證紀錄（claude 版本）

驗證日期：2026-10-06。執行者：Claude（Claude Code）。以下為實際執行的結果，不是推測。

## 環境

- OpenJDK 22（build 22+36-2370），以 `javac -encoding UTF-8 -Xlint:all` 編譯執行。
- 本週不限定 JDK 版本。另以 `--release 11` 編譯範例、起始碼與解答，確認舊版語法也能通過，`MissionLogCheck` 同為 12/12。
- Marp CLI（`npx @marp-team/marp-cli@latest`）匯出 HTML，並逐頁輸出 PNG 目視檢查。

## 已驗證

| 項目 | 方法 | 結果 |
|---|---|---|
| 四個範例 | 分別編譯執行；`ScriptArgs` 另跑 `1 1 2` | 無警告；輸出與講義 A–D、教師筆記解答一致 |
| 學生資料夾結構 | 把 4 個範例與 starter `MissionLog` 放進同一個 `java-04/src` 一起編譯 | 編譯成功；起始碼最後一行為 `6／5／3 SUCCESS` |
| 起始碼 | 檢查 TODO 數量，並跑無參數與 `3 9 0` | 5 個 TODO；可執行；回放、計數、摘要皆為未完成狀態 |
| 教師解答：五條必做測試 | 逐條執行 `MissionLog` | 最終狀態、結果、摘要、`counts` 全部與作業說明的表格一致 |
| 教師解答：邊界 | `MissionLogCheck` 共 12 項（含初始 5／0、6／0、2／4、1／0，空腳本，先返回，`countCommands` 遇到 9） | 12/12 passed |
| 模擬 AI 錯誤 1 | 拿掉範圍檢查後跑 `3 9 0`、無參數 | `3 9 0`：`ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 4`；無參數時正常 |
| 模擬 AI 錯誤 2 | 改成 `<=` 後跑 `3 9 0`、無參數 | `3 9 0`：印完 3 步後 `IndexOutOfBoundsException: Index 3 out of bounds for length 3`；無參數時正常 |
| 模擬 AI 錯誤 3 | 加入從前往後的 `removeRejected` 後跑 `3 9 0`、無參數 | `3 9 0`：回放剩 `9`、`0` 兩步，`steps=2, rejected=1, ignored=1`；無參數時正常 |
| 錯誤 3 改成從後往前 | 同上 | 回放剩 `0` 一步，`ignored=2`（摘要仍失真，教師筆記已說明） |
| `ArrayList<int>` 錯誤訊息 | 以副本編譯 | `error: unexpected type`、`required: reference`、`found: int` |
| 越界行號 | 檢查 `ArrayBasics.java` | 被註解的越界程式在第 36 行，與投影片一致 |
| 投影片 | 匯出 35 頁 PNG 並目視 | 無文字溢出；每頁都有 `claude` 徽章；23 頁有對應範例標籤 |

## 未驗證

- 教室電腦實際安裝的 JDK 版本（`--release 11` 只檢查語法與 API，不等於在 JDK 11 實機執行）。
- VS Code `launch.json` 與 IntelliJ Program arguments 的實際操作畫面。
- GitHub Desktop 的 push、全新 clone 流程（沿用第 1–3 週流程，本週未重新操作）。
- 150 分鐘時間配置的課堂試做。
- `println(script)` 的雜湊碼每次執行可能不同，投影片的 `[I@1b28cdfa` 只是範例。
