// 類別：ParcelDemo（範例一的執行入口；claude 依教師要求新增）
// 意義：示範抽象類別 Parcel 可以當作變數與參數的型態，但實際物件一定是具體子類別。
// 作用：建立一般包裹與快遞包裹各一件，交給同一個 show 方法印出運費。
//   先預測兩行輸出，再執行比對。
public class ParcelDemo {
    // main：程式進入點。
    //   1. 兩個變數的宣告型態都是 Parcel，實際物件分別是 NormalParcel 和 ExpressParcel。
    //   2. 兩件包裹重量相同，請預測運費是否也相同，並說明是哪個方法決定了差別。
    public static void main(String[] args) {
        Parcel books = new NormalParcel("書籍", 3);
        Parcel medicine = new ExpressParcel("藥品", 3);

        show(books);
        show(medicine);

        // 編譯實驗（請在 experiments 副本操作，正式檔案保持可編譯）：
        //   把下一行開頭的 // 刪掉後重新編譯。先預測編譯器會不會接受，再讀第一個錯誤訊息。
        // Parcel unknown = new Parcel("未分類", 1);
    }

    // show：印出一件包裹的名稱、重量與運費。
    //   參數型態是抽象類別 Parcel，所以任何 Parcel 的具體子類別物件都能傳進來。
    static void show(Parcel parcel) {
        System.out.println(parcel.getName() + " " + parcel.getWeight()
                + "公斤 運費=" + parcel.fee() + "元");
    }
}
