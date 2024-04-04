package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "secret_code_for_check_in")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecretCodeForCheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "secret_code_for_Check_in_id",
            updatable = false
    )
    private Integer secret_code_for_Check_in_id;

    @Column(name = "secret_code", nullable = false)
    private String secret_code;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "is_interpreted", nullable = false)
    private Boolean is_interpreted;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    private Schedule schedule_checkin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecretCodeForCheckIn)) return false;
        SecretCodeForCheckIn that = (SecretCodeForCheckIn) o;
        return Objects.equals(secret_code_for_Check_in_id, that.secret_code_for_Check_in_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secret_code_for_Check_in_id);
    }

    @Override
    public String toString() {
        return "SecretCodeForCheckIn{" +
                "secretCodeForCheckInId=" + secret_code_for_Check_in_id +
                ", secretCode='" + secret_code + '\'' +
                ", created=" + created +
                '}';
    }
}
