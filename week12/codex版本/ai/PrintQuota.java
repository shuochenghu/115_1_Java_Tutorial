// 類別：PrintQuota（模擬 AI 回覆的版本，教師設計）
// 意義：這不是真實 AI 對話，也不是正確答案；它模擬 AI 依需求寫出的額度類別。
// 作用：可以編譯，但不一定符合規格。請用課堂固定六筆資料執行，
//       觀察每一筆之後的剩餘額度，再自己找出問題所在並提出最小修正。
// 必須在 ai-check 隔離編譯，不能覆蓋正式 src 的人工初版。
public class PrintQuota {
    // remaining：目前剩餘頁數。
    private int remaining;

    // 建構子：新物件的額度從 10 頁開始。
    public PrintQuota() {
        remaining = 10;
    }

    // getRemaining：讀取目前剩餘頁數。
    public int getRemaining() {
        return remaining;
    }

    // reserve：申請 copies 頁；不合法時拋出 IllegalArgumentException。
    public void reserve(int copies) {
        if (copies < 1 || copies > 5) {
            throw new IllegalArgumentException("每筆限 1～5 頁");
        }
        remaining -= copies;
        if (remaining < 0) {
            throw new IllegalArgumentException("剩餘額度不足");
        }
    }
}
