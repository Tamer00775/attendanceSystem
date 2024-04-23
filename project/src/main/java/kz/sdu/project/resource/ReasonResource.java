package kz.sdu.project.resource;


import kz.sdu.project.dto.ReasonDTO;
import kz.sdu.project.service.ReasonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/reason")
public class ReasonResource {

    private final ReasonService reasonService;

    @PostMapping("/upload")
    public ResponseEntity<ReasonDTO> upload(){
        return null;
    }
}
