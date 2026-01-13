from flask_sqlalchemy import SQLAlchemy
from datetime import datetime

db = SQLAlchemy()

# Association tables
user_societies = db.Table('user_societies',
    db.Column('user_id', db.Integer, db.ForeignKey('users.id', ondelete='CASCADE'), primary_key=True),
    db.Column('society_id', db.Integer, db.ForeignKey('societies.id', ondelete='CASCADE'), primary_key=True)
)

user_interests = db.Table('user_interests',
    db.Column('user_id', db.Integer, db.ForeignKey('users.id', ondelete='CASCADE'), primary_key=True),
    db.Column('interest_id', db.Integer, db.ForeignKey('interests.id', ondelete='CASCADE'), primary_key=True)
)

society_interests = db.Table('society_interests',
    db.Column('society_id', db.Integer, db.ForeignKey('societies.id', ondelete='CASCADE'), primary_key=True),
    db.Column('interest_id', db.Integer, db.ForeignKey('interests.id', ondelete='CASCADE'), primary_key=True)
)

user_events = db.Table('user_events',
    db.Column('user_id', db.Integer, db.ForeignKey('users.id', ondelete='CASCADE'), primary_key=True),
    db.Column('event_id', db.Integer, db.ForeignKey('events.id', ondelete='CASCADE'), primary_key=True)
)


class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(50), nullable=False, unique=True)
    email = db.Column(db.String(100), nullable=False, unique=True)
    password = db.Column(db.String(255), nullable=False)
    role = db.Column(db.String(10), default='user')
    degree = db.Column(db.String(100), nullable=True)
    year_of_study = db.Column(db.Integer, nullable=True)

    societies = db.relationship('Society', secondary=user_societies, back_populates='members')
    interests = db.relationship('Interest', secondary=user_interests, back_populates='users')
    events = db.relationship('Event', secondary=user_events, back_populates='attendees')

    def to_dict(self):
        return {
            'id': self.id,
            'username': self.username,
            'email': self.email,
            'role': self.role,
            'degree': self.degree,
            'year_of_study': self.year_of_study
        }


class Society(db.Model):
    __tablename__ = 'societies'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(50), nullable=False, unique=True)
    description = db.Column(db.Text)
    category = db.Column(db.String(50), nullable=False)
    contact_info = db.Column(db.String(100), nullable=True)

    members = db.relationship('User', secondary=user_societies, back_populates='societies')
    interests = db.relationship('Interest', secondary=society_interests, back_populates='societies')
    events = db.relationship('Event', backref='society', cascade='all, delete-orphan')

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'category': self.category,
            'contact_info': self.contact_info
        }


class Interest(db.Model):
    __tablename__ = 'interests'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(50), nullable=False, unique=True)

    users = db.relationship('User', secondary=user_interests, back_populates='interests')
    societies = db.relationship('Society', secondary=society_interests, back_populates='interests')

    def to_dict(self):
        return {'id': self.id, 'name': self.name}


class Event(db.Model):
    __tablename__ = 'events'
    id = db.Column(db.Integer, primary_key=True)
    society_id = db.Column(db.Integer, db.ForeignKey('societies.id', ondelete='CASCADE'), nullable=False)
    name = db.Column(db.String(50), nullable=False)
    description = db.Column(db.Text)
    event_date = db.Column(db.DateTime, nullable=False)
    location = db.Column(db.String(100), nullable=True)

    attendees = db.relationship('User', secondary=user_events, back_populates='events')

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'event_date': self.event_date.isoformat() if self.event_date else None,
            'location': self.location,
            'society_id': self.society_id
        }


class SessionToken(db.Model):
    __tablename__ = 'session_tokens'
    id = db.Column(db.Integer, primary_key=True)
    token = db.Column(db.String(128), unique=True, nullable=False)
    user_id = db.Column(db.Integer, db.ForeignKey('users.id', ondelete='CASCADE'), nullable=False)
    created_at = db.Column(db.DateTime, default=datetime.utcnow)
    expires_at = db.Column(db.DateTime, nullable=False)
