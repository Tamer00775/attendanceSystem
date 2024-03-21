package kz.sdu.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class RequestBody3DTO {
    @NotBlank(message = "Teacher login shouldn't be empty...")
    private String login;
    @NotBlank(message = "SectionName shouldn't be empty...")
    private String sectionName;

}
