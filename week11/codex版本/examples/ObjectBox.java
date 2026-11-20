// 類別：ObjectBox（Object 盒子，對照用的獨立短例，可直接執行）
// 意義：學泛型之前常見的寫法。欄位型態寫成 Object，所以字串、整數都放得進去。
// 作用：和 Box<T> 對照。放入很方便，但取出時編譯器只知道「這是一個 Object」，
//       呼叫者必須自己寫 (String)、(Integer) 轉型；轉錯型態時，要等到「執行」才失敗。
//       Box<T> 則讓編譯器記得盒中的型態，同樣的錯誤在「編譯」時就被擋下。
// 補充：本檔由 Claude 依教師指示補充（2026-11-19），對應 D2 模擬 AI 的轉型問題。
public class ObjectBox {
    // value：盒中保存的值。型態是 Object，因此任何物件都能放進來，編譯器不會記得原本的型態。
    private Object value;

    // 建構子：保存傳入的值。傳入 7 時會自動包成 Integer 物件，再以 Object 保存。
    public ObjectBox(Object value) {
        this.value = value;
    }

    // get：交回保存的值。回傳型態是 Object，所以呼叫者拿到後通常要自己轉型才能使用。
    public Object get() {
        return value;
    }

    // main：程式進入點。分別用 Object 盒子保存字串與整數，取出時都要先轉型。
    public static void main(String[] args) {
        ObjectBox message = new ObjectBox("採樣完成");
        ObjectBox number = new ObjectBox(7);

        // get() 只保證回傳 Object。寫 (String)、(Integer) 是「呼叫者向編譯器保證」盒中是這個型態。
        String text = (String) message.get();
        Integer count = (Integer) number.get();
        System.out.println("Object 盒 訊息=" + text);
        System.out.println("Object 盒 數量=" + count);

        // 執行期實驗：只在副本取消下一行註解，再編譯、執行。
        // 這一行「可以通過編譯」，因為編譯器只知道 get() 是 Object，相信了你寫的 (Integer)。
        // 但 message 盒中其實是字串，所以要到執行這一行時才失敗。和 TypedBoxDemo 的編譯實驗比較失敗時機。
        // Integer wrongCount = (Integer) message.get();
    }
}
