import java.util.Arrays;

// 第六週 MissionLog（claude 版本）：只剩「讀參數、建立物件、印出結果」，任務流程與計算交給 Mission。
// 本檔已完成，學生不需修改；輸出與第五週逐字相同。與 Rover、MissionStep、Mission 放在同一個資料夾編譯。
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
        System.out.println("指令次數[0..3]=" + Arrays.toString(mission.countCommands()));
        printSummary(script, mission);
        System.out.println("最終能源=" + rover.getEnergy() + ", 樣本數=" + rover.getSamples()
                + ", 行動次數=" + rover.getActionCount() + " -> " + mission.getOutcome());
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
        System.out.println("=== 任務回放 ===");
        for (int i = 0; i < mission.getStepCount(); i++) {
            System.out.println("步驟 " + (i + 1) + ": " + mission.getStep(i).describe());
        }
    }

    // 腳本長度只有 MissionLog 知道，所以 ignored 在這裡算；其餘數字向 Mission 詢問。
    static void printSummary(int[] script, Mission mission) {
        int ignored = script.length - mission.getStepCount();
        System.out.println("步數=" + mission.getStepCount() + ", 成功=" + mission.countOk()
                + ", 拒絕=" + mission.countRejected() + ", 略過=" + ignored);
        if (mission.getStepCount() > 0) {
            System.out.println("最低能源=" + mission.lowestEnergy());
        }
    }
}
