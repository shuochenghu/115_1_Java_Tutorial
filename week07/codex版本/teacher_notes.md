# 第七週教師筆記｜繼承與新增能力

初稿由Codex生成。教師解答不發作學生初版。對象為已有基礎C# WinForms經驗的大二學生；認得C#不代表已會繼承。先診斷，再用短篇會員範例與既有Rover建立操作經驗。

## 定位與備課

第六週完成責任與物件合作，本週新增extends、super、protected、is-a與繼承圖。探索仍得2份，覆寫與多型留第八週。private成員不由子類別繼承，但子類別物件含父類別狀態；建構子不被繼承。不要沿用Claude版對契約外參數自動修正或命令列腳本的規則。

| 分鐘 | 活動 | 教師安排與最低成果 |
|---|---|---|
| 0–10 | A | 取回、確認九檔、診斷四類別責任與狀態 |
| 10–35 | B | 會員預測及呼叫判讀；B3連續加贈可課後補紀錄 |
| 35–50 | C | 隔離副本做缺super與private錯誤，protected同套件由教師帶測 |
| 50–60 | D1 | is-a／has-a，第一條繼承線畫完就產圖 |
| 60–72 | D2 | 學生完成spendEnergy；教師示範explore，學生獨立改scan |
| 72–78 | D3 | 子類別狀態與建構，super提供但須解釋 |
| 78–90 | D4 | 依規格獨立drill，不立即展示完整教師方法 |
| 90–100 | D5 | 十五行與核心兩路線；圖餘下成員未完可F補 |
| 100–125 | E | 預測5、執行7、兩份狀態5、修正重測5、Prompt3 |
| 125–135 | F | 修圖、檢核、記課後遷移選題 |
| 135–150 | G | 保存、push、遠端核對、新clone編譯；不可挪用 |

這是未經試教的備課配置。若實作落後，B3、E5文字與四條非核心路線課後補；不要教師代寫scan或drill來趕進度，也不把AI活動刪到只讀答案。獨立遷移課後只做一題，避免堆疊社團系統、第二份AI或完整API抄圖。

課前準備JDK、VS Code Java支援、IntelliJ來源根／SDK／main及PlantUML jar。沿用前週工具，不要求學生安裝Python、JUnit或Graphviz。本週九個學生來源檔，VS Code確認位置後示範IDEA開同份java-07；不要每題雙IDE重做。GUI指引未實測的部分需課前確認。

## A答案

A1：Rover保存目前狀態與行動規則；Mission處理腳本、停止與歷史；MissionStep保存一筆行動後快照；MissionLog輸入固定腳本並印報表。

A2：日誌為過去行動後快照，不能跟著之後充電改變。
A3：6/5/3、SUCCESS。
A4：MemberDemo及MissionLog可跑，HeavyRoverTry有DIFF；舊任務正常不等於鑽探完成。starter只是安全起點，不是錯誤答案。

## B答案與獨立練習

B1完整輸出：

```text
Member constructor: Amy
Member constructor: Bob
VipMember constructor: Bob
Amy points=12
Bob points=28
Bob bonus=1
```

B2：前兩行能編譯，分別使用繼承與新增的方法。amy.claimBonus不能，Member無該方法；bob.points不能，points是Member的private欄位。要讓每行單獨測，否則一個編譯錯誤會遮住其他判斷。

B3：全新Bob，85/10為8；兩次加贈後48點、bonusCount2。規格沒有每天或一次限制，不能自己補規格再宣稱程式錯誤。

B4：buy由Member實作，VipMember繼承它；它更新Member宣告的points。程式碼重用只是效果，應先確認VIP是一種會員且原有操作規則成立。

## C答案與語意界線

C1：沒有明寫super(name)時隱含super()，但Member只宣告Member(String)，沒有無參數建構子。JDK22診斷包含`constructor Member in class Member cannot be applied to given types`、required String、found no arguments。不要為了過編譯而隨意補無參數建構子、改掉領域需求。

C2：`points has private access in Member`。物件有資料與該程式位置可直接存取不同；用Member提供的方法，不重複宣告points，也不把欄位改protected。

C3：同套件能呼叫addPoints；在buy前額外加5後Bob最後33點、bonusCount1。測完還原。C#的protected讓宣告類別與衍生類別使用，Java另外允許同套件存取；跨套件的Java子類別存取仍有接收者限制，不把簡化表格當完整規格。本課程不安排跨套件實驗。

