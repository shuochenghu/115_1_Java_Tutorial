# 第五週教師筆記｜用一個物件保存一步歷史

## 目的、先備與證據

目的是讓學生把需求轉成類別責任與可執行契約，不只是記住 class 語法。前週已教陣列、ArrayList、參考與方法；本週仍要診斷清單索引、equals 與停止規則。作者實測見 verification，學生須填本人證據；教師展示不算學生親自操作。

本週可觀察成果：學生能建立兩個獨立 MissionStep、指出 this 的欄位／參數、用查詢方法讀取、解釋 static 造成的共享資料，並在相同任務輸出下完成三清單轉物件清單。

## 投影片目的與活動對照

投影片上方只列對應的.java教材相對路徑，沒有對應程式的頁面不加標示。學生自己的檔案位於java-05/src；活動操作與必做／選做依各頁內文說明，標題直接表達學習目的。

| 頁次 | 要解決的問題 | 學生應能完成／留下什麼 |
|---|---|---|
| 1–5 | 為什麼要改三條清單？本週改到哪裡？ | 說明同一步資料應一起保存，找到入口與合作類別 |
| 6–14 | 自訂類別如何建立並保存獨立資料？ | B1–B2追蹤、B3二選一編譯診斷，理解建構／this／getter |
| 15–19 | 哪些操作可以改狀態？欄位是否共享？ | C預測與執行、C1存取權對照；區分實例資料與static流程 |
| 20–24 | 參考會共享什麼？如何保留歷史？ | D1參考追蹤、D2三檔IDE操作、D3快照14／19 |
| 25–31 | 如何把資料設計接回任務？ | E1–E2六個TODO；第27頁為暫時檢查，第31頁為已給支援碼 |
| 32–33 | 如何知道改造後行為一致？ | E3完整輸出；第33頁由教師操作FixedScriptMission，學生記觀察 |
| 34–37 | 能否從新需求設計，再指出AI建議偏差？ | F1設計卡→F2至少四項偏差與呼叫例子→改寫Prompt |
| 38–41 | 如何用反例重現並修正錯誤？ | F3三選一；第41頁只承接選項3，任一選項都須修正後重測 |
| 42–45 | 設計反思、下週入口與保存 | F4／G說明責任，H保存可重現版本；來源頁無新增作業 |

第34–37頁必做是設計與判讀，可用標明未實測的紙上呼叫；SampleCase實作仍是課後選做。第36頁錯誤程式來自講義F2，不是teacher/SampleCase.java；教師完整參考在學生先設計與審查後才使用。第38–40頁是同一個三選一活動，不能累加成三份必做。所有新增說明均沿用既有講義與作業，不增列交件。

第33頁在week05/codex版本目錄編譯teacher後，依表格執行FixedScriptMission 2 4 2、0 0 3 1、20 5 1、20 0。參數為初始能源、樣本與其餘指令；空腳本只傳兩個初始值。MissionLogCheck是教師回歸檢查，不是學生另寫的作業。勿同時編譯starter與teacher的同名類別。

## 全頁清楚度複核補充（2026-10-29）

第2頁從正常三清單與「漏記第2步能源」的對照帶課：回放以相同索引取三欄，索引1錯把第3步能源6當成第2步，索引2則讀取越界。本週的物件表示讓同一步資料一起交付，但仍需正確傳值、每步建立與新增；不是宣稱物件自動避免所有漏記。

第8–11頁交代資料意義、建構呼叫與查詢的呼叫端；第17–19頁用目前能源14→19、歷史保留14區分資料角色及實例／static；第21頁接續first=14，spend後8、區域改指向1但first仍8。第23–24頁明確給快照的起點與預期；第25頁區分兩欄StepCard示範與三欄MissionStep作業，後者不執行任務行動。

第26–32頁補明TODO位置、先完成小類別再整合、查詢測試與任務輸入的不同用途、回放格式、摘要各筆數與最低值處理。第27頁的第二筆能源12／NOT_ENOUGH_ENERGY是人工查詢資料，不是聲稱能源12時掃描會被拒絕；正式任務結果必須仍依行動規則產生。第30頁的steps/ok/returned/rejected為3/0/1/2；第32頁終態數字為energy/samples/actionCount，不是日誌筆數。

