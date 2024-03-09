package kz.sdu.project.entity;


import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "speciality")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "speciality_id",
            updatable = false
    )
    private Integer specialty_id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code",nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "speciality_course", cascade = CascadeType.ALL)
    private Set<Course> courses;

    @OneToMany(mappedBy = "specialty_person_info", cascade = CascadeType.ALL)
    private List<PersonInfo> personInfos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speciality)) return false;
        Speciality that = (Speciality) o;
        return Objects.equals(specialty_id, that.specialty_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty_id);
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specialty_id=" + specialty_id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
