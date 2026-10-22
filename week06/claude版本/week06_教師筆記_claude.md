# 第 6 週教師筆記（claude 版本）

> 本筆記初稿由 Anthropic Claude（Claude Code）生成，供教師備課與批改使用，不發給學生。

## 定位與銜接

- **課綱**：類別與物件設計(II)：UML 類別圖與物件導向設計思維——AI 輔助系統設計練習。
- **承接第 5 週 claude 版**：學生已會 `private` 欄位、建構子、`this`、getter、實例方法與 `ArrayList<MissionStep>`。第 5 週留下三條伏筆：課末檢核第 5 題的方框草圖、課後選做「能源 0 時誰擋 `charge()`」、`MissionLog` 全是 static 方法且同時負責流程、計算與輸出。
- **本週新增**：讀寫類別圖（三格、可見性、static、`{readOnly}`）、關聯與相依、多重性、聚合／組合（只要認識）、責任分配的三個原則、依圖重構、用類別圖檢查 AI 的設計。
- **本週不教**：繼承與介面的圖示（第 7–9 週）、循序圖等其他 UML 圖、設計模式（第 15 週）。
- **工具決定**：學生用 PlantUML 文字寫圖，放進 `DESIGN.md` 的 ```` ```plantuml ```` 區塊，推上 GitHub 後用 PlantUML 官方的「PlantUML for GitHub」擴充功能（Chrome、Firefox）看圖。這個擴充功能在瀏覽器內產生圖，不送資料到伺服器，類別圖用 Smetana 排版，不需要 Graphviz。投影片與 `diagrams/` 的 SVG 由教師端 PlantUML 1.2026.9 以 Smetana 產生，和學生看到的排版引擎相同，但版本可能不同。
- **選擇文字格式的理由**：圖可以 diff、可以 commit 前後比較，也能直接和 AI 產生的 PlantUML 原始碼逐行對照，延續本課程「保留人工初版、比較 AI 建議」的做法。

## 時間配置（150 分鐘，休息另計）

這是備課配置，尚未經課堂試做。

| 分鐘 | 段落 | 教師動作 | 若進度落後 |
|---|---|---|---|
| 0–10 | 0：取回 repo、裝擴充功能、上週兩題 | 投影第 5 週課後選做題，請 2–3 位同學說答案 | 擴充功能可在 G 段再裝 |
| 10–30 | A：讀與畫類別圖 | 先投影 `battery_claude.svg` 與 `Battery.java` 並排；A2 全班舉手預測 | A3 改課後 |
| 30–50 | B：關係與多重性 | `RelationDemo` 先預測；黑板畫 a、b 指向同一顆電池；B4 修正上週草圖 | 聚合／組合只講一頁，B5 口頭 |
| 50–65 | C：責任與設計思維 | C1 現場跑 `new Rover(0, 0).charge()`（第 5 週版）；C2 用「改成 WinForms 視窗」來分 | C3 只填 `MissionLog` 一列 |
| 65–105 | D：TODO 1–7 | TODO 1–3 帶做；4、5 模仿；6 巡堂；D5 由教師投影示範一次 | 先確保 TODO 1–5 通過前三條測試；TODO 6–7 可課後 |
| 105–130 | E：AI 輔助系統設計 | E1 個人 10 分鐘（紙筆）；E2 鄰座兩人一組 10 分鐘；E3 當堂寫出 Prompt 草稿 | E1、E2 是課綱主題，不要壓縮；E3 可課後完成 |
| 130–135 | F：課末檢核 | 口頭抽問 | |
| 135–150 | G：保存、推送、GitHub 看圖 | 巡查 GitHub 網頁，確認至少一張圖顯示 | **不可挪用** |

## 程式碼設計說明

| 檔案 | 說明 |
|---|---|
| `examples/Battery.java` | 第 5 週範例原樣沿用，只改檔頭說明 |
| `examples/Flashlight.java` | 欄位保存 `Battery`：關聯。建構子接收外部建立的電池，所以可以共用（聚合） |
| `examples/Charger.java` | `Battery` 只在參數出現：相依 |
| `examples/RelationDemo.java` | A、B 共用一顆電池，C 自己一顆；預測題答案見下方 |
| `starter/Rover.java` | 第 5 週完成版；`isStopped()` 回傳 false、`execute` 沒有停止檢查、三個行動仍是 public |
| `starter/Mission.java` | 方法清單照類別圖寫好，`run` 空白、`getOutcome` 回傳 `UNFINISHED`、計數回傳 0、`lowestEnergy` 回傳 -1；可編譯並安全結束 |
| `starter/MissionStep.java`、`MissionLog.java` | 已完成，與教師版相同 |
| `starter/RoverTry.java` | 已完成；只呼叫 `execute`，行動改成 private 後仍能編譯 |
| `teacher/` | 完整解答與 `MissionCheck`（31 項） |
| `teacher/club/` | 作業延伸參考：`Member`、`Equipment`、`Loan`、`Club` 與 `ClubCheck`（17 項）；學生只需設計，不必寫程式 |

重要的設計決定：

- **停止規則放兩層**：`Rover.execute` 停止後回傳 `STOPPED`，守住探測車自己的狀態；`Mission.run` 停止後不讀、不記，這是任務日誌的規則。兩者各自守自己的資料，D5 用實驗證明 `Mission` 的檢查不能拿掉。從 `new Rover(20, 0)` 出發時，`STOPPED` 永遠不會出現在輸出中，所以輸出與第 5 週逐字相同（已實測 10 組參數）。
- **`Mission` 不交出清單**：提供 `getStepCount()`、`getStep(i)`。`MissionStep` 不可變，交出單筆是安全的。`MissionCheck` 用反射檢查 `Mission` 沒有回傳集合的非 private 方法。
- **`lowestEnergy()` 空紀錄回傳 -1**：避免學生用 `steps.get(0)` 在空清單時當掉，也避免把初值設成 0 或容量這種「看起來像真的」的數字。
- **`ignored` 留在 `MissionLog` 計算**：腳本長度只有 `MissionLog` 知道，`Mission.run` 不保存腳本。這是刻意留下的小討論點：如果要讓 `Mission` 回答 ignored，`run` 就得記下腳本長度。
- **`Mission` 到 `MissionStep` 畫成組合**：`Mission` 在 `run` 裡自己 `new` 出每筆紀錄，清單不交出去。`Mission` 到 `Rover` 只畫關聯，因為探測車是 `MissionLog` 建立後交進來的，`MissionLog` 自己也還拿著它。批改時學生把組合畫成普通實線關聯（有多重性）也算正確。

## 講義解答

### 0

- 第 5 週 `new Rover(0, 0).charge()` 回傳 `OK`，能源變 5、行動次數 1。規則應由 `Rover` 負責（能源是它的資料）。第 5 週其實是 `MissionLog.runMission` 在迴圈開頭擋住，直接呼叫就擋不住。
- 草圖因人而異；常見錯誤是畫 `Rover → MissionStep`，或用實線把三個類別連起來。

### A

A1：

| Java 程式 | 類別圖的一行 |
|---|---|
| `private int level;` | `- level : int` |
| `public Battery(int capacity, int level)` | `+ Battery(capacity : int, level : int)` |
| `public int getLevel()` | `+ getLevel() : int` |
| `public int charge(int amount)` | `+ charge(amount : int) : int` |

A2：

| 程式 | 答案 |
|---|---|
| `rover.energy = 5;` | 不能：`-` 是 private |
| `rover.explore();` | 能：`+` public，且是實例方法 |
| `int max = Rover.CAPACITY;` | 能：public 且 static（底線），用類別名稱存取 |
| `Rover.explore();` | 不能：沒有底線，不是 static，必須透過物件呼叫（第 5 週錯誤訊息 `non-static method ... cannot be referenced from a static context`） |
| `Rover r = new Rover();` | 不能：圖上唯一的建構子要兩個參數 |

A3：

```text
class Flashlight {
  - name : String {readOnly}
  - battery : Battery {readOnly}
  + Flashlight(name : String, battery : Battery)
  + getName() : String
  + turnOn(minutes : int) : boolean
}
```

常見錯誤：寫成 Java 順序 `- String name`；漏掉建構子；把 `battery` 只畫成箭頭而不寫欄位（兩種畫法都可以，但本課程要求欄位與箭頭都寫，方便和程式逐行對照）。

### B

`RelationDemo` 實際輸出：

```text
A on 6 min: true
B on 6 min: false
C on 6 min: true
shared level: 4
refill shared: +6
B on 6 min: true
shared level: 4
```

- B1：`a`、`b` 的 `battery` 欄位指向同一個 `Battery` 物件；A 用掉 6 格後只剩 4，B 要 6 格被拒絕。C 的電池是另一個物件。
- B2：`Flashlight → Battery` 關聯（欄位 `private final Battery battery;`）；`Charger → Battery` 相依（`refill(Battery battery)` 的參數）；`RelationDemo → Flashlight` 相依（`main` 的區域變數）。
- B3：2 支。`"0..*"` 表示一顆電池可以被 0 到多支手電筒使用，共用是允許的。
- B4：`MissionLog ..> Rover`、`MissionLog ..> MissionStep`，都是虛線（區域變數與參數）；`Rover` 與 `MissionStep` 之間沒有關係。參考圖 `diagrams/week05_overview_claude.svg`。
- B5：`new Flashlight("B", shared)`：電池在外面建立，同一顆交給兩支手電筒，不是某一支手電筒獨有。

### C

- C1：印出 `OK`，能源 5。第 5 週由 `MissionLog.runMission` 在執行前檢查 `isExhausted()`；別的類別直接呼叫 `charge()` 就擋不住。
- C2：勾「依腳本執行並記錄」「判斷結果」「計算摘要數字」。轉成 `int[]` 與印到螢幕屬於命令列介面，換成視窗就會改。
- C3：

| 類別 | 知道什麼 | 負責做什麼 | 合作對象 |
|---|---|---|---|
| `Rover` | `energy`、`samples`、`actionCount` | 執行行動規則；判斷成功、耗盡、停止 | 無 |
| `MissionStep` | `command`、`result`、`energyAfter` | 記錄一步，提供回放文字 | 無 |
| `Mission` | `rover`、`steps` | 依腳本執行並記錄、判斷結果、計算摘要數字 | `Rover`、`MissionStep` |
| `MissionLog` | 無 | 讀命令列參數、建立 `Rover` 與 `Mission`、印出回放與摘要 | `Mission`、`Rover`、`MissionStep` |

- C4：見 D5。

### D

- D1：6 行 `DIFF`（`tired` 兩行、`done` 兩行、`r.execute(3)` 與 `r` 各一行）。`done` 一開始就達標，但起始碼沒有停止檢查，探索照樣執行：20−6＝14、5＋2＝7、行動 1。
- D2-1：從 `+` 改成 `-`。外部只能呼叫 `execute`，而停止檢查就在 `execute` 的第一行，沒有其他入口能繞過。
- D3-1：建構子已把 `rover` 存進欄位、建立了 `steps`，物件自己記得；`run` 只負責執行，結果改由 `getOutcome()` 查詢。
- D3-2：`new Rover(2, 4)` 掃描後能源 0、樣本 5，同時耗盡與達標；規則是成功優先，所以 SUCCESS 必須先判斷。
- D4-1：`mission.getSteps().clear();`（或 `add(new MissionStep(1, "OK", 99))`）會直接改掉任務紀錄，摘要隨之錯誤。
- D5（實測）：拿掉 `run` 的停止檢查後，`1 1 1 1` 的輸出變成：

  ```text
  step 4: command=1, result=STOPPED, energy=2
  counts[0..3]=[0, 4, 0, 0]
  steps=4, ok=3, rejected=1, ignored=0
  ```

  最後一行仍是 `final energy=2, samples=6, actionCount=3 -> SUCCESS`，因為 `STOPPED` 沒有改變探測車。結論：「停止後不讀、不記」是任務日誌（`Mission`）的規則；`STOPPED` 保護的是探測車的狀態。預設腳本 `1 1 2` 剛好在達標時用完，看不出差異，所以實驗要用 `1 1 1 1`。

- D6：參考圖 `diagrams/week06_mission_claude.svg`。批改重點：`toScript`、`printReplay`、`printSummary` 是套件存取，應為 `~`（常被寫成 `-` 或 `+`）；`MissionLog ..> MissionStep` 常被漏掉（`printReplay` 呼叫 `getStep(i).describe()`）；圖上不能出現程式沒有的 `getSteps()`。

## E 段參考答案

### E1

1. 不需要。艙內數量由 `SampleBay` 保管，送回的另外計數；`Rover` 再存一份 `samples`，兩邊一定會有對不上的時候。
2. 參考設計放在 `Rover`：卸貨是 `Rover` 的行動，`isSuccess()` 也要用到它。放在 `Mission` 或新增 `Base` 類別也可以接受，但要說明誰在卸貨時通知它、`isStopped()` 怎麼知道成功。
3. `Rover → SampleBay`，1 對 1；`SampleBay` 不需要知道 `Rover`。參考設計由 `Rover` 建構子 `new SampleBay(bayCapacity)`，畫成組合；畫關聯也算對。
4. `Rover` 在 `explore`／`scan` 中檢查：先檢查能源（不足回 `NOT_ENOUGH_ENERGY`），再檢查 `bay.getFreeSpace()`（不足回 `BAY_FULL`），都通過才扣能源並存入。必須在扣能源之前，否則被拒絕時能源已經少了，違反需求 1。

參考類別圖：`diagrams/samplebay_ref_claude.svg`。

### E2：模擬 AI 類別圖的問題

| # | 問題 | 違反 | 情境 |
|---|---|---|---|
| 1 | `SampleBay` 的 `+ count : int` | 需求 4：不能直接修改 | `bay.count = 99;` 直接成立，容量限制失效 |
| 2 | `Rover "1" --> "0..*" SampleBay : bays` | 需求「加裝一個樣本艙」：1 對 1 | 0 個艙時探索不知道存哪；多個艙時不知道存哪一個 |
| 3 | `Rover` 保留 `- samples : int` | 同一份資料存兩處 | 存進艙時忘了同步 `samples`，`isSuccess()` 判斷錯 |
| 4 | `SampleBay + explore()` | 資料在誰身上，規則放誰身上 | 探索要扣能源，`SampleBay` 沒有能源；只好再把能源傳進去或讓艙知道探測車 |
| 5 | `Mission + getSteps()` | 只公開別人需要的 | `mission.getSteps().clear()` 清掉任務紀錄 |
| 6 | `MissionStep - rover : Rover`，少了執行後能源 | 第 5 週：紀錄要存當下的數值 | 回放每一步都顯示最後的能源 |
| 7 | 沒有任何地方記「已送回樣本數」 | 需求 3、4 | 無法判斷成功，也無法查詢 |

學生找到任四項並有情境即通過。`Mission --> "0..*" MissionStep` 沒有畫組合不算錯；`MissionStep --> Rover` 是第 6 項的一部分。

### E3：Prompt 參考

```text
請依下列需求與責任表，畫出 PlantUML 類別圖（只輸出 @startuml 到 @enduml 的原始碼）。
需求：（貼上講義 E 段需求 1–5）
責任表：（貼上 E1 的表）
已有類別：Mission（rover、steps，方法 run、getOutcome），MissionStep（command、result、energyAfter，皆不可變）。
要求：
1. 所有欄位 private；只列出外部需要的 public 方法；static 用 {static}，final 欄位標 {readOnly}。
2. 畫出類別之間的關係，欄位保存用實線關聯，只在方法裡使用用虛線相依，關聯兩端標多重性。
3. 不使用繼承、介面、抽象類別。
4. 圖之後用條列說明：每個類別的責任、每條關係的理由，以及需求中你不確定、需要我決定的地方。
```

## 課末檢核答案

1. 欄位雖然 private，但交出清單的參考後，外部可以 `add`、`remove`、`clear`，等於公開。
2. 實線關聯：欄位；虛線相依：參數、區域變數、使用別的方法回傳的物件。
3. 一個 `Mission` 物件連到恰好一台 `Rover`。
4. 停止規則：從 `MissionLog.runMission` 的迴圈搬到 `Rover.execute`（`isStopped()`），因為能源與樣本是 `Rover` 的資料。另外流程與計算從 `MissionLog` 搬到 `Mission`。
5. 複製會產生兩份幾乎相同的程式，改一條規則要改兩處，容易漏改。第 7 週用繼承解決（類別圖畫空心三角形箭頭）。

## 作業：社團器材借用參考答案

參考類別圖 `diagrams/club_ref_claude.svg`，程式 `code/teacher/club/`（`ClubCheck` 17 項）。設計重點：

- `Club` 保管器材清單與全部 `Loan`，所以由它檢查「器材是否已借出」「社員同時幾件」：這兩條規則都要看所有紀錄。
- `Member`、`Equipment` 只記身分，不另存「借了幾件」「是否借出」，避免與 `Loan` 不一致。學生若在 `Equipment` 存 `onLoan`，只要說明由誰在借出、歸還時更新、且不是 public 欄位，也可接受。
- `Loan` 的 `returnDate` 為 null 表示未歸還；`close` 第二次回傳 false（需求 5）。
- 「同時最多 2 件」是規則，不是多重性：一位社員的歷史紀錄會超過 2 筆。
- `borrow` 回傳 `OK`、`UNKNOWN_EQUIPMENT`、`ON_LOAN`、`LIMIT_REACHED`（需求 7）。
- 比較物件用 `==`（同一個物件）；`equals` 留到第 7–8 週。

模擬 AI 類別圖的問題：

| # | 問題 | 違反 | 情境 |
|---|---|---|---|
| 1 | `Member "1" --> "0..2" Loan` | 需求 4 說的是「同時未歸還」最多 2 件 | 借過、還過 3 次後，第 3 筆紀錄就違反圖上的多重性 |
| 2 | `Equipment + borrower : Member` | 封裝；與 `Loan` 重複記錄誰借走 | 任何類別都能改借用人；歸還時只改了 `Loan`，器材仍顯示被借走 |
| 3 | `Member + borrow(e) : void` | 需求 4、7 | 社員看不到別人的借用紀錄，無法判斷器材是否已借出；回傳 void 無法告知拒絕原因 |
| 4 | `Club + getLoans()` | 只公開別人需要的 | 外部 `getLoans().clear()` 清掉所有紀錄 |
| 5 | `Loan "1" --> "0..*" Equipment`，`Loan` 沒有器材欄位 | 需求 3：一筆紀錄是一位社員、一件器材 | 一筆紀錄對多件器材時，歸還其中一件無法表示 |
| 6 | `Loan` 沒有歸還日期，也沒有任何歸還方法 | 需求 5、6 | 無法記錄歸還，也列不出未歸還的紀錄 |
| 7 | `Club` 沒有借、還、查詢方法 | 需求 6、7 | 呼叫的人只能自己操作清單，規則散落各處 |

## 常見錯誤與診斷

| 現象 | 原因 | 處理 |
|---|---|---|
| 圖上寫 `- int level` | Java／C# 的型態在前習慣 | 對照 A 段表格：UML 名稱在前 |
| 多重性寫反 | 不清楚數字描述的是哪一端 | 從箭頭起點讀：「一個 Mission 連到 **1** 台 Rover」，數字寫在被連到的那一端 |
| 區域變數畫成實線 | 只看「有用到」 | 問「它存在欄位裡嗎」 |
| 每條線都畫實心菱形 | 以為菱形比較「專業」 | 本課程只在「自己 new、不交出去」時畫；不確定就畫普通關聯 |
| `RoverTry` 編譯失敗：`explore() has private access` | 學生自己在 `RoverTry` 加了直接呼叫 | 改用 `execute(1)`；正好示範 private 的效果 |
| 五條測試只有部分正確 | `run` 少了停止檢查、`getOutcome` 順序錯、`lowestEnergy` 初值 0 | 用 `MissionCheck` 定位；D5 的實驗對應第一種 |
| GitHub 上只看到原始碼 | 沒裝擴充功能，或程式碼區塊標記不是 `plantuml` | 換瀏覽器或電腦；教師以原始碼批改 |
| 擴充功能顯示錯誤 | `@startuml`／`@enduml` 缺漏、大括號不成對、多重性引號不成對 | 對照範本逐行檢查 |

## 批改工具

```powershell
javac -encoding UTF-8 -d out-check <學生的 Rover、MissionStep、Mission、MissionLog> code/teacher/MissionCheck.java
java -cp out-check MissionCheck
```

不要加入教師版同名類別。`MissionCheck` 共 31 項：14 組任務狀態（與第 5 週相同）、4 項停止規則、4 項 `Mission` 計算、5 條必做測試的完整輸出逐行比對、4 項設計檢查（欄位 private、行動 private、`Mission` 的 public 方法清單、不交出集合）。起始碼搭配它可執行，5/31 通過，不會中斷。

已用變異測試確認可抓到：拿掉 `execute` 的停止檢查（28/31）、拿掉 `run` 的停止檢查（26/31）、`lowestEnergy` 初值 0（24/31）、`explore` 保持 public（30/31）、加上 `getSteps()`（29/31）、結果判斷順序顛倒（30/31）。

已知限制：`countOk` 用 `==` 比字串時，因為結果都是字串常值，`MissionCheck` 抓不到；人工批改時看程式。

## 評分重點（配合作業說明）

| 項目 | 重點 |
|---|---|
| 程式 | 停止規則在 `Rover.execute`；三個行動 private；`Mission.run` 保留停止檢查；沒有 `getSteps()`；`countOk` 用 `equals` |
| 測試 | 五條與第 5 週相同；`RoverTry` 全部 `OK` |
| 任務系統類別圖 | 與程式逐項一致；`~` 用對；關聯與相依分清楚；有多重性 |
| 樣本艙設計 | 四個問題有理由；拒絕檢查在扣能源之前；E2 至少四項有情境 |
| 社團設計 | 第一版在比對前 commit（看 commit 時間）；比對有需求編號與情境；最終圖說明修改理由 |
| 誠信 | 模擬回覆與真實 AI 分開；能說明自己的圖每條線的理由 |

## 留給第 7 週

見 [week06_交接_claude.md](week06_交接_claude.md)。
