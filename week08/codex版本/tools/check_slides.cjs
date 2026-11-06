const fs = require('fs');
const path = require('path');
const { pathToFileURL } = require('url');
// 維護用：沿用第四週檢查，加入獨立profile與圖片檢查；不安裝套件。
// node check_slides.cjs <puppeteer-core目錄> <Chrome路徑> <bare HTML> <輸出目錄> [profile目錄]
(async () => {
  const [modulePath, chromePath, htmlPath, outputDir, profileDir] = process.argv.slice(2);
  if (!outputDir) throw new Error('Expected module, Chrome, bare HTML, output directory');
  fs.mkdirSync(outputDir, { recursive: true });
  const puppeteer = require(modulePath);
  const browser = await puppeteer.launch({ executablePath: chromePath, headless: true, userDataDir: profileDir || path.join(outputDir, 'chrome-profile'),
    args: ['--no-sandbox', '--disable-gpu'] });
  try {
    const page = await browser.newPage();
    await page.setViewport({ width: 1440, height: 900, deviceScaleFactor: 1 });
    await page.goto(pathToFileURL(path.resolve(htmlPath)).href, { waitUntil: 'networkidle0' });
    await page.evaluate(() => document.fonts.ready);
    const issues = await page.evaluate(() => {
      const result = [];
      [...document.querySelectorAll('section')].forEach((section, index) => {
        const box = section.getBoundingClientRect();
        for (const element of section.querySelectorAll('h1,h2,h3,p,li,table,pre,blockquote,img')) {
          const b = element.getBoundingClientRect();
          if (element.tagName === 'IMG' && !element.naturalWidth) result.push({ slide: index + 1, tag: 'IMG', text: element.src });
          if (b.left < box.left + 2 || b.right > box.right - 2 || b.top < box.top + 2 || b.bottom > box.bottom - 25) {
            result.push({ slide: index + 1, tag: element.tagName, text: (element.innerText || element.alt || '').slice(0, 90) });
          }
          if (element.tagName === 'PRE' && element.scrollWidth > element.clientWidth + 2) {
            result.push({ slide: index + 1, tag: 'PRE', text: 'code horizontal overflow' });
          }
        }
      });
      return result;
    });
    const svg = await page.$$('svg[data-marpit-svg]');
    for (let i = 0; i < svg.length; i++) {
      await svg[i].screenshot({ path: path.join(outputDir, `slide-${String(i + 1).padStart(2, '0')}.png`) });
    }
    fs.writeFileSync(path.join(outputDir, 'layout-report.json'), JSON.stringify({ slides: svg.length, issues }, null, 2));
    const contact = await browser.newPage();
    await contact.setViewport({ width: 1280, height: Math.ceil(svg.length / 4) * 205, deviceScaleFactor: 1 });
    const images = svg.map((_, i) => {
      const filename = `slide-${String(i + 1).padStart(2, '0')}.png`;
      const uri = `data:image/png;base64,${fs.readFileSync(path.join(outputDir, filename)).toString('base64')}`;
      return `<div><b>${i + 1}</b><img src="${uri}" /></div>`;
    }).join('');
    await contact.setContent(`<html><style>body{margin:0;background:#ddd;font:14px Arial;display:grid;grid-template-columns:repeat(4,320px)}div{height:205px}b{display:block;height:20px;padding-left:8px}img{width:320px;height:180px}</style>${images}</html>`);
    await contact.screenshot({ path: path.join(outputDir, 'contact-sheet.png'), fullPage: true });
    console.log(JSON.stringify({ slides: svg.length, issues }));
    if (!svg.length || issues.length) process.exitCode = 1;
  } finally { await browser.close(); }
})().catch(error => { console.error(error); process.exitCode = 1; });
