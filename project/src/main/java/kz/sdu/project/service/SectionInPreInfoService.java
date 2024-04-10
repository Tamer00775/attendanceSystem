package kz.sdu.project.service;

import kz.sdu.project.dto.SectionInPreInfoDto;
import kz.sdu.project.entity.*;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class SectionInPreInfoService {

    private final CourseService courseService;
    private final SectionService sectionService;
    private final PersonService personService;
    public SectionInPreInfoDto getInPreInfo(String courseCode) {

        Person student = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        Course course = courseService.findByCode(courseCode)
                .orElseThrow(IllegalAccessError::new);
        Section practiceSection = sectionService.findByPersonId(student.getId())
                .stream()
                .filter(section -> section.getCourse_section().getCourse_id().equals(course.getCourse_id())
                        && section.getType().equals("P"))
                .findAny()
                .orElseThrow(IllegalAccessError::new);
        Schedule schedule = Objects.requireNonNull(practiceSection.getSchedule());
        Person teacher = personService.findAll()
                .stream()
                .filter(person -> isTeacher(person.getRolePerson()))
                .filter(person -> sectionService.findByPersonId(person.getId())
                        .stream().anyMatch(section ->
                                section.getSectionId().equals(practiceSection.getSectionId())
                        && section.getType().equals("P")))
                .findAny()
                .orElseThrow(IllegalAccessError::new);

        return returnSectionPreInfo(schedule, teacher, course, practiceSection);
    }

    private SectionInPreInfoDto returnSectionPreInfo(Schedule schedule, Person teacher, Course course, Section practiceSection) {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        startDate = startDate.plusWeeks(getCurrentWeek() - 1);
        startDate = startDate.plusDays(schedule.getDayOfWeek() - 1);

        SectionInPreInfoDto sectionInPreInfoDto = new SectionInPreInfoDto();
        sectionInPreInfoDto.setCourseDesc(course.getCode() + " - " + course.getName());
        sectionInPreInfoDto.setTeacherName(teacher.getFirstName() + " " + teacher.getLastName());
        sectionInPreInfoDto.setStartDate(startDate.getDayOfMonth() + "." + startDate.getMonthValue()+"."+startDate.getYear() + " " +
                schedule.getStartTime() + ".00");
        sectionInPreInfoDto.setSectionName(practiceSection.getName());
        return sectionInPreInfoDto;
    }

    public boolean isTeacher(Set<Role> roleSet) {
        return roleSet
                .stream()
                .anyMatch(role -> role.getRole().equals("ROLE_TEACHER"));
    }
    private Integer getCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        LocalDate now = LocalDate.now();
        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
        return diffInDays / 7 + 1;
    }
}
