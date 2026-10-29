# 第 7 週作業：不要複製，要繼承（claude 版本）

> 本說明初稿由 Anthropic Claude（Claude Code）生成，教師審閱後使用。截止時間與繳交平台依教師公告。

本週有三部分：完成重型探測車（課堂 TODO 1–5）、依類別圖寫出社團器材的兩個子類別、追蹤並修正一份模擬 AI 寫的子類別。延續課堂專案，不必從空白檔重寫。

## 1. 程式：重型探測車

從 `code/starter/` 的檔案開始，完成 TODO 1–4。`Mission`、`MissionStep`、`MissionLog`、`RoverTry`、`HeavyRoverTry` 請勿修改。

| TODO | 檔案 | 要求 |
|---|---|---|
| 1 | `Rover` | 三個 `protected` 方法：`spendEnergy(int)`（不合理或不足回傳 `false`、能源不變；否則扣除回傳 `true`）、`addSamples(int)`（大於 0 才增加）、`countAction()` |
| 2 | `Rover` | `explore`、`scan` 改用 TODO 1 的方法；所有輸出與第 6 週逐字相同 |
| 3 | `HeavyRover` | `private int drillCount`，建構子在 `super(energy, samples)` 之後設為 0，`getDrillCount()` 回傳它 |
| 4 | `HeavyRover` | `drill()`：已停止回傳 `STOPPED`；能源不足回傳 `NOT_ENOUGH_ENERGY`；成功時能源 −8、樣本 +3、行動次數 +1、鑽探次數 +1，回傳 `OK`；被拒絕時狀態不變 |

限制：

- `Rover`、`HeavyRover` 的欄位都是 `private`；`HeavyRover` **不可**再宣告 `energy`、`samples`、`actionCount`。
- 不把 `Rover` 的欄位改成 `protected` 或 `public`；不新增 setter。
- `drill` 使用常數 `DRILL_COST`、`DRILL_YIELD`。
- 不覆寫 `Rover` 的方法（第 8 週才教），不修改 `execute` 的指令表。

### 必做測試

重構後每列重新執行一次，把實際輸出的**最後一行**貼到 `LAB_REPORT.md`；`3 9 0` 貼完整輸出。

| 參數 | 最後一行（與第 5、6 週相同） |
|---|---|
| （無） | `final energy=6, samples=5, actionCount=3 -> SUCCESS` |
| `2 2 2 2 2` | `final energy=10, samples=5, actionCount=5 -> SUCCESS` |
| `3 9 0` | `final energy=20, samples=0, actionCount=0 -> RETURNED` |
| `1 1 1 1` | `final energy=2, samples=6, actionCount=3 -> SUCCESS` |
| `2 1` | `final energy=12, samples=3, actionCount=2 -> UNFINISHED` |

另外執行 `RoverTry`（12 行）與 `HeavyRoverTry`（15 行），貼上完整輸出，所有行都必須是 `OK`。

## 2. 程式：社團器材的子類別

延續第 6 週的「社團器材借用」。社團的器材分成很多種，相機要記錄附帶幾張記憶卡，投影機要記錄是否需要布幕，但每件器材都有編號與名稱。

把 `code/starter/club/Equipment.java` 複製到 `src/`（已完成，不可修改），依下面的類別圖自己寫出 `Camera.java`、`Projector.java`（圖檔 `diagrams/club_claude.svg`）：

```text
@startuml
skinparam classAttributeIconSize 0
hide circle

class Equipment {
  - id : String {readOnly}
  - name : String {readOnly}
  + Equipment(id : String, name : String)
  + getId() : String
  + getName() : String
  + getLabel() : String
}
class Camera {
  - memoryCards : int {readOnly}
  + Camera(id : String, name : String, memoryCards : int)
  + getMemoryCards() : int
  + checklist() : String
}
class Projector {
  - needsScreen : boolean {readOnly}
  + Projector(id : String, name : String, needsScreen : boolean)
  + needsScreen() : boolean
  + checklist() : String
}

Equipment <|-- Camera
Equipment <|-- Projector
@enduml
```

規格：

- `Camera` 的記憶卡數小於 0 時改成 0。
- `checklist()` 的格式：`Camera` 是 `標籤 | cards=張數`，例如 `CAM-01 Sony A7 | cards=2`；`Projector` 是 `標籤 | screen=yes` 或 `screen=no`。標籤用繼承來的 `getLabel()`，**不可**在子類別另存編號或名稱。

兩個類別都寫好後，再把 `code/starter/club/ShelfTry.java` 複製到 `src/`（類別還不存在時它無法編譯，這是刻意的，請不要提早複製）。執行 `java -cp out ShelfTry`，9 行都必須是 `OK`。

## 3. 追蹤：模擬 AI 寫的 Camera

以下是**教師設計的模擬 AI 回覆**（不是真實對話，刻意含錯），程式在 `code/ai/Camera.java`：

