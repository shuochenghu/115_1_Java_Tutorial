# 第四週教材驗證紀錄

日期：2026-10-06。教材不限定 JDK 21 或特定版本。

## 實際環境與編譯

- java：OpenJDK 22，build 22+36-2370；javac 22。
- 使用 `javac -encoding UTF-8 -Xlint:all -d 臨時目錄 ...`，**沒有使用 --release 21**。
- 教師三個檔案與學生「六個 examples + starter」分開編譯，均成功且無警告。同名 MissionLog 不混編。
- 在獨立臨時目錄編譯執行，結束後清除；未改動 Claude 版或前三週材料。

## Java 實測

| 項目 | 方法與實際結果 |
|---|---|
| MissionLogCheck | 15/15 passed；逐字比對完整輸出（換行正規化），包含所有回放欄位、順序、counts、摘要、最低能源、最終狀態及 battery |
| 六條學生必做路線 | 均包含於完整輸出比對，結果與 assignment/teacher_notes 一致 |
| 邊界 | 能源不足後充電、成功優先、初始能源0、初始已達標、空腳本、恰好6格耗盡、19格充電封頂、負數指令、初始同時達標耗盡均通過 |
| 五個核心 examples | 實際執行並逐字核對教師筆記表列輸出，皆一致 |
| ScriptArgs 選讀 | 無參數為 script=[]；3 9 0 為 script=[3, 9, 0] |
| starter | 可編譯執行，五個 TODO 註解保留；行動結果為6/5/3 SUCCESS，日誌、counts與摘要仍為未完成狀態，不視為功能通過 |
| FixedScriptMission | 五筆講義教師示範逐筆執行；負能源、超過容量、負樣本輸出 INVALID_INITIAL_STATE；少於兩個參數提示 Usage |
| AI 模擬錯誤1 | 副本移除 counts 範圍檢查，{3,9,0} 在 counts[9] 發生 ArrayIndexOutOfBoundsException；恢復檢查後反例正常返回 |
| AI 模擬錯誤2 | 副本回放條件改 <=，{0} 先印一步後 get(1) 發生 IndexOutOfBoundsException；恢復 < 後反例及 {1,1,2}、{3,9,0} 正常 |
| 講義 B4 | CommandCounter 改為 {0,2,2,3}，輸出 [1,0,2,1] |
| 講義 B5 | 加強版 for 內重新指定 command=0，原 script 仍為 [1,1,2] |
| 講義錯誤示範 | ArrayBasics 越界在執行時發生 ArrayIndexOutOfBoundsException；ArrayList<int> 在編譯時報 unexpected type |

教師工具不使用 JUnit；擷取 stdout 的程式不是學生先備內容。反例只在副本重現，正式教師解答保持正確。學生仍須自行執行與記錄，不能直接把本表當成本人證據。

## 投影片檢查

- Marp CLI 4.5.1 / Marp Core 4.4.0，輸出38頁可瀏覽 HTML。
- 使用本機無頭 Chrome、puppeteer-core 匯出每頁 PNG，檢查標題、段落、清單、表格及程式區塊邊界；38頁未偵測內容溢出或程式碼水平溢出。
- 已檢視全38頁聯絡表，並放大抽查程式碼頁25、29。這是全頁縮圖巡視與代表頁放大檢查，不宣稱每頁均逐字放大目視。
- 維護工具見 tools/README.md；截圖與報告存本次視覺化工作目錄，不加入學生提交檔案。

## 文件核對

學習範圍、TODO 1–5、E/F操作順序、六條主路線、五筆教師觀察與最低能源定義已交叉核對。學生講義與AI紀錄可填；完整答案另存teacher。22個教材檔案中的22個Markdown本機連結均存在；38頁Markdown與HTML頁數一致，五個TODO註解齊全。文字檔無行尾空白，git diff --check通過（新增未追蹤檔另以文字檢查核對）；未保留class或臨時編譯產物。參考來源是課綱、前三週Codex、第四週Claude與Deitel EPUB相關章節；官方API連結附於教師筆記。

## 尚未驗證與使用限制

- 其他 JDK 版本與不同作業系統實際執行；不由目前成功推論所有版本都已驗證。
- 教室 VS Code/IntelliJ GUI、JDK設定、真實 GitHub Desktop登入與push/pull，以及全新遠端clone。
- 150分鐘學生試做、理解程度與教室投影效果。
- 主作業不讀命令列參數；教師與選讀工具的輸入契約是Java int範圍內整數，不測完整非整數/溢位例外處理。

