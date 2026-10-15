// 第五週教師完整解答（claude 版本）：一筆任務紀錄，取代第四週三條平行清單的同一個索引。
// 建立後內容不再改變，所以欄位用 private final，也不提供 setter。
public class MissionStep {
    private final int command;
    private final String result;
    private final int energyAfter;   // 執行「後」的能源；存的是當下的數值，不是探測車本身

    // TODO 3：參數與欄位同名，用 this 指出要存入的是欄位。
    public MissionStep(int command, String result, int energyAfter) {
        this.command = command;
        this.result = result;
        this.energyAfter = energyAfter;
    }

    public int getCommand() {
        return command;
    }

    // TODO 4：模仿 getCommand。
    public String getResult() {
        return result;
    }

    public int getEnergyAfter() {
        return energyAfter;
    }

    // TODO 7：不是 OK、也不是 RETURN，就是被拒絕或無效的一步。
    public boolean isRejected() {
        return !result.equals("OK") && !result.equals("RETURN");
    }

    // 回放用的一行文字，格式與第四週相同。
    public String describe() {
        return "command=" + command + ", result=" + result + ", energy=" + energyAfter;
    }
}
