# 第四週｜火星探測車任務日誌（Codex 版本）

沿用前三週的 C# → Java 遷移與探測車故事，解決「只留下最後狀態，無法知道走過哪條路線」的問題。對象為已有基礎 C# WinForms 經驗的資管系二年級學生；以三節各 50 分鐘規畫，休息另計。

## 學習成果

- 用陣列保存固定指令腳本，使用索引與加強版 for 走訪，檢查合法索引。
- 解釋陣列參考的複製、修改元素與重新指定的差異。
- 使用 ArrayList<Integer>、ArrayList<String> 逐筆記錄、回放歷程。
- 使用 Collections.min、frequency 整理摘要，理解排序會改動原清單。
- 描述資料結構需求，用反例驗證 AI 建議，保存與推送成果。

## 檔案索引

| 檔案 | 用途 |
|---|---|
| [assignment.md](assignment.md) | 必做功能、測試、繳交與驗收 |
| [week04_slides_codex.md](week04_slides_codex.md)／[HTML](week04_slides_codex.html) | Marp 投影片與講者備註 |
| [student_lab.md](student_lab.md) | A–H 可填實作講義，另存 LAB_REPORT.md |
| [teacher_notes.md](teacher_notes.md) | 節奏、逐題答案、常見誤解與示範輸出 |
| [AI_USAGE_template.md](AI_USAGE_template.md) | 四欄精簡紀錄與虛構填寫範例 |
| [examples/README.md](examples/README.md) | 五個核心示範與一個選讀範例 |
| [starter/MissionLog.java](starter/MissionLog.java) | 可編譯的學生骨架，五個 TODO |
| [teacher/MissionLog.java](teacher/MissionLog.java) | 完整解答，無 TODO |
| [teacher/FixedScriptMission.java](teacher/FixedScriptMission.java) | 教師固定初始狀態觀察工具 |
| [teacher/MissionLogCheck.java](teacher/MissionLogCheck.java) | 教師輸出比對工具，不是學生 JUnit 作業 |
| [handoff.md](handoff.md) | 第三→四→五週能力與資料契約 |
| [verification.md](verification.md) | 實際驗證、使用版本與限制 |

## 學生操作

無本機 repo 時 clone；已有副本先保存再 Fetch／Pull。在學期 repo `115_1_Java/YYYY_MM_DD/java-04/src/` 放五個核心 examples 與 **starter** 的 MissionLog.java；日期以實際上課日替換。不要另建巢狀 repo，不要把同名 teacher 放進 src。

在 java-04 內執行，先確認編譯成功再執行：

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
java -cp out ArrayBasics
```

本週不限定 JDK 21 或其他特定版本。使用教室或自己的已安裝 JDK，記錄實際版本；程式採傳統 class/main、一般 API，無預覽功能，也不使用 List.of。版本錯配時沿用第一週 IDE/JDK 檢查方式。

測試其他路線：修改 main 的 `int[] script = {1, 1, 2};`，重新編譯再執行；繳交前恢復這條預設路線。命令列腳本是選讀，主作業不讀取 args。

## 教師操作

在本教材目錄，分開編譯教師檔案：

```powershell
javac -encoding UTF-8 -d out-teacher teacher/*.java
java -cp out-teacher MissionLogCheck
java -cp out-teacher FixedScriptMission 5 0 1 3 0
```

out-teacher、out、class 不應提交學生 repo。教師工具可搭配已完成的學生 MissionLog 編譯，不能同時編譯兩份 MissionLog。輸入契約為 Java int 範圍內整數，非整數與完整例外處理留後續。

## 邊界與下一週

主線保留 energy、samples、actionCount 具名變數，固定陣列放指令與計數，三條 ArrayList 放日誌；平行清單只是本週過渡設計。第五週以 MissionStep 的欄位、建構子與封裝解決資料對齊問題。

本週不要求自訂領域類別、介面實作、泛型設計、Lambda、Stream、Iterator、二維陣列或 JUnit；List/Set/Map 僅作用途概觀。

## 教科書與來源

Deitel & Deitel, Java How to Program, Early Objects，第 11 版 EPUB：§7.2–7.4、§7.7–7.9、§7.16；§16.2–16.5 與 §16.6–16.7 選讀。§7.14 命令列參數為延伸。以章節標示，不猜紙本頁碼；本週教材與探測車情境為自編，不複製教科書全文或圖。

官方 API 依據附於教師筆記。本週實測範圍見 verification.md，不把作者執行當作教室 GUI、遠端 GitHub 或學生試做已完成。
