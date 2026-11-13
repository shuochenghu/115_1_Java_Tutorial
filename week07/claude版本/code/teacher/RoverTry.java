// 第七週沿用第六週的小測試程式（claude 版本）：不經過 Mission，直接對 Rover 下指令。
// 本週重構 Rover 的 explore、scan 之後再跑一次，每一行都應仍是 成功；這個檔案已完成，不必修改。
public class RoverTry {
    public static void main(String[] args) {
        Rover tired = new Rover(0, 0);
        check("tired.execute(3)", tired.execute(3), "任務已停止");
        show("tired", tired, "0/0/0");

        Rover done = new Rover(20, 5);
        check("done.execute(1)", done.execute(1), "任務已停止");
        show("done", done, "20/5/0");

        Rover r = new Rover(6, 0);
        check("r.execute(1)", r.execute(1), "成功");
        check("r.execute(3)", r.execute(3), "任務已停止");
        show("r", r, "0/2/1");

        Rover fresh = new Rover(20, 0);
        check("fresh.execute(3)", fresh.execute(3), "能源已滿");
        check("fresh.execute(2)", fresh.execute(2), "成功");
        check("fresh.execute(9)", fresh.execute(9), "無效指令");
        check("fresh.execute(0)", fresh.execute(0), "返回");
        show("fresh", fresh, "18/1/1");
    }

    static void check(String label, String actual, String expected) {
        String mark = actual.equals(expected) ? "相符" : "不符";
        System.out.println(label + " -> " + actual + "  （預期 " + expected + "） " + mark);
    }

    // 印出 energy/samples/actionCount，並列出預期值方便比對。
    static void show(String label, Rover rover, String expected) {
        String actual = rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount();
        String mark = actual.equals(expected) ? "相符" : "不符";
        System.out.println(label + ": " + actual + "  （預期 " + expected + "） " + mark);
    }
}
