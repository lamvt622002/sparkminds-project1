CREATE TABLE IF NOT EXISTS author (
    id INT NOT NULL AUTO_INCREMENT,
    last_name VARCHAR(45) NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    birth_day DATE NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id)
);