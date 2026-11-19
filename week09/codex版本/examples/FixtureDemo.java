// 類別：FixtureDemo（第 9 週範例三：巢狀類別與匿名類別）
// 意義：示範兩種建立「介面實作物件」的寫法。介面沿用範例二的 Weighable。
// 作用：用兩種寫法，各建立一個「固定回傳 2 公斤」的測試用 Weighable，交給同一個 show 印出。
//   寫法 1：先在 FixtureDemo 裡宣告一個有名字的類別 FixedWeight，再 new 它。
//   寫法 2：匿名類別，在 new 的同時把方法寫在大括號裡，這個類別沒有名字、只用這一次。
//   （claude 依教師要求修改：原本使用 SampleSource，改用範例二的 Weighable，讓三個範例各講一個概念。）
public class FixtureDemo {
    // 類別：FixedWeight（具名的 static 巢狀類別）
    // 意義：固定回傳 2 公斤的 Weighable 實作，當作測試資料。
    // 作用：寫法 1。這個類別只有本示範用得到，所以直接宣告在 FixtureDemo 裡面，不另開檔案。
    //   private 表示只有 FixtureDemo 用得到；static 表示建立它時不需要先有 FixtureDemo 物件。
    private static class FixedWeight implements Weighable {
        // getWeight：實作介面方法，固定回傳 2。
        @Override
        public int getWeight() {
            return 2;
        }
    }

    // main：程式進入點。用兩種寫法各建立一個 Weighable，再交給 show 印出。
    public static void main(String[] args) {
        Weighable named = new FixedWeight();
        // 寫法 2：new Weighable() { ... } 大括號裡的內容，就是這個沒有名字的類別。
        //   這不是「new 了一個介面」，而是建立「一個沒有名字、實作了 Weighable 的類別」的物件。
        //   整段是一個指定敘述，所以最後的大括號後面要有分號。
        Weighable anonymous = new Weighable() {
            // getWeight：實作介面方法，固定回傳 2。
            @Override
            public int getWeight() {
                return 2;
            }
        };

        show(named);
        show(anonymous);
    }

    // show：印出任何 Weighable 的重量；參數型態是介面，所以兩種寫法的物件都能傳入。
    private static void show(Weighable item) {
        System.out.println("測試資料重量=" + item.getWeight());
    }
}
