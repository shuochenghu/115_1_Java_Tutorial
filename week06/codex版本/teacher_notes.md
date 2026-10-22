# 第六週教師筆記｜從既有程式走到責任設計

本筆記為教師答案，不發成學生初版。初稿由Codex生成；課堂安排為三節各50分鐘，休息另計，尚未學生試教。實際作者驗證另見[verification](verification.md)。

## 本週定位與準備

Codex第五週只有MissionStep資料建模與RoverBattery示範，沒有完整Rover。本週先提供完整Rover，再讓學生集中寫Mission三組TODO；請勿直接把Claude版第五週的Rover、方法名稱或命令列輸入當作共同先備。SampleCase上週只有設計／審查必做，本週重新給需求與完整範例，不假定學生已實作。

| 時間 | 教師動作 | 最小可觀察成果 |
|---|---|---|
| 0–10 A | 取回、診斷、核對檔案 | 分辨目前狀態與歷史快照 |
| 10–30 B | 並排MissionStep程式與圖；學生補兩個方法 | 第一張本人圖已產生並看過 |
| 30–50 C | 先預測共享電池，再執行與解釋線／數量 | 說出欄位與參數證據 |
| 50–65 D1 | 跑RoverDemo；示範三個狀態欄位的搬移 | 理解Rover是本週新提供的類別 |
| 65–90 D2 | TODO1帶做，2模仿，3獨立 | 任務三筆快照、正確OK與最低值 |
| 90–100 D3 | 核心兩路線；補指定圖；教師示範刪停止檢查 | 圖與重構有證據；其餘路線課後 |
| 100–125 E | 初版7、AI審查8、反例7、Prompt3 | 需求與反例支持採納理由 |
| 125–135 F | 修圖5、檢核5 | puml／SVG同步與個人說明 |
| 135–150 G | 正確版保存、push、核對與新副本 | 記錄本人完成範圍，不挪用15分 |

D3時間緊時，先確認兩路線，再由教師示範停止反例；圖的未完成欄位課後補。不要壓掉E的人工初版與判讀，也不要把所有六路線的完整文字抄寫塞進10分鐘。課堂優先判斷責任與圖碼關係。

課前教師準備：

1. 檢查JDK、VS Code Java支援、IDEA的SDK與來源根，使用一般class/main，不跟著新版工具文件改成compact source或指定JDK25。
2. 從官方發行取得PlantUML jar，記錄實際版本；在教室位置測試README的UTF-8管線命令。學生不必安裝Python或Graphviz。
3. 提供三份練習puml與初始SVG。B畫完第一張立即產圖與開圖，不能只看預先匯出的教師參考圖。命令不可用時，教師以tools/render_diagrams.py接受學生puml當堂代產，學生核對本人內容；記錄操作者。
4. 課程圖原始碼／SVG隨HTML一起提供；DESIGN.md用SVG圖片，所以GitHub觀看不依賴瀏覽器擴充功能。真實GitHub操作仍須另實測。

## 知識點的教法與界線

### 類別圖是抽象與契約，不是逐行抄寫

三格依序類別名、欄位、操作。每個方法要看可見性、參數與回傳型態；建構子不填void。先讓學生讀「能否呼叫」，再補圖，避免把PlantUML語法當整堂課。顯示完整API或只選部分成員都是可用視角，但標題／note應說明省略範圍；省略的方法不能推論為不存在。本週B1參考圖列出MissionStep所有建構子，足以判斷無參數建構不存在；D圖則只要求指定部分。

### 關聯／相依的程式證據

本課程程式設計圖把持續保存的物件欄位畫關聯，把方法參數、區域變數與呼叫使用畫相依。這是本例的建模準則，不是Java型態宣告與所有UML模型的一對一定理。數字在被描述的一端；`1`要靠非null等契約維持，Java單一參考欄位本身也可能是null。

`0..*`描述允許零筆到多筆，不是某次main裡有幾筆。Lamp共用同一Battery，是物件層次的兩條實際連結，類別圖表達這種共享是允許的。

