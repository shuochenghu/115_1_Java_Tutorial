# 教材維護工具（非學生作業）

Java 功能驗證用 teacher/MissionLogCheck.java；不需要 Python、JUnit 或額外Java函式庫。

## 投影片匯出

本次使用已安裝的 Marp CLI 4.5.1；已有 Marp for VS Code 者也可直接匯出 HTML。在教材目錄執行：

```powershell
npx @marp-team/marp-cli@4.5.1 week04_slides_codex.md -o week04_slides_codex.html
```

修改 Markdown 後重新匯出，避免 HTML 與原稿不同步。

## 選用的本機版面檢查

check_slides.cjs 需現有 Node.js、puppeteer-core 與Chrome；不自行安裝套件，也不連外部帳號。先用Marp匯出 bare 模板至臨時路徑，再執行：

```powershell
node tools/check_slides.cjs "PUPPETEER_CORE_MODULE_PATH" "CHROME_EXE_PATH" "BARE_HTML_PATH" "QA_OUTPUT_DIR"
```

四個占位路徑請替換為本機實際位置。工具產生逐頁PNG、聯絡表及layout-report.json；輸出到教材之外的暫存/視覺化目錄。它檢查DOM邊界與程式區塊水平溢出，不取代教師目視、內容審閱或教室投影。
