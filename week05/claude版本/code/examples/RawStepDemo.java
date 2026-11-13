import java.util.ArrayList;

// 範例 1（claude 版本）：類別是設計圖，new 才產生物件；物件變數存的是參考。
public class RawStepDemo {
    public static void main(String[] args) {
        // 1. new 之後欄位已有預設值：int 是 0，String 這類參考型態是 null。
        RawStep a = new RawStep();
        System.out.println("剛建立 RawStep 時： 指令=" + a.command + ", 結果=" + a.result
                + ", 能源=" + a.energy);

        // 2. 兩個 new 是兩個獨立物件，各有自己的欄位。
        a.command = 1;
        a.result = "成功";
        a.energy = 14;
        RawStep b = new RawStep();
        b.command = 2;
        b.result = "成功";
        b.energy = 12;
        System.out.println("a: " + a.command + "/" + a.result + "/" + a.energy
                + "  b: " + b.command + "/" + b.result + "/" + b.energy);

        // 3. 指定運算複製的是參考：alias 和 a 指向同一個物件（第二、四週的箭頭圖）。
        RawStep alias = a;
        alias.energy = 99;
        System.out.println("執行 alias.energy = 99 後，a 的能源=" + a.energy
                + ", a 與 alias 指向同一物件？ " + (a == alias));

        // 4. 直接印物件，只看到「類別名稱@雜湊碼」，和第四週印陣列相同。
        System.out.println("直接印出 a：" + a);

        // 5. 一條清單放整筆紀錄，不必再維護三條平行清單。
        ArrayList<RawStep> steps = new ArrayList<>();
        steps.add(a);
        steps.add(b);
        System.out.println("清單筆數=" + steps.size()
                + ", 第二筆紀錄的能源=" + steps.get(1).energy);

        // 6. 問題：欄位是 public，任何程式都能寫入不合理的值，也可能忘了設定某個欄位。
        RawStep bad = new RawStep();
        bad.command = 1;
        bad.energy = -7;   // 能源不會是負的，但編譯器不會阻止
        System.out.println("錯誤範例的指令=" + bad.command + ", 結果=" + bad.result
                + ", 能源=" + bad.energy);
    }
}
