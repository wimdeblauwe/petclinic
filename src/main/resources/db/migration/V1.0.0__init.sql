CREATE TABLE owner
(
    id         UUID PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT,
    street     TEXT,
    city       TEXT,
    telephone  TEXT
);

CREATE TABLE pet
(
    id         UUID PRIMARY KEY,
    name       TEXT,
    birth_date DATE,
    type       TEXT,
    weight     INT,
    owner_id   UUID REFERENCES owner (id)
);

CREATE TABLE veterinarian
(
    id         UUID PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT
);

CREATE TABLE speciality
(
    id              UUID PRIMARY KEY,
    name            TEXT,
    since           DATE,
    veterinarian_id UUID references veterinarian (id)
);
