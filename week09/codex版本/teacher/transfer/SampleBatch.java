// 類別：SampleBatch（遷移作業教師參考：兩批樣本）
// 意義：地面站收到的兩批樣本，報表讀到的是兩批的合計。
// 作用：示範新類別只要實作 SampleSource，就能直接用既有的報表方法；每批樣本數都是 0 至 100。
public class SampleBatch implements SampleSource {
    // firstCount：第一批的樣本數。
    private final int firstCount;
    // secondCount：第二批的樣本數。
    private final int secondCount;

    // 建構子：設定兩批的樣本數，之後不再改變。
    public SampleBatch(int firstCount, int secondCount) {
        this.firstCount = firstCount;
        this.secondCount = secondCount;
    }

    // getSamples：回傳兩批合計；每次讀取都得到同一個數字。
    @Override
    public int getSamples() {
        return firstCount + secondCount;
    }
}
