CREATE TABLE IF NOT EXISTS otp_change_phone (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    new_phone_number VARCHAR(45) NOT NULL,
    otp VARCHAR(6) NOT NULL,
    is_used INT NOT NULL,
    expire_time DATETIME NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);