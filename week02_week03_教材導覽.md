# 第二、三週教材導覽

兩週以「火星探測車」串接：第二週建立能源儀表板，第三週加入探索、掃描與充電指令。適用已有基礎 C# WinForms 經驗的資管系二年級學生。

## 閱讀入口

| 週次 | 教材入口 | 學生實作 | 教師備課 | 投影片 |
|---|---|---|---|---|
| 2：型態與運算子 | [第二週說明](week02/codex版本/README.md) | [學生講義](week02/codex版本/student_lab.md) | [教師筆記](week02/codex版本/teacher_notes.md) | [Markdown](week02/codex版本/week02_slides_codex.md)／[HTML](week02/codex版本/week02_slides_codex.html) |
| 3：選擇與重複結構 | [第三週說明](week03/codex版本/README.md) | [學生講義](week03/codex版本/student_lab.md) | [教師筆記](week03/codex版本/teacher_notes.md) | [Markdown](week03/codex版本/week03_slides_codex.md)／[HTML](week03/codex版本/week03_slides_codex.html) |

## 教學順序

學生作業說明：[第二週：任務儀表板](week02/codex版本/assignment.md)／[第三週：互動任務](week03/codex版本/assignment.md)。內含必做功能、測試、繳交檔案與驗收標準，沿用課堂專案。

1. 第二週先回顧第一週方法的參數、回傳值與輸出，再完成型態選擇、參考追蹤及能源運算。學生以固定值測試儀表板，不需 Scanner 或控制流程。
2. 第三週先核對第二週布林條件與方法能力，依序完成單次行動、固定次數充電與互動任務。Scanner、switch 與迴圈骨架由教材提供並解釋。
3. 讓學生比較「探索、探索、掃描」及「掃描五次」的行動次數與能源差異；再用反例檢查 AI 修改是否符合規則。
4. 第三週末提出任務歷程回放需求，為第四週陣列與集合建立動機。

每週規劃 150 分鐘，包含最後 15 分鐘保存與推送；第三週採骨架補完，完整重寫及新增行動列為延伸。實際上課日期由教師指定。

## 使用注意

- `examples` 是可執行示範，`starter` 保留學生 TODO，`teacher` 為完整解答。同名起始碼與解答不能同時複製到一個專案。
- 學生沿用學期 repo `115_1_Java` 的日期／專案結構，不在每週目錄另建 repo。
- 教學基準為 JDK 21；本次環境為 JDK 22。`--release 21` 編譯及 JDK 22 執行不能等同 JDK 21 實機驗證。
- 任務規則是教學遊戲設定；學生可修改名稱與訊息，必做題保持共同規則以便驗證。

設計依據及三位 agents 的作者／審查分工見[跨週銜接設計](week02_week03_跨週銜接設計.md)。

## 驗證與審查

兩週示範、學生骨架及教師答案已完成本機編譯與對應案例執行；人工設計的 AI 錯誤以副本重現。詳細範圍見[第二週驗證](week02/codex版本/VALIDATION.md)、[第三週驗證](week03/codex版本/verification.md)。

第三位 agent 的連貫性、難度、一致性建議及修訂複查見[獨立審查報告](week02_week03_教材審查報告.md)。課堂節奏及教室端 GUI／遠端操作仍需授課前確認。
