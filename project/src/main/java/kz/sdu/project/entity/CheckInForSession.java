package kz.sdu.project.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "schedule")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckInForSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "check_in_for_session_id",
            updatable = false
    )
    private Integer check_in_for_session_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "get_passed", nullable = false)
    private LocalDateTime get_passed;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckInForSession)) return false;
        CheckInForSession that = (CheckInForSession) o;
        return Objects.equals(check_in_for_session_id, that.check_in_for_session_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(check_in_for_session_id);
    }

    @Override
    public String toString() {
        return "CheckInForSession{" +
                "checkInForSessionId=" + check_in_for_session_id +
                ", getPassed=" + get_passed +
                '}';
    }
}
