# 第 12 週教師維護工具

工作目錄為 `week12/codex版本`。只需 Python 與已安裝的 JDK，不依賴第 11 週或第三方 Python 套件；所有編譯、變體及輸出在新的系統暫存目錄，結束自動清理。

```powershell
python -X utf8 tools/verify.py
```

工具分開編譯 examples、兩組 starter、兩組 teacher、作業與 AI 副本；只把 A2/A3/B1/B2 的答案區塊填回起始碼，驗證實際完成路徑。另驗證 A2 完成時 A3 仍提示、課堂與作業各 13 組完整輸出、兩個物件獨立、AI 錯誤／實際修正、catch 順序與受檢例外的編譯拒絕、執行緒六次執行、直接 run 對照，以及受控中斷下的教師原支援段。latch 僅供教師控制等待條件，不列學生要求。

`--code-only` 僅供編寫中略過文件；交付跑完整命令。文件檢查涵蓋 UTF-8、程式區塊、本機連結、檔名、SVG XML、投影片原稿與 HTML 頁數。版面仍須另外檢查與目視。

手動執行教師答案，編譯成功才跑下一行：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-quota teacher/quota/PrintQuota.java teacher/quota/PrintQuotaDemo.java
java -cp out-quota PrintQuotaDemo
javac -encoding UTF-8 -Xlint:all -d out-threads teacher/threads/PrintTask.java teacher/threads/ThreadStartDemo.java
java -cp out-threads ThreadStartDemo
```

作業編譯時使用 `teacher/quota/PrintQuota.java` 與 `teacher/homework/PrintQuotaDemo.java`，另用 out-homework；不把兩個同名入口加入同一次編譯。AI 版搭配 `ai/PrintQuota.java` 與課堂 teacher/quota/PrintQuotaDemo.java，輸出目錄另分開。

## 圖與投影片

`.puml` 是可編輯圖稿，SVG 供文件與投影片引用。以已備妥的 PlantUML jar 與前週 render_diagrams.py 產生，不要求學生安裝產圖工具。每個來源只含一張圖；工具產 `檔名-1.svg`，確認成功後改存正式同名 `.svg`。

```powershell
python -X utf8 ../../week08/codex版本/tools/render_diagrams.py --jar C:\ClassTools\plantuml.jar --source diagrams/roles.puml --output diagrams
```

jar 位置換成實際已備路徑。投影片沿用 Marp CLI 4.5.1 與前週版式；不要求升級或安裝最新版：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week12_slides_codex.md -o week12_slides_codex.html
```

若離線套件索引失效，可用 Node 執行已快取的 marp-cli.js。分享 HTML 時附 diagrams 目錄。視覺維護沿用第 8 週 check_slides.cjs、已安裝的 Puppeteer／Chrome，以 `--html --template bare` 產生暫存裸 HTML 後檢查；使用獨立未登入 profile。記錄幾何檢查、縮圖與逐頁檢視的實際範圍，不把其中一項當全部通過。不能把互動播放中的隱藏頁面逐一截圖，否則可能得到重複封面。
