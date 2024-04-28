package kz.sdu.project.resource;

import kz.sdu.project.dto.SectionInfoDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.service.PersonSectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/person/section")
@AllArgsConstructor
public class PersonSectionResource {
    private final PersonSectionService personSectionService;
    @GetMapping("")
    public ResponseEntity<SectionInfoDTO> all() {
        return ResponseEntity.ok().body(personSectionService.all());
    }
}
