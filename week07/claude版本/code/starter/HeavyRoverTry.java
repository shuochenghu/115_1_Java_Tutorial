// 第七週學生用的小測試程式（claude 版本）：直接對 HeavyRover 下指令，檢查繼承來的方法與新增的鑽探。
// 這個檔案已完成，不必修改。每行印出實際值與預期值，最後一欄 成功 表示相同、不符 表示不同。
public class HeavyRoverTry {
    public static void main(String[] args) {
        // 建構子交給 Rover 檢查：超出範圍的初始值會被改成合法值。
        HeavyRover big = new HeavyRover(25, -1);
        show("重型探測車", big, "20/0/0 鑽探次數=0");

        // 鑽探一次，再用繼承來的 execute 探索一次，達到 5 份就停止。
        HeavyRover h = new HeavyRover(20, 0);
        check("h.drill()", h.drill(), "成功");
        show("h", h, "12/3/1 鑽探次數=1");
        check("h.execute(1)", h.execute(1), "成功");
        check("h.isSuccess()", "" + h.isSuccess(), "true");
        check("h.drill()", h.drill(), "任務已停止");
        show("h", h, "6/5/2 鑽探次數=1");

        // 能源不足：拒絕且狀態不變；之後仍能用繼承來的行動。
        HeavyRover low = new HeavyRover(7, 0);
        check("low.drill()", low.drill(), "能源不足");
        check("low.execute(1)", low.execute(1), "成功");
        show("能源不足", low, "1/2/1 鑽探次數=0");

        // 剛好用完能源：鑽探成功，之後耗盡停止。
        HeavyRover exact = new HeavyRover(8, 0);
        check("exact.drill()", exact.drill(), "成功");
        check("exact.execute(3)", exact.execute(3), "任務已停止");
        show("能源剛好足夠", exact, "0/3/1 鑽探次數=1");

        // 一開始就耗盡：不能鑽探。
        HeavyRover empty = new HeavyRover(0, 0);
        check("empty.drill()", empty.drill(), "任務已停止");
        show("初始能源為零的探測車", empty, "0/0/0 鑽探次數=0");
    }

    static void check(String label, String actual, String expected) {
        String mark = actual.equals(expected) ? "相符" : "不符";
        System.out.println(label + " -> " + actual + "  （預期 " + expected + "） " + mark);
    }

    // 印出 energy/samples/actionCount 與鑽探次數；前三個用的是從 Rover 繼承來的 getter。
    static void show(String label, HeavyRover rover, String expected) {
        String actual = rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount()
                + " 鑽探次數=" + rover.getDrillCount();
        String mark = actual.equals(expected) ? "相符" : "不符";
        System.out.println(label + ": " + actual + "  （預期 " + expected + "） " + mark);
    }
}
