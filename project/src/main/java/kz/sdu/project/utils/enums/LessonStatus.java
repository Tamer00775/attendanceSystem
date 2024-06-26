package kz.sdu.project.utils.enums;

public enum LessonStatus {
    // Teacher
    START_LESSON_PROCESS_IS_SUCCESSFULLY,
    END_LESSON_PROCESS_IS_SUCCESSFULLY,
    START_LESSON_PROCESS_IS_FAILED,
    END_LESSON_PROCESS_IS_FAILED,

    // Student

    JOIN_TO_SESSION_NOT_ON_TIME,
    JOIN_TO_SESSION_IS_ALREADY_DONE,
    UNEXPECTED_ISSUE_FROM_SYSTEM_DURING_JOIN_SESSION,
    SESSION_IS_CLOSED_BY_TEACHER,
    WRONG_SECRET_CODE_JOIN_SESSION,
    JOIN_SESSION_IS_ACCEPTED,
    LEAVING_SESSION_IS_DONE_ALREADY,
    U_CAN_LEAVE_AFTER_15_MIN,
    INITIALLY_YOU_SHOULD_ENTER_TO_SESSION,
    LEAVING_SESSION_IS_DONE
}
