// 範例 1（claude 版本）：把「一步」的三個值包成一個類別。
// 這一版刻意沒有建構子、欄位全部 public，用來觀察「能打包，但誰都能亂改」的問題。
public class RawStep {
    public int command;
    public String result;
    public int energy;
}
