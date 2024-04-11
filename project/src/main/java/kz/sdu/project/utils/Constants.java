package kz.sdu.project.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;

public class Constants {
    public static final ZoneId zoneId = ZoneId.of("Asia/Oral");
    public static final String START_DATE_FOR_SEMESTER = "22.01.2024";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String TIME_FORMAT = "hh:mm a";
    public static final String SEPARATE_BY_COMMA = ",";
    public static final String SEPARATE_BY_POINT = ".";
    public static final Integer SIX_SIZED_SECRET_CODE = 6;
    public static final Integer EIGHT_SIZED_SECRET_CODE = 8;
    public static final Integer ABSENCE_COEFFICIENT = 2;
    public static final Integer TIME_INTERVAL_BETWEEN_SECRET_CODE = 2;
    public static final Integer LESSON_START_TIME = 8;
    public static final Integer LESSON_END_TIME = 20;
    public static final Integer TEACHER_START_LESSON_TIME_IS_UNTIL = 15;
    public static final Integer TEACHER_END_LESSON_TIME_STARTS_FROM = 15;
    public static final Integer STUDENT_CAN_JOIN_SESSION_UNTIL = 15;
    public static final Integer STUDENT_CAN_LEFT_SESSION_FROM = 15;
    public static final Integer JOIN_SESSION_RANGE = 45;
    public static final Integer LEFT_SESSION_RANGE = 60;
    public static final Integer ACTIVE_SESSION_TIME_TO_BE_COUNTED = 30;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final Boolean USE_LETTERS_IN_SECRET_CODE = true;
    public static final Boolean USE_NUMBERS_IN_SECRET_CODE = true;
}
