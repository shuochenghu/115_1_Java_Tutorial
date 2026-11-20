# 第 12 週獨立短例

先預測，再執行。把這裡兩個 Java 檔複製到自己的 `java-12/examples/`；以下工作目錄為 `java-12`。

```powershell
javac -encoding UTF-8 -d out-examples examples/ParseDemo.java examples/FinallyTrace.java
java -cp out-examples ParseDemo caught abc
```

`ParseDemo` 的第一個參數是 caught 或 uncaught，第二個是要轉換的文字；省略時使用 caught、abc。這是教師提供的切換操作，不要求學生設計命令列解析。

| 執行參數 | 完整標準輸出 | 判讀 |
|---|---|---|
| `caught abc` | 轉換前 → 轉換失敗：請輸入整數 → 轉換流程之後 | 轉換後的成功敘述被跳過，catch 執行後繼續 |
| `caught 3` | 轉換前 → 轉換成功 頁數=3 → 轉換流程之後 | 沒有例外，略過 catch |
| `uncaught abc` | 只印「轉換前」；另在錯誤輸出顯示 NumberFormatException 與堆疊 | main 因未捕捉例外終止，不會印最後一行 |
| `uncaught 3` | 轉換前 → 轉換成功 頁數=3 → 轉換流程之後 | 無例外，仍可正常走完 |

表中箭頭代表換行，不是程式原樣輸出。可一次替換一組參數重跑：

```powershell
java -cp out-examples ParseDemo uncaught abc
java -cp out-examples ParseDemo caught 3
```

讀 `Integer.parseInt(raw.trim())` 時，由內而外看：trim 先去前後空白，parseInt 才轉成 int。方法不能把任意文字變整數；空字串、小數及超出 int 範圍都會失敗。try 也不會重新執行失敗的那行。

## finally 選讀

```powershell
java -cp out-examples FinallyTrace 3
java -cp out-examples FinallyTrace abc
```

正常依序印「轉換成功 頁數=3」「本筆轉換流程收尾」「繼續之後的流程」；格式錯誤依序印「轉換失敗」「本筆轉換流程收尾」「繼續之後的流程」。finally 表達收尾位置，不代表成功，也不保證在 JVM 被強制終止等情況仍能執行。本例只追蹤控制流程，沒有取得需要關閉的資源。
