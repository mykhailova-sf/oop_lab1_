CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    login     VARCHAR(255) UNIQUE NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    role      VARCHAR(50)         NOT NULL,
    full_name text default 'Батькін Батько Батькович'
);
CREATE TABLE appointment
(
    id         SERIAL PRIMARY KEY,
    user_id    int not null references users (id) on delete cascade,
    operations text,
    procedures text,
    drugs      text,
    info       text,
    discharged bool default false
);