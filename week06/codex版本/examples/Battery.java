// 類別：Battery（第 6 週範例：電池）
// 意義：一顆可以被使用和充電的電池，保存目前能源。
// 作用：在「關聯與相依」範例中被 Lamp 與 Charger 使用；它是獨立範例，沒有任務的成功、返回或停止規則。
// 使用約定：建構時 energy 為 0～20；use 的 amount 為正整數。
public class Battery {
    // energy：目前能源格數（0～20）。
    private int energy;
    // 建構子：設定電池的初始能源。
    public Battery(int energy) { this.energy = energy; }
    // getEnergy：回傳目前能源。
    public int getEnergy() { return energy; }
    // use：使用 amount 格能源。能源不夠就拒絕並回傳 false，能源不變；成功扣除並回傳 true。
    public boolean use(int amount) {
        if (amount > energy) { return false; }
        energy -= amount;
        return true;
    }
    // charge：充電一次，加 5 格、最多 20 格。已滿時拒絕並回傳 false；成功回傳 true。
    public boolean charge() {
        if (energy == 20) { return false; }
        energy += 5;
        if (energy > 20) { energy = 20; }
        return true;
    }
}
