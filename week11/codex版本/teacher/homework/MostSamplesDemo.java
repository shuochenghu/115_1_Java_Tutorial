// 類別：MostSamplesDemo（作業的教師參考入口）
// 意義：驗證 findMostSamples() 的最低驗收案例，輸出六行。
// 作用：資料刻意安排成「最大值在中間」與「兩個不同物件平手」，才抓得出
//       「只回第一筆」「平手回最後一筆」「回傳複製品」這三種錯誤。
public class MostSamplesDemo {
    // main：程式進入點。依序檢查樣本盒、重型車、單筆零樣本與空群組。
    public static void main(String[] args) {
        // 案例 1：樣本盒 4、9、9。最大值在第二筆，第三筆與它平手，應回傳第二筆的原物件。
        SampleGroup<SampleTray> trays = new SampleGroup<>();
        SampleTray firstNine = new SampleTray(9);
        trays.add(new SampleTray(4));
        trays.add(firstNine);
        trays.add(new SampleTray(9));
        SampleTray best = trays.findMostSamples();
        System.out.println("最大樣本數=" + best.getSamples());
        System.out.println("平手先加入=" + (best == firstNine));

        // 案例 2：兩台不同狀態的重型車。回傳值直接指派給 HeavyRover，不用轉型；查詢後狀態不變。
        SampleGroup<HeavyRover> vehicles = new SampleGroup<>();
        HeavyRover first = new HeavyRover(20, 1);
        HeavyRover second = new HeavyRover(18, 4);
        vehicles.add(first);
        vehicles.add(second);
        HeavyRover bestVehicle = vehicles.findMostSamples();
        System.out.println("最大來源能源=" + bestVehicle.getEnergy());
        System.out.println("原車狀態=" + second.getEnergy() + "/" + second.getSamples()
                + "/" + second.getActionCount());

        // 案例 3：只有一筆、樣本數為 0。有一筆 0 份資料，和「沒有資料」不同。
        SampleGroup<SampleTray> zero = new SampleGroup<>();
        zero.add(new SampleTray(0));
        System.out.println("單筆零樣本=" + zero.findMostSamples().getSamples());

        // 案例 4：空群組。先用 size() 判斷，空的就略過，不呼叫 findMostSamples()。
        SampleGroup<SampleTray> empty = new SampleGroup<>();
        if (empty.size() > 0) {
            System.out.println(empty.findMostSamples().getSamples());
        } else {
            System.out.println("空群組：略過最大值查詢");
        }
    }
}
