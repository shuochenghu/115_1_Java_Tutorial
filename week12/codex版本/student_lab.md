# 第 12 週學生實作｜例外處理與執行緒

依 [README](README.md) 準備，另存成當日 `LAB_REPORT.md`。本週從新檔案開始，不需要第 11 週成果。先寫預測再執行；未做的證據留「待補」，不要把教師答案當成本人實測。

姓名／學號：待填。實際日期：待填。java／javac 版本：待填。

## A1｜輸入 abc 之後，程式去哪裡？（第一節前 25 分鐘）

情境：在 C# WinForms 文字方塊輸入 abc，不能直接當成頁數。Java 的 main 也需要決定轉換失敗後怎麼辦。請先回答：

1. `int copies = 3;` 與 `String raw = "3";` 的值如何使用？答：待填。
2. 方法沒有正常走到最後，呼叫端還能直接說成功嗎？預測：待填。
3. 哪個欄位應該 private？誰可以合法改動剩餘額度？答：待填。

照 [短例操作](examples/README.md) 編譯 ParseDemo。先看 `Integer.parseInt(raw.trim())`：trim 去前後空白，parseInt 才轉 int。try 包住可能失敗的操作，catch 在相符例外發生後接手；不會回到失敗那行重做。

| 參數 | 成功那行會印嗎？ | 最後一行會印嗎？ | 實際／理由 |
|---|---|---|---|
| caught 3 | 待填 | 待填 | 待填 |
| caught abc | 待填 | 待填 | 待填 |
| uncaught abc | 待填 | 待填 | 待填 |

錯誤訊息很多行時，先找例外類別、第一個自己的檔名／行號，再回到原始碼；不需要背整段堆疊。記一個出錯位置與被跳過的敘述：待填。

## A2｜先檢查，通過才扣除（第一節後 25 分鐘）

看 [責任圖](diagrams/roles.svg) 與 [例外流程](diagrams/exception_flow.svg)。PrintQuota 保管數字；main 處理文字與顯示。每個新 PrintQuota 初始 10 頁，本週所有傳入的 raw 都非 null。

| 方法 | 作用 |
|---|---|
| `new PrintQuota()` | 建立自己的 10 頁額度，已提供 |
| `getRemaining()` | 只讀目前剩餘值，已提供 |
| `reserve(int copies)` | 驗證後扣除；失敗拋出例外，由你完成 |

修改自己的 `src/PrintQuota.java` 的 **TODO A2**：

1. 單筆小於 1 或大於 5 時，拋出 `IllegalArgumentException`，訊息固定「每筆限 1～5 頁」。
2. 單筆符合範圍，但超過目前 remaining 時，拋出同類例外，訊息「剩餘額度不足」。
3. 全部檢查通過後，才扣除 copies。不要在此印成功或錯誤訊息，不要在此 catch 自己的例外。

教師短示範 `throw new IllegalArgumentException("原因")`：new 建立例外物件，throw 使這次方法呼叫以例外離開。`void` 方法也可能因例外無法正常返回；不會因為回傳型態是 void 就代表成功。

先寫三條預測：10 頁申請 3 頁 → 待填；10 頁申請 0 頁 → 待填；剩 2 頁申請 4 頁 → 待填。

編譯兩個 src 檔確認語法。此時入口仍印「尚未完成」，是因為 A3 還沒接上，不要只刪提示就宣稱功能完成。接著才做 A3 的呼叫端。

## A3｜一筆失敗之後繼續下一筆（第二節前 30 分鐘）

在 `src/PrintQuotaDemo.java` 的 **TODO A3** 取代未完成提示。固定陣列與物件建立已提供；自行寫以下流程：

1. 用普通迴圈逐筆取出 raw；try 和兩個 catch 放在迴圈內。
2. try 內先以 `Integer.parseInt(raw.trim())` 轉換，再呼叫 reserve。正常返回後才印受理結果。
3. `NumberFormatException` 顯示「請輸入整數」；`IllegalArgumentException` 用 `e.getMessage()` 取得 A2 指定的原因。每筆各印一次目前額度。
4. 全部讀完才印批次結束。額度變成 0 也不提早 break；後續申請仍需有拒絕結果。

