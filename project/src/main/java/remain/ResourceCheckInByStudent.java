//package kz.sdu.project.resource;
//
//
//import kz.sdu.project.entity.*;
//import kz.sdu.project.service.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/student/checkIn")
//@Slf4j
//public class ResourceCheckInByStudent {
//
//    // Инициализация сервисов
//    private final PersonService personService;
//    private final CheckInForSessionService checkInForSessionService;
//    private final SectionService sectionService;
//    private final SecretCodeForCheckInService secretCodeForCheckInService;
//    private final AttendanceRecordService attendanceRecordService;
//
//    // Константы
//    private static final Long PERIOD_BETWEEN_REFRESHING = 2L;
//    private static final Integer SIX_SIZED_SECRET_CODE = 6;
//    private static final Boolean USE_LETTERS_IN_SECRET_CODE = true;
//    private static final Boolean USE_NUMBERS_IN_SECRET_CODE = true;
//    private static final String START_DATE_FOR_SEMESTER = "22.01.2024";
//    private static final Integer ACTIVE_TIME_LIMIT_MINUTES = 15;
//    private static final Integer MIN_TIME_BETWEEN_CHECK_INS_MINUTES = 45;
//
//
//    @Autowired
//    public ResourceCheckInByStudent(PersonService personService, CheckInForSessionService checkInForSessionService, SectionService sectionService, SecretCodeForCheckInService secretCodeForCheckInService, AttendanceRecordService attendanceRecordService) {
//        this.personService = personService;
//        this.checkInForSessionService = checkInForSessionService;
//        this.sectionService = sectionService;
//        this.secretCodeForCheckInService = secretCodeForCheckInService;
//        this.attendanceRecordService = attendanceRecordService;
//    }
//
//    /**
//     * Обрабатывает процесс регистрации студента на занятие по секции.
//     * Проверяет, зарегистрирован ли студент на указанную секцию, проверяет валидность секретного кода,
//     * обновляет запись о посещении и генерирует новый секретный код при необходимости.
//     * В случае успеха возвращает подтверждение регистрации.
//     *
//     * @param inData Тело запроса, содержащее логин студента, название секции и секретный код.
//     * @return ResponseEntity со статусом операции и соответствующим сообщением.
//     */
//    @PostMapping()
//    public ResponseEntity<String> processStudentCheckIn(@RequestBody @Valid InData inData) {
//
//        String login = inData.getLogin();
//        String sectionName = inData.getSectionName();
//        String password = inData.getPassword();
//
//
//        Person student = personService.findByLogin(login);
//        Section section = sectionService.findByName(sectionName);
//
//        if (isStudentRegisteredForSection(student, section)) {
//            return ResponseEntity.badRequest()
//                    .body("There's no neither Person, neither Section...");
//        }
//
//        Schedule schedule = section.getSchedule();
//        Optional<SecretCodeForCheckIn> secretCodeCheckIn = secretCodeForCheckInService
//                .findByScheduleId(schedule.getScheduleId());
//
//        if (!isCheckInWithinActiveTimeFrame(schedule)) {
//            return ResponseEntity.badRequest()
//                    .body("There's active until "+ACTIVE_TIME_LIMIT_MINUTES+" minutes...");
//        }
//        if (secretCodeCheckIn.isEmpty()) {
//            return ResponseEntity.badRequest()
//                    .body("Currently Secret Code doesn't exist for this Section...");
//        }
//
//        SecretCodeForCheckIn finalSecretCodeCheckIn = getUpdatedVersion(secretCodeCheckIn);
//        finalSecretCodeCheckIn = secretCodeForCheckInService.save(finalSecretCodeCheckIn);
//        String actualPassword = finalSecretCodeCheckIn.getSecret_code();
//
//        if (!actualPassword.equals(password)) {
//            return ResponseEntity.badRequest()
//                    .body("Secret Code is wrong for this Section...");
//        }
//
//        Optional<CheckInForSession> lastCheckInByStudent = checkInForSessionService
//                .findByPersonIdAndScheduleId(student.getId(), schedule.getScheduleId());
//
//        if (lastCheckInByStudent.isEmpty()) {
//            checkInForSessionService.save(CheckInForSession.builder()
//                            .schedule(schedule)
//                            .person_checkin(student)
//                            .get_passed(LocalDateTime.now())
//                            .build());
//            incrementStudentAttendanceRecord(student, schedule);
//            log.info("Student's firstly enrolled to this section...");
//            return ResponseEntity.ok()
//                    .body("You're successfully entered for this Section...");
//        }
//
//        LocalDateTime lastTimeByStudent = lastCheckInByStudent.get().getGet_passed();
//
//        if (!isCheckInTimeWithinLimit(lastTimeByStudent)) {
//            return ResponseEntity.badRequest()
//                    .body("There's for last active should be at least "+MIN_TIME_BETWEEN_CHECK_INS_MINUTES+" minutes ago...");
//        }
//
//        lastCheckInByStudent.get().setGet_passed(LocalDateTime.now());
//        checkInForSessionService.save(lastCheckInByStudent.get());
//        incrementStudentAttendanceRecord(student, schedule);
//        log.info("Student's enrolled to this section...");
//        return ResponseEntity.ok().body("You're successfully entered for this Section...");
//    }
//
//    /**
//     * Обновляет запись о посещаемости студента, увеличивая количество часов присутствия.
//     * Вызывается после успешной регистрации студента на секцию, учитывая текущую неделю семестра.
//     *
//     * @param student Студент, для которого обновляется запись.
//     * @param schedule Расписание занятий секции.
//     */
//    private void incrementStudentAttendanceRecord(Person student, Schedule schedule) {
//        Integer currentWeek = calculateCurrentWeekOfSemester();
//        AttendanceRecord attendanceRecord = attendanceRecordService
//                .findByPersonIdAndScheduleIdAndCurrentWeek(student.getId(), schedule.getScheduleId(), currentWeek);
//
//        Integer present_hours_plus = attendanceRecord.getTotal_present_hours() + 1;
//        attendanceRecord.setTotal_present_hours(present_hours_plus);
//        attendanceRecordService.save(attendanceRecord);
//    }
//
//
//    /**
//     * Вычисляет текущую неделю семестра на основе заданной даты начала семестра.
//     */
//    private Integer calculateCurrentWeekOfSemester() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate startDate = LocalDate.parse(START_DATE_FOR_SEMESTER, formatter),
//                now = LocalDate.now();
//
//        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
//
//        return diffInDays / 7 + 1;
//    }
//
//    /**
//     * Проверяет, прошло ли достаточно времени с последней регистрации студента для новой регистрации.
//     * Ограничение времени помогает предотвратить чрезмерную регистрацию.
//     */
//    private boolean isCheckInTimeWithinLimit(LocalDateTime lastInByStudent) {
//        LocalDateTime now = LocalDateTime.now();
//        return Duration.between(lastInByStudent, now).toMinutes() >= MIN_TIME_BETWEEN_CHECK_INS_MINUTES;
//    }
//
//
//    // Проверяет, находится ли текущее время в пределах активного временного окна для регистрации.
//    private boolean isCheckInWithinActiveTimeFrame(Schedule schedule) {
//        LocalDateTime now = LocalDateTime.now();
//        int minutes = now.getMinute(),
//                hours = now.getHour();
//        int schedule_start_time = Integer.parseInt(schedule.getStartTime().split("[;:]]")[0]),
//            duration = schedule.getTotalHours();
//
//        return  minutes <= ACTIVE_TIME_LIMIT_MINUTES || (hours < schedule_start_time + duration);
//    }
//
//    /**
//     * Генерирует новый секретный код или обновляет существующий, если прошло достаточно времени.
//     * Это обеспечивает, что секретный код обновляется регулярно для безопасности.
//     */
//    private SecretCodeForCheckIn getUpdatedVersion(Optional<SecretCodeForCheckIn> secretCodeCheckIn) {
//
//        SecretCodeForCheckIn forCheckIn = secretCodeCheckIn.get();
//        LocalDateTime lastTimeLesson = forCheckIn.getCreated(),
//                now = LocalDateTime.now();
//        long diffInMinutes = Duration.between(lastTimeLesson, now).toMinutes();
//
//        if (diffInMinutes > PERIOD_BETWEEN_REFRESHING) {
//            // Secret code generator
//            String secretCode = RandomStringUtils
//                    .random(SIX_SIZED_SECRET_CODE, USE_LETTERS_IN_SECRET_CODE, USE_NUMBERS_IN_SECRET_CODE);
//            forCheckIn.setCreated(LocalDateTime.now());
//            forCheckIn.setSecret_code(secretCode);
//        }
//
//        return forCheckIn;
//    }
//
//
//    /**
//     * Проверяет, зарегистрирован ли студент на указанную секцию.
//     */
//    private boolean isStudentRegisteredForSection(Person student,
//                                                 Section section) {
//        return student != null && section != null
//                && section.getPersons().contains(student);
//    }
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter @Setter
//    static class InData {
//        @NotBlank(message = "Login cannot be empty")
//        private String login;
//
//        @NotBlank(message = "Section name cannot be empty")
//        private String sectionName;
//
//        @NotBlank(message = "Password cannot be empty")
//        private String password;
//    }
//
//}
//
