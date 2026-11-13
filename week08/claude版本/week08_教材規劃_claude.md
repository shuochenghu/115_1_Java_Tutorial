# 第 8 週教材規劃（claude 版本）

> 本規劃初稿由 Anthropic Claude（Claude Code）撰寫，供教師在製作第 8 週 claude 版教材前確認方向。**這是規劃，不是已完成的教材**：本目錄目前只有本檔；下文的檔名、TODO 編號、輸出格式都是提案，正式製作時再驗證與調整。Codex 版若有製作，放在 `week08/codex版本/`，兩版獨立。

## 1. 定位

| 項目 | 內容 |
|---|---|
| 課綱主題 | 繼承(II)：覆寫、多型基礎與設計實務——Code Review：審查 AI 生成的繼承程式碼 |
| 日期 | 115 學年度第 1 學期第 8 週（11/01～11/07）；學生資料夾 `115_1_Java/YYYY_MM_DD/java-08/`，日期以實際上課日替換 |
| 承接 | 第 7 週 claude 版（`week07/claude版本/week07_交接_claude.md`） |
| 下一週 | 第 9 週：抽象類別與介面（含巢狀類別、匿名類別概念） |
| 參考書 | Deitel《Java How to Program, Early Objects》11e：9.4（子類別覆寫 `toString`、以 `super.` 呼叫父類別版本）、9.6（`Object` 的方法）、10.1–10.3（多型與示範）、10.5（動態綁定）、10.6（父類別與子類別變數的指定規則）、10.7（`final` 方法與類別）。章節名稱已用本專案 EPUB 目錄核對；小節頁內細節製作時再對照，不寫頁碼 |
| 時間 | 沿用三節各 50 分鐘（休息另計）的備課配置，最後 15 分鐘保存推送；尚未試教 |

## 2. 先備能力（第 7 週交接）

可承接：

- `extends`、子類別只寫新增成員；建構子第一行 `super(...)`；private 成員存在於子類別物件但不能用名字存取。
- `private`／`protected`／`public`，以及 Java `protected` 也開放給同一套件。
- 所有類別繼承 `Object`，`println(物件)` 印出 `類別名@雜湊碼`。
- is-a／has-a 判斷；類別圖空心三角形與 `#`。
- 追蹤 AI 子類別：缺 `super`、欄位重複宣告造成兩份狀態、新入口漏停止檢查。

開場要診斷、不能假設已熟練（第 7 週交接列出的風險）：

1. 欄位遮蔽與方法覆寫容易混為一談。
2. 以為 `protected` 只給子類別。
3. `super(...)` 的位置規則（本課程規定寫在第一行）。

另外，第 7 週課末檢核第 5 題已讓學生預測「`new Mission(new HeavyRover(20, 0))` 裡指令 1 仍得 2 份，只靠新增方法做不到得 3 份」——這就是本週入口。

## 3. 本週新增能力與邊界

### 學生在本週結束時應能

| # | 能力 | 主要證據（提案） |
|---|---|---|
| 1 | 說明覆寫：子類別寫出與父類別**同名、同參數、相容回傳型態**的實例方法，取代父類別版本 | 講義 A、TODO 1 |
| 2 | 使用 `@Override`，並說明它只是讓編譯器檢查，不加也會覆寫，但拼錯時不會報錯 | 講義 A3 實驗 |
| 3 | 用 `super.方法()` 沿用父類別版本再加上自己的部分 | 講義 A2、TODO 2 |
| 4 | 覆寫 `toString`，讓 `println(物件)` 有意義 | 講義 B、TODO 2 |
| 5 | 多型基礎：父類別型態的變數可以指向子類別物件；**能呼叫什麼看變數型態，執行哪個版本看物件本身** | 講義 C、D3 |
| 6 | 說明為什麼 `Mission` 一行都不改，傳入 `HeavyRover` 後探索就得 3 份 | 講義 D3、作業必做測試 |
| 7 | 用審查清單 Code Review AI 生成的子類別，寫出有證據（編譯訊息或測試輸出）的審查意見，做最小修正並重測 | 講義 E、作業第 3 部分 |
| 8 | 說出 C# 與 Java 的差異：Java 實例方法預設可覆寫（不需 `virtual`）；`@Override` 是標註不是關鍵字；`final` 禁止覆寫；`super.` 對應 C# `base.` | 講義 A4 |

