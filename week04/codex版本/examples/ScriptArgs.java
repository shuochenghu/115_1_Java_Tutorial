import java.util.Arrays;

// 選讀：命令列參數是字串，整數解析不是本週必做。
public class ScriptArgs {
    public static void main(String[] args) {
        int[] script = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            script[i] = Integer.parseInt(args[i]);
        }
        System.out.println("script=" + Arrays.toString(script));
        // 本例約定輸入 Java int 範圍內整數；非整數與超出範圍的參數留到例外處理。
    }
}
