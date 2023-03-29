package softarex.gorbachev.springbootquestionportal.exception.login;

public class InvalidedUserPasswordException extends LoginException {
    public InvalidedUserPasswordException() {
        super("User password entered incorrect.");
    }
}
