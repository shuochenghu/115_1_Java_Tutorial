// 類別：SampleReport（樣本報表）
// 意義：地面站的報表工具，所有方法都叫 print，靠「參數不同」區分（過載）。
// 作用：呼叫時，編譯器依引數的個數與宣告型態，選出要用哪一個 print。
// 課後作業教師參考：多了一個只收一個引數的快速查詢版本。
public class SampleReport {
    // print（共用報表）：印出一行「<標籤> 樣本數=<數量>」。任何實作 SampleSource 的物件都能傳入。
    public static void print(String label, SampleSource source) {
        // 介面只提供樣本數。交給 int 版本共用輸出格式。
        print(label, source.getSamples());
    }

    // print（車輛詳報）：印出一行「<標籤> 樣本數=<數量> 能源=<能源>」。只有 Rover 型態的引數會選到這個版本。
    public static void print(String label, Rover rover) {
        System.out.println(label + " 樣本數=" + rover.getSamples() + " 能源=" + rover.getEnergy());
    }

    // print（手動清點）：直接用整數印出一行「<標籤> 樣本數=<數量>」。
    public static void print(String label, int samples) {
        System.out.println(label + " 樣本數=" + samples);
    }

    // print（快速查詢）：只收一個引數，用固定標籤「快速查詢」轉交給共用報表。
    public static void print(SampleSource source) {
        // 使用固定標籤。以介面型態轉交後仍會印共用格式。
        print("快速查詢", source);
    }
}
