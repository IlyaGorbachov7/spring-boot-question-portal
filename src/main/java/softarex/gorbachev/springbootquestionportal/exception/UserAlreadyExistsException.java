package softarex.gorbachev.springbootquestionportal.exception;

public class UserAlreadyExistsException extends QuestionPortalServerException {
    public UserAlreadyExistsException(String email) {
        super(String.format("User with such an email %s already exists",email));
    }
}
