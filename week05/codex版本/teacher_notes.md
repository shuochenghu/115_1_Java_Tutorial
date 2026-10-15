# 第五週教師筆記｜用一個物件保存一步歷史

## 目的、先備與證據

目的是讓學生把需求轉成類別責任與可執行契約，不只是記住 class 語法。前週已教陣列、ArrayList、參考與方法；本週仍要診斷清單索引、equals 與停止規則。作者實測見 verification，學生須填本人證據；教師展示不算學生親自操作。

本週可觀察成果：學生能建立兩個獨立 MissionStep、指出 this 的欄位／參數、用查詢方法讀取、解釋 static 造成的共享資料，並在相同任務輸出下完成三清單轉物件清單。

## 150 分鐘配置

| 節次 | 活動 | 分鐘 | 必須看見的成果 |
|---|---|---:|---|
| 第1節 | A 取回與診斷 | 10 | 找到上週日誌的對齊問題 |
| 第1節 | B 類別、建構、this | 25 | 兩個物件追蹤、編譯錯誤擇一 |
| 第1節 | C 封裝與行為 | 15 | 能說明方法如何守住能源規則 |
| 第2節 | D 參考、快照、IDEA | 20 | 參考圖與三檔執行入口 |
| 第2節 | E1–E2 實作前段 | 30 | 建構與 getter 完成，開始整合 |
| 第3節 | E3 整合檢查 | 5 | 預設路線、無效／返回路線先通過 |
| 第3節 | F 需求、AI反例與反思 | 25 | 一項反例的前後實測 |
| 第3節 | G 檢核 | 5 | 類別責任與下週問題 |
| 第3節 | H 保存 | 15 | 正確版、紀錄與推送狀態 |

六條路線、B–D 詳細表與完整反例紀錄可課後補齊，課堂優先掌握兩個物件、建構／this、每步新物件及 `{1,1,2}`／`{3,9,0}`。150 分鐘為規畫，未經學生試教；若 D 的 IDE 操作較慢，改由教師帶看設定，保留實作時間，明確標示學生待補 GUI。

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

F1 可用 Prompt 示範：

> 設計 MissionStep，保存同一步 int command、int energy、String result，使用 private 實例欄位；建構時一次傳入三值，前提為 energy 0..20、result 為既有非null代碼，command可為任意int。提供三個getter及boolean isSuccessful，以內容比較OK；不需要setter及輸出方法。MissionLog每步行動後建立新物件並保存於ArrayList，負責回放與摘要。沿用一般Java，不用record、繼承、Lambda、例外處理。請先說明責任及順序，再列兩物件獨立與new String("OK")反例，不能聲稱我已執行測試。

這段是教師自編提示，不代表已呼叫真實 AI。

F2 選項1：三欄static後，E1第一行變為 `2/12/NOT_ENOUGH_ENERGY/false`，兩者看到相同共享資料。恢復實例欄位，再測兩物件與兩條路線。選項2：new String("OK")與常值參考不同，第一行 isSuccessful=false；改equals後true。固定任務中的常值可能讓==偶然通過，E1反例不可省略。

F3：新增setter會允許覆寫歷史，不符合需求；把三欄裝成物件能集中資料，但仍可能在錯誤時機建立、傳錯能源或結果。呼叫端與回歸測試仍有必要。

### G與H

G：宣告參考變數不等於建立物件；this指目前實例；資料欄位不static才能各自保存；add不複製物件；MissionStep保存一筆歷史、MissionLog推進任務及報表、RoverBattery封裝能源動作。H依本人實際保存與取回紀錄驗收。

## 常見失敗與最小診斷

| 現象 | 優先檢查 |
|---|---|
| 三欄仍0/null或讀取PENDING | 建構是否寫energy=energy、getter是否仍佔位 |
| 第一次物件建立後被第二次蓋掉 | 欄位是否static |
| 常值測試過，new String卻失敗 | 是否以==比較內容 |
| 日誌能源早一步 | 建立記錄是否放在switch之前 |
| {3,9,0}只剩返回或空記錄 | 是否只保存OK |
| 最低能源0但每步都大於0 | 最低值初始值是否用0 |
| 找不到main | 是否選了資料類別執行 |
| duplicate class／cannot find symbol | 同名教師檔混編，或src來源根與相依檔不在同一專案 |

## 教科書與官方來源

本專案 EPUB 目錄已核對：§3.2 Instance Variables, set Methods and get Methods；§3.3 Initializing Objects with Constructors；§3.4 Account with a Balance（取行為規則的概念）；§3.5 Primitive Types vs. Reference Types。補充§8.3控制存取、§8.4 this、§8.6預設／無參數建構子、§8.7 set/get。教材為自編，不複製教科書全文或圖，不引用未核對頁碼。

語言概念參考 Oracle 官方 Java Tutorials：[類別](https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html)、[建構子](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)、[this](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)、[存取控制](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)。這些頁面標示以JDK8撰寫，本週只取穩定的傳統class概念，不把它當新版功能清單。

IDE操作核對：[VS Code Java](https://code.visualstudio.com/docs/java/java-tutorial)、[IntelliJ Java專案與執行](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)、[IDEA內容根與來源根](https://www.jetbrains.com/help/idea/content-roots.html)。官方新版範例可能採Java25 compact source；本週維持傳統class/main及實際已安裝JDK。文件核對不能代替教室GUI實測。
