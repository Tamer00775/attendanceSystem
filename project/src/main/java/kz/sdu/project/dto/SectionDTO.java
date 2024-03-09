package kz.sdu.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

    private Integer sectionId;
    private String name;
    private Integer quota;
    private Integer count;
    private String type;
    private String relatedSectionName;
    private Integer courseId;
    private String courseName;

    public static SectionDTO fromEntity(kz.sdu.project.entity.Section section) {
        SectionDTO dto = new SectionDTO();
        dto.setSectionId(section.getSectionId());
        dto.setName(section.getName());
        dto.setQuota(section.getQuota());
        dto.setCount(section.getCount());
        dto.setType(section.getType());
        dto.setRelatedSectionName(section.getRelatedSectionName());
        if (section.getCourse_section() != null) {
            dto.setCourseId(section.getCourse_section().getCourse_id());
            dto.setCourseName(section.getCourse_section().getName()); // Assuming Course entity has a name field
        }
        return dto;
    }
}
