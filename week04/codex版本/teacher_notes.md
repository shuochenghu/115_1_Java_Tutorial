# 第四週教師筆記與完整解答

## 定位與時間

依課綱第四週：陣列、ArrayList、Collections Framework 入門；Prompt 描述資料結構需求。學生已有基礎 C# WinForms，教 Java 差異與容器用途，不重新教一套從零開始的迴圈。

| 節次 | 50 分鐘安排 |
|---|---|
| 第 1 節 | A 取回/診斷10；B 陣列/計數30；C 參考10 |
| 第 2 節 | D ArrayList15；E 日誌/回放/計數35 |
| 第 3 節 | F 摘要10、Prompt10、AI反例10；G 檢核5；H 保存15 |

E 先做 TODO 2→3→1；F1 做 TODO 4→5，再補齊 E 六條路線。若進度落後，縮短 remove 短示範與策略討論，六條測試可課後補齊；不得省略範圍檢查、空日誌防護或最後保存時間。此節奏未經學生試做。

JDK 不鎖定特定版本，不用 --release 21；傳統 main、一般 API，不用預覽功能。課前確認兩套 IDE 實際執行的 JDK 與 javac 是否一致。CLI 成功不代表 IDE GUI 已驗證。

## 投影片主線與各段目的

整週解決「只剩最後狀態，無法還原執行過程」：先學會保存與處理多筆資料，再說清楚選擇資料結構的理由，最後用執行證據檢查修改。

| 頁次 | 教學目的 | 學生要能做或解釋什麼 |
|---|---|---|
| 1–6 | 建立問題、成果與操作入口 | 說明相同終態為何不能辨識路線，找到範例與學生骨架 |
| 7–12 | 用陣列保存固定資料與計數 | 走訪腳本、分辨索引與值、檢查合法範圍 |
| 13–15 | 理解陣列參考與內容比較 | 預測共同指向、改元素與改參考的不同結果 |
| 16–20 | 依日誌需求認識集合 | 用ArrayList逐筆新增，說明順序、重複與有效筆數 |
| 21–27 | 把容器接進原任務 | 同步保存三欄、回放、計數，區分日誌筆數與成功行動 |
| 28–32 | 整理摘要並檢查邊界 | 使用frequency/min、保留日誌順序、處理空資料與停止狀態 |
| 33–35 | 從需求判讀建議，再以執行驗證修改 | 寫Prompt、完成兩項建議判斷、選一項錯誤重現與修正 |
| 36–38 | 檢核、保存與交接 | 解釋三欄對齊問題，保存可取回成果，辨認選讀範圍 |

第33頁是由「會實作」轉向「能說明需求」；第34頁把學生寫的需求當作判讀依據；第35頁再處理「如何證明程式修改正確」。三頁沿用原講義F2／F3與既有任務，沒有新增另一份程式作業或要求登入AI服務。

投影片上方只列對應的.java教材相對路徑，沒有對應程式的頁面不加標示；方法、活動操作與必做／選做依各頁內文說明。學生在自己的src開啟同名程式；來源位置不代表要把teacher答案或工具複製進src。

## A 解答：同一終點，不同路線

{1,1,2} 與 {2,1,1} 都得到 6/5/3 SUCCESS；最後狀態不足以分辨順序。暖身追問 {3,9,0}：讀取三筆指令，成功行動仍為 0。

起始碼能編譯、執行行動，但空日誌、全零 counts 和 TODO 摘要是尚未完成的占位；不能因此判定功能通過。

## B 解答：陣列

ArrayBasics：script=[1,1,2]，length=3，counts=[0,0,0,0]，labels=[null,null]；步1=1、步2=1、步3=2；sum=4。數值加總不是有效行動次數。

合法索引 0..length-1；長度0沒有合法索引。副本取 script[3] 會在執行時發生 ArrayIndexOutOfBoundsException；不是編譯錯誤。錯誤文字依 JDK 可能不同，不背行號。

CommandCounter 原資料 counts=[1,2,0,1]；{0,2,2,3} 為 [1,0,2,1]。負數與9必須排除。加強版 for 的 int command 是元素值副本，command=0 不會改掉原 int[]；要改元素須用索引。對參考元素，不可擴大推論成「加強版 for 不能修改任何物件」。

C# 陣列 Length 對應 Java length 欄位；String 為 length()，ArrayList 是 size()。new int[4] 與 new String[2] 的元素有預設值，不代表任意未初始化區域變數也有預設值。

## C 解答：陣列參考

alias change=[19,14,8]；element change=[18,14,8]；replaceLocal 內=[20,20,20]；回 main 仍=[18,14,8]。相同參考=false，相同內容=true。

