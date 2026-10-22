import java.util.ArrayList;

// 第六週作業延伸參考解答（claude 版本）：社團。保管器材清單與全部借用紀錄，所以由它檢查借用規則。
// 「每人同時最多 2 件」「一件器材同時只借一人」都要看所有紀錄才能判斷，只有 Club 看得到全部。
public class Club {
    public static final int MAX_ACTIVE_LOANS = 2;

    private final ArrayList<Equipment> equipments;
    private final ArrayList<Loan> loans;   // 由 Club 建立，不交給外部

    public Club() {
        this.equipments = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    // 回傳 OK、UNKNOWN_EQUIPMENT、ON_LOAN 或 LIMIT_REACHED；被拒絕時不建立紀錄。
    public String borrow(Member member, Equipment equipment, String date) {
        if (!equipments.contains(equipment)) {
            return "UNKNOWN_EQUIPMENT";
        }
        if (isOnLoan(equipment)) {
            return "ON_LOAN";
        }
        if (countActiveLoans(member) >= MAX_ACTIVE_LOANS) {
            return "LIMIT_REACHED";
        }
        loans.add(new Loan(member, equipment, date));
        return "OK";
    }

    // 找到這件器材未歸還的紀錄並結案；沒有借出就回傳 NOT_ON_LOAN。
    public String giveBack(Equipment equipment, String date) {
        for (Loan loan : loans) {
            if (loan.isActive() && loan.getEquipment() == equipment) {
                loan.close(date);
                return "OK";
            }
        }
        return "NOT_ON_LOAN";
    }

    // 用 == 比較：同一位社員、同一件器材指的是同一個物件（物件相等的 equals 留到第 7–8 週）。
    public boolean isOnLoan(Equipment equipment) {
        for (Loan loan : loans) {
            if (loan.isActive() && loan.getEquipment() == equipment) {
                return true;
            }
        }
        return false;
    }

    public int countActiveLoans(Member member) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.isActive() && loan.getMember() == member) {
                count++;
            }
        }
        return count;
    }

    public int getLoanCount() {
        return loans.size();
    }

    // 「列出目前未歸還的紀錄」：回傳文字，不交出清單本身。
    public String activeLoanReport() {
        String report = "";
        for (Loan loan : loans) {
            if (loan.isActive()) {
                report += loan.describe() + "\n";
            }
        }
        return report.isEmpty() ? "(none)" : report.trim();
    }
}
