package softarex.gorbachev.springbootquestionportal.exception.login;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class UserAlreadyExistsException extends LoginException {
    public UserAlreadyExistsException(String email) {
        super(String.format("User with such an email %s already exists",email));
    }
}
