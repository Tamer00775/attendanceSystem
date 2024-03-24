package kz.sdu.project.service;

import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.TeacherLessonShowDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TeacherLessonListService {

    private final PersonService personService;
    private final SectionService sectionService;

    @Autowired
    public TeacherLessonListService(PersonService personService, SectionService sectionService) {
        this.personService = personService;
        this.sectionService = sectionService;
    }

    public List<TeacherLessonShowDto> lessonList(RequestBodyDTO requestBodyDTO) {

        String login = requestBodyDTO.getLogin();
        Person teacher = personService.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Person with username %s not found",login)));
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
