package kz.sdu.project.adapter;

import kz.sdu.project.dto.PersonDto;
import kz.sdu.project.dto.RegistrationDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.PersonAuthority;
import kz.sdu.project.entity.PersonInfo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PersonAdapter {

    public static Person toEntity(RegistrationDto registrationDto) {
        Person person = new Person();
        person.setEmail(registrationDto.getEmail());
        person.setLogin(registrationDto.getEmail().substring(0, registrationDto.getEmail().indexOf("@")));
        person.setFirstName(registrationDto.getFirstname());
        person.setLastName(registrationDto.getLastname());
        person.setMiddleName(registrationDto.getMiddlename());

        return person;
    }

    public static PersonInfo toEntityPersonInfo(RegistrationDto registrationDto) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setTelephone(registrationDto.getTelephone());
        personInfo.setGender(registrationDto.getGender() ? "WOMEN" : "MEN");
        // personInfo.setImage(getDefaultImage());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(registrationDto.getBirthDate(), dateTimeFormatter);
        personInfo.setBirthDate(localDate);

        return personInfo;
    }

    public static PersonAuthority toEntityPersonAuth(RegistrationDto registrationDto) {
        PersonAuthority personAuthority = new PersonAuthority();
        personAuthority.setActive(true);
        personAuthority.setPasswordRefreshDate(LocalDate.now().plus(4, ChronoUnit.YEARS));
        personAuthority.setIsRefreshed(false);

        return personAuthority;
    }

    public static PersonDto toPersonDto(Person person) {
        PersonInfo personInfo = person.getPersonInfo();
        PersonAuthority personAuthority = person.getPersonAuthority();
        String code = personInfo.getSpecialty_person_info().getCode();
        return PersonDto.builder()
                .login(person.getLogin())
                .middlename(person.getMiddleName())
                .firstname(person.getFirstName())
                .lastname(person.getLastName())
                .email(person.getEmail())
                .lastLogin(personAuthority.getLastLogin())
                .telephone(person.getPersonInfo().getTelephone())
                .courseCode(code)
                .build();
    }

    private static byte[] getDefaultImage() {
        try {
            ClassPathResource imgFile = new ClassPathResource("static_resources/User.jpg");
            return StreamUtils.copyToByteArray(imgFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load default image", e);
        }
    }
}
