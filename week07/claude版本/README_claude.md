# 第 7 週教材（claude 版本）

本目錄所有檔案的初稿皆由 Anthropic Claude（Claude Code）生成，教師審閱後使用。同一週的 Codex 版本若有製作，放在 `week07/codex版本/`，兩版獨立製作。專案資料夾由兩版共用，因此本版的文件檔名都加上 `claude`。Java 檔案的檔名必須和類別名稱相同，所以維持原名，只用所在資料夾區分，並在檔頭註解標明「claude 版本」。

- **課綱主題**：繼承(I)：類別繼承基礎——AI 生成繼承程式碼閱讀與追蹤
- **對象**：資管系二年級，大一學過 C# WinForms；第 1–6 週已完成 Java 環境、型態與參考、選擇與重複結構、陣列與 ArrayList、封裝與建構子、UML 類別圖與責任分配
- **參考書**：Deitel, *Java How to Program, Early Objects* 11e，第 9 章 9.1–9.7

## 故事線

延續第 6 週 claude 版的探測車任務。第 6 週課末預告「重型探測車」：複製一份 `Rover` 會有兩份程式，而且 `Mission` 只接受 `Rover`。本週：

1. extends：用飲料店會員 `Member`／`VipMember` 看子類別只寫新增的部分，以及哪些成員能直接用。
2. 建構子、`super`、private 與 protected：用兩個編譯錯誤實驗看建構子順序與 private 的界線；Java 的 `protected` 也開放給同一套件，和 C# 不同。
3. is-a 還是 has-a，以及類別圖的空心三角形與 `#`。
4. 重型探測車：`Rover` 提供三個 protected 方法並用它們重構探索、掃描（輸出與第 6 週逐字相同）；`HeavyRover` 繼承 `Rover`，新增「鑽探」。
5. AI 生成繼承程式碼閱讀與追蹤：一份模擬 AI 寫的 `HeavyRover`，缺 `super`、重複宣告欄位（兩份狀態）、漏停止檢查；學生先預測、編譯、測試，再做最小修正。作業另有一份能編譯卻印出 `null` 的模擬 AI `Camera`。

**範圍決定**：第 6 週預告的是「探索一次得 3 份」，那需要覆寫，屬於第 8 週。本週的重型探測車探索、掃描、充電都和 `Rover` 相同，只多了鑽探；講義與投影片都向學生說明這個安排。

## 檔案

| 檔案 | 用途 |
|---|---|
| [week07_slides_claude.md](week07_slides_claude.md)／[.html](week07_slides_claude.html) | Marp 投影片，39 頁，16:9，每頁有 `claude` 徽章；有程式或圖的頁面左上角以藍色標籤標出對應檔案 |
| [week07_實作講義_claude.md](week07_實作講義_claude.md) | 學生講義：0、A–G 段，含預測／實際欄位 |
| [week07_作業說明_claude.md](week07_作業說明_claude.md) | 重型探測車、社團器材子類別、模擬 AI `Camera` 追蹤、設計文件、繳交與驗收標準 |
| [DESIGN_範本_claude.md](DESIGN_範本_claude.md) | 學生 `DESIGN.md` 範本 |
| [week07_教師筆記_claude.md](week07_教師筆記_claude.md) | 時間配置、全部解答與實際輸出、常見錯誤、批改工具、評分重點 |
| [AI使用紀錄表_範本_claude.md](AI使用紀錄表_範本_claude.md) | 本週 AI 使用紀錄範本，區分模擬回覆與真實 AI |
| [week07_交接_claude.md](week07_交接_claude.md) | 給第 8 週的能力清單、資料契約與建議入口 |
| [week07_驗證紀錄_claude.md](week07_驗證紀錄_claude.md) | 實際執行結果與未驗證項目 |
| `code/examples/` | `Member`、`VipMember`、`InheritDemo`（學生複製） |
| `code/starter/` | `Rover`（TODO 1–2）、`HeavyRover`（TODO 3–4）、已完成的 `Mission`、`MissionStep`、`MissionLog`、`RoverTry`、`HeavyRoverTry`；可直接編譯執行 |
| `code/starter/club/` | 作業用：`Equipment`（完成）、`ShelfTry`（要等學生寫好 `Camera`、`Projector` 才能編譯） |
| `code/ai/` | 模擬 AI 回覆：`HeavyRover`（講義 E 段）、`Camera`（作業第 3 部分）。刻意含錯，不是參考答案 |
| `code/teacher/` | 完整解答與檢查工具 `HeavyRoverCheck`（44 項） |
| `code/teacher/club/` | `Camera`、`Projector` 參考解答與 `ShelfCheck`（11 項） |
| `diagrams/*.puml`／`.svg` | 類別圖原始碼與圖檔；`ai_heavy_claude` 畫的是模擬 AI 的錯誤設計，`heavy_focus_claude` 是投影片用的節錄圖 |

