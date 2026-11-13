// 教師撰寫的模擬 AI 回覆。僅用於隔離的 ai-check，和正式答案分開。
public class SampleReport {
    public static void print(String label, Rover source) {
        System.out.println(label + " 樣本數=" + source.getSamples());
    }
}
