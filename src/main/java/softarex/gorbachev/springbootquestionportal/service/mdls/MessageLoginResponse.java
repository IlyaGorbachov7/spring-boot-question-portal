package softarex.gorbachev.springbootquestionportal.service.mdls;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageLoginResponse {
    private String message;
    private String token;
}
