# 第 6 週教材（claude 版本）

本目錄所有檔案的初稿皆由 Anthropic Claude（Claude Code）生成，教師審閱後使用。同一週的 Codex 版本若有製作，放在 `week06/codex版本/`，兩版獨立製作。專案資料夾由兩版共用，因此本版的文件檔名都加上 `claude`。Java 檔案的檔名必須和類別名稱相同，所以維持原名，只用所在資料夾區分，並在檔頭註解標明「claude 版本」。

- **課綱主題**：類別與物件設計(II)：UML 類別圖與物件導向設計思維——AI 輔助系統設計練習
- **對象**：資管系二年級，大一學過 C# WinForms；第 1–5 週已完成 Java 環境、型態與參考、選擇與重複結構、陣列與 ArrayList、封裝與建構子
- **參考書**：Deitel, *Java How to Program, Early Objects* 11e，1.5.10–1.5.11、3.2.4、3.3–3.4、8.8、8.11

## 故事線

延續第 5 週 claude 版的探測車任務。第 5 週留下三個問題：能源 0 的探測車仍能被直接呼叫 `charge()`、`MissionLog` 一個類別同時負責流程、計算與輸出、課末畫的方框草圖還不精確。本週：

1. 讀與畫類別圖：用 `Battery` 學三格與可見性，只看圖就能判斷哪些呼叫能編譯。
2. 關係與多重性：`Flashlight`（欄位，關聯）、`Charger`（參數，相依），共用電池的預測題。
3. 責任與設計思維：停止規則收回 `Rover`；流程與計算搬到新類別 `Mission`；`MissionLog` 只剩讀參數與輸出。
4. 依新圖重構，輸出與第 5 週逐字相同，再讓 `DESIGN.md` 的圖與程式一致。
5. AI 輔助系統設計：課堂設計「探測車加裝樣本艙」並檢查模擬 AI 的類別圖；作業換成「社團器材借用」，自己先畫，再與 AI 的圖逐項比對。

類別圖用 PlantUML 文字寫在 `DESIGN.md`，推上 GitHub 後以 PlantUML 官方的「PlantUML for GitHub」瀏覽器擴充功能（Chrome、Firefox）顯示。

## 檔案

| 檔案 | 用途 |
|---|---|
| [week06_slides_claude.md](week06_slides_claude.md)／[.html](week06_slides_claude.html) | Marp 投影片，16:9，每頁有 `claude` 徽章；有程式或圖的頁面左上角以藍色標籤標出對應檔案 |
| [week06_實作講義_claude.md](week06_實作講義_claude.md) | 學生講義：0、A–G 段，含預測／實際欄位 |
| [week06_作業說明_claude.md](week06_作業說明_claude.md) | 程式 TODO、必做測試、兩份設計、繳交檔案、驗收標準 |
| [DESIGN_範本_claude.md](DESIGN_範本_claude.md) | 學生 `DESIGN.md` 範本，含所有 PlantUML 區塊骨架 |
| [week06_教師筆記_claude.md](week06_教師筆記_claude.md) | 時間配置、全部解答、模擬 AI 圖的問題清單、常見錯誤、批改工具、評分重點 |
| [AI使用紀錄表_範本_claude.md](AI使用紀錄表_範本_claude.md) | 本週 AI 使用紀錄範本，區分模擬回覆與真實 AI |
| [week06_交接_claude.md](week06_交接_claude.md) | 給第 7 週的能力清單與資料契約 |
| [week06_驗證紀錄_claude.md](week06_驗證紀錄_claude.md) | 實際執行結果與未驗證項目 |
| `code/examples/` | `Battery`、`Flashlight`、`Charger`、`RelationDemo`（學生複製） |
| `code/starter/` | `Rover`（TODO 1–2）、`Mission`（TODO 3–6），以及已完成的 `MissionStep`、`MissionLog`、`RoverTry`；可直接編譯執行 |
| `code/teacher/` | 完整解答與檢查工具 `MissionCheck`（31 項，含輸出逐行比對與設計檢查） |
| `code/teacher/club/` | 作業「社團器材借用」的參考實作與 `ClubCheck`（17 項）；學生只需設計 |
| `diagrams/*.puml`／`.svg` | 投影片與講義用的類別圖原始碼與圖檔；`*_ai_claude` 是刻意含錯的模擬 AI 回覆，`*_ref_claude` 是教師參考答案 |

`starter` 與 `teacher` 有同名類別，不要放進同一個專案一起編譯。

## 執行

學生在 `115_1_Java/YYYY_MM_DD/java-06/` 內執行：

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
java -cp out MissionLog 3 9 0
java -cp out RoverTry
java -cp out RelationDemo
```

教師在本目錄檢查解答：

```powershell
javac -encoding UTF-8 -d out-teacher code/teacher/*.java
java -cp out-teacher MissionCheck
javac -encoding UTF-8 -d out-club code/teacher/club/*.java
java -cp out-club ClubCheck
```

## 重新產生類別圖與投影片

類別圖需要 PlantUML（[官方 GitHub Releases](https://github.com/plantuml/plantuml/releases) 的 jar，本次使用 `plantuml-mit-1.2026.9.jar`，未放入本專案）。資料夾名稱含中文時，PlantUML 的檔案模式可能讀錯路徑，改用 `-pipe` 逐檔轉換；`-P layout=smetana` 使用與擴充功能相同的排版引擎，不需要 Graphviz：

```powershell
cmd /c "java -jar plantuml-mit-1.2026.9.jar --charset UTF-8 -P layout=smetana --svg -pipe < diagrams\week06_mission_claude.puml > diagrams\week06_mission_claude.svg"
```

透過 `cmd /c` 轉向是為了讓檔案內容原樣傳遞；Windows PowerShell 5.1 的 `|` 管線會重新編碼文字，中文註解可能變成亂碼。在 Git Bash 可直接寫成 `java -jar plantuml-mit-1.2026.9.jar --charset UTF-8 -P layout=smetana --svg -pipe < x.puml > x.svg`。兩種寫法本次都實測過，輸出的 SVG 位元組相同。

```powershell
npx -y @marp-team/marp-cli@latest --no-stdin week06_slides_claude.md -o week06_slides_claude.html
```

投影片以相對路徑引用 `diagrams/*.svg`，搬移 HTML 時要連同 `diagrams/` 一起帶走。

## 與前幾週的一致性

- 沿用第 5 週 claude 版的 Marp 樣式、`by-claude` 徽章與範例檔標籤。
- 沿用探測車規則、結束判斷順序、命令列參數腳本、`115_1_Java/YYYY_MM_DD/java-NN/src` 結構、150 分鐘節奏（最後 15 分鐘保存推送），以及「模擬 AI 回覆，先自己做再比對」的練習形式。
- 程式輸出只用英數字，避免教室終端機的中文編碼問題。
- 不限定特定 JDK 版本：學生使用已安裝的 JDK，記錄 java／javac 實際版本，不設定固定編譯目標。
