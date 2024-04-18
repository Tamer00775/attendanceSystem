package kz.sdu.project.resource;


import kz.sdu.project.dto.PersonDto;
import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.dto.SectionResponseDto;
import kz.sdu.project.dto.UserDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Role;
import kz.sdu.project.service.PersonService;
import kz.sdu.project.service.RoleService;
import kz.sdu.project.utils.ObjectValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kz.sdu.project.utils.ObjectValidator.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/list")
public class PersonListResource {
    private final PersonService personService;
    private final RoleService roleService;
    @GetMapping("/person")
    public ResponseEntity<List<UserDTO>> studentList(
            @RequestParam(value = "role", required = true) String role,
            @RequestParam(value = "login", defaultValue = "") String login
    ) {
        if (roleService.findByRole(role).isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<UserDTO> personList = personService
                .findPeopleByLoginPattern(login)
                .stream()
                .filter(person -> isNeededPerson(person.getRolePerson(),role))
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(personList);
    }

}
