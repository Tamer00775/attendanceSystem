package kz.sdu.project.service;

import kz.sdu.project.dto.AttendanceStatusDetailDto;
import kz.sdu.project.dto.AttendanceStatusDto;
import kz.sdu.project.dto.RequestBody2DTO;
import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.entity.*;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.utils.CompletedAttributeValidation;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static kz.sdu.project.domain.ActionStatus.*;
import static kz.sdu.project.domain.Constants.*;
@Service
@Slf4j
@AllArgsConstructor
public class StudentAttStatusService {

    private final PersonService personService;
    private final AttendanceInfoService attendanceInfoService;
    private final AttendanceRecordService attendanceRecordService;
    private final SectionService sectionService;
    private final CompletedAttributeValidation val;

    public Map<String, AttendanceStatusDto> attStatusByAll() {
        Person student = SecurityUtils.getCurrentPerson();
        if (!val.isStudent(student))
            throw new EntityNotFoundException("User is not student with login");

        List<AttendanceInfo> attList = attendanceInfoService.findByPersonId(student.getId());

        return returnAllAtt(attList);
    }

    private Map<String, AttendanceStatusDto> returnAllAtt(List<AttendanceInfo> attList) {
        Map<String, AttendanceStatusDto> finalAttMap = new HashMap<>();
        attList.forEach(attendanceInfo -> updateAttendanceStatus(finalAttMap,attendanceInfo));

        log.info("Completed process AttStatusForAll...");
        return finalAttMap;
    }

    private void updateAttendanceStatus(Map<String, AttendanceStatusDto> attMapList, AttendanceInfo attendanceInfo) {

        if (val.oneOfParamIsNull(attendanceInfo))
                 throw new NullPointerException("AttendanceInfo is not clear...");

        String sectionName = attendanceInfo.getSection_att_info().getName();
        AttendanceNumbers attendanceNumbers = calculateAttendanceNumbers(attendanceInfo);

        findMatchingPrefixInSectionName(attMapList.keySet(), sectionName).ifPresentOrElse(
                prevSectionName ->
                        updateExistingAttendanceRecord(attMapList, prevSectionName, sectionName, attendanceNumbers),
                () -> addNewAttendanceRecord(attMapList, sectionName, attendanceInfo, attendanceNumbers)
        );
    }

    private AttendanceNumbers calculateAttendanceNumbers(AttendanceInfo attendanceInfo) {
        int hours = attendanceInfo.getFull_time();
        int absentHours = attendanceInfo.getPercent();
        int reasonHours = attendanceInfo.getReason_time();
        int presentHours = hours - absentHours - reasonHours;
        return new AttendanceNumbers(hours, presentHours, absentHours, reasonHours);
    }

    private void updateExistingAttendanceRecord(Map<String, AttendanceStatusDto> attendanceMap, String prevSectionName, String sectionName, AttendanceNumbers numbers) {
        AttendanceStatusDto attDto = attendanceMap.remove(prevSectionName);
        attDto.setHours(attDto.getHours() + numbers.getHours());
        attDto.setPresentHours(attDto.getPresentHours() + numbers.getPresentHours());
        attDto.setAbsentHours(attDto.getAbsentHours() + numbers.getAbsentHours());
        attDto.setReasonHours(attDto.getReasonHours() + numbers.getReasonHours());
        attDto.setAbsenceLimit(attDto.getAbsenceLimit() + numbers.getAbsentHours() * ABSENCE_COEFFICIENT);
        attDto.setSectionNames(prevSectionName + "," + sectionName);
        attendanceMap.put(prevSectionName + "," + sectionName, attDto);
    }

    private void addNewAttendanceRecord(Map<String, AttendanceStatusDto> attendanceMap, String sectionName, AttendanceInfo attendanceInfo, AttendanceNumbers numbers) {
        Course course = attendanceInfo.getSection_att_info().getCourse_section();
        attendanceMap.put(sectionName, AttendanceStatusDto.builder()
                .code(course.getCode())
                .courseName(course.getName())
                .hours(numbers.getHours())
                .presentHours(numbers.getPresentHours())
                .absentHours(numbers.getAbsentHours())
                .reasonHours(numbers.getReasonHours())
                .absenceLimit(numbers.getAbsentHours() * ABSENCE_COEFFICIENT)
                .sectionNames(sectionName)
                .build());
    }


