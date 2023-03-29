package softarex.gorbachev.springbootquestionportal.entity.dto;


import lombok.Data;

@Data
public class UserSessionDto {
    private String email;

    private String firstName;

    private String lastName;

    private String phone;
}
