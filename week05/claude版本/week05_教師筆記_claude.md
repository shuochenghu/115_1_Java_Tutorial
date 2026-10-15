# 第 5 週教師筆記（claude 版本）

> 本筆記初稿由 Anthropic Claude（Claude Code）生成，供授課教師備課使用；學生不需閱讀。

## 定位與銜接

| 方向 | 內容 |
|---|---|
| 承接第 4 週（claude 版） | `MissionLog` 的腳本（`args`）、三條平行日誌、`int[] state` 與具名索引、回放／計數／摘要的輸出格式、結束判斷「成功 > 耗盡 > 返回 > 未完成」 |
| 承接第 2、4 週 | 參考與箭頭圖、`==` 與 `equals`、`println` 印出 `類別@雜湊碼` |
| 承接第 1 週 | IntelliJ IDEA 基本操作與 `.gitignore`（`.idea/`、`*.iml`、`out/`） |
| 本週新增 | 類別與物件、欄位預設值、`new`、`private`／`public`、getter、建構子、`this`、預設建構子消失、`final` 欄位、實例方法與 `static` 的差別、物件清單 `ArrayList<MissionStep>`、需求轉換成設計卡 |
| 交給第 6 週 | 三個類別的責任表 → UML 類別圖；`MissionLog` 仍以 `static` 方法管流程；「能源 0 時誰負責擋下 `charge()`」的責任歸屬問題 |

**本週刻意不教**：繼承與 `toString()` 覆寫（第 7–8 週；`println(a)` 只觀察現象）、建構子過載與 `this(...)` 串接（第 10 週談過載時再補，Deitel 8.5）、`static` 成員的完整說明（只說明「`static` 方法沒有 `this`、要先有物件才能呼叫實例方法」，Deitel 8.11 留待需要時）、`enum`（8.9）、例外處理（第 12 週）、`record`、Lambda／Stream、JUnit。

**為什麼不提供 setter**：本週的設計原則是「狀態只能經由合法的操作改變」。`Battery`、`Rover`、`MissionStep` 都沒有 setter，讓學生看到 getter／setter 不是成對必備。Deitel 8.7 也提醒 set 方法要做驗證；本週直接以「行動方法」取代 setter。

## 時間配置（150 分鐘，休息另計）

這是備課配置，尚未經課堂試做。

| 分鐘 | 段落 | 教師動作 | 若進度落後 |
|---|---|---|---|
| 0–10 | 0：取回 repo、上週問題 | 投影第四週三條清單與 `state[1]++` | 不可省略取回 |
| 10–30 | A：類別與物件（`RawStep`） | 先預測再執行；黑板畫 `alias` 箭頭圖 | A3 改口頭 |
| 30–55 | B：封裝與建構子（`Battery`） | B1、B2 讓全班一起看編譯錯誤；B5 對照 C# | B4 併入課末檢核 |
| 55–65 | C：IntelliJ IDEA 開 `java-05` | 示範 Sources Root、Program arguments、Ctrl＋左鍵跳轉 | 用 VS Code 的同學可跳過 C，C1 用 VS Code 的 Outline 回答 |
| 65–105 | D：TODO 1–7 | TODO 1、3、5 帶做；2、4、6 模仿；7 巡堂；D2-2 由教師投影示範，D3-1 為選做 | 先確保 TODO 1–6 完成並通過前三條測試，TODO 7 可課後補 |
| 105–130 | E：需求轉換與 AI 除錯 | E1 個人 8 分鐘；E2 鄰座互相對照 10 分鐘；E3 當堂 7 分鐘，至少選題並寫出預測 | E3 的四筆證據可課後完成；E1、E2 是本週課綱主題，不要壓縮 |
| 130–135 | F：課末檢核 | 口頭抽問 | |
| 135–150 | G：保存與推送 | 巡查 GitHub 網頁 | **不可挪用** |

## 程式碼設計說明

| 檔案 | 角色 | 學生要寫的部分 |
|---|---|---|
| `examples/RawStep.java`＋`RawStepDemo.java` | 只有 public 欄位的「打包」，示範物件、參考、預設值、物件清單，以及缺乏保護的問題 | 無（觀察與預測） |
| `examples/Battery.java`＋`BatteryDemo.java` | 封裝的完整示範：`private`、`final`、建構子修正初始值、`this`、方法拒絕不合法操作 | 無（觀察、B1–B2 刻意改出編譯錯誤） |
| `starter/Rover.java` | `Battery` 的模式套用到探測車；`explore()` 作為範本，`execute()` 已提供 | TODO 1–2 |
| `starter/MissionStep.java` | `RawStep` 的封裝版，建立後不可變 | TODO 3、4、7（`isRejected`） |
| `starter/MissionLog.java` | 第四週流程改用物件；`toScript`、`countCommands`、結束判斷已提供 | TODO 5–7 |
| `starter/RoverTry.java` | 已完成的小測試程式，不經過 `MissionLog` 直接測 `Rover` | 無（執行並比對） |
| `teacher/` | 完整解答、`MissionLogCheck`（27 項）、延伸題 `SampleBay` 與 `SampleBayCheck` | — |

