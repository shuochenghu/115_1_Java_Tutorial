// 類別：PrintQuota（列印額度，學生起始碼）
// 意義：一個人的列印額度。每個新物件從 10 頁開始，欄位 private，只能透過 reserve 改動。
// 作用：reserve 先檢查申請是否合法，不合法就拋出例外、額度不變；全部檢查通過才扣除。
//       顯示訊息與接住例外是呼叫端（PrintQuotaDemo）的工作，這個類別不印任何東西。
// 本週新觀念：用 throw 回報「不能受理」，取代前幾週回傳 false 的做法。
// A2 未完成前，reserve 什麼都不做；此時不要接上 A3 的正式批次。
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

    // reserve：申請 copies 頁。目前是空方法，只是為了能編譯；規則見講義 A2。
    public void reserve(int copies) {
        // BEGIN A2
        // TODO A2：先驗證單筆 1～5 頁，再驗證額度足夠。
        // 失敗時依講義拋出指定例外；全部通過後才扣除。
        // 此空方法只是能編譯的起點，不代表已經受理成功。
        // END A2
    }
}
