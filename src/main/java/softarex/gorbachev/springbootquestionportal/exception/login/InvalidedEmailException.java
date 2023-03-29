package softarex.gorbachev.springbootquestionportal.exception.login;

public class InvalidedEmailException extends LoginException {

    public InvalidedEmailException() {
        super("User email entered incorrectly.");
    }

    public InvalidedEmailException(String message) {
        super(message);
    }
}
