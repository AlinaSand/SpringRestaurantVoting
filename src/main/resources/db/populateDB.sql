DELETE FROM votes;
DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@mail.com', '{noop}admin'),
       ('User1', 'user1@mail.com', '{noop}password1'),
       ('User2', 'user2@mail.com', '{noop}password2');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'ROLE_ADMIN'),
       (100000, 'ROLE_USER'),
       (100001, 'ROLE_USER'),
       (100002, 'ROLE_USER');

INSERT INTO restaurants (name)
VALUES ('Stolovaya'),
       ('Zabegalovka na Ordynke'),
       ('Good Cafe'),
       ('Dishes');

INSERT INTO dishes (name, date, price, restaurant_id)
VALUES ('Borsh', '2020-04-15', 250, 100003),
       ('Rice', '2020-04-15', 300, 100003),
       ('Coffee', '2020-04-15', 100, 100003),
       ('Soup day', '2020-04-15', 200, 100004),
       ('Grecha', '2020-04-15', 200, 100004),
       ('Coffee', '2020-04-15', 200, 100004),
       ('Harcho', CURRENT_DATE, 180, 100005),
       ('Belyash', CURRENT_DATE, 100, 100005),
       ('Bear', CURRENT_DATE, 200, 100005);

INSERT INTO votes (date, user_id, restaurant_id)
VALUES ('2020-04-15', 100001, 100003),
       ('2020-04-15', 100002, 100004),
       (now(), 100001, 100005);