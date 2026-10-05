# 第二週 → 第三週能力交接

這是教材覆蓋範圍，不代表每位學生已達標；第三週開場仍需短診斷。

| 可承接能力 | 教學位置 | 學生操作證據 |
|---|---|---|
| int、double、boolean、char、String；八種基本型態 | slides「從 C# 經驗選擇」「八種基本型態」；TypeReferenceDemo | lab A 選型與輸出 |
| 值與參考複製、重新指定；String equals 與 == | slides「基本型態指定運算」至「密室門禁」 | lab B 箭頭與追蹤表 |
| 整數除法、轉型時機與百分比 | slides「為何這個百分比是 0.0」「轉型的位置」；OperatorDemo | lab C 運算預測、lab D 方法 |
| + - * / %、括號與字串串接 | slides「數值計算」「括號」 | lab C、D 探索次數／餘量 |
| =、+=、獨立 ++／-- | slides「指定運算、累加、遞增與遞減」 | lab C.1 狀態追蹤 |
| 比較、&&、\|\|、!、short-circuit evaluation | slides「條件也是值」「short-circuit evaluation」；OperatorDemo | lab C 條件與呼叫觀察 |
| 有效能源與探索資格 | teacher/RoverDashboard.java main | lab D 六筆及獨立範圍題 |
| static 方法、參數、回傳值與輸出分工 | slides「從第一週」「跟做」 | lab 0 診斷、D TODO 5 |
| 預期→執行→反例→最小修正 | slides「AI 說回傳 double」 | lab E、AI_USAGE 範本 |
| 保存與重現 | lab G | 學生 commit、Push 與課後 clone 證據 |

可重用方法：`static double calculateBatteryPercent(int energy)`，`return energy / 20.0 * 100;`。前提為 0 至 20，沒有分支或輸入驗證。固定資料的 energy=7 產生 35.0；其餘標準能源已在作者驗證中執行。

第三週作者須自行介紹 if／else、switch／break、for／while／do-while、Scanner 的 import/new/nextInt 與整數輸入契約、狀態有效範圍與停止原因。不得把兩週 main 的 String[] args 骨架當作已教授陣列。

規則交接：容量 20；探索耗 6 得 2；掃描耗 2 得 1；充電加 5 上限 20；樣本至少 5 成功。本週只計算探索能力，沒有真正扣能源或取得樣本。locked 為診斷用；第三週基本任務預設未鎖定。
