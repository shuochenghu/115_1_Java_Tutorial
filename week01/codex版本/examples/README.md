# java-01：Java 入門與方法移植

## 課程資訊

- 上課日期：2026/09/18
- 作者：請填寫課堂暱稱
- JDK 版本：請填寫 `java -version` 的結果
- 開發工具：VS Code＋Extension Pack for Java、IntelliJ IDEA

## 專案目的

1. 熟悉 Java 程式的編譯與執行。
2. 將 C# 的字串處理方法移植到 Java。
3. 在兩套 IDE 中執行相同程式並比較結果。
4. 使用 Git／GitHub 保存課堂成果。

## 檔案結構

```text
java-01/
├── README.md
└── src/
    ├── HelloCourse.java
    └── GreetingApp.java
```

- `HelloCourse.java`：輸出課程問候訊息。
- `GreetingApp.java`：呼叫 `buildGreeting`，產生不同姓名的問候字串。
- `out/`：編譯時產生，不提交至 GitHub。

## 編譯與執行

請先在 PowerShell 切換到此專案目錄：

```text
115_1_Java/2026_09_18/java-01
```

編譯：

```powershell
javac -encoding UTF-8 -d out src/HelloCourse.java src/GreetingApp.java
```

執行：

```powershell
java -cp out HelloCourse
java -cp out GreetingApp
```

若編譯失敗，先修正錯誤，再重新編譯與執行。

## 預期輸出

### HelloCourse

初始版本：

```text
Hello, Java!
Course: Object-Oriented Programming
```

完成課堂的問候文字修改後，第一行應改為：

```text
Hello, my Java course!
```

### GreetingApp

完成 TODO 後：

```text
Hello, Ada!
Hello, Grace!
Hello, Ada Lovelace!
```

`buildGreeting` 應回傳字串，由 `main` 負責輸出。

## 測試紀錄

請依實際操作填寫，不要只複製預期結果。

| 測試項目 | 實際結果 | 是否通過 |
|---|---|---|
| 命令列編譯與執行 | 待填 | 待填 |
| VS Code 執行 | 待填 | 待填 |
| IntelliJ IDEA 執行 | 待填 | 待填 |
| 姓名 Ada | 待填 | 待填 |
| 姓名 Grace | 待填 | 待填 |
| 姓名 Ada Lovelace | 待填 | 待填 |

## 學習紀錄

當日完整紀錄位於上一層日期目錄：

- [實作講義與結果](../LAB_REPORT.md)
- [AI 使用與人工驗證紀錄](../AI_USAGE.md)

## 目前進度

- 已完成：待填。
- 尚未完成／目前錯誤：待填。
- 下次上課要繼續的工作：待填。

## 下課前保存（GitHub Desktop）

教室電腦關機後會還原，請依序完成：

1. 儲存所有程式與紀錄。
2. 在 GitHub Desktop 確認 Current repository 是 `115_1_Java`、Current branch 是 `main`；可用 Repository → Show in Explorer 確認路徑。
3. 在 Changes 查看差異，勾選要保存的原始碼、README 與當日紀錄，排除編譯產物。
4. Summary 填 `2026_09_18: save java-01 and learning records`，按 Commit to main。
5. 按 Push origin，等待完成；若沒有新修改可略過 Commit，但仍須確認提交已上傳。
6. 用 Repository → View on GitHub 開啟 main，核對最新 commit 與當日程式、紀錄，再登出及關機。

Changes 空白不代表已上傳；Commit 只保存在本機。如果 Push 失敗，立即請教師協助，保存完成前不要關機。
