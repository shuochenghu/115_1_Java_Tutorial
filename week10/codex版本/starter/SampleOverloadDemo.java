// 類別：SampleOverloadDemo（過載比較入口）
// 意義：把同一台車經不同型態的參考、以及樣本盒與手動數量，並排交給 print 的程式入口。
// 作用：用六行輸出比較編譯器各選到哪一個 print。
public class SampleOverloadDemo {
    // main：程式進入點。先讓一台重型車跑完任務，再依序印出六行報表。
    public static void main(String[] args) {
        Rover rover = new HeavyRover(20, 0);
        // 先把任務跑完，再用報表讀取同一台車的狀態。
        Mission mission = new Mission(rover);
        mission.run(new int[] {1, 1, 2});

        // TODO 4：建立同一台車的介面參考，依講義 C 的順序寫六個呼叫。
        System.out.println("尚未完成");
    }
}
