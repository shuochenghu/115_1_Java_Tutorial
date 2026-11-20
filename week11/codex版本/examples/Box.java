// 類別：Box<T>（泛型盒子，獨立短例）
// 意義：保存一個值的盒子。T 是「型態參數」，代表一個先不決定的型態，不是存放資料的變數。
// 作用：建立盒子時才指定 T，例如 Box<String>、Box<Integer>。編譯器因此記得盒中的型態，
//       get() 直接交回那個型態，呼叫者不必像 ObjectBox 一樣自己轉型。
// 使用約定：傳入的物件參考不為 null。
public class Box<T> {
    // value：盒中保存的值。型態是 T，在 Box<String> 中就是 String，在 Box<Integer> 中就是 Integer。
    private T value;

    // 建構子：保存傳入的值。參數型態也是 T，所以 Box<String> 只接受字串。
    public Box(T value) {
        this.value = value;
    }

    // get：回傳原本保存的值，型態是 T。若 T 是物件型態，交回的是同一個物件參考，不是複製品。
    public T get() {
        return value;
    }
}
