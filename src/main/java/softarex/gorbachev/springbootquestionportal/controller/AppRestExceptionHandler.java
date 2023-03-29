package softarex.gorbachev.springbootquestionportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import softarex.gorbachev.springbootquestionportal.exception.login.LoginException;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;
import softarex.gorbachev.springbootquestionportal.model.MessageResponse;

@RestControllerAdvice
public class AppRestExceptionHandler {

    @ExceptionHandler({QuestionPortalServerException.class})
    public ResponseEntity<MessageResponse> handlerGeneralExceptions(QuestionPortalServerException ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<MessageResponse> handlerJWTTokenExceptions(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new MessageResponse("JWT token is miss: " + ex.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<MessageResponse> handlerLoginExceptions(LoginException ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }
}
