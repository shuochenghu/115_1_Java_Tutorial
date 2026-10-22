# 第六週學生實作｜用圖說清楚誰負責什麼

本講義另存當日日期目錄LAB_REPORT.md，保留A–G題號。預測先寫，再執行；教師展示記「教師示範觀察」，沒做就寫待補。圖與責任表寫在java-06/DESIGN.md，可由本講義引用，避免重抄。

## A｜取回與先備診斷（10分鐘）

依[README](README.md)建立`115_1_Java/YYYY_MM_DD/java-06/src/`，放examples六檔與starter五檔；不複製teacher。記錄本人java／javac版本：待填。複製DESIGN範本、三張練習puml及SVG。教師先準備好PlantUML jar；第一次產圖在B3完成，不能等到下課才看圖。

1. `MissionStep`保存的是目前狀態，還是某一步已發生的資料？待填。
2. 第五週Codex版的energy、samples、actionCount原本在哪個方法裡？待填。
3. `history.add(step)`會複製整個物件嗎？待填。
4. `{1,1,2}`的最終能源／樣本／成功行動數與結果：待填。

本週的問題：資料已分成物件，但目前狀態、任務執行與輸出仍混在同一個類別。先畫責任，再改程式。

## B｜讀圖與第一張圖（20分鐘）

### B1 類別圖回答什麼

類別圖描述類別的名稱、欄位、建構子、方法與關係，幫助討論「誰知道什麼、誰能做什麼」。它不表示迴圈先後順序；要看執行順序仍需程式追蹤。先開`starter/MissionStep.java`，對照教師的[參考圖](diagrams/step.svg)。本類別與第五週Codex版相同。

| 程式 | 圖上的寫法 | 要讀懂的事 |
|---|---|---|
| `private int energy;` | `- energy : int` | 名稱在前、型態在後；private |
| `public MissionStep(int command, int energy, String result)` | `+ MissionStep(command : int, energy : int, result : String)` | 建構子不寫回傳型態 |
| `public int getEnergy()` | `+ getEnergy() : int` | 無參數，回傳int |
| `static void printReplay(Mission mission)` | `~ {static} printReplay(mission : Mission) : void` | 套件存取、static |

`+`為public、`-`為private、`~`為不寫修飾字的套件存取；static用底線，PlantUML以`{static}`產生底線。圖可只列本次討論的成員，但必須標明省略範圍；本週不要求抄出四個類別的所有方法。

假設`first`已用正確建構子建立，只看MissionStep參考圖判斷：

| 呼叫 | 能否編譯／理由（先預測） |
|---|---|
| `first.getEnergy();` | 待填 |
| `first.energy = 99;` | 待填 |
| `MissionStep.getEnergy();` | 待填 |
| `new MissionStep();` | 待填 |

這是圖文推論題；本人未編譯就不能寫「已實測」。

### B2 動手畫兩個方法

打開自己`diagrams/practice.puml`，依Java補入`getEnergy`與`isSuccessful`，保留原欄位及建構子。先填：

- getEnergy的可見性／參數／回傳型態：待填。
- isSuccessful的可見性／參數／回傳型態：待填。
- 這張圖刻意省略哪些getter：待填。

`.puml`是原始碼，`.svg`是產物；不要只修改圖片。開頭的PlantUML排版設定照抄即可，排版設定不是本週必寫能力。

### B3 現在就預覽並修正

依README產圖指令，把practice.puml轉成practice.svg，直接開圖檢查：新增的兩個方法、`+`與回傳型態是否出現。DESIGN.md也用同一張SVG。

| 預覽與修正 | 本人紀錄 |
|---|---|
| 實際jar位置與產圖方式 | 待填 |
| 第一次圖裡看見什麼 | 待填 |
| 修正了哪個欄位／方法 | 待填；沒改也說理由 |
| 重新產生與開啟的結果 | 待填 |

