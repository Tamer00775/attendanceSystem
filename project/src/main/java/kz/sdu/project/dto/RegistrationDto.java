package kz.sdu.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class RegistrationDto {
    @NotBlank(message = "email must be not blank")
    @Email(message = "Email format is incorrect. Example: user123@mail.ru, user123@yandex.ru, user@gmail.com")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Password must not be blank")
    private String firstname;

    @NotBlank(message = "Last name must not be blank")
    private String lastname;

    @NotBlank(message = "Middle name must not be blank")
    private String middlename;

    @NotBlank(message = "Course year must not be blank")
    private String courseYear;

    @NotNull(message = "Gender must be specified")
    private Boolean gender;

    @NotBlank(message = "Speciality code must not be blank")
    private String specialityCode;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Telephone must be a valid phone number")
    private String telephone;

   // @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Birthdate must be in the ISO format: YYYY-MM-DD")
    private String birthDate;
}