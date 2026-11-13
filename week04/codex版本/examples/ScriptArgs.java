import java.util.Arrays;

// 類別：ScriptArgs（第 4 週選讀範例）
// 意義：示範把命令列參數（字串）轉成整數腳本。
// 作用：用 Integer.parseInt 把每個參數轉成 int 存進陣列後印出；整數解析不是本週必做。
public class ScriptArgs {
    // main：程式進入點。args 是執行時在類別名稱後面輸入的參數，例如 java ScriptArgs 3 9 0。
    public static void main(String[] args) {
        // 指令編號：0＝返回、1＝探索、2＝掃描、3＝充電；其他整數為無效指令。
        // 例如參數 3 9 0 代表充電、無效指令、返回；本例只解析與列印，不執行動作。
        int[] script = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            script[i] = Integer.parseInt(args[i]);
        }
        System.out.println("指令腳本=" + Arrays.toString(script));
        // 本例約定輸入 Java int 範圍內整數；非整數與超出範圍的參數留到例外處理。
    }
}
