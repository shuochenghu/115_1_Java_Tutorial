# 第 6 週作業：先畫圖，再搬家（claude 版本）

> 本說明初稿由 Anthropic Claude（Claude Code）生成，教師審閱後使用。截止時間與繳交平台依教師公告。

本週有兩部分：一是依新的類別圖重構任務程式，輸出與第 5 週逐字相同；二是為一個新系統畫類別圖，並檢查 AI 給的設計。延續課堂專案，不必從空白檔重寫。

## 1. 程式：依類別圖重構

從 `code/starter/` 的檔案開始，完成 TODO 1–6。`MissionStep`、`MissionLog`、`RoverTry`，以及 `Mission` 中已提供的建構子、`getStepCount`、`getStep`、`countCommands` 請勿修改。

| TODO | 檔案 | 要求 |
|---|---|---|
| 1 | `Rover` | `isStopped()`：成功或耗盡時為 true，用 `isSuccess()`、`isExhausted()` 組合 |
| 2 | `Rover` | `execute` 一開始：已停止就回傳 `STOPPED`，狀態不變；`explore`、`scan`、`charge` 改為 `private` |
| 3 | `Mission` | `run(script)`：探測車已停止就不再讀指令、不記錄；每讀一個指令都記錄一筆 `MissionStep`（能源為執行後的值）；指令 0 記錄後結束 |
| 4 | `Mission` | `getOutcome()`：`SUCCESS` > `EXHAUSTED` > `RETURNED`（最後一筆是指令 0）> `UNFINISHED` |
| 5 | `Mission` | `countOk()`：結果為 `OK` 的筆數（用 `equals`）；`countRejected()`：`isRejected()` 為 true 的筆數 |
| 6 | `Mission` | `lowestEnergy()`：所有紀錄中最低的執行後能源；沒有紀錄回傳 -1 |

限制：

- 所有欄位都是 `private`；不新增 setter；`Mission` 不提供交出整條清單的方法。
- `Mission.run` 的停止檢查要保留（講義 D5）。
- 不必處理非整數參數。不使用繼承、`toString` 覆寫、`record`、Lambda、Stream。

### 必做測試

每列重新執行一次，把實際輸出貼到 `LAB_REPORT.md`：一般列貼回放之後的四行；`3 9 0` 貼完整輸出（含回放）。

| 參數 | 最終 energy／samples／actionCount | 結果 | 摘要 |
|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=0`，`lowest energy=6` |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | `steps=5, ok=5, rejected=0, ignored=0`，`lowest energy=10` |
| `3 9 0` | 20／0／0 | RETURNED | `steps=3, ok=0, rejected=2, ignored=0`，`lowest energy=20` |
| `1 1 1 1` | 2／6／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=1`，`lowest energy=2` |
| `2 1` | 12／3／2 | UNFINISHED | `steps=2, ok=2, rejected=0, ignored=0`，`lowest energy=12` |

這五列與第 5 週作業的表格完全相同。另外執行 `java -cp out RoverTry`，貼上完整輸出，所有行都必須是 `OK`。

## 2. 設計：任務系統與樣本艙

都寫在 `java-06/DESIGN.md`（從 `DESIGN_範本_claude.md` 複製）：

1. **第 1 節（TODO 7）**：任務系統類別圖與責任表，和你完成後的程式一致（講義 D6 的四項檢查）。
2. **第 2 節**：講義 E 段的樣本艙設計，包含四個問題的回答、責任表、你的類別圖、模擬 AI 類別圖的至少四項問題，以及你的設計 Prompt。

## 3. 設計：社團器材借用（新系統）

> 1. 社團有多件器材，每件有編號（例如 `CAM-01`）與名稱。只有社團登記過的器材可以借。
> 2. 社員有學號與姓名。
> 3. 社員借器材時，建立一筆借用紀錄：哪位社員、哪件器材、借出日期（用字串，例如 `2026-10-22`）。
> 4. 同一件器材同一時間只能借給一位社員；每位社員同時最多借 2 件未歸還的器材。
> 5. 歸還時，在借用紀錄填上歸還日期，器材恢復可借。同一筆紀錄不能歸還兩次。
> 6. 社團要能列出目前所有未歸還的紀錄，也要能查某位社員目前借了幾件。
> 7. 借用被拒絕時，要讓呼叫的人知道原因（例如器材已借出、已達上限）。

