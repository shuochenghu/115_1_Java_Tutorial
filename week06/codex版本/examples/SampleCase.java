// 類別：SampleCase（樣本盒）
// 意義：可存放樣本的盒子，有固定容量（1～8 份）。
// 作用：本週「AI 設計判讀」活動的參考實作；示範如何用方法守住「不能超過容量」的規則。
//       它是獨立範例，沒有接進 Rover 或 Mission。學生實作此類別為課後選做。
public class SampleCase {
    // capacity：盒子最多能放幾份樣本，建立後不再改變。
    private int capacity;
    // count：目前放了幾份樣本（0～capacity）。
    private int count;

    // 建構子：設定容量並讓盒子從空的開始。
    public SampleCase(int capacity) {
        // 樣本盒需求規定容量只能是 1～8：傳入小於 1 就當成 1，大於 8 就當成 8。
        // 注意：Rover 的作法不同，它假設呼叫端只傳合法值，不會自動修改傳入的參數。
        if (capacity < 1) {
            capacity = 1;
        } else if (capacity > 8) {
            capacity = 8;
        }
        this.capacity = capacity;
        count = 0;
    }

    // getCapacity：回傳容量。
    public int getCapacity() {
        return capacity;
    }

    // getCount：回傳目前樣本數。
    public int getCount() {
        return count;
    }

    // getFreeSpace：回傳還能再放幾份（容量 − 目前樣本數）。
    public int getFreeSpace() {
        return capacity - count;
    }

    // store：放入一批樣本。amount 必須是正數且放得下，否則整批拒絕、回傳 false，樣本數不變；成功回傳 true。
    public boolean store(int amount) {
        // 先比較剩餘空間再相加；超量必須整批拒絕，也避免大數相加溢位。
        if (amount <= 0 || amount > getFreeSpace()) {
            return false;
        }
        count += amount;
        return true;
    }

    // unload：一次取出全部樣本，回傳取出的份數，盒子變成空的。
    public int unload() {
        int unloaded = count;
        count = 0;
        return unloaded;
    }
}
