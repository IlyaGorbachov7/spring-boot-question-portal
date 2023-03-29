package softarex.gorbachev.springbootquestionportal.exception;

import softarex.gorbachev.springbootquestionportal.entity.dto.UserLoginDto;

public class LoginException extends QuestionPortalServerException {
    public LoginException() {
        super("Login data entered incorrectly.");
    }

    public LoginException(UserLoginDto loginDto) {
        super(String.format("Login data: email=%s, password=%s entered incorrectly!", loginDto.getEmail(), loginDto.getPassword()));
    }

    public LoginException(String message) {
        super(message);
    }
}
