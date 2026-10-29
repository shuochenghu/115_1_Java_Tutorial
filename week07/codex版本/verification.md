# 第七週Codex教材驗證紀錄

日期：2026-10-28。執行者：Codex。這是作者驗證，不是學生本人操作或課堂試教。

## 實際環境與方法

- Windows，OpenJDK22（22+36-2370）、javac22、Python3.14.0、Node.js24.11.0。
- 一般非預覽Java，未指定--release、-source或-target，不鎖教室JDK。
- `python -X utf8 tools/verify.py`：在本週新建暫存目錄，分開編譯及執行；離開後自動清理。javac帶UTF-8及-Xlint:all，編譯輸出無警告。
- PlantUML MIT 1.2026.9，沿用前週已備jar，以本機UTF-8位元組管線及來源Smetana設定產圖。未為本週安裝Graphviz或新套件。
- Marp CLI4.5.1／Marp Core4.4.0，直接用既有marp-cli.js匯出正式HTML及QA用bare HTML。
- Chrome與既有puppeteer-core只用本次獨立、未登入的profile讀本機HTML。首次沙箱啟動未成功，改以允許環境的新專用profile完成；未使用個人瀏覽器資料。

## 程式結果

| 項目 | 實際結果與支持範圍 |
|---|---|
| examples、starter、teacher各自編譯 | 全部成功，無警告 |
| 初始學生src（範例3檔＋starter6檔） | 九檔合併編譯成功；MemberDemo完整輸出符合預先期望；MissionLog與教師版一致；HeavyRoverTry有DIFF，明確不是完成版 |
| 完成學生配置 | 僅以教師完成的Rover、HeavyRover替換同名starter，其他提供檔不變；HeavyRoverTry十五行全OK |
| 既有完整輸出 | MissionLogCheck 15/15；期望檔逐位元組沿用第六週，涵蓋六條作業路線、邊界、拒絕、返回、空腳本、初始停止與成功優先 |
| 物件及任務狀態 | MissionStateCheck 16/16 |
| 繼承與新行動 | InheritanceCheck 45項通過：正常、7格不足、8格剛好、初始／行動後停止、成功與耗盡同時成立、局部輔助方法、獨立物件、欄位可見性與無重複狀態等 |
| 教師的下週伏筆檢查 | Mission接收HeavyRover跑{1,1,2}仍為SUCCESS、3步；只作教師驗證，不當成本週已教多型 |
| 會員B3及buy邊界補測 | 全新Bob買85後領兩次為48點／2次；price為0、-5、9時新會員仍0點，符合規格 |
| CargoRover參考 | CargoRoverTry七行完整輸出符合期望；另以新測試入口驗證負數、一次超量、剛好3及滿載後拒絕，狀態正確 |
| C1缺super | 隔離副本編譯失敗，診斷指出Member建構子參數不符 |
| C2直接改private | 隔離副本編譯失敗，診斷包含private access |
| C3同套件protected | MemberDemo暫加bob.addPoints(5)可編譯，Bob最後33點；正式範例維持原樣 |
| AI錯誤與正確重測 | 錯誤版能編譯，重現下列9行DIFF；完成版十五行全OK |
| 檢查工具的偵測力 | 副本分別移除drill停止、漏actionCount、增加子類別同名energy、spendEnergy改public、能源不足仍扣除，五種變異均使InheritanceCheck非0結束 |

所有錯誤實驗都在隔離副本，正式教師答案沒有被留成錯誤版。輸出相同不代表已完成局部重構；學生explore、scan是否使用指定方法，仍由教師閱讀程式。

## AI錯誤版的實際DIFF

原文是教師設計的模擬回覆，不是真實AI服務對話。它已呼叫super，但重複宣告狀態，漏停止及成功行動計次。

```text
after drill: 20/0/0/1 (expect 12/3/1/1) DIFF
after explore: 14/2/1/1 (expect 6/5/2/1) DIFF
done drill: OK (expect STOPPED) DIFF
done unchanged: 14/2/1/2 (expect 6/5/2/1) DIFF
exact state: 8/0/0/1 (expect 0/3/1/1) DIFF
zero cannot charge: OK (expect STOPPED) DIFF
initial zero: NOT_ENOUGH_ENERGY (expect STOPPED) DIFF
initial success: OK (expect STOPPED) DIFF
success unchanged: 20/5/0/1 (expect 20/5/0/0) DIFF
```

刪除重複欄位、改用父類別提供方法、補停止及計次後，使用相同入口十五行全部OK。

## 類別圖、產圖指令與來源

- 六份puml分別產同名SVG與QA PNG，全部成功；六圖實際目視，未見裁切或重疊。
- Member改為橫向，增大投影片內可讀文字；重新產圖及重新檢視第8頁。
- 對照六個類別的指定成員名稱及可見性：Member、VipMember、Rover、HeavyRover、Mission、CargoRover。部分視角保留省略說明，不要求完整API抄錄；參數、回傳型態、關係及常數另人工核對。
- README的cmd UTF-8管線實際由Windows PowerShell5.1.26100.9711啟動；以本週heavy_practice.puml產出的SVG與正式圖逐位元組一致。這是本機CLI，不是教室安裝或GUI證據。
- Deitel11e EPUB實際目錄核對§9.2–9.7；核心採9.2–9.5、9.7，Object僅補充，覆寫留第八週。Java語意及Java25建構子限制查核官方文件，連結見teacher_notes。

## 投影片與文件

- Markdown與正式HTML均35頁，HTML所用圖片存在；以bare HTML渲染35張PNG，幾何檢查0項溢出、無圖片載入失敗。
- 已逐頁開啟檢視全部35頁，包含文字、表格、程式及圖；修訂後重新檢視第1、5、7、8、24頁。調整封面頁尾對比、main入口表、單一父類別表述、會員圖及流程骨架提示。不是教室投影可讀性或學生理解程度的證據。
- UTF-8、Markdown連結、code fence、文字行尾空白、Java檔名與public class、三組TODO及學生／教師／作業規格核對完成；最後靜態檢查通過。
- 練習量按目標安排：三組主線由跟做到獨立；一個課後遷移；一份AI追蹤；六條舊路線其中兩條課堂、四條課後。不把選做社團、第二份AI或完整API抄圖列必做。
- `git diff --check`通過，並另外逐檔檢查本週未追蹤的文字檔，不以一般diff代替新增檔檢查。

## 版本保留與交付狀態

製作前保存311個既有檔案SHA-256，完成後重新核對均未變動，包含前週、Claude版及原有共用技能修改。這次只新增week07/codex版本，最終49個交付檔（22個Java來源檔）。不交付class、pyc、暫存編譯目錄或QA截圖；本次曾產生的Python快取已清理。

截圖、圖PNG、完整執行紀錄及保存基準放本次視覺化QA目錄，未放入學生教材。README是教材入口，handoff保存跨週決策。未建立commit、未push、未fetch，不宣稱已與遠端同步。

## 未驗證與限制

- 未在其他JDK／作業系統執行，Java25語意只查官方文件，不冒充實機編譯。
- 未實作教室VS Code／IntelliJ GUI流程、教室PlantUML安裝或教室投影測試。
- 未登入真實GitHub、push或clone；教材保存段落是學生操作指引。
- 未呼叫真實AI服務；模擬原文與虛構紀錄示範已標示來源。
- 未做150分鐘學生試教；獨立遷移的難度與實際課後負擔仍需教師觀察。
- 不驗收契約外建構值、溢位、null、重複run、共享車或並行行為。
- 自訂遷移題由教師按學生明訂規格人工審查，不能用Cargo參考輸出代替驗收所有題目。
