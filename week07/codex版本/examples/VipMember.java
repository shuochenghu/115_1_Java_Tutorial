// VIP 是一種會員，新增領取加贈點數的能力。
// Member 的 private 狀態仍在同一個物件內，但不是子類別繼承的成員。
public class VipMember extends Member {
    private int bonusCount;

    public VipMember(String name) {
        super(name);
        bonusCount = 0;
        System.out.println("VipMember constructor: " + name);
    }

    public int getBonusCount() { return bonusCount; }

    public void claimBonus() {
        addPoints(20);
        bonusCount++;
    }
}
