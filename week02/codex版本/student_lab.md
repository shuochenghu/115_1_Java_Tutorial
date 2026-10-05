# 第二週實作｜火星探測車任務儀表板

作業的必做範圍、繳交清單與驗收標準見 [assignment.md](assignment.md)。本講義另存後作為實作與測試報告，不必再寫一份重複報告。

你是任務控制員。今天用固定資料判讀探測車狀態；下週才讓它接收指令與改變狀態。規則是教學遊戲設定，不是太空工程模型。

## 0. 取回與建立今日工作位置（10 分鐘，含診斷）

沿用學期 repo `115_1_Java`。沒有本機副本時用 GitHub Desktop **Clone repository**；已有副本時先保存並提交本機工作，再 **Fetch origin → 有更新才 Pull origin**。遇到衝突請教師協助，不強制覆蓋。

上課日期由教師指定，不把課綱日期區間當成確定日期。在 repo 內新增 `實際日期/java-02/src`（將「實際日期」替換成例如 `YYYY_MM_DD` 的真實上課日期）；不另建 Git repo。

- 複製 `starter/RoverDashboard.java`、兩支 `examples/*.java` 到 `src`。
- 本講義另存當日 `LAB_REPORT_WEEK02.md`，AI 範本另存 `AI_USAGE_WEEK02.md`。
- 沿用根 `.gitignore` 的 `*.class`、`out/`、`bin/` 規則。
- 下列指令都在 **當日 java-02 目錄**執行，先確認 `src` 存在。

```powershell
javac --release 21 -encoding UTF-8 -d out src/RoverDashboard.java src/TypeReferenceDemo.java src/OperatorDemo.java
java -cp out RoverDashboard
```

教學目標 JDK 21；若教師核准使用較新 JDK，以 `--release 21` 保持目標版本。編譯失敗先修正，不執行殘留 `.class`。

| 基本資料 | 填寫 |
|---|---|
| 姓名／上課日期／專案實際路徑 | 待填 |
| GitHub repo、取回的 commit | 待填 |
| `java -version`、`javac -version` | 待填 |

診斷：`double p = calculateBatteryPercent(7);` 的引數、參數、回傳值各是什麼？`return` 會自動顯示到畫面嗎？回答：待填。

## A. 為探測車選型（15 分鐘）

| 資料 | 選擇 Java 型態 | C# 對照／理由 |
|---|---|---|
| 名稱「赤砂號」 | 待填 | 待填 |
| 區域 'A' | 待填 | 待填 |
| 能源 7 格 | 待填 | 待填 |
| 電量百分比 35.0 | 待填 | 待填 |
| 是否鎖定 false | 待填 | 待填 |

1. Java 的八種基本型態：待填。哪個常用型態不在其中？待填。
2. Java 能寫 `bool locked = false;` 或 `string name = "赤砂號";` 嗎？修正：待填。
3. `char zone = 'A';` 與 `String zone = "A";` 的型態是否相同？待填。
4. `long distance = 3000000000L;` 與 `float sensor = 3.5F;` 的後綴用途：待填。

執行 `java -cp out TypeReferenceDemo`。八種型態變數的前兩行實際輸出：待填。

## B. 值與參考追蹤（25 分鐘）

先讀 `TypeReferenceDemo`，先填預測，再執行觀察。

| 步驟 | energy | backupEnergy | 原因 |
|---|---|---|---|
| `int energy = 7; int backupEnergy = energy;` | 待填 | 待填 | 待填 |
| `energy = 6;` | 待填 | 待填 | 待填 |

| 步驟 | consoleName 指向的字串內容 | logName 指向的字串內容 |
|---|---|---|
| `String consoleName = "赤砂號";` | 待填 | 尚未宣告 |
| `String logName = consoleName;` | 待填 | 待填 |
| `consoleName = "晨星號";` | 待填 | 待填 |

畫出最後兩個變數與字串物件的箭頭：待填。這是重新指定參考，還是修改「赤砂號」物件？待填。能據此推論所有參考物件都不能修改嗎？待填。

密室門禁短題（最多 5 分鐘，已含在本節）：

```java
String code = "MARS";
String receivedCode = new String("MARS");
System.out.println(code == receivedCode);
System.out.println(code.equals(receivedCode));
```

| 檢查 | 預測 | 實際 | 比較的是什麼 |
|---|---|---|---|
| `code == receivedCode` | 待填 | 待填 | 待填 |
| `code.equals(receivedCode)` | 待填 | 待填 | 待填 |

教師用 `new String` 保證兩個不同字串參考，不要求自行設計類別。若兩個字串常值用 `==` 恰好得到 true，為何不能據此用 `==` 實作一般通行碼內容比較？待填。

`null` 表示參考未指向物件；本週字串題使用非 null 資料，先不加完整防護。

## C. 能源運算與條件（25 分鐘）

用 energy=7，先預測 `OperatorDemo`：

| 運算式 | 預測 | 實際 | 每步先算什麼／型態 |
|---|---|---|---|
| `energy / 20 * 100.0` | 待填 | 待填 | 待填 |
| `(double) (energy / 20) * 100` | 待填 | 待填 | 待填 |
| `(double) energy / 20 * 100` | 待填 | 待填 | 待填 |
| `energy / 20.0 * 100` | 待填 | 待填 | 待填 |
| `(int) 7.9` | 待填 | 待填 | 待填 |
| `"合計=" + 2 + 3` | 待填 | 待填 | 待填 |
| `"合計=" + (2 + 3)` | 待填 | 待填 | 待填 |

