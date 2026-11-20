// 類別：ParseDemo（例外流程對照，獨立短例）
// 意義：把同一段「字串轉整數」寫成兩種版本：有 try/catch 接住，以及完全不接住。
// 作用：用命令列參數選擇版本與輸入，比較轉換失敗時程式是繼續往下，還是整個中止。
// 用法：java ParseDemo caught abc、java ParseDemo uncaught abc（第二個參數可改成 3 等正常整數）。
public class ParseDemo {
    // main：程式進入點。args[0] 選版本（預設 caught），args[1] 是要轉換的文字（預設 "abc"）。
    public static void main(String[] args) {
        String mode = args.length > 0 ? args[0] : "caught";
        String raw = args.length > 1 ? args[1] : "abc";
        System.out.println("轉換前");
        if ("uncaught".equals(mode)) {
            // 不接住的版本：parseInt 遇到 "abc" 會拋出 NumberFormatException，
            // 沒有人處理，例外一路傳出 main，下面兩行和「轉換流程之後」都不會執行。
            int copies = Integer.parseInt(raw.trim());
            System.out.println("轉換成功 頁數=" + copies);
        } else {
            // 接住的版本：try 區塊中一旦拋出例外，就跳過 try 剩下的程式，
            // 直接進入型別相符的 catch，處理完後從整個 try/catch 之後繼續。
            try {
                int copies = Integer.parseInt(raw.trim());
                System.out.println("轉換成功 頁數=" + copies);
            } catch (NumberFormatException e) {
                System.out.println("轉換失敗：請輸入整數");
            }
        }
        // 有接住例外，或本來就沒例外，才會到這裡。
        System.out.println("轉換流程之後");
    }
}
