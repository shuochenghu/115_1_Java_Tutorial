# 第 10 週程式註解檢視（claude 版本）

本檔由 Anthropic Claude（Claude Code）於 2026-11-12 撰寫，檢視本資料夾 `examples/`、`starter/`、`teacher/`、`ai/` 的 Java 註解是否方便教學與學生理解。只提建議，未修改任何 codex 檔案。

**等級**：「建議」表示補上後對教學或理解有明確幫助；「可選」表示不改也可以。

## 總評

本週註解品質好：

- `Rover`、`HeavyRover` 沿用自前週的過時檔頭都已改寫。
- 教師 `SampleReport` 每個過載都說明了用途。
- `SampleOverloadDemo` 寫明「引數在編譯期的型態決定簽名」。
- 作業參考解說明了單參數入口的轉交方式。

缺的主要是三個新檔沒有檔頭，其中短例把三個類別放在同一個檔案，是學生第一次看到的結構。

## 建議

| 位置 | 問題 | 建議寫法 | 等級 |
|---|---|---|---|
| `examples/MemberOverloadDemo.java:1` | 沒有檔頭；一檔放三個類別是新結構，學生會疑惑為何其他週都是一檔一類別 | `// 短例：同一檔放三個類別，只有 public 類別須與檔名相同；主線仍維持一檔一類別。` 再加一行 `// 對照：describe() 是覆寫（同簽名），describe(int)、describe(String) 是過載（同名、不同參數）。` | 建議 |
| `ai/SampleReport.java:1` | 第 7–9 週的 ai 檔都在檔頭標明「教師設計的模擬 AI 回覆」，本週只在方法內一行 | 檔頭加 `// 教師設計的模擬 AI 回覆，刻意有錯，不是真實服務輸出。只在隔離的 ai-check 編譯。` | 建議 |
| `ai/SampleReport.java:4` | `instanceof` 與轉型是前兩週明訂不教的語法 | `// instanceof 與 (Rover) 轉型只需讀懂，不需會寫。` | 可選 |
| `starter/SampleReport.java:8` | TODO 3 沒寫轉交方向；學生若在 Rover 版內呼叫 `print(label, rover)` 會呼叫到自己，無限遞迴 | `// TODO 3：新增 int 版；讓 SampleSource 版先讀數量，再交給 int 版印出。` | 可選 |
| `starter/SampleOverloadDemo.java:1`、`teacher/SampleReport.java:1` | 沒有檔頭 | 例如 `// 同一台車經兩種參考型態呼叫報表，比較選到的簽名。` | 可選 |
| `starter/Rover.java:42`（teacher 同） | `addSamples` 的註解與程式實際行為不一致（見第 7 週檢視） | 同第 7 週建議 | 可選 |

## 處理狀態（2026-11-12）

依教師指示已補上全部「建議」項，「可選」項未處理。重跑 `tools/verify.py` 通過。

**2026-11-12 改寫**：教師回饋上表的建議寫法不夠清楚，程式中的註解已改寫成完整句子，說明「做什麼」與「為什麼」，並避開學生還不熟的術語。實際內容以程式檔為準；各週 `tools/verify.py` 重跑通過。
