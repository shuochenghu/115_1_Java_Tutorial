# 第 5 週交接：給第 6 週（claude 版本）

> 本文件由 Anthropic Claude（Claude Code）撰寫，說明第 5 週 claude 版留給第 6 週的能力與資料契約。第 6 週尚未製作；以下是建議入口，不是已完成的教材。

## 學生在第 5 週結束時應能

| 能力 | 證據 |
|---|---|
| 分辨類別與物件，說明 `new` 與參考 | 講義 A、課末檢核 1 |
| 用 `private` 欄位與公開方法守住狀態規則 | `Rover`、`Battery`；講義 B4 |
| 寫建構子、用 `this` 區分欄位與參數、知道預設建構子何時消失 | TODO 1、3；講義 B2–B3 |
| 用 `final` 欄位讓編譯器檢查初始化 | TODO 3 加上 `final`；講義 D3-1（選做） |
| 區分實例方法與 `static` 方法 | 講義 D2-1、D2-2 |
| 走訪 `ArrayList<MissionStep>`，用 getter 取欄位計算摘要 | TODO 6–7 |
| 把文字需求轉換成類別設計卡，用它檢查 AI 產生的類別 | 講義 E1–E2 |

仍需診斷、不能假設已熟練：

- 「不寫修飾字＝private」的 C# 習慣（Java 是套件存取）。
- 修正參數與修正欄位的先後順序（E3 第 3 題）。
- 存數值與存物件參考的差別（E3 第 1 題）。
- `static` 的完整意義：本週只說「`static` 方法沒有 `this`」，`static` 欄位只用在常數 `CAPACITY`、`TARGET_SAMPLES`。

## 第 5 週結束時的程式（資料契約）

```text
MissionLog（static 方法：main、toScript、runMission、printReplay、countCommands、printSummary）
  ├─ 建立並使用 Rover（一台）
  └─ 建立並保存 ArrayList<MissionStep>（每一步一筆）

Rover
  - energy: int（0..20）  - samples: int（>= 0）  - actionCount: int（>= 0）
  + CAPACITY = 20, TARGET_SAMPLES = 5（public static final）
  + Rover(energy, samples)：超出範圍改成最接近的合法值；actionCount = 0
  + getEnergy(), getSamples(), getActionCount()
  + explore(), scan(), charge(), execute(command)：回傳結果字串
  + isSuccess(), isExhausted()

MissionStep（建立後不可變）
  - command: int  - result: String  - energyAfter: int（全部 private final）
  + MissionStep(command, result, energyAfter)
  + getCommand(), getResult(), getEnergyAfter(), isRejected(), describe()
```

可觀察結果與第四週 claude 版相同：

- 結果字串：`OK`、`NOT_ENOUGH_ENERGY`、`ALREADY_FULL`、`RETURN`、`UNKNOWN_COMMAND`。
- 每一步（含拒絕、返回、無效指令）都記錄一筆，能源為執行後的值。
- 任務已成功或耗盡就不再讀指令、不記錄；從 `new Rover(20, 0)` 出發時與第四週輸出逐字相同。
- 結束判斷：`SUCCESS` > `EXHAUSTED` > `RETURNED`（最後一筆是指令 0）> `UNFINISHED`。
- `counts` 只計 0–3；`rejected` 為 `isRejected()` 的筆數；`ignored` = 腳本長度 − 紀錄筆數；沒有紀錄時不印最低能源。
- 命令列參數為指令腳本；非整數參數不在契約內。

## 建議第 6 週入口（UML 類別圖與設計思維）

1. 把上方的資料契約畫成 UML 類別圖：`-`／`+` 對應 `private`／`public`，`MissionLog` 使用 `Rover` 與 `MissionStep` 的關係。學生在第 5 週課末檢核 5 已畫過草圖，可直接拿來修正。
2. 責任討論：講義課後選做提出「能源 0 時誰負責擋下 `charge()`」。目前由 `MissionLog.runMission` 在執行前檢查，`Rover` 本身仍允許。可用來引出「規則應放在擁有資料的類別」與「流程類別」的分工。
3. `MissionLog` 全是 `static` 方法並同時負責流程與輸出，是抽出 `Mission`（保存 `Rover` 與 `steps`）之類類別的自然動機；是否在第 6 週重構，依第 6 週教材決定。
4. 延伸題 `SampleBay` 可作為「為現有系統加入新類別」的 UML 練習對象。

第 7–8 週（繼承、覆寫）可回收的伏筆：`println(step)` 印出 `MissionStep@雜湊碼`，本週以 `describe()` 代替 `toString()`。
