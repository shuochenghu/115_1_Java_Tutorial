// 類別：PrintQuotaDemo（課堂批次入口，學生起始碼）
// 意義：用固定六筆申請資料，模擬一個人連續送出列印申請。
// 作用：逐筆把文字轉成頁數、交給 PrintQuota.reserve，並依結果印出受理或拒絕。
//       某一筆失敗時要在迴圈內接住，下一筆才能繼續；A2 完成後再寫 A3。
public class PrintQuotaDemo {
    // main：程式進入點。資料固定，不讀命令列參數，方便和講義的預期輸出逐行比對。
    public static void main(String[] args) {
        PrintQuota quota = new PrintQuota();
        String[] inputs = {"3", "abc", "0", "5", "4", "2"};
        // BEGIN A3
        // TODO A3：移除這一段未完成提示，寫迴圈、try 與兩個 catch。
        // 每筆只印一行結果，成功訊息只能放在 reserve 正常返回之後。
        // 迴圈讀完後再印「批次結束 剩餘=...」。
        System.out.println("尚未完成：請先完成 A2，再接上 A3 的逐筆處理。");
        // END A3
    }
}
