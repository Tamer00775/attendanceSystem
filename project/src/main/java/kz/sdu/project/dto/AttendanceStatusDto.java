package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStatusDto {
    private String code;
    private String courseName;
    private String sectionNames;
    private Integer hours;
    private Integer presentHours;
    private Integer absentHours;
    private Integer reasonHours;
    private Integer absenceLimit;

}
