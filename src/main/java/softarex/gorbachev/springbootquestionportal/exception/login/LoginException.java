package softarex.gorbachev.springbootquestionportal.exception.login;

import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class LoginException extends QuestionPortalServerException {
    public LoginException() {
        super("Login data entered incorrectly.");
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String mail,String password) {
        super(String.format("Email=%s or password=%s entered incorrectly.", mail, password));
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(UserLoginDto loginDto) {
        super(String.format("Login data: email=%s, password=%s entered incorrectly!", loginDto.getEmail(), loginDto.getPassword()));
    }
}
