// 第五週學生起始碼（claude 版本）：探測車自己保管狀態，也自己執行行動規則。
// 規則沿用第三、四週：容量 20；探索耗 6 得 2；掃描耗 2 得 1；充電 +5 封頂 20；樣本達 5 成功。
// 可直接編譯執行；建構子完成前欄位都是預設值 0，所以 MissionLog 一開始會顯示 EXHAUSTED。
public class Rover {
    public static final int CAPACITY = 20;
    public static final int TARGET_SAMPLES = 5;

    // 第四週的 state[0]、state[1]、state[2]，改成有名字的 private 欄位。
    private int energy;
    private int samples;
    private int actionCount;

    // TODO 1：初始值超出範圍時改成最接近的合法值，再存入欄位；行動次數一律從 0 開始。
    // 提示：參照 Battery 的建構子。energy 限制在 0..CAPACITY，samples 不可小於 0。
    // 參數和欄位同名，存入欄位要寫 this.energy = ...；先修正參數、再存入欄位，順序不能顛倒。
    public Rover(int energy, int samples) {
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

    // 範例：能源不足時拒絕且狀態不變；成功時更新三個欄位。
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
    // scan：能源不足 2 回傳 "NOT_ENOUGH_ENERGY"；否則耗 2、樣本 +1、行動次數 +1，回傳 "OK"。
    // charge：已滿（energy >= CAPACITY）回傳 "ALREADY_FULL"；否則 +5、超過 CAPACITY 就改成 CAPACITY、行動次數 +1，回傳 "OK"。
    public String scan() {
        return "TODO";
    }

    public String charge() {
        return "TODO";
    }

    // 已提供：把指令代碼轉成對應的行動；0 是返回，其他代碼是無效指令，都不改變狀態。
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
