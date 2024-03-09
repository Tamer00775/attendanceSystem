package kz.sdu.project.repository;

import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);
}