`NumberFormatException` 繼承 `IllegalArgumentException`。預測：如果先 catch 父類別，後面的子類別 catch 還有機會執行嗎？會在編譯或執行時發現？待填。看教師的隔離副本編譯診斷後，補上實際結果：待填。

輸出格式：`輸入=[原文字] 受理=數量 頁 剩餘=額度` 或 `輸入=[原文字] 拒絕：原因 剩餘=額度`。

在自己的 `java-12` 編譯成功後才執行：

```powershell
javac -encoding UTF-8 -d out-quota src/PrintQuota.java src/PrintQuotaDemo.java
java -cp out-quota PrintQuotaDemo
```

完成後應有完整七行：

```text
輸入=[3] 受理=3 頁 剩餘=7
輸入=[abc] 拒絕：請輸入整數 剩餘=7
輸入=[0] 拒絕：每筆限 1～5 頁 剩餘=7
輸入=[5] 受理=5 頁 剩餘=2
輸入=[4] 拒絕：剩餘額度不足 剩餘=2
輸入=[2] 受理=2 頁 剩餘=0
批次結束 剩餘=0
```

不是七行就先找第一個差異：只處理到 abc，常是 catch 包住整個迴圈；abc 被說成範圍錯誤，先看 catch 型別與順序；拒絕後額度變負數，先看扣除的位置。不要改初始值湊結果。

## A4｜追蹤與自選邊界（穿插第二節）

| raw | 執行前額度 | 失敗位置／沒有失敗 | 哪個 catch／無 | 執行後額度 | 下一筆有處理嗎？ |
|---|---|---|---|---|---|
| 3 | 待填 | 待填 | 待填 | 待填 | 待填 |
| abc | 待填 | 待填 | 待填 | 待填 | 待填 |
| 0 | 待填 | 待填 | 待填 | 待填 | 待填 |
| 5 | 待填 | 待填 | 待填 | 待填 | 待填 |
| 4 | 待填 | 待填 | 待填 | 待填 | 待填 |
| 2 | 待填 | 待填 | 待填 | 待填 | 已讀完 |

另選一批資料，至少有一筆邊界或格式錯誤，及其後一筆合法資料。可從 `""`、`"   "`、`" 2 "`、`"-1"`、`"6"`、`"2147483648"` 選擇；空字串與純空白不能變整數，前後空白先 trim，超出 int 範圍也算格式錯誤。測完恢復固定六筆，以免 AI 比較用到不同資料。

我的資料：待填。預期逐筆結果：待填。完整實測：待填。第一個差異與修正：待填。

## AI｜拒絕了，額度有保留嗎？（第二節 15 分鐘）

先保存人工完成版，寫三至五句需求與預測：待填。需求要包含單筆範圍、失敗不得扣額度、下一筆繼續與輸出證據。

把教材 `ai/PrintQuota.java` 複製到自己的 `java-12/ai-check/`，再把自己完成的 PrintQuotaDemo.java 複製進去。這是**教師設計的模擬 AI 錯誤**，不要覆蓋 src。

```powershell
javac -encoding UTF-8 -d out-ai ai-check/PrintQuota.java ai-check/PrintQuotaDemo.java
java -cp out-ai PrintQuotaDemo
```

先預測剩 2 頁、申請 4 頁的結果，再執行。對照第五、六筆與最終額度；模擬版會出現 -2、最終 -4。指出哪一行改了欄位、例外為何沒有自動復原，然後在 ai-check 作最小修正並以原六筆重跑。

不能只在 catch 加「已拒絕」，也不能改初始額度或猜測補回數字。修好後重新編譯，核對全部七行，再測 5→5→1。將預測、錯誤證據、修改理由與重測填到 AI_USAGE.md。真實 AI 對話另記工具；沒用真實 AI 也要記這次驗證。

