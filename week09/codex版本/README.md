# 第 9 週｜抽象類別與介面（Codex 版本）

初稿由 Codex 生成，供教師審閱後使用。本週承接第 8 週 Codex 教材。先讓每種探測車明確交代自己的探索產量，再做一份能讀取探測車和樣本盒的共用報表。

## 學習目標

- 能說明抽象類別為何仍有建構子、欄位與一般方法，並以 `Rover` 參考指向具體車輛。
- 能用介面描述報表真正需要的操作，並用相同呼叫方式取得不同來源的樣本數。
- 讀懂具名靜態巢狀類別和匿名類別的短例。先看固定重量的測試資料，教師可另示範任務步驟通知。
- 先提出介面草案，再寫可驗收的 Prompt。對照模擬 AI 回覆，重現問題、修正並重測。

先備為方法簽名、`extends`、`super`、`@Override`、父類別型態參考與基本陣列。開場用第 8 週基準診斷。三節各 50 分鐘，休息另計，為備課估計。

## 從哪裡開始

| 檔案 | 用途 |
|---|---|
| [學生實作講義](student_lab.md) | 預測、TODO、實測與同儕檢核 |
| [投影片原稿](week09_slides_codex.md)／[HTML](week09_slides_codex.html) | 課堂講授與活動入口 |
| [另一份投影片（claude）](week09_slides_codex_claude.md)／[HTML](week09_slides_codex_claude.html) | 由 Anthropic Claude（Claude Code）依本版教材製作，61 頁，每頁標 claude 徽章。先用 `examples/` 的三個範例分別講完抽象類別、介面、巢狀與匿名類別，再用 `starter/` 說明抽象 Rover 與 TODO 1–4，教師答案只放附錄。可與上列投影片擇一使用；HTML 需連同 `diagrams/` 資料夾 |
| [第 8→9 週類別圖演進（claude）](diagrams/evolution_w08_claude.svg)、[第 9 週完成版](diagrams/evolution_w09_claude.svg)、[starter 的 TODO 位置](diagrams/starter_todo_claude.svg)、[範例一](diagrams/parcel_claude.svg)、[範例二](diagrams/scale_claude.svg)、[範例三](diagrams/fixture_claude.svg)、[AI 參數審查](diagrams/ai_param_claude.svg) | 上列 claude 投影片使用的 PlantUML 圖，原稿為同名 `.puml`。演進圖以顏色標示新增、修改及與第 8 週相同的類別 |
| [示範程式說明（claude）](examples/README_claude.md) | `examples/` 三個獨立範例：抽象類別（包裹運費）、介面（行李箱與貓上秤）、巢狀與匿名類別（固定重量測試資料）。由 Claude 依教師要求拆分改寫，先看範例再做對應的講義段落 |
| [課後作業](assignment.md) | 獨立完成一種新來源 |
| [AI 使用紀錄範本](AI_USAGE_template.md) | 人工草案、Prompt、審查及重測 |
| [教師筆記](teacher_notes.md) | 節奏、解答、輸出與診斷 |
| [UML](diagrams/responsibilities.svg)／[PlantUML 原稿](diagrams/responsibilities.puml) | 繼承、實作、欄位與參數的關係 |
| [交接](handoff.md)／[驗證](verification.md) | 能力契約、實測與第 10 週入口 |
| [教師工具](tools/README.md) | 重跑程式檢查、產圖與匯出 |

將 `starter/` 的 12 個 Java 檔複製到本人的 `src/`，不須複製第 8 週程式。日期換成上課日，沿用同一學期 repo。

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md        # 從 student_lab.md 複製
  AI_USAGE.md          # 從 AI_USAGE_template.md 複製
  java-09/
    src/               # starter 12 檔，作業再新增兩檔
    examples/          # 需要本機跑示範時才複製 examples 的 10 個檔案
    ai-check/          # 講義 E 使用 src 的隔離副本
```

在 VS Code 開 `java-09`，終端機也在這裡。IntelliJ IDEA 示範使用同一資料夾，以已安裝的 JDK 設定 SDK，將 `src` 設為來源根目錄，再從 main 執行。以一套 IDE 完成實作即可。

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
java -cp out CompareMissions
java -cp out HeavyRoverTry
java -cp out SampleReportDemo
```

編譯成功再執行。起始的三個探測車入口會跑出基準結果。`SampleReportDemo` 印 `尚未完成`，是介面與報表待完成的提示。完成 TODO 1–4 後，報表應印出 `重型探測車 樣本數=6`、`樣本盒 樣本數=4` 和 `空盒 樣本數=0`。

`examples/`、`teacher/` 和 `ai-check/` 各自編譯。`teacher/`、`ai-check/` 與 `src/` 有同名類別，分開使用輸出目錄；`examples/` 的類別名稱不與主線重複，但仍單獨編譯，避免把示範混進作業。HTML 投影片若單獨分享，請同時附 `diagrams/` 並保留相對位置。

## 本週程式範圍

固定 `int[]` 腳本，`0` 返回，`1` 探索、`2` 掃描、`3` 充電；重型車另接受 `4` 鑽探（第 8 週 TODO 4 覆寫 `execute`）。其他整數記為無效指令。一般車探索耗 6 格得 2 份，重型車得 3 份。停止與日誌沿用第 8 週規則，達標和耗盡同時成立時以成功為準。每個任務配一台新車並只跑一次，初始能源為 0 至 20，樣本為非負小整數，腳本與報表來源均有效。報表只讀數量。鑽探可直接呼叫 `drill()`，也可由腳本指令 4 進入；`countCommands` 仍只統計 0 至 3。過載的選擇規則留到第 10 週。
