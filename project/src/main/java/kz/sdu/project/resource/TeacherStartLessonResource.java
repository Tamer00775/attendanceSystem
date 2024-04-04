package kz.sdu.project.resource;

import kz.sdu.project.dto.RequestBody3DTO;
import kz.sdu.project.service.TeacherStartLessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
@Slf4j
public class TeacherStartLessonResource {

    private final TeacherStartLessonService teacherStartLessonService;

    @Autowired
    public TeacherStartLessonResource(TeacherStartLessonService teacherStartLessonService) {
        this.teacherStartLessonService = teacherStartLessonService;
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> startByManually
            (@RequestBody @Valid RequestBody3DTO requestBody3DTO) {
        log.info("Lesson Start by Teacher process... with {}", requestBody3DTO);
        return ResponseEntity.ok().body(teacherStartLessonService.start(requestBody3DTO));
    }

    @PostMapping("/end")
    public ResponseEntity<Map<String, String>> endByManually
            (@RequestBody @Valid RequestBody3DTO requestBody3DTO) {
        log.info("Lesson end by Teacher process... with {}", requestBody3DTO);
        return ResponseEntity.ok().body(teacherStartLessonService.end(requestBody3DTO));
    }
}
