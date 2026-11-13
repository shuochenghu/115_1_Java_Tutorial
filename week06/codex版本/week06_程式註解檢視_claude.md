# 第 6 週程式註解檢視（claude 版本）

本檔由 Anthropic Claude（Claude Code）於 2026-11-12 撰寫，檢視本資料夾 `examples/`、`starter/`、`teacher/` 的 Java 註解是否方便教學與學生理解。只提建議，未修改任何 codex 檔案。

**等級**：「建議」表示補上後對教學或理解有明確幫助；「可選」表示不改也可以。建議寫法刻意不透露講義要學生預測的結果。

## 總評

整體已足夠：檔頭大多寫清楚角色與契約（`Battery`、`Charger`、`MissionStep`、`MissionLog`、`Rover`、`RoverDemo`、`FixedScriptMission`、`MissionLogCheck`），starter 的 TODO 也具體。需要補強的有三處：

- 本週主題「關聯與相依」，`Charger` 有說明相依，但 `Lamp` 沒有說明關聯。
- `Mission` 中兩個不直觀的計數規則沒有註解。
- 教師版 `Mission` 的答案沒有標出對應的 TODO。

## 建議

| 位置 | 問題 | 建議寫法 | 等級 |
|---|---|---|---|
| `examples/Lamp.java:1` | 本週主題是關聯／相依，`Charger` 有「不用欄位保存電池」，`Lamp` 卻沒有對應說明，學生不易對照 | `// 以欄位保存建構時收到的 Battery，之後每次 turnOn 都用同一顆電池。` | 建議 |
| `examples/SampleCase.java:6` | 建構子把容量修正到 1..8，與 `Rover`「不自動修正契約外參數」的做法相反，卻沒有說明，學生會以為寫錯 | `// 依樣本盒需求：容量在建構時修正到 1..8（這是需求的一部分，與 Rover 不同）。` | 建議 |
| `starter/Mission.java:33`（teacher 同） | `countCommands` 只統計 0..3，`-1`、`9` 仍在日誌卻不計入，沒有說明 | `// 只統計 0..3 的已知指令；無效指令仍記在日誌，但不列入次數。` | 建議 |
| `starter/Mission.java:47`（teacher 同） | `countRejected` 用「總筆數 − 成功 − 返回」計算，沒說「拒絕」包含哪些結果 | `// 拒絕＝已記錄、但既不是成功也不是返回的行動，例如能源不足、能源已滿、無效指令。` | 建議 |
| `teacher/Mission.java:16、50、66` | 教師答案沒有標出對應的 TODO 1–3，第 7、8 週的教師版都有「TODO n 教師答案」 | `run` 前加 `// TODO 1 教師答案：先檢查停止，未讀的指令才算略過；行動後才記快照；返回也要先記錄再離開。`，`countOk`、`lowestEnergy` 前比照加註 | 建議 |
| `examples/RelationDemo.java:1` | 沒有檔頭 | `// 關係追蹤：A、B 收到同一個 Battery 參考，C 收到另一顆。先在講義 C 預測各行再執行。`（講義 C 已告知誰共用，不透露結果） | 可選 |
| `examples/Battery.java:12` | `charge` 的規則（已滿回 false、每次 +5、上限 20）沒寫 | `// 已滿回 false；否則加 5 格，最多 20 格（與 Rover 的充電規則相同）。` | 可選 |
| `teacher/MissionStateCheck.java:34` | 用 `new String("成功")` 的目的不明 | `// 內容相同但不同物件的字串：確認 isSuccessful 用 equals 比內容，而不是 ==。` | 可選 |
| `teacher/MissionStateCheck.java:19` | 「修改副本」檢查的目的不明 | `// countCommands 每次回傳新陣列；改回傳值不應影響任務日誌。` | 可選 |

## 處理狀態（2026-11-12）

依教師指示已補上全部「建議」項，「可選」項未處理。

- **與建議稿不同的一處**：`teacher/Mission.java` 的答案說明沒有寫成「TODO n 教師答案」。原因是這個檔案必須和第 7–10 週 starter／teacher 的 `Mission.java` 逐位元組相同（各週 `verify.py` 會檢查），寫上 TODO 編號會讓之後各週出現過時的標記。因此改為只說明理由，並把同一份檔案同步到第 7–10 週的 8 份副本。
- **重跑驗證**：第 6–10 週的 `tools/verify.py` 全部通過。

**2026-11-12 改寫**：教師回饋上表的建議寫法不夠清楚，程式中的註解已改寫成完整句子，說明「做什麼」與「為什麼」，並避開學生還不熟的術語。實際內容以程式檔為準；各週 `tools/verify.py` 重跑通過。
