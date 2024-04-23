package kz.sdu.project.resource;


import kz.sdu.project.dto.ReasonDTO;
import kz.sdu.project.dto.ReasonStatusDTO;
import kz.sdu.project.service.ReasonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/reason")
public class ReasonResource {
    private final ReasonService reasonService;

    @PostMapping("/upload")
    public ResponseEntity<ReasonStatusDTO> upload(
            @RequestBody @Validated ReasonDTO reasonDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reasonService.upload(reasonDTO));
    }
}