`runMission` 與第四週的差異：第四週先執行再判斷是否停止；本週在**執行前**先判斷 `isSuccess()`／`isExhausted()`。從 `new Rover(20, 0)` 出發時兩種寫法輸出完全相同；本週因為建構子可以指定初始狀態，才需要處理「一出發就已停止」：不讀指令、不記錄。

`MissionLogCheck` 涵蓋狀態與紀錄筆數、能源快照、`isRejected`（含 `new String("OK")`，抓出用 `==` 比字串的寫法）、`printSummary`，以及五條必做測試的完整輸出逐行比對（含回放與最低能源）。批改時可把它與學生的 `Rover.java`、`MissionStep.java`、`MissionLog.java` 放在同一個資料夾編譯，不要再加入教師版同名類別；未完成的 TODO 只會顯示 FAIL，不會中斷。

起始碼可直接編譯執行。因為 `Rover` 建構子是空的，欄位維持預設值 0，`runMission` 第一次檢查就判定耗盡，所以起始碼輸出 `0／0／0 EXHAUSTED`、回放為空。這是刻意保留的現象，用來接 A 段「欄位預設值」與 D1。

## 講義解答

### 0
- 三條清單：只要某處少加或多加一條，之後每一步都對錯行，而且編譯器不會發現。
- `state[1]++`：要先知道索引 1 代表樣本數；這個意義只存在於程式設計者的約定（或常數名稱）中。

### A

| 程式 | 輸出 |
|---|---|
| `new RawStep()` | `command=0, result=null, energy=0` |
| `alias.energy = 99` 之後 | `a.energy=99, a == alias? true` |
| `println(a)` | `RawStep@` 加十六進位雜湊碼（每次執行可能不同） |
| `steps.get(1).energy` | `12` |
| `bad` | `command=1, result=null, energy=-7` |

A1：類別是設計圖（有哪些欄位與方法），物件是依設計圖產生、各自擁有欄位值的實體。`new RawStep()` 產生物件；`RawStep a` 只是宣告一個可以指向物件的變數。
A2：`a` 與 `alias` 指向同一個物件（energy 99），`b` 指向另一個物件（energy 12）。
A3：物件沒有告訴 Java 該怎麼轉成文字，所以印出類別名稱與雜湊碼；陣列也一樣。自訂文字格式的正式做法（覆寫 `toString`）第 7–8 週教，本週用 `describe()` 方法代替。
A4：①任何程式都能寫入不合理的值（energy = -7）；②可能忘了設定欄位（result 仍是 null），而且編譯器不會提醒。

### B

| 程式 | 結果 |
|---|---|
| `new Battery(10, 25)` | 電量 10 |
| `new Battery(10, -3)` | 電量 0 |
| `spare.use(5)` | `false`，電量仍為 1 |
| `spare.use(-2)` | `false` |
| `spare.charge(20)` | 回傳 9，電量 10 |

B1：`error: level has private access in Battery`。
B2：`error: constructor Battery in class Battery cannot be applied to given types;`，並列出 `required: int,int`、`found: no arguments`。類別完全沒寫建構子時，編譯器才會提供無參數的預設建構子（Deitel 8.6）；`Battery` 自己寫了一個，就不再自動提供。
B3：`this.level` 是欄位，右邊的 `level` 是參數（參數遮蔽了同名欄位，Deitel 8.4）。寫成 `level = level;` 是把參數指定給自己，欄位維持預設值 0；若欄位是 `final`，編譯器會報 `variable ... might not have been initialized`（見 D3-1）。
B4：刻意的。電量只能透過 `use`／`charge` 改變，這兩個方法負責守住 0 到容量的範圍；若提供 `setLevel`，就得在裡面重寫同樣的檢查，或讓外部繞過規則。
B5：

| 想做的事 | C# | Java |
|---|---|---|
| 外部只能讀 | `public int Level { get; private set; }` | `private int level;` 加上 `public int getLevel()` |
| 欄位沒寫修飾字 | `private` | 套件存取（同一個套件的類別都能存取），**不是** private |
| 指定後不能改 | `readonly` | `final` |

