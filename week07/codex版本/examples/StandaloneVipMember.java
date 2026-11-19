// 類別：StandaloneVipMember（第 7 週對照範例：不用繼承的 VIP 會員）
// 意義：功能和 VipMember 完全相同的 VIP 會員，但「沒有」寫 extends Member；
//       名字、點數、購物規則這些 Member 已經有的東西，全部在這個類別裡自己再寫一次。
// 作用：課堂上和 VipMember（繼承版）並排比較，看出繼承省下了什麼：
//       ・相同：購物得點、領取加贈的規則與結果完全一樣。
//       ・不同：Member 的兩個欄位和四個方法在這裡被複製了一份。
//         購物規則若要改（例如改成每 5 元得 1 點），Member 和本類別兩處都要改；
//         漏改一處，一般會員和 VIP 的點數算法就會不一致。
//       ・不同：它不是一種 Member，不能存進 Member 型態的變數（見 CompareMemberDemo 的編譯實驗）。
// 本檔由 Claude 依教師要求新增，供課堂對照；不屬於學生必寫內容。
public class StandaloneVipMember {
    // ===== 以下兩個欄位是從 Member 複製過來的 =====
    // name：會員名字。（Member 已有同樣的欄位，這裡又寫了一份）
    private String name;
    // points：目前累積的點數。（Member 已有同樣的欄位，這裡又寫了一份）
    private int points;

    // ===== 以下欄位是 VIP 自己才有的（和 VipMember 相同）=====
    // bonusCount：已領取加贈點數的次數。
    private int bonusCount;

    // 建構子：所有欄位都由自己設定。
    // 繼承版的第一行是 super(name)，由 Member 設定名字和點數；這裡沒有自訂的父類別，只能自己來。
    // 所以建立物件時只會印出一行建構子訊息（繼承版會印兩行：先 Member、再 VipMember）。
    public StandaloneVipMember(String name) {
        this.name = name;
        points = 0;
        bonusCount = 0;
        System.out.println("StandaloneVipMember 建構子: " + name);
    }

    // getName：回傳會員名字。（從 Member 複製）
    public String getName() { return name; }
    // getPoints：回傳目前點數。（從 Member 複製）
    public int getPoints() { return points; }

    // buy：購物 price 元，每 10 元得 1 點（整數除法，不足 10 元的部分不計）。（從 Member 複製）
    public void buy(int price) {
        if (price > 0) { addPoints(price / 10); }
    }

    // addPoints：增加 amount 點；只接受正數。（從 Member 複製）
    // 這裡改成 private：沒有子類別會用到它，只給本類別自己的 buy 和 claimBonus 使用。
    private void addPoints(int amount) {
        if (amount > 0) { points += amount; }
    }

    // getBonusCount：回傳已領取加贈的次數。（和 VipMember 相同）
    public int getBonusCount() { return bonusCount; }

    // claimBonus：領取加贈 20 點，並把領取次數加 1。（和 VipMember 相同）
    // 繼承版呼叫的是父類別 Member 的 addPoints；這裡呼叫的是本類別自己複製的 addPoints。
    public void claimBonus() {
        addPoints(20);
        bonusCount++;
    }
}
