-- Création de la séquence pour l'ID
CREATE SEQUENCE IF NOT EXISTS users_adresse_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 1000000
    CACHE 1;

-- Création de la table
CREATE TABLE IF NOT EXISTS users_adresse (
    adresse_id BIGINT PRIMARY KEY DEFAULT nextval('users_adresse_seq'),
    nom_adresse VARCHAR(255),
    complement_adresse VARCHAR(255),
    code_postal VARCHAR(5),
    ville VARCHAR(255),
    pays VARCHAR(255)
);

ALTER TABLE users
ADD COLUMN user_adresse_id BIGINT,
ADD CONSTRAINT fk_users_users_adresse FOREIGN KEY (user_adresse_id) REFERENCES users_adresse(adresse_id);

