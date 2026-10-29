// 教師參考輸出；學生須自行寫相同規格的測試入口，不先複製教師答案。
public class CargoRoverTry {
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
