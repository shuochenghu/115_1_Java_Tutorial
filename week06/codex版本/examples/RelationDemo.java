public class RelationDemo {
    public static void main(String[] args) {
        Battery shared = new Battery(8);
        Lamp a = new Lamp(shared);
        Lamp b = new Lamp(shared);
        Lamp c = new Lamp(new Battery(8));
        System.out.println("A=" + a.turnOn(6));
        System.out.println("B=" + b.turnOn(6));
        System.out.println("C=" + c.turnOn(6));
        System.out.println("shared=" + shared.getEnergy());
        Charger charger = new Charger();
        System.out.println("charge=" + charger.refill(shared));
        System.out.println("B again=" + b.turnOn(6));
        System.out.println("shared=" + shared.getEnergy());
    }
}
