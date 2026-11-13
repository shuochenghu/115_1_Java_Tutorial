# 第六週Codex教材驗證紀錄

日期：2026-10-22。執行者：Codex。這是作者驗證，不是學生本人紀錄；下列結果依本次實際執行補齊。

## 已完成的程式驗證

本機OpenJDK22（22+36-2370）、javac22、Python3.14.0、Node.js24.11.0。日常編譯未指定--release／-source／-target，也未使用預覽語法。以tools/verify.py在本週內新暫存目錄分開編譯，完成後清除。

- examples六檔、starter五檔、teacher八檔與學生合併11檔編譯成功。
- RelationDemo與SampleCaseDemo完整輸出符合事先寫定期望；RoverDemo三行正確。
- starter能編譯安全返回，但日誌為空、0步、UNFINISHED，明確不算完成功能。
- MissionLogCheck 15/15：逐位元組沿用第五週完整輸出期望，包含六條作業路線、空腳本、初始停止、恰好能源、拒絕後充電、封頂、無效負指令及成功優先。
- MissionStateCheck 16/16：直接停止防護、拒絕不變、返回不讀後續、計數陣列副本、快照、最低值、空資料、獨立歷史及String內容比較。
- 學生提供檔＋完成Mission＋範例合併編譯，預設輸出與教師版相同。
- 四個錯誤副本：漏Mission停止、只記OK、最低初值0、漏Rover停止，均被對應檢查拒絕。
- E3實際重現錯誤版overflow=true/count4、fill到5、unload5；正式範例恢復後overflow=false/count2、fill3、unload3。
- final參考示範：final ArrayList可add並印1；重新指派版本編譯失敗。僅教師補充，不列學生先備。

## 產圖、文件與投影片

### 本機產圖與立即預覽

- PlantUML MIT 1.2026.9（18d0aae），由官方GitHub發行取得；jar存於本次視覺化輸出目錄，不加入教材或學生src。所有來源均明列Smetana，未為本次另安裝Graphviz；版本命令顯示本機原有dot，不把這次說成「完全未裝Graphviz環境」測試。
- 10份puml以UTF-8位元組管線產生同名SVG及教師檢視PNG，全部語法成功；補上繁體中文註記後重新產生。已看10圖縮圖聯絡表，包含完整參考、錯誤AI圖與三份練習骨架，未見裁切或文字重疊。
- README的學生Java管線命令實際於Windows PowerShell5.1執行，practice.svg與作者直接產出的SVG位元組相同。這是本機CLI測試，不是教室安裝或GUI測試。
- tools/render_diagrams.py實測puml輸入與Markdown區塊擷取；產圖與學生方式逐位元組一致。另以錯誤來源確認非0退出且不寫入有效命名的SVG。工具沿用來源的Smetana設定，避免命令列重複注入設定使產物不同。

### Marp與視覺檢查

- Marp CLI4.5.1／Marp Core4.4.0，輸出37頁HTML，與Markdown頁數一致。初次npx離線入口因環境快取索引不可用而失敗，改用已存在的marp-cli.js搭配node匯出成功；未安裝新npm套件。
- 以bare模板、既有puppeteer-core及Chrome產生37張截圖與版面報告。tools/check_slides.cjs沿用第四週工具，增加獨立profile及圖片檢查；所有文字、表格、程式及圖片均在頁內，無程式水平溢出或圖片載入失敗。
- 沙箱內Chrome啟動失敗後，使用本次獨立profile於允許環境完成檢查，不讀取個人登入狀態。圖更新為繁體中文後再跑一次，37頁、0項邊界問題。
- 已巡視37頁縮圖，另放大檢查第7、8、14、20、30、32頁（類別圖、長建構子表格、關係、AI圖與Prompt）；中文圖更新後重看20與30頁，未見截斷或重疊。這不是逐頁放大37頁，也不等於教室投影試教。
- 截圖、PNG與layout-report保存在本次視覺化目錄week06-qa及diagrams-qa，不交付學生src。正式HTML引用本週diagrams，分享時需連同圖檔。

### 文件、圖碼與原稿保留

