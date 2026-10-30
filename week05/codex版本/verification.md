# 第五週教材驗證紀錄

本檔依日期保留歷史證據；2026-10-15的頁數、檔案數與活動題號為初版紀錄，現行修訂與本次重跑結果見末段2026-10-16。

日期：2026-10-15。下列為作者本次本機實測，不能當作學生本人紀錄。教材不限定特定JDK版本；本次編譯未設定固定目標版本或使用預覽功能。

## 實際環境與方法

- OpenJDK22，build22+36-2370；javac22；Python3.14.0；Node.js24.11.0。
- 以 `python -X utf8 tools/verify.py` 執行；每組使用本週目錄內新建的隔離輸出目錄，以 `javac -encoding UTF-8 -Xlint:all` 編譯，依對應classpath執行，完成後清除本次暫存目錄。
- examples六檔、starter兩檔、teacher五檔分開編譯，另將「examples六檔+starter兩檔」合併模擬學生src編譯，均成功。同名教師與學生類別未混編。
- 初次工具執行在預期編譯錯誤階段遇到Windows本地診斷編碼問題；已為該階段指定英文診斷並容錯解碼，修正後完整重跑通過。未把工具錯誤寫成程式功能通過。

## 程式實測

| 項目 | 實際結果／能支持的結論 |
|---|---|
| 四個核心範例 | CreateStepDemo、BatteryDemo、ReferenceDemo、SnapshotDemo完整輸出逐字符合examples/README，含兩物件獨立、拒絕／封頂、共用參考／參數重新指定及行動後快照 |
| starter | 能編譯安全結束，六個TODO群組保留；預設任務仍得到6/5/3 SUCCESS，但日誌steps=0、ignored=3、最低N/A，明確為未完成功能 |
| MissionStepCheck | 8/8，檢查兩筆完整欄位、獨立實例、alias、new String("OK")內容比較、RETURN非成功、int快照及物件清單順序 |
| MissionLogCheck | 15/15；沿用第四週已寫定的完整輸出期望，回放、counts、摘要、最低能源、最終狀態及battery均一致，只正規化換行 |
| 六條作業路線 | {0}、{1,1,2}、{2,2,2,2,2}、{3,9,0}、{1,1,1,1}、{2,1}包含在上述回歸檢查，完整輸出通過 |
| 邊界 | 能源不足後充電、初始零能源、初始已達標、初始同時達標／耗盡、空腳本、恰好6格、19格封頂、負數指令、成功優先均包含在15項中 |
| 兩個預期編譯錯誤 | StepCard私有欄位存取與不存在的無參數建構子，均編譯失敗並核對診斷原因；正式範例仍可編譯 |
| 四項錯誤變異 | 暫存副本改成static共享欄位、==比較內容、漏存energy建構參數、只記OK步驟；均被對應檢查器拒絕。正式答案未改壞 |
| FixedScriptMission入口 | 實際透過CLI跑2/4+{2}、0/0+{3,1}、20/0+{}，分別得到SUCCESS、EXHAUSTED、UNFINISHED；其他狀態案例由15項完整輸出檢查覆蓋 |

教師Check中的輸出擷取、AssertionError、throws及Python變異工具不列為學生必寫技術；不使用JUnit。主作業不讀args，固定狀態入口仍為教師支援。

## 投影片

- Marp CLI4.5.1／Marp Core4.4.0，匯出39頁HTML，Markdown頁數一致。
- 以既有puppeteer-core24.43.1及本機無頭Chrome開啟bare模板，產生39頁PNG及layout-report.json；頁面文字、表格、程式區塊邊界與水平溢出檢查均無問題。
- 已檢視全頁聯絡表，並逐一開啟39頁截圖，以可讀尺寸檢查標題、程式、表格、段落與頁面順序，未發現截斷或重疊。這是本機畫面檢查，不等於教室投影或學生可讀性試教。
- 截圖與報告保存在本次Codex視覺化目錄的week05-qa，不放學生src；維護方法見tools/README。

## 文件與教學對齊

已交叉核對：六個TODO與教師答案、E1兩筆物件預期、六路線的狀態／counts／摘要／最低值、B–D範例輸出、F2模擬反例與AI範本；逐題答案在teacher_notes。Java檔名與public class相符，學生與教師檔案分開，沒有提交.class或測試副本。

