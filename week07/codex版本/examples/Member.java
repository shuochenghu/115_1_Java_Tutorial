// 獨立入門範例：一般會員保管點數規則。和主任務沒有程式相依。
public class Member {
    private String name;
    private int points;

    public Member(String name) {
        this.name = name;
        points = 0;
        // 只為課堂觀察建構順序，一般產品不必在建構子印訊息。
        System.out.println("Member constructor: " + name);
    }

    public String getName() { return name; }
    public int getPoints() { return points; }

    public void buy(int price) {
        if (price > 0) { addPoints(price / 10); }
    }

    // 本例在同一套件，其他類別也能呼叫；保留它給子類別擴充的意圖。
    protected void addPoints(int amount) {
        if (amount > 0) { points += amount; }
    }
}
