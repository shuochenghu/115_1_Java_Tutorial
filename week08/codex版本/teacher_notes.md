# 第八週教師筆記｜覆寫與多型基礎

初稿由Codex生成。完整答案與teacher程式供教師備課，不發作學生初版。學生熟悉C# WinForms不代表已會繼承或多型，先做短診斷。

## 備課、節奏與範圍

| 分鐘 | 活動 | 教師安排與最低成果 |
|---|---|---|
| 0–10 | A | 取回十一檔、版本與前週診斷；不要先跑會員答案 |
| 10–30 | B | B1預測7、B2註記實驗5、B3小修改8 |
| 30–50 | C | 型態表8、隔離編譯7、單一物件圖5 |
| 50–58 | D1 | 從需求追蹤execute到產量方法 |
| 58–66 | D2 | 學生自行改產量來源，一般車回歸 |
| 66–74 | D3 | 學生自行宣告覆寫；不要立即顯示完整方法 |
| 74–82 | D4 | 自行建立兩組任務，show只提供報表 |
| 82–92 | D5 | 比較完整輸出、兩個邊界；本人反例可課後補 |
| 92–100 | D6 | 覆寫execute加入指令4，super交回其他指令；Mission不改 |
| 100–125 | E | 預測5、反例6、編譯診斷4、修正重測7、Prompt3 |
| 125–135 | F | 補圖、產圖、課末檢核與遷移選題 |
| 135–150 | G | 保存、push、遠端核對、新clone編譯 |

150分鐘為未試教估計。進度落後先把B3文字整理、C3圖的美化、E5文字與個人反例完整整理課後補；保留本人至少一次反例實測、E4修正重測與最後15分鐘。課後只要求一個小型遷移，45–60分鐘是暫估，不硬化成工時評量。

先準備JDK、VS Code Java支援、IntelliJ來源根／SDK／main、前週PlantUML jar。JDK不鎖版，不加固定--release。會員已三檔，可沿用VS Code示範後用IDEA開同一java-08；不要求每題在兩套工具重做。GUI與教室可用性須實際課前確認。

## A答案

A1：工作目錄是學生java-08；五個主入口MemberDemo、MissionLog、HeavyRoverTry、CompareMissions、DrillMissionTry。JDK依本人實際輸出，不抄作者22。starter十一檔可合併編譯，只有會員與一般任務完成；HeavyRoverTry兩行不符、CompareMissions為尚未完成、DrillMissionTry四行不符。

A2：第七週探索2份；drill是新增入口，沒有改變execute呼叫的private explore。本週從此動機引入產量擴充。

A3：Rover保存目前狀態及行動；Mission負責讀腳本、停止與歷史；MissionStep是行動後快照。舊快照不隨之後充電變動。

A4：子類別不能直接讀寫父類別private欄位；protected也允許同套件存取。本課程仍限制局部方法由行動實作使用，不在main直接呼叫產量或更新方法。

## B答案

B1完整輸出：

```text
Member 建構子: Amy
Member 建構子: Bob
VipMember 建構子: Bob
會員 Amy / 點數=12
VIP 會員 Bob / 點數=28 / 獎勵次數=1
VIP 會員 Bob / 點數=28 / 獎勵次數=1
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
會員 Amy / 點數=12
會員 Bob / 點數=28 / VIP
會員 Bob / 點數=28 / VIP
```

