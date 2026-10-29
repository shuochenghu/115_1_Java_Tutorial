# 第 7 週教師筆記（claude 版本）

> 本筆記初稿由 Anthropic Claude（Claude Code）生成，供教師備課與批改使用，不發給學生。

## 定位與銜接

- **課綱**：繼承(I)：類別繼承基礎——AI 生成繼承程式碼閱讀與追蹤。
- **承接第 6 週 claude 版**：學生會讀寫類別圖（`+`、`-`、`~`、static、`{readOnly}`）、分辨關聯與相依、用責任表分配規則。程式端：停止規則已收回 `Rover.execute`，`explore`、`scan`、`charge` 為 private；`Mission` 只依賴 `Rover` 的 public 方法。第 6 週課末檢核第 5 題預告了「重型探測車」。
- **本週新增**：`extends`、哪些成員可以直接用、`super(...)` 與建構子執行順序、隱含的 `Object`、`protected`（含 Java 與 C# 的差異）、is-a 與 has-a、類別圖的繼承箭頭與 `#`。
- **本週刻意不教**：覆寫（`@Override`、`super.method()`、`toString`）、父類別參考指向子類別物件（多型）、抽象類別與介面。這些是第 8–10 週的主題。
- **範圍決定**：第 6 週預告的是「探索一次得 3 份」，那需要覆寫。本週改成「重型探測車多了鑽探能力」，只用新增，讓學生先把繼承的機制（建構子、可見性、狀態只有一份）弄清楚。講義 0 段明確告訴學生「探索不同」留到第 8 週，課末檢核第 5 題再預告一次。

## 時間配置（150 分鐘，休息另計）

這是備課配置，尚未經課堂試做。

| 分鐘 | 段落 | 教師動作 | 若進度落後 |
|---|---|---|---|
| 0–10 | 0：取回 repo、建資料夾、上週第 5 題 | 請 2–3 位同學說「為什麼不複製」 | 上週題目口頭帶過 |
| 10–30 | A：extends | 投影 `member_claude.svg`；A1 全班預測後再執行；A2 舉手預測 | A3 第二題只口頭 |
| 30–50 | B：建構子、super、protected | B2、B3 帶全班一起改、一起看錯誤訊息；B4 的表格強調 Java 與 C# 的差異 | B5 由教師示範一次 |
| 50–60 | C：is-a／has-a、類別圖 | C1 前兩列示範，其餘兩人一組 | C2 改課後 |
| 60–100 | D：TODO 1–5 | TODO 1、3 帶做；2、4 模仿；D4-2 先追蹤；D5 教師示範；TODO 5 可課後 | 先確保 TODO 1–4 通過 `HeavyRoverTry` |
| 100–130 | E：AI 程式碼追蹤 | E1 個人預測；E2 全班一起看錯誤；E3、E4 兩人一組；E5 第 1 步現場做 | 課綱主題，不要壓縮；E5 第 2、3 步與 E6 可課後 |
| 130–135 | F：課末檢核 | 口頭抽問 | |
| 135–150 | G：保存、推送 | 巡查 GitHub 上的 `ai-check` 兩次 commit | **不可挪用** |

## 程式碼設計說明

