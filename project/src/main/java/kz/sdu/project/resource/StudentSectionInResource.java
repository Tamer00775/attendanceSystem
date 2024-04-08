package kz.sdu.project.resource;

import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.dto.SectionResponseDto;
import kz.sdu.project.service.StudentSectionInService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/student/lesson")
public class StudentSectionInResource {

    private final StudentSectionInService studentSectionInService;
    @PostMapping("/in")
    public ResponseEntity<SectionResponseDto> studentInProcess(
            @RequestBody @Validated SectionInRequest sectionInRequest
    ) {
        return ResponseEntity.ok().body(studentSectionInService.studentInProcess(sectionInRequest));
    }

}
