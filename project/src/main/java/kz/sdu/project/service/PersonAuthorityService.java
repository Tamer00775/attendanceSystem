package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.PersonAuthority;
import kz.sdu.project.repository.PersonAuthorityRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonAuthorityService {

    private final PersonAuthorityRepo personAuthorityRepo;

    @Autowired
    public PersonAuthorityService(PersonAuthorityRepo personAuthorityRepo) {
        this.personAuthorityRepo = personAuthorityRepo;
    }

    public Optional<PersonAuthority> findByPersonId(Integer personId) {
        return personAuthorityRepo.findByPersonId(personId);
    }

    public Optional<PersonAuthority> findById(Integer id) {
        return personAuthorityRepo.findById(id);
    }

    public List<PersonAuthority> findAll() {
        return personAuthorityRepo.findAll();
    }

    @Transactional
    public PersonAuthority save(PersonAuthority personAuthority) {
        return personAuthorityRepo.save(personAuthority);
    }

    public void delete(PersonAuthority personAuthority) {
        personAuthorityRepo.delete(personAuthority);
    }

    public void deleteById(Integer id) {
        personAuthorityRepo.deleteById(id);
    }
}

