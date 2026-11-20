# 第 10 週教師筆記｜過載與多型

## 第 1 節｜從呼叫預測到覆寫

10 分鐘診斷兩車，15 分鐘會員短例及回傳型態預測，15 分鐘教師用完成版示範報表，10 分鐘回收第 8 週的 `@Override` 及覆寫。教師示範只顯示呼叫處與輸出，學生實作前把完成版方法本體留在教師材料。

- 一般車 `6/5/3`、讀 3 步，重型車 `8/6/2`、讀 2 步。車輛狀態依序為能源、樣本與成功行動數。共同探索流程成功扣 6 格後才呼叫 `exploreYield()`，由實際車種決定回 2 或 3。
- 會員短例三行為「VIP 會員」「VIP 會員 點數=30」「VIP 會員 活動=迎新」。`member.describe()` 編譯時依 `Member` 找到零參數簽名，執行時執行 VIP 的覆寫。`vip.describe(30)` 與 `vip.describe("迎新")` 分別選 `int` 與 `String` 版本。
- 第 8 週的 `describe(int)` 對照 `Member.describe()`。父類別沒有對應參數的簽名，所以保留 `@Override` 會被編譯器拒絕。移除標註後可以宣告這個過載。
- 在同一個類別再加 `int describe(int points)`，仍與 `String describe(int points)` 重複。回傳型態不構成不同的方法簽名。學生先答，再看隔離編譯的第一個相關錯誤。
- 同一重型車用 `Rover` 引數時，兩個參考型態版本適用，選較具體的 `Rover`。用 `SampleSource` 引數時，只適用共用版本。車種不會在執行期重新替 `static print` 選簽名。

## 第 2 節｜TODO 1–4 與回歸

10 分鐘閱讀規格，25 分鐘完成 B，15 分鐘完成 C。正式解答見 [SampleReport.java](teacher/SampleReport.java)、[SampleReportDemo.java](teacher/SampleReportDemo.java) 和 [SampleOverloadDemo.java](teacher/SampleOverloadDemo.java)。

| 檢查點 | 解答與診斷 |
|---|---|
| TODO 1 | 用 `getSamples()`、`getEnergy()` 印詳報。舊入口第一行從「重型探測車 樣本數=6」變為「重型探測車 樣本數=6 能源=8」，兩個盒子的行完全相同 |
| TODO 2 | 加 `SampleSource source = heavy;`，舊入口的車輛呼叫改用 `source`。重跑三行，第一行恢復共用格式 |
| TODO 3 | `int` 版印共用格式，介面版以 `print(label, source.getSamples())` 轉交。若在 `Rover` 版寫 `print(label, rover)`，呼叫會再次選到同一個 `Rover` 簽名，最後出現 `StackOverflowError`。先指出重複呼叫的位置。詳報仍須包含能源 |
| TODO 4 | 兩個參考共用同一台已執行一次任務的重型車。核對講義 C 的六行，注意兩個 0 分別經介面與整數過載。再以非 4 的樣本盒數量抓寫死資料 |

學生如果在 TODO 2 想移除詳報版本，引導回到「同一台車可從共用報表與維運詳報兩個入口呈現」的需求。若重跑出現舊輸出，先確認這次編譯成功、執行目錄是當前的 `out`。

## 第 3 節｜AI 除錯、同儕核對、保存

5 分鐘寫需求與預測，20 分鐘重現和重測，10 分鐘同儕說明，15 分鐘保存與取回。

模擬程式第二行錯誤地成為「重型探測車 樣本數=6 能源=8」，第一行及後四行仍正確。呼叫端先以 `SampleSource` 選定共用簽名。該方法的分支再以 `(Rover) source` 傳入另一個簽名。最小修正是刪除這段 `if`（含 `return`），保留用數量轉交的那一行。班上只讀教師提供的傳統 `instanceof` 與顯式轉型片段。若時間不足，保留同入口重測及保存成果。

D5 討論：不要讓學生把錯誤歸因成「過載在編譯時選，AI 卻在執行時再選」。`instanceof` 加轉型合法，依實際物件決定行為也是合理設計；判斷對錯要回到需求。本週需求是同一台車可經兩個入口呈現兩種格式：呼叫端傳 `SampleSource` 參考時要得到共用一行，舊入口 `SampleReportDemo` 與作業的快速查詢都依賴這點。AI 版讓呼叫端拿不到車的共用一行，所以拒絕。若需求改為「是車就印能源」，AI 的輸出就正確；但較好的設計是讓物件提供報表內容（例如在 `SampleSource` 加報表方法，由 `Rover` 覆寫多印能源），共用報表不必認得 `Rover`、也不需轉型，新增來源時只改新類別。這是延伸討論，本週不要求實作。

## 課後作業

教師參考在 [homework/SampleReport.java](teacher/homework/SampleReport.java) 與 [QuickSampleDemo.java](teacher/homework/QuickSampleDemo.java)。預設標籤範例為「快速查詢」。重型車印 6 份，空盒印 0 份。當 `print(rover)` 只有一個引數時，選單參數簽名。方法本體中的 `source` 宣告為 `SampleSource`，以兩個引數轉交時選共用格式。自行選的有效標籤也可通過驗收。

教師判讀學生證據時，要求他們能指到呼叫行和方法本體各一處，並說明修正後用哪一個案例重跑。短例限定固定個數和完全相符的基本型態。基本型態擴大轉換留待補充。
