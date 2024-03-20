package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.Person;
import kz.sdu.project.repository.PersonRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepo personRepo;

    @Autowired
    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Optional<Person> findById(Integer id) {
        return personRepo.findById(id);
    }

    @Transactional
    public Optional<Person> findByLogin(String login) {
        return personRepo.findByLogin(login);
    }

    @Transactional
    public Optional<Person> findByLoginAndLoadRoles(String login) {
        return personRepo.findByLoginAndLoadRoles(login);
    }

    public Optional<Person> findByEmail(String email) {
        return personRepo.findPersonByEmail(email);
    }

    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Transactional
    public Person save(Person person) {
        return personRepo.save(person);
    }

    public void delete(Person person) {
        personRepo.delete(person);
    }

    public void deleteById(Integer id) {
        personRepo.deleteById(id);
    }
}

