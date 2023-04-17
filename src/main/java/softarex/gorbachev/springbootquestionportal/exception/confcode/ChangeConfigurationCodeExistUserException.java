package softarex.gorbachev.springbootquestionportal.exception.confcode;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class ChangeConfigurationCodeExistUserException extends QuestionPortalServerException {
    public ChangeConfigurationCodeExistUserException(String email, long dateTime, String dateTimeTime) {
        super(String.format("Configuration code already will be send on the email: %s.\n" +
                            "The new configuration code can be sent in %d %s", email, dateTime,
                dateTimeTime.toLowerCase()));
    }
}
