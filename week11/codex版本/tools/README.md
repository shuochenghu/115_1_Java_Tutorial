# 第 11 週教師維護工具

從 `week11/codex版本` 執行，需本機 Python 與 JDK，不需第三方 Python 套件。所有編譯、錯誤副本及輸出在新的系統暫存目錄，結束自動清理。以下工具不列學生先備。

```powershell
python -X utf8 tools/verify.py
```

檢查 examples／starter／teacher（含 ObjectBox 兩行與執行期轉型失敗）、只替換 TODO 檔的完成路徑、兩段未完成提示、九行展示與泛型方法、講義 LiveTotalTry（並證明快取總和與轉型代替上限都能騙過九行）、25 項新群組檢查、既有 15／16／51 項、前週五個入口完整輸出、六個編譯拒絕、AI 反例與最小修正、最大來源作業與其回傳型態反例，以及 UTF-8／連結／Java 檔名／投影片頁數。需與第 10 週在同一課程 repo，才能比對前週原檔與輸出。

`--code-only` 僅供編寫中略過文件與 HTML 檢查；交付應跑完整命令。教師測試通過不能代替學生的自選案例、需求說明或 GUI／遠端操作。

手動驗證教師主線（編譯成功才執行）：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-teacher teacher/*.java
java -cp out-teacher SampleGroupDemo
java -cp out-teacher GenericMethodDemo
java -cp out-teacher SampleGroupCheck
```

驗證作業時，先把 teacher 頂層檔複製至另一個暫存來源目錄，再以 teacher/homework 的 SampleGroup.java 替換同名檔、加入 MostSamplesDemo.java，整組編譯至新目錄。不能同時把兩個 SampleGroup 加入一個 javac 命令，也不將教師 Check 交成學生程式。

圖使用 `.puml` 可編輯原稿與同名 SVG，依 README 索引。沿用前週已備的 PlantUML jar、Smetana，不為本週下載或升級工具。實際 jar 路徑換成自己的位置：

```powershell
python -X utf8 ../../week08/codex版本/tools/render_diagrams.py --jar C:\ClassTools\plantuml.jar --source diagrams/evolution.puml --output diagrams
```

工具先產 evolution-1.svg，檢查成功與圖內容後才替換正式同名 evolution.svg；其餘兩圖同樣處理。學生只要看圖、標註參考，不要求新裝產圖工具。

Marp 延續前週，使用已安裝的 CLI 匯出，不要求最新版：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week11_slides_codex.md -o week11_slides_codex.html
```

離線索引失效時，可由 Node 直接執行已快取的 marp-cli.js。分享 HTML 必須附 diagrams 並保持相對位置。版面檢查沿用第 8 週 check_slides.cjs、Puppeteer 與 Chrome 的獨立未登入 profile，對 bare HTML 渲染後檢查；須另看頁面影像，不能把幾何檢查當成目視驗收。
