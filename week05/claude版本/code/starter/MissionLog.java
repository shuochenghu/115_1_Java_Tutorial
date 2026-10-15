import java.util.ArrayList;
import java.util.Arrays;

// 第五週學生起始碼（claude 版本）：流程與輸出沿用第四週，資料改成 Rover 與 ArrayList<MissionStep>。
// 與 Rover.java、MissionStep.java 放在同一個 src 資料夾；可直接編譯執行，完成 TODO 5–7 後輸出才會完整。
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
            // TODO 5：建立一筆 MissionStep（指令、結果、執行後的能源）並加入 steps。
            // 第四週要寫三行 add；現在一步只新增一筆紀錄。

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

    // TODO 6：依序印出 "step N: " 加上該筆紀錄的 describe()；N 從 1 開始。
    static void printReplay(ArrayList<MissionStep> steps) {
        System.out.println("=== replay ===");
    }

    // 已提供：第四週的計數方法，改成從每筆紀錄取出指令。
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
    // 需要的值：steps（紀錄筆數）、ok（結果為 "OK"）、rejected（用 isRejected()）、
    // ignored（腳本中沒被執行的指令數）、lowest energy（所有紀錄中最低的執行後能源；沒有紀錄時不印這一行）。
    // 輸出格式：steps=3, ok=3, rejected=0, ignored=0
    //          lowest energy=6
    static void printSummary(int[] script, ArrayList<MissionStep> steps) {
        System.out.println("TODO 7: summary");
    }
}
