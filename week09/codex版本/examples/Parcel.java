// 類別：Parcel（第 9 週範例一：抽象類別；claude 依教師要求新增）
// 意義：所有包裹的共同部分。每件包裹都有名稱和重量，運費也都用同一個公式計算。
// 作用：把共同欄位、建構子和運費流程只寫一次；「每公斤費率」留給子類別各自決定。
//   類別宣告了 abstract，所以程式不能直接 new Parcel(...)，只能建立 NormalParcel 或 ExpressParcel。
public abstract class Parcel {
    // BASE_FEE：每件包裹都要付的基本費，單位是元。
    public static final int BASE_FEE = 60;

    // name：包裹名稱，例如「書籍」。建立後不再改變。
    private final String name;
    // weight：包裹重量，單位是公斤。範例只使用 1 到 30 的整數。
    private final int weight;

    // Parcel：建構子。抽象類別雖然不能 new，仍然可以有建構子。
    //   子類別的建構子用 super(name, weight) 呼叫它，讓上面兩個 private 欄位得到初始值。
    public Parcel(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    // getName：回傳包裹名稱。
    public String getName() {
        return name;
    }

    // getWeight：回傳包裹重量（公斤）。
    public int getWeight() {
        return weight;
    }

    // fee：計算運費，公式是「基本費 + 重量 × 每公斤費率」。
    //   這段流程每種包裹都一樣，所以寫在父類別。其中的 ratePerKg() 是抽象方法，
    //   執行時會依「實際物件」呼叫子類別的版本（第 8 週學過的覆寫與動態派送）。
    //   例：假設某種包裹每公斤 20 元，2 公斤的運費就是 60 + 2 × 20 = 100 元。
    public int fee() {
        return BASE_FEE + weight * ratePerKg();
    }

    // ratePerKg：抽象方法，回傳每公斤費率（元）。
    //   它只有方法簽名，以分號結束，沒有大括號裡的內容。
    //   每個沒有宣告 abstract 的子類別都必須覆寫它；忘了寫，編譯器就會報錯。
    //   這就是抽象方法的用意：讓編譯器替我們檢查「每種包裹都交代了自己的費率」。
    protected abstract int ratePerKg();
}
