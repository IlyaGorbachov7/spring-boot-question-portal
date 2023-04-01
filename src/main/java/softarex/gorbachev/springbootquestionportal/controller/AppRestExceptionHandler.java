package softarex.gorbachev.springbootquestionportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import softarex.gorbachev.springbootquestionportal.exception.login.LoginException;
import softarex.gorbachev.springbootquestionportal.exception.QuestionPortalServerException;
import softarex.gorbachev.springbootquestionportal.service.mdls.BadMessageResponse;

@RestControllerAdvice
public class AppRestExceptionHandler {

    @ExceptionHandler({QuestionPortalServerException.class})
    public ResponseEntity<BadMessageResponse> handlerGeneralExceptions(QuestionPortalServerException ex) {
        return ResponseEntity.badRequest().body(new BadMessageResponse(ex.getMessage()));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<BadMessageResponse> handlerJWTTokenExceptions(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(new BadMessageResponse("Sorry brooo! JWT token is missed: " + ex.getMessage()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<BadMessageResponse> handlerLoginExceptions(LoginException ex) {
        return ResponseEntity.badRequest().body(new BadMessageResponse(ex.getMessage()));
    }
}
