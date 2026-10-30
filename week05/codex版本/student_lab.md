# 第五週學生實作｜讓一步任務成為一個物件

本講義另存當日 LAB_REPORT.md，保留題號。每張表先填預測，再親自執行；教師展示只寫「教師示範觀察」，未操作項目寫待補。完整繳交規則見 [assignment.md](assignment.md)。

## A｜取回與診斷（10 分鐘）

在學期 repo 建立 `YYYY_MM_DD/java-05/src`，日期用實際上課日。先以 VS Code 開 java-05；examples 及 starter 複製方式見 README，不複製 teacher。記錄本人 java／javac 版本：待填。

1. 第四週 `{1,1,2}` 的最終 energy／samples／actionCount：待填。
2. 三條清單都各有三筆，就能保證同索引屬於同一步嗎？若只把 energyLog 排序會如何？待填。
3. 如果第二筆只新增 command 與 result，讀取三條清單可能出現哪些問題？待填。
4. 希望「新增一筆」時，哪些資料能一起交付？待填。

回看投影片第2頁：若第2步漏加能源8，第3步仍加6，energyLog會成為[14,6]；請用這個假設回答第3題。這是錯誤示意，正式程式應完整保存每一步。

本週共同需求：同一步包含指令、行動後能源、結果，保留原順序及重複指令，拒絕／無效／返回也記錄。

## B｜從資料需求到類別與建構（25 分鐘）

先看 examples/StepCard.java 與 CreateStepDemo.java。VS Code 以資料夾開啟，兩檔放同一 src，執行含 main 的 CreateStepDemo。若不用 Run 連結，可在 java-05 的終端機編譯所有 src，再以 classpath 執行。

### B1 類別與物件各指什麼

類別定義一種物件有哪些資料及可做的操作；`new StepCard(1,14)` 建立一個具體物件，呼叫建構子初始化它。`first` 是保存參考值的變數。`first.getEnergy()` 透過該參考呼叫物件方法，不會建立新物件。

這與 WinForms 已接觸的 `new Button()` 類似，但本週要自己定義類別。以前會使用現成物件，不代表已會寫建構子與維護欄位。

| 程式位置 | 角色／用途（本人填寫） |
|---|---|
| public class StepCard | 待填 |
| private int energy | 待填：其生命週期與方法內區域變數有何不同？ |
| StepCard(int command, int energy) | 待填：為何沒有 void？ |
| this.energy = energy | 待填：左／右各屬於誰？ |
| StepCard first = new StepCard(1,14) | 待填：哪一部分是型態、參考變數與建構？ |
| first.getEnergy() | 待填：呼叫對象與回傳型態？ |

### B2 追蹤兩個物件

| 時間點 | first 欄位 command/energy | second 欄位 command/energy |
|---|---|---|
| 第一個 new 完成 | 待填 | 尚未建立 |
| 第二個 new 完成 | 待填 | 待填 |

三行輸出預測：待填。本人實際：待貼。為何兩個 new 即使參數相同也可能是不同物件？待填。

### B3 編譯錯誤診斷（二選一，另用副本）

取消 CreateStepDemo 中「直接寫 first.energy」或「new StepCard()」其中一行的註解。先預測，編譯後記關鍵訊息，隨即恢復正確碼。

本人選擇：待填；錯誤原因：待填；實際編譯訊息：待貼；恢復後結果：待填。

private 限制其他類別直接存取。已有有參數建構子時，編譯器不會另補無參數版。`public void StepCard(...)` 是方法，不能完成這次建構呼叫。

## C｜封裝要保護什麼（15 分鐘）

看 RoverBattery.java 及 BatteryDemo.java。建構前提是 initialEnergy 0..20，本週不測契約外值。先在紙上算，再執行：

| 呼叫順序 | 預測回傳值／能源 | 本人實際 |
|---|---|---|
| low 從5開始，explore | 待填 | 待填 |
| low.charge | 待填 | 待填 |
| low.explore | 待填 | 待填 |
| edge 從19開始，charge | 待填 | 待填 |
| edge 再charge | 待填 | 待填 |

explore 在能源不足時回傳 false 並保留原值；charge 以方法集中封頂規則。若開放任意 `setEnergy(999)`，誰需要負責檢查？待填。為何歷史 StepCard 不需要 setter，但目前電池仍可透過方法改變？待填。

private 只控制存取，數值範圍要由契約與程式維護。電池物件只管能源，沒有任務是否結束的資訊；BatteryDemo 中可對 0 能源電池呼叫 charge，但主任務 energy=0 已停止，不能再讀充電指令。

### C1 C# 的存取習慣，哪些能直接沿用？

以下比較「類別裡的欄位」，不是最外層類別的預設可見性。先判斷，再用 B3 的副本檢查。

