package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.PersonAuthority;
import kz.sdu.project.repository.PersonAuthorityRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PersonAuthorityService {

    private final PersonAuthorityRepo personAuthorityRepo;

    @Autowired
    public PersonAuthorityService(PersonAuthorityRepo personAuthorityRepo) {
        this.personAuthorityRepo = personAuthorityRepo;
    }

    public PersonAuthority findByPersonId(Integer personId) {
        Optional<PersonAuthority> result = personAuthorityRepo.findByPersonId(personId);
        return result.orElse(null);
    }

    public PersonAuthority findById(Integer id) {
        Optional<PersonAuthority> result = personAuthorityRepo.findById(id);
        return result.orElse(null);
    }

    public List<PersonAuthority> findAll() {
        return personAuthorityRepo.findAll();
    }

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