若沒有jar，請教師當堂用維護工具協助產圖，記錄「教師代產、本人檢視」；不能把未產生的圖寫成成功。缺`@enduml`、大括號不成對時先看錯誤訊息，修正後重新產生；命令失敗時不能用先前舊SVG證明本次成功。

## C｜關係與多重性（20分鐘）

### C1 先預測共享電池

開examples的Battery、Lamp、Charger、RelationDemo，或自己src的同名檔。A與B收到同一顆8格電池，C有另一顆8格電池。先填，再編譯執行RelationDemo。

| 輸出 | 預測 | 本人實際 |
|---|---|---|
| A用6格 | 待填 | 待填 |
| B用6格 | 待填 | 待填 |
| C用6格 | 待填 | 待填 |
| shared剩幾格 | 待填 | 待填 |
| 充電是否成功 | 待填 | 待填 |
| B再用6格 | 待填 | 待填 |
| shared剩幾格 | 待填 | 待填 |

B第一次被拒絕，但C不受影響，原因：待填。這是一般電池範例，沒有主任務「能源0後結束」規則；不能拿它代替Rover任務規則。

### C2 用程式證據判斷線

![關聯與相依](diagrams/relations.svg)

| 關係 | 本週程式線索 | 畫法 |
|---|---|---|
| 關聯association | 物件以欄位保留另一個物件，形成持續的連結 | `-->`實線 |
| 相依dependency | 在方法參數、區域變數或呼叫中使用另一型別，沒有由該欄位保留連結 | `..>`虛線 |

這是本週範例的建模方式，不是Java語法對所有UML模型的一對一定律。箭頭指向被知道／被使用的型別。

| 從→到 | 關係 | 哪個欄位／參數／區域變數是證據 |
|---|---|---|
| Lamp→Battery | 待填 | 待填 |
| Charger→Battery | 待填 | 待填 |
| RelationDemo→Lamp | 待填 | 待填 |

### C3 多重性說的是允許數量

`Lamp "0..*" --> "1" Battery`：每支Lamp保留一顆Battery；一顆Battery可由零到多支Lamp使用。本例以非null電池建構；Java沒有自動替你檢查這條契約。

- `1`、`0..1`、`0..*`、`1..*`各表示：待填。
- shared這次連到幾支Lamp？這是否代表圖應寫死2？待填。
- 如果每支Lamp最多可有兩顆電池，哪一端改成`1..2`？待填；這只是設計討論，現有Java仍只存一顆，不能宣稱程式已實作。

聚合／組合本週只列教師補充。自己`new`或由外面傳入，都不足以單獨決定菱形；主作業畫普通關聯及正確多重性即可。

## D｜責任、銜接與局部重構（50分鐘）

### D1 從第五週走到Rover（15分鐘）

本週提供完整`Rover.java`，不是假設上週已寫過。先在VS Code編譯Rover與RoverDemo兩檔，執行RoverDemo。

| 操作 | 預測結果 | 本人實際 |
|---|---|---|
| 初始20/0，探索一次 | 待填：結果／energy／samples／actionCount | 待填 |
| 初始0/0，直接充電 | 待填 | 待填 |
| 初始20/5，直接探索 | 待填 | 待填 |

對照上週：energy、samples、actionCount從runMission的區域變數變成Rover欄位；探索、掃描、充電改成private，外部只透過execute。Rover在成功／耗盡後拒絕行動，回傳STOPPED。

然而「指令0返回後不再讀後面腳本」「所有已讀指令都要記錄」屬於任務流程，放在Mission。MissionLog留下固定腳本與輸出。以[DESIGN範本](DESIGN_template.md)填四個類別的責任；把Java輸出介面換成另一個Java介面時，可以保留不依賴顯示的任務邏輯。WinForms只作C#經驗對照，不能直接執行Java類別。

現在有四個任務類別合作，加入IntelliJ IDEA：開同一java-06，Project SDK選已安裝JDK，src標為Sources Root，從MissionLog的main啟動；不建立新repo、不混入teacher同名類別。選單依版本可能不同，CLI成功不能當成IDE GUI成功。

