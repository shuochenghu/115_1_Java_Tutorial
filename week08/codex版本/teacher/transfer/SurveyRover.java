// 類別：SurveyRover（遷移題教師參考：精細採樣探測車）
// 意義：一種探測車（extends Rover），精細採樣，每次探索只取 1 份樣本。
// 作用：示範只覆寫 exploreYield() 就能做出新車種；學生遷移題不發答案。
public class SurveyRover extends Rover {
    // 建構子：能源與樣本交給父類別 Rover 保存。
    public SurveyRover(int energy, int samples) {
        super(energy, samples);
    }

    // exploreYield：覆寫 Rover 的同名方法，探索一次只得 1 份。
    @Override
    protected int exploreYield() {
        return 1;
    }
}
