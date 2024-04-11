package kz.sdu.project.resource;


import kz.sdu.project.dto.SectionInPreInfoDto;
import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.dto.SectionResponseDto;
import kz.sdu.project.service.SectionInPreInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/student/lesson/in_pre_info")
public class SectionInPreInfoResource {
    private final SectionInPreInfoService sectionInPreInfoService;
    @GetMapping("/{courseCode}")
    public ResponseEntity<SectionInPreInfoDto> studentInProcess(
            @PathVariable("courseCode") String courseCode
    ) {
        return ResponseEntity.ok().body(sectionInPreInfoService.getInPreInfo(courseCode));
    }

}
