package kz.sdu.project.service;

import kz.sdu.project.dto.RequestBody3DTO;
import kz.sdu.project.entity.*;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static kz.sdu.project.domain.ActionStatus.MANUALLY;
import static kz.sdu.project.utils.Constants.*;
import static kz.sdu.project.utils.enums.LessonStatus.*;

@Service
@Slf4j
@AllArgsConstructor
public class TeacherStartLessonService {
    private final SectionService sectionService;
    private final AttendanceRecordService attendanceRecordService;
    private final AttendanceInfoService attendanceInfoService;
    private final SecretCodeForCheckInService secretCodeForCheckInService;
    private static Clock utcClock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));
    public Map<String, String> start(RequestBody3DTO requestBody3DTO) {

        Person teacher = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        String sectionName = requestBody3DTO.getSectionName();
        Section section = sectionService.findByName(sectionName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Section with name %s not found", sectionName)));
        Schedule schedule = section.getSchedule();
        if (schedule == null)
            throw new EntityNotFoundException(String.format("Schedule not created for section %s ", section));

        if (!canStartLesson(schedule)) {
            return Map.of("status",START_LESSON_PROCESS_IS_FAILED.toString());
        }
        SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInService
                .findByScheduleId(schedule.getScheduleId())
                        .orElse(null);
        if (secretCodeForCheckIn != null && isTheSameDay(secretCodeForCheckIn.getCreated())) {
            secretCodeForCheckIn = updateSecretCodeIfNeeded(secretCodeForCheckIn);
            return Map.of("secretCode", secretCodeForCheckIn.getSecret_code(),
                    "status",START_LESSON_PROCESS_IS_SUCCESSFULLY.name());
        }
        initializeAttInfoAndRecord(section, teacher);
        initializeSecretCode(schedule);

        secretCodeForCheckIn = secretCodeForCheckInService
                .findByScheduleId(schedule.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException("SecretCode not generated \nTry again..."));
        String secretCode = secretCodeForCheckIn.getSecret_code();

        return Map.of("secretCode", secretCode,
                "status",START_LESSON_PROCESS_IS_SUCCESSFULLY.name());
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

    private boolean canStartLesson(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now(utcClock);
        int startHour = schedule.getStartTime(),
             endHour = startHour + schedule.getTotalHours();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        DayOfWeek dayOfWeek2 = DayOfWeek.of(schedule.getDayOfWeek());
        return now.getMinute() <= TEACHER_START_LESSON_TIME_IS_UNTIL &&
                now.getHour() >= startHour &&
                now.getHour() < endHour &&
                dayOfWeek == dayOfWeek2;
    }

    private boolean canEndLesson(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now(utcClock);
        int startHour = schedule.getStartTime(),
                endHour = startHour + schedule.getTotalHours();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        DayOfWeek dayOfWeek2 = DayOfWeek.of(schedule.getDayOfWeek());
        return now.getMinute() >= TEACHER_END_LESSON_TIME_STARTS_FROM &&
                now.getHour() >= startHour &&
                now.getHour() < endHour &&
                dayOfWeek == dayOfWeek2;
    }

    private boolean isTheSameDay(LocalDateTime created) {
        LocalDateTime now = LocalDateTime.now(utcClock);
        return (created.getYear() == now.getYear() &&
                created.getMonth() == now.getMonth() &&
                created.getDayOfMonth() == now.getDayOfMonth());
    }

    private void initializeSecretCode(Schedule schedule) {
        Optional<SecretCodeForCheckIn> secretCodeForCheckInOptional = secretCodeForCheckInService
                .findByScheduleId(schedule.getScheduleId());
        String generateSecretCode = RandomStringUtils
                .random(SIX_SIZED_SECRET_CODE, USE_LETTERS_IN_SECRET_CODE, USE_NUMBERS_IN_SECRET_CODE);
        LocalDateTime now = LocalDateTime.now(utcClock);

        if (secretCodeForCheckInOptional.isPresent()) {
            SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInOptional.get();
            secretCodeForCheckIn.setSecret_code(generateSecretCode);
            secretCodeForCheckIn.setCreated(now);
            secretCodeForCheckIn.setIs_interpreted(false);
            secretCodeForCheckInService.save(secretCodeForCheckIn);
            return;
        }

        SecretCodeForCheckIn secretCodeForCheckIn = new SecretCodeForCheckIn();
        secretCodeForCheckIn.setSchedule_checkin(schedule);
        secretCodeForCheckIn.setSecret_code(generateSecretCode);
        secretCodeForCheckIn.setCreated(now);
        secretCodeForCheckIn.setIs_interpreted(false);
        secretCodeForCheckInService.save(secretCodeForCheckIn);
    }

    private void initializeAttInfoAndRecord(Section section, Person teacher) {
        Schedule schedule = section.getSchedule();
        List<Person> students = section.getPersons().stream()
                .filter(student -> !Objects.equals(student.getId(), teacher.getId()))
                .collect(Collectors.toList());
        if (students.size() == section.getPersons().size()) {
            throw new IllegalArgumentException(String.format("Teacher with username %s not for section : %s", teacher.getLogin(), section.getName()));
        }
        students.forEach(student -> initializeAttRecord(student, schedule, getCurrentWeek()));
        students.forEach(student -> initializeAttInfo(student, section));
    }

    private void initializeAttInfo(Person student, Section section) {

        int totalHours = section.getSchedule().getTotalHours();
        Optional<AttendanceInfo> attendanceInfoOptional = attendanceInfoService
                .findByPersonIdAndSectionId(student.getId(), section.getSectionId());
        if (attendanceInfoOptional.isEmpty()) {
            AttendanceInfo attendanceInfo = new AttendanceInfo();
            attendanceInfo.setPerson_attendanceInfo(student);
            attendanceInfo.setPercent(totalHours);
            attendanceInfo.setFull_time(totalHours);
            attendanceInfo.setReason_time(0);
            attendanceInfo.setSection_att_info(section);
            attendanceInfoService.save(attendanceInfo);
            return;
        }
        AttendanceInfo attendanceInfo = attendanceInfoOptional.get();
        attendanceInfo.setPercent(attendanceInfo.getPercent() + totalHours);
        attendanceInfo.setFull_time(attendanceInfo.getFull_time() + totalHours);
        attendanceInfoService.save(attendanceInfo);
    }

    private void initializeAttRecord(Person student, Schedule schedule, Integer currentWeek) {

        int stu_id = student.getId(),
                sch_id = schedule.getScheduleId(),
                totalHours = schedule.getTotalHours();

        Optional<AttendanceRecord> attendanceRecordOptional = attendanceRecordService
                              .findByPersonIdAndScheduleIdAndCurrentWeek(stu_id,sch_id,currentWeek);
        if (attendanceRecordOptional.isEmpty()) {
            AttendanceRecord attendanceRecord = new AttendanceRecord();
            attendanceRecord.setPerson_att_record(student);
            attendanceRecord.setSchedule_att_record(schedule);
            attendanceRecord.setTotal_present_hours(0);
            attendanceRecord.setTotal_hours(totalHours);
            attendanceRecord.setCurrentWeek(currentWeek);
            attendanceRecord.setRecord_type(MANUALLY.name());
            attendanceRecord.setIs_with_reason(false);
            attendanceRecordService.save(attendanceRecord);
        }
    }

    private Integer getCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        LocalDate now = LocalDate.now();
        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
        return diffInDays / 7 + 1;
    }

    public Map<String, String> end(RequestBody3DTO requestBody3DTO) {
        String sectionName = requestBody3DTO.getSectionName();
        Section section = sectionService.findByName(sectionName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Section with name %s not found", sectionName)));
        Schedule schedule = section.getSchedule();
        if (schedule == null)
            throw new EntityNotFoundException(String.format("Schedule not created for section %s ", section));

        SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInService
                .findByScheduleId(schedule.getScheduleId())
                .orElse(null);
        if(canEndLesson(schedule) && secretCodeForCheckIn != null) {
            secretCodeForCheckIn.setIs_interpreted(true);
            secretCodeForCheckInService.save(secretCodeForCheckIn);
            return Map.of("status", END_LESSON_PROCESS_IS_SUCCESSFULLY.name());
        }

        return Map.of("status", END_LESSON_PROCESS_IS_FAILED.name());
    }
}
