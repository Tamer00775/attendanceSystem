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
public class PersonInfoDTO {

    private Integer personInfoId;
    private String gender;
    private String telephone;
    private LocalDate birthDate;
    // Optional: If you want to include reference IDs for related entities
    private Integer personId;
    private Integer specialtyId;

    // Method to convert from entity to DTO
    public static PersonInfoDTO fromEntity(kz.sdu.project.entity.PersonInfo personInfo) {
        PersonInfoDTO dto = new PersonInfoDTO();
        dto.setPersonInfoId(personInfo.getPersonInfoId());
        dto.setGender(personInfo.getGender());
        dto.setTelephone(personInfo.getTelephone());
        dto.setBirthDate(personInfo.getBirthDate());
        // Set reference IDs if necessary
        if (personInfo.getPerson_person_info() != null) {
            dto.setPersonId(personInfo.getPerson_person_info().getId());
        }
        if (personInfo.getSpecialty_person_info() != null) {
            dto.setSpecialtyId(personInfo.getSpecialty_person_info().getSpecialty_id());
        }
        return dto;
    }
}
