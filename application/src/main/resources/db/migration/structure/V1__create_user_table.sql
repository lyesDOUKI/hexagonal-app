-- Création de la séquence pour l'ID
CREATE SEQUENCE IF NOT EXISTS users_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 1000000
    CACHE 1;

-- Création de la table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
    user_name VARCHAR(255),
    user_surname VARCHAR(255),
    user_email VARCHAR(255),
    user_birthdate DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    keycloak_id UUID NOT NULL UNIQUE
);
