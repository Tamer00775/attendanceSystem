package kz.sdu.project.repository;

import kz.sdu.project.entity.PersonAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonAuthorityRepo extends JpaRepository<PersonAuthority, Integer> {
    @Query("select p from PersonAuthority p where p.person.id = ?1")
    Optional<PersonAuthority> findByPersonId(Integer personId);
}
