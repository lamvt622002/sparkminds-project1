CREATE TABLE IF NOT EXISTS user_session (
    session_id BINARY(16) NOT NULL,
    user_id INT NOT NULL,
    end_at DATETIME NOT NULL,
    status INT NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (session_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);