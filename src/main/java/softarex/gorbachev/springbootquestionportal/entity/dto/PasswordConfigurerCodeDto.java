package softarex.gorbachev.springbootquestionportal.entity.dto;

import jakarta.persistence.*;
import lombok.Data;
import softarex.gorbachev.springbootquestionportal.entity.User;

import java.time.LocalDateTime;

@Data
public class PasswordConfigurerCodeDto {

    private String id;

    private String code;

    private UserDto userDto;

    private LocalDateTime expiryDateTime;
}
