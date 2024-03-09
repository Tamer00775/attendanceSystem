package kz.sdu.project.repository;

import kz.sdu.project.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SectionRepo extends JpaRepository<Section, Integer> {

    @Query("select s from Section s join fetch s.persons " +
            "where s.name like ?1")
    Optional<Section> findByName(String name);

    @Query("SELECT s FROM Section s JOIN s.persons p WHERE p.id = ?1")
    List<Section> findByPersonId(Integer personId);

    @Query("SELECT s FROM Section s where s.course_section.course_id = ?1")
    List<Section> findByCourseId(Integer id);


}
