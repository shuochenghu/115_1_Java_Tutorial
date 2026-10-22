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
