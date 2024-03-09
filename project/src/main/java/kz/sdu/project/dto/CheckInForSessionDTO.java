package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckInForSessionDTO {

    private Integer checkInForSessionId;
    private LocalDateTime getPassed;
    private Integer personId;
    private Integer scheduleId;

    public static CheckInForSessionDTO fromEntity(kz.sdu.project.entity.CheckInForSession session) {
        CheckInForSessionDTO dto = new CheckInForSessionDTO();
        dto.setCheckInForSessionId(session.getCheck_in_for_session_id());
        dto.setGetPassed(session.getGet_passed());
        if (session.getPerson_checkin() != null) {
            dto.setPersonId(session.getPerson_checkin().getId());
        }
        if (session.getSchedule() != null) {
            dto.setScheduleId(session.getSchedule().getScheduleId());
        }
        return dto;
    }
}
