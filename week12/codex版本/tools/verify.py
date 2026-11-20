"""第12週教師驗證：只依賴本週、Python 與 JDK；編譯及變體全在暫存目錄。"""
from pathlib import Path
from tempfile import TemporaryDirectory
import argparse
import json
import re
import subprocess
import xml.etree.ElementTree as ET

WEEK = Path(__file__).resolve().parents[1]
DATA = 'String[] inputs = {"3", "abc", "0", "5", "4", "2"};'
FIXED = """輸入=[3] 受理=3 頁 剩餘=7
輸入=[abc] 拒絕：請輸入整數 剩餘=7
輸入=[0] 拒絕：每筆限 1～5 頁 剩餘=7
輸入=[5] 受理=5 頁 剩餘=2
輸入=[4] 拒絕：剩餘額度不足 剩餘=2
輸入=[2] 受理=2 頁 剩餘=0
批次結束 剩餘=0"""
QUOTA_START = "尚未完成：請先完成 A2，再接上 A3 的逐筆處理。"
THREAD_START = "尚未完成：請完成 B1、B2，再啟動工作實驗。"


def read(relative):
    return (WEEK / relative).read_text(encoding="utf-8")


def run(*args):
    result = subprocess.run(list(map(str, args)), capture_output=True, timeout=35)
    return result.returncode, result.stdout.decode("utf-8", errors="replace").replace("\r\n", "\n").strip(), result.stderr.decode("utf-8", errors="replace")


def build(root, name, sources, reject=False):
    folder = root / name
    folder.mkdir()
    for filename, content in sources.items():
        (folder / filename).write_text(content, encoding="utf-8")
    output = folder / "out"
    code, stdout, stderr = run("javac", "-J-Duser.language=en", "-encoding", "UTF-8", "-Xlint:all", "-d", output,
                               *[folder / filename for filename in sources])
    if reject:
        assert code != 0, "預期編譯拒絕：" + name
        return stderr
    assert code == 0 and not stderr.strip(), (name, stdout, stderr)
    return output


