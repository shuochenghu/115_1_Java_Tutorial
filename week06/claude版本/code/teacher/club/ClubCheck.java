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
        Equipment outside = new Equipment("X-99", "非社團成員");
        club.addEquipment(cam);
        club.addEquipment(mic);
        club.addEquipment(tri);
        Member amy = new Member("B11001", "Amy");
        Member ben = new Member("B11002", "Ben");

        check("Amy 借相機", club.borrow(amy, cam, "2026-10-20"), "成功");
        check("Ben 借相機", club.borrow(ben, cam, "2026-10-20"), "已借出");
        check("Amy 借麥克風", club.borrow(amy, mic, "2026-10-21"), "成功");
        check("Amy 嘗試借第三件器材（三腳架）", club.borrow(amy, tri, "2026-10-21"), "已達借用上限");
        check("查無器材", club.borrow(ben, outside, "2026-10-21"), "查無器材");
        check("遭拒時不增加借用紀錄", "" + club.getLoanCount(), "2");
        check("尚未歸還的借用紀錄", club.countActiveLoans(amy) + " " + club.countActiveLoans(ben), "2 0");

        check("Amy 歸還相機", club.giveBack(cam, "2026-10-22"), "成功");
        check("重複歸還相機", club.giveBack(cam, "2026-10-23"), "未借出");
        check("三腳架從未借出", club.giveBack(tri, "2026-10-23"), "未借出");
        check("Amy 歸還器材後借三腳架", club.borrow(amy, tri, "2026-10-22"), "成功");
        check("相機歸還後由 Ben 借用", club.borrow(ben, cam, "2026-10-22"), "成功");
        // 歷史紀錄會一直增加：amy 共有 3 筆紀錄，但同時未歸還只有 2 筆。
        check("全部歷史與未歸還紀錄", club.getLoanCount() + " " + club.countActiveLoans(amy), "4 2");
        check("isOnLoan", club.isOnLoan(cam) + " " + club.isOnLoan(mic) + " " + club.isOnLoan(outside),
                "true true false");
        check("報表", club.activeLoanReport().replace("\n", " | "),
                "MIC-01 B11001 2026-10-21 -> （尚未歸還） | TRI-01 B11001 2026-10-22 -> （尚未歸還）"
                        + " | CAM-01 B11002 2026-10-22 -> （尚未歸還）");

        Loan loan = new Loan(ben, mic, "2026-10-01");
        check("重複結束借用", loan.close("2026-10-02") + " " + loan.close("2026-10-03") + " " + loan.describe(),
                "true false MIC-01 B11002 2026-10-01 -> 2026-10-02");
        check("空報表", new Club().activeLoanReport(), "（無）");

        System.out.println(passed + "/" + total + " 項通過");
    }

    static void check(String label, String actual, String expected) {
        total++;
        if (actual.equals(expected)) {
            passed++;
            System.out.println("通過 " + label + " -> " + actual);
        } else {
            System.out.println("失敗 " + label + " -> " + actual + " （預期 " + expected + "）");
        }
    }
}
