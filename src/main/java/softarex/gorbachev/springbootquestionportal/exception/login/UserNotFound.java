package softarex.gorbachev.springbootquestionportal.exception.login;

import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;

public class UserNotFound extends QuestionPortalServerException {
	public UserNotFound() {
		super("User not found");
	}
}
