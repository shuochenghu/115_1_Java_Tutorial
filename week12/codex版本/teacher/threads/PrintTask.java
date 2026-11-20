// 類別：PrintTask（列印工作，教師解答）
// 意義：一項「模擬列印」工作，用 label 區分是哪一份講義。
// 作用：實作 JDK 內建的 Runnable 介面，run() 連續印三個步驟，並印出實際執行它的執行緒名稱。
//       每個工作只用自己的標籤與區域變數，不共用額度或業務計數，所以兩條執行緒不會互相干擾。
public class PrintTask implements Runnable {
    // label：工作標籤，例如「講義A」；建構後不再改變，所以宣告為 final。
    private final String label;

    // 建構子：記下這項工作的標籤。
    public PrintTask(String label) {
        this.label = label;
    }

    // run：工作內容。由 Thread 在新的執行緒中呼叫；step 是區域變數，每個執行緒各有一份。
    @Override
    public void run() {
        // BEGIN B1
        for (int step = 1; step <= 3; step++) {
            System.out.println("工作=" + label + " 執行緒="
                    + Thread.currentThread().getName() + " 步驟=" + step);
        }
        // END B1
    }
}