本週25個檔案中的24個Markdown本機連結均存在；UTF-8、code fence、行尾空白、Java檔名與public class、六個TODO群組及39頁Markdown／HTML一致性檢查通過。新增未追蹤檔也納入，不只依賴git diff；git diff --check通過，未留下.class或.verify暫存目錄。JDK實測與歷史紀錄分開；未改第一至第四週及既有Claude教材。

依使用者本次補充，已將「單一程式以VS Code教學，超過兩個程式檔再加入IntelliJ IDEA」同步至共用技能、README、學生講義、教師筆記及投影片。本週以兩檔基本範例起步，在三檔SnapshotDemo加入IDEA。

教科書章節由本專案Deitel11e EPUB目錄核對。Java概念與IDE操作查官方文件，連結在teacher_notes；文件可用性不代表本機GUI已操作。

## 尚未驗證

- 其他JDK及作業系統；不從JDK22通過推論所有版本都通過。
- 教室VS Code／IntelliJ IDEA GUI的實際專案開啟、來源根、SDK及Run操作。
- 真實GitHub Desktop登入、push／pull／遠端clone與重新取回；本次也未做本機Git remote模擬。
- 真實AI服務呼叫；本週模擬建議及填寫示範為自編，未宣稱是真實模型回覆。
- 150分鐘學生試教、學生理解程度、教室投影效果。
- 建構契約外參數、完整非整數／溢位輸入的處理；本週不教授完整例外防護。

分支為main，教材與共用技能尚未提交；本次未fetch，不宣稱已與遠端同步。後續交接見handoff。

## 同日追加：第3至5週版本要求複核

本週README統一為「不限定特定JDK版本」，共用技能同步：日常編譯不設定固定--release／-source／-target，也不鎖定IDE SDK版本。本次再次以目前OpenJDK／javac22執行tools/verify.py，所有編譯、四個範例完整輸出、starter安全返回、8項物件檢查、15項完整輸出回歸、兩項預期編譯錯誤、四項錯誤變異及固定狀態入口檢查均通過。Java與投影片原稿未改，不重作本週視覺檢查。

前段「未改第一至第四週」描述的是第五週初次製作；本次後續工作已依使用者要求修訂第三週及第四週的JDK要求與相關資料初始化。跨週摘要見[第3至5週紀錄](../../week03_week05_JDK檢視紀錄.md)，原有測試環境與限制仍保留。

## 2026-10-16：依兩版比較建議修訂Codex版

### 改動及課程負擔

- C1加入C#／Java類別欄位的預設存取與屬性／getter對照；官方Java與Microsoft文件已核對，來源附於teacher_notes。
- F1改為獨立的SampleCase樣本運送盒設計卡；F2審查教師自編錯誤AI程式，至少四項偏差各附呼叫例子，再改寫Prompt。設計與審查是必做，完整實作為課後選做，不冒充本人已測。
- F3新增把同一可變電池參考存入兩次的快照反例，與static／==錯誤形成三選一。AI範本同步題號與虛構填寫示範。
- 保留主作業兩個類別、六組TODO及學生八個Java檔，不改原任務規則或輸出。完整Rover重構留給第六週；JDK仍不限定版本。
- E段由35改30分鐘，F段由25改30分鐘；三節各50分鐘，總計150。C1以約3分鐘口頭對照／教師副本示範融入原C段。這是備課配置，尚未學生試教。
- 同步README、student_lab、assignment、teacher_notes、AI_USAGE_template、handoff、tools/README與verify.py、Marp Markdown／HTML；新增teacher/SampleCase.java與SampleCaseCheck.java，答案不放學生src。

### 本次實測

環境為OpenJDK22（22+36-2370）／javac22、Python3.14.0、Node.js24.11.0。執行 `python -X utf8 tools/verify.py`，未指定--release、-source或-target；分別編譯examples六檔、starter兩檔、teacher七檔，另合併學生八檔模擬src，均成功。

