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
        mission(20, 0, new int[] {1, 1, 2}, 6, 5, 3, "任務成功", 3);
        mission(20, 0, new int[] {2, 2, 2, 2, 2}, 10, 5, 5, "任務成功", 5);
        mission(20, 0, new int[] {3, 9, 0}, 20, 0, 0, "已返回", 3);
        mission(20, 0, new int[] {1, 1, 1, 1}, 2, 6, 3, "任務成功", 3);
        mission(20, 0, new int[] {2, 1}, 12, 3, 2, "任務未完成", 2);
        mission(20, 0, new int[] {0, 1, 1}, 20, 0, 0, "已返回", 1);
        mission(20, 0, new int[] {}, 20, 0, 0, "任務未完成", 0);
        mission(5, 0, new int[] {1, 3, 0}, 10, 0, 1, "已返回", 3);
        mission(6, 0, new int[] {1, 2}, 0, 2, 1, "能源耗盡", 1);
        mission(2, 4, new int[] {2}, 0, 5, 1, "任務成功", 1);       // 同時耗盡與達標：成功優先
        mission(1, 0, new int[] {2, 1, 3}, 6, 0, 1, "任務未完成", 3);
        mission(0, 0, new int[] {3, 2}, 0, 0, 0, "能源耗盡", 0);  // 一開始就耗盡：不讀不記
        mission(20, 5, new int[] {1}, 20, 5, 0, "任務成功", 0);     // 一開始就達標：不讀不記
        mission(25, -3, new int[] {3}, 20, 0, 0, "任務未完成", 1); // 建構子修正為 20/0，充電被拒

        // 每筆紀錄保存的是當下能源，不是最後的能源。
        Rover rover = new Rover(20, 0);
        ArrayList<MissionStep> steps = new ArrayList<>();
        MissionLog.runMission(new int[] {1, 1, 2}, rover, steps);
        int[] energies = new int[steps.size()];
        for (int i = 0; i < steps.size(); i++) {
            energies[i] = steps.get(i).getEnergyAfter();
        }
        expect("能源快照", Arrays.toString(energies), "[14, 8, 6]");

        MissionStep step = new MissionStep(9, "無效指令", 20);
        expect("describe", step.describe(), "指令=9, 結果=無效指令, 能源=20");
        expect("isRejected 判斷無效指令", "" + step.isRejected(), "true");
        expect("isRejected 判斷返回", "" + new MissionStep(0, "返回", 20).isRejected(), "false");
        expect("isRejected 判斷成功", "" + new MissionStep(1, "成功", 14).isRejected(), "false");
        // 內容相同但不是同一個字串物件：用 == 比較字串的寫法會在這裡失敗。
        expect("isRejected 比較不同物件的字串內容", "" + new MissionStep(1, new String("成功"), 14).isRejected(), "false");

        // printSummary 也用 new String 建立結果，並檢查最低能源不是 0 或容量。
        ArrayList<MissionStep> built = new ArrayList<>();
        built.add(new MissionStep(1, new String("成功"), 14));
        built.add(new MissionStep(3, new String("能源不足"), 3));
        built.add(new MissionStep(0, new String("返回"), 3));
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, java.nio.charset.StandardCharsets.UTF_8));
        MissionLog.printSummary(new int[4], built);
        System.setOut(original);
        expect("printSummary", normalize(buffer.toString(java.nio.charset.StandardCharsets.UTF_8)).trim().replace("\n", " | "),
                "步數=3, 成功=1, 拒絕=1, 略過=1 | 最低能源=3");

        // 五條必做測試的完整輸出（含回放、counts、摘要），與第四週 claude 版逐字相同。
        output(new String[] {}, "=== 任務回放 ===",
                "步驟 1: 指令=1, 結果=成功, 能源=14",
                "步驟 2: 指令=1, 結果=成功, 能源=8",
                "步驟 3: 指令=2, 結果=成功, 能源=6",
                "指令次數[0..3]=[0, 2, 1, 0]",
                "步數=3, 成功=3, 拒絕=0, 略過=0",
                "最低能源=6",
                "最終能源=6, 樣本數=5, 行動次數=3 -> 任務成功");
        output(new String[] {"2", "2", "2", "2", "2"}, "=== 任務回放 ===",
                "步驟 1: 指令=2, 結果=成功, 能源=18",
                "步驟 2: 指令=2, 結果=成功, 能源=16",
                "步驟 3: 指令=2, 結果=成功, 能源=14",
                "步驟 4: 指令=2, 結果=成功, 能源=12",
                "步驟 5: 指令=2, 結果=成功, 能源=10",
                "指令次數[0..3]=[0, 0, 5, 0]",
                "步數=5, 成功=5, 拒絕=0, 略過=0",
                "最低能源=10",
                "最終能源=10, 樣本數=5, 行動次數=5 -> 任務成功");
        output(new String[] {"3", "9", "0"}, "=== 任務回放 ===",
                "步驟 1: 指令=3, 結果=能源已滿, 能源=20",
                "步驟 2: 指令=9, 結果=無效指令, 能源=20",
                "步驟 3: 指令=0, 結果=返回, 能源=20",
                "指令次數[0..3]=[1, 0, 0, 1]",
                "步數=3, 成功=0, 拒絕=2, 略過=0",
                "最低能源=20",
                "最終能源=20, 樣本數=0, 行動次數=0 -> 已返回");
        output(new String[] {"1", "1", "1", "1"}, "=== 任務回放 ===",
                "步驟 1: 指令=1, 結果=成功, 能源=14",
                "步驟 2: 指令=1, 結果=成功, 能源=8",
                "步驟 3: 指令=1, 結果=成功, 能源=2",
                "指令次數[0..3]=[0, 3, 0, 0]",
                "步數=3, 成功=3, 拒絕=0, 略過=1",
                "最低能源=2",
                "最終能源=2, 樣本數=6, 行動次數=3 -> 任務成功");
        output(new String[] {"2", "1"}, "=== 任務回放 ===",
                "步驟 1: 指令=2, 結果=成功, 能源=18",
                "步驟 2: 指令=1, 結果=成功, 能源=12",
                "指令次數[0..3]=[0, 1, 1, 0]",
                "步數=2, 成功=2, 拒絕=0, 略過=0",
                "最低能源=12",
                "最終能源=12, 樣本數=3, 行動次數=2 -> 任務未完成");

        // countCommands：無效指令 9 必須被略過而不是越界。
        ArrayList<MissionStep> mixed = new ArrayList<>();
        int[] commands = {3, 9, 0, 1, 1};
        for (int command : commands) {
            mixed.add(new MissionStep(command, "X", 0));
        }
        expect("countCommands", Arrays.toString(MissionLog.countCommands(mixed)), "[1, 2, 0, 1]");

        System.out.println(passed + "/" + total + " 項通過");
    }

    static void mission(int energy, int samples, int[] script, int expEnergy, int expSamples,
            int expActions, String expOutcome, int expSteps) {
        Rover rover = new Rover(energy, samples);
        ArrayList<MissionStep> steps = new ArrayList<>();
        String outcome = MissionLog.runMission(script, rover, steps);
        String actual = rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount()
                + " " + outcome + " 步數=" + steps.size();
        String expected = expEnergy + "/" + expSamples + "/" + expActions
                + " " + expOutcome + " 步數=" + expSteps;
        expect("new Rover(" + energy + ", " + samples + "） " + Arrays.toString(script), actual, expected);
    }

    // 擷取 MissionLog.main 的完整輸出，逐行比對；失敗時印出第一個不同的行。
    static void output(String[] args, String... expectedLines) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, java.nio.charset.StandardCharsets.UTF_8));
        MissionLog.main(args);
        System.setOut(original);
        String[] actualLines = normalize(buffer.toString(java.nio.charset.StandardCharsets.UTF_8)).split("\n");
        String label = "輸出 " + Arrays.toString(args);
        total++;
        for (int i = 0; i < Math.max(actualLines.length, expectedLines.length); i++) {
            String actual = i < actualLines.length ? actualLines[i] : "（缺少此行）";
            String expected = i < expectedLines.length ? expectedLines[i] : "（缺少此行）";
            if (!actual.equals(expected)) {
                System.out.println("失敗 " + label + " 第 " + (i + 1) + " -> " + actual
                        + " （預期 " + expected + "）");
                return;
            }
        }
        passed++;
        System.out.println("通過 " + label + " -> " + expectedLines.length + " 行");
    }

    static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }

    static void expect(String label, String actual, String expected) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println("通過 " + label + " -> " + actual);
        } else {
            System.out.println("失敗 " + label + " -> " + actual + " （預期 " + expected + "）");
        }
    }
}
