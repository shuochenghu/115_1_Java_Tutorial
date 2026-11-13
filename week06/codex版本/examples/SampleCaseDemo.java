// 類別：SampleCaseDemo（第 6 週範例）
// 意義：樣本盒的驗收程式。
// 作用：用容量 3 的盒子依序存入、超量存入、剛好裝滿、卸載，檢查「超量必須整批拒絕」；
//       也用來驗收 AI 設計圖和程式反例。
public class SampleCaseDemo {
    // main：程式進入點。依序操作一個樣本盒，印出每次操作的結果與存量。
    public static void main(String[] args) {
        SampleCase box = new SampleCase(3);
        System.out.println("第一次=" + box.store(2) + ", 存量=" + box.getCount());
        System.out.println("超量存放=" + box.store(2) + ", 存量=" + box.getCount());
        System.out.println("剛好裝滿=" + box.store(1) + ", 存量=" + box.getCount());
        System.out.println("卸載=" + box.unload());
        System.out.println("空盒卸載=" + box.unload());
    }
}
