
CREATE TABLE IF NOT EXISTS book (
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT NOT NULL,
    language_id INT NOT NULL,
    category_id INT NOT NULL,
    publisher_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    num_of_pages INT NOT NULL,
    description TEXT NOT NULL,
    quantity INT NOT NULL,
    image VARCHAR(255) NOT NULL,
    created_by VARCHAR(45),
    created_at DATETIME,
    last_modified_by VARCHAR(45),
    updated_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (language_id) REFERENCES book_language (id),
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);