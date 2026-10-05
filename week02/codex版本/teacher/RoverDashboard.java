public class RoverDashboard {
    public static void main(String[] args) {
        String roverName = "赤砂號";
        char zone = 'A';
        int energy = 7;
        boolean locked = false;

        double batteryPercent = calculateBatteryPercent(energy);
        int explorationCount = energy / 6;
        int remainingEnergy = energy % 6;
        boolean validEnergy = energy >= 0 && energy <= 20;
        boolean canExplore = energy >= 6 && !locked;

        System.out.println("探測車：" + roverName + "；區域：" + zone);
        System.out.println("能源：" + energy + "；電量百分比：" + batteryPercent);
        System.out.println("完整探索次數：" + explorationCount + "；剩餘能源：" + remainingEnergy);
        System.out.println("有效能源：" + validEnergy + "；能否探索：" + canExplore);
    }

    static double calculateBatteryPercent(int energy) {
        // 先進行浮點除法，才能保留不足一整顆電池的比例。
        // 本方法依題目契約接收 0 至 20，第三週才加入拒絕無效狀態的分支。
        return energy / 20.0 * 100;
    }
}
