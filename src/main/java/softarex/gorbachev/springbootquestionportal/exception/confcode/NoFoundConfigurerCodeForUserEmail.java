package softarex.gorbachev.springbootquestionportal.exception.confcode;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class NoFoundConfigurerCodeForUserEmail extends QuestionPortalServerException {
    public NoFoundConfigurerCodeForUserEmail() {
        super("No found configurer code for user email");
    }

    public NoFoundConfigurerCodeForUserEmail(String email) {
        super(String.format("No found configurer code for user email: %s", email));
    }
}
