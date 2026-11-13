from pathlib import Path
import re
import subprocess
from tempfile import TemporaryDirectory

WEEK = Path(__file__).resolve().parents[1]
BASELINE = WEEK.parents[1] / "week09" / WEEK.name / "teacher"
LEGACY = ["重型探測車 樣本數=6", "樣本盒 樣本數=4", "空盒 樣本數=0"]
DETAIL = "重型探測車 樣本數=6 能源=8"
COMPLETE = [DETAIL, *LEGACY, "手動清點 樣本數=7", "手動清點 樣本數=0"]


def command(*args):
    return subprocess.run(
        args, capture_output=True, encoding="utf-8", errors="replace", timeout=45
    )


def build(output, sources, diagnostic=None):
    output.mkdir()
    result = command(
        "javac", "-J-Duser.language=en", "-J-Duser.country=US", "-encoding",
        "UTF-8", "-Xlint:all", "-d", output, *sorted(sources)
    )
    if diagnostic:
        assert result.returncode != 0 and diagnostic in result.stderr, result.stderr
        return
    assert result.returncode == 0, result.stderr


def execute(output, main):
    result = command("java", "-Dstdout.encoding=UTF-8", "-cp", output, main)
    assert result.returncode == 0, result.stderr
    return result.stdout.splitlines()


def expect(actual, expected):
    assert actual == expected, f"\nExpected: {expected}\nActual: {actual}"


def source(directory, name):
    return (directory / name).read_text(encoding="utf-8")


def variant(root, folder, sources, replacements):
    # 每個情境有自己的來源副本與 class 輸出，避免誤用前一次編譯結果。
    directory = root / folder
    directory.mkdir()
    files = {file.name: file.read_text(encoding="utf-8") for file in sources}
    files.update(replacements)
    for name, contents in files.items():
        (directory / name).write_text(contents, encoding="utf-8")
    return directory.glob("*.java")


