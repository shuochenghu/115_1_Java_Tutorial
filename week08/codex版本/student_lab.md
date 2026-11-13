# 第八週學生實作｜覆寫、多型與AI審查

初稿由Codex生成。另存為當日LAB_REPORT.md，保留A–G題號；先填預測再執行，缺項標待補。設計與圖放java-08/DESIGN.md，不重複抄寫。

## A｜取回與診斷（10分鐘）

依README建立java-08，src先放examples三檔與starter七檔。取回兩份範本及dispatch_practice.puml／SVG；保留前週資料，不複製teacher。

A1. 記實際java／javac版本，指出本週四個main入口及工作目錄：待填。

A2. 不跑程式，說明第七週HeavyRover.execute(1)會取得幾份樣本，新增drill為何沒有改變它：待填。

A3. Rover、Mission、MissionStep各負責什麼？目前狀態與歷史快照有何差別？待填。

A4. 第七週子類別能否直接讀寫Rover.private samples？protected是否只有子類別能存取？待填。

## B｜會員覆寫（20分鐘）

開src/Member.java、VipMember.java、MemberDemo.java。先讀程式，勿先執行。describe是新增的公開實例方法；父、子方法的名稱、參數與回傳型態在本例完全一致。

B1（7分）. 預測建立物件的三行訊息，以及三次describe結果，才執行MemberDemo：

| 呼叫／階段 | 預測 | 實際 |
|---|---|---|
| 建構訊息及先後 | 待填 | 待填 |
| amy.describe() | 待填 | 待填 |
| bob.describe() | 待填 | 待填 |
| memberView.describe() | 待填 | 待填 |

B2（5分）. 以自己的話說明@Override的用途。把子類別方法名稱改成describeVIP且保留@Override，預測編譯結果；在experiments副本單獨編譯驗證後還原。只需保留第一個有意義的診斷，不抄整頁：待填。

B3（8分）. 在experiments複製會員三檔，獨立將VIP說明改為「沿用父類別describe，再加上 / VIP」。教師先介紹super.describe()語法，先不展示完整return。父方法不改，購買與領點也不改。先寫三行預期，再修改、編譯與執行；說明super呼叫有沒有新增另一個物件：待填。

在experiments內分開編譯，不把副本混入src：

```powershell
javac -encoding UTF-8 -d out *.java
java -cp out MemberDemo
```

## C｜參考型態與方法呼叫（20分鐘）

C1（8分）. 對照main中bob與memberView：

| 變數 | 宣告型態 | 指向的實際物件類別 | 可否呼叫describe | 執行哪個describe |
|---|---|---|---|---|
| bob | 待填 | 待填 | 待填 | 待填 |
| memberView | 待填 | 待填 | 待填 | 待填 |

memberView = bob這個指定會複製另一份點數嗎？待填。

C2（7分）. 在新的experiments副本，每次只做一項變動，先判斷再編譯，記錄後還原：

| 單獨實驗 | 能否編譯／理由 | 實際 |
|---|---|---|
| memberView.claimBonus(); | 待填 | 待填 |
| 在Member型態參考呼叫describe() | 待填 | 待填 |
| 把VIP describe改為describe(int amount)，保留@Override | 待填 | 待填 |

本題只用參數錯誤辨識「這不是題目要求的覆寫」；過載的完整規則留第十週。

C3（5分）. 畫bob與memberView指向同一個VipMember物件。寫出編譯判斷與執行時選擇各看什麼；口頭解釋後再填：待填。

## D｜探測車主線（50分鐘）

### D1 規格與追蹤（10分鐘）

一般車探索得2份；重型車得3份。兩者探索成本6、成功行動+1。產量方法只回答數量，停止、能源與更新由既有流程處理。

畫出本次execute(1)依序進入哪些方法，標出哪個仍是private、哪個會依實際物件選擇版本：待填。

D1-1. 為何不能只在HeavyRover新增同名private explore，就宣稱已改變父類別execute的探索行為？先記目前想法，E再驗證：待填。

### D2 TODO 1：讓流程使用產量方法（8分鐘）

開src/Rover.java。protected int exploreYield()回2已提供。自行修改private explore中的樣本增加來源；扣能源、拒絕、行動計次與回傳值保留。不可改成從main直接加樣本。

檢查點：重編譯、執行MissionLog，預設{1,1,2}仍為6/5/3、任務成功。執行HeavyRoverTry仍有兩行不符是合理的，因為TODO 2尚未完成。記實際：待填。

### D3 TODO 2：獨立完成HeavyRover覆寫（9分鐘）

自行寫出方法宣告與@Override，名稱、參數與回傳型態對照父類別，保留protected，固定回3。不能增加欄位、扣能源或多加一次行動；不改private explore、execute及drill。

完成後執行HeavyRoverTry，貼十五行完整輸出，應全成功。若失敗，先找哪個狀態不同，再看程式：待填。

追蹤新HeavyRover(20,0)連續drill、execute(1)、drill：

