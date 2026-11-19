"""教師用：在新的輸出目錄檢查第 9 週程式、編譯反例與教材連結。"""
import os
from pathlib import Path
import re
import shutil
import subprocess
from tempfile import TemporaryDirectory


ROOT = Path(__file__).resolve().parents[1]
WEEK8 = ROOT.parents[1] / "week08" / "codex版本"
REPORT = "重型探測車 樣本數=6\n樣本盒 樣本數=4\n空盒 樣本數=0\n"
# 第 8 週 TODO 4 沿用：重型車覆寫 execute，腳本指令 4 交給 drill；一般車仍回無效指令。
DRILL_PROBE = """
public class DrillProbe {
    public static void main(String[] args) {
        HeavyRover heavy = new HeavyRover(20, 0);
        Mission h = new Mission(heavy);
        h.run(new int[] {4, 1});
        Rover standard = new StandardRover(20, 0);
        Mission s = new Mission(standard);
        s.run(new int[] {4, 1});
        System.out.println(h.getStep(0).getResult() + " " + heavy.getEnergy() + "/" + heavy.getSamples()
                + "/" + heavy.getActionCount() + "/" + heavy.getDrillCount() + " " + h.getOutcome()
                + " " + java.util.Arrays.toString(h.countCommands()));
        System.out.println(s.getStep(0).getResult() + " " + standard.getEnergy() + "/"
                + standard.getSamples() + "/" + standard.getActionCount() + " " + s.getOutcome());
        System.out.println(new HeavyRover(20, 5).execute(4) + " " + new HeavyRover(7, 0).execute(4)
                + " " + new HeavyRover(20, 0).execute(2));
    }
}
"""
DRILL = '成功 6/6/2/1 任務成功 [0, 1, 0, 0]\n無效指令 14/2/1 任務未完成\n任務已停止 能源不足 成功\n'
TODO_FILES = (
    "Rover.java", "StandardRover.java", "SampleSource.java", "SampleTray.java",
    "SampleReport.java", "SampleReportDemo.java",
)


def run(*args, error=None):
    result = subprocess.run(
        args, capture_output=True, text=True, encoding="utf-8", errors="replace", timeout=45,
    )
    if error is not None:
        assert result.returncode != 0 and error in result.stderr, result
    else:
        assert result.returncode == 0 and not result.stderr, result
    return result.stdout


def compile_java(source, output, classpath=None, error=None):
    flags = ["-cp", str(classpath)] if classpath else []
    run(
        "javac", "-J-Duser.language=en", "-J-Duser.country=US",
        "-encoding", "UTF-8", "-Xlint:all", "-d", str(output), *flags,
        *(str(file) for file in sorted(source.glob("*.java"))), error=error,
    )


def execute(output, main):
    return run("java", "-Dstdout.encoding=UTF-8", "-cp", str(output), main)


def replace(file, before, after):
    text = file.read_text(encoding="utf-8")
    assert before in text, file
    file.write_text(text.replace(before, after), encoding="utf-8")


