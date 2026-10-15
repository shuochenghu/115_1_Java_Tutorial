import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// 同一任務規則，只新增逐筆保存；目前狀態維持具名變數，方便銜接第三週。
public class MissionLog {
    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        runMission(script, 20, 0);
    }

    // 教師測試入口；script 非 null，初始 energy 為 0..20、samples 非負。
    static void runMission(int[] script, int energy, int samples) {
        int actionCount = 0;
        boolean running = true;
        ArrayList<Integer> commandLog = new ArrayList<>();
        ArrayList<Integer> energyLog = new ArrayList<>();
        ArrayList<String> resultLog = new ArrayList<>();

        // 先判斷狀態，能源已耗盡或樣本已達標時，不再執行第一筆指令。
        for (int i = 0; i < script.length && running && energy > 0 && samples < 5; i++) {
            int command = script[i];
            String result;
            switch (command) {
                case 1:
                    if (energy >= 6) {
                        energy -= 6;
                        samples += 2;
                        actionCount++;
                        result = "OK";
                    } else {
                        result = "NOT_ENOUGH_ENERGY";
                    }
                    break;
                case 2:
                    if (energy >= 2) {
                        energy -= 2;
                        samples++;
                        actionCount++;
                        result = "OK";
                    } else {
                        result = "NOT_ENOUGH_ENERGY";
                    }
                    break;
                case 3:
                    if (energy < 20) {
                        energy += 5;
                        if (energy > 20) {
                            energy = 20;
                        }
                        actionCount++;
                        result = "OK";
                    } else {
                        result = "ALREADY_FULL";
                    }
                    break;
                case 0:
                    running = false;
                    result = "RETURN";
                    break;
                default:
                    result = "UNKNOWN_COMMAND";
                    break;
            }
            // 同一步三欄一起新增，包含拒絕與返回；能源記錄行動執行後的值。
            commandLog.add(command);
            energyLog.add(energy);
            resultLog.add(result);
        }

        String outcome;
        if (samples >= 5) {
            outcome = "SUCCESS";
        } else if (energy == 0) {
            outcome = "EXHAUSTED";
        } else if (!running) {
            outcome = "RETURNED";
        } else {
            // 固定腳本用完不等於返回；保留尚未完成這個新結果。
            outcome = "UNFINISHED";
        }
        printReplay(commandLog, energyLog, resultLog);
        System.out.println("counts[0..3]=" + Arrays.toString(countCommands(commandLog)));
        printSummary(script.length, energyLog, resultLog);
        System.out.println("final energy=" + energy + ", samples=" + samples
                + ", actionCount=" + actionCount + " -> " + outcome);
        System.out.println("battery=" + calculateBatteryPercent(energy));
    }

    static int[] countCommands(ArrayList<Integer> commandLog) {
        int[] counts = new int[4];
        // 日誌包含無效指令，先檢查範圍再用作索引。
        for (int command : commandLog) {
            if (command >= 0 && command < counts.length) {
                counts[command]++;
            }
        }
        return counts;
    }

    static void printReplay(ArrayList<Integer> commandLog, ArrayList<Integer> energyLog,
            ArrayList<String> resultLog) {
        System.out.println("=== replay ===");
        for (int i = 0; i < commandLog.size(); i++) {
            System.out.println("step " + (i + 1) + ": command=" + commandLog.get(i)
                    + ", result=" + resultLog.get(i) + ", energy=" + energyLog.get(i));
        }
    }

    static void printSummary(int scriptLength, ArrayList<Integer> energyLog,
            ArrayList<String> resultLog) {
        int ok = Collections.frequency(resultLog, "OK");
        int returned = Collections.frequency(resultLog, "RETURN");
        int steps = resultLog.size();
        int rejected = steps - ok - returned;
        int ignored = scriptLength - steps;
        System.out.println("steps=" + steps + ", ok=" + ok
                + ", rejected=" + rejected + ", ignored=" + ignored);
        // 空清單沒有最小值；這裡只統計每一步結束後的能源，不含初始能源。
        if (energyLog.isEmpty()) {
            System.out.println("lowest energy=N/A");
        } else {
            System.out.println("lowest energy=" + Collections.min(energyLog));
        }
    }

    static double calculateBatteryPercent(int energy) {
        return energy / 20.0 * 100;
    }
}
