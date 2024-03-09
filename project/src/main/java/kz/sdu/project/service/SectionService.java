package kz.sdu.project.service;

import kz.sdu.project.entity.Section;
import kz.sdu.project.repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SectionService {

    private final SectionRepo sectionRepo;

    @Autowired
    public SectionService(SectionRepo sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    public Optional<Section> findByName(String name) {
        return  sectionRepo.findByName(name);
    }

    public Optional<Section> findById(Integer id) {
        return  sectionRepo.findById(id);
    }

    public List<Section> findByPersonId(Integer id) {
        return  sectionRepo.findByPersonId(id);
    }

    public List<Section> all() {
        return sectionRepo.findAll();
    }

    public List<Section> findByCourseId(Integer id) {
        return sectionRepo.findByCourseId(id);
    }

    public void save(Section section) {
        sectionRepo.save(section);
    }

    public void delete(Section section) {
        sectionRepo.delete(section);
    }

    public void deleteById(Integer id) {
        sectionRepo.deleteById(id);
    }


}
