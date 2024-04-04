package kz.sdu.project.service;

import kz.sdu.project.AttendanceSystemApplication;
import kz.sdu.project.dto.RegistrationDto;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.utils.enums.RegistrationErrorCodeEnums;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = AttendanceSystemApplication.class)
public class RegistrationUnitTestIt {
    @Autowired
    private AuthService mockAuthService;
    private RegistrationDto registrationDto;

    @BeforeEach
    public void setupRegistrationData() {
       registrationDto = createRegistrationDto();
    }

    @Test
    public void registrationSuccessfully() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("tamerlankartaev04@gmail.com");
        registrationDto.setSpecialityCode("01");
        registrationDto.setGender(true);
        registrationDto.setLastname("Kartayev");
        registrationDto.setFirstname("Tamerlan");
        registrationDto.setMiddlename("Rustemuly");
        registrationDto.setPassword("Qwerty12");
        registrationDto.setBirthDate("11.10.2003");

        ResponseEntity<HttpStatus> register = mockAuthService.register(registrationDto);
        Assert.assertEquals("Test successfully", HttpStatus.OK, register.getStatusCode());
    }

    @Test
    public void specialityCodeShouldBeInDatabase() {
        RegistrationDto registrationDto = createRegistrationDto();
        registrationDto.setSpecialityCode("2222");
        Assert.assertThrows("Speciality code test", EntityNotFoundException.class,
                () ->  mockAuthService.register(registrationDto));
    }

    @Test
    public void userEmailShouldBeUnique() {
        RegistrationDto registrationRequest = createRegistrationDto();
        registrationRequest.setEmail("tamerlankartaev04@gmail.com");
        Assert.assertNotEquals(registrationDto.getEmail(), registrationRequest.getEmail());
    }

    @Test
    public void checkFieldsForNull() {
        RegistrationDto registrationRequest = new RegistrationDto();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RegistrationDto>> validate = validator.validate(registrationRequest);
        assertTrue(validate.size() == 7);
    }

    @Test
    public void checkPasswordPolicy() {
        RegistrationDto requestRegistration = createRegistrationDto();
        requestRegistration.setPassword("Qwerty123$");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RegistrationDto>> validate = validator.validate(requestRegistration);
        assertTrue(validate.size() == 1);
        ConstraintViolation<RegistrationDto> violation = validate.iterator().next();
        assertEquals(violation.getMessage(), RegistrationErrorCodeEnums.PASSWORD_POLICY_ERROR);
    }

    @Test
    public void userEmailShouldBeEmailFormat() {
        RegistrationDto requestRegistration = createRegistrationDto();
        requestRegistration.setEmail("test test test");
        Set<ConstraintViolation<RegistrationDto>> constraintViolations = validatorFactoryForRegistrationDto(requestRegistration);
        assertTrue(constraintViolations.size() == 1);
        ConstraintViolation<RegistrationDto> violation = constraintViolations.iterator().next();
        assertEquals(violation.getMessage(), RegistrationErrorCodeEnums.EMAIL_ERROR_MESSAGE.getRu());
    }


    private Set<ConstraintViolation<RegistrationDto>> validatorFactoryForRegistrationDto(RegistrationDto requestRegistration) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(requestRegistration);
    }
    private RegistrationDto createRegistrationDto() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("tamerlankartaev04@gmail.com");
        registrationDto.setSpecialityCode("01");
        registrationDto.setGender(true);
        registrationDto.setLastname("Kartayev");
        registrationDto.setFirstname("Tamerlan");
        registrationDto.setMiddlename("Rustemuly");
        registrationDto.setPassword("Qwerty12");
        registrationDto.setBirthDate("11.10.2003");

        return registrationDto;
    }
}
