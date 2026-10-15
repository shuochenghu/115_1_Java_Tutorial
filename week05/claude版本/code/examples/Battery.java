// 範例 2（claude 版本）：探測車上的備用電池模組。
// 封裝：欄位 private，外部只能透過方法改變電量，方法負責守住「0 <= level <= capacity」。
public class Battery {
    private final int capacity;   // final：建構子指定後就不能再改
    private int level;

    // 建構子：物件一產生就要是合法狀態。參數與欄位同名時，this.level 指欄位，level 指參數。
    public Battery(int capacity, int level) {
        if (capacity < 1) {
            capacity = 1;
        }
        this.capacity = capacity;
        // 超出範圍的初始電量改成最接近的合法值；本週尚未教例外處理。
        if (level < 0) {
            level = 0;
        } else if (level > capacity) {
            level = capacity;
        }
        this.level = level;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLevel() {
        return level;
    }

    // 電量不足或數量不合理時拒絕，電量維持不變。
    public boolean use(int amount) {
        if (amount <= 0 || amount > level) {
            return false;
        }
        level -= amount;
        return true;
    }

    // 回傳實際充入的量；充滿後多的部分不會存進去。
    public int charge(int amount) {
        if (amount <= 0) {
            return 0;
        }
        int before = level;
        level += amount;
        if (level > capacity) {
            level = capacity;
        }
        return level - before;
    }
}
