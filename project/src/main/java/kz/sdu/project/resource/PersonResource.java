package kz.sdu.project.resource;

import kz.sdu.project.adapter.PersonAdapter;
import kz.sdu.project.dto.PersonDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.service.PersonService;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/person")
public class PersonResource {

    private final PersonService personService;

    @GetMapping("/current")
    public PersonDto getCurrentPerson() {
        return PersonAdapter.toPersonDto(Objects.requireNonNull(SecurityUtils.getCurrentPerson()));
    }
}
