import java.util.ArrayList;

// 第六週起始碼（claude 版本）：一次任務。保管這次任務的探測車與每一步紀錄，並回答「任務結果如何」。
// 方法清單照類別圖寫好了；TODO 3–6 的內容從第五週 MissionLog 搬過來再調整。
// 未完成前可以編譯執行，但結果不對：任務一步也不跑，摘要都是 0。
public class Mission {
    private final Rover rover;                    // 關聯：探測車由外部建立後交進來，任務只使用它
    private final ArrayList<MissionStep> steps;   // 任務自己建立的清單，整條清單不交給外部

    public Mission(Rover rover) {
        this.rover = rover;
        this.steps = new ArrayList<>();
    }

    // TODO 3：第五週 runMission 的迴圈搬到這裡；rover 和 steps 已是欄位，不必再當參數傳。
    // 探測車已停止就不再讀指令，也不記錄；遇到返回指令記錄後結束。用 rover.isStopped() 判斷停止。
    public void run(int[] script) {
    }

    // TODO 4：第五週 runMission 最後的四段判斷搬到這裡，順序不變。
    public String getOutcome() {
        return "UNFINISHED";
    }

    public int getStepCount() {
        return steps.size();
    }

    // MissionStep 建立後不能修改，交出單筆紀錄是安全的；交出整條清單，外部就能 add、remove、clear。
    public MissionStep getStep(int index) {
        return steps.get(index);
    }

    // 只計 0–3 的指令；無效指令不計。
    public int[] countCommands() {
        int[] counts = new int[4];
        for (MissionStep step : steps) {
            int command = step.getCommand();
            if (command >= 0 && command < counts.length) {
                counts[command]++;
            }
        }
        return counts;
    }

    // TODO 5：模仿 countCommands 走訪 steps。結果是 "OK" 的筆數（字串用 equals 比較）。
    public int countOk() {
        return 0;
    }

    // TODO 5：被拒絕的筆數，交給 MissionStep.isRejected() 判斷。
    public int countRejected() {
        return 0;
    }

    // TODO 6：所有紀錄中最低的執行後能源；沒有紀錄時回傳 -1。
    public int lowestEnergy() {
        return -1;
    }
}
