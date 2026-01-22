import requests

base = 'http://127.0.0.1:5000'
s = requests.Session()
creds = {'email': 'alex.rush@mail.bcu.ac.uk', 'password': 'Penguin2580##'}
print('POST /api/v1/users/login')
r = s.post(base + '/api/v1/users/login', json=creds, timeout=10)
print('status', r.status_code)
print('set-cookie', r.headers.get('Set-Cookie'))
print('cookies', s.cookies.get_dict())
print('body', r.text[:1000])
print('\nGET /api/v1/users/85420f21-4b26-4e4c-a/societies')
r2 = s.get(base + '/api/v1/users/85420f21-4b26-4e4c-a/societies', timeout=10)
print('status', r2.status_code)
print('body', r2.text[:2000])
