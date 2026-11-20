# 第 11 週教師筆記｜泛型與型別安全

初稿由 Codex 製作。依重新檢視第 7～10 週後的規劃製作，主線是泛型類別與型態上限。方法自己的 T 只做閱讀示範，萬用字元與型態擦除留教師備查。不要把使用過 ArrayList 當成已會宣告泛型。

## 節奏與最低成果

| 節次 | 分鐘安排 | 診斷與取捨 |
|---|---|---|
| 1 | A1 10、A2 18、A3 15、A4 3、A5 4 | 短例先講完。重複盒子只讀，不能要求學生多抄兩個舊類別；ObjectBox 對照 3 分鐘，執行期實驗可教師示範 |
| 2 | B 入口／圖 10、TODO 25、C1 10、C2 5 | 保留自己寫清單／累計及至少一個編譯反例；進度不足其餘反例教師示範 |
| 3 | D1 5、D2 8、D3 7、D4 5、E 同儕 10、保存 15 | 保留 AI 原反例、最小修正與合法入口重測，不能只讀答案；文字整理可課後補 |

150 分鐘與課後 25–35 分鐘均未試教。課前確認教室 JDK、VS Code Java 支援、IntelliJ 來源根與 main 入口。短例兩個小程式先用 VS Code，主線示範同一專案在 IDEA 的入口即可，不每題雙 IDE 重做。不要求 Maven／Gradle、Python、JUnit 或 PlantUML 安裝作為學生先備。

方法選擇先看需求：單純混放及計總量，用 `ArrayList<SampleSource>` 已足夠；需要共用群組領域操作、取回時又保留各群組型態，才採 `SampleGroup<T extends SampleSource>`。不同元素型態不構成群組之間的 is-a，不能套用第 7 週繼承理由。

## A 逐題答案

**A1** `ArrayList<MissionStep>.get` 的結果是 MissionStep，可直接呼叫該類別 getter；放 String 會在編譯被拒絕。前週重型車的 Rover 引數選詳報，SampleSource 引數選共用報表。型態資訊影響可呼叫成員與過載選擇，物件仍是同一台車。

**A2** 兩個舊盒子欄位、建構儲存、get 回傳流程相同，只有名稱及型態不同。ObjectBox 只寫一次，但 `message.get()` 的編譯期型態是 Object，必須轉型；輸出「Object 盒 訊息=採樣完成」「Object 盒 數量=7」。取消 `wrongCount` 那行仍能編譯，執行時先印兩行，再於該行 ClassCastException（String cannot be cast to Integer）；只辨識失敗時機，不教 catch。對照 Box 版的 `Integer wrong = message.get();` 在編譯就被拒絕：泛型把同類錯誤從執行期提前到編譯期，也為 D2 的 `(T)` 轉型鋪路。Box 的 T 是型態參數，`Box<String>` 的 String 是型態引數，"採樣完成" 是值。輸出「訊息=採樣完成」「數量=7」。`message.get()` 為 String，`number.get()` 為 Integer，指定給 int 時拆箱。`Box<int>` 診斷含 unexpected type、required reference；String 給 Integer 診斷含 incompatible types。不同 JDK 字句可能不同，記第一個相關錯誤即可。

**A3** 介面清單可以混放、可以用迴圈讀 getSamples，但取出只保證 SampleSource，不能直接查 getEnergy。T 的上限讓編譯器知道有介面操作，`extends` 在型態參數中同時用於類別與介面上限。這不代表 SampleGroup 是一種 SampleSource。回扣第 9 週秤重：Weighable 參考不能直接 meow，並不是貓消失了。

**A4** 見 [GenericMethodDemo](teacher/GenericMethodDemo.java)。`public static` 後、回傳 T 前的 `<T extends SampleSource>` 宣告方法自己的 T；此類別本身沒有類別 T。`SampleGroup<T>` 的 `T get(int)` 沿用類別的 T，並未宣告新的方法型態參數。範例兩個 SampleTray 讓結果直接指定給 SampleTray，印「較多樣本=9」「平手回左側=true」。不要教成泛型方法所有呼叫都必須傳兩個完全相同具體類別，混合引數的推斷本週不展開。

**A5** SampleGroup 與 SampleGroupDemo 為新增；既有 Rover、HeavyRover、SampleSource、Mission、MissionStep、SampleReport 沿用。學生入口是 java-11/src 的 SampleGroupDemo，版本依本人 java／javac 輸出，不照抄作者 22。

## B 四組 TODO 與診斷

