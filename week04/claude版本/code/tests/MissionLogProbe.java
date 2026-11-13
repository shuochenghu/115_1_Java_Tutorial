import java.util.ArrayList;
import java.util.Arrays;

// 類別：MissionLogProbe（claude 版本測試輔助程式，不是學生作業）
// 意義：MissionLog 的 main 永遠從能源 20、樣本 0 出發，所以「能源耗盡」「能源不足」「空腳本」
//       這幾種情況只用命令列參數走不到。這個程式讓測試腳本自己指定初始能源和樣本數。
// 作用：讀入初始狀態與指令後，依照 MissionLog.main 相同的順序呼叫學生寫的
//       runMission、printReplay、countCommands、printSummary，印出和 main 同格式的報表，
//       讓測試腳本可以逐行比對。
// 用法：java MissionLogProbe 初始能源 初始樣本 指令1 指令2 …
//       例如 java MissionLogProbe 6 0 1 2 代表能源 6、樣本 0，依序執行探索、掃描。
public class MissionLogProbe {
    // main：程式進入點。
    // 1. 前兩個參數轉成初始能源與樣本數，其餘參數依序轉成指令腳本。
    // 2. 建立和 main 一樣的 state 陣列與三條日誌，交給 MissionLog.runMission 執行。
    // 3. 依 main 的順序印出回放、指令次數、摘要與最終狀態。
    // 參數少於兩個時印出用法並以結束碼 2 離開，讓測試腳本知道是呼叫方式錯誤而不是程式結果錯誤。
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("用法：MissionLogProbe 初始能源 初始樣本 [指令 ...]");
            System.exit(2);
        }
        int energy = Integer.parseInt(args[0]);
        int samples = Integer.parseInt(args[1]);
        int[] script = new int[args.length - 2];
        for (int i = 0; i < script.length; i++) {
            script[i] = Integer.parseInt(args[i + 2]);
        }

        // state 的三格依序是能源、樣本數、成功行動次數，和 MissionLog.main 的 {20, 0, 0} 相同排列。
        int[] state = {energy, samples, 0};
        ArrayList<Integer> commandLog = new ArrayList<>();
        ArrayList<Integer> energyLog = new ArrayList<>();
        ArrayList<String> resultLog = new ArrayList<>();

        String outcome = MissionLog.runMission(script, state, commandLog, energyLog, resultLog);

        MissionLog.printReplay(commandLog, energyLog, resultLog);
        int[] counts = MissionLog.countCommands(commandLog);
        System.out.println("指令次數[0..3]=" + Arrays.toString(counts));
        MissionLog.printSummary(script, energyLog, resultLog);
        System.out.println("最終能源=" + state[MissionLog.ENERGY] + ", 樣本數=" + state[MissionLog.SAMPLES]
                + ", 行動次數=" + state[MissionLog.ACTIONS] + " -> " + outcome);
    }
}
