package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;

@Data
public class UserRegistrationDto {

    @NotBlank(message = MSG_NOT_EMPTY)
    @Email(message = MSG_EMAIL, regexp = REGEX_EMAIL)
    private String email;

    @NotBlank(message = MSG_NOT_BLANK)
    @Size(max = MAX_SIZE_PASSWORD, message = MSG_SIZE_PASSWORD)
    private String password;

    @NotBlank(message = MSG_NOT_BLANK)
    @Pattern(regexp = REGEX_NAME, message = MSG_FIRSTNAME)
    @Size(max = MAX_SIZE_FIRSTNAME, message = MSG_SIZE_FIRSTNAME)
    private String firstName;

    @NotBlank(message = MSG_NOT_BLANK)
    @Pattern(regexp = REGEX_NAME, message = MSG_LASTNAME)
    @Size(max = MAX_SIZE_LASTNAME, message = MSG_SIZE_LASTNAME)
    private String lastName;

    @NotBlank(message = MSG_NOT_BLANK)
    @Size(max = MAX_SIZE_PHONE, message = MSG_SIZE_PHONE)
    private String phone;

    public String getFullUsername() {
        return firstName + " " + lastName;
    }
}