第33頁講清成功優先及開始已停止的情形；第34–37頁保留獨立設計與至少四項偏差，僅提供一個錯存到4的審查示例及Prompt規格句，不替學生完成整張卡或偏差表。第38–41頁維持三選一，操作與重測直接指向第27、32頁，選項3補上列印。第42頁以記錄時機錯誤指出private的限制；第43頁明示RoverBattery僅為示範，主任務尚未把目前狀態交給它。第44頁標明預設腳本與新副本驗收輸出。原有必做、選做、六個TODO與時間安排維持。

## 150 分鐘配置

| 節次 | 活動 | 分鐘 | 必須看見的成果 |
|---|---|---:|---|
| 第1節 | A 取回與診斷 | 10 | 找到上週日誌的對齊問題 |
| 第1節 | B 類別、建構、this | 25 | 兩個物件追蹤、編譯錯誤擇一 |
| 第1節 | C 封裝、C#存取權對照 | 15 | 受控修改；省略修飾字的語言差異 |
| 第2節 | D 參考、快照、IDEA | 20 | 參考圖與三檔執行入口 |
| 第2節 | E1–E2 實作前段 | 25 | 建構與 getter 完成，開始整合 |
| 第2節 | E3 整合檢查 | 5 | 預設路線、無效／返回路線先通過 |
| 第3節 | F 新需求、AI審查與反例 | 30 | F1設計8、F2審查8、F3除錯10、F4反思4 |
| 第3節 | G 檢核 | 5 | 類別責任與下週問題 |
| 第3節 | H 保存 | 15 | 正確版、紀錄與推送狀態 |

六條路線、B–D 詳細表與完整反例紀錄可課後補齊，課堂優先掌握兩個物件、建構／this、每步新物件及 `{1,1,2}`／`{3,9,0}`。150 分鐘為規畫，未經學生試教；C1佔C段約3分鐘，用口頭對照與教師副本展示；B3、F3的完整證據可課後補。若 D 的 IDE 操作較慢，改由教師帶看設定，保留實作時間，明確標示學生待補 GUI。F1／F2要保留學生先自行設計與審查的時間，不先展示SampleCase答案。

## 教學主線與知識點

### 類別不是檔案裡多一個 main

StepCard 定義一種資料的結構與查詢，CreateStepDemo 負責啟動與顯示。用兩個檔案展示「執行入口」與「領域資料」的不同角色。先在 VS Code 編譯執行；三檔的 SnapshotDemo 才加入 IDEA。同一專案可以有數個 main，但一次執行選定其中一個。

`StepCard first = new StepCard(1,14)` 依序建立物件、以建構子存入資料，再把參考值交給 first。不要把 first 畫成直接裝了整個物件的盒子。欄位跟著物件存在；建構參數是該次呼叫的區域資料，結束後仍需欄位保存值。

### 建構子與 this

建構子名稱與類別相同、不寫回傳型態；加 void 就成為普通方法。宣告了有參數建構子後，不自動獲得無參數版。本週不教建構子過載或 this(...) 串接。

在 `this.energy = energy` 中，左側是目前物件的欄位，右側是同名參數。錯寫 `energy = energy` 只把參數指定給自己，欄位仍為預設0；String 欄位預設 null。getter 若回傳常數，可讓一條路線偶然通過，兩個不同物件能揭露問題。

### 封裝與責任

private 限制跨類別直接讀寫；方法負責查詢或合法行為。RoverBattery.explore 能拒絕不足，charge 能封頂，比要求每個呼叫端自行記住扣電規則更集中。這是初步的責任集中與分離顯示，不宣稱已教完整 SOLID。

MissionStep 是已發生的快照，沒有改寫需求，所以只提供 getter／isSuccessful。方法不輸出，MissionLog 負責報表；將來換顯示方式不需動資料類別。所有欄位配 setter 不等於封裝完成。private 不檢查範圍；本週建構參數採合法前提，完整違約處理留後續。