| 檔案 | 說明 |
|---|---|
| `examples/Member.java` | 飲料店會員：`buy` 每滿 10 元 1 點；`points` private，`addPoints` protected 並擋負數。建構子印一行追蹤訊息（只為了課堂） |
| `examples/VipMember.java` | `super(id, name)`、新增欄位 `bonusCount` 與方法 `claimBonus`；透過 `addPoints`、`getPoints` 改與讀點數 |
| `examples/InheritDemo.java` | 建構子順序、繼承來的方法、`getClass().getSuperclass()` 看到 `Object` |
| `starter/Rover.java` | 第 6 週完成版，多了 TODO 1、2 的註解；可編譯 |
| `starter/HeavyRover.java` | `super(energy, samples)` 已寫好（否則無法編譯）；`getDrillCount` 回傳 0、`drill` 回傳 `NOT_READY` |
| `starter/Mission.java`、`MissionStep.java`、`MissionLog.java`、`RoverTry.java` | 沿用第 6 週，只改檔頭說明，行為相同 |
| `starter/HeavyRoverTry.java` | 已完成；15 行檢查，起始碼有 10 行 `DIFF` |
| `starter/club/` | 作業用：`Equipment`（完成）、`ShelfTry`（要等學生寫好 `Camera`、`Projector` 才能編譯，刻意設計） |
| `ai/HeavyRover.java` | 模擬 AI 回覆：沒有 `super`、重複宣告 `energy`／`samples`、沒有停止檢查、沒有 `countAction` |
| `ai/Camera.java` | 作業的模擬 AI 回覆：重複宣告 `id`／`name`（永遠是 null）、記憶卡數沒有下限 |
| `teacher/` | 完整解答與 `HeavyRoverCheck`（44 項） |
| `teacher/club/` | `Camera`、`Projector` 參考解答與 `ShelfCheck`（11 項） |

重要的設計決定：

- **欄位維持 private，另給三個 protected 方法**：若把 `energy` 改成 protected，子類別可以寫 `energy = 999` 繞過容量規則。`spendEnergy` 自己檢查夠不夠並回傳 `boolean`，呼叫的人忘了先檢查也不會扣成負數。這延續第 6 週「資料在誰身上，規則就放誰身上」。
- **`explore`、`scan` 改用輔助方法（TODO 2）**：讓學生看到「規則只寫一次、父類別自己也用」，並用第 5、6 週的必做測試確認重構沒有改變行為（已實測 10 組參數，與第 6 週教師版逐字相同）。`charge` 是增加能源，維持原樣；課後選做 `SolarRover` 會讓學生自己想要不要加 `gainEnergy`。
- **`drill` 必須自己檢查停止**：`explore` 藏在 `execute` 後面，由 `execute` 擋；`drill` 是新的公開入口，沒有人替它擋。這是本週最容易漏的地方，模擬 AI 回覆也漏了。
- **Java 的 protected 包含套件存取**：本課程所有類別在預設套件，`MissionLog`、`HeavyRoverCheck` 都能呼叫 `rover.spendEnergy(5)`（D5 讓學生親自試）。這和 C# 不同，要在 B4 講清楚；本課程仍寫 `protected`，表達「給子類別用」的意圖。
- **`Mission` 不畫到 `HeavyRover` 的線**：`Mission` 的程式沒有出現 `HeavyRover`。`new Mission(new HeavyRover(20, 0))` 能編譯並正常執行（已實測：`SUCCESS`、3 步），但這是第 8 週多型的入口，本週只在課末檢核預告。
- **`super(...)` 寫在第一行**：JDK 25 起（JEP 513，Flexible Constructor Bodies）允許在 `super(...)` 之前寫不使用 `this` 的敘述，甚至可以先指定本類別沒有初始值的欄位；JDK 24 以前則是編譯錯誤。本機 JDK 22 對 `this.bonusCount = 0; super(id, name);` 的錯誤是 `cannot reference this before supertype constructor has been called` 與 `statements before super() is a preview feature and is disabled by default.`。學生的 JDK 版本不一，所以講義沒有把「super 不在第一行」做成實驗，只要求一律寫在第一行。

## 講義解答

### 0

上週第 5 題：複製會有兩份幾乎相同的程式，改規則要改兩處；而且 `Mission` 只接受 `Rover`，複製出的類別和 `Rover` 無關。

### A

A1 實際輸出（`InheritDemo`）：

```text
new Member:
  [Member constructor] M001
new VipMember:
  [Member constructor] V001
  [VipMember constructor] V001
amy buy 120 -> +12
bob buy 85 -> +8
bob bonus -> 28
M001 Amy points=12
V001 Bob points=28
bob bonus count: 1
bob class: VipMember
parent of VipMember: Member
parent of Member: java.lang.Object
```

常見預測錯誤：以為建立 `VipMember` 只印一行；`85 / 10` 寫成 8.5（第 2 週的整數除法）。

A2：

