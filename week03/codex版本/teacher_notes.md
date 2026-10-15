# 第三週教師筆記與解答

## 定位與授課安排

承接第一週方法及第二週型態／運算子。以 C# WinForms 按鈕觸發行動的經驗理解 command 分派；Java 主控台由 main 啟動，程式自行寫迴圈讀下一次輸入。不要宣稱 WinForms 不使用迴圈，事件背後有框架訊息迴圈。

課綱第三週是選擇／重複結構與 AI 比較除錯；150 分鐘是備課安排，不是課綱指定。本週不限定特定 JDK 版本，使用已安裝的 JDK 並記錄 java／javac；編譯指令不設定固定目標版本。教材使用傳統冒號 case，不混入 switch expression、箭頭或模式比對。

| 時間 | 教學活動與收斂點 |
|---|---|
| 0–10 | A：取回、型態診斷、起始碼輸入 0 |
| 10–30 | B1：分類與 -1、5、6、21 邊界 |
| 30–50 | B2：switch 示範 10 分、學生修改 10 分 |
| 50–65 | C1：for 三輪及有效範圍 |
| 65–75 | C2：Scanner 最小骨架 |
| 75–100 | C3：while／do-while、停止條件、紙筆追蹤 |
| 100–125 | D：補三個 TODO，AI 錯誤三選一 |
| 125–135 | E：課末口頭檢核、第四週動機 |
| 135–150 | F：保存、push、全新 clone 核對 |

第三週概念較密；教師投影已備好的骨架，不要求學生抄寫整份程式。若落後，縮減策略挑戰與 live AI 對話；仍保留邊界案例、停止原因及保存。課前確認教室工具，真實 GitHub 認證與學生試做尚待驗證。

## A 答案與先備補強

`7 / 20 * 100.0` 為 0.0；`7 / 20.0 * 100` 為 35.0。探索條件為 true；calculateBatteryPercent(6) 回傳 30.0，showDashboard 負責輸出。若多數無法解釋，先用第二週方法做 3 分鐘追蹤，提供回傳式，勿把骨架理解與公式推導疊加。

本週未假設會 Scanner；第二週 locked 不帶進互動選單。基本型態與參考示意不再重教，但強調 input 是使用現成物件，不表示已完成類別設計教學。

## B 完整解答

EnergyStatus 的順序為先 `energy < 0 || energy > 20`，再 `<6`，最後 else。-1、0、5、6、20、21 依序 INVALID、LOW、LOW、READY、READY、INVALID。先判 `<6` 會把 -1 當 LOW。

SingleAction 四列依序：6／1 → energy=0、samples=2；5／1 → 5、0、NOT_ENOUGH_ENERGY；6／2 → 4、1；6／9 → 6、0、UNKNOWN_COMMAND。完整程式在 examples；行動成本及獎勵必須放同一個成功分支，拒絕時兩者都不改。

移除 case 1 的 break，以 energy=20、command=1 測試，先探索再掃描，最後 energy=12、samples=3；一次指令做兩種行動。Java 傳統冒號 case 可向下接續執行，不要直接搬用 C# 對非空 case 接續的限制。復原 break 後為 14、2。請學生先預測，不要一次提供兩個錯誤。

## C 完整解答

for 的 4 起點：9、14、19；18 起點：20、20、20。`round < 3` 只跑 round=1、2，共兩次。energy 在迴圈外才能保留每輪結果；round 宣告在 for 初始化，只在此迴圈範圍可用，離開後引用會編譯失敗。這個三輪預測會顯示三輪，滿電仍顯示 20；與互動任務「滿電充電不計有效行動」並無矛盾。

Scanner 的 import、建立工具、nextInt 應逐行示範；只接受 Java int 範圍內整數輸入直到結束。9 印 UNKNOWN_COMMAND；0 令 running=false。執行 break 後抵達 switch 下方 showDashboard，故仍印最後狀態，再回到 while 條件離開。close 在完成後關閉輸入；不將此獨立程式模式直接推論為所有共用輸入環境都應立即關閉。

LoopComparison 為 while=0、do-while=1。do-while 的尾端有分號；若主線初始能源已為 0，用 while 可直接不執行任務。沒有教 do-while 就要求重寫互動任務，會增加不必要負荷；本週僅小例比較。

| 指令 | energy 前→後 | samples 前→後 | actionCount 前→後 | 下一輪 |
|---|---|---|---|---|
| 1 | 20→14 | 0→2 | 0→1 | true |
| 1 | 14→8 | 2→4 | 1→2 | true |
| 2 | 8→6 | 4→5 | 2→3 | false |

