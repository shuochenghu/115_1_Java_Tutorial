// 類別：SampleOverloadDemo（過載比較入口）
// 意義：把同一台車經不同型態的參考、以及樣本盒與手動數量，並排交給 print 的程式入口。
// 作用：用六行輸出比較編譯器各選到哪一個 print。
public class SampleOverloadDemo {
    // main：程式進入點。先讓一台重型車跑完任務，再依序印出六行報表。
    public static void main(String[] args) {
        Rover rover = new HeavyRover(20, 0);
        Mission mission = new Mission(rover);
        mission.run(new int[] {1, 1, 2});

        // 指向同一台車。引數在編譯期的型態決定可用的報表簽名。
        SampleSource source = rover;
        SampleReport.print("重型探測車", rover);
        SampleReport.print("重型探測車", source);
        SampleReport.print("樣本盒", new SampleTray(4));
        SampleReport.print("空盒", new SampleTray(0));
        SampleReport.print("手動清點", 7);
        SampleReport.print("手動清點", 0);
    }
}
