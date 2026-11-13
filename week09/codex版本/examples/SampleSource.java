// 介面：SampleSource（樣本來源）
// 意義：「能提供樣本數的東西」的共同約定；介面只規定要有哪些方法，不提供內容。
// 作用：獨立示範 FixtureDemo 使用的版本，與課堂主線分開編譯。
public interface SampleSource {
    // getSamples：回傳目前的樣本數（不為負）。實作的類別必須提供這個方法，而且讀取時不能改變狀態。
    int getSamples();
}
