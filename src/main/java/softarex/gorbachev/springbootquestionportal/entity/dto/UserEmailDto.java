package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;

@Data
public class UserEmailDto {

    @NotBlank(message = MSG_NOT_EMPTY)
    @Email(message = MSG_EMAIL, regexp = REGEX_EMAIL)
    private String email;
}
