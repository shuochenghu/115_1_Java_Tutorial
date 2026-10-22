// 第六週教師完整解答（claude 版本）：沿用第五週的 Rover，再把「任務停止後不能再行動」的規則收回 Rover。
// 規則沿用第三至五週：容量 20；探索耗 6 得 2；掃描耗 2 得 1；充電 +5 封頂 20；樣本達 5 成功。
public class Rover {
    public static final int CAPACITY = 20;
    public static final int TARGET_SAMPLES = 5;

    private int energy;
    private int samples;
    private int actionCount;

    // 初始值超出範圍時改成最接近的合法值，再存入欄位；行動次數一律從 0 開始。
    public Rover(int energy, int samples) {
        if (energy < 0) {
            energy = 0;
        } else if (energy > CAPACITY) {
            energy = CAPACITY;
        }
        if (samples < 0) {
            samples = 0;
        }
        this.energy = energy;
        this.samples = samples;
        this.actionCount = 0;
    }

    public int getEnergy() {
        return energy;
    }

    public int getSamples() {
        return samples;
    }

    public int getActionCount() {
        return actionCount;
    }

    public boolean isSuccess() {
        return samples >= TARGET_SAMPLES;
    }

    public boolean isExhausted() {
        return energy == 0;
    }

    // TODO 1：成功或耗盡，任務就停止。
    public boolean isStopped() {
        return isSuccess() || isExhausted();
    }

    // 外部唯一的行動入口。0 是返回，其他未定義代碼是無效指令，都不改變狀態。
    public String execute(int command) {
        // TODO 2：已停止的探測車不接受任何指令，狀態不變。
        // 第五週由 MissionLog 在迴圈裡擋；規則收回 Rover 後，不論誰呼叫都擋得住。
        if (isStopped()) {
            return "STOPPED";
        }
        switch (command) {
            case 1:
                return explore();
            case 2:
                return scan();
            case 3:
                return charge();
            case 0:
                return "RETURN";
            default:
                return "UNKNOWN_COMMAND";
        }
    }

    // TODO 2：三個行動改為 private，外部只能經過 execute，就繞不過上面的停止檢查。
    private String explore() {
        if (energy < 6) {
            return "NOT_ENOUGH_ENERGY";
        }
        energy -= 6;
        samples += 2;
        actionCount++;
        return "OK";
    }

    private String scan() {
        if (energy < 2) {
            return "NOT_ENOUGH_ENERGY";
        }
        energy -= 2;
        samples++;
        actionCount++;
        return "OK";
    }

    private String charge() {
        if (energy >= CAPACITY) {
            return "ALREADY_FULL";
        }
        energy += 5;
        if (energy > CAPACITY) {
            energy = CAPACITY;
        }
        actionCount++;
        return "OK";
    }
}
