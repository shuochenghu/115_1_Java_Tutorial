// 介面：Weighable（第 9 週範例二：介面；claude 依教師要求新增）
// 意義：「可以放上秤量重量的東西」的共同約定。介面只規定要有哪些方法，不提供方法內容，也沒有欄位。
// 作用：讓 ScaleDemo 的 weigh 方法能接收行李箱和貓這兩種沒有繼承關係的物件。
//   範例三 FixtureDemo 也用這個介面示範巢狀類別與匿名類別。
public interface Weighable {
    // getWeight：回傳目前重量，單位是公斤，不為負。
    //   介面方法沒寫 public 也自動是 public。實作的類別必須提供這個方法，而且讀取重量時不能改變物件狀態。
    int getWeight();
}
