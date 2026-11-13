"""教師維護工具：隔離編譯、完整輸出、編譯反例與設計錯誤變異。只需現有JDK及Python。"""
from pathlib import Path
import subprocess
import tempfile
import re

BASE = Path(__file__).resolve().parents[1]


def run(args, ok=True):
    result = subprocess.run(args, capture_output=True, text=True, encoding="utf-8",
                            errors="replace", timeout=30)
    if (result.returncode == 0) != ok:
        raise AssertionError(f"Unexpected exit {result.returncode}: {args}\n"
                             f"{result.stdout}\n{result.stderr}")
    return result.stdout.replace("\r\n", "\n")


def compile_to(source, output, extras=()):
    output.mkdir(parents=True, exist_ok=True)
    files = sorted(source.glob("*.java")) + list(extras)
    run(["javac", "-encoding", "UTF-8", "-Xlint:all", "-d", str(output),
         *map(str, files)])


def execute(output, name):
    return run(["java", "-Dstdout.encoding=UTF-8", "-cp", str(output), name])


def main():
    version = subprocess.run(["java", "-version"], capture_output=True, text=True,
                             encoding="utf-8", errors="replace", check=True, timeout=30)
    print((version.stdout + version.stderr).strip())
    print(run(["javac", "-version"]).strip())
    with tempfile.TemporaryDirectory(prefix=".verify-", dir=BASE) as temporary:
        work = Path(temporary)
        for name in ("examples", "starter", "teacher"):
            compile_to(BASE / name, work / name)
            print("PASS compile", name)
        # 同學生src配置一起編譯，確認輔助範例不與作業類別衝突。
        compile_to(BASE / "starter", work / "student-src", (BASE / "examples").glob("*.java"))
        print("PASS compile combined student src")
        expected = {
            "CreateStepDemo": "第一次=1/14\n第二次=2/12\n同一個物件=false\n",
            "BatteryDemo": "探索=false, 能源=5\n充電=true, 能源=10\n"
                           "探索=true, 能源=4\n充至上限=true, 能源=20\n已充滿=false, 能源=20\n",
            "ReferenceDemo": "建立別名後=14/20\n同一個參考=true\n扣除能源後=8\n"
                             "方法內新物件的能源=1\n重新指定參考後=8\n",
            "SnapshotDemo": "指令=1, 能源=14\n指令=3, 能源=19\n目前能源=19\n"
                            "各自獨立的紀錄=true\n",
        }
        for name, text in expected.items():
            actual = execute(work / "examples", name)
            assert actual == text, (name, actual)
            print("PASS full example output", name)
        starter = execute(work / "starter", "MissionLog")
        assert starter == ("=== 任務回放 ===\n指令次數[0..3]=[0, 0, 0, 0]\n"
                           "步數=0, 成功=0, 拒絕=0, 略過=3\n最低能源=無紀錄\n"
                           "最終能源=6, 樣本數=5, 行動次數=3 -> 任務成功\n電量百分比=30.0\n"), starter
        print("PASS starter safe exit with unfinished log")
        for name in ("MissionStepCheck", "MissionLogCheck", "SampleCaseCheck"):
            print(execute(work / "teacher", name), end="")
        for label, statement, fragment in (
            ("private access", "StepCard s=new StepCard(1,14); s.energy=99;", "private"),
            ("no implicit constructor", "StepCard s=new StepCard();", "constructor"),
        ):
            bad = work / "CompileFailure.java"
            bad.write_text("public class CompileFailure { public static void main(String[] args) {"
                           + statement + "}}", encoding="utf-8")
            failure = subprocess.run(["javac", "-J-Duser.language=en", "-J-Duser.country=US",
                                      "-encoding", "UTF-8", "-cp", str(work / "examples"),
                                      "-d", str(work / "negative"), str(bad)],
                                     capture_output=True, text=True, encoding="utf-8",
                                     errors="replace", timeout=30)
            assert failure.returncode != 0 and fragment in failure.stderr.lower(), failure.stderr
            print("PASS expected compile failure", label)
        # C1對照：同套件欄位省略private可直接寫，不能誤套C#預設規則。
        access = work / "package-access"
        access.mkdir()
        step_card = (BASE / "examples/StepCard.java").read_text(encoding="utf-8")
        (access / "StepCard.java").write_text(
            step_card.replace("private int energy;", "int energy;"), encoding="utf-8")
        (access / "AccessDemo.java").write_text(
            "public class AccessDemo { public static void main(String[] args) {"
            "StepCard first=new StepCard(1,14); first.energy=99;"
            "System.out.println(first.getEnergy()); }}", encoding="utf-8")
        compile_to(access, access / "out")
        assert execute(access / "out", "AccessDemo") == "99\n"
        print("PASS package access permits same-package write without private")
        # 直接取用講義F3選項3程式，避免教材反例與工具中的另一份程式失去對齊。
        lab = (BASE / "student_lab.md").read_text(encoding="utf-8")
        trap_section = lab.split("### F3 ", 1)[1].split("### F4 ", 1)[0]
        trap_body = re.search(r"```java\n(.*?)\n```", trap_section, re.DOTALL).group(1)
        trap = work / "reference-trap"
        trap.mkdir()
        (trap / "SnapshotDemo.java").write_text(
            "import java.util.ArrayList;\npublic class SnapshotDemo {\n"
            "public static void main(String[] args) {\n" + trap_body + "\n}}\n", encoding="utf-8")
        compile_to(trap, trap / "out", [BASE / "examples/RoverBattery.java"])
        assert execute(trap / "out", "SnapshotDemo") == "19/19\n同一個物件=true\n"
        print("PASS reproduced reference trap; original snapshot output already checked as 14,19")
        # F2教師參考的偵測力：超量仍收進去，必須被檢查器拒絕。
        sample = work / "sample-mutation"
        sample.mkdir()
        sample_source = (BASE / "teacher/SampleCase.java").read_text(encoding="utf-8")
        (sample / "SampleCase.java").write_text(
            sample_source.replace("amount <= 0 || amount > getFreeSpace()", "amount <= 0"),
            encoding="utf-8")
        compile_to(sample, sample / "out", [BASE / "teacher/SampleCaseCheck.java"])
        run(["java", "-Dstdout.encoding=UTF-8", "-cp", str(sample / "out"), "SampleCaseCheck"], ok=False)
        print("PASS detected sample case batch overflow mutation")
        original = (BASE / "teacher/MissionStep.java").read_text(encoding="utf-8")
        log = (BASE / "teacher/MissionLog.java").read_text(encoding="utf-8")
        mutations = [
            ("shared static fields", original.replace("private int", "private static int")
             .replace("private String", "private static String"), log, "MissionStepCheck"),
            ("reference equality", original.replace('"成功".equals(result)', 'result == "成功"'),
             log, "MissionStepCheck"),
            ("lost constructor assignment", original.replace("this.energy = energy;", "energy = energy;"),
             log, "MissionStepCheck"),
            ("record only successful", original, log.replace(
                "history.add(new MissionStep(command, energy, result));",
                'if ("成功".equals(result)) { history.add(new MissionStep(command, energy, result)); }'),
             "MissionLogCheck"),
        ]
        for index, (label, step_source, log_source, checker) in enumerate(mutations):
            source = work / f"mutation-{index}"
            source.mkdir()
            (source / "MissionStep.java").write_text(step_source, encoding="utf-8")
            (source / "MissionLog.java").write_text(log_source, encoding="utf-8")
            for check in ("MissionStepCheck", "MissionLogCheck"):
                (source / f"{check}.java").write_text(
                    (BASE / f"teacher/{check}.java").read_text(encoding="utf-8"), encoding="utf-8")
            compile_to(source, source / "out")
            run(["java", "-Dstdout.encoding=UTF-8", "-cp", str(source / "out"), checker], ok=False)
            print("PASS detected mutation", label)
        # 固定狀態入口：CLI整數為契約，非整數/溢位例外不在本週範圍。
        for args, ending in [
            (["2", "4", "2"], "任務成功"),
            (["0", "0", "3", "1"], "能源耗盡"),
            (["20", "0"], "任務未完成"),
        ]:
            actual = run(["java", "-Dstdout.encoding=UTF-8", "-cp", str(work / "teacher"), "FixedScriptMission", *args])
            assert " -> " + ending + "\n" in actual, actual
        print("PASS fixed-state CLI entry")
    print("ALL CHECKS PASSED; temporary outputs removed")


if __name__ == "__main__":
    main()