## B1｜工作內容與執行工作的人（第三節前 25 分鐘）

另開 threads，這個實驗不使用額度物件。`Runnable` 代表「有一個 run 方法的工作約定」；Thread 是執行工作的一條執行緒。`implements Runnable` 讓 PrintTask 依約提供 `public void run()`。本週不寫 Lambda。

在 `threads/PrintTask.java` 完成 **TODO B1**：用 1 到 3 的迴圈，每步印一行。

```text
工作=講義A 執行緒=工作A 步驟=1
```

工作標籤由 label 取得，步驟由迴圈變數取得；實際執行緒名稱由 `Thread.currentThread().getName()` 取得。不要把名稱寫死成工作A，否則看不出 run 與 start 的差異。final label 表示建立後不再替換標籤，與迴圈變數沒有關係。

## B2｜先啟動兩者，再等待（第三節前 35 分鐘內完成）

看 [時序圖](diagrams/thread_flow.svg)。在 ThreadStartDemo 的 **TODO B2** 取代提示：

1. 兩個工作分別標籤「講義A」「講義B」，各自交給不同 Thread；執行緒名稱「工作A」「工作B」。可用 `new Thread(工作物件, "執行緒名稱")`。
2. 印「開始模擬列印」，先啟動 a、b，再等待。
3. 加入下列教師支援段，只在 TODO 位置自行寫兩次 join。main 等待的是被指定的工作執行緒，不是工作彼此等待。

```java
try {
    // 在此依序等 a、b 終止：自行加入兩次 join 呼叫。
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    System.out.println("等待被中斷，本次不宣告兩項工作皆已結束");
    return;
}
System.out.println("兩項工作皆已結束");
```

join 宣告可能拋出受檢例外 InterruptedException，所以這裡要接住；若直接省略處理，會在編譯時被要求處理或宣告。throw 是拋出，throws 是在方法簽名宣告可能傳出，不是接住。這段 catch 恢復 main 的中斷旗標後返回，不代表取消兩個工作。

```powershell
javac -encoding UTF-8 -d out-threads threads/PrintTask.java threads/ThreadStartDemo.java
java -cp out-threads ThreadStartDemo
```

驗收：共 8 行，開始 1 行、每份工作 3 行、結束 1 行。各工作自己的步驟為 1→2→3；跨工作不要求 A 先，也不要求交替。即使 A 三行都在 B 前面，也不能只據此說沒有建立另一執行緒。

把兩檔另複製到 `experiments/direct-run/`，只把兩次 start 改成 run；另編譯到 out-direct，不與正式 threads 混用。比較六行中的實際執行緒名稱，之後保留正式版的 start。

| 問題 | 預測 | 實測／解釋 |
|---|---|---|
| start 版兩份工作在哪個執行緒？ | 待填 | 待填 |
| 直接 run 版兩份工作在哪個執行緒？ | 待填 | 待填 |
| A 一定比 B 先印嗎？ | 待填 | 待填 |
| 最後結束訊息為何在六行之後？ | 待填 | 待填 |

join 只等待執行緒終止，不會自動把工作裡的例外傳回 main，也不證明一般業務工作一定成功。本例不共用額度，沒有教會多執行緒安全扣款。

## 保存與離堂檢查（最後 15 分鐘）

依 README 用 GitHub Desktop 檢查、commit、push、遠端核對，再於原 repo 外取回並重新編譯 src、threads。只完成本機步驟或網路中斷就如實填待補。

| 證據 | 本人結果／位置 |
|---|---|
| 額度七行、自選案例、AI 前後結果 | 待填 |
| start 與 run 的六行工作輸出 | 待填 |
| commit 說明與遠端當日路徑核對 | 待填 |
| 取回後兩個入口重編譯 | 待填 |

用一句話回答「catch 接住失敗」與「物件狀態正確」為何要分別檢查：待填。課後依 [作業說明](assignment.md) 完成單筆方法與批次摘要。
