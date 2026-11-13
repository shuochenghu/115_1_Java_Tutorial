// 類別：QuickSampleDemo（課後作業教師參考：示範入口）
// 意義：示範單參數快速查詢的程式入口。
// 作用：用一台重型車與一個空樣本盒各呼叫一次快速查詢；與 homework/SampleReport.java 一起編譯。
public class QuickSampleDemo {
    // main：程式進入點。印出兩行快速查詢結果。
    public static void main(String[] args) {
        Rover rover = new HeavyRover(20, 0);
        Mission mission = new Mission(rover);
        mission.run(new int[] {1, 1, 2});
        // 兩個呼叫都只有一個引數，使用同一個快速查詢入口。
        SampleReport.print(rover);
        SampleReport.print(new SampleTray(0));
    }
}
