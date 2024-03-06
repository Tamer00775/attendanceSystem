package kz.sdu.project.repository;

import kz.sdu.project.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);
}
