# 第 4 週實作講義：火星探測車任務日誌（claude 版本）

> 本講義初稿由 Anthropic Claude 生成，教師審閱後使用。
> 主題：陣列、ArrayList 與 Collections Framework 入門；Prompt 設計練習——描述資料結構需求。
> 參考書：Deitel, *Java How to Program, Early Objects* 11e，第 7 章、第 16 章（節次見各段）。

「待填」的地方請直接寫在本檔，課後複製到 `LAB_REPORT.md`。凡是要你**先預測**的題目，請先寫預測再執行，預測錯了不扣分，但不可事後修改預測。

## 0｜準備（10 分鐘）

1. 用 GitHub Desktop 取回學期 repo `115_1_Java`（還原過的電腦請重新 clone；已有副本請先 Fetch／Pull）。
2. 建立今天的資料夾：

   ```text
   115_1_Java/YYYY_MM_DD/java-04/src/
   ```

3. 把教師提供的 `code/examples/` 四個檔案與 `code/starter/MissionLog.java` 複製到 `src/`。**不要**複製 `code/teacher/` 的檔案（同名類別會互相衝突）。
4. 在 `java-04` 內編譯：

   ```powershell
   javac -encoding UTF-8 -d out src/*.java
   java -cp out MissionLog
   ```

   起始碼能執行，最後一行應為 `final energy=6, samples=5, actionCount=3 -> SUCCESS`。

上週的問題：只看最後一行 `6／5／3`，能不能知道每一步是什麼？寫出至少兩種可能的指令順序：待填

## A｜陣列語法與 C# 對照（15 分鐘，Deitel 7.2–7.4、7.7）

開啟 `ArrayBasics.java`，**先預測**再執行 `java -cp out ArrayBasics`。

| 程式 | 預測輸出 | 實際輸出 |
|---|---|---|
| `Arrays.toString(new int[4])` | 待填 | 待填 |
| `script.length`（script 為 `{1, 1, 2}`） | 待填 | 待填 |
| `Arrays.toString(new String[2])` | 待填 | 待填 |
| `System.out.println(script)` | 待填 | 待填 |
| 加強版 for 跑完後的 `counts` | 待填 | 待填 |

A1. 把 `ArrayBasics.java` 最後一行的註解拿掉再執行。記下錯誤訊息中的三件事：

- 例外名稱：待填
- 索引與長度：待填
- 發生在第幾行：待填

看完後把那一行恢復成註解。

A2. C# 寫 `script.Length`，Java 寫 `script.length`；Java 的 `String` 又寫 `name.length()`。請用一句話說明差別：待填

## B｜陣列是參考型態（15 分鐘，Deitel 7.8–7.9）

開啟 `ArrayReference.java`，先只看 `main` 的前三段，**預測**每次印出的 `state`：

| 時間點 | 預測 state | 實際 state |
|---|---|---|
| `alias[0] = 14` 之後 | 待填 | 待填 |
| `explore(state)` 之後 | 待填 | 待填 |
| `reset(state)` 之後 | 待填 | 待填 |

B1. 在紙上或本檔畫出 `reset` 執行時的箭頭圖：`main` 的 `state` 與 `reset` 的 `s` 各指向哪個陣列？

```text
待填
```

B2. 為什麼 `explore` 的修改留下來了，`reset` 的卻沒有？待填

B3. `state == same` 與 `Arrays.equals(state, same)` 的結果各是什麼？和第二週的 `String` 有什麼相同之處？待填

## C｜命令列參數與指令腳本（10 分鐘，Deitel 7.14）

```powershell
java -cp out ScriptArgs
java -cp out ScriptArgs 1 1 2
java -cp out ScriptArgs 3 9 0
```

| 執行 | `args.length` | `args[0] + args[0]` | `sum` |
|---|---|---|---|
| 無參數 | 待填 | （不會印） | （不會印） |
| `1 1 2` | 待填 | 待填 | 待填 |
| `3 9 0` | 待填 | 待填 | 待填 |

C1. 為什麼 `args[0] + args[0]` 不是 2？待填

C2. 在你使用的 IDE 設定 Program arguments 為 `1 1 2` 並執行一次（VS Code：`launch.json` 的 `args`；IntelliJ：Run ▸ Edit Configurations ▸ Program arguments）。成功了嗎？待填

## D｜ArrayList、包裝類別與 Collections（25 分鐘，Deitel 7.16、16.3–16.7）

開啟 `ListBasics.java`。

