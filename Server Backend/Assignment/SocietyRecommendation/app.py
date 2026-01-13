import os
import uuid
from datetime import datetime, timedelta
from functools import wraps

from flask import Flask, request, jsonify, g
from werkzeug.security import generate_password_hash, check_password_hash
from sqlalchemy.exc import IntegrityError

from models import db, User, Interest, Society, Event, SessionToken

app = Flask(__name__)

def _build_db_url():
    # If a full DATABASE_URL is provided, use it (works for sqlite, mysql, mariadb)
    if os.environ.get('DATABASE_URL'):
        return os.environ.get('DATABASE_URL')
    # Simple MariaDB configuration via env vars (keeps setup simple)
    user = os.environ.get('DB_USER')
    password = os.environ.get('DB_PASSWORD')
    host = os.environ.get('DB_HOST', 'localhost')
    port = os.environ.get('DB_PORT', '3306')
    name = os.environ.get('DB_NAME')
    if user and password and name:
        return f'mysql+pymysql://{user}:{password}@{host}:{port}/{name}'
    # fallback: local sqlite file
    return "sqlite:///societies.db"

app.config['SQLALCHEMY_DATABASE_URI'] = _build_db_url()
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db.init_app(app)


def auth_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.headers.get('Authorization', '')
        if not auth.startswith('Bearer '):
            return jsonify({"error": "missing or invalid authorization header"}), 401
        token = auth.split(' ', 1)[1]
        session = SessionToken.query.filter_by(token=token).first()
        if not session or session.expires_at < datetime.utcnow():
            return jsonify({"error": "invalid or expired token"}), 401
        g.current_user = User.query.get(session.user_id)
        if not g.current_user:
            return jsonify({"error": "user not found"}), 401
        return f(*args, **kwargs)
    return decorated


@app.route('/api/v1/users/create', methods=['POST'])
def create_user():
    data = request.get_json() or {}
    username = data.get('username')
    email = data.get('email')
    password = data.get('password')
    degree = data.get('degree', '')
    year = data.get('year_of_study', 1)
    if not username or not email or not password:
        return jsonify({"error": "username, email and password are required"}), 400
    if len(username) < 5:
        return jsonify({"error": "username must be at least 5 characters"}), 400
    hashed = generate_password_hash(password)
    user = User(username=username, email=email, password=hashed, degree=degree, year_of_study=year)
    try:
        db.session.add(user)
        db.session.commit()
    except IntegrityError:
        db.session.rollback()
        return jsonify({"error": "username or email already exists"}), 409
    return jsonify({"response_message": f"User account {user.id} has successfully been created", "user_id": user.id}), 201


@app.route('/api/v1/users/login', methods=['POST'])
def login_user():
    data = request.get_json() or {}
    email = data.get('email')
    password = data.get('password')
    if not email or not password:
        return jsonify({"error": "email and password required"}), 400
    user = User.query.filter_by(email=email).first()
    if not user or not check_password_hash(user.password, password):
        return jsonify({"error": "invalid credentials"}), 401
    token = uuid.uuid4().hex
    expires = datetime.utcnow() + timedelta(days=7)
    st = SessionToken(token=token, user_id=user.id, expires_at=expires)
    db.session.add(st)
    db.session.commit()
    return jsonify({"token": token, "expires_at": expires.isoformat()}), 200


@app.route('/api/v1/users/delete', methods=['DELETE'])
@auth_required
def delete_user():
    user = g.current_user
    db.session.delete(user)
    db.session.commit()
    return jsonify({"response_message": "User account has successfully been deleted"}), 200


