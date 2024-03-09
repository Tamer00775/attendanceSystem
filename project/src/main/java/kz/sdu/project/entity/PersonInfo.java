package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "person_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_info_id")
    private Integer personInfoId;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person_person_info;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "speciality_id")
    private Speciality specialty_person_info;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonInfo that = (PersonInfo) o;
        return Objects.equals(personInfoId, that.personInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personInfoId);
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "personInfoId=" + personInfoId +
                ", gender='" + gender + '\'' +
                ", telephone='" + telephone + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }


}
