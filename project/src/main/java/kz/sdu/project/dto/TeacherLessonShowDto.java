package kz.sdu.project.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherLessonShowDto {
    private String sectionName;
    private String courseName;
    private String startTime;
    private String endTime;
    private Boolean activeStart;
    private Boolean activeEnd;
}
