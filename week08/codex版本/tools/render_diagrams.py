"""以本機 PlantUML jar 產生 SVG，不需 Python 套件或網路服務。"""
from pathlib import Path
import argparse
import re
import subprocess


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--jar", type=Path, required=True)
    parser.add_argument("--source", type=Path, required=True,
                        help="一個 .puml 或含 plantuml 區塊的 .md")
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    if not args.jar.is_file():
        parser.error("找不到 jar；請先取得教師提供的 PlantUML 路徑。")
    source = args.source.read_text(encoding="utf-8")
    if args.source.suffix.lower() == ".md":
        blocks = re.findall(r"^```plantuml[^\n]*\n(.*?)^```\s*$", source,
                            flags=re.M | re.S)
    else:
        blocks = [source]
    if not blocks:
        parser.error("找不到 plantuml 程式碼區塊。")
    args.output.mkdir(parents=True, exist_ok=True)
    for index, block in enumerate(blocks, 1):
        result = subprocess.run(
            ["java", "-jar", str(args.jar.resolve()), "-charset", "UTF-8",
             "-tsvg", "-pipe"],
            input=block.encode("utf-8"), capture_output=True, timeout=45)
        if result.returncode != 0 or b"<svg" not in result.stdout:
            raise SystemExit(f"第 {index} 圖失敗：\n" + result.stderr.decode("utf-8", errors="replace"))
        target = args.output / f"{args.source.stem}-{index}.svg"
        target.write_bytes(result.stdout)
        print(target.resolve())


if __name__ == "__main__":
    main()
