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
public class SecretCodeForCheckInDTO {

    private Integer secretCodeForCheckInId;
    private String secretCode;
    private LocalDateTime created;
    private Integer scheduleId;

    public static SecretCodeForCheckInDTO fromEntity(kz.sdu.project.entity.SecretCodeForCheckIn secretCodeForCheckIn) {
        SecretCodeForCheckInDTO dto = new SecretCodeForCheckInDTO();
        dto.setSecretCodeForCheckInId(secretCodeForCheckIn.getSecret_code_for_Check_in_id());
        dto.setSecretCode(secretCodeForCheckIn.getSecret_code());
        dto.setCreated(secretCodeForCheckIn.getCreated());
        if (secretCodeForCheckIn.getSchedule_checkin() != null) {
            dto.setScheduleId(secretCodeForCheckIn.getSchedule_checkin().getScheduleId());
        }
        return dto;
    }
}
