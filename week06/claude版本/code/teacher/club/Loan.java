// 第六週作業延伸參考解答（claude 版本）：一筆借用紀錄。誰、借了哪件、何時借、何時還。
// 日期用 "2026-10-22" 這種字串，本週不教日期 API；returnDate 為 null 表示尚未歸還。
public class Loan {
    private final Member member;
    private final Equipment equipment;
    private final String borrowDate;
    private String returnDate;   // 唯一會改變的欄位：只能從 null 變成日期一次

    public Loan(Member member, Equipment equipment, String borrowDate) {
        this.member = member;
        this.equipment = equipment;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public Member getMember() {
        return member;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    // 已歸還的紀錄不能再歸還一次，回傳 false 且內容不變。
    public boolean close(String returnDate) {
        if (!isActive()) {
            return false;
        }
        this.returnDate = returnDate;
        return true;
    }

    public String describe() {
        return equipment.getId() + " " + member.getStudentId() + " " + borrowDate + " -> "
                + (isActive() ? "(not returned)" : returnDate);
    }
}
