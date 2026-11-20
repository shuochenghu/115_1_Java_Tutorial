import java.util.ArrayList;

// 類別：SampleGroup<T extends SampleSource>（模擬 AI 回覆的版本，教師設計）
// 意義：這不是真實 AI 服務的回覆，也不是教師解答。它模擬 AI 為了「讓什麼來源都能加進去」而改動 add。
// 作用：只放在 ai-check 資料夾編譯，用來練習判讀：編譯有警告、合法資料也正確，但違反群組約定。
// 群組約定（與正式版相同）：重型車群組只能保存重型車；get 取回的型態由 T 決定；
//           來源不為 null，索引滿足 0 <= index < size()，總和在 int 範圍內。
// 判讀方式：拿自己完成的 SampleGroup 逐一對照每個方法，找出哪裡讓型態限制流失，再做最小修改。
public class SampleGroup<T extends SampleSource> {
    // sources：依加入順序保存來源參考，元素型態宣告為 T。
    private ArrayList<T> sources;

    // 建構子：建立空清單。
    public SampleGroup() {
        sources = new ArrayList<>();
    }

    // add（模擬 AI 改動的方法）：參數型態被放寬成 SampleSource，再用 (T) 轉型後放入清單。
    // 模擬 AI 的說法：「都實作同一介面，轉成 T 就能放入。」編譯時會出現 unchecked 警告。
    // 請檢查：重型車群組呼叫 add(new SampleTray(4)) 時，編譯器還會不會拒絕？
    public void add(SampleSource source) {
        sources.add((T) source);
    }

    // get：交回第 index 筆的參考，宣告的回傳型態是 T。
    public T get(int index) {
        return sources.get(index);
    }

    // size：回傳目前筆數（不是樣本數）。
    public int size() {
        return sources.size();
    }

    // totalSamples：每次重新讀各來源目前的樣本數再累加；空群組回傳 0。
    public int totalSamples() {
        int total = 0;
        for (T source : sources) {
            total += source.getSamples();
        }
        return total;
    }
}
