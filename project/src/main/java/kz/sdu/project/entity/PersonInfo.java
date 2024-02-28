package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "person_info")
@Builder
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

    @Lob
    @Column(name = "image", length = 1000)
    @JsonIgnore
    private byte[] image;

    @Column(nullable = false)
    private String telephone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "specialty_id")
    private Specialty specialty_person_info;

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
                ", image='" + image + '\'' +
                ", telephone='" + telephone + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }


}
