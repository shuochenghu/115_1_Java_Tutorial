// 類別：ScaleDemo（範例二的執行入口；claude 依教師要求新增）
// 意義：示範介面當作參數型態：同一個 weigh 方法可以秤不同類別的物件。
// 作用：秤一個行李箱和一隻貓，再觀察「用介面型態的變數，只能呼叫介面宣告的方法」。
//   先預測四行輸出，再執行比對。
public class ScaleDemo {
    // main：程式進入點。
    //   1. 行李箱空箱 4 公斤，再裝 6 公斤和 3 公斤的物品。
    //   2. 貓重 5 公斤。行李箱和貓交給同一個 weigh。
    //   3. 用 Cat 型態的變數叫 meow；再用 Weighable 型態的變數參考同一隻貓，讀取重量。
    public static void main(String[] args) {
        Suitcase suitcase = new Suitcase(4);
        suitcase.pack(6);
        suitcase.pack(3);
        Cat cat = new Cat("咪咪", 5);

        weigh("行李箱", suitcase);
        weigh("貓", cat);

        System.out.println(cat.meow());
        Weighable item = cat;
        System.out.println("同一隻貓用 Weighable 變數讀取：" + item.getWeight() + "公斤");

        // 編譯實驗（請在 experiments 副本操作，一次只做一個）：先預測，再刪掉該行開頭的 // 編譯。
        //   實驗 1：item 的宣告型態是 Weighable，能不能呼叫 meow？
        // System.out.println(item.meow());
        //   實驗 2：範例一的包裹也有 getWeight()，但沒有宣告 implements Weighable，能不能放上秤？
        // weigh("包裹", new NormalParcel("書籍", 3));
    }

    // weigh：印出一行「標籤 重量=數字公斤」。
    //   參數型態是介面 Weighable：任何實作了 Weighable 的物件都能傳進來，
    //   而方法裡只能呼叫 Weighable 有宣告的 getWeight()。
    static void weigh(String label, Weighable item) {
        System.out.println(label + " 重量=" + item.getWeight() + "公斤");
    }
}
