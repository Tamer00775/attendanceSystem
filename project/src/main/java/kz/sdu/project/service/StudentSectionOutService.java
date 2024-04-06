package kz.sdu.project.service;

import kz.sdu.project.dto.SectionOutRequest;
import kz.sdu.project.entity.*;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StudentSectionOutService {
    private final SectionService sectionService;
    private final CheckInForSessionService checkInForSessionService;
    private final AttendanceInfoService attendanceInfoService;
    private final AttendanceRecordService attendanceRecordService;

    public String studentOutProcess(SectionOutRequest sectionOutRequest) {
        Person student = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        Section section = sectionService.findByName(sectionOutRequest.getSection())
                .orElseThrow(() -> new EntityNotFoundException("Section is not found : " + sectionOutRequest.getSection()));
        Schedule schedule = Objects.requireNonNull(section.getSchedule());
        CheckInForSession checkInForSession = checkInForSessionService
                .findByPersonIdAndScheduleId(student.getId(), schedule.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException("Unexpected error from system..."));

        if (user_did_get_left_before(checkInForSession)) return "LEAVING_SESSION_IS_DONE_ALREADY";
        if (!canLeftSession(schedule)) return "U_CAN_LEAVE_AFTER_15_MIN";
        long min_diff_between_session = min_diff_between(checkInForSession.getGet_passed());
        if (min_diff_between_session > 45) return "INITIALLY_YOU_SHOULD_ENTER_TO_SESSION";

        updateAttIfNeeded(min_diff_between_session, student, section, schedule);
        updateCheckIn(checkInForSession);

        return "LEAVING_SESSION_IS_DONE";
    }

    private void updateCheckIn(CheckInForSession checkInForSession) {
        checkInForSession.setGet_left(LocalDateTime.now());
        checkInForSessionService.save(checkInForSession);
    }

    private boolean user_did_get_left_before(CheckInForSession checkInForSession) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime get_left = checkInForSession.getGet_left();
        if (get_left == null) return false;
        long min_diff = Math.abs(Duration.between(now, get_left).toMinutes());
        return min_diff < 60;
    }

    public void updateAttIfNeeded(long min_diff_between_session, Person student, Section section, Schedule schedule) {
        if (min_diff_between_session < 30) {
            AttendanceInfo attendanceInfo = attendanceInfoService
                    .findByPersonIdAndSectionId(student.getId(), section.getSectionId())
                    .orElseThrow(() -> new EntityNotFoundException("Unexpected error from system..."));
            AttendanceRecord attendanceRecord = attendanceRecordService
                    .findByPersonIdAndScheduleIdAndCurrentWeek(student.getId(), schedule.getScheduleId(),getCurrentWeek())
                    .orElseThrow(() -> new EntityNotFoundException("Unexpected error from system..."));
            attendanceRecord.setTotal_present_hours(attendanceRecord.getTotal_present_hours() - 1);
            attendanceInfo.setPercent(attendanceInfo.getPercent() + 1);
            attendanceInfoService.save(attendanceInfo);
            attendanceRecordService.save(attendanceRecord);
        }
    }

    private long min_diff_between(LocalDateTime getPassed) {
        LocalDateTime now = LocalDateTime.now();
        return Math.abs(Duration.between(now, getPassed).toMinutes());
    }

    private boolean canLeftSession(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now();
        int startHour = schedule.getStartTime(),
                endHour = startHour + schedule.getTotalHours();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        DayOfWeek dayOfWeek2 = DayOfWeek.of(schedule.getDayOfWeek());
        return now.getHour() >= startHour &&
                now.getHour() < endHour &&
                dayOfWeek == dayOfWeek2;
    }
    private Integer getCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        LocalDate now = LocalDate.now();
        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
        return diffInDays / 7 + 1;
    }
}
