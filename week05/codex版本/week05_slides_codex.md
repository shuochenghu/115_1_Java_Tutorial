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
  section .slide-ref {
    position: absolute;
    top: 12px;
    left: 64px;
    right: 64px;
    margin: 0;
    font-size: 18px;
    line-height: 1.2;
    color: #62748a;
  }
  section .slide-ref code { font-size: inherit; background: none; padding: 0; }
  section.lead .slide-ref { color: #c9dce8; }
---
<!-- _class: lead -->
## 第五週｜把任務日誌設計成物件

類別、建構子、封裝與設計責任

AI 協作：從需求寫出可驗證的類別設計

資訊管理學系二年級｜胡碩誠

<!-- 教師提示：承接第四週，不重新安排從零開始的語法課。 -->

---
<!-- _class: small -->
## 三條清單靠索引配對，漏記能源會怎樣？

<p class="slide-ref" data-slide="2">對應程式：<code>../../week04/codex版本/starter/MissionLog.java</code></p>

第四週把同一步的指令、行動後能源與結果，分開存進三條清單。
初始能源20，路線 `{1,1,2}` 是探索→探索→掃描，能源依序為14、8、6。

**假設第2步漏掉 `energyLog.add(8)`，第3步仍加入6：**

| 索引 | commandLog | resultLog | 正常 energyLog | 漏記後 energyLog |
|---|---:|---|---:|---|
| 0（第1步） | 1 | OK | 14 | 14 |
| 1（第2步） | 1 | OK | 8 | **6（其實是第3步）** |
| 2（第3步） | 2 | OK | 6 | **不存在** |

回放若用相同索引讀取：第2步配到錯誤能源，第3步會讀取越界。
**本週改成一筆 MissionStep 保存三欄，再加入一條 history 清單。**

<!-- 教師提示：A暖身。初始能源20、樣本0。這是刻意假設漏加的錯誤，不是提供的正確答案自帶此錯誤；starter尚有TODO。正常三條清單是[1,1,2]、[14,8,6]、[OK,OK,OK]；漏記後energyLog變[14,6]。相同索引1會錯配6，索引2讀能源會拋出IndexOutOfBoundsException。MissionStep讓同一步三欄一起交付，仍須由呼叫端傳正確值、每步建立並新增，不能宣稱使用物件便不會漏記。 -->

---
## 本週的改造範圍

<p class="slide-ref" data-slide="3">對應程式：<code>starter/MissionStep.java</code>、<code>starter/MissionLog.java</code></p>

- 同一步的三欄整合成 MissionStep。
- 三條清單改成 ArrayList<MissionStep>。
- 原來行動規則、停止時機及報表保持一致。
- 比較修改前後的完整輸出，檢查改造是否正確。

**完成後：每筆記錄資料獨立，同一路線輸出與第四週一致。**

目前狀態仍用 energy、samples、actionCount。

<!-- 教師提示：這次只集中改資料表示。完整Rover設計可到下週再討論。 -->

---
## 150 分鐘安排

| 第1節 | 第2節 | 第3節 |
|---|---|---|
| A 取回與診斷10 | D 參考、快照、IDEA20 | F 新需求與反例30 |
| B 類別與建構25 | E 實作與整合30 | G 檢核5 |
| C 封裝與C#對照15 | | H 保存15 |

課堂先驗證 `{1,1,2}` 成功、`{3,9,0}` 拒絕／無效／返回。
其餘四條路線與完整紀錄，未完成者課後補齊。

<!-- 教師提示：休息另計。IDE若卡住由教師帶看，保留實作時間；時間配置不是試教成果。 -->

---
<!-- _class: small -->
## 範例檔案與執行入口

| 活動 | main 入口 | 合作的類別 |
|---|---|---|
| B | examples/CreateStepDemo.java | examples/StepCard.java |
| C | examples/BatteryDemo.java | examples/RoverBattery.java |
| D1 | examples/ReferenceDemo.java | examples/RoverBattery.java |
| D2–D3 | examples/SnapshotDemo.java | examples/StepCard.java<br>examples/RoverBattery.java |
| E | starter/MissionLog.java | starter/MissionStep.java |

同一列檔案要一起編譯，執行 main 所在的類別。

路徑從本週教材目錄起算；複製到學生 src 後使用同名檔。
基本兩檔先用 VS Code，三檔合作再加入 IDEA。

<!-- 教師提示：examples共六個檔案、四個main入口。每次選定main，不執行純資料類別。 -->

---
## 你已經使用過物件

<p class="slide-ref" data-slide="6">對應程式：<code>../../week04/codex版本/starter/MissionLog.java</code></p>

```java
ArrayList<Integer> energyLog = new ArrayList<>();
energyLog.add(14);
```

WinForms 的 `new Button()` 也是建立物件的經驗。

上週使用現成 ArrayList 的建構子與 add。
本週自己寫 StepCard：決定保存哪些欄位、如何建立、如何讀取。

<!-- 教師提示：使用現成類別不代表已掌握自行設計。先肯定已有經驗，再界定本週新增能力。 -->

---
## 類別、物件與參考變數

<p class="slide-ref" data-slide="7">對應程式：<code>examples/CreateStepDemo.java</code>、<code>examples/StepCard.java</code></p>

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

<p class="slide-ref" data-slide="8">對應程式：<code>examples/StepCard.java</code></p>

```java
public class StepCard {
    private int command;
    private int energy;
    // 建構子與方法在後面
}
```

command 記指令編號；energy 記該步行動後的能源。
每個物件有自己的欄位，建構結束後資料仍保留。

<!-- 教師提示：比較方法內區域變數。private在這裡限制別的類別直接存取，數值範圍還需規則。 -->

---
## 建構子一次收齊初始資料

<p class="slide-ref" data-slide="9">對應程式：<code>examples/StepCard.java</code></p>

```java
public StepCard(int command, int energy) {
    this.command = command;
    this.energy = energy;
}
```

名稱與類別相同，**沒有回傳型態**。

呼叫 `new StepCard(1, 14)` 時，參數先收到1與14，
再存入新物件的 command 與 energy 欄位。

<!-- 教師提示：B1。刻意加void會變普通方法。這裡先處理合法引數，完整違約防護留後續。 -->

---
## this 區分欄位與同名參數

<p class="slide-ref" data-slide="10">對應程式：<code>examples/StepCard.java</code></p>

```java
this.energy = energy;
```

| 左側 | 右側 |
|---|---|
| 目前物件的 energy 欄位 | 此次呼叫的 energy 參數 |

以 `new StepCard(1, 14)` 為例：右側參數為14，指定給左側欄位。
若寫 `energy = energy`，只是參數指定給自己，欄位仍為預設0。

<!-- 教師提示：預設int欄位為0。不要把this講成全域變數或目前類別本身；它指目前實例。 -->

---
## getter 讓呼叫端讀取資料

<p class="slide-ref" data-slide="11">對應程式：<code>examples/StepCard.java</code>、<code>examples/CreateStepDemo.java</code></p>

StepCard 內定義查詢方法：

```java
public int getEnergy() {
    return energy;
}
```

CreateStepDemo.main 中，first 建好後呼叫：

```java
System.out.println("first=" + first.getCommand()
        + "/" + first.getEnergy());
```

getEnergy 回傳14，與指令1組成輸出 first=1/14。
getter 只回傳資料；由 main 決定如何顯示。

<!-- 教師提示：getter是一般方法的命名慣例。C#屬性語法不直接移植為Java；沿用方法參數/回傳的理解。 -->

---
## 兩次 new，兩份實例資料

<p class="slide-ref" data-slide="12">對應程式：<code>examples/CreateStepDemo.java</code></p>

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
## 實作：預測輸出，再診斷編譯錯誤

<p class="slide-ref" data-slide="13">對應程式：<code>examples/CreateStepDemo.java</code></p>

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

<p class="slide-ref" data-slide="14">對應程式：<code>examples/CreateStepDemo.java</code>、<code>examples/StepCard.java</code></p>

- 完全沒宣告建構子：編譯器提供預設無參數版。
- 只要自己宣告了建構子，編譯器就不再自動補預設版。
- StepCard 已宣告兩參數版，因此建立時必須傳指令與能源。

```java
new StepCard(); // 刻意錯誤：沒有無參數版，編譯失敗
```

<!-- 教師提示：不在本週展開過載、繼承或super。只回答目前編譯錯誤的原因。 -->

---
## 封裝讓行為集中維護規則

<p class="slide-ref" data-slide="15">對應程式：<code>examples/RoverBattery.java</code>、<code>examples/BatteryDemo.java</code></p>

```java
public boolean explore() {
    if (energy < 6) {
        return false;
    }
    energy -= 6;
    return true;
}
```

energy 是 private，外部透過方法改變能源。
不足6：回傳 false，能源不變；足夠：扣6並回傳 true。

<!-- 教師提示：C。private加上受控方法才讓規則集中；不能說private會自動驗證範圍。 -->

---
## 封裝驗證：拒絕時不變，充電時封頂

<p class="slide-ref" data-slide="16">對應程式：<code>examples/BatteryDemo.java</code>、<code>examples/RoverBattery.java</code></p>

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

<p class="slide-ref" data-slide="17">對應程式：<code>examples/RoverBattery.java</code>、<code>examples/StepCard.java</code>、<code>starter/MissionStep.java</code></p>

| 類別 | 資料的角色 | 對外操作 |
|---|---|---|
| RoverBattery | 現在有多少能源 | 探索、充電、讀取 |
| StepCard／MissionStep | 某一步發生了什麼 | 建立後讀取 |

例如電池由14充到19，先前記錄的14仍應保留。
所以歷史類別只提供讀取，不提供改值的 setter。

history 可以繼續 add 新記錄；已存的舊記錄不跟著電池改變。

<!-- 教師提示：沒有setter是此需求的選擇，不是所有類別都禁setter。私有欄位仍需正確建構契約。 -->

---
## C# 與 Java 的欄位存取差異

<p class="slide-ref" data-slide="18">對應程式：<code>examples/StepCard.java</code>、<code>examples/CreateStepDemo.java</code></p>

| 類別裡的欄位 | C# | Java |
|---|---|---|
| 明寫 private | 類別內使用 | 類別內使用 |
| 省略修飾字 | 預設 private | 同一套件可存取 |
| 外部讀、內部改 | 屬性加 private set | private 欄位、getter與行為 |

教師在副本示範：保留 private，直接寫 first.energy 會編譯失敗；
刪掉 private 後，同套件可寫入99。觀察後恢復原碼。

<!-- 教師提示：講義C1。比較的是類別欄位，避免與最外層類別混淆。教師副本省略private後可編譯並改99；隨即恢復。 -->

---
## static 與實例成員

<p class="slide-ref" data-slide="19">對應程式：<code>starter/MissionLog.java</code>、<code>starter/MissionStep.java</code></p>

static 成員屬於類別；實例成員屬於某個物件。

| 成員 | 本週用途 |
|---|---|
| static main | 啟動流程 |
| static 工具方法 | 由參數處理回放與摘要 |
| 實例欄位 | 各筆記錄保存自己的資料 |
| 實例方法 | 查詢該筆記錄，或改變該電池 |

實例欄位：第一筆保留14、第二筆保留12。
若 energy 改成 static，兩個物件會共用同一欄位，後寫的值會覆蓋前值。

<!-- 教師提示：不教static計數器。F3反例再揭露共享覆寫，避免只背語法。 -->

---
## 兩個參考指向同一物件

<p class="slide-ref" data-slide="20">對應程式：<code>examples/ReferenceDemo.java</code></p>

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
<!-- _class: small -->
## 方法取得參考值的副本

<p class="slide-ref" data-slide="21">對應程式：<code>examples/ReferenceDemo.java</code></p>

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

接續上一頁，first 的能源為14：
- `spend(first)` 操作同一電池，返回後 first 的能源為8。
- `replaceLocal(first)` 內的新電池為1，返回後 first 仍指向能源8的舊電池。

**方法拿到的是參考值的副本，不是呼叫端變數本身。**

<!-- 教師提示：Java傳值。ReferenceDemo完整輸出內部替換1、回到main仍8。 -->

---
## 三個檔案合作，加入 IDEA

<p class="slide-ref" data-slide="22">對應程式：<code>examples/SnapshotDemo.java</code>、<code>examples/RoverBattery.java</code>、<code>examples/StepCard.java</code></p>

- SnapshotDemo：啟動、順序與顯示。
- RoverBattery：目前能源與合法動作。
- StepCard：保存某一步的指令與能源。

使用同一 java-05/src，不另外建立一份來源。

單一程式用 VS Code，超過兩檔再加入 IDEA。

<!-- 教師提示：本次使用者指定的工具教學順序；兩檔也可在VS Code完成，不要求所有活動兩IDE重做。 -->

---
## IDEA：先確認專案，再執行 main

<p class="slide-ref" data-slide="23">對應程式：<code>examples/SnapshotDemo.java</code></p>

1. 開 java-05，一般 Java／IntelliJ build system。
2. Project Structure 確認已安裝 JDK 與 src Sources Root。
3. 從 SnapshotDemo.main 執行，核對輸出。
4. 找不到 Run 時先查 JDK、來源根與編譯錯誤。

預期兩筆為 command/energy＝1/14、3/19；current＝19，
separate records＝true。兩套 IDE 使用同一份 src。

<!-- 教師提示：D2詳細步驟見講義。維持傳統class/main，不跟新版文件改成compact source；GUI未實做就填待補。 -->

---
## 每一步建立新的快照

<p class="slide-ref" data-slide="24">對應程式：<code>examples/SnapshotDemo.java</code></p>

電池從20開始：指令1探索扣6，指令3充電加5。

```java
battery.explore();
history.add(new StepCard(1, battery.getEnergy()));
battery.charge();
history.add(new StepCard(3, battery.getEnergy()));
```

第一筆保存14，第二筆保存19；現在的電池是19。

快照＝建立當時的資料。每次 new 都複製當下整數值，
所以之後充電，不會把第一筆的14改成19。

<!-- 教師提示：兩次new是關鍵。清單的add只保存參考，不是自動複製物件。 -->

---
<!-- _class: small -->
## 把需求變成 MissionStep

<p class="slide-ref" data-slide="25">對應程式：<code>starter/MissionStep.java</code>、<code>starter/MissionLog.java</code></p>

StepCard 先示範兩欄；正式 MissionStep 再加入結果 result。

| 需求語句 | 類別設計 |
|---|---|
| 指令、行動後能源、結果一起保存 | command、energy、result 三個private欄位 |
| 建立就有完整資料 | 三參數建構子 |
| 歷史建立後只讀 | 三個getter，不提供setter |
| 判斷這一步是否成功 | boolean isSuccessful() |
| 控制中心顯示報表 | MissionLog負責輸出 |

MissionStep 只保存／查詢傳入資料；耗電與停止判斷仍由 MissionLog 處理。

<!-- 教師提示：由需求推設計而非先把名詞都變類別。完整防護不在本週必做。 -->

---
## 完成 MissionStep：建構、讀值、判斷成功

<p class="slide-ref" data-slide="26">對應程式：<code>starter/MissionStep.java</code></p>

| TODO | 實作內容 | 檢查方式 |
|---|---|---|
| 1 | 建構子以 this 保存 command、energy、result | 建立後讀三欄 |
| 2 | getCommand／getEnergy／getResult | 各回傳本物件的對應欄位 |
| 3 | isSuccessful：內容為OK才回傳true | new String("OK")也應為true |

三個欄位已提供；完成TODO 1–3後，先用下一頁查詢驗證，
再處理 MissionLog 的TODO 4–6。getter不印報表。

<!-- 教師提示：E1使用講義暫時檢查，兩個物件都建好再讀第一個。starter可編譯不代表完成。 -->

---
<!-- _class: small -->
## 兩個物件要一起檢查

<p class="slide-ref" data-slide="27">對應程式：<code>starter/MissionLog.java</code>、<code>teacher/MissionStepCheck.java</code></p>

這是獨立查詢測試資料，不代表連續兩步任務。
暫放自己的 MissionLog.main 最前；兩筆建好後印出查詢值，記錄後移除。

```java
MissionStep first =
    new MissionStep(1, 14, new String("OK"));
MissionStep second =
    new MissionStep(2, 12, "NOT_ENOUGH_ENERGY");
```

| 物件 | 預期 command／energy／result／isSuccessful |
|---|---|
| first | 1／14／OK／true |
| second | 2／12／NOT_ENOUGH_ENERGY／false |

要確認：建立second不覆蓋first；不同字串物件只要內容為OK，也應判成功。

<!-- 教師提示：若first變成second的值，查static；OK卻false，查==。new String只用於內容比較反例。 -->

---
## 每步建立物件，三條清單改成一條

<p class="slide-ref" data-slide="28">對應程式：<code>starter/MissionLog.java</code></p>

runMission 的 history 已宣告；本頁要填的是 switch 後的TODO 4：

```java
ArrayList<MissionStep> history = new ArrayList<>();
```

```java
// switch 完成，取得本步的實際結果後
history.add(new MissionStep(command, energy, result));
```

例：首次探索完成後，新增一筆 `(1, 14, "OK")`。
每筆已讀指令都記，包含拒絕、無效及返回；停止後的指令不讀也不記。

<!-- 教師提示：若放在switch前，能源早一步；若只在OK分支add，摘要與counts會改變。 -->

---
## TODO 5：先取物件，再讀欄位

<p class="slide-ref" data-slide="29">對應程式：<code>starter/MissionLog.java</code></p>

```java
MissionStep step = history.get(i);
int command = step.getCommand();
int energy = step.getEnergy();
String result = step.getResult();
```

在 printReplay 的TODO 5補上索引for迴圈與列印。
`i` 從0到 history.size()−1，顯示步號用 i+1；第一筆應印：

```text
step 1: command=1, result=OK, energy=14
```

<!-- 教師提示：完整回放for與println由學生組合，不在投影片塞全部程式。長迴圈在講義及教師答案看。 -->

---
<!-- _class: small -->
## TODO 6：由物件查詢做摘要

<p class="slide-ref" data-slide="30">對應程式：<code>starter/MissionLog.java</code></p>

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

steps 是 history.size()；rejected＝steps−ok−returned。
例 `{3,9,0}`：已滿電拒絕、無效、返回，得到 steps=3、ok=0、returned=1，
所以 rejected=2。RETURN 單獨計，不算成功也不算拒絕。

<!-- 教師提示：RETURN不是成功也不是拒絕。ignored仍為script.length-steps。 -->

---
<!-- _class: small -->
## 求最低能源：讀值後比較

<p class="slide-ref" data-slide="31">對應程式：<code>starter/MissionLog.java</code></p>

清單裝的是 MissionStep，不是整數；先讀 getEnergy，再比較大小。

```java
int lowest = history.get(0).getEnergy();
for (MissionStep step : history) {
    if (step.getEnergy() < lowest) {
        lowest = step.getEnergy();
    }
}
```

支援碼已提供，**不是新增TODO**；空清單先印N/A，不可讀第0筆。
非空才以上面迴圈處理。用首筆當起點，避免預設0把14、8、6錯算成0。

<!-- 教師提示：這段支援碼已提供。不能直接用min(history)，MissionStep尚未定義比較順序。 -->

---
## 先驗證成功與返回兩條核心路線

<p class="slide-ref" data-slide="32">對應程式：<code>starter/MissionLog.java</code></p>

每次從energy=20、samples=0獨立執行；改main的script後重新編譯。
終態三個數字依序為**能源／樣本／成功行動數**。

| 路線 | 預期終態 | 日誌重點 |
|---|---|---|
| {1,1,2} | 6/5/3 SUCCESS | 能源14、8、6 |
| {3,9,0} | 20/0/0 RETURNED | 拒絕、無效、返回都保存 |

除了終態，還要核對回放、指令次數counts、摘要與最低能源。
這兩條路線的最低能源為6／20，battery百分比為30.0／100.0。

完整六條路線見講義E3與作業，課後補齊本人實測。

<!-- 教師提示：不能只看最終三個數字。檢查物件快照與原行為一致。 -->

---
<!-- _class: small -->
## 教師示範：改初始狀態，檢查停止邊界

<p class="slide-ref" data-slide="33">對應程式：<code>teacher/FixedScriptMission.java</code>、<code>teacher/MissionLogCheck.java</code></p>

在本週教材 `week05/codex版本` 目錄執行：

```powershell
javac -encoding UTF-8 -d out-teacher teacher/*.java
java -cp out-teacher FixedScriptMission 2 4 2
```

參數依序是初始能源2、樣本4、掃描指令2。

| 初始 energy/samples；script | 預期 |
|---|---|
| 2/4；{2} | 同時耗盡與達標，SUCCESS |
| 0/0；{3,1} | EXHAUSTED，零筆日誌 |
| 20/5；{1} | SUCCESS，零筆日誌 |
| 20/0；{} | UNFINISHED、最低值N/A |

判斷重點：達標優先於耗盡；初始已停止就不讀指令。
教師操作工具，學生記錄終態與日誌筆數。

<!-- 教師提示：這是教師支援工具，不要求學生另寫命令列介面。四列依序執行FixedScriptMission 2 4 2、0 0 3 1、20 5 1、20 0；空腳本只傳兩個初始值。第一列應為0/5/1 SUCCESS、1筆日誌、最低能源0；初始已停止兩列都為零筆。教師回歸工具另執行java -cp out-teacher MissionLogCheck；不把電池charge規則套成任務可復活。 -->

---
## 新需求：先設計樣本運送盒

**目的：換一個情境，練習從需求決定欄位與方法。**

- 建立時指定容量：小於1改1，大於8改8，初始存量0。
- 份數須為正且放得下；否則整批拒絕、回傳false、存量不變。
- 查存量與剩餘空間，無任意改寫存量的操作。
- 一次卸空，回傳卸下份數，空盒回傳0。

本段產出是下一頁的設計卡，接著用它審查 AI 建議。
完整 Java 實作列課後選做，不接進 MissionLog。

<!-- 教師提示：F1共8分鐘，先讓學生從未做過的需求推設計，不先投影教師SampleCase完整碼。 -->

---
## 設計卡：每個決定都要對應需求

<p class="slide-ref" data-slide="35">對應程式：<code>teacher/SampleCase.java</code></p>

| 設計卡 | 要回答的問題 |
|---|---|
| 責任、欄位、可見性 | 哪些資料需保存？哪些可計算？ |
| 建構子 | 初始值與不合法容量怎麼處理？ |
| 方法契約 | 輸入參數、回傳型態、成功／拒絕時存量如何變？ |
| 測試 | 填滿、超量、卸空會看到什麼？ |

填入講義 F1 設計卡；方法名可自訂，但卡片、Prompt與呼叫要一致。
以容量3為例，自己寫出存2、再存2、存1、卸空的預期。

<!-- 教師提示：方法名可自訂但需一致。紙上預期不是已執行證據；教師參考另存，不是學生必交新類別。 -->

---
<!-- _class: small -->
## 審查模擬 AI 程式：找出需求偏差

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

例：容量3連存2兩次，這段會讓count變4；需求應拒絕第二次且保留2。
再以設計卡核對，**至少四項偏差各附呼叫例子**。
完整錯誤版在講義 F2；這是教師自編片段，不放正式 src。

<!-- 教師提示：教師自編模擬建議。至少找四項偏差，各配具體呼叫，不能只回答封裝不好；F2共8分鐘。 -->

---
## 用反例說明偏差，再改寫 Prompt

- 容量0或9，建構後應符合什麼範圍？
- 容量3先存2，再存2，回傳與存量應如何？
- 存0或負數，呼叫者能知道拒絕嗎？
- 如何查剩餘空間、卸空並取得份數？

產出：F2 偏差表＋一份改寫後的 Prompt。
把問題改寫成AI可遵守的規格，例如「容量小於1時設為1」。
其餘依你的設計卡寫清責任、建構規則、方法參數／回傳與測試。
可用紙上推論並標明未實測，不要求登入 AI 服務。

<!-- 教師提示：可以紙上推論，必須標示未實測。完整實作選做；不強制登入AI服務，不提前看教師答案。 -->

---
## 除錯選項一：資料全加 static

<p class="slide-ref" data-slide="38">對應程式：<code>starter/MissionStep.java</code></p>

**第38–40頁三選一，在 repo 外副本操作。**

> 模擬建議：「所有地方都要讀資料，加 static 更方便。」

1. 正確版先保存，副本把三欄加 static。
2. 用第27頁的兩筆測試資料，兩個都建立後再讀第一筆。
3. 第一筆為何也變成第二筆資料？
4. 恢復實例欄位，重測第27頁與第32頁兩條路線。

預期反例：第一筆變成 2／12／NOT_ENOUGH_ENERGY。

<!-- 教師提示：F3三選一，10分鐘先完成預測與一輪操作，完整紀錄可課後補。錯誤只放repo外的副本。 -->

---
## 除錯選項二：用 == 比較結果

<p class="slide-ref" data-slide="39">對應程式：<code>starter/MissionStep.java</code></p>

**第38–40頁三選一，在 repo 外副本操作。**

> 模擬建議：「固定代碼，用 == 比較就好。」

```java
return result == "OK"; // 副本錯誤示範
```

用第27頁的 `new String("OK")` 測試：這個錯誤寫法回傳false。
因為 == 比參考，並非比內容；改回內容比較應為true。
再重測第27頁與第32頁兩條路線，記錄修正理由。

<!-- 教師提示：常值字串可能讓錯誤偶然通過，所以不能只測主任務的固定常值。 -->

---
<!-- _class: small -->
## 除錯選項三：保存同一個可變物件

<p class="slide-ref" data-slide="40">對應程式：<code>examples/SnapshotDemo.java</code></p>

**第38–40頁三選一，在 repo 外副本操作。**

在副本以本段替換 SnapshotDemo.main；執行後讀取兩筆能源：

```java
RoverBattery battery = new RoverBattery(20);
ArrayList<RoverBattery> history = new ArrayList<>();
battery.explore();
history.add(battery);
battery.charge();
history.add(battery);
System.out.println(history.get(0).getEnergy() + "/"
        + history.get(1).getEnergy());
```

先預測輸出，再執行，與下一頁的正確快照比較。

<!-- 教師提示：用SnapshotDemo副本，只替換main內容；完整片段與列印在講義F3。不要更動正式examples。 -->

---
## 快照反例：最後能源相同，歷史卻錯了

<p class="slide-ref" data-slide="41">對應程式：<code>examples/SnapshotDemo.java</code></p>

| 保存方式 | 第一筆能源 | 第二筆能源 |
|---|---:|---:|
| 同一電池參考存兩次 | 19 | 19 |
| 每步 new StepCard 保存數值 | 14 | 19 |

兩種方式的目前電池都為19，單看最後能源抓不到錯誤。

本頁承接選項三；恢復原 SnapshotDemo，確認逐筆為14、19。
三選一完成任一項後，都重測 `{1,1,2}` 與 `{3,9,0}`。

<!-- 教師提示：清單add不複製物件。新反例不需完整Rover，保留原有小類別即可；答案先遮住讓學生預測。 -->

---
## 資料只讀，仍可能記錯時間點

<p class="slide-ref" data-slide="42">對應程式：<code>starter/MissionStep.java</code>、<code>starter/MissionLog.java</code>、<code>teacher/SampleCase.java</code></p>

- MissionStep 保存過去：建立後只讀，不提供改寫歷史的setter。
- SampleCase 管目前存量：可以透過合法的存入／卸空動作改變。

**private 限制直接存取；它不會替你判斷記錄時機。**

例：探索前先記能源20，接著才扣到14；能源值在合法範圍，歷史仍記錯。
因此 MissionLog 必須先完成行動，再用當步結果建立 MissionStep。

反思：哪些方法可以改資料，應由該類別的需求決定。

<!-- 教師提示：F4反思仍沿用原三題。此例為時機錯誤示意，不修改正式程式。類別封裝不會自動驗證三欄語意一致；建構子依既有契約保存值。歷史只讀不等於所有類別都不能有修改操作。 -->

---
<!-- _class: small -->
## 用類別責任表，交接給第六週

<p class="slide-ref" data-slide="43">對應程式：<code>starter/MissionStep.java</code>、<code>starter/MissionLog.java</code>、<code>examples/RoverBattery.java</code></p>

| 類別 | 一句話責任 |
|---|---|
| MissionStep | 保存一筆已發生的任務記錄 |
| MissionLog | 推進腳本、保存歷史、產生報表 |
| RoverBattery | 管理能源及受控變更 |

RoverBattery 只用在本週示範；主任務的energy／samples仍由MissionLog管理。
下週將責任表轉成UML類別圖，再討論如何拆分目前狀態的責任。

<!-- 教師提示：不直接把清單判為UML組合，不提前要求完整Rover。先口述責任，正式建模留第六週。 -->

---
## 保存可重現的正確版本

<p class="slide-ref" data-slide="44">對應程式：<code>starter/MissionLog.java</code></p>

1. main的script恢復 `{1,1,2}`，移除臨時檢查，還原錯誤副本的修改。
2. 重新編譯、執行，完成LAB_REPORT與AI_USAGE。
3. GitHub Desktop檢查差異、commit、push、遠端核對。
4. 另取回新副本重編譯，未做項目如實標示。

新副本應有回放能源14、8、6，終態6/5/3 SUCCESS，battery=30.0。
src交examples六檔＋完成的starter兩檔；teacher同名檔及out不提交。

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
