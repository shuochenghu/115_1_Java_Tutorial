# 第 10 週教師工具

從 `week10/codex版本` 執行，使用本機 JDK 與 Python。教材需與第 9 週位在同一個課程儲存庫，工具會以現行第 9 週 Codex 教師版比對舊任務輸出。

```powershell
python -X utf8 tools/verify.py
```

工具以獨立暫存目錄編譯短例、starter、teacher、隔離實驗及作業參考。若要驗收學生程式，可單獨編譯學生的 `src`，依講義重跑入口。教師答案與學生同名 Java 檔各自編譯。

手動執行完成版：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-teacher teacher/*.java
java -cp out-teacher SampleReportDemo
java -cp out-teacher SampleOverloadDemo
java -cp out-teacher CompareMissions
```

從本目錄以現有 Marp CLI 匯出 HTML：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week10_slides_codex.md -o week10_slides_codex.html
```

也可由 Node 直接呼叫已安裝的 `marp-cli.js`。匯出後檢查頁數、程式碼與表格邊界，以及實際呈現。
