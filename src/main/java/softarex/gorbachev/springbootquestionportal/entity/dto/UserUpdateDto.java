package softarex.gorbachev.springbootquestionportal.entity.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static softarex.gorbachev.springbootquestionportal.constant.ValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDto {
    @NotBlank(message = MSG_NOT_EMPTY)
    @Email(message = MSG_EMAIL, regexp = REGEX_EMAIL)
    private String email;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String password;

    private String newPassword;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String firstName;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String lastName;

    @NotBlank(message = MSG_NOT_EMPTY)
    private String phone;
}