| 呼叫 | 回傳預測／實際 | energy | samples | actionCount | drillCount |
|---|---|---|---|---|---|
| drill() | 待填 | 待填 | 待填 | 待填 | 待填 |
| execute(1) | 待填 | 待填 | 待填 | 待填 | 待填 |
| drill() | 待填 | 待填 | 待填 | 待填 | 待填 |

### D4 TODO 3：兩組任務（8分鐘）

開src/CompareMissions.java。show只是已提供的報表，不會建立任務、執行行動或判斷車種。自行完成main：

1. 兩個參考都宣告為Rover，分別指向new Rover(20,0)與new HeavyRover(20,0)。
2. 每台車各交給一個新Mission，分別run同一份script一次。
3. 各呼叫show，標籤用「一般探測車」與「重型探測車」，傳入相應車、任務及script.length。

不加入if車種判斷，不改Mission，不把同一台車交給兩個任務。先預測兩段結果再執行：待填。

### D5 比較與本人反例（15分鐘）

貼CompareMissions兩段完整輸出，解釋兩個差異：為何重型探測車少一筆掃描、為何最低能源與battery不同？待填。

另在experiments建立自訂測試入口，沿用完成的Rover、HeavyRover、Mission、MissionStep；每次新車、新任務。先預測再實測，記回傳或outcome及全部三個狀態，不必重寫報表。

| 案例 | 一般車預測／實際 | 重型車預測／實際 |
|---|---|---|
| 6格、2份，腳本{1,3} | 待填 | 待填 |
| 5格、0份，腳本{1} | 待填 | 待填 |
| 自己設計一個可抓錯的案例 | 待填 | 待填 |

自己案例要寫「能抓到何種錯誤」，例如覆寫多計次、停止後仍讀指令或產量方法從未被呼叫；不能只有換數字與貼成功。

## E｜AI Code Review（25分鐘）

來源：教師設計的模擬回覆，不是真實AI對話。需求：「保留Rover的探索流程，讓HeavyRover每次探索得3份，成本與停止規則不變。」模擬AI聲稱：「在子類別加同名private explore就會自動替換父方法。」原文見ai/HeavyRover.java。

建立java-08/ai-check，複製**自己完成的Rover.java**、提供的HeavyRoverTry.java及模擬AI HeavyRover.java；只有三檔，不放正式src。進入ai-check後用javac -encoding UTF-8 -d out *.java編譯，成功才執行java -cp out HeavyRoverTry。

E1（5分）. 不跑程式，先預測能否編譯及鑽探後探索的samples。用commit保存本人D段完成碼及預測：待填。

E2（6分）. 保存[AI]原文後執行，貼兩行不符及其上下文。它能編譯是否已滿足需求？請指出execute實際走哪個private explore、又呼叫哪個產量方法：待填。

E3（4分）. 在ai-check副本的private explore前加@Override，只編譯，記診斷，然後還原。再檢查「把父類別產量方法改static」是否符合本週實例覆寫的設計，文字判讀即可：待填。

E4（7分）. 移除無效的子類別private explore，改在正確產量方法覆寫；不用重寫execute、不改父類別private狀態或drill。重編譯，用相同入口重測十五行都顯示「相符」，記最小修改並以[fix] commit保存：待填。

E5（3分）. 用三至五句補強Prompt，包含可覆寫方法的精確宣告、禁止改狀態與預期案例。AI_USAGE記教師模擬來源，若另用真實AI再分開揭露：待填。

進度落後可課後補E5文字，保留E4重測時間。

## F｜UML與課末檢核（10分鐘）

F1. 在自己的dispatch_practice.puml補Rover與HeavyRover的產量方法，保留private explore；HeavyRover方法旁用註記標示覆寫。箭頭仍指Rover，Mission欄位仍為Rover。建立HeavyRover物件不代表Mission原始碼需新增對HeavyRover的關聯。

從puml重新產SVG，立即開啟檢視，DESIGN引用自己的SVG，記產圖方式與實際檢視結果：待填。圖為部分視角，無須抄完整API。

F2. 各用一句回答：

1. 父類別參考是否把重型車變成一般車？待填。
2. 兩個參考型態不同，能否仍指向同一個物件？待填。
3. 為何private explore可保留，重型車仍能改變產量？待填。
4. 產量覆寫為何不該另加一次actionCount？待填。
5. 如果新車種都被迫重寫execute，有什麼維護成本？待填。

F3. 課後遷移選題與探索產量規則，記在DESIGN；不用課堂完成第二個子類別：待填。

## G｜保存與取回（15分鐘）

- [ ] 正式src重編譯；MemberDemo、MissionLog、HeavyRoverTry、CompareMissions符合各自規格。
- [ ] README說明main入口、JDK及完成度；puml與SVG同步；缺項據實標示。
- [ ] GitHub Desktop檢查差異、commit、push，在GitHub核對程式、AI修正與SVG。
- [ ] 在原repo以外的新位置clone，保留原副本；編譯java-08/src，執行CompareMissions。
- [ ] ai-check單獨編譯與重測；排除out、class、IDE暫存及憑證。

記commit、遠端核對、新clone位置與結果：待填。無法push時先另存完整repo備份並確認能開啟，記失敗原因；備份不能寫成遠端成功。
