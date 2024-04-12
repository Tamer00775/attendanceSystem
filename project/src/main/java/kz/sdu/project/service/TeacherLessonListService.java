package kz.sdu.project.service;

import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.TeacherLessonShowDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Schedule;
import kz.sdu.project.entity.SecretCodeForCheckIn;
import kz.sdu.project.entity.Section;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kz.sdu.project.utils.Constants.*;
import static kz.sdu.project.utils.ObjectValidator.getCurrentWeek;

@Service
@AllArgsConstructor
public class TeacherLessonListService {

    private final PersonService personService;
    private final SectionService sectionService;
    private final SecretCodeForCheckInService secretCodeForCheckInService;
    public List<TeacherLessonShowDto> lessonList() {
        Person teacher = SecurityUtils.getCurrentPerson();
        List<Section> sections = sectionService.findByPersonId(teacher.getId());
        List<TeacherLessonShowDto> listLesson = new ArrayList<>();
        System.out.println("Huuuuh");
        for (Section section : sections) {
            Schedule schedule = section.getSchedule();
            System.out.println(schedule.getScheduleId());
            System.out.println(secretCodeForCheckInService
                    .findByScheduleId(schedule.getScheduleId()));
            SecretCodeForCheckIn secretCodeForCheckIn = secretCodeForCheckInService
                    .findByScheduleId(schedule.getScheduleId())
                            .orElse(null);

            if (secretCodeForCheckIn != null) {
                boolean cannotStart = secretCodeForCheckIn.getIs_interpreted();
                if (cannotStart) {
                    LocalDateTime now = LocalDateTime.now(zoneId);
                    long minutesDiff = Math.abs(Duration.between(now, secretCodeForCheckIn.getCreated()).toMinutes());
                    if (minutesDiff > 300) cannotStart = false;
                }
                listLesson.add(TeacherLessonShowDto.builder()
                        .sectionName(section.getName())
                        .courseName(section.getCourse_section().getName())
                        .startTime(returnStartTime(schedule))
                        .endTime(returnEndTime(schedule))
                        .activeStart(cannotStart ?  cannotStart : canStartLesson(schedule))
                        .activeEnd(cannotStart ?  cannotStart : canEndLesson(schedule))
                        .build());
            }else {
                listLesson.add(TeacherLessonShowDto.builder()
                        .sectionName(section.getName())
                        .courseName(section.getCourse_section().getName())
                        .startTime(returnStartTime(schedule))
                        .endTime(returnEndTime(schedule))
                        .activeStart(false)
                        .activeEnd(false)
                        .build());
            }
        }
        return listLesson;
    }

    private String returnEndTime(Schedule schedule) {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        startDate = startDate.plusWeeks(getCurrentWeek() - 1);
        startDate = startDate.plusDays(schedule.getDayOfWeek() - 1);
        int hour = schedule.getStartTime() + schedule.getTotalHours() - 1;
        int minute = 50;
        LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), hour, minute);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        return formatter.format(startDateTime);
    }

    private String returnStartTime(Schedule schedule) {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        startDate = startDate.plusWeeks(getCurrentWeek() - 1);
        startDate = startDate.plusDays(schedule.getDayOfWeek() - 1);
        int hour = schedule.getStartTime(); // Placeholder method
        int minute = 0; // Placeholder method
        LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), hour, minute);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        return formatter.format(startDateTime);
    }

    private boolean canStartLesson(Schedule schedule) {
        LocalDateTime now = LocalDateTime.now(zoneId);
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
        LocalDateTime now = LocalDateTime.now(zoneId);
        int startHour = schedule.getStartTime(),
                endHour = startHour + schedule.getTotalHours();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        DayOfWeek dayOfWeek2 = DayOfWeek.of(schedule.getDayOfWeek());
        return now.getMinute() > TEACHER_END_LESSON_TIME_STARTS_FROM &&
                now.getHour() >= startHour &&
                now.getHour() < endHour &&
                dayOfWeek == dayOfWeek2;
    }
}
