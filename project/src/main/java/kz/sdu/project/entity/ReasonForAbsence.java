package kz.sdu.project.entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reason_for_absence")
@Builder
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

    @Column(name = "isAccepted")
    private Boolean isAccepted;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "date_info",
            nullable = false
    )
    private LocalDate date_info;

    @ManyToOne  // Changed from @OneToOne to @ManyToOne
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
                ", person_reason_for_absence=" + (person_reason_for_absence != null ? person_reason_for_absence.getId() : "null") +
                ", section_reason_for_absence=" + (section_reason_for_absence != null ? section_reason_for_absence.getSectionId() : "null") +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(reasonId, description, document, status, isAccepted, date_info, person_reason_for_absence, section_reason_for_absence);
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
                Objects.equals(date_info, that.date_info) &&
                Objects.equals(person_reason_for_absence, that.person_reason_for_absence) &&
                Objects.equals(section_reason_for_absence, that.section_reason_for_absence);
    }



}

