"""第 11 週教師驗證工具；所有編譯、錯誤副本都在新的暫存目錄。"""
from pathlib import Path
from tempfile import TemporaryDirectory
import argparse
import os
import re
import shutil
import subprocess

WEEK = Path(__file__).resolve().parents[1]
PREVIOUS = WEEK.parents[1] / "week10" / "codex版本"
DEMO = """樣本盒群組 筆數=2 總樣本數=4
車輛群組 筆數=1 總樣本數=6
混合群組 筆數=3 總樣本數=10
重型探測車 樣本數=6 能源=8
重型探測車 樣本數=6
取回重型車 能源=8 鑽探次數=0
同一台車=true
空群組 筆數=0 總樣本數=0
任務狀態=8/6/2 日誌筆數=2"""
STAGE1 = "尚未完成：TODO 1～3（保存與取回）還沒通過，請依講義 B 檢查。"
STAGE2 = "尚未完成：TODO 1～3 已能保存與取回，TODO 4 總樣本數還沒通過。"
LIVE = "加入後 總樣本數=0\n探索後 總樣本數=3 車上樣本數=3"


def lab_snippet(name):
    """從學生講義取出指定類別的程式區塊，確保講義與驗證使用同一份程式。"""
    text = (WEEK / "student_lab.md").read_text(encoding="utf-8")
    for block in re.findall(r"```java\n(.*?)```", text, re.S):
        if "public class " + name in block:
            return block
    raise AssertionError("講義缺少 " + name)


def run(*args):
    return subprocess.run(list(map(str, args)), capture_output=True, text=True,
                          encoding="utf-8", errors="replace", timeout=35)


def build(files, output, *, cp=None, reject=None, warning=False):
    output.mkdir(parents=True, exist_ok=True)
    command = ["javac", "-encoding", "UTF-8", "-Xlint:all", "-d", output]
    if cp is not None:
        command += ["-cp", cp]
    result = run(*command, *files)
    if reject:
        assert result.returncode != 0 and reject in result.stderr, result.stderr
    else:
        assert result.returncode == 0, result.stderr
        if warning:
            assert "unchecked" in result.stderr, result.stderr
        else:
            assert not result.stderr.strip(), result.stderr


def execute(cp, name, failure=None):
    result = run("java", "-Dstdout.encoding=UTF-8", "-cp", cp, name)
    if failure:
        assert result.returncode != 0 and failure in result.stderr, result.stderr
    else:
        assert result.returncode == 0, result.stderr
    return result.stdout.strip()


def variant(root, name, replacements=None):
    target = root / name
    target.mkdir()
    for source in (WEEK / "teacher").glob("*.java"):
        shutil.copyfile(source, target / source.name)
    for filename, content in (replacements or {}).items():
        (target / filename).write_text(content, encoding="utf-8")
    return list(target.glob("*.java"))


