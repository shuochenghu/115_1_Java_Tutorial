// 類別：PrintQuotaDemo（作業批次入口，教師解答）
// 意義：課堂批次的延伸：同樣六筆資料，最後多印受理筆數與拒絕筆數。
// 作用：把單筆處理抽成 processOne，回傳 true／false 讓 main 計數。
// 編譯方式：與 teacher/quota/PrintQuota.java 搭配；不與課堂同名入口一起編譯。
public class PrintQuotaDemo {
    // main：程式進入點。逐筆呼叫 processOne，依回傳值累加受理與拒絕，最後印出摘要。
    public static void main(String[] args) {
        PrintQuota quota = new PrintQuota();
        String[] inputs = {"3", "abc", "0", "5", "4", "2"};
        int accepted = 0;
        int rejected = 0;
        for (String raw : inputs) {
            if (processOne(quota, raw)) {
                accepted++;
            } else {
                rejected++;
            }
        }
        System.out.println("批次結束 受理=" + accepted + " 拒絕=" + rejected
                + " 剩餘=" + quota.getRemaining());
    }

    // processOne：處理單筆申請並印出一行結果；受理回傳 true，拒絕回傳 false。
    // 例外在這個方法裡就接住，不會傳回 main；呼叫端只依結果計數，不能再重做一次 reserve。
    public static boolean processOne(PrintQuota quota, String raw) {
        try {
            int copies = Integer.parseInt(raw.trim());
            quota.reserve(copies);
            System.out.println("輸入=[" + raw + "] 受理=" + copies
                    + " 頁 剩餘=" + quota.getRemaining());
            return true;
        } catch (NumberFormatException e) {
            System.out.println("輸入=[" + raw + "] 拒絕：請輸入整數 剩餘="
                    + quota.getRemaining());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("輸入=[" + raw + "] 拒絕：" + e.getMessage()
                    + " 剩餘=" + quota.getRemaining());
            return false;
        }
    }
}