@app.route('/api/v1/users/interests/add', methods=['POST'])
@auth_required
def add_interest():
    data = request.get_json() or {}
    # accept multiple parameter naming styles for compatibility with client
    interest_name = data.get('interest_name') or data.get('interest name') or data.get('interest')
    if not interest_name:
        return jsonify({"error": "could not add interest, request body format invalid"}), 400
    interest = Interest.query.filter_by(name=interest_name).first()
    if not interest:
        interest = Interest(name=interest_name)
        db.session.add(interest)
        db.session.commit()
    user = g.current_user
    if interest in user.interests:
        return jsonify({"error": "interest already added"}), 409
    user.interests.append(interest)
    db.session.commit()
    return jsonify({"response_message": "Interest successfully added"}), 200


@app.route('/api/v1/users/interests/delete', methods=['DELETE'])
@auth_required
def delete_interest():
    data = request.get_json() or {}
    # accept both 'interest_id' and 'interest id'
    interest_id = data.get('interest_id') or data.get('interest id') or data.get('interest')
    if not interest_id:
        return jsonify({"error": "interest_id required"}), 400
    interest = Interest.query.get(interest_id)
    if not interest:
        return jsonify({"error": "interest not found"}), 404
    user = g.current_user
    if interest not in user.interests:
        return jsonify({"error": "interest not linked to user"}), 404
    user.interests.remove(interest)
    db.session.commit()
    return jsonify({"response_message": "Interest successfully deleted"}), 200


@app.route('/api/v1/users/<int:user_id>/societies/join', methods=['POST'])
@auth_required
def join_society(user_id):
    if g.current_user.id != user_id and g.current_user.role != 'admin':
        return jsonify({"error": "forbidden"}), 403
    data = request.get_json() or {}
    # accept 'society_id', 'society id' or 'society_name' to join by name
    society_id = data.get('society_id') or data.get('society id')
    society_name = data.get('society_name') or data.get('society name')
    if not society_id and not society_name:
        return jsonify({"error": "society_id or society_name required"}), 400
    society = None
    if society_id:
        society = Society.query.get(society_id)
    else:
        society = Society.query.filter_by(name=society_name).first()
    if not society:
        return jsonify({"error": "society not found"}), 404
    if society in g.current_user.societies:
        return jsonify({"error": "already a member"}), 409
    g.current_user.societies.append(society)
    db.session.commit()
    return jsonify({"response_message": "Society successfully joined"}), 200


@app.route('/api/v1/users/<int:user_id>/societies/unjoin', methods=['DELETE'])
@auth_required
def unjoin_society(user_id):
    if g.current_user.id != user_id and g.current_user.role != 'admin':
        return jsonify({"error": "forbidden"}), 403
    data = request.get_json() or {}
    society_id = data.get('society_id') or data.get('society id')
    society_name = data.get('society_name') or data.get('society name')
    if not society_id and not society_name:
        return jsonify({"error": "society_id or society_name required"}), 400
    society = None
    if society_id:
        society = Society.query.get(society_id)
    else:
        society = Society.query.filter_by(name=society_name).first()
    if not society:
        return jsonify({"error": "society not found"}), 404
    if society not in g.current_user.societies:
        return jsonify({"error": "not a member"}), 404
    g.current_user.societies.remove(society)
    db.session.commit()
    return jsonify({"response_message": "Society successfully removed"}), 200


@app.route('/api/v1/societies/<int:soc_id>', methods=['GET'])
def get_society(soc_id):
    soc = Society.query.get(soc_id)
    if not soc:
        return jsonify({"error": "society not found"}), 404
    events = [e.to_dict() for e in soc.events]
    return jsonify({"society": soc.to_dict(), "events": events}), 200


