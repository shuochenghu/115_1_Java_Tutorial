// 類別：ThreadStartDemo（執行緒啟動入口，學生起始碼）
// 意義：把兩項 PrintTask 各交給一個 Thread，讓兩項工作同時進行。
// 作用：start() 會開一條新的執行緒去呼叫 run()；join() 讓 main 停下來，等指定的執行緒結束。
//       兩項工作的輸出可能交錯，先後順序每次執行都可能不同，這是正常現象。
public class ThreadStartDemo {
    // main：程式進入點，也是 main 執行緒。B1 完成後，再於 B2 建立並啟動兩個工作。
    public static void main(String[] args) {
        // BEGIN B2
        // TODO B2：建立兩個不同的 PrintTask 與 Thread，名稱依講義指定。
        // 先印開始、啟動兩者，再使用講義提供的等待支援段。
        System.out.println("尚未完成：請完成 B1、B2，再啟動工作實驗。");
        // END B2
    }
}
