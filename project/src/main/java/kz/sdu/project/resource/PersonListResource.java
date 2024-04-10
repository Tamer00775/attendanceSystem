package kz.sdu.project.resource;


import kz.sdu.project.dto.PersonDto;
import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.dto.SectionResponseDto;
import kz.sdu.project.dto.UserDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Role;
import kz.sdu.project.service.PersonService;
import kz.sdu.project.utils.ObjectValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kz.sdu.project.utils.ObjectValidator.isStudent;
import static kz.sdu.project.utils.ObjectValidator.isTeacher;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/list")
public class PersonListResource {

    private final PersonService personService;
    @GetMapping("/student")
    public ResponseEntity<List<UserDTO>> studentList() {
        List<UserDTO> personList = personService
                .findAll()
                .stream()
                .filter(person -> isStudent(person.getRolePerson()))
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(personList);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<UserDTO>> teacherList() {
        List<UserDTO> personList = personService
                .findAll()
                .stream()
                .filter(person -> isTeacher(person.getRolePerson()))
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(personList);
    }
}
