package kz.sdu.project.repository;

import kz.sdu.project.entity.ReasonForAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReasonForAbsenceRepo extends JpaRepository<ReasonForAbsence, Integer> {

    @Query("select r from ReasonForAbsence r where r.person_reason_for_absence.id = ?1 " +
            "and r.section_reason_for_absence.name like ?2")
    Optional<ReasonForAbsence> findByPersonIdAndSectionName(Integer id, String name);
}