# ---- Event management endpoints ----
@app.route('/api/v1/societies/<int:soc_id>/events', methods=['POST'])
@auth_required
def create_event(soc_id):
    soc = Society.query.get(soc_id)
    if not soc:
        return jsonify({"error": "society not found"}), 404
    data = request.get_json() or {}
    name = data.get('name') or data.get('event_name') or data.get('event name')
    description = data.get('description')
    date_str = data.get('event_date') or data.get('date')
    location = data.get('location')
    if not name or not date_str:
        return jsonify({"error": "event name and event_date are required"}), 400
    try:
        event_date = datetime.fromisoformat(date_str)
    except Exception:
        return jsonify({"error": "invalid date format, use ISO format (YYYY-MM-DDTHH:MM:SS)"}), 400
    ev = Event(society=soc, name=name, description=description, event_date=event_date, location=location)
    db.session.add(ev)
    db.session.commit()
    return jsonify({"response_message": "Event created", "event": ev.to_dict()}), 201


@app.route('/api/v1/events/<int:event_id>', methods=['GET'])
def get_event(event_id):
    ev = Event.query.get(event_id)
    if not ev:
        return jsonify({"error": "event not found"}), 404
    return jsonify({"event": ev.to_dict()}), 200


@app.route('/api/v1/events/<int:event_id>', methods=['PUT'])
@auth_required
def update_event(event_id):
    ev = Event.query.get(event_id)
    if not ev:
        return jsonify({"error": "event not found"}), 404
    data = request.get_json() or {}
    # simple role check: allow admin or any authenticated user to update (can be tightened later)
    name = data.get('name') or data.get('event_name') or data.get('event name')
    description = data.get('description')
    date_str = data.get('event_date') or data.get('date')
    location = data.get('location')
    if name:
        ev.name = name
    if description is not None:
        ev.description = description
    if date_str:
        try:
            ev.event_date = datetime.fromisoformat(date_str)
        except Exception:
            return jsonify({"error": "invalid date format, use ISO format"}), 400
    if location is not None:
        ev.location = location
    db.session.commit()
    return jsonify({"response_message": "Event updated", "event": ev.to_dict()}), 200


@app.route('/api/v1/events/<int:event_id>', methods=['DELETE'])
@auth_required
def delete_event(event_id):
    ev = Event.query.get(event_id)
    if not ev:
        return jsonify({"error": "event not found"}), 404
    db.session.delete(ev)
    db.session.commit()
    return jsonify({"response_message": "Event deleted"}), 200


@app.route('/api/v1/events/<int:event_id>/join', methods=['POST'])
@auth_required
def join_event(event_id):
    ev = Event.query.get(event_id)
    if not ev:
        return jsonify({"error": "event not found"}), 404
    user = g.current_user
    if user in ev.attendees:
        return jsonify({"error": "already joined"}), 409
    ev.attendees.append(user)
    db.session.commit()
    return jsonify({"response_message": "Joined event"}), 200


@app.route('/api/v1/events/<int:event_id>/leave', methods=['DELETE'])
@auth_required
def leave_event(event_id):
    ev = Event.query.get(event_id)
    if not ev:
        return jsonify({"error": "event not found"}), 404
    user = g.current_user
    if user not in ev.attendees:
        return jsonify({"error": "not an attendee"}), 404
    ev.attendees.remove(user)
    db.session.commit()
    return jsonify({"response_message": "Left event"}), 200


@app.route('/api/v1/users/<int:user_id>/recommendations', methods=['GET'])
@auth_required
def recommendations(user_id):
    # Only allow owner or admin
    if g.current_user.id != user_id and g.current_user.role != 'admin':
        return jsonify({"error": "forbidden"}), 403
    user = User.query.get(user_id)
    if not user:
        return jsonify({"error": "user not found"}), 404
    if not user.interests:
        # fallback: return general list
        societies = Society.query.limit(20).all()
        return jsonify({"recommendations": [s.to_dict() for s in societies]}), 200
    # rank societies by number of shared interests
    results = {}
    for interest in user.interests:
        for soc in interest.societies:
            results.setdefault(soc, 0)
            results[soc] += 1
    ranked = sorted(results.items(), key=lambda kv: (-kv[1], kv[0].name))
    return jsonify({"recommendations": [s.to_dict() for s, score in ranked]}), 200


if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(host='0.0.0.0', port=5000)