### 本週刻意不教（避免提前負擔）

- 抽象類別、抽象方法、介面、匿名類別：第 9 週。本週會「製造需求」（見第 8 節），但不給語法。
- 多載（overloading）的完整規則：第 10 週。本週只講到「參數不同就不是覆寫，是另一個方法」，作為 `@Override` 的動機。
- `instanceof` 與向下轉型：只在教師筆記與延伸題出現。主線的多型寫法不需要它，且「一長串 `instanceof` 判斷」本身是審查清單上的壞味道。不使用 `instanceof` 樣式比對（JDK 16 起），避免依賴版本。
- `equals`／`hashCode` 覆寫：概念牽涉集合契約，留給第 11 週泛型或之後；本週只覆寫 `toString`。
- 建構子呼叫可覆寫方法的陷阱（Deitel 10.8）：教師筆記補充，不放進學生必做。

## 4. 主線設計決定

### 4.1 兌現第 6 週預告：重型探測車探索一次得 3 份

`Rover.explore` 維持 private，停止檢查、耗能 6、行動計數都不動。`Rover` 新增一個可覆寫的「產量」方法：

```text
Rover
  # exploreYield() : int        回傳 2；explore() 改成 addSamples(exploreYield())
  + toString() : String         "能源=E, 樣本數=S, actions=A"

HeavyRover extends Rover
  # exploreYield() : int        @Override，回傳 3
  + toString() : String         @Override，"HEAVY " + super.toString() + ", drills=D"
```

理由：

- 子類別只改「產量」這一個會變的數字，能源與停止規則仍只寫在 `Rover`，延續第 6、7 週「資料在誰身上，規則就放誰身上」。
- 與 Codex 版第 7 週交接建議的入口相同（`protected int exploreYield()`），兩版的下一步不會互相矛盾；但本版仍獨立製作與驗證。
- **不覆寫 `execute` 來加指令 4（鑽探）**。`Mission.countCommands()` 只統計 0–3，`MissionLog` 報表也依 0–3 設計；加指令 4 會同時改動輸入、計數陣列與報表契約。反過來，「覆寫 `execute` 的 AI 寫法」正好拿來當 Code Review 素材（見 4.3）。若教師希望學生做指令 4，放在延伸題，並在作業中重新界定整組契約。

### 4.2 多型：`Mission` 不必改

本週不修改 `Mission`、`MissionStep`、`MissionLog`（只改檔頭說明）。新增一個比較程式（暫名 `MissionCompare`）用同一份腳本分別跑 `Rover` 與 `HeavyRover`，再用 `ArrayList<Rover>` 裝兩台車逐台 `execute(1)` 與 `println`。`ArrayList<型態>` 第 4、5 週已使用，這裡只當容器，不講泛型設計。

已在暫存目錄用原型（第 7 週教師版加上 4.1 的兩個覆寫）實測，JDK 22：

| 起始 | 腳本 | 結果 | 最後狀態（`toString`） |
|---|---|---|---|
| `Rover(20, 0)` | `1 1 1 0` | `任務成功`，3 步 | `能源=2, 樣本數=6, actions=3` |
| `HeavyRover(20, 0)` | `1 1 1 0` | `任務成功`，2 步 | `HEAVY 能源=8, 樣本數=6, actions=2, drills=0` |
| `HeavyRover(6, 4)` | `1` | `任務成功`，1 步（同時耗盡，成功優先） | `HEAVY 能源=0, 樣本數=7, actions=1, drills=0` |
| `HeavyRover(5, 4)` | `1 0` | `已返回`，2 步（能源不足被拒） | `HEAVY 能源=5, 樣本數=4, actions=0, drills=0` |
| `HeavyRover(20, 5)` | `1` | `任務成功`，0 步（一開始就已停止） | `HEAVY 能源=20, 樣本數=5, actions=0, drills=0` |
| `HeavyRover(20, 0)` | `3 2 1 1` | `任務成功`，4 步（充電 `能源已滿` 被拒） | `HEAVY 能源=6, 樣本數=7, actions=3, drills=0` |

另確認：`Rover x = new HeavyRover(20, 0); x.drill();` 編譯錯誤 `cannot find symbol`——這是「能呼叫什麼看變數型態」的現場證據。正式製作時要在乾淨目錄重新驗證，並與第 7 週 `MissionLog` 逐字比對（`Rover` 的輸出不得改變）。

