"""教師維護工具：乾淨編譯、舊輸出、繼承案例、AI反例與文件檢查。"""
from pathlib import Path
import re
import shutil
import subprocess
import tempfile
from urllib.parse import unquote

BASE = Path(__file__).resolve().parents[1]
PREVIOUS = BASE.parents[1] / "week06/codex版本"


def run(args, success=True):
    result = subprocess.run(list(map(str, args)), capture_output=True, text=True,
                            encoding="utf-8", errors="replace", timeout=45)
    output = (result.stdout + result.stderr).replace("\r\n", "\n")
    if (result.returncode == 0) != success:
        raise AssertionError(f"Unexpected exit {result.returncode}: {args}\n{output}")
    return output


def compile_to(files, out):
    out.mkdir(parents=True, exist_ok=True)
    output = run(["javac", "-encoding", "UTF-8", "-Xlint:all", "-d", out, *files])
    assert not output.strip(), output


def java(out, name):
    return run(["java", "-Dstdout.encoding=UTF-8", "-cp", out, name])


def static_checks():
    links = 0
    for p in BASE.rglob("*"):
        if p.suffix not in {".java", ".md", ".py", ".puml", ".cjs"}:
            continue
        s = p.read_text(encoding="utf-8")
        assert "\ufffd" not in s, p
        assert all(line == line.rstrip() for line in s.splitlines()), p
        if p.suffix == ".java":
            match = re.search(r"public class (\w+)", s)
            assert match and match.group(1) == p.stem, p
        if p.suffix == ".md":
            assert len(re.findall(r"^```", s, re.M)) % 2 == 0, p
            for target in re.findall(r"\[[^\]]+\]\(([^)]+)\)", s):
                target = unquote(target.strip("<>"))
                if "://" in target or target.startswith("#"):
                    continue
                assert (p.parent / target.split("#")[0]).exists(), (p, target)
                links += 1
    for name in ["Mission.java", "MissionStep.java", "MissionLog.java"]:
        for group in ["starter", "teacher"]:
            assert (BASE / group / name).read_bytes() == (PREVIOUS / "teacher" / name).read_bytes(), name
    for name in ["MissionLogCheck.java", "MissionStateCheck.java", "FixedScriptMission.java"]:
        assert (BASE / "teacher" / name).read_bytes() == (PREVIOUS / "teacher" / name).read_bytes(), name
    assert re.findall(r"TODO (\d)", (BASE / "starter/Rover.java").read_text(encoding="utf-8")) == ["1"]
    assert re.findall(r"TODO (\d)", (BASE / "starter/HeavyRover.java").read_text(encoding="utf-8")) == ["2", "3"]
    for p in (BASE / "diagrams").glob("*.puml"):
        assert p.with_suffix(".svg").is_file(), p
        assert "Syntax Error" not in p.with_suffix(".svg").read_text(encoding="utf-8"), p
    md = (BASE / "week07_slides_codex.md").read_text(encoding="utf-8")
    html = (BASE / "week07_slides_codex.html").read_text(encoding="utf-8")
    pages = len(re.findall(r"^---\s*$", md, re.M)) - 1
    assert len(re.findall(r"<section\b", html)) == pages
    for target in re.findall(r'<img[^>]+src="([^"]+)"', html):
        assert (BASE / unquote(target)).exists(), target
    print(f"PASS static links={links}, slides={pages}, preserved contracts and TODO 1-3")


