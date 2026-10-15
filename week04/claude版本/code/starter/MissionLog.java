import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// 第四週學生起始碼（claude 版本）：可直接編譯執行，完成 TODO 1–5 後輸出才會完整。
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
        // 提示：先 new 一個和 args 一樣長的 int 陣列，再用 Integer.parseInt 逐格轉換。
        // 未完成前回傳空陣列，所以帶參數執行時任務會是 UNFINISHED。
        return new int[0];
    }

    // 依腳本逐步執行，每一步（含被拒絕的）都寫入三條平行日誌。
    static String runMission(int[] script, int[] state, ArrayList<Integer> commandLog,
            ArrayList<Integer> energyLog, ArrayList<String> resultLog) {
        for (int command : script) {
            String result = applyCommand(state, command);
            // TODO 2：把 command、執行後的 state[ENERGY]、result 分別加入三條日誌。
            // 三條日誌必須同時新增，索引才會對齊。

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
        // 輸出格式：step 1: command=1, result=OK, energy=14

    }

    // TODO 4：counts[指令] 計次；9 之類的無效指令不在 0..3，先檢查範圍才不會越界。
    static int[] countCommands(ArrayList<Integer> commandLog) {
        int[] counts = new int[4];
        // 提示：用加強版 for 走訪 commandLog，指令本身就是 counts 的索引。
        return counts;
    }

    // TODO 5：用 Collections 的現成方法整理摘要，不自己寫找最小值的迴圈。
    static void printSummary(int[] script, ArrayList<Integer> energyLog, ArrayList<String> resultLog) {
        // 需要的值：steps（日誌筆數）、ok（結果為 "OK" 的筆數）、
        // rejected（不是 OK 也不是 RETURN 的筆數）、ignored（腳本中沒被執行的指令數）、
        // lowest energy（energyLog 的最小值，日誌為空時不印）。
        // 輸出格式：steps=3, ok=3, rejected=0, ignored=0
        //          lowest energy=6
        System.out.println("TODO 5: summary");
    }
}