def verify_output(temp):
    for part in ("examples", "starter", "teacher"):
        compile_java(ROOT / part, temp / part)
    compile_java(WEEK8 / "teacher", temp / "baseline")
    teacher = temp / "teacher"
    starter = temp / "starter"

    for main in ("MissionLog", "CompareMissions", "HeavyRoverTry"):
        baseline = execute(temp / "baseline", main)
        assert execute(starter, main) == baseline == execute(teacher, main), main
    notes = (ROOT / "teacher_notes.md").read_text(encoding="utf-8")
    comparison = execute(teacher, "CompareMissions")
    assert f"```text\n{comparison}```" in notes
    assert execute(starter, "SampleReportDemo") == "尚未完成\n"
    assert execute(teacher, "SampleReportDemo") == REPORT
    assert f"```text\n{REPORT}```" in notes
    assert execute(teacher, "StepListenerDemo") == "能源偏低，步驟=2 能源=2\n"
    assert execute(temp / "examples", "ParcelDemo") == "書籍 3公斤 運費=90元\n藥品 3公斤 運費=135元\n"
    assert execute(temp / "examples", "ScaleDemo") == (
        "行李箱 重量=13公斤\n貓 重量=5公斤\n咪咪：喵～\n同一隻貓用 Weighable 變數讀取：5公斤\n"
    )
    assert execute(temp / "examples", "FixtureDemo") == "測試資料重量=2\n" * 2
    for main, summary in (
        ("MissionLogCheck", "15/15 項通過"),
        ("MissionStateCheck", "16/16 項通過"),
        ("PolymorphismCheck", "PolymorphismCheck 51 項通過"),
    ):
        assert execute(teacher, main).splitlines()[-1] == summary, main
    print("PASS 分開編譯、前週完整輸出、15/16/51 項回歸、報表、三個範例及通知示範")

    transfer = temp / "transfer"
    compile_java(ROOT / "teacher" / "transfer", transfer, classpath=teacher)
    paths = os.pathsep.join((str(teacher), str(transfer)))
    assert execute(paths, "SampleBatchDemo") == "混合樣本 樣本數=7\n空盒 樣本數=0\n再次讀取 樣本數=7\n"
    print("PASS 遷移作業參考解答")

    student = shutil.copytree(ROOT / "starter", temp / "student")
    for name in TODO_FILES:
        shutil.copyfile(ROOT / "teacher" / name, student / name)
    compile_java(student, temp / "student-out")
    for main in ("MissionLog", "CompareMissions", "HeavyRoverTry", "SampleReportDemo"):
        assert execute(temp / "student-out", main) == execute(teacher, main), main
    print("PASS 只替換 TODO 檔即可完成學生版")
    return student


def verify_report_contract(student, temp):
    probe = temp / "probe"
    probe.mkdir()
    (probe / "ReportProbe.java").write_text(
        """
public class ReportProbe {
    public static void main(String[] args) {
        Rover rover = new HeavyRover(20, 0);
        Mission mission = new Mission(rover);
        mission.run(new int[] {1, 1, 2});
        SampleSource tray = new SampleTray(9);
        SampleReport.print("自選樣本盒", tray);
        SampleReport.print("再次讀取", tray);
        SampleReport.print("重型探測車", rover);
        SampleReport.print("重型探測車", rover);
        System.out.println(rover.getEnergy() + "/" + rover.getSamples()
                + "/" + rover.getActionCount() + "/" + mission.getStepCount());

        for (Rover low : new Rover[] {new StandardRover(5, 0), new HeavyRover(5, 0)}) {
            Mission rejected = new Mission(low);
            rejected.run(new int[] {1});
            System.out.println(rejected.getStep(0).getResult() + " " + low.getEnergy()
                    + "/" + low.getSamples() + "/" + low.getActionCount()
                    + " " + rejected.getOutcome());
        }
    }
}
""",
        encoding="utf-8",
    )
    student_out = temp / "student-out"
    compile_java(probe, probe / "out", classpath=student_out)
    paths = os.pathsep.join((str(student_out), str(probe / "out")))
    assert execute(paths, "ReportProbe") == (
        "自選樣本盒 樣本數=9\n再次讀取 樣本數=9\n重型探測車 樣本數=6\n重型探測車 樣本數=6\n8/6/2/2\n"
        + "能源不足 5/0/0 任務未完成\n" * 2
    )
    print("PASS 自選數量、重讀不改狀態、兩車能源不足")

    drill = temp / "drill-probe"
    drill.mkdir()
    (drill / "DrillProbe.java").write_text(DRILL_PROBE, encoding="utf-8")
    compile_java(drill, drill / "out", classpath=student_out)
    assert execute(os.pathsep.join((str(student_out), str(drill / "out"))), "DrillProbe") == DRILL
    print("PASS 沿用第 8 週指令 4：重型車經 execute 鑽探、一般車拒絕、停止與其他指令不變")


