package kz.sdu.project.service;

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
}

