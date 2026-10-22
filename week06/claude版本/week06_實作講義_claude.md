# 第 6 週實作講義：先畫圖，再搬家（claude 版本）

> 本講義初稿由 Anthropic Claude（Claude Code）生成，教師審閱後使用。
> 主題：類別與物件設計(II)：UML 類別圖與物件導向設計思維——AI 輔助系統設計練習。
> 參考書：Deitel, *Java How to Program, Early Objects* 11e，1.5.10–1.5.11、3.2.4、3.3–3.4、8.8、8.11（節次見各段）。

「待填」的地方請直接寫在本檔，課後複製到 `LAB_REPORT.md`。凡是要你**先預測**的題目，請先寫預測再執行；預測錯了不扣分，但不可事後修改預測。

## 0｜準備（10 分鐘）

1. 用 GitHub Desktop 取回學期 repo `115_1_Java`（還原過的電腦請重新 clone；已有副本請先 Fetch／Pull）。
2. 建立今天的資料夾：

   ```text
   115_1_Java/YYYY_MM_DD/java-06/src/
   ```

3. 把教師提供的檔案複製到 `src/`：
   - `code/examples/` 的 `Battery.java`、`Flashlight.java`、`Charger.java`、`RelationDemo.java`
   - `code/starter/` 的 `Rover.java`、`MissionStep.java`、`Mission.java`、`MissionLog.java`、`RoverTry.java`

   **不要**複製 `code/teacher/` 的檔案（同名類別會互相衝突）。
4. 把 `DESIGN_範本_claude.md` 複製到 `java-06/`，改名為 `DESIGN.md`（與 `src` 同一層）。
5. 在 `java-06` 內編譯並執行：

   ```powershell
   javac -encoding UTF-8 -d out src/*.java
   java -cp out MissionLog
   ```

   起始碼能執行：回放是空的，摘要是 `steps=0, ok=0, rejected=0, ignored=3`，最後一行是 `final energy=20, samples=0, actionCount=0 -> UNFINISHED`。任務一步都沒跑，原因在 D 段。

6. 用 Chrome 或 Firefox 安裝 **PlantUML for GitHub** 擴充功能（由 PlantUML 官方發布）。它讓 github.com 上的 PlantUML 原始碼直接顯示成圖。教室電腦若不能安裝擴充功能，G 段有替代做法。

上週留下兩個問題，請各用一句話回答：

- 第 5 週課後選做：能源 0 的探測車被呼叫 `charge()` 會怎樣？這條規則應該由誰負責？待填
- 第 5 週課末檢核第 5 題，你畫的三個方框與箭頭（照抄即可，A、B 段會修正它）：

  ```text
  待填
  ```

## A｜讀與畫類別圖（20 分鐘，Deitel 1.5.10–1.5.11、3.2.4、3.3、8.11）

類別圖（class diagram）用方框畫出類別的**名稱、欄位、方法**，再用箭頭畫出類別之間的關係。它不畫執行順序，只畫「有哪些類別、各自知道什麼、能做什麼、和誰合作」。

| 位置 | 內容 | 寫法 |
|---|---|---|
| 第一格 | 類別名稱 | `Battery` |
| 第二格 | 欄位 | `- level : int`（可見性、名稱、冒號、型態） |
| 第三格 | 建構子與方法 | `+ use(amount : int) : boolean`（參數同樣是「名稱 : 型態」，回傳型態寫在最後） |

| 符號 | 意義 | Java 寫法 |
|---|---|---|
| `+` | public | `public` |
| `-` | private | `private` |
| `~` | 套件存取 | 不寫修飾字（第 5 週：不是 private！） |
| 底線 | static | `static`（PlantUML 寫 `{static}`） |
| `{readOnly}` | 建立後不能改 | `final` 欄位 |

注意順序：Java 與 C# 都是**型態在前**（`int level`），UML 是**名稱在前、型態在後**（`level : int`）。

PlantUML 的寫法（本課程統一用這種文字格式，放進 `.md` 的 ```` ```plantuml ```` 區塊）：

```text
@startuml
skinparam classAttributeIconSize 0
hide circle

