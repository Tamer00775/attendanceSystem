INSERT INTO person (first_name, last_name, middle_name, login, email) VALUES
('Yesset', 'Assan', 'Zhumabekuly', '210107038', '210107038@stu.sdu.edu.kz'),
('Jane', 'Doe', 'Ann', 'jane_doe', 'jane_doe@example.com'),
('Mike', 'Smith', 'Brian', 'mike_smith', 'mike_smith@example.com'),
('Emily', 'Jones', 'Claire', 'emily_jones', 'emily_jones@example.com'),
('David', 'Brown', 'Lee', 'david_brown', 'david_brown@example.com')
ON CONFLICT (login) DO NOTHING;