import java.util.ArrayList;
import java.util.Arrays;

// 教師檢查工具（claude 版本）：用陣列描述多筆案例，一次跑完並比對預期。
// 與 MissionLog.java 放在同一資料夾編譯；不是學生必寫作業，也不是 JUnit（第十四週才教）。
public class MissionLogCheck {
    public static void main(String[] args) {
        // 每列：初始 energy、初始 samples、腳本、預期 energy、samples、actionCount、結果、日誌步數
        int passed = 0;
        int total = 0;

        total++; passed += check(20, 0, new int[] {1, 1, 2}, 6, 5, 3, "SUCCESS", 3);
        total++; passed += check(20, 0, new int[] {2, 2, 2, 2, 2}, 10, 5, 5, "SUCCESS", 5);
        total++; passed += check(20, 0, new int[] {3, 9, 0}, 20, 0, 0, "RETURNED", 3);
        total++; passed += check(20, 0, new int[] {1, 1, 1, 1}, 2, 6, 3, "SUCCESS", 3);
        total++; passed += check(20, 0, new int[] {2, 1}, 12, 3, 2, "UNFINISHED", 2);
        total++; passed += check(20, 0, new int[] {0, 1, 1}, 20, 0, 0, "RETURNED", 1);
        total++; passed += check(20, 0, new int[] {}, 20, 0, 0, "UNFINISHED", 0);
        total++; passed += check(5, 0, new int[] {1, 3, 0}, 10, 0, 1, "RETURNED", 3);
        total++; passed += check(6, 0, new int[] {1, 2}, 0, 2, 1, "EXHAUSTED", 1);
        total++; passed += check(2, 4, new int[] {2}, 0, 5, 1, "SUCCESS", 1);
        total++; passed += check(1, 0, new int[] {2, 1, 3}, 6, 0, 1, "UNFINISHED", 3);

        // countCommands：無效指令 9 必須被略過而不是越界。
        ArrayList<Integer> log = new ArrayList<>(Arrays.asList(3, 9, 0, 1, 1));
        int[] counts = MissionLog.countCommands(log);
        total++;
        if (Arrays.equals(counts, new int[] {1, 2, 0, 1})) {
            passed++;
        } else {
            System.out.println("FAIL countCommands: " + Arrays.toString(counts));
        }

        System.out.println(passed + "/" + total + " passed");
    }

    static int check(int energy, int samples, int[] script, int expEnergy, int expSamples,
            int expActions, String expOutcome, int expSteps) {
        int[] state = {energy, samples, 0};
        ArrayList<Integer> commandLog = new ArrayList<>();
        ArrayList<Integer> energyLog = new ArrayList<>();
        ArrayList<String> resultLog = new ArrayList<>();
        String outcome = MissionLog.runMission(script, state, commandLog, energyLog, resultLog);

        boolean ok = state[0] == expEnergy && state[1] == expSamples && state[2] == expActions
                && outcome.equals(expOutcome) && commandLog.size() == expSteps
                && energyLog.size() == expSteps && resultLog.size() == expSteps;
        String label = energy + "/" + samples + " " + Arrays.toString(script);
        if (ok) {
            System.out.println("PASS " + label + " -> " + Arrays.toString(state) + " " + outcome);
            return 1;
        }
        System.out.println("FAIL " + label + " -> " + Arrays.toString(state) + " " + outcome
                + " steps=" + commandLog.size());
        return 0;
    }
}
