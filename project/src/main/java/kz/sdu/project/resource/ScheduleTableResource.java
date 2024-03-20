package kz.sdu.project.resource;


import kz.sdu.project.dto.RequestBodyDTO;
import kz.sdu.project.dto.ScheduleTableFormatDto;
import kz.sdu.project.service.ScheduleTableService;
import kz.sdu.project.service.ScheduleTableShowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/person/schedule")
@Slf4j
@AllArgsConstructor
public class ScheduleTableResource {

    private final ScheduleTableShowService scheduleTableShowService;

    @PostMapping
    public ResponseEntity<List<ScheduleTableFormatDto>> schedule(@RequestBody @Valid RequestBodyDTO requestBodyDTO) {
      log.info("Process getting schedule status...");
      return ResponseEntity.ok().body(scheduleTableShowService.schedule(requestBodyDTO));
    }
}
