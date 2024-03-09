package kz.sdu.project.resource;

import kz.sdu.project.dto.SectionDTO;
import kz.sdu.project.entity.Section;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/section/v1")
public class SectionResource {

    private final SectionService sectionService;

    @Autowired
    public SectionResource(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<List<SectionDTO>> all() {
        List<Section> sections = sectionService.all();
        List<SectionDTO> sectionDTOs = sections.stream()
                .map(SectionDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.accepted().body(sectionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionDTO> getById(@PathVariable Integer id) {
        Section section = sectionService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section with id " + id + " not found."));
        return ResponseEntity.accepted().body(SectionDTO.fromEntity(section));
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<SectionDTO> getByName(@PathVariable String name) {
        Section section = sectionService.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Section with name " + name + " not found."));
        return ResponseEntity.accepted().body(SectionDTO.fromEntity(section));
    }

    @GetMapping("/byCourse/{courseId}")
    public ResponseEntity<List<SectionDTO>> findByCourseId(@PathVariable Integer courseId) {
        List<Section> sections = sectionService.findByCourseId(courseId);
        List<SectionDTO> sectionDTOs = sections.stream()
                .map(SectionDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.accepted().body(sectionDTOs);
    }
}
