import sqlite3
from pathlib import Path
base = Path('SocietyRecommendation/instance')
paths = [base / 'dev.sqlite3', base / 'societies.db']
for p in paths:
    print('DB:', p)
    if not p.exists():
        print('  missing')
        continue
    conn = sqlite3.connect(str(p))
    cur = conn.cursor()
    try:
        cur.execute("SELECT COUNT(*) FROM users")
        print('  users:', cur.fetchone()[0])
        cur.execute("SELECT id, student_id, username, email FROM users LIMIT 10")
        for r in cur.fetchall():
            print('   ', r)
    except Exception as e:
        print('  error:', e)
    conn.close()
