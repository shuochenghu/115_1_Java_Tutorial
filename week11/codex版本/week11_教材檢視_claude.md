# 第 11 週 Codex 教材檢視（claude 版本）

本檢視由 Anthropic Claude（Claude Code）撰寫，日期 2026-11-19。檢視對象是 `week11/codex版本/`，包含 Codex 初稿，以及同日由另一個 Claude 工作階段依教師指示完成的補充修訂（ObjectBox、兩段提示、TODO 4 流程、LiveTotalTry、作業反例、註解格式）。本檔只回報，沒有修改 codex 檔案。另外製作的投影片是 `week11_slides_codex_claude.md`／`.html`（57 頁，每頁 by-claude），新圖放在 `diagrams/*_claude.puml`／`.svg`（8 張）。

## 結論

沒有發現程式錯誤，也沒有發現講義、教師筆記、作業與程式輸出互相矛盾的地方。補充修訂後的教材可以直接使用。只有一項小的不一致（見「建議」）。

## 實際驗證（Windows、OpenJDK 22+36-2370、javac 22）

每一組都在新的暫存目錄分開編譯（`-encoding UTF-8 -Xlint:all`）：

| 項目 | 結果 |
|---|---|
| examples | `ObjectBox` 兩行、`TypedBoxDemo` 兩行，與範例說明相符。取消 `wrongCount` 註解後可編譯，執行時先印兩行，再在第 35 行 ClassCastException |
| examples 編譯實驗 | `Box<int>`：unexpected type／required reference；`Integer wrong = message.get()`：String cannot be converted to Integer |
| starter | 15 檔無警告編譯；起始印「TODO 1～3（保存與取回）還沒通過」；只補完 TODO 1–3 時改印「TODO 4 總樣本數還沒通過」；舊入口輸出與第十週一致 |
| TODO 4 第 1 步 | 在學生檔案無上限時寫迴圈：cannot find symbol，`T extends Object declared in class SampleGroup` |
| teacher | 九行、GenericMethodDemo 兩行、SampleGroupCheck 25 項全部通過 |
| LiveTotalTry（從講義取出原文編譯） | 「加入後 總樣本數=0」「探索後 總樣本數=3 車上樣本數=3」 |
| C2 三題 | ①SampleTray cannot be converted to HeavyRover ②type argument String is not within bounds（JDK 22 另附第二個菱形推斷錯誤）③SampleGroup&lt;HeavyRover&gt; cannot be converted to SampleGroup&lt;SampleSource&gt; |
| 轉型代替上限 | 九行正確、`-Xlint:all` 無警告；`SampleGroup<String>` 能編譯，`totalSamples()` 執行時 ClassCastException，與教師筆記相符 |
| 模擬 AI | `ai/SampleGroup.java:22` unchecked 警告；九行正確；`UnsafeGroupTry.java:12` ClassCastException。最小修正後同一反例在第 10 行編譯失敗；移開後合法九行無警告 |
| 作業 | 教師參考六行相符；回傳型態反例：SampleSource cannot be converted to SampleTray |
| 其他補測 | `get` 回傳改成 Object 時入口報 `no suitable method found for print(String,Object)`；在 Mission 加入 String 報 String cannot be converted to MissionStep（A1） |
| `tools/verify.py` | ALL CHECKS PASSED（加入本份投影片後重跑，55 個本機連結、codex 投影片 39 頁） |

## 建議

1. （**已依教師指示套用**，2026-11-19）`diagrams/todo.puml` 的註記原本寫「TODO 4：類別 T 加上來源上限，再累計數量」，順序和修訂後的講義、起始碼註解相反（現在是先寫迴圈、編譯看 cannot find symbol，再補上限）。已改為「TODO 4：先寫加總迴圈並編譯，看到錯誤後再替類別的 T 加上來源上限」，重新產生 `todo.svg`，`tools/verify.py` 重跑通過，並在 verification.md 補充修訂表加一列。

## 投影片設計說明

- 依教師要求，先把 examples 講完（重複盒子 → ObjectBox → Box&lt;T&gt;，比較錯誤在執行時或編譯時被發現；接著 A3 上限、A4 泛型方法），再進入 starter（A5 演進圖、TODO 1–4、C 自測與反例、D 模擬 AI、E 保存、作業），teacher 答案只放在附錄。
- 與上週演進的 UML：`start_w10_claude`（第十週結束時已在用 `ArrayList<MissionStep>`）、`evolution_w11_claude`（第十週 → 第十一週，新增／沿用色塊）、投影片末頁另有第 8–12 週演進表。其他圖：`box_generic_claude`、`bound_claude`、`starter_todo_w11_claude`、`references_w11_claude`、`no_subtype_claude`、`ai_cast_claude`。
- 所有輸出與編譯診斷都是本次 JDK 22 實測結果；行號對應 2026-11-19 修訂後的檔案。
- 已用 Marp 匯出 PNG 逐張看過版面，修正了 5 頁的圖片大小或欄寬。未在教室投影機、其他 JDK 或學生試教中驗證。