def verify(root):
    examples, starter, teacher = [WEEK / name for name in ("examples", "starter", "teacher")]
    for name, source in (("examples", examples), ("starter", starter), ("teacher", teacher)):
        build(list(source.glob("*.java")), root / name)
    assert execute(root / "examples", "TypedBoxDemo") == "訊息=採樣完成\n數量=7"
    assert execute(root / "examples", "ObjectBox") == "Object 盒 訊息=採樣完成\nObject 盒 數量=7"
    # ObjectBox 的執行期實驗：取消註解後仍可編譯，執行時才 ClassCastException。
    runtime = root / "object-runtime"
    runtime.mkdir()
    box = (examples / "ObjectBox.java").read_text(encoding="utf-8")
    assert "        // Integer wrongCount" in box
    (runtime / "ObjectBox.java").write_text(box.replace("        // Integer wrongCount", "        Integer wrongCount"), encoding="utf-8")
    build([runtime / "ObjectBox.java"], root / "object-runtime-out")
    execute(root / "object-runtime-out", "ObjectBox", failure="ClassCastException")
    assert execute(root / "starter", "SampleGroupDemo") == STAGE1
    assert execute(root / "teacher", "SampleGroupDemo") == DEMO
    assert execute(root / "teacher", "GenericMethodDemo") == "較多樣本=9\n平手回左側=true"
    print("PASS examples／starter／teacher 分開編譯，完整展示與泛型方法輸出")

    # 只替換唯一 TODO 檔，確認學生可以使用提供的入口完成，不需取用其他答案。
    completed = root / "completed-src"
    shutil.copytree(starter, completed)
    shutil.copyfile(teacher / "SampleGroup.java", completed / "SampleGroup.java")
    build(list(completed.glob("*.java")), root / "completed")
    assert execute(root / "completed", "SampleGroupDemo") == DEMO
    print("PASS 只替換 SampleGroup.java 即可完成學生版")

    # 分段提示：TODO 1～3 完成、TODO 4 仍是佔位值時，展示入口要印第二段提示。
    answer = (teacher / "SampleGroup.java").read_text(encoding="utf-8")
    partial = root / "partial-src"
    shutil.copytree(starter, partial)
    (partial / "SampleGroup.java").write_text(
        answer.replace("<T extends SampleSource>", "<T>").replace("total += source.getSamples();", ""), encoding="utf-8")
    build(list(partial.glob("*.java")), root / "partial")
    assert execute(root / "partial", "SampleGroupDemo") == STAGE2
    print("PASS 起始碼與 TODO 1～3 完成時的兩段未完成提示")

    # 講義 C1 的 LiveTotalTry：正式答案從 0 變 3；在 add 時先存總和的寫法九行仍正確，卻被這個案例抓到。
    live = lab_snippet("LiveTotalTry")
    (root / "LiveTotalTry.java").write_text(live, encoding="utf-8")
    build([root / "LiveTotalTry.java"], root / "live", cp=root / "teacher")
    assert execute(f"{root / 'teacher'}{os.pathsep}{root / 'live'}", "LiveTotalTry") == LIVE
    cached = answer.replace("private ArrayList<T> sources;", "private ArrayList<T> sources;\n    private int cached;")
    cached = cached.replace("sources.add(source);", "sources.add(source);\n        cached += source.getSamples();")
    cached = cached.replace("        int total = 0;\n        for (T source : sources) {\n            total += source.getSamples();\n        }\n        return total;", "        return cached;")
    assert "return cached;" in cached
    files = variant(root, "cached-src", {"SampleGroup.java": cached, "LiveTotalTry.java": live})
    build(files, root / "cached")
    assert execute(root / "cached", "SampleGroupDemo") == DEMO
    assert execute(root / "cached", "LiveTotalTry") == "加入後 總樣本數=0\n探索後 總樣本數=0 車上樣本數=3"
    # 用轉型代替上限：九行仍正確且無警告，但 SampleGroup<String> 也能編譯，所以 C2 第 2 題要用自己的群組實測。
    casted = answer.replace("<T extends SampleSource>", "<T>").replace(
        "total += source.getSamples();", "total += ((SampleSource) source).getSamples();")
    build(variant(root, "cast-src", {"SampleGroup.java": casted}), root / "cast")
    assert execute(root / "cast", "SampleGroupDemo") == DEMO
    words = root / "Words.java"
    words.write_text("public class Words { void check() { SampleGroup<String> g = new SampleGroup<>(); } }", encoding="utf-8")
    build([words], root / "words", cp=root / "cast")
    print("PASS LiveTotalTry 0→3；快取總和與轉型代替上限都能通過九行展示，需靠講義自測抓出")

    output = execute(root / "teacher", "SampleGroupCheck")
    assert output.endswith("SampleGroupCheck 25 項通過"), output
    print(output.splitlines()[-1])
    for name in ("MissionLogCheck", "MissionStateCheck", "PolymorphismCheck"):
        print(execute(root / "teacher", name).splitlines()[-1])
    build(list((PREVIOUS / "teacher").glob("*.java")), root / "previous")
    for name in ("MissionLog", "CompareMissions", "HeavyRoverTry", "SampleReportDemo", "SampleOverloadDemo"):
        assert execute(root / "teacher", name) == execute(root / "previous", name), name
    for name in ("Rover.java", "HeavyRover.java", "Mission.java", "MissionStep.java", "SampleReport.java"):
        assert (teacher / name).read_bytes() == (PREVIOUS / "teacher" / name).read_bytes(), name
    print("PASS 第 10 週五個入口完整輸出回歸；指令 4 與群組查詢不改狀態／完整日誌")

    failures = {
        "WrongElement": ("SampleGroup<HeavyRover> g=new SampleGroup<>(); g.add(new SampleTray(4));", "cannot be converted"),
        "BadBound": ("SampleGroup<String> g=new SampleGroup<>();", "not within bounds"),
        "Invariance": ("SampleGroup<HeavyRover> g=new SampleGroup<>(); SampleGroup<SampleSource> s=g;", "cannot be converted"),
        "WrongRead": ("SampleGroup<SampleSource> g=new SampleGroup<>(); HeavyRover r=g.get(0);", "cannot be converted"),
    }
    for name, (body, diagnostic) in failures.items():
        source = root / (name + ".java")
        source.write_text("public class " + name + " { void check() { " + body + " } }", encoding="utf-8")
        build([source], root / name, cp=root / "teacher", reject=diagnostic)
    unbounded = root / "MissingBound.java"
    unbounded.write_text("class MissingBound<T> { int count(T value) { return value.getSamples(); } }", encoding="utf-8")
    build([unbounded], root / "missing", reject="cannot find symbol")
    primitive = root / "Primitive.java"
    primitive.write_text("class Primitive { Box<int> value; }", encoding="utf-8")
    build([primitive], root / "primitive", cp=root / "examples", reject="unexpected type")
    print("PASS 錯誤元素、上限、賦值、取出、缺上限與 int 型態引數的六個編譯拒絕")

    unsafe = (WEEK / "ai" / "SampleGroup.java").read_text(encoding="utf-8")
    unsafe_try = (WEEK / "ai" / "UnsafeGroupTry.java").read_text(encoding="utf-8")
    bad_files = variant(root, "unsafe-src", {"SampleGroup.java": unsafe, "UnsafeGroupTry.java": unsafe_try})
    build(bad_files, root / "unsafe", warning=True)
    assert execute(root / "unsafe", "SampleGroupDemo") == DEMO
    execute(root / "unsafe", "UnsafeGroupTry", failure="ClassCastException")
    fixed = unsafe.replace("public void add(SampleSource source)", "public void add(T source)")
    fixed = fixed.replace("sources.add((T) source);", "sources.add(source);")
    repaired = variant(root, "fixed-src", {"SampleGroup.java": fixed})
    build(repaired, root / "fixed")
    assert execute(root / "fixed", "SampleGroupDemo") == DEMO
    assert execute(root / "fixed", "SampleGroupCheck").endswith("SampleGroupCheck 25 項通過")
    illegal = root / "fixed-src" / "UnsafeGroupTry.java"
    illegal.write_text(unsafe_try, encoding="utf-8")
    build([illegal], root / "fixed-negative", cp=root / "fixed", reject="cannot be converted")
    print("PASS AI 警告／執行失敗、最小修正、相同非法呼叫編譯拒絕與合法入口重測")

    # 作業與主線有同名類別，整組替換並獨立編譯，不混入兩個 SampleGroup。
    hw = {p.name: p.read_text(encoding="utf-8") for p in (teacher / "homework").glob("*.java")}
    files = variant(root, "homework-src", hw)
    build(files, root / "homework")
    assert execute(root / "homework", "MostSamplesDemo") == (
        "最大樣本數=9\n平手先加入=true\n最大來源能源=18\n原車狀態=18/4/0\n單筆零樣本=0\n空群組：略過最大值查詢")
    assert execute(root / "homework", "SampleGroupDemo") == DEMO
    # 作業的回傳型態反例：來源群組的最大值只保證 SampleSource，不能直接交給 SampleTray。
    returned = root / "ReturnType.java"
    returned.write_text("public class ReturnType { void check() { SampleGroup<SampleSource> g = new SampleGroup<>(); "
                        "SampleTray t = g.findMostSamples(); } }", encoding="utf-8")
    build([returned], root / "returned", cp=root / "homework", reject="cannot be converted")
    print("PASS 作業最大值、平手原物件、具體型態、零樣本、空群組呼叫前判斷、回傳型態反例與主線回歸")