class Battery {
  - capacity : int {readOnly}
  - level : int
  + Battery(capacity : int, level : int)
  + use(amount : int) : boolean
}
@enduml
```

前兩行設定讓 `+`、`-` 顯示成符號而不是彩色圖示，並隱藏類別名稱旁的圓形標記；每張圖都照抄即可。

A1. 對照 `src/Battery.java`，把程式寫成類別圖的一行：

| Java 程式 | 類別圖的一行 |
|---|---|
| `private final int capacity;` | `- capacity : int {readOnly}` |
| `private int level;` | 待填 |
| `public Battery(int capacity, int level)` | 待填 |
| `public int getLevel()` | 待填 |
| `public int charge(int amount)` | 待填 |

A2. 下面是第 5 週 `Rover` 的類別圖（節錄）。**只看圖、不看程式**，預測在另一個類別裡寫這些程式能不能編譯（本課程的圖會列出所有建構子）：

```text
Rover
  + CAPACITY : int = 20     （有底線＝static）
  - energy : int
  + Rover(energy : int, samples : int)
  + explore() : String
```

| 程式 | 預測：能／不能，理由 |
|---|---|
| `rover.energy = 5;` | 待填 |
| `rover.explore();` | 待填 |
| `int max = Rover.CAPACITY;` | 待填 |
| `Rover.explore();` | 待填 |
| `Rover r = new Rover();` | 待填 |

A3. 打開 `DESIGN.md` 的「練習區」，照著 `src/Flashlight.java` 寫出 `Flashlight` 的 PlantUML 方框（欄位與方法都要）。G 段推上 GitHub 後再看圖是否正確。

## B｜關係與多重性（20 分鐘，Deitel 8.8）

開啟 `Flashlight.java`、`Charger.java`、`RelationDemo.java`。兩支手電筒 A、B 拿到**同一顆**電池，C 有自己的電池。**先預測**再執行 `java -cp out RelationDemo`：

| 輸出行 | 預測 | 實際 |
|---|---|---|
| `A on 6 min:` | 待填 | 待填 |
| `B on 6 min:`（第一次） | 待填 | 待填 |
| `C on 6 min:` | 待填 | 待填 |
| `shared level:`（第一次） | 待填 | 待填 |
| `refill shared: +` | 待填 | 待填 |
| `B on 6 min:`（第二次） | 待填 | 待填 |
| `shared level:`（第二次） | 待填 | 待填 |

B1. B 第一次為什麼開不了燈？C 為什麼不受影響？（提示：第 5 週的 `alias`。）待填

兩種最常用的關係：

| 程式裡的樣子 | 關係 | 圖上的畫法 | 例子 |
|---|---|---|---|
| 存在**欄位**裡，物件活多久就一直帶著 | 關聯（association） | 實線箭頭 `-->` | `Flashlight` 的 `battery` 欄位 |
| 只出現在**參數、區域變數**或 `new` 之後不保存，方法結束就不用了 | 相依（dependency） | 虛線箭頭 `..>` | `Charger.refill(Battery battery)` |

箭頭從「用的人」指向「被用的」。關聯的兩端可以寫**多重性**（multiplicity）：

```text
Flashlight "0..*" --> "1" Battery : battery
```

讀法：從 `Flashlight` 看，一支手電筒連到 **1** 顆電池；從 `Battery` 看，一顆電池可以被 **0 到多支**手電筒使用。常用寫法：`1`、`0..1`、`0..*`（也寫成 `*`）、`1..*`、`0..2`。

B2. 判斷關係，填「關聯」或「相依」，並寫出程式證據（哪一行）：

| 從 → 到 | 關係 | 證據 |
|---|---|---|
| `Flashlight` → `Battery` | 待填 | 待填 |
| `Charger` → `Battery` | 待填 | 待填 |
| `RelationDemo` → `Flashlight` | 待填 | 待填 |

B3. 在 `RelationDemo` 裡，`shared` 那顆電池被幾支手電筒使用？這和 `"0..*"` 有什麼關係？待填

B4. 第 5 週的 `MissionLog` **沒有任何欄位**，它在 `main` 裡 `new Rover(...)`，再把 `rover`、`steps` 當參數傳給其他 static 方法。所以 `MissionLog` 和 `Rover` 是什麼關係？修正你在 0 段抄下的草圖：

```text
待填
```

**認識即可：聚合與組合。** 關聯還可以再說明「整體與部分」：

| 畫法 | 名稱 | 本課程的判斷方式 |
|---|---|---|
| 空心菱形 `o--` | 聚合 | 部分在外面建立後交進來，可以被別人共用（`Flashlight` 與共用的 `Battery`） |
| 實心菱形 `*--` | 組合 | 整體自己 `new` 出部分，不把部分交給別人擁有，整體不在了部分也沒用（`Mission` 與它的 `MissionStep`） |

Java 沒有「組合」這個關鍵字：判斷要看程式**誰 new、存在哪、有沒有交出去**。分不清楚時，畫普通的實線關聯加上多重性就是正確的。

B5. `Flashlight` 和 `Battery` 為什麼不能畫成實心菱形？用 `RelationDemo` 的一行程式說明：待填

## C｜責任與設計思維（15 分鐘）

畫類別圖不只是把程式翻譯成方框，更是在決定**每件事該由誰負責**。本週用三個原則檢查設計：

1. **資料在誰身上，規則就放誰身上**：能源是 `Rover` 的欄位，「能源 0 不能再行動」就該由 `Rover` 檢查。
2. **一個類別一種主要工作**：方法都在處理同一件事，類別才好讀、好改。
3. **只公開別人需要的**：多一個 public 方法，就多一條繞過規則的路。

C1. 第 5 週的 `Rover` 能被這樣呼叫：

```java
Rover r = new Rover(0, 0);
System.out.println(r.charge());
```

會印出什麼？能源變多少？任務規則說能源耗盡就結束，第 5 週是誰擋下這種情況？如果有人寫了另一個類別直接呼叫 `charge()`，還擋得住嗎？待填

C2. 第 5 週 `MissionLog` 做了下面這些事。假設下學期要把輸出改成 WinForms 視窗（你大一寫過的那種），勾出「改成視窗也**完全不用改**」的項目，它們屬於任務本身：

- [ ] 把命令列參數轉成 `int[]`
- [ ] 依腳本一步一步執行、記錄
- [ ] 判斷 SUCCESS／EXHAUSTED／RETURNED／UNFINISHED
- [ ] 計算 counts、ok、rejected、最低能源
- [ ] 把結果印到螢幕

本週的新設計：勾到的項目搬到新類別 `Mission`，`MissionLog` 只留下讀參數、建立物件、印出結果。換成視窗時，只要換掉 `MissionLog`，`Mission` 可以原封不動地使用。

C3. 完成新設計的責任表（`Mission` 那列已示範）：

| 類別 | 知道什麼（欄位） | 負責做什麼 | 合作對象 |
|---|---|---|---|
| `Rover` | 待填 | 待填 | 無 |
| `MissionStep` | 待填 | 記錄一步，提供回放文字 | 無 |
| `Mission` | 這次任務的 `rover`、每一步紀錄 `steps` | 依腳本執行並記錄、判斷結果、計算摘要數字 | `Rover`、`MissionStep` |
| `MissionLog` | 無 | 待填 | 待填 |

對照新的類別圖 `diagrams/week06_mission_claude.svg`（教師投影）：`Mission` 用欄位保存 `rover` 與 `steps`，所以是實線；`MissionLog` 只在方法裡用到 `Mission`、`Rover`，以及 `getStep(i)` 交回來的 `MissionStep`，所以都是虛線。

C4. **先預測**：`Rover` 收回停止規則後，`execute` 遇到停止會回傳 `"STOPPED"`。那 `Mission.run` 的迴圈裡還需要檢查 `rover.isStopped()` 嗎？拿掉會怎樣？預測：待填（D5 實測）

## D｜依新圖重構（40 分鐘）

本週專案有 9 個檔案、3 個 `main`，用 IntelliJ IDEA 開啟 `java-06` 資料夾（操作同第 5 週 C 段：`src` 為 Sources Root、SDK 選已安裝的 JDK、詢問建立 Git repository 時選否）。VS Code 仍可使用。

任務的輸出格式、規則與第 5 週**完全相同**；改變的是程式放在哪個類別。

| TODO | 檔案 | 內容 | 方式 |
|---|---|---|---|
| 1 | `Rover` | `isStopped()`：成功或耗盡 | 跟做 |
| 2 | `Rover` | `execute` 開頭擋下停止狀態；三個行動改為 `private` | 跟做 |
| 3 | `Mission` | `run`：第 5 週 `runMission` 的迴圈搬過來 | 跟做 |
| 4 | `Mission` | `getOutcome`：四段判斷搬過來 | 模仿 |
| 5 | `Mission` | `countOk`、`countRejected` | 模仿 `countCommands` |
| 6 | `Mission` | `lowestEnergy`：沒有紀錄回傳 -1 | 獨立 |
| 7 | `DESIGN.md` | 類別圖與完成後的程式一致 | 獨立 |

`MissionLog` 與 `MissionStep` 已完成，不必修改。

D1. 動手前先執行 `java -cp out RoverTry`，有幾行 `DIFF`？為什麼 `done`（一開始就有 5 份樣本）會變成 `14/7/1`？待填

### D2. TODO 1–2：Rover（跟做）

1. `isStopped()`：用 `isSuccess()` 與 `isExhausted()` 組合。
2. `execute` 的第一件事：已停止就回傳 `"STOPPED"`，不進入 `switch`。
3. 把 `explore`、`scan`、`charge` 的 `public` 改成 `private`，重新編譯。在 IntelliJ 對 `explore` 按 Alt＋F7（Find Usages），確認只有 `execute` 用到它。

再執行 `RoverTry`，每一行都應是 `OK`。

D2-1. 改成 `private` 之後，類別圖上這三個方法的符號要從什麼改成什麼？為什麼這讓 TODO 2 的檢查「不會被繞過」？待填

### D3. TODO 3–4：Mission（跟做＋模仿）

把第 5 週 `MissionLog.runMission` 的內容搬到 `Mission.run`，對照兩者的方法開頭：

```java
// 第 5 週：MissionLog
static String runMission(int[] script, Rover rover, ArrayList<MissionStep> steps)
// 第 6 週：Mission
public void run(int[] script)
```

D3-1. `rover` 和 `steps` 為什麼不用再當參數傳進來？`run` 為什麼不再回傳結果字串，結果改由誰提供？待填

D3-2. 迴圈中的停止判斷改用 `rover.isStopped()`。判斷結果的四段 `if` 搬到 `getOutcome()`，順序不能改：為什麼 SUCCESS 要排在 EXHAUSTED 前面？（提示：`new Rover(2, 4)` 執行掃描。）待填

### D4. TODO 5–6：摘要數字（模仿＋獨立）

`countOk`、`countRejected` 模仿 `countCommands` 走訪 `steps`。`lowestEnergy` 沒有紀錄時回傳 -1，`MissionLog` 只在有紀錄時才印它。

D4-1. `Mission` 為什麼提供 `getStepCount()` 與 `getStep(int index)`，而不是直接提供 `getSteps()` 交出整條清單？寫出一行「拿到清單後就能破壞紀錄」的程式：待填

完成後執行五條必做測試，輸出必須與第 5 週**逐字相同**：

| 參數 | 最後一行 |
|---|---|
| （無） | `final energy=6, samples=5, actionCount=3 -> SUCCESS` |
| `2 2 2 2 2` | `final energy=10, samples=5, actionCount=5 -> SUCCESS` |
| `3 9 0` | `final energy=20, samples=0, actionCount=0 -> RETURNED` |
| `1 1 1 1` | `final energy=2, samples=6, actionCount=3 -> SUCCESS` |
| `2 1` | `final energy=12, samples=3, actionCount=2 -> UNFINISHED` |

摘要行與最低能源見作業說明的表格。

### D5. 實測 C4（5 分鐘）

先 commit 目前完成的程式。在 `Mission.run` 中暫時把「已停止就 break」的三行註解掉，重新編譯，執行 `java -cp out MissionLog 1 1 1 1`：

- 回放多了哪一步？摘要哪些數字改變？待填
- C4 的預測對嗎？結論：停止後「不讀、不記」是誰的規則？`STOPPED` 擋下的又是什麼？待填

做完**還原那三行**，重新編譯並再跑一次 `1 1 1 1`，確認回到 3 步。

### D6. TODO 7：讓圖跟程式一致

在 `DESIGN.md` 第 1 節完成任務系統的類別圖：四個類別的欄位與方法都要列出，可見性與 static 要和程式一致，並畫出關係與多重性。完成後逐項打勾：

- [ ] 每個欄位、建構子、方法都在圖上，圖上也沒有程式裡不存在的東西
- [ ] `+`、`-`、`~` 與程式的修飾字一致；static 有 `{static}`；`final` 欄位有 `{readOnly}`
- [ ] 欄位保存的用實線，只在方法裡用到的用虛線
- [ ] `Mission` 到 `Rover`、`Mission` 到 `MissionStep` 有多重性

## E｜AI 輔助系統設計（25 分鐘）

流程：**需求 → 自己的責任表與類別圖 → 看 AI 的類別圖 → 逐項判斷 → 決定採納什麼**。先自己設計，才有東西能檢查 AI。

任務中心的新需求：

> 探測車加裝第 5 週設計的樣本艙 `SampleBay`，本次任務容量 4。
> 1. 探索得 2 份、掃描得 1 份，樣本改為存進樣本艙。能源不足時照舊回傳 `NOT_ENOUGH_ENERGY`；能源足夠但艙內放不下時回傳 `BAY_FULL`。兩種拒絕都不改變任何狀態。
> 2. 新增指令 4「卸貨」：把艙內樣本全部送回基地，耗能 1、行動次數 +1；艙是空的時回傳 `BAY_EMPTY`，不耗能。
> 3. 任務成功改為「已送回基地的樣本」累計達 5 份。
> 4. 任務中心要能查詢能源、艙內樣本數、已送回樣本數、行動次數，但不能直接修改它們。
> 5. 任務日誌與停止規則照舊。

### E1. 自己先設計（10 分鐘，先在紙上畫）

回答下面四個問題，再畫出類別圖（只畫有變動的 `Rover`、`SampleBay`，以及它們與 `Mission` 的關係）：

1. `Rover` 原本的 `samples` 欄位還需要嗎？為什麼？待填
2. 「已送回樣本數」放在哪個類別？為什麼？待填
3. `Rover` 與 `SampleBay` 誰知道誰（箭頭方向）？多重性是多少？待填
4. 「放不下就拒絕」由誰檢查？要在扣能源之前還是之後？待填

| 類別 | 知道什麼（欄位） | 負責做什麼 | 合作對象 |
|---|---|---|---|
| `Rover` | 待填 | 待填 | 待填 |
| `SampleBay` | 待填 | 待填 | 待填 |

課後把紙上的圖寫成 PlantUML，放進 `DESIGN.md` 第 2 節。

### E2. 檢查模擬 AI 的類別圖（10 分鐘）

有位同學只輸入「幫我畫探測車加樣本艙的類別圖」，以下是**教師設計的模擬 AI 回覆**（不是真實對話），圖檔見 `diagrams/samplebay_ai_claude.svg`：

```text
@startuml
skinparam classAttributeIconSize 0
hide circle
left to right direction

