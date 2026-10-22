// 教師檢查工具（claude 版本）：檢查作業延伸題「社團器材借用」參考解答的借用規則。
// 與同資料夾的 Member、Equipment、Loan、Club 一起編譯；學生必做的是設計圖，不是這份程式。
public class ClubCheck {
    static int passed = 0;
    static int total = 0;

    public static void main(String[] args) {
        Club club = new Club();
        Equipment cam = new Equipment("CAM-01", "Camera");
        Equipment mic = new Equipment("MIC-01", "Microphone");
        Equipment tri = new Equipment("TRI-01", "Tripod");
        Equipment outside = new Equipment("X-99", "Not in club");
        club.addEquipment(cam);
        club.addEquipment(mic);
        club.addEquipment(tri);
        Member amy = new Member("B11001", "Amy");
        Member ben = new Member("B11002", "Ben");

        check("amy borrows cam", club.borrow(amy, cam, "2026-10-20"), "OK");
        check("ben borrows cam", club.borrow(ben, cam, "2026-10-20"), "ON_LOAN");
        check("amy borrows mic", club.borrow(amy, mic, "2026-10-21"), "OK");
        check("amy borrows tripod (3rd)", club.borrow(amy, tri, "2026-10-21"), "LIMIT_REACHED");
        check("unknown equipment", club.borrow(ben, outside, "2026-10-21"), "UNKNOWN_EQUIPMENT");
        check("rejected adds no loan", "" + club.getLoanCount(), "2");
        check("active loans", club.countActiveLoans(amy) + " " + club.countActiveLoans(ben), "2 0");

        check("amy returns cam", club.giveBack(cam, "2026-10-22"), "OK");
        check("cam returned twice", club.giveBack(cam, "2026-10-23"), "NOT_ON_LOAN");
        check("tripod never borrowed", club.giveBack(tri, "2026-10-23"), "NOT_ON_LOAN");
        check("amy borrows tripod after return", club.borrow(amy, tri, "2026-10-22"), "OK");
        check("ben borrows cam after return", club.borrow(ben, cam, "2026-10-22"), "OK");
        // 歷史紀錄會一直增加：amy 共有 3 筆紀錄，但同時未歸還只有 2 筆。
        check("history vs active", club.getLoanCount() + " " + club.countActiveLoans(amy), "4 2");
        check("isOnLoan", club.isOnLoan(cam) + " " + club.isOnLoan(mic) + " " + club.isOnLoan(outside),
                "true true false");
        check("report", club.activeLoanReport().replace("\n", " | "),
                "MIC-01 B11001 2026-10-21 -> (not returned) | TRI-01 B11001 2026-10-22 -> (not returned)"
                        + " | CAM-01 B11002 2026-10-22 -> (not returned)");

        Loan loan = new Loan(ben, mic, "2026-10-01");
        check("close twice", loan.close("2026-10-02") + " " + loan.close("2026-10-03") + " " + loan.describe(),
                "true false MIC-01 B11002 2026-10-01 -> 2026-10-02");
        check("empty report", new Club().activeLoanReport(), "(none)");

        System.out.println(passed + "/" + total + " passed");
    }

    static void check(String label, String actual, String expected) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println("PASS " + label + " -> " + actual);
        } else {
            System.out.println("FAIL " + label + " -> " + actual + " (expect " + expected + ")");
        }
    }
}
