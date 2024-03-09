package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceInfoDTO {

    private Integer attendanceInfoId;
    private Integer percent;
    private Integer fullTime;
    private Integer reasonTime;
    private Integer personId;
    private Integer sectionId;

    public static AttendanceInfoDTO fromEntity(kz.sdu.project.entity.AttendanceInfo attendanceInfo) {
        AttendanceInfoDTO dto = new AttendanceInfoDTO();
        dto.setAttendanceInfoId(attendanceInfo.getAttendance_info_id());
        dto.setPercent(attendanceInfo.getPercent());
        dto.setFullTime(attendanceInfo.getFull_time());
        dto.setReasonTime(attendanceInfo.getReason_time());
        if (attendanceInfo.getPerson_attendanceInfo() != null) {
            dto.setPersonId(attendanceInfo.getPerson_attendanceInfo().getId());
        }
        if (attendanceInfo.getSection_att_info() != null) {
            dto.setSectionId(attendanceInfo.getSection_att_info().getSectionId());
        }
        return dto;
    }
}
