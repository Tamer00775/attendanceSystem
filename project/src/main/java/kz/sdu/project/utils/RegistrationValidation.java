package kz.sdu.project.utils;

import kz.sdu.project.dto.RegistrationDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Specialty;
import kz.sdu.project.service.PersonService;
import kz.sdu.project.service.SpecialtyService;
import kz.sdu.project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RegistrationValidation {
    private final PersonService personService;
    private final SpecialtyService specialtyService;

    public void validation(RegistrationDto dto) {
        if (!dto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z]).{8,}$\n")) {
            throw new IllegalArgumentException("Password must be have at least 8 characters and one uppercase letter");
        }
        Optional<Person> byEmail = personService.findByEmail(dto.getEmail());
        if (byEmail.isPresent()) {
            throw new IllegalArgumentException("Email already registered in our system");
        }
        Specialty byCode = specialtyService.findByCode(dto.getSpecialityCode());
        if (byCode == null) {
            throw new IllegalArgumentException(String.format("Speciality with code %s does not exists", dto.getSpecialityCode()));
        }

    }
}
