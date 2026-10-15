import java.util.Arrays;

// 固定四個計數位置：counts[k] 表示指令 k 出現幾次，不是成功行動次數。
public class CommandCounter {
    public static void main(String[] args) {
        int[] script = {3, 9, 0, 1, 1, -1};
        int[] counts = new int[4];
        for (int command : script) {
            // 輸入可能無效；先判斷範圍，再把指令當索引。
            if (command >= 0 && command < counts.length) {
                counts[command]++;
            }
        }
        System.out.println("counts=" + Arrays.toString(counts));
    }
}