正式完整答案在 [SampleGroup](teacher/SampleGroup.java)，學生只有這個檔案需要修改。[展示入口](starter/SampleGroupDemo.java) 由教師提供，starter 與 teacher 兩份相同。

| 題目 | 完成內容 | 常見失敗與提問 |
|---|---|---|
| TODO 1 | private ArrayList<T> sources；建構子建立 new ArrayList<>() | 若 static 共用，兩個新群組為何互相影響？若沒初始化，第一次 add 會失敗；本週不要求 catch |
| TODO 2 | sources.add(source) | 是否重新 new 了來源？是否執行行動？應保存原參考 |
| TODO 3 | get 回 sources.get(index)，size 回 sources.size() | T 不改 Object，不轉型；size 不是總樣本數 |
| TODO 4 | 類別加 `<T extends SampleSource>`，total 從 0 開始，普通迴圈累加 source.getSamples | 無上限 T 不保證 getSamples；警告不能用轉型壓掉。foreach 每次讀目前數量，不快取加入時的數字 |

TODO 1–3 仍能編譯無上限的群組。TODO 4 第 1 步直接在學生自己的 `SampleGroup<T>` 寫迴圈呼叫 getSamples，編譯器在 `source.getSamples()` 報 cannot find symbol；第 2 步補上上限就通過。不需另建副本，診斷也直接連到自己的程式。不預先替學生把上限寫好。

兩種九行展示抓不到的寫法要特別留意（教師工具已重現）：

| 寫法 | 九行展示 | 抓到的位置 |
|---|---|---|
| `add` 時先 `cached += source.getSamples()`，`totalSamples` 回傳 cached | 全部正確 | C1 的 LiveTotalTry：探索後總和仍 0，車上樣本 3 |
| 保留 `<T>`，在迴圈寫 `((SampleSource) source).getSamples()` | 全部正確，`-Xlint:all` 也沒有警告 | C2 第 2 題：`SampleGroup<String>` 竟能編譯，執行時才 ClassCastException |

第二種是學生或 AI 常給的「繞過上限」寫法：從 T 轉成介面不屬於 unchecked 轉型，所以編譯器不提醒。講義因此要求 C2 第 2 題用學生自己的群組實測。

示範入口分兩段檢查。第一段比筆數，再用短路邏輯比第 0 筆是否為原物件，失敗印「TODO 1～3（保存與取回）還沒通過」；第二段比三個固定總量，失敗印「TODO 1～3 已能保存與取回，TODO 4 總樣本數還沒通過」，避免佔位 0 被當成完成。這個保護不等於完整測試，改 Demo 的數字也不能證明群組正確。正式交付的起始版無警告可編譯，這裡沒有把例外處理混入 TODO。

## C 完整結果、圖與反例

C1 的九行見 [學生講義](student_lab.md)。關鍵是同一台 heavy 被放進兩個獨立清單：vehicles.get(0) 為 HeavyRover，選 `print(String, Rover)`；mixed.get(0) 為 SampleSource，選介面版。兩次都讀原物件，能源仍 8、樣本 6、行動 2、鑽探 0、日誌 2 步。泛型不會重新執行探索，也不會把實際物件轉成另一種車。

自選數量 11、2 應為筆數 2／總量 13，可抓寫死 10、誤回 size；同一個 4 份盒加入兩次應為 2／8，可抓偷偷去重。空群組的 0 來自累加初始值，不呼叫 get(0)。

參考圖兩個群組各持有自己的清單，清單第一筆同指一台 HeavyRover。MissionStep 的 energy 則是建立時保存的 int 快照，不會隨之後行動改變。講義 C1 的固定自測 LiveTotalTry 用一台**沒有交給 Mission**的新車，先加入群組再 `execute(1)` 探索，應印「加入後 總樣本數=0」「探索後 總樣本數=3 車上樣本數=3」，讓學生親自觀察查詢讀目前值，並抓出快取總和的寫法。學生在 experiments 以 `-cp out` 編譯，執行時類別路徑為 `"out;out-exp"`。不要在讀任務結果時操作任務同一車。

| C2 | 預期與理由 |
|---|---|
| 樣本盒加進重型車群組 | cannot be converted to HeavyRover；型態引數已固定接受重型車 |
| SampleGroup<String> | not within bounds；String 沒有實作 SampleSource |
| 重型車群組指定給來源群組 | incompatible types；若允許就能經後者加入樣本盒，破壞前者承諾 |

另可備查：`HeavyRover r = mixed.get(0)` 也不直接通過，取出只保證介面。需要混合資料時從一開始宣告 `SampleGroup<SampleSource>`，不靠轉型解套。

## D AI 審查與最小修正