建構子不被繼承。父類別建構子執行不代表new第二個父物件。private成員不由子類別繼承，但物件仍含父類別宣告的狀態。對本例兩個獨立頂層類別，private僅由宣告類別程式直接存取；不擴張成所有巢狀類別情況的通則。

本課程super寫第一個敘述，方便閱讀並適用目前普通Java範例。Java25已正式放寬部分前置敘述，不做「super不在第一行必然錯」實驗。Object只簡介，不要求getClass、反射或toString覆寫。

## D答案與程式對應

D1：VipMember／Member及HeavyRover／Rover為is-a；Mission有一台Rover及多筆MissionStep，為has-a。若Mission extends Rover，mission.execute、getEnergy等變成合法，卻把任務當成車；應保留欄位合作。本週圖是程式設計的部分視角，不用菱形或完整API抄錄。

D2：參考teacher/Rover.java。spendEnergy先檢查amount<=0或amount>energy，回false且狀態不變；否則扣除並回true。探索與掃描都用相同的局部方法，成功後才加樣本、加行動。只跑行為測試無法分辨「仍保留舊寫法」與「已重構」，批改需讀explore、scan。這是結構要求，不以測試行數取代閱讀。

D2-1：boolean讓呼叫端知道是否更新成功；先扣再檢查可能在拒絕時已破壞能源。D2-2：還要確認使用指定方法、沒有繞過規則；輸出通過不是所有設計要求的證明。

D3：private drillCount，建構子super後設0，getter回該欄位。super把energy、samples交給Rover儲存，不做自動修正。不要求學生在主線從空白重寫整個Rover；課後遷移會親自再寫一個建構子。

D3-1：重複宣告會形成兩份不同欄位，getter與行動可能讀不同份，不能靠名字相同自動同步。

D4參考teacher/HeavyRover.java。先isStopped，再spendEnergy，成功後addSamples、countAction、drillCount++，最後OK。先拒絕後更新，所有拒絕狀態都不變。

D4-1預期：

| 呼叫 | 回傳 | energy/samples/actionCount/drillCount |
|---|---|---|
| drill | OK | 12/3/1/1 |
| execute(1) | OK | 6/5/2/1 |
| drill | STOPPED | 6/5/2/1 |

D4-2可接受例：從8/2鑽探，結果0/5/1/1，同時達標與耗盡；或第一次鑽探後查看getter及actionCount，抓到回OK卻沒更新的錯誤。學生須解釋反例目的，不只照搬輸出。

D5：六路線數字與assignment一致，原有15項完整輸出期望沿用第六週，另16項物件與任務規則。HeavyRoverTry完整十五行由正常、低能源、剛好、耗盡與初始達標組成。只在合法初始值內測，不引入Claude的25/-1自動修正案例。

圖參考diagrams/heavy_reference.svg：private三欄、protected三個方法、HeavyRover兩個常數／drillCount／建構子／getter／drill，三角形指Rover。父類別其他成員可省略；圖明示省略，不推論未畫的方法不存在。Mission原碼沒有出現HeavyRover，不新增這條靜態相依線。

## E答案與AI追蹤

本週模擬錯誤程式已含super，可編譯。不要把它當成缺super題的另一份重複練習。AI聲稱「private就重新宣告」不成立。

第一次drill回OK，但getEnergy／getSamples／getActionCount仍為20/0/0；只有getDrillCount為1。其後execute(1)使用父類別狀態，會得到14/2/1；子類別那份卻停在12/3。這是一個物件內的兩份同名狀態，不是兩個物件，也不是方法覆寫。嚴格語意上private欄位不被繼承，這裡稱「重複宣告造成兩份狀態」，避免把所有現象都叫覆寫或繼承。

E3參考diagrams/ai_state.svg。E4最小修正：刪energy／samples及建構子內對它們的設定；drill改用isStopped、spendEnergy、addSamples、countAction與自己的drillCount。先刪欄位直接編譯會看到private錯誤，這是引導找正確擴充方法的證據，不應改欄位可見性來壓掉錯誤。

完整AI錯誤實測DIFF與本次環境見verification；正式教師答案一直保持正確，錯誤版只在ai與暫存副本。修正後HeavyRoverTry十五行全部OK。

E5可用Prompt：

