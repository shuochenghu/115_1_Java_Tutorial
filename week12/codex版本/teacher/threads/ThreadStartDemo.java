// 類別：ThreadStartDemo（執行緒啟動入口，教師解答）
// 意義：把兩項 PrintTask 各交給一個有名字的 Thread，讓兩項工作同時進行。
// 作用：先啟動兩者，再等兩者終止。start() 開新執行緒；join() 讓 main 等待指定執行緒結束。
//       join 正常返回只代表該執行緒已結束，本身不是業務成功的回報。
//       兩項工作的輸出可能交錯，但每項工作自己的步驟 1→2→3 不會亂序。
public class ThreadStartDemo {
    // main：程式進入點，也是 main 執行緒。join() 宣告會拋出 InterruptedException（受檢例外），
    // 所以呼叫它的地方一定要 try/catch，否則編譯不會通過。
    public static void main(String[] args) {
        // BEGIN B2
        Thread a = new Thread(new PrintTask("講義A"), "工作A");
        Thread b = new Thread(new PrintTask("講義B"), "工作B");
        System.out.println("開始模擬列印");
        a.start();
        b.start();
        try {
            // 必須先讓兩者啟動，才在這裡等待。
            a.join();
            b.join();
        } catch (InterruptedException e) {
            // 恢復的是目前等待者（main）的旗標，不等於取消 a、b。
            Thread.currentThread().interrupt();
            System.out.println("等待被中斷，本次不宣告兩項工作皆已結束");
            return;
        }
        System.out.println("兩項工作皆已結束");
        // END B2
    }
}
