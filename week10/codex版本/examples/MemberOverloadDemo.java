// 這個短例把三個類別放在同一個檔案裡。
// Java 只要求 public 類別（MemberOverloadDemo）的名稱和檔名相同；主線程式仍是一個類別一個檔案。
//
// VipMember 裡有三個都叫 describe 的方法，差別在參數：
//   describe()        沒有參數，和父類別 Member 的完全相同 → 覆寫：執行時依實際物件決定用哪一版
//   describe(int)     多了一個 int 參數 → 過載：是另一個新方法，編譯時依引數決定
//   describe(String)  多了一個 String 參數 → 過載
// 類別：Member（一般會員）
// 意義：父類別，只有一個沒有參數的 describe()。
// 作用：讓子類別 VipMember 覆寫它。
class Member {
    // describe：回傳一般會員的說明文字。
    public String describe() {
        return "一般會員";
    }
}

// 類別：VipMember（VIP 會員）
// 意義：一種會員（extends Member），有三個都叫 describe 的方法。
// 作用：同時示範覆寫（describe()）與過載（describe(int)、describe(String)）。
class VipMember extends Member {
    // describe()：覆寫父類別的版本，回傳 VIP 的說明文字。
    @Override
    public String describe() {
        return "VIP 會員";
    }

    // describe(int)：過載版本，說明文字加上點數。
    public String describe(int points) {
        // 父類別只有 describe()。新增參數會形成另一個簽名。
        return "VIP 會員 點數=" + points;
    }

    // describe(String)：過載版本，說明文字加上活動名稱。
    public String describe(String event) {
        return "VIP 會員 活動=" + event;
    }
}

// 類別：MemberOverloadDemo（程式入口）
// 意義：觀察覆寫與過載差別的程式入口。
// 作用：用 Member 型態的變數呼叫 describe()，再用 VipMember 變數呼叫兩個過載版本。
public class MemberOverloadDemo {
    // main：程式進入點。印出三行說明文字。
    public static void main(String[] args) {
        VipMember vip = new VipMember();
        // 指向同一個物件。編譯時由 Member 找簽名，執行時由 VIP 提供覆寫。
        Member member = vip;
        System.out.println(member.describe());
        System.out.println(vip.describe(30));
        System.out.println(vip.describe("迎新"));
    }
}
