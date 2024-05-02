CREATE TABLE IF NOT EXISTS hold (
    id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    user_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status INT NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);




