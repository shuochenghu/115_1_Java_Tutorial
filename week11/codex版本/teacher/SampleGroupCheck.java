import java.util.Arrays;

// 教師工具：依群組契約檢查資料、參考、狀態與第 8 週起的指令 4。
// AssertionError 與測試計數不列學生實作要求，學生仍要提出自己的案例與解釋。
public class SampleGroupCheck {
    private static int checks = 0;

    static void check(String label, boolean ok) {
        if (!ok) { throw new AssertionError(label); }
        checks++;
        System.out.println("通過 " + label);
    }

    // 保存每筆日誌內容，稍後比對查詢有沒有改動歷史；不只看日誌筆數。
    static String history(Mission mission) {
        String text = "";
        for (int i = 0; i < mission.getStepCount(); i++) {
            MissionStep step = mission.getStep(i);
            text += step.getCommand() + "/" + step.getEnergy() + "/" + step.getResult() + ";";
        }
        return text;
    }

    public static void main(String[] args) {
        SampleGroup<SampleTray> trays = new SampleGroup<>();
        check("空群組", trays.size() == 0 && trays.totalSamples() == 0);
        SampleTray four = new SampleTray(4);
        SampleTray zero = new SampleTray(0);
        trays.add(four);
        trays.add(zero);
        check("筆數與總量不同", trays.size() == 2 && trays.totalSamples() == 4);
        check("順序與原參考", trays.get(0) == four && trays.get(1) == zero);
        SampleGroup<SampleTray> independent = new SampleGroup<>();
        check("群組各自獨立", independent.size() == 0);
        trays.add(four);
        check("允許重複來源", trays.size() == 3 && trays.totalSamples() == 8);
        SampleGroup<SampleTray> changed = new SampleGroup<>();
        changed.add(new SampleTray(11));
        check("非示範數量", changed.totalSamples() == 11);

        HeavyRover heavy = new HeavyRover(20, 0);
        Mission mission = new Mission(heavy);
        mission.run(new int[] {1, 1, 2});
        String before = history(mission);
        SampleGroup<HeavyRover> vehicles = new SampleGroup<>();
        vehicles.add(heavy);
        SampleGroup<SampleSource> mixed = new SampleGroup<>();
        mixed.add(heavy);
        mixed.add(four);
        mixed.add(zero);
        check("混合來源", mixed.size() == 3 && mixed.totalSamples() == 10);
        HeavyRover recovered = vehicles.get(0);
        check("具體型態取回", recovered.getEnergy() == 8 && recovered.getDrillCount() == 0);
        check("同一物件", recovered == mixed.get(0));
        check("三次重讀相同", mixed.totalSamples() == 10 && mixed.totalSamples() == 10
                && mixed.totalSamples() == 10);
        check("車輛狀態不變", heavy.getEnergy() == 8 && heavy.getSamples() == 6
                && heavy.getActionCount() == 2 && heavy.getDrillCount() == 0);
        check("完整日誌不變", before.equals(history(mission)) && mission.getStepCount() == 2);
        check("任務結果不變", "任務成功".equals(mission.getOutcome()));

        // 獨立行動展示參考語意，不干擾前一個任務的車。
        HeavyRover changing = new HeavyRover(20, 0);
        SampleGroup<HeavyRover> live = new SampleGroup<>();
        live.add(changing);
        check("更新前讀值", live.totalSamples() == 0);
        changing.execute(1);
        check("更新後重讀目前值", live.totalSamples() == 3);

        HeavyRover drill = new HeavyRover(20, 0);
        Mission drillMission = new Mission(drill);
        drillMission.run(new int[] {4, 1});
        check("指令4重型車狀態", drill.getEnergy() == 6 && drill.getSamples() == 6
                && drill.getActionCount() == 2 && drill.getDrillCount() == 1);
        check("指令4結果與停止", "成功".equals(drillMission.getStep(0).getResult())
                && drillMission.getStepCount() == 2 && "任務成功".equals(drillMission.getOutcome()));
        check("指令4成功拒絕筆數", drillMission.countOk() == 2 && drillMission.countRejected() == 0);
        check("指令4不增計數格", Arrays.equals(drillMission.countCommands(), new int[] {0, 1, 0, 0}));
        SampleGroup<HeavyRover> drilled = new SampleGroup<>();
        String drillBefore = history(drillMission);
        drilled.add(drill);
        drilled.totalSamples();
        drilled.get(0).getEnergy();
        check("鑽探後群組重讀不操作車輛", drill.getDrillCount() == 1
                && drill.getActionCount() == 2 && drillBefore.equals(history(drillMission)));

        Rover normal = new StandardRover(20, 0);
        Mission normalMission = new Mission(normal);
        normalMission.run(new int[] {4, 1});
        check("一般車拒絕指令4", "無效指令".equals(normalMission.getStep(0).getResult())
                && normal.getEnergy() == 14 && normal.getSamples() == 2 && normal.getActionCount() == 1);
        check("一般車步數結果與計數", normalMission.getStepCount() == 2
                && "任務未完成".equals(normalMission.getOutcome())
                && normalMission.countOk() == 1 && normalMission.countRejected() == 1
                && Arrays.equals(normalMission.countCommands(), new int[] {0, 1, 0, 0}));
        HeavyRover low = new HeavyRover(7, 0);
        check("鑽探能源不足", "能源不足".equals(low.execute(4))
                && low.getEnergy() == 7 && low.getSamples() == 0 && low.getActionCount() == 0
                && low.getDrillCount() == 0);
        HeavyRover done = new HeavyRover(20, 5);
        check("已達標不能鑽探", "任務已停止".equals(done.execute(4))
                && done.getEnergy() == 20 && done.getSamples() == 5 && done.getActionCount() == 0);
        HeavyRover scan = new HeavyRover(20, 0);
        check("其他指令交回父類別", "成功".equals(scan.execute(2))
                && scan.getEnergy() == 18 && scan.getSamples() == 1 && scan.getActionCount() == 1);
        System.out.println("SampleGroupCheck " + checks + " 項通過");
    }
}
