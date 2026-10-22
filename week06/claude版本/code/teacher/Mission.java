import java.util.ArrayList;

// 第六週教師完整解答（claude 版本）：一次任務。保管這次任務的探測車與每一步紀錄，並回答「任務結果如何」。
// 由第五週 MissionLog 的 runMission、countCommands、printSummary 的計算部分搬過來；印出結果仍是 MissionLog 的工作。
public class Mission {
    private final Rover rover;                    // 關聯：探測車由外部建立後交進來，任務只使用它
    private final ArrayList<MissionStep> steps;   // 任務自己建立的清單，整條清單不交給外部

    public Mission(Rover rover) {
        this.rover = rover;
        this.steps = new ArrayList<>();
    }

    // TODO 3：第五週 runMission 的迴圈搬到這裡；rover 和 steps 已是欄位，不必再當參數傳。
    // 探測車已停止就不再讀指令，也不記錄；遇到返回指令記錄後結束。
    public void run(int[] script) {
        for (int command : script) {
            if (rover.isStopped()) {
                break;
            }
            String result = rover.execute(command);
            steps.add(new MissionStep(command, result, rover.getEnergy()));
            if (command == 0) {
                break;
            }
        }
    }

    // TODO 4：判斷順序與第三至五週相同，同時耗盡與達標時成功優先。
    public String getOutcome() {
        if (rover.isSuccess()) {
            return "SUCCESS";
        } else if (rover.isExhausted()) {
            return "EXHAUSTED";
        } else if (!steps.isEmpty() && steps.get(steps.size() - 1).getCommand() == 0) {
            return "RETURNED";
        } else {
            return "UNFINISHED";
        }
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

    // TODO 5：模仿 countCommands 走訪 steps。
    public int countOk() {
        int ok = 0;
        for (MissionStep step : steps) {
            if (step.getResult().equals("OK")) {
                ok++;
            }
        }
        return ok;
    }

    public int countRejected() {
        int rejected = 0;
        for (MissionStep step : steps) {
            if (step.isRejected()) {
                rejected++;
            }
        }
        return rejected;
    }

    // TODO 6：所有紀錄中最低的執行後能源；沒有紀錄時回傳 -1。
    public int lowestEnergy() {
        if (steps.isEmpty()) {
            return -1;
        }
        int lowest = steps.get(0).getEnergyAfter();
        for (MissionStep step : steps) {
            if (step.getEnergyAfter() < lowest) {
                lowest = step.getEnergyAfter();
            }
        }
        return lowest;
    }
}
