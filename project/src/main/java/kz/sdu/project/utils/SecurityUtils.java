package kz.sdu.project.utils;

import kz.sdu.project.entity.Person;
import kz.sdu.project.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Person getCurrentPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            PersonDetails personDetails = (PersonDetails) principal;
            return personDetails.getPerson();
        }
        return null;
    }

}
