package kz.sdu.project.security;

import kz.sdu.project.entity.Person;
import kz.sdu.project.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class PersonDetails implements UserDetails {
    private final Person person;
    private final Set<Role> roles;

    @Override
    // TODO : fix
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authority = new ArrayList<>();
//         roles.forEach(role -> {
//             authority.add(new SimpleGrantedAuthority(role.getRole()));
//         });

        return authority;
    }

    @Override
    public String getPassword() {
        return person.getPersonAuthority().getPasswordHash();
    }

    @Override
    public String getUsername() {
        return person.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return person.getPersonAuthority().getActive();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Person getPerson() {
        return person;
    }
}
