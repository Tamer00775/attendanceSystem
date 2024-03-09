package kz.sdu.project.repository;

import kz.sdu.project.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepo extends JpaRepository<Speciality, Integer> {
    Optional<Speciality> findByCode(String code);
}
