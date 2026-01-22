import sqlite3
from werkzeug.security import generate_password_hash
from pathlib import Path

root = Path(__file__).resolve().parents[1]
db_path = root / 'SocietyRecommendation' / 'instance' / 'dev.sqlite3'
if not db_path.exists():
    raise SystemExit(f"DB not found: {db_path}")

pw = 'password123'
hash = generate_password_hash(pw)
conn = sqlite3.connect(str(db_path))
try:
    cur = conn.cursor()
    cur.execute("UPDATE users SET password = ? WHERE username = ?", (hash, 'alice'))
    conn.commit()
    print('Updated alice password to password123')
finally:
    conn.close()
