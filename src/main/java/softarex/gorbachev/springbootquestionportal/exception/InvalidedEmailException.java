package softarex.gorbachev.springbootquestionportal.exception;

public class InvalidedEmailException extends QuestionPortalServerException{

    public InvalidedEmailException() {
        super("User email entered incorrectly.");
    }

    public InvalidedEmailException(String message) {
        super(message);
    }
}
