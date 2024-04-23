package kz.sdu.project.specification;

import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class PersonSpecification {
    public static Specification<Person> hasLoginLike(String login) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("login")), "%" + login.toLowerCase() + "%");
    }

    public static Specification<Person> hasRole(String roleName) {
        return (root, query, criteriaBuilder) -> {
            Join<Person, Role> rolesJoin = root.join("RolePerson", JoinType.INNER);
            return criteriaBuilder.equal(criteriaBuilder.lower(rolesJoin.get("role")), roleName.toLowerCase());
        };
    }
}
