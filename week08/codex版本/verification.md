# 第八週Codex教材驗證紀錄

日期：2026-10-30。執行者：Codex。這是作者驗證，不是學生本人操作或課堂試教。

## 實際環境與方法

- Windows，OpenJDK22（22+36-2370）、javac22、Python3.14.0、Node.js24.11.0。
- 普通非預覽Java，未指定--release、-source或-target，不鎖教室JDK。
- `python -X utf8 tools/verify.py`：新的暫存目錄、examples／starter／teacher各自編譯，javac使用UTF-8及-Xlint:all；編譯成功後才執行。暫存目錄完成自動清理。
- PlantUML MIT 1.2026.9，以本機jar及來源Smetana設定產四份SVG；未為本週安裝Graphviz或套件。
- Marp CLI4.5.1／Marp Core4.4.0，使用既有工具匯出正式HTML及QA用bare HTML。
- Chrome與既有puppeteer-core僅讀本機HTML，使用本次獨立未登入profile。第一次沙箱內啟動失敗，改以允許環境的獨立profile完成，未使用個人瀏覽器登入資料。

## 程式實測

| 項目 | 實際結果與支持範圍 |
|---|---|
| examples、starter、teacher各自編譯 | 全部成功，無編譯警告 |
| 完整輸出比對 | MemberDemo六行、HeavyRoverTry十五行及CompareMissions兩段共十九行均符合預先計算的期望 |
| starter未完成標示 | MissionLog保持一般車舊行為；HeavyRoverTry恰有兩行DIFF；CompareMissions印NOT_READY並安全結束 |
| 學生初始配置 | examples三檔＋starter七檔，共十檔合併編譯成功；MemberDemo符合期望，CompareMissions仍明確未完成 |
| 完成學生配置 | 只替換三個TODO檔，其餘提供檔不變；兩車完整報表與重型車十五行皆符合期望，一般車MissionLog亦相同 |
| 一般車完整輸出回歸 | MissionLogCheck 15/15，期望與檢查檔沿用前週，涵蓋返回、拒絕、無效指令、腳本用完、初始停止、空資料、充電及成功優先 |
| 任務狀態回歸 | MissionStateCheck 16/16，含快照、獨立歷史及counts回傳副本 |
| 本週覆寫與多型 | PolymorphismCheck 51項通過，含父型態參考呼叫、兩車不同停止時機、精確能源邊界、拒絕不取產量、產量只呼叫一次、第三種測試子類別、掃描／充電不變、鑽探後新產量、private／protected／非static及無重複狀態 |
| 遷移參考 | SurveyRoverTry四行完整輸出符合期望：正常UNFINISHED、邊界SUCCESS、拒絕不變、初始停止 |
| 模擬AI反例 | 原文可編譯；after explore及done unchanged為6/5/2/1，與預期6/6/2/1不符，恰有兩行DIFF；其餘十三行正確 |
| 最小修正 | 在AI副本只替換無效private探索方法為正確產量覆寫，保留drill等其餘程式；相同入口十五行全OK |
| 隔離編譯失敗 | 會員方法名稱不符、AI private explore加@Override、產量方法參數不符、子方法縮小為private、Member型態參考呼叫claimBonus，五種均如預期失敗並比對有意義的診斷 |
| B3學生小修改 | 使用super.describe()加VIP尾碼的副本完整輸出符合預期，正式src範例不變 |
| 教師工具偵測力 | 父流程寫死2、重型車回2、產量方法額外計次、移除execute停止檢查、子類別重複energy，五種變異各自使PolymorphismCheck失敗 |

編譯錯誤與變異皆只存在暫存副本，正式教師解答維持正確。學生仍須提供自己的規格、預測、解釋及反例；教師工具不能代替自由題驗收。

最初起始訊息含中文，在此Windows Java輸出擷取時編碼不一致；改為ASCII的NOT_READY訊息後重新完整驗證通過，不以工具解碼失敗當作多型功能錯誤。

