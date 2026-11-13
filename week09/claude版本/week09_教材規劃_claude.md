# 第 9 週教材規劃（claude 版本）

> 本規劃初稿由 Anthropic Claude（Claude Code）撰寫，供教師在製作第 9 週 claude 版教材前確認方向。**這是規劃，不是已完成的教材**：本目錄目前只有本檔；下文的檔名、TODO 編號、輸出格式都是提案，正式製作時再驗證與調整。Codex 版若有製作，放在 `week09/codex版本/`，兩版獨立。

## 0. 前提：第 8 週 claude 版尚未製作

截至 2026-11-10，`week08/claude版本/` 只有 `week08_教材規劃_claude.md`，教材與程式尚未製作（第 8 週 Codex 版已完成，但兩版獨立，不拿 Codex 版當 claude 版的先備）。本規劃**假設第 8 週 claude 版依其規劃 §4 完成**，即：

- `Rover` 有 `protected int exploreYield()`（回傳 2），`private explore()` 改成 `addSamples(exploreYield())`；`Rover`、`HeavyRover` 都覆寫 `toString`。
- `HeavyRover` 以 `@Override` 讓 `exploreYield()` 回傳 3；`drill()` 沿用第 7 週。
- `Mission`、`MissionStep`、`MissionLog` 與第 7 週相同。
- 社團器材：`Equipment` 有預設 `checklist()`（回傳 `getLabel()`），`Camera`、`Projector` 覆寫並用 `super.checklist()`；第 8 週反思題問「能不能禁止 `new Equipment(...)`」。

若第 8 週製作時改了上述任何一點（尤其第 8 週規劃 §10 三項待決事項），本規劃 §4 要跟著調整。**建議先完成第 8 週 claude 版，再依本規劃製作第 9 週。**

## 1. 定位

| 項目 | 內容 |
|---|---|
| 課綱主題 | 抽象類別與介面（含巢狀類別、匿名類別概念）：Prompt 設計練習與 AI 協作介面設計 |
| 日期 | 115 學年度第 1 學期第 9 週（11/08～11/14）；學生資料夾 `115_1_Java/YYYY_MM_DD/java-09/`，日期以實際上課日替換 |
| 承接 | 第 8 週 claude 版（見 §0；交接檔製作後為 `week08/claude版本/week08_交接_claude.md`） |
| 下一週 | 第 10 週：過載與多型——AI 生成多型程式碼比較與除錯 |
| 參考書 | Deitel《Java How to Program, Early Objects》11e：10.4（抽象類別與方法）、10.5.1（抽象父類別 `Employee`）、10.7（`final` 方法與類別）、10.9（建立與使用介面；10.9.1–10.9.5 的 `Payable` 讓不相關的 `Invoice` 與 `Employee` 一起被處理）、10.10.1（`default` 方法與「介面 vs. 抽象類別」，只當閱讀）、10.13（針對介面寫程式；教師參考）、12.5.5（`TipCalculatorController` 中「Using an Anonymous Inner Class for Event Handling」，只取匿名類別概念，不教 JavaFX）。章節已用本專案 EPUB 目錄核對；不寫頁碼 |
| 時間 | 沿用三節各 50 分鐘（休息另計）的備課配置，最後 15 分鐘保存推送；尚未試教 |

## 2. 先備能力（第 8 週規劃預定的交接）

可承接（前提是第 8 週依規劃完成）：

- 覆寫：同名、同參數、相容回傳；`@Override` 讓編譯器檢查；覆寫不能縮小存取範圍。
- `super.方法()` 沿用父類別版本；覆寫 `toString`。
- 多型基礎：父類別型態變數指向子類別物件；能呼叫什麼看變數型態，執行哪個版本看物件本身。
- `Mission` 一行不改，傳入 `HeavyRover` 探索就得 3 份。
- 依審查清單 Code Review 子類別，意見附編譯訊息或測試輸出。

開場要診斷、不能假設已熟練：

1. `Rover r = new HeavyRover(20, 0); r.execute(1);` 得幾份？`r.drill()` 能編譯嗎？（變數型態 vs 物件型態）
2. 第 8 週 Code Review 的「覆寫 `execute` 繞過停止檢查」為什麼能編譯？（本週用 `final` 回答）
3. 第 8 週反思題：「還沒分類的器材」的 `checklist()` 該回傳什麼？（本週用抽象方法回答）

