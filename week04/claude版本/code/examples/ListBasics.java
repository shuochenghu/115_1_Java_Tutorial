import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 範例 4（claude 版本）：ArrayList、包裝類別與自動裝箱、Collections 常用方法、三個常見陷阱。
public class ListBasics {
    public static void main(String[] args) {
        // C#：List<int> log = new List<int>();
        // Java：泛型參數只能放參考型態，所以寫 Integer 而不是 int（ArrayList<int> 無法編譯）。
        ArrayList<Integer> log = new ArrayList<>();
        log.add(1);          // 自動裝箱：int 1 → Integer
        log.add(1);
        log.add(2);
        log.add(9);
        int first = log.get(0);   // 自動拆箱：Integer → int
        // C#：log.Count、log[0]、log[0] = 3；Java：size()、get(0)、set(0, 3)
        System.out.println("日誌 = " + log + ", 筆數 = " + log.size() + ", 第一筆 = " + first);

        log.set(3, 3);
        System.out.println("呼叫 set(3, 3) 後：" + log);
        System.out.println("包含 2？" + log.contains(2) + ", 2 首次出現的索引 = " + log.indexOf(2));

        // 陷阱 1：remove(int) 是「刪索引」，remove(Object) 才是「刪值」。
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(5, 0, 2));
        a.remove(0);                       // 刪掉索引 0（值 5）
        System.out.println("依索引刪除 remove(0) → " + a);
        ArrayList<Integer> b = new ArrayList<>(Arrays.asList(5, 0, 2));
        b.remove(Integer.valueOf(0));      // 刪掉值 0
        System.out.println("依值刪除 remove(Integer.valueOf(0)) → " + b);

        // Collections：對整個集合操作的現成方法。
        ArrayList<Integer> energy = new ArrayList<>(Arrays.asList(14, 8, 20, 6));
        System.out.println("最小值 = " + Collections.min(energy) + ", 最大值 = " + Collections.max(energy)
                + ", 數值 8 的出現次數 = " + Collections.frequency(energy, 8));
        Collections.sort(energy);
        System.out.println("排序後 = " + energy);

        // 宣告時左邊寫介面 List、右邊寫實作 ArrayList；介面在第九週詳談。
        List<String> results = new ArrayList<>();
        results.add("成功");
        results.add("能源已滿");
        System.out.println("結果清單 = " + results);

        // 陷阱 2：Integer 是物件，== 比參考。-128..127 有快取，所以小數字「剛好」相等。
        Integer small1 = 100, small2 = 100;
        Integer big1 = 1000, big2 = 1000;
        System.out.println("100 == 100: " + (small1 == small2) + "; 1000 == 1000: " + (big1 == big2)
                + "; 以 equals 比較兩個 1000：" + big1.equals(big2));

        // 陷阱 3：邊走訪邊用索引刪除，後面的元素會往前補位而被跳過。
        ArrayList<String> steps = new ArrayList<>(Arrays.asList("成功", "能源已滿", "無效指令", "成功"));
        for (int i = 0; i < steps.size(); i++) {
            if (!steps.get(i).equals("成功")) {
                steps.remove(i);
            }
        }
        System.out.println("正向刪除（錯誤）→ " + steps);
        ArrayList<String> steps2 = new ArrayList<>(Arrays.asList("成功", "能源已滿", "無效指令", "成功"));
        for (int i = steps2.size() - 1; i >= 0; i--) {
            if (!steps2.get(i).equals("成功")) {
                steps2.remove(i);
            }
        }
        System.out.println("反向刪除（修正）→ " + steps2);
    }
}
