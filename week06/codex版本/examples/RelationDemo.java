// 類別：RelationDemo（第 6 週範例）
// 意義：觀察物件之間「關聯」與「相依」的程式入口。
// 作用：讓三盞燈分別連到電池（兩盞收到同一顆、一盞收到另一顆），再用充電器充電，
//       由輸出判斷哪些燈共用同一個 Battery 物件。
public class RelationDemo {
    // main：程式進入點。建立電池、燈與充電器，依序開燈與充電並印出結果。
    public static void main(String[] args) {
        Battery shared = new Battery(8);
        Lamp a = new Lamp(shared);
        Lamp b = new Lamp(shared);
        Lamp c = new Lamp(new Battery(8));
        System.out.println("燈具 A=" + a.turnOn(6));
        System.out.println("燈具 B=" + b.turnOn(6));
        System.out.println("燈具 C=" + c.turnOn(6));
        System.out.println("共用電池能源=" + shared.getEnergy());
        Charger charger = new Charger();
        System.out.println("充電=" + charger.refill(shared));
        System.out.println("燈具 B 再開啟=" + b.turnOn(6));
        System.out.println("共用電池能源=" + shared.getEnergy());
    }
}
