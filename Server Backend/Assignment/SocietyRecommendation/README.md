# BCU Societies - Backend Prototype

Run a minimal Flask REST API implementing the group design document.

Quick start (Windows PowerShell):

1. Create a virtual environment and install dependencies

```powershell
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
```

2. Initialise the database with sample data

```powershell
python db_init.py
```

3. Run the server

```powershell
python app.py
```

API highlights:
- `POST /api/v1/users/create` - create account
- `POST /api/v1/users/login` - login and receive token
- `DELETE /api/v1/users/delete` - delete current user (Authorization: Bearer <token>)
- `POST /api/v1/users/interests/add` - add interest for current user
- `DELETE /api/v1/users/interests/delete` - delete interest for current user
- `POST /api/v1/users/{id}/societies/join` - join society (auth)
- `DELETE /api/v1/users/{id}/societies/unjoin` - leave society (auth)
- `GET /api/v1/societies/{id}` - get society details and events
- `GET /api/v1/users/{id}/recommendations` - interest-based recommendations (auth)

Notes:
- By default the app uses `sqlite:///societies.db`.
- To use MariaDB/MySQL you can either set a full `DATABASE_URL` env var (SQLAlchemy format) or provide simple env vars:

	- `DB_USER`, `DB_PASSWORD`, `DB_NAME` (required)
	- optional: `DB_HOST` (default: localhost), `DB_PORT` (default: 3306)

	Example PowerShell to run with MariaDB credentials:

	```powershell
	$env:DB_USER='myuser'
	$env:DB_PASSWORD='mypassword'
	$env:DB_NAME='societies_db'
	python app.py
	```
