// 教師檢查工具（claude 版本）：檢查延伸題 SampleBay 的建構子、拒絕條件與卸貨。
public class SampleBayCheck {
    public static void main(String[] args) {
        SampleBay bay = new SampleBay(4);
        String actual = bay.getCapacity() + "/" + bay.getCount() + "/" + bay.getFreeSpace()
                + " " + bay.store(3) + " " + bay.store(2) + " " + bay.store(0) + " " + bay.store(-1)
                + " " + bay.getCount() + " " + bay.store(1) + " " + bay.getFreeSpace()
                + " " + bay.unload() + " " + bay.getCount();
        check("new SampleBay(4) flow", actual, "4/0/4 true false false false 3 true 0 4 0");
        check("new SampleBay(0)", "" + new SampleBay(0).getCapacity(), "1");
        check("new SampleBay(15)", "" + new SampleBay(15).getCapacity(), "10");
        check("unload empty", "" + new SampleBay(3).unload(), "0");
    }

    static void check(String label, String actual, String expected) {
        String mark = actual.equals(expected) ? "PASS " : "FAIL ";
        System.out.println(mark + label + " -> " + actual
                + (actual.equals(expected) ? "" : " (expect " + expected + ")"));
    }
}
