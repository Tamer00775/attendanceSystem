package kz.sdu.project.resource;

import kz.sdu.project.entity.*;
import kz.sdu.project.service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teacher/startLesson")
@Slf4j
public class ResourceCheckUpByTeacher {

    // Инициализация сервисов
    private final AttendanceRecordService attendanceRecordService;
    private final SectionService sectionService;
    private final PersonService personService;
    private final AttendanceInfoService attendanceInfoService;
    private final SecretCodeForCheckInService secretCodeForCheckInService;

    // Константы
    private static final Integer SIX_SIZED_SECRET_CODE = 6;
    private static final Boolean USE_LETTERS_IN_SECRET_CODE = true;
    private static final Boolean USE_NUMBERS_IN_SECRET_CODE = true;
    private static final String START_DATE_FOR_SEMESTER = "22.01.2024";
    @Autowired
    public ResourceCheckUpByTeacher(AttendanceRecordService attendanceRecordService, SectionService sectionService, PersonService personService, AttendanceInfoService attendanceInfoService, SecretCodeForCheckInService secretCodeForCheckInService) {
        this.attendanceRecordService = attendanceRecordService;
        this.sectionService = sectionService;
        this.personService = personService;
        this.attendanceInfoService = attendanceInfoService;
        this.secretCodeForCheckInService = secretCodeForCheckInService;
    }


    /**
     * Инициирует начало урока, создавая записи посещаемости для всех студентов зарегистрированных в секции и генерируя новый секретный код для сессии.
     * Проверяет, назначен ли преподаватель на указанную секцию и валидны ли данные преподавателя и секции.
     *
     * @param inRequestBody Тело запроса, содержащее логин преподавателя и название секции.
     * @return ResponseEntity с сообщением об успешном или неудачном начале урока.
     */
    @PostMapping()
    public ResponseEntity<String> initiateSection(@RequestBody @Valid InRequestBody inRequestBody) {

        String teacherLogin = inRequestBody.getTeacherLogin();
        String sectionName = inRequestBody.getSectionName();
        Person teacher = personService.findByLogin(teacherLogin);
        Section section = sectionService.findByName(sectionName);
        log.info("Initiating section: {} for teacher: {}", sectionName, teacherLogin);

        if (!isTeacherAssignedToSection(teacher, section)) {
            log.error("Teacher: {} is not assigned to section: {}", teacherLogin, sectionName);
            return ResponseEntity.badRequest().body("Person or Section is Wrong...");
        }

        Schedule schedule = section.getSchedule();

        section.getPersons().forEach(
                person -> recordInitialAttendance(person,schedule,section));

        createOrUpdateSecretCode(schedule);
        log.info("Secret code for schedule: {} is successfully generated or updated", schedule.getScheduleId());

        return ResponseEntity.ok().body("The Section is Successfully stated...");
    }

    /**
     * Создает начальную запись посещаемости для каждого участника секции на текущей неделе семестра.
     * Определяет статус посещения на основе роли участника (преподаватель или студент) и учитывает текущую неделю семестра.
     *
     * @param person Объект Person, для которого создается запись посещаемости.
     * @param schedule Расписание секции, для которой инициируется урок.
     * @param section Секция, для которой инициируется урок.
     */
    public void recordInitialAttendance(Person person, Schedule schedule, Section section) {
        Integer currentWeek = getCurrentWeek();
        boolean checkIfPersonIsTeacher = checkIfPersonIsTeacher(person);

        AttendanceRecord attendanceForPerson = AttendanceRecord.builder()
                .person_att_record(person)
                .schedule_att_record(schedule)
                .total_hours(schedule.getTotalHours())
                .total_present_hours(checkIfPersonIsTeacher ? schedule.getTotalHours() : 0)
                .currentWeek(currentWeek)
                .record_type(checkIfPersonIsTeacher ? "STARTED_BY_BUTTON" : "MANUALLY")
                .is_with_reason(false)
                .build();
        attendanceRecordService.save(attendanceForPerson);
        log.info("AttendanceRecord has been save for : {}",checkIfPersonIsTeacher ? "Teacher" : "Student");

        initializeAttendanceInfo(person,section);
        updateAttendanceInformation(person,section,schedule, !checkIfPersonIsTeacher);
    }

    /**
     * Инициализирует информацию о посещаемости для участника, если она еще не была создана.
     */
    private void initializeAttendanceInfo(Person person, Section section) {

        if (attendanceInfoService.findByPersonIdAndSectionId(person.getId(),
                section.getSectionId()) == null) {
            attendanceInfoService.save(AttendanceInfo.builder()
                    .person_attendanceInfo(person)
                    .section_att_info(section)
                    .percent(0)
                    .full_time(0)
                    .reason_time(0)
                    .build());

            log.info("Initialized AttendanceInfo for Person");
        }

    }

    /**
     * Обновляет информацию о посещаемости участника, учитывая посещенные и пропущенные часы.
     */
    private void updateAttendanceInformation(Person person, Section section, Schedule schedule, boolean willBeAbsent) {
        AttendanceInfo attendanceInfo = attendanceInfoService
                .findByPersonIdAndSectionId(person.getId(), section.getSectionId());
        Integer full_time = attendanceInfo.getFull_time(),
                absent_time = attendanceInfo.getPercent();

        attendanceInfo.setFull_time(full_time + schedule.getTotalHours());
        attendanceInfo.setPercent(absent_time + (willBeAbsent ? schedule.getTotalHours() : 0));
        attendanceInfoService.save(attendanceInfo);
    }

    /**
     * Генерирует и сохраняет новый секретный код для секции или обновляет существующий, если он уже был создан.
     */
    private void createOrUpdateSecretCode(Schedule schedule) {
        SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInService.findByScheduleId(schedule.getScheduleId())
                .orElse(new SecretCodeForCheckIn());

        String secretCode = RandomStringUtils.random(SIX_SIZED_SECRET_CODE, USE_LETTERS_IN_SECRET_CODE, USE_NUMBERS_IN_SECRET_CODE);
        secretCodeForCheckIn.setSchedule(schedule);
        secretCodeForCheckIn.setSecret_code(secretCode);
        secretCodeForCheckIn.setCreated(LocalDateTime.now());
        secretCodeForCheckInService.save(secretCodeForCheckIn);
        log.info("Secret code is successfully generated or updated.");
    }

    /**
     * Проверяет, является ли указанный человек преподавателем, основываясь на его ролях.
     */
    private boolean checkIfPersonIsTeacher(Person x) {
        return x.getRolePerson().stream().anyMatch(y ->
                y.getRole().equals("TEACHER_ROLE"));
    }

    /**
     * Проверяет, назначен ли преподаватель на указанную секцию и валидны ли данные преподавателя и секции.
     */
    private boolean isTeacherAssignedToSection(Person teacher, Section section) {
        return teacher != null && section != null && section.getPersons().contains(teacher);
    }

    /**
     * Вычисляет текущую неделю семестра, исходя из даты начала семестра.
     */
    private Integer getCurrentWeek() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(START_DATE_FOR_SEMESTER, formatter),
                now = LocalDate.now();

        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);

        return diffInDays / 7 + 1;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    static class InRequestBody {
        @NotBlank(message = "Login cannot be empty")
        private String teacherLogin;

        @NotBlank(message = "Section name cannot be empty")
        private String sectionName;
    }
}
