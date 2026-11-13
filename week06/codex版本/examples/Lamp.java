// 類別：Lamp（第 6 週範例：燈具）
// 意義：一盞靠電池發光的燈。
// 作用：示範「關聯」：燈用欄位一直記著自己的電池，每次開燈都從這顆電池扣能源。
public class Lamp {
    // battery：這盞燈使用的電池。建構時把收到的 Battery 存進這個欄位。
    // 之後每次 turnOn，都使用這個欄位指向的那一顆電池。
    // （對照 Charger：只在方法參數中使用電池，用完不保存。）
    private Battery battery;
    // 建構子：記住這盞燈要使用的電池。
    public Lamp(Battery battery) { this.battery = battery; }
    // turnOn：開燈 minutes 分鐘，每分鐘用 1 格能源；回傳電池是否成功提供能源。
    public boolean turnOn(int minutes) { return battery.use(minutes); }
}
