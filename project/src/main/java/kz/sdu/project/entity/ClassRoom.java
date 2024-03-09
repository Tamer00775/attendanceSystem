package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "class_room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "class_room_id",
            updatable = false
    )
    private Integer class_room_id;

    @Column(
            name = "room_number",
            unique = true,
            nullable = false
    )
    private String room_number;

    @OneToMany(mappedBy = "classRoom_schedule", cascade = CascadeType.ALL)
    private Set<Schedule> schedules;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassRoom classRoom = (ClassRoom) o;

        return class_room_id != null ? class_room_id.equals(classRoom.class_room_id) : classRoom.class_room_id == null;
    }

    @Override
    public int hashCode() {
        return class_room_id != null ? class_room_id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "class_room_id=" + class_room_id +
                ", room_number='" + room_number + '\'' +
                '}';
    }
}
