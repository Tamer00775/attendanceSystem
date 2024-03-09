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

    public Optional<Course> findByCode(String code) {
        return courseRepo.findByCode(code);
    }

    public Optional<Course> findById(Integer id) {
        return courseRepo.findById(id);
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public List<Course> findBySpecialityId(Integer id) {
        return courseRepo.findBySpecialityId(id);
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
