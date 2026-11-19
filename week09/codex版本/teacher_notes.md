# 第 9 週教師筆記｜抽象類別與介面（Codex 版本）

## 節奏與引導

| 節次 | 建議做法 | 時間不足時 |
|---|---|---|
| 1（50 分鐘） | A 基準 10，B 抽象類別 40。先預測誰會讀到掃描，再操作。完成一般車覆寫後才抽象化父類別 | 編譯實驗只選一個，由教師示範另一個 |
| 2（50 分鐘） | C 報表 45，D 巢狀與匿名類別 5。先收一分鐘人工簽章草案，再顯示共同契約。教師示範多檔入口及來源根目錄 | 同類型延伸案例留課後。保留車、非車與空盒三個輸出 |
| 3（50 分鐘） | E Prompt 及反例 25，F 看圖 10，G 保存 15。先保留學生自己的報表版本，再用副本跑錯誤 | F 的漏寫 `public` 只口頭預測。保留修正重測和保存時間 |

建議第 2 節從 WinForms 的「顯示數量」按鈕切入。按鈕只需要一個數字，先請學生用口語描述它要向各來源取得什麼。可在 D 後或 F 前用 2–3 分鐘看 [StepListenerDemo.java](teacher/StepListenerDemo.java)，視進度替換部分 F 討論。教師用一個圖或兩行口述對照「報表主動問」與「回放程式逐步通知」。這裡回放已記錄的步驟，`Mission.run` 裡沒有註冊或發送通知。重型車由 14 格探索兩次，依序剩 8、2 格，低能源門檻 6 只觸發第二步，印出 `能源偏低，步驟=2 能源=2`。這和 WinForms 事件處理有相似的呼叫方向，具體觸發機制不同。這段為教師示範。

## A–B｜前週基準與抽象化

B 開始前用 2–3 分鐘執行範例一 [ParcelDemo.java](examples/ParcelDemo.java)，印 `書籍 3公斤 運費=90元`、`藥品 3公斤 運費=135元`。重點是運費流程 `fee()` 只寫在抽象類別 `Parcel`，每公斤費率 `ratePerKg()` 由 `NormalParcel`（10）與 `ExpressParcel`（25）提供。程式中被註解的 `new Parcel(...)` 在副本取消註解後，診斷為 `Parcel is abstract; cannot be instantiated`。這組範例和探測車結構相同但數字與名稱不同，學生可對照，不能照抄成 TODO 答案。

A1 的宣告型態為 `Rover`，實際物件是 `HeavyRover`。`Rover.execute(1)` 進入共用的私有 `explore()`，取得產量時依物件選擇 `HeavyRover.exploreYield()`。第一步耗 6 格並取得 3 份。A2 一般車讀到第 3 步，因兩次探索只累積 4 份。重型車第二次探索後已有 6 份，掃描尚未讀取就停止。

- TODO 1 先完成 [StandardRover.java](teacher/StandardRover.java) 的覆寫。只回 2，建構資料由父類別持有。
- TODO 2 見 [Rover.java](teacher/Rover.java)。`protected abstract int exploreYield();` 留簽名。抽象類別建構子仍會在 `new StandardRover(...)` 時透過 `super` 執行，讓私有狀態有初始值。
- 建立具體車輛的地方換成 `new StandardRover(...)`。欄位仍可宣告為 `Rover`，因為兩種具體車都能交給 `Mission`。

`CompareMissions` 起始與完成版的完整輸出相同。

```text
=== 一般探測車 ===
=== 任務回放 ===
步驟 1: 指令=1, 結果=成功, 能源=14
步驟 2: 指令=1, 結果=成功, 能源=8
步驟 3: 指令=2, 結果=成功, 能源=6
指令次數[0..3]=[0, 2, 1, 0]
步數=3, 成功=3, 拒絕=0, 略過=0
最低能源=6
最終能源=6, 樣本數=5, 行動次數=3 -> 任務成功
電量百分比=30.0
=== 重型探測車 ===
=== 任務回放 ===
步驟 1: 指令=1, 結果=成功, 能源=14
步驟 2: 指令=1, 結果=成功, 能源=8
指令次數[0..3]=[0, 2, 0, 0]
步數=2, 成功=2, 拒絕=0, 略過=1
最低能源=8
最終能源=8, 樣本數=6, 行動次數=2 -> 任務成功
電量百分比=40.0
```

`MissionLog` 印一般車段落中從 `=== 任務回放 ===` 起的九行。`HeavyRoverTry` 的 15 行應全顯示「相符」，該程式的 [各步期望值](teacher/HeavyRoverTry.java) 可在巡視時用來比對。若仍看到一般車結果正常，提醒學生另做編譯實驗，因為起始的預設產量也能讓報表正確。

