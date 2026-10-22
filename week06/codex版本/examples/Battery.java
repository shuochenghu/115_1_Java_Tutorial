// 獨立的電池範例，不是任務：沒有成功、返回或任務耗盡後停止的規則。
// 建構 energy 0..20；use 的 amount 為正整數。
public class Battery {
    private int energy;
    public Battery(int energy) { this.energy = energy; }
    public int getEnergy() { return energy; }
    public boolean use(int amount) {
        if (amount > energy) { return false; }
        energy -= amount;
        return true;
    }
    public boolean charge() {
        if (energy == 20) { return false; }
        energy += 5;
        if (energy > 20) { energy = 20; }
        return true;
    }
}
