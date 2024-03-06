package kz.sdu.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PersonDto {
    public String lastname;
    private String firstname;
    private String middlename;
    private String login;
    private String email;
    private LocalDate lastLogin;
    private String courseCode;
    private String telephone;
}