| 程式 | 答案 |
|---|---|
| `bob.buy(50);` | 能：`buy` 是 `Member` 的 public 方法，`VipMember` 繼承了它 |
| `bob.claimBonus();` | 能：`VipMember` 自己的方法 |
| `amy.claimBonus();` | 不能：`cannot find symbol`（`symbol: method claimBonus()`、`location: variable amy of type Member`）。父類別不會得到子類別的方法 |
| `bob.points = 99;` | 不能：`points has private access in Member` |

A3：`buy` 寫在 `Member`，`VipMember` 繼承了它。`describe` 也是繼承來的，它寫在 `Member`，只知道 `Member` 的欄位，所以沒有加贈次數；要讓 VIP 的 `describe` 不同，需要第 8 週的覆寫。

### B

- B1-1：`java.lang.Object`；隱含呼叫 `super()`，也就是 `Object` 的無參數建構子。
- B2：

  ```text
  VipMember.java:8: error: constructor Member in class Member cannot be applied to given types;
    required: String,String
    found:    no arguments
    reason: actual and formal argument lists differ in length
  ```

  沒寫 `super(...)` 時 Java 自動補上 `super()`，但 `Member` 只有兩個參數的建構子。
- B3：`points has private access in Member`。物件裡有 `points`（`getPoints()` 讀得到），但 private 只允許 `Member` 自己的程式用名字存取。
- B4-1：`addPoints` 擋掉負數，點數只會增加；`points` 若是 protected，子類別可以寫 `points = -100`，規則就守不住。
- B5：印出 `Member@` 加上一串十六進位數字（例如 `Member@1b6d3586`），每次執行可能不同，不用和同學比對數字。

### C

| 兩個類別 | 判斷 | 理由 |
|---|---|---|
| `HeavyRover`、`Rover` | is-a | 重型探測車是一種探測車，探索、掃描、充電、停止規則都相同 |
| `Flashlight`、`Battery` | has-a：`Flashlight` 有 `Battery` | 手電筒不是一種電池；電池存在欄位裡 |
| `Mission`、`Rover` | has-a：`Mission` 有 `Rover` | 任務不是探測車；任務使用一台探測車 |
| `Mission`、`MissionStep` | has-a：`Mission` 有多筆 `MissionStep` | 任務不是一筆紀錄 |
| `Camera`、`Equipment` | is-a | 相機是一種器材，有編號、名稱、可以借 |
| `Loan`、`Member` | 都不是 is-a；`Loan` 有 `Member` | 借用紀錄不是社員；紀錄記下是哪位社員借的 |

C2：`mission.execute(1)`、`mission.getEnergy()`、`mission.isStopped()` 都會合法，任務自己變成一台探測車，還得自己存能源。任務和探測車綁死，不能把另一台車（例如 `HeavyRover`）交給任務；第 6 週 `Mission(Rover rover)` 的設計也無法沿用。

### D

- D1：10 行 `DIFF`。`big` 已經是 `20/0/0`，因為建構子把值交給 `super(energy, samples)`，`Rover` 的建構子把 25 改成 20、−1 改成 0。
- D2-1：`spendEnergy(6)` 在 `6 <= 0`（不可能）或 `6 > energy` 時回傳 false，也就是 `energy < 6`，兩者相同。
- D2-2：呼叫的人可能忘了先檢查，能源就會扣成負數；檢查寫在 `spendEnergy` 裡，每個子類別都不可能漏。
- D3-1：`energy`、`samples` 是 `Rover` 的 private 欄位，`HeavyRover` 不能用名字指定；而且範圍檢查寫在 `Rover` 的建構子，交給它才不會漏。
- D4-1：`explore` 只能由 `execute` 呼叫，`execute` 第一行已經檢查停止；`drill` 是外部直接呼叫的新入口，不經過 `execute`。
- D4-2：

  | 呼叫 | 回傳 | energy | samples | actionCount | drillCount |
  |---|---|---|---|---|---|
  | `h.drill()` | `OK` | 12 | 3 | 1 | 1 |
  | `h.execute(1)` | `OK` | 6 | 5 | 2 | 1 |
  | `h.drill()` | `STOPPED` | 6 | 5 | 2 | 1 |

