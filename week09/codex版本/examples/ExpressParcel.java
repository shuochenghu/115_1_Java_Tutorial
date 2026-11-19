// 類別：ExpressParcel（範例一：Parcel 的具體子類別；claude 依教師要求新增）
// 意義：快遞包裹，每公斤 25 元，比一般包裹貴。
// 作用：和 NormalParcel 一樣只提供每公斤費率；運費公式完全沿用 Parcel.fee()。
public class ExpressParcel extends Parcel {
    // ExpressParcel：建構子。名稱與重量交給父類別 Parcel 的建構子保存。
    public ExpressParcel(String name, int weight) {
        super(name, weight);
    }

    // ratePerKg：覆寫父類別的抽象方法。快遞包裹每公斤 25 元。
    @Override
    protected int ratePerKg() {
        return 25;
    }
}
