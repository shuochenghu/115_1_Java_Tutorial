// 教師參考，學生遷移題不發答案。精細採樣每次探索只取1份。
public class SurveyRover extends Rover {
    public SurveyRover(int energy, int samples) {
        super(energy, samples);
    }

    @Override
    protected int exploreYield() {
        return 1;
    }
}