常見誤解：C# 學生以為不寫修飾字就是 private。本週所有範例在 `src` 根目錄（預設套件），沒寫 `private` 的欄位其他類別都能直接存取，不會出現編譯錯誤，所以要靠習慣與 Code Review 抓出來。

### C
C1：欄位 `CAPACITY`、`TARGET_SAMPLES`（`public static final`）、`energy`、`samples`、`actionCount`（`private`）；建構子 `Rover(int, int)`；方法 `getEnergy`、`getSamples`、`getActionCount`、`explore`、`scan`、`charge`、`execute`、`isSuccess`、`isExhausted`（都是 `public`）。IntelliJ 在每個成員旁以小圖示標示存取修飾字（圖示樣式依版本而異）。
C2：IDE 依你按下 ▶ 的那個檔案（或 Run Configuration 中的 Main class）決定；命令列由 `java -cp out 類別名稱` 的類別名稱決定。

GUI 操作步驟尚未在教室電腦實際截圖驗證，選單名稱可能因 IntelliJ 版本略有差異。

### D
D1：建構子是空的，三個欄位維持 `int` 的預設值 0。`runMission` 執行第一個指令前檢查 `isExhausted()`（energy == 0），立刻停止，所以沒有任何紀錄。

D2（完成 TODO 1–2 後的 `RoverTry`）：

```text
new Rover(20, 0): 20/0/0  (expect 20/0/0) OK
new Rover(25, -3): 20/0/0  (expect 20/0/0) OK
new Rover(4, 2): 4/2/0  (expect 4/2/0) OK
r.charge() -> ALREADY_FULL  (expect ALREADY_FULL)
r.scan() -> OK  (expect OK)
r.charge() -> OK  (expect OK, 18+5 capped at 20)
r: 20/1/2  (expect 20/1/2) OK
low.scan() -> NOT_ENOUGH_ENERGY  (expect NOT_ENOUGH_ENERGY)
low.explore() -> NOT_ENOUGH_ENERGY  (expect NOT_ENOUGH_ENERGY)
low: 1/0/0  (expect 1/0/0) OK
```

D2-1：`execute` 是實例方法，透過 `rover.` 呼叫時，方法內的 `energy` 就是 `rover` 這個物件的欄位（隱含的 `this`）。資料與操作在同一個物件內，不必再把狀態傳來傳去。
D2-2（教師示範）：`error: non-static method explore() cannot be referenced from a static context`。`Rover.explore()` 以類別呼叫，沒有指定是哪一台探測車；`rover.explore()` 以物件呼叫。`static` 方法屬於類別，沒有 `this`。

D3-1（選做）：`error: variable energyAfter might not have been initialized`，位置指向建構子結尾的 `}`。`final` 讓編譯器幫忙檢查「每個欄位都恰好被設定一次」。
D3-2：一步發生後就是歷史紀錄，不應被改寫；若需要修改，代表紀錄與實際發生的事不一致。

D4 必做測試：五條的完整輸出與第四週 claude 版相同（實測見驗證紀錄）。
D4-1：避免三條清單長度不一致、索引對錯行；新增欄位時也只要改 `MissionStep` 一處。
D4-2：讀 0 個指令、記錄 0 筆，結果 `EXHAUSTED`，`ignored` 等於腳本長度，`lowest energy` 不印。

TODO 7 參考寫法見 `code/teacher/MissionLog.java`。`lowest` 的初始值用 `Rover.CAPACITY`（或第一筆的能源）都可以；只在清單非空時印出。常見錯誤：

| 錯誤 | 現象 | 診斷 |
|---|---|---|
| `step.getResult() == "OK"` | 跑任務時多半仍會得到正確計數，因為字串常值會被共用，但這是巧合；`MissionLogCheck` 以 `new String("OK")` 測試時會 FAIL | 回到第二週：字串內容比較用 `equals` |
| `lowest` 初始為 0 | `lowest energy=0` | 初始值必須不小於任何可能的能源 |
| `rejected = steps.size() - ok` | `3 9 0` 得到 `rejected=3` | 返回不算被拒絕，要用 `isRejected()` |
| 仍呼叫 `Collections.min(steps)` | 編譯錯誤（`MissionStep` 不能比大小） | 物件清單要自己決定比較哪個欄位；Comparator 第 11／13 週 |

## E 段參考答案

### E1 設計卡

