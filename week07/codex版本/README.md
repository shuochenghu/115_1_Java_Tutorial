# 第七週｜繼承與新增能力（Codex 版本）

課綱：繼承(I)：類別繼承基礎，AI 生成繼承程式碼閱讀與追蹤。初稿由 Codex 生成，供教師審閱後使用。本版承接第六週 Codex，與 Claude 版獨立製作。

本週先用會員小範例觀察繼承，再回到探測車。HeavyRover 沿用探索、掃描與充電，新增耗能 8、取得 3 份樣本的 drill。探索仍是耗能 6、取得 2 份；改變探索行為留到第八週覆寫。

## 目標與適量練習

- 以 extends 建立 is-a 關係，追蹤 super(...) 與父／子建構流程。
- 分辨繼承的方法、子類別新增方法，以及物件內仍存在但不由子類別繼承的 private 成員。
- 保留 private 欄位，透過 protected 方法擴充；知道同套件也可存取。
- 完成三組主線 TODO、指定類別圖，以及一份模擬 AI 的預測、反例、修正與重測。
- 課後獨立完成一個小型子類別。最低要求固定、題目與設計開放，不另外要求第二套大型系統。

| 節次 | 備課配置 | 學生產出 |
|---|---|---|
| 第1節50分 | A診斷10、B會員範例25、C建構與存取15 | 預測、呼叫判讀、兩個隔離錯誤實驗 |
| 第2節50分 | D1關係與圖10、D2–D4局部重構／子類別30、D5檢查10 | 三組TODO、狀態追蹤與舊輸出 |
| 第3節50分 | E模擬AI25、F修圖與檢核10、G保存15 | 修正證據、本人圖、取回紀錄 |

150分鐘是備課估計，未經學生試教。課堂先確認兩條主任務路線；其餘四條及獨立遷移課後完成。不能挪用AI重測及最後15分鐘。與前週相比，本週保留學生實作，避免以完整支援碼代做核心學習。

## 索引

| 檔案 | 用途 |
|---|---|
| [投影片](week07_slides_codex.md)／[HTML](week07_slides_codex.html) | 沿用前週Marp版型，含講者備註與活動檔案 |
| [student_lab.md](student_lab.md) | A–G可填講義 |
| [assignment.md](assignment.md) | 必做／選做、遷移規格、繳交與評量 |
| [teacher_notes.md](teacher_notes.md) | 逐題答案、節奏、診斷與來源 |
| [DESIGN_template.md](DESIGN_template.md)／[AI_USAGE_template.md](AI_USAGE_template.md) | 學生設計與精簡AI紀錄 |
| [examples/README.md](examples/README.md) | 會員獨立範例、預期輸出與限制 |
| [starter/Rover.java](starter/Rover.java)／[HeavyRover.java](starter/HeavyRover.java) | 三組主線TODO；同目錄其餘四檔已完成 |
| [teacher/InheritanceCheck.java](teacher/InheritanceCheck.java) | 教師狀態及設計檢查；另有舊任務回歸工具 |
| [ai/HeavyRover.java](ai/HeavyRover.java) | 可編譯但刻意有錯的教師模擬回覆 |
| [handoff.md](handoff.md)／[verification.md](verification.md) | 下一週契約、本次驗證與限制 |
| [tools/README.md](tools/README.md) | 教師編譯、產圖、投影片檢查 |

## 學生位置與執行

沿用學期repo `115_1_Java/YYYY_MM_DD/java-07/`，日期改成實際上課日。src放examples三檔、starter六檔，共九檔；完成獨立遷移後再增加自己的兩檔。不要複製teacher或ai到同一個src。LAB_REPORT.md、AI_USAGE.md放日期目錄；README.md、DESIGN.md、src、diagrams、ai-check放java-07。

開VS Code確認檔案；本週三個以上檔案合作，再示範IntelliJ IDEA開啟同一個java-07，不必每題在兩套IDE重做。SDK選已安裝JDK、src設來源根、選有main的入口，不建Maven／Gradle或巢狀repo。

在自己的java-07內逐行執行，編譯成功才執行Java：

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out MemberDemo
java -cp out MissionLog
java -cp out HeavyRoverTry
```

starter的MissionLog輸出已與前週完成版相同；HeavyRoverTry會出現DIFF，因為drill只是NOT_READY。舊任務成功不代表本週TODO已完成。完整完成後HeavyRoverTry十五行都為OK。

## 契約與邊界

- 主任務仍修改main的固定int[] script，預設{1,1,2}；不讀命令列參數。不要用`MissionLog 3 9 0`冒充換路線。
- 初始energy為0..20、samples非負；不自動修正契約外參數。課堂只使用小整數，不處理數值溢位、null、例外或輸入解析。
- Mission、MissionStep、MissionLog沿用第六週Codex原檔：建構順序command/energy/result，getEnergy、isSuccessful、battery及空日誌N/A均保留。
- 每個新Mission配新Rover，只run一次。任務執行及查結果期間，不另外呼叫同一台車的drill或protected方法；直接行動練習使用另一台車，不寫入Mission歷史。
- 新公開入口drill自行檢查isStopped。protected方法分別負責局部更新，不自動保證停止、計次或整組行動正確；同套件其他類別也能呼叫，課程規定只有行動實作用它們。
- 本週不覆寫execute，不增加指令4；腳本無法呼叫drill。返回由Mission記住，Rover不新增returned狀態。

## 類別圖的立即預覽

複製diagrams/heavy_practice.puml與同名SVG到自己的diagrams，補圖後立即重新產SVG並開啟檢查。DESIGN.md直接引用SVG，GitHub閱讀不依賴瀏覽器擴充功能。不修改舊SVG冒充新結果。

教師課前從[PlantUML官方發行](https://github.com/plantuml/plantuml/releases)備妥jar。下列是位置範例，換成實際路徑；在java-07執行：

```powershell
cmd /c "java -jar C:\ClassTools\plantuml.jar -charset UTF-8 -tsvg -pipe < diagrams\heavy_practice.puml > diagrams\heavy_practice.svg"
```

來源已指定Smetana，不要求學生安裝Graphviz或Python。出錯時教師可用tools/render_diagrams.py協助當堂代產，學生核對自己的修改並記操作者。全新clone後仍須編譯與開圖，不能以作者驗證代替本人操作。