## 3. 本週新增能力與邊界

### 學生在本週結束時應能

| # | 能力 | 主要證據（提案） |
|---|---|---|
| 1 | 說明抽象類別：不能 `new`，但可以當變數型態；可以有欄位、建構子與一般方法 | 講義 A1–A2；`Rover is abstract; cannot be instantiated` |
| 2 | 宣告抽象方法，說明「子類別一定要實作，否則自己也得是抽象類別」 | 講義 A3、TODO 1–2；`is not abstract and does not override abstract method` |
| 3 | 用 `final` 方法保護父類別的流程，讀懂 `overridden method is final` | 講義 B2、TODO 3 |
| 4 | 宣告介面、用 `implements` 實作；介面方法實作時必須 `public` | 講義 C、TODO 4 |
| 5 | 說明何時用抽象類別、何時用介面：同一家族共享狀態與流程 → 抽象類別；不同家族要遵守同一組操作 → 介面；一個類別只能 `extends` 一個，但可 `implements` 多個 | 講義 C4、作業設計文件 |
| 6 | 用匿名類別就地實作一個介面，並說出它也是一個類別（`ListenerDemo$1.class`） | 講義 D |
| 7 | 知道巢狀類別的存在與基本分類（概念層級），不要求自己設計 | 講義 D3 |
| 8 | 為介面設計寫出結構化 Prompt，比較模糊與具體 Prompt 的 AI 回覆，用介面審查清單判斷採納與否 | 講義 E、`AI_USAGE.md` |
| 9 | C# 對照：`abstract` 用法相同；`implements` ↔ `:`；Java 介面方法實作必須 `public`；Java 匿名類別 ↔ C# 事件處理常寫的委派／事件處理方法 | 講義 C5、D2 |

### 本週刻意不教（避免提前負擔）

- Lambda：第 13 週。匿名類別示範時只說「第 13 週會看到更短的寫法」，不提前寫 `->`。
- 介面的 `default`、`static`、`private` 方法（Java 8／9 起）：AI 很常生成，因此放在 Code Review 清單「本週先不要用」並在教師筆記說明；學生不需要自己寫。
- 介面常數（介面欄位自動為 `public static final`）：只在審查清單提到「介面不放狀態」，不示範常數介面。
- 過載的完整規則：第 10 週。
- 泛型介面（如 `Comparable<T>`）：第 11 週。本週 `ArrayList<型態>` 只當容器。
- `instanceof`／向下轉型：沿用第 8 週規劃，主線不需要；`instanceof` 長鏈列為審查清單的壞味道。
- 內部類別（非 static）存取外部實例欄位、`Outer.this`、區域類別：只在教師筆記列出名稱，學生只需知道「類別裡可以再寫類別」。

## 4. 主線設計決定

### 4.1 探測車：`Rover` 成為抽象類別

第 8 週留下的掛鉤 `exploreYield()` 在 `Rover` 裡回傳 2，但「普通探測車」其實只是另一種探測車。本週：

```text
«abstract» Rover
  - energy, samples, actionCount              （不變）
  + execute(command) : String  {final}        行動流程與停止檢查只寫在這裡，子類別不能覆寫
  # exploreYield() : int  {abstract}          每種探測車都必須決定探索得幾份
  + toString() : String                       （第 8 週）
  其餘方法不變

StandardRover extends Rover                   新增：exploreYield() 回傳 2
HeavyRover extends Rover                      不變：exploreYield() 回傳 3、drill()、toString()
```

理由：

- **回答第 8 週的問題**：`Rover` 不再提供「預設 2 份」——這個數字屬於標準探測車，不屬於所有探測車。抽象方法讓「忘記決定產量」變成編譯錯誤，而不是悄悄沿用 2。
- **`final execute` 回收第 8 週 Code Review**：第 8 週模擬 AI 覆寫 `execute`、繞過停止檢查卻能編譯；本週加上 `final` 後，同一份 AI 寫法會得到 `overridden method is final`。讓學生看到「規則寫在父類別」之外，還能「禁止子類別改流程」。
- **`Mission` 的欄位型態仍是 `Rover`**：抽象類別不能 `new`，卻能當變數與欄位型態——這是本週最重要的觀念，`Mission` 不用改型態就是現場證據。
- 類別名維持 `Rover`，不改成 `AbstractRover`：`Mission` 與前幾週文件都用 `Rover`，改名的成本大於好處。

