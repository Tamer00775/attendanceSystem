package kz.sdu.project.service;


import kz.sdu.project.dto.SectionInfoDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Section;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonSectionService {
    public SectionInfoDTO all() {
        Person person = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        List<String> sections = new ArrayList<>();
        Set<Section> sectionSet = person.getSections();
        for (Section section : sectionSet) {
            String name = section.getName() + " - " + section.getCourse_section().getName();
            sections.add(name);
        }
        return new SectionInfoDTO(sections);
    }
}
