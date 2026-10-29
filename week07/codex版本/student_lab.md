# 第七週學生實作｜繼承與新增能力

初稿由Codex生成。另存當日日期目錄LAB_REPORT.md，保留A–G題號。先寫預測再執行，沒做就標待補。圖與設計集中java-07/DESIGN.md，避免重抄。

## A｜取回與診斷（10分鐘）

依[README](README.md)建java-07，複製examples三檔、starter六檔到src，記錄實際java／javac版本。複製DESIGN、AI紀錄範本，另複製heavy_practice.puml與SVG到diagrams。不複製教師答案。

A1. 說出Rover、Mission、MissionStep、MissionLog各負責什麼：待填。
A2. 目前energy和某一步的energy為何可能不同：待填。
A3. 20格、0份跑{1,1,2}，最後energy/samples/actionCount/outcome：待填。
A4. 執行MemberDemo、MissionLog、HeavyRoverTry，記哪個已完整、哪個尚未完成與證據：待填。

上週留下「重型探測車探索得3份」的問題。本週先新增鑽探；探索行為改變留到第八週覆寫，不把兩種設計混在一起。

## B｜會員繼承與呼叫追蹤（25分鐘）

開examples/Member.java、VipMember.java、MemberDemo.java。extends寫成`class VipMember extends Member`；C#的對照是`class VipMember : Member`。繼承的理由要有is-a，不能只因為兩個類別有重複程式。

B1. 跑程式前先預測，執行後填實際：

| 項目 | 預測 | 實際 |
|---|---|---|
| 建立Bob時的兩行及先後 | 待填 | 待填 |
| Amy點數 | 待填 | 待填 |
| Bob點數／加贈次數 | 待填 | 待填 |

B2. 每行單獨判斷，先寫理由，再用副本驗證：

| 呼叫（amy是Member，bob是VipMember） | 能否編譯／理由 | 實際 |
|---|---|---|
| bob.buy(50); | 待填 | 待填 |
| bob.claimBonus(); | 待填 | 待填 |
| amy.claimBonus(); | 待填 | 待填 |
| bob.points = 99; | 待填 | 待填 |

B3. 只在新建的Bob上buy(85)，連續claimBonus兩次，先算點數／次數，再執行。能否用「每天限一次」判定程式錯誤？規格哪裡有或沒有這個要求：待填。

B4. VipMember沒有buy的程式卻能呼叫，為何不需要再抄一份？請指出它實際讀寫哪個類別宣告的points：待填。

## C｜建構與存取實驗（15分鐘）

在java-07另建experiments，複製三個會員檔，進入該目錄分開編譯。不要把錯誤副本放回src。

```powershell
javac -encoding UTF-8 -d out *.java
java -cp out MemberDemo
```

C1. 註解VipMember的`super(name);`，只編譯不執行。記錯誤第一行與required／found，說明隱含super()為何找不到可用建構子；記完還原：待填。

C2. 把claimBonus中的`addPoints(20);`換成`points += 20;`，記編譯結果與理由，記完還原：待填。

C3. 在MemberDemo裡、兩次buy之前加`bob.addPoints(5);`，先預測能否編譯，再實測。Bob最後點數多少？同套件protected可被誰呼叫？測完還原：待填。

| 修飾字 | UML | 本例的重點 |
|---|---|---|
| private | - | 本例中只有宣告類別的程式能直接存取 |
| 不寫 | ~ | 同套件可存取 |
| protected | # | 同套件可存取，跨套件子類別另有受限存取規則 |
| public | + | 本課程公開給其他類別使用的入口 |

本課程類別都在同一預設套件，不把protected教成只有子類別能用。跨套件限制暫不列學生操作。父類別private成員不被繼承，但子類別物件仍有父類別那部分狀態；建構子也不被繼承。

本週規定super(...)寫在建構子第一個敘述，先初始化父類別，再設定子類別欄位。這是課堂統一寫法；Java25已放寬部分前置敘述限制，不做跨版本編譯錯誤題。沒寫extends的Member直接繼承Object，知道即可。

## D｜關係、重構與子類別（50分鐘）

### D1 關係與圖（10分鐘）

| 類別 | is-a或has-a／理由 |
|---|---|
| VipMember與Member | 待填 |
| HeavyRover與Rover | 待填 |
| Mission與Rover | 待填 |
| Mission與MissionStep | 待填 |

`Mission extends Rover`會讓哪個不合理的呼叫變成合法？待填。

開heavy_practice.puml，先補`Rover <|-- HeavyRover`，依README產SVG並立即開圖。三角形指向父類別。圖上已註明省略既有成員；不必抄滿Rover所有方法。參考[關係示意](diagrams/relationships.svg)。

### D2 TODO 1：父類別的局部重構（12分鐘）

在src/Rover完成spendEnergy：amount<=0或超過目前能源，回false且不改狀態；否則扣除並回true。addSamples、countAction已提供；它們不會自動完成整個行動或停止檢查。

先跟教師把explore改成下列寫法，再自行以相同方式改scan，成本2、產量1。charge不改。

```java
private String explore() {
    if (!spendEnergy(6)) { return "NOT_ENOUGH_ENERGY"; }
    addSamples(2);
    countAction();
    return "OK";
}
```

D2-1. 扣能源的方法為何回boolean，為何要先拒絕再扣？待填。
D2-2. scan重構前後，輸出不變能證明沒有漏改嗎？還需檢查什麼程式結構？待填。

### D3 TODO 2：自己的狀態（6分鐘）

