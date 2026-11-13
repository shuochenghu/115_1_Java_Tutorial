import java.util.Arrays;

// 類別：ArrayReference（第 4 週範例）
// 意義：示範「陣列變數存的是參考」。
// 作用：比較三種情況：兩個變數指向同一個陣列、在方法裡修改元素、在方法裡改指向新陣列；
//       並比較 ==（是否同一個陣列）與 Arrays.equals（內容是否相同）。
public class ArrayReference {
    // main：程式進入點。依序執行三種參考實驗，每一步都印出陣列內容供預測核對。
    public static void main(String[] args) {
        int[] energy = {20, 14, 8};
        int[] alias = energy;
        alias[0] = 19;
        System.out.println("透過別名修改後=" + Arrays.toString(energy));
        changeFirst(energy);
        System.out.println("修改元素後=" + Arrays.toString(energy));
        replaceLocal(energy);
        System.out.println("返回呼叫端後=" + Arrays.toString(energy));
        int[] sameContent = {18, 14, 8};
        System.out.println("參考相同=" + (energy == sameContent));
        System.out.println("內容相同=" + Arrays.equals(energy, sameContent));
    }

    // changeFirst：把傳入陣列的第一格改成 18，用來觀察方法能否修改呼叫端的陣列元素。
    static void changeFirst(int[] values) {
        // 傳入的是參考值的副本，仍可透過它修改共同指向的陣列元素。
        values[0] = 18;
    }

    // replaceLocal：在方法內讓參數改指向一個新陣列，用來觀察呼叫端的變數會不會跟著改變。
    static void replaceLocal(int[] values) {
        // 只更換方法內的參考；呼叫端變數不會改指向這個新陣列。
        values = new int[] {20, 20, 20};
        System.out.println("方法內重新指定後=" + Arrays.toString(values));
    }
}
