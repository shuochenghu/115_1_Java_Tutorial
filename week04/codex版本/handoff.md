# 第三、四、五週銜接與資料契約

## 第三週已教，第四週仍要診斷

| 能力 | 目前依據 | 第四週用途 |
|---|---|---|
| if、傳統 switch、for/while、break 的範圍 | 第三週 student_lab B–C | 重用行動規則與停止條件 |
| energy/samples/actionCount 跨輪累計 | 第三週 RoverMission | 保留具名變數，集中增加容器 |
| 方法、參數、回傳值、輸出 | 第一週 GreetingApp、第二週儀表板 | 使用提供的方法骨架 |
| 參考、equals、整數除法 | 第二週 lab B–D | 陣列別名、結果字串比較與百分比 |
| 預測、執行、反例、最小修正 | 第二、三週 AI 練習 | 索引範圍與回放次數驗證 |

上述是教材覆蓋，不保證每位學生已達標。main 的 String[] args 骨架不代表已教過陣列；C# List<T> 也不視為人人已熟練。

## 第四週規則

容量 20；探索耗 6 得 2，掃描耗 2 得 1，充電 +5 封頂 20，目標至少 5 份樣本。指令 0 返回；其他整數為無效指令。成功行動才增加 actionCount。

- script 非 null，初始 energy 在 0..20，samples 非負；學生從 20/0 開始。
- 每筆已讀指令都記錄，包括拒絕、返回及無效指令。已停止後的指令不讀、不記錄。
- commandLog[i]、energyLog[i]、resultLog[i] 同屬第 i+1 步，三條長度相等。
- energyLog 保存每一步結束後能源；不包含初始能源。
- counts[k] 計「已讀指令 k」的筆數，包含被拒絕的合法指令與返回；無效指令不進 counts。
- steps 是日誌筆數；ok 是 OK 筆數；rejected 是既非 OK 也非 RETURN 的筆數；ignored 是腳本長度減 steps。
- 最低能源只統計 energyLog；空清單輸出 lowest energy=N/A。
- 執行前及每輪檢查能源與樣本；初始已耗盡或已達標時日誌為空。
- 結果判斷優先順序：SUCCESS、EXHAUSTED、RETURNED、UNFINISHED。腳本用完但未停止是 UNFINISHED，不自動當成返回。

## 範圍與支援

教師提供行動規則、迴圈與方法骨架，學生完成五個容器 TODO。Arrays.toString/equals 解釋為陣列工具；ArrayList 的 Integer 與裝箱/拆箱只教使用所需深度。static 方法沿用第一週；不要求自行設計業務類別。

JDK 不鎖定；第四週不用 --release 21。前三週既有驗證紀錄保留當時使用的版本，不回寫歷史結果。

## 第五週入口

學生應能解釋「三條清單必須同時 add」及「不能單獨排序其中一條」。第五週可改成 ArrayList<MissionStep>，由建構子一次建立同一步資料，用封裝維護一致性；Rover 物件設計由第五週重新介紹。

本文件只提出第五週銜接需求，未製作第五週教材，也不提前要求學生完成該類別。
