package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "specialty")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "specialty_id",
            updatable = false
    )
    private Integer specialty_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @OneToMany(mappedBy = "specialty_course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Course> courses;

    @OneToOne(mappedBy = "specialty_person_info", cascade = CascadeType.ALL)
    @JsonBackReference
    private PersonInfo personInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialty)) return false;
        Specialty that = (Specialty) o;
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