def execute(output, main, *args, failure=False):
    code, stdout, stderr = run("java", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8", "-cp", output, main, *args)
    assert (code != 0) == failure, (main, code, stderr)
    if not failure:
        assert not stderr.strip(), stderr
    return stdout, stderr


def sources(relative):
    return {p.name: p.read_text(encoding="utf-8") for p in (WEEK / relative).glob("*.java")}


def fill(starter, answer, marker):
    pattern = rf"(// BEGIN {marker}\n)(.*?)(\s*// END {marker})"
    replacement = re.search(pattern, answer, re.S)
    assert replacement, marker
    result, count = re.subn(pattern, lambda m: m[1] + replacement[2] + m[3], starter, flags=re.S)
    assert count == 1, marker
    return result


def verify(root):
    example = build(root, "examples", sources("examples"))
    assert execute(example, "ParseDemo", "caught", "abc")[0] == "轉換前\n轉換失敗：請輸入整數\n轉換流程之後"
    for mode in ("caught", "uncaught"):
        assert execute(example, "ParseDemo", mode, "3")[0] == "轉換前\n轉換成功 頁數=3\n轉換流程之後"
    stdout, stderr = execute(example, "ParseDemo", "uncaught", "abc", failure=True)
    assert stdout == "轉換前" and "NumberFormatException" in stderr
    for value, first in (("3", "轉換成功 頁數=3"), ("abc", "轉換失敗")):
        assert execute(example, "FinallyTrace", value)[0] == first + "\n本筆轉換流程收尾\n繼續之後的流程"
    print("通過：獨立短例、未捕捉失敗、finally 兩條完整路徑")

    teacher_q, starter_q = sources("teacher/quota"), sources("starter/quota")
    teacher_t, starter_t = sources("teacher/threads"), sources("starter/threads")
    quota_out = build(root, "teacher-quota", teacher_q)
    assert execute(quota_out, "PrintQuotaDemo")[0] == FIXED
    for name, src, main, expected in (("starter-quota", starter_q, "PrintQuotaDemo", QUOTA_START),
                                     ("starter-threads", starter_t, "ThreadStartDemo", THREAD_START)):
        assert execute(build(root, name, src), main)[0] == expected
    completed_q = {name: fill(code, teacher_q[name], "A2" if name == "PrintQuota.java" else "A3") for name, code in starter_q.items()}
    completed_t = {name: fill(code, teacher_t[name], "B1" if name == "PrintTask.java" else "B2") for name, code in starter_t.items()}
    assert execute(build(root, "completed-quota", completed_q), "PrintQuotaDemo")[0] == FIXED
    partial = dict(starter_q)
    partial["PrintQuota.java"] = completed_q["PrintQuota.java"]
    assert execute(build(root, "partial-quota", partial), "PrintQuotaDemo")[0] == QUOTA_START
    threads_out = build(root, "completed-threads", completed_t)
    # 額外分開編譯教師執行緒原檔，學生完成路徑不是以教師檔案整份取代。
    teacher_threads_out = build(root, "teacher-threads", teacher_t)
    print("通過：兩組 starter／teacher 分開編譯、僅填 TODO 完成、A2 單獨完成時仍提示")

    # 各案例預期由規格列定，不以被測方法計算預期。
    cases = [
        (["1"], ["輸入=[1] 受理=1 頁 剩餘=9"], 9, 1, 0),
        (["5"], ["輸入=[5] 受理=5 頁 剩餘=5"], 5, 1, 0),
        (["0"], ["輸入=[0] 拒絕：每筆限 1～5 頁 剩餘=10"], 10, 0, 1),
        (["-1"], ["輸入=[-1] 拒絕：每筆限 1～5 頁 剩餘=10"], 10, 0, 1),
        (["6"], ["輸入=[6] 拒絕：每筆限 1～5 頁 剩餘=10"], 10, 0, 1),
        ([""], ["輸入=[] 拒絕：請輸入整數 剩餘=10"], 10, 0, 1),
        (["   "], ["輸入=[   ] 拒絕：請輸入整數 剩餘=10"], 10, 0, 1),
        ([" 2 "], ["輸入=[ 2 ] 受理=2 頁 剩餘=8"], 8, 1, 0),
        (["2147483648"], ["輸入=[2147483648] 拒絕：請輸入整數 剩餘=10"], 10, 0, 1),
        (["2.5"], ["輸入=[2.5] 拒絕：請輸入整數 剩餘=10"], 10, 0, 1),
        (["5", "5", "1", "6"], ["輸入=[5] 受理=5 頁 剩餘=5", "輸入=[5] 受理=5 頁 剩餘=0", "輸入=[1] 拒絕：剩餘額度不足 剩餘=0", "輸入=[6] 拒絕：每筆限 1～5 頁 剩餘=0"], 0, 2, 2),
        (["", "0", " 2 "], ["輸入=[] 拒絕：請輸入整數 剩餘=10", "輸入=[0] 拒絕：每筆限 1～5 頁 剩餘=10", "輸入=[ 2 ] 受理=2 頁 剩餘=8"], 8, 1, 2),
        ([], [], 10, 0, 0),
    ]
    homework = {"PrintQuota.java": teacher_q["PrintQuota.java"], **sources("teacher/homework")}
    assert execute(build(root, "homework", homework), "PrintQuotaDemo")[0] == "\n".join(FIXED.splitlines()[:-1] + ["批次結束 受理=3 拒絕=3 剩餘=0"])
    for kind, baseline in (("quota", teacher_q), ("summary", homework)):
        variants = {"PrintQuota.java": baseline["PrintQuota.java"]}
        for index, (inputs, _, _, _, _) in enumerate(cases):
            replacement = "String[] inputs = {" + ", ".join(json.dumps(value, ensure_ascii=False) for value in inputs) + "};"
            assert DATA in baseline["PrintQuotaDemo.java"]
            variants[f"Case{index}.java"] = baseline["PrintQuotaDemo.java"].replace("PrintQuotaDemo", f"Case{index}").replace(DATA, replacement)
        output = build(root, kind + "-cases", variants)
        for index, (_, lines, remaining, accepted, rejected) in enumerate(cases):
            last = f"批次結束 剩餘={remaining}" if kind == "quota" else f"批次結束 受理={accepted} 拒絕={rejected} 剩餘={remaining}"
            assert execute(output, f"Case{index}")[0] == "\n".join(lines + [last]), (kind, index)
    probe = '''public class StateProbe {
      public static void main(String[] args) {
        PrintQuota a = new PrintQuota(); PrintQuota b = new PrintQuota();
        a.reserve(5); a.reserve(5);
        try { a.reserve(1); throw new AssertionError("未拒絕"); }
        catch (IllegalArgumentException e) {
          if (!"剩餘額度不足".equals(e.getMessage())) throw new AssertionError(e);
        }
        System.out.println("第一個=" + a.getRemaining() + " 第二個=" + b.getRemaining());
      }
    }'''
    assert execute(build(root, "independent-state", {"PrintQuota.java": teacher_q["PrintQuota.java"], "StateProbe.java": probe}), "StateProbe")[0] == "第一個=0 第二個=10"
    print("通過：課堂與作業各 13 組完整輸出、空批次、拒絕後成功、零額度及物件獨立")

    bad = {"PrintQuota.java": read("ai/PrintQuota.java"), "PrintQuotaDemo.java": teacher_q["PrintQuotaDemo.java"]}
    wrong_expected = "\n".join(FIXED.splitlines()[:4] + ["輸入=[4] 拒絕：剩餘額度不足 剩餘=-2", "輸入=[2] 拒絕：剩餘額度不足 剩餘=-4", "批次結束 剩餘=-4"])
    assert execute(build(root, "ai-bad", bad), "PrintQuotaDemo")[0] == wrong_expected
    fixed = dict(bad)
    old = 'remaining -= copies;\n        if (remaining < 0) {\n            throw new IllegalArgumentException("剩餘額度不足");\n        }'
    new = 'if (copies > remaining) {\n            throw new IllegalArgumentException("剩餘額度不足");\n        }\n        remaining -= copies;'
    assert old in fixed["PrintQuota.java"]
    fixed["PrintQuota.java"] = fixed["PrintQuota.java"].replace(old, new)
    assert execute(build(root, "ai-fixed", fixed), "PrintQuotaDemo")[0] == FIXED
    order = '''public class CatchOrderTry { public static void main(String[] args) {
      try { Integer.parseInt("abc"); }
      catch (IllegalArgumentException e) { System.out.println("參數錯誤"); }
      catch (NumberFormatException e) { System.out.println("格式錯誤"); }
    }}'''
    assert "NumberFormatException" in build(root, "catch-order", {"CatchOrderTry.java": order}, reject=True)
    missing = "public class JoinTry { public static void main(String[] args) { new Thread().join(); } }"
    assert "InterruptedException" in build(root, "missing-checked-catch", {"JoinTry.java": missing}, reject=True)
    print("通過：AI 錯誤重現、實際最小修正與同案例重測、兩個編譯拒絕")

    for output, count in ((threads_out, 5), (teacher_threads_out, 1)):
        for _ in range(count):
            lines = execute(output, "ThreadStartDemo")[0].splitlines()
            assert len(lines) == 8 and lines[0] == "開始模擬列印" and lines[-1] == "兩項工作皆已結束"
            for label, name in (("講義A", "工作A"), ("講義B", "工作B")):
                assert [line for line in lines if line.startswith("工作=" + label)] == [f"工作={label} 執行緒={name} 步驟={step}" for step in (1, 2, 3)]
    direct = dict(teacher_t)
    direct["ThreadStartDemo.java"] = direct["ThreadStartDemo.java"].replace("a.start();", "a.run();").replace("b.start();", "b.run();")
    expected_direct = ["開始模擬列印"] + [f"工作={label} 執行緒=main 步驟={step}" for label in ("講義A", "講義B") for step in (1, 2, 3)] + ["兩項工作皆已結束"]
    assert execute(build(root, "direct-run", direct), "ThreadStartDemo")[0].splitlines() == expected_direct
    # 用 latch 保持工作存活，預先中斷等待者，再執行教師原檔的 catch 本體。
    support = re.search(r"catch \(InterruptedException e\) \{(.*?)\n        \}", teacher_t["ThreadStartDemo.java"], re.S)[1]
    interrupt_probe = '''import java.util.concurrent.CountDownLatch;
    public class InterruptProbe {
      static CountDownLatch gate = new CountDownLatch(1);
      static void waitFor(Thread worker) {
        try { worker.join(); } catch (InterruptedException e) { SUPPORT }
        System.out.println("錯誤：走到正常結束");
      }
      public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(new Runnable() {
          public void run() {
            try { gate.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
          }
        });
        worker.start(); Thread.currentThread().interrupt();
        try {
          waitFor(worker);
          System.out.println("恢復旗標=" + Thread.currentThread().isInterrupted());
        } finally { Thread.interrupted(); gate.countDown(); worker.join(); }
      }
    }'''.replace("SUPPORT", support)
    assert execute(build(root, "interrupt-support", {"InterruptProbe.java": interrupt_probe}), "InterruptProbe")[0] == "等待被中斷，本次不宣告兩項工作皆已結束\n恢復旗標=true"
    print("通過：學生完成版五次與教師版一次、直接 run 完整對照、受控中斷支援段")


def documents():
    for path in WEEK.rglob("*"):
        if path.suffix not in (".md", ".java", ".py", ".puml", ".svg", ".html"):
            continue
        content = path.read_text(encoding="utf-8")
        assert "\ufffd" not in content, path
        assert not any(line.rstrip() != line for line in content.splitlines()), path
        if path.suffix == ".md":
            assert len(re.findall(r"^```", content, re.M)) % 2 == 0, path
            for target in re.findall(r"!?\[[^\]]*\]\(([^)]+)\)", content):
                if "://" not in target and not target.startswith("#"):
                    assert (path.parent / target.split("#")[0]).exists(), (path, target)
        if path.suffix == ".java":
            assert re.search(r"public class\s+" + re.escape(path.stem) + r"\b", content), path
        if path.suffix == ".svg":
            ET.fromstring(content)
    slides = read("week12_slides_codex.md")
    body = slides.split("---", 2)[2]
    count = 1 + len(re.findall(r"^---\s*$", body, re.M))
    html = read("week12_slides_codex.html")
    assert len(re.findall(r'<svg\b[^>]*data-marpit-svg', html)) == count, count
    for text in ("輸入=[4] 拒絕：剩餘額度不足 剩餘=2", "輸入=[2] 受理=2 頁 剩餘=0"):
        assert text in read("student_lab.md") and text in slides
    assert "../week11" not in read("README.md")
    print(f"通過：UTF-8、區塊、行尾、本機連結、Java 檔名、SVG 與 {count} 頁原稿／HTML 數量")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--code-only", action="store_true", help="編寫中暫略過文件與匯出檢查")
    args = parser.parse_args()
    print(run("java", "-version")[2].splitlines()[0])
    print(run("javac", "-version")[1])
    with TemporaryDirectory(prefix="java-week12-verify-") as temp:
        verify(Path(temp))
    if not args.code_only:
        documents()
    print("第12週本次檢查全部通過；GUI、真實遠端與學生試教另記。")
