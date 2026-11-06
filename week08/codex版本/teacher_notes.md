# 第八週教師筆記｜覆寫與多型基礎

初稿由Codex生成。完整答案與teacher程式供教師備課，不發作學生初版。學生熟悉C# WinForms不代表已會繼承或多型，先做短診斷。

## 備課、節奏與範圍

| 分鐘 | 活動 | 教師安排與最低成果 |
|---|---|---|
| 0–10 | A | 取回十檔、版本與前週診斷；不要先跑會員答案 |
| 10–30 | B | B1預測7、B2註記實驗5、B3小修改8 |
| 30–50 | C | 型態表8、隔離編譯7、單一物件圖5 |
| 50–60 | D1 | 從需求追蹤execute到產量方法 |
| 60–68 | D2 | 學生自行改產量來源，一般車回歸 |
| 68–77 | D3 | 學生自行宣告覆寫；不要立即顯示完整方法 |
| 77–85 | D4 | 自行建立兩組任務，show只提供報表 |
| 85–100 | D5 | 比較完整輸出、兩個邊界與本人反例 |
| 100–125 | E | 預測5、反例6、編譯診斷4、修正重測7、Prompt3 |
| 125–135 | F | 補圖、產圖、課末檢核與遷移選題 |
| 135–150 | G | 保存、push、遠端核對、新clone編譯 |

150分鐘為未試教估計。進度落後先把B3文字整理、C3圖的美化、E5文字與個人反例完整整理課後補；保留本人至少一次反例實測、E4修正重測與最後15分鐘。課後只要求一個小型遷移，45–60分鐘是暫估，不硬化成工時評量。

先準備JDK、VS Code Java支援、IntelliJ來源根／SDK／main、前週PlantUML jar。JDK不鎖版，不加固定--release。會員已三檔，可沿用VS Code示範後用IDEA開同一java-08；不要求每題在兩套工具重做。GUI與教室可用性須實際課前確認。

## A答案

A1：工作目錄是學生java-08；四個主入口MemberDemo、MissionLog、HeavyRoverTry、CompareMissions。JDK依本人實際輸出，不抄作者22。starter十檔可合併編譯，只有會員與一般任務完成；HeavyRoverTry兩行DIFF、CompareMissions為NOT_READY。

A2：第七週探索2份；drill是新增入口，沒有改變execute呼叫的private explore。本週從此動機引入產量擴充。

A3：Rover保存目前狀態及行動；Mission負責讀腳本、停止與歷史；MissionStep是行動後快照。舊快照不隨之後充電變動。

A4：子類別不能直接讀寫父類別private欄位；protected也允許同套件存取。本課程仍限制局部方法由行動實作使用，不在main直接呼叫產量或更新方法。

## B答案

B1完整輸出：

```text
Member constructor: Amy
Member constructor: Bob
VipMember constructor: Bob
Member Amy / points=12
VIP Bob / points=28 / bonus=1
VIP Bob / points=28 / bonus=1
```

memberView與bob指向同一個VipMember，兩次呼叫相同實作。新增Member型態參考不會再跑建構子，也不複製點數。

B2：@Override要求編譯器檢查此宣告是否正確覆寫／實作上層方法；註記本身不製造多型。describeVIP名稱不同，在本例保留@Override會報does not override or implement a method from a supertype。診斷具體字句依JDK可能不同。去掉註記只會新增另一個方法，原describe呼叫仍不會選到它；請學生保留註記修正宣告。

B3參考答案：

```java
@Override
public String describe() {
    return super.describe() + " / VIP";
}
```

建構訊息三行不變，最後三行變成：

```text
Member Amy / points=12
Member Bob / points=28 / VIP
Member Bob / points=28 / VIP
```

super.describe()指定呼叫父類別版本，仍讀同一個物件內Member宣告的狀態，不建立另一個物件。這是小型實作體驗；更複雜的組合設計不列本週要求。此修改只在experiments，不要求正式src與原範例輸出一起變動。

## C答案與語意

| 變數 | 宣告型態 | 實際物件 | describe能否編譯 | 實際方法 |
|---|---|---|---|---|
| bob | VipMember | VipMember | 能 | VipMember.describe |
| memberView | Member | VipMember | 能 | VipMember.describe |

C2：memberView.claimBonus()不能編譯，Member沒有此方法；memberView.describe()能編譯，Member有宣告，執行時使用VipMember覆寫。改describe(int amount)且保留@Override不能編譯，並未覆寫無參數describe。只解釋本例的宣告不符，過載完整選擇規則留第十週。不用轉型替這個活動解套。

C3：圖畫一個VipMember物件，bob與memberView兩條參考線指向它；不是兩個物件或兩份點數。可呼叫成員先依編譯時型態與宣告判定；本週可覆寫的實例方法，在執行時依實際物件選擇實作。不能把所有方法、欄位、static、private都概括為同一規則。

