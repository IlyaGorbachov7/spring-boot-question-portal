package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegistrationDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String phone;

    public String getFullUsername() {
        return firstName + " " + lastName;
    }
}
