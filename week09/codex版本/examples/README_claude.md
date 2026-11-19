# 第 9 週示範程式（claude 版說明）

本資料夾原本只有一個範例（`FixtureDemo` + `SampleSource`），把「介面」和「巢狀類別、匿名類別」放在同一個程式裡。教師要求把概念分開，Anthropic Claude（Claude Code）因此改寫成三個獨立範例，每個範例只講一個新概念。三個範例都不是探測車作業的答案：名稱與數字不同，結構可以對照。

| 範例 | 概念 | 檔案 | 執行入口 | 對應講義 |
|---|---|---|---|---|
| 一 | 抽象類別、抽象方法 | `Parcel.java`（抽象）、`NormalParcel.java`、`ExpressParcel.java` | `ParcelDemo` | B（TODO 1–2 之前） |
| 二 | 介面、`implements`、介面當參數型態 | `Weighable.java`（介面）、`Suitcase.java`、`Cat.java` | `ScaleDemo` | C（TODO 3–4 之前） |
| 三 | 具名 `static` 巢狀類別、匿名類別 | `FixtureDemo.java`（沿用範例二的 `Weighable`） | `FixtureDemo` | D |

範例三使用範例二的介面，所以 10 個檔案一起編譯。在自己的 `java-09` 建 `examples/` 資料夾放這 10 個檔案，與作業的 `src/` 分開編譯：

```powershell
javac -encoding UTF-8 -d out-examples examples/*.java
java -cp out-examples ParcelDemo
java -cp out-examples ScaleDemo
java -cp out-examples FixtureDemo
```

## 範例一｜抽象類別：包裹運費

`Parcel` 保存名稱與重量，並寫好運費流程 `fee()`＝基本費 60 元＋重量×每公斤費率。每公斤費率 `ratePerKg()` 宣告為抽象方法，由 `NormalParcel`（每公斤 10 元）和 `ExpressParcel`（每公斤 25 元）各自提供。

觀察重點：

- 抽象類別仍有欄位、建構子和寫好的方法；子類別用 `super(name, weight)` 讓父類別保存資料。
- `fee()` 寫在父類別，但其中的 `ratePerKg()` 依實際物件執行子類別的版本。
- `Parcel` 可以當作變數和參數的型態，卻不能 `new Parcel(...)`。

## 範例二｜介面：行李箱和貓上同一個秤

`Weighable` 只宣告 `int getWeight()`。`Suitcase`（空箱重量＋裝進的物品）與 `Cat`（體重，還會 `meow()`）沒有共同父類別，但都 `implements Weighable`，所以都能交給 `ScaleDemo.weigh(String, Weighable)`。

觀察重點：

- 介面方法自動是 `public`，實作時也必須寫 `public`。
- 參數或變數的宣告型態是 `Weighable` 時，只能呼叫 `getWeight()`；要叫 `meow()` 必須用 `Cat` 型態的變數。
- 範例一的 `Parcel` 也有 `getWeight()`，但沒有宣告 `implements Weighable`，所以不能放上秤。Java 看的是「有沒有宣告實作」，不是「方法名稱剛好一樣」。作業 TODO 3b 的 `Rover` 也是這個情況。

## 範例三｜巢狀類別與匿名類別：固定重量的測試資料

`FixtureDemo` 用兩種寫法各做一個「固定回傳 2 公斤」的 `Weighable`：具名的 `private static class FixedWeight`，以及 `new Weighable() { ... }` 匿名類別。兩個物件交給同一個 `show`。編譯後可在 `out-examples` 看到 `FixtureDemo$FixedWeight.class` 和 `FixtureDemo$1.class`，後者是編譯器替匿名類別取的名字。

## 預期輸出

先寫預測，再展開核對。

<details>
<summary>完成預測後展開</summary>

```text
書籍 3公斤 運費=90元
藥品 3公斤 運費=135元
```

```text
行李箱 重量=13公斤
貓 重量=5公斤
咪咪：喵～
同一隻貓用 Weighable 變數讀取：5公斤
```

```text
測試資料重量=2
測試資料重量=2
```

</details>

## 編譯實驗

`ParcelDemo.java`、`ScaleDemo.java` 裡留有被註解掉的實驗行。請把 10 個檔案複製到 `experiments/` 副本，一次只取消一行的註解，先預測再編譯，記下第一個相關的錯誤訊息。正式的 `examples/` 保持可編譯。

| 實驗 | 取消註解的那一行 | 要回答的問題 |
|---|---|---|
| 1 | `ParcelDemo`：`new Parcel("未分類", 1)` | 抽象類別能不能直接建立物件？ |
| 2 | `ScaleDemo`：`item.meow()` | 宣告型態是介面時，能呼叫實作類別自己的方法嗎？ |
| 3 | `ScaleDemo`：`weigh("包裹", new NormalParcel(...))` | 方法名稱相同、但沒有 `implements` 的類別能傳進來嗎？ |

<details>
<summary>教師核對：OpenJDK 22 的診斷</summary>

1. `Parcel is abstract; cannot be instantiated`
2. `cannot find symbol`，`symbol: method meow()`，`location: variable item of type Weighable`
3. `incompatible types: NormalParcel cannot be converted to Weighable`

其他 JDK 版本的字句可能略有不同，關鍵詞相同。

</details>
