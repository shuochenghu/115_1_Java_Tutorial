import java.util.ArrayList;

// 類別：ListBasics（第 4 週範例）
// 意義：示範 ArrayList 的基本操作。
// 作用：新增、讀取、修改、依索引刪除、依值刪除；並說明 int 與 Integer 之間的自動裝箱與拆箱。
public class ListBasics {
    // main：程式進入點。對同一條指令清單依序做各種操作，每一步都印出清單內容。
    public static void main(String[] args) {
        // 指令編號：0＝返回、1＝探索、2＝掃描、3＝充電；本例只操作清單，不執行動作。
        ArrayList<Integer> commands = new ArrayList<>();
        commands.add(1); // 加入指令 1（探索）；int 自動裝箱成 Integer，不需自行 new Integer。
        commands.add(1); // 再加入一筆指令 1（探索），示範清單允許重複。
        commands.add(2); // 加入指令 2（掃描）。
        int first = commands.get(0); // Integer 自動拆箱成 int。
        System.out.println("指令清單=" + commands + ", 筆數=" + commands.size());
        System.out.println("第一筆=" + first);
        commands.set(1, 3); // 把索引 1 的指令改成 3（充電）；改內容，筆數不變。
        System.out.println("修改後=" + commands);
        commands.remove(0); // 這個 0 是索引，刪除第一筆探索指令；不是指令 0（返回）。
        System.out.println("依索引刪除後=" + commands);
        commands.remove(Integer.valueOf(3)); // 刪除第一筆指令 3（充電）；包成 Integer 才是按值刪除。
        System.out.println("依值刪除後=" + commands);
        System.out.println("清單為空=" + commands.isEmpty());
        // ArrayList<int> wrong; // 泛型型態參數須為參考型態，請在副本試編譯。
    }
}
