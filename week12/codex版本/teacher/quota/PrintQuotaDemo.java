// 類別：PrintQuotaDemo（課堂批次入口，教師解答）
// 意義：用固定六筆申請資料，模擬一個人連續送出列印申請。
// 作用：逐筆轉換並呼叫 reserve；每一筆自己接住可預期的失敗，下一筆才有機會繼續。
//       try 放在迴圈「裡面」：若包住整個迴圈，第一次失敗就會跳出迴圈，後面幾筆都不會處理。
public class PrintQuotaDemo {
    // main：程式進入點。資料固定，不讀命令列參數，方便和講義的預期輸出逐行比對。
    public static void main(String[] args) {
        PrintQuota quota = new PrintQuota();
        String[] inputs = {"3", "abc", "0", "5", "4", "2"};
        // BEGIN A3
        for (String raw : inputs) {
            try {
                int copies = Integer.parseInt(raw.trim());
                quota.reserve(copies);
                // 只有方法正常返回，才能說這筆已受理。
                System.out.println("輸入=[" + raw + "] 受理=" + copies
                        + " 頁 剩餘=" + quota.getRemaining());
            } catch (NumberFormatException e) {
                // 子類別放前面，格式錯誤才有自己的處理方式。
                System.out.println("輸入=[" + raw + "] 拒絕：請輸入整數 剩餘="
                        + quota.getRemaining());
            } catch (IllegalArgumentException e) {
                System.out.println("輸入=[" + raw + "] 拒絕：" + e.getMessage()
                        + " 剩餘=" + quota.getRemaining());
            }
        }
        System.out.println("批次結束 剩餘=" + quota.getRemaining());
        // END A3
    }
}
