# 第二週｜火星探測車任務儀表板

對象為已有 C# WinForms 基礎的資管系二年級學生。以固定資料的火星探測車儀表板教授 Java 基本型態、參考型態與運算子；預計三節各 50 分鐘，休息另計。

## 學習成果

- 依用途選 int、double、boolean、char、String，辨識八種基本型態及 C# 命名差異。
- 追蹤基本值與參考值的複製，區別 String 的 == 與 equals。
- 正確計算電量、探索次數、餘量與布林資格，解釋整數除法及 short-circuit evaluation。
- 保留人工初版，用反例驗證 AI 建議，保存並推送學習證據。

第一週先備為 main、方法引數／參數與回傳、Console、編譯執行、學期 repo。今天不要求 Scanner、分支、迴圈、陣列、集合或自訂物件模型。規則與第二至三週設計相同；第二週只分析探索，第三週加入互動行動。

## 檔案索引

作業範圍與繳交清單請先看 [第二週作業說明](assignment.md)；實作講義用來逐步操作與填寫紀錄。

| 檔案 | 用途 |
|---|---|
| [assignment.md](assignment.md) | 必做功能、測試、繳交檔案及驗收標準 |
| [week02_slides_codex.md](week02_slides_codex.md) | Marp 投影片與講者備註 |
| [week02_slides_codex.html](week02_slides_codex.html) | 同步匯出的瀏覽器投影片，由統籌產出 |
| [student_lab.md](student_lab.md) | 學生可填講義：追蹤、預測、實測、反思、保存 |
| [teacher_notes.md](teacher_notes.md) | 教學節奏、完整答案、預期輸出、來源與常見誤解 |
| [examples/TypeReferenceDemo.java](examples/TypeReferenceDemo.java) | 八種基本型態、複製、String 比較 |
| [examples/OperatorDemo.java](examples/OperatorDemo.java) | 整數除法、轉型、short-circuit evaluation、配料短題 |
| [starter/RoverDashboard.java](starter/RoverDashboard.java) | 可編譯的五個 TODO 起始碼，尚不滿足需求 |
| [teacher/RoverDashboard.java](teacher/RoverDashboard.java) | 完整儀表板答案 |
| [AI_USAGE_template.md](AI_USAGE_template.md) | 四欄 AI 使用紀錄範本，附填寫範例 |
| [handoff.md](handoff.md) | 第三週可承接能力與教材位置 |
| [VALIDATION.md](VALIDATION.md) | 作者實際驗證與未覆蓋限制 |

## 快速執行

教學目標 JDK 21。從本教材目錄執行以下指令；每次編譯成功才執行 java：

```powershell
javac --release 21 -encoding UTF-8 -d out/examples examples/TypeReferenceDemo.java examples/OperatorDemo.java
java -cp out/examples TypeReferenceDemo
java -cp out/examples OperatorDemo
javac --release 21 -encoding UTF-8 -d out/teacher teacher/RoverDashboard.java
java -cp out/teacher RoverDashboard
javac --release 21 -encoding UTF-8 -d out/starter starter/RoverDashboard.java
java -cp out/starter RoverDashboard
```

starter 與 teacher 類別同名，務必分開輸出目錄與執行 classpath。學生將 starter 與 examples 複製到學期 repo 當日 `java-02/src`，按 lab 執行；不複製教師答案作為人工初版。

energy=7、未鎖定時，完整答案輸出百分比 35.0、完整探索 1 次、餘 1 格、有效 true、探索資格 true。starter 會輸出佔位的 0／false；可編譯不等於完成需求。

## 教學與驗證界線

150 分鐘是備課配置，尚未學生試教。Java 程式作者實測與 HTML 版面檢查分開記錄；未代學生執行 GitHub、AI 服務或雙 IDE 操作。AI 錯誤情境為教材自編，範本保留待填，不代表真實對話。
