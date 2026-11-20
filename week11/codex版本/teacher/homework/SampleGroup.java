import java.util.ArrayList;

// 類別：SampleGroup<T extends SampleSource>（樣本來源群組，作業教師解答）
// 意義：與主線教師解答相同，另外加上作業要求的 findMostSamples()。
// 作用：示範「回傳 T」讓取回的最大來源保留群組的具體型態，例如重型車群組取回 HeavyRover。
// 使用約定：來源不為 null；索引滿足 0 <= index < size()；總和在 int 範圍內；
//           findMostSamples() 只在 size() > 0 時呼叫。
// 注意：與主線 teacher/SampleGroup.java 同名，必須分開編譯，不能放進同一個 javac 命令。
public class SampleGroup<T extends SampleSource> {
    // sources：依加入順序保存來源參考。每個群組有自己的一份，不對外交出整份清單。
    private ArrayList<T> sources;

    // 建構子：建立空清單。
    public SampleGroup() {
        sources = new ArrayList<>();
    }

    // add：把原參考加到清單尾端。同一個參考加入兩次就是兩筆。
    public void add(T source) {
        sources.add(source);
    }

    // get：交回第 index 筆的原參考，型態由這個群組的 T 決定。
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

    // findMostSamples（作業教師答案）：回傳目前樣本數最多的「原物件」。
    // 步驟：1. 先把第一筆當成目前最大；2. 逐筆比較，只有「嚴格大於」才換成新的一筆；
    //       3. 回傳最後留下的參考。因為平手不替換，所以平手時會保留最早加入的那一筆。
    // 呼叫前必須確認 size() > 0，否則讀第 0 筆會失敗。
    public T findMostSamples() {
        T best = sources.get(0);
        for (T source : sources) {
            if (source.getSamples() > best.getSamples()) {
                best = source;
            }
        }
        return best;
    }
}
