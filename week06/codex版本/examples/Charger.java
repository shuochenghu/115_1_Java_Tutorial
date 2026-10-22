public class Charger {
    // 只使用這次收到的參數，不用欄位保存電池。
    public boolean refill(Battery battery) { return battery.charge(); }
}
