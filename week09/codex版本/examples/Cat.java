// 類別：Cat（範例二：實作 Weighable 的類別之二；claude 依教師要求新增）
// 意義：一隻貓，有名字和體重，還會叫。
// 作用：示範「完全不像行李箱」的類別，只要實作同一個介面，也能放上同一個秤。
public class Cat implements Weighable {
    // name：貓的名字。
    private final String name;
    // weight：貓的體重（公斤），範例中建立後不變。
    private final int weight;

    // Cat：建構子。收到名字與體重。
    public Cat(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    // meow：回傳貓的叫聲。這是 Cat 自己的方法，Weighable 介面裡沒有它。
    public String meow() {
        return name + "：喵～";
    }

    // getWeight：實作介面方法，直接回傳 weight 欄位。
    @Override
    public int getWeight() {
        return weight;
    }
}
