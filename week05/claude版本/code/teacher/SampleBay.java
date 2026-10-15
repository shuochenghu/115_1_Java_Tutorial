// 第五週延伸參考解答（claude 版本）：講義 E 段「樣本儲存艙」需求轉換成的類別。
// 學生必做的是設計卡與 Prompt；實作這個類別屬於課後選做。
public class SampleBay {
    private final int capacity;
    private int count;

    // 容量限制在 1..10；超出範圍改成最接近的合法值。新的儲存艙一律是空的。
    public SampleBay(int capacity) {
        if (capacity < 1) {
            capacity = 1;
        } else if (capacity > 10) {
            capacity = 10;
        }
        this.capacity = capacity;
        this.count = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCount() {
        return count;
    }

    public int getFreeSpace() {
        return capacity - count;
    }

    // 數量不是正數，或放不下時，整批拒絕且內容不變。
    public boolean store(int amount) {
        if (amount <= 0 || amount > getFreeSpace()) {
            return false;
        }
        count += amount;
        return true;
    }

    // 回傳卸下的數量，並清空儲存艙。
    public int unload() {
        int unloaded = count;
        count = 0;
        return unloaded;
    }
}
