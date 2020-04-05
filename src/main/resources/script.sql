CREATE SCHEMA hotel_storage;

CREATE TABLE hotel_storage.guests(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    surname VARCHAR(256) NOT NULL,
    patronymic VARCHAR(256),
    phoneNumber VARCHAR(256) NOT NULL,
    birthDate TIMESTAMP WITH TIME ZONE,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES hotel_storage.users (id) ON DELETE CASCADE
);

CREATE TABLE hotel_storage.users(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256) UNIQUE NOT NULL ,
    password VARCHAR(256) NOT NULL
);

CREATE TABLE hotel_storage.rooms(
    id BIGSERIAL PRIMARY KEY,
    number INTEGER UNIQUE NOT NULL,
    places VARCHAR(256) NOT NULL,
    level VARCHAR(256),
    rent INTEGER
);

CREATE TABLE hotel_storage.orders(
    id BIGSERIAL PRIMARY KEY,
    guest_id INTEGER NOT NULL,
    room_id INTEGER NOT NULL,
    settlementDate TIMESTAMP NOT NULL,
    leaveDate TIMESTAMP NOT NULL,
    settleState VARCHAR(256) NOT NULL,
    rent INTEGER,
    FOREIGN KEY (guest_id) REFERENCES hotel_storage.guests (id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES hotel_storage.rooms (id) ON DELETE CASCADE
);