### C# 存取權的遷移

C1答案：兩種語言都可寫private欄位；C#類別欄位省略修飾字為private，Java則為套件存取。本週StepCard與CreateStepDemo都在預設套件，省略private後直接寫first.energy會編譯成功，值可變成99。這是刻意在副本暴露的保護缺口，展示後恢復。

C#的public int Energy { get; private set; }表示外部可讀、類別內可寫。Java本週用private欄位、public getter及explore／charge等行為表達需求；不強迫補setEnergy，也不把最外層類別的預設可見性混入這張欄位對照表。只需辨識差異，不展開套件設計。

### static、參考與快照

static main 可啟動流程，static 工具方法不依賴某筆記錄；每筆資料則需要自己的實例欄位。若三欄加 static，後一次建構會覆蓋先前物件看到的資料。

Java 一律傳值；對物件是複製參考值。傳入方法後修改同一物件看得見，重新指定參數不改呼叫端。ArrayList 同樣保存參考，add 不會自動深複製。StepCard／MissionStep 複製傳入的 int 值，各步建立新記錄，所以後來改目前能源不會改歷史能源。本週 String 為不可變型態；不要延伸推論所有參考型態都會如此。

### 為何本週重新用迴圈做摘要

第四週的 Collections.min(energyLog) 處理 Integer；現在清單裝的是 MissionStep，尚未定義比較順序。直接 min(history) 不能表達取最低能源，也不應為此提前加入 Comparable／Comparator。讀 getEnergy 再用已教迴圈處理即可。最低值先取第0筆，但必須先判斷是否空；初始值設0會在所有能源都大於0時產生錯誤。

## 講義逐段答案

### A

1. 6／5／3 SUCCESS。2. 長度相同不保證語意對齊；單獨排序能源會讓同一步指令配錯能源。3. 可能越界，也可能索引仍在範圍卻配錯資料。4. command、行動後 energy、result 一次保存。

### B

B1：class 是類別宣告；private int energy 是實例欄位；建構子在建立時初始化；this.energy 指目前物件欄位、energy 指參數；StepCard 為型態、first 為參考變數、new 為建立運算式；getEnergy 的對象是 first 指向的物件，回傳 int。

B2：第一個 new 後 first=1/14；第二個 new 後 first 仍1/14、second=2/12；輸出 `first=1/14`、`second=2/12`、`same object=false`。兩次 new 建立不同實例，內容相同也不自動變同物件。

B3：直接存取會報 private access；new StepCard() 會報建構引數不符。編譯訊息依 JDK 語言不同，驗收抓原因，不要求逐字抄固定訊息。

### C

low：false/5、true/10、true/4；edge：true/20、false/20。setter 若任意寫入999，原本狀態約束失效；如果需要 setter，也應明確限制並驗證。歷史記錄只讀，電池透過合法動作更新。RoverBattery 不知道任務結束，所以它與任務停止條件不是相同責任。

### D

D1：alias 與 first 指同一電池，other 是另一個。alias.explore 後14/20，spend後8/20；replaceLocal內印1，返回後 first仍8。傳入的是參考值的副本。

D2：學生填實際 IDEA／SDK／GUI；教師不可先填作者沒做過的成功。依官方文件核對一般 Java 專案、SDK、來源根與 main；本週不跟隨新版文件的 compact source／Java25範例要求。

D3：第0筆1/14，第1筆3/19，目前能源19，separate records=true。清單不自動複製；重複 add 同一物件可以有兩個索引但只有一個物件。

### E：六個 TODO 答案

TODO 1：`this.command=command; this.energy=energy; this.result=result;`。

TODO 2：三個 getter 分別 `return command;`、`return energy;`、`return result;`。

TODO 3：`return "OK".equals(result);`。E1 兩行是 `1/14/OK/true`、`2/12/NOT_ENOUGH_ENERGY/false`，即使建立 second 後 first 也不變。

TODO 4：`history.add(new MissionStep(command, energy, result));`，位於 switch 之後。不得只在 OK 分支記錄。

