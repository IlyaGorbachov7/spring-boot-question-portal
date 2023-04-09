package softarex.gorbachev.springbootquestionportal.exception.anwertype;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class AnswerTypeNotFoundException extends QuestionPortalServerException {
    public AnswerTypeNotFoundException() {
        System.out.println("Answer type not found by id");
    }

    public AnswerTypeNotFoundException(String message) {
        super(message);
    }

    public AnswerTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
