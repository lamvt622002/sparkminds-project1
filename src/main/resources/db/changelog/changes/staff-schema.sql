CREATE TABLE IF NOT EXISTS staff (
    id INT NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    hire_date DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES users (id)
);