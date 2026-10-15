# 第五週教材驗證紀錄

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