### 規則放在能維護它的類別

Rover知道目前能源／樣本，所以用execute守住成功與耗盡後的狀態；Mission知道腳本／歷史與返回，所以守住不再讀取、不再記錄。MissionLog保管輸入與格式，仍有ignored與battery這類呈現計算；不能宣稱「所有計算完全移除」。本週只改責任配置，不改既有對外輸出。

「資料在誰身上，規則就放誰身上」是起點，跨物件規則可能需要看得到多個物件的流程類別協調；不可教成每條規則都塞進資料類別。「只公開需要的」也不是public越少越好，而是公開入口要維持契約。

若日後換成另一個Java介面，可重用不依賴輸出的任務邏輯。WinForms屬於.NET，只能借學生經驗比較責任分工，不可承諾Java原碼原封移入WinForms即可執行。

### 對外只讀、final與組合（選聽）

MissionStep沿用第五週：private欄位且無setter，保存int／String值。這是本週「對外只讀快照」設計；不宣稱已講完不可變物件或繼承下的型別安全。

本週主線不增加final欄位要求。補充時可示範：`final ArrayList<String> names = new ArrayList<>(); names.add("A");`合法，但重新指派names不合法。final限制參考重新指派，不保證集合內容不變；不要把`{readOnly}`貼在多重性關係上便宣稱集合不可改。

聚合／組合不列必做。共享聚合的精確意義依模型而定；組合涉及部分最多屬於一個組合整體及其生命週期責任。建立物件的順序不是充分判準，外部持有參考也不直接等同外部擁有。Java GC不是UML刪除語意的自動保證。因此主圖使用普通關聯與多重性，若要加菱形，先補領域契約再討論，不能只看誰new。

## A–C答案

A1歷史快照；A2在MissionLog.runMission的區域變數；A3複製參考值，不複製整個物件；A4為6/5/3 SUCCESS。

B1四個呼叫依序：能（public實例方法）；不能（private欄位）；不能（getEnergy不是static）；不能（無無參數建構子）。這四題先紙上推論；若做編譯驗證，用副本且一次只測一行，別把錯誤放進正式src。

B2應補`+ getEnergy() : int`、`+ isSuccessful() : boolean`；練習圖刻意省略getCommand與getResult，需標明是部分成員圖。B3不預填學生成功；需看本人的SVG裡確實出現新增的兩個方法。

C1完整輸出：

```text
A=true
B=false
C=true
shared=2
charge=true
B again=true
shared=1
```

C2：Lamp→Battery為關聯，`private Battery battery`；Charger→Battery為相依，`refill(Battery battery)`參數；RelationDemo→Lamp為相依，main的a/b/c區域變數。C3：1恰好一個、0..1零或一、0..*零到多、1..*至少一。shared此次兩支，不代表模型上限2；要允許每支Lamp一到兩顆，改Battery那端為1..2並修改實作，不能只改圖。

## D答案與診斷

D1 RoverDemo：

```text
OK/14/2/1
STOPPED/0
STOPPED/20
```

| 類別 | 保存 | 負責 | 不負責 |
|---|---|---|---|
| Rover | energy、samples、actionCount | 行動與成功／耗盡後狀態防護 | 回放、腳本讀取、返回流程 |
| MissionStep | command、energy、result | 一步行動後的只讀資料、isSuccessful | 推進任務、輸出 |
| Mission | rover、history、returned | 執行腳本、記錄、返回、結果與摘要數值 | 終端機格式、解析輸入 |
| MissionLog | 無物件欄位，輸入是區域變數 | 預設腳本、建立物件、格式化輸出 | 直接扣電、管理歷史清單 |

TODO1完整答案在[teacher/Mission.java](teacher/Mission.java)。逐輪順序是停止檢查→讀指令→執行→建立行動後快照→0返回。拒絕、無效與0都記錄，不把new放到execute之前。TODO2以isSuccessful累加；TODO3先判空，再以第0筆初始化與迴圈比較。

