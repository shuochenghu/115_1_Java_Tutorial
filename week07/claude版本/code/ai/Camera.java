// 第七週作業的「模擬 AI 回覆」（claude 版本）：由教師設計、刻意含錯，不是真實 AI 對話，也不是參考答案。
// 情境 Prompt：「Equipment 有 id 和 name，幫我寫繼承它的 Camera，多記錄記憶卡數，並提供 checklist()。」
// 用法見作業說明第 3 節：放進單獨的資料夾，和 Equipment、ShelfTry 一起編譯（ShelfTry 還需要你寫好的 Projector）。
public class Camera extends Equipment {
    private String id;
    private String name;
    private int memoryCards;

    public Camera(String id, String name, int memoryCards) {
        super(id, name);
        this.memoryCards = memoryCards;
    }

    public int getMemoryCards() {
        return memoryCards;
    }

    public String checklist() {
        return id + " " + name + " | cards=" + memoryCards;
    }
}
