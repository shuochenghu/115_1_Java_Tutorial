# 第四週作業：火星探測車任務日誌

完成課堂 MissionLog.java，保存每一步指令、結果與能源，回放並產生摘要。截止時間與繳交平台依教師公告；沿用學期 repo，不從空白檔重寫。

## 1. 五個必做 TODO

從 [starter/MissionLog.java](starter/MissionLog.java) 開始，保留行動規則、停止條件、方法簽章與顯示骨架。課堂操作順序為 **2 → 3 → 1 → 4 → 5**。

| TODO | 要求 |
|---|---|
| 1 countCommands | 用 int[4] 計數已讀的 0–3 指令；先檢查範圍，9 與負數不得越界 |
| 2 新增日誌 | 每步同時 add 指令、執行後能源、結果；拒絕、無效與返回也保存 |
| 3 printReplay | 用同一索引讀三條清單，印出 step N: command=…, result=…, energy=… |
| 4 printSummary | 用 frequency 計算 OK 與 RETURN，印 steps、ok、rejected、ignored |
| 5 最低能源 | 空 energyLog 印 lowest energy=N/A；非空才呼叫 min |

steps 為讀過指令數；ok 為成功行動數；rejected 不含返回；ignored 為未執行指令數。counts 計指令出現次數，合法但被拒絕的指令也計入，故不等於 actionCount。最低能源不含初始能源。

## 2. 六條必做路線

每列修改 main 的 script，重新編譯執行；初始值保持 20/0。記錄到講義 E，不另做第二份測試報告。

| script | 最終 energy/samples/actionCount | 結果 | counts[0..3] | steps/ok/rejected/ignored | 最低能源 |
|---|---|---|---|---|---|
| {0} | 20/0/0 | RETURNED | [1,0,0,0] | 1/0/0/0 | 20 |
| {1,1,2} | 6/5/3 | SUCCESS | [0,2,1,0] | 3/3/0/0 | 6 |
| {2,2,2,2,2} | 10/5/5 | SUCCESS | [0,0,5,0] | 5/5/0/0 | 10 |
| {3,9,0} | 20/0/0 | RETURNED | [1,0,0,1] | 3/0/2/0 | 20 |
| {1,1,1,1} | 2/6/3 | SUCCESS | [0,3,0,0] | 3/3/0/1 | 2 |
| {2,1} | 12/3/2 | UNFINISHED | [0,1,1,0] | 2/2/0/0 | 12 |

逐步回放也要核對，不只核對最後狀態。最後恢復 {1,1,2}。另完成講義 F 的五筆教師邊界示範觀察（可依教師安排課後補看），不要求另寫教師入口。

## 3. Prompt 與 AI 驗證

- F2：寫一份可直接提問的需求，包含欄位/型態、筆數、順序、重複值、操作、允許技術，並判讀兩個模擬建議。
- F3：從移除索引範圍檢查、回放條件改成 <= 兩種模擬錯誤選一種；在副本重現後做最小修正。
- 保留一筆反例的預期、修改前、修正後輸出；修正後再跑 {1,1,2} 與 {3,9,0}。引用講義表格即可。
- 可以使用教師模擬建議，不要求 AI 帳號；若使用真實服務，依實際對話記錄，不把模擬示例當真實紀錄。

## 4. 繳交

```text
115_1_Java/
└─ YYYY_MM_DD/
   ├─ LAB_REPORT.md
   ├─ AI_USAGE.md
   └─ java-04/
      ├─ README.md
      └─ src/
         ├─ MissionLog.java
         ├─ ArrayBasics.java
         ├─ CommandCounter.java
         ├─ ArrayReference.java
         ├─ ListBasics.java
         └─ CollectionsDemo.java
```

五個示範保留課堂版本即可；ScriptArgs 為選做。LAB_REPORT 由學生講義另存，完成 A–H；AI_USAGE 使用四欄範本；README 記錄功能、實際 JDK、執行方式與預設輸出。不要把 teacher 同名類別、教科書、憑證、out 或 class 放進學生 repo。

在 java-04 執行：

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out MissionLog
```

commit、push，核對 GitHub 最新版本，再交 repo 網址、作業目錄及最終 commit。新位置 clone 後重新編譯並跑預設路線，將本人結果記入 H；時間不足課後完成，未做不得標示通過。

## 5. 驗收

- 五個 TODO 完成，六條路線的回放、計數、摘要及最終狀態符合規則。
- 能解釋 length/size、合法索引、三欄對齊與「日誌筆數≠成功行動次數」。
- 有 Prompt、反例、修正後重測與精簡 AI 紀錄，教師觀察與本人測試分開。
- GitHub 來源與紀錄完整，教師依 README 可編譯執行。

沿用課綱評量比例；延伸不列為必交缺項。不要求自訂領域類別、Stream、Lambda、JUnit、GUI 或例外處理。
