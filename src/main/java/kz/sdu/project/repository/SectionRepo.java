package kz.sdu.project.repository;

import kz.sdu.project.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<Section, Integer> {

    @Query("select s from Section s join fetch s.persons")
    Optional<Section> findByName(String name);
}
