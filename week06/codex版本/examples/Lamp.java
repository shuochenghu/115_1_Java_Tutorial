public class Lamp {
    private Battery battery;
    public Lamp(Battery battery) { this.battery = battery; }
    public boolean turnOn(int minutes) { return battery.use(minutes); }
}
