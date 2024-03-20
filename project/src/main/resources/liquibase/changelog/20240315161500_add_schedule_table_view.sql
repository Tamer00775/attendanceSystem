CREATE VIEW schedule_table AS
SELECT
    s.schedule_id,
    s.day_of_week,
    s.start_time,
    s.start_time + s.total_hours AS end_time,
    cr.room_number,
    c.code,
    sec.type
FROM
    schedule s
        JOIN
    class_room cr ON s.class_room_id = cr.class_room_id
        JOIN
    section sec ON s.section_id = sec.section_id
        JOIN
    course c ON sec.course_id = c.course_id;