package kz.sdu.project.utils;

import kz.sdu.project.entity.Role;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Component
@AllArgsConstructor
public class ObjectValidator {
    private final Validator validator;


    public void validateObject(Object obj) {
        Set<ConstraintViolation<Object>> violationSet = validator.validate(obj);
        violationSet.forEach(violation -> {
            String fieldName = ((PathImpl) violation.getPropertyPath()).asString();
            if (violation.getConstraintDescriptor().getAnnotation() instanceof NotNull ||
             violation.getConstraintDescriptor().getAnnotation() instanceof NotEmpty ||
            violation.getConstraintDescriptor().getAnnotation() instanceof NotBlank) {
                throw new NullPointerException(String.format("field %s null or empty or blank", fieldName));
            }

            if (violation.getConstraintDescriptor().getAnnotation() instanceof Size) {
                long min = ((Size) violation.getConstraintDescriptor().getAnnotation()).min();
                long max = ((Size) violation.getConstraintDescriptor().getAnnotation()).max();
                throw new IllegalArgumentException(String.format("field %s must be in range from " +
                        min + " to " + max, fieldName));
            }
            if (violation.getConstraintDescriptor().getAnnotation() instanceof Pattern) {
                String pattern = ((Pattern) violation.getConstraintDescriptor().getAnnotation()).message();
                throw new IllegalArgumentException(String.format("field %s was not have current pattern: %s", fieldName, pattern));
            }
        });
    }

    public static boolean isStudent(Set<Role> rolePerson) {
        return rolePerson
                .stream()
                .anyMatch(role -> role.getRole().equals("ROLE_STUDENT"));
    }

    public static boolean isTeacher(Set<Role> rolePerson) {
        return rolePerson
                .stream()
                .anyMatch(role -> role.getRole().equals("ROLE_TEACHER"));
    }
    public static Integer getCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 1, 22);
        LocalDate now = LocalDate.now();
        int diffInDays = (int) ChronoUnit.DAYS.between(startDate, now);
        return diffInDays / 7 + 1;
    }
}
