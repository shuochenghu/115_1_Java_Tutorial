// 第七週範例（claude 版本）：飲料店會員。作為 VipMember 的父類別（superclass）。
// 消費每滿 10 元得 1 點（整數除法）。欄位 private，子類別也不能直接改點數，只能透過 protected 的 addPoints。
public class Member {
    private final String id;
    private final String name;
    private int points;

    // 建構子印出一行，只為了課堂追蹤「誰的建構子先執行」；一般程式的建構子不會這樣印。
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.points = 0;
        System.out.println("  [Member constructor] " + id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    // 回傳這次得到的點數；金額不合理時不得點。
    public int buy(int price) {
        if (price <= 0) {
            return 0;
        }
        int earned = price / 10;
        addPoints(earned);
        return earned;
    }

    // protected：子類別可以呼叫（Java 中同一套件的類別也可以）。點數只會增加。
    protected void addPoints(int amount) {
        if (amount > 0) {
            points += amount;
        }
    }

    public String describe() {
        return id + " " + name + " points=" + points;
    }
}
