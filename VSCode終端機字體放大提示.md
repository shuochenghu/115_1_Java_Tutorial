# VS Code 終端機字體放大提示

上課投影或螢幕較小時，可以用以下方式放大 VS Code 終端機（Terminal）的字體。

## 方法一：設定介面（最直覺）

1. 按 `Ctrl + ,` 開啟設定。
2. 在搜尋框輸入 `terminal font size`。
3. 找到 **Terminal › Integrated: Font Size**，改成想要的數字，例如 `18` 或 `20`。

## 方法二：編輯 settings.json

1. 按 `Ctrl + Shift + P`，輸入並選擇 **Preferences: Open User Settings (JSON)**。
2. 加入以下設定：

```json
"terminal.integrated.fontSize": 18
```

需要的話，也可以一起調整行高：

```json
"terminal.integrated.lineHeight": 1.2
```

## 方法三：自訂快捷鍵臨時縮放（適合上課投影）

VS Code 預設沒有「只縮放終端機」的快捷鍵，需要自己綁定：

1. 按 `Ctrl + Shift + P`，輸入並選擇 **Preferences: Open Keyboard Shortcuts (JSON)**。
2. 在陣列中加入：

```json
{ "key": "ctrl+alt+=", "command": "workbench.action.terminal.fontZoomIn",    "when": "terminalFocus" },
{ "key": "ctrl+alt+-", "command": "workbench.action.terminal.fontZoomOut",   "when": "terminalFocus" },
{ "key": "ctrl+alt+0", "command": "workbench.action.terminal.fontZoomReset", "when": "terminalFocus" }
```

3. 之後游標在終端機內時：

| 快捷鍵 | 功能 |
| --- | --- |
| `Ctrl + Alt + =` | 放大終端機字體 |
| `Ctrl + Alt + -` | 縮小終端機字體 |
| `Ctrl + Alt + 0` | 還原預設大小 |

## 補充

- `Ctrl + =`、`Ctrl + -` 會縮放**整個 VS Code 視窗**，不是只有終端機。
- 如果只想讓設定在某個專案生效，請寫在該專案的 `.vscode/settings.json`，而不是使用者設定。