三個停止條件為主動返回、能源耗盡、樣本達標。samples 若每輪重新指定值 0，不能累計至 5；若在外層已有區域變數 samples 而內層再寫 `int samples=0`，Java 會因重複宣告而編譯失敗，應區分這兩種錯誤。command 本輪讀取即可；計數狀態需跨輪存在。

## D 完整實作與驗收

完整答案見 [teacher/RoverMission.java](teacher/RoverMission.java)，三個 TODO 對應 case 1、2、3。其餘骨架與學生版相同。統一採教師引導 D1、學生模仿 D2、獨立完成 D3。每次成功扣款與獎勵一起更新，actionCount 只在成功分支加一。充電使用 if 封頂，避免提前教授 Math API 來掩蓋分支練習。

D 的 25 分鐘建議：TODO 12 分鐘、學生標準路線 5 分鐘、教師固定狀態帶測 3 分鐘、AI 反例與修正重測 5 分鐘。三筆教師邊界案例由教師操作，學生預測、觀察並填表，不要求全班逐筆修改入口。當堂至少完成一筆人工反例與修正重測；AI 的一般成功及返回／拒絕回歸測試若未及完成，課後補齊，繳交時仍需三筆證據。保留最後 15 分鐘保存，不要求當堂完成三種 AI 錯誤。

學生四列及教師三列的完整預期已列在講義；實際結果見 verification。輸出 SUCCESS 成功、EXHAUSTED 耗盡、RETURNED 主動返回、ALREADY_FULL 已滿電。先判成功才能讓 energy=0、samples=5 被判 SUCCESS。

教師固定狀態不用增加學生設定選單。可暫改 main 的引數，或編譯 teacher 的兩個檔案後使用：

```powershell
"5 0 1 3 0" | java -cp out-teacher FixedStateMission
"6 0 1" | java -cp out-teacher FixedStateMission
"2 4 2" | java -cp out-teacher FixedStateMission
```

前兩個整數為 energy、samples，後面才是指令；actionCount 從 0 開始。方法的 Scanner 參數僅為教師提供的測試入口，不要求學生自行設計依賴注入等模式。

### AI 三選一參考答案

| 錯誤 | 最小反例與錯誤結果 | 修正與重測 |
|---|---|---|
| `energy > 6` | 固定 6／0，輸入 1、0；拒絕探索，最後 6／0／0 RETURNED | 改 >=6；輸入 1 即 0／2／1 EXHAUSTED |
| case 1 漏 break | 預設，輸入 1、0；探索後再掃描，12／3／2 RETURNED | 補 break，14／2／1 RETURNED |
| while 開頭 `samples=0` | 預設，輸入 1、1、2、0；最後 6／0／3 RETURNED | 刪除每輪重設，前三步 6／5／3 SUCCESS |

第三項輸入最後 0 仍先重設樣本，因此最後為 0；三次行動後曾為 1。錯誤測試需提供 0，避免不能成功時持續等輸入。學生只選一項，可全程使用同一個教師模擬錯誤，保留三筆證據：反例的修改前／修正後比較、修正後一般成功、修正後返回／拒絕。講義已提供填寫表；後兩筆可用預設狀態的五次 2（10／5／5 SUCCESS）與 3、9、0（20／0／0 RETURNED）。未實際使用 AI 服務須據實標示；課後補測不等於當堂已通過。

## E、F 解答與驗收

1、1、2 共 3 次行動、剩餘 6 格；五次 2 共 5 次行動、剩餘 10 格，前者少行動、後者省能源。不必求數學最佳解。energy=2、samples=4 掃描同時達標耗盡，需求指定成功優先；只寫 break 不改 running，下一輪仍會讀指令。最後狀態無法重建所有路線，需保存逐筆歷程，作第四週陣列與集合動機。

必收：完成碼、邊界測試與追蹤表、AI_USAGE、GitHub 可存取版本與取回驗證。沿用課綱比例，不另加配分。不能只看程式能跑或學生能按 Run；抽問一次 switch break 走到哪裡、一次失敗行動為何不計次。F 中未做全新 clone 必須標記待驗證，不可將範例本地測試當成學生遠端成果。

## 官方語意依據

2026-09-29 核對 Java SE 21 官方文件：[JLS 第 14 章](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html)（§14.9 if、§14.11 switch、§14.12–14.14 迴圈、§14.15 break）及 [Scanner API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html)。本教材為原創教學情境；語法示範保留第三週需要的最小範圍。
