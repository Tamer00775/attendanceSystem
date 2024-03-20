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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<Map<String, AttendanceStatusDto>> getAllAttendanceStatuses(@RequestBody @Valid RequestBodyDTO requestBodyDTO) {
        log.info("Process Getting AttendanceStatusAll by {}" , requestBodyDTO);
        return ResponseEntity.ok(studentAttStatusService.attStatusByAll(requestBodyDTO));
    }
    
    @PostMapping("/bySection")
    public ResponseEntity<Map<String, List<AttendanceStatusDetailDto>>> getAttendanceStatusBySection(@RequestBody @Valid RequestBody2DTO requestBodyDTO) {
        log.info("Process Getting AttendanceStatusAllBySection by {}" , requestBodyDTO);
        return ResponseEntity.ok(studentAttStatusService.attStatusBySection(requestBodyDTO));
    }

}


