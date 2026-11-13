// 類別：SampleReport（樣本報表）
// 意義：地面站的報表工具，所有方法都叫 print，靠「參數不同」區分（過載）。
// 作用：呼叫時，編譯器依引數的個數與宣告型態，選出要用哪一個 print。
// 目前只有共用報表一個版本；本週要再新增兩個過載。
public class SampleReport {
    // print（共用報表）：印出一行「<標籤> 樣本數=<數量>」。任何實作 SampleSource 的物件都能傳入。
    public static void print(String label, SampleSource source) {
        // 共用報表只讀取樣本數，適用於車輛及樣本盒。
        System.out.println(label + " 樣本數=" + source.getSamples());
    }

    // TODO 1：新增 Rover 版的詳報，讀取樣本數與能源。
    // TODO 3：新增 int 版，並由共用報表轉交相同的輸出格式。
}
