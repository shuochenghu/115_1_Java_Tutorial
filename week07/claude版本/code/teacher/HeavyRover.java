// 第七週教師完整解答（claude 版本）：重型探測車。繼承 Rover 的狀態、規則與所有 public 方法，再新增鑽探能力。
// 鑽探：耗能 8、得 3 份樣本、行動次數 +1；已停止回傳 STOPPED，能源不足回傳 NOT_ENOUGH_ENERGY，兩者都不改變狀態。
// 本週只「新增」：探索、掃描、充電與停止規則都沿用 Rover。讓探索的結果也不同，要等第八週的覆寫。
public class HeavyRover extends Rover {
    public static final int DRILL_COST = 8;
    public static final int DRILL_YIELD = 3;

    // TODO 3：只有重型探測車才有的狀態。energy、samples、actionCount 已在 Rover 裡，這裡不能再宣告一次。
    private int drillCount;

    // TODO 3：先用 super(...) 交給 Rover 的建構子檢查並設定能源與樣本，再設定自己的欄位。
    public HeavyRover(int energy, int samples) {
        super(energy, samples);
        this.drillCount = 0;
    }

    public int getDrillCount() {
        return drillCount;
    }

    // TODO 4：模仿 Rover.explore，但要自己先檢查停止：drill 不經過 execute，沒有人替它擋。
    public String drill() {
        if (isStopped()) {
            return "STOPPED";
        }
        if (!spendEnergy(DRILL_COST)) {
            return "NOT_ENOUGH_ENERGY";
        }
        addSamples(DRILL_YIELD);
        countAction();
        drillCount++;
        return "OK";
    }
}
