import java.util.ArrayList;

// 教師工具：檢查物件資料與參考，不是學生 JUnit 作業。
public class MissionStepCheck {
    public static void main(String[] args) {
        MissionStep first = new MissionStep(1, 14, new String("OK"));
        MissionStep second = new MissionStep(2, 12, "NOT_ENOUGH_ENERGY");
        check("first values", first.getCommand() == 1 && first.getEnergy() == 14
                && "OK".equals(first.getResult()));
        check("second values", second.getCommand() == 2 && second.getEnergy() == 12
                && "NOT_ENOUGH_ENERGY".equals(second.getResult()));
        check("independent instances", first != second && first.getEnergy() == 14);
        MissionStep alias = first;
        check("alias reference", alias == first && alias.getCommand() == 1);
        check("content equality", first.isSuccessful() && !second.isSuccessful());
        MissionStep returned = new MissionStep(0, 20, "RETURN");
        check("return is not success", !returned.isSuccessful());
        int currentEnergy = 8;
        MissionStep snapshot = new MissionStep(-1, currentEnergy, "UNKNOWN_COMMAND");
        currentEnergy = 20;
        check("value snapshot", snapshot.getEnergy() == 8 && snapshot.getCommand() == -1
                && !snapshot.isSuccessful() && currentEnergy == 20);
        ArrayList<MissionStep> history = new ArrayList<>();
        history.add(first);
        history.add(second);
        check("list order", history.size() == 2 && history.get(0) == first
                && history.get(1) == second && history.get(1).getEnergy() == 12);
        System.out.println("8/8 object checks passed");
    }

    static void check(String label, boolean correct) {
        if (!correct) {
            throw new AssertionError(label);
        }
        System.out.println("PASS " + label);
    }
}
