# 第 11 週學生實作｜泛型與型別安全

依 [README](README.md) 準備，另存為當日 `LAB_REPORT.md`。先寫預測，再編譯、執行。沒有做的證據標「待補」，不要抄教師實測成自己的結果。

## A｜先備診斷與獨立短例（第 1 節，50 分鐘）

A1（10 分鐘）看 `Mission` 的 `ArrayList<MissionStep>`。get 取出時知道什麼型態？若 add 一個 String，何時發現？再說明同一個 HeavyRover 透過 Rover、SampleSource 參考呼叫報表時，為何可能選不同簽名。回答與理由：待填。

A2（18 分鐘）先比較 [範例說明](examples/README.md) 的兩個重複盒子，再依序看兩種「只寫一次」的做法：

1. `ObjectBox.java`：欄位用 Object，什麼都能放，取出時要自己轉型。
2. `Box.java` 與 `TypedBoxDemo.java`：欄位用 T，建立盒子時指定型態，取出不必轉型。

每個程式先寫兩行預期，才執行。記下 T、String、"採樣完成" 各代表型態參數、型態引數或資料：待填。

| 運算式／實驗 | 編譯期型態或預測 | 實際與理由 |
|---|---|---|
| ObjectBox 的 `message.get()` | 待填 | 待填 |
| ObjectBox 取消 `wrongCount` 那行：能否編譯？何時失敗？ | 待填 | 待填 |
| Box 的 `message.get()` | 待填 | 待填 |
| Box 的 `number.get()` | 待填 | 待填 |
| `Box<int>` | 待填 | 待填 |
| `Integer wrong = message.get();`（Box 版） | 待填 | 待填 |

最後比較：同樣是「把字串當成 Integer」，ObjectBox 版和 Box 版各在什麼時候被發現？待填。

實驗行由教師在隔離副本示範，或自己複製 examples 到 experiments，一次取消一行再編譯、執行；正式 examples 保持正確。

A3（15 分鐘）地面站現在要有樣本盒群組與重型車群組。只用 `ArrayList<SampleSource>` 能不能混放與加總？取出後能否直接使用 HeavyRover 的能源 getter？說明何時已足夠、何時想保留具體型態：待填。教師介紹 `T extends SampleSource`，再回答：介面上限為何也寫 extends？待填。

A4（3 分鐘，閱讀）看教師的 `GenericMethodDemo`。型態參數 `<T extends SampleSource>` 寫在哪裡？比較泛型類別中的 `T get(int)`：它有沒有重新宣告方法自己的 T？兩個 9 份樣本盒平手選誰？待填。

A5（4 分鐘）讀 [類別演進圖](diagrams/evolution.svg) 及 [TODO 圖](diagrams/todo.svg)，指出哪兩個類別本週新增、哪些車輛與報表仍沿用。記自己的 java／javac 版本與 main 入口：待填。

## B｜完成一個可保留型態的群組（第 2 節前 35 分鐘）

先花 10 分鐘看提供的 `SampleGroupDemo`：一台新車執行任務一次，再把原參考放入兩個群組。展示入口已提供，不用修改檢查數字來消除「尚未完成」。

**TODO 1** 在 `SampleGroup.java` 宣告 private `ArrayList<T>`，在建構子建立空清單。每個群組有自己的清單，不能使用 static 共用。此時保留 `class SampleGroup<T>`，上限留 TODO 4。

**TODO 2** 完成 `add(T source)`。把原參考存進清單，維持順序，不改來源、不轉型。

**TODO 3** 完成 `T get(int index)` 與 `int size()`，移除佔位回傳。get 回原物件，不能改回 Object。索引由呼叫端先確認合法。

**TODO 4** 分兩步，都在自己的 `SampleGroup.java` 完成，不用另建副本：

1. 先預測：無上限的 T 能否直接呼叫 `getSamples()`？類別宣告保持 `SampleGroup<T>`，在 `totalSamples` 用普通迴圈對每筆來源呼叫 `getSamples()` 並累加，編譯，記下第一個相關診斷。
2. 在類別宣告替 T 加上 `extends SampleSource`，重新編譯並執行。空群組要回 0；不回傳 size，也不把結果寫死成 10。

