package kz.sdu.project.resource;

import kz.sdu.project.dto.ReasonDTO;
import kz.sdu.project.dto.ReasonForAbsenceDTO;
import kz.sdu.project.dto.ReasonStatusDTO;
import kz.sdu.project.service.ReasonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/reason")
public class ReasonResource {
    private final ReasonService reasonService;

    @GetMapping("")
    public ResponseEntity<List<ReasonForAbsenceDTO>> all() {
        log.info("Received request to fetch all reasons for absence");
        List<ReasonForAbsenceDTO> allReasons = reasonService.all();
        log.info("Returning {} reasons for absence", allReasons.size());
        return ResponseEntity.ok().body(allReasons);
    }

    @PostMapping("/upload")
    public ResponseEntity<ReasonStatusDTO> upload(@RequestBody @Validated ReasonDTO reasonDTO) {
        log.info("Received upload request for reason for absence with description: {}", reasonDTO.getDescription());
        ReasonStatusDTO status = reasonService.upload(reasonDTO);
        log.info("Upload process completed with status: {}", status.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @PostMapping("/accept/{reasonId}")
    public ResponseEntity<ReasonStatusDTO> accept(@PathVariable(name = "reasonId", required = true) Integer reasonId) {
        log.info("Received request to accept reason for absence with ID: {}", reasonId);
        ReasonStatusDTO status = reasonService.accept(reasonId);
        log.info("Acceptance of reason ID {} completed with status: {}", reasonId, status.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @PostMapping("/reject/{reasonId}")
    public ResponseEntity<ReasonStatusDTO> reject(@PathVariable(name = "reasonId", required = true) Integer reasonId) {
        log.info("Received request to reject reason for absence with ID: {}", reasonId);
        ReasonStatusDTO status = reasonService.reject(reasonId);
        log.info("Rejection of reason ID {} completed with status: {}", reasonId, status.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }
}
