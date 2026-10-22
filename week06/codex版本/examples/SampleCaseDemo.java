// AI 圖與程式反例的共同驗收：容量 3，超量必須整批拒絕。
public class SampleCaseDemo {
    public static void main(String[] args) {
        SampleCase box = new SampleCase(3);
        System.out.println("first=" + box.store(2) + ", count=" + box.getCount());
        System.out.println("overflow=" + box.store(2) + ", count=" + box.getCount());
        System.out.println("fill=" + box.store(1) + ", count=" + box.getCount());
        System.out.println("unload=" + box.unload());
        System.out.println("empty=" + box.unload());
    }
}
