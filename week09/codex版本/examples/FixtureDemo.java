// 類別：FixtureDemo（第 9 週閱讀範例）
// 意義：示範兩種建立「介面實作物件」的寫法。
// 作用：用兩種寫法，各建立一個「固定回傳 2 份樣本」的 SampleSource。
//   寫法 1：先在 FixtureDemo 裡宣告一個有名字的類別 FixedSampleSource，再 new 它。
//   寫法 2：匿名類別，在 new 的同時把方法寫在大括號裡，這個類別沒有名字、只用這一次。
public class FixtureDemo {
    // 類別：FixedSampleSource（巢狀類別）
    // 意義：固定回傳 2 份樣本的 SampleSource 實作，當作測試資料。
    // 作用：寫法 1：這個類別只有本示範用得到，所以直接宣告在 FixtureDemo 裡面。
    private static class FixedSampleSource implements SampleSource {
        // getSamples：實作介面方法，固定回傳 2。
        @Override
        public int getSamples() {
            return 2;
        }
    }

    // main：程式進入點。用兩種寫法各建立一個樣本來源，再交給 show 印出。
    public static void main(String[] args) {
        SampleSource named = new FixedSampleSource();
        // 寫法 2：new SampleSource() { ... } 大括號裡的內容，就是這個沒有名字的類別。
        SampleSource anonymous = new SampleSource() {
            // getSamples：實作介面方法，固定回傳 2。
            @Override
            public int getSamples() {
                return 2;
            }
        };

        show(named);
        show(anonymous);
    }

    // show：印出任何 SampleSource 的樣本數；參數型態是介面，所以兩種寫法的物件都能傳入。
    private static void show(SampleSource source) {
        System.out.println("測試資料樣本數=" + source.getSamples());
    }
}
