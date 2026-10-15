import java.util.Arrays;

// 範例 2（claude 版本）：陣列是參考型態。延續第二週的箭頭圖：變數存的是「指向陣列物件的參考」。
public class ArrayReference {
    public static void main(String[] args) {
        int[] state = {20, 0, 0};

        // 1. 指定運算只複製參考：alias 與 state 指向同一個陣列。
        int[] alias = state;
        alias[0] = 14;
        System.out.println("after alias[0]=14: state = " + Arrays.toString(state));

        // 2. 傳進方法也是複製參考：方法改「元素」，呼叫端看得到。
        explore(state);
        System.out.println("after explore(state): state = " + Arrays.toString(state));

        // 3. 方法把參數「重新指定」到新陣列，只改到方法裡的區域變數，呼叫端不受影響。
        reset(state);
        System.out.println("after reset(state): state = " + Arrays.toString(state));

        // 4. 需要獨立副本時用 Arrays.copyOf。
        int[] backup = Arrays.copyOf(state, state.length);
        backup[0] = 0;
        System.out.println("backup = " + Arrays.toString(backup) + ", state = " + Arrays.toString(state));

        // 5. == 比較參考；Arrays.equals 比較內容（和第二週 String 的 == / equals 同一個道理）。
        int[] same = {8, 2, 1};
        System.out.println("state == same: " + (state == same));
        System.out.println("Arrays.equals(state, same): " + Arrays.equals(state, same));
    }

    static void explore(int[] s) {
        s[0] -= 6;
        s[1] += 2;
        s[2]++;
    }

    static void reset(int[] s) {
        s = new int[] {20, 0, 0};
        System.out.println("  inside reset: s = " + Arrays.toString(s));
    }
}