D2取得內部清單後可`mission.getHistory().clear()`；本週沒有提供該方法，這是「假設提供會怎樣」的反例，不是要求正式程式能呼叫它。getStep只交單筆對外只讀資料，countCommands每次新建陣列，不會讓外部改寫history。

D3五條關係：Mission→Rover為1；Mission→MissionStep為0..*；MissionLog相依於Mission、Rover、MissionStep。後者取getStep並呼叫getter，也是型別使用的證據。完整參考見[after圖](diagrams/after.svg)，[Mission與Rover完整成員](diagrams/mission_focus.svg)供備課，不要求學生全抄。

預設完整輸出與第五週相同：

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

3/9/0的三筆result為ALREADY_FULL、UNKNOWN_COMMAND、RETURN，能源皆20；counts[1,0,0,1]，steps3/ok0/rejected2/ignored0，最低20，20/0/0 RETURNED，battery100.0。另四路線見assignment。

教師停止反例：副本刪`if (rover.isStopped()) { break; }`，跑1/1/1/1，會多一筆`step 4: command=1, result=STOPPED, energy=2`，counts[0,4,0,0]，steps4/ok3/rejected1/ignored0，最終仍2/6/3 SUCCESS。恢復後3筆、ignored1。預設1/1/2剛好在結束時用完，抓不到此錯誤，所以要選有剩餘指令的反例。

教師邊界入口（teacher單獨編譯，沿用FixedScriptMission）：

| 參數energy samples script... | 結果 |
|---|---|
| `0 0 3 1` | EXHAUSTED、0筆、最低N/A |
| `20 5 1` | SUCCESS、0筆 |
| `2 4 2` | 0能源／5樣本，SUCCESS優先 |
| `20 0` | 空腳本UNFINISHED、0筆 |

## E答案與可接受替代

先讓學生保存人工初版，再揭示[錯誤AI圖](diagrams/samplecase_ai.svg)。容量修正與整批拒絕是行為契約，方法名稱可不同但必須與需求／呼叫一致。

| 參考設計 | 理由 |
|---|---|
| private int capacity、count | 外部不能任意改寫；freeSpace可由兩者計算 |
| SampleCase(int capacity) | 建構修正到1..8，count0 |
| getCapacity/getCount/getFreeSpace回int | 查詢不改狀態 |
| boolean store(int amount) | 正數且不超過freeSpace才存；失敗不改值 |
| int unload() | 回傳原count，再清成0；空盒回0 |

[教師參考圖](diagrams/samplecase_ref.svg)在學生初版及問題表完成後才揭示。圖可用note寫前置條件、拒絕語意，不要用「每個數值約束都要新欄位」增加重複狀態。

E2至少三項：

| 問題 | 呼叫情境 |
|---|---|
| capacity/count公開 | box.count=-1可破壞範圍 |
| store回void | 呼叫端無法用boolean接收成敗；boolean ok=box.store(2)不能編譯 |
| note說直接累加且不檢查 | 容量3先存2再存2會變4 |
| 缺卸空 | box.unload找不到方法，無法取得卸下份數 |
| 缺剩餘空間／容量查詢 | 無法完成指定查詢 |
| 建構無容量修正契約 | new SampleCase(0)若依note不檢查就違反1..8；須進一步要求或檢查實作 |

最後一項不能只從「有建構子」便斷言其內部錯誤，須說明模擬note或取得程式的依據。E3只對超量邏輯作可執行實驗，並非驗證全部UML語意。

錯誤副本完整輸出：

```text
first=true, count=2
overflow=true, count=4
fill=true, count=5
unload=5
empty=0
```

恢復後第二行false/2、第三行true/3、unload3、empty0。SampleCaseDemo完整正確輸出見examples/README。再重測主任務兩路線，確認正確src未混入副本。預期值由需求計算，本人的結果仍須自己執行。

E4 Prompt參考（教師自編，未呼叫服務）：

