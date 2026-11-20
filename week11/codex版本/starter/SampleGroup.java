import java.util.ArrayList;

// 類別：SampleGroup<T>（樣本來源群組，學生起始碼，Codex 版本）
// 意義：地面站用來收集同一種來源的群組，例如「樣本盒群組」或「重型車群組」。
//       T 是型態參數，建立群組時才決定，例如 SampleGroup<SampleTray>。
// 作用：保存來源、依序取回原物件，並統計總樣本數。本週四組 TODO 都在這個檔案。
// 使用約定：來源不為 null；get 的索引滿足 0 <= index < size()；樣本數與總和都是不為負的小整數。
// 注意：不要修改其他探測車檔案，也不要把教師版整份貼進來。
public class SampleGroup<T> {
    // TODO 1：在這裡宣告一個 private 的 ArrayList 欄位，元素型態要用 T，用來依序保存加入的來源。

    // 建構子：建立一個空的群組。
    public SampleGroup() {
        // TODO 1：在這裡建立空清單並存進欄位。每個群組都要有自己的一份清單，不能用 static 共用。
    }

    // add：把一個來源加入群組尾端。參數型態是 T，所以重型車群組只接受重型車。
    // TODO 2：把 source 這個參考存進清單。保持加入順序；同一個來源加入兩次就算兩筆。
    //         不要建立新物件、不要轉型，也不要呼叫來源的行動方法。
    public void add(T source) {
    }

    // get：取回第 index 筆來源。回傳型態是 T，所以重型車群組取回的就是 HeavyRover。
    // TODO 3：回傳清單中第 index 筆的原物件。回傳型態維持 T，不要改成 Object，也不要加轉型。
    public T get(int index) {
        return null; // 暫時的回傳值，只是讓起始碼能編譯；完成 TODO 3 時要換掉。
    }

    // size：回傳群組目前有幾筆來源（筆數，不是樣本數）。
    // TODO 3：回傳清單目前的筆數。
    public int size() {
        return 0; // 暫時的回傳值；展示入口看到筆數不對時，會印出未完成提示並安全結束。
    }

    // totalSamples：回傳群組內所有來源「目前」樣本數的總和；空群組回傳 0。
    // TODO 4：分兩步完成。
    //   第 1 步：先保持類別宣告為 SampleGroup<T>，在這個方法用普通迴圈對每一筆來源呼叫 getSamples() 並累加，
    //           然後編譯，記下第一個相關的錯誤訊息（想想看：編譯器知道 T 有 getSamples() 嗎？）。
    //   第 2 步：在類別宣告替 T 加上 SampleSource 的上限，重新編譯並執行展示入口。
    //   每次呼叫都要重新讀各來源當下的數量，不要在 add 時先存一個總和；也不要用轉型代替上限。
    public int totalSamples() {
        return 0; // 暫時的回傳值，不是所有群組的答案；完成 TODO 4 時要換掉。
    }
}