不要用 `(SampleSource)` 轉型代替上限，也不要在 `add` 時先存一份總和。這兩種寫法的九行展示都可能完全正確，C1 的探索後總和與 C2 第 2 題才抓得到。

25 分鐘實作內每次修改都重新編譯；全部完成後再驗收 C。展示入口會分段提示：起始時印「TODO 1～3（保存與取回）還沒通過」；TODO 1–3 正確但總和未完成時印「TODO 1～3 已能保存與取回，TODO 4 總樣本數還沒通過」。若要自己看存入／取出，可在 experiments 自建入口，只在 size 足夠時讀第 0 筆。

| 檢查點 | 你修改的位置與理由 | 實測結果 |
|---|---|---|
| 每個群組有自己的清單 | 待填 | 待填 |
| add 的參數與 get 的回傳都使用 T | 待填 | 待填 |
| 沒有上限時呼叫 getSamples | 待填 | 待填 |
| 上限加在哪裡、統計為何能呼叫 getter | 待填 | 待填 |

## C｜完整輸出與編譯反例（第 2 節後 15 分鐘）

C1（10 分鐘）編譯正式 src，執行 `SampleGroupDemo`，九行應為：

```text
樣本盒群組 筆數=2 總樣本數=4
車輛群組 筆數=1 總樣本數=6
混合群組 筆數=3 總樣本數=10
重型探測車 樣本數=6 能源=8
重型探測車 樣本數=6
取回重型車 能源=8 鑽探次數=0
同一台車=true
空群組 筆數=0 總樣本數=0
任務狀態=8/6/2 日誌筆數=2
```

兩行車輛報表分別使用哪個 get 回傳型態、哪個 print 簽名？待填。看 [同一物件參考圖](diagrams/references.svg)，用箭頭標示 mixed 和 vehicles 的第一筆指向誰，並說明為何沒有兩份能源：待填。

**固定自測：探索後總和。** 九行展示裡的車加入群組後就不再動，所以看不出 `totalSamples` 是「每次重讀目前數量」還是「沿用加入時的數字」。在 `java-11/experiments/` 建立下面這個檔案，用一台**沒有交給 Mission 的新車**檢查：

```java
// 固定自測：群組加入一台新車後，讓車從外部探索一次，再看總和有沒有跟著變。
public class LiveTotalTry {
    public static void main(String[] args) {
        HeavyRover fresh = new HeavyRover(20, 0);    // 新車，沒有交給 Mission
        SampleGroup<HeavyRover> live = new SampleGroup<>();
        live.add(fresh);
        System.out.println("加入後 總樣本數=" + live.totalSamples());
        fresh.execute(1);                             // 指令 1：探索一次
        System.out.println("探索後 總樣本數=" + live.totalSamples()
                + " 車上樣本數=" + fresh.getSamples());
    }
}
```

先預測兩行輸出：待填。正式 src 編譯成功後，在 `java-11` 執行（Windows 的類別路徑用分號分隔）：

```powershell
javac -encoding UTF-8 -cp out -d out-exp experiments/LiveTotalTry.java
java -cp "out;out-exp" LiveTotalTry
```

實際結果與理由：待填。若「探索後」的總和和車上樣本數不同，代表你的總和沒有讀來源目前的數量，回 TODO 4 修正。

另在 experiments 自建一個小測試，改用不同的合法數量（例如 11 與 2），或同一個 4 份來源加入兩次。寫下想抓的錯誤、預期與實際：待填。不要改正式 Demo 的固定檢查值。

C2（5 分鐘）每段獨立判斷，在副本一次只編譯一題；課堂至少實測第 1、2 題，第 3 題可由教師示範後補整理。正式 src 不放刻意錯誤碼。第 2 題一定要搭配**自己完成的 SampleGroup** 編譯：若它竟然能通過，代表你的 T 沒有真正的上限（例如用轉型代替了 `extends`），回 TODO 4 修正。

