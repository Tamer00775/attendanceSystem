package kz.sdu.project.service;

import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.TeacherLessonShowDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Section;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TeacherLessonListService {

    private final PersonService personService;
    private final SectionService sectionService;
    public List<TeacherLessonShowDto> lessonList() {
        Person teacher = SecurityUtils.getCurrentPerson();
        List<Section> sections = sectionService.findByPersonId(teacher.getId());
        List<TeacherLessonShowDto> listLesson = new ArrayList<>();

        for (Section section : sections) {
            listLesson.add(TeacherLessonShowDto.builder()
                            .sectionName(section.getName())
                            .courseName(section.getCourse_section().getName())
                    .build());
        }
        return listLesson;
    }
}
