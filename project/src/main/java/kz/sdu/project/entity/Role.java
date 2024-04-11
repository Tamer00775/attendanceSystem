package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "role_id",
            updatable = false
    )
    private Integer role_id;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "RolePerson",fetch = FetchType.EAGER)
    private Set<Person> persons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(role_id, role.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