| 檢查 | 本次結果 |
|---|---|
| 原有四個範例、starter、任務 | 四範例完整輸出及starter安全返回通過；MissionStepCheck 8/8、MissionLogCheck 15/15；所有既有完整輸出期望保留 |
| C1存取權 | private直接寫入仍編譯失敗；副本省略private後，同套件AccessDemo可寫入並由getter讀到99 |
| F3選項3 | 直接擷取講義程式片段建立暫存SnapshotDemo，實際輸出19/19、same object=true；原正式SnapshotDemo完整輸出核對為1/14、3/19、current=19、separate records=true |
| 新情境教師參考 | SampleCaseCheck 12/12，包含初始空盒、存入、整批拒絕、0／負數／極大正數、填滿、滿盒拒絕、卸空、容量上下界及兩盒獨立 |
| 錯誤偵測能力 | 原四項錯誤變異仍被拒絕；另在SampleCase副本移除剩餘空間檢查，檢查器能偵測超量存入。正式答案保持正確 |
| 固定狀態入口 | 原SUCCESS、EXHAUSTED、UNFINISHED三種CLI入口檢查通過 |

錯誤與編譯測試均在本次新暫存目錄進行，結束已清除。沒有把F1／F2的紙上測試要求改成學生必須實作SampleCase。

### 投影片與限制

Marp CLI4.5.1／Core4.4.0重新匯出45頁HTML。以現有puppeteer-core24.43.1與本機無頭Chrome檢查全45頁，未發現DOM邊界或程式區塊水平溢出；已巡視全頁聯絡表，並放大檢查時間表、C1及F段各頁。沿用原有16:9、字型及配色。截圖與layout-report.json保存在本次視覺化目錄的week05-revised-qa。

首次瀏覽器檢查遇暫存profile衝突，改用獨立profile完成；第四週版面工具原檔未更動。其後一次臨時包裝有變數名稱衝突，修正後成功執行，未把失敗當作版面通過。

本次只修改week05/codex版本；Claude教材及其他週次不變。IDE GUI、真實AI服務、遠端GitHub、其他JDK與學生150分鐘試教仍未驗證。分支與未提交狀態另依handoff本次追加紀錄，不把初版狀態當現況。

靜態交付檢查：本週27個檔案、27個Markdown本機連結皆可讀取／定位，code fence、public class檔名、行尾空白、45頁Markdown／HTML同步檢查通過；starter與本次修改前相同，沒有.class或.verify暫存產物，git diff --check通過。工作分支main，本次修改尚未提交。

## 2026-10-29：依第四週原則釐清全45頁投影片

逐頁檢視本週投影片與examples、starter、teacher、學生講義及作業，以「目的明確、例子可追蹤、對應位置完整、必做與支援分清楚」修訂Codex版。每頁上方加入程式檔／方法／TODO、講義題號與相關作業對照，共45條、122次可定位檔案引用；第5頁列完整教材路徑與main入口，README說明教材路徑和學生src的關係。活動代碼移到對照列，標題直接描述教學目的。

第3頁明示改造後成果；第19頁用14／12說明獨立實例資料；第27頁標明檢查片段暫放MissionLog.main、記錄後移除；第31頁明示最低能源支援碼已提供。第33頁補教師FixedScriptMission工作目錄、編譯／執行、參數順序，講者備註列四種邊界操作與回歸工具。第34–37頁串起F1設計卡→F2至少四項需求偏差及呼叫例子→改寫Prompt，標清模擬錯誤片段的來源；第38–40頁明示F3三選一，第41頁承接選項3並說明重測。教師筆記補全週目的對照，講義F1／F2同步釐清既有產出。

保留原45頁、兩個starter類別、六個TODO及作業要求；SampleCase完整實作仍為選做，不新增必交程式。15個Java檔與投影片中的全部Java片段均與本次修改前相同。code fence、27個Markdown本機連結、逐頁引用、原稿／HTML頁數及git diff --check通過；不宣稱學生佔位TODO已完成。

使用Marp CLI 4.5.1／Core 4.4.0同步匯出HTML。以本機無頭Chrome及puppeteer-core 24.43.1檢查全45頁內容邊界、對照列和標題間距及水平溢出，未發現問題。已巡視全頁聯絡表與45條對照文字，並放大檢查第5、19、24、27、33–41、44頁。第一次擷取有少量行內文字未完成繪製的暫態，改為逐頁等待完成繪製後重截，確認文字完整；未把截圖暫態當成教材缺字。本次是全頁縮圖與對照列巡視加重點頁放大，並非全45頁內文逐字放大檢查。快照、逐頁引用清單、截圖及layout-report.json保存於本次visualizations/week05-slide-references（最終截圖qa-stable）。

