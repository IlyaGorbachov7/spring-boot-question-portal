package softarex.gorbachev.springbootquestionportal.exception.quest;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class UserCanNotChangeQuestionException extends QuestionPortalServerException {
    public UserCanNotChangeQuestionException(String email) {
        super(String.format("User with email %s can not change this question.", email));
    }
}
