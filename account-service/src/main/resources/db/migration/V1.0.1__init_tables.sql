CREATE TABLE accounts
(
    id         UUID PRIMARY KEY,
    currency   VARCHAR,
    balance    DECIMAL,
    owner_id   VARCHAR,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE reservations
(
    id             UUID PRIMARY KEY,
    account_id     UUID,
    amount         DECIMAL,
    transaction_id VARCHAR(255),
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP
);

CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);