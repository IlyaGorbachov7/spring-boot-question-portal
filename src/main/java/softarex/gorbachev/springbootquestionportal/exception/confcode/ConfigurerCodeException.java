package softarex.gorbachev.springbootquestionportal.exception.confcode;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class ConfigurerCodeException extends QuestionPortalServerException {
    public ConfigurerCodeException() {
        super("Configurer code entered incorrectly.");
    }

    public ConfigurerCodeException(String mail) {
        super(String.format("Configurer code or user email=%s entered incorrectly.", mail));
    }

    public ConfigurerCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
