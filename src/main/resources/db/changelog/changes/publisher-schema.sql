CREATE TABLE IF NOT EXISTS publisher (
    id INT NOT NULL AUTO_INCREMENT,
    publisher_name VARCHAR(45) NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id)
);