# 第 11 週｜泛型與型別安全（Codex 版本）

初稿由 Codex 製作。承接現行第 10 週完成版。這週用同一個 `SampleGroup<T>` 管理樣本盒、重型車及混合來源，讓編譯器知道可以放什麼、取回的是什麼型態。三節各 50 分鐘，休息另計；工時為備課估計。

| 文件 | 用途 |
|---|---|
| [學生實作講義](student_lab.md) | 預測、四組 TODO、AI 反例與本人證據 |
| [投影片原稿](week11_slides_codex.md)／[HTML](week11_slides_codex.html) | 先獨立範例，再主線實作；含講者備註與教師附錄 |
| [範例說明](examples/README.md) | Object 盒子對照、泛型盒子、操作與預期輸出 |
| [作業說明](assignment.md)／[AI 紀錄範本](AI_USAGE_template.md) | 最大來源查詢、繳交與驗收 |
| [教師筆記](teacher_notes.md)／[教師工具](tools/README.md) | 逐題答案、誤解、測試與維護 |
| [類別演進](diagrams/evolution.svg)／[TODO 位置](diagrams/todo.svg)／[同一物件參考](diagrams/references.svg) | 圖與同名 `.puml` 原稿一併保存 |
| [交接](handoff.md)／[驗證紀錄](verification.md) | 能力邊界與實測範圍 |
| [生成規劃](week11_教材生成規劃.md) | 依第 7～10 週變動修訂的製作依據 |

## 準備與執行位置

先備為類別／建構子、ArrayList、普通迴圈、介面、參考型態及前週的報表過載。使用過泛型集合，不代表已會設計泛型類別，開場會先診斷。前週個別作業不是本週共同先備。

把 `starter/` 頂層的 **15 個 Java 檔**複製到自己的 `src/`。本週只需修改 `SampleGroup.java`。起始碼已包含前週完成的樣本報表與 `HeavyRover.execute` 指令 4 覆寫，請勿換成第 7 週舊檔。

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md        （從 student_lab.md 另存）
  AI_USAGE.md          （從 AI_USAGE_template.md 另存）
  java-11/
    src/              （starter 頂層 15 檔，作業再新增入口）
    examples/         （需要自己跑短例時複製三個 Java 檔）
    experiments/      （編譯反例副本，不交回正式 src）
    ai-check/         （模擬 AI 副本，獨立編譯）
```

日期換成實際上課日。短例是兩個小程式（ObjectBox 自帶 main；Box 配 TypedBoxDemo），可在 VS Code 執行；主線超過兩檔，可在 IntelliJ IDEA 開啟同一個 `java-11`，選已安裝 JDK、將 `src` 標記為來源根目錄，再從 `SampleGroupDemo.main` 執行。不建立 Maven／Gradle 或巢狀 repo，以一套 IDE 完成即可。

在自己的 `java-11` 執行，編譯成功後才執行下一行：

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out SampleGroupDemo
java -cp out CompareMissions
```

起始 `SampleGroupDemo` 只印「尚未完成：TODO 1～3（保存與取回）還沒通過，請依講義 B 檢查。」TODO 1～3 正確但總和未完成時改印「尚未完成：TODO 1～3 已能保存與取回，TODO 4 總樣本數還沒通過。」兩者都是未完成提示。舊入口 `CompareMissions`、`HeavyRoverTry`、`SampleReportDemo`、`SampleOverloadDemo` 已可跑前週基準。完成後主線九行見學生講義 C；九行正確仍要跑講義 C1 的 `LiveTotalTry`（探索後總和）與 C2 第 2 題。

## 本週資料與行為約定

來源有效且非 null；`get(index)` 的索引為 `0 <= index < size()`；樣本數與總和為 int 範圍內的非負小整數。空群組筆數與總和為 0，不讀第 0 筆。相同來源加入兩次代表兩筆，總和算兩次。群組保存原物件參考，統計每次讀來源目前值，查詢不執行車輛行動，也不交出內部清單。

任務維持固定 `int[]`，每次新車、新任務、只 run 一次。0 返回、1 探索、2 掃描、3 充電；重型車另接受 4 鑽探，一般車拒絕 4。`countCommands()` 仍只有 `[0..3]` 四格，4 會寫日誌與成功／拒絕筆數。達標與耗盡同時發生時成功優先。執行及讀任務結果時不從外部操作同一車。

`examples/`、`src/`、`teacher/`、`ai-check/`、`experiments/` 分開編譯與輸出，同名類別不能一起編譯。教師工具與教師作業解答不要複製進學生 `src`。不要求 JUnit、例外處理、執行緒、完整萬用字元或真實 AI 服務。
