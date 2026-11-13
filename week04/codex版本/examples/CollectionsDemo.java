import java.util.ArrayList;
import java.util.Collections;

// 類別：CollectionsDemo（第 4 週範例）
// 意義：示範 Collections 工具類別處理 ArrayList 的常用方法。
// 作用：用 min、max、frequency 整理能源與結果清單，並示範排序前先複製一份，保留原本的時間順序。
public class CollectionsDemo {
    // main：程式進入點。建立能源清單與結果清單，依序印出最小值、最大值、次數與排序前後的差別。
    public static void main(String[] args) {
        ArrayList<Integer> energyLog = new ArrayList<>();
        energyLog.add(14);
        energyLog.add(8);
        energyLog.add(20);
        energyLog.add(6);
        System.out.println("最小值=" + Collections.min(energyLog));
        System.out.println("最大值=" + Collections.max(energyLog));
        System.out.println("數值 8 的出現次數=" + Collections.frequency(energyLog, 8));
        // 把值加到另一條清單，避免排序破壞原始任務的時間順序。
        ArrayList<Integer> sorted = new ArrayList<>();
        for (int energy : energyLog) {
            sorted.add(energy);
        }
        Collections.sort(sorted);
        System.out.println("原清單=" + energyLog);
        System.out.println("排序後=" + sorted);
        ArrayList<String> results = new ArrayList<>();
        results.add("成功");
        results.add("能源已滿");
        results.add("成功");
        System.out.println("成功=" + Collections.frequency(results, "成功"));
    }
}
