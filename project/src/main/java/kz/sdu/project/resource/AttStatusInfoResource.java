package kz.sdu.project.resource;

import kz.sdu.project.dto.AttendanceStatusDetailDto;
import kz.sdu.project.dto.AttendanceStatusDto;
import kz.sdu.project.dto.RequestBody2DTO;
import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.service.StudentAttStatusService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/attStatus")
@Slf4j
public class AttStatusInfoResource {

    private final StudentAttStatusService studentAttStatusService;

    @Autowired
    public AttStatusInfoResource(StudentAttStatusService studentAttStatusService) {
        this.studentAttStatusService = studentAttStatusService;
    }

    @GetMapping
    public ResponseEntity<Map<String, AttendanceStatusDto>> getAllAttendanceStatuses() {
        log.info("Process Getting AttendanceStatusAll ");
        return ResponseEntity.ok(studentAttStatusService.attStatusByAll());
    }
    
    @GetMapping("/bySection/{sectionNames}")
    public ResponseEntity<List<AttendanceStatusDetailDto>> getAttendanceStatusBySection(@PathVariable("sectionNames") String sectionNames) {
        log.info("Process Getting AttendanceStatusAllBySection by {}" , sectionNames);
        return ResponseEntity.ok(studentAttStatusService.attStatusBySection(sectionNames));
    }

}