- 7個類別在完整參考圖中的成員名稱與可見性通過程式對照：MissionStep、Battery、Lamp、Charger、Mission、Rover、SampleCase。參數、回傳型態、建構順序與本週五條關係另人工核對；部分視角與練習骨架不冒充完整API圖。
- 40個本機Markdown連結／圖片引用存在；UTF-8、圍欄、Java檔名與public class、三個TODO及37頁Markdown／HTML同步檢查通過。
- 新增文字檔另檢查行尾空白及git diff --no-index --check；排除Git「LF將轉CRLF」的一般提示，沒有空白錯誤。git diff --check亦通過，沒有只依賴不涵蓋未追蹤檔的一般diff。
- 建立前保存191個前週與Claude檔案的SHA-256，本次完成後核對均相同。新增內容只在week06/codex版本，未更動共用skill、課綱、前週或Claude版。根目錄沒有PROJECT_MEMORY.md，本週決策保存在handoff。
- 已對齊150分鐘節奏、三組TODO、六路線、圖的指定補填範圍、AI三項問題與反例／修正／重測、繳交位置與教師答案。暫存bare HTML與學生指令測試SVG於交付前清除，不交付.class或暫存編譯目錄。

## 證據邊界與未驗證

- 未在其他JDK／作業系統執行，不把JDK22通過推論到所有環境。
- 未做教室VS Code／IntelliJ GUI操作與教室PlantUML配置，不把CLI當GUI證據。
- 未實際登入／push／clone GitHub；學生G段是操作指引，本次未模擬遠端或測真實遠端。
- 未呼叫真實AI服務；AI圖、Prompt與填寫示例為教師自編或明確虛構。
- 未做150分鐘學生試教、教室投影可讀性或理解程度觀察。
- 契約外資料、重複run、共享Rover與並行執行不在本週驗收範圍。
- 社團遷移題只有紙上參考，未編寫或執行社團Java實作。

未提交、未fetch，不能宣稱與遠端同步。原教材與Claude版保留檢查於交付前記錄。

## 2026-11-06｜獨立程式關聯補充投影片（20頁初稿紀錄）

依使用者要求，另製[程式關聯投影片原稿](week06_program_relationships_codex.md)與[HTML](week06_program_relationships_codex.html)，共20頁。內容限兩週Codex程式，說明第六週teacher六個非Check類別、三個入口、主任務合作、快照、停止責任、第五週責任搬移，以及獨立SampleCase的位置。每頁附教師備註，末頁問題答案放備註；不增加學生必交工作。

- 新增teacher_entries.puml／同名SVG，表示FixedScriptMission→MissionLog與RoverDemo→Rover；沿用既有before.svg、after.svg。以既有PlantUML MIT 1.2026.9匯出新圖，未改動原圖。
- Marp CLI4.5.1／Core4.4.0匯出20頁HTML。以既有check_slides.cjs、puppeteer-core及獨立profile的本機Chrome檢查20頁，圖片均載入，版面邊界與程式水平溢出0問題。已逐頁檢視20張完整截圖，圖、表、中文與程式未見裁切重疊；釐清teacher目錄執行指令的措辭後，重新匯出與檢查。
- 以OpenJDK22在隔離暫存目錄分別編譯第5、6週非Check教師程式。比較MissionLog預設腳本與FixedScriptMission的20/0＋{1,3,0}、2/4＋{2}、20/0＋{1,1,1,1}，四組完整輸出一致；確認投影片能源、樣本、行動數、成功優先與停止後不多記的數值。
- 實跑本週RoverDemo，輸出為OK/14/2/1、STOPPED/0、STOPPED/20。兩週MissionStep、FixedScriptMission及第五週teacher/SampleCase→本週examples/SampleCase，三組來源逐位元組相同。
- 本次未修改Java，只針對簡報敘述執行上述範例，未重新執行2026-10-22的完整15＋16項檢查或錯誤變異。第12頁停止反例沿用原教材既有驗證，本次未再次製作錯誤副本。
- Markdown／HTML均20頁，每頁教師備註存在，三個SVG引用及本次文件連結存在；UTF-8、行尾空白與git diff --check通過。暫存編譯檔與bare HTML於完成後清除。
- 建立前保存第5、6週157份既有檔案SHA-256；允許變動的只有本週Codex README、verification、handoff三份索引／紀錄，其餘154份檔案雜湊不變，包括原37頁投影片、Java、第五週與Claude版。新增內容僅在本週Codex目錄。

本次截圖、layout-report.json、code-comparison.json及保留檢查證據位於本機視覺化目錄program-relations-qa，不交付學生src。HTML分享時仍需連同diagrams資料夾；沒有教室投影、學生試教、跨JDK或跨平台證據。未提交或推送。

### 同日依使用者指定精簡：現行9頁

使用者要求只把「第六週teacher非Check程式彼此的關係，以及與第五週teacher的關聯」這題回答做成投影片。現行原稿／HTML已改為9頁，保留程式分工、合作圖與呼叫流程、輔助入口、第五週設計、責任搬移及SampleCase位置；移除名詞教學、操作示範、數值追蹤、停止反例與檢核題。README、handoff已同步。