在HeavyRover宣告private int drillCount、建構子初始化為0、getter回傳欄位。super已提供；說明傳入的兩個值由誰保存，並在課後獨立遷移親自寫出建構子。

D3-1. 為何不能另外宣告energy、samples來解決private存取問題？待填。

### D4 TODO 3：獨立完成drill（12分鐘）

先讀規格再寫，教師此時不展示完整方法：

| 條件 | 回傳 | 狀態 |
|---|---|---|
| isStopped()為true | STOPPED | 全部不變 |
| 未停止但能源不足8 | NOT_ENOUGH_ENERGY | 全部不變 |
| 未停止且能源足夠 | OK | energy-8、samples+3、actionCount+1、drillCount+1 |

使用提供的DRILL_COST、DRILL_YIELD。先檢查停止，再使用spendEnergy；只有成功才更新另外三項。drill是新增公開入口，不經過execute；不能以execute已有停止檢查為由省略。

D4-1. 全新20/0的HeavyRover依序執行，先追蹤再跑：

| 呼叫 | 回傳預測／實際 | energy | samples | actionCount | drillCount |
|---|---|---|---|---|---|
| drill() | 待填 | 待填 | 待填 | 待填 | 待填 |
| execute(1) | 待填 | 待填 | 待填 | 待填 | 待填 |
| drill() | 待填 | 待填 | 待填 | 待填 | 待填 |

D4-2. 自己選一個能抓到「只回OK卻沒更新」的案例，寫預期、實際與理由：待填。

### D5 行為與圖的檢查（10分鐘）

編譯src/*.java，先跑HeavyRoverTry十五行，貼完整輸出。再跑主任務{1,1,2}、{3,9,0}的完整輸出；在MissionLog修改固定script、重編譯，做完恢復{1,1,2}。不使用命令列傳腳本。其餘四路線課後補。

在heavy_practice.puml補三個#方法、HeavyRover指定成員，保留Mission對Rover的關聯，再產SVG、開圖，DESIGN引用自己的SVG。圖可省略既有成員，但要保留省略說明；不畫Mission對HeavyRover的線，因為本週Mission原始碼沒有使用該類別。

## E｜AI程式閱讀、反例與修正（25分鐘）

以下是教師設計的模擬回覆，不是真實AI對話。模擬Prompt：「請繼承Rover新增drill，耗8格得3份，並記鑽探次數。」模擬AI聲稱：「父類別欄位是private，所以子類別重新宣告一份，再更新它們即可。」錯誤程式完整原文在[ai/HeavyRover.java](ai/HeavyRover.java)。

先把完成的Rover、HeavyRoverTry及這份AI HeavyRover放入java-07/ai-check。三檔單獨編譯，不混正式src。

E1（5分）. 看原文，先預測能否編譯，以及drill後getter會讀到什麼。先commit自己的D段完成碼與預測，再看實際結果：待填。

E2（7分）. 在ai-check執行上述編譯命令及HeavyRoverTry，貼全部DIFF行。至少保留「after drill」「done drill」「zero cannot charge」的證據。實際和預測差在哪：待填。

E3（5分）. 畫一個HeavyRover物件：Rover宣告的energy／samples，與AI子類別另宣告的energy／samples各是多少？getEnergy讀哪一份？不要畫成兩台車：待填。

E4（5分）. 用[AI] commit保留模擬原文，再刪掉重複欄位與設定、改用protected方法、補停止檢查與行動次數，重測十五行全部OK，另用[fix] commit保存。記最小修正及重測：待填。修正前後不能一起混編。

E5（3分）. 用三到五句補強Prompt：附上父類別公開／protected方法、狀態只有一份、拒絕不變、停止優先、不可覆寫與要求反例。填AI_USAGE；沒有使用真實AI就明寫來源為教師模擬：待填。

若進度落後，保留E4重測，E5文字課後補；不再加第二份AI程式追蹤。

## F｜修圖與課末檢核（10分鐘）

F1. 請用自己的話回答：

1. private欄位不被繼承，為何getter仍讀得到父類別那份狀態？待填。
2. protected方法是否自動保證drill遵守停止與計次？待填。
3. 子類別建構子是否繼承父類別建構子？待填。
4. 同套件其他類別能否呼叫spendEnergy，課程為何限制其使用位置？待填。
5. HeavyRover.execute(1)目前得到幾份？下週要改成3份，這週新增drill是否已經達成？待填。

F2. 檢查自己的圖：三角形指父類別、#與protected一致、子類別只列新增成員、Mission仍關聯Rover。記產圖方式、實際看見的修改與修正結果：待填。

F3. 課後獨立遷移選題：預備採CargoRover或自訂何種子類別、增加什麼狀態與規則：待填。課堂先記設計，不要求此時完成第二套程式。

## G｜保存與取回（15分鐘）

- [ ] 重編譯，執行MemberDemo、MissionLog、HeavyRoverTry；固定script恢復預設。
- [ ] DESIGN引用本人SVG，puml與SVG同步；LAB_REPORT、AI_USAGE記本人結果，缺項標待補。
- [ ] GitHub Desktop檢查差異，commit；[AI]原文與[fix]修正能比較，不提交out、class、IDE暫存、jar。
- [ ] Push後在GitHub網頁核對本次程式與SVG；記實際完成或失敗原因。
- [ ] 在另一個新位置clone，確認java-07/src與diagrams，編譯並跑HeavyRoverTry。不要刪除原副本，也不要在日期或專案內再建repo。

記錄commit、遠端核對、新clone路徑與實際結果：待填。網路失敗先另存完整repo備份，確認能打開，再求助；備份不能寫成push成功。
