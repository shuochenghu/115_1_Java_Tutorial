---
marp: true
theme: default
size: 16:9
paginate: true
lang: zh-TW
title: 第五週｜把任務日誌設計成物件
author: 胡碩誠
description: 115 學年度第 1 學期｜物件導向程式設計
footer: '物件導向程式設計｜Week 05'
style: |
  section {
    font-family: "Microsoft JhengHei", "Noto Sans TC", sans-serif;
    font-size: 28px;
    line-height: 1.45;
    color: #203047;
    background: #fbfcfe;
    padding: 48px 64px;
  }
  h1 { font-size: 44px; color: #123d65; }
  h2 { font-size: 38px; color: #123d65; margin-bottom: 22px; }
  strong { color: #006d77; }
  a { color: #126496; }
  pre { font-size: 23px; line-height: 1.35; background: #eef2f6; }
  code { font-family: Consolas, "Microsoft JhengHei", monospace; }
  table { font-size: 25px; width: 100%; }
  th { background: #e7eff5; }
  footer { font-size: 16px; color: #62748a; }
  section::after { font-size: 16px; color: #62748a; }
  blockquote { border-left: 5px solid #00858c; color: #344d63; }
  section.lead { background: #123047; color: #f5f8fc; }
  section.lead h1, section.lead h2 { color: #ffffff; }
  section.lead strong { color: #81ded8; }
  section.small { font-size: 24px; }
  section.small table { font-size: 22px; }
---
<!-- _class: lead -->
## 第五週｜把任務日誌設計成物件

類別、建構子、封裝與設計責任

AI 協作：從需求寫出可驗證的類別設計

資訊管理學系二年級｜胡碩誠

<!-- 教師提示：承接第四週，不重新安排從零開始的語法課。 -->

---
## 三條清單，一次漏加就配錯

| 索引 | commandLog | energyLog | resultLog |
|---|---:|---:|---|
| 0 | 1 | 14 | OK |
| 1 | 1 | 8 | OK |
| 2 | 2 | 6 | OK |

如果第二筆只新增指令與結果，回放會怎麼配？

**需要把「同一步」的資料一起保存。**

<!-- 教師提示：A。長度相同仍可能對錯資料，排序單一清單也有相同風險。 -->

---
## 本週的改造範圍

- 同一步的三欄整合成 MissionStep。
- 三條清單改成 ArrayList<MissionStep>。
- 原來行動規則、停止時機及報表保持一致。
- 比較修改前後的完整輸出，檢查改造是否正確。

目前狀態仍用 energy、samples、actionCount。

<!-- 教師提示：這次只集中改資料表示。完整Rover設計可到下週再討論。 -->

---
## 150 分鐘安排

| 第1節 | 第2節 | 第3節 |
|---|---|---|
| A 取回與診斷10 | D 參考、快照、IDEA20 | F 新需求與反例30 |
| B 類別與建構25 | E 實作與整合30 | G 檢核5 |
| C 封裝與C#對照15 | | H 保存15 |

必做先掌握核心兩路線，其餘測試課後補齊。

<!-- 教師提示：休息另計。IDE若卡住由教師帶看，保留實作時間；時間配置不是試教成果。 -->

---
## 範例檔案與執行入口

| 活動 | main 入口 | 合作的類別 |
|---|---|---|
| B | CreateStepDemo | StepCard |
| C | BatteryDemo | RoverBattery |
| D1 | ReferenceDemo | RoverBattery |
| D2–D3 | SnapshotDemo | StepCard、RoverBattery |
| E–F | MissionLog | MissionStep |

基本兩檔先用 VS Code，三檔合作再加入 IDEA。

<!-- 教師提示：examples共六個檔案、四個main入口。每次選定main，不執行純資料類別。 -->

---
## 你已經使用過物件

```java
ArrayList<Integer> energyLog = new ArrayList<>();
energyLog.add(14);
```

WinForms 的 `new Button()` 也是建立物件的經驗。

本週開始自己定義資料、初始化方式與可用操作。

<!-- 教師提示：使用現成類別不代表已掌握自行設計。先肯定已有經驗，再界定本週新增能力。 -->

---
## 類別、物件與參考變數

```java
StepCard first = new StepCard(1, 14);
```

| 片段 | 角色 |
|---|---|
| StepCard | 變數的型態，定義資料與操作 |
| new StepCard(1,14) | 建立物件並呼叫建構子 |
| first | 保存參考值，指向建立的物件 |

<!-- 教師提示：B1。先追蹤右側建立，再看指定。只宣告StepCard first不會建立StepCard物件。 -->

---
## 欄位讓物件記住資料

來源：examples/StepCard.java

```java
public class StepCard {
    private int command;
    private int energy;
    // 建構子與方法在後面
}
```

欄位跟著物件存在，每個實例各自保存。

<!-- 教師提示：比較方法內區域變數。private在這裡限制別的類別直接存取，數值範圍還需規則。 -->

---
## 建構子一次收齊初始資料

來源：examples/StepCard.java

```java
public StepCard(int command, int energy) {
    this.command = command;
    this.energy = energy;
}
```

名稱與類別相同，**沒有回傳型態**。

建立後就能查詢這筆記錄，不必先零散填欄位。

<!-- 教師提示：B1。刻意加void會變普通方法。這裡先處理合法引數，完整違約防護留後續。 -->

---
## this 區分欄位與同名參數

```java
this.energy = energy;
```

| 左側 | 右側 |
|---|---|
| 目前物件的 energy 欄位 | 此次呼叫的 energy 參數 |

若只寫 `energy = energy`，欄位會留下什麼值？

<!-- 教師提示：預設int欄位為0。不要把this講成全域變數或目前類別本身；它指目前實例。 -->

---
## getter 讓呼叫端讀取資料

```java
public int getEnergy() {
    return energy;
}
```

```java
System.out.println(first.getEnergy());
```

回傳值由呼叫端使用，資料類別不負責印報表。

<!-- 教師提示：getter是一般方法的命名慣例。C#屬性語法不直接移植為Java；沿用方法參數/回傳的理解。 -->

---
## 兩次 new，兩份實例資料

來源：examples/CreateStepDemo.java

```java
StepCard first = new StepCard(1, 14);
StepCard second = new StepCard(2, 12);
```

| 參考變數 | 指向物件的 command／energy |
|---|---|
| first | 1／14 |
| second | 2／12 |

<!-- 教師提示：B2，先預測再執行。第二次建立後再讀第一次，確認資料獨立。 -->

---
## B 活動：先看懂，再驗證

1. 在 VS Code 執行 CreateStepDemo，填 B1–B2。
2. 副本中擇一取消註解：直接存取 energy 或無參數建構。
3. 編譯，記錄訊息並解釋原因，再恢復正確程式。

```text
first=1/14
second=2/12
same object=false
```

<!-- 教師提示：B3預期編譯失敗，不把故意錯誤混入正式版本。 -->

---
## 無參數建構子何時會自動提供

- 完全沒宣告建構子：編譯器提供預設無參數版。
- 已宣告有參數建構子：需要什麼版本就明確宣告。
- 本週只設計一個有參數建構子。

```java
new StepCard(); // 這份 StepCard 沒有此建構子
```

<!-- 教師提示：不在本週展開過載、繼承或super。只回答目前編譯錯誤的原因。 -->

---
## 封裝讓行為集中維護規則

來源：examples/RoverBattery.java

```java
public boolean explore() {
    if (energy < 6) {
        return false;
    }
    energy -= 6;
    return true;
}
```

不足時拒絕，成功時扣電。呼叫端看回傳值。

<!-- 教師提示：C。private加上受控方法才讓規則集中；不能說private會自動驗證範圍。 -->

---
## C 活動：預測拒絕與封頂

來源：examples/BatteryDemo.java

| 起點／操作 | 回傳值 | 能源 |
|---|---|---:|
| 5，探索 | false | 5 |
| 接著充電 | true | 10 |
| 接著探索 | true | 4 |
| 另一物件19，充電 | true | 20 |
| 再充電 | false | 20 |

如果任意開放 setEnergy，規則要由誰維護？

<!-- 教師提示：先遮住答案讓學生預測。建構前提0..20；電池類別沒有任務終止概念。 -->

---
## 目前狀態與歷史資料有不同需求

| 類別 | 資料的角色 | 對外操作 |
|---|---|---|
| RoverBattery | 現在有多少能源 | 探索、充電、讀取 |
| StepCard／MissionStep | 某一步發生了什麼 | 建立後讀取 |

歷史沒有改寫需求，本週不提供 setter。

**可修改的清單，也能保存對外只讀的記錄。**

<!-- 教師提示：沒有setter是此需求的選擇，不是所有類別都禁setter。私有欄位仍需正確建構契約。 -->

---
## C1：C# 與 Java 的欄位存取

| 類別裡的欄位 | C# | Java |
|---|---|---|
| 明寫 private | 類別內使用 | 類別內使用 |
| 省略修飾字 | 預設 private | 同一套件可存取 |
| 外部讀、內部改 | 屬性加 private set | private 欄位、getter與行為 |

同套件的另一類別，能寫 `first.energy = 99` 嗎？

<!-- 教師提示：講義C1。比較的是類別欄位，避免與最外層類別混淆。教師副本省略private後可編譯並改99；隨即恢復。 -->

---
## static 與實例成員

| 成員 | 本週用途 |
|---|---|
| static main | 啟動流程 |
| static 工具方法 | 由參數處理回放與摘要 |
| 實例欄位 | 各筆記錄保存自己的資料 |
| 實例方法 | 查詢該筆記錄，或改變該電池 |

若 MissionStep 欄位都加 static，還能各自保存嗎？

<!-- 教師提示：不教static計數器。F3反例再揭露共享覆寫，避免只背語法。 -->

---
## D1：兩個參考指向同一物件

來源：examples/ReferenceDemo.java

```java
RoverBattery first = new RoverBattery(20);
RoverBattery alias = first;
RoverBattery other = new RoverBattery(20);
alias.explore();
```

first 與 alias 共同看見14，other 仍為20。

`==` 可檢查此處的參考是否相同。

<!-- 教師提示：請學生畫first、alias連到同一物件，other另連一個。不要用數值相同推論同物件。 -->

---
## 方法取得參考值的副本

```java
static void spend(RoverBattery battery) {
    battery.explore();
}
```

```java
static void replaceLocal(RoverBattery battery) {
    battery = new RoverBattery(1);
}
```

修改同一物件看得見，改指向只影響區域參數。

<!-- 教師提示：Java傳值。ReferenceDemo完整輸出內部替換1、回到main仍8。 -->

---
## D2：三個檔案合作，加入 IDEA

- SnapshotDemo：啟動、順序與顯示。
- RoverBattery：目前能源與合法動作。
- StepCard：保存某一步的指令與能源。

使用同一 java-05/src，不另外建立一份來源。

單一程式用 VS Code，超過兩檔再加入 IDEA。

<!-- 教師提示：本次使用者指定的工具教學順序；兩檔也可在VS Code完成，不要求所有活動兩IDE重做。 -->

---
## IDEA：先確認專案，再執行 main

1. 開 java-05，一般 Java／IntelliJ build system。
2. Project Structure 確認已安裝 JDK 與 src Sources Root。
3. 從 SnapshotDemo.main 執行，核對輸出。
4. 找不到 Run 時先查 JDK、來源根與編譯錯誤。

不另建巢狀 Git，不增加 Maven／Gradle。

<!-- 教師提示：D2詳細步驟見講義。維持傳統class/main，不跟新版文件改成compact source；GUI未實做就填待補。 -->

---
## D3：每一步建立新的快照

來源：examples/SnapshotDemo.java

```java
battery.explore();
history.add(new StepCard(1, battery.getEnergy()));
battery.charge();
history.add(new StepCard(3, battery.getEnergy()));
```

第一筆保存14，第二筆保存19；現在的電池是19。

**建構當時的 int 值已複製到各筆欄位。**

<!-- 教師提示：兩次new是關鍵。清單的add只保存參考，不是自動複製物件。 -->

---
## 把需求變成 MissionStep

| 需求語句 | 類別設計 |
|---|---|
| 同一步的三欄一起保存 | 三個private實例欄位 |
| 建立就有完整資料 | 三參數建構子 |
| 歷史建立後只讀 | 三個getter，不提供setter |
| 判斷這一步是否成功 | boolean isSuccessful() |
| 控制中心顯示報表 | MissionLog負責輸出 |

<!-- 教師提示：由需求推設計而非先把名詞都變類別。完整防護不在本週必做。 -->

---
## E1：完成自己的資料類別

來源：starter/MissionStep.java

| TODO | 實作內容 | 檢查方式 |
|---|---|---|
| 1 | 三欄建構指定 | 建立後讀值 |
| 2 | 三個getter | 不回傳寫死常數 |
| 3 | isSuccessful內容比較 | new String("OK")反例 |

先完成小類別，再接回任務，避免一次改太多。

<!-- 教師提示：E1使用講義暫時檢查，兩個物件都建好再讀第一個。starter可編譯不代表完成。 -->

---
## 兩個物件要一起檢查

```java
MissionStep first =
    new MissionStep(1, 14, new String("OK"));
MissionStep second =
    new MissionStep(2, 12, "NOT_ENOUGH_ENERGY");
```

| 物件 | 預期讀值及 isSuccessful |
|---|---|
| first | 1／14／OK／true |
| second | 2／12／NOT_ENOUGH_ENERGY／false |

<!-- 教師提示：若first變成second的值，查static；OK卻false，查==。new String只用於內容比較反例。 -->

---
## E2：從三條清單改成一條

來源：starter/MissionLog.java

```java
ArrayList<MissionStep> history = new ArrayList<>();
```

```java
// switch 完成，取得本步的實際結果後
history.add(new MissionStep(command, energy, result));
```

TODO 4：每筆已讀指令都記，包含拒絕、無效及返回。

<!-- 教師提示：若放在switch前，能源早一步；若只在OK分支add，摘要與counts會改變。 -->

---
## TODO 5：先取物件，再讀欄位

```java
MissionStep step = history.get(i);
int command = step.getCommand();
int energy = step.getEnergy();
String result = step.getResult();
```

三個值都來自同一筆 step。

回放格式維持第四週，便於直接比對。

<!-- 教師提示：完整回放for與println由學生組合，不在投影片塞全部程式。長迴圈在講義及教師答案看。 -->

---
## TODO 6：由物件查詢做摘要

```java
for (MissionStep step : history) {
    if (step.isSuccessful()) {
        ok++;
    }
    if ("RETURN".equals(step.getResult())) {
        returned++;
    }
}
```

rejected = steps − ok − returned

<!-- 教師提示：RETURN不是成功也不是拒絕。ignored仍為script.length-steps。 -->

---
## 求最低能源：讀值後比較

```java
int lowest = history.get(0).getEnergy();
for (MissionStep step : history) {
    if (step.getEnergy() < lowest) {
        lowest = step.getEnergy();
    }
}
```

先判斷清單非空，空日誌印 N/A。

<!-- 教師提示：這段支援碼已提供。不能直接用min(history)，MissionStep尚未定義比較順序。 -->

---
## E3：先驗證兩條核心路線

| 路線 | 預期終態 | 日誌重點 |
|---|---|---|
| {1,1,2} | 6/5/3 SUCCESS | 能源14、8、6 |
| {3,9,0} | 20/0/0 RETURNED | 拒絕、無效、返回都保存 |

看回放、counts、摘要、最低值與battery。

完整六條路線見講義E3與作業，課後補齊本人實測。

<!-- 教師提示：不能只看最終三個數字。檢查物件快照與原行為一致。 -->

---
## 邊界仍要保留

| 初始 energy/samples；script | 預期 |
|---|---|
| 2/4；{2} | 同時耗盡與達標，SUCCESS |
| 0/0；{3,1} | 已停止，零筆日誌 |
| 20/5；{1} | 已達標，零筆日誌 |
| 20/0；{} | UNFINISHED、最低值N/A |

教師 FixedScriptMission 示範，學生記觀察。

<!-- 教師提示：工具是教師支援，不要求學生另寫命令列介面。不要把電池charge規則套成任務可復活。 -->

---
## F1：換一個新需求，設計樣本運送盒

- 建造時決定容量，修正到1～8，初始空盒。
- 正數且放得下才整批存入，回傳是否成功。
- 查存量與剩餘空間，無任意改寫存量的操作。
- 一次卸空，回傳卸下份數，空盒回傳0。

先寫設計卡，完整Java實作列課後選做。

<!-- 教師提示：F1共8分鐘，先讓學生從未做過的需求推設計，不先投影教師SampleCase完整碼。 -->

---
## F1：每個設計決定都要對應需求

| 設計卡 | 要回答的問題 |
|---|---|
| 責任、欄位、可見性 | 哪些資料需保存？哪些可計算？ |
| 建構子 | 初始值與不合法容量怎麼處理？ |
| 方法契約 | 參數、回傳、拒絕時的狀態？ |
| 測試 | 填滿、超量、卸空會看到什麼？ |

以容量3為例，自己寫出存2、再存2、存1、卸空的預期。

<!-- 教師提示：方法名可自訂但需一致。紙上預期不是已執行證據；教師參考另存，不是學生必交新類別。 -->

---
## F2：對照設計卡，審查模擬 AI 程式

```java
public int capacity;
public int count;
public SampleCase(int capacity) {
    this.capacity = capacity;
}
public void store(int amount) {
    count += amount;
}
```

這些片段符合剛才的需求嗎？完整錯誤版見講義F2。

<!-- 教師提示：教師自編模擬建議。至少找四項偏差，各配具體呼叫，不能只回答封裝不好；F2共8分鐘。 -->

---
## F2：用具體呼叫檢查，再改寫 Prompt

- 容量0或9，建構後應符合什麼範圍？
- 容量3先存2，再存2，回傳與存量應如何？
- 存0或負數，呼叫者能知道拒絕嗎？
- 如何查剩餘空間、卸空並取得份數？

Prompt要帶入責任、方法契約與測試，不只要求寫類別。

<!-- 教師提示：可以紙上推論，必須標示未實測。完整實作選做；不強制登入AI服務，不提前看教師答案。 -->

---
## F3：錯誤建議一，資料全加 static

> 模擬建議：「所有地方都要讀資料，加 static 更方便。」

1. 正確版先保存，副本把三欄加 static。
2. 建立兩個物件後再讀第一筆。
3. 第一筆為何也變成第二筆資料？
4. 恢復實例欄位，重測E1與兩條核心路線。

預期反例：第一筆變成 2／12／NOT_ENOUGH_ENERGY。

<!-- 教師提示：F3三選一，10分鐘先完成預測與一輪操作，完整紀錄可課後補。錯誤只放repo外的副本。 -->

---
## F3：錯誤建議二，用 == 比較結果

> 模擬建議：「固定代碼，用 == 比較就好。」

```java
return result == "OK"; // 副本錯誤示範
```

用 `new String("OK")` 建立第一筆，isSuccessful 會如何？

改回內容比較，再重測，寫下採納或拒絕理由。

<!-- 教師提示：常值字串可能讓錯誤偶然通過，所以不能只測主任務的固定常值。 -->

---
## F3：錯誤建議三，保存可變物件的參考

```java
RoverBattery battery = new RoverBattery(20);
ArrayList<RoverBattery> history = new ArrayList<>();
battery.explore();
history.add(battery);
battery.charge();
history.add(battery);
```

兩個索引都指向同一電池，稍後讀取還能保留14嗎？

<!-- 教師提示：用SnapshotDemo副本，只替換main內容；完整片段與列印在講義F3。不要更動正式examples。 -->

---
## F3：比對兩筆歷史，不能只看最後能源

| 保存方式 | 第一筆能源 | 第二筆能源 |
|---|---:|---:|
| 同一電池參考存兩次 | 19 | 19 |
| 每步 new StepCard 保存數值 | 14 | 19 |

兩種方式的目前電池都為19，單看最後能源抓不到錯誤。

修正後重測原快照與兩條主任務路線。

<!-- 教師提示：清單add不複製物件。新反例不需完整Rover，保留原有小類別即可；答案先遮住讓學生預測。 -->

---
## F4：依資料角色，決定允許的操作

- MissionStep是歷史資料，建立後對外只讀。
- SampleCase是目前存量，由合法存入／卸空動作改變。
- 每個類別都要依需求決定可用方法。

仍須檢查：呼叫端傳值是否正確？建立時機對嗎？

**把資料放進類別後，驗證工作仍然必要。**

<!-- 教師提示：F4共4分鐘。類別不自動知道任務真相，也不自動驗證語意一致；完整Rover責任分工留第六週。 -->

---
## G：把責任交接給第六週

| 類別 | 一句話責任 |
|---|---|
| MissionStep | 保存一筆已發生的任務記錄 |
| MissionLog | 推進腳本、保存歷史、產生報表 |
| RoverBattery | 管理能源及受控變更 |

下週用 UML 類別圖描述欄位、方法與使用關係。

<!-- 教師提示：不直接把清單判為UML組合，不提前要求完整Rover。先口述責任，正式建模留第六週。 -->

---
## H：保存可重現的正確版本

1. 恢復預設腳本，移除臨時檢查與錯誤變異。
2. 重新編譯、執行，完成LAB_REPORT與AI_USAGE。
3. GitHub Desktop檢查差異、commit、push、遠端核對。
4. 另取回新副本重編譯，未做項目如實標示。

src 放八個Java檔，teacher同名檔及out不提交。

<!-- 教師提示：預留15分鐘。推送失敗先求助及保存到不還原位置，不讓未完成程式遺失。 -->

---
<!-- _class: small -->
## 教材與來源

- Deitel Java How to Program, Early Objects 11e EPUB：§3.2–3.5；§8.3、8.4、8.6、8.7補充。
- [Oracle 建構子](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)、[this](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)、[存取控制](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)。
- [VS Code Java](https://code.visualstudio.com/docs/java/java-tutorial)、[IntelliJ Java專案](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)。
- 本週README、學生講義、作業、教師筆記及驗證紀錄。

情境與活動為自編；JDK不鎖定，實際測試範圍見verification。

<!-- 教師提示：Oracle頁面為傳統JDK8教學；只引用穩定概念。IDE文件核對不代表GUI已驗證。 -->
