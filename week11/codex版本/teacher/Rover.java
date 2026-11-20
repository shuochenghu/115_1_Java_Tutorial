// 類別：Rover（探測車的共同部分，抽象類別）
// 意義：所有探測車共有的狀態（能源、樣本、行動次數）、行動規則與探索流程。
// 作用：子類別（StandardRover、HeavyRover）只需覆寫 exploreYield() 決定產量；
//       實作 SampleSource，所以車輛也能交給樣本報表。
// 建構契約保持 energy 0..20、samples >= 0，不自動修正契約外參數。
public abstract class Rover implements SampleSource {
    // energy：目前能源格數（0～20）。
    private int energy;
    // samples：目前已收集的樣本數，達到 5 份就算任務成功。
    private int samples;
    // actionCount：成功行動的次數；被拒絕的指令不算。
    private int actionCount;

    // 建構子：設定初始能源與樣本，行動次數從 0 開始。
    public Rover(int energy, int samples) {
        this.energy = energy;
        this.samples = samples;
        actionCount = 0;
    }

    // getEnergy：回傳目前能源。
    public int getEnergy() { return energy; }
    // getSamples：回傳目前樣本數；這也是實作 SampleSource 介面規定的方法。
    @Override
    public int getSamples() { return samples; }
    // getActionCount：回傳成功行動次數。
    public int getActionCount() { return actionCount; }
    // isSuccess：樣本數是否已達 5 份（任務成功的條件）。
    public boolean isSuccess() { return samples >= 5; }
    // isExhausted：能源是否已經用完（等於 0）。
    public boolean isExhausted() { return energy == 0; }
    // isStopped：成功或耗盡任一成立就停止，之後不再接受行動。
    public boolean isStopped() { return isSuccess() || isExhausted(); }

    // execute：執行一個指令（1 探索、2 掃描、3 充電、0 返回），回傳結果字串。
    // 原有指令入口。新增的 drill 不經過這裡，必須自行檢查停止。
    // 返回 的任務流程由 Mission 記住；Rover 本身不記錄返回。
    public String execute(int command) {
        if (isStopped()) { return "任務已停止"; }
        switch (command) {
            case 1: return explore();
            case 2: return scan();
            case 3: return charge();
            case 0: return "返回";
            default: return "無效指令";
        }
    }

    // spendEnergy：嘗試扣除 amount 格能源。amount 必須是正數且不超過目前能源，
    // 成功就扣除並回傳 true；否則回傳 false，能源不變。
    // 此方法只檢查並扣除能源。停止、加樣本與計次由行動入口處理。
    protected boolean spendEnergy(int amount) {
        if (amount <= 0 || amount > energy) { return false; }
        energy -= amount;
        return true;
    }

    // addSamples：增加 amount 份樣本。只接受正數，0 或負數會被忽略。
    // 已提供；停止檢查與「整組行動」仍由呼叫它的行動方法（例如 explore）負責。
    protected void addSamples(int amount) {
        if (amount > 0) { samples += amount; }
    }

    // countAction：成功行動次數加 1。由行動方法在行動成功後呼叫。
    protected void countAction() {
        actionCount++;
    }

    // exploreYield：回傳「探索一次得到幾份樣本」。
    // 抽象方法：只有宣告、沒有本體，每個具體子類別都必須覆寫它。
    // 只回產量。扣能源、加樣本、計次由探索流程處理。
    protected abstract int exploreYield();

    // explore：探索。用 spendEnergy 扣 6 格，成功才加樣本並計一次行動；能源不足回「能源不足」。
    // 加幾份樣本由 exploreYield() 決定，所以會依實際車種而不同。
    private String explore() {
        if (!spendEnergy(6)) { return "能源不足"; }
        addSamples(exploreYield());
        countAction();
        return "成功";
    }

    // scan：掃描。用 spendEnergy 扣 2 格，成功才加 1 份樣本並計一次行動；能源不足回「能源不足」。
    private String scan() {
        if (!spendEnergy(2)) { return "能源不足"; }
        addSamples(1);
        countAction();
        return "成功";
    }

    // charge：充電。能源未滿時加 5 格（最多 20）、行動次數加 1；已滿回「能源已滿」。
    private String charge() {
        if (energy == 20) { return "能源已滿"; }
        energy += 5;
        if (energy > 20) { energy = 20; }
        actionCount++;
        return "成功";
    }
}
