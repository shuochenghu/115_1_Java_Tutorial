# 第五週｜把任務日誌設計成物件（Codex 版本）

課綱主題為類別與物件設計 I：封裝、建構子與設計原則，以及 AI 協作需求轉換。對象為有基礎 C# WinForms 經驗的資管系二年級學生。以三節各 50 分鐘規畫，休息另計；這是備課配置，尚未學生試教。

第四週要同時維護 commandLog、energyLog、resultLog，本週讓同一步資料由一個 MissionStep 保存，再用 ArrayList<MissionStep> 回放。行動規則與既有輸出不變，讓學生能判斷「表示方式改了，行為是否保持」。

## 學習成果

- 區分類別、物件、參考變數、實例欄位、區域變數與參數。
- 寫出有參數建構子，利用 this 分辨同名欄位與參數，以 getter 及物件方法讀取資料。
- 解釋私有欄位、受控的狀態修改、每個物件獨立狀態，以及共用參考的效果。
- 將三條平行清單改成一條物件清單，保留順序、每步快照與所有終止規則。
- 把需求轉為類別責任與方法契約，用反例檢驗 AI 的設計建議。

## 檔案索引

| 檔案 | 用途 |
|---|---|
| [投影片 Markdown](week05_slides_codex.md)／[HTML](week05_slides_codex.html) | Marp 16:9，附講者備註與範例檔案位置 |
| [student_lab.md](student_lab.md) | A–H 可填講義，另存 LAB_REPORT.md |
| [assignment.md](assignment.md) | 必做／延伸、案例、繳交與評分 |
| [teacher_notes.md](teacher_notes.md) | 150 分鐘節奏、逐題答案、示範與診斷 |
| [examples/README.md](examples/README.md) | 四個示範入口與預期結果 |
| [starter/MissionStep.java](starter/MissionStep.java)／[MissionLog.java](starter/MissionLog.java) | 六個 TODO 群組的可編譯骨架 |
| [teacher/MissionStep.java](teacher/MissionStep.java)／[MissionLog.java](teacher/MissionLog.java) | 完整答案 |
| [teacher/MissionStepCheck.java](teacher/MissionStepCheck.java)／[MissionLogCheck.java](teacher/MissionLogCheck.java) | 物件檢查及第四週完整輸出回歸檢查 |
| [teacher/FixedScriptMission.java](teacher/FixedScriptMission.java) | 教師固定初始狀態入口 |
| [AI_USAGE_template.md](AI_USAGE_template.md) | 四欄簡表與虛構／模擬完整示範 |
| [handoff.md](handoff.md) | 第四至第六週能力與資料契約 |
| [verification.md](verification.md) | 本次實測、版本及限制 |
| [tools/README.md](tools/README.md) | 教師維護與驗證方式 |

## 學生操作

無本機副本時 clone；已有副本先保存，再 Fetch／Pull。沿用學期 repo，在 `115_1_Java/YYYY_MM_DD/java-05/src/` 放 examples 六個 Java 檔及 starter 兩個檔案，日期以實際上課日替換。LAB_REPORT.md、AI_USAGE.md 放當日日期目錄，專案 README 放 java-05。不要另建巢狀 repo，也不要加入 teacher 同名檔案。

在 java-05 內執行，編譯成功才繼續：

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out CreateStepDemo
java -cp out BatteryDemo
java -cp out ReferenceDemo
java -cp out SnapshotDemo
java -cp out MissionLog
```

MissionStep 是資料類別，不含 main；執行入口為 MissionLog。起始碼能編譯安全結束，但日誌尚未實作，不算通過作業。依講義順序完成 TODO 1–6，每次重新編譯。主作業仍修改 main 的固定 int[] script，不讀取 args；繳交前恢復 `{1,1,2}`。

教學先以 VS Code 開兩檔基本範例；SnapshotDemo 需要三個檔案合作時，再加入 IntelliJ IDEA。兩套工具使用同一份來源，不要求每題都重做；IDEA 的來源根目錄、JDK 與 main 選擇見學生講義 D。GitHub Desktop 仍管理整個學期 repo。

本週不限定特定 JDK 版本。使用一般 class/main，無預覽語法、record、繼承設計或額外函式庫；環境錯配沿用第一週 IDE/JDK 診斷。不要把本機編譯成功推論成所有 JDK 或 IDE GUI 都已測過。

## 教師操作

在本教材目錄分開編譯教師檔案：

```powershell
javac -encoding UTF-8 -Xlint:all -d out-teacher teacher/*.java
java -cp out-teacher MissionStepCheck
java -cp out-teacher MissionLogCheck
java -cp out-teacher FixedScriptMission 2 4 2
```

教師工具可搭配學生完成的 MissionStep／MissionLog，只取這一組，不與 teacher 的同名答案一起編譯。out、out-teacher、.class 不應提交學生 repo。

## 設計邊界與來源

目前 energy、samples、actionCount 及停止條件仍在 MissionLog；本週集中把歷史記錄建模，不要求同時完成完整 Rover 系統。MissionStep 建構契約為 energy 0..20、result 非 null 的既有代碼、command 可為任何 int；呼叫端負責提供正確資料，完整防護與例外處理留後續。

RoverBattery 是概念示範，允許 energy=0 的電池充電；MissionLog 是整個任務，初始能源 0 時已結束，不能執行充電。兩者責任不同，不能拿電池方法取代任務停止規則。

教科書依本專案 Deitel《Java How to Program, Early Objects》11e EPUB 核對：§3.2–3.5 為主，§8.3、§8.4、§8.6、§8.7 補充；有參數建構子採單一版本，不要求 §8.5 的過載。以章節引用，不猜紙本頁碼。官方語言依據見教師筆記；情境與活動為自編。
