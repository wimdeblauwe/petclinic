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

CREATE TABLE visit
(
    id               UUID PRIMARY KEY,
    veterinarian_id  UUID references veterinarian (id),
    owner_id         UUID references owner (id),
    pet_id           UUID references pet (id),
    appointment_time TIMESTAMPTZ
)
