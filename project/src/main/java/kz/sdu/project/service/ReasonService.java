package kz.sdu.project.service;

import kz.sdu.project.dto.ReasonDTO;
import kz.sdu.project.dto.ReasonForAbsenceDTO;
import kz.sdu.project.dto.ReasonStatusDTO;
import kz.sdu.project.entity.*;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static kz.sdu.project.utils.enums.ReasonStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class ReasonService {
    private final ReasonForAbsenceService reasonForAbsenceService;
    private final SectionService sectionService;
    private final AttendanceInfoService attendanceInfoService;
    private final AttendanceRecordService attendanceRecordService;

    public ReasonStatusDTO upload(ReasonDTO reasonDTO) {
        log.info("Starting to process reason for absence upload with document ID: {}", reasonDTO.getDocument());
        validReasonDates(reasonDTO);
        ReasonForAbsence reasonForAbsence = createReasonAbsence(reasonDTO);
        log.info("Reason for absence created and sent for user ID: {}", reasonForAbsence.getPerson_reason_for_absence().getId());
        return ReasonStatusDTO
                .builder()
                .status("SUCCESSFULLY_SENT")
                .build();
    }

    private void validReasonDates(ReasonDTO reasonDTO) {
        try {
            LocalDate from = LocalDate.parse(reasonDTO.getFrom());
            LocalDate to = LocalDate.parse(reasonDTO.getTo());
            log.info("Validating dates: from {} to {}", reasonDTO.getFrom(), reasonDTO.getTo());
            if (to.isBefore(from))
                throw new IllegalArgumentException("To date cannot be earlier than From date.");
            if (from.isBefore(LocalDate.of(2024,1,22)))
                throw new IllegalArgumentException("Date is before the allowed system start date.");
        } catch (DateTimeParseException e) {
            log.error("Date parsing error for dates: {} or {}", reasonDTO.getFrom(), reasonDTO.getTo(), e);
            throw new IllegalArgumentException("Invalid date format. Please use the format yyyy-MM-dd.");
        }
    }

    private ReasonForAbsence createReasonAbsence(ReasonDTO reasonDTO) {
        Person person = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        ReasonForAbsence reasonForAbsence = new ReasonForAbsence();
        String sectionName = reasonDTO.getSection().substring(0, reasonDTO.getSection().indexOf(' '));
        Section section = sectionService
                .findByName(sectionName)
                        .orElseThrow();
        Optional<Person> teacher = section
                .getPersons()
                        .stream()
                                .filter(tea -> isTeacher(tea.getRolePerson()))
                                        .findAny();
        reasonForAbsence.setPerson_reason_for_absence(person);
        reasonForAbsence.setPerson_teacher_reason_for_absence(teacher.get());
        reasonForAbsence.setSection_reason_for_absence(section);
        reasonForAbsence.setDescription(reasonDTO.getDescription());
        reasonForAbsence.setDocument(reasonDTO.getDocument());
        reasonForAbsence.setStatus(IN_PROCESS.name());
        reasonForAbsence.setIsAccepted(false);
        reasonForAbsence.setDate_from(convertToLocalDate(reasonDTO.getFrom()));
        reasonForAbsence.setDate_to(convertToLocalDate(reasonDTO.getTo()));
        log.info("Creating reason for absence for user: {}", person.getId());
        return reasonForAbsenceService.save(reasonForAbsence);
    }

    private LocalDate convertToLocalDate(String from) {
        return LocalDate.parse(from);
    }

    public List<ReasonForAbsenceDTO> allByAdmin() {
        log.info("Retrieving all reasons for absence");
        return reasonForAbsenceService
                .findAll()
                .stream()
                .map(ReasonForAbsenceService::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ReasonForAbsenceDTO> allByStudent(Person person) {
        log.info("Retrieving all reasons for absence");
        return reasonForAbsenceService
                .findAllByPersonId(person.getId())
                .stream()
                .map(ReasonForAbsenceService::fromEntity)
                .collect(Collectors.toList());
    }

    public ReasonStatusDTO accept(Integer reasonId) {
        log.info("Accepting reason for absence with ID: {}", reasonId);
        ReasonForAbsence reasonForAbsence = reasonForAbsenceService
                .findById(reasonId)
                .orElseThrow(IllegalArgumentException::new);
        setUpAttendance(reasonForAbsence);
        reasonForAbsence.setStatus(ACCEPTED.name());
        reasonForAbsenceService.save(reasonForAbsence);
        return ReasonStatusDTO
                .builder()
                .status(SUCCESSFULLY_ACCEPTED.name())
                .build();
    }

    private void setUpAttendance(ReasonForAbsence reasonForAbsence) {
        LocalDate startDate = reasonForAbsence.getDate_from(),
                endDate = reasonForAbsence.getDate_to();
        while (!startDate.isAfter(endDate)) {
            updateAttendance(startDate, reasonForAbsence.getPerson_reason_for_absence(), reasonForAbsence.getSection_reason_for_absence());
            startDate = startDate.plusDays(7);
        }
    }

    private void updateAttendance(LocalDate startDate, Person student, Section section) {

        Schedule schedule = section.getSchedule();
        int currentWeek = getWeek(startDate, LocalDate.of(2024, 1,22));
        System.out.println("currentWeek : " + currentWeek);
        AttendanceRecord attendanceRecord = attendanceRecordService
                .findByPersonIdAndScheduleIdAndCurrentWeek(
                        student.getId(), schedule.getScheduleId(),currentWeek)
                .orElse(null);
        AttendanceInfo attendanceInfo = attendanceInfoService
                .findByPersonIdAndSectionId(student.getId(), section.getSectionId())
                .orElse(null);
        if (attendanceRecord == null || attendanceInfo == null) return;
        int totalHours = attendanceRecord.getTotal_hours(),
                presentHours = attendanceRecord.getTotal_present_hours();

        attendanceRecord.setTotal_present_hours(0);
        attendanceRecord.setIs_with_reason(true);

        attendanceInfo.setReason_time(attendanceInfo.getReason_time() + totalHours);
        attendanceInfo.setPercent(attendanceInfo.getPercent() - (totalHours - presentHours));

        attendanceInfoService.save(attendanceInfo);
        attendanceRecordService.save(attendanceRecord);
    }

    private Integer dayDiff(LocalDate date1,LocalDate date2 ) {
        return Math.abs((int)ChronoUnit.DAYS.between(date1, date2));
    }

    private int getWeek(LocalDate date1,LocalDate date2 ) {
        return Math.abs((int)ChronoUnit.WEEKS.between(date1, date2)) + 1;
    }

    public ReasonStatusDTO reject(Integer reasonId) {
        log.info("Rejecting reason for absence with ID: {}", reasonId);
        ReasonForAbsence reasonForAbsence = reasonForAbsenceService
                .findById(reasonId)
                .orElseThrow(IllegalArgumentException::new);
        reasonForAbsence.setStatus(REJECTED.name());
        reasonForAbsenceService.save(reasonForAbsence);
        return ReasonStatusDTO
                .builder()
                .status(SUCCESSFULLY_REJECTED.name())
                .build();
    }

    public List<ReasonForAbsenceDTO> all(String checked) {
        Person person = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        if (isTeacher(person.getRolePerson())) return allByTeacher(person,checked);
        return allByStudent(person);
    }

    private List<ReasonForAbsenceDTO> allByTeacher(Person person, String checked) {
        List<ReasonForAbsence> reasons = reasonForAbsenceService
                .findAll()
                .stream()
                    .filter(reasonForAbsence -> reasonForAbsence.getPerson_teacher_reason_for_absence() != null &&
                        reasonForAbsence.getPerson_teacher_reason_for_absence().getId().equals(person.getId()))
                .collect(Collectors.toList());

        return reasons
                .stream()
                .filter(reason -> {
                    if (checked.equals("true")) {
                        return !reason.getStatus().equals(IN_PROCESS.name());
                    }
                    return reason.getStatus().equals(IN_PROCESS.name());
                })
                .map(ReasonForAbsenceService::fromEntity)
                .collect(Collectors.toList());
    }

    private boolean isTeacher(Set<Role> rolePerson) {
        return rolePerson
                .stream()
                .anyMatch(role -> role.getRole().equals("ROLE_TEACHER"));
    }
}
