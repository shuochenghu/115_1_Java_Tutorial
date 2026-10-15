# 第 4 週教師筆記（claude 版本）

> 本筆記初稿由 Anthropic Claude 生成，供授課教師備課使用；學生不需閱讀。

## 定位與銜接

| 方向 | 內容 |
|---|---|
| 承接第 3 週 | 行動規則（`switch`、能源檢查）、迴圈與 `break`、結束判斷「成功 > 耗盡 > 返回」，以及第 3 週結尾的問題：只剩最後狀態無法回放 |
| 承接第 2 週 | 參考與箭頭圖、`==` 與 `equals`；本週延伸到陣列（`Arrays.equals`）與 `Integer` |
| 本週新增 | 陣列（建立、預設值、`length`、走訪、越界、參考、`Arrays`）、`args`、`ArrayList`、包裝類別與自動裝箱、`Collections`、Prompt 需求描述 |
| 交給第 5 週 | 三條平行清單容易對不齊、`state[1]` 靠位置表達意義 → 自訂 `MissionStep`／`Rover` 類別，`ArrayList<MissionStep>` |

**刻意不教**：自訂類別、`try/catch`、Lambda／Stream、`Iterator`、泛型方法、二維陣列（Deitel 7.11–7.12 可留到需要時）、可變長度參數（7.13）。`Arrays.asList` 只當作建立測試資料的工具，需 import java.util.Arrays；它建立固定大小的清單，外層 new ArrayList 複製成可增刪清單，不展開可變長度參數。

Set 與 Map 只在投影片的地圖上出現一次。課綱寫 Collections Framework「入門」，本週以 `List` 為主，`HashMap` 放在選做。

## 時間配置（150 分鐘，休息另計）

| 分鐘 | 段落 | 教師動作 | 若進度落後 |
|---|---|---|---|
| 0–10 | 0：取回 repo、上週問題 | 請學生舉出能得到 6／5／3 的不同指令順序 | 不可省略取回 |
| 10–25 | A：陣列語法 | 投影 C#／Java 對照；學生先預測再執行 | A2 改口頭 |
| 25–40 | B：陣列參考 | 黑板畫箭頭圖，重點在 `reset` | B3 併入課末檢核 |
| 40–50 | C：`args` | 示範一次 IDE 設定參數 | C2 留到課後 |
| 50–75 | D：ArrayList、Collections、三陷阱 | 陷阱 1–3 都先讓學生預測 | D4 對照表課後填 |
| 75–112 | E：MissionLog TODO 1–5 | TODO 1–2 帶做，3 模仿，4–5 巡堂 | 先做 TODO 4，TODO 5 可課後補 |
| 112–130 | F：Prompt 與 AI 除錯 | F1 個人寫 8 分鐘，F2 鄰座討論，F3 三選一 | F3 當堂至少完成「反例＋修正」兩列 |
| 130–135 | G：課末檢核 | 口頭抽問 | |
| 135–150 | H：保存與推送 | 巡查 GitHub 網頁 | **不可挪用** |

## 講義解答

### 0
6／5／3 可由 `1 1 2`、`1 2 1`、`2 1 1` 得到。只有最終狀態時，無法區分這些順序，也看不到被拒絕的指令。

### A
| 程式 | 輸出 |
|---|---|
| `new int[4]` | `[0, 0, 0, 0]` |
| `script.length` | `3` |
| `new String[2]` | `[null, null]` |
| `println(script)` | `[I@` 開頭加十六進位雜湊碼（每次執行可能不同） |
| `counts` | `[0, 2, 1, 0]` |

A1：`ArrayIndexOutOfBoundsException`；`Index 3 out of bounds for length 3`；`ArrayBasics.java:36`（行號以學生實際檔案為準）。
A2：陣列的 `length` 是欄位，沒有括號；`String` 的 `length()` 是方法。C# 兩者都用屬性 `Length`。

### B
| 時間點 | state |
|---|---|
| `alias[0] = 14` 後 | `[14, 0, 0]` |
| `explore` 後 | `[8, 2, 1]` |
| `reset` 後 | `[8, 2, 1]`（`reset` 內印出 `[20, 0, 0]`） |

B2：Java 只有傳值。陣列變數的值是參考，`explore` 透過這個參考修改同一個物件的元素；`reset` 把區域變數 `s` 改指向新陣列，`main` 的 `state` 仍指向原陣列。
B3：`false`、`true`。和 `String` 一樣，`==` 比參考，內容比較要用專門的方法。

常見誤解：「陣列是傳參考（pass by reference）」。可以接受學生說「傳的是參考的副本」，但要用 `reset` 的結果糾正「傳參考」的說法（Deitel 7.9 的說法與此相同）。

### C
| 執行 | `args.length` | `args[0] + args[0]` | `sum` |
|---|---|---|---|
| 無參數 | 0 | — | — |
| `1 1 2` | 3 | `11` | 4 |
| `3 9 0` | 3 | `33` | 12 |

C1：`args` 的元素是 `String`，`+` 是字串串接。

### D
| 程式 | 輸出 |
|---|---|
| `remove(0)` | `[0, 2]` |
| `remove(Integer.valueOf(0))` | `[5, 2]` |
| `min` | `6` |
| `frequency(…, 8)` | `1` |
| 100 `==` 100 | `true` |
| 1000 `==` 1000 | `false` |
| 從前往後刪 | `[OK, UNKNOWN, OK]` |

