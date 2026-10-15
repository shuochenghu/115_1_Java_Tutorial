import java.util.Arrays;

// 範例 1（claude 版本）：陣列宣告、預設值、length、兩種走訪方式。對照 C# 寫在註解。
public class ArrayBasics {
    public static void main(String[] args) {
        // C#：int[] counts = new int[4];  —— 寫法相同
        int[] counts = new int[4];
        System.out.println("default counts = " + Arrays.toString(counts));

        // 陣列初始化器：長度由大括號內的元素數決定。
        int[] script = {1, 1, 2};
        // C#：script.Length（屬性，大寫）；Java：script.length（欄位，小寫、沒有括號）
        System.out.println("script.length = " + script.length);

        // 其他型態的預設值：boolean 是 false，參考型態（含 String）是 null。
        boolean[] flags = new boolean[2];
        String[] names = new String[2];
        System.out.println("flags = " + Arrays.toString(flags) + ", names = " + Arrays.toString(names));

        // 直接印陣列只會看到「型態@雜湊碼」，不是內容；要用 Arrays.toString。
        System.out.println("println(script) = " + script);

        // 索引走訪：需要索引（例如第幾步）時使用。
        for (int i = 0; i < script.length; i++) {
            System.out.println("step " + (i + 1) + " -> command " + script[i]);
        }

        // 加強版 for：C# 的 foreach (int c in script)；只讀值、不需要索引時使用。
        for (int command : script) {
            counts[command]++;
        }
        System.out.println("counts after = " + Arrays.toString(counts));

        // 想看越界錯誤：把下一行的註解拿掉再執行。
        // C# 會丟 IndexOutOfRangeException，Java 是 ArrayIndexOutOfBoundsException。
        // System.out.println(script[script.length]);
    }
}