D1 保存人工完成碼及預測。規格要包含元素型態、取回保證、空資料及不改來源，避免只寫「幫我做一個泛型類別」。

D2 [模擬錯誤](ai/SampleGroup.java) 把 add 參數放寬成 SampleSource，再做未檢查的 `(T)`。合法 Demo 仍過，因此「只有合法例」不能排除漏洞。加進 SampleTray 時繞過 HeavyRover 的限制，存入的物件依然是樣本盒；UnsafeGroupTry 指定取出結果給 HeavyRover 時才有 ClassCastException。記行號以本人副本為準，不硬訂同一行號。

D3 最小修正兩處：參數型態 `SampleSource` 改為 `T`，`sources.add((T) source)` 改為 `sources.add(source)`。保留其他方法。先重新編譯同一份 UnsafeGroupTry，應在 add 呼叫行拒絕；不要執行 out-bad 的舊 class。移開非法來源檔後另編譯到 out-fixed，合法 Demo 九行一致且無警告。教師工具會驗證這個完整順序，不能拿整份教師解答覆蓋副本冒充最小修改。

D4 第 10 週已說過依需求判讀，instanceof 或轉型本身並非一律錯誤。本例違反的是「重型車群組保存重型車」；真的要混合就使用 SampleSource 型態引數。泛型無法自動保證非 null、合法索引、非負資料、來源不可變或並行安全，這些仍依本週契約。本活動只讀懂失敗，不教 try/catch 或 SuppressWarnings。

進度落後時先確保 D3 的負向編譯與合法重測完成，再補 D4 的文字，不犧牲最後保存時段。教師模擬與真實 AI 分開揭露，沒有真實 AI 也可完整完成。

## E、作業與批改

E 檢查學生能否指出 T、add/get、兩種 print，以及同一參考。GitHub Desktop 依本人操作記差異、commit、push、遠端核對與新位置取回編譯；作者 CLI 證據不能當學生完成證據。

作業完整答案在 [homework/SampleGroup](teacher/homework/SampleGroup.java) 與 [MostSamplesDemo](teacher/homework/MostSamplesDemo.java)。非空時先選第一筆，遇到嚴格較大 `>` 才替換，平手不替換。回傳 best 原參考，不重建物件、不改回 SampleSource。六行參考輸出：

```text
最大樣本數=9
平手先加入=true
最大來源能源=18
原車狀態=18/4/0
單筆零樣本=0
空群組：略過最大值查詢
```

資料與標籤可不同，最低證據依 assignment 判定。零樣本有一筆與空群組是不同案例；最大值不放第一筆，平手使用兩個不同物件，才測得出「只回第一筆」「平手回最後筆」「回複製物件」的錯。查詢前後要看原參考與狀態，不能只比最大數字。

作業編譯反例改為回傳型態：`SampleGroup<SampleSource> g` 的 `SampleTray t = g.findMostSamples();` 應報 incompatible types: SampleSource cannot be converted to SampleTray。重點是 findMostSamples 回傳 T，混合群組的 T 只保證 SampleSource；這與課堂 C2 第 1 題的錯誤元素不同，檢驗的是作業本身的回傳設計。評分表的「寫出型態引數」指不要寫成沒有 `<>` 的 `SampleGroup`，這種寫法會讓編譯器停止檢查元素型態；一般編譯只出現「uses unchecked or unsafe operations」的 Note，加 `-Xlint:unchecked` 才看到逐行警告。

## 教師備查與來源

`? extends SampleSource` 可供讀取多種元素型態的清單，但不代表不可變，仍不在學生必做。擦除可能使兩個僅型態引數不同的 ArrayList 過載發生簽名衝突，屬第 10 週的延伸對照，不列本週編程要求。

教科書依專案 Deitel 11e EPUB 實際目錄：§20.6 Generic Classes 為主線，§20.2–20.4 泛型方法為閱讀，§16.3–16.4 包裝／裝拆箱僅補充，§20.5、§20.7 留延伸。不猜紙本頁碼。

語言規則依據：[JLS 型態變數與上限](https://docs.oracle.com/javase/specs/jls/se25/html/jls-4.html#jls-4.4)、[泛型類別](https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-8.1.2)、[未檢查縮小轉換](https://docs.oracle.com/javase/specs/jls/se25/html/jls-5.html#jls-5.1.6.2)、[Oracle 泛型與繼承](https://docs.oracle.com/javase/tutorial/java/generics/inheritance.html)、[泛型方法](https://docs.oracle.com/javase/tutorial/java/generics/methods.html)。語言文件版本不等於課堂 JDK 要求；實際環境與界線見 verification。