def documents():
    links = 0
    for path in WEEK.rglob("*.md"):
        text = path.read_text(encoding="utf-8")
        assert "\ufffd" not in text, path
        assert len(re.findall(r"^```", text, re.M)) % 2 == 0, path
        for target in re.findall(r"\]\(([^)]+)\)", text):
            if "://" not in target and not target.startswith("#"):
                assert (path.parent / target.split("#")[0]).is_file(), (path, target)
                links += 1
    for path in WEEK.rglob("*.java"):
        declaration = re.search(r"^public (?:abstract )?(?:class|interface) (\w+)", path.read_text(encoding="utf-8"), re.M)
        assert declaration and declaration.group(1) == path.stem, path
    slides = (WEEK / "week11_slides_codex.md").read_text(encoding="utf-8")
    html = (WEEK / "week11_slides_codex.html").read_text(encoding="utf-8")
    count = len(re.findall(r"^---\s*$", slides, re.M)) - 1
    assert html.count("<svg data-marpit-svg") == count
    assert not list(WEEK.rglob("*.class"))
    print(f"PASS UTF-8、{links} 個本機連結、Java 檔名、投影片 {count} 頁一致、無 class 產物")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--code-only", action="store_true")
    args = parser.parse_args()
    for tool in (("java", "-version"), ("javac", "-version")):
        result = run(*tool)
        print((result.stdout + result.stderr).strip(), flush=True)
    with TemporaryDirectory(prefix="java-week11-") as directory:
        verify(Path(directory))
    if not args.code_only:
        documents()
    print("ALL CHECKS PASSED")
