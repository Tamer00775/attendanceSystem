package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reason_for_absence")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReasonForAbsence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reason_id")
    private Integer reasonId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "document", columnDefinition = "TEXT")
    private String document;

    @Column(name = "status")
    private String status;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Column(
            name = "date_info",
            nullable = false
    )
    private LocalDate date_info;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_reason_for_absence;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section_reason_for_absence;

    @Override
    public String toString() {
        return "ReasonForAbsence{" +
                "reasonId=" + reasonId +
                ", description='" + description + '\'' +
                ", document='" + document + '\'' +
                ", status='" + status + '\'' +
                ", isAccepted=" + isAccepted +
                ", date_info=" + date_info +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(reasonId, description, document, status, isAccepted, date_info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReasonForAbsence that = (ReasonForAbsence) o;
        return Objects.equals(reasonId, that.reasonId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(document, that.document) &&
                Objects.equals(status, that.status) &&
                Objects.equals(isAccepted, that.isAccepted) &&
                Objects.equals(date_info, that.date_info);
    }



}

