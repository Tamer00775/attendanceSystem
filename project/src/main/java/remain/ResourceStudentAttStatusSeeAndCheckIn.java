//package kz.sdu.project.resource;
//
//import kz.sdu.project.entity.*;
//import kz.sdu.project.service.*;
//import lombok.*;
//import org.aspectj.apache.bcel.classfile.Code;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api2/student/attStatus")
//public class ResourceStudentAttStatusSeeAndCheckIn {
//
//    // Инициализация сервисов
//    private final AttendanceInfoService attendanceInfoService;
//    private final PersonService personService;
//    private final AttendanceRecordService attendanceRecordService;
//
//    // Константы
//    private static final String START_DATE_FOR_SEMESTER = "22.01.2024";
//
//    @Autowired
//    public ResourceStudentAttStatusSeeAndCheckIn(AttendanceInfoService attendanceInfoService, PersonService personService, AttendanceRecordService attendanceRecordService) {
//        this.attendanceInfoService = attendanceInfoService;
//        this.personService = personService;
//        this.attendanceRecordService = attendanceRecordService;
//    }
//
//    /**
//     * Получает общую информацию о посещаемости студента по всем курсам.
//     * Метод возвращает список статусов посещаемости для каждого курса, на который зарегистрирован студент,
//     * включая общее количество часов, часы присутствия, отсутствия и отсутствия по уважительной причине.
//     *
//     * @param inRequestBody Тело запроса, содержащее логин студента.
//     * @return ResponseEntity с списком статусов посещаемости по всем курсам.
//     */
//    // TODO : переделать адреса
//    // TODO : снести всю логику с rest и перенапралять все в service как в authResource
//    // TODO : убрать inner classes from rest and add it to dto package, but not like inner class
//    @PostMapping("/test")
//    public ResponseEntity<List<AttStatus>> allCoursesAttendance(@RequestBody @Valid InRequestBody inRequestBody) {
//
//        String login = inRequestBody.getLogin();
//        Person person = personService.findByLogin(login);
//
//        if (person == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // List<Section> sectionList = person.getSections().stream().toList();
//        List<Section> sectionList = new ArrayList<>();
//        Map<String, AttStatus> attStatusMap = new HashMap<>();
//
//        for (Section section : sectionList) {
//            Course course = section.getCourse_section();
//            String code = course.getCode();
//            String name = course.getName();
//
//            AttendanceInfo attendanceInfo = attendanceInfoService
//                    .findByPersonIdAndSectionId(person.getId(), section.getSectionId());
//            Integer full_time = attendanceInfo.getFull_time(),
//                    absence_time = attendanceInfo.getPercent(),
//                    reason_time = attendanceInfo.getReason_time(),
//                    present_time = full_time - reason_time - absence_time;
//
//            AttStatus attStatus = attStatusMap.get(code);
//            if (attStatus == null) {
//                attStatusMap.put(code, AttStatus.builder()
//                        .code(code)
//                        .courseName(name)
//                        .totalHours(full_time)
//                        .presentHours(present_time)
//                        .reasonHours(reason_time)
//                        .absentHours(absence_time)
//                        .absenceLimit(absence_time * 2)
//                        .build());
//            } else {
//                attStatus.setTotalHours(attStatus.getTotalHours() + full_time);
//                attStatus.setPresentHours(attStatus.getPresentHours() + present_time);
//                attStatus.setReasonHours(attStatus.getReasonHours() + reason_time);
//                attStatus.setAbsentHours(attStatus.getAbsentHours() + absence_time);
//                attStatus.setAbsenceLimit(attStatus.getAbsenceLimit() + absence_time * 2);
//                attStatusMap.put(code, attStatus);
//            }
//        }
//
//        // return ResponseEntity.ok().body(attStatusMap.values().stream().toList());
//        return null;
//    }
//
//    /**
//     * Предоставляет детализированную информацию о посещаемости по конкретному курсу для студента.
//     * Метод фильтрует секции по заданному коду курса и агрегирует данные посещаемости, формируя подробный отчет.
//     *
//     * @param inRequestBody2 Тело запроса, содержащее логин студента и код курса.
//     * @return ResponseEntity с картой, включающей информацию о посещаемости и список статусов посещения для каждого занятия.
//     */
//    @PostMapping()
//    public ResponseEntity<Map<PreAttStatusLPNInfo, List<AttStatusLPN>>> codeCoursesAttendance(
//            @RequestBody @Valid InRequestBody2 inRequestBody2) {
//
//        String login = inRequestBody2.getLogin();
//        String codeCourse = inRequestBody2.getCodeCourse();
//        Person person = personService.findByLogin(login);
//
//        if (person == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        List<Section> sections = person.getSections().stream()
//                .filter(x -> x.getCourse_section().getCode().equals(codeCourse)).collect(Collectors.toList());
//        Map<PreAttStatusLPNInfo, List<AttStatusLPN>> attStatusLPNMap = new HashMap<>();
//
//        for (Section section : sections) {
//            Integer weeks = 15;
//            Schedule schedule = section.getSchedule();
//            List<AttendanceRecord> attendanceRecords = attendanceRecordService
//                    .findByPersonIdAndScheduleId(person.getId(), schedule.getScheduleId());
//            List<AttStatusLPN> attStatusLPNS = new ArrayList<>();
//
//            // Устанавливаем значения для PreAttStatusLPNInfo и списка AttStatusLPN
//            int full_present_hours = 0, full_absent_hours = 0,
//                    full_reason_hours = 0, order = 1;
//
//            // и подробный список посещений (List<AttStatusLPN>) для каждой занятия в секции.
//            for (AttendanceRecord attendanceRecord : attendanceRecords) {
//                int total_hours = attendanceRecord.getTotal_hours(),
//                        total_present_hours = attendanceRecord.getTotal_present_hours();
//                boolean with_reason = attendanceRecord.getIs_with_reason();
//                for (int i = 0; i < total_hours; i++) {
//                    String forTime = calculateStartTimeForHour(schedule.getStartTime(), i);
//                    AttStatusLPN attStatusLPN = AttStatusLPN.builder()
//                            .order(order++)
//                            .date(calculateDateForWeekAndDay(attendanceRecord.getCurrentWeek(), schedule.getDayOfWeek()))
//                            .time(forTime)
//                            .status(with_reason ? "REASON" : total_present_hours > 0 ? "PRESENT" : "ABSENT")
//                            .place(schedule.getClassRoom_schedule().getRoom_number())
//                            .build();
//                    if (attStatusLPN.getStatus().equals("PRESENT")) {
//                        full_present_hours++;
//                    } else if(attStatusLPN.getStatus().equals("ABSENT")) {
//                        full_absent_hours++;
//                    } else {
//                        full_reason_hours++;
//                    }
//                    attStatusLPNS.add(attStatusLPN);
//                    total_present_hours--;
//                }
//            }
//
//            // Включает подсчет количества часов присутствия, отсутствия и отсутствия по уважительной причине.
//            Person teacher = section.getPersons().stream()
//                    .filter(this::checkIfPersonIsTeacher).findFirst().get();
//            String description = section.getName() + " - " + teacher.getFirstName() + " " + teacher.getLastName() +
//                    + weeks + " (week) , " + (weeks * schedule.getTotalHours())  + " (hours)";
//            PreAttStatusLPNInfo preAttStatusLPNInfo = PreAttStatusLPNInfo.builder()
//                    .description(description)
//                    .full_hours(weeks * schedule.getTotalHours())
//                    .present_hours(full_present_hours)
//                    .absent_hours(full_absent_hours)
//                    .reason_hours(full_reason_hours)
//                    .build();
//
//            attStatusLPNMap.put(preAttStatusLPNInfo, attStatusLPNS);
//        }
//
//        return ResponseEntity.ok().body(attStatusLPNMap);
//    }
//    /**
//     * Вычисляет время начала для конкретного часа занятия, основываясь на начальном времени расписания.
//     */
//    private String calculateStartTimeForHour(String startTime, int i) {
//        try {
//            String[] parts = startTime.split("[:.]");
//            int time = Integer.parseInt(parts[0]) + i;
//
//            return time + ":"+"00";
//        }catch (Exception e) {
//            return startTime;
//        }
//    }
//
//    /**
//     * Вычисляет дату занятия на основе текущей недели семестра и дня недели, в котором оно проходит.
//     */
//    private  LocalDate calculateDateForWeekAndDay(Integer currentWeek, Integer dayOfWeek) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate startDate = LocalDate.parse(START_DATE_FOR_SEMESTER, formatter);
//
//        return startDate.plusWeeks(currentWeek - 1).plusDays(dayOfWeek - 1);
//    }
//
//    /**
//     * Проверяет, является ли указанный человек преподавателем, основываясь на его ролях в системе.
//     */
//    private boolean checkIfPersonIsTeacher(Person x) {
//        return x.getRolePerson().stream().anyMatch(y ->
//                y.getRole().equals("TEACHER_ROLE"));
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter @Setter
//    @Builder
//    static class AttStatus {
//        private String code;
//        private String courseName;
//        private Integer totalHours;
//        private Integer presentHours;
//        private Integer absentHours;
//        private Integer reasonHours;
//        private Integer absenceLimit;
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter @Setter
//    @Builder
//    static class AttStatusLPN {
//        private Integer order;
//        private LocalDate date;
//        private String time;
//        private String status;
//        private String place;
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter @Setter
//    @Builder
//    static class PreAttStatusLPNInfo {
//        private String description;
//        private Integer full_hours;
//        private Integer absent_hours;
//        private Integer reason_hours;
//        private Integer present_hours;
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter @Setter
//    @Builder
//    static class InRequestBody {
//        @NotBlank(message = "Login cannot be empty")
//        private String login;
//    }
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Getter @Setter
//    @Builder
//    static class InRequestBody2 {
//        @NotBlank(message = "Login cannot be empty")
//        private String login;
//
//        @NotBlank(message = "CodeCourse cannot be empty")
//        private String codeCourse;
//    }
//}
