import java.lang.reflect.Modifier;

// 類別：InheritanceCheck（教師驗證工具）
// 意義：檢查第 7 週的繼承設計是否符合規格。
// 作用：用固定案例檢查鑽探與既有行動的結果、狀態、protected 輔助方法，
//       並用反射確認父類別欄位仍是 private、子類別沒有重複宣告、也沒有覆寫方法。
//       反射與 AssertionError 不是學生先備。
public class InheritanceCheck {
    // passed：目前通過的檢查數。
    private static int passed;

    // main：程式進入點。依序執行所有檢查，任何一項失敗就停止。
    public static void main(String[] args) throws Exception {
        HeavyRover h = new HeavyRover(20, 0);
        check("一般鑽探的結果", "成功".equals(h.drill()));
        state("一般鑽探後的狀態", h, 12, 3, 1, 1);
        check("繼承的探索結果", "成功".equals(h.execute(1)));
        state("兩個行動操作同一份狀態", h, 6, 5, 2, 1);
        check("達標後拒絕鑽探", "任務已停止".equals(h.drill()));
        state("達標後遭拒時維持狀態", h, 6, 5, 2, 1);
        check("達標後拒絕充電", "任務已停止".equals(h.execute(3)));

        HeavyRover low = new HeavyRover(7, 0);
        check("能源不足時的結果", "能源不足".equals(low.drill()));
        state("能源不足時維持狀態", low, 7, 0, 0, 0);
        check("低能源探測車仍可探索", "成功".equals(low.execute(1)));
        state("遭拒後執行繼承的行動", low, 1, 2, 1, 0);
        HeavyRover exact = new HeavyRover(8, 0);
        check("能源剛好足夠時的結果", "成功".equals(exact.drill()));
        state("能源剛好足夠時的狀態", exact, 0, 3, 1, 1);
        check("能源耗盡後拒絕鑽探", "任務已停止".equals(exact.drill()));
        check("能源耗盡後拒絕充電", "任務已停止".equals(exact.execute(3)));
        state("能源耗盡後維持狀態", exact, 0, 3, 1, 1);
        HeavyRover zero = new HeavyRover(0, 0);
        check("初始能源為零便停止", "任務已停止".equals(zero.drill()));
        state("初始能源為零時維持狀態", zero, 0, 0, 0, 0);
        HeavyRover done = new HeavyRover(20, 5);
        check("初始已達標便停止", "任務已停止".equals(done.drill()));
        state("初始已達標時維持狀態", done, 20, 5, 0, 0);
        HeavyRover both = new HeavyRover(8, 2);
        check("耗盡最後一格能源時達標", "成功".equals(both.drill()));
        state("達標與耗盡同時成立時的狀態", both, 0, 5, 1, 1);
        check("達標與耗盡同時成立", both.isSuccess() && both.isExhausted());

        // 同套件可存取 protected。只在本次獨立物件驗證輔助方法，不在任務中外部改狀態。
        Rover helper = new Rover(5, 0);
        check("拒絕零成本", !helper.spendEnergy(0) && helper.getEnergy() == 5);
        check("拒絕負數成本", !helper.spendEnergy(-1) && helper.getEnergy() == 5);
        check("能源不足以支付成本時拒絕行動", !helper.spendEnergy(6) && helper.getEnergy() == 5);
        check("能源剛好足夠時接受行動", helper.spendEnergy(5) && helper.getEnergy() == 0);
        check("輔助方法不計入行動次數", helper.getActionCount() == 0);
        Rover sample = new Rover(20, 0);
        sample.addSamples(-1);
        sample.addSamples(0);
        check("忽略零份或負數樣本", sample.getSamples() == 0);
        sample.addSamples(2);
        sample.countAction();
        check("已提供的輔助方法", sample.getSamples() == 2 && sample.getActionCount() == 1);

        Mission priority = new Mission(new Rover(2, 4));
        priority.run(new int[] {2, 3});
        check("任務優先判定達標", "任務成功".equals(priority.getOutcome()) && priority.getStepCount() == 1);
        HeavyRover independent = new HeavyRover(20, 0);
        state("各實例獨立", independent, 20, 0, 0, 0);
        // 下一週伏筆，只作教師驗證，不列本週學生必寫。
        Mission preview = new Mission(new HeavyRover(20, 0));
        preview.run(new int[] {1, 1, 2});
        check("任務沿用父類別行為", "任務成功".equals(preview.getOutcome()) && preview.getStepCount() == 3);

        for (String field : new String[] {"energy", "samples", "actionCount"}) {
            check("父類別的私有欄位 " + field, Modifier.isPrivate(Rover.class.getDeclaredField(field).getModifiers()));
            boolean duplicate = false;
            for (java.lang.reflect.Field f : HeavyRover.class.getDeclaredFields()) {
                if (f.getName().equals(field)) { duplicate = true; }
            }
            check("無重複欄位 " + field, !duplicate);
        }
        check("子類別的私有狀態", Modifier.isPrivate(HeavyRover.class.getDeclaredField("drillCount").getModifiers()));
        check("扣除能源的方法為 protected", Modifier.isProtected(Rover.class.getDeclaredMethod("spendEnergy", int.class).getModifiers()));
        check("累加樣本的方法為 protected", Modifier.isProtected(Rover.class.getDeclaredMethod("addSamples", int.class).getModifiers()));
        check("計次方法為 protected", Modifier.isProtected(Rover.class.getDeclaredMethod("countAction").getModifiers()));
        check("繼承 Rover", HeavyRover.class.getSuperclass() == Rover.class);
        boolean override = false;
        for (java.lang.reflect.Method m : HeavyRover.class.getDeclaredMethods()) {
            for (java.lang.reflect.Method parent : Rover.class.getDeclaredMethods()) {
                if (!Modifier.isPrivate(parent.getModifiers()) && m.getName().equals(parent.getName())
                        && java.util.Arrays.equals(m.getParameterTypes(), parent.getParameterTypes())) { override = true; }
            }
        }
        check("新增方法而非覆寫", !override);
        System.out.println(passed + " 項繼承檢查通過");
    }

    // state：檢查一台重型車的能源、樣本、行動次數、鑽探次數是否都等於預期。
    private static void state(String label, HeavyRover h, int energy, int samples, int actions, int drills) {
        check(label, h.getEnergy() == energy && h.getSamples() == samples
                && h.getActionCount() == actions && h.getDrillCount() == drills);
    }

    // check：result 為 false 就丟出錯誤；為 true 就累加 passed 並印出「通過」。
    private static void check(String label, boolean result) {
        if (!result) { throw new AssertionError(label); }
        passed++;
        System.out.println("通過 " + label);
    }
}
