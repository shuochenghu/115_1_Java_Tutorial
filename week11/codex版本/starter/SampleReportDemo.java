// 類別：SampleReportDemo（舊報表入口）
// 意義：第 9 週留下的報表示範入口，規格是三行都印共用格式。
// 作用：本週用來觀察「新增一個過載方法，會不會改變既有呼叫選到的版本」。
//       先執行任務，再讀取樣本數；各來源是互相獨立的物件。
public class SampleReportDemo {
    // main：程式進入點。準備一台跑完任務的重型車與兩個樣本盒，各印出一行報表。
    public static void main(String[] args) {
        Rover heavy = new HeavyRover(20, 0);
        Mission mission = new Mission(heavy);
        mission.run(new int[] {1, 1, 2});

        SampleTray tray = new SampleTray(4);
        SampleTray empty = new SampleTray(0);

        // 舊入口需要共用報表。以介面參考傳入同一台重型車。
        SampleSource source = heavy;
        SampleReport.print("重型探測車", source);
        SampleReport.print("樣本盒", tray);
        SampleReport.print("空盒", empty);
    }
}