| 目的／宣告 | C# 經驗 | Java 對照（本人填寫） |
|---|---|---|
| 欄位只供類別內使用 | private int energy; | 待填 |
| 類別欄位省略修飾字 | 預設 private | 待填：同一套件其他類別是否可存取？ |
| 外部讀取、內部才改值 | public int Energy { get; private set; } | 待填：本週用欄位與哪個方法組合？ |

Java 本週使用 `private int energy` 加 `public int getEnergy()`，修改則交給 explore／charge。不要直接把 C# 屬性寫法貼進 Java。

在 B3 副本將 StepCard 的 `private int energy` 改成 `int energy`，再取消 `first.energy = 99` 註解，會編譯失敗嗎？預測：待填；本人實際／教師示範觀察：待填。兩個類別都在預設套件，因此可直接寫入；結束後恢復 private 與原註解。此活動說明省略修飾字可能讓保護消失，不把套件設計擴成另一份作業。

## D｜參考、快照與多檔專案（20 分鐘）

### D1 追蹤 ReferenceDemo

指派 `alias=first` 複製參考值，兩者可指向同一個物件。方法參數同樣採傳值：修改該物件看得見，重新指定參數只改區域變數。

| 位置 | first.energy 預測 | other.energy 預測 | 本人實際觀察 |
|---|---|---|---|
| alias.explore 後 | 待填 | 待填 | 待填 |
| spend(first) 後 | 待填 | 待填 | 待填 |
| replaceLocal(first) 返回後 | 待填 | 待填 | 待填 |

inside replacement 印什麼？待填。畫出 first、alias、other 指向的物件，或用文字說明：待填。不要寫「Java 傳參考」。

### D2 三個檔案合作，加入 IntelliJ IDEA

SnapshotDemo 需要 SnapshotDemo.java、StepCard.java、RoverBattery.java。此前兩檔基本範例以 VS Code 操作；專案超過兩個程式檔時加入 IDEA，使用同一份來源，不逐題重做兩套 IDE。

1. 用 IDEA 開啟同一 java-05 目錄；若顯示建立／匯入專案，採一般 Java、IntelliJ build system，使用已安裝 JDK。不要在學期 repo 內再勾 Create Git repository，不新增 Maven／Gradle。
2. 在 File → Project Structure 確認 Project SDK；確認 src 為 Sources Root。若未標示，可在 src 上用 Mark Directory as → Sources Root，或在 Modules → Sources 設定。選單可能依版本不同，請教師核對。
3. 保留傳統 public class 與 public static void main；不改成新版 compact source。從 SnapshotDemo 的 main 執行，不選 StepCard 或 RoverBattery。
4. 若看不到 Run，先確認 SDK、來源根目錄及編譯錯誤；若重複類別，檢查是否混入 teacher；若版本錯配，核對 IDE 使用的 JDK 與重新編譯結果。

本人 IDEA 版本／Project SDK／來源位置：待填。是否實際用 GUI 執行：待填；若尚未操作，寫待補，不能用終端機結果代替。

### D3 追蹤快照

SnapshotDemo 每次執行行動後建立新 StepCard，建構時複製當下 int 值。電池後來改變，不會改掉已保存的整數欄位。

| 資料 | 預測 command/energy | 本人實際 |
|---|---|---|
| history 第0筆 | 待填 | 待填 |
| history 第1筆 | 待填 | 待填 |
| 最後 battery.energy | 待填 | 待填 |
| 兩筆是否不同物件 | 待填 | 待填 |

ArrayList 保存的是物件的參考值；如果兩次 add 同一個可變物件，清單會自動幫你複製物件嗎？待填。

## E｜完成自己的 MissionStep 與整合（30 分鐘）

本週 MissionLog 保留前週 action、終止與輸出流程。先完成小類別，再改清單，避免同時重寫全部任務。

### E1 TODO 1–3：資料與查詢

- TODO 1：建構子保存 command、energy、result，以 this 區分同名欄位。三欄均為 private 實例欄位，不加 static。
- TODO 2：getter 回傳自己的欄位。getter 不修改狀態、不印出文字。
- TODO 3：isSuccessful 只在 result 內容為 OK 時回傳 true。沿用第二週 equals，不依賴字串常值池。

在自己的 MissionLog.main 最前暫加以下檢查；在兩個物件都建立後才讀 first，以免漏掉共享欄位問題。記錄後移除，恢復原 main。

```java
MissionStep first = new MissionStep(1, 14, new String("OK"));
MissionStep second = new MissionStep(2, 12, "NOT_ENOUGH_ENERGY");
System.out.println(first.getCommand() + "/" + first.getEnergy()
        + "/" + first.getResult() + "/" + first.isSuccessful());
System.out.println(second.getCommand() + "/" + second.getEnergy()
        + "/" + second.getResult() + "/" + second.isSuccessful());
```

