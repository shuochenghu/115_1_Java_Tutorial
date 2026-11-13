# 第 7 週程式註解檢視（claude 版本）

本檔由 Anthropic Claude（Claude Code）於 2026-11-12 撰寫，檢視本資料夾 `examples/`、`starter/`、`teacher/`、`ai/` 的 Java 註解是否方便教學與學生理解。只提建議，未修改任何 codex 檔案。

**等級**：「建議」表示補上後對教學或理解有明確幫助；「可選」表示不改也可以。

## 總評

整體良好：

- 會員範例的檔頭清楚，建構子印訊息的用意、`protected` 的同套件存取都有說明。
- `Rover` 的 TODO 1 與三個輔助方法分工清楚。
- `ai/HeavyRover.java` 標明是模擬回覆，也沒有提示答案。
- `InheritanceCheck` 標明了反射不是學生先備。

需要補強的是 starter `HeavyRover` 的兩處註解：一處語意不清，一處是沒標示的佔位。

## 建議

| 位置 | 問題 | 建議寫法 | 等級 |
|---|---|---|---|
| `starter/HeavyRover.java:12` | 「super 已提供供追蹤與隔離實驗」語意不清，學生不知道 super 這行在做什麼 | `// super(energy, samples) 已寫好：先由 Rover 建立能源、樣本與行動數，再在下面初始化自己的鑽探次數。` | 建議 |
| `starter/HeavyRover.java:15` | `getDrillCount()` 固定回傳 0，是佔位，卻沒有標示。學生在 `HeavyRoverTry` 看到鑽探次數一直是 0，會誤以為是 `drill` 寫錯 | `// 佔位：補上鑽探次數欄位後，改成回傳該欄位。` | 建議 |
| `starter/HeavyRover.java:17`（teacher 同） | 「先全部拒絕條件」語句不完整 | `// TODO 3：新公開入口自己檢查停止。先檢查所有拒絕條件，全部通過才更新整組狀態。` | 可選 |
| `starter/Rover.java:41`（teacher:42；第 8–10 週沿用） | 註解說「呼叫者只傳課堂小整數」，程式實際會忽略 0 和負數，兩者不一致 | `// 已提供。只接受正數，0 或負數會被忽略；停止與整組行動仍由入口負責。` | 可選 |
| `teacher/transfer/CargoRover.java:15` | `3 - cargo` 的 3 沒有說明。同週的 `HeavyRover` 用具名常數，作法不一致 | 加 `// 貨物容量固定 3（作業規格）。`，或比照 `DRILL_COST` 改用具名常數 | 可選 |

## 處理狀態（2026-11-12）

依教師指示已補上全部「建議」項，「可選」項未處理。重跑 `tools/verify.py` 通過。

**2026-11-12 改寫**：教師回饋上表的建議寫法不夠清楚，程式中的註解已改寫成完整句子，說明「做什麼」與「為什麼」，並避開學生還不熟的術語。實際內容以程式檔為準；各週 `tools/verify.py` 重跑通過。