def verify_experiments(student, temp):
    for index, (name, before, after, error) in enumerate((
        ("CompareMissions.java", "new StandardRover(20, 0)", "new Rover(20, 0)",
         "Rover is abstract; cannot be instantiated"),
        ("StandardRover.java", "    @Override\n    protected int exploreYield() {\n        return 2;\n    }\n", "",
         "does not override abstract method exploreYield() in Rover"),
        ("SampleTray.java", "public int getSamples()", "int getSamples()",
         "attempting to assign weaker access privileges; was public"),
    ), 1):
        experiment = shutil.copytree(student, temp / f"experiment-{index}")
        replace(experiment / name, before, after)
        compile_java(experiment, experiment / "out", error=error)

    for index, (name, line, error) in enumerate((
        ("ParcelDemo.java", 'Parcel unknown = new Parcel("未分類", 1);',
         "Parcel is abstract; cannot be instantiated"),
        ("ScaleDemo.java", "System.out.println(item.meow());", "cannot find symbol"),
        ("ScaleDemo.java", 'weigh("包裹", new NormalParcel("書籍", 3));',
         "NormalParcel cannot be converted to Weighable"),
    ), 1):
        example = shutil.copytree(ROOT / "examples", temp / f"example-experiment-{index}")
        replace(example / name, "// " + line, line)
        compile_java(example, example / "out", error=error)

    ai = shutil.copytree(student, temp / "ai")
    shutil.copyfile(ROOT / "ai" / "SampleReport.java", ai / "SampleReport.java")
    compile_java(ai, ai / "out-broken", error="SampleTray cannot be converted to Rover")
    main = ai / "SampleReportDemo.java"
    original = main.read_text(encoding="utf-8")
    replace(main, '        SampleReport.print("樣本盒", tray);\n'
                  '        SampleReport.print("空盒", empty);\n', "")
    compile_java(ai, ai / "out-heavy-only")
    assert execute(ai / "out-heavy-only", "SampleReportDemo") == "重型探測車 樣本數=6\n"
    main.write_text(original, encoding="utf-8")
    replace(ai / "SampleReport.java", "Rover source", "SampleSource source")
    compile_java(ai, ai / "out-fixed")
    assert execute(ai / "out-fixed", "SampleReportDemo") == REPORT
    print("PASS 三個隔離編譯診斷、三個範例編譯實驗與模擬 AI 的失敗、單車對照、修正重測")


def verify_files():
    for name in ("Mission.java", "MissionStep.java"):
        baseline = (WEEK8 / "teacher" / name).read_bytes()
        for part in ("starter", "teacher"):
            assert (ROOT / part / name).read_bytes() == baseline, name
    for file in ROOT.rglob("*.md"):
        text = file.read_text(encoding="utf-8")
        assert "\ufffd" not in text, file
        assert len(re.findall(r"^\s*```", text, re.M)) % 2 == 0, file
        for target in re.findall(r"!?\[[^\]\n]*\]\(([^)]+)\)", text):
            if "://" not in target:
                assert (file.parent / target).exists(), (file, target)
    for file in ROOT.rglob("*.java"):
        text = file.read_text(encoding="utf-8")
        declaration = rf"^public (?:abstract )?(?:class|interface) {file.stem}\b"
        assert re.search(declaration, text, re.M), file
    assert len(list((ROOT / "starter").glob("*.java"))) == 12
    print("PASS 共用檔案、檔名、UTF-8、Markdown 連結與程式區塊")


if __name__ == "__main__":
    with TemporaryDirectory(prefix="java-week09-") as folder:
        temp = Path(folder)
        student = verify_output(temp)
        verify_report_contract(student, temp)
        verify_experiments(student, temp)
    verify_files()