### 4.3 Code Review：審查 AI 生成的繼承程式碼

第 7 週 E 段練的是「追蹤」；本週升級為「審查」：學生拿到一份模擬 AI 寫的 `HeavyRover`，依清單逐項檢查，每條意見都要附證據。原型已確認下列缺陷的實際表現：

| 預埋缺陷 | 實際表現（JDK 22 原型） | 對應清單項目 |
|---|---|---|
| 方法名拼錯成 `exploreYeild` 且沒加 `@Override` | **能編譯**，`HeavyRover(20,0)` 跑 `1 1 1 0` 仍要 3 步、每次得 2 份 | 覆寫簽章是否真的對上？有沒有 `@Override`？ |
| 同上但有加 `@Override` | `method does not override or implement a method from a supertype` | （對照組：示範 `@Override` 的價值） |
| 覆寫改成 `private` | `attempting to assign weaker access privileges; was protected` | 覆寫不能縮小存取範圍 |
| 覆寫 `execute`，自己重寫指令 1（`spendEnergy(6)`、`addSamples(3)`、`countAction()`），其他 `super.execute(command)` | **能編譯**；`HeavyRover(20,5)` 已成功仍可探索，回傳 `成功`，樣本變 8——繞過了父類別的停止檢查 | 是否在子類別重複父類別規則？覆寫後父類別的保護還在嗎？ |

正式的模擬 AI 檔建議只放 2–3 個缺陷（例如「拼錯＋無 `@Override`」與「覆寫 `execute` 重複規則、漏停止檢查」），另外一個做成講義中的小實驗，避免學生在一個檔案裡找太多錯而變成挑錯遊戲。

審查清單（學生版，6 項以內）：

1. 每個打算覆寫的方法都有 `@Override` 嗎？簽章（名稱、參數、回傳）和父類別一致嗎？
2. 存取範圍有沒有比父類別小？
3. 子類別有沒有重寫父類別已經有的規則（能源、停止、計數）？能不能只覆寫「會變的那一小塊」？
4. 覆寫後，父類別原本的檢查（停止、範圍）還會執行嗎？
5. 是否符合 is-a？是否重複宣告父類別的欄位（第 7 週）？
6. `toString` 等輸出是否用 `super.toString()` 沿用，而不是把父類別欄位再組一次？

審查意見格式（`REVIEW.md`，欄位少）：位置／問題／依據（清單第幾項）／證據（編譯訊息或測試輸出）／建議修正／是否採納。

同儕互評：課堂上兩人一組交換彼此的 `HeavyRover`（TODO 完成後），各寫 2 條意見到對方的 `REVIEW.md` 草稿，口頭交換；不要求在 GitHub 上留言。若教師想用 GitHub 的 commit 留言功能，製作時查官方文件確認操作步驟後再列為選做。

### 4.4 輔助短篇：飲料店會員（沿用第 7 週範例）

第 7 週的 `Member` 已有 `describe()`，`VipMember` 直接繼承。本週 `VipMember` 以 `@Override` 改寫成 `super.describe() + " 獎勵次數=" + bonusCount`。這回收第 7 週 A3 留下的「`bob.describe()` 沒有 VIP 資料」與 B5 的 `Member@雜湊碼`。範例要讓學生先預測，再加 `toString` 看 `println(bob)` 變化。範例只新增「覆寫」這一項負擔，不新增業務規則。

### 4.5 作業短篇：社團器材（沿用第 7 週作業）

第 7 週 `Camera`、`Projector` 各有一個父類別沒有的 `checklist()`，因此無法用 `Equipment` 的參考統一呼叫。本週：

- `Equipment` 新增 `checklist()`，預設回傳 `getLabel()`。
- 學生讓 `Camera`、`Projector` 以 `@Override` 覆寫，用 `super.checklist()` 沿用標籤。
- 用 `ArrayList<Equipment>` 列印整排器材的核對清單：一個迴圈、不判斷型別。
- 反思題：「`Equipment` 的預設 `checklist()` 對一台『還沒分類的器材』有意義嗎？能不能禁止別人 `new Equipment(...)`？」——留給第 9 週抽象類別回答，本週只寫想法。

