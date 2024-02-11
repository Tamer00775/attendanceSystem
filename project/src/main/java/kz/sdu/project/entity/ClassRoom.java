package kz.sdu.project.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "class_room")
@Builder
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

    @OneToOne(mappedBy = "classRoom_schedule", cascade = CascadeType.ALL)
    @JsonBackReference
    private Schedule schedule;

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
