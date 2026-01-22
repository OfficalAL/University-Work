import sqlite3
p = r'C:\Users\Offic\OneDrive - Birmingham City University\2nd Year\Server Backend\Assignment\SocietyRecommendation\instance\dev.sqlite3'
conn = sqlite3.connect(p)
cur = conn.cursor()
cur.execute("SELECT id, student_id, username, email, password FROM users")
for r in cur.fetchall():
    print(r)
conn.close()
