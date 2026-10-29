// 第七週教師完整解答（claude 版本）：第六週的 Rover 加上三個 protected 方法，讓子類別能新增行動，又不能繞過狀態規則。
// 規則沿用第三至六週：容量 20；探索耗 6 得 2；掃描耗 2 得 1；充電 +5 封頂 20；樣本達 5 成功。
public class Rover {
    public static final int CAPACITY = 20;
    public static final int TARGET_SAMPLES = 5;

    // 欄位維持 private：子類別也不能直接改，只能透過下面的 protected 方法。
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

    public boolean isStopped() {
        return isSuccess() || isExhausted();
    }

    // 外部唯一的行動入口。0 是返回，其他未定義代碼是無效指令，都不改變狀態。
    public String execute(int command) {
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

    // TODO 1：給子類別使用的三個 protected 方法。
    // 能源不足或數量不合理時回傳 false，能源不變；夠用才扣除。能源不會變成負數。
    protected boolean spendEnergy(int amount) {
        if (amount <= 0 || amount > energy) {
            return false;
        }
        energy -= amount;
        return true;
    }

    // 數量不合理時不改變；樣本只會增加。
    protected void addSamples(int amount) {
        if (amount > 0) {
            samples += amount;
        }
    }

    protected void countAction() {
        actionCount++;
    }

    // TODO 2：探索與掃描改用上面的方法，規則只寫在一個地方；結果與第六週相同。
    private String explore() {
        if (!spendEnergy(6)) {
            return "NOT_ENOUGH_ENERGY";
        }
        addSamples(2);
        countAction();
        return "OK";
    }

    private String scan() {
        if (!spendEnergy(2)) {
            return "NOT_ENOUGH_ENERGY";
        }
        addSamples(1);
        countAction();
        return "OK";
    }

    // 充電是增加能源，不屬於上面三個方法的工作，維持第六週的寫法。
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
