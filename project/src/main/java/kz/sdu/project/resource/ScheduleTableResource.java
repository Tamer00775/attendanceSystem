package kz.sdu.project.resource;


import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.ScheduleTableFormatDto;
import kz.sdu.project.service.ScheduleTableService;
import kz.sdu.project.service.ScheduleTableShowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/person/schedule")
@Slf4j
@AllArgsConstructor
public class ScheduleTableResource {

    private final ScheduleTableShowService scheduleTableShowService;

    @GetMapping
    public ResponseEntity<List<ScheduleTableFormatDto>> schedule() {
      log.info("Process getting schedule status...");
      return ResponseEntity.ok().body(scheduleTableShowService.schedule());
    }
}
