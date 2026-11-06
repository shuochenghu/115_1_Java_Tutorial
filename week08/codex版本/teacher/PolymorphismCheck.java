import java.lang.reflect.Modifier;
import java.util.Arrays;

// 教師驗證工具：反射與AssertionError不列學生先備，也不放學生src。
public class PolymorphismCheck {
    private static int passed;

    public static void main(String[] args) throws Exception {
        Rover normal = new Rover(20, 0);
        Rover heavy = new HeavyRover(20, 0);
        check("normal execute", "OK".equals(normal.execute(1)));
        state("normal yield", normal, 14, 2, 1);
        check("heavy execute", "OK".equals(heavy.execute(1)));
        state("heavy yield through Rover reference", heavy, 14, 3, 1);
        check("ordinary object independent", normal.getSamples() == 2);

        Mission n = new Mission(new Rover(20, 0));
        n.run(new int[] {1, 1, 2});
        check("normal mission", n.getStepCount() == 3 && "SUCCESS".equals(n.getOutcome()));
        Rover h = new HeavyRover(20, 0);
        Mission m = new Mission(h);
        m.run(new int[] {1, 1, 2});
        state("heavy mission state", h, 8, 6, 2);
        check("stops before scan", m.getStepCount() == 2 && "SUCCESS".equals(m.getOutcome()));
        check("heavy command counts", Arrays.equals(m.countCommands(), new int[] {0, 2, 0, 0}));
        check("heavy summary", m.countOk() == 2 && m.countRejected() == 0);
        check("snapshot energy", m.getStep(0).getEnergy() == 14 && m.getStep(1).getEnergy() == 8);
        check("lowest energy", m.lowestEnergy() == 8);
        int[] counts = m.countCommands();
        counts[1] = 99;
        check("counts copy", m.countCommands()[1] == 2);

        Rover exactNormal = new Rover(6, 2);
        Mission en = new Mission(exactNormal);
        en.run(new int[] {1, 3});
        state("normal exact energy", exactNormal, 0, 4, 1);
        check("normal exhausted", "EXHAUSTED".equals(en.getOutcome()) && en.getStepCount() == 1);
        Rover exactHeavy = new HeavyRover(6, 2);
        Mission eh = new Mission(exactHeavy);
        eh.run(new int[] {1, 3});
        state("heavy exact energy", exactHeavy, 0, 5, 1);
        check("success before exhausted", "SUCCESS".equals(eh.getOutcome()) && eh.getStepCount() == 1);

        Probe low = new Probe(5, 0);
        check("low rejection", "NOT_ENOUGH_ENERGY".equals(low.execute(1)));
        state("low unchanged", low, 5, 0, 0);
        check("no yield lookup on rejection", low.calls == 0);
        Probe ready = new Probe(6, 0);
        ready.execute(1);
        state("custom yield dispatch", ready, 0, 4, 1);
        check("yield called once", ready.calls == 1);
        check("zero cannot charge", "STOPPED".equals(ready.execute(3)));
        state("zero stays unchanged", ready, 0, 4, 1);
        Probe done = new Probe(20, 5);
        check("initial success stopped", "STOPPED".equals(done.execute(1)) && done.calls == 0);
        state("initial success unchanged", done, 20, 5, 0);
        Rover zero = new HeavyRover(0, 0);
        Mission z = new Mission(zero);
        z.run(new int[] {3, 1});
        check("initial zero reads nothing", z.getStepCount() == 0 && z.lowestEnergy() == -1);
        check("initial zero outcome", "EXHAUSTED".equals(z.getOutcome()));

        Rover scan = new HeavyRover(20, 0);
        check("scan unchanged result", "OK".equals(scan.execute(2)));
        state("scan unchanged state", scan, 18, 1, 1);
        check("cap charge", "OK".equals(scan.execute(3)));
        state("charge capped", scan, 20, 1, 2);
        check("full rejected", "ALREADY_FULL".equals(scan.execute(3)));
        state("full unchanged", scan, 20, 1, 2);
        Mission returned = new Mission(new HeavyRover(20, 0));
        returned.run(new int[] {3, 9, 0, 1});
        check("return stops", "RETURNED".equals(returned.getOutcome()) && returned.getStepCount() == 3);
        check("rejected excludes return", returned.countRejected() == 2 && returned.countOk() == 0);
        check("return counts", Arrays.equals(returned.countCommands(), new int[] {1, 0, 0, 1}));
        Mission empty = new Mission(new HeavyRover(20, 0));
        empty.run(new int[] {});
        check("empty mission", empty.getStepCount() == 0 && empty.lowestEnergy() == -1
                && "UNFINISHED".equals(empty.getOutcome()));

        HeavyRover drill = new HeavyRover(20, 0);
        check("drill unchanged", "OK".equals(drill.drill()));
        state("after drill", drill, 12, 3, 1);
        drill.execute(1);
        state("new explore after drill", drill, 6, 6, 2);
        check("drill stops after success", "STOPPED".equals(drill.drill()) && drill.getDrillCount() == 1);
        state("stopped drill unchanged", drill, 6, 6, 2);
        HeavyRover exactDrill = new HeavyRover(8, 0);
        check("exact drill", "OK".equals(exactDrill.drill()));
        state("exact drill state", exactDrill, 0, 3, 1);

        check("parent private state", Modifier.isPrivate(Rover.class.getDeclaredField("energy").getModifiers())
                && Modifier.isPrivate(Rover.class.getDeclaredField("samples").getModifiers())
                && Modifier.isPrivate(Rover.class.getDeclaredField("actionCount").getModifiers()));
        check("private explore retained", Modifier.isPrivate(Rover.class.getDeclaredMethod("explore").getModifiers()));
        check("protected parent yield", Modifier.isProtected(Rover.class.getDeclaredMethod("exploreYield").getModifiers()));
        check("protected child yield", Modifier.isProtected(HeavyRover.class.getDeclaredMethod("exploreYield").getModifiers()));
        check("yield is instance method", !Modifier.isStatic(HeavyRover.class.getDeclaredMethod("exploreYield").getModifiers()));
        boolean repeated = false;
        for (java.lang.reflect.Field field : HeavyRover.class.getDeclaredFields()) {
            if (Arrays.asList("energy", "samples", "actionCount").contains(field.getName())) { repeated = true; }
        }
        check("no duplicate parent state", !repeated);
        System.out.println("PolymorphismCheck " + passed + " passed");
    }

    private static void state(String label, Rover rover, int energy, int samples, int actions) {
        check(label, rover.getEnergy() == energy && rover.getSamples() == samples
                && rover.getActionCount() == actions);
    }

    private static void check(String label, boolean result) {
        if (!result) { throw new AssertionError(label); }
        passed++;
        System.out.println("PASS " + label);
    }

    // 驗證父類別確實呼叫可覆寫產量方法，不限定只能辨識HeavyRover。
    private static class Probe extends Rover {
        private int calls;
        Probe(int energy, int samples) { super(energy, samples); }
        @Override
        protected int exploreYield() { calls++; return 4; }
    }
}
