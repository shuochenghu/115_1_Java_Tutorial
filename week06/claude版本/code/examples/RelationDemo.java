// 第六週範例（claude 版本）：先預測，再執行。
// 兩支手電筒拿到同一顆電池的參考，第三支有自己的電池；充電器只在呼叫時用到電池。
public class RelationDemo {
    public static void main(String[] args) {
        Battery shared = new Battery(10, 10);
        Flashlight a = new Flashlight("A", shared);
        Flashlight b = new Flashlight("B", shared);
        Flashlight c = new Flashlight("C", new Battery(10, 10));

        System.out.println("燈具 A 開啟 6 分鐘： " + a.turnOn(6));
        System.out.println("燈具 B 開啟 6 分鐘： " + b.turnOn(6));
        System.out.println("燈具 C 開啟 6 分鐘： " + c.turnOn(6));
        System.out.println("共用電池電量： " + shared.getLevel());

        Charger charger = new Charger();
        System.out.println("替共用電池充電： +" + charger.refill(shared));
        System.out.println("燈具 B 開啟 6 分鐘： " + b.turnOn(6));
        System.out.println("共用電池電量： " + shared.getLevel());
    }
}