def main():
    print(run(["java", "-version"]).strip())
    print(run(["javac", "-version"]).strip())
    with tempfile.TemporaryDirectory(prefix=".verify-", dir=BASE) as directory:
        work = Path(directory)
        for group in ["examples", "starter", "teacher"]:
            compile_to(sorted((BASE / group).glob("*.java")), work / group)
            print("PASS compile", group)
        example_expected = ("Member 建構子: Amy\nMember 建構子: Bob\nVipMember 建構子: Bob\n"
                            "Amy 的點數=12\nBob 的點數=28\nBob 的獎勵次數=1\n")
        assert java(work / "examples", "MemberDemo") == example_expected
        print("PASS MemberDemo full output")
        for name, expected in [("MissionLogCheck", "15/15 項通過"), ("MissionStateCheck", "16/16 項通過"),
                               ("InheritanceCheck", "項繼承檢查通過")]:
            output = java(work / "teacher", name)
            assert expected in output, output
            print(output.strip())
        supplied = list((BASE / "starter").glob("*.java")) + list((BASE / "examples").glob("*.java"))
        compile_to(supplied, work / "student")
        assert java(work / "student", "MemberDemo") == example_expected
        assert java(work / "student", "MissionLog") == java(work / "teacher", "MissionLog")
        assert "不符" in java(work / "student", "HeavyRoverTry")
        print("PASS initial nine-file src, old behavior and unfinished drill")
        complete = [p for p in supplied if p.name not in {"Rover.java", "HeavyRover.java"}]
        complete += [BASE / "teacher/Rover.java", BASE / "teacher/HeavyRover.java"]
        compile_to(complete, work / "complete")
        output = java(work / "complete", "HeavyRoverTry")
        assert output.count("） 相符") == 15 and "不符" not in output, output
        print("PASS completed student setup, fifteen drill checks")
        compile_to([BASE / "teacher/Rover.java", *sorted((BASE / "teacher/transfer").glob("*.java"))], work / "transfer")
        cargo_expected = "成功/2\n已拒絕/2\n成功/3\n已拒絕/3\n20/0/0\n任務已停止/0\n任務已停止/0\n"
        assert java(work / "transfer", "CargoRoverTry") == cargo_expected
        # 加上預先決定的負數與超量邊界，不只重跑參考入口。
        probe = work / "CargoProbe.java"
        probe.write_text('public class CargoProbe { public static void main(String[] a) { CargoRover c = new CargoRover(20, 0); if (!"已拒絕".equals(c.load(-1)) || c.getCargo() != 0 || !"已拒絕".equals(c.load(4)) || c.getCargo() != 0 || !"成功".equals(c.load(3)) || c.getCargo() != 3 || !"已拒絕".equals(c.load(1)) || c.getCargo() != 3) { throw new AssertionError("cargo boundary"); } } }', encoding="utf-8")
        compile_to([BASE / "teacher/Rover.java", BASE / "teacher/transfer/CargoRover.java", probe], work / "cargo-probe")
        java(work / "cargo-probe", "CargoProbe")
        print("PASS Cargo transfer full output and independent boundary probe")
        # 讓AI錯誤版與正式版在不同輸出位置。
        compile_to([BASE / "teacher/Rover.java", BASE / "ai/HeavyRover.java", BASE / "starter/HeavyRoverTry.java"], work / "ai")
        wrong = java(work / "ai", "HeavyRoverTry")
        assert "鑽探後: 20/0/0/1" in wrong and "不符" in wrong
        assert "能源耗盡後無法充電: 成功" in wrong
        print("PASS AI counterexample reproduced; 不符 lines:")
        print("\n".join(line for line in wrong.splitlines() if line.endswith("不符")))
        assert "不符" not in java(work / "complete", "HeavyRoverTry")
        print("PASS restored correct implementation")
        # 隔離實驗：建構與private錯誤，從完整範例每次只改一處。
        for label, old, new, diagnostic in [
            ("missing-super", "super(name);", "// missing super", "constructor Member"),
            ("private-access", "addPoints(20);", "points += 20;", "private access"),
        ]:
            sources = work / label
            shutil.copytree(BASE / "examples", sources)
            p = sources / "VipMember.java"
            text = p.read_text(encoding="utf-8")
            assert old in text
            p.write_text(text.replace(old, new), encoding="utf-8")
            errors = run(["javac", "-encoding", "UTF-8", "-d", work / (label + "-out"), *sources.glob("*.java")], success=False)
            assert diagnostic in errors, errors
            print("PASS expected compiler rejection", label)
        protected_sources = work / "protected-access"
        shutil.copytree(BASE / "examples", protected_sources)
        p = protected_sources / "MemberDemo.java"
        p.write_text(p.read_text(encoding="utf-8").replace("amy.buy(120);", "bob.addPoints(5);\n        amy.buy(120);"), encoding="utf-8")
        compile_to(sorted(protected_sources.glob("*.java")), work / "protected-out")
        assert "Bob 的點數=33" in java(work / "protected-out", "MemberDemo")
        print("PASS same-package protected access")
        mutations = [
            ("HeavyRover.java", 'if (isStopped()) { return "任務已停止"; }', "// missing stop"),
            ("HeavyRover.java", "        countAction();", "        // missing action count"),
            ("HeavyRover.java", "private int drillCount;", "private int drillCount;\n    private int energy;"),
            ("Rover.java", "protected boolean spendEnergy", "public boolean spendEnergy"),
            ("Rover.java", "if (amount <= 0 || amount > energy) { return false; }", "if (amount <= 0) { return false; }"),
        ]
        for i, (filename, old, new) in enumerate(mutations):
            sources = work / f"mutation-{i}"
            shutil.copytree(BASE / "teacher", sources)
            p = sources / filename
            text = p.read_text(encoding="utf-8")
            assert old in text
            p.write_text(text.replace(old, new), encoding="utf-8")
            compile_to(sorted(sources.glob("*.java")), work / f"mutant-out-{i}")
            run(["java", "-Dstdout.encoding=UTF-8", "-cp", work / f"mutant-out-{i}", "InheritanceCheck"], success=False)
        print("PASS five detected behavior/design mutations")
    static_checks()
    print("ALL CHECKS PASSED")


if __name__ == "__main__":
    main()
