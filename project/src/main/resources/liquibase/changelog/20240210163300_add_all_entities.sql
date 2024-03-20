-- 1. Person
CREATE TABLE person (
                        id serial PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        middle_name VARCHAR(255),
                        login VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE
);

-- 2. Role
CREATE TABLE role (
                      role_id serial PRIMARY KEY,
                      role VARCHAR(255) NOT NULL,
                      description TEXT
);

-- 3. Person Role
CREATE TABLE person_role (
                             person_id INT,
                             role_id INT,
                             PRIMARY KEY (person_id, role_id),
                             FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
                             FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE
);

-- 5. Person Authority
CREATE TABLE person_auth (
                             person_authority_id serial PRIMARY KEY,
                             person_id INT NOT NULL,
                             last_login date,
                             password_hash TEXT NOT NULL,
                             active BOOLEAN NOT NULL,
                             password_refresh_date DATE,
                             isRefreshed BOOLEAN NOT NULL,
                             FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

-- 6. Specialty
CREATE TABLE speciality (
                            speciality_id serial PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE,
                            code VARCHAR(50) NOT NULL UNIQUE
);

-- 7. Course
CREATE TABLE course (
                        course_id serial PRIMARY KEY,
                        speciality_id INT,
                        code VARCHAR(50) NOT NULL UNIQUE,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        description TEXT,
                        FOREIGN KEY (speciality_id) REFERENCES speciality(speciality_id) ON DELETE CASCADE
);

-- 8. Person Info
CREATE TABLE person_info (
                             person_info_id serial PRIMARY KEY,
                             person_id INT NOT NULL,
                             gender VARCHAR(255) NOT NULL,
                             imageData BYTEA,
                             telephone VARCHAR(255) NOT NULL,
                             birth_date DATE NOT NULL,
                             speciality_id INT,
                             FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
                             FOREIGN KEY (speciality_id) REFERENCES speciality(speciality_id) ON DELETE SET NULL
);

-- 9. Class Room
CREATE TABLE class_room (
                            class_room_id serial PRIMARY KEY,
                            room_number VARCHAR(50) NOT NULL UNIQUE
);

-- 10. Section
CREATE TABLE section (
                         section_id serial PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         course_id INT,
                         quota INT,
                         count INT,
                         type VARCHAR(255),
                         related_section_name VARCHAR(255),
                         FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE SET NULL
);

-- 11. Schedule
CREATE TABLE schedule (
                          schedule_id serial PRIMARY KEY,
                          section_id INT,
                          class_room_id INT,
                          day_of_week INT NOT NULL,
                          start_time INT,
                          total_hours INT,
                          FOREIGN KEY (section_id) REFERENCES section(section_id) ON DELETE CASCADE,
                          FOREIGN KEY (class_room_id) REFERENCES class_room(class_room_id) ON DELETE SET NULL
);


-- 12. Reason For Absence
CREATE TABLE reason_for_absence (
                                    reason_id serial PRIMARY KEY,
                                    person_id INT,
                                    section_id INT,
                                    description TEXT,
                                    document TEXT,
                                    status VARCHAR(255),
                                    is_accepted BOOLEAN,
                                    date_info DATE NOT NULL,
                                    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
                                    FOREIGN KEY (section_id) REFERENCES section(section_id) ON DELETE CASCADE
);

-- 13. Attendance Record
CREATE TABLE attendance_record (
                                   record_id serial PRIMARY KEY,
                                   person_id INT,
                                   schedule_id INT,
                                   total_present_hours INT,
                                   total_hours INT,
                                   current_week INT,
                                   record_type VARCHAR(255) NOT NULL,
                                   is_with_reason BOOLEAN NOT NULL,
                                   FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
                                   FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id) ON DELETE CASCADE
);

-- 14. Attendance Info
CREATE TABLE attendance_info (
                                 attendance_info_id serial PRIMARY KEY,
                                 person_id INT,
                                 percent INT NOT NULL,
                                 full_time INT NOT NULL,
                                 reason_time INT NOT NULL,
                                 section_id INT NOT NULL,
                                 FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
                                 FOREIGN KEY (section_id) REFERENCES section(section_id) ON DELETE CASCADE
);

-- 15. Secret Code for Check-in
CREATE TABLE secret_code_for_check_in (
                                          secret_code_for_check_in_id serial PRIMARY KEY,
                                          schedule_id INT NOT NULL,
                                          secret_code VARCHAR(255) NOT NULL,
                                          created timestamp NOT NULL,
                                          FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id) ON DELETE CASCADE
);

-- 16. Check-in for Session
CREATE TABLE check_in_for_session (
                                      check_id serial PRIMARY KEY,
                                      schedule_id INT NOT NULL,
                                      person_id INT NOT NULL,
                                      get_passed timestamp NOT NULL,
                                      FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id) ON DELETE CASCADE,
                                      FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

-- 4. Section Person
CREATE TABLE section_person (
                                section_id INTEGER NOT NULL,
                                person_id INTEGER NOT NULL,
                                PRIMARY KEY (section_id, person_id),
                                FOREIGN KEY (section_id) REFERENCES section(section_id) ON DELETE CASCADE,
                                FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);

