import java.util.ArrayList;

public class ListBasics {
    public static void main(String[] args) {
        ArrayList<Integer> commands = new ArrayList<>();
        commands.add(1); // int 自動裝箱成 Integer；不需自行 new Integer。
        commands.add(1);
        commands.add(2);
        int first = commands.get(0); // Integer 自動拆箱成 int。
        System.out.println("commands=" + commands + ", size=" + commands.size());
        System.out.println("first=" + first);
        commands.set(1, 3); // 改內容，筆數不變。
        System.out.println("after set=" + commands);
        commands.remove(0); // 傳 int 時刪索引 0，即刪第一筆。
        System.out.println("after remove index=" + commands);
        commands.remove(Integer.valueOf(3)); // 包成 Integer 才是刪第一個值 3。
        System.out.println("after remove value=" + commands);
        System.out.println("empty=" + commands.isEmpty());
        // ArrayList<int> wrong; // 泛型型態參數須為參考型態，請在副本試編譯。
    }
}
