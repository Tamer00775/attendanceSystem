package kz.sdu.project;

import kz.sdu.project.dto.AuthDto;
import kz.sdu.project.dto.RequestBody3DTO;
import kz.sdu.project.utils.enums.LessonStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentCheckOutResourceIt {
    private static final String STUDENT_CHECK_IN_URL = "http://localhost:8080/api/student/lesson/out";
    private static final String LOGIN_URL = "http://localhost:8080/open-api/auth/login";

    private final TestRestTemplate testRestTemplate;
    private HttpHeaders headers;

    @Autowired
    public StudentCheckOutResourceIt(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @BeforeEach
    public void login() {
        AuthDto authDto = new AuthDto();
        authDto.setLogin("210107038");
        authDto.setPassword("210107038");

        ResponseEntity<Map> mapResponseEntity = testRestTemplate.postForEntity(LOGIN_URL, authDto, Map.class);
        if (mapResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            String bearerToken = mapResponseEntity.getBody().get("token").toString();
            headers = new HttpHeaders();
            headers.setBearerAuth(bearerToken);
        }
    }

    @Test
    public void userMustBeAuthorized() {
        String sectionName= "12392i34";
        RequestBody3DTO requestBody3DTO = new RequestBody3DTO();
        requestBody3DTO.setSectionName(sectionName);
        HttpEntity<RequestBody3DTO> httpEntity = new HttpEntity<>(requestBody3DTO);
        ResponseEntity<String> exchange = testRestTemplate.exchange(STUDENT_CHECK_IN_URL, HttpMethod.POST, httpEntity, String.class);
        Assertions.assertEquals(exchange.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void sectionCodeMustBeCorrect() {
        String sectionName= "12392i34";
        RequestBody3DTO requestBody3DTO = new RequestBody3DTO();
        requestBody3DTO.setSectionName(sectionName);
        HttpEntity<RequestBody3DTO> httpEntity = new HttpEntity<>(requestBody3DTO, headers);
        ResponseEntity<Map> exchange = testRestTemplate.exchange(STUDENT_CHECK_IN_URL, HttpMethod.POST, httpEntity, Map.class);
        Assertions.assertEquals(exchange.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void studentShouldOutToSectionOnTime() {
        String sectionName = "C0002.02";
        RequestBody3DTO requestBody3DTO = new RequestBody3DTO();
        requestBody3DTO.setSectionName(sectionName);
        HttpEntity<RequestBody3DTO> httpEntity = new HttpEntity<>(requestBody3DTO, headers);
        ResponseEntity<Map> exchange = testRestTemplate.exchange(STUDENT_CHECK_IN_URL, HttpMethod.POST, httpEntity, Map.class);
        Map body = exchange.getBody();
        Object code = body.get("code");
        String shouldBe = LessonStatus.U_CAN_LEAVE_AFTER_15_MIN.name();
        Assertions.assertEquals(code, shouldBe);
    }

    @Test
    public void studentShouldOutToSectionSuccess() {
        String sectionName = "C0002.02";
        RequestBody3DTO requestBody3DTO = new RequestBody3DTO();
        requestBody3DTO.setSectionName(sectionName);
        HttpEntity<RequestBody3DTO> httpEntity = new HttpEntity<>(requestBody3DTO, headers);
        ResponseEntity<Map> exchange = testRestTemplate.exchange(STUDENT_CHECK_IN_URL, HttpMethod.POST, httpEntity, Map.class);
        Map body = exchange.getBody();
        Object code = body.get("code");
        String shouldBe = LessonStatus.LEAVING_SESSION_IS_DONE.name();
        Assertions.assertEquals(code, shouldBe);
    }
}
