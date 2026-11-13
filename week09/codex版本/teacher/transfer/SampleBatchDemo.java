// 類別：SampleBatchDemo（遷移作業教師參考：示範入口）
// 意義：示範 SampleBatch 如何接上既有報表的程式入口。
// 作用：印出一般合計、零樣本，以及重讀同一個物件的結果。
public class SampleBatchDemo {
    // main：程式進入點。建立兩個 SampleBatch，用同一個報表方法印出三行。
    public static void main(String[] args) {
        SampleSource mixed = new SampleBatch(2, 5);
        SampleReport.print("混合樣本", mixed);
        SampleReport.print("空盒", new SampleBatch(0, 0));
        SampleReport.print("再次讀取", mixed);
    }
}
