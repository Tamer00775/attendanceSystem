package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "person_auth")
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

}
