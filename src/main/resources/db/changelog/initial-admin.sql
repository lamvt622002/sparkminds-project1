INSERT INTO users (phone_number, email, password, failed_login_attempts, lockout_time, role, status, created_by, created_at, last_modified_by, updated_at)
VALUES ('+84896424478', 'admin@gmail.com', '$2a$10$fnN6DvksHY6CF6vBqojwMuNKfjJkS1yn2e1RgZorUl0qs8.X1221u', 0, NULL, 1, 1, 'anonymousUser', NOW(), 'anonymousUser', NOW());

INSERT INTO staff (id, full_name, hire_date)
VALUES (1, 'Admin', NOW());
