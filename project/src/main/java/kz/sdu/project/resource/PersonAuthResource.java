package kz.sdu.project.resource;

import kz.sdu.project.dto.PersonAuthorityDTO;
import kz.sdu.project.entity.PersonAuthority;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.PersonAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personAuth/v1")
public class PersonAuthResource {

    private final PersonAuthorityService personAuthorityService;

    @Autowired
    public PersonAuthResource(PersonAuthorityService personAuthorityService) {
        this.personAuthorityService = personAuthorityService;
    }

    @GetMapping
    public ResponseEntity<List<PersonAuthorityDTO>> all() {
        List<PersonAuthority> personAuthorities = personAuthorityService.findAll();
        List<PersonAuthorityDTO> dtoList = personAuthorities.stream()
                .map(PersonAuthorityDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtoList);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<PersonAuthorityDTO> getById(@PathVariable Integer id) {
        PersonAuthority personAuthority = personAuthorityService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("PersonAuthority with id %d not found.", id)));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(PersonAuthorityDTO.fromEntity(personAuthority));
    }
}
