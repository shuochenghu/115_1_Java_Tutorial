// 類別：CargoRover（遷移題教師參考：貨運探測車）
// 意義：一種探測車（extends Rover），除了既有行動外還能裝載貨物。
// 作用：示範子類別新增自己的狀態與方法；貨物不是科學樣本，所以有自己的 cargo 欄位。
// 裝貨不耗能源、不增加成功行動次數，但仍遵守探測車停止規則。
public class CargoRover extends Rover {
    // cargo：目前裝載的貨物數量（0～3）。
    private int cargo;

    // 建構子：能源與樣本交給 Rover 保存，貨物從 0 開始。
    public CargoRover(int energy, int samples) {
        super(energy, samples);
        cargo = 0;
    }

    // getCargo：回傳目前貨物數量。
    public int getCargo() { return cargo; }

    // load：裝載 amount 件貨物（容量固定 3）。已停止回「任務已停止」；
    // amount 不是正數或裝不下回「已拒絕」；否則裝入並回「成功」。
    public String load(int amount) {
        if (isStopped()) { return "任務已停止"; }
        if (amount <= 0 || amount > 3 - cargo) { return "已拒絕"; }
        cargo += amount;
        return "成功";
    }
}
