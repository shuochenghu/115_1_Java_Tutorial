// 類別：TypedBoxDemo（泛型盒子的示範入口）
// 意義：用同一份 Box<T> 分別保存字串與整數，觀察取回時的型態。
// 作用：先預測 get() 的型態與兩行輸出，再執行。這是獨立短例，不使用探測車，也沒有樣本群組的統計答案。
public class TypedBoxDemo {
    // main：程式進入點。建立兩個型態引數不同的盒子，取回後各印一行。
    public static void main(String[] args) {
        Box<String> message = new Box<>("採樣完成");
        Box<Integer> number = new Box<>(7);

        // 編譯器分別知道 get() 回傳 String 與 Integer，所以不需要像 ObjectBox 一樣自己轉型。
        String text = message.get();
        int count = number.get(); // Integer 自動拆箱為 int，本週只需讀懂這個用法。
        System.out.println("訊息=" + text);
        System.out.println("數量=" + count);

        // 編譯實驗：每次只在副本取消其中一行註解，再編譯。兩行都會在「編譯」時被拒絕。
        // Box<int> wrong = new Box<>(7);        // 型態引數不能直接用基本型態 int，要用 Integer。
        // Integer wrong = message.get();       // message.get() 是 String，不能直接交給 Integer。
    }
}
