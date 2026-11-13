import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// 類別：MissionLog（第 4 週探測車任務日誌）
// 意義：一次火星探測車任務的完整程式；任務規則沿用第 3 週，本週新增「逐筆記錄每一步」。
// 作用：依固定腳本執行指令，用三條 ArrayList 分別記下每一步的指令、能源、結果，
//       最後印出回放、指令次數、摘要與最終狀態。
// 目前狀態（能源、樣本、行動次數）仍是方法裡的具名變數，方便和第 3 週對照。
public class MissionLog {
    // main：程式進入點。準備固定腳本 {1, 1, 2}（探索、探索、掃描），
    // 以能源 20、樣本 0 開始執行一次任務。
    public static void main(String[] args) {
        int[] script = {1, 1, 2}; // 探索 → 探索 → 掃描。
        runMission(script, 20, 0);
    }

    // runMission：執行一次完整任務並印出全部報表。
    // 參數 script 是指令腳本（不可為 null），energy 是初始能源（0～20），samples 是初始樣本數（不可為負）。
    // 教師工具 FixedScriptMission 與 MissionLogCheck 也從這個方法進入，所以方法名稱和參數要保持不變。
    static void runMission(int[] script, int energy, int samples) {
        // actionCount：成功行動的次數；running：還沒收到返回指令時為 true。
        // 三條清單的同一個索引 i 屬於同一步：指令、執行後能源、結果。
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
                case 1: // 指令 1：探索，消耗 6 格能源，取得 2 份樣本。
                    if (energy >= 6) {
                        energy -= 6;
                        samples += 2;
                        actionCount++;
                        result = "成功";
                    } else {
                        result = "能源不足";
                    }
                    break;
                case 2: // 指令 2：掃描，消耗 2 格能源，取得 1 份樣本。
                    if (energy >= 2) {
                        energy -= 2;
                        samples++;
                        actionCount++;
                        result = "成功";
                    } else {
                        result = "能源不足";
                    }
                    break;
                case 3: // 指令 3：充電，增加 5 格能源，最多 20 格；已滿時拒絕充電。
                    if (energy < 20) {
                        energy += 5;
                        if (energy > 20) {
                            energy = 20;
                        }
                        actionCount++;
                        result = "成功";
                    } else {
                        result = "能源已滿";
                    }
                    break;
                case 0: // 指令 0：返回，停止讀取後續指令，不增加成功行動次數。
                    running = false;
                    result = "返回";
                    break;
                default: // 其他整數：無效指令，不改變能源、樣本或成功行動次數。
                    result = "無效指令";
                    break;
            }
            // TODO 2：把 command、energy、result 加到對應清單，拒絕與返回也要記錄。
        }

        String outcome;
        if (samples >= 5) {
            outcome = "任務成功";
        } else if (energy == 0) {
            outcome = "能源耗盡";
        } else if (!running) {
            outcome = "已返回";
        } else {
            // 固定腳本用完不等於返回；保留尚未完成這個新結果。
            outcome = "任務未完成";
        }
        printReplay(commandLog, energyLog, resultLog);
        System.out.println("指令次數[0..3]=" + Arrays.toString(countCommands(commandLog)));
        printSummary(script.length, energyLog, resultLog);
        System.out.println("最終能源=" + energy + ", 樣本數=" + samples
                + ", 行動次數=" + actionCount + " -> " + outcome);
        System.out.println("電量百分比=" + calculateBatteryPercent(energy));
    }

    // countCommands：統計日誌中指令 0～3 各出現幾次，回傳長度 4 的陣列。
    // counts[0] 是返回次數、counts[1] 是探索次數，依此類推；無效指令不計入。
    static int[] countCommands(ArrayList<Integer> commandLog) {
        int[] counts = new int[4];
        // TODO 1：走訪 commandLog；指令在 0..3 才增加 counts[command]。
        return counts;
    }

    // printReplay：依序印出每一步的指令、結果與執行後能源（任務回放）。
    // 三條清單用同一個索引讀取，才能對到同一步。
    static void printReplay(ArrayList<Integer> commandLog, ArrayList<Integer> energyLog,
            ArrayList<String> resultLog) {
        System.out.println("=== 任務回放 ===");
        // TODO 3：用相同索引讀三條清單，格式見講義 E。
    }

    // printSummary：印出摘要（步數、成功、拒絕、略過）與最低能源。
    // scriptLength 是腳本長度，用來算出「沒讀到」而略過的指令數。
    static void printSummary(int scriptLength, ArrayList<Integer> energyLog,
            ArrayList<String> resultLog) {
        // TODO 4：用 frequency 計算 成功、返回，印出 steps、ok、rejected、ignored。
        System.out.println("待辦 4：印出摘要");
        // TODO 5：先用 isEmpty 判斷；空清單印 無紀錄，否則用 min 印最小能源。
        System.out.println("待辦 5：印出最低能源");
    }

    // calculateBatteryPercent：把能源格數換算成百分比（20 格 = 100%）。
    // 除以 20.0 而不是 20，才不會變成整數除法。
    static double calculateBatteryPercent(int energy) {
        return energy / 20.0 * 100;
    }
}
