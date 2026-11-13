// 類別：StepListenerDemo（教師短示範）
// 意義：示範「通知接收者」的寫法：任務完成後回放日誌，逐筆通知一個接收者。
// 作用：用巢狀介面 StepListener 定義通知方法，再用匿名類別做出「能源偏低就提醒」的接收者。
public class StepListenerDemo {
    // 介面：StepListener（步驟接收者）
    // 意義：「想知道每一步發生什麼事的人」的共同約定。
    // 作用：規定接收者要提供 onStep 方法；只在本示範內使用，所以宣告為 private 巢狀介面。
    private interface StepListener {
        // onStep：收到第 stepNumber 步（從 1 開始）的紀錄時要做的事。
        void onStep(int stepNumber, MissionStep step);
    }

    // main：程式進入點。先讓重型車執行任務，再把每一筆日誌交給接收者。
    public static void main(String[] args) {
        Mission mission = new Mission(new HeavyRover(14, 0));
        mission.run(new int[] {1, 1, 2});

        // alarm：用匿名類別實作 StepListener；某一步執行後能源 6 格以下就印出提醒。
        StepListener alarm = new StepListener() {
            // onStep：某一步執行後能源在 6 格以下，就印出步驟編號與能源。
            @Override
            public void onStep(int stepNumber, MissionStep step) {
                if (step.getEnergy() <= 6) {
                    System.out.println("能源偏低，步驟=" + stepNumber + " 能源=" + step.getEnergy());
                }
            }
        };

        for (int i = 0; i < mission.getStepCount(); i++) {
            alarm.onStep(i + 1, mission.getStep(i));
        }
    }
}
