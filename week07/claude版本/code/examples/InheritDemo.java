// 第七週範例（claude 版本）：先預測，再執行。
// 1. 建立物件時，父類別與子類別的建構子誰先執行；2. 子類別物件能用哪些方法；3. 每個類別的父類別是誰。
public class InheritDemo {
    public static void main(String[] args) {
        System.out.println("建立 Member：");
        Member amy = new Member("M001", "Amy");
        System.out.println("建立 VipMember：");
        VipMember bob = new VipMember("V001", "Bob");

        System.out.println("Amy 消費 120，增加點數：" + amy.buy(120));
        System.out.println("Bob 消費 85，增加點數：" + bob.buy(85));      // buy 是從 Member 繼承來的
        System.out.println("Bob 領取獎勵：" + bob.claimBonus());   // claimBonus 是 VipMember 新增的
        System.out.println(amy.describe());
        System.out.println(bob.describe());                        // 繼承來的 describe 不知道 VIP 的資料
        System.out.println("Bob 的獎勵次數：" + bob.getBonusCount());

        // getClass() 也是繼承來的：它寫在每個類別最上層的父類別 Object 裡。
        System.out.println("Bob 的實際類別：" + bob.getClass().getName());
        System.out.println("VipMember 的父類別：" + bob.getClass().getSuperclass().getName());
        System.out.println("Member 的父類別：" + amy.getClass().getSuperclass().getName());
    }
}
