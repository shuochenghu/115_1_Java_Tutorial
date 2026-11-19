# 會員繼承獨立範例

先預測再執行MemberDemo。Member保管姓名與點數，VipMember新增領取加贈點數及bonusCount；不接主任務，不要求社團系統先備。

```powershell
javac -encoding UTF-8 -d out examples/*.java
java -cp out MemberDemo
```

上面在教材目錄執行；學生在自己的java-07改編譯src/*.java。

完整預期輸出：

```text
Member 建構子: Amy
Member 建構子: Bob
VipMember 建構子: Bob
Amy 的點數=12
Bob 的點數=28
Bob 的獎勵次數=1
```

buy每滿10元得1點，非正數不加點；claimBonus每次加20且bonusCount加1。沒有次數或每日限制，不要自行推論一次後不能再領。先用全新物件測試，避免前一個實驗更動點數。

父類別建構子處理父類別狀態，子類別建構子接著處理自己的狀態。本例把super寫在第一個敘述，建構子不被繼承。points是Member的private成員，不由VipMember繼承，但同一個VipMember物件仍有這份狀態，Member的方法負責存取。

兩個刻意編譯錯誤實驗與同套件protected實驗見student_lab C；請放隔離副本，實驗後還原。Object只簡介：Member沒寫extends時直接父類別是Object；不要求本週學反射或覆寫toString。

## 對照範例：不用繼承的 VIP 會員（claude 新增）

以下兩檔由 Claude 依教師要求新增，供課堂對照，不屬於學生必寫內容。

建議講解順序：先展示 `StandaloneVipMember`，請學生找出和 `Member` 重複的部分，並說明規則修改時要改兩處；再教 `extends` 與 `VipMember`；學完繼承後執行 `CompareMemberDemo`，回頭比較繼承省去了什麼。

| 檔案 | 用途 |
|---|---|
| `StandaloneVipMember.java` | 功能和 `VipMember` 相同，但沒有 `extends Member`：名字、點數、購物規則全部自己再寫一次 |
| `CompareMemberDemo.java` | 讓兩種 VIP 做同樣的購物與領取加贈，並排印出結果 |

```powershell
javac -encoding UTF-8 -d out examples/*.java
java -cp out CompareMemberDemo
```

完整預期輸出：

```text
--- 繼承版 VipMember ---
Member 建構子: Bob
VipMember 建構子: Bob
點數=28, 獎勵次數=1
--- 不用繼承版 StandaloneVipMember ---
StandaloneVipMember 建構子: Bob
點數=28, 獎勵次數=1
```

| 比較 | 繼承版 `VipMember` | 不用繼承版 `StandaloneVipMember` |
|---|---|---|
| 名字、點數欄位 | 由 `Member` 保存，不重寫 | 自己再宣告一份 |
| `getName`、`getPoints`、`buy`、`addPoints` | 沿用 `Member` 的 | 自己再寫一份 |
| 建構子 | `super(name)` 交給 `Member`，印兩行 | 自己設定全部欄位，只印一行 |
| 能不能存進 `Member` 型態的變數 | 可以（VIP 是一種會員） | 編譯錯誤：`incompatible types` |
| 購物規則要修改時 | 只改 `Member` 一處 | `Member` 和本類別兩處都要改 |

課堂實驗（在副本操作）：

1. 把 `Member.java` 的 `addPoints(price / 10)` 改成 `addPoints(price / 5)`，重新編譯執行 `CompareMemberDemo`。先預測兩位 VIP 的點數。實測繼承版變成 `點數=37`，不用繼承版仍是 `點數=28`：規則只改了一處，複製的那份沒有跟著變。
2. 依 `CompareMemberDemo.java` 末端註解，一次取消一行：`Member asMember = inherited;` 可以編譯；`Member notMember = standalone;` 出現 `incompatible types: StandaloneVipMember cannot be converted to Member`。用父類別型態的變數操作子類別物件，第 8 週會正式學。
