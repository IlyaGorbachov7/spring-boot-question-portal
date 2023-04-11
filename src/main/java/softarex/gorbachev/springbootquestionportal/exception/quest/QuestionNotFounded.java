package softarex.gorbachev.springbootquestionportal.exception.quest;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class QuestionNotFounded extends QuestionPortalServerException {
    public QuestionNotFounded() {
        super("Question is not founded");
    }

    public QuestionNotFounded(String id) {
        super(String.format("Question is not founded by id: %s", id));
    }
}
