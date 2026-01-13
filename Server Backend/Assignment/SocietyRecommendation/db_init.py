from datetime import datetime
from models import db, User, Society, Interest, Event
from SocietyRecommendation.app import app


def seed():
    with app.app_context():
        db.create_all()
        if User.query.first():
            return
        u1 = User(username='alexrush', email='alex.rush@mail.bcu.ac.uk', password='pbkdf2:fakehash', degree='Computer Science', year_of_study=2)
        u2 = User(username='daniel', email='daniel.roberts4@mail.bcu.ac.uk', password='pbkdf2:fakehash', degree='Cyber Security', year_of_study=1)
        s1 = Society(name='Coding Club', description='We code together', category='Technical', contact_info='coding@bcu.ac.uk')
        s2 = Society(name='Gaming Society', description='Games and socials', category='Leisure', contact_info='gaming@bcu.ac.uk')
        i1 = Interest(name='Coding')
        i2 = Interest(name='Gaming')
        e1 = Event(society=s1, name='Weekly Workshop', description='Beginner workshop', event_date=datetime.utcnow(), location='Room 101')
        s1.interests.append(i1)
        s2.interests.append(i2)
        u1.interests.append(i1)
        u2.interests.append(i2)
        db.session.add_all([u1, u2, s1, s2, i1, i2, e1])
        db.session.commit()


if __name__ == '__main__':
    seed()
    print('Database seeded')
