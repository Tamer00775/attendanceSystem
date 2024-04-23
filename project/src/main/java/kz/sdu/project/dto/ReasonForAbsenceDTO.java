package kz.sdu.project.dto;

import kz.sdu.project.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReasonForAbsenceDTO {
    private String description;
    private String document;
    private String status;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String fullName;
    private Integer id;
}
