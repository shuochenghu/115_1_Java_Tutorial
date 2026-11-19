// 類別：CompareMemberDemo（第 7 週對照範例）
// 意義：把「繼承版」VipMember 和「不用繼承版」StandaloneVipMember 放在一起執行的程式入口。
// 作用：讓兩位 VIP 做同樣的購物與領取加贈，比較兩件事：
//       (1) 建立物件時印出的建構子訊息：繼承版印兩行，不用繼承版只印一行。
//       (2) 點數與獎勵次數：兩種寫法的結果完全相同。
// 執行：在教材目錄 javac -encoding UTF-8 -d out examples/*.java，再 java -cp out CompareMemberDemo。
// 本檔由 Claude 依教師要求新增，供課堂對照；先預測輸出再執行。
public class CompareMemberDemo {
    // main：程式進入點。兩位 VIP 各購物 85 元並領取一次加贈，再印出點數與獎勵次數。
    public static void main(String[] args) {
        System.out.println("--- 繼承版 VipMember ---");
        VipMember inherited = new VipMember("Bob");
        inherited.buy(85);
        inherited.claimBonus();
        System.out.println("點數=" + inherited.getPoints() + ", 獎勵次數=" + inherited.getBonusCount());

        System.out.println("--- 不用繼承版 StandaloneVipMember ---");
        StandaloneVipMember standalone = new StandaloneVipMember("Bob");
        standalone.buy(85);
        standalone.claimBonus();
        System.out.println("點數=" + standalone.getPoints() + ", 獎勵次數=" + standalone.getBonusCount());

        // 編譯實驗（只在副本取消註解，一次一行）：「是不是一種 Member」
        // 下一行可以編譯：VipMember extends Member，VIP 是一種會員。
        // Member asMember = inherited;
        // 下一行會編譯錯誤：StandaloneVipMember 和 Member 沒有繼承關係，雖然方法名稱都一樣，
        // 編譯器也不會把它當成 Member。（用父類別型態的變數操作子類別物件，第 8 週會正式學。）
        // Member notMember = standalone;
    }
}
