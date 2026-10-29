// 第七週作業參考解答（claude 版本）：投影機是一種器材，另外記錄是否需要搭配布幕。
public class Projector extends Equipment {
    private final boolean needsScreen;

    public Projector(String id, String name, boolean needsScreen) {
        super(id, name);
        this.needsScreen = needsScreen;
    }

    public boolean needsScreen() {
        return needsScreen;
    }

    public String checklist() {
        String screen = "no";
        if (needsScreen) {
            screen = "yes";
        }
        return getLabel() + " | screen=" + screen;
    }
}