    public List<AttendanceStatusDetailDto> attStatusBySection(String sectionNames) {
        Person student = SecurityUtils.getCurrentPerson();
        if (!val.isStudent(student))
            throw new EntityNotFoundException("User is not student with login");
        String[] sections = sectionNames.split(SEPARATE_BY_COMMA);

        return returnSectionAtt(student,sections);
    }

    private List<AttendanceStatusDetailDto> returnSectionAtt(Person student, String[] sections) {

        List<AttendanceStatusDetailDto> finalAttDetList = new ArrayList<>();
        for (String section : sections) {
            updateAttendanceDetailStatus(finalAttDetList,section,student);
        }

        return finalAttDetList;
    }


    private void updateAttendanceDetailStatus(List<AttendanceStatusDetailDto> attList, String sectionName, Person student) {

        Section section = sectionService.findByName(sectionName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Section with name %s not found", sectionName)));
        Schedule schedule = section.getSchedule();
        if (section.getSchedule() == null)
            throw new EntityNotFoundException(String.format("Schedule with section name %s not found", section));
        String lecType = section.getType();


        List<AttendanceRecord> attRecords = attendanceRecordService
                .findByPersonIdAndScheduleId(student.getId(), schedule.getScheduleId());
        attRecords.forEach(attendanceRecord -> updateAttDetList(attList,attendanceRecord,schedule, lecType));
    }

    private void updateAttDetList(List<AttendanceStatusDetailDto> attDetList, AttendanceRecord attendanceRecord, Schedule schedule, String lecType) {
        int totalHours = attendanceRecord.getTotal_hours(),
                presentHours = attendanceRecord.getTotal_present_hours();
        boolean isWithReason = attendanceRecord.getIs_with_reason();
        LocalDate date = getCurrentLocalDate(attendanceRecord.getCurrentWeek(), schedule.getDayOfWeek());
        String place = schedule.getClassRoom_schedule().getRoom_number();

        for (int hour = 0; hour < totalHours; hour++) {
            String attStatus = determineAttendanceStatus(presentHours--,isWithReason);
            String time = formatHour(schedule.getStartTime(), hour);
            addAttendanceDetailDto(attDetList, date, place, attStatus, time,attendanceRecord.getCurrentWeek(),lecType);
        }

        log.info("Completed process AttStatusBySection...");
    }

    private String determineAttendanceStatus(int presentHours, boolean isWithReason) {
        if (presentHours > 0) {
            return PRESENT_STATUS.toString();
        } else if (isWithReason) {
            return WITH_REASON_STATUS.toString();
        } else {
            return ABSENT_STATUS.toString();
        }
    }

    private String formatHour(int startsAt, int hourIncrement) {
        return (startsAt + hourIncrement) + ":00";
    }

    private void addAttendanceDetailDto(List<AttendanceStatusDetailDto> list, LocalDate date, String place, String status, String time, Integer currentWeek, String lecType) {
        list.add(AttendanceStatusDetailDto.builder()
                .date(date)
                .place(place)
                .attStatus(status)
                .hour(time)
                .week(currentWeek)
                .type(lecType)
                .build());
    }

    private LocalDate getCurrentLocalDate(int currentWeek, Integer dayOfWeek) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate startDate = LocalDate.parse(START_DATE_FOR_SEMESTER, formatter);

        return startDate.plusWeeks(currentWeek - 1).plusDays(dayOfWeek - 1);
    }

    private Optional<String> findMatchingPrefixInSectionName(Set<String> sectionNames, String sectionName) {
        return sectionNames.stream()
                .filter(prevSectionName -> shareSamePrefixName(prevSectionName,sectionName))
                .findFirst();
    }

    private boolean shareSamePrefixName(String sName, String name) {
        String prevSectionName = extractPrefix(sName);
        String sectionName = extractPrefix(name);
        return Objects.equals(prevSectionName, sectionName);
    }

    private String extractPrefix(String name) {
        int dotIndex = name.indexOf(SEPARATE_BY_POINT);
        return dotIndex != -1 ? name.substring(0, dotIndex) : name;
    }

    @Data
    private static class AttendanceNumbers {
        private final int hours;
        private final int presentHours;
        private final int absentHours;
        private final int reasonHours;
    }
}



