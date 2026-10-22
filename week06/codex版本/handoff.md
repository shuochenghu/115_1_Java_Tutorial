# 第六週Codex交接｜從UML到第七週繼承

## 承接與本週新增

承接第五週Codex的MissionStep、物件清單與具名目前狀態；完整Rover是本週提供的銜接，不能寫成第五週共同能力。學生新增類別圖讀寫、關聯／相依、多重性、責任表、Mission局部重構，以及用需求與反例判讀AI類別圖。

程式必做只有Mission三組TODO：run、countOk、lowestEnergy。類別圖只補指定部分成員及關係，不要求完整API抄寫。SampleCase活動與主任務獨立，上週選做實作不算先備。社團題只列選做，不算全班已學會。final與聚合／組合只作教師補充。

## 精確契約

- 版本只限week06/codex版本；原教材、前週及Claude版保留。
- main固定int[] script，預設1/1/2；不讀命令列args。
- 每次新建Rover與Mission，只run一次，script非null；初始energy 0..20、samples非負。run與查結果期間不由外部另外操作同一Rover。不承諾重複run、共享Rover或並行行為。
- Rover保存energy、samples、actionCount，集中execute入口；成功／耗盡時回STOPPED，不改狀態；探索／掃描／充電private。指令0返回由Mission控制流程。
- Mission保存rover、history、returned；只在未停止時讀下一筆，先execute再new MissionStep；每筆已讀指令都記，0記完再停。結果順序SUCCESS、EXHAUSTED、RETURNED、UNFINISHED。
- MissionStep檔案逐位元組沿用第五週教師版，建構順序command/energy/result，getter名稱getEnergy；無setter，非final欄位，不要求新增final。
- counts計已讀0..3（含拒絕與0），無效整數不計；steps日誌數、ok成功行動數、rejected排除OK與RETURN、ignored腳本長度減steps。
- lowestEnergy無紀錄回-1，報表印N/A；有紀錄只看行動後快照。battery格式仍保留。15項既有完整輸出期望逐位元組沿用第五週MissionLogCheck。
- getStep交出單筆對外只讀資料；不交出內部可變history；countCommands回新陣列。

## 類別圖契約

主線畫普通關聯與多重性，不用菱形作所有權推論。Mission→Rover為1（非null契約），Mission→MissionStep為0..*；MissionLog在方法中建立／使用三者，畫三條相依。D圖有意省略非指定成員，不能因省略就說方法不存在。圖原始碼puml與SVG一起保存，DESIGN.md嵌入本人SVG；修改後立即重新產圖。

## 下一週入口與待診斷

第七週課綱為繼承基礎。先診斷學生是否能說出四類別責任、實例／static、private與套件存取、參考與快照。建立過類別圖不代表已會繼承、多型或覆寫。

HeavyRover「探索一次得3份」是動機，不是本週已交付的功能。第七週若引入extends／super，可先教子類別新增能力；改探索產量涉及擴充點與覆寫，依第7–8週範圍安排。不能以為把explore改protected就能直接修改Rover的private energy/samples；可考慮保留行動內部流程，再設計回傳產量的受保護方法，實際方案由下一週重新界定，不先擴張本週教材。

## 狀態與驗證

本週新教材生成日期2026-10-22。實際環境、測試、匯出與視覺範圍見[verification](verification.md)。不以課堂規畫代替試教，不以本機CLI代替IDE或遠端服務。根目錄沒有PROJECT_MEMORY.md，本週決策保存在本檔及驗證紀錄；初次生成時未另建使用者層級記憶。

未建立commit、未fetch，不宣稱與遠端同步。交接時仍需重查最新Git狀態，避免把此描述當成未來現況。

## 2026-10-22晚間｜回家接續工作快照

使用者要求先記錄進度，方便回家繼續。第六週Codex教材已生成完成，本次只補交接，不重做教材或開始第七週。依使用者明確要求，另存一筆工作記憶；換電腦時仍應以實際帶回／同步的專案檔案為準。

### 已完成及入口

- 目錄week06/codex版本，共54個交付檔；README.md是索引，week06_slides_codex.html是37頁投影片。
- 150分鐘三節配置；先提供完整Rover銜接第五週，再由學生完成Mission三組TODO（run、countOk、lowestEnergy）。
- 包含學生講義、作業、DESIGN與AI紀錄範本、教師答案、10份puml與同名SVG、驗證與維護工具。
- AI完整活動僅獨立SampleCase；社團器材題選做；聚合／組合與final只作補充。Codex固定script與MissionStep建構順序保留，不混用Claude版。
- Java檢查：OpenJDK22，15項既有完整輸出回歸及16項狀態檢查通過，另有錯誤變異與AI超量反例。
- 37頁HTML與Markdown一致、版面邊界0問題；全頁縮圖巡視與6頁放大檢查，非逐頁放大或教室試教。
- 學生產圖命令已於本機Windows PowerShell5.1實測；教師產圖工具正常。jar與PNG等QA檔在本機視覺化目錄，不在教材，家中需另備官方PlantUML jar。
- 前週與Claude版191個既有檔案雜湊不變；沒有留下.class或暫存編譯目錄。

### 接續順序

1. 先讀本檔、verification.md、README.md及專案共用java-course-material-authoring skill，再看最新Git狀態，避免從記憶直接覆蓋新修改。
2. 可先人工檢視投影片與student_lab.md，評估150分鐘負擔；本週教材已完成，不需從零生成。
3. 上課前仍須確認教室JDK、PlantUML jar實際位置、VS Code／IntelliJ來源根與入口；IDE GUI、真實GitHub操作、真實AI服務及學生試教尚未驗證。
4. 若修改程式，執行tools/verify.py；若修改圖或投影片，重新產圖／匯出並檢查版面，同步相關學生與教師文件。只修改Codex版本，原稿與Claude版保留。
5. 使用者明確要求後再進入第七週；本週僅保留HeavyRover作為繼承動機。

### Git與攜回狀態（本次查核）

分支main，HEAD為6885de0（本機端圖片）；git status --short為「?? week06/」。第六週兩版目前都在未追蹤資料夾內，本次未提交、未推送、未fetch。記錄完成不代表已把檔案傳到家中電腦；換電腦前需同步或攜回整個week06及相關專案檔案，尤其HTML須連同diagrams。請勿在新電腦看到舊HEAD就誤以為第六週教材遺失。

回家可使用的接續提示：「接續Java課程第6週Codex教材，先讀week06/codex版本/handoff.md與verification.md，核對目前檔案後，再依我的要求調整。」
