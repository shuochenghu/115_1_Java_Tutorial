# 第八週｜覆寫與多型基礎（Codex 版本）

課綱：繼承(II)：覆寫、多型基礎與設計實務，Code Review：審查AI生成的繼承程式碼。初稿由Codex生成，供教師審閱後使用；承接第七週Codex，兩個模型版本獨立。

同一份Mission程式如何讓一般車探索得2份、重型車得3份？本週保留Rover的行動流程，新增protected exploreYield()，再由HeavyRover覆寫。每次探索仍耗6格、只增加一次行動。

## 目標、先備與範圍

- 先診斷extends、super建構、private狀態、protected局部操作、Mission的停止與日誌責任。前週課後遷移不當作全班已會的先備。
- 本週新增實例方法覆寫、@Override、父類別型態參考與執行時方法選擇。
- 分開判斷「能否編譯」與「執行哪個版本」，完成三組主線TODO、一份AI審查與一個小型課後遷移。
- 抽象類別與介面留第九週；過載的完整比較留第十週；轉型、instanceof、泛型設計、JUnit不列本週必做。

| 節次 | 備課配置 | 最低產出 |
|---|---|---|
| 第1節50分 | A診斷10、B會員20、C參考與實驗20 | 預測、小修改、編譯判讀 |
| 第2節50分 | D1規格10、D2–D4實作25、D5比較15 | 三組TODO、兩車完整結果 |
| 第3節50分 | E模擬AI25、F圖與檢核10、G保存15 | 反例、最小修正及重測、本人圖 |

150分鐘為未經試教的備課估計。課後新增工作暫估45–60分鐘，另加個別未完成項目；不以工時當驗收標準。先確保核心練習與重測，再補文字整理，不另堆一套大型系統。

## 教材索引

| 檔案 | 用途 |
|---|---|
| [Marp原稿](week08_slides_codex.md)／[HTML](week08_slides_codex.html) | 16:9講授、活動入口與講者備註 |
| [student_lab.md](student_lab.md) | A–G學生可填講義 |
| [assignment.md](assignment.md) | 必做／選做、案例、繳交與評分 |
| [teacher_notes.md](teacher_notes.md) | 逐題答案、完整輸出與診斷 |
| [examples/README.md](examples/README.md) | 三檔獨立會員範例 |
| [starter/Rover.java](starter/Rover.java) | TODO 1：流程使用產量方法 |
| [starter/HeavyRover.java](starter/HeavyRover.java) | TODO 2：自行宣告覆寫方法 |
| [starter/CompareMissions.java](starter/CompareMissions.java) | TODO 3：兩組獨立任務 |
| [ai/HeavyRover.java](ai/HeavyRover.java) | 刻意有錯的教師模擬回覆 |
| [DESIGN_template.md](DESIGN_template.md)／[AI_USAGE_template.md](AI_USAGE_template.md) | 精簡設計及AI紀錄 |
| [teacher/PolymorphismCheck.java](teacher/PolymorphismCheck.java) | 教師狀態／設計檢查，不放學生src |
| [handoff.md](handoff.md)／[verification.md](verification.md) | 跨週能力契約、本次實測與限制 |
| [tools/README.md](tools/README.md) | 教師驗證、產圖與匯出 |

## 學生位置與操作

沿用學期repo，不另建巢狀repo。日期換成實際上課日：

```text
115_1_Java/YYYY_MM_DD/
  LAB_REPORT.md
  AI_USAGE.md
  java-08/
    README.md
    DESIGN.md
    src/          # examples三檔＋starter七檔，共十檔
    diagrams/     # dispatch_practice.puml與同名SVG
    ai-check/     # E活動三檔，分開編譯
```

先將examples三檔與starter七檔複製到自己的src；不複製teacher及ai。先做講義預測，再執行指定入口。VS Code確認檔案後，示範IntelliJ IDEA開同一java-08，確認已安裝JDK、src來源根與main入口；不建立Maven／Gradle，不必每題雙IDE重做。

在自己的java-08，逐行執行，編譯成功才執行：

```powershell
java -version
javac -version
javac -encoding UTF-8 -d out src/*.java
java -cp out MemberDemo
java -cp out MissionLog
java -cp out HeavyRoverTry
java -cp out CompareMissions
```

起始碼能編譯，MissionLog仍正確；HeavyRoverTry有兩行不符，CompareMissions印尚未完成。這些是未完成標示。三組TODO完成後，HeavyRoverTry十五行都顯示「相符」，CompareMissions兩段完整報表符合教師筆記D5。

## 精確契約與版本變動

- 一般車探索耗6格得2份，重型車耗6格得3份；掃描耗2格得1份、充電+5上限20、目標至少5份均不變。鑽探耗8格得3份，只是另一個直接行動。
- exploreYield()只回傳正的探索產量。本週固定為2或3；遷移題限固定1..4。它不改狀態、不扣能源、不檢查停止；execute守停止，private explore守能源與整組更新。
- 父類別三個狀態維持private；private explore不改可見性、不重寫execute、不加入指令4。protected在同套件亦可存取，本課程只由行動流程使用產量方法，不在main外部操作局部方法。
- Mission、MissionStep、MissionLog逐位元組沿用第七週教師版。固定int[] script，不讀命令列或Scanner；command/energy/result建構順序、getEnergy、報表及N/A不變。
- 每個新Mission配新車，只run一次；腳本非null，energy為0..20、samples非負，合法小整數。不驗收溢位、契約外建構值、共享車、並行或重複run。
- 未停止才讀下一筆；返回記完停止；成功與耗盡同時成立時成功優先。任務及查結果期間不由外部另行操作同一台車。
- 第七週重型車探索得2份的預期不再適用本週。HeavyRoverTry的兩處6/5/2/1更新為6/6/2/1；前週原檔保留。

## UML與立即預覽

將[dispatch_practice.puml](diagrams/dispatch_practice.puml)及[SVG](diagrams/dispatch_practice.svg)複製到自己的diagrams；完成F後重新產圖，DESIGN引用本人SVG。教師圖[dispatch_reference.svg](diagrams/dispatch_reference.svg)供備課，不直接發作答案。

沿用前週教師備妥的PlantUML jar。以下路徑換實際位置，在java-08執行：

```powershell
cmd /c "java -jar C:\ClassTools\plantuml.jar -charset UTF-8 -tsvg -pipe < diagrams\dispatch_practice.puml > diagrams\dispatch_practice.svg"
```

來源使用Smetana，不要求另裝Graphviz或Python。jar取得參考[官方發行](https://github.com/plantuml/plantuml/releases)。學生補圖後立即開SVG核對；教師代產須註記操作者，不只改舊SVG。完整實測及未驗證項目見verification。
