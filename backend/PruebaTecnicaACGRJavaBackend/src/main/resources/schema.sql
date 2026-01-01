CREATE TABLE transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(19,2) NOT NULL,
    commission DECIMAL(19,2) NOT NULL,
    executed_at TIMESTAMP NOT NULL
);
