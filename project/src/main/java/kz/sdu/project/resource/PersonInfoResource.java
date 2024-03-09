package kz.sdu.project.resource;

import kz.sdu.project.dto.PersonInfoDTO;
import kz.sdu.project.entity.PersonInfo;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personInfo/v1")
public class PersonInfoResource {

    private final PersonInfoService personInfoService;

    @Autowired
    public PersonInfoResource(PersonInfoService personInfoService) {
        this.personInfoService = personInfoService;
    }

    @GetMapping
    public ResponseEntity<List<PersonInfoDTO>> all() {
        List<PersonInfo> personInfos = personInfoService.findAll();
        List<PersonInfoDTO> dtoList = personInfos.stream().map(PersonInfoDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonInfoDTO> getById(@PathVariable Integer id) {
        PersonInfo personInfo = personInfoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonInfo with id " + id + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(PersonInfoDTO.fromEntity(personInfo));
    }
}