`starter`、`teacher`、`ai` 有同名類別，不要放進同一個資料夾一起編譯。

## 執行

學生在 `115_1_Java/YYYY_MM_DD/java-07/` 內執行：

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out InheritDemo
java -cp out MissionLog 3 9 0
java -cp out RoverTry
java -cp out HeavyRoverTry
java -cp out ShelfTry
```

講義 E 段與作業第 3 部分在 `java-07/ai-check/`、`java-07/ai-camera/` 內各自執行 `javac -encoding UTF-8 -d out *.java`。

教師在本目錄檢查解答：

```powershell
javac -encoding UTF-8 -d out-teacher code/teacher/*.java
java -cp out-teacher HeavyRoverCheck
javac -encoding UTF-8 -d out-club code/teacher/club/*.java
java -cp out-club ShelfCheck
```

## 重新產生類別圖與投影片

類別圖需要 PlantUML（[官方 GitHub Releases](https://github.com/plantuml/plantuml/releases) 的 jar，本次使用 `plantuml-mit-1.2026.9.jar`，未放入本專案）。資料夾名稱含中文時，PlantUML 的檔案模式可能讀錯路徑，改用 `-pipe` 逐檔轉換；`-P layout=smetana` 不需要 Graphviz：

```powershell
cmd /c "java -jar plantuml-mit-1.2026.9.jar --charset UTF-8 -P layout=smetana --svg -pipe < diagrams\heavy_claude.puml > diagrams\heavy_claude.svg"
```

在 Git Bash 可直接寫成 `java -jar plantuml-mit-1.2026.9.jar --charset UTF-8 -P layout=smetana --svg -pipe < x.puml > x.svg`（第 6 週已確認兩種寫法輸出相同）。

```powershell
npx -y @marp-team/marp-cli@latest --no-stdin week07_slides_claude.md -o week07_slides_claude.html
```

投影片以相對路徑引用 `diagrams/*.svg`，搬移 HTML 時要連同 `diagrams/` 一起帶走。

## 與前幾週的一致性

- 沿用第 6 週 claude 版的 Marp 樣式、`by-claude` 徽章、範例檔標籤、`DESIGN.md` 與 PlantUML 寫法。
- 沿用探測車規則、結束判斷順序、命令列參數腳本、`115_1_Java/YYYY_MM_DD/java-NN/src` 結構、150 分鐘節奏（最後 15 分鐘保存推送），以及「模擬 AI 回覆，先預測再驗證」的練習形式。
- `Mission`、`MissionStep`、`MissionLog` 與第 6 週相同，只改檔頭說明；`MissionLog` 輸出與第 5、6 週逐字相同。
- 程式輸出只用英數字，避免教室終端機的中文編碼問題。
- 不限定特定 JDK 版本：學生使用已安裝的 JDK，記錄 java／javac 實際版本，不設定固定編譯目標。`super(...)` 一律寫在建構子第一行，在 JDK 25 放寬規則前後都正確。