以上兩筆是獨立測試資料，不代表連續兩步任務的實際結果；用來檢查欄位獨立、getter與字串內容比較。new String("OK") 沿用第二週的反例手法，刻意使字串參考不同；只用於檢查內容比較。預測兩行：待填；本人實際：待貼。

### E2 TODO 4–6：每步一個物件

- TODO 4：switch 完成後、每輪結束前，新增 `new MissionStep(command, energy, result)`；所有已讀指令都記，不只記成功。不要搬到 switch 前。
- TODO 5：依 history 索引取得一筆物件，以 getter 印 `step 1: command=1, result=OK, energy=14`。先取得物件，再取欄位。
- TODO 6：走訪 history，以 isSuccessful 計 ok，以 getResult 內容為 RETURN 計 returned；後面的 rejected、ignored、空清單與最低值已提供。

清單宣告已提供。new建立一筆MissionStep，add保存其參考，get依索引取回同一物件；get本身不會建立新記錄。isSuccessful只表示當步result為OK，不表示任務已收集5份樣本。

每完成一組就編譯執行。為何 countCommands 現在要先取得 step.getCommand？待填。為何不能直接對 history 使用 Collections.min 取得能源最小值？待填，本週不用 Comparator 解決。

### E3 本人測試

每列從初始 20/0 獨立執行；修改 main 的固定 script，重新編譯。下表預期狀態依 energy／samples／actionCount；steps 等摘要也要核對，完整輸出貼在表後。時間不足者課後補齊，不抄教師紀錄。

| script | 預期最終狀態／結果 | 預期 steps/ok/rejected/ignored | 本人實際與相符否 |
|---|---|---|---|
| {0} | 20/0/0 RETURNED | 1/0/0/0 | 待填 |
| {1,1,2} | 6/5/3 SUCCESS | 3/3/0/0 | 待填 |
| {2,2,2,2,2} | 10/5/5 SUCCESS | 5/5/0/0 | 待填 |
| {3,9,0} | 20/0/0 RETURNED | 3/0/2/0 | 待填 |
| {1,1,1,1} | 2/6/3 SUCCESS | 3/3/0/1 | 待填 |
| {2,1} | 12/3/2 UNFINISHED | 2/2/0/0 | 待填 |

完整輸出（回放、counts、摘要、最低能源、終態、battery）：待貼。恢復 `{1,1,2}`。

教師另用 FixedScriptMission 示範：5/0、{1,3,0}；2/4、{2}；0/0、{3,1}；20/5、{1}；20/0、{}。寫下觀察到的終態及日誌筆數：待填。沒有看到示範者標示待補，這些不是另一份學生輸入介面作業。

## F｜把新需求轉成設計，再驗證 AI 建議（30 分鐘）

### F1 獨立需求設計卡（8 分鐘）

本段目的是獨立從需求決定欄位、建構與方法。產出F1設計卡後，用它審查F2模擬建議，再整理偏差與改寫Prompt；不必先完成另一個Java程式。任務中心需要一個樣本運送盒：

> 建造時決定容量，範圍為 1～8；超出時改為最接近的合法值。新盒子為空。每次存入的份數要大於 0；若剩餘空間不夠，整批拒絕且存量不變。呼叫者要知道是否存入成功。可以查目前存量與剩餘空間，但不能任意改寫存量。返航時可一次卸空，並取得卸下的份數；卸空盒子應回傳 0。

以下暫以 SampleCase 命名，你可另取合理名稱，但設計卡、Prompt 與呼叫例子須一致。本段必做是設計與判讀，完整 Java 實作列為課後選做。

| 從需求抽出的設計 | 本人內容與對應需求 |
|---|---|
| 類別名稱與一句責任 | 待填 |
| 欄位／型態／可見性，哪些資料由其他值算出即可？ | 待填 |
| 建構參數、初始存量、不合法容量處理 | 待填 |
| 公開方法的參數、回傳型態、拒絕時是否改狀態 | 待填 |
| 不提供哪些方法？物件應一直符合什麼條件？ | 待填 |
| 測試呼叫、預期回傳值與狀態 | 待填：至少涵蓋填滿、超量拒絕與卸空 |

### F2 用設計卡審查模擬 AI 回覆（8 分鐘）

以F1設計卡逐條核對，留下至少四項偏差及各自的呼叫例子，再完成下方Prompt。下列是教師自編的錯誤建議，不是真實 AI 對話，也不是教師解答；完整教師參考另在teacher/SampleCase.java。不要直接放進正式 src。