> Prompt：Equipment 有 id 和 name，幫我寫繼承它的 Camera，多記錄記憶卡數，並提供 checklist()。

```java
public class Camera extends Equipment {
    private String id;
    private String name;
    private int memoryCards;

    public Camera(String id, String name, int memoryCards) {
        super(id, name);
        this.memoryCards = memoryCards;
    }

    public int getMemoryCards() {
        return memoryCards;
    }

    public String checklist() {
        return id + " " + name + " | cards=" + memoryCards;
    }
}
```

在 `java-07` 另建資料夾 `ai-camera/`，放入 `Equipment.java`、你寫的 `Projector.java`、`ShelfTry.java` 與這份 `Camera.java`，依序完成：

1. **先預測**（寫進 `DESIGN.md` 第 4 節，再編譯）：能編譯嗎？`cam.checklist()` 會印出什麼？
2. 編譯並執行 `ShelfTry`，抄下所有 `DIFF` 行。
3. 解釋原因：`cam` 物件裡有幾個 `id`？`checklist()` 讀到的是哪一個？為什麼 `cam.getLabel()` 卻是對的？
4. 最小修正，重測到 9 行全部 `OK`。和你在第 2 部分寫的 `Camera` 比較。

先用 `[AI]` commit 模擬回覆原文，修正後再用 `[fix]` commit。

## 4. 設計文件

都寫在 `java-07/DESIGN.md`（從 `DESIGN_範本_claude.md` 複製）：

1. **第 1 節（TODO 5）**：`Rover`、`HeavyRover` 類別圖與 `Mission` 的關係，和完成後的程式一致（講義 D6 的四項檢查）。
2. **第 2 節**：講義 C1 的 is-a／has-a 判斷表，以及 C2 的回答。
3. **第 3 節**：社團器材再加一種：自己選一種器材（例如麥克風、延長線、腳架），替它設計一個子類別，至少一個新欄位、一個新方法，畫進繼承圖，並用 is-a 測試說明為什麼它該繼承 `Equipment`。只要設計，可以不寫程式。
4. **第 4 節**：講義 E 段（AI 寫的 `HeavyRover`）與本說明第 3 部分（AI 寫的 `Camera`）的追蹤紀錄：預測、實際、原因、最小修正。

## 5. 繳交檔案

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md      ← 講義 0、A–G 的作答，必做測試與三個測試程式的輸出
   ├─ AI_USAGE.md        ← 依 AI使用紀錄表_範本_claude.md
   └─ java-07/
      ├─ README.md       ← 如何編譯執行、本週完成項目、Rover 與 HeavyRover 各負責什麼
      ├─ DESIGN.md       ← 第 1–4 節
      ├─ src/
      │  ├─ Member.java      VipMember.java     InheritDemo.java
      │  ├─ Rover.java       HeavyRover.java    Mission.java      MissionStep.java   MissionLog.java
      │  ├─ RoverTry.java    HeavyRoverTry.java
      │  └─ Equipment.java   Camera.java        Projector.java    ShelfTry.java
      ├─ ai-check/       ← Rover.java、HeavyRoverTry.java、修正後的 HeavyRover.java
      └─ ai-camera/      ← Equipment.java、Projector.java、ShelfTry.java、修正後的 Camera.java
```

不交 `out/`（三個資料夾底下都不交）、`.class`、`.idea/`、`*.iml`。commit 訊息使用 `[manual]`、`[AI]`、`[fix]`、`[doc]` 前綴；有 `[AI]` 的 commit，`AI_USAGE.md` 必須有對應紀錄（模擬回覆也要記，來源寫「教師模擬回覆」）。

## 6. 驗收標準

| 項目 | 通過條件 |
|---|---|
| 編譯 | 全新 clone 後在 `java-07` 執行 `javac -encoding UTF-8 -d out src/*.java` 無錯誤；`ai-check`、`ai-camera` 各自用 `javac -encoding UTF-8 -d out *.java` 無錯誤 |
| 重構不改行為 | 五條必做測試與上表一致；`RoverTry` 全部 `OK` |
| 重型探測車 | `HeavyRoverTry` 全部 `OK` |
| 繼承設計 | `HeavyRover extends Rover`；子類別沒有重複宣告父類別的欄位；欄位皆 `private`；三個輔助方法為 `protected`；`drill` 有停止檢查 |
| 社團子類別 | `ShelfTry` 全部 `OK`；`Camera`、`Projector` 用 `super(id, name)`，沒有另存編號與名稱 |
| AI 追蹤 | E 段與第 3 部分都有先預測、實際輸出、原因與最小修正；修正後兩個資料夾的測試都通過；commit 紀錄看得到模擬原文與修正 |
| 類別圖 | 繼承方向正確（三角形指向父類別）；`#`、`+`、`-` 與程式一致；子類別只列新增的成員 |
| 誠信 | 能逐行解釋自己的程式；真實 AI 與模擬回覆分開記錄 |