Java 一律傳值，參考型態傳入參考值的副本；兩份參考可指向同一物件，所以修改元素可被呼叫端觀察。重新指定方法參數只改區域參考。不說「Java 陣列是 pass-by-reference」，也不把參考描述為可操作的實體位址。

## D 解答：ArrayList

第16頁先區分「預定指令腳本」與「實際已讀指令日誌」。從20/0出發，{1,1,1,1}每次探索取得2份樣本，第三次已累積6份、剩能源2，第四筆不讀不記。若只以長度4的int陣列保存已讀指令，填入三個1後內容為[1,1,1,0]，最後0是未使用格的預設值，須另記used=3；ArrayList逐筆add後為[1,1,1]，size()直接回報3。這是便利性比較，兩種容器都能保存歷程；本週用ArrayList簡化實際筆數管理，接第17頁ListBasics的新增、讀取與筆數操作，不新增陣列版作業。

ListBasics：commands=[1,1,2]，size=3，first=1；set 後 [1,3,2]；remove(0) 後 [3,2]；remove(Integer.valueOf(3)) 後 [2]；empty=false。

ArrayList<int> 不合法，使用 ArrayList<Integer>。本週只要理解型態標籤、裝箱/拆箱；泛型設計在第十一週。remove(int) 刪索引，remove(Object) 刪第一個匹配值；先用小範例示範，過載解析在第十週。本週不要求 Integer 快取推論，也不把任何大數字 == 結果寫成跨環境保證。

## E 完整解答與驗收

完整碼見 [teacher/MissionLog.java](teacher/MissionLog.java)，不要覆蓋 starter。狀態維持具名變數；runMission 的迴圈先檢查停止條件，每步 switch 後同時 add 三欄。

| TODO | 教師答案的重點 |
|---|---|
| 1 | new int[4]；加強版 for 讀 commandLog，0<=command<counts.length 才累加 |
| 2 | commandLog.add(command)、energyLog.add(energy)、resultLog.add(result) 同時執行 |
| 3 | i<commandLog.size()，三欄用 get(i)，step 用 i+1 |
| 4 | frequency 找 OK/RETURN；rejected=steps-ok-returned，ignored=scriptLength-steps |
| 5 | 空清單印 N/A；其他情況印 Collections.min(energyLog) |

六條主路線的狀態、counts、摘要、最低能源見 assignment.md。逐筆期望如下，順序為「指令/結果/能源」：

| script | 完整回放 |
|---|---|
| {0} | 0/RETURN/20 |
| {1,1,2} | 1/OK/14；1/OK/8；2/OK/6 |
| 五次2 | 2/OK/18；2/OK/16；2/OK/14；2/OK/12；2/OK/10 |
| {3,9,0} | 3/ALREADY_FULL/20；9/UNKNOWN_COMMAND/20；0/RETURN/20 |
| {1,1,1,1} | 1/OK/14；1/OK/8；1/OK/2；第四筆未讀取 |
| {2,1} | 2/OK/18；1/OK/12 |

預設完整輸出：

```text
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
step 3: command=2, result=OK, energy=6
counts[0..3]=[0, 2, 1, 0]
steps=3, ok=3, rejected=0, ignored=0
lowest energy=6
final energy=6, samples=5, actionCount=3 -> SUCCESS
battery=30.0
```

常見錯誤：在行動前記能源；只保存 OK；三欄漏一欄；counts 未驗範圍；把回放最後條件寫 <=；將腳本用完視為 RETURNED；把拒絕也算成功行動。用 {3,9,0}、{1,1,1,1}、{2,1} 分別定位，避免一次改多處。

教室展示：完成 TODO 2 時只核對日誌，其他占位仍未完成；不能要求每個中間階段都已有最後摘要。

## F1 解答：Collections 與邊界

CollectionsDemo：min=6、max=20、frequency(8)=1，original=[14,8,20,6]，sorted=[6,8,14,20]，ok=2。sort 會改動排序清單；用另一條清單存相同數值後排序，保留原順序。原始 int 與 Integer 這裡無可變物件欄位，不提前談深複製。

第20頁依資料需求比較三種介面：List保留順序與重複，可依索引讀取，因此指令1、1、2能保留兩次探索；Set只留下出現過的指令種類1、2，順序依實作而定，HashSet不保證執行順序；Map以指令代碼當鍵、動作名稱當值，例如1對應探索，適合依鍵查找，鍵不能重複而值可以重複。ArrayList、HashSet、HashMap是對應實作類別，本週只實作ArrayList，其他先辨識用途。

Collection是List、Set的共同上層介面；Map屬Collections Framework但不繼承Collection。Collections則是提供min、frequency、sort等靜態工具方法的類別。先分辨「保存資料的容器」與「處理容器的工具」，介面實作與多型留後續，本週不新增相關實作要求。

