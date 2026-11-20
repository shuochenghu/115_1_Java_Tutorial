// 類別：UnsafeGroupTry（非法資料反例入口，教師提供）
// 意義：故意把樣本盒加進「重型車群組」，用來檢查群組有沒有守住型態約定。
// 作用：只放在 ai-check 資料夾，不可混入正式 src。
//       配上模擬 AI 的 SampleGroup：可以編譯（有警告），但執行到取出那一行才失敗。
//       配上修正後的 SampleGroup：在 add 那一行就編譯失敗，這正是修正成功的證據。
public class UnsafeGroupTry {
    // main：程式進入點。建立重型車群組，加入一個樣本盒，再把第一筆當成重型車取出。
    public static void main(String[] args) {
        SampleGroup<HeavyRover> vehicles = new SampleGroup<>();
        vehicles.add(new SampleTray(4));
        // 轉型不會把樣本盒變成重型車。宣稱回傳 HeavyRover 的取出動作，執行到這裡就無法成立。
        HeavyRover rover = vehicles.get(0);
        System.out.println("能源=" + rover.getEnergy());
    }
}
