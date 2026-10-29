// 第七週學生用的小測試程式（claude 版本）：直接對 HeavyRover 下指令，檢查繼承來的方法與新增的鑽探。
// 這個檔案已完成，不必修改。每行印出實際值與預期值，最後一欄 OK 表示相同、DIFF 表示不同。
public class HeavyRoverTry {
    public static void main(String[] args) {
        // 建構子交給 Rover 檢查：超出範圍的初始值會被改成合法值。
        HeavyRover big = new HeavyRover(25, -1);
        show("big", big, "20/0/0 drill=0");

        // 鑽探一次，再用繼承來的 execute 探索一次，達到 5 份就停止。
        HeavyRover h = new HeavyRover(20, 0);
        check("h.drill()", h.drill(), "OK");
        show("h", h, "12/3/1 drill=1");
        check("h.execute(1)", h.execute(1), "OK");
        check("h.isSuccess()", "" + h.isSuccess(), "true");
        check("h.drill()", h.drill(), "STOPPED");
        show("h", h, "6/5/2 drill=1");

        // 能源不足：拒絕且狀態不變；之後仍能用繼承來的行動。
        HeavyRover low = new HeavyRover(7, 0);
        check("low.drill()", low.drill(), "NOT_ENOUGH_ENERGY");
        check("low.execute(1)", low.execute(1), "OK");
        show("low", low, "1/2/1 drill=0");

        // 剛好用完能源：鑽探成功，之後耗盡停止。
        HeavyRover exact = new HeavyRover(8, 0);
        check("exact.drill()", exact.drill(), "OK");
        check("exact.execute(3)", exact.execute(3), "STOPPED");
        show("exact", exact, "0/3/1 drill=1");

        // 一開始就耗盡：不能鑽探。
        HeavyRover empty = new HeavyRover(0, 0);
        check("empty.drill()", empty.drill(), "STOPPED");
        show("empty", empty, "0/0/0 drill=0");
    }

    static void check(String label, String actual, String expected) {
        String mark = actual.equals(expected) ? "OK" : "DIFF";
        System.out.println(label + " -> " + actual + "  (expect " + expected + ") " + mark);
    }

    // 印出 energy/samples/actionCount 與鑽探次數；前三個用的是從 Rover 繼承來的 getter。
    static void show(String label, HeavyRover rover, String expected) {
        String actual = rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount()
                + " drill=" + rover.getDrillCount();
        String mark = actual.equals(expected) ? "OK" : "DIFF";
        System.out.println(label + ": " + actual + "  (expect " + expected + ") " + mark);
    }
}
