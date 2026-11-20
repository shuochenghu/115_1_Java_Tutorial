// 類別：FinallyTrace（finally 收尾順序，延伸閱讀短例）
// 意義：在 try/catch 後面加上 finally，觀察成功與失敗兩條路徑都會經過的收尾區塊。
// 作用：只追蹤印出的先後順序。finally 一定會執行，但它不代表這筆已經受理成功。
// 用法：java FinallyTrace 3（成功路徑）、java FinallyTrace abc（失敗路徑）。
public class FinallyTrace {
    // main：程式進入點。args[0] 是要轉換的文字，沒有給時用 "abc" 走失敗路徑。
    public static void main(String[] args) {
        String raw = args.length > 0 ? args[0] : "abc";
        try {
            System.out.println("轉換成功 頁數=" + Integer.parseInt(raw));
        } catch (NumberFormatException e) {
            System.out.println("轉換失敗");
        } finally {
            // 無論 try 正常結束或進入 catch，離開前都會執行這裡，適合放「收尾」工作。
            System.out.println("本筆轉換流程收尾");
        }
        System.out.println("繼續之後的流程");
    }
}
