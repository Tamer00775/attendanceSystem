package kz.sdu.project.dto;

import kz.sdu.project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private Integer roleId;
    private String role;
    private String description;

    public static RoleDTO fromEntity(Role roleEntity) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(roleEntity.getRole_id());
        dto.setRole(roleEntity.getRole());
        dto.setDescription(roleEntity.getDescription());
        return dto;
    }
}
