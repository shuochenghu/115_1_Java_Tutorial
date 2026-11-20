// 類別：PrintTask（列印工作，學生起始碼）
// 意義：一項「模擬列印」工作，用 label 區分是哪一份講義。
// 作用：實作 JDK 內建的 Runnable 介面，把要做的事寫在 run()。
//       Runnable 只描述「做什麼」；交給哪個 Thread、在哪個執行緒執行，由 ThreadStartDemo 決定。
// 本週新觀念：Runnable 是 JDK 提供的介面，只要求一個沒有參數、沒有回傳值的 run() 方法。
public class PrintTask implements Runnable {
    // label：工作標籤，例如「講義A」；建構後不再改變，所以宣告為 final。
    private final String label;

    // 建構子：記下這項工作的標籤。
    public PrintTask(String label) {
        this.label = label;
    }

    // run：工作內容。由 Thread 在新的執行緒中呼叫，不要在 main 裡直接呼叫它（對照見講義 B2）。
    @Override
    public void run() {
        // BEGIN B1
        // TODO B1：自行寫三步迴圈，每步印工作、實際執行緒名稱、步驟。
        // 工作標籤取 label；實際名稱取 Thread.currentThread().getName()。
        // END B1
    }
}
