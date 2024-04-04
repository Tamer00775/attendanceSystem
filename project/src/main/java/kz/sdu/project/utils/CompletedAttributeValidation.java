package kz.sdu.project.utils;

import kz.sdu.project.entity.AttendanceInfo;
import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class CompletedAttributeValidation {

    public boolean oneOfParamIsNull(AttendanceInfo attendanceInfo) {
        return  (attendanceInfo == null
                || attendanceInfo.getReason_time() == null
                || attendanceInfo.getPercent() == null
                || attendanceInfo.getFull_time() == null
                || attendanceInfo.getSection_att_info().getName() == null);
    }

    public boolean isStudent(Person student) {
        Set<Role> roles = student.getRolePerson();
        Optional<Role> roleOptional =  roles.stream()
                .filter(role -> role.getRole().equals("ROLE_STUDENT"))
                .findFirst();
        return roleOptional.isPresent();
    }
}
