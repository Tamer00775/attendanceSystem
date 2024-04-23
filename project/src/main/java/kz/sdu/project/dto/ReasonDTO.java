package kz.sdu.project.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReasonDTO {
    @NotBlank(message = "description shouldn't be empty...")
    private String description;
    @NotBlank(message = "document shouldn't be empty...")
    private String document;
    @NotBlank(message = "from shouldn't be empty...")
    private String from;
    @NotBlank(message = "to shouldn't be empty...")
    private String to;
}