| 實驗 | JDK 22 診斷重點 | 理由 |
|---|---|---|
| 在 `CompareMissions` 直接 `new Rover(...)` | `Rover is abstract; cannot be instantiated` | 抽象類別可宣告型態，但建立物件要選具體類別 |
| 移除一般車的產量方法 | `StandardRover is not abstract and does not override abstract method exploreYield() in Rover` | 具體子類別要補齊抽象方法 |
| `SampleTray.getSamples()` 漏寫 `public` | `attempting to assign weaker access privileges; was public` | 本例介面方法為公開，類別實作時維持公開 |

## C–D｜共用報表與兩種測試來源

C 開始前執行範例二 [ScaleDemo.java](examples/ScaleDemo.java)，印 `行李箱 重量=13公斤`、`貓 重量=5公斤`、`咪咪：喵～`、`同一隻貓用 Weighable 變數讀取：5公斤`。行李箱與貓沒有共同父類別，只因都 `implements Weighable` 而能交給同一個 `weigh`。兩個註解中的編譯實驗：`item.meow()` 的診斷為 `cannot find symbol`（宣告型態 `Weighable` 沒有這個方法）；把範例一的 `NormalParcel` 放上秤，診斷為 `NormalParcel cannot be converted to Weighable`，雖然它也有 `getWeight()`，但沒有宣告實作介面。第二個實驗正好對應 TODO 3b：`Rover` 早有 `getSamples()`，仍要寫 `implements SampleSource`。

先收學生的草案。合理的契約是無參數、回傳整數、讀取不改狀態。回到統一簽名 `int getSamples()` 後，TODO 3a 寫介面，3b 用 `Rover` 已有的公開方法，3c 由 `SampleTray` 讀自己的欄位。TODO 4 用 [SampleReport.java](teacher/SampleReport.java) 的 `SampleSource` 參數，三個呼叫見 [SampleReportDemo.java](teacher/SampleReportDemo.java)。

```text
重型探測車 樣本數=6
樣本盒 樣本數=4
空盒 樣本數=0
```

若樣本數寫死，請讓學生換成 9 份的盒子或再讀空盒，並說明這個輸入能抓什麼錯。若學生把樣本盒設成 `extends Rover`，問它是否真的有能源、移動與停止規則。報表接的是「會提供樣本數」的能力，任務接的是整套探測車行為。

D 的範例三 [FixtureDemo.java](examples/FixtureDemo.java) 沿用範例二的 `Weighable`，有兩個 `getWeight()`，各回 2。具名 `static` 巢狀類別 `FixedWeight` 宣告在 `FixtureDemo` 內，建立時不用先建立外層的 `FixtureDemo` 物件。匿名類別的宣告與建立寫在同一個運算式。兩個物件都能當作 `Weighable` 交給 `show`，所以印兩行 `測試資料重量=2`。課堂先看這一對，非靜態內部類別只補充它需要外層物件。

## E–F｜AI 審查及類別圖

四欄示例可在學生交人工草案後投影。「地面站的 `SampleReport.print` 要同時讀取探測車與樣本盒。共同操作為 `int getSamples()`，表示目前樣本數。資料留在來源，讀取無副作用，報表透過 `SampleSource` 取得數量。完成任務的重型車印 `重型探測車 樣本數=6`，有 4 份的盒子印 `樣本盒 樣本數=4`。」

模擬回覆的輸出格式與讀取寫法可用。參數縮為 `Rover` 使兩個樣本盒呼叫無法編譯，JDK 22 診斷含 `SampleTray cannot be converted to Rover`。暫去掉兩個盒子呼叫後，重型車印一行 `重型探測車 樣本數=6`。把呼叫復原並將報表參數改成 `SampleSource` 後，三行應和 C 一樣。這份固定回覆用於審查，讓所有學生重現同一個問題。

F 請學生指到具體位置。`Mission` 持有 `Rover` 欄位，`SampleReport.print` 接收 `SampleSource` 參數。`SampleTray` 與 `Rover` 都實作介面，圖中以虛線空心三角表示。`StandardRover`、`HeavyRover` 用實線空心三角指向 `Rover`。圖是課堂責任視角，省略與本題無關的成員。

## G 與作業批改

用已取回的提交跑主線，再跑作業的 `SampleBatchDemo`。教師參考檔在 [teacher/transfer](teacher/transfer)，示例印 `混合樣本 樣本數=7`、`空盒 樣本數=0`、`再次讀取 樣本數=7`。學生可選不同資料。檢查一個案例能否分辨正確值與任一批被漏算，再看零樣本或重讀，以及是否真的呼叫同一報表方法。確認 AI 紀錄的來源、預測、實測診斷和重測齊全。

教科書閱讀用專案 Deitel《Java How to Program, Early Objects》11e 的 §10.4 Abstract Classes and Methods、§10.9 Creating and Using Interfaces。§10.9 的不同類別透過同一介面共用處理方式，可對照本週報表。
