// 類別：GenericMethodDemo（泛型方法的教師短示範）
// 意義：這個類別本身沒有 <T>；型態參數 <T extends SampleSource> 只屬於 moreSamples 這一個方法。
// 作用：對照 SampleGroup<T> 的 T get(int)：那裡的 T 沿用類別宣告，並沒有在方法上重新宣告。
// 使用約定：來源不為 null；moreSamples 不修改來源。
public class GenericMethodDemo {
    // moreSamples：回傳兩個來源中樣本數較多的「原物件」；平手時回傳左側。
    // 寫在 static 後、回傳型態 T 前的 <T extends SampleSource>，就是這個方法自己宣告的型態參數。
    // 因為回傳型態是 T，傳入兩個 SampleTray 時，結果可以直接指派給 SampleTray，不必轉型。
    public static <T extends SampleSource> T moreSamples(T left, T right) {
        if (left.getSamples() >= right.getSamples()) {
            return left;
        }
        return right;
    }

    // main：程式進入點。比較 4 份與 9 份樣本盒，再檢查兩個 9 份樣本盒平手時回傳哪一個。
    public static void main(String[] args) {
        SampleTray left = new SampleTray(4);
        SampleTray right = new SampleTray(9);
        // 編譯器由兩個 SampleTray 引數與接收的變數推斷 T 是 SampleTray。
        SampleTray selected = moreSamples(left, right);
        System.out.println("較多樣本=" + selected.getSamples());
        // 用 == 比較是不是同一個物件，而不只是樣本數相同。
        System.out.println("平手回左側=" + (moreSamples(right, new SampleTray(9)) == right));
    }
}
