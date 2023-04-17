package softarex.gorbachev.springbootquestionportal.entity.dto;

import lombok.Data;
import softarex.gorbachev.springbootquestionportal.config.security.Roles;
import softarex.gorbachev.springbootquestionportal.entity.User;

/**
 * This class is full mapping for class {@link User}
 */
@Data
public final class UserDto {

    private String id;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Roles roles;


    public String getFullUsername() {
        return firstName + " " + lastName;
    }
}