教師先把 teacher/*.java 分開編譯，再用 FixedScriptMission：

| 參數（energy samples commands） | 狀態/結果 | steps/ok/rejected/ignored | 最低能源 |
|---|---|---|---|
| 5 0 1 3 0 | 10/0/1 RETURNED | 3/1/1/0 | 5 |
| 2 4 2 | 0/5/1 SUCCESS | 1/1/0/0 | 0 |
| 0 0 3 1 | 0/0/0 EXHAUSTED | 0/0/0/2 | N/A |
| 20 5 1 | 20/5/0 SUCCESS | 0/0/0/1 | N/A |
| 20 0 | 20/0/0 UNFINISHED | 0/0/0/0 | N/A |

初始已停止不得先充電或記一筆；空清單呼叫 min 會失敗，需先判斷。工具輸入限定整數，檢查初始範圍；非整數與超出 int 範圍的參數不在契約。學生只需教師觀察紀錄，不要求另寫工具。

## F2 參考 Prompt 與判讀

第33頁先讓學生回看已完成的日誌，寫出欄位、筆數、順序、重複、操作、技術與驗證要求，再組成一段Prompt。目的是確認學生能以需求說明容器選擇；本段產出為講義F2的Prompt與判斷表。第34頁再問兩個建議能否滿足這些要求，保留學生先作答的時間。第35頁的程式反例沿用F3，不要求把AI建議接進正式程式後重新實作。

可供教師展示的需求範例：

> 請以 Java 保存火星探測車每一步資料：指令 int、執行後能源 int、結果 String。保留執行順序，允許重複；每讀一筆指令就新增，拒絕、無效與返回也保存，任務停止後不保存剩餘腳本。現階段由固定 int[] 腳本執行，日誌筆數隨執行增加，未來可能改回互動輸入。請用三條 ArrayList、一般 for、Collections.min/frequency，示範回放與0–3指令計數；不要自訂領域類別、Lambda、Stream、Iterator 或 JUnit。最低能源不含初始值，空日誌印 N/A。請用 {3,9,0} 與空腳本列出預期結果，並解釋為何需要索引檢查。

範例是教學需求，不宣稱已送給真實 AI。學生可寫不同措辭，但欄位、順序、重複、操作與驗證要明確。

int[100] 可以存有限數量資料，但要有上限、有效筆數與滿載處理；不是任何需求都不可用。固定 script 也可搭配等長陣列，只是本週要體驗逐筆增長的 ArrayList。Set 去重會丟失重複指令，且若用 HashSet 不保留執行順序，不能用來回放路線。ArrayList 不等於資源無限，只免除學生自行管理固定容量。

## F3 反例與最小修正

| 模擬錯誤 | 修改前 | 最小修正與修正後 |
|---|---|---|
| 刪索引範圍檢查；{3,9,0} | 回放後在 counts[9] 發生 ArrayIndexOutOfBoundsException | 恢復範圍檢查；counts=[1,0,0,1]、3/0/2/0、RETURNED |
| 回放改 <=；{0} | 先印一步，接著 get(1) 發生 IndexOutOfBoundsException | 改回 <；只印一步、1/0/0/0、RETURNED |

修正後另跑 {1,1,2} 與 {3,9,0}；不能只有錯誤訊息而缺正確版證據。操作在測試副本，正確正式版與預設腳本應恢復。服務不可用者使用模擬建議；紀錄不得假裝真實 AI 對話。

## G、H 解答與交接

length：陣列；size()：ArrayList；length()：String。三筆日誌不代表三次成功；三欄不齊可能回放錯配或越界。四種指令計數位置固定，ArrayList 適合逐步追加。第五週以 MissionStep 封裝同一步資料，由建構子保證一起建立；本週只提出問題。

保存沿用第一週 Desktop 流程：commit、push、核對 main 最新來源與紀錄；已有副本先保存並同步，還原後無副本 clone。新 clone 重新編譯執行證據由學生填寫，教師工具實測不能代替本人驗證。

## 備課依據

- 課綱第四週及第五週；前三週 Codex 版、week02_week03_跨週銜接設計.md。
- Claude 第四週日誌情境作為參考；本版保留具名狀態、固定腳本必做、命令列選讀，另明定初始停止與最低能源範圍。
- Deitel 11e EPUB §7.2–7.4、7.7–7.9、7.16；§16.2–16.5、16.6–16.7 選讀，§7.14 延伸。
- [JLS 陣列](https://docs.oracle.com/javase/specs/jls/se22/html/jls-10.html)：參考型態、索引與 length。
- [ArrayList API](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/ArrayList.html)：新增、取值、修改、刪除與筆數。
- [Collections API](https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/util/Collections.html)：min、frequency、sort。

2026-10-06 核對上述官方文件；文件版本是查證來源，不是限制學生 JDK。完整驗證見 verification.md。
