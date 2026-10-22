"""教師工具：乾淨編譯、既有完整輸出回歸、責任邊界與反例。"""
from pathlib import Path
import re
import shutil
import subprocess
import tempfile
from urllib.parse import unquote

BASE = Path(__file__).resolve().parents[1]


def run(args, success=True):
    p = subprocess.run(list(map(str, args)), capture_output=True, text=True,
                       encoding="utf-8", errors="replace", timeout=45)
    if (p.returncode == 0) != success:
        raise AssertionError(f"Unexpected exit {p.returncode}: {args}\n{p.stdout}\n{p.stderr}")
    return (p.stdout + p.stderr).replace("\r\n", "\n")


def compile_to(files, out):
    out.mkdir(parents=True, exist_ok=True)
    run(["javac", "-encoding", "UTF-8", "-Xlint:all", "-d", out, *files])


def java(out, name):
    return run(["java", "-cp", out, name])


def static_checks():
    links = 0
    for path in BASE.rglob("*"):
        if path.suffix not in {".md", ".java", ".puml", ".py"}:
            continue
        if any(part.startswith(".verify-") for part in path.parts):
            continue
        text = path.read_text(encoding="utf-8")
        assert "\ufffd" not in text, path
        assert all(line == line.rstrip() for line in text.splitlines()), path
        if path.suffix == ".md":
            fences = re.findall(r"^```", text, re.M)
            assert len(fences) % 2 == 0, path
            for target in re.findall(r"\[[^\]]+\]\(([^)]+)\)", text):
                target = unquote(target.strip("<>"))
                if "://" in target or target.startswith("#"):
                    continue
                assert (path.parent / target.split("#")[0]).exists(), (path, target)
                links += 1
        if path.suffix == ".java":
            match = re.search(r"public class (\w+)", text)
            assert match and match.group(1) == path.stem, path
    starter = (BASE / "starter/Mission.java").read_text(encoding="utf-8")
    assert re.findall(r"TODO (\d)", starter) == ["1", "2", "3"]
    if (BASE / "week06_slides_codex.html").exists():
        md = (BASE / "week06_slides_codex.md").read_text(encoding="utf-8")
        html = (BASE / "week06_slides_codex.html").read_text(encoding="utf-8")
        pages = len(re.findall(r"^---\s*$", md, re.M)) - 1
        assert len(re.findall(r'<section\b', html)) == pages
        for target in re.findall(r'<img[^>]+src="([^"]+)"', html):
            assert (BASE / unquote(target)).exists(), target
        print(f"PASS slides {pages} Markdown/HTML pages")
    pairs = {
        "step": {"MissionStep": "starter/MissionStep.java"},
        "relations": {name: f"examples/{name}.java" for name in ["Battery", "Lamp", "Charger"]},
        "mission_focus": {name: f"teacher/{name}.java" for name in ["Mission", "Rover"]},
        "samplecase_ref": {"SampleCase": "examples/SampleCase.java"},
    }
    for diagram, classes in pairs.items():
        puml = (BASE / f"diagrams/{diagram}.puml").read_text(encoding="utf-8")
        for name, relative in classes.items():
            body = re.search(r"class " + name + r" \{(.*?)\n\}", puml, re.S).group(1)
            source = (BASE / relative).read_text(encoding="utf-8")
            java_methods = {member: visibility for visibility, member in re.findall(
                r"^\s*(public|private)\s+(?:static\s+)?(?:[\w<>\[\]]+\s+)?(\w+)\([^)]*\)", source, re.M)}
            uml_methods = {member: {"+": "public", "-": "private"}[visibility]
                           for visibility, member in re.findall(r"^\s*([+-])\s+(\w+)\(", body, re.M)}
            assert java_methods == uml_methods, (diagram, name, java_methods, uml_methods)
            java_fields = {member: visibility for visibility, member in re.findall(
                r"^\s*(public|private)\s+(?:static\s+)?(?:final\s+)?[\w<>\[\]]+\s+(\w+)\s*(?:;|=)", source, re.M)}
            uml_fields = {member: {"+": "public", "-": "private"}[visibility]
                          for visibility, member in re.findall(r"^\s*([+-])\s+(\w+)\s*:", body, re.M)}
            assert java_fields == uml_fields, (diagram, name, java_fields, uml_fields)
    for source in (BASE / "diagrams").glob("*.puml"):
        svg = source.with_suffix(".svg")
        assert svg.is_file() and "Syntax Error" not in svg.read_text(encoding="utf-8"), source
    print("PASS 7 class member-name/visibility mappings and 10 SVG files")
    print(f"PASS static, local links={links}, TODO 1-3 preserved")