### D2 三組程式TODO（25分鐘）

只改自己`Mission.java`；其他四個starter檔已完成。每個新Mission搭配一個新Rover，只run一次；script非null，初始energy 0..20、samples非負。讀取結果前不要從其他位置操作同一Rover。

| TODO | 程式位置 | 操作順序 | 完成後應看見 |
|---|---|---|---|
| 1 | run | 先檢查rover是否停止；再讀指令、execute、new MissionStep、加入history；0也先記，再returned=true與break | 預設三筆能源14、8、6；返回／拒絕／無效也保存 |
| 2 | countOk | 模仿countCommands走訪history，用isSuccessful計數並回傳 | 預設ok=3；3/9/0路線ok=0 |
| 3 | lowestEnergy | 空清單回傳-1；非空先取第一筆，再走訪求最小 | 預設6；空資料由報表印N/A |

MissionStep的建構參數順序仍是`(command, energy, result)`，不要套用另一版本的順序。先確認一組再做下一組；starter的0、-1只是佔位，不算完成功能。

getOutcome、countCommands、countRejected與報表由教師提供。`countCommands()`每次回傳新陣列，不是把內部history交出去。為什麼不直接提供可變的`getHistory()`？寫一個拿到清單就能破壞資料的呼叫：待填。

### D3 行為比對與關係圖（10分鐘）

先測核心兩路線，每次改main的固定script後重新編譯，編譯成功才執行；記錄完整輸出，最後恢復`{1,1,2}`。

| script | 預期energy/samples/actionCount、結果 | 預期steps/ok/rejected/ignored | 本人實際 |
|---|---|---|---|
| {1,1,2} | 6/5/3 SUCCESS | 3/3/0/0 | 待貼完整輸出 |
| {3,9,0} | 20/0/0 RETURNED | 3/0/2/0 | 待貼完整輸出 |

完整預期與另外四路線見[作業說明](assignment.md)。保留第五週空日誌N/A、counts與battery格式，不能只看最後成功字樣。

打開自己的task_template.puml，只補Mission三欄、run/countOk/lowestEnergy，以及五條關係（Mission的兩條關聯、MissionLog的三條相依）。兩條關聯標出「一個Mission能連到多少Rover／MissionStep」。這是部分成員圖，不要求重抄所有API。重新產SVG並填DESIGN.md；未完成部分課後補。

教師反例觀察：在副本拿掉run的停止檢查，跑`{1,1,1,1}`，預測：待填。實際／教師示範：待填。最後狀態即使仍成功，日誌會不會多出STOPPED？為什麼Rover與Mission的檢查各有責任？待填。恢復後應回到3筆；未親自做請標明教師觀察。

## E｜人工設計、AI審查與反例（25分鐘）

### E1 先畫自己的設計（7分鐘）

本週只做一個完整AI設計活動：獨立樣本盒SampleCase，不接進探測車任務。

> 容量在建構時決定並修正到1..8，新盒為空。只接受正數且放得下的整批樣本；拒絕不改存量，呼叫端要知道成敗。能查容量、存量與剩餘空間；一次卸空並回傳份數，空盒卸空回傳0。外部不能任意改寫存量。

上週實作SampleCase是選做，本週先重新提供需求。先不看examples/SampleCase.java與教師參考圖，將自己的欄位、方法與回傳型態寫進samplecase_initial.puml。產圖、填DESIGN.md並commit人工初版，再進E2。

初版的capacity/count可見性、store回傳、unload回傳：待填。先預測容量3，存2後再存2的成敗與存量：待填。

### E2 判讀模擬AI圖（8分鐘）

以下是教師自編的錯誤AI建議，不是真實對話。不要當成標準答案。

![刻意含錯的模擬AI類別圖](diagrams/samplecase_ai.svg)

