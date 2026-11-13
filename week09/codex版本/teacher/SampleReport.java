// 類別：SampleReport（樣本報表）
// 意義：地面站的共用報表工具。
// 作用：用一個 static 方法印出任何樣本來源的樣本數，每次一行。
// 參數宣告成介面 SampleSource，而不是 Rover 或 SampleTray：
// 只要類別寫了 implements SampleSource（車和樣本盒都有），它的物件就能傳進來。
// 報表只呼叫 getSamples()，所以不需要知道傳進來的是車還是樣本盒。
public class SampleReport {
    // print：印出一行「<標籤> 樣本數=<數量>」。
    // TODO 4 教師答案：一個方法同時服務車輛與樣本盒。
    public static void print(String label, SampleSource source) {
        System.out.println(label + " 樣本數=" + source.getSamples());
    }
}
