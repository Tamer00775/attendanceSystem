package kz.sdu.project.service;

import kz.sdu.project.dto.ReasonDTO;
import kz.sdu.project.dto.ReasonStatusDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.ReasonForAbsence;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static kz.sdu.project.utils.enums.ReasonStatus.IN_PROCESS;
import static kz.sdu.project.utils.enums.ReasonStatus.REJECTED;

@Service
@AllArgsConstructor
public class ReasonService {
    private final ReasonForAbsenceService reasonForAbsenceService;
    public ReasonStatusDTO upload(ReasonDTO reasonDTO) {
        validReasonDates(reasonDTO);
        ReasonForAbsence reasonForAbsence = createReasonAbsence(reasonDTO);
        return ReasonStatusDTO
                .builder()
                .status("SUCCESSFULLY_SENT")
                .build();
    }

    private void validReasonDates(ReasonDTO reasonDTO) {
        try {
            LocalDate from = LocalDate.parse(reasonDTO.getFrom());
            LocalDate to = LocalDate.parse(reasonDTO.getTo());

            if (to.isBefore(from))
                throw new IllegalArgumentException("Invalid date format. To can not be early than from...");
            if (from.isBefore(LocalDate.of(2024,1,22)))
                throw new IllegalArgumentException("Invalid date format. Time is out of system...");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use the format yyyy-MM-dd.");
        }
    }

    private ReasonForAbsence createReasonAbsence(ReasonDTO reasonDTO) {
        Person person = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        ReasonForAbsence reasonForAbsence = new ReasonForAbsence();
        reasonForAbsence.setPerson_reason_for_absence(person);
        reasonForAbsence.setDescription(reasonDTO.getDescription());
        reasonForAbsence.setDocument(reasonDTO.getDocument());
        reasonForAbsence.setStatus(IN_PROCESS.name());
        reasonForAbsence.setIsAccepted(false);
        reasonForAbsence.setDate_from(convertToLocalDate(reasonDTO.getFrom()));
        reasonForAbsence.setDate_to(convertToLocalDate(reasonDTO.getTo()));
        return reasonForAbsenceService.save(reasonForAbsence);
    }

    private LocalDate convertToLocalDate(String from) {
        return LocalDate.parse(from);
    }
}
