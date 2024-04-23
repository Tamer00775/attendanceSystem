INSERT INTO person (first_name, last_name, middle_name, login, email) VALUES
('Yesset', 'Assan', 'Zhumabekuly', '210107038', '210107038@stu.sdu.edu.kz'),
('Jane', 'Doe', 'Ann', 'jane_doe', 'jane_doe@example.com'),
('Mike', 'Smith', 'Brian', 'mike_smith', 'mike_smith@example.com'),
('Emily', 'Jones', 'Claire', 'emily_jones', 'emily_jones@example.com'),
('David', 'Brown', 'Lee', 'david_brown', 'david_brown@example.com'),
('Alice', 'Green', 'Marie', 'alice_green', 'alice_green@example.com'),
('Bob', 'White', 'James', 'bob_white', 'bob_white@example.com'),
('Charlie', 'Black', 'Edward', 'charlie_black', 'charlie_black@example.com'),
('Daisy', 'Gray', 'Elizabeth', 'daisy_gray', 'daisy_gray@example.com'),
('Ethan', 'Hill', 'Robert', 'ethan_hill', 'ethan_hill@example.com'),
('Fiona', 'Clark', 'Anne', 'fiona_clark', 'fiona_clark@example.com'),
('George', 'Lewis', 'Paul', 'george_lewis', 'george_lewis@example.com'),
('Hannah', 'Walker', 'Jane', 'hannah_walker', 'hannah_walker@example.com'),
('Ian', 'Turner', 'Richard', 'ian_turner', 'ian_turner@example.com'),
('Jessica', 'Moore', 'Patricia', 'jessica_moore', 'jessica_moore@example.com')
ON CONFLICT (login) DO NOTHING;