# 第 12 週教師筆記｜例外處理與執行緒（Codex 版本）

以本週 README 與規劃為準，完全獨立於第 11 週。教學節奏為熟悉情境→短例預測→學生寫規則→逐筆驗證→AI 反例→有限工作。不要一開始投影教師完整程式讓學生照抄；完整解答在 teacher，投影片附錄供備查。

## 三節節奏與學生負擔

| 節次 | 配置 | 巡視重點 |
|---|---|---|
| 1 | 10 分診斷、15 分 ParseDemo、15 分 throw 與責任、10 分 A2 | 字串與數字、跳過敘述、先驗證後改欄位 |
| 2 | 10 分契約／catch 順序、20 分 A3/A4、15 分 AI、5 分自選 | catch 在迴圈內；同一六筆重測，不改數據湊答案 |
| 3 | 10 分 Runnable／Thread、15 分 B1/B2、10 分結果／join、15 分保存 | 兩個 start 先於 join，名稱由 currentThread 取得 |

時間是備課估計，未做學生試教。若落後，省略 finally 附錄與額外教師測例；保留學生核心規則、AI 原案例重測與最後保存。不要增加同步、Lambda 或完整互動輸入。

## A1 答案：從失敗位置讀流程

`int copies=3` 可參與算術；`String raw="3"` 是文字，要明確轉換。main 是本例的流程入口，對照 C# 事件方法即可，不假定學生已熟悉例外。

| 模式 | 標準輸出依序 | 解釋 |
|---|---|---|
| caught 3 | 轉換前／轉換成功 頁數=3／轉換流程之後 | 沒例外，不進 catch |
| caught abc | 轉換前／轉換失敗：請輸入整數／轉換流程之後 | 成功那行被跳過，接住後繼續 |
| uncaught abc | 只有轉換前 | NumberFormatException 在錯誤輸出；main 終止 |

先辨認類別、自己的檔名／行號，不要求背堆疊。例外傳出後不會回到原方法的失敗那行，也不會替學生重做轉換。

## A2 答案：把狀態留在合法範圍

對應 [教師額度類別](teacher/quota/PrintQuota.java) 的 A2 區塊。10 申請 3 → 7；10 申請 0 → 單筆範圍拒絕、10；2 申請 4 → 額度不足拒絕、2。檢查單筆範圍在前，全部通過才 remaining -= copies。

學生要自行寫條件、建立例外與 throw。private remaining 由 PrintQuota 管理，呼叫端只讀 getter；方法不印訊息也不自己 catch。即使回傳 void，也可能因例外未正常返回。

本例把額度不足視為不被方法接受的參數，採 IllegalArgumentException 簡化；可以討論在其他設計中用明確結果或不同例外，但不要求重構。可預期的條件仍用 if 判斷，不必靠陣列越界等偶然例外驗證輸入。

## A3、A4 答案：每筆接住，下一筆繼續

對應 [教師額度入口](teacher/quota/PrintQuotaDemo.java) 的 A3 區塊；完整七行見學生講義。請用驗證工具核對，不能只看最終 0。

| raw | 前額度 | 結果位置 | catch | 後額度 |
|---|---|---|---|---|
| 3 | 10 | reserve 正常返回 | 無 | 7 |
| abc | 7 | parseInt 拋出 | NumberFormatException | 7 |
| 0 | 7 | reserve 單筆範圍 | IllegalArgumentException | 7 |
| 5 | 7 | reserve 正常返回 | 無 | 2 |
| 4 | 2 | reserve 剩餘額度 | IllegalArgumentException | 2 |
| 2 | 2 | reserve 正常返回 | 無 | 0 |

前五筆之後都有下一筆；最後一筆之後讀完。try/catch 若包在整個 for 外，abc 之後通常直接離開整個批次，不能達到逐筆繼續的需求。

NumberFormatException 是 IllegalArgumentException 子類別。交換兩個 catch 時後一個被前一個涵蓋，編譯失敗，不是「先跑再決定」。工具會建立隔離副本實測，不將故意錯誤檔放在正式 examples。

自選範例 `{"", "0", " 2 "}`：依序格式拒絕剩 10、範圍拒絕剩 10、受理 2 剩 8。純空白 trim 後變空字串；2147483648 超出 int，即使都是數字符號仍不能轉換。raw 非 null 是本週輸入約定，不加 null 防護作業。數量 6 且只剩 0 時，仍優先顯示每筆限 1～5 頁。

常見錯誤與診斷：

| 現象 | 優先查看 |
|---|---|
| A2 完成仍印未完成 | A3 尚未替換提示，這是預期的階段狀態 |
| 例外後仍印成功 | 成功訊息是否放在 catch 後／finally |
| 第五筆後額度為負 | 扣除是否在不足判斷之前 |
| abc 與 0 無法區分 | 是否只 catch 一個過廣型別、順序或訊息錯誤 |
| 每筆都從 10 開始 | 是否在迴圈內重新 new 額度物件 |
| 第二個物件沒有 10 | remaining 是否誤設 static |