class Mission {
  - rover : Rover
  - steps : ArrayList<MissionStep>
  + run(script : int[]) : void
  + getSteps() : ArrayList<MissionStep>
  + getOutcome() : String
}
class Rover {
  - energy : int
  - samples : int
  - actionCount : int
  + execute(command : int) : String
  + unload() : String
  + isSuccess() : boolean
}
class SampleBay {
  - capacity : int
  + count : int
  + store(amount : int) : boolean
  + explore() : String
}
class MissionStep {
  - command : int
  - result : String
  - rover : Rover
}

Mission --> "1" Rover
Mission --> "0..*" MissionStep
Rover "1" --> "0..*" SampleBay : bays
MissionStep --> Rover
@enduml
```

對照需求與你的設計，找出至少四處問題：

| # | 問題（圖上哪裡） | 違反哪一句需求或哪個原則 | 會造成什麼錯（舉一個情境或呼叫） |
|---|---|---|---|
| 1 | 待填 | 待填 | 待填 |
| 2 | 待填 | 待填 | 待填 |
| 3 | 待填 | 待填 | 待填 |
| 4 | 待填 | 待填 | 待填 |

### E3. 寫一個設計用的 Prompt（5 分鐘＋課後）

把 E1 的責任表放進 Prompt，並要求 AI：輸出 PlantUML 類別圖原始碼；欄位一律 private；標出關係與多重性；不使用繼承與介面（第 7–9 週才教）；**另外列出它的設計理由，以及需求中它不確定的地方**。

```text
待填
```

若你用真實 AI 工具送出這個 Prompt，請把它的圖貼進 `DESIGN.md` 第 2 節，逐項寫出採納或不採納的理由，並記在 `AI_USAGE.md`；不要把上面的模擬回覆寫成真實對話。

## F｜課末檢核（5 分鐘）

1. 類別圖上同時出現 `- steps : ArrayList<MissionStep>` 和 `+ getSteps() : ArrayList<MissionStep>`，封裝出了什麼問題？待填
2. 實線箭頭與虛線箭頭，在程式上各對應什麼寫法？待填
3. `Mission --> "1" Rover` 的 `1` 是什麼意思？待填
4. 本週你把哪一條規則換了位置？從哪個類別搬到哪個類別？為什麼？待填
5. 下週預告：如果要加入「重型探測車」，探索一次得 3 份樣本，其他規則都和 `Rover` 一樣。你會複製一份 `Rover` 改成 `HeavyRover` 嗎？可能有什麼麻煩？待填

## G｜保存、推送、在 GitHub 看圖（15 分鐘）

- [ ] 重新編譯並跑一次 `3 9 0` 與 `RoverTry`；`LAB_REPORT.md`、`AI_USAGE.md`、`java-06/README.md`、`java-06/DESIGN.md`、`src/` 齊全；不交 `out/`、`.class`、`.idea/`、`*.iml`
- [ ] GitHub Desktop 檢查差異；commit 訊息使用 `[manual]`／`[AI]`／`[fix]`／`[doc]` 前綴
- [ ] Push origin，在 GitHub 網頁打開 `java-06/DESIGN.md`：裝了擴充功能的瀏覽器，PlantUML 區塊應顯示成圖；擴充功能加的標題列可切換「圖／原始碼」
- [ ] 在另一個路徑全新 clone，於 `java-06` 重新編譯並跑 `3 9 0` 與 `RoverTry`；不要刪除原本的副本

看不到圖時：

- 沒裝擴充功能時，GitHub 只會顯示原始碼，這是正常的；換一台能安裝的電腦，或回家再看。
- 有裝卻顯示錯誤：通常是少了 `@startuml`／`@enduml`、大括號沒有成對，或程式碼區塊標記不是 `plantuml`。
- GitHub Desktop 與 VS Code 的預覽不會顯示 PlantUML 圖，這不是你的錯。

最新 commit 訊息與時間：待填
`DESIGN.md` 在 GitHub 上是否顯示成圖（是／否，用哪個瀏覽器）：待填
全新 clone 路徑與執行結果：待填
未完成事項（課後補）：待填

推送失敗時：先把整個 repo 複製到隨身碟或雲端，確認檔案打得開，再請教師協助；不要直接關機。

## 課後選做

- 依你在 E1 的設計，實作有樣本艙的探測車（可以從第 5 週延伸題的 `SampleBay` 開始），並用 `MissionLog` 測試 `1 1 4 1 1 4`。先寫出你預期的回放，再執行比對。
- 若你的 IntelliJ IDEA 有內建 Diagrams 功能（Ultimate 或教育授權版本），在 `Mission.java` 按右鍵 → Diagrams → Show Diagram，和你的 `DESIGN.md` 比較：工具自動產生的圖與你畫的圖，哪些地方不同？哪一張比較適合跟別人討論設計？