D1. 把第一行的 `ArrayList<Integer>` 暫時改成 `ArrayList<int>` 再編譯，記下錯誤訊息的關鍵字：待填。改回來。

D2. **先預測**再執行：

| 程式 | 預測 | 實際 |
|---|---|---|
| `[5, 0, 2]` 執行 `remove(0)` | 待填 | 待填 |
| `[5, 0, 2]` 執行 `remove(Integer.valueOf(0))` | 待填 | 待填 |
| `Collections.min([14, 8, 20, 6])` | 待填 | 待填 |
| `Collections.frequency([14, 8, 20, 6], 8)` | 待填 | 待填 |
| `Integer` 100 `==` 100 | 待填 | 待填 |
| `Integer` 1000 `==` 1000 | 待填 | 待填 |
| 從前往後刪除不是 OK 的元素 | 待填 | 待填 |

D3. 用自己的話解釋「從前往後刪除」為什麼會漏掉一個：待填

D4. 完成對照表：

| 動作 | C# `List<int>` | Java `ArrayList<Integer>` |
|---|---|---|
| 筆數 | `Count` | 待填 |
| 讀第 i 筆 | `log[i]` | 待填 |
| 改第 i 筆 | `log[i] = x` | 待填 |
| 刪掉值為 0 的元素 | `Remove(0)` | 待填 |

## E｜任務日誌 MissionLog（37 分鐘）

規則與第三週相同：能源上限 20；探索耗 6 得 2 樣本；掃描耗 2 得 1；充電 +5（封頂 20）；樣本達 5 成功；0 返回。

資料放法：

- `int[] state = {20, 0, 0}`：位置 `ENERGY=0`、`SAMPLES=1`、`ACTIONS=2`
- 三條平行日誌：`commandLog`、`energyLog`（執行後的能源）、`resultLog`（`OK`、`NOT_ENOUGH_ENERGY`、`ALREADY_FULL`、`UNKNOWN_COMMAND`、`RETURN`）
- 腳本用完前任務若已結束（成功、耗盡、返回），剩下的指令不執行

| TODO | 方法 | 支援程度 | 時間 |
|---|---|---|---|
| 1 | `toScript`：`String[]` → `int[]` | 教師帶做 | 6 分 |
| 2 | `runMission` 內寫入三條日誌 | 教師帶做 | 4 分 |
| 3 | `printReplay`：印出每一步 | 模仿投影片輸出 | 10 分 |
| 4 | `countCommands`：各指令次數 | 獨立完成 | 8 分 |
| 5 | `printSummary`：步數、成功、拒絕、忽略、最低能源 | 獨立完成 | 9 分 |

每完成一個 TODO 就重新編譯並執行 `java -cp out MissionLog 3 9 0`。

### 必做測試

每列重新執行一次，填實際結果（E／S／A = energy／samples／actionCount）：

