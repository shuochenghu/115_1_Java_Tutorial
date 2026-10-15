import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

// 教師檢查工具（claude 版本）：一次跑完多筆案例並比對預期。
// 與教師版 Rover、MissionStep、MissionLog 放在同一資料夾編譯；不是學生必寫作業，也不是 JUnit（第十四週才教）。
// 批改時也可只搭配學生的 Rover、MissionStep、MissionLog 三個檔案編譯（不要再加入教師版同名類別）。
public class MissionLogCheck {
    static int passed = 0;
    static int total = 0;

    public static void main(String[] args) {
        // 任務案例：初始 energy、samples、腳本、預期 energy、samples、actionCount、結果、紀錄筆數
        mission(20, 0, new int[] {1, 1, 2}, 6, 5, 3, "SUCCESS", 3);
        mission(20, 0, new int[] {2, 2, 2, 2, 2}, 10, 5, 5, "SUCCESS", 5);
        mission(20, 0, new int[] {3, 9, 0}, 20, 0, 0, "RETURNED", 3);
        mission(20, 0, new int[] {1, 1, 1, 1}, 2, 6, 3, "SUCCESS", 3);
        mission(20, 0, new int[] {2, 1}, 12, 3, 2, "UNFINISHED", 2);
        mission(20, 0, new int[] {0, 1, 1}, 20, 0, 0, "RETURNED", 1);
        mission(20, 0, new int[] {}, 20, 0, 0, "UNFINISHED", 0);
        mission(5, 0, new int[] {1, 3, 0}, 10, 0, 1, "RETURNED", 3);
        mission(6, 0, new int[] {1, 2}, 0, 2, 1, "EXHAUSTED", 1);
        mission(2, 4, new int[] {2}, 0, 5, 1, "SUCCESS", 1);       // 同時耗盡與達標：成功優先
        mission(1, 0, new int[] {2, 1, 3}, 6, 0, 1, "UNFINISHED", 3);
        mission(0, 0, new int[] {3, 2}, 0, 0, 0, "EXHAUSTED", 0);  // 一開始就耗盡：不讀不記
        mission(20, 5, new int[] {1}, 20, 5, 0, "SUCCESS", 0);     // 一開始就達標：不讀不記
        mission(25, -3, new int[] {3}, 20, 0, 0, "UNFINISHED", 1); // 建構子修正為 20/0，充電被拒

        // 每筆紀錄保存的是當下能源，不是最後的能源。
        Rover rover = new Rover(20, 0);
        ArrayList<MissionStep> steps = new ArrayList<>();
        MissionLog.runMission(new int[] {1, 1, 2}, rover, steps);
        int[] energies = new int[steps.size()];
        for (int i = 0; i < steps.size(); i++) {
            energies[i] = steps.get(i).getEnergyAfter();
        }
        expect("energy snapshots", Arrays.toString(energies), "[14, 8, 6]");

        MissionStep step = new MissionStep(9, "UNKNOWN_COMMAND", 20);
        expect("describe", step.describe(), "command=9, result=UNKNOWN_COMMAND, energy=20");
        expect("isRejected UNKNOWN", "" + step.isRejected(), "true");
        expect("isRejected RETURN", "" + new MissionStep(0, "RETURN", 20).isRejected(), "false");
        expect("isRejected OK", "" + new MissionStep(1, "OK", 14).isRejected(), "false");
        // 內容相同但不是同一個字串物件：用 == 比較字串的寫法會在這裡失敗。
        expect("isRejected new String", "" + new MissionStep(1, new String("OK"), 14).isRejected(), "false");

        // printSummary 也用 new String 建立結果，並檢查最低能源不是 0 或容量。
        ArrayList<MissionStep> built = new ArrayList<>();
        built.add(new MissionStep(1, new String("OK"), 14));
        built.add(new MissionStep(3, new String("NOT_ENOUGH_ENERGY"), 3));
        built.add(new MissionStep(0, new String("RETURN"), 3));
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true));
        MissionLog.printSummary(new int[4], built);
        System.setOut(original);
        expect("printSummary", normalize(buffer.toString()).trim().replace("\n", " | "),
                "steps=3, ok=1, rejected=1, ignored=1 | lowest energy=3");

        // 五條必做測試的完整輸出（含回放、counts、摘要），與第四週 claude 版逐字相同。
        output(new String[] {}, "=== replay ===",
                "step 1: command=1, result=OK, energy=14",
                "step 2: command=1, result=OK, energy=8",
                "step 3: command=2, result=OK, energy=6",
                "counts[0..3]=[0, 2, 1, 0]",
                "steps=3, ok=3, rejected=0, ignored=0",
                "lowest energy=6",
                "final energy=6, samples=5, actionCount=3 -> SUCCESS");
        output(new String[] {"2", "2", "2", "2", "2"}, "=== replay ===",
                "step 1: command=2, result=OK, energy=18",
                "step 2: command=2, result=OK, energy=16",
                "step 3: command=2, result=OK, energy=14",
                "step 4: command=2, result=OK, energy=12",
                "step 5: command=2, result=OK, energy=10",
                "counts[0..3]=[0, 0, 5, 0]",
                "steps=5, ok=5, rejected=0, ignored=0",
                "lowest energy=10",
                "final energy=10, samples=5, actionCount=5 -> SUCCESS");
        output(new String[] {"3", "9", "0"}, "=== replay ===",
                "step 1: command=3, result=ALREADY_FULL, energy=20",
                "step 2: command=9, result=UNKNOWN_COMMAND, energy=20",
                "step 3: command=0, result=RETURN, energy=20",
                "counts[0..3]=[1, 0, 0, 1]",
                "steps=3, ok=0, rejected=2, ignored=0",
                "lowest energy=20",
                "final energy=20, samples=0, actionCount=0 -> RETURNED");
        output(new String[] {"1", "1", "1", "1"}, "=== replay ===",
                "step 1: command=1, result=OK, energy=14",
                "step 2: command=1, result=OK, energy=8",
                "step 3: command=1, result=OK, energy=2",
                "counts[0..3]=[0, 3, 0, 0]",
                "steps=3, ok=3, rejected=0, ignored=1",
                "lowest energy=2",
                "final energy=2, samples=6, actionCount=3 -> SUCCESS");
        output(new String[] {"2", "1"}, "=== replay ===",
                "step 1: command=2, result=OK, energy=18",
                "step 2: command=1, result=OK, energy=12",
                "counts[0..3]=[0, 1, 1, 0]",
                "steps=2, ok=2, rejected=0, ignored=0",
                "lowest energy=12",
                "final energy=12, samples=3, actionCount=2 -> UNFINISHED");

        // countCommands：無效指令 9 必須被略過而不是越界。
        ArrayList<MissionStep> mixed = new ArrayList<>();
        int[] commands = {3, 9, 0, 1, 1};
        for (int command : commands) {
            mixed.add(new MissionStep(command, "X", 0));
        }
        expect("countCommands", Arrays.toString(MissionLog.countCommands(mixed)), "[1, 2, 0, 1]");

        System.out.println(passed + "/" + total + " passed");
    }

    static void mission(int energy, int samples, int[] script, int expEnergy, int expSamples,
            int expActions, String expOutcome, int expSteps) {
        Rover rover = new Rover(energy, samples);
        ArrayList<MissionStep> steps = new ArrayList<>();
        String outcome = MissionLog.runMission(script, rover, steps);
        String actual = rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount()
                + " " + outcome + " steps=" + steps.size();
        String expected = expEnergy + "/" + expSamples + "/" + expActions
                + " " + expOutcome + " steps=" + expSteps;
        expect("new Rover(" + energy + ", " + samples + ") " + Arrays.toString(script), actual, expected);
    }

    // 擷取 MissionLog.main 的完整輸出，逐行比對；失敗時印出第一個不同的行。
    static void output(String[] args, String... expectedLines) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true));
        MissionLog.main(args);
        System.setOut(original);
        String[] actualLines = normalize(buffer.toString()).split("\n");
        String label = "output " + Arrays.toString(args);
        total++;
        for (int i = 0; i < Math.max(actualLines.length, expectedLines.length); i++) {
            String actual = i < actualLines.length ? actualLines[i] : "(no line)";
            String expected = i < expectedLines.length ? expectedLines[i] : "(no line)";
            if (!actual.equals(expected)) {
                System.out.println("FAIL " + label + " line " + (i + 1) + " -> " + actual
                        + " (expect " + expected + ")");
                return;
            }
        }
        passed++;
        System.out.println("PASS " + label + " -> " + expectedLines.length + " lines");
    }

    static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }

    static void expect(String label, String actual, String expected) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println("PASS " + label + " -> " + actual);
        } else {
            System.out.println("FAIL " + label + " -> " + actual + " (expect " + expected + ")");
        }
    }
}
