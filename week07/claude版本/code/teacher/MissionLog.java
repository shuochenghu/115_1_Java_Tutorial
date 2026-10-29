import java.util.Arrays;

// 第七週沿用第六週的 MissionLog（claude 版本），本週不需修改：讀參數、建立物件、印出結果。
// 輸出與第五、六週逐字相同；本週重構 Rover 後，用它確認任務行為沒有改變。
public class MissionLog {
    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        if (args.length > 0) {
            script = toScript(args);
        }

        Rover rover = new Rover(20, 0);
        Mission mission = new Mission(rover);
        mission.run(script);

        printReplay(mission);
        System.out.println("counts[0..3]=" + Arrays.toString(mission.countCommands()));
        printSummary(script, mission);
        System.out.println("final energy=" + rover.getEnergy() + ", samples=" + rover.getSamples()
                + ", actionCount=" + rover.getActionCount() + " -> " + mission.getOutcome());
    }

    // 與第四、五週相同：命令列參數逐一轉成 int。
    static int[] toScript(String[] args) {
        int[] script = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            script[i] = Integer.parseInt(args[i]);
        }
        return script;
    }

    static void printReplay(Mission mission) {
        System.out.println("=== replay ===");
        for (int i = 0; i < mission.getStepCount(); i++) {
            System.out.println("step " + (i + 1) + ": " + mission.getStep(i).describe());
        }
    }

    // 腳本長度只有 MissionLog 知道，所以 ignored 在這裡算；其餘數字向 Mission 詢問。
    static void printSummary(int[] script, Mission mission) {
        int ignored = script.length - mission.getStepCount();
        System.out.println("steps=" + mission.getStepCount() + ", ok=" + mission.countOk()
                + ", rejected=" + mission.countRejected() + ", ignored=" + ignored);
        if (mission.getStepCount() > 0) {
            System.out.println("lowest energy=" + mission.lowestEnergy());
        }
    }
}