| 參數 | 預期 E／S／A | 預期結果 | 預期摘要 | 實際 |
|---|---|---|---|---|
| （無） | 6／5／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=0` | 待填 |
| `2 2 2 2 2` | 10／5／5 | SUCCESS | `steps=5, ok=5, rejected=0, ignored=0` | 待填 |
| `3 9 0` | 20／0／0 | RETURNED | `steps=3, ok=0, rejected=2, ignored=0` | 待填 |
| `1 1 1 1` | 2／6／3 | SUCCESS | `steps=3, ok=3, rejected=0, ignored=1` | 待填 |
| `2 1` | 12／3／2 | UNFINISHED | `steps=2, ok=2, rejected=0, ignored=0` | 待填 |

`3 9 0` 的 `counts[0..3]` 應為 `[1, 0, 0, 1]`：指令 9 不計數，程式也不能當掉。

E1. 執行 `1 1 1 1`，回放中看得到第 4 個指令嗎？為什麼？待填

E2. 為什麼 `countCommands` 用陣列，日誌卻用 `ArrayList`？待填

E3. `runMission` 修改了 `state`，`main` 為什麼看得到最終值？（連結 B 段）待填

## F｜Prompt 設計與 AI 除錯（18 分鐘）

### F1. 寫一份資料結構需求（8 分鐘）

針對「記錄任務每一步的歷程」，用七個欄位寫出你會給 AI 的需求描述：

| 欄位 | 你的描述 |
|---|---|
| 存什麼（欄位與型態） | 待填 |
| 幾筆（固定？上限？） | 待填 |
| 順序是否重要 | 待填 |
| 是否允許重複 | 待填 |
| 怎麼找（位置／鍵） | 待填 |
| 需要的操作 | 待填 |
| 限制（版本、本週不可用的語法） | 待填 |

### F2. 判斷模擬 AI 建議

以下是**教師設計的模擬 AI 建議**，不是真實對話。對每一項寫出可不可行、代價，以及你的 Prompt 少寫了哪個欄位才導致這種建議。

| 需求 | 模擬 AI 建議 | 可行？代價？ | Prompt 該補哪一欄？ |
|---|---|---|---|
| 統計指令 0–3 各用幾次 | `HashMap<Integer, Integer>` | 待填 | 待填 |
| 記錄每一步歷程 | `int[] log = new int[100]` | 待填 | 待填 |
| 記錄每一步歷程 | 一條 `ArrayList<String>`，每筆存 `"1,OK,14"` | 待填 | 待填 |

若你用真實 AI 工具，請把實際 Prompt 與回覆摘要記在 `AI_USAGE.md`，不要拿本表冒充真實對話。

### F3. AI 除錯三選一（10 分鐘）

**先 commit 你完成的 MissionLog**，再在副本（或另一個資料夾）重現錯誤。三種模擬 AI 修改都能通過預設腳本 `1 1 2`：

1. `countCommands` 拿掉範圍檢查，只寫 `counts[command]++;`（AI 說「指令只有 0–3，檢查多餘」）
2. `printReplay` 的迴圈條件改成 `i <= commandLog.size()`（AI 說「這樣才會印到最後一步」）
3. AI 新增以下方法，並在 `main` 的 `printReplay` 之前呼叫（AI 說「回放時只看有效步驟比較清楚」）：

   ```java
   static void removeRejected(ArrayList<Integer> commandLog, ArrayList<Integer> energyLog,
           ArrayList<String> resultLog) {
       for (int i = 0; i < resultLog.size(); i++) {
           String r = resultLog.get(i);
           if (!r.equals("OK") && !r.equals("RETURN")) {
               commandLog.remove(i);
               energyLog.remove(i);
               resultLog.remove(i);
           }
       }
   }
   ```

我選第 待填 題。

| 證據 | 輸入 | 預期 | 實際 |
|---|---|---|---|
| 反例：修改前（AI 版） | 待填 | 待填 | 待填 |
| 反例：最小修正後 | 同上 | 待填 | 待填 |
| 修正後一般成功 | `2 2 2 2 2` | 10／5／5 SUCCESS | 待填 |
| 修正後拒絕／返回 | `3 9 0` | 20／0／0 RETURNED，回放 3 步 | 待填 |

最小修正是什麼？採納或拒絕這個 AI 建議？理由：待填

（選第 3 題的同學：除了漏刪，也檢查 `printSummary` 的 `ignored` 是否還正確。）

## G｜課末檢核（5 分鐘）

1. `int[] a = {1, 2}; int[] b = a; b[0] = 9;` 之後 `a[0]` 是多少？為什麼？待填
2. `ArrayList<Integer>` 內容 `[3, 9, 0]`，`remove(0)` 之後是什麼？待填
3. 兩個 `Integer` 比較內容要用什麼？待填
4. 下週預告：三條平行清單、`state[1]` 這種寫法，哪裡容易出錯？如果「一步」可以是一個自己設計的型態，你希望它有哪些欄位？待填

## H｜保存、推送、取回驗證（15 分鐘）

- [ ] 重新編譯並跑一次 `3 9 0`；`LAB_REPORT.md`、`AI_USAGE.md`、`java-04/README.md`、`src/` 齊全；不交 `out/` 或 `.class`
- [ ] GitHub Desktop 檢查差異；commit 訊息使用 `[manual]`／`[AI]`／`[fix]`／`[doc]` 前綴
- [ ] Push origin，到 GitHub 網頁確認今天的檔案與最新 commit
- [ ] 在另一個路徑全新 clone，於 `java-04` 重新編譯並跑 `3 9 0`；不要刪除原本的副本

最新 commit 訊息與時間：待填
全新 clone 路徑與執行結果：待填
未完成事項（課後補）：待填

推送失敗時：先把整個 repo 複製到隨身碟或雲端，確認檔案打得開，再請教師協助；不要直接關機。

## 課後選做

- 新增互動模式：第一個參數為 `-i` 時改用 Scanner 逐筆讀指令，但沿用 `applyCommand` 與三條日誌。
- 把 `removeRejected` 改寫成正確版本，並說明你為什麼選「從後往前」或其他做法。
- 用 `HashMap<String, Integer>` 記錄三台探測車（名稱自訂）的最佳樣本數。只能用本週語法，不可自訂類別。
