package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordDTO {

    private Integer recordId;
    private Integer totalPresentHours;
    private Integer totalHours;
    private Integer currentWeek;
    private String recordType;
    private Boolean isWithReason;
    private Integer personId;
    private Integer scheduleId;

    public static AttendanceRecordDTO fromEntity(kz.sdu.project.entity.AttendanceRecord record) {
        AttendanceRecordDTO dto = new AttendanceRecordDTO();
        dto.setRecordId(record.getRecordId());
        dto.setTotalPresentHours(record.getTotal_present_hours());
        dto.setTotalHours(record.getTotal_hours());
        dto.setCurrentWeek(record.getCurrentWeek());
        dto.setRecordType(record.getRecord_type());
        dto.setIsWithReason(record.getIs_with_reason());
        if (record.getPerson_att_record() != null) {
            dto.setPersonId(record.getPerson_att_record().getId());
        }
        if (record.getSchedule_att_record() != null) {
            dto.setScheduleId(record.getSchedule_att_record().getScheduleId());
        }
        return dto;
    }
}
