package kz.sdu.project.repository;

import kz.sdu.project.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepo extends JpaRepository<Specialty, Integer> {
    Optional<Specialty> findByCode(String code);
}