D1：錯誤訊息含 `unexpected type`，以及 `required: reference`、`found: int`。
D3：刪除索引 i 後，原本在 i+1 的元素補到 i，但迴圈接著檢查 i+1，跳過了補上來的元素。
D4：`size()`、`get(i)`、`set(i, x)`、`remove(Integer.valueOf(0))`。

`Integer` 快取的範圍 -128..127 是 Java 規格保證的下限，實作可以放大。課堂上只需說「小數字剛好相等，不能依賴」。

### E
- E1：看不到。第 3 步後樣本 6 ≥ 5，迴圈 `break`；第 4 個指令計入 `ignored=1`。
- E2：指令是 0–3 的固定小範圍，直接當索引最簡單；步數事先未知，需要能成長的容器。
- E3：`state` 是陣列，`runMission` 透過參考修改同一個物件的元素（同 B 的 `explore`）。

TODO 參考解答見 `code/teacher/MissionLog.java`。巡堂時常見錯誤：

| 錯誤 | 症狀 | 引導問題 |
|---|---|---|
| TODO 1 用 `script.length` 當迴圈上限，但 `script` 尚未建立 | 編譯錯誤或長度 0 | 「新陣列應該多長？」 |
| TODO 2 在 `applyCommand` 之前加入 `energyLog` | 回放中的能源是前一步的值 | 「這一步做完後剩多少？」 |
| TODO 2 只在 `OK` 時記錄 | `3 9 0` 回放只剩 1 步 | 「回放要不要看得到失敗？」 |
| TODO 3 用加強版 for 走訪 `commandLog` | 拿不到另外兩條清單的對應值 | 「另外兩條要用什麼索引？」 |
| TODO 4 沒有檢查範圍 | `3 9 0` 當掉 | 讓學生自己讀例外訊息 |
| TODO 5 用 `==` 比較 `"OK"` | 通常結果是對的（字串常值共用同一物件），卻是錯誤寫法 | 回到第 2 週的 `equals` |
| TODO 5 `rejected` 把 RETURN 也算進去 | `3 9 0` 得到 `rejected=3` | 回頭看 `rejected` 的定義 |

### F2 參考判斷
| 建議 | 判斷 | Prompt 該補 |
|---|---|---|
| `HashMap<Integer, Integer>` 計數 | 可行，但鍵是 0–3 連續小整數，`int[4]` 更簡單；Map 要處理鍵不存在的情況 | 「幾筆／範圍」：指令只有 0–3 |
| `int[100]` 記歷程 | 有上限，還要另外維護 count；超過 100 步會越界 | 「幾筆」：步數事先未知 |
| 一條 `ArrayList<String>` 存 `"1,OK,14"` | 可行但失去型態；取能源要 `split` 加 `parseInt`，不能直接用 `Collections.min` | 「存什麼」：三個欄位與各自型態；「要做什麼」：找最低能源 |

學生若主張「自訂一個 Step 類別」，這是正確的直覺，但屬第 5 週內容。請他記在 G4。

### F3 三選一參考答案

三種修改都能通過預設腳本 `1 1 2`，用 `3 9 0` 都能找出錯誤（以下皆已實測）。

| # | `3 9 0` 的結果 | 最小修正 | 判斷 |
|---|---|---|---|
| 1 | 回放正常，接著 `ArrayIndexOutOfBoundsException: Index 9 out of bounds for length 4`，發生在 `countCommands` | 恢復 `if (command >= 0 && command < counts.length)` | 拒絕：「指令只有 0–3」只對合法輸入成立，日誌本來就會記錄無效指令 |
| 2 | 印完 3 步後 `IndexOutOfBoundsException: Index 3 out of bounds for length 3`，發生在 `ArrayList.get`（`printReplay`） | 改回 `<` | 拒絕：最後索引是 `size() - 1` |
| 3 | 回放只剩 `9 UNKNOWN_COMMAND` 與 `0 RETURN` 兩步；`counts=[1, 0, 0, 0]`；摘要 `steps=2, rejected=1, ignored=1` | 改成從後往前（`for (int i = resultLog.size() - 1; i >= 0; i--)`） | 修正後也要指出副作用：刪除日誌會讓 `ignored` 和 `counts` 失真，因此「回放時跳過」優於「刪掉資料」 |

第 3 題修正後，`3 9 0` 回放只剩 `0 RETURN`，`ignored` 會被算成 2，仍然錯誤。這是好的討論點：AI 的建議改變了資料本身，不只是顯示方式。接受學生「拒絕整個建議，改成在 `printReplay` 跳過非 OK」的答案。

## 評分重點

| 項目 | 看什麼 |
|---|---|
| MissionLog | 五條必做測試全部正確；`3 9 0` 不當掉 |
| 預測欄 | 有填且看得出是先寫的（與實際不同也無妨） |
| F1 | 七個欄位都有實質內容，尤其「幾筆」和「要做什麼」 |
| F3 | 反例在修改前後都有實際結果；修正是最小的；採納或拒絕的理由與證據一致 |
| AI_USAGE | 真實與模擬分開；沒有使用 AI 也要交 |
| 保存 | GitHub 上看得到當日資料夾；全新 clone 驗證有紀錄或據實註明未完成 |

## 驗證狀態

程式碼與預期輸出的實測紀錄見 `week04_驗證紀錄_claude.md`。仍未驗證：教室電腦實際安裝的 JDK、VS Code／IntelliJ 的參數設定畫面、學生課堂試做的時間。
