package kz.sdu.project.resource;

import kz.sdu.project.dto.RequestBody3DTO;
import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.TeacherLessonShowDto;
import kz.sdu.project.service.TeacherLessonListService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacherLessonList")
@Slf4j
@AllArgsConstructor
public class TeacherLessonListResource {
    private  TeacherLessonListService teacherLessonListService;
    @GetMapping
    public ResponseEntity<List<TeacherLessonShowDto>> lessonList() {
        return ResponseEntity.ok().body(teacherLessonListService.lessonList());
    }
}

