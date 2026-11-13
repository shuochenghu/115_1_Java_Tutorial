// 類別：SampleTray（樣本盒）
// 意義：地面站的樣本盒，只保存一個固定的樣本數量；它不是探測車，和 Rover 沒有繼承關係。
// 作用：實作 SampleSource 介面後，就能和探測車一起傳給同一個報表方法。
// 使用約定：建構時傳入不為負的小整數。
public class SampleTray implements SampleSource {
    // samples：盒中的樣本數。final 表示建構後不再改變，所以讀取幾次都一樣。
    private final int samples;

    // 建構子：設定盒中的樣本數。
    public SampleTray(int samples) {
        this.samples = samples;
    }

    // getSamples：回傳盒中的樣本數。
    // TODO 3c 教師答案：實作介面規定的 getSamples()。
    // 介面裡的方法都是 public，實作時不能縮小存取範圍，所以這裡一定要寫 public。
    @Override
    public int getSamples() {
        return samples;
    }
}