以OpenJDK／javac 22在新暫存教師副本原樣執行第33頁指令，編譯成功；2/4、{2}得到0/5/1 SUCCESS與一筆日誌，另三列初始零能源／初始達標／空腳本得到EXHAUSTED／SUCCESS／UNFINISHED，均為零筆日誌，與表格一致。MissionLogCheck 15/15通過；暫存編譯副本已清除。其他Java範例未重跑，IDE GUI、其他JDK、真實AI／GitHub與學生試教未新增證據。本次僅修訂week05/codex版本，未提交。

## 2026-10-29：依最新要求只保留Java檔案標示

將上方對照列改為只列對應的.java路徑，移除其他文件、方法與題號；沒有Java引用的頁面不加標示。45頁中36頁保留標示，共51次Java檔案引用，均可定位。README與教師筆記同步說明新格式。移除對照列後與本次修改前比對，投影片內文完全相同，15個Java檔案雜湊亦相同，未重跑Java測試。Marp CLI 4.5.1同步匯出45頁HTML；無頭Chrome檢查全頁邊界、標題間距及水平溢出，無問題。已巡視全頁聯絡表，放大檢查第22、36頁；快照及報告在本次visualizations/java-only-labels/week05-qa。未新增IDE GUI或學生試教證據。

## 2026-10-29：全45頁清楚度檢視與35頁修訂

依使用者先指出第2頁不清楚、再要求全週檢視，逐頁核對原稿、範例、骨架、講義與教師筆記；修訂35頁，保留已足夠清楚的10頁。第2頁明示初始能源20、{1,1,2}的動作與能源，並列正常與漏記第2步後的清單，指出索引1配錯能源6、索引2讀取越界，再引出每筆MissionStep一起保存三欄。此例是刻意假設的錯誤，不是聲稱原教師答案有漏記，也不宣稱物件能自動確保所有輸入與新增正確。

第6–19頁補資料意義、建構呼叫、this錯寫後果、getter呼叫端與顯示責任、預設建構規則、受控改值與static共用；第21–27頁補參考方法的起點及8／1差異、快照14／19、兩欄StepCard與三欄MissionStep的分工，以及人工查詢資料不等於連續任務結果。第28–33頁補TODO位置、回放預期格式、摘要3/0/1/2例子、空清單與最低值起點、終態三數字順序、battery百分比及停止優先。第34–44頁補容量修正細則、審查反例、Prompt規格句、三選一的操作／重測入口、選項3列印、記錄時機錯誤、示範電池與主任務的責任邊界，以及新副本驗收結果。講義A／E1與教師筆記同步說明；六個TODO、任務規則、必做／選做與作業份量不變。

檔案標示維持只列.java，本次補上第42–44頁直接討論的程式位置後，共38頁標示、57次Java路徑引用，均存在。15個Java原始檔雜湊不變；45頁原稿與HTML、27個Markdown本機連結、code fence及git diff --check通過。逐頁檢視清單與修改前快照保存在本次visualizations/week05-full-clarity。

以OpenJDK／javac 22在隔離暫存目錄分開編譯examples與teacher，核對CreateStepDemo、ReferenceDemo、SnapshotDemo完整輸出，與投影片的1/14、2/12、14→8／區域1、快照14／19一致。MissionStepCheck 8/8、MissionLogCheck 15/15、SampleCaseCheck 12/12通過。另以暫存反例重現第2頁的1/6/OK錯配與索引2越界，將第40頁原樣片段放入獨立main執行得到19/19；結果保存於execution-results.json。暫存編譯副本已清除，未把錯誤碼放入正式src。

Marp CLI 4.5.1／Core 4.4.0重新匯出45頁HTML；本機無頭Chrome與puppeteer-core 24.43.1檢查全頁邊界、檔名列和標題間距及水平溢出，無問題。已巡視全頁聯絡表，放大檢查第2、11、21、25–27、29–33、36、40、42–44頁；最後微調後再次放大檢查第2、11、27、42頁。最終截圖與報告在qa-final；這不是全45頁內文逐字放大檢查，未新增IDE GUI、其他JDK、教室投影或學生試教證據。本次僅修訂week05/codex版本，尚未提交。