唯一的外部影響：`MissionLog` 的 `new Rover(20, 0)` 改成 `new StandardRover(20, 0)`。

### 4.2 介面：任務每一步的「通知對象」`StepListener`

本週介面要回答：**`Mission` 想在每一步執行完時通知「某個人」，但不想知道那個人是誰。** 這和學生熟悉的 C# WinForms 事件很像（按鈕不知道誰處理 `Click`）。

```text
«interface» StepListener
  + onStep(stepNumber : int, step : MissionStep) : void

ConsoleListener implements StepListener       每步印一行
LowEnergyAlarm implements StepListener        執行後能源 ≤ 門檻就警告並計數（有自己的欄位）
（匿名類別）                                    只在示範中就地實作：遇到被拒的步驟才印

Mission
  - listener : StepListener                   新增；沒設定時為 null，不通知任何人
  + setStepListener(listener)                 新增
  + run(script)                               每記一步後，若 listener != null 就呼叫 onStep
```

理由與取捨：

- **為什麼用介面不用抽象類別**：`ConsoleListener` 和 `LowEnergyAlarm` 不是同一家族、沒有共享欄位或流程，只要求「有 `onStep` 這個操作」。這正好和 4.1 的 `Rover`（共享能源規則與流程）形成對照。
- **不用多載**：不新增 `run(int[], StepListener)`，改用 setter，避免提前碰第 10 週的多載。
- **`Mission` 的修改是加法**：沒設定 listener 時行為完全不變。原型已確認 `MissionLog` 輸出與第 7 週逐字相同（§6）。
- **替代方案（未採用）**：讓 `Mission` 接受「任何可被任務驅動的單位」介面（如無人機）。`Mission` 用到 `Rover` 的五個方法，新的實作類別得自己寫整套能源規則，份量太大，且與 4.1 的抽象類別主題重疊。若教師希望走這條，列入 §10 待決。

### 4.3 匿名類別與巢狀類別（概念）

- 匿名類別：`ListenerDemo` 裡用 `new StepListener() { ... }` 就地實作一個「只用一次」的通知對象。編譯後 `out/` 多出 `ListenerDemo$1.class`，用來證明匿名類別仍是類別、只是沒有名字。
- 對照 C#：WinForms 設計工具產生 `button1.Click += button1_Click;` 加一個處理方法；Java 在 Lambda 之前常用「匿名類別實作 listener 介面」做同樣的事（Deitel 12.5.5 的 JavaFX 例子即是），不要求學生學 JavaFX。
- 巢狀類別：一張投影片＋講義一題預測。示範把 `LowEnergyAlarm` 改寫成 `ListenerDemo` 裡的 `private static class`，編譯出 `ListenerDemo$LowEnergyAlarm.class`；說明「只有這個類別用得到時可以藏在裡面」。學生不需要自己改寫。

### 4.4 AI 協作：介面設計的 Prompt 練習（課綱主題）

情境沿用社團器材（作業短篇），讓學生**先自己設計，再用 Prompt 請 AI 設計，最後審查**：

> 需求：社團除了器材（相機、投影機、麥克風），也開放預約社辦。預約看板要用同一個迴圈列出所有可預約項目的標籤與最長借用時數。

流程：

1. **人工初版**（5 分鐘）：自己寫出介面名稱與方法（只寫簽章）、哪些類別要實作。
2. **Prompt v1（模糊）**：教師提供，例如「幫我設計社團預約系統的介面」。
3. **模擬 AI 回覆 A**（教師撰寫，清楚標示「模擬」）：用抽象類別 `BookableItem` 放 `id`、`name` 欄位，再讓 `Room extends Equipment`——違反 is-a，也把狀態放進共同型態。
4. **Prompt v2（結構化）**：學生用欄位表撰寫（沿用第 4 週 F1 的表格形式，換成介面設計欄位）：

   | 欄位 | 要寫什麼 |
   |---|---|
   | 現有類別 | `Equipment`（抽象）、`Camera`、`Projector`、`Microphone`；新的 `Room` |
   | 誰要遵守 | 哪些類別、它們是否同一家族 |
   | 只需要的操作 | 看板真正會呼叫的方法 |
   | 不要的東西 | 不放欄位、不用 `default`／`static` 方法、不改既有父類別 |
   | 語法限制 | 本週未教 Lambda、泛型介面、`instanceof` |
   | 輸出格式 | 介面原始碼＋每個類別要加的程式＋設計理由＋它不確定的地方 |

