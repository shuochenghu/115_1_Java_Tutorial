// 第六週作業延伸參考解答（claude 版本）：社員。只記身分，不記「借了幾件」。
// 借了幾件可以從 Club 的借用紀錄算出來；另外存一份計數，兩邊就可能對不起來。
public class Member {
    private final String studentId;
    private final String name;

    public Member(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }
}
