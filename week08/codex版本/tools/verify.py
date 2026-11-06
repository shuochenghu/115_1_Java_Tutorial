"""第八週教師工具：獨立編譯、完整輸出、多型案例、錯誤實驗及文件核對。"""
from pathlib import Path
import argparse
import re
import shutil
import subprocess
import tempfile
from urllib.parse import unquote

BASE = Path(__file__).resolve().parents[1]
OLD = BASE.parents[1] / 'week07/codex版本'


def run(args, success=True):
    result = subprocess.run(list(map(str, args)), capture_output=True, text=True,
                            encoding='utf-8', errors='replace', timeout=60)
    output = (result.stdout + result.stderr).replace('\r\n', '\n')
    assert (result.returncode == 0) == success, (args, result.returncode, output)
    return output


def compile_to(files, out, success=True):
    out.mkdir(parents=True, exist_ok=True)
    output = run(['javac', '-encoding', 'UTF-8', '-Xlint:all', '-d', out, *files], success)
    if success:
        assert not output.strip(), output
    return output


def java(out, name):
    return run(['java', '-cp', out, name])


MEMBER = '''Member constructor: Amy
Member constructor: Bob
VipMember constructor: Bob
Member Amy / points=12
VIP Bob / points=28 / bonus=1
VIP Bob / points=28 / bonus=1
'''
COMPARE = '''=== normal ===
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
step 3: command=2, result=OK, energy=6
counts[0..3]=[0, 2, 1, 0]
steps=3, ok=3, rejected=0, ignored=0
lowest energy=6
final energy=6, samples=5, actionCount=3 -> SUCCESS
battery=30.0
=== heavy ===
=== replay ===
step 1: command=1, result=OK, energy=14
step 2: command=1, result=OK, energy=8
counts[0..3]=[0, 2, 0, 0]
steps=2, ok=2, rejected=0, ignored=1
lowest energy=8
final energy=8, samples=6, actionCount=2 -> SUCCESS
battery=40.0
'''
TRIAL = '''drill: OK (expect OK) OK
after drill: 12/3/1/1 (expect 12/3/1/1) OK
explore: OK (expect OK) OK
after explore: 6/6/2/1 (expect 6/6/2/1) OK
done drill: STOPPED (expect STOPPED) OK
done unchanged: 6/6/2/1 (expect 6/6/2/1) OK
low: NOT_ENOUGH_ENERGY (expect NOT_ENOUGH_ENERGY) OK
low unchanged: 7/0/0/0 (expect 7/0/0/0) OK
exact: OK (expect OK) OK
exact state: 0/3/1/1 (expect 0/3/1/1) OK
zero cannot charge: STOPPED (expect STOPPED) OK
initial zero: STOPPED (expect STOPPED) OK
zero unchanged: 0/0/0/0 (expect 0/0/0/0) OK
initial success: STOPPED (expect STOPPED) OK
success unchanged: 20/5/0/0 (expect 20/5/0/0) OK
'''