重新匯出Marp HTML；9頁版面邊界／圖片載入檢查0問題，巡視全頁縮圖並放大檢查新改寫的合作流程頁。Markdown與HTML均9頁、圖片引用存在，git diff --check及新增原稿行尾空白檢查通過。沿用檔案相同性重新核對；Java未改，本輪未再編譯或執行程式。截圖與報告另存program-relations-qa/focused，20頁初稿的紀錄僅代表歷史檢查，不代表現行頁數。暫存bare HTML已清除。

### 同日改以使用者關係圖為主體

依使用者「我要這張圖」「放在投影片中為主體並說明」的指示，將附件PNG原樣複製為diagrams/teacher_relationships_overview.png，SHA-256與附件相同，未裁切、重畫或修改像素。第3–5頁均以左側59%的原圖為主體，右側分別說明整體關係、Mission與狀態／歷史的連結、FixedScriptMission與RoverDemo入口；維持9頁，後段保留第五週對照。

重新匯出HTML並以Chrome檢查9頁，版面邊界0問題；已放大檢視第3–5頁，確認原圖完整顯示、中文與說明未裁切。背景圖不在既有工具的img載入檢查範圍，因此另核對本機檔案、HTML引用，並以三頁實際截圖確認顯示。截圖與報告在program-relations-qa/user-diagram。Java未修改，本輪未編譯執行程式；README與handoff同步，暫存bare HTML清除。HTML分享需連同diagrams資料夾。

## 2026-11-07 claude 投影片（Claude Code 製作）

使用者要求比照第五週，由 Claude 依本目錄教材另製投影片：`week06_slides_codex_claude.md`／`.html`，44頁，每頁 `by-claude` 徽章。結構：第一部分（第5–15頁）只用 examples 的 Battery／Lamp／Charger／RelationDemo 教讀圖、PlantUML、關聯／相依與多重性；第二部分（第16–38頁）以 starter 說明作業，SampleCase 的 E 段因屬必做而放在此段（程式在 examples）；附錄（第39–44頁）放 teacher 工具、after／samplecase_ref／mission_focus 參考圖與 final／組合補充。左上標籤藍 examples、綠 starter、灰 teacher；頁尾依段落標示。TODO 1 只列順序，TODO 2、3 只列規則，不投影答案。原37頁投影片、程式關聯補充投影片及其他教材、程式未改；本段與 README 索引列為 Claude 依使用者要求補記。

- 環境：OpenJDK／javac 22（build 22+36-2370），未加 --release；examples、starter、teacher 分別在暫存目錄編譯。
- CLI：RelationDemo、SampleCaseDemo、RoverDemo、starter MissionLog（空日誌）、teacher MissionLog 預設路線輸出與投影片一致；MissionLogCheck 15/15、MissionStateCheck 16/16；FixedScriptMission `0 0 3 1`、`20 5 1`、`2 4 2`、`20 0`、`20 0 3 9 0` 與投影片表格一致。另在暫存副本重現兩個反例：刪除 Mission.run 停止檢查跑 `{1,1,1,1}` 得第4筆 STOPPED、steps=4／rejected=1／ignored=0；刪除 SampleCase 超量條件得 overflow=true／count=4、fill 5、unload 5。六路線其餘四條依 assignment 核對，本次未逐條重跑。
- 投影片：Marp CLI（npx @marp-team/marp-cli@latest）以 `--html` 匯出 HTML；另以 `--allow-local-files --images png` 匯出44頁 PNG，用縮圖聯絡表逐頁巡視。修正第6、10、18、25頁下緣溢出，第13頁關係圖過小，第21頁 Rover 程式被自動縮小，第29頁六路線表右側截斷，修正後重看修改頁。第21、25頁程式為節錄，講者備註已說明。
- 未驗證：無頭瀏覽器自動溢出量測、教室投影可讀性、學生試教。尚未提交。

## 2026-11-11 繁體中文輸出驗證

- OpenJDK 22，使用 javac -encoding UTF-8 -Xlint:all 分開編譯範例、學生起始碼及教師解答。以 UTF-8 擷取主控台輸出。
- 執行該週 tools/verify.py，中文預期輸出、教師檢查與原有回歸案例通過。
- 歷史驗證中的英文輸出反映當時的程式，現行預期輸出以本週講義和教師筆記為準。
- Marp CLI 4.5.1 重新匯出 3 份 HTML（9/37/44 頁）。以無頭 Chromium 檢查程式碼區塊與表格的版面邊界，未偵測到溢出。
