package softarex.gorbachev.springbootquestionportal.exception.confcode;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class NoMatchesBetweenConfigurerCodeException extends QuestionPortalServerException {
    public NoMatchesBetweenConfigurerCodeException() {
        super("Configuration code not matches");
    }

    public NoMatchesBetweenConfigurerCodeException(String codeFromBD) {
        super(String.format("Entered Configuration code  not matches configure code : %s", codeFromBD));
    }

    public NoMatchesBetweenConfigurerCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
