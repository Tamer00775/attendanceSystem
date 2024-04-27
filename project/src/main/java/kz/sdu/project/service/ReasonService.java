package kz.sdu.project.service;

import kz.sdu.project.dto.ReasonDTO;
import kz.sdu.project.dto.ReasonForAbsenceDTO;
import kz.sdu.project.dto.ReasonStatusDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.ReasonForAbsence;
import kz.sdu.project.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static kz.sdu.project.utils.enums.ReasonStatus.*;

@Service
@AllArgsConstructor
@Slf4j
public class ReasonService {
    private final ReasonForAbsenceService reasonForAbsenceService;

    public ReasonStatusDTO upload(ReasonDTO reasonDTO) {
        log.info("Starting to process reason for absence upload with document ID: {}", reasonDTO.getDocument());
        validReasonDates(reasonDTO);
        ReasonForAbsence reasonForAbsence = createReasonAbsence(reasonDTO);
        log.info("Reason for absence created and sent for user ID: {}", reasonForAbsence.getPerson_reason_for_absence().getId());
        return ReasonStatusDTO
                .builder()
                .status("SUCCESSFULLY_SENT")
                .build();
    }

    private void validReasonDates(ReasonDTO reasonDTO) {
        try {
            LocalDate from = LocalDate.parse(reasonDTO.getFrom());
            LocalDate to = LocalDate.parse(reasonDTO.getTo());
            log.info("Validating dates: from {} to {}", reasonDTO.getFrom(), reasonDTO.getTo());

            if (to.isBefore(from))
                throw new IllegalArgumentException("To date cannot be earlier than From date.");
            if (from.isBefore(LocalDate.of(2024,1,22)))
                throw new IllegalArgumentException("Date is before the allowed system start date.");
        } catch (DateTimeParseException e) {
            log.error("Date parsing error for dates: {} or {}", reasonDTO.getFrom(), reasonDTO.getTo(), e);
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
        log.info("Creating reason for absence for user: {}", person.getId());
        return reasonForAbsenceService.save(reasonForAbsence);
    }

    private LocalDate convertToLocalDate(String from) {
        return LocalDate.parse(from);
    }

    public List<ReasonForAbsenceDTO> allByAdmin() {
        log.info("Retrieving all reasons for absence");
        return reasonForAbsenceService
                .findAll()
                .stream()
                .map(ReasonForAbsenceService::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ReasonForAbsenceDTO> allByStudent() {
        log.info("Retrieving all reasons for absence");
        Person person = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        return reasonForAbsenceService
                .findAllByPersonId(person.getId())
                .stream()
                .map(ReasonForAbsenceService::fromEntity)
                .collect(Collectors.toList());
    }

    public ReasonStatusDTO accept(Integer reasonId) {
        log.info("Accepting reason for absence with ID: {}", reasonId);
        ReasonForAbsence reasonForAbsence = reasonForAbsenceService
                .findById(reasonId)
                .orElseThrow(IllegalArgumentException::new);
        reasonForAbsence.setStatus(ACCEPTED.name());
        reasonForAbsenceService.save(reasonForAbsence);
        return ReasonStatusDTO
                .builder()
                .status(SUCCESSFULLY_ACCEPTED.name())
                .build();
    }

    public ReasonStatusDTO reject(Integer reasonId) {
        log.info("Rejecting reason for absence with ID: {}", reasonId);
        ReasonForAbsence reasonForAbsence = reasonForAbsenceService
                .findById(reasonId)
                .orElseThrow(IllegalArgumentException::new);
        reasonForAbsence.setStatus(REJECTED.name());
        reasonForAbsenceService.save(reasonForAbsence);
        return ReasonStatusDTO
                .builder()
                .status(SUCCESSFULLY_REJECTED.name())
                .build();
    }

    public List<ReasonForAbsenceDTO> all() {
        Person person = Objects.requireNonNull(SecurityUtils.getCurrentPerson());
        if (person.getId() != 1) return allByStudent();
        return allByAdmin();
    }
}