5. **模擬 AI 回覆 B**：介面方向正確，但方法過多（加了會改狀態的 `book()`／`returnItem()`，以及一個 `default` 方法）。學生用審查清單決定刪哪些。
6. **審查清單（介面版，6 項以內）**：
   1. 共同型態的成員是否「不同家族」？若都是同一父類別的子類別，抽象類別可能更合適。
   2. 有沒有讓不是 is-a 的類別去 `extends`（第 7 週）？
   3. 介面只列使用端真的會呼叫的操作嗎？有沒有多餘的方法？
   4. 介面裡有沒有放狀態？（介面欄位自動是常數，不能當實例欄位用）
   5. 實作方法是否都 `public`、都有 `@Override`？
   6. 使用端是否只依賴介面型態，而不是用 `instanceof` 一個個判斷？
7. **定稿**：課堂寫出採納／不採納理由；實作留到作業。

真實 AI 工具為選用；若使用，Prompt 與回覆摘要記在 `AI_USAGE.md`，不能拿模擬回覆冒充真實對話（沿用第 4、6 週規則）。

### 4.5 作業短篇：社團器材的抽象類別＋預約介面

原型已確認可行的設計（§6）：

```text
«interface» Bookable
  + getLabel() : String
  + maxHours() : int

«abstract» Equipment implements Bookable
  getLabel() 原本就有，自動滿足介面；maxHours() 不實作，留給子類別
  + checklist() : String  {abstract}         取代第 8 週的預設 checklist()

Camera、Projector extends Equipment           checklist()、maxHours()
Microphone extends Equipment                  學生新增（取代第 7 週 ShelfTry 的 new Equipment("MIC-01", ...)）
Room implements Bookable                      不是器材，不繼承 Equipment
```

- 第 7 週 `ShelfTry` 第 5 行 `new Equipment("MIC-01", "Shure SM58")` 在 `Equipment` 改成抽象後會出現 `Equipment is abstract; cannot be instantiated`——作業從這個編譯錯誤開始，學生要新增 `Microphone` 解決。
- 第 8 週的 `super.checklist()` 不能再用（父類別方法已抽象），學生改用 `getLabel()`；講義要提示這個變化，避免誤以為是自己寫錯。
- 「抽象類別實作介面時可以不實作全部方法、交給子類別」是較細的規則：主作業的 `Equipment` 由教師提供（已寫好 `implements Bookable`），學生只需在子類別補 `maxHours()`；規則放在教師筆記與講義延伸說明。
- 時數值（相機 72、投影機 24、社辦 3 等）為原型暫定，製作時定稿。

## 5. 課堂流程（150 分鐘，提案）

| 分鐘 | 段落 | 內容 | 落後時 |
|---|---|---|---|
| 0–10 | 0 準備＋診斷 | 取回 repo、建 `java-09`；§2 三題快速診斷 | 只問第 2 題 |
| 10–40 | A 抽象類別 | A1 預測 `new Rover(20, 0)` 能否編譯；A2 抽象類別當變數型態（`Rover r = new StandardRover(...)`）；A3 刻意漏寫 `exploreYield` 的 `ScoutRover` 讀錯誤訊息；TODO 1 `Rover` 改抽象＋抽象方法（跟做）、TODO 2 `StandardRover`（模仿 `HeavyRover`） | A3 改教師示範 |
| 40–55 | B `final` | B1 回顧第 8 週 AI 的 `execute` 覆寫；TODO 3 加 `final` 後重編譯，讀 `overridden method is final`；B2 什麼該 `final`、什麼該留給子類別 | B2 課後讀 |
| 55–85 | C 介面 | C1 `StepListener`（提供）；TODO 4 `ConsoleListener`（跟做）；C2 執行 `ListenerDemo` 前預測輸出；C3 `LowEnergyAlarm` 讀程式並預測警告次數；C4 抽象類別 vs 介面比較表；C5 C# 對照 | C3 課後 |
| 85–100 | D 匿名與巢狀類別 | D1 讀匿名類別、預測輸出、看 `$1.class`；D2 C# 事件對照；D3 巢狀類別一題預測 | D3 改投影片帶過 |
| 100–130 | E AI 介面設計 | §4.4 流程 1–7（人工初版→v1→回覆 A→v2→回覆 B→清單→定稿） | 課綱主題，不壓縮；定稿可課後 |
| 130–135 | F 課末檢核 | 口頭 5 題，最後一題預告第 10 週（「同名不同參數」的 `run`） | |
| 135–150 | G 保存推送 | commit 慣例 `[manual]`／`[AI]`／`[fix]`／`[doc]`；全新 clone 後重編譯 | **不可挪用** |

