package kz.sdu.project.service;

import kz.sdu.project.entity.Section;
import kz.sdu.project.repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepo sectionRepo;

    @Autowired
    public SectionService(SectionRepo sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    public Section findByName(String name) {
        Optional<Section> section = sectionRepo.findByName(name);
        return section.orElse(null);
    }

    public List<Section> all() {
        return sectionRepo.findAll();
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
