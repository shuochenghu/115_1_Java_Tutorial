import java.util.Scanner;

/** 教師測試入口；前兩個整數是測試設定，後面才是任務指令。 */
public class FixedStateMission {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int energy = input.nextInt();
        int samples = input.nextInt();
        // actionCount 固定從 0 開始；此入口只讀兩項初始狀態。
        RoverMission.runMission(input, energy, samples);
        input.close();
    }
}
