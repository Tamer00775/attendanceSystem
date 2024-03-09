package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityDTO {

    private Integer specialtyId;
    private String name;
    private String code;

    public static SpecialityDTO fromEntity(kz.sdu.project.entity.Speciality specialty) {
        SpecialityDTO dto = new SpecialityDTO();
        dto.setSpecialtyId(specialty.getSpecialty_id());
        dto.setName(specialty.getName());
        dto.setCode(specialty.getCode());
        return dto;
    }
}
