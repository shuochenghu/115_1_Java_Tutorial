# 第四、五、六週銜接與資料契約

## 從第四週承接

依據 [第四週 handoff](../../week04/codex版本/handoff.md) 與教師 MissionLog。本週保留所有可觀察任務規則、輸出格式、counts、摘要與最低能源定義，只把三條平行日誌換成一條物件日誌。

| 第四週 | 第五週 | 保持的意思 |
|---|---|---|
| commandLog[i] | history.get(i).getCommand() | 第 i+1 筆已讀指令，可為無效整數 |
| energyLog[i] | history.get(i).getEnergy() | 同一步執行後能源，不含初始狀態 |
| resultLog[i] | history.get(i).getResult() | 同一步結果代碼 |
| frequency(resultLog,"OK") | step.isSuccessful() 搭配迴圈 | OK 的筆數 |
| min(energyLog) | getter 搭配最小值迴圈 | 空日誌仍為 N/A |

## 精確契約

- script 非 null；初始 energy 0..20、samples 非負；學生預設 20/0；每次 runMission 的歷史與計數各自建立。
- 容量20；探索耗6得2，掃描耗2得1，充電加5封頂20，至少5份樣本成功。拒絕不改狀態；只有成功行動增加 actionCount。
- 每讀一筆指令，在執行後 `new MissionStep(command, energy, result)` 再 add。返回、拒絕、無效也記錄；停止後的指令不讀、不記錄。
- MissionStep 三個 private 實例欄位為 int command、int energy、String result；單一 public 三參數建構子，三個 public getter、public boolean isSuccessful()；不提供 setter，不輸出文字，不包含 static 的資料欄位。
- result 為 OK、NOT_ENOUGH_ENERGY、ALREADY_FULL、RETURN、UNKNOWN_COMMAND 其中之一。建構子只存值，合法性及欄位語意一致由呼叫端維護；不宣稱自動檢查。
- counts[k] 統計已讀合法指令 k，包含拒絕與返回；無效整數不進 counts。
- steps=history.size()；ok=OK筆數；rejected=steps-ok-RETURN筆數；ignored=script.length-steps。
- 最低能源只看已記錄的行動後能源；沒有記錄為 N/A。任務開始與每輪前檢查停止；終態依 SUCCESS、EXHAUSTED、RETURNED、UNFINISHED 優先判斷。
- 類別只保存所需值；私有欄位與不提供 setter 限制外部修改，不能保證使用者不傳錯數值。清單可變，記錄對外只讀；這兩件事分開解釋。

## 第五週新增與支援

學生必做為建構、this、getter、物件方法與物件清單；以 RoverBattery 示範受控修改。主作業仍保留已熟悉的具名狀態，不同時搬成 Rover 類別。countCommands、最低值迴圈、終止判斷及教師測試入口由教師提供。

使用者本次補充：單一程式以 VS Code 教學；專案超過兩個程式檔時加入 IntelliJ IDEA。本週兩檔基本範例先在 VS Code，SnapshotDemo 的三檔合作再介紹 IDEA；不要求每題雙 IDE 重做。共用技能已同步此規範。

目前只需要理解 static main／工具方法與實例成員的差異，不教 static 計數器、繼承、多型、equals/toString 覆寫、record、自訂泛型、Comparator、Lambda、Stream 或 JUnit。沒有 setter 是本次「已發生歷史」的需求選擇，不是所有類別都禁止 setter。

## 交給第六週的問題

下週課綱為 UML 類別圖與物件導向設計思維。學生應能指出每個類別的責任、欄位、建構子、方法，以及 MissionLog 使用多筆 MissionStep 的關係；可比較目前狀態與歷史資料應各由誰維護，再討論是否引入 Rover。

本週只做「類別責任表」及簡單參考示意，不要求正式 UML 關係、生命週期／組合判定或完整系統設計。不以 ArrayList<MissionStep> 就直接宣稱已成立 UML 組合關係。

## 本次交接狀態

建立 week05/codex版本，原有第四週與 Claude 版保留。實際驗證範圍見 [verification.md](verification.md)。目前工作分支 main，未建立提交、未執行 fetch，不能宣稱已與遠端同步。根目錄沒有 PROJECT_MEMORY.md，本週決策保存在本檔與驗證紀錄；後續作者仍須查閱最新工作區狀態。
