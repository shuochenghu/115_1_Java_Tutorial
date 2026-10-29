// 重型探測車沿用探索、掃描、充電，新增鑽探；本週不覆寫任何方法。
public class HeavyRover extends Rover {
    // 已提供的具名常數，避免混用成本與產量；不是本週新增語法驗收。
    public static final int DRILL_COST = 8;
    public static final int DRILL_YIELD = 3;

    // TODO 2：只新增鑽探次數，不再宣告 energy、samples、actionCount。
    private int drillCount;

    public HeavyRover(int energy, int samples) {
        super(energy, samples);
        drillCount = 0;
    }

    public int getDrillCount() { return drillCount; }

    // TODO 3：新公開入口自己檢查停止。先全部拒絕條件，再更新整組狀態。
    public String drill() {
        if (isStopped()) { return "STOPPED"; }
        if (!spendEnergy(DRILL_COST)) { return "NOT_ENOUGH_ENERGY"; }
        addSamples(DRILL_YIELD);
        countAction();
        drillCount++;
        return "OK";
    }
}
