// 第七週「模擬 AI 回覆」（claude 版本）：由教師設計、刻意含錯，不是真實 AI 對話，也不是參考答案。
// 情境 Prompt：「幫我寫一個繼承 Rover 的 HeavyRover，多一個 drill 方法，耗能 8、得 3 份樣本，並記錄鑽探次數。」
// 用法見講義 E 段：先預測能不能編譯，再放進單獨的資料夾和你完成的 Rover、HeavyRoverTry 一起編譯。
public class HeavyRover extends Rover {
    private int energy;
    private int samples;
    private int drillCount;

    public HeavyRover(int energy, int samples) {
        this.energy = energy;
        this.samples = samples;
        this.drillCount = 0;
    }

    public int getDrillCount() {
        return drillCount;
    }

    public String drill() {
        if (energy < 8) {
            return "NOT_ENOUGH_ENERGY";
        }
        energy -= 8;
        samples += 3;
        drillCount++;
        return "OK";
    }
}
