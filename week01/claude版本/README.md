# 第 1 週教材（claude 版本）

本目錄的所有檔案初稿皆由 Anthropic Claude（Claude Code）生成，教師審閱後使用。
內容深度已針對「大二、大一學過 C# 與 WinForms」的學生調整：不從零解釋程式概念，改以 C#/.NET 與 WinForms 經驗對照 Java。

| 檔案 | 內容 |
|---|---|
| `week01_slides.md` | Marp 投影片（51 頁）：課程說明、C#→Java 平台與 OOP 對照、開發環境（VS Code + Extension Pack for Java、IntelliJ IDEA）、程式結構（`Welcome1`、`Welcome2` 命令列參數、`Student` 自訂類別、`javap`）、Git 與 GitHub、AI 協作規範、課堂實作對照表、隨堂小測、作業 |
| `week01_實作講義.md` | 學生實作講義：Lab 0 安裝 → Lab 1 環境檢查 → Lab 2 三種方式執行 Welcome1 → Lab 3 Welcome2 / Student → Lab 4 Git 兩次 commit → Lab 5 GitHub push 與 collaborator → Lab 6 AI 協作與 compare；每個 Lab 附疑難排解與檢核點；附錄 C#→Java 速查、Git 速查 |
| `AI使用紀錄表_範本.md` | 學生每次作業要附的 AI 使用紀錄表 |
| `code/Welcome1.java` | Deitel Fig. 2.1 改寫 |
| `code/Welcome2.java` | 命令列參數與 `printf` |
| `code/Student.java` | 第一個自訂類別（建構子、getter、方法） |

三支範例程式皆已用 JDK 編譯執行確認。

## 與 `week01/` 根目錄版本的差異

| | `week01/`（Claude + Codex 合作版） | `week01/claude版本/` |
|---|---|---|
| 生成來源 | Claude 43 頁 + Codex 14 頁 | 全部 Claude |
| 深度 | 通用入門 | 以 C# WinForms 經驗對照，加入 `javap`、命令列參數、自訂類別、除錯 |
| GitHub | 一頁（push） | 五頁：帳號與 repo 建立、HTTPS 認證、日常 pull/push/clone、網頁上看 Commits/compare/Blame、IDE 整合 |
| 實作 | 投影片內的 Lab | 獨立的實作講義，投影片只放對照表 |

## 匯出投影片

```bash
npx -y @marp-team/marp-cli@latest week01_slides.md -o week01_slides.html
npx -y @marp-team/marp-cli@latest week01_slides.md --pdf -o week01_slides.pdf
```

或在 VS Code 安裝 *Marp for VS Code* 擴充直接預覽。

## 生成來源標註

每頁投影片第一行 `<!-- _class: by-claude -->`，front-matter 的 CSS 會在右上角顯示橘色 `claude` 徽章。純教師手寫的頁面不加 class 即不顯示徽章。
