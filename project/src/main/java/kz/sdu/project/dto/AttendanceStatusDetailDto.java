package kz.sdu.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStatusDetailDto {
    private LocalDate date;
    private String hour;
    private String attStatus;
    private String place;
    private String type;
    private Integer week;
}
