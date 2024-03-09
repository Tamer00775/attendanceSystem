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

    public static UserDTO fromEntity(Person person) {
        UserDTO dto = new UserDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setMiddleName(person.getMiddleName());
        dto.setLogin(person.getLogin());
        dto.setEmail(person.getEmail());
        return dto;
    }

}
