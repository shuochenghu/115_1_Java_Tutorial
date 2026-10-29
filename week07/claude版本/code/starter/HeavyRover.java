// 第七週起始碼（claude 版本）：重型探測車。繼承 Rover 的狀態、規則與所有 public 方法，再新增鑽探能力。
// 鑽探：耗能 8、得 3 份樣本、行動次數 +1；已停止回傳 STOPPED，能源不足回傳 NOT_ENOUGH_ENERGY，兩者都不改變狀態。
// 未完成前可以編譯執行，但 drill 只回傳 NOT_READY，什麼都不做。
public class HeavyRover extends Rover {
    public static final int DRILL_COST = 8;
    public static final int DRILL_YIELD = 3;

    // TODO 3：宣告只有重型探測車才有的欄位 drillCount（鑽探次數）。
    // energy、samples、actionCount 已在 Rover 裡，這裡不能再宣告一次（講義 E 段會看到為什麼）。

    // super(...) 把能源與樣本交給 Rover 的建構子檢查並設定；這一行先寫好了，講義 B2 會把它拿掉做實驗。
    public HeavyRover(int energy, int samples) {
        super(energy, samples);
        // TODO 3：drillCount 從 0 開始。
    }

    // TODO 3：回傳 drillCount。
    public int getDrillCount() {
        return 0;
    }

    // TODO 4：模仿 Rover.explore，用 TODO 1 的方法完成鑽探。
    // 注意：drill 不經過 execute，要自己先檢查停止。
    public String drill() {
        return "NOT_READY";
    }
}
