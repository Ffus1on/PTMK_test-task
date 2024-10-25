CREATE TABLE IF NOT EXISTS employees
(
    id SERIAL PRIMARY KEY,
    fullName VARCHAR(100),
    birthDate DATE,
    sex VARCHAR(6)
);

