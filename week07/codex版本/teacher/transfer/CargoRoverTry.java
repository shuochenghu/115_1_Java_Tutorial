// 類別：CargoRoverTry（遷移題教師參考：測試入口）
// 意義：示範如何測試 CargoRover 的程式入口。
// 作用：依序測試正常裝載、超量、剛好裝滿、零件數，以及已停止的車；
//       學生須自行寫相同規格的測試入口，不先複製教師答案。
public class CargoRoverTry {
    // main：程式進入點。每次操作後印出「結果/貨物數」。
    public static void main(String[] args) {
        CargoRover c = new CargoRover(20, 0);
        System.out.println(c.load(2) + "/" + c.getCargo());
        System.out.println(c.load(2) + "/" + c.getCargo());
        System.out.println(c.load(1) + "/" + c.getCargo());
        System.out.println(c.load(0) + "/" + c.getCargo());
        System.out.println(c.getEnergy() + "/" + c.getSamples() + "/" + c.getActionCount());
        CargoRover stopped = new CargoRover(0, 0);
        System.out.println(stopped.load(1) + "/" + stopped.getCargo());
        CargoRover success = new CargoRover(20, 5);
        System.out.println(success.load(1) + "/" + success.getCargo());
    }
}