| 問題（至少3項） | 違反哪句需求 | 哪個呼叫／情境能看出問題 |
|---|---|---|
| 待填 | 待填 | 待填 |
| 待填 | 待填 | 待填 |
| 待填 | 待填 | 待填 |

圖的note已明說「直接累加、不檢查容量」，所以可以用超量反例檢驗該建議；只有欄位／方法圖本身，不能證明方法內一定正確或一定錯誤。以自己的初版與需求決定採納／拒絕，修同一份puml並重新產圖，不必重抄三份完整圖。

### E3 超量反例、最小修正與重測（7分鐘）

1. 保存自己的正確src，另複製到練習副本；只改副本SampleCase.java，其他檔案保留。
2. 先執行SampleCaseDemo，與examples/README完整輸出比較。
3. 在store方法的條件中，暫刪`|| amount > getFreeSpace()`，保留`amount <= 0`，代表接受「直接累加、不檢查超量」的一部分錯誤建議。不要改方法簽名。
4. 編譯副本，執行SampleCaseDemo；觀察第二次存入是否仍被拒絕。這個實驗只檢驗超量檢查，不代表AI圖的所有問題都已測完。
5. 恢復條件，重新編譯並跑同一測試，最後在自己的正確版重跑D3兩條主任務路線。

| 證據 | 本人紀錄 |
|---|---|
| 錯誤前預測 | 待填 |
| 錯誤副本完整輸出 | 待貼 |
| 造成錯誤的需求缺口與最小修正 | 待填 |
| 恢復後完整輸出 | 待貼 |
| D3兩條路線重測位置 | 待填 |

### E4 設計Prompt（3分鐘，細節可課後補）

寫出需求、自己的類別設計、可見性、回傳型態、拒絕時狀態、測試，以及本週限制。要求AI輸出PlantUML並說明設計理由與不確定處，不用繼承／介面／例外處理。Prompt：待填。

可使用教師模擬回覆完成，不要求AI帳號或付費服務。若真的送出Prompt，另記真實回覆與採納理由；沒有呼叫服務就不能寫「真實AI已驗證」。

## F｜修圖與課末檢核（10分鐘）

前5分鐘重新產圖，對照本次修改的Java，填DESIGN.md。後5分鐘回答：

1. MissionLog沒有保管Rover欄位，為何仍相依於Rover？待填。
2. 一個Mission對MissionStep的`0..*`，為什麼不是預設腳本的3？待填。
3. 外部呼叫execute時，Rover與Mission各守住什麼規則？待填。
4. 有public getter是否必定安全？若回傳內部可變清單會如何？待填。
5. 下週加入探索得3份的HeavyRover，複製整個Rover會產生什麼維護問題？待填。本週不實作繼承。

教師補充（選聽）：`final ArrayList`只保證參考不改指向，仍可add；不要把final理解成整個物件不能變。聚合／組合需要額外建模契約，本週不必畫菱形。

## G｜保存與取回（15分鐘）

- [ ] 正確src、main預設腳本、三份puml及重新產生的SVG、DESIGN.md、README.md齊全。
- [ ] LAB_REPORT.md與AI_USAGE.md放日期目錄；未使用AI也要保留本人驗證。
- [ ] GitHub Desktop檢查差異，不提交out、.class、.idea、*.iml或PlantUML jar；commit並push。
- [ ] 在遠端確認最新版本及DESIGN.md的SVG圖片；圖片顯示正常只證明可顯示，不證明類別圖正確。
- [ ] 在另一位置clone新副本，不刪原副本；於java-06重編譯並執行MissionLog預設路線與SampleCaseDemo。

repo／遠端位置：待填；本人commit與遠端核對：待填；新副本路徑與輸出：待填；未完成項目：待填。

15分鐘不足時先保存正確版與未完成清單，取回驗證課後補。推送失敗先保留可讀副本，再請教師協助；不能把尚未登入／推送／clone寫成成功。
