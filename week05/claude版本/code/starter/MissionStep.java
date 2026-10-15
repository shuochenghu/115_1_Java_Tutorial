// 第五週學生起始碼（claude 版本）：一筆任務紀錄，取代第四週三條平行清單的同一個索引。
// 建立後內容不再改變，所以不提供 setter。
public class MissionStep {
    // 完成 TODO 3 後，在三個欄位的 private 後面加上 final 再編譯一次：
    // 若建構子漏掉某個欄位，編譯器會指出它可能沒有被初始化。
    private int command;
    private String result;
    private int energyAfter;   // 執行「後」的能源；存的是當下的數值，不是探測車本身

    // TODO 3：參數與欄位同名，用 this 指出要存入的是欄位，三個欄位都要存。
    public MissionStep(int command, String result, int energyAfter) {
    }

    public int getCommand() {
        return command;
    }

    // TODO 4：模仿 getCommand，回傳對應的欄位。
    public String getResult() {
        return "TODO";
    }

    public int getEnergyAfter() {
        return -1;
    }

    // TODO 7：不是 "OK"、也不是 "RETURN"，就是被拒絕或無效的一步，回傳 true。
    // 提示：字串內容比較用 equals（第二週）。
    public boolean isRejected() {
        return false;
    }

    // 已提供：回放用的一行文字，格式與第四週相同。
    public String describe() {
        return "command=" + command + ", result=" + result + ", energy=" + energyAfter;
    }
}
