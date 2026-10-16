// 教師支援：把F1的測試設計變成實際檢查，不要求學生先學JUnit。
public class SampleCaseCheck {
    public static void main(String[] args) {
        SampleCase box = new SampleCase(3);
        check("new box", box.getCapacity() == 3 && box.getCount() == 0
                && box.getFreeSpace() == 3);
        check("store two", box.store(2) && box.getCount() == 2 && box.getFreeSpace() == 1);
        check("reject entire batch", !box.store(2) && box.getCount() == 2);
        check("reject zero", !box.store(0) && box.getCount() == 2);
        check("reject negative", !box.store(-1) && box.getCount() == 2);
        check("reject huge batch", !box.store(Integer.MAX_VALUE) && box.getCount() == 2);
        check("exact capacity", box.store(1) && box.getCount() == 3 && box.getFreeSpace() == 0);
        check("reject full", !box.store(1) && box.getCount() == 3);
        check("unload", box.unload() == 3 && box.getCount() == 0 && box.getFreeSpace() == 3);
        check("unload empty", box.unload() == 0 && box.getCount() == 0);
        SampleCase low = new SampleCase(0);
        SampleCase high = new SampleCase(9);
        check("capacity bounds", low.getCapacity() == 1 && high.getCapacity() == 8);
        high.store(2);
        check("independent boxes", box.getCount() == 0 && low.getCount() == 0 && high.getCount() == 2);
        System.out.println("12/12 sample case checks passed");
    }

    static void check(String label, boolean correct) {
        if (!correct) {
            throw new AssertionError(label);
        }
        System.out.println("PASS " + label);
    }
}
