# 第八週Codex交接｜覆寫與多型基礎

初稿由Codex生成。承接第七週Codex；本週只新增week08/codex版本，前週與另一模型版本保留。實際驗證見verification，不把作者測試寫成學生已會。

## 已安排的能力與待診斷

- 會員短例觀察覆寫，學生在隔離副本做小修改與super.describe()；D6再以super.execute(command)應用一次。super方法呼叫只做到這兩處，不視為熟練設計。
- 四組主線TODO：1 private explore呼叫產量方法；2 HeavyRover自行宣告@Override；3 CompareMissions建立兩組新車、新Mission及報表呼叫；4 HeavyRover覆寫execute，指令4交給drill、其他指令交回super.execute。
- 用父類別參考追蹤實際物件，區分可呼叫成員與執行時選擇；不要求向下轉型、instanceof或過載完整規則。
- 一份模擬AI私有方法錯誤、兩行不符、編譯診斷、最小修正及同入口重測。
- 一個課後小型遷移，SurveyRover或自訂。未完成課後題不能當第九週共同先備。
- 150分鐘與課後45–60分鐘皆備課估計，未經試教；下週仍診斷覆寫簽名、參考型態及狀態更新位置。

## 精確程式契約

Rover新增protected int exploreYield()回2；private explore保留扣能源6、拒絕不變、addSamples(exploreYield())、countAction一次、回成功。HeavyRover覆寫產量回3，只回數量、不改狀態。private explore不被覆寫；Rover.execute、停止與各行動規則不改。

HeavyRover另覆寫public String execute(int command)：指令4回傳drill()，其他指令回傳super.execute(command)。一般車收到4仍為無效指令。Mission不判斷車種，靠執行時選擇覆寫版本。DrillMissionTry十四行與PolymorphismCheck的指令4案例驗收：重型車20/0跑{4,1}為6/6/2、鑽探1次、任務成功、2步；一般車為14/2/1、任務未完成、成功1拒絕1。

HeavyRover.drill仍耗8得3、成功才增加行動及鑽探次數，停止優先。新版先drill再探索結果6/6/2/1，前週的6/5/2/1只適用前週。HeavyRoverTry已同步兩個期望字串，不以舊預期判新版錯誤。

Mission、MissionStep、MissionLog逐位元組沿用第七週教師檔；建構順序command/energy/result、getEnergy、counts、rejected、battery、空日誌N/A不變。一般車的15項完整輸出期望保留；重型車結果須按本週新規格重算。

固定int[] script，不讀命令列。指令0..4，指令4只有重型車接受；countCommands仍只統計0..3，指令4記在日誌與成功／拒絕筆數，報表標籤[0..3]不變。energy初始0..20、samples非負、合法小整數；script非null；每次新車、新Mission，只run一次；執行與讀結果期間不外部操作同一車。不承諾契約外建構值、溢位、共享車、重複run或並行。

CompareMissions一般車20/0跑{1,1,2}為6/5/3 任務成功、3步；重型車為8/6/2 任務成功、2步、忽略1筆。6/2跑{1,3}一般車0/4/1 能源耗盡，重型車0/5/1 任務成功；皆只讀1步。

遷移題產量固定1..4且不改狀態，不宣稱父流程能安全支援任意惡意覆寫。protected同套件亦能呼叫，課程約定局部方法只供行動實作。

## 第九週入口

先診斷「Rover r = new HeavyRover(...)」的參考型態、實際物件與呼叫路徑，再問：如果共同類別本身不適合直接建立物件，或不同家族都要遵守共同操作規格，該如何表示？用此需求引入abstract與interface。

第九週以後沿用的HeavyRover應保留execute覆寫，否則指令4會退回無效指令；HeavyRoverTry與CompareMissions不含指令4，輸出不受影響。本週仍為可建立的普通Rover；不預先把類別改abstract，不假設匿名／巢狀類別已會。第九週對共同操作的抽象化與第十週過載／多型整合另定範圍，不把本週出現參數錯誤就當作已教過載。

UML只補指定產量方法與覆寫註記；Mission仍關聯Rover，不因main建立HeavyRover就新增Mission對它的欄位關聯。原稿與SVG同步，部分視角有省略說明。

## 維護與驗證入口

以README為教材入口，teacher_notes為答案，tools/verify.py為作者／教師工具。examples、starter、teacher、ai-check、experiments及transfer分開編譯；不把完整答案或教師反射檢查交作學生src。

未有PROJECT_MEMORY.md，長期決策保存在本檔。未建立commit、未fetch或push，不宣稱遠端同步。不更新使用者層級記憶。
