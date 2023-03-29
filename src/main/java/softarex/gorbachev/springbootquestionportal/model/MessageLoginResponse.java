package softarex.gorbachev.springbootquestionportal.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageLoginResponse {
    private String message;
    private String token;
}
