# 第二週作業：火星探測車任務儀表板

完成課堂的 `RoverDashboard.java`，整理測試與學習紀錄後繳交。這份作業延續課堂成果，不必另外建立新專案。

截止時間與繳交平台：依教師公告。以下使用的日期目錄請替換成實際上課日期。

## 1. 要完成什麼？

從 [starter/RoverDashboard.java](starter/RoverDashboard.java) 開始，完成五個 TODO：

| 項目 | 功能要求 |
|---|---|
| 電量百分比 | 電池容量 20 格；7 格時應為 35.0%。保留小數，方法只回傳結果，由 main 顯示。 |
| 完整探索次數 | 每次探索需要 6 格，計算不充電能完整探索幾次。 |
| 剩餘能源 | 計算完成上述探索後剩下幾格。 |
| 能源範圍 | 判斷能源是否介於 0 至 20，包含兩端。 |
| 探索資格 | 能源至少 6 格且未鎖定時為 true。 |

主程式使用固定資料，繳交版恢復 `energy=7`、`locked=false`。探測車名稱及區域可自行設定。數值計算的輸入假設為 0 至 20；本週只計算並顯示，不加入 Scanner、if、迴圈或實際探索行動。

## 2. 必做測試與紀錄

每次修改資料後重新編譯、執行，在 [student_lab.md](student_lab.md) D 填入預測與實際結果。

| energy | locked | 百分比 | 探索次數 | 餘量 | canExplore |
|---:|---|---:|---:|---:|---|
| 0 | false | 0.0 | 0 | 0 | false |
| 5 | false | 25.0 | 0 | 5 | false |
| 6 | false | 30.0 | 1 | 0 | true |
| 7 | false | 35.0 | 1 | 1 | true |
| 20 | false | 100.0 | 3 | 2 | true |
| 6 | true | 30.0 | 1 | 0 | false |

以上六筆的 `validEnergy` 都是 true。另完成講義 D 的獨立範圍題：只判斷 -1、0、20、21 是否有效，不要求以無效值執行整個儀表板。

完成講義 A–F 的作答、追蹤與反思，以及 G 的保存紀錄。講義 E 的模擬 AI 錯誤要有反例、最小修正與重測結果；可依教師提供的情境完成，不必使用付費 AI。不要把範例中的結果直接當成自己的實測。

## 3. 要繳交哪些檔案？

沿用學期 repo，檔案位置與講義一致：

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT_WEEK02.md
   ├─ AI_USAGE_WEEK02.md
   └─ java-02/
      ├─ README.md
      └─ src/
         ├─ RoverDashboard.java
         ├─ TypeReferenceDemo.java
         └─ OperatorDemo.java
```

- `RoverDashboard.java`：自己完成的作業程式。兩支 examples 程式可保留課堂使用的版本，無須另寫一份作業。
- `LAB_REPORT_WEEK02.md`：由學生講義另存，保留作答、測試與保存紀錄。
- `AI_USAGE_WEEK02.md`：由 [AI_USAGE_template.md](AI_USAGE_template.md) 另存，填四個必要欄位；未使用 AI 時依範本說明填寫。測試結果可引用講義，不必重抄。
- `java-02/README.md`：簡述功能、實際 JDK 版本、執行方式與 energy=7 的預期結果。

不要上傳 `out/`、`.class`，也不要把 starter 與 teacher 的同名類別同時放進 src。教師解答用於核對，不能當成自己撰寫的人工初版。

## 4. 執行、保存與繳交

在 `java-02` 目錄執行；編譯成功後再執行程式：

```powershell
javac --release 21 -encoding UTF-8 -d out src/*.java
java -cp out RoverDashboard
```

1. 存檔，檢查 GitHub Desktop 的修改差異，commit、push。
2. 到 GitHub 核對程式及兩份紀錄，確認最新 commit 已出現在遠端。
3. 將 repo 網址、當日作業目錄及最終 commit 交到教師指定平台。若繳交前又修改，重新 commit、push 並更新繳交版本。
4. 依講義 G 在另一個新位置 clone，重新編譯並執行，將路徑、版本及結果記入報告；可課後完成。未成功的步驟如實記錄問題，不填「通過」。

## 5. 驗收標準

- 程式可編譯，五個 TODO 完成，六筆測試及範圍題符合需求。
- 能解釋整數除法、參考比較，以及回傳值與輸出的差別。
- 有自己的預測、實測與修正說明，AI 紀錄符合實際情形。
- 教師可依 README 找到程式、重新執行並核對繳交版本。

沿用課綱評量方式，本說明不另訂配分。自訂名稱與顯示文字可自由發揮；額外 GUI、輸入選單或新行動不是必交項目。