def verify(root):
    examples = WEEK / "examples"
    starter = WEEK / "starter"
    teacher = WEEK / "teacher"
    report = source(teacher, "SampleReport.java")
    example = source(examples, "MemberOverloadDemo.java")

    build(root / "examples", examples.glob("*.java"))
    expect(execute(root / "examples", "MemberOverloadDemo"),
           ["VIP 會員", "VIP 會員 點數=30", "VIP 會員 活動=迎新"])
    build(root / "starter", starter.glob("*.java"))
    expect(execute(root / "starter", "SampleReportDemo"), LEGACY)
    expect(execute(root / "starter", "SampleOverloadDemo"), ["尚未完成"])

    build(root / "teacher", teacher.glob("*.java"))
    expect(execute(root / "teacher", "SampleReportDemo"), LEGACY)
    expect(execute(root / "teacher", "SampleOverloadDemo"), COMPLETE)

    build(root / "week09", BASELINE.glob("*.java"))
    for main in ("MissionLog", "CompareMissions", "HeavyRoverTry"):
        expected = execute(root / "week09", main)
        expect(execute(root / "teacher", main), expected)
        expect(execute(root / "starter", main), expected)
    for main, summary in (("MissionLogCheck", "15/15 項通過"),
                          ("MissionStateCheck", "16/16 項通過"),
                          ("PolymorphismCheck", "PolymorphismCheck 51 項通過")):
        expect(execute(root / "teacher", main)[-1], summary)
    print("第 9 週三個入口及任務回歸 15／16／51 項通過")

    # 只先加入 TODO 1，重現原呼叫改選簽名。再替換三個 TODO 檔驗證完成路徑。
    detailed = report.split("    public static void print(String label, Rover rover)")[1]
    detailed = detailed.split("    public static void print(String label, int samples)")[0]
    first_step = source(starter, "SampleReport.java").rstrip().removesuffix("}")
    first_step += "    public static void print(String label, Rover rover)" + detailed + "}\n"
    build(root / "changed", variant(root, "add-overload", starter.glob("*.java"),
                                    {"SampleReport.java": first_step}))
    expect(execute(root / "changed", "SampleReportDemo"), [DETAIL, *LEGACY[1:]])
    todo_names = ("SampleReport.java", "SampleReportDemo.java", "SampleOverloadDemo.java")
    completed = {name: source(teacher, name) for name in todo_names}
    build(root / "complete", variant(root, "student-complete", starter.glob("*.java"), completed))
    expect(execute(root / "complete", "SampleReportDemo"), LEGACY)
    expect(execute(root / "complete", "SampleOverloadDemo"), COMPLETE)

    ai = source(WEEK / "ai", "SampleReport.java")
    branch = re.compile(r"        if \(source instanceof Rover\) \{.*?\n        \}\n", re.S)
    fixed, count = branch.subn("", ai)
    assert count == 1
    for name, contents, expected in (("bad", ai, [DETAIL, DETAIL, *COMPLETE[2:]]),
                                     ("fixed", fixed, COMPLETE)):
        build(root / f"ai-{name}", variant(root, f"ai-{name}-src", starter.glob("*.java"),
                                           {**completed, "SampleReport.java": contents}))
        expect(execute(root / f"ai-{name}", "SampleOverloadDemo"), expected)

    homework = teacher / "homework"
    replacements = {file.name: file.read_text(encoding="utf-8") for file in homework.glob("*.java")}
    build(root / "homework", variant(root, "quick-src", teacher.glob("*.java"), replacements))
    expect(execute(root / "homework", "QuickSampleDemo"), ["快速查詢 樣本數=6", "快速查詢 樣本數=0"])
    expect(execute(root / "homework", "SampleReportDemo"), LEGACY)
    expect(execute(root / "homework", "SampleOverloadDemo"), COMPLETE)
    expect(execute(root / "homework", "CompareMissions"), execute(root / "teacher", "CompareMissions"))

    checks = """
class ReportStateCheck {
    public static void main(String[] args) {
        Rover rover = new HeavyRover(20, 0);
        SampleSource source = rover;
        Mission mission = new Mission(rover);
        mission.run(new int[] {1, 1, 2});
        MissionStep first = mission.getStep(0);
        MissionStep second = mission.getStep(1);
        SampleReport.print("重型探測車", rover);
        SampleReport.print("重型探測車", source);
        SampleReport.print("重型探測車", source);
        SampleReport.print("改數量的盒子", new SampleTray(9));
        if (rover.getEnergy() != 8 || rover.getSamples() != 6 || rover.getActionCount() != 2
                || mission.getStepCount() != 2 || mission.getStep(0) != first
                || mission.getStep(1) != second || !mission.getOutcome().equals("任務成功")) {
            throw new AssertionError("報表讀取改變任務狀態");
        }
    }
}
"""
    build(root / "state", variant(root, "state-src", teacher.glob("*.java"),
                                  {"ReportStateCheck.java": checks}))
    expect(execute(root / "state", "ReportStateCheck"),
           [DETAIL, LEGACY[0], LEGACY[0], "改數量的盒子 樣本數=9"])

    signature = "    public String describe(int points) {"
    experiments = (
        ("override", example.replace(signature, "    @Override\n" + signature),
         "does not override"),
        ("return-type", example.replace(signature, "    public int describe(int points) { return points; }\n" + signature),
         "already defined"),
    )
    for name, contents, diagnostic in experiments:
        build(root / name, variant(root, name + "-src", examples.glob("*.java"),
                                    {"MemberOverloadDemo.java": contents}), diagnostic)
    print("過載、舊入口三階段、AI 重測、作業、狀態與編譯反例通過")


def check_documents():
    for file in WEEK.rglob("*.md"):
        text = file.read_text(encoding="utf-8")
        assert len(re.findall(r"^```", text, re.M)) % 2 == 0, file
        for target in re.findall(r"\]\(([^)]+)\)", text):
            if "://" not in target and not target.startswith("#"):
                assert (file.parent / target.split("#", 1)[0]).is_file(), (file, target)
    for file in WEEK.rglob("*.java"):
        class_name = re.search(r"^public (?:abstract )?(?:class|interface) (\w+)",
                               file.read_text(encoding="utf-8"), re.M)
        if class_name:
            expect(class_name.group(1), file.stem)
    slides = source(WEEK, "week10_slides_codex.md")
    html = source(WEEK, "week10_slides_codex.html")
    expect(html.count('<svg data-marpit-svg'), len(re.findall(r"^---\s*$", slides, re.M)) - 1)
    print("UTF-8、文件連結、Java 檔名及投影片頁數通過")


if __name__ == "__main__":
    print(command("javac", "-version").stdout.strip(), flush=True)
    with TemporaryDirectory(prefix="java-week10-") as temporary:
        verify(Path(temporary))
    check_documents()
