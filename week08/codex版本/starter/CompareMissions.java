// 第八週主入口：在本人的java-08編譯src/*.java，再執行CompareMissions。
// 固定腳本，不讀命令列參數。每個新任務配新車，只run一次。
public class CompareMissions {
    public static void main(String[] args) {
        int[] script = {1, 1, 2};
        // TODO 3：兩次分別建立Rover型態參考、新車與新Mission。
        // 一般車與重型車各run同一份script一次，再呼叫已提供的show。
        System.out.println("NOT_READY: create two independent missions");
    }

    // 已提供的報表程式，這裡不判斷車種，也不執行行動。
    static void show(String label, Rover rover, Mission mission, int scriptLength) {
        System.out.println("=== " + label + " ===");
        MissionLog.printReplay(mission);
        System.out.println("counts[0..3]="
                + java.util.Arrays.toString(mission.countCommands()));
        MissionLog.printSummary(scriptLength, mission);
        System.out.println("final energy=" + rover.getEnergy()
                + ", samples=" + rover.getSamples()
                + ", actionCount=" + rover.getActionCount()
                + " -> " + mission.getOutcome());
        System.out.println("battery=" + rover.getEnergy() / 20.0 * 100);
    }
}
