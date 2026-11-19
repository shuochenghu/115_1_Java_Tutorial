// 類別：HeavyRover（重型探測車，第 8 週版本）
// 意義：一種探測車（extends Rover），探索一次得 3 份樣本（一般車得 2 份），另外能鑽探。
// 作用：示範兩種覆寫：覆寫 exploreYield()，沿用 Rover 的探索流程卻得到不同產量；
//       覆寫 execute()，讓任務腳本可以下指令 4（鑽探），其他指令交回父類別處理。鑽探規則沿用前週。
public class HeavyRover extends Rover {
    // 已提供的具名常數，避免把成本和產量兩個數字搞混；不是本週新增語法驗收。
    // DRILL_COST：鑽探一次消耗的能源格數。
    public static final int DRILL_COST = 8;
    // DRILL_YIELD：鑽探一次取得的樣本份數。
    public static final int DRILL_YIELD = 3;

    // drillCount：這台重型車成功鑽探的次數；是子類別自己才有的欄位。
    // 能源、樣本、行動次數由父類別 Rover 保存，這裡不再重複宣告。
    private int drillCount;

    // 建構子：能源與樣本交給父類別 Rover 保存，鑽探次數從 0 開始。
    public HeavyRover(int energy, int samples) {
        super(energy, samples);
        drillCount = 0;
    }

    // exploreYield：覆寫 Rover 的同名方法，重型車探索一次得 3 份。
    // TODO 2 教師答案：實例方法覆寫，沿用父類別的 protected 可見性。
    @Override
    protected int exploreYield() {
        return 3;
    }

    // execute：覆寫父類別的指令入口，讓重型車多接受指令 4（鑽探）。
    // 指令 4 交給 drill()；其他指令用 super.execute(command) 交回父類別，
    // 所以探索、掃描、充電、返回與無效指令的規則仍只寫在 Rover 一處，不在這裡重抄 switch。
    // Mission 只認得 Rover，執行 rover.execute(command) 時會依實際物件選到這個版本，Mission 不必修改。
    // 例：重型車收到 4 會鑽探；一般車沒有這個覆寫，收到 4 仍回「無效指令」。
    // TODO 4 教師答案：方法宣告與父類別相同，保留 public 並加 @Override。
    @Override
    public String execute(int command) {
        if (command == 4) { return drill(); }
        return super.execute(command);
    }

    // getDrillCount：回傳成功鑽探的次數。
    public int getDrillCount() { return drillCount; }

    // drill：鑽探一次，回傳結果字串（「成功」「能源不足」或「任務已停止」）。
    // 可以直接呼叫，也會由 execute(4) 呼叫。這條路不經過父類別 execute 的停止檢查，
    // 所以要自行檢查停止；只有成功才更新狀態。
    public String drill() {
        if (isStopped()) { return "任務已停止"; }
        if (!spendEnergy(DRILL_COST)) { return "能源不足"; }
        addSamples(DRILL_YIELD);
        countAction();
        drillCount++;
        return "成功";
    }
}