如需C#對照，只提醒Java本例沒有virtual關鍵字；不要暗示C# WinForms經驗已包含完整virtual／override知識。

## D答案與檢查點

D1呼叫追蹤：Mission.run呼叫Rover.execute(1)，通過停止檢查後進入Rover.private explore；足夠能源才呼叫exploreYield。若物件是HeavyRover，使用HeavyRover的產量方法，仍由Rover.explore更新一次樣本與行動。

D1-1：父類別private方法不被繼承／覆寫，子類別同名private方法是另一個方法。不要為教覆寫把private explore整段改protected並複製所有狀態更新，會同時增加錯誤負擔。用短產量方法集中本週新增觀念。

D2（TODO 1）教師答案：

```java
private String explore() {
    if (!spendEnergy(6)) { return "NOT_ENOUGH_ENERGY"; }
    addSamples(exploreYield());
    countAction();
    return "OK";
}
```

產量方法已提供回2，只改呼叫位置。一般車預設仍6/5/3 SUCCESS，舊完整輸出不變；重型車未覆寫前仍有兩行DIFF。單獨只完成TODO 2但保留addSamples(2)，也不會改變行為，學生應追蹤是否真正呼叫產量方法。

D3（TODO 2）教師答案：

```java
@Override
protected int exploreYield() {
    return 3;
}
```

產量方法不改狀態；若在此也countAction，成功探索會計兩次。本週同樣回int、無參數、protected，不需引入協變回傳或例外宣告規則。

| 重型車連續操作 | 回傳 | energy | samples | actionCount | drillCount |
|---|---|---:|---:|---:|---:|
| drill() | OK | 12 | 3 | 1 | 1 |
| execute(1) | OK | 6 | 6 | 2 | 1 |
| drill() | STOPPED | 6 | 6 | 2 | 1 |

HeavyRoverTry十五行完整輸出：

```text
drill: OK (expect OK) OK
after drill: 12/3/1/1 (expect 12/3/1/1) OK
explore: OK (expect OK) OK
after explore: 6/6/2/1 (expect 6/6/2/1) OK
done drill: STOPPED (expect STOPPED) OK
done unchanged: 6/6/2/1 (expect 6/6/2/1) OK
low: NOT_ENOUGH_ENERGY (expect NOT_ENOUGH_ENERGY) OK
low unchanged: 7/0/0/0 (expect 7/0/0/0) OK
exact: OK (expect OK) OK
exact state: 0/3/1/1 (expect 0/3/1/1) OK
zero cannot charge: STOPPED (expect STOPPED) OK
initial zero: STOPPED (expect STOPPED) OK
zero unchanged: 0/0/0/0 (expect 0/0/0/0) OK
initial success: STOPPED (expect STOPPED) OK
success unchanged: 20/5/0/0 (expect 20/5/0/0) OK
```

D4（TODO 3）：完整main見teacher/CompareMissions.java。兩個參考都是Rover，各指向新建的不同車；兩個新Mission各run同一個script一次，再各show。共用腳本只讀不修改，沒有共享車或任務。show只負責顯示，不代寫學生建立任務的核心學習。

D5兩段完整預期輸出：

```text
=== normal ===
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
step 3: command=2, result=OK, energy=6
counts[0..3]=[0, 2, 1, 0]
steps=3, ok=3, rejected=0, ignored=0
lowest energy=6
final energy=6, samples=5, actionCount=3 -> SUCCESS
battery=30.0
=== heavy ===
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
counts[0..3]=[0, 2, 0, 0]
steps=2, ok=2, rejected=0, ignored=1
lowest energy=8
final energy=8, samples=6, actionCount=2 -> SUCCESS
battery=40.0
```

Heavy第二次探索已得6份，Mission下一輪先查停止，不讀掃描，所以counts及最低能源不同。比較的是各自一次完整任務，不應為了同樣讀三筆而取消停止規則。

6/2跑{1,3}一般車0/4/1 EXHAUSTED，重型車0/5/1 SUCCESS，均只有1步；成功優先。5/0跑{1}兩車都記一筆NOT_ENOUGH_ENERGY，狀態5/0/0，outcome UNFINISHED，counts[1]=1、ok0、rejected1。

本人反例不限定答案。可用HeavyRover(6,1)探索驗證只計一次、樣本4而耗盡；或HeavyRover(20,5)腳本{1}驗證不讀不記、狀態不變。要求學生說明可抓錯誤，不只重抄教師案例。

## E答案與最小修正

E1：模擬AI能編譯，drill後是12/3/1/1；execute(1)仍沿用Rover.explore與繼承的產量2，所以為6/5/2/1。沒有真正覆寫產量方法。

E2兩行DIFF：

```text
after explore: 6/5/2/1 (expect 6/6/2/1) DIFF
done unchanged: 6/5/2/1 (expect 6/6/2/1) DIFF
```

