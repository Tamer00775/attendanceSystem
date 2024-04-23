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
public class ReasonForAbsenceDTO {

    private Integer reasonId;
    private String description;
    private String document;
    private String status;
    private Boolean isAccepted;
    private LocalDate dateInfo;
    private Integer personId;
    private Integer sectionId;

    public static ReasonForAbsenceDTO fromEntity(kz.sdu.project.entity.ReasonForAbsence reasonForAbsence) {
        ReasonForAbsenceDTO dto = new ReasonForAbsenceDTO();
        dto.setReasonId(reasonForAbsence.getReasonId());
        dto.setDescription(reasonForAbsence.getDescription());
        dto.setDocument(reasonForAbsence.getDocument());
        dto.setStatus(reasonForAbsence.getStatus());
        dto.setIsAccepted(reasonForAbsence.getIsAccepted());
        if (reasonForAbsence.getPerson_reason_for_absence() != null) {
            dto.setPersonId(reasonForAbsence.getPerson_reason_for_absence().getId());
        }
        if (reasonForAbsence.getSection_reason_for_absence() != null) {
            dto.setSectionId(reasonForAbsence.getSection_reason_for_absence().getSectionId());
        }
        return dto;
    }
}