def main():
    print(run(["java", "-version"]).strip())
    print(run(["javac", "-version"]).strip())
    with tempfile.TemporaryDirectory(prefix=".verify-", dir=BASE) as directory:
        work = Path(directory)
        for group in ["examples", "starter", "teacher"]:
            compile_to(sorted((BASE / group).glob("*.java")), work / group)
            print("PASS compile", group)
        compile_to(sorted((BASE / "starter").glob("*.java")) + sorted((BASE / "examples").glob("*.java")), work / "student")
        print("PASS combined 11-file student src")
        expected = {
            "RelationDemo": "A=true\nB=false\nC=true\nshared=2\ncharge=true\nB again=true\nshared=1\n",
            "SampleCaseDemo": "first=true, count=2\noverflow=false, count=2\nfill=true, count=3\nunload=3\nempty=0\n",
        }
        for name, text in expected.items():
            assert java(work / "examples", name) == text, name
            print("PASS full output", name)
        rover_output = "OK/14/2/1\nSTOPPED/0\nSTOPPED/20\n"
        assert java(work / "starter", "RoverDemo") == rover_output
        starter_expected = "=== replay ===\ncounts[0..3]=[0, 0, 0, 0]\nsteps=0, ok=0, rejected=0, ignored=3\nlowest energy=N/A\nfinal energy=20, samples=0, actionCount=0 -> UNFINISHED\nbattery=100.0\n"
        assert java(work / "starter", "MissionLog") == starter_expected
        print("PASS starter safe return and supplied RoverDemo")
        for name, total in [("MissionLogCheck", "15/15 passed"), ("MissionStateCheck", "16/16 passed")]:
            output = java(work / "teacher", name)
            assert total in output, output
            print(output.strip())
        # 舊的逐字期望沿用第五週，避免只用新實作自行推導答案。
        previous = BASE.parents[1] / "week05/codex版本/teacher/MissionLogCheck.java"
        assert (BASE / "teacher/MissionLogCheck.java").read_bytes() == previous.read_bytes()
        # 完成學生 TODO 後，配上提供檔和例子仍能一起編譯。
        supplied = [p for p in (BASE / "starter").glob("*.java") if p.name != "Mission.java"]
        compile_to(supplied + [BASE / "teacher/Mission.java"] + list((BASE / "examples").glob("*.java")), work / "completed")
        assert java(work / "completed", "MissionLog") == java(work / "teacher", "MissionLog")
        print("PASS completed student setup")
        mutations = [
            ("Mission.java", "if (rover.isStopped()) { break; }", "// intentionally missing stop", "MissionLogCheck"),
            ("Mission.java", "history.add(new MissionStep(command, rover.getEnergy(), result));", 'if ("OK".equals(result)) { history.add(new MissionStep(command, rover.getEnergy(), result)); }', "MissionLogCheck"),
            ("Mission.java", "int lowest = history.get(0).getEnergy();", "int lowest = 0;", "MissionLogCheck"),
            ("Rover.java", 'if (isStopped()) { return "STOPPED"; }', "// intentionally missing protection", "MissionStateCheck"),
        ]
        for i, (filename, old, new, check) in enumerate(mutations):
            source = work / f"mutation-{i}"
            shutil.copytree(BASE / "teacher", source)
            path = source / filename
            text = path.read_text(encoding="utf-8")
            assert old in text
            path.write_text(text.replace(old, new), encoding="utf-8")
            out = work / f"mutation-out-{i}"
            compile_to(sorted(source.glob("*.java")), out)
            run(["java", "-cp", out, check], success=False)
        print("PASS four rejected mutations")
        # E3 錯誤只放副本，重現後保留正式答案。
        ai = work / "ai"
        shutil.copytree(BASE / "examples", ai)
        path = ai / "SampleCase.java"
        text = path.read_text(encoding="utf-8")
        assert "amount > getFreeSpace()" in text
        path.write_text(text.replace("amount <= 0 || amount > getFreeSpace()", "amount <= 0"), encoding="utf-8")
        compile_to(sorted(ai.glob("*.java")), work / "ai-out")
        wrong = java(work / "ai-out", "SampleCaseDemo")
        assert "overflow=true, count=4" in wrong, wrong
        assert "fill=true, count=5\nunload=5" in wrong, wrong
        print("PASS E3 counterexample: overflow=true/count=4; restored correct output already checked")
        # final 參考不是不可變物件：實際示範新增成功，重新指派編譯失敗。
        final_demo = work / "FinalDemo.java"
        final_demo.write_text('import java.util.ArrayList; public class FinalDemo { public static void main(String[] a) { final ArrayList<String> x = new ArrayList<>(); x.add("OK"); System.out.println(x.size()); } }', encoding="utf-8")
        compile_to([final_demo], work / "final")
        assert java(work / "final", "FinalDemo") == "1\n"
        final_demo.write_text(final_demo.read_text(encoding="utf-8").replace('x.add("OK");', 'x = new ArrayList<>();'), encoding="utf-8")
        run(["javac", "-encoding", "UTF-8", "-d", work / "final-bad", final_demo], success=False)
        print("PASS final reference demonstration")
    static_checks()
    print("ALL CHECKS PASSED")


if __name__ == "__main__":
    main()