- D5：能編譯，執行時能源真的減少 5（已實測：`spendEnergy(5)` 回傳 `true`，能源 15）。同一套件的類別都能呼叫 protected 方法，所以本課程的 `protected` 是「說明意圖」，不是強制保護。
- D6：參考圖 `diagrams/heavy_claude.svg`，加上 `Mission --> "1" Rover : rover`。批改重點：`HeavyRover` 方框重複列出 `energy`、`execute`；三角形方向畫反；`#` 寫成 `+`；畫了 `Mission --> HeavyRover`。

## E 段參考答案

- E1：不能編譯（建構子沒有 `super(...)`，`Rover` 沒有無參數建構子）。假設能執行，`getEnergy()` 仍是 20。
- E2：

  ```text
  HeavyRover.java:9: error: constructor Rover in class Rover cannot be applied to given types;
    required: int,int
    found:    no arguments
  ```

  和 B2 同一種錯誤。最小修正：建構子第一行加 `super(energy, samples);`。
- E3：加上 `super` 後 `HeavyRoverTry` 的完整輸出（已實測）：

  ```text
  big: 20/0/0 drill=0  (expect 20/0/0 drill=0) OK
  h.drill() -> OK  (expect OK) OK
  h: 20/0/0 drill=1  (expect 12/3/1 drill=1) DIFF
  h.execute(1) -> OK  (expect OK) OK
  h.isSuccess() -> false  (expect true) DIFF
  h.drill() -> OK  (expect STOPPED) DIFF
  h: 14/2/1 drill=2  (expect 6/5/2 drill=1) DIFF
  low.drill() -> NOT_ENOUGH_ENERGY  (expect NOT_ENOUGH_ENERGY) OK
  low.execute(1) -> OK  (expect OK) OK
  low: 1/2/1 drill=0  (expect 1/2/1 drill=0) OK
  exact.drill() -> OK  (expect OK) OK
  exact.execute(3) -> OK  (expect STOPPED) DIFF
  exact: 13/0/1 drill=1  (expect 0/3/1 drill=1) DIFF
  empty.drill() -> NOT_ENOUGH_ENERGY  (expect STOPPED) DIFF
  empty: 0/0/0 drill=0  (expect 0/0/0 drill=0) OK
  ```

  物件裡有兩個 `energy`：`Rover` 部分的是 20（`getEnergy()` 讀這個），`HeavyRover` 部分的是 12（`drill` 改這個）。`samples` 同理。這叫欄位**遮蔽**（hiding）：子類別的同名欄位讓子類別程式看不到父類別的那一份，但父類別的方法仍讀自己的。

  ```text
  h ──> [ HeavyRover 物件                         ]
        [  Rover 的部分： energy=20 samples=0 actionCount=0 ]  ← getEnergy()、isStopped()、execute() 用這份
        [  HeavyRover 的部分： energy=12 samples=3 drillCount=1 ]  ← drill() 用這份
  ```

- E4（任三項即可，第 1 項已示範）：

  | 問題 | 證據 | 後果 |
  |---|---|---|
  | 重複宣告 `energy`、`samples`，狀態有兩份 | `h: 20/0/0 drill=1` | 鑽探的樣本永遠不會讓任務成功；`Mission` 看不到鑽探 |
  | 沒有停止檢查 | 第二次 `h.drill() -> OK`、`empty.drill() -> NOT_ENOUGH_ENERGY` | 已達標或耗盡後還能鑽探，第 6 週收回 `Rover` 的停止規則被繞過 |
  | 沒有增加行動次數 | 第一次 `h: 20/0/0 drill=1`：鑽探成功了，行動次數仍是 0 | 摘要的 actionCount 少算 |
  | 子類別的 `energy` 沒有範圍檢查 | `new HeavyRover(25, 0)` 時子類別那份是 25 | 超過容量的能源可以鑽探 |
  | AI 宣稱「可以直接使用」 | 無法編譯 | 說明不能代替編譯與測試 |