| 項目 | 參考設計 |
|---|---|
| 類別名稱 | `SampleBay` |
| 欄位 | `private final int capacity`；`private int count` |
| 建構子 | `SampleBay(int capacity)`：小於 1 改為 1，大於 10 改為 10；`count` 為 0 |
| 公開方法 | `boolean store(int amount)`：amount ≤ 0 或超過剩餘空間 → 回傳 false、內容不變；`int unload()`：回傳卸下的數量並歸零；`int getCount()`；`int getFreeSpace()`；`int getCapacity()` |
| 不提供 | `setCount`、`setCapacity`：存量只能經由存入與卸下改變；容量建造時決定 |
| 必須成立的條件 | `1 <= capacity <= 10`，`0 <= count <= capacity` |

`code/teacher/SampleBay.java` 是課後選做的參考實作，`SampleBayCheck` 已實測（見驗證紀錄）。

### E2 模擬 AI 回覆的問題
可接受的答案（至少四項）：
1. 欄位是 `public`：外部可寫 `bay.count = -5` 或 `bay.capacity = 99`。
2. 只有無參數建構子，容量固定 10：無法依需求在建造時指定容量。
3. `setCount` 讓外部直接改存量，違反「不能直接改存量」。
4. `store` 沒有檢查正數與剩餘空間：`store(15)` 讓 count 超過容量，`store(-3)` 讓存量減少。
5. `store` 回傳 `void`，呼叫的人不知道有沒有存成功。
6. 缺少 `unload` 與剩餘空間查詢。

好的 Prompt 應包含：類別名稱、欄位與存取修飾字、建構子修正規則、每個方法的參數／回傳／拒絕條件、不可提供的方法、本週限制（不用例外、繼承、Lambda），以及要求 AI 附上邊界測試呼叫。重點不是 Prompt 越長越好，而是需求中的每一句都能對應到設計卡的一格。

### E3 參考答案

| # | 反例 | AI 版實際結果 | 最小修正 |
|---|---|---|---|
| 1 | `MissionLog`（無參數）或 `2 2 2 2 2` | 回放每一步能源都是 6（或都是 10）；最後一行與摘要仍正確 | `MissionStep` 改回存 `int energyAfter`，`runMission` 傳 `rover.getEnergy()`。原因：存的是同一個物件的參考，任務結束後再讀，只會讀到最後的能源 |
| 2 | `MissionLog 3 9 0` | step 1 變成 `result=OK`，`ok=1, rejected=1`，`actionCount=1` | 加回 `if (energy >= CAPACITY) return "ALREADY_FULL";`，放在 `actionCount++` 之前 |
| 3 | `RoverTry` | `new Rover(25, -3): 25/-3/0 ... DIFF` | 把三個 `this.欄位 = 參數` 移到修正之後（或修正時直接改欄位） |

三種都採「拒絕」：第 1 種看似省事，但違反「紀錄存的是當下的值」；第 2 種把「已滿」的規則刪掉了；第 3 種修正的是參數，欄位已經存了錯的值。第 3 種也可以和 `Battery` 的建構子對照：同樣先修正參數，但 `Battery` 是修正完才存入欄位。

學生選第 3 題時，`MissionLog` 的五條必做測試都會通過（`main` 只用 `new Rover(20, 0)`），這正是本題的重點：要直接測類別本身，才會發現建構子的錯誤。

## 課末檢核答案
1. 2。`b` 與 `a` 指向同一個 `Battery` 物件。
2. 不能。自己寫了建構子後，編譯器不再提供無參數建構子。
3. 可以。沒寫修飾字是套件存取，同一個套件（本週是預設套件）的類別都能存取。
4. 存數值才會保留「當下」的能源；存 `Rover` 只是存同一台車的參考，之後讀到的是最新狀態。
5. `MissionLog` → `Rover`（建立並呼叫行動）、`MissionLog` → `MissionStep`（建立並加入清單、讀取）；`Rover` 與 `MissionStep` 互不知道對方。

## 評分重點（配合作業說明）

| 項目 | 重點 |
|---|---|
| 程式 | 欄位皆為 `private`；`MissionStep` 欄位為 `final`；建構子用 `this` 且修正順序正確；`scan`／`charge` 被拒絕時狀態不變；`printSummary` 使用 `isRejected()` |
| 測試 | 五條必做＋`RoverTry` 全部 `OK`；自訂測試有預期、實際與說明 |
| 需求轉換 | 設計卡每一格能對應需求中的句子；至少四項偏差有「呼叫例子」 |
| AI 除錯 | 四筆證據為實際執行結果；能說出「為什麼預設腳本抓不到」 |
| 誠信 | 模擬建議與真實 AI 分開；能逐行解釋自己的類別 |

## 留給第 6 週

見 [week05_交接_claude.md](week05_交接_claude.md)。
