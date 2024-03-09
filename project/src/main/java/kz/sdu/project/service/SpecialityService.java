package kz.sdu.project.service;

import kz.sdu.project.entity.Speciality;
import kz.sdu.project.entity.Speciality;
import kz.sdu.project.repository.SpecialityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialityService {

    private final SpecialityRepo specialityRepo;

    @Autowired
    public SpecialityService(SpecialityRepo specialityRepo) {
        this.specialityRepo = specialityRepo;
    }

    public List<Speciality> all() {
        return specialityRepo.findAll();
    }

    public Optional<Speciality> findByCode(String code) {
        return specialityRepo.findByCode(code);
    }

    public Optional<Speciality> findById(Integer id) {
        return specialityRepo.findById(id);
    }

    public void save(Speciality speciality) {
        if (speciality == null || speciality.getCode() == null || speciality.getName() == null) return;

        specialityRepo.save(speciality);
    }

    public void delete(Speciality speciality) {
        specialityRepo.delete(speciality);
    }

}
