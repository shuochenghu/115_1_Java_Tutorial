// 第六週範例（claude 版本）：手電筒「一直帶著」一顆電池。
// 電池存在欄位裡，物件活多久就用多久，類別圖畫成實線箭頭的「關聯」。
public class Flashlight {
    private final String name;
    private final Battery battery;   // 只存參考：電池由外部建立後交進來，可能和別人共用

    public Flashlight(String name, Battery battery) {
        this.name = name;
        this.battery = battery;
    }

    public String getName() {
        return name;
    }

    // 開燈 minutes 分鐘，每分鐘耗 1 格電；電量不夠就不開燈，回傳 false。
    public boolean turnOn(int minutes) {
        return battery.use(minutes);
    }
}
