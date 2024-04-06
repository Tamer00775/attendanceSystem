package kz.sdu.project.resource;

import kz.sdu.project.dto.SectionInRequest;
import kz.sdu.project.dto.SectionOutRequest;
import kz.sdu.project.service.StudentSectionInService;
import kz.sdu.project.service.StudentSectionOutService;
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
public class StudentSectionResource {

    private final StudentSectionInService studentSectionInService;
    private final StudentSectionOutService studentOutProcess;
    @PostMapping("/in")
    public ResponseEntity<String> studentInProcess(
            @RequestBody @Validated SectionInRequest sectionInRequest
    ) {
        return ResponseEntity.ok().body(studentSectionInService.studentInProcess(sectionInRequest));
    }

    @PostMapping("/out")
    public ResponseEntity<String> studentInProcess(
            @RequestBody @Validated SectionOutRequest sectionOutRequest
    ) {
        return ResponseEntity.ok().body(studentOutProcess.studentOutProcess(sectionOutRequest));
    }

}
