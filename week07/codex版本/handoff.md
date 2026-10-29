# 第七週Codex交接｜繼承基礎

本週承接第六週Codex。完整Rover是第六週提供的銜接，不把它倒寫成第五週共同能力。第七週新增extends、super建構、protected、is-a／has-a、UML繼承及AI子類別狀態追蹤。

## 學生能力與負擔

- 三組主線TODO：1父類別spendEnergy與explore／scan局部重構，2子類別狀態，3完整drill。探索示範、掃描模仿、鑽探獨立，不提供完整方法讓學生只抄。
- 一份AI追蹤，包含可編譯卻錯誤、兩份狀態、停止與計次、修正及重測。
- 一個課後小型遷移，CargoRover或自訂，固定最低要求、開放設計；社團系統與第二份AI不是必做。
- 課堂150分鐘為備課配置，尚未試教；能力是預期目標，實際熟練度仍需下週診斷。未完成遷移不能當成下週共同先備。

## 精確資料與行為契約

Mission、MissionStep、MissionLog逐位元組沿用第六週Codex教師檔；固定int[] script、建構順序command/energy/result、getEnergy、isSuccessful、完整報表battery與空日誌N/A不改。初始energy 0..20、samples非負、不自動修正契約外參數。

Rover原有三個private狀態、public方法、private explore／scan／charge保持。新增protected boolean spendEnergy(int)：amount<=0或不足回false且不變；否則扣除並回true。protected addSamples(int)只接受正數增加；protected countAction()加1。它們只是局部操作，不自動保證停止或整組行動；同套件程式也能存取。本週限制只有行動實作使用它們，Mission／報表不外部操作它們。

HeavyRover extends Rover，只有private drillCount及提供的DRILL_COST=8、DRILL_YIELD=3。建構super(energy,samples)、drillCount=0。drill先停止，再扣能源，成功+3樣本、+1行動、+1鑽探。拒絕完全不變。execute指令表不變，探索仍得2份，不新增4鑽探指令。

每個新Mission配新Rover，只run一次；任務執行與讀結果期間不外部呼叫同一車的drill或protected方法。獨立直接行動不記入Mission歷史，返回仍由Mission記住。合法小整數，不處理溢位、例外、null或並行。

private成員不被子類別繼承，但子類別物件有父類別宣告的狀態；建構子不被繼承。重複宣告private同名欄位形成兩份狀態，本週不把它誤稱為方法覆寫。super放第一個敘述是課程慣例，不宣稱所有新版Java都強制如此。

## 第八週建議入口

1. 先診斷三組TODO、super與一份／兩份狀態、protected同套件存取。再問「現在多了鑽探，探索為何仍得2份？」
2. 保留private探索流程，在Rover另設protected int exploreYield()回2，HeavyRover以@Override回3，引入覆寫與動態方法選擇；這只是下週建議，本週沒有此API。
3. 再讓Mission接收HeavyRover，觀察Mission程式不改而探索產量不同。多型是下一週新增，不把本週教師測試當成已教會。
4. 不急著覆寫execute加指令4，以免指令計數陣列、輸入與報表契約同時變動。若日後新增，須重新界定並驗證整組契約。
5. Object／toString、C# virtual／override對照可隨第八週安排；抽象類別及介面依第九週課綱。

## 驗證與版本

本週只新增week07/codex版本，未修改前週及Claude版。實際測試、工具版本、視覺範圍與尚未驗證項目見[verification](verification.md)。沒有PROJECT_MEMORY.md，本週長期決策保存在本檔。不更新使用者層級記憶；不建立commit、push或fetch，也不宣稱遠端同步。
