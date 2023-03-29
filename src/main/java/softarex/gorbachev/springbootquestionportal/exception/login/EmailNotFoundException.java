package softarex.gorbachev.springbootquestionportal.exception.login;

public class EmailNotFoundException extends LoginException {
    public EmailNotFoundException(String email) {
        super(String.format("User with email %s not found", email));
    }
}
