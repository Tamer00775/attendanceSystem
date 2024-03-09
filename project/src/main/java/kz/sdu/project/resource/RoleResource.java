package kz.sdu.project.resource;

import kz.sdu.project.dto.RoleDTO;
import kz.sdu.project.entity.Role;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import kz.sdu.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/role/v1")
public class RoleResource {

    private final RoleService roleService;

    @Autowired
    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> all() {
        List<Role> roles = roleService.findAll();
        List<RoleDTO> roleDTOs = roles.stream()
                .map(RoleDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Integer id) {
        Role role = roleService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id %d not found.", id)));
        return ResponseEntity.ok(RoleDTO.fromEntity(role));
    }
}