## 5. 課堂流程（150 分鐘，提案）

| 分鐘 | 段落 | 內容 | 落後時 |
|---|---|---|---|
| 0–10 | 0 準備＋診斷 | 取回 repo、建 `java-08`；3 題快速診斷（遮蔽 vs 覆寫、`protected` 範圍、上週第 5 題） | 只問第 3 題 |
| 10–35 | A 覆寫與 `@Override` | 會員範例：先預測 `describe()`，再覆寫；A3 拼錯實驗（有／無 `@Override`）；A4 C# 對照表（`virtual`/`override`/`base`/`sealed` ↔ Java） | A4 表格課後讀 |
| 35–45 | B `toString` | `println(bob)` 前後對照；`super.toString()` | 示範一次即可 |
| 45–60 | C 多型基礎 | `Member m = new VipMember(...)`：預測哪些呼叫可編譯、執行哪個版本；`ArrayList<Member>` 一個迴圈 | 只做預測表前半 |
| 60–95 | D 重型探測車 | TODO 1 `exploreYield`（跟做）、TODO 2 `toString`（模仿）、D3 跑 `MissionCompare` 看 `Mission` 不改、TODO 3 更新類別圖（課後可補） | 先確保 TODO 1–2 通過檢查程式 |
| 95–130 | E Code Review | 讀模擬 AI 檔→依清單預測→編譯→測試→寫 `REVIEW.md`→最小修正→重測；最後 10 分鐘兩人互評 | 課綱主題，不壓縮；互評可改課後 |
| 130–135 | F 課末檢核 | 口頭 5 題，最後一題預告第 9 週 | |
| 135–150 | G 保存推送 | `[AI]` commit 原文、`[fix]` commit 修正，GitHub 上看兩次 commit 差異；全新 clone 後重編譯 | **不可挪用** |

工具：本週專案超過三個檔案，沿用 VS Code 編譯執行，並在 IntelliJ 示範與覆寫有關的功能（行號旁的覆寫圖示、Ctrl＋O 產生覆寫方法、Ctrl＋H 階層）。快捷鍵與圖示製作時以實際 IntelliJ 版本確認；IDE 自動產生的 `@Override` 方法也要學生讀過、能解釋。

## 6. 學生負擔評估

| 類型 | 內容 | 必做／選做 | 評估 |
|---|---|---|---|
| 引導 | 會員範例 A–C（預測後執行） | 必做 | 只新增覆寫一項概念，三個小實驗 |
| 跟做＋模仿 | TODO 1–2（兩個短方法） | 必做 | 程式量小，重點在「為什麼只覆寫產量」 |
| 獨立 | 作業 `Camera`／`Projector` 覆寫 `checklist`＋整排列印 | 必做 | 和課堂結構相同但換情境，非換數字 |
| 審查 | E 段 Code Review＋`REVIEW.md` | 必做 | 課綱主題；格式固定、欄位少 |
| 同儕 | 兩人互評各 2 條意見 | 必做（課堂） | 10 分鐘，可課後補 |
| 遷移 | 自選另一種探測車只覆寫一個方法（例如 `ScoutRover` 掃描得 2 份需新增 `scanYield`） | 選做 | 讓學生自己判斷父類別要多開哪個「掛鉤」 |
| 延伸 | 覆寫 `execute` 加指令 4 鑽探，並自訂計數與報表契約 | 選做 | 會碰到 `countCommands` 只統計 0–3，需自行寫測試 |

整體比第 7 週輕一點（第 7 週有 5 個 TODO 與 2 個模擬 AI 檔），因為本週新增的審查與互評需要時間。作業不再出第二份模擬 AI 檔，改為審查同學的程式。

## 7. 預定交付檔案（`week08/claude版本/`）

