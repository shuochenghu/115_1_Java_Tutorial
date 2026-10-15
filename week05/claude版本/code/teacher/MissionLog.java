import java.util.ArrayList;
import java.util.Arrays;

// 第五週教師完整解答（claude 版本）：流程與輸出沿用第四週，資料改成 Rover 與 ArrayList<MissionStep>。
// 與 Rover.java、MissionStep.java 放在同一個資料夾編譯。
public class MissionLog {
    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        if (args.length > 0) {
            script = toScript(args);
        }

        Rover rover = new Rover(20, 0);
        ArrayList<MissionStep> steps = new ArrayList<>();

        String outcome = runMission(script, rover, steps);

        printReplay(steps);
        int[] counts = countCommands(steps);
        System.out.println("counts[0..3]=" + Arrays.toString(counts));
        printSummary(script, steps);
        // runMission 改的是同一個 Rover 物件，所以 main 用 getter 讀得到最終狀態。
        System.out.println("final energy=" + rover.getEnergy() + ", samples=" + rover.getSamples()
                + ", actionCount=" + rover.getActionCount() + " -> " + outcome);
    }

    // 與第四週相同：命令列參數逐一轉成 int。
    static int[] toScript(String[] args) {
        int[] script = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            script[i] = Integer.parseInt(args[i]);
        }
        return script;
    }

    // 依腳本逐步執行；任務已停止（成功或耗盡）就不再讀指令，也不記錄。
    static String runMission(int[] script, Rover rover, ArrayList<MissionStep> steps) {
        for (int command : script) {
            if (rover.isSuccess() || rover.isExhausted()) {
                break;
            }
            String result = rover.execute(command);
            // TODO 5：一步只新增一筆紀錄，不會再有三條清單對不齊的問題。
            steps.add(new MissionStep(command, result, rover.getEnergy()));
            if (command == 0) {
                break;
            }
        }
        // 判斷順序與第三、四週相同：同時耗盡與達標時成功優先。
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

    // TODO 6：每一步的文字交給 MissionStep.describe()，這裡只負責編號。
    static void printReplay(ArrayList<MissionStep> steps) {
        System.out.println("=== replay ===");
        for (int i = 0; i < steps.size(); i++) {
            System.out.println("step " + (i + 1) + ": " + steps.get(i).describe());
        }
    }

    // 第四週的計數方法，改成從每筆紀錄取出指令。
    static int[] countCommands(ArrayList<MissionStep> steps) {
        int[] counts = new int[4];
        for (MissionStep step : steps) {
            int command = step.getCommand();
            if (command >= 0 && command < counts.length) {
                counts[command]++;
            }
        }
        return counts;
    }

    // TODO 7：清單裡是 MissionStep，Collections.frequency／min 不能直接比結果或能源，改用迴圈。
    static void printSummary(int[] script, ArrayList<MissionStep> steps) {
        int ok = 0;
        int rejected = 0;
        int lowest = Rover.CAPACITY;
        for (MissionStep step : steps) {
            if (step.getResult().equals("OK")) {
                ok++;
            } else if (step.isRejected()) {
                rejected++;
            }
            if (step.getEnergyAfter() < lowest) {
                lowest = step.getEnergyAfter();
            }
        }
        int ignored = script.length - steps.size();
        System.out.println("steps=" + steps.size() + ", ok=" + ok
                + ", rejected=" + rejected + ", ignored=" + ignored);
        if (!steps.isEmpty()) {
            System.out.println("lowest energy=" + lowest);
        }
    }
}
