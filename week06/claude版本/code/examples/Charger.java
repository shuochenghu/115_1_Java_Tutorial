// 第六週範例（claude 版本）：充電器「借用一下」電池。
// 電池只出現在參數，方法結束就不再保留，類別圖畫成虛線箭頭的「相依」。
public class Charger {
    // 把電池充滿，回傳實際充入的量。
    public int refill(Battery battery) {
        return battery.charge(battery.getCapacity());
    }
}
