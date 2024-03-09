package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonAuthorityDTO {

    private Integer id;
    private LocalDate lastLogin;
    private Boolean active;
    private LocalDate passwordRefreshDate;
    private Boolean isRefreshed;
    private Integer personId;

    public static PersonAuthorityDTO fromEntity(kz.sdu.project.entity.PersonAuthority personAuthority) {
        PersonAuthorityDTO dto = new PersonAuthorityDTO();
        dto.setId(personAuthority.getId());
        dto.setLastLogin(personAuthority.getLastLogin());
        dto.setActive(personAuthority.getActive());
        dto.setPasswordRefreshDate(personAuthority.getPasswordRefreshDate());
        dto.setIsRefreshed(personAuthority.getIsRefreshed());
        if (personAuthority.getPerson() != null) {
            dto.setPersonId(personAuthority.getPerson().getId());
        }
        return dto;
    }
}
