INSERT INTO person (first_name, last_name, middle_name, login, email) VALUES
('Tamerlan', 'Kartayev', 'Rustemuly', '210107177', '210107177@stu.sdu.edu.kz');

INSERT INTO person_role (person_id, role_id) VALUES (1, 2);

INSERT INTO person_authority (person_id, last_login, password_hash, active, user_role_id, isRefreshed) VALUES
(1, '2024-03-03', '$2a$10$HtCYkcjoZMtEX3oZLzb5nOzU0qCkiH.STn2/dBJeX8EespWTTcyyW', TRUE, 2, FALSE);

INSERT INTO person_info (person_id, gender, imageData, telephone, birth_date, speciality_id) VALUES
(1, 'Male', NULL, '+12345678901', '01.01.2003', NULL);