# 第 4 週教材（claude 版本）

本目錄所有檔案的初稿皆由 Anthropic Claude（Claude Code）生成，教師審閱後使用。同一週的 Codex 版本另放在 `week04/codex版本/`，兩版獨立製作。專案資料夾由兩版共用，因此本版的文件檔名都加上 `claude`。Java 檔案的檔名必須和類別名稱相同，所以維持原名，只用所在資料夾區分。

- **課綱主題**：陣列、ArrayList 與 Collections Framework 入門；Prompt 設計練習——描述資料結構需求
- **對象**：資管系二年級，大一學過 C# WinForms；第 1–3 週已完成 Java 環境、型態與參考、選擇與重複結構
- **參考書**：Deitel, *Java How to Program, Early Objects* 11e，7.2–7.4、7.7–7.9、7.14–7.16；16.2–16.4、16.6–16.7

## 故事線

延續第 2–3 週的火星探測車。第 3 週結束時只剩最後的能源、樣本、行動次數，無法回放過程。本週新增以下功能：

1. 用陣列統計指令次數，並用陣列當作「指令腳本」，取代逐筆輸入。
2. 用 `ArrayList` 記錄每一步，任務結束後回放與統計。
3. 以「三條平行清單容易對不齊」引出第 5 週的自訂類別。

## 檔案

| 檔案 | 用途 |
|---|---|
| [week04_slides_claude.md](week04_slides_claude.md)／[.html](week04_slides_claude.html) | Marp 投影片 35 頁，16:9，每頁有 `claude` 徽章與講者備註；有程式的頁面左上角以藍色標籤標出對應範例檔，第 6 頁為範例檔對照表 |
| [week04_實作講義_claude.md](week04_實作講義_claude.md) | 學生講義：0、A–H 段，含預測／實際欄位 |
| [week04_作業說明_claude.md](week04_作業說明_claude.md) | 必做功能、測試、繳交檔案、驗收標準 |
| [week04_教師筆記_claude.md](week04_教師筆記_claude.md) | 時間配置、全部解答、常見錯誤、AI 除錯參考答案、評分重點 |
| [AI使用紀錄表_範本_claude.md](AI使用紀錄表_範本_claude.md) | 本週 AI 使用紀錄範本，區分模擬建議與真實 AI |
| [week04_驗證紀錄_claude.md](week04_驗證紀錄_claude.md) | 實際執行結果與未驗證項目 |
| `code/examples/` | `ArrayBasics`、`ArrayReference`、`ScriptArgs`、`ListBasics`（學生複製） |
| `code/starter/MissionLog.java` | 學生起始碼，TODO 1–5，可直接編譯執行 |
| `code/teacher/MissionLog.java` | 完整解答 |
| `code/teacher/MissionLogCheck.java` | 教師檢查工具：12 項案例一次跑完 |

`starter` 與 `teacher` 都有 `MissionLog` 類別，不要放進同一個專案一起編譯。

## 執行

學生在 `115_1_Java/YYYY_MM_DD/java-04/` 內執行：

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
java -cp out MissionLog 3 9 0
```

教師在本目錄檢查解答：

```powershell
javac -encoding UTF-8 -d out-teacher code/teacher/*.java
java -cp out-teacher MissionLogCheck
```

## 匯出投影片

```powershell
npx -y @marp-team/marp-cli@latest week04_slides_claude.md -o week04_slides_claude.html
```

## 與前幾週的一致性

- 沿用第 1 週 claude 版的 Marp 樣式與 `by-claude` 徽章。
- 沿用第 2–3 週的探測車規則、結束判斷順序、`115_1_Java/YYYY_MM_DD/java-NN/src` 結構、150 分鐘節奏（最後 15 分鐘保存推送），以及「模擬 AI 錯誤，先預測再重現」的除錯練習形式。
- 不限定 JDK 版本：程式只用到 Java 11 就有的語法（`List.of` 需 Java 9 以上），學生用電腦上已安裝的 JDK 即可。驗證結果見驗證紀錄。
