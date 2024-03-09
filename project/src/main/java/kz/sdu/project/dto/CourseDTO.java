package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Integer courseId;
    private String code;
    private String name;
    private String description;
    private Integer specialtyId;

    public static CourseDTO fromEntity(kz.sdu.project.entity.Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourse_id());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        if (course.getSpeciality_course() != null) {
            dto.setSpecialtyId(course.getSpeciality_course().getSpecialty_id());
        }
        return dto;
    }
}
