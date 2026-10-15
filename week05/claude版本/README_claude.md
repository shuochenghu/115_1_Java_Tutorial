# 第 5 週教材（claude 版本）

本目錄所有檔案的初稿皆由 Anthropic Claude（Claude Code）生成，教師審閱後使用。同一週的 Codex 版本另放在 `week05/codex版本/`，兩版獨立製作。專案資料夾由兩版共用，因此本版的文件檔名都加上 `claude`。Java 檔案的檔名必須和類別名稱相同，所以維持原名，只用所在資料夾區分，並在檔頭註解標明「claude 版本」。

- **課綱主題**：類別與物件設計(I)：封裝、建構子與設計原則——AI 協作需求轉換練習
- **對象**：資管系二年級，大一學過 C# WinForms；第 1–4 週已完成 Java 環境、型態與參考、選擇與重複結構、陣列與 ArrayList
- **參考書**：Deitel, *Java How to Program, Early Objects* 11e，3.2–3.5、8.2–8.4、8.6–8.7、8.13

## 故事線

延續第 4 週 claude 版的火星探測車任務日誌。第 4 週用 `int[] state` 和三條平行清單記錄任務，留下兩個問題：三條清單容易對不齊，`state[1]` 要靠記憶才知道是樣本數。本週：

1. 用 `RawStep` 把一步打包成物件，再看見 public 欄位的問題。
2. 用 `Battery` 示範封裝：`private` 欄位、建構子修正初始值、方法拒絕不合法操作。
3. 把同樣的設計套用到任務：`Rover` 保管狀態與規則，`MissionStep` 記錄一步，`MissionLog` 改用 `ArrayList<MissionStep>`，輸出與第 4 週逐字相同。
4. 需求轉換：把「樣本儲存艙」需求寫成類別設計卡，用它檢查模擬 AI 產生的類別。

程式由三個以上的類別合作，本週重新使用 IntelliJ IDEA（第 1 週已介紹），VS Code 仍可使用。

## 檔案

| 檔案 | 用途 |
|---|---|
| [week05_slides_claude.md](week05_slides_claude.md)／[.html](week05_slides_claude.html) | Marp 投影片，16:9，每頁有 `claude` 徽章；有程式的頁面左上角以藍色標籤標出對應範例檔 |
| [week05_實作講義_claude.md](week05_實作講義_claude.md) | 學生講義：0、A–G 段，含預測／實際欄位 |
| [week05_作業說明_claude.md](week05_作業說明_claude.md) | 必做功能、測試、繳交檔案、驗收標準 |
| [week05_教師筆記_claude.md](week05_教師筆記_claude.md) | 時間配置、全部解答、常見錯誤、AI 除錯參考答案、評分重點 |
| [AI使用紀錄表_範本_claude.md](AI使用紀錄表_範本_claude.md) | 本週 AI 使用紀錄範本，區分模擬建議與真實 AI |
| [week05_交接_claude.md](week05_交接_claude.md) | 給第 6 週的能力清單與資料契約 |
| [week05_驗證紀錄_claude.md](week05_驗證紀錄_claude.md) | 實際執行結果與未驗證項目 |
| `code/examples/` | `RawStep`＋`RawStepDemo`、`Battery`＋`BatteryDemo`（學生複製） |
| `code/starter/` | `Rover`、`MissionStep`、`MissionLog`（TODO 1–7，可直接編譯執行）與已完成的 `RoverTry` |
| `code/teacher/` | 完整解答 `Rover`、`MissionStep`、`MissionLog`；檢查工具 `MissionLogCheck`（27 項，含五條必做測試的完整輸出逐行比對）；延伸題參考 `SampleBay`、`SampleBayCheck` |

`starter` 與 `teacher` 有同名類別，不要放進同一個專案一起編譯。

## 執行

學生在 `115_1_Java/YYYY_MM_DD/java-05/` 內執行：

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
java -cp out MissionLog 3 9 0
java -cp out RoverTry
```

教師在本目錄檢查解答：

```powershell
javac -encoding UTF-8 -d out-teacher code/teacher/*.java
java -cp out-teacher MissionLogCheck
java -cp out-teacher SampleBayCheck
```

## 匯出投影片

```powershell
npx -y @marp-team/marp-cli@latest --no-stdin week05_slides_claude.md -o week05_slides_claude.html
```

## 與前幾週的一致性

- 沿用第 4 週 claude 版的 Marp 樣式、`by-claude` 徽章與範例檔標籤。
- 沿用探測車規則、結束判斷順序、`115_1_Java/YYYY_MM_DD/java-NN/src` 結構、150 分鐘節奏（最後 15 分鐘保存推送），以及「模擬 AI 錯誤，先預測再重現」的除錯練習形式。
- 程式輸出只用英數字，與前幾週相同，避免教室終端機的中文編碼問題。
- 不限定特定 JDK 版本：學生使用已安裝的 JDK，記錄 java／javac 實際版本，不設定固定編譯目標。
