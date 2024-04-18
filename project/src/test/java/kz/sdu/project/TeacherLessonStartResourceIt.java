package kz.sdu.project;

import kz.sdu.project.dto.AuthDto;
import kz.sdu.project.dto.RequestBody3DTO;
import kz.sdu.project.utils.enums.LessonStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherLessonStartResourceIt {
    private static final String LOGIN_URL = "http://localhost:8081/open-api/auth/login";
    private static final String TEACHER_START_LESSON = "http://localhost:8081/api/teacher/start";

    private final TestRestTemplate testRestTemplate;
    private HttpHeaders headers;

    @Autowired
    public TeacherLessonStartResourceIt(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    @BeforeEach
    public void login() {
        AuthDto authDto = new AuthDto();
        authDto.setLogin("210107111");
        authDto.setPassword("123456");

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
        ResponseEntity<String> exchange = testRestTemplate.exchange(TEACHER_START_LESSON, HttpMethod.POST, httpEntity, String.class);
        Assertions.assertEquals(exchange.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void sectionCodeMustBeCorrect() {
        String sectionName= "12392i34";
        RequestBody3DTO requestBody3DTO = new RequestBody3DTO();
        requestBody3DTO.setSectionName(sectionName);
        HttpEntity<RequestBody3DTO> httpEntity = new HttpEntity<>(requestBody3DTO, headers);
        ResponseEntity<Map> exchange = testRestTemplate.exchange(TEACHER_START_LESSON, HttpMethod.POST, httpEntity, Map.class);
        Assertions.assertEquals(exchange.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void sectionMustBeStartedByTeacher() {
        String sectionName = "C0002.02";
        RequestBody3DTO requestBody3DTO = new RequestBody3DTO();
        requestBody3DTO.setSectionName(sectionName);
        HttpEntity<RequestBody3DTO> httpEntity = new HttpEntity<>(requestBody3DTO, headers);
        ResponseEntity<Map> exchange = testRestTemplate.exchange(TEACHER_START_LESSON, HttpMethod.POST, httpEntity, Map.class);
        Map body = exchange.getBody();
        System.out.println(body);
        Object status = body.get("status");
        final String shouldBe = LessonStatus.START_LESSON_PROCESS_IS_FAILED.name();
        Assertions.assertEquals(shouldBe, status.toString());
    }
}
