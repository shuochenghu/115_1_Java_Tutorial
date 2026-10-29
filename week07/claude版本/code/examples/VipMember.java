// 第七週範例（claude 版本）：VIP 會員是一種會員（is-a），所以繼承 Member（subclass）。
// 只寫「比一般會員多的部分」：領取加贈點數。id、name、points、buy、describe 都從 Member 繼承，不必重寫。
public class VipMember extends Member {
    public static final int BONUS = 20;

    private int bonusCount;   // 只有 VIP 才有的狀態

    public VipMember(String id, String name) {
        super(id, name);      // 先讓 Member 的建構子設定 id、name、points
        this.bonusCount = 0;
        System.out.println("  [VipMember constructor] " + id);
    }

    public int getBonusCount() {
        return bonusCount;
    }

    // 回傳領取後的總點數。points 是 Member 的 private 欄位，這裡只能透過 addPoints 與 getPoints。
    public int claimBonus() {
        addPoints(BONUS);
        bonusCount++;
        return getPoints();
    }
}
