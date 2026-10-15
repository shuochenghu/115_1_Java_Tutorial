# 教材維護工具（非學生作業）

## 功能驗證

本週教材目錄執行 `python -X utf8 tools/verify.py`。需要現有Python與JDK，不安裝套件。使用本週目錄內新的暫存子目錄，分別編譯examples、starter、teacher及模擬學生src；檢查四個範例完整輸出、starter安全返回、8項物件檢查、15項完整輸出回歸、兩個預期編譯錯誤、四項錯誤變異及固定狀態入口。結束自動清除本次暫存產物。

錯誤變異只修改暫存副本；檢查器必須能拒絕static共享資料、==內容比較、漏存建構參數及只記成功步驟，不能只證明正確版會跑。

不用Python時，依README分開編譯教師Java檔，再執行兩個Check即可；Python工具、輸出擷取、AssertionError及變異測試不是學生先備要求。

## 投影片與版面

沿用現有Marp工具與前週版面檢查，不另安裝套件。修改Markdown後重新匯出HTML；本機有Marp CLI 4.5.1時，可執行：

```powershell
npx --offline @marp-team/marp-cli@4.5.1 week05_slides_codex.md -o week05_slides_codex.html
```

版面檢查使用[第四週工具](../../../week04/codex版本/tools/check_slides.cjs)，用Marp的bare模板匯出暫存HTML，再傳入現有puppeteer-core、Chrome、bare HTML與視覺化輸出目錄。使用方式見[前週工具說明](../../../week04/codex版本/tools/README.md)。不要把PNG或暫存輸出加入學生src。

靜態、CLI、視覺、IDE GUI、遠端GitHub及學生試教分開記錄，見[本週verification](../verification.md)。
