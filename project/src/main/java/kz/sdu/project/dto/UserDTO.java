package kz.sdu.project.dto;

import kz.sdu.project.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String login;
    private String email;
    private String speciality;

    public static UserDTO fromEntity(Person person) {
        UserDTO dto = new UserDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setMiddleName(person.getMiddleName());
        dto.setLogin(person.getLogin());
        dto.setEmail(person.getEmail());
        if (person.getPersonInfo().getSpecialty_person_info() != null)
           dto.setSpeciality(person.getPersonInfo().getSpecialty_person_info().getName());
        return dto;
    }

}
