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
