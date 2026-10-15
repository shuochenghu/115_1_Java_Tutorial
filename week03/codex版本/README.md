# 第三週教材：火星探測車互動任務

對象為已有基礎 C# WinForms 經驗的資管系二年級學生。依課綱回顧 Java 選擇與重複結構，承接第二週能源儀表板，完成可操作的任務；不需要陣列、集合、自訂物件模型或 JUnit。

## 學習目標與前置能力

能用 if 與 switch 實現行動規則、以 for 預測充電、以 while 驅動任務，並說明 do-while 首次執行的差異。能追蹤狀態與有效範圍，以反例驗證 AI 建議。

前置為第二週 int、double、boolean、String、比較與邏輯運算、累加及簡單 static 方法。Scanner 與控制流程在本週介紹；第二週 locked 僅為條件診斷，本週任務固定未鎖定。

## 檔案索引

作業範圍與繳交清單請先看 [第三週作業說明](assignment.md)；固定狀態入口屬教師測試工具，不是學生另一份必寫作業。

| 檔案 | 用途 |
|---|---|
| [assignment.md](assignment.md) | 必做功能、測試、繳交檔案及驗收標準 |
| [投影片 Markdown](week03_slides_codex.md)／[HTML](week03_slides_codex.html) | Marp 16:9，沿用第一週樣式、附講者備註 |
| [student_lab.md](student_lab.md) | A–F 可填寫的操作、預測、追蹤與驗收 |
| [teacher_notes.md](teacher_notes.md) | 150 分鐘安排、答案、常見錯誤與差異化 |
| [examples](examples/README.md) | 四個獨立可執行的小範例 |
| [starter/RoverMission.java](starter/RoverMission.java) | 可編譯的 TODO 骨架，功能尚未完成 |
| [teacher/RoverMission.java](teacher/RoverMission.java) | 完整互動任務 |
| [teacher/FixedStateMission.java](teacher/FixedStateMission.java) | 教師固定狀態測試入口 |
| [AI_USAGE_template.md](AI_USAGE_template.md) | 四欄 AI 使用紀錄範本，附填寫範例 |
| [verification.md](verification.md) | 作者實測環境、案例、限制 |

## 執行方式

學生延用 `115_1_Java/YYYY_MM_DD/java-03/src`，日期由教師公告。複製 examples 內四個 Java 檔及 starter 的 RoverMission 至 src；當日 LAB_REPORT.md、AI_USAGE.md 放在日期目錄。不要複製教師同名類別到同一專案。

在 java-03 內：

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out EnergyStatus
java -cp out SingleAction
java -cp out ChargeForecast
java -cp out LoopComparison
java -cp out RoverMission
```

起始碼先輸入 0 測試返回；完成 TODO 後用 1、1、2 成功取得 5 份樣本，剩餘 6 格能源、3 次行動。輸入契約為 Java int 範圍內整數，直到成功、耗盡或 0 返回；非整數與輸入中斷處理不列入本週。

教師在本教材目錄編譯：

```powershell
javac -encoding UTF-8 -d out-teacher teacher/*.java
java -cp out-teacher RoverMission
```

本週不限定特定 JDK 版本，使用教室或自己已安裝的 JDK，記錄實際 java／javac 版本；編譯指令不設定固定目標版本。IDE 與終端機應使用一致的 JDK，版本錯配時清除舊編譯產物後重編譯。過去實測環境與本次複核見 verification，不代表所有版本均已驗證。三節各 50 分鐘、休息另計；內容較密，完整骨架為教師支援，課堂必做只補三個行動分支。

## 保存與取回

無本機 repo 時 clone；已有副本先保存工作，再於 GitHub Desktop Fetch／Pull。下課前保留 15 分鐘存檔、檢查差異、commit、push，核對 GitHub 最新版本；另以全新 clone 編譯重跑，不能只依賴舊 out。指令補充沿用[第一週](../../week01/codex版本/git_cli_reference.md)，包含 `git pull --ff-only`。未實際操作遠端者須據實記錄。
