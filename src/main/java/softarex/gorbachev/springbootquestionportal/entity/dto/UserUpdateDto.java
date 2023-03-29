package softarex.gorbachev.springbootquestionportal.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDto {
    private String email;

    private String password;

    private String newPassword;

    private String firstName;

    private String lastName;

    private String phone;
}