> 請依下列SampleCase需求與我的人工初版，輸出PlantUML類別圖，再列出設計理由與不確定處。容量建構時修正到1..8，初始空盒；只接受正數且放得下的整批，拒絕不改存量並回傳成敗。可查容量、存量與剩餘空間；卸空回傳原份數，空盒回0。欄位private，方法註明參數及回傳型態，不新增任意setter。不用繼承、介面、例外處理、record或Lambda。請提出容量3先存2再存2、剛好填滿、非正數、卸空與空盒的預期，不能聲稱我已實測。我的初版：（貼本人原始碼）。

## F、G與選做答案

F1 main區域變數建立與使用Rover；F2 0..*是所有合法任務允許數量，空腳本可0、長腳本可多；F3 Rover保護成功／耗盡後狀態，Mission守腳本停止、記錄與返回；F4 getter回可變內部清單會讓外部改值；F5複製Rover會產生重複規則與修一漏一。第7週才教繼承，不要求現在改protected欄位或覆寫。

G按本人真實狀況驗收，未做就寫待補。本機CLI不代表IDE GUI、真實GitHub或課堂成功。

選做社團題：Club保管器材與借用歷史，檢查同時借出及同時未歸還上限；Loan連一個Member、一個Equipment並保存日期；Member／Equipment管理身分。Loan→Member與Equipment各1，Member／Equipment在歷史層次可連0..*筆Loan。「同時最多2筆未歸還」是需加註的行為約束，不是把所有歷史多重性寫成0..2。借、還、再借同一器材，歷史兩筆仍合理。這是紙上設計參考，未製作社團Java解答，也不宣稱已執行。

## 常見失敗

| 現象 | 優先診斷 |
|---|---|
| 日誌一直空，仍編譯成功 | run是否仍TODO；編譯成功不等於完成功能 |
| return被算拒絕 | countRejected是否排除RETURN；countOk是否完成 |
| 日誌有STOPPED | Mission停止檢查漏了；只保護Rover還不夠 |
| 歷史能源早一步 | new MissionStep是否在execute之前 |
| 最低值永遠0 | 是否用0初始化，而非先判空取第一筆 |
| 找不到main或duplicate class | 選錯資料類別；混進teacher同名檔 |
| 圖沒隨程式更新 | 修改puml後是否重新產SVG，DESIGN是否連自己的圖 |
| 多重性寫反 | 從一個起點物件讀「能連到幾個另一端」 |
| 程式與圖都像另一版本 | 檢查energy欄位名稱、建構參數順序與固定script契約 |

## 教科書與官方依據（2026-10-22核對）

本專案Deitel《Java How to Program, Early Objects》11e EPUB目錄：§1.5 Introduction to Object Technology、§3.2 Instance Variables, set Methods and get Methods、§3.3 Account Class: Initializing Objects with Constructors；§8.8 Composition與§8.11 static Class Members作補充。以實際章節引用，不猜紙本頁碼；活動與圖均自編，不複製原書圖文。

- [OMG UML 2.5.1規範](https://www.omg.org/spec/UML/2.5.1/PDF)：關聯、多重性、相依與聚合語意；生命週期需明確建模。
- [PlantUML類別圖](https://plantuml.com/class-diagram)、[Smetana](https://plantuml.com/smetana02)：文字語法、線、多重性、版面。
- [Java語言規範§4.12.4](https://docs.oracle.com/javase/specs/jls/se22/html/jls-4.html#jls-4.12.4)：final參考與物件狀態的區別。
- [VS Code Java](https://code.visualstudio.com/docs/java/java-tutorial)、[IDEA Java專案](https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html)：JDK、專案與入口。新版IDEA官方範例採Java25，不是本課程新增的版本要求。
- [Windows Forms官方介紹](https://learn.microsoft.com/en-us/dotnet/desktop/winforms/overview/)：.NET介面技術，用於經驗對照。

文件核對只支持教學依據，不能代替本次IDE／教室操作。聚合／組合與final補充的深度由課堂餘裕決定。
