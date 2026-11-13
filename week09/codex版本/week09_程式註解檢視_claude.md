# 第 9 週程式註解檢視（claude 版本）

本檔由 Anthropic Claude（Claude Code）於 2026-11-12 撰寫，檢視本資料夾 `examples/`、`starter/`、`teacher/`、`ai/` 的 Java 註解是否方便教學與學生理解。只提建議，未修改任何 codex 檔案。

**等級**：「建議」表示補上後對教學或理解有明確幫助；「可選」表示不改也可以。

## 總評

starter 的 TODO（1、2、3a–3c、4）分得清楚，`SampleSource` 的教師版寫明了「非負、無副作用」的契約。不過本週新概念的註解偏少，主要有兩點：

- 抽象類別、介面參數和匿名類別這三個新概念，在程式中缺少說明。
- 教師版拿掉了第 8 週的「TODO n 教師答案」標記，學生和教師難以把答案對回 TODO。

## 建議

| 位置 | 問題 | 建議寫法 | 等級 |
|---|---|---|---|
| `starter/HeavyRover.java:1`（teacher 同） | 檔頭仍寫「第八週：…」。第 10 週已改寫，本週沒有 | `// 重型探測車：覆寫產量方法，每次成功探索得 3 份；鑽探規則沿用前週。` | 建議 |
| `teacher/Rover.java:3` | 本週核心「為何改成抽象類別」沒有說明 | `// 抽象類別：只放共同狀態與流程，不能直接 new Rover；每種車都必須提供自己的探索產量。` | 建議 |
| `teacher/SampleReport.java:1` | 沒有檔頭。「參數型態是介面」正是本週重點 | `// 參數型態是介面：任何實作 SampleSource 的物件（車、樣本盒）都能傳入；報表只讀樣本數。` | 建議 |
| `examples/FixtureDemo.java:1、12` | 沒有檔頭；匿名類別是本週閱讀層級的新語法，卻沒有標示 | 檔頭 `// 閱讀範例：用具名巢狀類別和匿名類別，建立兩個相同行為的 SampleSource。`；第 12 行前 `// 匿名類別：在 new 的同時寫出實作，沒有類別名稱。` | 建議 |
| `teacher/StandardRover.java:7`、`teacher/SampleTray.java:9`、`teacher/SampleReport.java:2` | 教師答案沒有「TODO n 教師答案」標記，第 8 週有 | 比照第 8 週，在答案前加 `// TODO 1 教師答案：…` 等 | 建議 |
| `teacher/Rover.java:53` | 抽象方法沒有本體，學生第一次看到 | `// 抽象方法：只有宣告、沒有本體，具體子類別必須覆寫。` | 可選 |
| `teacher/Rover.java:15` | `getSamples()` 上的 `@Override` 來自介面，學生可能以為是覆寫父類別 | `// 實作 SampleSource 的讀取方法。` | 可選 |
| `starter/SampleTray.java:3` | `final` 是第 6 週的教師補充，不在必學範圍 | `// final：建構後不再改變，報表讀取不會改到數量。` | 可選 |
| `starter/Rover.java:44`（teacher:43） | `addSamples` 的註解與程式實際行為不一致（見第 7 週檢視） | 同第 7 週建議 | 可選 |

## 處理狀態（2026-11-12）

依教師指示已補上全部「建議」項，「可選」項未處理。

教師答案標記：`Rover`（TODO 2、3b）、`StandardRover`（1）、`SampleSource`（3a）、`SampleTray`（3c）、`SampleReport` 與 `SampleReportDemo`（4）。重跑 `tools/verify.py` 通過。

**2026-11-12 改寫**：教師回饋上表的建議寫法不夠清楚，程式中的註解已改寫成完整句子，說明「做什麼」與「為什麼」，並避開學生還不熟的術語。實際內容以程式檔為準；各週 `tools/verify.py` 重跑通過。
