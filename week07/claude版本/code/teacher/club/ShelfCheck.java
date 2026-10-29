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
        expect("camera inherited", cam.getId() + " | " + cam.getName() + " | " + cam.getLabel(),
                "CAM-01 | Sony A7 | CAM-01 Sony A7");
        expect("camera checklist", cam.checklist() + " " + cam.getMemoryCards(), "CAM-01 Sony A7 | cards=2 2");
        expect("camera negative cards", new Camera("CAM-02", "Canon R50", -1).checklist(),
                "CAM-02 Canon R50 | cards=0");
        expect("camera zero cards", new Camera("CAM-03", "Ricoh GR", 0).checklist(), "CAM-03 Ricoh GR | cards=0");

        Projector big = new Projector("PRJ-01", "Epson X49", true);
        expect("projector inherited", big.getId() + " | " + big.getLabel(), "PRJ-01 | PRJ-01 Epson X49");
        expect("projector checklist", big.checklist() + " " + big.needsScreen(), "PRJ-01 Epson X49 | screen=yes true");
        expect("projector without screen", new Projector("PRJ-02", "Mini Beam", false).checklist(),
                "PRJ-02 Mini Beam | screen=no");

        expect("design: Camera extends Equipment", Camera.class.getSuperclass().getSimpleName(), "Equipment");
        expect("design: Projector extends Equipment", Projector.class.getSuperclass().getSimpleName(), "Equipment");
        expect("design: no hidden copies", hiddenFields(Camera.class) + " " + hiddenFields(Projector.class),
                "(none) (none)");
        expect("design: fields private", nonPrivateFields(Camera.class, Projector.class), "(none)");

        System.out.println(passed + "/" + total + " passed");
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
        return found.isEmpty() ? "(none)" : found.trim();
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
        return found.isEmpty() ? "(none)" : found.trim();
    }

    static void expect(String label, String actual, String expected) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println("PASS " + label + " -> " + actual);
        } else {
            System.out.println("FAIL " + label + " -> " + actual + " (expect " + expected + ")");
        }
    }
}
