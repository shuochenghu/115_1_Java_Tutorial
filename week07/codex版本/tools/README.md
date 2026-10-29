# 第七週教師維護工具

## 程式與文件

在本週教材目錄執行：

```powershell
python -X utf8 tools/verify.py
```

需要既有Python與JDK，不需要第三方Python套件。暫存目錄只建立在本週內，完成自動清理；examples／starter／teacher分開編譯，再測九檔學生src及完成配置。包括會員完整輸出、15項舊完整輸出、16項任務狀態、繼承與局部方法、Cargo遷移、AI反例／正確重測、缺super／private編譯錯誤、同套件protected及五種錯誤變異，最後檢查文件與投影片頁數。每次只在副本改錯，正式答案保持正確。

此工具不取代讀學生scan／explore是否真的完成局部重構；結構要求需教師閱讀。也不能替自訂遷移題憑空決定規格，依assignment與學生規格驗收。反射、AssertionError、Python與測試輸出擷取不列學生先備。

直接Java驗證：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-teacher teacher/*.java
java -cp out-teacher MissionLogCheck
java -cp out-teacher MissionStateCheck
java -cp out-teacher InheritanceCheck
javac -encoding UTF-8 -Xlint:all -d out-transfer teacher/Rover.java teacher/transfer/*.java
java -cp out-transfer CargoRoverTry
```

批學生主線：只編譯學生src與教師三個Check，不加入教師同名Rover／HeavyRover／Mission等答案；獨立全新輸出目錄，編譯成功才執行。主線Check不驗收自訂遷移或圖的可讀性。

## 圖與Marp

學生產圖用README本機Java命令，不要求Python。教師維護：

```powershell
python -X utf8 tools/render_diagrams.py --jar C:\ClassTools\plantuml.jar --source diagrams/heavy_practice.puml --output diagrams
```

jar路徑換實際位置。工具產heavy_practice-1.svg，確認後同步正式同名heavy_practice.svg；來源已指定Smetana。可接受Markdown內plantuml區塊，出錯不覆寫有效命名產物。學生圖可由教師代產，但要立即本人檢視。

沿用既有Marp，不要求最新版：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week07_slides_codex.md -o week07_slides_codex.html
```

若離線npx索引失效，可直接用node啟動已安裝的marp-cli.js。分享HTML需連同diagrams資料夾。來源編輯後同步HTML。

check_slides.cjs沿用前週，接受puppeteer-core路徑、Chrome路徑、bare HTML、截圖目錄及可選profile。必須用獨立profile，不讀個人登入資料。先由Marp的bare模板產暫存HTML，再執行檢查。會產每頁PNG、聯絡表與layout-report；幾何與匯出成功不代替目視檢查，實際檢視範圍寫verification。