工具：專案超過三個檔案，VS Code 編譯執行為主，IntelliJ 示範與本週有關的功能（抽象方法與介面的行號圖示、`implements` 後自動產生缺少的方法、類別階層視窗）。快捷鍵與圖示製作時以實際 IntelliJ 版本確認；IDE 自動產生的方法學生要讀過、能解釋，不能留著空方法交差。

## 6. 原型驗證（2026-11-10，暫存目錄，非正式教材）

以第 7 週 claude 版教師程式加上第 8 週規劃 §4.1 的變更，再加上 §4.1–4.2、§4.5 的設計，在 Claude 的暫存目錄實測（`openjdk 22`、`javac 22`）。**正式製作時要在乾淨目錄重做**，且第 8 週實際教材若與假設不同需重測。

執行結果：

| 案例 | 輸出（節錄） |
|---|---|
| `MissionLog` 改用 `StandardRover`，10 組參數（含無參數、`0`、`5 -1 1`、`3 1 9 1 0` 等） | 與第 7 週教師版**逐字相同** |
| `StandardRover(20,0)` 掛 `ConsoleListener`，腳本 `1 1 3 1 0` | `#1 cmd=1 成功 能源=14` … `#4 cmd=1 成功 能源=7`，`任務成功`（第 4 步達標後不讀 0） |
| `HeavyRover(14,0)` 掛 `LowEnergyAlarm(6)`，同腳本 | `ALARM at 步驟 2: 能源=2`，`任務成功 alarms=1` |
| `HeavyRover(20,0)` 掛匿名類別，腳本 `3 1 1` | `rejected at #1: 能源已滿`，`任務成功` |
| `ArrayList<Rover>` 各 `execute(1)` 後 `println` | `能源=14, 樣本數=2, actions=1`／`HEAVY 能源=14, 樣本數=3, actions=1, drills=0` |
| `ArrayList<Bookable>` 看板 | `CAM-01 Sony A7 max=72h`／`PRJ-01 Epson X49 max=24h`／`R-201 room(12) max=3h` |
| 編譯產物 | 出現 `ListenerDemo$1.class` |
| `--release 11` 編譯全部原型 | 無錯誤（只確認語法相容，不是學生要求） |

刻意錯誤的實際編譯訊息（講義與投影片要逐字使用正式製作時的實測結果）：

| 實驗 | 訊息 |
|---|---|
| `new Rover(20, 0)` | `Rover is abstract; cannot be instantiated` |
| `ScoutRover` 沒實作 `exploreYield` | `ScoutRover is not abstract and does not override abstract method exploreYield() in Rover` |
| 子類別覆寫 `final execute` | `execute(int) in BadHeavy cannot override execute(int) in Rover` ／ `overridden method is final` |
| 類別 `implements StepListener` 但沒寫方法 | `Quiet is not abstract and does not override abstract method onStep(int,MissionStep) in StepListener` |
| 實作 `onStep` 時漏寫 `public` | `onStep(int,MissionStep) in Q2 cannot implement onStep(int,MissionStep) in StepListener` ／ `attempting to assign weaker access privileges; was public` |
| `new StepListener()` 沒有大括號 | `StepListener is abstract; cannot be instantiated` |
| 匿名類別參數寫錯 | `<anonymous E3$1> is not abstract and does not override abstract method onStep(int,MissionStep) in StepListener` |
| `ConsoleListener c = l;`（`l` 是 `StepListener`） | `incompatible types: StepListener cannot be converted to ConsoleListener` |
| 第 7 週 `ShelfTry` 遇到抽象 `Equipment` | `Equipment is abstract; cannot be instantiated` |

