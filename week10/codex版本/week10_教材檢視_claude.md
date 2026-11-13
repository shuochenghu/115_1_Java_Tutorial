# 第 10 週 Codex 教材檢視（claude 版本）

本檔由 Anthropic Claude（Claude Code）於 2026-11-12 撰寫，檢視本資料夾的 Codex 教材：README、學生講義、作業、教師筆記、交接、驗證紀錄、AI 範本、codex 投影片，以及 `examples/`、`starter/`、`teacher/`、`ai/`、`teacher/homework/` 的程式。只提建議，未修改任何 codex 檔案。

## 結論

**教材可直接使用，沒有發現錯誤或前後不一致。** 程式輸出、題號、TODO、時間配置與各文件寫的預期結果都對得上。

規劃審閱（[week10_教材生成規劃_claude建議.md](week10_教材生成規劃_claude建議.md)）提出的建議，Codex 採納了：

- 建議 1：把「舊入口輸出被新簽名改變」做成預測題（講義 TODO 2）。
- 建議 2：回收第 8 週的 `describe(int)` 加 `@Override`（講義 A3、教師筆記第 1 節）。
- 建議 3：只改回傳型態的學生預測題（講義 A3）。
- 建議 4：遞迴呼叫自己的常見錯誤（教師筆記 TODO 3）。
- 建議 5：`instanceof` 只需讀懂（講義 D2）。
- 建議 6：短例只用完全相符的基本型態（教師筆記末段）。
- 建議 7：作業多問一題「為何只顯示樣本數」（作業第 2 點）。

## 實測

環境：Windows、OpenJDK 22。examples、starter、teacher、作業參考、模擬 AI 副本各在獨立目錄編譯，逐一執行所有 `main`。

| 項目 | 實測結果 | 與文件 |
|---|---|---|
| `MemberOverloadDemo` | `VIP 會員`／`VIP 會員 點數=30`／`VIP 會員 活動=迎新` | 符合教師筆記 |
| starter `SampleReportDemo`／`SampleOverloadDemo` | 6、4、0 三行／`尚未完成` | 符合 README |
| 只完成 TODO 1 | 舊入口第一行變成 `重型探測車 樣本數=6 能源=8`，盒子兩行不變 | 符合教師筆記 TODO 1 |
| teacher `SampleReportDemo` | 恢復 6、4、0 共用格式 | 符合 TODO 2 |
| teacher `SampleOverloadDemo` | 六行與講義 C 完全相同 | 符合 |
| `CompareMissions`、`MissionLog`、`HeavyRoverTry` | 一般車 `6/5/3` 讀 3 步，重型車 `8/6/2` 讀 2 步；HeavyRoverTry 15 行相符 | 符合 |
| 既有檢查工具 | 15/15、16/16、51 項通過 | 符合驗證紀錄 |
| 作業參考 `QuickSampleDemo` | `快速查詢 樣本數=6`、`快速查詢 樣本數=0` | 符合教師筆記 |
| 模擬 AI 副本 | 可編譯；第二行多出 `能源=8`，其餘四行正確 | 符合教師筆記第 3 節 |
| `describe(int)` 加 `@Override` | `method does not override or implement a method from a supertype` | 符合 |
| 只改回傳型態 | `method describe(int) is already defined in class VipMember` | 符合 |
| Rover 版內寫 `print(label, rover)` | `StackOverflowError` | 符合教師筆記 TODO 3 |
| `tools/verify.py` | 全部通過 | 符合 |

## 建議（皆為可選）

1. **舊入口也能抓到模擬 AI 的錯誤。** 實測模擬 AI 版也會讓 `SampleReportDemo` 第一行多出能源，因為它即使收到的是 `SampleSource` 參考，也會轉交詳報。講義 D 只要求比對 `SampleOverloadDemo`。可在 D4 或教師筆記補一句「再跑一次舊入口，第一行也應恢復共用格式」，讓學生看到回歸測試的價值。
2. **程式註解**：可選項目見 [week10_程式註解檢視_claude.md](week10_程式註解檢視_claude.md)，建議項已於 2026-11-12 補上。
