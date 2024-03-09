package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "course_id",
            updatable = false
    )
    private Integer course_id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "specialty_id")
    @JsonBackReference
    private Specialty specialty_course;


    @OneToMany(mappedBy = "course_section", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Section> sections;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(course_id, course.course_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "course_id=" + course_id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", specialty_course=" + (specialty_course != null ? specialty_course.getSpecialty_id() : "null") +
                '}';
    }

}