super.describe()指定呼叫父類別版本，仍讀同一個物件內Member宣告的狀態，不建立另一個物件。D6會在HeavyRover.execute再用一次super方法呼叫；更複雜的組合設計不列本週要求。此修改只在experiments，不要求正式src與原範例輸出一起變動。

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
    if (!spendEnergy(6)) { return "能源不足"; }
    addSamples(exploreYield());
    countAction();
    return "成功";
}
```

產量方法已提供回2，只改呼叫位置。一般車預設仍6/5/3 任務成功，舊完整輸出不變；重型車未覆寫前仍有兩行不符。單獨只完成TODO 2但保留addSamples(2)，也不會改變行為，學生應追蹤是否真正呼叫產量方法。

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
| drill() | 成功 | 12 | 3 | 1 | 1 |
| execute(1) | 成功 | 6 | 6 | 2 | 1 |
| drill() | 任務已停止 | 6 | 6 | 2 | 1 |

HeavyRoverTry十五行完整輸出：

```text
鑽探: 成功 （預期 成功） 相符
鑽探後: 12/3/1/1 （預期 12/3/1/1） 相符
探索: 成功 （預期 成功） 相符
探索後: 6/6/2/1 （預期 6/6/2/1） 相符
任務完成後鑽探: 任務已停止 （預期 任務已停止） 相符
完成後狀態維持: 6/6/2/1 （預期 6/6/2/1） 相符
能源不足: 能源不足 （預期 能源不足） 相符
能源不足時狀態維持: 7/0/0/0 （預期 7/0/0/0） 相符
能源剛好足夠: 成功 （預期 成功） 相符
能源剛好足夠時的狀態: 0/3/1/1 （預期 0/3/1/1） 相符
能源耗盡後無法充電: 任務已停止 （預期 任務已停止） 相符
初始能源為零: 任務已停止 （預期 任務已停止） 相符
耗盡後狀態維持: 0/0/0/0 （預期 0/0/0/0） 相符
初始已達標: 任務已停止 （預期 任務已停止） 相符
達標後狀態維持: 20/5/0/0 （預期 20/5/0/0） 相符
```

D4（TODO 3）：完整main見teacher/CompareMissions.java。兩個參考都是Rover，各指向新建的不同車；兩個新Mission各run同一個script一次，再各show。共用腳本只讀不修改，沒有共享車或任務。show只負責顯示，不代寫學生建立任務的核心學習。

D5兩段完整預期輸出：

```text
=== 一般探測車 ===
=== 任務回放 ===
步驟 1: 指令=1, 結果=成功, 能源=14
步驟 2: 指令=1, 結果=成功, 能源=8
步驟 3: 指令=2, 結果=成功, 能源=6
指令次數[0..3]=[0, 2, 1, 0]
步數=3, 成功=3, 拒絕=0, 略過=0
最低能源=6
最終能源=6, 樣本數=5, 行動次數=3 -> 任務成功
電量百分比=30.0
=== 重型探測車 ===
=== 任務回放 ===
步驟 1: 指令=1, 結果=成功, 能源=14
步驟 2: 指令=1, 結果=成功, 能源=8
指令次數[0..3]=[0, 2, 0, 0]
步數=2, 成功=2, 拒絕=0, 略過=1
最低能源=8
最終能源=8, 樣本數=6, 行動次數=2 -> 任務成功
電量百分比=40.0
```

Heavy第二次探索已得6份，Mission下一輪先查停止，不讀掃描，所以counts及最低能源不同。比較的是各自一次完整任務，不應為了同樣讀三筆而取消停止規則。

6/2跑{1,3}一般車0/4/1 能源耗盡，重型車0/5/1 任務成功，均只有1步；成功優先。5/0跑{1}兩車都記一筆能源不足，狀態5/0/0，outcome 任務未完成，counts[1]=1、ok0、rejected1。

本人反例不限定答案。可用HeavyRover(6,1)探索驗證只計一次、樣本4而耗盡；或HeavyRover(20,5)腳本{1}驗證不讀不記、狀態不變。要求學生說明可抓錯誤，不只重抄教師案例。

## D6答案（TODO 4：任務下鑽探指令）

教師答案（teacher/HeavyRover.java）：

```java
@Override
public String execute(int command) {
    if (command == 4) { return drill(); }
    return super.execute(command);
}
```

宣告須與父類別相同並保留public；改成protected會得到attempting to assign weaker access privileges。指令4交給drill，drill自行檢查停止，所以已停止的車回「任務已停止」且不鑽探。其他指令交回super.execute，探索、掃描、充電、返回與無效指令的規則仍只寫在Rover。不要讓學生把父類別switch整份抄進子類別；那樣能過DrillMissionTry，但規則變成兩份，F2第5題就是在問這個成本。

D6-1：兩台新車各跑{4,1}。

| 項目 | 一般車Rover(20,0) | 重型車HeavyRover(20,0) |
|---|---|---|
| 第1步結果 | 無效指令（能源20不變） | 成功（鑽探，12/3/1，鑽探1次） |
| 最終energy/samples/actionCount | 14/2/1 | 6/6/2 |
| outcome及已讀步數 | 任務未完成，2步 | 任務成功，2步 |

重型車的counts為[0, 1, 0, 0]：指令4不另加計數格，但這一步記在日誌，成功筆數為2。一般車成功1、拒絕1。

DrillMissionTry十四行完整輸出：

```text
重型車：指令4的結果: 成功 （預期 成功） 相符
重型車：任務後狀態: 6/6/2/1 （預期 6/6/2/1） 相符
重型車：任務結果與已讀步數: 任務成功/2 （預期 任務成功/2） 相符
重型車：指令次數[0..3]: [0, 1, 0, 0] （預期 [0, 1, 0, 0]） 相符
一般車：指令4的結果: 無效指令 （預期 無效指令） 相符
一般車：任務後狀態: 14/2/1 （預期 14/2/1） 相符
一般車：成功／拒絕筆數: 1/1 （預期 1/1） 相符
能源不足時下指令4: 能源不足 （預期 能源不足） 相符
能源不足時狀態維持: 7/0/0/0 （預期 7/0/0/0） 相符
已達標後下指令4: 任務已停止 （預期 任務已停止） 相符
已達標後狀態維持: 20/5/0/0 （預期 20/5/0/0） 相符
重型車掃描仍用原規則: 成功 （預期 成功） 相符
掃描後狀態: 18/1/1/0 （預期 18/1/1/0） 相符
重型車的無效指令: 無效指令 （預期 無效指令） 相符
```

未完成TODO 4時，「重型車：指令4的結果」「重型車：任務後狀態」「重型車：任務結果與已讀步數」「能源不足時下指令4」四行不符。只完成TODO 1–2時重型車狀態為14/3/1/0，仍是這四行。

D6-2：Mission的欄位宣告為Rover，只能呼叫Rover宣告過的execute，編譯因此通過；執行時依實際物件HeavyRover選到子類別覆寫的execute，所以收到4會鑽探。與D1的exploreYield是同一個機制，只是這次覆寫的是公開入口。

D6-3：在Mission判斷車種，每加一種有特殊指令的車都要回頭改Mission，Mission也要認得每個子類別，還需要本週未教的轉型才能呼叫drill。覆寫execute時，新車只在自己的類別加入指令，Mission與Rover都不必改。判斷車種與向下轉型列第十週以後的閱讀內容，本週不要求寫。

## E答案與最小修正

E1：模擬AI能編譯，drill後是12/3/1/1；execute(1)仍沿用Rover.explore與繼承的產量2，所以為6/5/2/1。沒有真正覆寫產量方法。

E2兩行不符：

```text
探索後: 6/5/2/1 （預期 6/6/2/1） 不符
完成後狀態維持: 6/5/2/1 （預期 6/6/2/1） 不符
```

其他十三行為成功。樣本5已達標，所以最後drill仍任務已停止；只測「是否成功」抓不到產量錯誤，要看精確samples。這是本次反例的重點。

E3：子類別private explore加@Override會編譯失敗，診斷does not override or implement a method from a supertype。Rover的private explore不是可覆寫入口。把產量改static不符合實例方法覆寫設計，不能用static隱藏解決。限制本週static判讀為文字，不延伸完整隱藏／欄位規則。

E4：移除AI private explore整段，加入D3產量方法；保留正確drill及父execute。模擬原稿沒有D6的execute覆寫，ai-check只用HeavyRoverTry驗收探索產量，不要求補上。相同HeavyRoverTry十五行全部顯示「相符」；再看正常兩車任務仍符合D5。不將teacher整份答案蓋進去冒充本人最小修正。

E5示範Prompt（教師示範，非真實AI對話）：

> 請繼承Rover，覆寫protected int exploreYield()，無參數、回int，回傳3並加@Override。此方法只回傳數量，不扣能源、不改樣本、不加行動次數，也不重複宣告狀態。保留private explore、execute與drill。請先預測20/0跑{1,1,2}與6/2跑{1,3}的最終狀態及已讀步數，再提供最小修改。

採納與拒絕理由須對應實測，AI宣稱正確不算證據。

## F與G答案／檢查

F1：補Rover的# exploreYield(): int與HeavyRover相同方法，以及HeavyRover的+ execute(command : int) : String，註記兩個覆寫；保留Rover的- explore(): String。繼承三角形指Rover，Mission關聯的欄位型態仍Rover。參考圖diagrams/dispatch_reference.puml／SVG只列指定成員，明示省略。不要把樣本多型用兩份狀態解釋。

F2：1不會，物件仍HeavyRover；2可以，bob/memberView即例；3父流程呼叫可覆寫產量方法；4父流程已計次，子方法再計會重複；5父類別switch變成兩份，改掃描或停止規則時要改兩處，漏改一處兩種車就不一致；用super.execute交回才只有一份規則。

F3：只記自選題規格；不提前把課後子類別當全班先備。G依本人實際成果核對，不能拿作者驗證替代學生clone／GUI／遠端操作。網路失敗保留備份及錯誤，不虛填push成功。

## 課後遷移參考與評量

SurveyRover完整教師參考在teacher/transfer，與主線教師檔分開編譯。SurveyRoverTry四行輸出：

```text
6/3/3/任務未完成
0/5/1/任務成功
能源不足/5/0/0
任務已停止/20/5/0
```

第一條同腳本一般車為任務成功；少量採樣車無法在三步內完成，是規格效果，不因名稱較專業就視為錯誤。學生仍須加本人反例，參考答案不能代替學生自由題驗收。自訂題依明訂產量1..4、成本6與同一Mission契約驗收；圖可只在原圖加一個子類別，不畫第二套全圖。

## 常見錯誤與診斷

| 現象 | 原因與處理 |
|---|---|
| Heavy仍得2份 | 父explore仍寫死2，或子方法未真正覆寫；逐段追蹤呼叫 |
| @Override報錯 | 比較名稱、參數、可見性與父方法是否private，不直接刪註記 |
| 行動次數多1 | 產量方法額外改狀態；只保留回傳值 |
| 父類別參考不能drill | Rover沒有此宣告；改用D6的execute(4)，經覆寫進入drill，不要求轉型 |
| 指令4仍回無效指令 | HeavyRover沒有真正覆寫execute（名稱、參數或public不符），或物件是一般車；先看@Override診斷 |
| 掃描等指令在重型車失效 | 覆寫execute後沒有用super.execute交回其他指令 |
| Heavy仍讀最後掃描 | Mission停止檢查被改、共用任務或輸出硬寫；恢復原契約 |
| AI只看任務成功就通過 | 5與6都達標，要檢查精確樣本數 |
| 先改Try的expect消除不符 | 驗收規格遭改動；恢復預先算好的期望，修正實作 |
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
