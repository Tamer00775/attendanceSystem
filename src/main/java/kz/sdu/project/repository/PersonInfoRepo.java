package kz.sdu.project.repository;

import kz.sdu.project.entity.PersonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonInfoRepo extends JpaRepository<PersonInfo, Integer> {
    @Query("select pi from PersonInfo pi where pi.person.id = :personId")
    Optional<PersonInfo> findByPersonId(@Param("personId") Integer id);
}
