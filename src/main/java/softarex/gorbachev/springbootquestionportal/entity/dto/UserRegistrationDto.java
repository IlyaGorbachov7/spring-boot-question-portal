package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;

@Data
public class UserRegistrationDto {

    @NotBlank(message = MSG_NOT_EMPTY)
    @Email(message = MSG_EMAIL, regexp = REGEX_EMAIL)
    private String email;

    @NotBlank(message = MSG_NOT_BLANK)
    private String password;

    @NotBlank(message = MSG_NOT_BLANK)
    private String firstName;

    @NotBlank(message = MSG_NOT_BLANK)
    private String lastName;

    @NotBlank(message = MSG_NOT_BLANK)
    private String phone;

    public String getFullUsername() {
        return firstName + " " + lastName;
    }
}
