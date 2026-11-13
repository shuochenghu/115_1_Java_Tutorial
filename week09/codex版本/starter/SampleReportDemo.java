// 類別：SampleReportDemo（報表示範入口）
// 意義：示範同一個報表方法可以讀取不同種類的樣本來源。
// 作用：先讓一台重型車執行任務，再把重型車與兩個樣本盒交給報表；各來源是互相獨立的物件。
public class SampleReportDemo {
    // main：程式進入點。準備三個樣本來源並各印出一行報表。
    public static void main(String[] args) {
        Rover heavy = new HeavyRover(20, 0);
        Mission mission = new Mission(heavy);
        mission.run(new int[] {1, 1, 2});

        SampleTray tray = new SampleTray(4);
        SampleTray empty = new SampleTray(0);

        // TODO 4：以同一報表方法讀取 heavy、tray、empty，然後移除提示。
        System.out.println("尚未完成");
    }
}
