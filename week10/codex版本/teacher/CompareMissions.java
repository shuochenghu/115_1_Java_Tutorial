// 類別：CompareMissions（任務回歸入口）
// 意義：確認任務程式和第 9 週行為相同的程式入口。
// 作用：兩種車使用同一份腳本，方便比較實際產量與停止時機。
// 固定腳本，不讀命令列參數。每個新任務配新車，只run一次。
public class CompareMissions {
    // main：程式進入點。準備固定腳本 {1, 1, 2}，讓兩台新車各執行一次任務並印出報表。
    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        // 兩台車各有一份任務，Mission 一律用 Rover 型態操作。
        Rover normal = new StandardRover(20, 0);
        Mission normalMission = new Mission(normal);
        normalMission.run(script);
        show("一般探測車", normal, normalMission, script.length);

        Rover heavy = new HeavyRover(20, 0);
        Mission heavyMission = new Mission(heavy);
        heavyMission.run(script);
        show("重型探測車", heavy, heavyMission, script.length);
    }

    // show：印出一台車的完整報表：標題、任務回放、指令次數、摘要、最終狀態與電量百分比。
    // 已提供；這裡不判斷車種，也不執行行動，只讀取資料。
    static void show(String label, Rover rover, Mission mission, int scriptLength) {
        System.out.println("=== " + label + " ===");
        MissionLog.printReplay(mission);
        System.out.println("指令次數[0..3]="
                + java.util.Arrays.toString(mission.countCommands()));
        MissionLog.printSummary(scriptLength, mission);
        System.out.println("最終能源=" + rover.getEnergy()
                + ", 樣本數=" + rover.getSamples()
                + ", 行動次數=" + rover.getActionCount()
                + " -> " + mission.getOutcome());
        System.out.println("電量百分比=" + rover.getEnergy() / 20.0 * 100);
    }
}
