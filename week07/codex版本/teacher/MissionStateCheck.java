// 類別：MissionStateCheck（教師驗證工具）
// 意義：檢查 Rover 與 Mission 在各種狀態下的行為。
// 作用：用 16 項檢查涵蓋停止規則、充電上限、拒絕時狀態不變、返回、指令次數、快照、
//       空日誌與成功優先；每個案例建立新的探測車或任務。不列入學生必寫語法。
public class MissionStateCheck {
    // passed：目前通過的檢查數。
    private static int passed;
    // main：程式進入點。依序執行 16 項檢查，最後確認全部通過。
    public static void main(String[] args) {
        Rover empty = new Rover(0, 0);
        check("能源耗盡後無法充電", "任務已停止".equals(empty.execute(3)) && empty.getEnergy() == 0);
        Rover done = new Rover(20, 5);
        check("達標後無法探索", "任務已停止".equals(done.execute(1)) && done.getEnergy() == 20);
        Rover edge = new Rover(19, 0);
        check("充電上限", "成功".equals(edge.execute(3)) && edge.getEnergy() == 20);
        check("已充滿時拒絕充電", "能源已滿".equals(edge.execute(3)) && edge.getActionCount() == 1);
        Rover low = new Rover(5, 0);
        check("拒絕時維持狀態", "能源不足".equals(low.execute(1)) && low.getEnergy() == 5 && low.getActionCount() == 0);
        Mission mission = new Mission(new Rover(20, 0));
        mission.run(new int[] {3, 9, 0, 1});
        check("返回後停止讀取", mission.getStepCount() == 3 && "已返回".equals(mission.getOutcome()));
        check("返回不列入拒絕", mission.countOk() == 0 && mission.countRejected() == 2);
        check("指令次數的副本", mission.countCommands()[3] == 1);
        int[] counts = mission.countCommands();
        counts[3] = 99;
        check("修改副本不影響日誌", mission.countCommands()[3] == 1);
        Mission history = new Mission(new Rover(20, 0));
        history.run(new int[] {1, 3, 0});
        check("第一筆快照", history.getStepCount() == 3 && history.getStep(0).getEnergy() == 14);
        check("後續快照", history.getStepCount() == 3 && history.getStep(1).getEnergy() == 19);
        check("最低能源不一定是最終能源", history.lowestEnergy() == 14);
        Mission blank = new Mission(new Rover(20, 0));
        blank.run(new int[] {});
        check("空日誌的最低能源標記", blank.lowestEnergy() == -1 && blank.getStepCount() == 0);
        check("各自獨立的歷史", history.getStepCount() == 3 && blank.getStepCount() == 0);
        Mission priority = new Mission(new Rover(2, 4));
        priority.run(new int[] {2, 3});
        check("達標優先於能源耗盡", "任務成功".equals(priority.getOutcome()) && priority.getStepCount() == 1);
        check("字串內容", new MissionStep(1, 14, new String("成功")).isSuccessful());
        System.out.println(passed + "/16 項通過");
        if (passed != 16) { throw new AssertionError("任務狀態檢查失敗"); }
    }
    // check：印出「通過」或「失敗」與檢查名稱；通過時累加 passed。
    private static void check(String label, boolean result) {
        System.out.println((result ? "通過 " : "失敗 ") + label);
        if (result) { passed++; }
    }
}
