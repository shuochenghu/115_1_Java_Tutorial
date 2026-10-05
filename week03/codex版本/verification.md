# 第三週作者驗證紀錄

日期：2026-09-29。教學基準 Java 21；當前 `java -version` 為 OpenJDK 22（build 22+36-2370），`javac -version` 為 javac 22。編譯使用 `--release 21 -encoding UTF-8`，不能將目標版本當作 JDK 21 執行證據。

## 作者檢查

- 已核對課綱、第一週形式、跨週設計；本週不依賴陣列處理、集合、自訂 Rover 物件、JUnit 或新版 switch。main 的 String[] args 只是沿用骨架。
- 分開保留 starter TODO 與 teacher 完整碼；Scanner、顯示、while 與返回骨架是明示的教師支援。
- 已核對行動成本、成功優先、滿電及拒絕不計次、兩條成功路線及 AI 反例預期；這些靜態核對不等同程式執行。
- 作者嘗試編譯至暫存及本週輸出目錄時，遇沙箱寫入拒絕或 `Fatal Error: Cannot close compiler resources`，因此移交統籌；統籌在獲准環境、獨立暫存目錄完成以下實測。

## 執行清單（統籌實測）

| 類別／輸入 | 預期 |
|---|---|
| EnergyStatus | -1 INVALID、0 LOW、5 LOW、6 READY、20 READY、21 INVALID |
| SingleAction 原版 | energy=0、samples=2 |
| SingleAction 改為 5/1、6/2、6/9 | 5/0、4/1、6/0 |
| ChargeForecast | 起點4：9、14、19；起點18：20、20、20 |
| LoopComparison | while=0、do-while=1 |
| starter RoverMission 輸入0 | 20/0/0 RETURNED；其他行動仍是TODO |
| teacher RoverMission：0 | 20/0/0 RETURNED |
| teacher RoverMission：1 1 2 | 6/5/3 SUCCESS |
| teacher RoverMission：2 2 2 2 2 | 10/5/5 SUCCESS |
| teacher RoverMission：3 9 0 | 20/0/0 RETURNED |
| FixedStateMission：5 0 1 3 0 | 10/0/1 RETURNED |
| FixedStateMission：6 0 1 | 0/2/1 EXHAUSTED |
| FixedStateMission：2 4 2 | 0/5/1 SUCCESS |
| FixedStateMission：1 0 2 0 | 1/0/0 RETURNED |
| FixedStateMission：19 0 3 0 | 20/0/1 RETURNED |

狀態順序為 energy/samples/actionCount；固定入口前兩個整數是 energy、samples，其餘為任務指令，每列重新開始。

AI 變異測試使用暫存副本，不修改正式解答：`>6` 固定6/0輸入1、0應錯誤返回6/0/0；漏第一個break預設輸入1、0應返回12/3/2；每輪先重設samples預設輸入1、1、2、0應返回6/0/3。原版對應案例須重跑證明修正。

實測紀錄：examples、starter、teacher 分開以 `--release 21` 編譯成功。四個示範輸出已核對表列預期；starter 返回、四組預設任務及五組固定狀態共十個執行情境符合預期。三種 AI 錯誤各以副本重現，皆得到上列錯誤結果；正確版的界線、成功與返回路線符合規則。另已補測 SingleAction 的 5/1、6/2、6/9 三組修改，輸出依序為 5/0、4/1、6/0；補回 break 的正確任務輸入 1、0，得到 14/2/1 RETURNED，與錯誤副本的 12/3/2 有明確差異。表列所有執行案例均已完成。

## 投影片驗證

使用 Marp CLI 4.5.1／Marp Core 4.4.0 匯出 32 頁 HTML，以無頭 Chrome 在 1280×720 逐頁截圖與內容邊界檢查，首輪未偵測溢出；已檢視全頁聯絡表與程式碼代表頁。審查修訂後同步重匯出及複查。

## 限制

非整數、EOF 與例外處理不在本週輸入契約。JDK 21 實機、VS Code／IntelliJ GUI、真實 GitHub Desktop 認證／push／pull／全新遠端 clone 及學生課堂試做未執行。暫存編譯不等同 clean-clone 驗證。程式執行與投影片 QA 已由統籌完成相應紀錄，不代表教室端操作已經實測。
