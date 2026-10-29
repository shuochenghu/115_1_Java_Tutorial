# 第 7 週交接：給第 8 週（claude 版本）

> 本文件由 Anthropic Claude（Claude Code）撰寫，說明第 7 週 claude 版留給第 8 週的能力與資料契約。第 8 週尚未製作；以下是建議入口，不是已完成的教材。

## 學生在第 7 週結束時應能

| 能力 | 證據 |
|---|---|
| 用 `extends` 寫子類別，只寫新增的欄位與方法 | 講義 A、TODO 3–4；作業 `Camera`、`Projector` |
| 說出子類別物件能用哪些方法；父類別物件不會得到子類別的方法 | 講義 A2 |
| 說明建構子順序，子類別建構子第一行呼叫 `super(...)` | 講義 B1–B2、E2 |
| 知道 private 成員在子類別物件裡存在，但子類別程式不能用名字存取 | 講義 B3、E5 |
| 分辨 `private`／`protected`／`public`，知道 Java 的 `protected` 也開放給同一套件 | 講義 B4、D5 |
| 知道所有類別都繼承 `Object`，`println(物件)` 印出 `類別名@雜湊碼` | 講義 B5 |
| 用 is-a 測試決定繼承或欄位 | 講義 C1–C2；`DESIGN.md` 第 2 節 |
| 類別圖：空心三角形指向父類別、`#`、子類別只列新增的成員 | TODO 5；`DESIGN.md` 第 1、3 節 |
| 追蹤 AI 寫的子類別：認出缺 `super`、欄位遮蔽（兩份狀態）、新入口漏停止檢查 | 講義 E；作業第 3 部分 |

仍需診斷、不能假設已熟練：

- 遮蔽（hiding）只在本週示範到「欄位」；方法同名同參數會是覆寫，第 8 週才教，學生可能混為一談。
- `protected` 在預設套件中等於套件存取，學生容易以為它和 C# 一樣只給子類別。
- `super(...)` 前能否寫敘述依 JDK 版本而不同（JDK 25 起 JEP 513 放寬）；本課程規定寫在第一行。

## 第 7 週結束時的程式（資料契約）

```text
Rover（父類別）
  - energy, samples, actionCount
  + CAPACITY = 20, TARGET_SAMPLES = 5
  + Rover(energy, samples)：超出範圍改成最接近的合法值
  + getEnergy(), getSamples(), getActionCount(), isSuccess(), isExhausted(), isStopped()
  + execute(command)：已停止回傳 STOPPED；1 探索、2 掃描、3 充電、0 RETURN、其他 UNKNOWN_COMMAND
  # spendEnergy(amount) : boolean   amount <= 0 或 > energy 回傳 false 且不變；否則扣除
  # addSamples(amount)              amount > 0 才增加
  # countAction()
  - explore()、scan()：改用上面三個方法；charge() 維持原寫法

HeavyRover extends Rover
  + DRILL_COST = 8, DRILL_YIELD = 3
  - drillCount
  + HeavyRover(energy, samples)：super(energy, samples)，drillCount = 0
  + getDrillCount()
  + drill()：已停止 STOPPED；能源不足 NOT_ENOUGH_ENERGY；成功 −8、+3、行動 +1、鑽探 +1 回傳 OK

Mission、MissionStep、MissionLog：與第 6 週相同（只改檔頭說明）
```

`MissionLog` 從 `new Rover(20, 0)` 出發的輸出與第 5、6 週 claude 版逐字相同（已用 10 組參數比對第 6 週教師版）。

`new Mission(new HeavyRover(20, 0))` 能編譯並正常執行（已實測預設腳本 `SUCCESS`、3 步），但 `Mission` 不會呼叫 `drill`，指令 1 仍得 2 份。本週只在課末檢核第 5 題預告，沒有當成教學內容。

作業延伸：`code/teacher/club/` 的 `Equipment`（多了 `getLabel()`）、`Camera`、`Projector`。兩個子類別各有一個名稱相同、但父類別沒有的 `checklist()`。

## 建議第 8 週入口（覆寫、多型基礎、Code Review）

1. **兌現第 6 週的預告**：「重型探測車探索一次得 3 份」。本週的 `explore` 是 `private`，子類別無法覆寫。可以讓 `Rover` 提供一個可覆寫的方法（例如 `protected int exploreYield()` 回傳 2），`HeavyRover` 用 `@Override` 改成 3；`explore` 本身仍 private，停止檢查與能源規則不動。
2. **多型**：`Mission` 的欄位型態是 `Rover`，傳入 `HeavyRover` 後 `execute(1)` 會呼叫子類別的版本，`Mission` 不必修改。本週課末檢核第 5 題已讓學生預測「仍得 2 份」，可以拿來對照覆寫前後。
3. **鑽探接上任務**：若要讓腳本能下指令 4 鑽探，需要覆寫 `execute` 並在其他情況呼叫 `super.execute(command)`。
4. **`toString` 與 `describe`**：本週 B5 留下 `Member@雜湊碼`，A3 留下「`bob.describe()` 沒有 VIP 資料」，第 5 週留下 `MissionStep@雜湊碼`，都可用覆寫回收。
5. **社團器材**：`Camera`、`Projector` 都有 `checklist()`，但 `Equipment` 沒有，所以不能用 `Equipment` 的參考統一呼叫。這是把方法提到父類別再覆寫的動機（也可留給第 9 週抽象類別）。
6. **Code Review**：課綱第 8 週要求審查 AI 生成的繼承程式碼。本週 E 段已練習「追蹤」，第 8 週可以換成審查清單（覆寫簽章不符、忘了 `@Override`、在子類別重複父類別邏輯、違反 is-a）。
7. 第 8 週要提醒 C# 差異：Java 的實例方法預設就可覆寫，不需要 `virtual`；`@Override` 是給編譯器檢查用的標註。
