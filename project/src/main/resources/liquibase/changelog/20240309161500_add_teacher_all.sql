INSERT INTO person (first_name, last_name, middle_name, login, email) VALUES
('Benjamin', 'Hill', 'Charles', 'benjamin_hill', 'benjamin_hill@example.com'),
('Zoe', 'Scott', 'Alice', 'zoe_scott', 'zoe_scott@example.com'),
('Logan', 'Adams', 'Daniel', 'logan_adams', 'logan_adams@example.com'),
('Lily', 'Baker', 'Jean', 'lily_baker', 'lily_baker@example.com'),
('Khamza', 'Kabylbek', 'Kanatbek', 'khamzaaa01', 'khamzaaa01@example.com')
ON CONFLICT (login) DO NOTHING;