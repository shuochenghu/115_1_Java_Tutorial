// 類別：SampleCaseCheck（教師驗證工具）
// 意義：檢查 SampleCase 是否符合樣本盒需求。
// 作用：把 F1 設計卡的測試案例變成 12 項實際檢查（存入、拒絕、裝滿、卸載、容量範圍、各盒獨立）；
//       不要求學生先學 JUnit。
public class SampleCaseCheck {
    // main：程式進入點。依序執行 12 項檢查，任何一項失敗就停止。
    public static void main(String[] args) {
        SampleCase box = new SampleCase(3);
        check("新樣本盒", box.getCapacity() == 3 && box.getCount() == 0
                && box.getFreeSpace() == 3);
        check("存入兩份", box.store(2) && box.getCount() == 2 && box.getFreeSpace() == 1);
        check("拒絕整批存放", !box.store(2) && box.getCount() == 2);
        check("拒絕零份", !box.store(0) && box.getCount() == 2);
        check("拒絕負數", !box.store(-1) && box.getCount() == 2);
        check("拒絕過大批次", !box.store(Integer.MAX_VALUE) && box.getCount() == 2);
        check("剛好裝滿", box.store(1) && box.getCount() == 3 && box.getFreeSpace() == 0);
        check("已滿時拒絕存放", !box.store(1) && box.getCount() == 3);
        check("卸載樣本", box.unload() == 3 && box.getCount() == 0 && box.getFreeSpace() == 3);
        check("卸載空盒", box.unload() == 0 && box.getCount() == 0);
        SampleCase low = new SampleCase(0);
        SampleCase high = new SampleCase(9);
        check("容量範圍", low.getCapacity() == 1 && high.getCapacity() == 8);
        high.store(2);
        check("各樣本盒獨立", box.getCount() == 0 && low.getCount() == 0 && high.getCount() == 2);
        System.out.println("12/12 項樣本盒檢查通過");
    }

    // check：correct 為 true 就印出「通過」；為 false 就丟出錯誤，訊息是檢查項目名稱。
    static void check(String label, boolean correct) {
        if (!correct) {
            throw new AssertionError(label);
        }
        System.out.println("通過 " + label);
    }
}