- E5：刪掉重複欄位後出現 `energy has private access in Rover`（兩行）與 `samples has private access in Rover`。這是「好的錯誤」：編譯器直接指出子類別不能碰父類別的狀態，引導改用 protected 方法，而不是讓錯誤留到執行時才被發現。修正後與 D 段的 `drill` 相同。最誤導人的一句通常是「子類別存取不到，所以我重新宣告」：存取不到應該找父類別提供的方法，而不是另建一份。
- E6 參考：

  ```text
  以下是 Rover 的類別圖（貼上 diagrams/heavy_claude 中 Rover 的部分，含 # 方法）。
  請寫 HeavyRover extends Rover：
  1. 建構子 HeavyRover(int energy, int samples) 用 super 交給 Rover 檢查。
  2. 新增 private int drillCount 與 getDrillCount()。
  3. 新增 public String drill()：已停止（isStopped()）回傳 "STOPPED"；能源不足回傳 "NOT_ENOUGH_ENERGY"；
     成功時能源 -8、樣本 +3、行動次數 +1、drillCount +1，回傳 "OK"。拒絕時任何狀態都不變。
  限制：不可重新宣告 Rover 已有的欄位；只能用 spendEnergy、addSamples、countAction 改變狀態；
  不覆寫 Rover 的方法；欄位一律 private。
  最後列出你認為該測的案例（含已停止、能源剛好 8、能源 7），以及你不確定的地方。
  ```

## 課末檢核答案

1. 有，`energy` 在物件裡；但它是 `Rover` 的 private 欄位，`HeavyRover` 的程式不能直接寫 `energy -= 8`，要用 `spendEnergy`。
2. `Rover` 沒有無參數建構子；不寫的話 Java 自動補 `super()`，編譯錯誤。也只有 `Rover` 的建構子能設定並檢查它的 private 欄位。
3. Java 的 `protected` 也開放給同一套件的所有類別；C# 只開放給子類別。
4. 不對。借用紀錄不是一種社員，社員能做的事（借器材）紀錄不能做；應該是 `Loan` 有一個 `Member` 欄位。
5. 能編譯（`HeavyRover` 是一種 `Rover`），已實測預設腳本得 `SUCCESS`。指令 1 仍得 2 份，因為 `execute` 呼叫的是 `Rover` 的 `explore`；`Mission` 也不會呼叫 `drill`。只靠新增方法做不到「探索得 3 份」，需要第 8 週的覆寫。

## 作業參考答案

- 第 1 部分：`code/teacher/Rover.java`、`HeavyRover.java`。
- 第 2 部分：`code/teacher/club/Camera.java`、`Projector.java`。`Camera` 先 `super(id, name)` 再檢查記憶卡數（檢查的是參數，寫在 `super` 之後即可）。`Projector` 的 getter 名稱依類別圖是 `needsScreen()`，學生寫成 `isNeedsScreen()` 時 `ShelfTry` 無法編譯，請他對照類別圖。
- 第 3 部分（已實測）：AI 版 `Camera` 能編譯；`ShelfTry` 的 `DIFF` 行：

  ```text
  cam.checklist() -> null null | cards=2  (expect CAM-01 Sony A7 | cards=2) DIFF
  odd.getMemoryCards() -> -1  (expect 0) DIFF
  odd.checklist() -> null null | cards=-1  (expect CAM-02 Canon R50 | cards=0) DIFF
  ```

  `cam` 有兩個 `id`：`Equipment` 部分的由 `super(id, name)` 設定，`getLabel()`、`getId()` 讀它，所以是對的；`Camera` 部分的從未指定，預設 `null`，`checklist()` 讀到它。最小修正：刪掉重複的 `id`、`name` 欄位，`checklist()` 改用 `getLabel()`；記憶卡數加上「小於 0 改成 0」。這和課堂 E 段是同一種錯誤，但這次**能編譯**，只能靠執行結果發現。
- 第 4 部分第 3 節：學生自選器材，檢查 is-a 理由是否成立、新成員是否真的只屬於這一種器材（例如「是否附防風罩」屬於麥克風，「借出日期」就不屬於器材）。

