// 第七週作業的測試程式（claude 版本）：檢查 Camera、Projector 是否符合類別圖與規格。
// 本檔已完成，不必修改。要等 Camera.java、Projector.java 寫好才能編譯（刻意設計：類別不存在時會出現 cannot find symbol）。
public class ShelfTry {
    public static void main(String[] args) {
        Equipment mic = new Equipment("MIC-01", "Shure SM58");
        check("mic.getLabel()", mic.getLabel(), "MIC-01 Shure SM58");

        Camera cam = new Camera("CAM-01", "Sony A7", 2);
        check("cam.getLabel()", cam.getLabel(), "CAM-01 Sony A7");   // 繼承來的方法
        check("cam.getId()", cam.getId(), "CAM-01");
        check("cam.checklist()", cam.checklist(), "CAM-01 Sony A7 | 記憶卡數=2");

        Camera odd = new Camera("CAM-02", "Canon R50", -1);
        check("odd.getMemoryCards()", "" + odd.getMemoryCards(), "0");
        check("odd.checklist()", odd.checklist(), "CAM-02 Canon R50 | 記憶卡數=0");

        Projector big = new Projector("PRJ-01", "Epson X49", true);
        check("big.checklist()", big.checklist(), "PRJ-01 Epson X49 | 需要布幕=是");
        Projector mini = new Projector("PRJ-02", "Mini Beam", false);
        check("mini.needsScreen()", "" + mini.needsScreen(), "false");
        check("mini.checklist()", mini.checklist(), "PRJ-02 Mini Beam | 需要布幕=否");
    }

    static void check(String label, String actual, String expected) {
        String mark = actual.equals(expected) ? "相符" : "不符";
        System.out.println(label + " -> " + actual + "  （預期 " + expected + "） " + mark);
    }
}