暫存副本編譯與本機截圖不等於clean-clone、教室GUI或學生本人測試。前三週歷史驗證中的JDK21目標紀錄保留原樣，本週依使用者要求不鎖定版本。

## 2026-10-15：第3至5週版本要求複核

本週原已不鎖定JDK，README與講義統一為「不限定特定JDK版本」。本次以目前OpenJDK／javac22、不指定--release／-source／-target，在新暫存目錄分開編譯examples、starter、teacher，均成功，MissionLogCheck維持15/15。程式與投影片內容未改，未重作本週版面檢查。暫存產物已清除；其他JDK、IDE GUI與教室實測仍未覆蓋。跨週摘要見[第3至5週紀錄](../../week03_week05_JDK檢視紀錄.md)。

## 2026-10-29：教師解答標示學生 TODO 對應位置

依使用者要求，在 teacher/MissionLog.java 的五段完整解答前加入「對應學生版 TODO 1–5」註解，README 檔案索引同步說明。學生骨架與投影片未修改。

移除整行註解後與修改前逐行比較，程式內容一致；五個對應標記各出現一次。以目前 OpenJDK／javac 22 在暫存目錄編譯教師程式，MissionLogCheck 15/15 通過；暫存編譯產物已清除。本次僅調整註解與說明，未重作投影片版面、IDE GUI 或學生試教驗證。

同日依使用者追加要求，在教師 MissionLog 的各個 case 旁標示 1＝探索、2＝掃描、3＝充電、0＝返回及動作規則，default 標示無效指令；預設腳本旁補上動作順序。保留字串常值並移除註解後，與原版本比較確認程式內容不變，四個指令標記齊全；此次追加僅改註解，未重跑執行測試。

同日將指令動作註解補入 starter/MissionLog.java，以及 examples 中使用指令編號的 ArrayBasics、CommandCounter、ScriptArgs、ListBasics。計數陣列標示各索引的動作，ListBasics 區分 remove(0) 的索引與返回指令；計數、解析及清單範例標明不執行任務動作。未使用指令編號的 ArrayReference、CollectionsDemo 保留原樣。比較 examples 與 starter 共七個 Java 檔，確認移除註解後程式內容不變，學生五個 TODO 註解逐字保留；以目前 javac 22 在分開的暫存目錄編譯 examples、starter，均成功。暫存產物已清除，本次未重跑執行測試。

## 2026-10-29：第32頁補上教師工具操作

依使用者要求，在「教師邊界示範」頁補上終端機工作位置、教師程式編譯指令、FixedScriptMission 5 0 1 3 0 執行範例，以及初始能源／樣本／後續指令的參數順序與動作。原五筆案例及學生觀察提醒保留，講者備註補充空腳本只傳20 0。

使用既有Marp CLI 4.5.1重新匯出HTML，原稿仍為38頁，逐頁比較確認僅第32頁內容改變。以本機無頭Chrome與既有puppeteer-core產生修改前後的第32頁截圖，DOM邊界與程式區塊水平溢出檢查皆無問題，已並列檢視兩張截圖，確認新增指令、參數、表格與頁尾無截斷或重疊。未重作其他37頁的視覺檢查；圖檔與檢查報告保存在本次visualizations的week04-slide32，不加入學生教材。

在暫存教師程式副本中以javac 22執行投影片原樣指令，編譯與示範均成功；結果為10/0/1 RETURNED、3筆日誌、最低能源5，與表格一致。暫存編譯副本已清除；未變更Java程式、學生實作或Claude版，未新增IDE GUI或學生試教證據。

## 2026-10-29：重新檢視全週投影片並釐清第33頁目的

重新閱讀38頁投影片、學生講義、作業、教師筆記及第三至第五週銜接，確認主線為「保存過程→回放與摘要→說明資料需求→判讀AI建議→用反例驗證修改」。第33頁原本只有需求項目，缺少活動目的、產出與下一頁用途；本次在第3頁明示整週成果，第33–35頁分別補明寫Prompt、判讀容器建議及執行反例驗證的目的與學生產出，沿用既有講義F2／F3，不增加程式作業或真實AI服務要求。教師筆記新增各頁段目的與預期能力對照，學生講義F2同步說明操作順序。

保留本次修改前的檔案快照於visualizations/week04-purpose-review。逐頁比較確認仍為38頁，本次僅第3、33、34、35頁內容改變，第32頁既有操作說明完整保留；Java檔案雜湊與修改前一致，學生TODO及任務規則不變，故未重跑Java測試。

