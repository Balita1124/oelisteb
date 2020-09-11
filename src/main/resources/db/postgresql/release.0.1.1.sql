CREATE TABLE IF NOT EXISTS pays
(
    id         SERIAL,
    code       VARCHAR(255),
    nom        VARCHAR(255),
    devise     VARCHAR(255),
    regime_tva VARCHAR(255),
    zone       VARCHAR(255),
    CONSTRAINT pk_pays PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_pays_code ON pays (code);

ALTER SEQUENCE pays_id_seq RESTART WITH 100;


CREATE TABLE IF NOT EXISTS forme_juridiques
(
    id      SERIAL,
    code    VARCHAR(255),
    libelle VARCHAR(255),
    CONSTRAINT pk_forme_juridiques PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_forme_juridiques_code ON forme_juridiques (code);

ALTER SEQUENCE forme_juridiques_id_seq RESTART WITH 100;



CREATE TABLE IF NOT EXISTS clients
(
    id         SERIAL,
    code       VARCHAR(30),
    siret       VARCHAR(30),
    siret DATE,
    type_id    INT NOT NULL,
    owner_id   INT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES owners (id),
    FOREIGN KEY (type_id) REFERENCES types (id),
    CONSTRAINT pk_pets PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_pets_name ON pets (name);

ALTER SEQUENCE pets_id_seq RESTART WITH 100;

