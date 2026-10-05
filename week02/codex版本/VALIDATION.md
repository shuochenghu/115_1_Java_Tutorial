# 第二週驗證紀錄

日期：2026-09-29。作者完成教材後，由統籌接手編譯、執行與投影片驗證。

## 環境與方法

- 實測為 OpenJDK 22（build 22+36-2370）、javac 22。
- 全部 Java 來源以 `javac --release 21 -encoding UTF-8` 編譯，並在 JDK 22 執行；這不是 JDK 21 實機驗證。
- examples、starter、teacher 分開編譯至工作區以外的驗證暫存目錄；修改測試資料與 AI 錯誤均在副本進行。
- 沙箱內 javac 曾因資源寫入限制失敗；改在獲准的執行環境完成。測試程式逐筆比對輸出，不只檢查程序結束碼。
- 自動測試以 UTF-8 接收輸出，故 Java 子程序使用 `-Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8`。這是測試工具的編碼配對；一般 IDE／終端顯示仍需依教室環境確認。

## 執行結果

| 項目 | 實際結果 |
|---|---|
| 三組來源編譯 | examples、starter、teacher 均通過 |
| TypeReferenceDemo | 八種型態輸出、energy=6／backupEnergy=7、晨星號／赤砂號、false／true 均符合預期 |
| OperatorDemo | 除法與轉型為 0.0、0.0、35.0、35.0；轉 int 得 7；字串串接為合計=23／合計=5；樣本=2 |
| short-circuit evaluation 與配料 | short-circuit evaluation 觀察依序 false、true、右側檢查已執行、true；配料 6 份、餘 5 |
| starter | 可編譯執行，百分比 0.0、布林 false 為刻意保留的未完成佔位 |
| energy=0、5、6、7、20，locked=false | 百分比 0、25、30、35、100；次數 0、0、1、1、3；餘量 0、5、0、1、2；資格 false、false、true、true、true |
| energy=6、locked=true | 30.0%、1 次、餘 0、資格 false |
| 課末練習 energy=13 | 65.0%、2 次、餘 1、資格 true |
| 獨立範圍題 -1、0、20、21 | false、true、true、false |
| AI 錯誤副本 | energy=7 時 `energy / 20 * 100.0` 得 0.0；正確教師版同資料得 35.0 |

## 投影片

Marp CLI 4.5.1／Marp Core 4.4.0 匯出 HTML，共 30 頁。以無頭 Chrome 在 1280×720 逐頁截圖及檢查內容元素邊界，首輪未偵測到溢出，另檢視全頁聯絡表與代表頁。

依使用者要求，第 20 頁表頭改為前頁變數 `energy`、`locked`、`valid`、`canExplore`，已同步匯出 HTML；修訂後重新檢查全部 30 頁，未偵測溢出，第 20 頁另以截圖核對表頭及數值。

## 未涵蓋

未實際使用 JDK 21 執行，未走完 VS Code／IntelliJ GUI、真實 GitHub Desktop 認證／push／pull／全新遠端 clone、AI 服務或學生試教。來源副本編譯不等於 Git clean-clone 驗證。學生講義的相關證據欄位保留待填，不能用本紀錄代填。
