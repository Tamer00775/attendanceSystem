package kz.sdu.project.security;

import kz.sdu.project.entity.Person;
import kz.sdu.project.service.PersonService;
import kz.sdu.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class PersonDetailsService implements UserDetailsService {
    private final PersonService personService;

    @Autowired
    public PersonDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person byLogin = personService.findByLoginAndLoadRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Person with username %s not found",username)));

        return new PersonDetails(byLogin, byLogin.getRolePerson());
    }
}
