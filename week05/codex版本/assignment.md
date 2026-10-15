# 第五週作業｜從三條清單到 MissionStep

## 目標與必做

將既有任務日誌轉成物件設計，不重寫任務規則。提交能解釋、能編譯執行的程式及本人測試紀錄；課堂未完成者課後補齊。截止與繳交平台由教師公告，不以教材範例日期代替。

1. 完成 starter 兩檔的 TODO 1–6：MissionStep 的三個 private 實例欄位、三參數建構子、三個 getter、isSuccessful；MissionLog 的每步新增、回放及 OK／RETURN 計數。
2. 使用 ArrayList<MissionStep>；每筆已讀指令新增一個物件，保存行動後能源。保留順序、重複、拒絕、無效及返回；停止後不讀後續腳本。
3. 保留提供的任務規則、counts、最低能源、摘要與預設輸出格式；不要新增選單或改用命令列輸入。資料契約見 [handoff.md](handoff.md)。
4. 完成講義 E1 物件檢查、E3 六條路線及 F2 一項模擬錯誤的重現／修正／重測；B3 編譯錯誤擇一，其餘 B–D 觀察表與 F1 設計表完成。
5. 用自己的話解釋：建構順序、this、private、static 與實例資料、共用參考與快照、歷史不需要 setter 的原因。

不要求自訂完整 Rover、正式 UML、繼承、record、泛型設計、Comparator、Stream、例外處理或 JUnit。教師 FixedScriptMission／兩個 Check 不是學生另寫作業。

## 驗收資料

初始狀態每次為 energy=20、samples=0。完整輸出要有每筆回放、counts、steps/ok/rejected/ignored、最低能源、最終狀態與 battery；不要只交最終三個數字。

| script | 最終 energy/samples/actionCount | outcome | steps/ok/rejected/ignored | counts[0..3] | lowest |
|---|---|---|---|---|---|
| {0} | 20/0/0 | RETURNED | 1/0/0/0 | [1,0,0,0] | 20 |
| {1,1,2} | 6/5/3 | SUCCESS | 3/3/0/0 | [0,2,1,0] | 6 |
| {2,2,2,2,2} | 10/5/5 | SUCCESS | 5/5/0/0 | [0,0,5,0] | 10 |
| {3,9,0} | 20/0/0 | RETURNED | 3/0/2/0 | [1,0,0,1] | 20 |
| {1,1,1,1} | 2/6/3 | SUCCESS | 3/3/0/1 | [0,3,0,0] | 2 |
| {2,1} | 12/3/2 | UNFINISHED | 2/2/0/0 | [0,1,1,0] | 12 |

E1 必須在兩個物件都建立後讀取，第一筆仍為 `1/14/OK/true`，第二筆為 `2/12/NOT_ENOUGH_ENERGY/false`。F2 恢復正確版本後再次執行 E1 與兩條指定路線；修改前後的紀錄不能只填「AI 說已修好」。

## 繳交

- 學期 repo 當日 `java-05/src/`：examples 六檔及完成的 MissionStep.java、MissionLog.java，共八個 Java 檔；不放 teacher 同名檔。
- java-05/README.md：類別責任、main 入口、版本、編譯執行方式、已完成及未完成項目。可由教師提供的週 README 理解後用自己的話撰寫。
- 日期目錄 LAB_REPORT.md：保留 A–H 題號、預測與本人操作、E1／E3 完整輸出、F2 證據、工具操作與保存狀態。教師示範未看到就寫待補。
- 日期目錄 AI_USAGE.md：用本週範本，記真實情況；未使用 AI 也須完成程式測試。虛構範例不能當成個人紀錄。
- commit／push 及遠端位置；另取回新副本重編譯。尚未操作的 GUI／遠端步驟據實標示，不編造成功。

## 評分建議（本作業100分）

| 項目 | 配分 | 觀察重點 |
|---|---:|---|
| 類別與封裝 | 30 | 實例資料、建構、this、正確查詢、合理方法責任 |
| 物件日誌整合 | 30 | 每步新物件、行動後快照、回放、計數與邊界 |
| 本人驗證與診斷 | 25 | E1、六路線、編譯錯誤與模擬錯誤的前後證據 |
| 說明與保存 | 15 | 類別責任、需求 Prompt、AI 揭露、Git 保存與可重現性 |

這是當週作業內配分，不變更課綱總評比例；延伸不取代必做。

## 選做

以 getter 寫「只顯示成功步驟」報表，但保留原 history；新增情境路線，比較剩餘能源與步數。或提出 Rover 類別的責任表，說明與 MissionStep 的區別；不必提前實作完整類別系統。
