package kz.sdu.project.service;

import kz.sdu.project.dto.ReasonForAbsenceDTO;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.ReasonForAbsence;
import kz.sdu.project.repository.ReasonForAbsenceRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ReasonForAbsenceService {

    private final ReasonForAbsenceRepo reasonForAbsenceRepo;

    @Autowired
    public ReasonForAbsenceService(ReasonForAbsenceRepo reasonForAbsenceRepo) {
        this.reasonForAbsenceRepo = reasonForAbsenceRepo;
    }

    public Optional<ReasonForAbsence> findByPersonIdAndSectionName(Integer personId, String sectionName) {
        return reasonForAbsenceRepo.findByPersonIdAndSectionName(personId, sectionName);
    }

    public Optional<ReasonForAbsence> findById(Integer reasonId) {
        return reasonForAbsenceRepo.findById(reasonId);
    }

    public List<ReasonForAbsence> findAll() {
        return reasonForAbsenceRepo.findAll();
    }

    public List<ReasonForAbsence> findAllByPersonId(Integer id) {
        return reasonForAbsenceRepo.findAllByPersonId(id);
    }

    public ReasonForAbsence save(ReasonForAbsence reasonForAbsence) {
        return reasonForAbsenceRepo.save(reasonForAbsence);
    }

    public void delete(ReasonForAbsence reasonForAbsence) {
        reasonForAbsenceRepo.delete(reasonForAbsence);
    }

    public void deleteById(Integer id) {
        reasonForAbsenceRepo.deleteById(id);
    }
    public static ReasonForAbsenceDTO fromEntity(kz.sdu.project.entity.ReasonForAbsence reasonForAbsence) {
        ReasonForAbsenceDTO dto = new ReasonForAbsenceDTO();
        Person person = reasonForAbsence.getPerson_reason_for_absence();
        Section section = reasonForAbsence.getSection_reason_for_absence();
        dto.setId(reasonForAbsence.getReasonId());
        dto.setFullName(person.getFirstName() + " " + person.getLastName() + " " + person.getMiddleName());
        dto.setSection(section.getName() + " - " + section.getCourse_section().getName());
        dto.setDescription(reasonForAbsence.getDescription());
        dto.setDocument(reasonForAbsence.getDocument());
        dto.setStatus(reasonForAbsence.getStatus());
        dto.setDateFrom(reasonForAbsence.getDate_from());
        dto.setDateTo(reasonForAbsence.getDate_to());
        return dto;
    }
}

