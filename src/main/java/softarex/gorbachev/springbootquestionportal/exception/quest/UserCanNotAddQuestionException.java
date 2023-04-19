package softarex.gorbachev.springbootquestionportal.exception.quest;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class UserCanNotAddQuestionException extends QuestionPortalServerException {
    public UserCanNotAddQuestionException(String email) {
        super(String.format("User with email %s can not add this question.", email));
    }
}
