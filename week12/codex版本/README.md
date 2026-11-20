# 第 12 週｜例外處理與執行緒基礎（Codex 版本）

初稿由 Codex 製作。以「校園列印額度」練習錯誤發生後的流程與狀態，再用兩個獨立工作認識執行緒。**本週可獨立開始，不需要第 11 週程式、泛型或探測車作業。** 三節各 50 分鐘，休息另計；時間是備課估計。

學完應能說明：誰拋出例外、誰接住、哪一行被跳過、拒絕後額度是否不變，以及兩個工作的哪一些先後順序有保證。

| 文件／資料夾 | 用途 |
|---|---|
| [學生講義](student_lab.md) | 預測、A1～A4、AI 反例、B1～B2、本人證據 |
| [投影片原稿](week12_slides_codex.md)／[HTML](week12_slides_codex.html) | 三節課活動與講者備註，最後有教師附錄 |
| [獨立範例](examples/README.md) | 文字轉換、未捕捉／捕捉對照、finally 選讀 |
| [額度起始碼](starter/quota/PrintQuota.java)／[入口](starter/quota/PrintQuotaDemo.java) | A2、A3；共兩檔 |
| [工作起始碼](starter/threads/PrintTask.java)／[入口](starter/threads/ThreadStartDemo.java) | B1、B2；共兩檔 |
| [作業](assignment.md)／[AI 紀錄範本](AI_USAGE_template.md) | 單筆方法、批次統計與繳交 |
| [教師筆記](teacher_notes.md)／[驗證工具](tools/README.md) | 完整答案、診斷、維護與可重跑檢查 |
| [例外流程](diagrams/exception_flow.svg)／[責任圖](diagrams/roles.svg)／[執行緒時序](diagrams/thread_flow.svg) | 同名 `.puml` 可編輯原稿一起保存 |
| [交接](handoff.md)／[驗證紀錄](verification.md)／[生成規劃](week12_教材生成規劃.md) | 能力邊界、實測範圍與設計依據 |

## 準備與檔案位置

先備是 if、普通迴圈、字串、陣列、方法及基本類別；介面由本週小圖回顧。不假設已學過 C# 例外處理。JDK 使用自己的已安裝版本，不鎖版本、不使用預覽功能。

在既有 `115_1_Java` repo 的當日資料夾建立下列結構，日期換成實際上課日：

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md      從 student_lab.md 另存
  AI_USAGE.md        從 AI_USAGE_template.md 另存
  java-12/
    src/            複製 starter/quota/ 的兩檔
    threads/        複製 starter/threads/ 的兩檔
    examples/       複製 examples/ 的兩個 Java 檔
    ai-check/       AI 活動才建立，放錯誤副本與已完成的額度入口
```

兩組各兩檔、各有 main，不互相相依。以 VS Code 開當日資料夾；IntelliJ IDEA 使用者可沿用同一組來源與入口，一套 IDE 完成即可。不要把 teacher、starter、ai 中的同名類別放進同一來源目錄。

## 執行方式

以下工作目錄都是自己的 `java-12`。先確認編譯成功，再執行下一行；失敗時不要使用舊的 out 結果。可以先以 `java -version`、`javac -version` 記下版本。

```powershell
javac -encoding UTF-8 -d out-quota src/PrintQuota.java src/PrintQuotaDemo.java
java -cp out-quota PrintQuotaDemo
```

起始時只印「尚未完成：請先完成 A2，再接上 A3 的逐筆處理。」完成後應出現講義的七行結果。只改 PrintQuota 還不會自動啟用入口，A3 也要自己寫。

```powershell
javac -encoding UTF-8 -d out-threads threads/PrintTask.java threads/ThreadStartDemo.java
java -cp out-threads ThreadStartDemo
```

起始時只印「尚未完成：請完成 B1、B2，再啟動工作實驗。」完成後共 8 行：開始、兩個工作各三步、結束。跨工作順序不固定，不要求交替。

例外流程主線是單執行緒。工作實驗不持有額度物件、不連真實印表機；所有工作有限且自然結束。`join()` 等待執行緒終止，不能當成所有業務工作必定成功的證明。

Windows 終端若中文顯示異常，先確認字型及 UTF-8 顯示設定。作者自動工具以明確的 stdout 編碼擷取結果，不把這類環境參數列為學生語法要求。

## 保存成果

繳交 `src/`、`threads/`、`LAB_REPORT.md`、`AI_USAGE.md`。範例可一併保存；AI 錯誤證據貼報告，不把錯誤副本當正式答案。排除 out 目錄與 `.class`；若既有 `.gitignore` 尚未涵蓋，加入 `*.class`、`out*/`。

最後 15 分鐘以 GitHub Desktop 檢查差異、填寫 commit 說明、commit、push，再到遠端核對當日路徑與最新內容。接著將 repo 取回到原 repo 外的另一個位置，重新編譯兩個入口；不要在日期資料夾內建巢狀 repo。登入或網路未完成就記「待補」，不可把本機 commit 說成已上傳。
