# 第六週教師維護工具

## 功能與文件

在本週教材目錄執行：

```powershell
python -X utf8 tools/verify.py
```

需要現有JDK與Python，無第三方Python套件。使用本週內新暫存目錄、分開編譯examples／starter／teacher與合併學生src；結束自動清理本次暫存目錄。核對兩個獨立範例完整輸出、RoverDemo、starter安全返回、15項既有完整輸出回歸、16項物件／任務檢查、完成學生配置、四個錯誤變異、E3超量反例與final參考示範，並檢查Markdown、連結及投影片頁數。變異都在副本，正式教師答案不改壞。學生不要求寫Python、反射、AssertionError或JUnit。

直接用Java時，在本週教材目錄分開編譯teacher：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-teacher teacher/*.java
java -cp out-teacher MissionLogCheck
java -cp out-teacher MissionStateCheck
java -cp out-teacher FixedScriptMission 2 4 2
```

批學生完成碼，只加入學生五檔與教師兩個Check／FixedScriptMission，不混入教師同名答案。新輸出目錄先確認編譯成功才執行，不拿舊.class充當本次結果。out-teacher不提交。

## 本機產圖

PlantUML jar由[官方發行](https://github.com/plantuml/plantuml/releases)取得，放課堂工具目錄，不放學生repo。本次實測版本見verification。每張來源已指定Smetana。以下jar路徑是配置範例，換成教室實際位置：

```powershell
python -X utf8 tools/render_diagrams.py --jar C:\ClassTools\plantuml.jar --source diagrams/step.puml --output diagrams
```

產出`step-1.svg`，這個編號格式便於一次抽取Markdown多個plantuml區塊。教材正式同名step.svg由維護者確認後同步；學生用README的Java管線直接產同名SVG，不要求Python。

render_diagrams.py也能接受含plantuml圍欄的Markdown，逐塊產SVG；遇到語法錯誤不覆寫該張正式圖。教師可接收學生自己的puml協助當堂預覽，但要標記教師代產／學生檢視。教室配置命令與GUI仍需課前測試。

## 投影片與版面

使用既有Marp CLI，不固定要求安裝最新版。維護範例：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week06_slides_codex.md -o week06_slides_codex.html
```

若npx的快取不可用，可直接用node執行已安裝的marp-cli.js，不必重新安裝；本次採此方式。HTML使用相對圖片連結，分享時連同diagrams資料夾。

本週[check_slides.cjs](check_slides.cjs)沿用第四週邊界檢查，另加入獨立瀏覽器profile與圖片載入／邊界檢查。先用Marp的bare模板產暫存HTML，再傳入既有puppeteer-core、Chrome、bare HTML、視覺化輸出目錄及可選profile目錄。只使用本次專用profile，不讀取個人登入狀態。不要把PNG／暫存檔加進學生src。

語法成功、SVG存在、HTML匯出、程式幾何檢查、縮圖巡視與逐頁放大檢查分開記錄。工具產生的結果不代替實際檢視；視覺化範圍及未完成項目寫verification。