只要設計，**不必寫程式**。依 `DESIGN.md` 第 3 節的順序：

1. **責任表**：每個類別知道什麼、負責做什麼、和誰合作。
2. **第一版類別圖**：在問 AI **之前**自己畫，commit 一次（commit 訊息用 `[manual]`）。
3. **AI 的類別圖**，擇一：
   - 用真實 AI：Prompt 要包含需求原文與你的責任表，要求它輸出 PlantUML、標出多重性、不使用繼承與介面，並說明設計理由。把它的原始碼貼進 3.3，在 `AI_USAGE.md` 記錄。
   - 不使用 AI 帳號：使用下面的**教師模擬回覆**（不是真實對話，刻意含有問題）。
4. **逐項比對**：至少四項，每項寫出對應的需求句子（用編號，例如「需求 4」），以及「照 AI 的設計會發生什麼事」的具體情境。
5. **最終類別圖**：說明比第一版改了什麼、哪些採納了 AI 的想法、哪些沒有採納。

教師模擬回覆（圖檔見 `diagrams/club_ai_claude.svg`）：

```text
@startuml
skinparam classAttributeIconSize 0
hide circle

class Club {
  - equipments : ArrayList<Equipment>
  - loans : ArrayList<Loan>
  + getLoans() : ArrayList<Loan>
}
class Member {
  - studentId : String
  - name : String
  + borrow(e : Equipment) : void
}
class Equipment {
  - id : String
  - name : String
  + borrower : Member
}
class Loan {
  - member : Member
  - borrowDate : String
}

Club --> "0..*" Equipment
Club --> "0..*" Loan
Member "1" --> "0..2" Loan : loans
Loan "1" --> "0..*" Equipment
@enduml
```

情境的寫法範例（取自第 5 週，和上面的問題無關）：「如果 `MissionStep` 只存 `Rover` 而不存執行後能源，跑預設腳本時，回放的三步都會印出 `energy=6`，看不出每一步之後剩多少能源。」

## 4. 繳交檔案

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md      ← 講義 0、A–G 的作答，五條測試＋RoverTry 的輸出
   ├─ AI_USAGE.md        ← 依 AI使用紀錄表_範本_claude.md
   └─ java-06/
      ├─ README.md       ← 如何編譯執行、本週完成項目、四個類別各負責什麼
      ├─ DESIGN.md       ← 第 1–3 節
      └─ src/
         ├─ Battery.java      Flashlight.java   Charger.java   RelationDemo.java
         ├─ Rover.java        MissionStep.java  Mission.java   MissionLog.java
         └─ RoverTry.java
```

不交 `out/`、`.class`、`.idea/`、`*.iml`。commit 訊息使用 `[manual]`、`[AI]`、`[fix]`、`[doc]` 前綴；有 `[AI]` 的 commit，`AI_USAGE.md` 必須有對應紀錄。

## 5. 驗收標準

| 項目 | 通過條件 |
|---|---|
| 編譯 | 全新 clone 後在 `java-06` 執行 `javac -encoding UTF-8 -d out src/*.java` 無錯誤 |
| 功能 | 五條必做測試與上表一致；`3 9 0` 的回放三步與 `counts=[1, 0, 0, 1]` 正確；`RoverTry` 全部 `OK` |
| 封裝與責任 | 欄位皆為 `private`；三個行動方法為 `private`；停止規則在 `Rover.execute`；`Mission` 沒有交出整條清單；`MissionLog` 沒有任務流程或計算 |
| 任務系統類別圖 | 四個類別的欄位、方法、可見性、static 與程式一致；關聯與相依分清楚；`Mission` 的兩條關聯有多重性 |
| 樣本艙設計 | 四個問題有理由；類別圖能對應需求 1–5；模擬 AI 至少四項問題且有情境 |
| 社團設計 | 第一版在比對之前 commit；至少四項比對都有需求編號與情境；最終圖說明修改理由 |
| 圖能顯示 | PlantUML 區塊語法正確（推上 GitHub 後可用擴充功能顯示；無法安裝擴充功能時，教師以原始碼批改） |
| 誠信 | 能逐行解釋自己的程式與圖；真實 AI 與模擬回覆分開記錄 |
