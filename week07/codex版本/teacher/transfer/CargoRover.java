// 獨立遷移題的教師參考：貨物不是科學樣本，所以有自己的 cargo 狀態。
// 裝貨不耗能源、不增加成功行動次數，但仍遵守探測車停止規則。
public class CargoRover extends Rover {
    private int cargo;

    public CargoRover(int energy, int samples) {
        super(energy, samples);
        cargo = 0;
    }

    public int getCargo() { return cargo; }

    public String load(int amount) {
        if (isStopped()) { return "STOPPED"; }
        if (amount <= 0 || amount > 3 - cargo) { return "REJECTED"; }
        cargo += amount;
        return "OK";
    }
}