## 常見錯誤與診斷

| 現象 | 原因 | 處理 |
|---|---|---|
| `constructor Rover in class Rover cannot be applied to given types` | 子類別建構子沒寫 `super(...)`，或參數個數不對 | 對照 B2 |
| `energy has private access in Rover` | 子類別直接存取父類別欄位 | 用 protected 方法；**不要**把欄位改成 protected |
| 子類別再宣告 `private int energy;` 之後就能編譯了 | 遮蔽：多了一份狀態 | 用 E3 的物件圖說明；`HeavyRoverCheck` 的「no hidden copies」會抓到 |
| `drill` 在達標後仍回傳 `OK` | 漏了停止檢查 | 問「誰呼叫 drill？有經過 execute 嗎？」 |
| `exact: 0/3/0` | 漏了 `countAction()` | 對照規格表 |
| 圖上三角形指向子類別 | 箭頭方向畫反 | 讀成「子類別是一種父類別」，三角形在父類別那端 |
| `HeavyRover` 方框列出所有繼承來的方法 | 以為子類別要完整列出 | 只列新增的；重複列出在之後覆寫時會分不清 |
| `ShelfTry` 編譯錯誤 `cannot find symbol: class Camera` | 太早複製 `ShelfTry` | 先寫好兩個類別 |
| `ai-check` 和 `src` 的 `HeavyRover` 互相干擾 | 把 AI 版放進 `src` | 分資料夾、各自的 `out` |
| C# 習慣寫 `: base(...)` 或 `class A : B` | 語法對照 | A 段表格 |

## 批改工具

```powershell
javac -encoding UTF-8 -d out-check <學生的 Rover、HeavyRover、MissionStep、Mission、MissionLog> code/teacher/HeavyRoverCheck.java
java -cp out-check HeavyRoverCheck
javac -encoding UTF-8 -d out-club <學生的 Equipment、Camera、Projector> code/teacher/club/ShelfCheck.java
java -cp out-club ShelfCheck
```

不要加入教師版同名類別。`HeavyRoverCheck` 共 44 項：14 組任務狀態、4 項停止規則、4 項 `Mission` 計算、5 條必做測試完整輸出逐行比對（以上與第 6 週 `MissionCheck` 相同）、3 項 protected 方法、9 項 `HeavyRover`、6 項設計檢查（繼承關係、欄位 private、子類別沒有同名欄位、三個方法 protected、行動 private、`HeavyRover` 自己的 public 方法只有 `drill`、`getDrillCount`）。`ShelfCheck` 共 11 項。

學生沒有完成 TODO 1（`Rover` 沒有 `spendEnergy` 等方法）時，`HeavyRoverCheck` 無法編譯，錯誤訊息本身就指出未完成的部分。

已用變異測試確認可抓到：`drill` 漏停止檢查（41/44）、子類別多宣告 `energy`（43/44）、`spendEnergy` 改成 public（43/44）、`spendEnergy` 不足時扣到 0 而不是拒絕（40/44）、`drill` 漏 `countAction`（40/44）、`energy` 改成 protected（43/44）、`explore` 得 3 份（任務回歸多項失敗）。AI 版 `Camera` 搭配 `ShelfCheck` 為 7/11。

## 評分重點（配合作業說明）

| 項目 | 重點 |
|---|---|
| 程式 | 欄位 private；三個 protected 方法；`drill` 有停止檢查、用常數；`HeavyRover` 沒有重複欄位 |
| 測試 | 五條與第 6 週相同；`RoverTry`、`HeavyRoverTry`、`ShelfTry` 全部 `OK` |
| AI 追蹤 | 預測寫在執行之前（看 commit 順序）；能說出「兩份狀態」；修正是最小的 |
| 類別圖 | 繼承方向、`#`、子類別只列新增成員 |
| 設計 | is-a 判斷有理由；自選器材的子類別成員合理 |
| 誠信 | 模擬回覆與真實 AI 分開；`[AI]` 與 `[fix]` commit 對得上 |

## 留給第 8 週

見 [week07_交接_claude.md](week07_交接_claude.md)。
