package kz.sdu.project.resource;

import kz.sdu.project.dto.CourseDTO;
import kz.sdu.project.entity.Course;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.CourseService;
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
@RequestMapping("/api/course/v1")
public class CourseResource {

    private final CourseService courseService;

    @Autowired
    public CourseResource(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> all() {
        List<Course> courseList = courseService.findAll();
        List<CourseDTO> courseDTOs = courseList.stream()
                .map(CourseDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseDTOs);
    }

    @GetMapping("/bySpecialityId/{specialityId}")
    public ResponseEntity<List<CourseDTO>> specialityId(@PathVariable Integer specialityId) {
        List<Course> courseList = courseService.findBySpecialityId(specialityId);
        List<CourseDTO> courseDTOs = courseList.stream()
                .map(CourseDTO::fromEntity).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseDTOs);
    }

    @GetMapping("/byCode/{code}")
    public ResponseEntity<CourseDTO> getByCode(@PathVariable String code) {
        Course course = courseService.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Course with code " + code + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CourseDTO.fromEntity(course));
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable Integer id) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + id + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(CourseDTO.fromEntity(course));
    }
}