| 檔案 | 內容 |
|---|---|
| `README_claude.md` | 目標、故事線、檔案索引、執行指令、與前週一致性 |
| `week08_slides_claude.md`／`.html` | Marp，約 35–40 頁，每頁 `by-claude`；沿用第 7 週樣式與範例檔標籤 |
| `week08_實作講義_claude.md` | 0、A–G 段與課後選做，含預測／實際欄位 |
| `week08_作業說明_claude.md` | 重型探測車必做測試、社團器材覆寫、Code Review、設計文件、繳交與驗收 |
| `week08_教師筆記_claude.md` | 時間配置、全部解答與實際輸出、常見誤解、批改工具、評分重點 |
| `REVIEW_範本_claude.md` | 學生 `REVIEW.md` 範本（審查清單＋意見表），含一則標示「示範」的填寫例 |
| `DESIGN_範本_claude.md` | 沿用第 7 週，加一節「覆寫了哪些方法、為什麼」 |
| `AI使用紀錄表_範本_claude.md` | 本週欄位；區分模擬 AI 檔、教師示範、真實 AI 對話 |
| `week08_交接_claude.md` | 給第 9 週的能力與資料契約 |
| `week08_驗證紀錄_claude.md` | 實測結果與未驗證項目 |
| `code/examples/` | `Member`（沿用，加 `toString`）、`VipMember`（覆寫 `describe`、`toString`）、`OverrideDemo` |
| `code/starter/` | `Rover`（TODO 1–2）、`HeavyRover`（TODO 1–2 的子類別部分）、沿用的 `Mission`／`MissionStep`／`MissionLog`、`MissionCompare`、`HeavyRoverTry`；可編譯、安全結束，佔位結果以 `不符` 標出 |
| `code/starter/club/` | `Equipment`（加預設 `checklist`）、`ShelfTry` |
| `code/ai/` | 模擬 AI 的 `HeavyRover`（刻意含錯，不是參考答案） |
| `code/teacher/`、`code/teacher/club/` | 完整解答與檢查程式（延續 `HeavyRoverCheck`、`ShelfCheck` 的寫法） |
| `diagrams/*.puml`／`.svg` | 覆寫後的類別圖（子類別重列被覆寫的方法）、模擬 AI 的錯誤設計圖 |

TODO 編號在製作時統一，講義、starter、教師筆記、投影片的檔案對照必須一致；`starter`、`teacher`、`ai` 同名類別分開編譯。

## 8. 給第 9 週的入口（預計寫入交接）

- **社團器材**：`Equipment` 的預設 `checklist()` 沒有意義、`new Equipment(...)` 不該被允許 → 抽象類別與抽象方法。
- **探測車**：`exploreYield` 這類「留給子類別填的掛鉤」→ 抽象方法；若要讓 `Mission` 也能接「非探測車」的任務單位（例如無人機）→ 介面。
- **`final`**：本週已提到 `final` 方法可禁止覆寫；第 9 週可討論 `Rover` 哪些方法應該 `final`（例如 `execute`，以防 4.3 的繞過問題）。
- **多載**：本週 `@Override` 拼錯實驗產生「名稱不同的另一個方法」；第 10 週正式比較多載與覆寫。

## 9. 驗證計畫（正式製作時）

- 記錄 java／javac 版本；`javac -encoding UTF-8 -Xlint:all` 在乾淨目錄分開編譯 examples、starter、starter/club、teacher、teacher/club、ai；另以 `--release 11` 分開編譯只確認語法相容，不當成學生要求。
- `Rover` 輸出回歸：第 8 週教師版 `MissionLog` 與第 7 週教師版以 10 組參數逐字比對。
- 4.2 表格的六組案例＋檢查程式全通過；檢查程式要能抓到 4.3 的缺陷（變異測試）。
- 講義所有「預測後執行」與編譯錯誤實驗實際跑過，錯誤訊息逐字取自實測。
- PlantUML 以 `-P layout=smetana -pipe` 重新產生；Marp 匯出 HTML 與逐頁 PNG 做視覺檢查。
- 雙 IDE GUI、真實 GitHub 操作、學生試教：做了才寫通過，未做就列為未驗證。

## 10. 需要教師決定的事項

1. **指令 4（鑽探進任務）**：本規劃放在延伸題、主線不改 `Mission` 契約。若希望主線就做，需要同步調整 `countCommands`、`MissionLog` 報表與第 4–7 週的回歸比對。
2. **同儕互評的形式**：本規劃採課堂兩人交換＋`REVIEW.md`；是否改用 GitHub（協作者留言或 Pull Request）？若採 PR，需另排教學時間（學生目前只用單人 repo 與 GitHub Desktop）。
3. **`instanceof`／向下轉型**：本規劃只放教師筆記與延伸題；若期末考會考，需在本週或第 10 週補一段。
