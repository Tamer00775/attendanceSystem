package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassRoomDTO {

    private Integer classRoomId;
    private String roomNumber;

    public static ClassRoomDTO fromEntity(kz.sdu.project.entity.ClassRoom classRoom) {
        ClassRoomDTO dto = new ClassRoomDTO();
        dto.setClassRoomId(classRoom.getClass_room_id());
        dto.setRoomNumber(classRoom.getRoom_number());
        return dto;
    }
}
