package kz.sdu.project.service;

import kz.sdu.project.dto.EmailDto;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.PersonAuthority;
import kz.sdu.project.ex_handler.EntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kz.sdu.project.domain.Constants.*;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    private final PersonAuthorityService personAuthorityService;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, PersonService personService, PasswordEncoder passwordEncoder, PersonAuthorityService personAuthorityService) {
        this.javaMailSender = javaMailSender;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.personAuthorityService = personAuthorityService;
    }
    public String sendMail(EmailDto emailDto) {

        Person person = personService.findByEmail(emailDto.getToEmail())
                .orElseThrow(() -> new EntityNotFoundException("In system this email not included..."));
        String toEmail = emailDto.getToEmail(),
                generateSecretCode = RandomStringUtils
                        .random(EIGHT_SIZED_SECRET_CODE, USE_LETTERS_IN_SECRET_CODE, USE_NUMBERS_IN_SECRET_CODE),
                body = String.format("Dear %s %s,\n" +
                                "\n" +
                                "We've received a request to reset the password for your account. To ensure the security of your information, please use the password provided below to access your account and promptly set a new password.\n" +
                                "\n" +
                                "Temporary Password: %s",
                        person.getLastName(), person.getFirstName(),generateSecretCode),
                subject = String.format("Immediate Action Required: Password Reset for %s, %s",
                        person.getLastName(), person.getFirstName());


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yesset.assan@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        javaMailSender.send(message);

        PersonAuthority personAuthority = person.getPersonAuthority();
        personAuthority.setPasswordHash(passwordEncoder.encode(generateSecretCode));
        personAuthority.setPerson(person);
        personAuthorityService.save(personAuthority);

        personService.save(person);

        return "Mail Sent Successfully...";
    }
}
