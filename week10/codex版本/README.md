# 第 10 週｜過載與多型（Codex 版本）

初稿由 Codex 製作。承接第 9 週的樣本報表。這週要能預測編譯器會選哪個同名簽名，再判斷覆寫的實例方法會執行哪個版本。三節各 50 分鐘，休息另計。

| 文件 | 用途 |
|---|---|
| [學生實作講義](student_lab.md) | 先預測、寫過載、驗證舊呼叫及除錯 |
| [Marp 原稿](week10_slides_codex.md)／[HTML](week10_slides_codex.html) | 課堂講授及活動入口 |
| [作業說明](assignment.md)／[AI 使用紀錄範本](AI_USAGE_template.md) | 快速查詢過載、人工驗證證據 |
| [教師筆記](teacher_notes.md)／[教師工具](tools/README.md) | 各節時間、解答、診斷、重跑驗證 |
| [交接](handoff.md)／[驗證紀錄](verification.md) | 第 11 週入口與實際驗證範圍 |
| [教材生成規劃](week10_教材生成規劃.md) | 教學取捨與生成範圍 |

## 開始實作

開場用第 9 週的 `SampleSource`、`Rover` 與 `exploreYield()` 做診斷。把 `starter/` 的 13 個 Java 檔複製到自己當日的 `src/`，把學生講義和 AI 範本另存為當日紀錄。

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md
  AI_USAGE.md
  java-10/
    src/
    examples/    （選擇在本機跑短例時）
    ai-check/    （活動 D 的獨立副本）
```

短例可用 VS Code。在 IntelliJ IDEA 開啟 `java-10`，選已安裝的 JDK，將 `src` 標記為來源根目錄，再由對應類別的 `main` 執行。一套 IDE 即可完成。

在 `java-10` 終端機執行以下指令。編譯成功後再執行 `java`。

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out CompareMissions
java -cp out SampleReportDemo
java -cp out SampleOverloadDemo
```

`SampleReportDemo` 一開始印樣本數 6、4、0。`SampleOverloadDemo` 在完成 TODO 4 前顯示「尚未完成」。`examples/`、`teacher/` 與 `ai-check/` 各有自己的編譯輸出目錄，同名類別分開編譯。

報表標籤與來源皆有效，手動數量是非負小整數。報表每次印一行，讀取不變更車輛或日誌。任務使用第 9 週的固定 `int[]` 指令 0 返回、1 探索、2 掃描、3 充電。每個任務配一台新車且執行一次，初始能源 0 至 20，樣本為非負小整數，腳本可為空陣列。探索耗 6 格，一般車得 2 份，重型車得 3 份。達標與耗盡同時成立時，任務成功優先。