```java
public class SampleCase {
    public int capacity;
    public int count;
    public SampleCase(int capacity) {
        this.capacity = capacity;
    }
    public void store(int amount) {
        count += amount;
    }
    public int getCount() {
        return count;
    }
}
```

| 不符合需求之處（至少四項） | 違反哪句需求？ | 哪個呼叫會看出問題？ |
|---|---|---|
| 待填 | 待填 | 待填 |
| 待填 | 待填 | 待填 |
| 待填 | 待填 | 待填 |
| 待填 | 待填 | 待填 |

完整 Prompt：待填。請帶入 F1 的責任、欄位、建構處理、方法契約與測試；限定本週已教的一般 Java、單一建構子，不用 record／繼承／Lambda／例外處理。提出測試呼叫不等於已執行，本段呼叫例子可以是紙上推論，務必標示。

### F3 模擬錯誤三選一（10 分鐘，完整紀錄可課後補）

先保存人工正確版，再把 src 複製到學期 repo 以外的練習目錄。錯誤只放副本，正式 src 最後保持正確。

1. 「欄位加 static 可以讓所有地方方便讀取。」把副本 MissionStep 三欄改成 static，用 E1 在兩個物件都建立後讀取。
2. 「固定代碼用 == 比較就好。」把副本 isSuccessful 改為 `return result == "OK";`，用 E1 的 new String("OK") 檢查。
3. 「把電池本身存進清單，之後讀能源就好。」在 SnapshotDemo 副本中，只將 main 的內容換成以下片段；保留原 import。先預測第一筆是不是仍保留探索後的能源，再編譯執行。

```java
RoverBattery battery = new RoverBattery(20);
ArrayList<RoverBattery> history = new ArrayList<>();
battery.explore();
history.add(battery);
battery.charge();
history.add(battery);
System.out.println(history.get(0).getEnergy() + "/"
        + history.get(1).getEnergy());
System.out.println("same object=" + (history.get(0) == history.get(1)));
```

選項3只改「保存歷史的方式」，呼叫順序仍是探索再充電。以為有兩筆就有兩個獨立物件，會漏看什麼？待填。最小的概念修正是每步建立 StepCard，保存當時數值；實作時恢復原 SnapshotDemo，再比對第0筆與第1筆的能源。這個小反例不要求另寫 Rover 類別，也不更動主任務規則。

| 證據 | 本人紀錄 |
|---|---|
| 選項、反例與修改前預測 | 待填 |
| 錯誤副本實際結果／錯誤訊息 | 待填 |
| 最小修正與採納／拒絕理由 | 待填 |
| 修正後同一反例結果（1、2用E1；3用SnapshotDemo） | 待填 |
| 修正後 {1,1,2} 與 {3,9,0} 完整輸出 | 待貼，可引用 E3 本人重測 |

### F4 設計反思（4 分鐘）

1. MissionStep 不提供 setter，SampleCase 卻能透過 store／unload 改變資料，兩者各符合什麼需求？待填。
2. 只測最後的能源與成功字樣，能證明每筆歷史快照都正確嗎？待填。
3. 類別把資料放在一起後，仍需由誰確保傳入值與建立時機正確？待填。

## G｜課末檢核與第六週入口（5 分鐘）

1. `MissionStep step` 與 `new MissionStep(...)` 差在哪？待填。
2. this.energy 與參數 energy 差在哪？待填。
3. 為何 main 為 static，但 MissionStep 資料不用 static？待填。
4. history.add 同一個物件兩次，是否產生兩個新物件？待填。
5. 分別用一句話描述 MissionStep、MissionLog、RoverBattery 的責任：待填。這份責任表下週可轉成 UML 類別圖，本週不要求正式關係符號。

## H｜保存與取回（15 分鐘）

恢復正確欄位、equals、預設腳本，移除 E1 暫時輸出，重新編譯。完成 LAB_REPORT、AI_USAGE、專案 README。GitHub Desktop 管理整個學期 repo，檢查差異後 commit／push，核對遠端最新版本。不要提交 out、.class 或本機 IDE 暫存資料；沿用根 .gitignore，必要時補入 out/、*.class、.idea/、*.iml。

repo／作業位置：待填；最終 commit：待填；遠端核對結果：待填。

在另一位置 clone，開 java-05，重新編譯並執行預設 MissionLog；本人新副本 JDK／輸出：待填。課堂不足可課後完成。若推送失敗，請教師協助，先保存至不會還原的位置，確認可讀再補推。

## 課後選做｜依設計卡實作 SampleCase

先完成主作業，再在獨立專案實作 F1 設計，寫出填滿、超量、0／負數、容量修正、卸空與兩盒獨立的測試。教師參考答案另存 teacher，不列入學生必交八檔；不把 SampleCase 接進主任務或提前重構完整 Rover。
