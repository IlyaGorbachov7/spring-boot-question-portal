package softarex.gorbachev.springbootquestionportal.exception.quest;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class UserCanNotDeleteQuestionException extends QuestionPortalServerException {
    public UserCanNotDeleteQuestionException(String email) {
        super(String.format("User with email %s can not delete this question.", email));
    }
}
