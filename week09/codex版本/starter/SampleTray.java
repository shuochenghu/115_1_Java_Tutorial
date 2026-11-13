// 類別：SampleTray（樣本盒）
// 意義：地面站的樣本盒，只保存一個固定的樣本數量；它不是探測車，和 Rover 沒有繼承關係。
// 作用：實作 SampleSource 介面後，就能和探測車一起傳給同一個報表方法。
// 使用約定：建構時傳入不為負的小整數。
public class SampleTray {
    // samples：盒中的樣本數。final 表示建構後不再改變，所以讀取幾次都一樣。
    private final int samples;

    // 建構子：設定盒中的樣本數。
    public SampleTray(int samples) {
        this.samples = samples;
    }

    // TODO 3c：接上共同介面並提供樣本數。
}
