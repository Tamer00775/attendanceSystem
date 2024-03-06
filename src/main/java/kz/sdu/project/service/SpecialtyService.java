package kz.sdu.project.service;

import kz.sdu.project.entity.Specialty;
import kz.sdu.project.repository.SpecialityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {

    private final SpecialityRepo specialityRepo;

    @Autowired
    public SpecialtyService(SpecialityRepo specialityRepo) {
        this.specialityRepo = specialityRepo;
    }

    public List<Specialty> all() {
        return specialityRepo.findAll();
    }

    public Specialty findByCode(String code) {
        Optional<Specialty> specialty = specialityRepo.findByCode(code);

        return specialty.orElse(null);
    }

    public void save(Specialty specialty) {
        if (specialty == null || specialty.getCode() == null || specialty.getName() == null) return;

        specialityRepo.save(specialty);
    }

    public void delete(Specialty specialty) {
        specialityRepo.delete(specialty);
    }

}
