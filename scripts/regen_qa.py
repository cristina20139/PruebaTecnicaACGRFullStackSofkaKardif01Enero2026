import re
from pathlib import Path

BASE_URL = "https://github.com/cristina20139/PruebaTecnicaACGRFullStackSofkaKardif01Enero2026/blob/main/"

RAW_HTML_PATH = Path("docs/qa-backend-frontend.html")


def parse_articles(html: str):
    pattern = r"<article[^>]*>.*?</article>"
    return re.findall(pattern, html, re.S)


def extract_text(block: str, tag: str, label: str):
    regex = rf"<p class='{tag}'>.*?<span class='label'>{label}</span>(.*?)</p>"
    match = re.search(regex, block, re.S)
    return match.group(1).strip() if match else ""


KEYWORD_PATHS = [
    # Backend
    ("TransactionController", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/web/TransactionController.java"),
    ("TransactionService", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/TransactionService.java"),
    ("TransactionRequest", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/dto/TransactionRequest.java"),
    ("TransactionResponse", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/dto/TransactionResponse.java"),
    ("TransactionRecord", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/domain/model/TransactionRecord.java"),
    ("TransactionRepository", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/domain/repository/TransactionRepository.java"),
    ("TransactionRulesConfiguration", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/config/TransactionRulesConfiguration.java"),
    ("TransactionRulesProperties", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/TransactionRulesProperties.java"),
    ("CommissionRule", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/rules/CommissionRule.java"),
    ("ThresholdCommissionRule", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/rules/ThresholdCommissionRule.java"),
    ("CommissionResult", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/rules/CommissionResult.java"),
    ("TransactionEventPublisher", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/messaging/TransactionEventPublisher.java"),
    ("TransactionEvent", "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/messaging/TransactionEvent.java"),
    ("schema.sql", "backend/PruebaTecnicaACGRJavaBackend/src/main/resources/schema.sql"),
    ("data.sql", "backend/PruebaTecnicaACGRJavaBackend/src/main/resources/data.sql"),
    ("build.gradle", "backend/PruebaTecnicaACGRJavaBackend/build.gradle"),
    ("application.properties", "backend/PruebaTecnicaACGRJavaBackend/src/main/resources/application.properties"),
    ("kafka", "backend/PruebaTecnicaACGRJavaBackend/src/main/resources/application.properties"),
    # Frontend
    ("AppComponent", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("ReactiveFormsModule", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("FormBuilder", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("transactionForm", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("refresher", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("loadTransactions", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("switchMap", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("shareReplay", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("catchError", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("timer", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"),
    ("TransactionService", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/transactions.service.ts"),
    ("transactions.service.ts", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/transactions.service.ts"),
    ("app.component.html", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.html"),
    ("transactions-table", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.css"),
    ("empty-state", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.html"),
    ("hero", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.html"),
    ("panel", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.css"),
    ("grid", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.css"),
    ("app.component.css", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.css"),
    ("app.config.ts", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.config.ts"),
    ("app.routes.ts", "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.routes.ts"),
]


def build_reference(answer: str, prefix: str):
    paths = re.findall(r"(?:backend|frontend)/[A-Za-z0-9_./-]+", answer)
    seen = []
    for path in paths:
        if path not in seen:
            seen.append(path)
    if not seen:
        lower_answer = answer.lower()
        for keyword, path in KEYWORD_PATHS:
            if keyword.lower() in lower_answer:
                if path not in seen:
                    seen.append(path)
    if not seen:
        fallback = (
            "backend/PruebaTecnicaACGRJavaBackend/src/main/java/com/acgr/sofka/pt/kardif/service/TransactionService.java"
            if prefix == "backend"
            else "frontend/prueba-tecnica-acgr-sofka-angular-ene2026/src/app/app.component.ts"
        )
        seen.append(fallback)
    if not seen:
        return ""
    links = [f"<a href='{BASE_URL}{path}' target='_blank' rel='noreferrer'>{path}</a>" for path in seen]
    return "Referencia: " + " y ".join(links)


def parse_sections(html: str):
    articles = parse_articles(html)
    backend = []
    frontend = []
    for article in articles:
        id_match = re.search(r"id='([^']+)'", article)
        prefix = id_match.group(1).split("-")[0] if id_match else "backend"
        question_raw = extract_text(article, "qa-question", "P:")
        question = re.sub(r"<span class='qa-number'>.*?</span>", "", question_raw).strip()
        answer_raw = extract_text(article, "qa-answer", "R:")
        reference = build_reference(answer_raw, prefix)
        entry = (question, answer_raw, reference)
        (backend if prefix == "backend" else frontend).append(entry)
    return backend, frontend


STYLE = """
body {
  font-family: 'Segoe UI', Arial, sans-serif;
  background: #ebeef6;
  color: #0f172a;
  margin: 0;
  padding: 0;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 32px 24px 60px;
}
header {
  text-align: center;
  margin-bottom: 24px;
}
header h1 {
  margin: 0;
  font-size: clamp(2rem, 3vw, 2.8rem);
  color: #1f2937;
}
header p {
  margin: 0;
  font-size: 1rem;
  color: #475569;
}
section {
  margin-top: 40px;
}
section h2 {
  margin-bottom: 18px;
  font-size: 1.5rem;
  color: #111827;
}
.qa-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 16px;
}
.qa-card {
  background: #fff;
  border-radius: 16px;
  padding: 18px;
  border: 1px solid #d1d5db;
  box-shadow: 0 12px 35px rgba(15, 23, 42, 0.12);
  transition: transform 0.2s ease;
}
.qa-card:hover {
  transform: translateY(-3px);
}
.qa-question,
.qa-answer {
  margin: 6px 0;
  line-height: 1.4;
  font-size: 0.95rem;
}
.qa-question {
  font-weight: 600;
}
.qa-answer {
  color: #1f2937;
}
.label {
  font-weight: 700;
  color: #1d4ed8;
  margin-right: 6px;
}
.qa-number {
  font-weight: 700;
  color: #0f172a;
  margin-right: 8px;
}
.reference {
  display: block;
  margin-top: 6px;
  font-size: 0.8rem;
  color: #475569;
}
.reference a {
  color: #1d4ed8;
  text-decoration: none;
}
.reference a:hover {
  text-decoration: underline;
}
footer {
  margin-top: 48px;
  text-align: center;
  font-size: 0.9rem;
  color: #6b7280;
}
@media (max-width: 600px) {
  .container {
    padding: 20px 16px 40px;
  }
  .qa-card {
    padding: 16px;
  }
}
"""


def render_section(title: str, qas, prefix: str):
    rows = [f"<section><h2>{title}</h2><div class='qa-grid'>"]
    for idx, (question, answer, reference) in enumerate(qas, start=1):
        rows.append(f"  <article class='qa-card' id='{prefix}-{idx}'>")
        rows.append(f"    <p class='qa-question'><span class='label'>P:</span><span class='qa-number'>{idx}.</span>{question}</p>")
        rows.append(f"    <p class='qa-answer'><span class='label'>R:</span>{answer}</p>")
        if reference:
            rows.append(f"    <span class='reference'>{reference}</span>")
        rows.append("  </article>")
    rows.append("</div></section>")
    return "\n".join(rows)


def rebuild_html():
    raw_html = RAW_HTML_PATH.read_text(encoding="utf-8")
    backend_qas, frontend_qas = parse_sections(raw_html)
    html = f"""<!DOCTYPE html>
<html lang='es'>
<head>
  <meta charset='utf-8'>
  <title>QA Backend y Frontend</title>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <style>{STYLE}</style>
</head>
<body>
  <div class='container'>
    <header>
      <h1>QA técnico – Backend y Frontend</h1>
      <p>100 respuestas que describen los conceptos aplicados y sus beneficios.</p>
    </header>
    {render_section('Backend: 50 preguntas técnicas', backend_qas, 'backend')}
    {render_section('Frontend: 50 preguntas técnicas', frontend_qas, 'frontend')}
    <footer>Generado automáticamente para documentar las decisiones técnicas del proyecto.</footer>
  </div>
</body>
</html>"""
    RAW_HTML_PATH.write_text(html, encoding="utf-8")


if __name__ == "__main__":
    rebuild_html()