def static_checks():
    links = 0
    for p in BASE.rglob('*'):
        if p.suffix not in {'.java', '.md', '.py', '.puml', '.cjs'}:
            continue
        s = p.read_text(encoding='utf-8')
        assert '\ufffd' not in s, p
        assert all(line == line.rstrip() for line in s.splitlines()), p
        if p.suffix == '.java':
            match = re.search(r'public class (\w+)', s)
            assert match and match.group(1) == p.stem, p
        if p.suffix == '.md':
            assert len(re.findall(r'^```', s, re.M)) % 2 == 0, p
            for target in re.findall(r'\[[^\]]+\]\(([^)]+)\)', s):
                target = unquote(target.strip('<>'))
                if '://' in target or target.startswith('#'):
                    continue
                assert (p.parent / target.split('#')[0]).exists(), (p, target)
                links += 1
    for name in ['Mission.java', 'MissionStep.java', 'MissionLog.java']:
        for group in ['starter', 'teacher']:
            assert (BASE / group / name).read_bytes() == (OLD / 'teacher' / name).read_bytes(), name
    for name in ['MissionLogCheck.java', 'MissionStateCheck.java', 'FixedScriptMission.java']:
        assert (BASE / 'teacher' / name).read_bytes() == (OLD / 'teacher' / name).read_bytes(), name
    for name, number in [('Rover.java', '1'), ('HeavyRover.java', '2'), ('CompareMissions.java', '3')]:
        assert re.findall(r'TODO (\d)', (BASE / 'starter' / name).read_text(encoding='utf-8')) == [number]
    for p in (BASE / 'diagrams').glob('*.puml'):
        assert p.with_suffix('.svg').is_file(), p
        assert 'Syntax Error' not in p.with_suffix('.svg').read_text(encoding='utf-8'), p
    md = (BASE / 'week08_slides_codex.md').read_text(encoding='utf-8')
    html = (BASE / 'week08_slides_codex.html').read_text(encoding='utf-8')
    pages = len(re.findall(r'^---\s*$', md, re.M)) - 1
    assert len(re.findall(r'<section\b', html)) == pages
    for target in re.findall(r'<img[^>]+src="([^"]+)"', html):
        assert (BASE / unquote(target)).exists(), target
    assert 'instanceof' not in (BASE / 'teacher/CompareMissions.java').read_text(encoding='utf-8')
    print(f'PASS static links={links}, slides={pages}, preserved contracts and TODO 1-3')


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--code-only', action='store_true')
    args = parser.parse_args()
    print(run(['java', '-version']).strip())
    print(run(['javac', '-version']).strip())
    with tempfile.TemporaryDirectory(prefix='.verify-', dir=BASE) as directory:
        work = Path(directory)
        for group in ['examples', 'starter', 'teacher']:
            compile_to(sorted((BASE / group).glob('*.java')), work / group)
            print('PASS compile', group)
        assert java(work / 'examples', 'MemberDemo') == MEMBER
        assert java(work / 'teacher', 'CompareMissions') == COMPARE
        assert java(work / 'teacher', 'HeavyRoverTry') == TRIAL
        assert java(work / 'starter', 'CompareMissions') == 'NOT_READY: create two independent missions\n'
        starter_trial = java(work / 'starter', 'HeavyRoverTry')
        assert len([line for line in starter_trial.splitlines() if line.endswith('DIFF')]) == 2
        assert 'after explore: 6/5/2/1' in starter_trial
        print('PASS full expected outputs and explicit unfinished starter')
        for check in ['MissionLogCheck', 'MissionStateCheck', 'PolymorphismCheck']:
            print(java(work / 'teacher', check).strip())
        # 完成學生配置：替換三個TODO檔，其餘保持提供的學生版本。
        combined = work / 'student-src'
        combined.mkdir()
        for group in ['examples', 'starter']:
            for p in (BASE / group).glob('*.java'):
                shutil.copyfile(p, combined / p.name)
        compile_to(sorted(combined.glob('*.java')), work / 'student-start')
        assert java(work / 'student-start', 'MemberDemo') == MEMBER
        assert java(work / 'student-start', 'CompareMissions').startswith('NOT_READY:')
        for name in ['Rover.java', 'HeavyRover.java', 'CompareMissions.java']:
            shutil.copyfile(BASE / 'teacher' / name, combined / name)
        compile_to(sorted(combined.glob('*.java')), work / 'student-finish')
        assert java(work / 'student-finish', 'CompareMissions') == COMPARE
        assert java(work / 'student-finish', 'HeavyRoverTry') == TRIAL
        assert java(work / 'student-finish', 'MissionLog') == java(work / 'teacher', 'MissionLog')
        print('PASS combined student start/finish configuration')
        compile_to([BASE / 'teacher/Rover.java', BASE / 'teacher/Mission.java',
                    BASE / 'teacher/MissionStep.java', *sorted((BASE / 'teacher/transfer').glob('*.java'))],
                   work / 'transfer')
        assert java(work / 'transfer', 'SurveyRoverTry') == (
            '6/3/3/UNFINISHED\n0/5/1/SUCCESS\nNOT_ENOUGH_ENERGY/5/0/0\nSTOPPED/20/5/0\n')
        print('PASS independent transfer full output')

        ai = work / 'ai-src'
        ai.mkdir()
        for name in ['Rover.java', 'HeavyRoverTry.java']:
            shutil.copyfile(BASE / 'teacher' / name, ai / name)
        shutil.copyfile(BASE / 'ai/HeavyRover.java', ai / 'HeavyRover.java')
        compile_to(sorted(ai.glob('*.java')), work / 'ai')
        wrong = java(work / 'ai', 'HeavyRoverTry')
        expected_wrong = TRIAL.replace('after explore: 6/6/2/1 (expect 6/6/2/1) OK',
                                       'after explore: 6/5/2/1 (expect 6/6/2/1) DIFF')
        expected_wrong = expected_wrong.replace('done unchanged: 6/6/2/1 (expect 6/6/2/1) OK',
                                                 'done unchanged: 6/5/2/1 (expect 6/6/2/1) DIFF')
        assert wrong == expected_wrong, wrong
        print('PASS simulated AI compiles and two expected DIFF lines')
        # 最小修正：移除無效private方法，改寫產量方法；其餘AI內容保留。
        p = ai / 'HeavyRover.java'
        s = p.read_text(encoding='utf-8')
        s = re.sub(r'    private String explore\(\) \{.*?\n    \}',
                   '    @Override\n    protected int exploreYield() { return 3; }', s, flags=re.S)
        p.write_text(s, encoding='utf-8')
        compile_to(sorted(ai.glob('*.java')), work / 'ai-fix')
        assert java(work / 'ai-fix', 'HeavyRoverTry') == TRIAL
        print('PASS minimal AI repair full fifteen-line retest')

        experiments = [
            ('method-name', BASE / 'examples/VipMember.java', 'public String describe()',
             'public String describeVIP()', 'does not override', 'member'),
            ('private-override', BASE / 'ai/HeavyRover.java', 'private String explore()',
             '@Override\n    private String explore()', 'does not override', 'ai'),
            ('parameter-typo', BASE / 'teacher/HeavyRover.java', 'protected int exploreYield()',
             'protected int exploreYield(int amount)', 'does not override', 'heavy'),
            ('narrow-access', BASE / 'teacher/HeavyRover.java', 'protected int exploreYield()',
             'private int exploreYield()', 'weaker access', 'heavy'),
            ('reference-member', BASE / 'examples/MemberDemo.java', 'System.out.println(memberView.describe());',
             'memberView.claimBonus();', 'cannot find symbol', 'member'),
        ]
        for label, source, old, new, diagnostic, group in experiments:
            target = work / label
            target.mkdir()
            sources = list((BASE / 'examples').glob('*.java')) if group == 'member' else [
                BASE / 'teacher/Rover.java', source]
            for src in sources:
                shutil.copyfile(src, target / src.name)
            p = target / source.name
            s = p.read_text(encoding='utf-8')
            assert old in s
            p.write_text(s.replace(old, new), encoding='utf-8')
            errors = compile_to(sorted(target.glob('*.java')), work / (label + '-out'), success=False)
            assert diagnostic in errors, errors
            print('PASS expected compiler rejection', label)
        # B3學生小修改：保持父方法，VIP顯示沿用父說明再加尾碼。
        experiment = work / 'member-edit'
        shutil.copytree(BASE / 'examples', experiment)
        p = experiment / 'VipMember.java'
        s = p.read_text(encoding='utf-8')
        s = re.sub(r'        return "VIP ".*?bonusCount;',
                   '        return super.describe() + " / VIP";', s, flags=re.S)
        p.write_text(s, encoding='utf-8')
        compile_to(sorted(experiment.glob('*.java')), work / 'member-edit-out')
        assert java(work / 'member-edit-out', 'MemberDemo') == MEMBER.replace(
            'VIP Bob / points=28 / bonus=1', 'Member Bob / points=28 / VIP')
        print('PASS guided member edit and super call')

        mutations = [
            ('Rover.java', 'addSamples(exploreYield());', 'addSamples(2);'),
            ('HeavyRover.java', '        return 3;', '        return 2;'),
            ('HeavyRover.java', '        return 3;', '        countAction();\n        return 3;'),
            ('Rover.java', 'if (isStopped()) { return "STOPPED"; }', '// missing stop'),
            ('HeavyRover.java', 'private int drillCount;', 'private int drillCount;\n    private int energy;'),
        ]
        for i, (name, old, new) in enumerate(mutations):
            target = work / f'mutation-{i}'
            shutil.copytree(BASE / 'teacher', target)
            p = target / name
            s = p.read_text(encoding='utf-8')
            assert old in s
            p.write_text(s.replace(old, new), encoding='utf-8')
            out = work / f'mutation-out-{i}'
            compile_to(sorted(target.glob('*.java')), out)
            run(['java', '-cp', out, 'PolymorphismCheck'], success=False)
        print('PASS five detected behavior/design mutations')
    if not args.code_only:
        static_checks()
    print('ALL CHECKS PASSED')


if __name__ == '__main__':
    main()
