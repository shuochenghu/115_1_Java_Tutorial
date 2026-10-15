// 第五週教師完整解答（claude 版本）：探測車自己保管狀態，也自己執行行動規則。
// 規則沿用第三、四週：容量 20；探索耗 6 得 2；掃描耗 2 得 1；充電 +5 封頂 20；樣本達 5 成功。
public class Rover {
    public static final int CAPACITY = 20;
    public static final int TARGET_SAMPLES = 5;

    // 第四週的 state[0]、state[1]、state[2]，改成有名字的 private 欄位。
    private int energy;
    private int samples;
    private int actionCount;

    // TODO 1：初始值超出範圍時改成最接近的合法值，再存入欄位；行動次數一律從 0 開始。
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

    public String explore() {
        if (energy < 6) {
            return "NOT_ENOUGH_ENERGY";
        }
        energy -= 6;
        samples += 2;
        actionCount++;
        return "OK";
    }

    // TODO 2：模仿 explore 完成 scan 與 charge；被拒絕時狀態不變。
    public String scan() {
        if (energy < 2) {
            return "NOT_ENOUGH_ENERGY";
        }
        energy -= 2;
        samples++;
        actionCount++;
        return "OK";
    }

    public String charge() {
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

    // 把指令代碼轉成對應的行動；0 是返回，其他代碼是無效指令，都不改變狀態。
    public String execute(int command) {
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

    public boolean isSuccess() {
        return samples >= TARGET_SAMPLES;
    }

    public boolean isExhausted() {
        return energy == 0;
    }
}
