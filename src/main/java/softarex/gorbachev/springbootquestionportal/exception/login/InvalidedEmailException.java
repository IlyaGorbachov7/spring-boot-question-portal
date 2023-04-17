package softarex.gorbachev.springbootquestionportal.exception.login;

public class InvalidedEmailException extends LoginException {
    public InvalidedEmailException(String email) {
        super(String.format("User email %s is invalided format.", email));
    }

    public InvalidedEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
