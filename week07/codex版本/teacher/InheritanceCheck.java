import java.lang.reflect.Modifier;

// 教師驗證工具：案例先由規格決定。反射與 AssertionError 不是學生先備。
public class InheritanceCheck {
    private static int passed;

    public static void main(String[] args) throws Exception {
        HeavyRover h = new HeavyRover(20, 0);
        check("normal drill result", "OK".equals(h.drill()));
        state("normal drill state", h, 12, 3, 1, 1);
        check("inherited explore result", "OK".equals(h.execute(1)));
        state("same state for both actions", h, 6, 5, 2, 1);
        check("success blocks drill", "STOPPED".equals(h.drill()));
        state("success rejection unchanged", h, 6, 5, 2, 1);
        check("success blocks charge", "STOPPED".equals(h.execute(3)));

        HeavyRover low = new HeavyRover(7, 0);
        check("insufficient result", "NOT_ENOUGH_ENERGY".equals(low.drill()));
        state("insufficient unchanged", low, 7, 0, 0, 0);
        check("low rover still explores", "OK".equals(low.execute(1)));
        state("inherited action after rejection", low, 1, 2, 1, 0);
        HeavyRover exact = new HeavyRover(8, 0);
        check("exact result", "OK".equals(exact.drill()));
        state("exact state", exact, 0, 3, 1, 1);
        check("exhaustion blocks drill", "STOPPED".equals(exact.drill()));
        check("exhaustion blocks charge", "STOPPED".equals(exact.execute(3)));
        state("exhaustion unchanged", exact, 0, 3, 1, 1);
        HeavyRover zero = new HeavyRover(0, 0);
        check("initial zero stopped", "STOPPED".equals(zero.drill()));
        state("initial zero unchanged", zero, 0, 0, 0, 0);
        HeavyRover done = new HeavyRover(20, 5);
        check("initial success stopped", "STOPPED".equals(done.drill()));
        state("initial success unchanged", done, 20, 5, 0, 0);
        HeavyRover both = new HeavyRover(8, 2);
        check("last energy yields success", "OK".equals(both.drill()));
        state("both flags state", both, 0, 5, 1, 1);
        check("both flags true", both.isSuccess() && both.isExhausted());

        // 同套件可存取 protected。只在本次獨立物件驗證輔助方法，不在任務中外部改狀態。
        Rover helper = new Rover(5, 0);
        check("zero cost rejected", !helper.spendEnergy(0) && helper.getEnergy() == 5);
        check("negative cost rejected", !helper.spendEnergy(-1) && helper.getEnergy() == 5);
        check("over cost rejected", !helper.spendEnergy(6) && helper.getEnergy() == 5);
        check("exact cost accepted", helper.spendEnergy(5) && helper.getEnergy() == 0);
        check("helper does not count action", helper.getActionCount() == 0);
        Rover sample = new Rover(20, 0);
        sample.addSamples(-1);
        sample.addSamples(0);
        check("nonpositive samples ignored", sample.getSamples() == 0);
        sample.addSamples(2);
        sample.countAction();
        check("provided helpers", sample.getSamples() == 2 && sample.getActionCount() == 1);

        Mission priority = new Mission(new Rover(2, 4));
        priority.run(new int[] {2, 3});
        check("mission success priority", "SUCCESS".equals(priority.getOutcome()) && priority.getStepCount() == 1);
        HeavyRover independent = new HeavyRover(20, 0);
        state("instances independent", independent, 20, 0, 0, 0);
        // 下一週伏筆，只作教師驗證，不列本週學生必寫。
        Mission preview = new Mission(new HeavyRover(20, 0));
        preview.run(new int[] {1, 1, 2});
        check("mission retains base behavior", "SUCCESS".equals(preview.getOutcome()) && preview.getStepCount() == 3);

        for (String field : new String[] {"energy", "samples", "actionCount"}) {
            check("base private " + field, Modifier.isPrivate(Rover.class.getDeclaredField(field).getModifiers()));
            boolean duplicate = false;
            for (java.lang.reflect.Field f : HeavyRover.class.getDeclaredFields()) {
                if (f.getName().equals(field)) { duplicate = true; }
            }
            check("no duplicate " + field, !duplicate);
        }
        check("subclass private state", Modifier.isPrivate(HeavyRover.class.getDeclaredField("drillCount").getModifiers()));
        check("protected spend", Modifier.isProtected(Rover.class.getDeclaredMethod("spendEnergy", int.class).getModifiers()));
        check("protected samples", Modifier.isProtected(Rover.class.getDeclaredMethod("addSamples", int.class).getModifiers()));
        check("protected count", Modifier.isProtected(Rover.class.getDeclaredMethod("countAction").getModifiers()));
        check("extends Rover", HeavyRover.class.getSuperclass() == Rover.class);
        boolean override = false;
        for (java.lang.reflect.Method m : HeavyRover.class.getDeclaredMethods()) {
            for (java.lang.reflect.Method parent : Rover.class.getDeclaredMethods()) {
                if (!Modifier.isPrivate(parent.getModifiers()) && m.getName().equals(parent.getName())
                        && java.util.Arrays.equals(m.getParameterTypes(), parent.getParameterTypes())) { override = true; }
            }
        }
        check("adds rather than overrides", !override);
        System.out.println(passed + " inheritance checks passed");
    }

    private static void state(String label, HeavyRover h, int energy, int samples, int actions, int drills) {
        check(label, h.getEnergy() == energy && h.getSamples() == samples
                && h.getActionCount() == actions && h.getDrillCount() == drills);
    }

    private static void check(String label, boolean result) {
        if (!result) { throw new AssertionError(label); }
        passed++;
        System.out.println("PASS " + label);
    }
}
