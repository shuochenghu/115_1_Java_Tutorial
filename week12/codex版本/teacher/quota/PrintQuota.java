// 類別：PrintQuota（列印額度，教師解答）
// 意義：一個人的列印額度。每個新物件從 10 頁開始，欄位 private，只能透過 reserve 改動。
// 作用：驗證與狀態更新由額度物件負責：不合法就拋出 IllegalArgumentException、額度不變；
//       全部檢查通過才扣除。顯示訊息交給呼叫端，這個類別不印任何東西。
public class PrintQuota {
    // remaining：目前剩餘頁數。每個物件各有一份，彼此不共用。
    private int remaining;

    // 建構子：新物件的額度一律從 10 頁開始。
    public PrintQuota() {
        remaining = 10;
    }

    // getRemaining：讀取目前剩餘頁數，只讀不改。
    public int getRemaining() {
        return remaining;
    }

    // reserve：申請 copies 頁。正常返回代表已受理並扣除；拋出例外代表拒絕且額度不變。
    // 例外訊息（e.getMessage()）就是拒絕原因，呼叫端直接拿來顯示。
    public void reserve(int copies) {
        // BEGIN A2
        // 先決定能否受理，避免拋出例外時欄位已經被扣掉。
        // 檢查順序也決定同時違規時要回報哪個原因。
        if (copies < 1 || copies > 5) {
            throw new IllegalArgumentException("每筆限 1～5 頁");
        }
        if (copies > remaining) {
            throw new IllegalArgumentException("剩餘額度不足");
        }
        remaining -= copies;
        // END A2
    }
}
