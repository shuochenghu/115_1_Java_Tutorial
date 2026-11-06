// 學生須自行建立同等規模的入口；不要求再寫整套報表。
public class SurveyRoverTry {
    public static void main(String[] args) {
        Rover survey = new SurveyRover(20, 0);
        Mission mission = new Mission(survey);
        mission.run(new int[] {1, 1, 2});
        System.out.println(survey.getEnergy() + "/" + survey.getSamples()
                + "/" + survey.getActionCount() + "/" + mission.getOutcome());

        Rover edge = new SurveyRover(6, 4);
        Mission edgeMission = new Mission(edge);
        edgeMission.run(new int[] {1, 3});
        System.out.println(edge.getEnergy() + "/" + edge.getSamples()
                + "/" + edge.getActionCount() + "/" + edgeMission.getOutcome());

        Rover low = new SurveyRover(5, 0);
        System.out.println(low.execute(1) + "/" + low.getEnergy()
                + "/" + low.getSamples() + "/" + low.getActionCount());
        Rover stopped = new SurveyRover(20, 5);
        System.out.println(stopped.execute(1) + "/" + stopped.getEnergy()
                + "/" + stopped.getSamples() + "/" + stopped.getActionCount());
    }
}
