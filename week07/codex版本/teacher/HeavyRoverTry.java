// 學生用檢查入口，已提供；實際值和預期值不同會印 DIFF。
// 先看講義規格自行推算，再執行。本檔不能代替你自己的反例。
public class HeavyRoverTry {
    public static void main(String[] args) {
        HeavyRover h = new HeavyRover(20, 0);
        check("drill", h.drill(), "OK");
        show("after drill", h, "12/3/1/1");
        check("explore", h.execute(1), "OK");
        show("after explore", h, "6/5/2/1");
        check("done drill", h.drill(), "STOPPED");
        show("done unchanged", h, "6/5/2/1");

        HeavyRover low = new HeavyRover(7, 0);
        check("low", low.drill(), "NOT_ENOUGH_ENERGY");
        show("low unchanged", low, "7/0/0/0");
        HeavyRover exact = new HeavyRover(8, 0);
        check("exact", exact.drill(), "OK");
        show("exact state", exact, "0/3/1/1");
        check("zero cannot charge", exact.execute(3), "STOPPED");
        HeavyRover zero = new HeavyRover(0, 0);
        check("initial zero", zero.drill(), "STOPPED");
        show("zero unchanged", zero, "0/0/0/0");
        HeavyRover done = new HeavyRover(20, 5);
        check("initial success", done.drill(), "STOPPED");
        show("success unchanged", done, "20/5/0/0");
    }

    static void show(String label, HeavyRover h, String expected) {
        String actual = h.getEnergy() + "/" + h.getSamples() + "/"
                + h.getActionCount() + "/" + h.getDrillCount();
        check(label, actual, expected);
    }

    static void check(String label, String actual, String expected) {
        String mark = actual.equals(expected) ? "OK" : "DIFF";
        System.out.println(label + ": " + actual + " (expect " + expected + ") " + mark);
    }
}
