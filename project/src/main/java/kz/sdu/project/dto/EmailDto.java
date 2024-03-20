package kz.sdu.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailDto {
    @NotBlank(message = "Email must be not blank")
    @Email(message = "Email format is incorrect. Example: user123@mail.ru, user123@yandex.ru, user@gmail.com")
    private String toEmail;
}
