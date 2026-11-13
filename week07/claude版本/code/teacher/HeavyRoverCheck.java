import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

// 第七週教師檢查工具（claude 版本）：任務回歸（與第六週 MissionCheck 相同的案例）、HeavyRover 規格與繼承設計檢查。
// 與 Rover、HeavyRover、MissionStep、Mission、MissionLog 放在同一資料夾編譯；不是學生必寫作業，也不是 JUnit（第十四週才教）。
// 批改時可只搭配學生的五個類別編譯（不要再加入教師版同名類別）。
// 本工具直接呼叫 Rover 的 protected 方法：Java 的 protected 也開放給同一套件，本課程的類別都在預設套件。
// 「設計」檢查用到反射（reflection）讀取修飾字，這是教師工具的寫法，學生不必學。
public class HeavyRoverCheck {
    static int passed = 0;
    static int total = 0;

    public static void main(String[] args) {
        // 任務案例與第五、六週相同：初始 energy、samples、腳本、預期 energy、samples、actionCount、結果、紀錄筆數
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

        // 停止規則收回 Rover：不經過 Mission 直接下指令也擋得住，狀態不變。
        Rover tired = new Rover(0, 0);
        expect("tired.execute(3)", tired.execute(3) + " " + state(tired), "任務已停止 0/0/0");
        Rover done = new Rover(20, 5);
        expect("done.execute(1)", done.execute(1) + " " + state(done), "任務已停止 20/5/0");
        Rover drained = new Rover(6, 0);
        expect("drained.execute(1), execute(3)",
                drained.execute(1) + " " + drained.execute(3) + " " + state(drained), "成功 任務已停止 0/2/1");
        expect("isStopped", new Rover(20, 0).isStopped() + " " + new Rover(0, 0).isStopped() + " "
                + new Rover(3, 5).isStopped(), "false true true");

        // Mission 的計算方法。
        Mission empty = new Mission(new Rover(20, 0));
        expect("空任務", empty.getStepCount() + " " + empty.countOk() + " " + empty.countRejected()
                + " " + empty.lowestEnergy() + " " + Arrays.toString(empty.countCommands()) + " " + empty.getOutcome(),
                "0 0 0 -1 [0, 0, 0, 0] 任務未完成");
        Mission mixed = new Mission(new Rover(20, 0));
        mixed.run(new int[] {3, 9, 2, 1, 3, 0});
        expect("混合指令的任務", mixed.getStepCount() + " 成功=" + mixed.countOk() + " 拒絕=" + mixed.countRejected()
                + " 最低能源=" + mixed.lowestEnergy() + " " + Arrays.toString(mixed.countCommands()) + " "
                + mixed.getOutcome(), "6 成功=3 拒絕=2 最低能源=12 [1, 1, 1, 2] 已返回");
        // 每筆紀錄保存的是當下能源。
        Mission snap = new Mission(new Rover(20, 0));
        snap.run(new int[] {1, 1, 2});
        String energies = "";
        for (int i = 0; i < snap.getStepCount(); i++) {
            energies += (i == 0 ? "" : ",") + snap.getStep(i).getEnergyAfter();
        }
        expect("能源快照", energies, "14,8,6");
        // 最低能源出現在中間，不在第一筆也不在最後一筆。
        Mission dip = new Mission(new Rover(8, 0));
        dip.run(new int[] {1, 3, 3});
        expect("最低能源出現在途中", dip.lowestEnergy() + " " + dip.getOutcome(), "2 任務未完成");

        // 五條必做測試的完整輸出（含回放、counts、摘要），與第五、六週 claude 版逐字相同。
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

        // TODO 1：三個 protected 方法。不合理的數量不改變狀態，能源不會變成負數。
        Rover tool = new Rover(5, 0);
        expect("spendEnergy(6), (0), (-3)", tool.spendEnergy(6) + " " + tool.spendEnergy(0) + " "
                + tool.spendEnergy(-3) + " " + state(tool), "false false false 5/0/0");
        expect("spendEnergy(5)", tool.spendEnergy(5) + " " + state(tool), "true 0/0/0");
        tool.addSamples(-2);
        tool.addSamples(0);
        tool.addSamples(3);
        tool.countAction();
        expect("addSamples(-2, 0, 3), countAction()", state(tool), "0/3/1");

        // TODO 3–4：HeavyRover，包含 HeavyRoverTry 的案例，另加已達標、連續鑽探與混用繼承來的行動。
        HeavyRover big = new HeavyRover(25, -1);
        expect("new HeavyRover(25, -1)", heavy(big), "20/0/0 鑽探次數=0");
        HeavyRover h = new HeavyRover(20, 0);
        expect("drill, execute(1), drill", h.drill() + " " + h.execute(1) + " " + h.drill() + " " + heavy(h),
                "成功 成功 任務已停止 6/5/2 鑽探次數=1");
        HeavyRover low = new HeavyRover(7, 0);
        expect("能源不足時嘗試鑽探", low.drill() + " " + low.execute(1) + " " + heavy(low),
                "能源不足 成功 1/2/1 鑽探次數=0");
        HeavyRover exact = new HeavyRover(8, 0);
        expect("鑽探後能源恰為零", exact.drill() + " " + exact.isExhausted() + " " + exact.execute(3) + " " + heavy(exact),
                "成功 true 任務已停止 0/3/1 鑽探次數=1");
        HeavyRover flat = new HeavyRover(0, 0);
        expect("能源耗盡後嘗試鑽探", flat.drill() + " " + heavy(flat), "任務已停止 0/0/0 鑽探次數=0");
        HeavyRover full = new HeavyRover(20, 5);
        expect("已達標後嘗試鑽探", full.drill() + " " + heavy(full), "任務已停止 20/5/0 鑽探次數=0");
        HeavyRover twice = new HeavyRover(20, 0);
        expect("鑽探兩次", twice.drill() + " " + twice.drill() + " " + heavy(twice), "成功 成功 4/6/2 鑽探次數=2");
        HeavyRover combo = new HeavyRover(20, 0);
        expect("繼承的行動", combo.execute(2) + " " + combo.execute(3) + " " + combo.execute(9) + " "
                + combo.drill() + " " + heavy(combo), "成功 成功 無效指令 成功 12/4/3 鑽探次數=1");

        // 設計：繼承關係、可見性，以及子類別沒有另外宣告一份狀態。
        expect("設計檢查：HeavyRover 繼承 Rover", HeavyRover.class.getSuperclass().getSimpleName(), "Rover");
        expect("設計檢查：欄位為 private", nonPrivateFields(Rover.class, HeavyRover.class, MissionStep.class,
                Mission.class), "（無）");
        expect("設計檢查：HeavyRover 無重複欄位", hiddenFields(HeavyRover.class, Rover.class), "（無）");
        expect("設計檢查：輔助方法為 protected", visibility(Rover.class, "spendEnergy") + " "
                + visibility(Rover.class, "addSamples") + " " + visibility(Rover.class, "countAction"),
                "protected protected protected");
        expect("設計檢查：行動方法為 private", visibility(Rover.class, "explore") + " " + visibility(Rover.class, "scan")
                + " " + visibility(Rover.class, "charge"), "private private private");
        expect("設計檢查：HeavyRover 的公開方法", publicMethods(HeavyRover.class), "drill getDrillCount");

        System.out.println(passed + "/" + total + " 項通過");
    }

