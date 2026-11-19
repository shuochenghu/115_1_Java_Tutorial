// 類別：NormalParcel（範例一：Parcel 的具體子類別；claude 依教師要求新增）
// 意義：一般包裹，每公斤 10 元。
// 作用：沿用 Parcel 的欄位與運費流程，只提供自己的每公斤費率。
//   因為它覆寫了所有抽象方法，所以是「具體類別」，可以用 new 建立物件。
public class NormalParcel extends Parcel {
    // NormalParcel：建構子。名稱與重量交給父類別 Parcel 的建構子保存，這裡不再宣告同樣的欄位。
    public NormalParcel(String name, int weight) {
        super(name, weight);
    }

    // ratePerKg：覆寫父類別的抽象方法。一般包裹每公斤 10 元。
    //   @Override 讓編譯器檢查方法名稱與參數是否真的對應到父類別的方法，打錯字會立刻報錯。
    @Override
    protected int ratePerKg() {
        return 10;
    }
}
