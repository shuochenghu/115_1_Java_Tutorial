# 第 12 週 Codex 教材檢視（claude）

本檔由 Anthropic Claude（Claude Code）於 2026-11-19 撰寫，檢視 `week12/codex版本/` 教材並記錄本份投影片 `week12_slides_codex_claude.md`／`.html` 的製作依據。檢視當時只回報，未修改 codex 檔案。

> **2026-11-20 更新（claude）**：教師同意後，已套用下方建議 1、2，改動 12 個 Java 檔，只動註解，程式邏輯、`BEGIN/END` 區塊與 TODO 文字都沒改。改完後 `tools/verify.py` 全部通過。新增檔頭使行號下移，本份投影片引用的行號已依 JDK 22 重新實測更新：`ParseDemo.java:8`→`:14`、對調 catch 副本 `:17`→`:21`、拿掉 try 的副本 `:10`→`:16`。

## 一、實測範圍（Windows、OpenJDK 22、javac 22）

| 項目 | 結果 |
|---|---|
| `python -X utf8 tools/verify.py` | 全部通過（6 組檢查） |
| examples：ParseDemo `caught/uncaught` × `abc/3`、空字串；FinallyTrace `3/abc` | 輸出與 examples/README、講義 A1、教師筆記一致；未捕捉版堆疊指向 `ParseDemo.java:8` |
| starter quota／threads 編譯與執行 | 可編譯、只印「尚未完成」提示，安全結束，沒有洩漏答案 |
| teacher quota 七行、作業摘要 `3/3/0`、自選 `{"", "0", " 2 "}` | 與講義、作業、教師筆記一致 |
| ai 版（搭配教師入口） | 第五筆 -2、最後 -4，與講義一致 |
| catch 對調副本 | `exception NumberFormatException has already been caught` |
| 省略 join 的 try／catch | `unreported exception InterruptedException; must be caught or declared to be thrown`（兩處） |
| 執行緒正式版／直接 run 版 | 8 行、名稱對應、各自 1→2→3；本次實測出現 B 先開始且交錯（合法）。直接 run 版六行皆為 main |
| 投影片另測：`try` 包住整個 for 的錯誤版 | 只印 3 行（受理 3、拒絕 abc、批次結束 7），用於 A3 診斷頁 |

TODO 編號（A2、A3、B1、B2）、講義題號、教師 BEGIN/END 區塊、作業規則與預期輸出互相一致；public class 與檔名相符。**未發現錯誤。**

## 二、建議（claude 建議，2026-11-20 已套用）

### 建議 1：`ai/PrintQuota.java` 的註解直接點出錯誤位置

第 18 行 `// 錯誤在這裡：拋出例外不會自動還原前面已改動的欄位。` 會被學生一起複製到 `ai-check/`，講義 AI 段要求「指出哪一行改了欄位、例外為何沒有自動復原」，答案已寫在檔案中。建議改成不提示位置的說明，例如：

```java
// 這是教師設計的「模擬 AI 回覆」，可以編譯，但不一定符合需求。
// 請用固定六筆資料執行，觀察第五、六筆的剩餘額度，再自己找出問題所在。
```

教師版說明可留在 teacher_notes（已有）。本份投影片展示這段程式時已不含該註解。

### 建議 2：Java 註解格式與第 4～11 週不一致

第 4～11 週 codex 程式已採「`// 類別：Name（角色）`＋`// 意義：…`＋`// 作用：…`，每個欄位／建構子／方法前一行說明」的格式（2026-11-12 教師要求）。第 12 週 10 個 Java 檔只有一行檔頭，欄位與方法沒有說明。例如 `starter/quota/PrintQuota.java` 可補成：

```java
// 類別：PrintQuota（額度物件，學生起始碼）
// 意義：一個人的列印額度。每個新物件從 10 頁開始，欄位 private，只能透過 reserve 改動。
// 作用：reserve 先檢查申請是否合法，不合法就拋出例外、額度不變；全部通過才扣除。
//       顯示訊息與接住例外是呼叫端（PrintQuotaDemo）的工作，這個類別不印任何東西。
public class PrintQuota {
    // remaining：目前剩餘頁數，建構時設為 10。
    private int remaining;
    // reserve：申請 copies 頁。TODO A2 由你完成；目前是空方法，只是為了能編譯。
```

`PrintTask` 可註明「`Runnable` 是 JDK 的介面，只要求一個 `run()` 方法」；`ThreadStartDemo` 可註明「`start()` 開新執行緒，`join()` 讓 main 等待」。注意 verify.py 會計算 TODO 標記，補註解時不要新增 `TODO` 字樣。

## 三、可選

- README「準備與檔案位置」的目錄圖沒有 `experiments/direct-run/`，但講義 B2 會用到；可在圖中補一行（本份投影片第 8 頁已列出）。

## 四、本份投影片

- `week12_slides_codex_claude.md`／`.html`，65 頁，每頁 `by-claude`；依教師要求**先講完 examples（ParseDemo、FinallyTrace 選讀），再講 starter**（A2→A3→A4→AI→B1→B2），teacher 答案只在附錄。
- 新繪 9 張 `diagrams/*_claude.puml/.svg`：`start_w11`（上週兩種失敗下場）、`reject_evolution`（第 4～11 週回傳值 → 第 12 週 throw）、`exception_tree`、`throw_flow`、`starter_todo_w12`、`ai_deduct`、`runnable`（第 9～11 週自寫介面 → JDK 的 Runnable）、`start_run`、`evolution_w12`（第 11 週 → 第 12 週總覽）。PlantUML 1.2026.9、smetana 版面。
- 投影片中的輸出與編譯診斷都來自上表的 JDK 22 實測；javac 診斷為英文（JDK 22 無繁中訊息）。
- 已以 Marp CLI 匯出 PNG 逐張檢視版面（修正 B1、演進總覽溢出，改用 CSS class 控制兩欄寬度）。教室投影、學生試教未驗證。
