// 類別：StandardRover（一般探測車）
// 意義：一種具體的探測車（extends Rover），探索一次得 2 份樣本。
// 作用：Rover 改成抽象類別後，用來建立「一般車」的物件；建構資料仍交給父類別 Rover 保存。
public class StandardRover extends Rover {
    // 建構子：能源與樣本交給父類別 Rover 保存。
    public StandardRover(int energy, int samples) {
        super(energy, samples);
    }

    // exploreYield：回傳一般車的探索產量 2。
    // TODO 1 教師答案：覆寫 Rover 的 exploreYield()，一般車每次探索得 2 份。
    @Override
    protected int exploreYield() {
        return 2;
    }
}
