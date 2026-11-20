// 類別：SampleGroupDemo（本週主線展示入口，教師提供，starter 與 teacher 兩份相同）
// 意義：建立樣本盒群組、重型車群組與混合群組，完成後印出九行固定結果。
// 作用：資料建立與展示都已提供，不是學生的 TODO；學生只修改 SampleGroup.java。
//       同一台已完成任務的車同時放進兩個群組，觀察取回型態與報表過載；不會讓它再跑一次任務。
public class SampleGroupDemo {
    // main：程式進入點。先執行一次任務，建立三個群組，分兩段檢查進度，全部通過才印出九行結果。
    public static void main(String[] args) {
        HeavyRover heavy = new HeavyRover(20, 0);
        Mission mission = new Mission(heavy);
        mission.run(new int[] {1, 1, 2});
        SampleTray tray = new SampleTray(4);
        SampleTray emptyTray = new SampleTray(0);

        SampleGroup<SampleTray> trays = new SampleGroup<>();
        trays.add(tray);
        trays.add(emptyTray);
        SampleGroup<HeavyRover> vehicles = new SampleGroup<>();
        vehicles.add(heavy);
        SampleGroup<SampleSource> mixed = new SampleGroup<>();
        mixed.add(heavy);
        mixed.add(tray);
        mixed.add(emptyTray);

        // 第一段檢查（TODO 1～3：保存與取回）：筆數要正確，第 0 筆要是當初加入的同一個物件。
        // || 是短路判斷：筆數不對時就不再往右執行，所以不會在空清單讀第 0 筆。
        if (trays.size() != 2 || vehicles.size() != 1 || mixed.size() != 3
                || trays.get(0) != tray || vehicles.get(0) != heavy || mixed.get(0) != heavy) {
            System.out.println("尚未完成：TODO 1～3（保存與取回）還沒通過，請依講義 B 檢查。");
            return;
        }
        // 第二段檢查（TODO 4：總樣本數）：保存與取回已正確，再比對三個群組的總和。
        if (trays.totalSamples() != 4 || vehicles.totalSamples() != 6 || mixed.totalSamples() != 10) {
            System.out.println("尚未完成：TODO 1～3 已能保存與取回，TODO 4 總樣本數還沒通過。");
            return;
        }
        // 這兩段只用來提示進度並避免未完成時出錯，不是完整驗收；
        // 講義 C1 的「探索後總和」與自選數量案例仍要自己跑。不要修改這裡的數字來消除提示。

        System.out.println("樣本盒群組 筆數=" + trays.size() + " 總樣本數=" + trays.totalSamples());
        System.out.println("車輛群組 筆數=" + vehicles.size() + " 總樣本數=" + vehicles.totalSamples());
        System.out.println("混合群組 筆數=" + mixed.size() + " 總樣本數=" + mixed.totalSamples());
        // 兩個 get 的編譯期型態不同（HeavyRover 與 SampleSource），因此會選到第 10 週的兩種報表過載。
        SampleReport.print("重型探測車", vehicles.get(0));
        SampleReport.print("重型探測車", mixed.get(0));
        // 從重型車群組取回的就是 HeavyRover，可以直接呼叫 getDrillCount()，不用轉型。
        HeavyRover recovered = vehicles.get(0);
        System.out.println("取回重型車 能源=" + recovered.getEnergy()
                + " 鑽探次數=" + recovered.getDrillCount());
        // == 比較的是參考：兩個群組的第 0 筆指向同一台車，群組沒有複製車輛。
        System.out.println("同一台車=" + (recovered == mixed.get(0)));
        SampleGroup<SampleTray> empty = new SampleGroup<>();
        System.out.println("空群組 筆數=" + empty.size() + " 總樣本數=" + empty.totalSamples());
        // 最後確認查詢沒有改動車輛狀態與任務日誌。
        System.out.println("任務狀態=" + heavy.getEnergy() + "/" + heavy.getSamples()
                + "/" + heavy.getActionCount() + " 日誌筆數=" + mission.getStepCount());
    }
}
