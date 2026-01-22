from pathlib import Path
import sqlite3

root = Path(__file__).parent
schema_path = root / "SocietyRecommendation" / "SQLDDL" / "schema.sql"
seed_path = root / "SocietyRecommendation" / "SQLDDL" / "sample_seed.sql"
db_path = root / "SocietyRecommendation" / "instance" / "dev.sqlite3"

if not schema_path.exists():
    raise SystemExit(f"Schema file not found: {schema_path}")
if not seed_path.exists():
    raise SystemExit(f"Seed file not found: {seed_path}")

schema = schema_path.read_text()
seed = seed_path.read_text()

# Make schema SQLite-compatible: replace SERIAL PRIMARY KEY with AUTOINCREMENT
schema = schema.replace('SERIAL PRIMARY KEY', 'INTEGER PRIMARY KEY AUTOINCREMENT')
# Some SQL dialects use DOUBLE quotes or backticks; keep as-is but ensure PRAGMA foreign_keys is enabled

# Ensure instance dir exists
db_path.parent.mkdir(parents=True, exist_ok=True)

conn = sqlite3.connect(str(db_path))
try:
    cur = conn.cursor()
    cur.execute('PRAGMA foreign_keys = ON;')
    # Execute schema and seed
    cur.executescript(schema)
    cur.executescript(seed)
    conn.commit()
    print(f"Created SQLite DB at: {db_path}")
finally:
    conn.close()
