// 第八週：重型探測車探索產量改為3，鑽探與其他規則沿用前週。
public class HeavyRover extends Rover {
    // 已提供的具名常數，避免混用成本與產量；不是本週新增語法驗收。
    public static final int DRILL_COST = 8;
    public static final int DRILL_YIELD = 3;

    // 前週已完成的子類別狀態，不再宣告父類別狀態。
    private int drillCount;

    public HeavyRover(int energy, int samples) {
        super(energy, samples);
        drillCount = 0;
    }

    // TODO 2：自行寫出覆寫方法，使用 @Override，探索產量固定回傳3。
    // 只回傳產量，不額外扣能源、增加樣本或計次。

    public int getDrillCount() { return drillCount; }

    // 沿用前週：新公開入口自行檢查停止，只有成功才更新狀態。
    public String drill() {
        if (isStopped()) { return "STOPPED"; }
        if (!spendEnergy(DRILL_COST)) { return "NOT_ENOUGH_ENERGY"; }
        addSamples(DRILL_YIELD);
        countAction();
        drillCount++;
        return "OK";
    }
}
