package kz.sdu.project.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class ReasonDTO {
    @NotBlank(message = "Section shouldn't be empty.")
    private String section;

    @NotBlank(message = "Description shouldn't be empty.")
    private String description;

    @NotBlank(message = "Document shouldn't be empty.")
    private String document;

    @NotBlank(message = "From date shouldn't be empty.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "From date must be in the format yyyy-MM-dd.")
    private String from;

    @NotBlank(message = "To date shouldn't be empty.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "To date must be in the format yyyy-MM-dd.")
    private String to;
}