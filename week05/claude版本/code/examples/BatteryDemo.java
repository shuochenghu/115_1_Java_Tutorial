// 範例 2（claude 版本）：建構子把不合理的初始值改成合法值；方法拒絕不合法的操作。
public class BatteryDemo {
    public static void main(String[] args) {
        Battery spare = new Battery(10, 4);
        System.out.println("new Battery(10, 4): level=" + spare.getLevel()
                + ", capacity=" + spare.getCapacity());

        Battery tooFull = new Battery(10, 25);
        Battery negative = new Battery(10, -3);
        System.out.println("new Battery(10, 25): level=" + tooFull.getLevel());
        System.out.println("new Battery(10, -3): level=" + negative.getLevel());

        System.out.println("spare.use(3) -> " + spare.use(3) + ", level=" + spare.getLevel());
        System.out.println("spare.use(5) -> " + spare.use(5) + ", level=" + spare.getLevel());
        System.out.println("spare.use(-2) -> " + spare.use(-2) + ", level=" + spare.getLevel());
        System.out.println("spare.charge(20) -> " + spare.charge(20) + ", level=" + spare.getLevel());

        // 每個物件有自己的欄位：改 spare 不影響 tooFull。
        System.out.println("tooFull.getLevel() is still " + tooFull.getLevel());

        // 拿掉下一行的註解再編譯：level 是 private，Battery 以外的類別不能直接存取。
        // spare.level = 99;

        // 拿掉下一行的註解再編譯：自己寫了建構子，編譯器就不再提供無參數的預設建構子。
        // Battery empty = new Battery();
    }
}
