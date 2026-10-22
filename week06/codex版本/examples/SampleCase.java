// F1 設計卡的教師參考；學生必做設計與審查，實作此類別為課後選做。
public class SampleCase {
    private int capacity;
    private int count;

    public SampleCase(int capacity) {
        if (capacity < 1) {
            capacity = 1;
        } else if (capacity > 8) {
            capacity = 8;
        }
        this.capacity = capacity;
        count = 0;
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

    public boolean store(int amount) {
        // 先比較剩餘空間再相加；超量必須整批拒絕，也避免大數相加溢位。
        if (amount <= 0 || amount > getFreeSpace()) {
            return false;
        }
        count += amount;
        return true;
    }

    public int unload() {
        int unloaded = count;
        count = 0;
        return unloaded;
    }
}
