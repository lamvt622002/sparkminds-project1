CREATE TABLE IF NOT EXISTS book_language (
    id INT NOT NULL AUTO_INCREMENT,
    language_name VARCHAR(45) NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id)
);