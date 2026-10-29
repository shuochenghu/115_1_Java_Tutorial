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

        // 停止規則收回 Rover：不經過 Mission 直接下指令也擋得住，狀態不變。
        Rover tired = new Rover(0, 0);
        expect("tired.execute(3)", tired.execute(3) + " " + state(tired), "STOPPED 0/0/0");
        Rover done = new Rover(20, 5);
        expect("done.execute(1)", done.execute(1) + " " + state(done), "STOPPED 20/5/0");
        Rover drained = new Rover(6, 0);
        expect("drained.execute(1), execute(3)",
                drained.execute(1) + " " + drained.execute(3) + " " + state(drained), "OK STOPPED 0/2/1");
        expect("isStopped", new Rover(20, 0).isStopped() + " " + new Rover(0, 0).isStopped() + " "
                + new Rover(3, 5).isStopped(), "false true true");

        // Mission 的計算方法。
        Mission empty = new Mission(new Rover(20, 0));
        expect("empty mission", empty.getStepCount() + " " + empty.countOk() + " " + empty.countRejected()
                + " " + empty.lowestEnergy() + " " + Arrays.toString(empty.countCommands()) + " " + empty.getOutcome(),
                "0 0 0 -1 [0, 0, 0, 0] UNFINISHED");
        Mission mixed = new Mission(new Rover(20, 0));
        mixed.run(new int[] {3, 9, 2, 1, 3, 0});
        expect("mixed mission", mixed.getStepCount() + " ok=" + mixed.countOk() + " rejected=" + mixed.countRejected()
                + " lowest=" + mixed.lowestEnergy() + " " + Arrays.toString(mixed.countCommands()) + " "
                + mixed.getOutcome(), "6 ok=3 rejected=2 lowest=12 [1, 1, 1, 2] RETURNED");
        // 每筆紀錄保存的是當下能源。
        Mission snap = new Mission(new Rover(20, 0));
        snap.run(new int[] {1, 1, 2});
        String energies = "";
        for (int i = 0; i < snap.getStepCount(); i++) {
            energies += (i == 0 ? "" : ",") + snap.getStep(i).getEnergyAfter();
        }
        expect("energy snapshots", energies, "14,8,6");
        // 最低能源出現在中間，不在第一筆也不在最後一筆。
        Mission dip = new Mission(new Rover(8, 0));
        dip.run(new int[] {1, 3, 3});
        expect("lowest in the middle", dip.lowestEnergy() + " " + dip.getOutcome(), "2 UNFINISHED");

        // 五條必做測試的完整輸出（含回放、counts、摘要），與第五、六週 claude 版逐字相同。
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
        expect("new HeavyRover(25, -1)", heavy(big), "20/0/0 drill=0");
        HeavyRover h = new HeavyRover(20, 0);
        expect("drill, execute(1), drill", h.drill() + " " + h.execute(1) + " " + h.drill() + " " + heavy(h),
                "OK OK STOPPED 6/5/2 drill=1");
        HeavyRover low = new HeavyRover(7, 0);
        expect("low energy drill", low.drill() + " " + low.execute(1) + " " + heavy(low),
                "NOT_ENOUGH_ENERGY OK 1/2/1 drill=0");
        HeavyRover exact = new HeavyRover(8, 0);
        expect("drill to zero", exact.drill() + " " + exact.isExhausted() + " " + exact.execute(3) + " " + heavy(exact),
                "OK true STOPPED 0/3/1 drill=1");
        HeavyRover flat = new HeavyRover(0, 0);
        expect("drill when exhausted", flat.drill() + " " + heavy(flat), "STOPPED 0/0/0 drill=0");
        HeavyRover full = new HeavyRover(20, 5);
        expect("drill when already successful", full.drill() + " " + heavy(full), "STOPPED 20/5/0 drill=0");
        HeavyRover twice = new HeavyRover(20, 0);
        expect("drill twice", twice.drill() + " " + twice.drill() + " " + heavy(twice), "OK OK 4/6/2 drill=2");
        HeavyRover combo = new HeavyRover(20, 0);
        expect("inherited actions", combo.execute(2) + " " + combo.execute(3) + " " + combo.execute(9) + " "
                + combo.drill() + " " + heavy(combo), "OK OK UNKNOWN_COMMAND OK 12/4/3 drill=1");

        // 設計：繼承關係、可見性，以及子類別沒有另外宣告一份狀態。
        expect("design: HeavyRover extends Rover", HeavyRover.class.getSuperclass().getSimpleName(), "Rover");
        expect("design: fields private", nonPrivateFields(Rover.class, HeavyRover.class, MissionStep.class,
                Mission.class), "(none)");
        expect("design: no hidden copies in HeavyRover", hiddenFields(HeavyRover.class, Rover.class), "(none)");
        expect("design: helpers protected", visibility(Rover.class, "spendEnergy") + " "
                + visibility(Rover.class, "addSamples") + " " + visibility(Rover.class, "countAction"),
                "protected protected protected");
        expect("design: actions private", visibility(Rover.class, "explore") + " " + visibility(Rover.class, "scan")
                + " " + visibility(Rover.class, "charge"), "private private private");
        expect("design: HeavyRover public methods", publicMethods(HeavyRover.class), "drill getDrillCount");

        System.out.println(passed + "/" + total + " passed");
    }

    static String heavy(HeavyRover rover) {
        return state(rover) + " drill=" + rover.getDrillCount();
    }

    static String state(Rover rover) {
        return rover.getEnergy() + "/" + rover.getSamples() + "/" + rover.getActionCount();
    }

    static void mission(int energy, int samples, int[] script, int expEnergy, int expSamples,
            int expActions, String expOutcome, int expSteps) {
        Rover rover = new Rover(energy, samples);
        Mission mission = new Mission(rover);
        mission.run(script);
        String actual = state(rover) + " " + mission.getOutcome() + " steps=" + mission.getStepCount();
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
        String[] actualLines = buffer.toString().replace("\r\n", "\n").split("\n");
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
        return found.isEmpty() ? "(none)" : found.trim();
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
        return found.isEmpty() ? "(none)" : found.trim();
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
        return "missing";
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
            System.out.println("PASS " + label + " -> " + actual);
        } else {
            System.out.println("FAIL " + label + " -> " + actual + " (expect " + expected + ")");
        }
    }
}
