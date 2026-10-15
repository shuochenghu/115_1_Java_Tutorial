import java.util.Arrays;

// 固定的指令腳本：同一條路線可以重跑，逐步核對預測。
public class ArrayBasics {
    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        int[] counts = new int[4];
        String[] labels = new String[2];
        System.out.println("script=" + Arrays.toString(script));
        System.out.println("length=" + script.length);
        System.out.println("counts=" + Arrays.toString(counts));
        System.out.println("labels=" + Arrays.toString(labels));
        for (int i = 0; i < script.length; i++) {
            System.out.println("step " + (i + 1) + ": " + script[i]);
        }
        int total = 0;
        for (int command : script) {
            total += command;
        }
        System.out.println("sum=" + total);
        // 越界練習只在副本取消下一行註解；索引最後一格為 length - 1。
        // System.out.println(script[script.length]);
    }
}
