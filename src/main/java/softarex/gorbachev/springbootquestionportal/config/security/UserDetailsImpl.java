package softarex.gorbachev.springbootquestionportal.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import softarex.gorbachev.springbootquestionportal.entity.User;
import softarex.gorbachev.springbootquestionportal.entity.dto.UserDto;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private UserDto target;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(target.getRoles());
    }

    @Override
    public String getPassword() {
        return target.getPassword();
    }

    public UserDto getTarget(){
        return target;
    }

    @Override
    public String getUsername() {
        return target.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
