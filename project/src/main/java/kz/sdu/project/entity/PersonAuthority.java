package kz.sdu.project.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "person_authority")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "person_authority_id",
            updatable = false
    )
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "last_login")
    private LocalDate lastLogin;

    @Column(
            name = "password_hash",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String passwordHash;

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "password_refresh_date")
    private LocalDate passwordRefreshDate;

    @Column(
            name = "isrefreshed",
            nullable = false
    )
    private Boolean isRefreshed;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;


    @OneToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "role_id")
    private Role userRole;
}
