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

2026-10-16修訂：C1增加C#與Java類別欄位的存取對照；F1／F2改以獨立SampleCase需求做設計卡與AI程式審查，實作只列選做。F3第三種反例使用SnapshotDemo副本，把同一RoverBattery加入清單兩次，觀察19/19後恢復14、19的數值快照。主作業仍是原有兩類別與六組TODO，沒有增加完整Rover或final欄位要求。

使用者本次補充：單一程式以 VS Code 教學；專案超過兩個程式檔時加入 IntelliJ IDEA。本週兩檔基本範例先在 VS Code，SnapshotDemo 的三檔合作再介紹 IDEA；不要求每題雙 IDE 重做。共用技能已同步此規範。

目前只需要理解 static main／工具方法與實例成員的差異，不教 static 計數器、繼承、多型、equals/toString 覆寫、record、自訂泛型、Comparator、Lambda、Stream 或 JUnit。沒有 setter 是本次「已發生歷史」的需求選擇，不是所有類別都禁止 setter。

## 交給第六週的問題

下週課綱為 UML 類別圖與物件導向設計思維。學生應能指出每個類別的責任、欄位、建構子、方法，以及 MissionLog 使用多筆 MissionStep 的關係；可比較目前狀態與歷史資料應各由誰維護，再討論是否引入 Rover。

本週只做「類別責任表」及簡單參考示意，不要求正式 UML 關係、生命週期／組合判定或完整系統設計。不以 ArrayList<MissionStep> 就直接宣稱已成立 UML 組合關係。

第六週可取F1的SampleCase設計卡作為新情境類別圖，或把主任務狀態重構為Rover；需重新界定責任、方法與停止規則，不把本週的選做程式算成全體學生已掌握的先備能力。

## 初版交接狀態（2026-10-15）

建立 week05/codex版本，原有第四週與 Claude 版保留。實際驗證範圍見 [verification.md](verification.md)。目前工作分支 main，未建立提交、未執行 fetch，不能宣稱已與遠端同步。根目錄沒有 PROJECT_MEMORY.md，本週決策保存在本檔與驗證紀錄；後續作者仍須查閱最新工作區狀態。

## 修訂交接狀態（2026-10-16）

本次在main完成C1與F段修訂，僅改動week05/codex版本。講義、教師筆記、作業、README、AI範本、交接、驗證紀錄、維護工具及45頁投影片已同步；新增SampleCase／SampleCaseCheck兩個教師參考檔。六組TODO與八檔學生src保持原範圍，完整Rover仍交由第六週規畫。

本次重跑8項物件檢查、15項任務回歸及12項SampleCase檢查，並實測同套件存取、錯存可變參考與錯誤變異；45頁版面檢查通過，細節與限制見verification。修改尚未提交，未fetch，不宣稱與遠端同步。下一步可備第六週責任表／UML，或安排本週學生試教，再依實際完成時間調整。

## 說明補充與目前頁碼（2026-10-30）

目前Codex投影片為47頁；第29頁補new／add／get與物件清單，第35頁補教師檢查工具操作。主作業的六個TODO、兩個類別、八個學生來源檔、任務輸入輸出與第六週先備契約維持；新增頁不是新增學生活動。學生先掌握單步isSuccessful與任務outcome不同、電池物件與任務停止責任不同，再進入第六週的責任分工。檔名列維持只列.java，教師筆記的頁碼對照已更新；歷史45頁紀錄保留原日期。CLI與版面驗證範圍見verification，本次尚未提交，未宣稱已與遠端同步。
