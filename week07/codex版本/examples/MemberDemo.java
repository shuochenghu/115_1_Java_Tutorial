// 執行：在 java-07 先編譯 src/*.java，再 java -cp out MemberDemo。
// 先預測父／子建構順序、整數除法及新增方法的作用，再執行。
public class MemberDemo {
    public static void main(String[] args) {
        Member amy = new Member("Amy");
        VipMember bob = new VipMember("Bob");
        amy.buy(120);
        bob.buy(85);
        bob.claimBonus();
        System.out.println("Amy points=" + amy.getPoints());
        System.out.println("Bob points=" + bob.getPoints());
        System.out.println("Bob bonus=" + bob.getBonusCount());
    }
}
