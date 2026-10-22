# 第六週｜用類別圖分配責任（Codex 版本）

本週為「類別與物件設計 II：UML 類別圖與物件導向設計思維——AI 輔助系統設計練習」。對象為已有基礎 C# WinForms 經驗的資管系二年級學生。初稿由 Codex 生成，教師審閱後使用；與同週 Claude 版獨立製作。

第五週 Codex 版只把日誌變成 `MissionStep`，目前能源等仍在 `MissionLog`。本週先提供完整 `Rover` 作銜接，確認狀態與行動搬到物件的意思，再用類別圖引導學生把流程與歷史放進 `Mission`。學生只寫三組程式 TODO，不從零重做探測車。主作業維持固定 `int[] script`，不沿用 Claude 版的命令列輸入。

## 學習成果與課堂安排

- 從 Java 讀出類別圖的欄位、建構子、方法、可見性及 static；從圖判斷簡單呼叫。
- 以程式證據區分關聯與相依，正確解釋多重性。
- 用責任表區分目前狀態、任務流程、歷史快照與輸出。
- 完成 `Mission.run`、`countOk`、`lowestEnergy`，與第五週完整輸出比較。
- 先自己畫 `SampleCase` 設計，再判讀模擬 AI 圖，重現超量存入反例、修正與重測。

| 節次 | 配置 | 主要成果 |
|---|---|---|
| 第1節50分 | A診斷10、B類別圖20、C關係20 | 自己的第一張圖與共享電池追蹤 |
| 第2節50分 | D1責任與Rover銜接15、D2局部重構25、D3行為比對10 | 三組TODO與任務關係圖 |
| 第3節50分 | E人工設計／AI審查25、F修圖與檢核10、G保存15 | 初版／修正版、本人驗證、Git保存 |

150分鐘是備課配置，尚未學生試教。核心路線先做 `{1,1,2}` 與 `{3,9,0}`；其餘路線與完整紀錄課後補。課堂唯一完整AI設計活動是樣本盒；社團器材題列選做。聚合／組合、final參考說明列教師補充，不列必考新符號。

## 檔案索引

| 檔案 | 用途 |
|---|---|
| [投影片原稿](week06_slides_codex.md)／[HTML](week06_slides_codex.html) | 16:9、講者備註、對應檔案與活動 |
| [student_lab.md](student_lab.md) | A–G 可填講義，另存LAB_REPORT |
| [assignment.md](assignment.md) | 必做、六路線、繳交與評量 |
| [DESIGN_template.md](DESIGN_template.md) | 圖片、責任表、初版與AI採納理由 |
| [AI_USAGE_template.md](AI_USAGE_template.md) | 四欄紀錄與清楚標示的虛構示例 |
| [examples/README.md](examples/README.md) | 共享電池、樣本盒範例及完整輸出 |
| [starter/Mission.java](starter/Mission.java) | 三組學生TODO；同目錄其他四檔已完成 |
| [teacher/Mission.java](teacher/Mission.java) | 完整解答；同目錄含15項回歸及16項狀態檢查 |
| [teacher_notes.md](teacher_notes.md) | 逐題答案、節奏、診斷、來源與工具準備 |
| [handoff.md](handoff.md)／[verification.md](verification.md) | 跨週契約、實際驗證與限制 |
| [tools/README.md](tools/README.md) | 教師功能驗證、產圖與Marp維護 |

## 學生工作位置

沿用學期repo：`115_1_Java/YYYY_MM_DD/java-06/`，日期使用實際上課日。`src/` 放 examples六檔及starter五檔，共11檔；範例彼此獨立，主任務只需四個類別，`RoverDemo` 是銜接入口。LAB_REPORT.md與AI_USAGE.md放日期目錄，README.md與DESIGN.md放java-06。

只複製三份練習圖 `diagrams/practice.puml`、`task_template.puml`、`samplecase_initial.puml` 及其同名SVG到自己的diagrams，不把教師參考圖當自己的作答。`DESIGN_template.md` 另存DESIGN.md。

先在VS Code只編譯兩檔銜接範例；四個任務類別合作時再加入IntelliJ IDEA，兩套工具使用同份來源，不逐題重做。IDEA確認JDK、`src`來源根與main入口；不建Maven／Gradle或巢狀Git repo。工具步驟見講義。

```powershell
# 在自己的 java-06 內；編譯成功才執行
java -version
javac -version
javac -encoding UTF-8 -d out src/Rover.java src/RoverDemo.java
java -cp out RoverDemo
javac -encoding UTF-8 -d out src/*.java
java -cp out RelationDemo
java -cp out MissionLog
java -cp out SampleCaseDemo
```

`MissionLog`是主任務入口，`MissionStep`、`Mission`、`Rover`不是入口。starter可編譯安全結束，但任務日誌是空的，尚未滿足作業。

## 第一張圖就立即預覽

PlantUML原始碼放`.puml`，SVG是產出；改圖後重新產SVG，再由DESIGN.md嵌入圖片。GitHub可直接顯示SVG，不要求安裝瀏覽器擴充功能，也不必把圖送到外部服務。禁止只改SVG而讓原始碼不同步。

教師課前從[PlantUML官方發行頁](https://github.com/plantuml/plantuml/releases)準備jar；以下`C:\ClassTools\plantuml.jar`是教室配置範例，必須改成實際位置。在自己的java-06執行，下列`cmd /c`只用來原樣轉送UTF-8內容，避免Windows PowerShell 5.1文字管線改編碼：

```powershell
cmd /c "java -jar C:\ClassTools\plantuml.jar -charset UTF-8 -tsvg -pipe < diagrams\practice.puml > diagrams\practice.svg"
```

開啟剛產生的SVG，確認本次新增的方法出現。`!pragma layout smetana`已放在每張圖，不需另裝Graphviz。D、E段將檔名換成task_template、samplecase_initial並重新產圖。命令失敗時先保留原始碼，不把舊圖當本次結果；教師可用[產圖工具](tools/render_diagrams.py)當堂協助，不把「回家再看」當成預覽完成。第一次練習即預覽，最後15分鐘仍保留給保存與取回。

## 規則與限制

容量20；探索耗6得2、掃描耗2得1、充電+5封頂20，樣本至少5成功。每個Mission搭配一個新的Rover，只呼叫一次run；run期間與讀結果前不從其他位置操作同一Rover。script非null，初始energy 0..20、samples非負，不處理契約外資料。任務停止後不讀不記；每筆已讀指令（含拒絕、返回、無效）記行動後能源。結果優先SUCCESS、EXHAUSTED、RETURNED、UNFINISHED。空日誌仍輸出最低N/A及battery，與Codex第五週一致。

本週不限定JDK、不固定編譯目標、不用預覽語法。繼承、覆寫、例外處理、泛型設計與JUnit依後續週次安排。樣本盒沿用第五週需求，但本週重新提供完整程式，不假設上週選做已完成；不把樣本盒接進主任務。
