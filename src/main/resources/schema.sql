DROP TABLE IF EXISTS  USERS, FRIENDS, STATUSES, MPA, FILMS, GENRE, GENRES_LIST, LIKES_LIST;

create table IF NOT EXISTS USERS
(
    user_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(20) not null,
    login     VARCHAR(20)  not null,
    email      VARCHAR(100) not null,
    birthday  DATE,
    UNIQUE (login),
    UNIQUE (email)
);

create table IF NOT EXISTS FRIENDS
(
    user_id   INTEGER REFERENCES USERS (user_id),
    friend_id INTEGER REFERENCES USERS (user_id),
    status    BOOLEAN not null,
    constraint friend_list_pk primary key (user_id, friend_id)
);


create table IF NOT EXISTS MPA
(
    mpa_id   INT PRIMARY KEY AUTO_INCREMENT not null ,
    mpa_name VARCHAR(20) not null,
    constraint MPA_PK primary key (mpa_id)
);


CREATE TABLE IF NOT EXISTS FILMS
(
    film_id     INT PRIMARY KEY AUTO_INCREMENT,
    film_name   VARCHAR(50)  not null,
    description VARCHAR(200) not null,
    release_date DATE,
    duration    INT CHECK (duration > 0),
    mpa_id      INT REFERENCES mpa (mpa_id),
    rating      INT
);


CREATE TABLE IF NOT EXISTS LIKES_LIST
(
    film_id INTEGER REFERENCES films (film_id),
    user_id INTEGER REFERENCES users (user_id),
    constraint likes_list_pk primary key (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS GENRE
(
    genre_id    INT PRIMARY KEY AUTO_INCREMENT,
    genre_name  VARCHAR(50) not null
);

CREATE TABLE IF NOT EXISTS GENRES_LIST
(
    film_id  INTEGER REFERENCES films (film_id),
    genre_id INTEGER REFERENCES genre (genre_id)
);