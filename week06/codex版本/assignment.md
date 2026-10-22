# 第六週作業｜責任、類別圖與行為保持

截止與繳交平台由教師公告。本週延續Codex第五週，不把Claude版的類別API與輸入方式混用。

## 必做

1. 完成Mission.java的TODO 1–3：run、countOk、lowestEnergy。其他四個starter檔已完成，不重寫行動規則、main輸入或報表。
2. 依講義完成B的兩個方法練習圖、C共享電池追蹤、D四類別責任表與部分成員關係圖。D圖只要求Mission三欄、run/countOk/lowestEnergy、兩條關聯及三條相依；不要求抄滿所有方法。
3. 先測D3核心兩路線，再完成下表其他四路線。每條獨立執行，從20/0開始；改main固定script後重編譯，最後恢復`{1,1,2}`。
4. 完成E1人工初版（看AI圖前commit）、E2至少三項問題（每項有需求及呼叫情境）、E3錯誤副本／修正／重測、E4設計Prompt。沒有真實AI帳號可用教師模擬圖，不影響完成作業。
5. 三份puml與SVG同步，立即預覽，DESIGN.md引用自己的圖。F檢核與G保存據實記錄。圖的語法正確、可顯示、設計符合需求、程式實際行為正確是不同項目。

## 輸入與狀態契約

每個新Mission搭配一個新Rover，只run一次；script非null，energy 0..20、samples非負。學生使用20/0，教師另示範邊界。只在合法整數契約內操作，不新增Scanner、命令列解析、例外處理、繼承、泛型設計或JUnit。

探索耗6得2、掃描耗2得1、充電+5封頂20，成功行動才增加actionCount。返回、無效及被拒絕的指令都保留；已停止後不讀、不記。優先SUCCESS、EXHAUSTED、RETURNED、UNFINISHED。MissionStep仍保存行動後數值快照，建構順序`command, energy, result`，無setter。Rover停止後直接execute回STOPPED；正常Mission日誌不應出現STOPPED。

## 本人測試與驗收數字

六路線完整輸出放LAB_REPORT，包含回放、counts、摘要、最低能源、最終狀態及battery。不同路線的輸出可用折疊區塊保存，不只截最後一行。

| script | 最終energy/samples/actionCount | outcome | steps/ok/rejected/ignored | counts[0..3] | lowest | battery |
|---|---|---|---|---|---|---|---|
| {0} | 20/0/0 | RETURNED | 1/0/0/0 | [1,0,0,0] | 20 | 100.0 |
| {1,1,2} | 6/5/3 | SUCCESS | 3/3/0/0 | [0,2,1,0] | 6 | 30.0 |
| {2,2,2,2,2} | 10/5/5 | SUCCESS | 5/5/0/0 | [0,0,5,0] | 10 | 50.0 |
| {3,9,0} | 20/0/0 | RETURNED | 3/0/2/0 | [1,0,0,1] | 20 | 100.0 |
| {1,1,1,1} | 2/6/3 | SUCCESS | 3/3/0/1 | [0,3,0,0] | 2 | 10.0 |
| {2,1} | 12/3/2 | UNFINISHED | 2/2/0/0 | [0,1,1,0] | 12 | 60.0 |

RoverDemo須得到`OK/14/2/1`、`STOPPED/0`、`STOPPED/20`三行。RelationDemo與SampleCaseDemo完整輸出見[examples/README](examples/README.md)。E3錯誤版第二行應為`overflow=true, count=4`；修正後`overflow=false, count=2`，再填到3、卸空3、空盒卸空0。

教師另以FixedScriptMission示範初始零能源、初始已達標、2格4份加掃描的成功優先、空腳本；學生記觀察而非冒充本人操作。沒有紀錄時lowestEnergy方法回-1，但報表必須印`lowest energy=N/A`，不能把-1當實際能源。

## 繳交

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md
  AI_USAGE.md
  java-06/
    README.md
    DESIGN.md
    src/        # starter五檔、examples六檔，共11個Java檔
    diagrams/   # practice、task_template、samplecase_initial各自.puml及.svg
```

README用自己的話寫四個主要類別責任、main入口、實際JDK、編譯與執行、完成／未完成項目。DESIGN保留人工初版commit及修正版的理由，來源puml與SVG一起提交。LAB_REPORT保留A–G題號、先預測再實測、完整輸出與個人修正。AI_USAGE四欄填真實情況，刪除虛構範例。教師版與學生版同名類別不能混編；不提交.class、out、IDE暫存或jar。

保存包含檢查差異、commit、push、遠端核對及新clone重編譯。未完成步驟要明列，不抄作者或教師的驗證紀錄。

## 評分建議（當週100分）

| 項目 | 配分 | 重點 |
|---|---:|---|
| 行為保持與三組TODO | 35 | 六路線、快照、返回／拒絕、停止與最低值 |
| UML與責任 | 25 | 可見性、型態、指定成員、線與多重性有程式證據 |
| AI判讀與本人驗證 | 25 | 人工初版、三項有情境的問題、反例／恢復／重測 |
| 可重現與說明 | 15 | 圖碼同步、README、AI揭露與保存狀態 |

這是當週內配分，不改課綱總評。圖的排版美觀不取代設計理由；聚合／組合與final不列必考符號。

## 選做：社團器材借用的遷移題

先完成必做，再設計Member、Equipment、Loan與Club的責任：每筆借用連到一位社員與一件器材，記借出／歸還日期；同一器材不能同時借給兩人，社員同時最多兩件未歸還，歷史永久保留。只畫責任圖並用「借、還、再借」說明多重性，不要求Java實作或另一份AI紀錄。參考方向與邊界在教師筆記；此題不取代必做，也不是全班的下週先備能力。
