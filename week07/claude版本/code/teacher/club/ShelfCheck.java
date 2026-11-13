import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

// 第七週作業的教師檢查工具（claude 版本）：Camera、Projector 的規格與繼承設計。
// 與學生的 Camera、Projector 及提供的 Equipment 一起編譯；不是學生必寫作業，也不是 JUnit。
// 「設計」檢查用到反射（reflection）讀取欄位與修飾字，這是教師工具的寫法，學生不必學。
public class ShelfCheck {
    static int passed = 0;
    static int total = 0;

    public static void main(String[] args) {
        Camera cam = new Camera("CAM-01", "Sony A7", 2);
        expect("相機繼承的方法", cam.getId() + " | " + cam.getName() + " | " + cam.getLabel(),
                "CAM-01 | Sony A7 | CAM-01 Sony A7");
        expect("相機檢查清單", cam.checklist() + " " + cam.getMemoryCards(), "CAM-01 Sony A7 | 記憶卡數=2 2");
        expect("相機的記憶卡數為負數", new Camera("CAM-02", "Canon R50", -1).checklist(),
                "CAM-02 Canon R50 | 記憶卡數=0");
        expect("相機的記憶卡數為零", new Camera("CAM-03", "Ricoh GR", 0).checklist(), "CAM-03 Ricoh GR | 記憶卡數=0");

        Projector big = new Projector("PRJ-01", "Epson X49", true);
        expect("投影機繼承的方法", big.getId() + " | " + big.getLabel(), "PRJ-01 | PRJ-01 Epson X49");
        expect("投影機檢查清單", big.checklist() + " " + big.needsScreen(), "PRJ-01 Epson X49 | 需要布幕=是 true");
        expect("投影機不需要布幕", new Projector("PRJ-02", "Mini Beam", false).checklist(),
                "PRJ-02 Mini Beam | 需要布幕=否");

        expect("設計檢查：Camera 繼承 Equipment", Camera.class.getSuperclass().getSimpleName(), "Equipment");
        expect("設計檢查：Projector 繼承 Equipment", Projector.class.getSuperclass().getSimpleName(), "Equipment");
        expect("設計檢查：無重複欄位", hiddenFields(Camera.class) + " " + hiddenFields(Projector.class),
                "（無） （無）");
        expect("設計檢查：欄位為 private", nonPrivateFields(Camera.class, Projector.class), "（無）");

        System.out.println(passed + "/" + total + " 項通過");
    }

    // 子類別又宣告了 id 或 name：會遮蔽 Equipment 的欄位，作業說明第 3 節的模擬 AI 回覆就是這樣。
    static String hiddenFields(Class<?> child) {
        String found = "";
        for (Field field : child.getDeclaredFields()) {
            for (Field inherited : Equipment.class.getDeclaredFields()) {
                if (field.getName().equals(inherited.getName())) {
                    found += child.getSimpleName() + "." + field.getName() + " ";
                }
            }
        }
        return found.isEmpty() ? "（無）" : found.trim();
    }

    static String nonPrivateFields(Class<?>... types) {
        String found = "";
        for (Class<?> type : types) {
            for (Field field : type.getDeclaredFields()) {
                int mod = field.getModifiers();
                boolean constant = Modifier.isStatic(mod) && Modifier.isFinal(mod);
                if (!constant && !Modifier.isPrivate(mod)) {
                    found += type.getSimpleName() + "." + field.getName() + " ";
                }
            }
        }
        return found.isEmpty() ? "（無）" : found.trim();
    }

    static void expect(String label, String actual, String expected) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println("通過 " + label + " -> " + actual);
        } else {
            System.out.println("失敗 " + label + " -> " + actual + " （預期 " + expected + "）");
        }
    }
}
