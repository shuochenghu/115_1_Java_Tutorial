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

## 2026-11-07 claude 投影片（Claude Code 製作）

使用者要求比照第五、六週，由 Claude 依本目錄教材另製投影片：`week07_slides_codex_claude.md`／`.html`，40頁，每頁 `by-claude` 徽章。結構：第一部分（第5–17頁）只用 examples 的 Member／VipMember／MemberDemo 教 extends、建構順序、super、private 不被繼承、protected 同套件、UML 繼承與 is-a／has-a；第二部分（第18–34頁）以 starter 說明作業（D1–D5 三組 TODO、E 段模擬 AI、F、課後獨立遷移、G、評分）；附錄（第35–40頁）放 teacher 檢查工具、heavy_reference／ai_state／cargo_reference 參考圖與第八週入口。左上標籤藍 examples、綠 starter、紫 ai、灰 teacher；頁尾依段落標示。TODO 1 只投影講義已給的 explore 示範與 spendEnergy 規則，TODO 2、3 只列規格，drill 完整答案只在附錄。原投影片及其他教材、程式未改；本段與 README 索引列為 Claude 依使用者要求補記。

- 環境：OpenJDK／javac 22（build 22+36-2370），未加 --release；examples、starter、teacher、ai-check、transfer 分別在暫存目錄編譯。
- CLI：MemberDemo 六行輸出；C1 拿掉 super 得 `constructor Member in class Member cannot be applied to given types`、required String／found no arguments；C2 得 `points has private access in Member`；C3 加 `bob.addPoints(5)` 可編譯、Bob 33 點。starter MissionLog 為 6/5/3 SUCCESS、starter HeavyRoverTry 有 DIFF；teacher HeavyRoverTry 十五行全 OK、InheritanceCheck 45 項、MissionLogCheck 15/15、MissionStateCheck 16/16；ai/HeavyRover 配完成的 Rover 得9行 DIFF（after drill 20/0/0/1 等，與投影片節錄一致）；CargoRoverTry 七行與投影片一致。六路線其餘四條依 assignment 核對，本次未逐條重跑。
- 投影片：Marp CLI（npx @marp-team/marp-cli@latest）以 `--html` 匯出 HTML；另以 `--allow-local-files --images png` 匯出40頁 PNG，用縮圖聯絡表逐頁巡視。修正第7頁程式被自動縮小與右欄橫向溢出、第28頁 AI 程式超出下緣、第29頁貼近頁尾、第36頁表格壓到頁尾，修正後重看修改頁。
- 未驗證：無頭瀏覽器自動溢出量測、教室投影可讀性、學生試教。尚未提交。

## 2026-11-07 第 5–7 週教師程式演進講解稿（Codex）

- 逐項對照三週 `teacher/` 程式。以 SHA-256 核對 `MissionStep.java` 三週一致，以及第 6、7 週 `Mission.java`、`MissionLog.java` 內容一致。
- Windows OpenJDK／javac 22（build 22+36-2370）分開編譯三週 `teacher/` 頂層 Java。三週 `MissionLogCheck` 各 15/15，預設 `{1,1,2}` 與教師路線 `{1,3,0,2}` 分別核對三週完整輸出一致。第 6 週 `RoverDemo` 的三行輸出與講解稿一致，第 7 週 `HeavyRoverTry` 15 行均為 OK，`InheritanceCheck` 45 項通過。
- 講解稿的本機路徑、引用的 SVG 與第 7 週 README 入口已檢查。15 分鐘為教學安排，實際教室投影與學生試教待實施。

## 2026-11-07 第 5→7 週 teacher 類別演進投影片（Claude Code 製作）

使用者要求說明 codex版第 5、6、7 週 teacher 類別的演進並能上課使用。Claude 新增 `week07_teacher_evolution_codex_claude.md`／`.html`（18頁，每頁 `by-claude`）及 `diagrams/evolution_w05_claude`、`w06`、`w07` 三組 puml／SVG；與同日 Codex 的講解稿各自獨立，未修改該稿或其他教材、程式。

- 檔案比對：以雜湊確認 MissionStep、MissionLogCheck、FixedScriptMission 三週相同；第六、七週 Mission、MissionLog、MissionStateCheck 相同；SampleCase 第五週 teacher 與第六週 examples 相同；Rover 第六→七週差異為新增三個 protected 方法並以其改寫 explore、scan。
- CLI（OpenJDK／javac 22）：三週 teacher 分別編譯；MissionLogCheck 15/15（三週）、MissionStepCheck 8/8、MissionStateCheck 16/16、InheritanceCheck 45 項。另在暫存副本實測兩個投影片用例：`new Mission(new HeavyRover(20,0))` 跑 `{1,1,2}` 得 SUCCESS、energy=6、samples=5、drillCount=0；第六週 Mission 改存行動前能源時 MissionLogCheck 8 組失敗、7 組通過。
- 圖：以本機 PlantUML MIT 1.2026.9（smetana）pipe 模式產 SVG，並另產 PNG 檢視三張圖。
- 投影片：Marp CLI 以 `--html` 匯出；另以 `--allow-local-files --images png` 匯出18頁 PNG 逐頁巡視，修正第5、10、13頁貼近頁尾與第12頁程式縮小／右欄溢出後重看。
- 未驗證：教室投影可讀性、學生試教。尚未提交。

## 2026-11-11 繁體中文輸出驗證

- OpenJDK 22，使用 javac -encoding UTF-8 -Xlint:all 分開編譯範例、學生起始碼及教師解答。以 UTF-8 擷取主控台輸出。
- 執行該週 tools/verify.py，中文預期輸出、教師檢查與原有回歸案例通過。
- 歷史驗證中的英文輸出反映當時的程式，現行預期輸出以本週講義和教師筆記為準。
- Marp CLI 4.5.1 重新匯出 3 份 HTML（35/40/18 頁）。以無頭 Chromium 檢查程式碼區塊與表格的版面邊界，未偵測到溢出。
