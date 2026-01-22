import requests
from pathlib import Path
import json

out = Path('scripts/output')
out.mkdir(parents=True, exist_ok=True)

session = requests.Session()
base = 'http://127.0.0.1:5000'

# Login
login_payload = {'email': 'alice@example.edu', 'password': 'password123'}
try:
    r = session.post(f'{base}/api/v1/users/login', json=login_payload, timeout=5)
    (out / 'login_status.txt').write_text(str(r.status_code))
    try:
        (out / 'login.json').write_text(r.text)
    except Exception:
        (out / 'login.txt').write_text(r.text)
except Exception as e:
    (out / 'login_error.txt').write_text(str(e))

# Protected user societies
try:
    r = session.get(f'{base}/api/v1/users/stu-0001/societies', timeout=5)
    (out / 'user_societies_status.txt').write_text(str(r.status_code))
    (out / 'user_societies.json').write_text(r.text)
except Exception as e:
    (out / 'user_societies_error.txt').write_text(str(e))

# Protected recommendations
try:
    r = session.get(f'{base}/api/v1/users/stu-0001/societies/recommendations', timeout=5)
    (out / 'recommendations_status.txt').write_text(str(r.status_code))
    (out / 'recommendations.json').write_text(r.text)
except Exception as e:
    (out / 'recommendations_error.txt').write_text(str(e))

# Public societies
try:
    r = session.get(f'{base}/api/v1/societies', timeout=5)
    (out / 'all_societies_status.txt').write_text(str(r.status_code))
    (out / 'all_societies.json').write_text(r.text)
except Exception as e:
    (out / 'all_societies_error.txt').write_text(str(e))

print('Saved outputs to', out.resolve())
