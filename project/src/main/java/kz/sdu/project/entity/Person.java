package kz.sdu.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> RolePerson;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private PersonAuthority personAuthority;

    @OneToOne(mappedBy = "person_person_info", cascade = CascadeType.ALL)
    private PersonInfo personInfo;

    @OneToMany(mappedBy = "person_reason_for_absence", cascade = CascadeType.ALL)
    private Set<ReasonForAbsence> reasonsForAbsence;

    @OneToMany(mappedBy = "person_att_record", cascade = CascadeType.ALL)
    private Set<AttendanceRecord> attendanceRecords;

    @OneToMany(mappedBy = "person_attendanceInfo", cascade = CascadeType.ALL)
    private Set<AttendanceInfo> attendanceInfos;

    @ManyToMany(mappedBy = "persons", cascade = CascadeType.ALL)
    private Set<Section> sections = new HashSet<>();

    @OneToMany(mappedBy = "person_checkin", cascade = CascadeType.ALL)
    private Set<CheckInForSession> checkInsForSession = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
