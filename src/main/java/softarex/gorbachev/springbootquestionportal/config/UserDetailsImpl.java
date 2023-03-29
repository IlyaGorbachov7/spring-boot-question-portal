package softarex.gorbachev.springbootquestionportal.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import softarex.gorbachev.springbootquestionportal.entity.User;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private User target;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(target.getRoles());
    }

    @Override
    public String getPassword() {
        return target.getPassword();
    }

    public User getTarget(){
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
