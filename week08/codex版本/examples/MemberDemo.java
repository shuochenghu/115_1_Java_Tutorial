// 先在講義B1預測，才執行。會員範例與探測車完全獨立。
public class MemberDemo {
    public static void main(String[] args) {
        Member amy = new Member("Amy");
        VipMember bob = new VipMember("Bob");
        amy.buy(120);
        bob.buy(80);
        bob.claimBonus();

        // 同一物件由不同型態參考指向，不會產生另一份點數。
        Member memberView = bob;
        System.out.println(amy.describe());
        System.out.println(bob.describe());
        System.out.println(memberView.describe());
    }
}
