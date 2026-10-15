# 第3至5週教材：JDK要求檢視紀錄

日期：2026-10-15。依使用者指示，第3至5週教材不限定特定JDK版本。學生使用已安裝的JDK，記錄實際java／javac；課堂指令不指定固定--release、-source或-target，IDE不硬設某版SDK。這項教學安排不代表所有年代的JDK都已相容或完成測試。

## 範圍與修正

| 版本 | 檢視結果與處理 |
|---|---|
| [第三週Codex](week03/codex版本/README.md) | 移除README、講義、作業及投影片的--release21，取消教師筆記與投影片的JDK21教學基準，重新匯出HTML |
| [第四週Codex](week04/codex版本/README.md) | 原已不鎖版，統一README與學生講義措辭，保留程式及既有投影片 |
| [第四週Claude](week04/claude版本/README_claude.md) | 移除Java11／List.of的版本門檻說明；ListBasics與投影片改以Arrays.asList準備資料，教師筆記同步，重新匯出HTML |
| [第五週Codex](week05/codex版本/README.md) | 原已不鎖版，統一README與驗證說明；程式與投影片不變 |

目前工作區第三、五週只有Codex版，第四週有Codex與Claude版；以上涵蓋現有版本。API替換的固定大小與可增刪副本語意依[官方Arrays文件](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-)核對，網址中的文件版本不是學生使用要求。

跨週設計文件的舊JDK21預設已註明取消；共用skill改成不限定特定JDK版本，涵蓋編譯與IDE設定。舊verification中的JDK22、--release21／11與日期保留為歷史證據，追加本次結果，避免改寫過去實際發生的操作。

## 本次實際驗證

- 環境：OpenJDK22（22+36-2370）／javac22。12組examples／starter／teacher隔離編譯成功，皆未指定固定目標版本；額外學生src合併編譯由第五週工具覆蓋。
- 第三週：四個範例、starter返回、四條預設任務路線、五個固定狀態案例通過；任務案例核對最後儀表板與終態。
- 第四週：Codex的15項與Claude的12項教師檢查通過；Claude的ListBasics替換前後於同一JDK完整輸出一致。
- 第五週：完整重跑tools/verify.py，8項物件檢查、15項任務回歸、四個範例、starter、編譯錯誤反例、四項錯誤變異及固定狀態入口均通過。
- 受影響投影片：第三週32頁、Claude第四週35頁，Markdown／HTML頁數一致，全頁DOM邊界與程式區塊水平溢出檢查無問題；修改的4頁截圖另經目視檢查。

詳細範圍分別追加於各週verification；沒有將歷史結果當成本次重跑證據。

## 保留的限制與工作區狀態

其他JDK、作業系統、IDE GUI、真實GitHub遠端操作與學生試教尚未驗證。教學檢視聚焦JDK要求及相關指令／API，未重寫課程內容或變更既有任務規則。

分支main，修改與第五週新增教材尚未提交；本次未fetch，不宣稱與遠端同步。第一、二週教材不在本次修改範圍；根目錄第二至三週銜接設計僅同步現行JDK要求。
