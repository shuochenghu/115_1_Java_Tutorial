import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// 第四週教師完整解答（claude 版本）
// 沿用第三週規則：容量 20；探索耗 6 得 2；掃描耗 2 得 1；充電 +5 封頂 20；樣本達 5 成功。
public class MissionLog {
    // state 陣列的位置。用名字取代 0、1、2，但「位置代表意義」本身仍容易出錯，是第五週改用類別的動機。
    static final int ENERGY = 0;
    static final int SAMPLES = 1;
    static final int ACTIONS = 2;

    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        if (args.length > 0) {
            script = toScript(args);
        }

        int[] state = {20, 0, 0};
        ArrayList<Integer> commandLog = new ArrayList<>();
        ArrayList<Integer> energyLog = new ArrayList<>();
        ArrayList<String> resultLog = new ArrayList<>();

        String outcome = runMission(script, state, commandLog, energyLog, resultLog);

        printReplay(commandLog, energyLog, resultLog);
        int[] counts = countCommands(commandLog);
        System.out.println("counts[0..3]=" + Arrays.toString(counts));
        printSummary(script, energyLog, resultLog);
        // runMission 改的是同一個陣列物件，所以 main 看得到最終狀態。
        System.out.println("final energy=" + state[ENERGY] + ", samples=" + state[SAMPLES]
                + ", actionCount=" + state[ACTIONS] + " -> " + outcome);
    }

    // TODO 1：命令列參數都是字串，逐一轉成 int 存入新陣列。
    static int[] toScript(String[] args) {
        int[] script = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            script[i] = Integer.parseInt(args[i]);
        }
        return script;
    }

    // 依腳本逐步執行，每一步（含被拒絕的）都寫入三條平行日誌。
    static String runMission(int[] script, int[] state, ArrayList<Integer> commandLog,
            ArrayList<Integer> energyLog, ArrayList<String> resultLog) {
        for (int command : script) {
            String result = applyCommand(state, command);
            // TODO 2：三條日誌必須同時新增，索引才會對齊。
            commandLog.add(command);
            energyLog.add(state[ENERGY]);
            resultLog.add(result);

            if (command == 0 || state[ENERGY] == 0 || state[SAMPLES] >= 5) {
                break;
            }
        }
        // 判斷順序與第三週相同：同時耗盡與達標時成功優先。
        if (state[SAMPLES] >= 5) {
            return "SUCCESS";
        } else if (state[ENERGY] == 0) {
            return "EXHAUSTED";
        } else if (!commandLog.isEmpty() && commandLog.get(commandLog.size() - 1) == 0) {
            return "RETURNED";
        } else {
            return "UNFINISHED";
        }
    }

    // 第三週的行動規則，改成修改 state 陣列並回傳結果字串。
    static String applyCommand(int[] state, int command) {
        switch (command) {
            case 1:
                if (state[ENERGY] < 6) {
                    return "NOT_ENOUGH_ENERGY";
                }
                state[ENERGY] -= 6;
                state[SAMPLES] += 2;
                state[ACTIONS]++;
                return "OK";
            case 2:
                if (state[ENERGY] < 2) {
                    return "NOT_ENOUGH_ENERGY";
                }
                state[ENERGY] -= 2;
                state[SAMPLES]++;
                state[ACTIONS]++;
                return "OK";
            case 3:
                if (state[ENERGY] >= 20) {
                    return "ALREADY_FULL";
                }
                state[ENERGY] += 5;
                if (state[ENERGY] > 20) {
                    state[ENERGY] = 20;
                }
                state[ACTIONS]++;
                return "OK";
            case 0:
                return "RETURN";
            default:
                return "UNKNOWN_COMMAND";
        }
    }

    // TODO 3：用同一個索引 i 讀三條日誌；size() 是最後索引 + 1。
    static void printReplay(ArrayList<Integer> commandLog, ArrayList<Integer> energyLog,
            ArrayList<String> resultLog) {
        System.out.println("=== replay ===");
        for (int i = 0; i < commandLog.size(); i++) {
            System.out.println("step " + (i + 1) + ": command=" + commandLog.get(i)
                    + ", result=" + resultLog.get(i) + ", energy=" + energyLog.get(i));
        }
    }

    // TODO 4：counts[指令] 計次；9 之類的無效指令不在 0..3，先檢查範圍才不會越界。
    static int[] countCommands(ArrayList<Integer> commandLog) {
        int[] counts = new int[4];
        for (int command : commandLog) {
            if (command >= 0 && command < counts.length) {
                counts[command]++;
            }
        }
        return counts;
    }

    // TODO 5：用 Collections 的現成方法整理摘要，不自己寫找最小值的迴圈。
    static void printSummary(int[] script, ArrayList<Integer> energyLog, ArrayList<String> resultLog) {
        int okSteps = Collections.frequency(resultLog, "OK");
        int returnSteps = Collections.frequency(resultLog, "RETURN");
        int rejected = resultLog.size() - okSteps - returnSteps;
        // 腳本在任務結束後剩下沒執行的指令。
        int ignored = script.length - resultLog.size();
        System.out.println("steps=" + resultLog.size() + ", ok=" + okSteps
                + ", rejected=" + rejected + ", ignored=" + ignored);
        if (!energyLog.isEmpty()) {
            System.out.println("lowest energy=" + Collections.min(energyLog));
        }
    }
}