## 教材、UML與投影片

- 三組TODO與講義D2–D4、教師答案、作業及投影片一致。學生保留自行修改產量來源、宣告覆寫與建立任務的核心實作，不以show報表代做任務建立。
- Mission、MissionStep、MissionLog及三個既有回歸支援檔逐位元組與第七週教師版核對；一般車舊期望保留，重型車另用本週新規格。
- teacher_notes的會員六行、重型車十五行、兩車十九行完整輸出，逐字核對教師工具預先期望常數。
- 五欄AI範本附清楚標示的虛構示範；模擬原稿標示來源，診斷答案集中教師筆記，AI學生原稿不先寫出錯誤原因。
- 四份puml及SVG同步；member_views原圖第一次因混合圖元素而語法失敗，改用清楚標示參考／物件的矩形後重新產出成功。三張教學圖隨投影片實際檢視，教師參考dispatch_reference另產PNG實際檢視；未見文字裁切、箭頭錯位或重疊。
- README的Windows cmd UTF-8管線以本機jar實測；產出dispatch_practice.svg與正式圖逐位元組一致。這是本機CLI證據，非教室安裝證據。
- Markdown及正式HTML皆32頁；bare HTML渲染32張PNG，自動檢查0項溢出、無圖片載入失敗。已實際逐頁開啟檢視全部32頁，包含文字、表格、程式及圖；最後文字修訂後重新匯出並重查第2、30頁與全部頁面幾何，仍0問題。
- 輸出遵循前週16:9、中文字型、配色、頁尾與講者備註；程式及圖在本機圖像中可讀，尚未證明教室投影可讀性。
- UTF-8、替代字元、文字尾端空白、Markdown連結與code fence、Java檔名／public class、圖檔存在及投影片頁數均檢查；完整工具靜態檢查23個本機連結。
- 實際核對專案Deitel11e EPUB目錄的§9.4、§10.2–10.3及部分§10.6；官方JLS覆寫／方法選擇、Override與Object API連結見teacher_notes，不將Java25文件當成本機25實測。

## 原檔保留與交付狀態

製作前保存362個既有檔案SHA-256，完成後核對均未變動，包含前週、Claude版與原有未追蹤文件。只新增week08/codex版本，共47個交付檔，其中24個Java來源檔；無class、pyc或暫存編譯產物。

截圖、圖PNG、SVG管線核對檔及保存基準放本次視覺化QA目錄，未放入學生教材。沒有新增教材以外的repo檔案。README為入口，handoff記第九週能力與資料契約。未建立commit、未fetch、未push，不宣稱遠端同步。

## 未驗證與限制

- 未在其他JDK或作業系統執行；課程不鎖定22，其他環境仍需課前驗證。
- 未實作教室VS Code／IntelliJ GUI、教室PlantUML安裝或教室投影。
- 未登入真實GitHub、push或clone；學生保存段落為操作指引，非作者完成證據。
- 未呼叫真實AI服務；模擬回覆及虛構紀錄已標示來源。
- 未做150分鐘學生試教；課堂與課後工時皆為估計，需依理解及操作情況調整。
- 不驗收契約外建構值、溢位、null、重複run、共享車或並行，也不承諾支援任意會改狀態的惡意產量覆寫。
- 自訂遷移依學生明訂規格人工審查；不能拿SurveyRover參考輸出套用所有題目。

## 2026-11-11 繁體中文輸出驗證

- OpenJDK 22，使用 javac -encoding UTF-8 -Xlint:all 分開編譯範例、學生起始碼及教師解答。以 UTF-8 擷取主控台輸出。
- 執行該週 tools/verify.py，中文預期輸出、教師檢查與原有回歸案例通過。
- 歷史驗證中的英文輸出反映當時的程式，現行預期輸出以本週講義和教師筆記為準。
- Marp CLI 4.5.1 重新匯出 1 份 HTML（32 頁）。以無頭 Chromium 檢查程式碼區塊與表格的版面邊界，未偵測到溢出。
