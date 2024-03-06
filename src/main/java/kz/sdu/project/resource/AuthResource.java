package kz.sdu.project.resource;

import kz.sdu.project.dto.AuthDto;
import kz.sdu.project.dto.RegistrationDto;
import kz.sdu.project.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/open-api/auth")
@Slf4j
public class AuthResource {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody  @Valid RegistrationDto registrationDto) {
        log.info("Register process with {}" , registrationDto);
        return authService.register(registrationDto);
    }

}
