// 教師設計的模擬 AI 回覆，刻意有錯，不是真實服務輸出。只在隔離的 ai-check 編譯。
public class SampleReport {
    public static void print(String label, SampleSource source) {
        // 教師模擬 AI 回覆：請以共用報表規格核對這段轉交。
        if (source instanceof Rover) {
            print(label, (Rover) source);
            return;
        }
        print(label, source.getSamples());
    }

    public static void print(String label, Rover rover) {
        System.out.println(label + " 樣本數=" + rover.getSamples() + " 能源=" + rover.getEnergy());
    }

    public static void print(String label, int samples) {
        System.out.println(label + " 樣本數=" + samples);
    }
}