1. `int n=0; n+=2; n++; n--;` 最後 n：待填。`n++` 作獨立敘述有何用途？待填。
2. 能源介於 0 與 20（含兩端）的條件：待填。不要寫數學連寫 `0 <= energy <= 20`。
3. 能源至少 6 且未鎖定的條件：待填。
4. 能源無效（小於 0 或大於 20）的條件：待填。

short-circuit evaluation 追蹤：`reportCheck()` 會印「右側檢查已執行」再回傳 true。

| 運算 | 右側方法會被呼叫嗎？ | 最後布林結果 | 實際輸出 |
|---|---|---|---|
| `false && reportCheck()` | 待填 | 待填 | 待填 |
| `true \|\| reportCheck()` | 待填 | 待填 | 待填 |
| `true && reportCheck()` | 待填 | 待填 | 待填 |

實際執行：`java -cp out OperatorDemo`。`&&` 左側 false、`||` 左側 true 時，右側不求值；不只是在最後才比較結果。

手搖飲配料短題（最多 3 分鐘）：有 53 單位珍珠，每份用 8 單位，最多完整做幾份？剩多少？運算式與實測：待填。

## D. 完成任務儀表板（25 分鐘）

電池容量 20；探索一次用 6 格。核心計算假設 energy 為 0 至 20。完成 starter 的五個 TODO，不加入 if、迴圈或輸入工具：

1. 跟做：完成 `calculateBatteryPercent(int energy)`，使用浮點除法，方法只回傳。
2. 獨立完成：完整探索次數、剩餘能源、有效範圍、探索資格。
3. 可改名稱與區域。只輸出資格，今天還不真正執行探索。
4. 保存人工初版；重新編譯，逐次修改 energy／locked 並重跑。

| energy | locked | 預測百分比／探索次數／餘量／資格 | 實際四個結果 | 通過／修正 |
|---|---|---|---|---|
| 0 | false | 待填 | 待填 | 待填 |
| 5 | false | 待填 | 待填 | 待填 |
| 6 | false | 待填 | 待填 | 待填 |
| 7 | false | 待填 | 待填 | 待填 |
| 20 | false | 待填 | 待填 | 待填 |
| 6 | true | 待填 | 待填 | 待填 |

獨立範圍題：只評估 `energy >= 0 && energy <= 20`；分別代入 -1、0、20、21，結果：待填。-1、21 不屬於本週計算方法的輸入契約，不要求儀表板拒絕這些值。

## E. AI 建議反例與自主驗證（20 分鐘）

以下是教材自編的 AI 建議情境，並非真實服務對話：

```java
static double calculateBatteryPercent(int energy) {
    return energy / 20 * 100.0;
}
```

1. 對方說「回傳 double 所以一定有小數」，用 energy=7 解釋反例：待填。
2. 為何只測 energy=0 和 20 可能漏掉問題？待填。
3. 在測試副本重現錯誤，實際輸出與最小修正：待填。
4. 恢復正確程式，再跑 D 的六筆。證據位置：待填。
5. 用 AI_USAGE 範本記錄實際對話。沒有 AI 權限可自行解釋本情境，並明寫未使用服務；不捏造模型、回覆或測試。

## F. 課末檢核（15 分鐘）

不看教師答案完成：

1. energy=13、locked=false，百分比／探索次數／餘量／資格：待填。
2. 為何 `(double)(7 / 20)` 不能補回被捨去的部分？待填。
3. `String a="A"; String b=a; a="B";`，b 內容與理由：待填。
4. 寫出「能源無效或車輛被鎖定」的條件：待填。
5. 用自己的話解釋方法回傳與 Console 輸出：待填。

同儕回饋：哪一筆測試最能揭露錯誤？為什麼？待填。

## G. 保存、推送與繳交（15 分鐘）

- [ ] 當日專案 README 寫明用途、JDK、上述編譯指令、energy=7 的預期輸出。
- [ ] 本講義、AI 紀錄、原始碼已存檔；`.class` 和 out 未納入版本控制。
- [ ] Desktop 選對原本 repo/main，閱讀 Changes，Commit to main（例如 `week02: complete rover dashboard`）。
- [ ] Push origin 成功，再到 GitHub 核對最新 commit 與當日來源檔、紀錄。
- [ ] 繳交 repo 網址與最終 commit；若最後又修改紀錄，重新 commit/push。
- [ ] 無法 Push 時告知教師，備份完整 repo 至不會還原的位置；不要只留在還原磁碟。

| 保存證據 | 填寫 |
|---|---|
| 最終 commit／GitHub 連結 | 待填 |
| 網站來源檔、紀錄與本機一致 | 待填 |
| 尚未完成與後續事項 | 待填 |

CLI 選讀：已有副本且工作已保存時，依第一週補充使用 `git pull --ff-only`；結束用 `git add`（明確檔案）、`git commit`、`git push`。命令失敗先確認路徑與歷史，不強制推送。

課後重現：將已推送 repo clone 到另一個不存在的檢查位置，在當日 java-02 重新編譯並執行；不要複製 out。記錄 clone commit 與輸出：待填。此步可依教師安排課後完成。

## 下週入口

你已會算「是否能探索」，下週用 if 讓條件決定行動，用 switch 選探索／掃描／充電，用迴圈完成任務。本週不要求 Scanner、陣列、集合或自行設計 Rover 類別。