## AI 答案與紀錄判定

[模擬錯誤](ai/PrintQuota.java) 先檢查數量，再扣除，最後才檢查是否負數。原六筆的剩餘依序為 7、7、7、2、-2、-4；最後 -4。第五筆雖被 catch 接住，已改動的欄位不會復原。

最小修正：把不足條件改成 copies > remaining，整段判斷移到扣除前。保持其他規則、初始值與資料不變，再編譯、核對全部七行與 5→5→1。只有改 `<0` 為其他數字、catch 補回或換大額度都不接受。

紀錄要有本人預測、具體狀態、修改位置與同案例結果；模擬案例、虛構示例與真實 AI 分清。沒有真實 AI 帳號仍能完成全部必做。

## B1、B2 答案：工作與執行緒分開

對應 [PrintTask](teacher/threads/PrintTask.java) 的 B1、[ThreadStartDemo](teacher/threads/ThreadStartDemo.java) 的 B2。前者描述有限的 1～3 步，後者建立不同工作與不同 Thread，先 start 兩者，再 join 兩者。

Runnable 只有一個要實作的抽象方法 run；本週先使用具名類別。final label 表示建立後不能替換欄位值，學生不必自行設計 final。Thread.currentThread 是現在實際執行這行程式的執行緒，不是變數 a 所指物件的名字。

正確版 8 行，工作A／工作B 各有自己的 1、2、3，結束訊息在六行之後。A、B 之間排列不固定，不能要求交替或一定加速。改成直接 run 後六行都在 main；此時 join 對未啟動的執行緒不會建立或啟動它。

| 學生說法 | 判讀 |
|---|---|
| 這次 A 全印完才 B，所以不是兩個執行緒 | 不足以判斷；核對 currentThread 的名稱與 start 呼叫 |
| start A 後 join A，再 start B | 工作啟動被前一個等待延後，未照本實驗規格 |
| join 表示列印一定成功 | join 等的是執行緒終止，終止也可能來自未捕捉例外 |
| main 的 catch 能接住工作 run 裡的任何例外 | 不會；工作有自己的執行堆疊 |
| 多個工作共用 PrintQuota 也會安全 | 本例沒有同步保證，不作這種延伸宣稱 |

join 的 InterruptedException 是受檢例外，編譯器要求處理或宣告。支援 catch 恢復等待者的中斷旗標並 return，不印兩項工作皆已結束；它不取消 a、b，有限工作仍自然結束。此支援由教師提供，學生只須解釋誰等待誰。工具用受控的等待點中斷教師支援段，與工作自然排程分開驗證。

throw 是拋出例外物件；throws 寫在方法簽名表示可能往外傳，不是接住。reserve 使用非受檢例外，不必加 throws。finally 的短例只供選讀；不能在其中扣除、累計成功或 return，尤其 return 可能掩蓋原本結果。

## 作業答案與驗收

[作業解答](teacher/homework/PrintQuotaDemo.java) 與 teacher/quota/PrintQuota.java 搭配；只編譯這兩檔，不同時加入課堂同名入口。

processOne 成功分支回 true，兩個 catch 各回 false；main 每筆只呼叫一次並做一次計數。原六筆受理 3、拒絕 3、剩餘 0；5→5→1 受理 2、拒絕 1、剩餘 0；空批次 0／0／10。自選範例空字串、0、前後空白的 2，摘要 1／2／8。

評分依作業表。核心不是另造功能，而是把成功與處理過一筆分開，從回傳值形成摘要。學生自選資料要有預算與實際，空批次不能靠製造例外結束。

## 教師操作、來源與限制

工具及正式編譯指令見 [維護說明](tools/README.md)，實測見 [verification](verification.md)。兩組各兩檔以 VS Code 為主，不強迫兩套 IDE 重做。教師工具不列學生先備；不要求 JUnit、Maven 或 Gradle。

教科書 EPUB 已核對：第 11 章 §11.3～11.5、§11.7、§11.10；finally §11.6 選讀。並行參考第 23 章 §23.1、§23.2；§23.3 是 Executor，本週採傳統 Thread 的最小實驗，不冒稱書中原例，不猜紙本頁碼。

語言規則查核見規劃末節：[JLS 例外檢查](https://docs.oracle.com/javase/specs/jls/se25/html/jls-11.html#jls-11.2)、[try/catch](https://docs.oracle.com/javase/specs/jls/se25/html/jls-14.html#jls-14.20)、[NumberFormatException 繼承關係](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/NumberFormatException.html)、[Thread API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Thread.html)。文件版本不等於課堂 JDK 要求。課堂熟練度、GUI 與真實遠端操作依實際紀錄，不把作者 CLI 通過當成學生已學會。
