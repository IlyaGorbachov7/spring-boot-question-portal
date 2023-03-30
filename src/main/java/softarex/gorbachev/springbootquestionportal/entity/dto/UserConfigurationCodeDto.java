package softarex.gorbachev.springbootquestionportal.entity.dto;

import lombok.Data;

@Data
public class UserConfigurationCodeDto {
    private String email;

    private String newPassword;

    private String code;
}
