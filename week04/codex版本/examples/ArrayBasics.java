import java.util.Arrays;

// 類別：ArrayBasics（第 4 週範例）
// 意義：示範如何用陣列保存一份固定的指令腳本。
// 作用：建立陣列、讀取長度、用索引與加強版 for 走訪；同一條路線可以重跑，逐步核對預測。
public class ArrayBasics {
    // main：程式進入點。依序印出陣列內容、長度、預設值，再用兩種 for 迴圈走訪腳本。
    public static void main(String[] args) {
        // 指令編號：0＝返回、1＝探索、2＝掃描、3＝充電；其他整數為無效指令。
        int[] script = {1, 1, 2}; // 探索 → 探索 → 掃描。
        int[] counts = new int[4]; // 索引 0–3 依序對應返回、探索、掃描、充電的次數。
        String[] labels = new String[2];
        System.out.println("指令腳本=" + Arrays.toString(script));
        System.out.println("長度=" + script.length);
        System.out.println("指令次數=" + Arrays.toString(counts));
        System.out.println("標籤=" + Arrays.toString(labels));
        for (int i = 0; i < script.length; i++) {
            System.out.println("步驟 " + (i + 1) + ": " + script[i]);
        }
        int total = 0;
        for (int command : script) {
            total += command;
        }
        System.out.println("加總=" + total);
        // 越界練習只在副本取消下一行註解；索引最後一格為 length - 1。
        // System.out.println(script[script.length]);
    }
}