「漏寫 `public`」是 C# 學生的高風險錯誤（C# 介面實作也要 `public`，但 Java 介面方法本身不寫修飾字時預設 `public`，學生在類別裡照抄就會漏），列為講義 C 段的預測題。

## 7. 學生負擔評估

| 類型 | 內容 | 必做／選做 | 評估 |
|---|---|---|---|
| 跟做 | TODO 1 `Rover` 改抽象、TODO 3 `final`、TODO 4 `ConsoleListener` | 必做 | 每個 TODO 只有一兩行關鍵字或一個短方法；重點在讀錯誤訊息 |
| 模仿 | TODO 2 `StandardRover` | 必做 | 照 `HeavyRover` 的建構子與覆寫寫法 |
| 預測 | C2–C3、D1、D3 | 必做（D3 可略） | 讀程式預測，不寫新程式 |
| AI 介面設計 | E 段人工初版、Prompt v2、清單審查 | 必做 | 課綱主題；欄位固定、時間有限 |
| 獨立（作業） | `Microphone`、`maxHours()`、`Room implements Bookable`、看板迴圈 | 必做 | 同時用到抽象類別與介面，但每個類別都很短；和課堂換情境，不是換數字 |
| 設計文件（作業） | `DESIGN.md` 加「為什麼 `Equipment` 是抽象類別、`Bookable` 是介面」 | 必做 | 3–5 句＋更新後的類別圖 |
| 遷移 | 自訂一個 `StepListener`（例如統計被拒次數的 `RejectCounter`），或用匿名類別改寫 | 選做 | 讓學生自己決定要不要欄位 |
| 延伸 | 讀 Deitel 10.10.1 的 `default` 方法，說明模擬 AI 回覆 B 的 `default` 方法是否合理 | 選做 | 不要求寫程式 |

整體與第 8 週規劃相近：課堂 4 個短 TODO＋大量預測與讀錯誤訊息，AI 段落以設計判斷為主。作業只出一份短篇，不再另附模擬 AI 程式檔（AI 素材集中在課堂 E 段的兩份模擬回覆）。

## 8. 預定交付檔案（`week09/claude版本/`）

| 檔案 | 內容 |
|---|---|
| `README_claude.md` | 目標、故事線、檔案索引、執行指令、與前週一致性、Codex 版位置 |
| `week09_slides_claude.md`／`.html` | Marp，約 35–40 頁，每頁 `by-claude`；沿用第 7 週樣式 |
| `week09_實作講義_claude.md` | 0、A–G 段與課後選做，含預測／實際欄位、Prompt 欄位表 |
| `week09_作業說明_claude.md` | 社團器材抽象類別＋`Bookable`、設計文件、AI 紀錄、繳交與驗收 |
| `week09_教師筆記_claude.md` | 時間配置、全部解答與實際輸出、常見誤解（漏 `public`、以為介面能放實例欄位、抽象類別不能有建構子）、批改重點 |
| `Prompt範本_claude.md` | 介面設計 Prompt 欄位表、Prompt v1、標示「模擬」的回覆 A／B、介面審查清單 |
| `DESIGN_範本_claude.md` | 沿用第 7–8 週，加「抽象類別或介面？理由」一節 |
| `AI使用紀錄表_範本_claude.md` | 本週欄位；區分模擬回覆、教師示範、真實 AI 對話 |
| `week09_交接_claude.md` | 給第 10 週的能力與資料契約 |
| `week09_驗證紀錄_claude.md` | 實測結果與未驗證項目 |
| `code/starter/` | `Rover`（TODO 1、3）、`StandardRover`（TODO 2 骨架）、`HeavyRover`、`StepListener`、`ConsoleListener`（TODO 4）、`LowEnergyAlarm`、`ListenerDemo`、沿用的 `Mission`（已加 listener）／`MissionStep`／`MissionLog`、`AbstractTry`（檢查程式，佔位以 `不符` 標出） |
| `code/starter/club/` | 抽象 `Equipment`（已 `implements Bookable`）、`Bookable`、第 8 週版 `Camera`／`Projector`（待改）、`BoardTry` |
| `code/experiments/` | §6 的刻意錯誤小檔，每個實驗獨立資料夾編譯 |
| `code/teacher/`、`code/teacher/club/` | 完整解答與檢查程式（延續 `HeavyRoverCheck`、`ShelfCheck` 寫法） |
| `diagrams/*.puml`／`.svg` | 抽象類別（斜體／`«abstract»`）與介面（虛線空心三角形）類別圖；社團預約看板圖 |

