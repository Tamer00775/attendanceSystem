package kz.sdu.project.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SectionInPreInfoDto {
    @NotBlank(message = "teacherName shouldn't be empty...")
    private String teacherName;
    @NotBlank(message = "courseDesc shouldn't be empty...")
    private String courseDesc;
    @NotBlank(message = "startDate shouldn't be empty...")
    private String startDate;
    @NotBlank(message = "sectionName shouldn't be empty...")
    private String sectionName;
}