package kz.sdu.project.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ReasonStatusDTO {
    @NotBlank(message = "status shouldn't be empty...")
    private String status;
}
