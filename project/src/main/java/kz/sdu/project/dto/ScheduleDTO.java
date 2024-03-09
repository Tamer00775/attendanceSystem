package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Integer scheduleId;
    private Integer dayOfWeek;
    private String startTime;
    private Integer totalHours;
    private Integer sectionId;
    private Integer classRoomId;

    public static ScheduleDTO fromEntity(kz.sdu.project.entity.Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setScheduleId(schedule.getScheduleId());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setTotalHours(schedule.getTotalHours());
        if (schedule.getSection_schedule() != null) {
            dto.setSectionId(schedule.getSection_schedule().getSectionId());
        }
        if (schedule.getClassRoom_schedule() != null) {
            dto.setClassRoomId(schedule.getClassRoom_schedule().getClass_room_id());
        }
        return dto;
    }
}