TODO 編號在製作時統一，講義、starter、教師筆記、投影片的檔案對照必須一致；`starter`、`teacher`、`experiments` 同名類別分開編譯。

**starter 可編譯的問題**：starter 的 `Rover` 先保留第 8 週的具體 `exploreYield()`（回傳 2），`StandardRover` 的 `@Override` 在 TODO 1 前後都能編譯，所以 starter 可安全執行。但「`Rover` 是否已改成抽象」從執行輸出看不出來：學生端改用講義 A1 的編譯實驗（`new Rover(20, 0)` 應該失敗）自我確認，教師檢查程式另行確認。製作時定案檢查方式，不讓 starter 宣稱佔位結果已完成。

## 9. 給第 10 週的入口（預計寫入交接）

- **過載**：本週刻意用 `setStepListener` 避開 `run(int[], StepListener)`；第 10 週可正式比較「同名不同參數（過載）」與「同名同參數（覆寫）」，並回收第 8 週 `@Override` 拼錯實驗。
- **多型綜合**：`Rover`（抽象類別）與 `StepListener`（介面）兩種多型來源都已出現；第 10 週可讓 AI 生成「用 `instanceof` 判斷車種」與「用覆寫」兩版程式比較除錯。
- **Lambda 預告**：`StepListener` 只有一個抽象方法，第 13 週可直接改用 Lambda。
- **例外處理預告**：`Mission` 的 `listener != null` 檢查可在第 12 週對照 `NullPointerException`。

## 10. 驗證計畫（正式製作時）

- 記錄 java／javac 版本；`javac -encoding UTF-8 -Xlint:all` 在乾淨目錄分開編譯 starter、starter/club、teacher、teacher/club、experiments 各資料夾；另以 `--release 11` 確認語法相容（不當成學生要求）。
- 回歸：`MissionLog` 與第 8 週 claude 版教師版（製作後）以 10 組參數逐字比對；未設定 listener 時 `Mission` 行為不變。
- §6 所有案例與錯誤訊息在正式檔案上重跑，講義引用實測原文。
- 檢查程式要能抓到常見錯誤（變異測試）：`StandardRover` 回傳 3、`execute` 未加 `final`、`Room extends Equipment`、`maxHours` 寫錯。
- PlantUML 以 `-P layout=smetana -pipe` 產生，確認抽象類別與介面記號在 Smetana 下正確顯示；Marp 匯出 HTML 與逐頁 PNG 做視覺檢查。
- 雙 IDE GUI、真實 AI 對話、真實 GitHub 操作、學生試教：做了才寫通過，未做列為未驗證。

## 11. 需要教師決定的事項

1. **第 8 週先做還是一起做**：本規劃依賴第 8 週 claude 版（§0）。建議先完成第 8 週；若要兩週一起製作，第 8 週規劃 §10 的三項待決事項需要先回覆。
2. **`Rover` 改抽象的範圍**：本規劃讓 `Rover` 變抽象並新增 `StandardRover`，`MissionLog` 跟著改一行。若希望前幾週的 `new Rover(20, 0)` 完全不動，替代方案是只在社團器材示範抽象類別，探測車只加 `final`——但會失去「`Mission` 欄位型態是抽象類別」的主線證據。
3. **介面主線**：本規劃採 `StepListener`（通知對象，可順勢帶出匿名類別與 C# 事件對照）。另一選項是「可被任務驅動的單位」介面（如無人機），概念更接近 Deitel 10.9 的 `Payable`，但實作負擔大。
4. **E 段是否要求真實 AI**：本規劃以教師撰寫的模擬回覆為主、真實 AI 選用。若教室可用 AI 工具，可改為學生實際送出 Prompt v1／v2 並比較，但結果不可預期，教師筆記只能給判斷原則而非固定答案。
