CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    account_number VARCHAR(20) PRIMARY KEY,
    user_id INT,
    account_holder VARCHAR(100) NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0,
    account_type ENUM('SAVINGS', 'CURRENT') NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20),
    transaction_type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER_OUT', 'TRANSFER_IN') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    related_account VARCHAR(20),
    balance_after DECIMAL(15,2) NOT NULL,
    description VARCHAR(200),
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- Insert sample data
INSERT INTO users (username, password, full_name, email) VALUES
('john', 'password123', 'John Doe', 'john@email.com'),
('jane', 'password123', 'Jane Smith', 'jane@email.com');

INSERT INTO accounts (account_number, user_id, account_holder, balance, account_type) VALUES
('SA001', 1, 'John Doe', 1000.00, 'SAVINGS'),
('CA001', 2, 'Jane Smith', 500.00, 'CURRENT');