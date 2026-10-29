// 第七週：在第六週 Codex 的 Rover 上提供子類別擴充方法。
// 建構契約保持 energy 0..20、samples >= 0，不自動修正契約外參數。
public class Rover {
    private int energy;
    private int samples;
    private int actionCount;

    public Rover(int energy, int samples) {
        this.energy = energy;
        this.samples = samples;
        actionCount = 0;
    }

    public int getEnergy() { return energy; }
    public int getSamples() { return samples; }
    public int getActionCount() { return actionCount; }
    public boolean isSuccess() { return samples >= 5; }
    public boolean isExhausted() { return energy == 0; }
    public boolean isStopped() { return isSuccess() || isExhausted(); }

    // 原有指令入口。新增的 drill 不經過這裡，必須自行檢查停止。
    // RETURN 的任務流程由 Mission 記住；Rover 本身不記錄返回。
    public String execute(int command) {
        if (isStopped()) { return "STOPPED"; }
        switch (command) {
            case 1: return explore();
            case 2: return scan();
            case 3: return charge();
            case 0: return "RETURN";
            default: return "UNKNOWN_COMMAND";
        }
    }

    // TODO 1：支援擴充及局部重構。先完成扣能源，跟做 explore，再獨立改 scan。
    // 這個方法只守住扣能源規則，不負責停止、加樣本或計次。
    protected boolean spendEnergy(int amount) {
        if (amount <= 0 || amount > energy) { return false; }
        energy -= amount;
        return true;
    }

    // 已提供。呼叫者只傳課堂小整數；停止與整組行動仍由入口負責。
    protected void addSamples(int amount) {
        if (amount > 0) { samples += amount; }
    }

    protected void countAction() {
        actionCount++;
    }

    private String explore() {
        if (!spendEnergy(6)) { return "NOT_ENOUGH_ENERGY"; }
        addSamples(2);
        countAction();
        return "OK";
    }

    private String scan() {
        if (!spendEnergy(2)) { return "NOT_ENOUGH_ENERGY"; }
        addSamples(1);
        countAction();
        return "OK";
    }

    // 充電不是扣能源，本週保留既有寫法。
    private String charge() {
        if (energy == 20) { return "ALREADY_FULL"; }
        energy += 5;
        if (energy > 20) { energy = 20; }
        actionCount++;
        return "OK";
    }
}