```java
// 第 1 題
SampleGroup<HeavyRover> cars = new SampleGroup<>();
cars.add(new SampleTray(4));
// 第 2 題（另一個副本）
SampleGroup<String> words = new SampleGroup<>();
// 第 3 題（另一個副本）
SampleGroup<HeavyRover> cars = new SampleGroup<>();
SampleGroup<SampleSource> sources = cars;
```

| 題目 | 能否編譯與原因 | 第一個相關診斷 |
|---|---|---|
| 第 1 題 | 待填 | 待填 |
| 第 2 題 | 待填 | 待填 |
| 第 3 題 | 待填 | 待填 |

## D｜AI 判讀、最小修正與重測（第 3 節前 25 分鐘）

D1（5 分鐘）先保存人工完成版，於 AI_USAGE 用 3–5 句描述「接受的型態、取回型態、統計與無副作用、空資料結果、驗收案例」。預測重型車群組能否放進樣本盒：待填。

D2（8 分鐘）這是**教師設計的模擬 AI 回覆**，不是真實服務對話。複製已完成的全部 src Java 檔到 `java-11/ai-check`，用 [模擬 SampleGroup](ai/SampleGroup.java) 覆蓋其中同名檔，另放 [UnsafeGroupTry](ai/UnsafeGroupTry.java)。在 ai-check 操作：

```powershell
javac -encoding UTF-8 -Xlint:unchecked -d out-bad *.java
java -cp out-bad SampleGroupDemo
java -cp out-bad UnsafeGroupTry
```

這次編譯預期有 unchecked 警告，但仍產生可執行檔。先跑合法資料的 Demo，為何仍有九行正確結果？再跑非法來源的 UnsafeGroupTry，記警告、失敗種類與第一次和自己原始碼有關的行號：待填。`(T)` 只是轉型語法，不會把樣本盒製造成重型車。

D3（7 分鐘）只改造成型態限制流失的位置，把 add 的參數恢復 T、直接加入 source，移除轉型。先不執行，用新的輸出目錄編譯同一份反例：

```powershell
javac -encoding UTF-8 -Xlint:unchecked -d out-negative *.java
```

此時應在 `vehicles.add(new SampleTray(4))` **編譯失敗**，這是預期證據。不要在失敗後執行舊的 out-bad。

把 UnsafeGroupTry 移到 ai-check 外的證據資料夾，或移除這份副本的 `.java`、將片段留在報告。其他程式不變，編譯合法入口並重跑：

```powershell
javac -encoding UTF-8 -Xlint:unchecked -d out-fixed *.java
java -cp out-fixed SampleGroupDemo
```

修正後應無 unchecked 警告、九行一致。記最小修改、非法案例的拒絕位置及合法案例的重測：待填。

D4（5 分鐘）若需求就是混放車輛與樣本盒，應宣告什麼群組？為何不用硬轉成 T？本週的泛型能否自動保證 null、索引或來源的數值正確？說明採納／拒絕理由：待填。不以 SuppressWarnings 隱藏問題。

## E｜同儕說明與保存（第 3 節後 25 分鐘）

10 分鐘互相指認：T 宣告位置、add／get 的型態關係、兩行報表的簽名、來源參考與 MissionStep 快照差別。各留一項同儕回饋與修正：待填。

最後 15 分鐘：

- [ ] 正式 src 重新編譯，跑 SampleGroupDemo 與 CompareMissions；AI 的合法／非法證據分清楚。
- [ ] GitHub Desktop 檢查差異、commit、push，在 GitHub 核對本次檔案。
- [ ] 在原 repo 外新位置取回，重編譯 java-11/src，跑 SampleGroupDemo，記 commit、位置及實際結果。
- [ ] 排除 out、.class、ai-check、experiments 與憑證；不提交教師答案。失敗據實記錄，保留本機提交，不能寫成遠端成功。

保存證據：待填。依 [作業說明](assignment.md) 課後新增最大來源查詢。
