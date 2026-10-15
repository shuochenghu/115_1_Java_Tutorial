import java.util.Arrays;

// 範例 3（claude 版本）：main 的 String[] args 終於登場。
// 執行：java -cp out ScriptArgs 1 1 2
public class ScriptArgs {
    public static void main(String[] args) {
        // C# 的 Main(string[] args) 同樣不含程式名稱；args[0] 就是第一個參數。
        System.out.println("args.length = " + args.length);
        System.out.println("args = " + Arrays.toString(args));

        if (args.length == 0) {
            System.out.println("no arguments; try: java -cp out ScriptArgs 1 1 2");
            return;
        }

        // args 裡都是字串，"1" + "1" 是字串串接，不是加法。
        System.out.println("args[0] + args[0] = " + (args[0] + args[0]));

        // 轉成 int 才能計算。C#：int.Parse(...)；Java：Integer.parseInt(...)
        int[] script = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            script[i] = Integer.parseInt(args[i]);
        }
        int total = 0;
        for (int command : script) {
            total += command;
        }
        System.out.println("script = " + Arrays.toString(script) + ", sum = " + total);
        // 輸入 "abc" 會得到 NumberFormatException；本週約定只輸入整數，例外處理在第十二週。
    }
}
