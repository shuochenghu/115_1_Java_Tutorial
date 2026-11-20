# 第 11 週獨立短例｜Object 盒子與泛型盒子

初稿由 Codex 製作；`ObjectBox.java` 對照例由 Claude 依教師指示補充（2026-11-19）。先用小盒子看清楚 T，再回探測車主線；這裡沒有 SampleGroup 的統計答案。

| 檔案 | 責任 |
|---|---|
| [ObjectBox.java](ObjectBox.java) | 對照例：欄位用 Object，取出要自己轉型；自帶 main，可單獨執行 |
| [Box.java](Box.java) | 保存一個 T，get 回傳同一個 T |
| [TypedBoxDemo.java](TypedBoxDemo.java) | 分別指定 String 與 Integer，先預測再取回 |

## 1. 重複的兩個盒子

教師先投影下面的重複片段，不要求學生另寫兩個類別。請指出兩份程式除了型態和類別名稱外，哪些流程完全相同。

```java
class StringBox {
    private String value;
    StringBox(String value) { this.value = value; }
    String get() { return value; }
}
class IntegerBox {
    private Integer value;
    IntegerBox(Integer value) { this.value = value; }
    Integer get() { return value; }
}
```

## 2. 第一種「只寫一次」：Object 盒子

`ObjectBox` 把欄位寫成 Object，字串、整數都放得進去，只要寫一個類別。代價是取出時編譯器只知道「這是 Object」，呼叫者要自己寫 `(String)`、`(Integer)` 轉型。轉錯型態時編譯器不會阻止，要等**執行**到那一行才失敗（ClassCastException，本週只辨識失敗的時機，不教例外處理）。

## 3. 第二種「只寫一次」：泛型盒子

`Box<T>` 把型態也留成可指定的位置。`new Box<>("採樣完成")` 傳入的字串是資料，`Box<String>` 的 String 才是型態引數。T 不代表某個固定數字，也不代表只能放探測車。編譯器記得每個盒子的 T，所以取出不必轉型，型態不符時在**編譯**就被拒絕。

## 執行

在自己的 `java-11` 執行，編譯成功再執行：

```powershell
javac -encoding UTF-8 -d out-examples examples/*.java
java -cp out-examples ObjectBox
java -cp out-examples TypedBoxDemo
```

預測後核對，`ObjectBox` 印：

```text
Object 盒 訊息=採樣完成
Object 盒 數量=7
```

`TypedBoxDemo` 印：

```text
訊息=採樣完成
數量=7
```

`Integer` 是 int 的包裝型態。此例把 7 放進盒子時自動裝箱，取回交給 int 時自動拆箱。Java 的型態引數用參考型態，不能寫 `Box<int>`。目前只需讀懂這一組對照。

## 實驗（在獨立副本，一次只取消一行註解）

| 檔案與那一行 | 能否編譯 | 何時發現錯誤 |
|---|---|---|
| ObjectBox：`Integer wrongCount = (Integer) message.get();` | 可以 | 執行到該行才 ClassCastException，前兩行已印出 |
| TypedBoxDemo：`Box<int> wrong = new Box<>(7);` | 不行 | 編譯時拒絕基本型態作為型態引數 |
| TypedBoxDemo：`Integer wrong = message.get();` | 不行 | 編譯時拒絕 String／Integer 不相容 |

同樣是「把字串當整數」，Object 盒到執行才出錯，泛型盒在編譯就被擋下。這也是第三節模擬 AI 用 `(T)` 轉型時要特別注意的原因。正式 examples 維持可編譯。

教師另以 [GenericMethodDemo](../teacher/GenericMethodDemo.java) 示範方法自己的 `<T>`，編譯 teacher 頂層程式後執行，無需將探測車相依檔案放進本資料夾。
