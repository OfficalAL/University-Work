from werkzeug.security import generate_password_hash
import sqlite3
p = r'C:\Users\Offic\OneDrive - Birmingham City University\2nd Year\Server Backend\Assignment\SocietyRecommendation\instance\dev.sqlite3'
h = generate_password_hash('Penguin2580##')
print('generated hash:', h)
conn = sqlite3.connect(p)
cur = conn.cursor()
cur.execute('UPDATE users SET password=? WHERE email=?', (h, 'alex.rush@mail.bcu.ac.uk'))
conn.commit()
cur.execute('SELECT id, student_id, username, email, password FROM users WHERE email=?', ('alex.rush@mail.bcu.ac.uk',))
print('updated row:', cur.fetchone())
conn.close()
