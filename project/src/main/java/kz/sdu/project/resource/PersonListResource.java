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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<UserDTO>> studentList(
            @RequestParam(value = "role", required = true) String role,
            @RequestParam(value = "login", defaultValue = "") String login,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size
    ) {
        if (roleService.findByRole(role).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<UserDTO> personList = personService.findPeopleByLoginPattern(login, pageable,role)
                .map(UserDTO::fromEntity);

        return ResponseEntity.ok().body(personList);
    }

}