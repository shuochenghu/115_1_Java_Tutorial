// 第七週作業提供的父類別（claude 版本）：社團器材。延續第六週「社團器材借用」的 Equipment，多了 getLabel。
// 本檔已完成，學生不需修改。Camera、Projector 繼承它，編號與名稱只存在這裡。
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

    // 清單上顯示的標籤，例如 "CAM-01 Sony A7"。
    public String getLabel() {
        return id + " " + name;
    }
}
