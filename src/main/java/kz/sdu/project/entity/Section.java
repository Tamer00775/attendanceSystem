package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "section")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quota")
    private Integer quota;

    @Column(name = "count")
    private Integer count;

    @Column(name = "type")
    private String type;

    @Column(name = "related_section_name")
    private String relatedSectionName;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @JsonBackReference
    private Course course_section;

    @OneToOne(mappedBy = "section_schedule", cascade = CascadeType.ALL)
    @JsonBackReference
    private Schedule schedule;

    @OneToMany(mappedBy = "section_reason_for_absence", cascade = CascadeType.ALL)
    private Set<ReasonForAbsence> reasonsForAbsence;

    @OneToMany(mappedBy = "section_att_info", cascade = CascadeType.ALL)
    private Set<AttendanceInfo> attendanceInfos;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "section_person",
            joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    @JsonManagedReference
    private Set<Person> persons = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return Objects.equals(sectionId, section.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId);
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", name='" + name + '\'' +
                ", quota=" + quota +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", relatedSectionName='" + relatedSectionName + '\'' +
                ", course_section=" + (course_section != null ? course_section.getCourse_id() : "null") +
                '}';
    }

}