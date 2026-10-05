import java.util.Scanner;

public class RoverMission {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        runMission(input, 20, 0);
        input.close();
    }

    // 教師可由獨立入口傳入初始值；學生只需在提供的骨架補行動規則。
    static void runMission(Scanner input, int energy, int samples) {
        int actionCount = 0;
        boolean running = true;
        showDashboard(energy, samples, actionCount);
        while (running && energy > 0 && samples < 5) {
            System.out.println("1 Explore | 2 Scan | 3 Charge | 0 Return");
            int command = input.nextInt();
            switch (command) {
                case 1:
                    if (energy >= 6) {
                        energy -= 6;
                        samples += 2;
                        actionCount++;
                    } else {
                        System.out.println("NOT_ENOUGH_ENERGY");
                    }
                    break;
                case 2:
                    if (energy >= 2) {
                        energy -= 2;
                        samples++;
                        actionCount++;
                    } else {
                        System.out.println("NOT_ENOUGH_ENERGY");
                    }
                    break;
                case 3:
                    if (energy < 20) {
                        energy += 5;
                        if (energy > 20) {
                            energy = 20;
                        }
                        actionCount++;
                    } else {
                        System.out.println("ALREADY_FULL");
                    }
                    break;
                case 0:
                    // break 只離開 switch，旗標讓下一次 while 判斷停止。
                    running = false;
                    break;
                default:
                    System.out.println("UNKNOWN_COMMAND");
                    break;
            }
            // 拒絕及返回時也顯示，可觀察到狀態沒有被修改。
            showDashboard(energy, samples, actionCount);
        }
        // 同一行動可能同時耗盡能源與完成任務，依需求優先判斷成功。
        if (samples >= 5) {
            System.out.println("SUCCESS");
        } else if (energy == 0) {
            System.out.println("EXHAUSTED");
        } else {
            System.out.println("RETURNED");
        }
    }

    static double calculateBatteryPercent(int energy) {
        return energy / 20.0 * 100;
    }

    static void showDashboard(int energy, int samples, int actionCount) {
        System.out.println("energy=" + energy + ", samples=" + samples
                + ", actionCount=" + actionCount
                + ", battery=" + calculateBatteryPercent(energy));
    }
}
