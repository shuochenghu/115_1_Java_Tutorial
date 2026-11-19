// 類別：Suitcase（範例二：實作 Weighable 的類別之一；claude 依教師要求新增）
// 意義：一個行李箱，重量＝空箱重量＋已裝進去的物品重量。
// 作用：用 implements Weighable 承諾「我能回報重量」，於是可以放上 ScaleDemo 的秤。
//   它和 Cat 沒有共同的父類別，兩者唯一的共同點就是都實作了 Weighable。
public class Suitcase implements Weighable {
    // emptyWeight：空行李箱本身的重量（公斤），建立後不變。
    private final int emptyWeight;
    // contentWeight：目前裝進去的物品總重量（公斤），一開始是 0，每次 pack 會增加。
    private int contentWeight;

    // Suitcase：建構子。收到空箱重量，裝載量從 0 開始。
    public Suitcase(int emptyWeight) {
        this.emptyWeight = emptyWeight;
        contentWeight = 0;
    }

    // pack：把一件物品放進行李箱。只接受正數；0 或負數不改變重量。
    //   這是行李箱自己的方法，Weighable 介面裡沒有它。
    public void pack(int kg) {
        if (kg > 0) {
            contentWeight += kg;
        }
    }

    // getWeight：實作介面方法。重量由兩個欄位相加得到。
    //   必須寫 public：介面方法是 public，實作時不能把存取範圍變小。
    @Override
    public int getWeight() {
        return emptyWeight + contentWeight;
    }
}
