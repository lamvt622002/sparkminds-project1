CREATE TABLE IF NOT EXISTS project1.refresh_token (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    token TEXT NOT NULL,
    expire_time DATETIME NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES project1.users (id)
);
