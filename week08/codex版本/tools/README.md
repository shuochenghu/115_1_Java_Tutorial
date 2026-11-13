# 第八週教師維護工具

## 程式與文件

在week08/codex版本執行：

```powershell
python -X utf8 tools/verify.py
```

需要已安裝JDK與Python，不需第三方Python套件。工具在本週建立新的暫存目錄，完成自動清理；不使用舊out。

分開編譯examples、starter、teacher；驗證會員完整輸出、重型車十五行、兩車完整報表、未完成starter、十檔學生合併配置及僅替換三個TODO檔的完成配置。再做15項一般車完整輸出、16項任務狀態、51項覆寫／多型與結構檢查、遷移四行、模擬AI兩行不符與最小修正十五行重測、隔離編譯失敗、B3小修改、五種錯誤變異，最後核對UTF-8、連結、圖、頁數及前週契約。

開發中可用--code-only暫略文件檢查；正式交付須用完整命令。反射、AssertionError、Python、子行程及錯誤變異屬教師工具，不列學生先備。

直接執行教師檢查：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-teacher teacher/*.java
java -cp out-teacher MissionLogCheck
java -cp out-teacher MissionStateCheck
java -cp out-teacher PolymorphismCheck
java -cp out-teacher CompareMissions
javac -encoding UTF-8 -Xlint:all -d out-transfer teacher/Rover.java teacher/Mission.java teacher/MissionStep.java teacher/transfer/*.java
java -cp out-transfer SurveyRoverTry
```

批學生主線時只編譯學生完成的src及需要的教師Check，不加入教師同名答案。MissionLogCheck另需FixedScriptMission一起編譯；PolymorphismCheck與MissionStateCheck單獨加入即可。檢查器不驗收自訂遷移規格、自由題設計、圖的可讀性或學生解釋能力，仍需人工審查。

## 圖與Marp

學生用README本機jar管線，不要求Python。教師可用：

```powershell
python -X utf8 tools/render_diagrams.py --jar C:\ClassTools\plantuml.jar --source diagrams/dispatch_practice.puml --output diagrams
```

jar路徑換實際位置。工具會產dispatch_practice-1.svg，成功且確認圖後移為正式dispatch_practice.svg；來源用Smetana，不額外要求Graphviz。原始檔改動後須同步SVG，不可只修改匯出圖。

沿用既有Marp CLI與前週版型，不下載或升級工具：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week08_slides_codex.md -o week08_slides_codex.html
```

離線npx索引失效時可直接用node啟動已安裝marp-cli.js。分享HTML時連同diagrams。重新匯出後檢查實際頁面，記錄真正視覺檢查範圍。

check_slides.cjs沿用前週，輸入為puppeteer-core目錄、Chrome路徑、Marp bare HTML、輸出目錄及可選獨立profile。bare HTML旁需有同名diagrams目錄。會輸出每頁PNG、聯絡表及layout-report.json；只使用本次獨立未登入profile，不讀取個人瀏覽器資料。自動未溢出不等於逐頁視覺檢查。
