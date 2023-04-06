package softarex.gorbachev.springbootquestionportal.entity.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;

@Data
public class UserLoginDto {

    @NotBlank(message = MSG_NOT_EMPTY)
    @Email(message = MSG_EMAIL, regexp = REGEX_EMAIL)
    private String email;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String password;
}
