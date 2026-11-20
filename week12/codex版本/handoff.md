# 第 12 週交接｜獨立的例外處理與執行緒基礎

本週不依賴第 11 週泛型或探測車。下一位授課者只需本目錄即可開課；不將第 11 週完成度列為進入條件。未建立 PROJECT_MEMORY.md，長期契約保存在本檔。

## 已安排的能力

學生寫 reserve 的驗證／throw、迴圈內的 try/catch、追蹤下一筆及失敗不改欄位；以 AI 扣除時機反例做最小修正。執行緒使用具名 Runnable 與兩個不同工作，先 start 兩者再 join，辨認實際名稱與部分順序。課後抽成 boolean processOne，再累計成功／拒絕。

這是教材安排，不等於每位學生已熟練。下一週可用一筆格式拒絕與一筆合法資料診斷；中斷恢復支援碼由教師提供，不視為已會設計執行緒取消。

## 需要保留的契約

- 額度物件每個初始 10，單筆 1～5；先範圍、再餘額、最後扣除。拒絕不變、零額度仍處理後續申請。raw 非 null，trim 後 parseInt。
- NumberFormatException 先 catch，IllegalArgumentException 後 catch；逐筆顯示由入口負責。固定 3、abc、0、5、4、2，額度 7、7、7、2、2、0。
- AI 副本先扣才檢查，故第五筆 -2、最後 -4；修正須先驗證並同六筆重測，不改資料掩蓋。
- ThreadStartDemo 不持有 PrintQuota。各工作有限三步；輸出共 8 行，各內部 1→2→3，跨工作無固定交錯。join 只等終止，不保證工作業務成功，不自動傳遞 run 的例外。
- 中斷 catch 恢復等待者旗標並 return，不誤印結束；沒有取消工作。主線未教同步或並行安全共用資料。
- 作業固定批次摘要 3／3／0，空批次 0／0／10；不得重複 reserve 或把處理過計成成功。

## 第 13 週

課綱為 Lambda／Stream，現階段沒有正式第 13 週教材可核對。可重新提供 PrintTask 最小完成版，從具名 Runnable 對照 Lambda，學生不必先完成本週作業。Stream 另給固定資料，單執行緒起步；不把共享額度、平行 Stream 或工作排程列作已會能力。

實際驗證與尚未驗證項目見 [verification](verification.md)，操作入口見 [tools](tools/README.md)。未提交、fetch 或 push，不能宣稱與遠端同步。
