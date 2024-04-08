package kz.sdu.project.service;

import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.dto.SectionResponseDto;
import kz.sdu.project.entity.*;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static kz.sdu.project.domain.Constants.*;
import static kz.sdu.project.domain.Constants.USE_NUMBERS_IN_SECRET_CODE;

@Service
@AllArgsConstructor
@Slf4j
public class StudentSectionInService {
    private final AttendanceRecordService attendanceRecordService;
    private final SectionService sectionService;
    private final CheckInForSessionService checkInForSessionService;
    private final AttendanceInfoService attendanceInfoService;
    private final SecretCodeForCheckInService secretCodeForCheckInService;

    public SectionResponseDto studentInProcess(SectionInRequest sectionInRequest) {
        SectionResponseDto sectionResponseDto = new SectionResponseDto();
        Optional<Person> personOptional = Optional.ofNullable(SecurityUtils.getCurrentPerson());
        Person person = personOptional.get();
        Section section = sectionService.findByName(sectionInRequest.getSection())
                .orElseThrow(EntityNotFoundException::new);
        Schedule schedule = section.getSchedule();
        Optional<CheckInForSession> checkInForSession = checkInForSessionService
                .findByPersonIdAndScheduleId(person.getId(), schedule.getScheduleId());
        if (!canJoinSession(schedule)) {
            sectionResponseDto.setCode("JOIN_TO_SESSION_NOT_ON_TIME");
            return sectionResponseDto;
        }
        if (checkInForSession.isPresent() && joinedSessionAtThisHour(checkInForSession)) {
            sectionResponseDto.setCode("JOIN_TO_SESSION_IS_ALREADY_DONE");
            return sectionResponseDto;
        }

        return null;

        // return joinSession(person, schedule, section,sectionInRequest.getCode(), sectionResponseDto);
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
            return "UNEXPECTED_ISSUE_FROM_SYSTEM_DURING_JOIN_SESSION";
        }


        AttendanceRecord attendanceRecord = attendanceRecordOptional.get();
        AttendanceInfo attendanceInfo = attendanceInfoOptional.get();
        SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInOptional.get();

        if (secretCodeForCheckIn.getIs_interpreted()) {
            return "SESSION_IS_CLOSED_BY_TEACHER";
        }
        secretCodeForCheckIn = updateSecretCodeIfNeeded(secretCodeForCheckIn);

        if (!secretCodeForCheckIn.getSecret_code().equals(code))
            return "WRONG_SECRET_CODE_JOIN_SESSION";

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
        checkInForSession.setGet_passed(LocalDateTime.now());
        checkInForSessionService.save(checkInForSession);
        return "JOIN_SESSION_IS_ACCEPTED";
    }

    private SecretCodeForCheckIn updateSecretCodeIfNeeded(SecretCodeForCheckIn secretCodeForCheckIn) {

        LocalDateTime now = LocalDateTime.now();
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
        LocalDateTime now = LocalDateTime.now();
        int startHour = schedule.getStartTime(),
                endHour = startHour + schedule.getTotalHours();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        DayOfWeek dayOfWeek2 = DayOfWeek.of(schedule.getDayOfWeek());
        return now.getMinute() <= 15 &&
                now.getHour() >= startHour &&
                now.getHour() < endHour &&
                dayOfWeek == dayOfWeek2;
    }

    private boolean joinedSessionAtThisHour(Optional<CheckInForSession> checkInForSession) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime request = checkInForSession.get().getGet_passed();
        long minutesDiff = Math.abs(Duration.between(now, request).toMinutes());
        return minutesDiff < 45;
    }

    private Integer getCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        LocalDate now = LocalDate.now();
        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
        return diffInDays / 7 + 1;
    }
}
