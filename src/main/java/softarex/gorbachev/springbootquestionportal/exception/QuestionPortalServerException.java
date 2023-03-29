package softarex.gorbachev.springbootquestionportal.exception;

public class QuestionPortalServerException extends RuntimeException{
    public QuestionPortalServerException() {
    }

    public QuestionPortalServerException(String message) {
        super(message);
    }

    public QuestionPortalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
