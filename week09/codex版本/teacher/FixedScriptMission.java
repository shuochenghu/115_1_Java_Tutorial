// 類別：FixedScriptMission（教師工具）
// 意義：讓教師自訂初始能源、樣本與指令，觀察任務結果的程式入口；不是學生必寫作業。
// 作用：把命令列參數轉成整數，檢查初始狀態合法後，交給 MissionLog.runMission 執行並印出報表。
// 用法：java FixedScriptMission 能源 樣本數 指令1 指令2 …
public class FixedScriptMission {
    // main：程式進入點。前兩個參數是初始能源與樣本數，其餘參數依序成為指令腳本。
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("用法：FixedScriptMission 能源 樣本數 [指令 ...]");
            return;
        }
        int energy = Integer.parseInt(args[0]);
        int samples = Integer.parseInt(args[1]);
        if (energy < 0 || energy > 20 || samples < 0) {
            System.out.println("初始狀態無效");
            return;
        }
        int[] script = new int[args.length - 2];
        for (int i = 0; i < script.length; i++) {
            script[i] = Integer.parseInt(args[i + 2]);
        }
        MissionLog.runMission(script, energy, samples);
    }
}