其他十三行為OK。樣本5已達標，所以最後drill仍STOPPED；只測「是否成功」抓不到產量錯誤，要看精確samples。這是本次反例的重點。

E3：子類別private explore加@Override會編譯失敗，診斷does not override or implement a method from a supertype。Rover的private explore不是可覆寫入口。把產量改static不符合實例方法覆寫設計，不能用static隱藏解決。限制本週static判讀為文字，不延伸完整隱藏／欄位規則。

E4：移除AI private explore整段，加入D3產量方法；保留正確drill及父execute。相同HeavyRoverTry十五行全部OK；再看正常兩車任務仍符合D5。不將teacher整份答案蓋進去冒充本人最小修正。

E5示範Prompt（教師示範，非真實AI對話）：

> 請繼承Rover，覆寫protected int exploreYield()，無參數、回int，回傳3並加@Override。此方法只回傳數量，不扣能源、不改樣本、不加行動次數，也不重複宣告狀態。保留private explore、execute與drill。請先預測20/0跑{1,1,2}與6/2跑{1,3}的最終狀態及已讀步數，再提供最小修改。

採納與拒絕理由須對應實測，AI宣稱正確不算證據。

## F與G答案／檢查

F1：補Rover的# exploreYield(): int與HeavyRover相同方法，註記override與產量；保留Rover的- explore(): String。繼承三角形指Rover，Mission關聯的欄位型態仍Rover。參考圖diagrams/dispatch_reference.puml／SVG只列指定成員，明示省略。不要把樣本多型用兩份狀態解釋。

F2：1不會，物件仍HeavyRover；2可以，bob/memberView即例；3父流程呼叫可覆寫產量方法；4父流程已計次，子方法再計會重複；5停止、扣能源、記次與指令分派容易重複並不同步。

F3：只記自選題規格；不提前把課後子類別當全班先備。G依本人實際成果核對，不能拿作者驗證替代學生clone／GUI／遠端操作。網路失敗保留備份及錯誤，不虛填push成功。

## 課後遷移參考與評量

SurveyRover完整教師參考在teacher/transfer，與主線教師檔分開編譯。SurveyRoverTry四行輸出：

```text
6/3/3/UNFINISHED
0/5/1/SUCCESS
NOT_ENOUGH_ENERGY/5/0/0
STOPPED/20/5/0
```

第一條同腳本一般車為SUCCESS；少量採樣車無法在三步內完成，是規格效果，不因名稱較專業就視為錯誤。學生仍須加本人反例，參考答案不能代替學生自由題驗收。自訂題依明訂產量1..4、成本6與同一Mission契約驗收；圖可只在原圖加一個子類別，不畫第二套全圖。

## 常見錯誤與診斷

| 現象 | 原因與處理 |
|---|---|
| Heavy仍得2份 | 父explore仍寫死2，或子方法未真正覆寫；逐段追蹤呼叫 |
| @Override報錯 | 比較名稱、參數、可見性與父方法是否private，不直接刪註記 |
| 行動次數多1 | 產量方法額外改狀態；只保留回傳值 |
| 父類別參考不能drill | Rover沒有此宣告；本週用共同操作，不要求轉型 |
| Heavy仍讀最後掃描 | Mission停止檢查被改、共用任務或輸出硬寫；恢復原契約 |
| AI只看SUCCESS就通過 | 5與6都達標，要檢查精確樣本數 |
| 先改Try的expect消除DIFF | 驗收規格遭改動；恢復預先算好的期望，修正實作 |
| private explore加@Override失敗 | 正是預定編譯實驗；放隔離副本，記錄後還原 |
| duplicate class | 同名starter／teacher／ai混編；分開來源與out |
| 圖改了但顯示舊結果 | puml重產SVG，開檔確認，檢查DESIGN引用位置 |

## 來源與教學邊界

2026-10-30以專案Deitel《Java How to Program, Early Objects》11e EPUB實際目錄核對：§9.4 Relationship Between Superclasses and Subclasses、§10.2 Polymorphism Examples、§10.3 Demonstrating Polymorphic Behavior；§10.6 Allowed Assignments Between Superclass and Subclass Variables僅取父類別參考指向子物件。§10.4抽象類別、§10.9介面留第九週；不猜紙本頁碼、不複製全文。

官方語意依據：

- [JLS §8.4.8.1](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.4.8.1)：實例方法覆寫。
- [JLS §8.4.8.3](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.4.8.3)：可見性與覆寫限制、private方法界線。
- [JLS §15.12.4.4](https://docs.oracle.com/javase/specs/jls/se25/html/jls-15.html#jls-15.12.4.4)：執行時選擇實際方法。
- [Override API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Override.html)：編譯期覆寫檢查。
- [Object API](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Object.html#toString())：toString僅選做閱讀。

官方文件語意與本機JDK實測分列，不把Java25文件引用當成Java25實機驗證。C#對照只作熟悉背景橋接；Java本例的方法無須virtual關鍵字，不展開C#完整規則。
