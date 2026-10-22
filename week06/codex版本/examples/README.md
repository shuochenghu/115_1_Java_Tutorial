# 第六週獨立範例

可在本目錄編譯六檔，也可依週README複製至學生src，編譯成功才執行。此處產生的out不提交。

```powershell
javac -encoding UTF-8 -d out *.java
java -cp out RelationDemo
java -cp out SampleCaseDemo
```

## 共享電池：程式證據對應圖上的線

Battery、Lamp、Charger、RelationDemo四檔合作。Battery只管理能源，沒有任務是否結束的資訊。Lamp以欄位保存電池；Charger只在參數中使用。RelationDemo的區域變數不是欄位。

完整輸出：

```text
A=true
B=false
C=true
shared=2
charge=true
B again=true
shared=1
```

A、B共用8格電池；A用6剩2，B被拒絕；C有獨立電池。充電後共享能源7，再用6剩1。多重性描述模型允許的數量，不是這次main恰好建立幾個物件。Battery建構energy 0..20，use正整數；不處理契約外值。

## 樣本盒：獨立AI設計活動

SampleCase沿用上週教師參考，不假設學生已實作。容量建構時修正到1..8；只接受正數且放得下的整批，拒絕不改狀態；unload回傳卸下份數並清空。完整輸出：

```text
first=true, count=2
overflow=false, count=2
fill=true, count=3
unload=3
empty=0
```

E3只在副本暫改store，拿掉「超過剩餘空間」的判斷。錯誤輸出應變成overflow=true,count=4，之後fill至5、unload=5。恢復條件後重跑本範例及兩條主任務路線。這是教師自編模擬建議，不是真實AI回覆。
