package softarex.gorbachev.springbootquestionportal.exception;

public class EmailNotFoundException extends QuestionPortalServerException {
    public EmailNotFoundException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
