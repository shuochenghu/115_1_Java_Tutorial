// 第五週學生用的小測試程式（claude 版本）：不經過 MissionLog，直接建立 Rover 物件檢查建構子與行動。
// 這個檔案已完成，不必修改；完成 Rover 的 TODO 1–2 後執行，逐行比對「實際」與「預期」。
public class RoverTry {
    public static void main(String[] args) {
        show("new Rover(20, 0)", new Rover(20, 0), "20/0/0");
        show("new Rover(25, -3)", new Rover(25, -3), "20/0/0");
        show("new Rover(4, 2)", new Rover(4, 2), "4/2/0");

        Rover r = new Rover(20, 0);
        System.out.println("r.charge() -> " + r.charge() + "  （預期 能源已滿）");
        System.out.println("r.scan() -> " + r.scan() + "  （預期 成功）");
        System.out.println("r.charge() -> " + r.charge() + "  （預期 成功, 18+5 封頂為 20）");
        show("r", r, "20/1/2");

        Rover low = new Rover(1, 0);
        System.out.println("low.scan() -> " + low.scan() + "  （預期 能源不足）");
        System.out.println("low.explore() -> " + low.explore() + "  （預期 能源不足）");
        show("low", low, "1/0/0");
    }

    // 印出 energy/samples/actionCount，並列出預期值方便比對。
    static void show(String label, Rover rover, String expected) {
        String actual = rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount();
        String mark = actual.equals(expected) ? "相符" : "不符";
        System.out.println(label + ": " + actual + "  （預期 " + expected + "） " + mark);
    }
}
