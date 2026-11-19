# 第 9 週教師工具

在 `week09/codex版本` 執行。使用本機 JDK 與 Python。

```powershell
python -X utf8 tools/verify.py
```

此工具在新的暫存目錄分開編譯起始碼、教師版與示範，檢查回歸、樣本來源、作業參考答案及隔離編譯實驗。正式檔案無需放入暫存輸出。

## 個別執行

```powershell
javac -encoding UTF-8 -d out-examples examples/*.java
java -cp out-examples ParcelDemo
java -cp out-examples ScaleDemo
java -cp out-examples FixtureDemo

javac -encoding UTF-8 -d out-teacher teacher/*.java
java -cp out-teacher StepListenerDemo
java -cp out-teacher MissionLogCheck
java -cp out-teacher MissionStateCheck
java -cp out-teacher PolymorphismCheck

javac -encoding UTF-8 -cp out-teacher -d out-transfer teacher/transfer/*.java
java -cp 'out-teacher;out-transfer' SampleBatchDemo
```

## 更新 UML 及投影片

沿用 [第 8 週教師維護工具](../../../week08/codex版本/tools/README.md)。使用教師備妥的 PlantUML jar，將路徑換成實際位置。

```powershell
python -X utf8 ../../week08/codex版本/tools/render_diagrams.py --jar C:\ClassTools\plantuml.jar --source diagrams/responsibilities.puml --output diagrams
Move-Item -LiteralPath diagrams/responsibilities-1.svg -Destination diagrams/responsibilities.svg -Force
npx --offline @marp-team/marp-cli@4.5.1 week09_slides_codex.md -o week09_slides_codex.html
```

先確認產圖成功再替換 SVG。HTML 旁保留 `diagrams/`。版面檢查可用第 8 週的 `check_slides.cjs` 搭配既有 Chromium 與 Puppeteer，並檢視輸出的頁面影像。
