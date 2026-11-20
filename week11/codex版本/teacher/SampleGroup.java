import java.util.ArrayList;

// 類別：SampleGroup<T extends SampleSource>（樣本來源群組，Codex 教師解答）
// 意義：地面站收集同一種來源的群組。T 保留每個群組的元素型態，例如 SampleGroup<HeavyRover> 取回的就是 HeavyRover。
// 作用：「extends SampleSource」是 T 的上限，保證每個 T 都有 getSamples()，統計方法才能呼叫它。
//       上限不代表 SampleGroup 本身是一種 SampleSource。
// 使用約定：來源不為 null；索引滿足 0 <= index < size()；總和在 int 範圍內。
//           群組只保存參考，不複製來源；所有查詢都不會讓車輛執行行動。
public class SampleGroup<T extends SampleSource> {
    // sources（TODO 1 教師答案）：依加入順序保存來源參考。每個群組有自己的一份，不對外交出整份清單。
    private ArrayList<T> sources;

    // 建構子（TODO 1 教師答案）：建立空清單。若用 static 共用，兩個群組會互相影響。
    public SampleGroup() {
        sources = new ArrayList<>();
    }

    // add（TODO 2 教師答案）：把原參考加到清單尾端。同一個參考加入兩次就是兩筆，總和也會算兩次。
    public void add(T source) {
        sources.add(source);
    }

    // get（TODO 3 教師答案）：交回第 index 筆的原參考，型態由這個群組的 T 決定，不需要轉型。
    public T get(int index) {
        return sources.get(index);
    }

    // size（TODO 3 教師答案）：回傳目前筆數。這是來源有幾筆，不是樣本數。
    public int size() {
        return sources.size();
    }

    // totalSamples（TODO 4 教師答案）：每次呼叫都重新讀各來源「目前」的樣本數再累加，不快取加入時的數字。
    // 例如一台車加入時 0 份、之後探索得到 3 份，再次呼叫就會得到 3。空群組不進迴圈，回傳 0。
    public int totalSamples() {
        int total = 0;
        for (T source : sources) {
            total += source.getSamples();
        }
        return total;
    }
}