    static String heavy(HeavyRover rover) {
        return state(rover) + " 鑽探次數=" + rover.getDrillCount();
    }

    static String state(Rover rover) {
        return rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount();
    }

    static void mission(int energy, int samples, int[] script, int expEnergy, int expSamples,
            int expActions, String expOutcome, int expSteps) {
        Rover rover = new Rover(energy, samples);
        Mission mission = new Mission(rover);
        mission.run(script);
        String actual = state(rover) + " " + mission.getOutcome() + " 步數=" + mission.getStepCount();
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
        String[] actualLines = buffer.toString(java.nio.charset.StandardCharsets.UTF_8).replace("\r\n", "\n").split("\n");
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

    // 列出不是 private 的實例欄位；static final 常數（如 CAPACITY）不算。
    static String nonPrivateFields(Class<?>... types) {
        String found = "";
        for (Class<?> type : types) {
            for (Field field : type.getDeclaredFields()) {
                int mod = field.getModifiers();
                boolean constant = Modifier.isStatic(mod) && Modifier.isFinal(mod);
                if (!constant && !Modifier.isPrivate(mod)) {
                    found += type.getSimpleName() + "." + field.getName() + " ";
                }
            }
        }
        return found.isEmpty() ? "（無）" : found.trim();
    }

    // 子類別宣告了和父類別同名的欄位：會遮蔽（hide）父類別的欄位，兩份狀態各改各的。
    static String hiddenFields(Class<?> child, Class<?> parent) {
        String found = "";
        for (Field field : child.getDeclaredFields()) {
            for (Field inherited : parent.getDeclaredFields()) {
                if (field.getName().equals(inherited.getName())) {
                    found += field.getName() + " ";
                }
            }
        }
        return found.isEmpty() ? "（無）" : found.trim();
    }

    static String visibility(Class<?> type, String name) {
        for (Method method : type.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                int mod = method.getModifiers();
                if (Modifier.isPrivate(mod)) {
                    return "private";
                } else if (Modifier.isProtected(mod)) {
                    return "protected";
                } else if (Modifier.isPublic(mod)) {
                    return "public";
                } else {
                    return "package";
                }
            }
        }
        return "缺少";
    }

    // 依名稱排序、只列這個類別自己宣告的 public 方法（繼承來的不算）。
    static String publicMethods(Class<?> type) {
        ArrayList<String> names = new ArrayList<>();
        for (Method method : type.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                names.add(method.getName());
            }
        }
        names.sort(null);
        return String.join(" ", names);
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
