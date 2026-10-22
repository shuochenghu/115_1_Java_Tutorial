// 第六週作業延伸參考解答（claude 版本）：器材。只記編號與名稱。
// 「是否借出」由 Club 查借用紀錄回答（Club.isOnLoan），器材本身不另存一份狀態。
public class Equipment {
    private final String id;
    private final String name;

    public Equipment(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
