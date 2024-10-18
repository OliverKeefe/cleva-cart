CREATE DATABASE IF NOT EXISTS AuthServiceDB;

USE AuthServiceDB;

CREATE TABLE Role (
    id INT PRIMARY KEY,
    name VARCHAR(32)
);

CREATE TABLE User (
    id BINARY(16) PRIMARY KEY,
    username VARCHAR(24),
    password VARCHAR(60),
    email VARCHAR(65),
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES Role(id)
);
