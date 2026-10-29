# 第七週作業｜新增能力與驗證繼承

初稿由Codex生成。截止與平台由教師公告。先完成主線，再獨立做一個小型遷移題；選做不取代必做。

## 一、主線必做

1. 完成Rover的TODO 1：spendEnergy、explore及scan的重構。只把舊程式留著讓測試過，不算完成重構。教師示範explore，scan由學生模仿完成。
2. 完成HeavyRover的TODO 2（自己的狀態）與TODO 3（drill）。欄位private、不可重複宣告energy／samples／actionCount，不新增setter，不覆寫父類別方法。
3. HeavyRoverTry十五行全OK。從講義D4再提出一個自己的反例，列預期、實際、能抓到的錯誤，不只抄教師測試。
4. 六條既有路線完整輸出保持一致。前兩條核心路線已於課堂做，課後補齊其餘；每次從20/0的新任務出發。修改MissionLog固定script後重編譯，最後恢復{1,1,2}。

| script | 最終energy/samples/actionCount | outcome | steps/ok/rejected/ignored | counts[0..3] | lowest | battery |
|---|---|---|---|---|---|---|---|
| {0} | 20/0/0 | RETURNED | 1/0/0/0 | [1,0,0,0] | 20 | 100.0 |
| {1,1,2} | 6/5/3 | SUCCESS | 3/3/0/0 | [0,2,1,0] | 6 | 30.0 |
| {2,2,2,2,2} | 10/5/5 | SUCCESS | 5/5/0/0 | [0,0,5,0] | 10 | 50.0 |
| {3,9,0} | 20/0/0 | RETURNED | 3/0/2/0 | [1,0,0,1] | 20 | 100.0 |
| {1,1,1,1} | 2/6/3 | SUCCESS | 3/3/0/1 | [0,3,0,0] | 2 | 10.0 |
| {2,1} | 12/3/2 | UNFINISHED | 2/2/0/0 | [0,1,1,0] | 12 | 60.0 |

保留回放、counts、摘要、最低能源、最終狀態及battery，不只貼最後一行。完整輸出可用Markdown折疊區塊收納。

Mission、MissionStep與報表不改；getEnergy與command/energy/result建構順序保留。初始energy 0..20、samples非負，不驗收契約外參數；不讀命令列或Scanner、不增加指令4。舊任務已停止後不讀不記；直接drill不屬於任務日誌。每次新建Mission只run一次，執行與讀結果期間不外部另行操作同一台車。

## 二、一個獨立遷移題（必做，自選題目）

目的：親自寫出另一個子類別與super(...)，確認能離開HeavyRover範例。最低要求固定，題目與設計開放。不必完成社團借用系統或第二份AI除錯。

### 路線A：CargoRover（提供完整規格，可直接選）

自行新建CargoRover.java與CargoRoverTry.java，不複製教師答案。沿用本週完成的Rover。

- CargoRover extends Rover，建構子CargoRover(int energy, int samples)，本週寫法為第一個敘述super(energy, samples)。private int cargo初始0，getCargo()回傳它。
- 貨物容量固定3；它不是科學樣本，不另存能源或樣本。
- 新方法public String load(int amount)：先檢查isStopped，為true回STOPPED；未停止時amount<=0或超出剩餘貨位回REJECTED；其餘增加cargo並回OK。
- 被拒絕時全部狀態不變。裝貨不耗能源、不加樣本、不增加actionCount；這是本題不同於鑽探的規則。貨量已滿不等於Rover成功，成功仍看samples。
- 不覆寫execute，不把裝貨接到Mission腳本；所有輸入為合法小整數。

自行寫測試入口，以下是必要案例，可再補自己的案例：

| 新物件／呼叫 | 回傳與狀態 |
|---|---|
| CargoRover(20,0)連續load(2)、load(2)、load(1) | OK/cargo2、REJECTED/cargo2、OK/cargo3 |
| 同一台接著load(0) | REJECTED/cargo3 |
| 查看原有三個getter | energy20、samples0、actionCount0 |
| CargoRover(0,0).load(1) | STOPPED/cargo0 |
| CargoRover(20,5).load(1) | STOPPED/cargo0 |

另自己補一個負數或剛好滿載的案例，先寫預期再執行。類別圖加上自己的子類別與新成員即可，不必複製另一張完整Rover圖。

### 路線B：自訂同等規模的子類別

先寫一句情境與is-a理由。至少一個private新欄位、一個新方法、使用super的建構子及getter；不可重複父類別狀態、不可覆寫。本週採既有Rover停止規則，新公開的狀態變更方法先檢查停止；其餘拒絕條件由你明訂，拒絕時狀態不變。

行為要有自己的規則，例如容量、次數或紀錄更新，不只改鑽探的名稱和兩個數字。提供正常、邊界、拒絕、已停止案例及完整實際結果；測試入口自行命名並在README說明。規模以一個子類別及一個main為限，增加更多功能列選做。

## 三、AI與設計紀錄（必做）

完成講義E一份模擬AI追蹤，保留預測、DIFF證據、同一物件的兩份狀態圖、最小修正及十五行重測。[AI]原文與[fix]修正可在commit比較。教師模擬和真實AI分開填；沒有真實AI帳號也可完成。

DESIGN只放本週指定圖、is-a理由、遷移規格與測試表。講義／DESIGN可互相引用，不重複抄寫。原始puml與SVG同步，圖改完立即產圖與本人檢視。

## 四、繳交與驗收

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md
  AI_USAGE.md
  java-07/
    README.md
    DESIGN.md
    src/          # examples三檔＋starter六檔＋自己的子類別與測試入口兩檔
    diagrams/     # heavy_practice.puml與.svg；遷移子類別可加在同圖
    ai-check/     # 完成的Rover、修正後AI HeavyRover、HeavyRoverTry
```

C段experiments只供練習，不列繳交必要檔案；不要把刻意錯誤副本混入src。排除out、class、jar、.idea、iml及憑證。教師答案和教師檢查工具不放學生src。

README說明：實際JDK、操作位置、main入口、三組TODO與遷移完成度、如何編譯執行、哪些結果是本人完成。新clone後src與ai-check分開編譯，預設MissionLog及HeavyRoverTry正確，自己的遷移入口符合所寫規格。

| 項目 | 建議配分 | 驗收重點 |
|---|---:|---|
| 主線程式 | 35 | 三組TODO、真正完成局部重構、十五行檢查與舊六路線 |
| 獨立遷移 | 25 | 自己的建構子、狀態與規則、正常／邊界／拒絕／停止案例 |
| AI追蹤與修正 | 20 | 先預測、證據支持原因、最小修正與重測 |
| UML與可重現 | 20 | 指定成員、箭頭、本人SVG、README及保存狀態 |

這是當週內配分，不更動課綱總評。評量重點是能說明並驗證，程式行數或多寫題目不直接加分。課後工時依個別熟練度不同，教師可按課堂觀察調整補件期限，不強迫增加第二個遷移題。

## 選做

- 在自訂子類別補充另一個有實際用途的新方法，明訂與測試規則。
- 自行設計Equipment與一個器材子類別，比較is-a與has-a；不用前週社團程式，不要求第二份AI紀錄。
- Object與toString只閱讀概念，覆寫實作留到第八週。
