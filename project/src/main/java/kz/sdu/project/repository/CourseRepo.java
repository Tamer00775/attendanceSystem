package kz.sdu.project.repository;

import kz.sdu.project.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);
    @Query("select c from Course c where c.speciality_course.specialty_id = ?1")
    List<Course> findBySpecialityId(Integer id);
}
