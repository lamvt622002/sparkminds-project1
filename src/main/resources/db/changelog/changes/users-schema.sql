CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    phone_number VARCHAR(45) UNIQUE NOT NULL,
    email VARCHAR(45) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    failed_login_attempts INT NOT NULL,
    lockout_time DATETIME,
    role INT NOT NULL,
    status INT NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (role) REFERENCES authorities (id)
);