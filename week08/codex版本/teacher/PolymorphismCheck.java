import java.lang.reflect.Modifier;
import java.util.Arrays;

// 類別：PolymorphismCheck（教師驗證工具）
// 意義：檢查第 8 週的覆寫與多型是否符合規格。
// 作用：用固定案例檢查一般車與重型車的產量、任務結果、邊界案例，
//       並用反射確認產量方法的可見性、是實例方法、子類別沒有重複宣告父類別狀態。
//       反射與 AssertionError 不列學生先備，也不放進學生 src。
public class PolymorphismCheck {
    // passed：目前通過的檢查數。
    private static int passed;

    // main：程式進入點。依序執行所有檢查，任何一項失敗就停止。
    public static void main(String[] args) throws Exception {
        Rover normal = new Rover(20, 0);
        Rover heavy = new HeavyRover(20, 0);
        check("一般探測車執行指令", "成功".equals(normal.execute(1)));
        state("一般探測車的產量", normal, 14, 2, 1);
        check("重型探測車執行指令", "成功".equals(heavy.execute(1)));
        state("透過 Rover 參考取得重型探測車的產量", heavy, 14, 3, 1);
        check("一般探測車的物件狀態獨立", normal.getSamples() == 2);

        Mission n = new Mission(new Rover(20, 0));
        n.run(new int[] {1, 1, 2});
        check("一般探測車的任務", n.getStepCount() == 3 && "任務成功".equals(n.getOutcome()));
        Rover h = new HeavyRover(20, 0);
        Mission m = new Mission(h);
        m.run(new int[] {1, 1, 2});
        state("重型探測車的任務狀態", h, 8, 6, 2);
        check("掃描前已停止", m.getStepCount() == 2 && "任務成功".equals(m.getOutcome()));
        check("重型探測車的指令次數", Arrays.equals(m.countCommands(), new int[] {0, 2, 0, 0}));
        check("重型探測車的摘要", m.countOk() == 2 && m.countRejected() == 0);
        check("快照的能源", m.getStep(0).getEnergy() == 14 && m.getStep(1).getEnergy() == 8);
        check("最低能源", m.lowestEnergy() == 8);
        int[] counts = m.countCommands();
        counts[1] = 99;
        check("指令次數的副本", m.countCommands()[1] == 2);

        Rover exactNormal = new Rover(6, 2);
        Mission en = new Mission(exactNormal);
        en.run(new int[] {1, 3});
        state("一般探測車能源剛好足夠", exactNormal, 0, 4, 1);
        check("一般探測車能源耗盡", "能源耗盡".equals(en.getOutcome()) && en.getStepCount() == 1);
        Rover exactHeavy = new HeavyRover(6, 2);
        Mission eh = new Mission(exactHeavy);
        eh.run(new int[] {1, 3});
        state("重型探測車能源剛好足夠", exactHeavy, 0, 5, 1);
        check("達標優先於能源耗盡", "任務成功".equals(eh.getOutcome()) && eh.getStepCount() == 1);

        Probe low = new Probe(5, 0);
        check("能源不足時拒絕行動", "能源不足".equals(low.execute(1)));
        state("能源不足時維持狀態", low, 5, 0, 0);
        check("遭拒時不查詢產量", low.calls == 0);
        Probe ready = new Probe(6, 0);
        ready.execute(1);
        state("呼叫子類別覆寫的產量方法", ready, 0, 4, 1);
        check("只查詢一次產量", ready.calls == 1);
        check("能源耗盡後無法充電", "任務已停止".equals(ready.execute(3)));
        state("能源為零時維持狀態", ready, 0, 4, 1);
        Probe done = new Probe(20, 5);
        check("初始已達標便停止", "任務已停止".equals(done.execute(1)) && done.calls == 0);
        state("初始已達標時維持狀態", done, 20, 5, 0);
        Rover zero = new HeavyRover(0, 0);
        Mission z = new Mission(zero);
        z.run(new int[] {3, 1});
        check("初始能源為零時不讀指令", z.getStepCount() == 0 && z.lowestEnergy() == -1);
        check("初始能源為零時的任務結果", "能源耗盡".equals(z.getOutcome()));

        Rover scan = new HeavyRover(20, 0);
        check("掃描結果維持原規則", "成功".equals(scan.execute(2)));
        state("掃描後的狀態維持原規則", scan, 18, 1, 1);
        check("充電上限", "成功".equals(scan.execute(3)));
        state("充電量達上限", scan, 20, 1, 2);
        check("已充滿時拒絕充電", "能源已滿".equals(scan.execute(3)));
        state("已充滿時維持狀態", scan, 20, 1, 2);
        Mission returned = new Mission(new HeavyRover(20, 0));
        returned.run(new int[] {3, 9, 0, 1});
        check("返回後停止", "已返回".equals(returned.getOutcome()) && returned.getStepCount() == 3);
        check("返回不列入拒絕", returned.countRejected() == 2 && returned.countOk() == 0);
        check("返回指令的計次", Arrays.equals(returned.countCommands(), new int[] {1, 0, 0, 1}));
        Mission empty = new Mission(new HeavyRover(20, 0));
        empty.run(new int[] {});
        check("空任務", empty.getStepCount() == 0 && empty.lowestEnergy() == -1
                && "任務未完成".equals(empty.getOutcome()));

        HeavyRover drill = new HeavyRover(20, 0);
        check("鑽探遭拒後維持狀態", "成功".equals(drill.drill()));
        state("鑽探後", drill, 12, 3, 1);
        drill.execute(1);
        state("鑽探後再探索", drill, 6, 6, 2);
        check("達標後停止鑽探", "任務已停止".equals(drill.drill()) && drill.getDrillCount() == 1);
        state("任務停止後鑽探維持狀態", drill, 6, 6, 2);
        HeavyRover exactDrill = new HeavyRover(8, 0);
        check("能源剛好足夠時鑽探", "成功".equals(exactDrill.drill()));
        state("能源剛好足夠並鑽探後的狀態", exactDrill, 0, 3, 1);

        check("父類別的私有狀態", Modifier.isPrivate(Rover.class.getDeclaredField("energy").getModifiers())
                && Modifier.isPrivate(Rover.class.getDeclaredField("samples").getModifiers())
                && Modifier.isPrivate(Rover.class.getDeclaredField("actionCount").getModifiers()));
        check("explore 維持 private", Modifier.isPrivate(Rover.class.getDeclaredMethod("explore").getModifiers()));
        check("父類別的產量方法為 protected", Modifier.isProtected(Rover.class.getDeclaredMethod("exploreYield").getModifiers()));
        check("子類別的產量方法為 protected", Modifier.isProtected(HeavyRover.class.getDeclaredMethod("exploreYield").getModifiers()));
        check("產量方法為實例方法", !Modifier.isStatic(HeavyRover.class.getDeclaredMethod("exploreYield").getModifiers()));
        boolean repeated = false;
        for (java.lang.reflect.Field field : HeavyRover.class.getDeclaredFields()) {
            if (Arrays.asList("energy", "samples", "actionCount").contains(field.getName())) { repeated = true; }
        }
        check("無重複的父類別狀態", !repeated);
        System.out.println("PolymorphismCheck " + passed + " 項通過");
    }

    // state：檢查一台車的能源、樣本、行動次數是否都等於預期。
    private static void state(String label, Rover rover, int energy, int samples, int actions) {
        check(label, rover.getEnergy() == energy && rover.getSamples() == samples
                && rover.getActionCount() == actions);
    }

    // check：result 為 false 就丟出錯誤；為 true 就累加 passed 並印出「通過」。
    private static void check(String label, boolean result) {
        if (!result) { throw new AssertionError(label); }
        passed++;
        System.out.println("通過 " + label);
    }

    // 類別：Probe（測試用的探測車）
    // 意義：只在檢查工具內使用的 Rover 子類別，產量固定回 4。
    // 作用：記錄 exploreYield 被呼叫幾次，確認父類別的探索流程真的會呼叫可覆寫的產量方法，
    //       而不是只認得 HeavyRover。
    private static class Probe extends Rover {
        // calls：exploreYield 被呼叫的次數。
        private int calls;
        // 建構子：能源與樣本交給 Rover 保存。
        Probe(int energy, int samples) { super(energy, samples); }
        @Override
        // exploreYield：記一次呼叫，並回傳產量 4。
        protected int exploreYield() { calls++; return 4; }
    }
}
