CREATE TABLE IF NOT EXISTS test (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    created_by VARCHAR(45) NOT NULL,
    created_at DATETIME,
    last_modified_by VARCHAR(45) NOT NULL
)