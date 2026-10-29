// 重型探測車沿用探索、掃描、充電，新增鑽探；本週不覆寫任何方法。
public class HeavyRover extends Rover {
    // 已提供的具名常數，避免混用成本與產量；不是本週新增語法驗收。
    public static final int DRILL_COST = 8;
    public static final int DRILL_YIELD = 3;

    // TODO 2：只新增鑽探次數，不再宣告 energy、samples、actionCount。
    // 在這裡補欄位。

    public HeavyRover(int energy, int samples) {
        super(energy, samples);
        // 在這裡初始化自己的欄位；super 已提供供追蹤與隔離實驗。
    }

    public int getDrillCount() { return 0; }

    // TODO 3：新公開入口自己檢查停止。先全部拒絕條件，再更新整組狀態。
    public String drill() {
        return "NOT_READY";
    }
}
