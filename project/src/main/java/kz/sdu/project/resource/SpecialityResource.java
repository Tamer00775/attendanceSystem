package kz.sdu.project.resource;

import kz.sdu.project.dto.SpecialityDTO;
import kz.sdu.project.entity.Speciality;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.SpecialityService;
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
@RequestMapping("/api/speciality/v1")
public class SpecialityResource {

    private final SpecialityService specialityService;

    @Autowired
    public SpecialityResource(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public ResponseEntity<List<SpecialityDTO>> all() {
        List<Speciality> specialities = specialityService.all();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(specialities.stream()
                .map(SpecialityDTO::fromEntity).collect(Collectors.toList()));
    }

    @GetMapping("/byCode/{code}")
    public ResponseEntity<SpecialityDTO> getByCode(@PathVariable String code) {
        Speciality speciality = specialityService.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Speciality with code " + code + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(SpecialityDTO.fromEntity(speciality));
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<SpecialityDTO> getById(@PathVariable Integer id) {
        Speciality speciality = specialityService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Speciality with id " + id + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(SpecialityDTO.fromEntity(speciality));
    }
}
