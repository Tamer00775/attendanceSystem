package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.Course;
import kz.sdu.project.repository.CourseRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepo courseRepo;

    @Autowired
    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course findByCode(String code) {
        Optional<Course> result = courseRepo.findByCode(code);
        return result.orElse(null);
    }

    public Course findById(Integer id) {
        Optional<Course> result = courseRepo.findById(id);
        return result.orElse(null);
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }
    public Course save(Course course) {
        return courseRepo.save(course);
    }
    public void delete(Course course) {
        courseRepo.delete(course);
    }
    public void deleteById(Integer id) {
        courseRepo.deleteById(id);
    }
}
