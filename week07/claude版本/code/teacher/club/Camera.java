// 第七週作業參考解答（claude 版本）：相機是一種器材，另外記錄附帶的記憶卡數。
// 編號與名稱交給 Equipment 保存；記憶卡數小於 0 時改成 0。
public class Camera extends Equipment {
    private final int memoryCards;

    public Camera(String id, String name, int memoryCards) {
        super(id, name);
        if (memoryCards < 0) {
            memoryCards = 0;
        }
        this.memoryCards = memoryCards;
    }

    public int getMemoryCards() {
        return memoryCards;
    }

    // 借出時核對用的一行文字；標籤用繼承來的 getLabel，不另存編號與名稱。
    public String checklist() {
        return getLabel() + " | cards=" + memoryCards;
    }
}