使用既有Marp CLI 4.5.1同步匯出HTML。以本機無頭Chrome與puppeteer-core檢查全38頁DOM邊界及程式區塊水平溢出，無問題；已巡視全頁聯絡表，並以可讀尺寸逐一檢視第3、33、34、35頁，確認目的、表格、學生產出與頁尾沒有截斷或重疊。修改前後快照、截圖與layout-report.json保存在本次visualizations的week04-purpose-review，不加入學生src。Markdown code fence、HTML更新內容與git diff --check通過。本次不是其餘各頁逐字放大檢查，未新增教室投影或學生試教證據。

## 2026-10-29：第20頁補充集合用途與例子

依使用者要求，將第20頁改為「依資料需求分辨List、Set、Map」，加入指令順序、指令種類、代碼對應名稱三種探測車例子及ArrayList／HashSet／HashMap實作類別，說明本週日誌選用ArrayList的理由。分清Collection與Collections，Map屬框架但不繼承Collection；Set順序依實作而定，Map的鍵與值語意在教師備註與教師筆記補充。本週仍只實作ArrayList，未增加學生練習。

使用既有Marp CLI 4.5.1同步匯出HTML，修改前後皆38頁，逐頁比較確認本次僅第20頁改變。以本機無頭Chrome與puppeteer-core檢查修改前後第20頁的DOM邊界與水平溢出，均無問題；已開啟兩張截圖，以可讀尺寸確認四欄表格、說明與頁尾無截斷或重疊。快照、圖檔與報告保存於本次visualizations的week04-slide20。Java檔案雜湊不變，故未重跑程式測試；未重作其餘37頁視覺檢查。HTML內容與git diff --check通過。

## 2026-10-29：第16頁釐清腳本、日誌與筆數管理

依使用者指出的說明不清問題，第16頁改以{1,1,1,1}從20/0出發、第三次探索達標的實例，區分四筆預定指令與三筆已讀日誌。比較長度4的int陣列填入三筆後需另記used=3，以及ArrayList逐筆add後由size()取得3；說明陣列末格0是未使用格的預設值，兩種容器都能保存歷程，本週選ArrayList是為了簡化筆數管理。學生講義D與教師筆記D同步說明，不增加陣列版實作或其他學生作業。

使用既有Marp CLI 4.5.1同步匯出HTML，仍為38頁，本次僅第16頁改變。以本機無頭Chrome與puppeteer-core檢查修改前後該頁的DOM邊界及水平溢出，均無問題；已開啟前後截圖，以可讀尺寸檢查例子、表格及頁尾無截斷或重疊。快照與報告保存於本次visualizations的week04-slide16。Java檔案雜湊不變，未重跑程式測試；本次未重作其餘37頁視覺檢查。HTML內容與git diff --check通過。

## 2026-10-29：全38頁補齊程式、活動與作業對照

逐頁核對投影片、examples、starter、teacher、學生講義與作業，在每頁上方增加可見的來源／活動對照，標明程式檔與相關方法、TODO、講義題號及適用作業段落；概念比較、錯誤副本、教師工具及課後選讀各自說明角色。第6頁檔案索引改用完整教材相對路徑，README與教師筆記補明教材路徑和學生src的關係。不新增學生作業，不把教師檢查工具列為學生實作。

38頁各有一條對照，96次檔案引用均可定位。Java檔案雜湊與本次逐頁標示前一致，故未重跑Java測試。Marp CLI 4.5.1重新匯出38頁HTML；以本機無頭Chrome與puppeteer-core 24.43.1檢查全頁內容邊界、對照文字與標題間距及水平溢出，未發現問題。已巡視全頁聯絡表與38頁對照文字，另放大檢查第6、20、28、32、37頁；這是全頁縮圖及對照文字巡視，並非全頁內文逐字放大檢查。快照、逐頁對照清單及截圖保存在本次visualizations/week04-slide-references。未新增IDE GUI、教室投影或學生試教證據。

## 2026-10-29：依最新要求只保留Java檔案標示

將上方對照列改為只列對應的.java路徑，移除其他文件、方法與題號；沒有Java引用的頁面不加標示。38頁中33頁保留標示，共37次Java檔案引用，均可定位。README與教師筆記同步說明新格式。移除對照列後與本次修改前比對，投影片內文完全相同，10個Java檔案雜湊亦相同，未重跑Java測試。Marp CLI 4.5.1同步匯出38頁HTML；無頭Chrome檢查全頁邊界、標題間距及水平溢出，無問題。已巡視全頁聯絡表，放大檢查第23、32頁；快照及報告在本次visualizations/java-only-labels/week04-qa。未新增IDE GUI或學生試教證據。
