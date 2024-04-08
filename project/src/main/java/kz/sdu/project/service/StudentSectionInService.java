package kz.sdu.project.service;

import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.entity.*;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import static kz.sdu.project.utils.Constants.*;
import static kz.sdu.project.utils.Constants.USE_NUMBERS_IN_SECRET_CODE;
import static kz.sdu.project.utils.enums.LessonStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class StudentSectionInService {
    private final AttendanceRecordService attendanceRecordService;
    private final SectionService sectionService;
    private final CheckInForSessionService checkInForSessionService;
    private final AttendanceInfoService attendanceInfoService;
    private final SecretCodeForCheckInService secretCodeForCheckInService;
    private static Clock utcClock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));
    public String studentInProcess(SectionInRequest sectionInRequest) {
        Person student = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        Section section = sectionService.findByName(sectionInRequest.getSection())
                .orElseThrow(EntityNotFoundException::new);
        Schedule schedule = section.getSchedule();
        Optional<CheckInForSession> checkInForSession = checkInForSessionService
                .findByPersonIdAndScheduleId(student.getId(), schedule.getScheduleId());
        if (!canJoinSession(schedule)) {
            return JOIN_TO_SESSION_NOT_ON_TIME.name();
        }
        if (checkInForSession.isPresent() && joinedSessionAtThisHour(checkInForSession)) {
            return JOIN_TO_SESSION_IS_ALREADY_DONE.name();
        }

        return joinSession(student, schedule, section,sectionInRequest.getCode());
    }

    private String joinSession(Person person, Schedule schedule, Section section, String code) {
        Optional<AttendanceRecord> attendanceRecordOptional = attendanceRecordService
                .findByPersonIdAndScheduleIdAndCurrentWeek(
                        person.getId(),schedule.getScheduleId(),getCurrentWeek());
        Optional<AttendanceInfo> attendanceInfoOptional = attendanceInfoService
                .findByPersonIdAndSectionId(person.getId(), section.getSectionId());
        Optional<SecretCodeForCheckIn> secretCodeForCheckInOptional = secretCodeForCheckInService
                .findByScheduleId(schedule.getScheduleId());
        Optional<CheckInForSession> checkInForSessionOptional = checkInForSessionService
                .findByPersonIdAndScheduleId(person.getId(), schedule.getScheduleId());
        if (attendanceRecordOptional.isEmpty() || attendanceInfoOptional.isEmpty()
            || secretCodeForCheckInOptional.isEmpty()) {
            return UNEXPECTED_ISSUE_FROM_SYSTEM_DURING_JOIN_SESSION.name();
        }


        AttendanceRecord attendanceRecord = attendanceRecordOptional.get();
        AttendanceInfo attendanceInfo = attendanceInfoOptional.get();
        SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInOptional.get();

        if (secretCodeForCheckIn.getIs_interpreted()) {
            return SESSION_IS_CLOSED_BY_TEACHER.name();
        }
        secretCodeForCheckIn = updateSecretCodeIfNeeded(secretCodeForCheckIn);

        if (!secretCodeForCheckIn.getSecret_code().equals(code))
            return WRONG_SECRET_CODE_JOIN_SESSION.name();

        return joinedSession(attendanceInfo, attendanceRecord,checkInForSessionOptional, schedule, person);
    }

    private String joinedSession(AttendanceInfo attendanceInfo, AttendanceRecord attendanceRecord, Optional<CheckInForSession> checkInForSessionOptional, Schedule schedule, Person person) {
        attendanceRecord.setTotal_present_hours(attendanceRecord.getTotal_present_hours() + 1);
        attendanceInfo.setPercent(attendanceInfo.getPercent() - 1);
        attendanceInfoService.save(attendanceInfo);
        attendanceRecordService.save(attendanceRecord);

        CheckInForSession checkInForSession;
        if (checkInForSessionOptional.isPresent()) {
            checkInForSession = checkInForSessionOptional.get();
        } else {
            checkInForSession = new CheckInForSession();
            checkInForSession.setSchedule(schedule);
            checkInForSession.setPerson_checkin(person);
        }
        checkInForSession.setGet_passed(LocalDateTime.now(utcClock));
        checkInForSessionService.save(checkInForSession);
        return JOIN_SESSION_IS_ACCEPTED.name();
    }

    private SecretCodeForCheckIn updateSecretCodeIfNeeded(SecretCodeForCheckIn secretCodeForCheckIn) {

        LocalDateTime now = LocalDateTime.now(utcClock);
        LocalDateTime request = secretCodeForCheckIn.getCreated();
        long minutesDiff = Math.abs(Duration.between(now, request).toMinutes());
        log.info("minutesDiff ; {}", minutesDiff);
        if (minutesDiff > TIME_INTERVAL_BETWEEN_SECRET_CODE) {
            String generateSecretCode = RandomStringUtils
                    .random(SIX_SIZED_SECRET_CODE, USE_LETTERS_IN_SECRET_CODE, USE_NUMBERS_IN_SECRET_CODE);
            secretCodeForCheckIn.setSecret_code(generateSecretCode);
            secretCodeForCheckIn.setCreated(now);
            log.info("SecretCodeForCheckIn is changed...");
        }
        return secretCodeForCheckInService.save(secretCodeForCheckIn);
    }

    private boolean canJoinSession(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now(utcClock);
        int startHour = schedule.getStartTime(),
                endHour = startHour + schedule.getTotalHours();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        DayOfWeek dayOfWeek2 = DayOfWeek.of(schedule.getDayOfWeek());
        return now.getMinute() <= STUDENT_CAN_JOIN_SESSION_UNTIL &&
                now.getHour() >= startHour &&
                now.getHour() < endHour &&
                dayOfWeek == dayOfWeek2;
    }

    private boolean joinedSessionAtThisHour(Optional<CheckInForSession> checkInForSession) {
        LocalDateTime now = LocalDateTime.now(utcClock);
        LocalDateTime request = checkInForSession.get().getGet_passed();
        long minutesDiff = Math.abs(Duration.between(now, request).toMinutes());
        return minutesDiff < JOIN_SESSION_RANGE;
    }

    private Integer getCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        LocalDate now = LocalDate.now();
        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
        return diffInDays / 7 + 1;
    }
}
