// 第七週沿用第五、六週的完整 MissionStep（claude 版本），本週不需修改：一筆任務紀錄。
// 建立後內容不再改變，所以欄位用 private final，也不提供 setter；Mission 可以放心把單筆紀錄交給外部讀取。
public class MissionStep {
    private final int command;
    private final String result;
    private final int energyAfter;   // 執行「後」的能源；存的是當下的數值，不是探測車本身

    public MissionStep(int command, String result, int energyAfter) {
        this.command = command;
        this.result = result;
        this.energyAfter = energyAfter;
    }

    public int getCommand() {
        return command;
    }

    public String getResult() {
        return result;
    }

    public int getEnergyAfter() {
        return energyAfter;
    }

    public boolean isRejected() {
        return !result.equals("OK") && !result.equals("RETURN");
    }

    // 回放用的一行文字，格式與第四週相同。
    public String describe() {
        return "command=" + command + ", result=" + result + ", energy=" + energyAfter;
    }
}
