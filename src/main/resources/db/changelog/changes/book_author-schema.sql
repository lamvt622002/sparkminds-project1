CREATE TABLE IF NOT EXISTS book_author (
    book_id INT NOT NULL,
    author_id INT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
);