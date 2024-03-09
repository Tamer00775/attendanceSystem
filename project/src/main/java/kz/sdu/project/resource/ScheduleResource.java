package kz.sdu.project.resource;

import kz.sdu.project.dto.ScheduleDTO;
import kz.sdu.project.entity.Schedule;
import kz.sdu.project.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedule/v1")
public class ScheduleResource {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        List<ScheduleDTO> scheduleDTOs = schedules.stream()
                .map(ScheduleDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(scheduleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Integer id) {
        Schedule schedule = scheduleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule with id " + id + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ScheduleDTO.fromEntity(schedule));
    }

    @GetMapping("/byClassRoomId/{classRoomId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByClassRoomId(@PathVariable Integer classRoomId) {
        List<Schedule> schedules = scheduleService.finByClassRoomId(classRoomId);
        List<ScheduleDTO> scheduleDTOs = schedules.stream()
                .map(ScheduleDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(scheduleDTOs);
    }

    @GetMapping("/bySectionName/{name}")
    public ResponseEntity<ScheduleDTO> getScheduleBySectionName(@PathVariable String name) {
        Schedule schedule = scheduleService.findBySectionName(name)
                .orElseThrow(() -> new EntityNotFoundException("Schedule with name " + name + " not found."));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ScheduleDTO.fromEntity(schedule));
    }
}