TODO 5：用 i 走訪；`MissionStep step=history.get(i);`，再以三個 getter 印出既有格式。完整碼見 teacher/MissionLog.java。

TODO 6：從0開始；`if(step.isSuccessful()) ok++;`、`if("RETURN".equals(step.getResult())) returned++;`。rejected 是 steps-ok-returned，不把 RETURN 算拒絕。

E3 六路線的 counts／摘要／最低值見 assignment。預設完整輸出：

```text
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
step 3: command=2, result=OK, energy=6
counts[0..3]=[0, 2, 1, 0]
steps=3, ok=3, rejected=0, ignored=0
lowest energy=6
final energy=6, samples=5, actionCount=3 -> SUCCESS
battery=30.0
```

教師固定狀態：5/0+{1,3,0}為10/0/1 RETURNED、3筆、最低5；2/4+{2}為0/5/1 SUCCESS、1筆、最低0；0/0+{3,1}為EXHAUSTED、0筆、最低N/A；20/5+{1}為SUCCESS、0筆、最低N/A；20/0+{}為UNFINISHED、0筆、最低N/A。

### F

F1設計卡參考（名稱可不同，需求與呼叫須一致）：

| 項目 | 參考答案與理由 |
|---|---|
| 類別與責任 | SampleCase，維護一盒樣本的容量與存取規則，不印報表、不操作探測車 |
| 欄位 | private int capacity、count；freeSpace用capacity-count計算，避免多存一份需同步的狀態 |
| 建構 | SampleCase(int capacity)，小於1改1、大於8改8，count=0；先修正參數再指定欄位 |
| 查詢 | getCapacity/getCount/getFreeSpace均無參數、回傳int，不改狀態 |
| 修改 | boolean store(int amount)，非正數或超過剩餘空間回傳false且不改；int unload()回傳卸下份數後count=0 |
| 約束與不提供操作 | 1<=capacity<=8、0<=count<=capacity；無任意setCount，不提供改容量操作 |

必要測試：容量3初始count0/free3；store(2)為true、count2/free1；再store(2)為false且維持2；store(1)為true、count3/free0；unload()回傳3，再unload回傳0。另測容量0改1、9改8、store(0)與store(-1)拒絕，以及兩盒資料獨立。

這些先作為學生紙上預期，不能寫成本人已實測。完整教師參考在teacher/SampleCase.java，teacher/SampleCaseCheck.java實際檢查12項；學生不必複製這兩個檔案，也不把SampleCase接進主任務。

F2模擬AI回覆至少可指出：

| 偏差 | 呼叫反例與後果 |
|---|---|
| capacity/count公開 | box.count=-1或box.capacity=0可破壞狀態 |
| 建構不修正容量 | new SampleCase(0)的capacity仍0 |
| 未拒絕非正數 | 先存2後store(-1)，存量竟減少；store(0)也沒有拒絕訊號 |
| 未檢查剩餘空間 | 容量3先存2再存2，變4而非整批拒絕 |
| store回傳void | 呼叫端無法用boolean接收成敗；boolean ok=box.store(1)無法編譯 |
| 缺卸空／剩餘空間查詢 | 需求中的unload、getFreeSpace找不到可用方法 |

學生選四項即可，每項要指出需求句及具體呼叫。數值回傳錯誤與缺方法的編譯錯誤可同時存在，不能只寫「封裝不好」。

改寫Prompt可用：

> 設計SampleCase，建造時傳入容量並修正到1..8，初始空盒。欄位private；提供查詢容量、存量及剩餘空間的方法。store(int amount)僅在正數且放得下時整批存入，回傳boolean；拒絕不改存量。unload()清空並回傳原份數，空盒回傳0。不要任意setter或輸出方法。使用本週一般Java、單一建構子、不用繼承、record、Lambda或例外處理。請先說明責任及方法契約，再提出填滿、超量、非正數、卸空、容量修正與兩盒獨立的測試呼叫，不得聲稱我已實測。

本段是教師自編提示，沒有呼叫真實AI。完整程式保留在教師目錄，課堂先讓學生完成設計與審查。