```text
請根據提供的Rover程式，新增HeavyRover子類別。
只新增drillCount與drill，不重複宣告父類別欄位，不覆寫方法。
建構子用super(energy,samples)。drill先檢查停止，再處理能源不足。
成功耗8格得3份，行動與鑽探次數各+1；拒絕時所有狀態不變。
只能透過提供的protected方法更新父類別狀態。請附上你預期的邊界與停止案例。
```

教師模擬與真實AI分開記，AI_USAGE四欄可引用LAB_REPORT，不重抄。沒有真實AI工具也能完整完成課綱活動。

## F答案

1. getter是Member／Rover的程式，存取自己宣告的狀態；子類別物件含這份狀態，但private成員不被繼承。
2. 不會。輔助方法只處理局部更新，drill入口仍要停止、成功才加樣本與計次。
3. 不會。子類別宣告自己的建構子並呼叫父類別建構子。
4. 同套件能呼叫；課程使用約定不允許在Mission或報表繞過行動流程，否則歷史、計次與停止可能不一致。
5. 仍2份；新增drill沒有改探索。覆寫與多型是第八週新內容，不能把本週教師測試當作學生已學。

F2檢查本人圖與重新產生的SVG，非教師參考圖；教師代產需據實標記。F3只記選題，獨立遷移課後完成。

## G與課後作業批改

保存包含檢查差異、commit、push、遠端核對及新clone編譯。未完成可標待補，不能冒用教師作者紀錄或把備份稱為推送。保留最後15分鐘，不把獨立遷移提前塞入此段。

CargoRover參考在teacher/transfer。cargo不是samples，load不耗能源、不增加actionCount；先停止，再檢查amount與剩餘容量。用amount>3-cargo避免先相加再判斷。預期CargoRoverTry：

```text
OK/2
REJECTED/2
OK/3
REJECTED/3
20/0/0
STOPPED/0
STOPPED/0
```

自訂題不要求同樣輸出，依學生自己寫的規格驗收：is-a成立、至少一個新狀態／方法、有super、正常／邊界／拒絕／停止及自己的案例。只換鑽探數字不算完整遷移。額外功能不是加分的必要條件，題目規模以一個子類別與一個main為限。

建議配分沿用assignment：主線35、遷移25、AI20、UML與可重現20。若學生程式測試通過卻沒完成scan重構，指出結構缺項而非捏造行為失敗。若新入口漏停止，以初始達標、零能源與達標後再次操作反例診斷。

## 常見錯誤

| 現象 | 診斷與修正 |
|---|---|
| 找不到父類別無參數建構子 | 檢查super的參數，不能只補一個不合需求的建構子 |
| energy private access | 改用提供的方法，不改欄位可見性 |
| drill回OK但getter沒有變 | 查是否另宣告同名狀態 |
| drill成功但actionCount少1 | 查成功路徑是否countAction |
| 達標後仍鑽探 | 新入口沒檢查停止，execute不會替它擋 |
| 能源不足卻變負數 | 扣能源前要拒絕，拒絕不改狀態 |
| 舊輸出都對但TODO 1未完成 | 閱讀scan／explore是否真的重構 |
| 重複類別編譯錯誤 | 正式src、ai-check、teacher分開編譯 |
| 只有SVG有改、puml沒改 | 從原始碼重新產圖，核對本人的實際變更 |

## 來源與查核

教科書以專案Deitel《Java How to Program, Early Objects》11e EPUB的實際目錄核對：§9.2 Superclasses and Subclasses、§9.3 protected Members、§9.4 Relationship Between Superclasses and Subclasses、§9.5 Constructors in Subclasses、§9.7 Designing with Composition vs. Inheritance；§9.6 Class Object僅補充。§9.4中的覆寫部分留後續週，不整節照抄。未猜紙本頁碼，也不複製教科書全文。

2026-10-28核對官方文件：

- [JLS §8.2](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.2)：private成員與建構子不被繼承。
- [JLS §6.6](https://docs.oracle.com/javase/specs/jls/se25/html/jls-6.html#jls-6.6)：同套件protected及跨套件受限存取。
- [Oracle Java25建構子說明](https://docs.oracle.com/en/java/javase/25/language/flexible-constructor-bodies.html)：super之前允許部分前置敘述，本課程統一用第一個敘述。
- [PlantUML官方發行](https://github.com/plantuml/plantuml/releases)：課前準備本機jar；本週沿用前週已備工具。

實測工具、範圍與限制見verification；不把官方文件查核說成已在Java25實機或IDE GUI驗證。
