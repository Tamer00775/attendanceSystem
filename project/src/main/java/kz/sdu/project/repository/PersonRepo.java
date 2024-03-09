package kz.sdu.project.repository;

import kz.sdu.project.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

    Optional<Person> findByLogin(String login);
    Optional<Person> findPersonByEmail(String email);

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.RolePerson WHERE p.id = ?1")
    Optional<Person> findByIdWithRoles(Integer id);
}
