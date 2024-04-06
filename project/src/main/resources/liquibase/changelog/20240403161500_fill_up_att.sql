INSERT INTO attendance_record (person_id, schedule_id, total_present_hours, total_hours, current_week, record_type, is_with_reason, is_interpreted)
VALUES
    (2, 5, 2, 2, 1, 'CARD', FALSE, FALSE),
    (2, 5, 1, 2, 2, 'CARD', FALSE, FALSE),
    (2, 5, 0, 2, 3, 'CARD', TRUE, FALSE),
    (2, 5, 1, 2, 4, 'CARD', FALSE, FALSE),
    (2, 5, 2, 2, 5, 'CARD', FALSE, FALSE),
    (2, 5, 1, 2, 6, 'CARD', FALSE, FALSE),
    (2, 5, 0, 2, 7, 'CARD', TRUE, FALSE),
    (2, 5, 2, 2, 8, 'CARD', FALSE, FALSE),
    (2, 5, 1, 2, 9, 'CARD', FALSE, FALSE),
    (2, 5, 2, 2, 10,'CARD', FALSE, FALSE);


delete from attendance_info
where person_id = 2 and  section_id = 5;

INSERT INTO attendance_info (person_id, percent, full_time, reason_time, section_id)
VALUES
    (2, 4, 20, 4, 5);
