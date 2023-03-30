package softarex.gorbachev.springbootquestionportal.exception.confcode;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class NoMatchEmailException extends QuestionPortalServerException {

    public NoMatchEmailException() {
    }

    public NoMatchEmailException(String message) {
        super(message);
    }

    public NoMatchEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
