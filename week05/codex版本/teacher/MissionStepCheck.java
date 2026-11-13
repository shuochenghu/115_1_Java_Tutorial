import java.util.ArrayList;

// 類別：MissionStepCheck（教師驗證工具）
// 意義：檢查 MissionStep 是否正確保存資料。
// 作用：用 8 項檢查確認建構子與 getter、物件彼此獨立、別名、字串內容比較、數值快照與清單順序；
//       不是學生的 JUnit 作業。
public class MissionStepCheck {
    // main：程式進入點。依序執行 8 項檢查，任何一項失敗就停止。
    public static void main(String[] args) {
        MissionStep first = new MissionStep(1, 14, new String("成功"));
        MissionStep second = new MissionStep(2, 12, "能源不足");
        check("第一筆資料", first.getCommand() == 1 && first.getEnergy() == 14
                && "成功".equals(first.getResult()));
        check("第二筆資料", second.getCommand() == 2 && second.getEnergy() == 12
                && "能源不足".equals(second.getResult()));
        check("各實例獨立", first != second && first.getEnergy() == 14);
        MissionStep alias = first;
        check("別名指向同一物件", alias == first && alias.getCommand() == 1);
        check("比較字串內容", first.isSuccessful() && !second.isSuccessful());
        MissionStep returned = new MissionStep(0, 20, "返回");
        check("返回不算成功", !returned.isSuccessful());
        int currentEnergy = 8;
        MissionStep snapshot = new MissionStep(-1, currentEnergy, "無效指令");
        currentEnergy = 20;
        check("保存當時數值", snapshot.getEnergy() == 8 && snapshot.getCommand() == -1
                && !snapshot.isSuccessful() && currentEnergy == 20);
        ArrayList<MissionStep> history = new ArrayList<>();
        history.add(first);
        history.add(second);
        check("清單順序", history.size() == 2 && history.get(0) == first
                && history.get(1) == second && history.get(1).getEnergy() == 12);
        System.out.println("8/8 項物件檢查通過");
    }

    // check：correct 為 true 就印出「通過」；為 false 就丟出錯誤，訊息是檢查項目名稱。
    static void check(String label, boolean correct) {
        if (!correct) {
            throw new AssertionError(label);
        }
        System.out.println("通過 " + label);
    }
}
