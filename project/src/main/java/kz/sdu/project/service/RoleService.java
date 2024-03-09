package kz.sdu.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kz.sdu.project.entity.Role;
import kz.sdu.project.repository.RoleRepo;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Optional<Role> findByRole(String role) {
        return roleRepo.findByRole(role);
    }

    public Optional<Role> findById(Integer id) {
        return roleRepo.findById(id);
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Role save(Role role) {
        return roleRepo.save(role);
    }

    public void delete(Role role) {
        roleRepo.delete(role);
    }

    public void deleteById(Integer id) {
        roleRepo.deleteById(id);
    }

}
