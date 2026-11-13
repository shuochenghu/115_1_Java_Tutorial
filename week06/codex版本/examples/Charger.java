// 類別：Charger（第 6 週範例：充電器）
// 意義：替電池充電的工具。
// 作用：示範「相依」：充電器沒有欄位保存電池，只在 refill 被呼叫時使用參數收到的那顆電池。
public class Charger {
    // refill：替傳入的電池充電一次，回傳電池的充電結果（true 成功、false 已滿）。
    // 只使用這次收到的參數，不用欄位保存電池。
    public boolean refill(Battery battery) { return battery.charge(); }
}
