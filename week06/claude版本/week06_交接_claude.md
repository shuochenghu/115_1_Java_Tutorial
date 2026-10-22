# 第 6 週交接：給第 7 週（claude 版本）

> 本文件由 Anthropic Claude（Claude Code）撰寫，說明第 6 週 claude 版留給第 7 週的能力與資料契約。第 7 週尚未製作；以下是建議入口，不是已完成的教材。

## 學生在第 6 週結束時應能

| 能力 | 證據 |
|---|---|
| 把類別寫成 UML 方框：可見性、`名稱 : 型態`、static、`{readOnly}` | 講義 A1–A3；`DESIGN.md` 第 1 節 |
| 只看類別圖判斷哪些呼叫能編譯 | 講義 A2 |
| 分辨關聯（欄位）與相依（參數、區域變數），並寫出多重性 | 講義 B2–B4 |
| 認得聚合與組合，知道 Java 中要看「誰 new、有沒有交出去」 | 講義 B5（只要認識） |
| 用責任表分配規則：資料在誰身上，規則放誰身上 | 講義 C1–C3、E1 |
| 依類別圖重構，保持輸出不變，並讓圖與程式一致 | TODO 1–7、`MissionCheck` |
| 用需求與自己的責任表檢查 AI 的類別圖 | 講義 E2；作業第 3 節 |

仍需診斷、不能假設已熟練：

- 多重性寫在哪一端（常寫反）。
- 規則與多重性的差別（社團的「同時最多 2 件」）。
- `~`（套件存取）在圖上的寫法；學生常把沒寫修飾字的方法畫成 `-` 或 `+`。
- 組合與聚合的判斷：本週只要求認識，批改時畫普通關聯也算對。

## 第 6 週結束時的程式（資料契約）

```text
MissionLog（static：main、toScript、printReplay、printSummary）
  ..> Mission（建立、run、查詢）
  ..> Rover（建立、讀 getter）
  ..> MissionStep（getStep(i).describe()）

Mission
  - rover: Rover（final，由外部傳入）
  - steps: ArrayList<MissionStep>（final，自己建立，不交出）
  + Mission(rover)
  + run(script)：停止後不讀不記；每個讀入的指令記一筆；指令 0 記錄後結束
  + getOutcome()：SUCCESS > EXHAUSTED > RETURNED > UNFINISHED
  + getStepCount(), getStep(index), countCommands(), countOk(), countRejected()
  + lowestEnergy()：沒有紀錄回傳 -1

Rover
  - energy, samples, actionCount
  + CAPACITY = 20, TARGET_SAMPLES = 5
  + Rover(energy, samples)：超出範圍改成最接近的合法值
  + getEnergy(), getSamples(), getActionCount()
  + isSuccess(), isExhausted(), isStopped()
  + execute(command)：已停止回傳 STOPPED，不改狀態
  - explore(), scan(), charge()

MissionStep（不可變，與第 5 週相同）
```

可觀察結果與第 4、5 週 claude 版相同：從 `new Rover(20, 0)` 出發時輸出逐字相同（已用 10 組參數比對第 5 週教師版）。`STOPPED` 只會在直接呼叫 `Rover.execute` 時看到，不會出現在任務輸出。

## 建議第 7 週入口（繼承基礎）

1. 課末檢核第 5 題已預告：「重型探測車探索一次得 3 份樣本，其他規則相同」。複製 `Rover` 會造成兩份程式，引出繼承。
2. 本週 `explore` 已是 `private`，子類別無法覆寫或呼叫。第 7 週若要讓子類別改變探索行為，需要討論把它改成 `protected`（類別圖的 `#`）或另設計可覆寫的方法；這正好接到「可見性與繼承」。
3. `Mission` 只依賴 `Rover` 的 public 方法（`execute`、`isStopped`、getter），所以傳入子類別物件時 `Mission` 不必修改，可作為第 8 週多型的伏筆。
4. 類別圖：第 7 週加入空心三角形的繼承箭頭（PlantUML `<|--`），沿用本週的 `DESIGN.md` 與擴充功能。
5. 第 5 週留下的 `println(step)` 印出 `MissionStep@雜湊碼`，可在第 7–8 週以覆寫 `toString()` 回收。

作業延伸的社團系統（`code/teacher/club/`）只作為設計練習的參考，不要假設學生已實作。