F3反例三選一：

| 選項 | 錯誤副本預期表現 | 最小修正及重測 |
|---|---|---|
| 1 static共享資料 | E1第一行被覆蓋成2/12/NOT_ENOUGH_ENERGY/false | 恢復實例欄位；E1第一行回到1/14/OK/true，兩條任務路線再測 |
| 2 ==內容比較 | new String("OK")的isSuccessful為false；任務常值可能偶然通過 | 改equals；E1與兩條任務路線再測 |
| 3 保存可變電池參考 | 輸出19/19及same object=true；探索後本應保留的14消失 | 恢復每步new StepCard的原SnapshotDemo；兩筆command/energy為1/14、3/19，current=19、separate records=true；兩條任務路線再測 |

選項3的清單確實有兩筆，但兩個索引指向同一電池，add不複製物件。正式快照保存的是當時int值，不因後來充電而改變。此活動使用原有RoverBattery，不需引入完整Rover。工具verify.py會在暫存副本重現錯誤輸出，並以正式SnapshotDemo完整輸出檢查修正結果。

F4：歷史沒有改寫需求，所以MissionStep只讀；SampleCase存量需要改變，但只能由守規則的store／unload處理。只看最後能源無法證明歷史正確；呼叫端仍要在對的時機提供正確值，完整回歸檢查仍必要。

### G與H

G：宣告參考變數不等於建立物件；this指目前實例；資料欄位不static才能各自保存；add不複製物件；MissionStep保存一筆歷史、MissionLog推進任務及報表、RoverBattery封裝能源動作。H依本人實際保存與取回紀錄驗收。

## 常見失敗與最小診斷

| 現象 | 優先檢查 |
|---|---|
| 三欄仍0/null或讀取PENDING | 建構是否寫energy=energy、getter是否仍佔位 |
| 第一次物件建立後被第二次蓋掉 | 欄位是否static |
| 常值測試過，new String卻失敗 | 是否以==比較內容 |
| 快照兩筆能源都變成最後值 | 是否存同一可變物件的參考，未保存當時數值 |
| 同套件直接寫欄位居然能編譯 | 是否省略private，誤套C#類別欄位預設規則 |
| 日誌能源早一步 | 建立記錄是否放在switch之前 |
| {3,9,0}只剩返回或空記錄 | 是否只保存OK |
| 最低能源0但每步都大於0 | 最低值初始值是否用0 |
| 找不到main | 是否選了資料類別執行 |
| duplicate class／cannot find symbol | 同名教師檔混編，或src來源根與相依檔不在同一專案 |

## 教科書與官方來源

本專案 EPUB 目錄已核對：§3.2 Instance Variables, set Methods and get Methods；§3.3 Initializing Objects with Constructors；§3.4 Account with a Balance（取行為規則的概念）；§3.5 Primitive Types vs. Reference Types。補充§8.3控制存取、§8.4 this、§8.6預設／無參數建構子、§8.7 set/get。教材為自編，不複製教科書全文或圖，不引用未核對頁碼。

語言概念參考 Oracle 官方 Java Tutorials：[類別](https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html)、[建構子](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)、[this](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)、[存取控制](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)。這些頁面標示以JDK8撰寫，本週只取穩定的傳統class概念，不把它當新版功能清單。

IDE操作核對：[VS Code Java](https://code.visualstudio.com/docs/java/java-tutorial)、[IntelliJ Java專案與執行](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)、[IDEA內容根與來源根](https://www.jetbrains.com/help/idea/content-roots.html)。官方新版範例可能採Java25 compact source；本週維持傳統class/main及實際已安裝JDK。文件核對不能代替教室GUI實測。

C#對照依據：[Microsoft 類別成員的預設存取](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/classes-and-structs/access-modifiers)、[屬性存取子限制](https://learn.microsoft.com/en-us/dotnet/csharp/programming-guide/classes-and-structs/restricting-accessor-accessibility)。Java套件存取另對照上述Oracle存取控制頁；本週只比較類別欄位。
